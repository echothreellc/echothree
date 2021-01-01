// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import java.sql.ResultSet;
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
        
        columnNames.stream().forEach((columnName) -> {
            columns.add(ct.getColumn(columnName));
        });
        
        System.out.println("---   Index name: " + indexName + ", " + columnNames +  ", " + unique);

        CurrentIndex ci = new CurrentIndex(ct, indexName, unique, columns);
        ct.addIndex(ci);
        columns.stream().forEach((cc) -> {
            cc.addIndex(ci);
        });
    }

    private void getIndexes(String catalog, DatabaseMetaData dmd, CurrentTable ct)
            throws SQLException {
        String currentIndexName = null;
        List<String> currentColumnNames = new ArrayList<>();
        boolean currentUnique = false;

        try(ResultSet ii = dmd.getIndexInfo(catalog, null, ct.getTableName(), false, true)) {
            while(ii.next()) {
                String indexName = ii.getString("INDEX_NAME");
                String columnName = ii.getString("COLUMN_NAME");
                boolean unique = !ii.getBoolean("NON_UNIQUE");

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
        try(ResultSet pkColumns = dmd.getPrimaryKeys(catalog, null, ct.getTableName())) {
            if(pkColumns.next()) {
                String pkName = pkColumns.getString("PK_NAME");

                System.out.println("---   PK index name: " + pkName);

                CurrentIndex ci = ct.getIndex(pkName);

                ci.setPrimaryKey(true);
            }
        }
    }

    private void getColumns(String catalog, DatabaseMetaData dmd, CurrentTable ct)
            throws SQLException {
        try(ResultSet c = dmd.getColumns(catalog, null, ct.getTableName(), null)) {
            while(c.next()) {
                String columnName = c.getString("COLUMN_NAME");
                int type = c.getInt("DATA_TYPE");
                int columnSize = c.getInt("COLUMN_SIZE");
                int nullable = c.getInt("NULLABLE");

                System.out.println("---   Column name: " + columnName + ", " + type +  ", " + columnSize +  ", " + nullable);

                ct.addColumn(new CurrentColumn(ct, columnName, type, columnSize, nullable != 0));
            }
        }
    }

    public void getTables(String catalog, DatabaseMetaData dmd, CurrentDatabase cd)
            throws SQLException {
        try(ResultSet rs = dmd.getTables(catalog, null, null, new String[]{"TABLE"})) {
            while(rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                CurrentTable ct = new CurrentTable(tableName);

                System.out.println("--- Table: " + tableName);

                cd.addTable(ct);
                getColumns(catalog, dmd, ct);
                getIndexes(catalog, dmd, ct);
            }
        }
    }

    private void getForeignKeys(String catalog, DatabaseMetaData dmd, CurrentDatabase cd)
            throws SQLException {
        for(CurrentTable ct: cd.getTables().values()) {
            String tableName = ct.getTableName();

            try(ResultSet ik = dmd.getImportedKeys(catalog, null, tableName)) {
                System.out.println("--- Table: " + tableName);

                while(ik.next()) {
                    String importedKeyName = ik.getString("FK_NAME");
                    String fkColumnName = ik.getString("FKCOLUMN_NAME");
                    String pkTableName = ik.getString("PKTABLE_NAME");
                    String pkColumnName = ik.getString("PKCOLUMN_NAME");
                    CurrentColumn column = ct.getColumn(fkColumnName);
                    CurrentColumn targetColumn = cd.getTable(pkTableName).getColumn(pkColumnName);

                    System.out.println("---   Foreign key: " + importedKeyName +  ", " + fkColumnName + ", " + pkTableName +  ", " + pkColumnName);
                    
                    CurrentForeignKey cfk = new CurrentForeignKey(ct, importedKeyName, column, targetColumn);
                    ct.addForeignKey(cfk);
                    column.addForeignKey(cfk);
                    targetColumn.addTargetForeignKey(cfk);
                }
            }
        }
    }
    
    public CurrentDatabase getCurrentDatabase(Connection conn)
            throws SQLException {
        String catalog = conn.getCatalog();
        DatabaseMetaData dmd = conn.getMetaData();
        CurrentDatabase cd = new CurrentDatabase();
        
        getTables(catalog, dmd, cd);
        getForeignKeys(catalog, dmd, cd);
        
        return cd;
    }

}
