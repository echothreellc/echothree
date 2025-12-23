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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Table {
    
    Database database;
    Component component;
    
    String namePlural;
    String nameSingular;
    String columnPrefix;
    String chunkSize;
    String description;
    
    List<Column> columns;
    List<Index> indexes;
    List<Column> foreignKeys;
    List<Column> notForeignKeys;
    
    Map<String, Index> myIndexes;
    Map<String, Column> myColumns;
    Map<String, Column> myColumnsByLowerCase;
    
    Index primaryKey;
    
    String dbTableName = null;
    
    String pkClass = null;
    String valueClass = null;
    String factoryClass = null;
    String constantsClass = null;
    
    String pkImport = null;
    String valueImport = null;
    String entityImport = null;
    String factoryImport = null;
    String constantsImport = null;
    
    private static final String PK_SUFFIX = "PK";
    private static final String VALUE_SUFFIX = "Value";
    private static final String FACTORY_SUFFIX = "Factory";
    private static final String CONSTANTS_SUFFIX = "Constants";
    private static final String PERIOD = ".";
    
    /** Creates a new instance of Table */
    public Table(Database database, Component component, String namePlural, String nameSingular,
            String columnPrefix, String chunkSize, String description) {
        this.database = database;
        this.component = component;
        this.namePlural = namePlural;
        this.nameSingular = nameSingular;
        this.columnPrefix = columnPrefix;
        this.chunkSize = chunkSize;
        this.description = description;
        this.primaryKey = null;
        
        columns = new ArrayList<>();
        indexes = new ArrayList<>();
        foreignKeys = new ArrayList<>();
        notForeignKeys = new ArrayList<>();
        
        myIndexes = new HashMap<>();
        myColumns = new HashMap<>();
        myColumnsByLowerCase = new HashMap<>();
    }
    
    public Database getDatabase() {
        return database;
    }
    
    public Component getComponent() {
        return component;
    }
    
    public String getNamePlural() {
        return namePlural;
    }
    
    public String getNamePluralLowerCase() {
        return getNamePlural().toLowerCase(Locale.getDefault());
    }
    
    public String getNameSingular() {
        return nameSingular;
    }
    
    public String getColumnPrefix() {
        return columnPrefix;
    }
    
    public String getColumnPrefixLowerCase() {
        return getColumnPrefix().toLowerCase(Locale.getDefault());
    }
    
    public String getChunkSize() {
        return chunkSize;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Index getPrimaryKey() {
        return primaryKey;
    }
    
    public Column addColumn(String attrName, String attrType, String attrMaxLength, String attrDefaultValue, String attrSequenceSource, boolean attrNullAllowed,
            String description, String destinationTable, String destinationColumn, String onParentDelete)
            throws Exception {
        if(myColumns.get(attrName) != null)
            throw new Exception("Duplicate column " + attrName + " in table " + namePlural);

        var newColumn = new Column(this, attrName, attrType, attrMaxLength, attrDefaultValue, attrSequenceSource, attrNullAllowed, description,
                destinationTable, destinationColumn, onParentDelete);
        
        columns.add(newColumn);
        myColumns.put(attrName, newColumn);
        myColumnsByLowerCase.put(attrName.toLowerCase(Locale.getDefault()), newColumn);
        
        if(newColumn.getType() == ColumnType.columnForeignKey) {
            foreignKeys.add(newColumn);
        } else {
            notForeignKeys.add(newColumn);
        }
        
        return newColumn;
    }
    
    public Column getColumn(String columnName) throws Exception {
        var result = myColumns.get(columnName);
        
        if(result == null)
            throw new Exception("Column " + columnName + " not found in table " + namePlural);
        
        return result;
    }
    
    public boolean hasColumn(String columnName) {
        return myColumns.get(columnName) != null;
    }
    
    public Column getColumnLowerCase(String columnName) throws Exception {
        var result = myColumnsByLowerCase.get(columnName);
        
        if(result == null)
            throw new Exception("Column " + columnName + " not found in table " + namePlural);
        
        return result;
    }
    
    public boolean hasColumnLowerCase(String columnName) {
        return myColumnsByLowerCase.get(columnName) != null;
    }
    
    public Index addIndex(String type, String name) throws Exception {
        if(name != null && myIndexes.get(name) != null)
            throw new Exception("Duplicate index " + name + " in table " + namePlural);

        var newIndex = new Index(this, type, name);
        
        if(newIndex.getType() == Index.indexPrimaryKey) {
            if(primaryKey == null) {
                primaryKey = newIndex;
            } else {
                throw new Exception("More than one primary key defined in " + namePlural);
            }
        }
        
        indexes.add(newIndex);
        if(name != null)
            myIndexes.put(name, newIndex);

        return newIndex;
    }
    
    List<Column> getColumns() {
        return columns;
    }
    
    List<Index> getIndexes() {
        return indexes;
    }
    
    List<Column> getForeignKeys() {
        return foreignKeys;
    }
    
    List<Column> getNotForeignKeys() {
        return notForeignKeys;
    }
    
    public boolean isColumnValid(String dbColumnName) {
        var columnValid = true;
        String columnBaseName;

        var underscore = dbColumnName.indexOf('_');
        if(underscore != 0) {
            columnBaseName = dbColumnName.substring(underscore + 1);
        } else {
            columnBaseName = null;
            columnValid = false;
        }
        
        // At this point, if the columnBaseName still contains an underscore, it
        // is a foreign key column. Otherwise, check to see if it's in the list of
        // our normal database columns.
        if(columnValid) {
            if(columnBaseName.indexOf('_') != -1) {
                // Something needs to go here for foreign key columns
                //columnValid = fkColumns.contains(columnBaseName);
            } else if(!hasColumnLowerCase(columnBaseName))
                // Otherwise, if it's not in our list of columns, then it's not
                // a valid column.
                columnValid = false;
        }
        return columnValid;
    }
    
    public int countColumnsWithDestinationTable(String destinationTable) {
        var totalColumns = 0;
        
        totalColumns = columns.stream().filter((theColumn) -> (theColumn.getType() == ColumnType.columnForeignKey)).filter((theColumn) -> theColumn.getDestinationTable().equals(destinationTable)).map((_item) -> 1).reduce(totalColumns, Integer::sum);
        
        return totalColumns;
    }
    
    public boolean isColumnInMultipleIndex(Column destinationColumn) {
        for(var theIndex: indexes) {
            if(theIndex.isColumnInIndex(destinationColumn)) {
                var indexType = theIndex.getType();
                if(indexType == Index.indexMultiple)
                    return true;
                else if(indexType == Index.indexUnique && (theIndex.countIndexColumns() > 1))
                    return true;
            }
        }
        return false;
    }
    
    public boolean isColumnInMultipleIndex(String destinationColumn)
    throws Exception {
        return isColumnInMultipleIndex(getColumn(destinationColumn));
    }
    
    public boolean hasEID() {
        return hasColumnOfType(ColumnType.columnEID);
    }
    
    public Column getEID() {
        Column result = null;
        
        for(var theColumn: columns) {
            if(theColumn.getType() == ColumnType.columnEID) {
                result = theColumn;
                break;
            }
        }
        
        return result;
    }
    
    public boolean hasBlob() {
        return hasColumnOfType(ColumnType.columnBLOB);
    }
    
    public boolean hasClob() {
        return hasColumnOfType(ColumnType.columnCLOB);
    }
    
    public boolean hasForeignKey() {
        return hasColumnOfType(ColumnType.columnForeignKey);
    }
    
    public boolean hasColumnOfType(int columnType) {
        var result = false;
        
        for(var theColumn: columns) {
            if(theColumn.getType() == columnType) {
                result = true;
                break;
            }
        }
        
        return result;
    }
    
    public boolean hasNotNullColumn() {
        var result = true;
        
        for(var theColumn: columns) {
            if((theColumn.getType() != ColumnType.columnEID) && !theColumn.getNullAllowed()) {
                result = false;
                break;
            }
        }
        
        return result;
    }
    
    public String getDbTableName() {
        if(dbTableName == null) {
            dbTableName = namePlural.toLowerCase(Locale.getDefault());
        }
        return dbTableName;
    }
    
    public String getPKClass() {
        if(pkClass == null) {
            pkClass = nameSingular + PK_SUFFIX;
        }
        
        return pkClass;
    }
    
    public String getValueClass() {
        if(valueClass == null) {
            valueClass = nameSingular + VALUE_SUFFIX;
        }
        
        return valueClass;
    }
    
    public String getEntityClass() {
        return nameSingular;
    }
    
    public String getFactoryClass() {
        if(factoryClass == null) {
            factoryClass = nameSingular + FACTORY_SUFFIX;
        }
        
        return factoryClass;
    }
    
    public String getConstantsClass() {
        if(constantsClass == null) {
            constantsClass = nameSingular + CONSTANTS_SUFFIX;
        }
        
        return constantsClass;
    }
    
    public String getPKImport() {
        if(pkImport == null) {
            pkImport = component.getPKPackage() + PERIOD + getPKClass();
        }
        
        return pkImport;
    }
    
    public String getValueImport() {
        if(valueImport == null) {
            valueImport = component.getValuePackage() + PERIOD + getValueClass();
        }
        
        return valueImport;
    }
    
    public String getEntityImport() {
        if(entityImport == null) {
            entityImport = component.getEntityPackage() + PERIOD + getEntityClass();
        }
        
        return entityImport;
    }
    
    public String getFactoryImport() {
        if(factoryImport == null) {
            factoryImport = component.getFactoryPackage() + PERIOD + getFactoryClass();
        }
        
        return factoryImport;
    }
    
    public String getConstantsImport() {
        if(constantsImport == null) {
            constantsImport = component.getCommonPackage() + PERIOD + getConstantsClass();
        }

        return constantsImport;
    }
    
}
