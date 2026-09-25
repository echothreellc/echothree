// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.ui.cli.database.util.javagen;

import com.echothree.ui.cli.database.util.definition.Database;
import com.echothree.ui.cli.database.util.definition.DatabaseDefinitionParser;
import com.echothree.ui.cli.database.util.definition.Databases;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.HexFormat;
import javax.sql.rowset.RowSetMetaDataImpl;
import javax.sql.rowset.RowSetProvider;
import javax.tools.ToolProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FactoryJavaGeneratorTest {

    @TempDir
    Path directory;

    @Test
    public void entityAndValueReadersUseUuidConversion() throws Exception {
        var databases = new Databases();
        new DatabaseDefinitionParser(databases).parse("/DatabaseDefinition.xml");
        var database = databases.getDatabase("echothree");
        var generator = new FactoryJavaGenerator(false, database);
        var table = database.getTable("EntityInstances");

        var source = new StringWriter();
        try(var writer = new PrintWriter(source)) {
            generator.writeFactoryValueFunctions(writer, table);
            generator.writeFactoryEntityFunctions(writer, table);
        }
        assertEquals(2, source.toString().split("getUuidFromResultSet\\(rs, ENI_UUID\\)", -1).length - 1);
        assertFalse(source.toString().contains("rs.getString(ENI_UUID)"));

        var statements = new StringWriter();
        try(var writer = new PrintWriter(statements)) {
            generator.writeFactoryInstanceVariables(writer, table);
        }
        var sql = statements.toString();
        assertFalse(sql.contains("BIN_TO_UUID("));
        assertEquals(2, sql.lines().filter(line -> line.contains("SQL_SELECT_READ_")
                && line.contains(", eni_uuid FROM entityinstances")).count());
        assertFalse(sql.contains("UUID_TO_BIN("));
        assertTrue(sql.contains("eni_uuid = ?"));
        assertTrue(sql.contains("VALUES (?, ?, ?, ?)"));

        var bindings = new StringWriter();
        try(var writer = new PrintWriter(bindings)) {
            generator.writeFactoryCreateFunctions(writer, table);
            generator.writeFactoryStoreFunctions(writer, table);
        }
        assertTrue(bindings.toString().contains("_ps.setBytes(4, uuidToBytes(eni_uuid))"));
        assertTrue(bindings.toString().contains("_ps.setBytes(3, uuidToBytes(eni_uuid))"));
        assertTrue(bindings.toString().contains("_ps.setNull(4, Types.BINARY)"));
        assertTrue(bindings.toString().contains("_ps.setNull(3, Types.BINARY)"));
    }

    @Test
    public void generatedUuidConversionsSupportBinaryAndNullRoundTrips() throws Exception {
        var source = new StringWriter();
        try(var writer = new PrintWriter(source)) {
            writer.println("import java.sql.*; public class UuidReader {");
            // Supply only the exception type needed to compile the reader in isolation.
            writer.println("static class PersistenceDatabaseException extends RuntimeException {"
                    + " PersistenceDatabaseException(String message) { super(message); } }");
            new FactoryJavaGenerator(false, new Database("UuidTest")).writeFactoryUuidFunction(writer);
            writer.println("}");
        }
        var sourceFile = directory.resolve("UuidReader.java");
        Files.writeString(sourceFile, source.toString());
        assertEquals(0, ToolProvider.getSystemJavaCompiler().run(null, null, null,
                "-d", directory.toString(), sourceFile.toString()));

        try(var loader = new URLClassLoader(new java.net.URL[] { directory.toUri().toURL() })) {
            var reader = loader.loadClass("UuidReader").getDeclaredMethod("getUuidFromResultSet", ResultSet.class, String.class);
            reader.setAccessible(true);

            var writer = loader.loadClass("UuidReader").getDeclaredMethod("uuidToBytes", String.class);
            // Fixed database byte representation, including high-bit bytes and leading zeroes.
            var uuid = "00112233-4455-6677-8899-aabbccddeeff";
            var bytes = (byte[])writer.invoke(null, uuid);
            assertArrayEquals(HexFormat.of().parseHex("00112233445566778899aabbccddeeff"), bytes);
            assertArrayEquals(bytes, (byte[])writer.invoke(null, "00112233-4455-6677-8899-AABBCCDDEEFF"));
            assertNull(writer.invoke(null, (Object)null));
            var metadata = new RowSetMetaDataImpl();
            metadata.setColumnCount(1);
            metadata.setColumnName(1, "eni_uuid");
            metadata.setColumnType(1, Types.BINARY);

            try(var rows = RowSetProvider.newFactory().createCachedRowSet()) {
                rows.setMetaData(metadata);
                rows.moveToInsertRow();
                rows.updateBytes(1, bytes);
                rows.insertRow();
                rows.moveToCurrentRow();
                rows.beforeFirst();
                assertTrue(rows.next());
                assertEquals(uuid, reader.invoke(null, rows, "eni_uuid"));
                assertFalse(rows.wasNull());

                rows.updateNull(1);
                assertNull(reader.invoke(null, rows, "eni_uuid"));
            }
        }
    }

}
