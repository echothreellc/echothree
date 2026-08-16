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

import com.echothree.ui.cli.database.util.current.CurrentColumn;
import com.echothree.ui.cli.database.util.definition.Column;
import com.echothree.ui.cli.database.util.definition.ColumnDataType;
import com.echothree.ui.cli.database.util.definition.Database;
import com.echothree.ui.cli.database.util.definition.DatabasePhysicalNames;
import com.echothree.ui.cli.database.util.definition.Index;
import com.echothree.ui.cli.database.util.definition.ParentDeleteAction;
import com.echothree.ui.cli.database.util.definition.Table;
import java.sql.Types;

public class DatabaseUtilitiesForMySQL
        extends DatabaseUtilities {
    
    /** Creates a new instance of DatabaseUtilitiesForMySQL */
    public DatabaseUtilitiesForMySQL(boolean verbose, Database theDatabase, String connectionClass, String connectionUrl,
            String connectionUser, String connectionPassword, String connectionCharacterSet, String connectionCollation) {
        super(verbose, theDatabase, connectionClass, connectionUrl, connectionUser, connectionPassword, connectionCharacterSet,
                connectionCollation);
    }
    
    @Override
    void doEmptyDatabase() throws Exception {
        try(var stmt = myConnection.createStatement()) {
            var dmd = myConnection.getMetaData();
            
            try(var rs = dmd.getTables(null, null, null, new String[] {"TABLE"})) {
                while(rs.next()) {
                    var tableName = rs.getString("TABLE_NAME");
                    
                    try(var ik = dmd.getImportedKeys(null, null, tableName)) {
                        while(ik.next()) {
                            var importedKeyName = ik.getString("FK_NAME");
                            
                            stmt.execute("ALTER TABLE " + tableName + " DROP FOREIGN KEY " + importedKeyName);
                        }
                    }
                }
                
                rs.first();
                while(rs.next()) {
                    var tableName = rs.getString("TABLE_NAME");
                    
                    stmt.execute("DROP TABLE " + tableName);
                }
            }
        }
    }
    
    @Override
    void doSetDatabaseCharacterSetAndCollection()
            throws Exception {
        try(var stmt = myConnection.createStatement()) {
            stmt.execute("ALTER DATABASE CHARACTER SET " + connectionCharacterSet + " COLLATE " + connectionCollation);
        }
    }

    @Override
    void doSetTableCharacterSetAndCollection()
            throws Exception {
        try(var stmt = myConnection.createStatement()) {
            var dmd = myConnection.getMetaData();
            
            try(var rs = dmd.getTables(null, null, null, new String[] {"TABLE"})) {
                while(rs.next()) {
                    var tableName = rs.getString("TABLE_NAME");
                    
                    stmt.execute("ALTER TABLE " + tableName + " DEFAULT CHARSET=" + connectionCharacterSet + " COLLATE=" + connectionCollation);
                }
            }
        }
    }

    @Override
    void doSetColumnCharacterSetAndCollection()
            throws Exception {
        try(var stmt = myConnection.createStatement()) {
            var dmd = myConnection.getMetaData();
            
            try(var trs = dmd.getTables(null, null, null, new String[] {"TABLE"})) {
                while(trs.next()) {
                    var tableName = trs.getString("TABLE_NAME");
                    
                    try(var c = dmd.getColumns(null, null, tableName, null)) {
                        while(c.next()) {
                            var columnName = c.getString("COLUMN_NAME");
                            var type = c.getInt("DATA_TYPE");

                            switch(type) {
                                case Types.VARCHAR, Types.LONGVARCHAR -> {
                                    var myTable = myDatabase.getTableLowerCase(tableName);

                                    for(var foundColumn : myTable.getColumns()) {
                                        if(foundColumn.getDbColumnName().equals(columnName)) {
                                            stmt.execute("ALTER TABLE " + tableName + " MODIFY " + getColumnDefinition(foundColumn));
                                            break;
                                        }
                                    }
                                }
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
        var result = columnName + " INT";
        var nullTestColumn = theFKColumn == null? theColumn: theFKColumn;
        
        if(!nullTestColumn.getNullAllowed())
            result += " NOT NULL";
        
        return result;
    }
    
    @Override
    String getLongDefinition(String columnName, Column theColumn, Column theFKColumn) {
        var result = columnName + " BIGINT";
        var nullTestColumn = theFKColumn == null? theColumn: theFKColumn;
        
        if(!nullTestColumn.getNullAllowed())
            result += " NOT NULL";
        
        return result;
    }
    
    @Override
    String getStringDefinition(String columnName, Column theColumn, Column theFKColumn) {
        var result = columnName + " ";
        var maxLength = theColumn.getMaxLength();
        
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

        var nullTestColumn = theFKColumn == null? theColumn: theFKColumn;
        if(!nullTestColumn.getNullAllowed())
            result += " NOT NULL";
        
        return result;
    }
    
    boolean checkColumnDefinition(CurrentColumn cc, Column theColumn, boolean fkCheck) throws Exception {
        var columnRealType = theColumn.getType();
        var result = true;

        switch(columnRealType) {
            case ColumnDataType.EID, ColumnDataType.LONG, ColumnDataType.TIME ->
                    result = cc.getType() == Types.BIGINT && cc.getColumnSize() == 19;
            case ColumnDataType.BOOLEAN ->
                    result = cc.getType() == Types.BIT && cc.getColumnSize() == 1;
            case ColumnDataType.INTEGER, ColumnDataType.DATE ->
                    result = cc.getType() == Types.INTEGER && cc.getColumnSize() == 10;
            case ColumnDataType.STRING -> {
                var maxLength = theColumn.getMaxLength();

                if(maxLength < 256) {
                    result = cc.getType() == Types.VARCHAR && cc.getColumnSize() == maxLength;
                } else {
                    result = cc.getType() == Types.LONGVARCHAR;
                }
            }
            case ColumnDataType.CLOB ->
                    result = cc.getType() == Types.LONGVARCHAR && cc.getColumnSize() == 2147483647;
            case ColumnDataType.BLOB ->
                    result = cc.getType() == Types.LONGVARBINARY && cc.getColumnSize() == 2147483647;
            case ColumnDataType.FOREIGN_KEY -> {
                var destinationColumn = myDatabase.getTable(theColumn.getDestinationTable()).getColumn(theColumn.getDestinationColumn());

                result = checkColumnDefinition(cc, destinationColumn, true);
            }
            case ColumnDataType.UUID ->
                    result = cc.getType() == Types.BINARY && cc.getColumnSize() == 16;
            default -> {
            }
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
        var result = columnName + " BIT(1)";
        var nullTestColumn = theFKColumn == null? theColumn: theFKColumn;
        if(!nullTestColumn.getNullAllowed())
            result += " NOT NULL";
        
        return result;
    }
    
    @Override
    String getDateDefinition(String columnName, Column theColumn, Column theFKColumn) {
        var result = columnName + " INT(8) UNSIGNED ZEROFILL";
        var nullTestColumn = theFKColumn == null? theColumn: theFKColumn;
        if(!nullTestColumn.getNullAllowed())
            result += " NOT NULL";
        
        return result;
    }
    
    @Override
    String getCLOBDefinition(String columnName, Column theColumn) {
        var result = columnName + " LONGTEXT";
        
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
        var result = columnName + " LONGBLOB";
        if(!theColumn.getNullAllowed())
            result += " NOT NULL";
        
        return result;
    }
    
    @Override
    String getPrimaryKeyIndex(Index theIndex) throws Exception {
        return "PRIMARY KEY (" + getIndexColumnList(theIndex) + ")";
    }
    
    @Override
    String getUniqueIndex(Index theIndex) throws Exception {
        return "UNIQUE KEY " + getIndexName(theIndex) + " ("
                + getIndexColumnList(theIndex) + ")";
    }
    
    @Override
    String getMultipleIndex(Index theIndex) throws Exception {
        return "KEY " + getIndexName(theIndex) + " ("
                + getIndexColumnList(theIndex) + ")";
    }
    
    @Override
    String getForeignKeyDefinition(Column theFK, Table sourceTable, String sourceColumnName, Table destinationTable, String destinationColumnName) throws Exception {
        var result = "CONSTRAINT " + DatabasePhysicalNames.foreignKeyName(theFK) + " FOREIGN KEY (" + sourceColumnName + ") REFERENCES "
                + DatabasePhysicalNames.tableName(destinationTable) + "("
                + destinationColumnName + ") ON DELETE ";
        switch(theFK.getOnParentDelete()) {
            case ParentDeleteAction.DELETE -> result += "CASCADE";
            case ParentDeleteAction.SET_NULL -> result += "SET NULL";
            case ParentDeleteAction.NONE -> {
            }
        }
        return result;
    }

    @Override
    String getUUIDDefinition(String columnName, Column theColumn) {
        var result = columnName + " BINARY(16)";
        if(!theColumn.getNullAllowed())
            result += " NOT NULL";

        return result;
    }

}
