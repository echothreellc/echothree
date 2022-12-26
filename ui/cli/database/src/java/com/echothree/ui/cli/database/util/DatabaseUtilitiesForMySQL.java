// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.ui.cli.database.util.current.CurrentColumn;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

public class DatabaseUtilitiesForMySQL
        extends DatabaseUtilities {
    
    /** Creates new DatabaseUtilitiesForMySQL */
    /** Creates a new instance of DatabaseUtilitiesForMySQL */
    public DatabaseUtilitiesForMySQL(boolean verbose, Database theDatabase, String connectionClass, String connectionUrl,
            String connectionUser, String connectionPassword, String connectionCharacterSet, String connectionCollation)
            throws Exception {
        super(verbose, theDatabase, connectionClass, connectionUrl, connectionUser, connectionPassword, connectionCharacterSet,
                connectionCollation);
    }
    
    @Override
    void doEmptyDatabase() throws Exception {
        try(Statement stmt = myConnection.createStatement()) {
            DatabaseMetaData dmd = myConnection.getMetaData();
            
            try(ResultSet rs = dmd.getTables(null, null, null, new String[] {"TABLE"})) {
                while(rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    
                    try(ResultSet ik = dmd.getImportedKeys(null, null, tableName)) {
                        while(ik.next()) {
                            String importedKeyName = ik.getString("FK_NAME");
                            
                            stmt.execute("ALTER TABLE " + tableName + " DROP FOREIGN KEY " + importedKeyName);
                        }
                    }
                }
                
                rs.first();
                while(rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    
                    stmt.execute("DROP TABLE " + tableName);
                }
            }
        }
    }
    
    @Override
    void doSetDatabaseCharacterSetAndCollection()
            throws Exception {
        try(Statement stmt = myConnection.createStatement()) {
            stmt.execute("ALTER DATABASE CHARACTER SET " + connectionCharacterSet + " COLLATE " + connectionCollation);
        }
    }

    @Override
    void doSetTableCharacterSetAndCollection()
            throws Exception {
        try(Statement stmt = myConnection.createStatement()) {
            DatabaseMetaData dmd = myConnection.getMetaData();
            
            try(ResultSet rs = dmd.getTables(null, null, null, new String[] {"TABLE"})) {
                while(rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    
                    stmt.execute("ALTER TABLE " + tableName + " DEFAULT CHARSET=" + connectionCharacterSet + " COLLATE=" + connectionCollation);
                }
            }
        }
    }

    @Override
    void doSetColumnCharacterSetAndCollection()
            throws Exception {
        try(Statement stmt = myConnection.createStatement()) {
            DatabaseMetaData dmd = myConnection.getMetaData();
            
            try(ResultSet trs = dmd.getTables(null, null, null, new String[] {"TABLE"})) {
                while(trs.next()) {
                    String tableName = trs.getString("TABLE_NAME");
                    
                    try(ResultSet c = dmd.getColumns(null, null, tableName, null)) {
                        while(c.next()) {
                            String columnName = c.getString("COLUMN_NAME");
                            int type = c.getInt("DATA_TYPE");

                            switch(type) {
                                case Types.VARCHAR:
                                case Types.LONGVARCHAR:
                                    Table myTable = myDatabase.getTableLowerCase(tableName);

                                    for (Column foundColumn : myTable.getColumns()) {
                                        if (foundColumn.getDbColumnName().equals(columnName)) {
                                            stmt.execute("ALTER TABLE " + tableName + " MODIFY " + getColumnDefinition(foundColumn));
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    String getCreateTableParameters(String tableName) {
        return " ENGINE=InnoDB DEFAULT CHARSET=" + connectionCharacterSet + " COLLATE=" + connectionCollation;
    }
    
    @Override
    String getIntegerDefinition(String columnName, Column theColumn, Column theFKColumn) {
        String result = columnName + " INT";
        Column nullTestColumn = theFKColumn == null? theColumn: theFKColumn;
        
        if(!nullTestColumn.getNullAllowed())
            result += " NOT NULL";
        
        return result;
    }
    
    @Override
    String getLongDefinition(String columnName, Column theColumn, Column theFKColumn) {
        String result = columnName + " BIGINT";
        Column nullTestColumn = theFKColumn == null? theColumn: theFKColumn;
        
        if(!nullTestColumn.getNullAllowed())
            result += " NOT NULL";
        
        return result;
    }
    
    @Override
    String getStringDefinition(String columnName, Column theColumn, Column theFKColumn) {
        String result = columnName + " ";
        long maxLength = theColumn.getMaxLength();
        
        if(maxLength < 256)
            result += "VARCHAR(" + maxLength + ")";
        else if(maxLength < 65536)
            result += "TEXT";
        else if(maxLength < 1677217)
            result += "MEDIUMTEXT";
        else
            result += "LONGTEXT";
        
        result += " CHARACTER SET ";
        result += connectionCharacterSet;
        result += " COLLATE ";
        result += connectionCollation;
        
        Column nullTestColumn = theFKColumn == null? theColumn: theFKColumn;
        if(!nullTestColumn.getNullAllowed())
            result += " NOT NULL";
        
        return result;
    }
    
    boolean checkColumnDefinition(CurrentColumn cc, Column theColumn, boolean fkCheck) throws Exception {
        int columnRealType = theColumn.getType();
        boolean result = true;

        switch (columnRealType) {
            case ColumnType.columnEID:
            case ColumnType.columnLong:
            case ColumnType.columnTime:
                result = cc.getType() == Types.BIGINT && cc.getColumnSize() == 19;
                break;
            case ColumnType.columnBoolean:
                result = cc.getType() == Types.BIT && cc.getColumnSize() == 1;
                break;
            case ColumnType.columnInteger:
            case ColumnType.columnDate:
                result = cc.getType() == Types.INTEGER && cc.getColumnSize() == 10;
                break;
            case ColumnType.columnString:
                long maxLength = theColumn.getMaxLength();

                if(maxLength < 256) {
                    result = cc.getType() == Types.VARCHAR && cc.getColumnSize() == maxLength;
                } else {
                    result = cc.getType() == Types.LONGVARCHAR;
                }
                break;
            case ColumnType.columnCLOB:
                result = cc.getType() == Types.LONGVARCHAR && cc.getColumnSize() == 2147483647;
                break;
            case ColumnType.columnBLOB:
                result = cc.getType() == Types.LONGVARBINARY && cc.getColumnSize() == 2147483647;
                break;
            case ColumnType.columnForeignKey:
                Column destinationColumn = myDatabase.getTable(theColumn.getDestinationTable()).getColumn(theColumn.getDestinationColumn());

                result = checkColumnDefinition(cc, destinationColumn, true);
                break;
            default:
                break;
        }
        
        if(!fkCheck && result && (cc.isNullable() != theColumn.getNullAllowed())) {
            result = false;
        }
        
        if(!result) {
            System.out.println("--- Incorrect column definition: " + cc.getType() + ", " + cc.getColumnSize() + ", " + theColumn.getTable().getNamePlural()
                    + ", " + theColumn.getName() + ", " + columnRealType);
        }
        
        return result;
    }
    
    @Override
    boolean checkColumnDefinition(CurrentColumn cc, Column theColumn) throws Exception {
        return checkColumnDefinition(cc, theColumn, false);
    }
    
    @Override
    String getBooleanDefinition(String columnName, Column theColumn, Column theFKColumn) {
        String result = columnName + " BIT(1)";
        Column nullTestColumn = theFKColumn == null? theColumn: theFKColumn;
        if(!nullTestColumn.getNullAllowed())
            result += " NOT NULL";
        
        return result;
    }
    
    @Override
    String getDateDefinition(String columnName, Column theColumn, Column theFKColumn) {
        String result = columnName + " INT(8) UNSIGNED ZEROFILL";
        Column nullTestColumn = theFKColumn == null? theColumn: theFKColumn;
        if(!nullTestColumn.getNullAllowed())
            result += " NOT NULL";
        
        return result;
    }
    
    @Override
    String getCLOBDefinition(String columnName, Column theColumn) {
        String result = columnName + " LONGTEXT";
        
        result += " CHARACTER SET ";
        result += connectionCharacterSet;
        result += " COLLATE ";
        result += connectionCollation;
        
        if(!theColumn.getNullAllowed())
            result += " NOT NULL";
        
        return result;
    }
    
    @Override
    String getBLOBDefinition(String columnName, Column theColumn) {
        String result = columnName + " LONGBLOB";
        if(!theColumn.getNullAllowed())
            result += " NOT NULL";
        
        return result;
    }
    
    @Override
    String getPrimaryKeyIndex(Index theIndex) throws Exception {
        String result = "PRIMARY KEY (" + getIndexColumnList(theIndex) + ")";
        return result;
    }
    
    @Override
    String getUniqueIndex(Index theIndex) throws Exception {
        String result = "UNIQUE KEY " + getIndexName(theIndex) + " ("
                + getIndexColumnList(theIndex) + ")";
        return result;
    }
    
    @Override
    String getMultipleIndex(Index theIndex) throws Exception {
        String result = "KEY " + getIndexName(theIndex) + " ("
                + getIndexColumnList(theIndex) + ")";
        return result;
    }
    
    @Override
    String getForeignKeyDefinition(Column theFK, Table sourceTable, String sourceColumnName, Table destinationTable, String destinationColumnName)
            throws Exception {
        String result = "CONSTRAINT " + sourceColumnName + "_fk FOREIGN KEY (" + sourceColumnName + ") REFERENCES "
                + destinationTable.getNamePlural().toLowerCase() + "("
                + destinationColumnName + ") ON DELETE ";
        switch(theFK.getOnParentDelete()) {
            case Column.parentDelete:
                result += "CASCADE";
                break;
            case Column.parentSetNull:
                result += "SET NULL";
                break;
        }
        return result;
    }
    
}
