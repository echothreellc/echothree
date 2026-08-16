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

package com.echothree.ui.cli.database.util;

import com.echothree.ui.cli.database.util.definition.Column;
import com.echothree.ui.cli.database.util.definition.ColumnType;
import com.echothree.ui.cli.database.util.definition.Database;
import com.echothree.ui.cli.database.util.definition.Index;
import com.echothree.ui.cli.database.util.definition.Table;
import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;

public class DatabaseUtilitiesForJooq {

    private final Database myDatabase;

    /** Creates a new instance of DatabaseUtilitiesForJooq. */
    public DatabaseUtilitiesForJooq(boolean verbose, Database theDatabase) {
        myDatabase = theDatabase;
    }

    public String createXmlDirectory(String baseDirectory) {
        var theDirectory = new File(baseDirectory);

        if(!theDirectory.exists()) {
            theDirectory.mkdirs();
        }

        return baseDirectory;
    }

    private String xml(String value) {
        return value == null ? "" : value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    private String tableName(Table table) {
        return table.getDbTableName();
    }

    private String columnName(Column column) throws Exception {
        return column.getDbColumnName();
    }

    private String constraintName(Index index) {
        return index.getType() == Index.indexPrimaryKey ? "PRIMARY" : index.getName().toLowerCase(Locale.getDefault()) + "_idx";
    }

    /* jOOQ's XML format omits the referenced table from a referential
     * constraint. Use its constraint catalog solely to disambiguate MySQL's
     * table-local PRIMARY and unique-key names. */
    private String constraintCatalog(Table table) {
        return table.getDbTableName();
    }

    private String foreignKeyName(Column column) throws Exception {
        return column.getDbColumnName() + "_fk";
    }

    private Column physicalTypeColumn(Column column) throws Exception {
        if(column.getType() == ColumnType.columnForeignKey) {
            var destinationTable = myDatabase.getTable(column.getDestinationTable());

            return physicalTypeColumn(destinationTable.getColumn(column.getDestinationColumn()));
        }

        return column;
    }

    private void printColumnType(PrintWriter pw, Column column) throws Exception {
        var physicalColumn = physicalTypeColumn(column);

        switch(physicalColumn.getType()) {
            case ColumnType.columnEID, ColumnType.columnLong, ColumnType.columnTime -> {
                pw.println("                <data_type>bigint</data_type>");
                pw.println("                <numeric_precision>19</numeric_precision>");
            }
            case ColumnType.columnInteger, ColumnType.columnDate -> {
                pw.println("                <data_type>int</data_type>");
                pw.println("                <numeric_precision>10</numeric_precision>");
            }
            case ColumnType.columnString -> {
                var maximumLength = physicalColumn.getMaxLength();

                if(maximumLength < 256) {
                    pw.println("                <data_type>varchar</data_type>");
                    pw.println("                <character_maximum_length>" + maximumLength + "</character_maximum_length>");
                } else if(maximumLength < 65536) {
                    pw.println("                <data_type>text</data_type>");
                } else if(maximumLength < 1677217) {
                    pw.println("                <data_type>mediumtext</data_type>");
                } else {
                    pw.println("                <data_type>longtext</data_type>");
                }
            }
            case ColumnType.columnBoolean -> {
                pw.println("                <data_type>bit</data_type>");
                pw.println("                <numeric_precision>1</numeric_precision>");
            }
            case ColumnType.columnCLOB -> pw.println("                <data_type>longtext</data_type>");
            case ColumnType.columnBLOB -> pw.println("                <data_type>longblob</data_type>");
            case ColumnType.columnUUID -> {
                pw.println("                <data_type>binary</data_type>");
                pw.println("                <character_maximum_length>16</character_maximum_length>");
            }
            default -> throw new Exception("Unsupported jOOQ column type " + physicalColumn.getType());
        }
    }

    private void printTables(PrintWriter pw) {
        pw.println("    <tables>");
        for(var table : myDatabase.getTables()) {
            pw.println("        <table>");
            pw.println("            <table_schema>" + xml(myDatabase.getName()) + "</table_schema>");
            pw.println("            <table_name>" + xml(tableName(table)) + "</table_name>");
            if(table.getDescription() != null) {
                pw.println("            <comment>" + xml(table.getDescription()) + "</comment>");
            }
            pw.println("        </table>");
        }
        pw.println("    </tables>");
    }

    private void printColumns(PrintWriter pw) throws Exception {
        pw.println("    <columns>");
        for(var table : myDatabase.getTables()) {
            var ordinalPosition = 1;

            for(var column : table.getColumns()) {
                pw.println("        <column>");
                pw.println("            <table_schema>" + xml(myDatabase.getName()) + "</table_schema>");
                pw.println("            <table_name>" + xml(tableName(table)) + "</table_name>");
                pw.println("            <column_name>" + xml(columnName(column)) + "</column_name>");
                printColumnType(pw, column);
                pw.println("            <ordinal_position>" + ordinalPosition++ + "</ordinal_position>");
                pw.println("            <is_nullable>" + column.getNullAllowed() + "</is_nullable>");
                if(column.getDescription() != null) {
                    pw.println("            <comment>" + xml(column.getDescription()) + "</comment>");
                }
                pw.println("        </column>");
            }
        }
        pw.println("    </columns>");
    }

    private void printTableConstraints(PrintWriter pw) throws Exception {
        pw.println("    <table_constraints>");
        for(var table : myDatabase.getTables()) {
            for(var index : table.getIndexes()) {
                if(index.getType() != Index.indexMultiple) {
                    pw.println("        <table_constraint>");
                    pw.println("            <constraint_catalog>" + xml(constraintCatalog(table)) + "</constraint_catalog>");
                    pw.println("            <constraint_schema>" + xml(myDatabase.getName()) + "</constraint_schema>");
                    pw.println("            <constraint_name>" + xml(constraintName(index)) + "</constraint_name>");
                    pw.println("            <constraint_type>" + (index.getType() == Index.indexPrimaryKey ? "PRIMARY KEY" : "UNIQUE") + "</constraint_type>");
                    pw.println("            <table_schema>" + xml(myDatabase.getName()) + "</table_schema>");
                    pw.println("            <table_name>" + xml(tableName(table)) + "</table_name>");
                    pw.println("        </table_constraint>");
                }
            }

            for(var column : table.getForeignKeys()) {
                pw.println("        <table_constraint>");
                pw.println("            <constraint_catalog>" + xml(constraintCatalog(table)) + "</constraint_catalog>");
                pw.println("            <constraint_schema>" + xml(myDatabase.getName()) + "</constraint_schema>");
                pw.println("            <constraint_name>" + xml(foreignKeyName(column)) + "</constraint_name>");
                pw.println("            <constraint_type>FOREIGN KEY</constraint_type>");
                pw.println("            <table_schema>" + xml(myDatabase.getName()) + "</table_schema>");
                pw.println("            <table_name>" + xml(tableName(table)) + "</table_name>");
                pw.println("        </table_constraint>");
            }
        }
        pw.println("    </table_constraints>");
    }

    private void printKeyColumnUsages(PrintWriter pw) throws Exception {
        pw.println("    <key_column_usages>");
        for(var table : myDatabase.getTables()) {
            for(var index : table.getIndexes()) {
                if(index.getType() != Index.indexMultiple) {
                    var ordinalPosition = 1;

                    for(var column : index.getIndexColumns()) {
                        printKeyColumnUsage(pw, constraintName(index), table, column, ordinalPosition++);
                    }
                }
            }

            for(var column : table.getForeignKeys()) {
                printKeyColumnUsage(pw, foreignKeyName(column), table, column, 1);
            }
        }
        pw.println("    </key_column_usages>");
    }

    private void printKeyColumnUsage(PrintWriter pw, String name, Table table, Column column, int ordinalPosition) throws Exception {
        pw.println("        <key_column_usage>");
        pw.println("            <constraint_catalog>" + xml(constraintCatalog(table)) + "</constraint_catalog>");
        pw.println("            <constraint_schema>" + xml(myDatabase.getName()) + "</constraint_schema>");
        pw.println("            <constraint_name>" + xml(name) + "</constraint_name>");
        pw.println("            <table_schema>" + xml(myDatabase.getName()) + "</table_schema>");
        pw.println("            <table_name>" + xml(tableName(table)) + "</table_name>");
        pw.println("            <column_name>" + xml(columnName(column)) + "</column_name>");
        pw.println("            <ordinal_position>" + ordinalPosition + "</ordinal_position>");
        pw.println("        </key_column_usage>");
    }

    private void printReferentialConstraints(PrintWriter pw) throws Exception {
        pw.println("    <referential_constraints>");
        for(var table : myDatabase.getTables()) {
            for(var column : table.getForeignKeys()) {
                var destinationTable = myDatabase.getTable(column.getDestinationTable());

                pw.println("        <referential_constraint>");
                pw.println("            <constraint_catalog>" + xml(constraintCatalog(table)) + "</constraint_catalog>");
                pw.println("            <constraint_schema>" + xml(myDatabase.getName()) + "</constraint_schema>");
                pw.println("            <constraint_name>" + xml(foreignKeyName(column)) + "</constraint_name>");
                pw.println("            <unique_constraint_catalog>" + xml(constraintCatalog(destinationTable)) + "</unique_constraint_catalog>");
                pw.println("            <unique_constraint_schema>" + xml(myDatabase.getName()) + "</unique_constraint_schema>");
                pw.println("            <unique_constraint_name>" + xml(constraintName(destinationTable.getPrimaryKey())) + "</unique_constraint_name>");
                switch(column.getOnParentDelete()) {
                    case Column.parentDelete -> pw.println("            <delete_rule>CASCADE</delete_rule>");
                    case Column.parentSetNull -> pw.println("            <delete_rule>SET NULL</delete_rule>");
                    default -> {
                    }
                }
                pw.println("        </referential_constraint>");
            }
        }
        pw.println("    </referential_constraints>");
    }

    public void exportJooq(String baseDirectory) throws Exception {
        var directory = createXmlDirectory(baseDirectory);
        var f = new File(directory + File.separatorChar + "XMLDatabase.xml");

        try(BufferedWriter bw = Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8); var pw = new PrintWriter(bw)) {
            pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            pw.println("<information_schema xmlns=\"http://www.jooq.org/xsd/jooq-meta-3.15.0.xsd\">");
            pw.println("    <schemata>");
            pw.println("        <schema>");
            pw.println("            <schema_name>" + xml(myDatabase.getName()) + "</schema_name>");
            pw.println("        </schema>");
            pw.println("    </schemata>");
            printTables(pw);
            printColumns(pw);
            printTableConstraints(pw);
            printKeyColumnUsages(pw);
            printReferentialConstraints(pw);
            pw.println("</information_schema>");
        }
    }

}
