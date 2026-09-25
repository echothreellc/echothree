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
    }

    @Test
    public void generatedUuidReaderSupportsBinaryAndLegacyTextResults() throws Exception {
        var source = new StringWriter();
        try(var writer = new PrintWriter(source)) {
            writer.println("import java.sql.*; public class UuidReader {");
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

            // Fixed MySQL UUID_TO_BIN vector, including high-bit bytes and leading zeroes.
            var uuid = "00112233-4455-6677-8899-aabbccddeeff";
            for(var type : new int[] { Types.BINARY, Types.VARCHAR }) {
                var metadata = new RowSetMetaDataImpl();
                metadata.setColumnCount(1);
                metadata.setColumnName(1, "eni_uuid");
                metadata.setColumnType(1, type);

                try(var rows = RowSetProvider.newFactory().createCachedRowSet()) {
                    rows.setMetaData(metadata);
                    rows.moveToInsertRow();
                    if(type == Types.BINARY) {
                        rows.updateBytes(1, HexFormat.of().parseHex("00112233445566778899aabbccddeeff"));
                    } else {
                        rows.updateString(1, uuid);
                    }
                    rows.insertRow();
                    rows.moveToCurrentRow();
                    rows.beforeFirst();
                    assertTrue(rows.next());
                    assertEquals(uuid, reader.invoke(null, rows, "eni_uuid"));
                    assertFalse(rows.wasNull());

                    rows.updateNull(1);
                    assertNull(reader.invoke(null, rows, "eni_uuid"));
                    assertTrue(rows.wasNull());
                }
            }
        }
    }

}
