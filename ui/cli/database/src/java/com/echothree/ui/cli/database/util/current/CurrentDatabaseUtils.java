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

package com.echothree.ui.cli.database.util.current;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CurrentDatabaseUtils {

    private CurrentDatabaseUtils() {
        super();
    }

    private static class CurrentDatabaseUtilsHolder {
        static CurrentDatabaseUtils instance = new CurrentDatabaseUtils();
    }

    public static CurrentDatabaseUtils getInstance() {
        return CurrentDatabaseUtilsHolder.instance;
    }

    private void handleIndex(CurrentTable ct, String indexName, List<String> columnNames, boolean unique) {
        Set<CurrentColumn> columns = new LinkedHashSet<>(columnNames.size());
        
        columnNames.forEach((columnName) -> {
            columns.add(ct.getColumn(columnName));
        });
        
        System.out.println("---   Index name: " + indexName + ", " + columnNames +  ", " + unique);

        var ci = new CurrentIndex(ct, indexName, unique, columns);
        ct.addIndex(ci);
        columns.forEach((cc) -> {
            cc.addIndex(ci);
        });
    }

    private void getIndexes(String catalog, DatabaseMetaData dmd, CurrentTable ct)
            throws SQLException {
        String currentIndexName = null;
        List<String> currentColumnNames = new ArrayList<>();
        var currentUnique = false;

        try(var ii = dmd.getIndexInfo(catalog, null, ct.getTableName(), false, true)) {
            while(ii.next()) {
                var indexName = ii.getString("INDEX_NAME");
                var columnName = ii.getString("COLUMN_NAME");
                var unique = !ii.getBoolean("NON_UNIQUE");

                if(currentIndexName != null && !currentIndexName.equals(indexName)) {
                    handleIndex(ct, currentIndexName, currentColumnNames, currentUnique);

                    currentColumnNames = new ArrayList<>();
                }

                currentIndexName = indexName;
                currentColumnNames.add(columnName);
                currentUnique = unique;
            }
        }

        if(currentIndexName != null) {
            handleIndex(ct, currentIndexName, currentColumnNames, currentUnique);
        }

        // Determine the name of the primary key index by getting one component of it, and marking the index as such.
        try(var pkColumns = dmd.getPrimaryKeys(catalog, null, ct.getTableName())) {
            if(pkColumns.next()) {
                var pkName = pkColumns.getString("PK_NAME");

                System.out.println("---   PK index name: " + pkName);

                var ci = ct.getIndex(pkName);

                ci.setPrimaryKey(true);
            }
        }
    }

    private void getColumns(String catalog, DatabaseMetaData dmd, CurrentTable ct)
            throws SQLException {
        try(var c = dmd.getColumns(catalog, null, ct.getTableName(), null)) {
            while(c.next()) {
                var columnName = c.getString("COLUMN_NAME");
                var type = c.getInt("DATA_TYPE");
                var columnSize = c.getInt("COLUMN_SIZE");
                var nullable = c.getInt("NULLABLE");

                System.out.println("---   Column name: " + columnName + ", " + type +  ", " + columnSize +  ", " + nullable);

                ct.addColumn(new CurrentColumn(ct, columnName, type, columnSize, nullable != 0));
            }
        }
    }

    public void getTables(String catalog, DatabaseMetaData dmd, CurrentDatabase cd)
            throws SQLException {
        try(var rs = dmd.getTables(catalog, null, null, new String[]{"TABLE"})) {
            while(rs.next()) {
                var tableName = rs.getString("TABLE_NAME");
                var ct = new CurrentTable(tableName);

                System.out.println("--- Table: " + tableName);

                cd.addTable(ct);
                getColumns(catalog, dmd, ct);
                getIndexes(catalog, dmd, ct);
            }
        }
    }

    private void getForeignKeys(String catalog, DatabaseMetaData dmd, CurrentDatabase cd)
            throws SQLException {
        for(var ct: cd.getTables().values()) {
            var tableName = ct.getTableName();

            try(var ik = dmd.getImportedKeys(catalog, null, tableName)) {
                System.out.println("--- Table: " + tableName);

                while(ik.next()) {
                    var importedKeyName = ik.getString("FK_NAME");
                    var fkColumnName = ik.getString("FKCOLUMN_NAME");
                    var pkTableName = ik.getString("PKTABLE_NAME");
                    var pkColumnName = ik.getString("PKCOLUMN_NAME");
                    var column = ct.getColumn(fkColumnName);
                    var targetColumn = cd.getTable(pkTableName).getColumn(pkColumnName);

                    System.out.println("---   Foreign key: " + importedKeyName +  ", " + fkColumnName + ", " + pkTableName +  ", " + pkColumnName);

                    var cfk = new CurrentForeignKey(ct, importedKeyName, column, targetColumn);
                    ct.addForeignKey(cfk);
                    column.addForeignKey(cfk);
                    targetColumn.addTargetForeignKey(cfk);
                }
            }
        }
    }
    
    public CurrentDatabase getCurrentDatabase(Connection conn)
            throws SQLException {
        var catalog = conn.getCatalog();
        var dmd = conn.getMetaData();
        var cd = new CurrentDatabase();
        
        getTables(catalog, dmd, cd);
        getForeignKeys(catalog, dmd, cd);
        
        return cd;
    }

}
