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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DatabaseViewUtilities {
    
    static Log log = LogFactory.getLog(DatabaseViewUtilities.class);
    
    boolean verbose;
    Database database;
    String connectionClass;
    String connectionUrl;
    String connectionUser;
    String connectionPassword;
    
    /** Creates a new instance of DatabaseViewUtilities */
    public DatabaseViewUtilities(boolean verbose, Database database, String connectionClass, String connectionUrl, String connectionUser,
            String connectionPassword) {
        this.verbose = verbose;
        this.database = database;
        this.connectionClass = connectionClass;
        this.connectionUrl = connectionUrl;
        this.connectionUser = connectionUser;
        this.connectionPassword = connectionPassword;
    }
    
    Connection connection;
    DatabaseMetaData databaseMetaData;
    
    void openDatabaseConnection()
            throws Exception {
        Class.forName(connectionClass);
        connection = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);
        databaseMetaData = connection.getMetaData();
    }
    
    void closeDatabaseConnection()
            throws Exception {
        databaseMetaData = null;
        connection.close();
        connection = null;
    }
    
    void dropAllViews()
            throws Exception {
        var stmt = connection.createStatement();
        var dmd = connection.getMetaData();
        var rs = dmd.getTables(null, null, null, new String[]{"VIEW"});

        while(rs.next()) {
            var tableName = rs.getString("TABLE_NAME");

            var query = "DROP VIEW " +
                    tableName;
            log.info(query);
            stmt.execute(query);
        }

        rs.close();
        stmt.close();
    }
    
    Column getTablePrimaryKey(Table table)
            throws Exception {
        var primaryKeyColumns = table.getPrimaryKey().getIndexColumns();
        Column result;
        
        if(primaryKeyColumns.size() == 1) {
            result = primaryKeyColumns.iterator().next();
        } else {
            throw new Exception("Only primary keys with one column are allowed.");
        }
        
        return result;
    }
    
    Column getTableActiveDetail(Table table)
            throws Exception {
        var activeDetail = table.getColumn("ActiveDetailId");
        
        if(activeDetail == null) {
            throw new Exception("Table must have an ActiveDetail column.");
        }
        
        return activeDetail;
    }
    
    void createViewsWithDetails(Set<Table> tablesWithDetails)
            throws Exception {
        var statement = connection.createStatement();
        
        for(var table : tablesWithDetails) {
            var primaryKey = getTablePrimaryKey(table);
            var activeDetail = getTableActiveDetail(table);
            var detailTable = database.getTableByPlural(table.getNameSingular() + "Details");
            var detailPrimaryKey = getTablePrimaryKey(detailTable);
            var viewColumns = new StringBuilder();
            var selectColumns = new StringBuilder();
            var usedColumnCount = 0;
            
            for(var column : detailTable.getColumns()) {
                var columnName = column.getName();
                
                if(column != detailPrimaryKey && !primaryKey.getName().equals(column.getName())) {
                    if(!(columnName.equals("FromTime") || columnName.equals("ThruTime"))) {
                        if(usedColumnCount > 0) {
                            viewColumns.append(", ");
                            selectColumns.append(", ");
                        }

                        viewColumns.append(column.getDbColumnName(table.getColumnPrefixLowerCase()));

                        var selectColumnName = column.getDbColumnName();
                        if(column.getType() == ColumnType.columnUUID)
                            selectColumnName = "BIN_TO_UUID(" + selectColumnName + ") AS " + selectColumnName;
                        selectColumns.append(selectColumnName);

                        usedColumnCount++;
                    }
                }
            }

            var createView = "CREATE VIEW " +
                    table.getDbTableName() +
                    "(" +
                    primaryKey.getDbColumnName() +
                    ", " +
                    viewColumns +
                    ") AS SELECT " +
                    primaryKey.getDbColumnName() +
                    ", " +
                    selectColumns +
                    " FROM echothree." +
                    table.getDbTableName() +
                    ", echothree." +
                    detailTable.getDbTableName() +
                    " WHERE " +
                    activeDetail.getDbColumnName() +
                    " = " +
                    detailPrimaryKey.getDbColumnName();

            var query = createView;
            log.info(query);
            statement.execute(query);
        }
        
        statement.close();
    }
    
    void createViewsWithoutDetails(Set<Table> tablesWithoutDetails)
            throws Exception {
        var statement = connection.createStatement();
        
        for(var table : tablesWithoutDetails) {
            Column thruTimeColumn = null;
            var viewColumns = new StringBuilder();
            var selectColumns = new StringBuilder();
            var usedColumnCount = 0;
            
            for(var column : table.getColumns()) {
                var columnName = column.getName();
                
                if(columnName.equals("FromTime") || columnName.equals("ThruTime")) {
                    thruTimeColumn = column;
                } else {
                    if(usedColumnCount > 0) {
                        viewColumns.append(", ");
                        selectColumns.append(", ");
                    }

                    viewColumns.append(column.getDbColumnName(table.getColumnPrefixLowerCase()));

                    var selectColumnName = column.getDbColumnName();
                    if(column.getType() == ColumnType.columnUUID)
                        selectColumnName = "BIN_TO_UUID(" + selectColumnName + ") AS " + selectColumnName;
                    selectColumns.append(selectColumnName);

                    usedColumnCount++;
                }
            }

            var createView = new StringBuilder("CREATE VIEW ")
                    .append(table.getDbTableName())
                    .append("(")
                    .append(viewColumns)
                    .append(") AS SELECT ")
                    .append(selectColumns)
                    .append(" FROM echothree.")
                    .append(table.getDbTableName());
            if(thruTimeColumn != null) {
                createView.append(" WHERE ")
                        .append(thruTimeColumn
                        .getDbColumnName())
                        .append(" = ")
                        .append(Long.MAX_VALUE);
            }

            var query = createView.toString();
            log.info(query);
            statement.execute(query);
        }
        
        statement.close();
    }
    
    void createAllViews()
            throws Exception {
        Set<Table> tablesWithDetails = new HashSet<>();
        Set<Table> tablesWithoutDetails = new HashSet<>();
        
        database.getTables().forEach((table) -> {
            var tableName = table.getNameSingular();
            
            if(tableName.endsWith("Detail")) {
                log.info("Skipping " + tableName);
            } else if(database.myTablesByPlural.containsKey(tableName + "Details")) {
                log.info("With details: " + tableName);
                tablesWithDetails.add(table);
            } else {
                log.info("Without details: " + tableName);
                tablesWithoutDetails.add(table);
            }
        });
        
        createViewsWithDetails(tablesWithDetails);
        createViewsWithoutDetails(tablesWithoutDetails);
    }
    
    public void execute()
            throws Exception {
        openDatabaseConnection();
        dropAllViews();
        createAllViews();
        closeDatabaseConnection();
    }
}
