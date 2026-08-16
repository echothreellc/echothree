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

package com.echothree.ui.cli.database.util.definition;

import java.util.Locale;

public final class DatabasePhysicalNames {

    private DatabasePhysicalNames() {
    }

    public static String tableName(String namePlural) {
        return namePlural.toLowerCase(Locale.ROOT);
    }

    public static String tableName(Table table) {
        return tableName(table.getNamePlural());
    }

    public static String columnPrefix(Table table) {
        return table.getColumnPrefix().toLowerCase(Locale.ROOT);
    }

    public static String columnName(String columnPrefix, String columnName) {
        return columnPrefix + "_" + columnName.toLowerCase(Locale.ROOT);
    }

    public static String columnPrefix(Column column, String columnPrefixLowerCase) throws Exception {
        var table = column.getTable();
        var result = columnPrefixLowerCase;

        if(column.getType() == ColumnType.columnForeignKey) {
            var destinationTable = table.getDatabase().getTable(column.getDestinationTable());
            var referencesSelf = destinationTable.getNamePlural().equals(table.getNamePlural());
            var columnNameNotPrimaryKey = !destinationTable.getEID().getName().equals(column.getName());

            if(!referencesSelf && !columnNameNotPrimaryKey) {
                result += "_" + columnPrefix(destinationTable);
            }
        }

        return result;
    }

    public static String columnPrefix(Column column) throws Exception {
        return columnPrefix(column, columnPrefix(column.getTable()));
    }

    public static String columnName(Column column, String columnPrefixLowerCase) throws Exception {
        return columnPrefix(column, columnPrefixLowerCase) + "_" + column.getName().toLowerCase(Locale.ROOT);
    }

    public static String columnName(Column column) throws Exception {
        return columnName(column, columnPrefix(column.getTable()));
    }

    public static String indexName(Index index) {
        return index.getType() == Index.indexPrimaryKey
                ? "PRIMARY"
                : index.getName().toLowerCase(Locale.ROOT) + "_idx";
    }

    public static String foreignKeyName(Column column) throws Exception {
        return columnName(column) + "_fk";
    }

}
