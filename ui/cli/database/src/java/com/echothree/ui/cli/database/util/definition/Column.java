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

import com.google.common.base.Splitter;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class Column {
    
    Table table;
    
    String name;
    ColumnType columnType;
    ColumnDataType type;
    boolean hasMaxLength;
    long maxLength;
    boolean hasTotalDigits;
    String defaultValue;
    String sequenceSource;
    boolean nullAllowed;
    String description;
    String destinationTable;
    String destinationColumn;
    ParentDeleteAction onParentDelete;
    
    String javaType = null;
    String javaSqlType = null;
    String variableName = null;
    String getFunctionName = null;
    String getEntityFunctionName = null;
    String setFunctionName = null;
    String setEntityFunctionName = null;
    String variableSuffixName = null;
    String entityVariableName = null;
    String fkEntityClass = null;
    String fkFactoryClass = null;
    String fkPKClass = null;
    String dbColumnName = null;
    
    /** Creates a new instance of Column */
    public Column(Table table, String name, String type, String maxLength, String defaultValue, String sequenceSource, boolean nullAllowed, String description,
            String destinationTable, String destinationColumn, String onParentDelete)
            throws Exception {
        this.table = table;
        this.name = name;
        if(maxLength != null) {
            hasMaxLength = true;
            this.maxLength = Long.parseLong(maxLength);
        } else {
            hasMaxLength = false;
        }
        this.defaultValue = defaultValue;
        this.sequenceSource = sequenceSource;
        this.nullAllowed = nullAllowed;
        this.description = description;
        this.destinationTable = destinationTable;
        this.destinationColumn = destinationColumn;
        
        this.onParentDelete = ParentDeleteAction.fromDefinitionName(onParentDelete);
        
        this.columnType = null;
        switch(type) {
            case "EID" -> this.type = ColumnDataType.EID;
            case "Integer" -> this.type = ColumnDataType.INTEGER;
            case "Long" -> this.type = ColumnDataType.LONG;
            case "String" -> {
                this.type = ColumnDataType.STRING;
                if(!hasMaxLength) {
                    throw new Exception("String column type requires length");
                }
            }
            case "Boolean" -> this.type = ColumnDataType.BOOLEAN;
            case "Date" -> this.type = ColumnDataType.DATE;
            case "Time" -> this.type = ColumnDataType.TIME;
            case "CLOB" -> this.type = ColumnDataType.CLOB;
            case "BLOB" -> this.type = ColumnDataType.BLOB;
            case "ForeignKey" -> {
                this.type = ColumnDataType.FOREIGN_KEY;
                if(destinationTable == null || destinationColumn == null || onParentDelete == null) {
                    throw new Exception("Foreign Key missing one or more of destinationTable, destinationColumn or onParentDelete");
                }
            }
            case "UUID" -> this.type = ColumnDataType.UUID;
            default -> {
                var types = Splitter.on(':').splitToList(type).toArray(new String[0]);

                for(var i = 0; i < types.length; i++) {
                    var currentColumnType = table.getDatabase().getColumnType(types[i]);

                    if(currentColumnType == null) {
                        throw new Exception("Illegal column type \"" + type + "\"");
                    } else {
                        if(this.type != null && currentColumnType.getRealType() != this.type) {
                            throw new Exception("Multiple incompatible types used \"" + type + "\"");

                        }

                        columnType = currentColumnType;
                        this.type = columnType.getRealType();

                        if(columnType.hasMaxLength()) {
                            this.maxLength = Math.max(this.maxLength, columnType.getMaxLength());
                        }

                        this.destinationTable = columnType.getDestinationTable();
                        this.destinationColumn = columnType.getDestinationColumn();
                        this.onParentDelete = columnType.getOnParentDelete();
                    }
                }
            }
        }
    }

    public Table getTable() {
        return table;
    }
    
    public String getName() {
        return name;
    }
    
    /** Used during code generation when this column appears as a variable name.
     */
    public String getEntityVariableName() {
        if(entityVariableName == null) {
            if(type == ColumnDataType.FOREIGN_KEY || type == ColumnDataType.EID)
                entityVariableName = name.substring(0, 1).toLowerCase(Locale.getDefault()) + name.substring(1, name.length() - 2);
            else
                entityVariableName = name.substring(0, 1).toLowerCase(Locale.getDefault()) + name.substring(1);
        }
        return entityVariableName;
    }
    
    /** Used during code generation when this column appears as a variable name.
     */
    public String getVariableName() {
        if(variableName == null) {
            if(type == ColumnDataType.FOREIGN_KEY || type == ColumnDataType.EID)
                variableName = name.substring(0, 1).toLowerCase(Locale.getDefault()) + name.substring(1, name.length() - 2) + "PK";
            else
                variableName = name.substring(0, 1).toLowerCase(Locale.getDefault()) + name.substring(1);
        }
        return variableName;
    }
    
    /** Used during code generation when this column appears appended to another string as a variable name.
     */
    public String getVariableSuffixName() {
        if(variableSuffixName == null) {
            if(type == ColumnDataType.FOREIGN_KEY || type == ColumnDataType.EID)
                variableSuffixName = name.substring(0, name.length() - 2) + "PK";
            else
                variableSuffixName = name;
        }
        return variableSuffixName;
    }
    
    public String getSetFunctionName() {
        if(setFunctionName == null) {
            if(type == ColumnDataType.FOREIGN_KEY || type == ColumnDataType.EID)
                setFunctionName = "set" + name.substring(0, name.length() - 2) + "PK";
            else
                setFunctionName = "set" + name;
        }
        return setFunctionName;
    }
    
    /** Same as getSetFunctionName, just with a PK on the end if It's for a FK */
    public String getSetEntityFunctionName() {
        if(setEntityFunctionName == null) {
            if(type == ColumnDataType.FOREIGN_KEY || type == ColumnDataType.EID)
                setEntityFunctionName = "set" + name.substring(0, name.length() - 2);
            else
                setEntityFunctionName = "set" + name;
        }
        return setEntityFunctionName;
    }
    
    public String getGetFunctionName() {
        if(getFunctionName == null) {
            if(type == ColumnDataType.FOREIGN_KEY || type == ColumnDataType.EID)
                getFunctionName = "get" + name.substring(0, name.length() - 2) + "PK";
            else
                getFunctionName = "get" + name;
        }
        return getFunctionName;
    }
    
    /** Same as getGetFunctionName, just with a PK on the end if It's for a FK */
    public String getGetEntityFunctionName() {
        if(getEntityFunctionName == null) {
            if(type == ColumnDataType.FOREIGN_KEY || type == ColumnDataType.EID)
                getEntityFunctionName = "get" + name.substring(0, name.length() - 2);
            else
                getEntityFunctionName = "get" + name;
        }
        return getEntityFunctionName;
    }
    
    public String getNameLowerCase() {
        return getName().toLowerCase(Locale.ROOT);
    }
    
    public ColumnDataType getType() {
        return type;
    }
    
    public String getTypeAsJavaType() {
        if(javaType == null) {
            switch(type) {
                case ColumnDataType.EID -> javaType = table.getPKClass();
                case ColumnDataType.INTEGER -> javaType = "Integer";
                case ColumnDataType.LONG -> javaType = "Long";
                case ColumnDataType.STRING -> javaType = "String";
                case ColumnDataType.BOOLEAN -> javaType = "Boolean";
                case ColumnDataType.DATE -> javaType = "Integer";
                case ColumnDataType.TIME -> javaType = "Long";
                case ColumnDataType.CLOB -> javaType = "String";
                case ColumnDataType.BLOB -> javaType = "ByteArray";
                case ColumnDataType.FOREIGN_KEY -> {
                    try {
                        var fkTable = getTable().getDatabase().getTable(destinationTable);
                        javaType = fkTable.getPKClass();
                    } catch(Exception e) {
                        javaType = "<error>";
                    }
                }
                case ColumnDataType.UUID -> javaType = "String";
            }
        }
        return javaType;
    }
    
    public String getTypeAsJavaSqlType() {
        if(javaSqlType == null) {
            javaSqlType = switch(type) {
                case ColumnDataType.EID -> "BIGINT";
                case ColumnDataType.INTEGER -> "INTEGER";
                case ColumnDataType.LONG -> "BIGINT";
                case ColumnDataType.STRING -> "VARCHAR";
                case ColumnDataType.BOOLEAN -> "BIT";
                case ColumnDataType.DATE -> "INTEGER";
                case ColumnDataType.TIME -> "BIGINT";
                case ColumnDataType.CLOB -> "CLOB";
                case ColumnDataType.BLOB -> "BLOB";
                case ColumnDataType.FOREIGN_KEY -> "BIGINT";
                case ColumnDataType.UUID -> "BINARY";
            };
        }
        return javaSqlType;
    }
    
    public String getFKEntityClass() {
        if(fkEntityClass == null) {
            if(type == ColumnDataType.FOREIGN_KEY) {
                try {
                    var fkTable = getTable().getDatabase().getTable(destinationTable);
                    fkEntityClass = fkTable.getEntityClass();
                } catch(Exception e) {
                    fkEntityClass = "<error>";
                }
            }
        }
        return fkEntityClass;
    }
    
    public String getFKFactoryClass() {
        if(fkFactoryClass == null) {
            if(type == ColumnDataType.FOREIGN_KEY) {
                try {
                    var fkTable = getTable().getDatabase().getTable(destinationTable);
                    fkFactoryClass = fkTable.getFactoryClass();
                } catch(Exception e) {
                    fkFactoryClass = "<error>";
                }
            }
        }
        return fkFactoryClass;
    }
    
    public String getFKPKClass() {
        if(fkPKClass == null) {
            if(type == ColumnDataType.FOREIGN_KEY) {
                try {
                    var fkTable = getTable().getDatabase().getTable(destinationTable);
                    fkPKClass = fkTable.getPKClass();
                } catch(Exception e) {
                    fkPKClass = "<error>";
                }
            }
        }
        return fkPKClass;
    }
    
    public String getDbColumnName(String columnPrefixLowerCase)
            throws Exception {
        return DatabasePhysicalNames.columnName(this, columnPrefixLowerCase);
    }
    
    public String getDbColumnName()
            throws Exception {
        if(dbColumnName == null) {
            dbColumnName = getDbColumnName(table.getColumnPrefixLowerCase());
        }
        
        return dbColumnName;
    }
    
    public ColumnType getColumnType() {
        return columnType;
    }
    
    public boolean hasMaxLength() {
        return hasMaxLength;
    }
    
    public long getMaxLength() {
        return maxLength;
    }
    
    public boolean hasTotalDigits() {
        return hasTotalDigits;
    }
    
    public String getDefaultValue() {
        return defaultValue;
    }
    
    public Column getSequenceSource() throws Exception {
        if(sequenceSource == null)
            return null;
        else {
            var thePeriod = sequenceSource.indexOf('.');
            var stringTable = new String(sequenceSource.getBytes(StandardCharsets.UTF_8), 0, thePeriod, StandardCharsets.UTF_8);
            var stringColumn = new String(sequenceSource.getBytes(StandardCharsets.UTF_8), thePeriod + 1, sequenceSource.length() - thePeriod - 1, StandardCharsets.UTF_8);
            return table.getDatabase().getTable(stringTable).getColumn(stringColumn);
        }
    }
    
    public void setNullAllowed(boolean nullAllowed) {
        this.nullAllowed = nullAllowed;
    }
    
    public boolean getNullAllowed() {
        return nullAllowed;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getDestinationTable() {
        return destinationTable;
    }
    
    public String getDestinationColumn() {
        return destinationColumn;
    }
    
    public ParentDeleteAction getOnParentDelete() {
        return onParentDelete;
    }
    
}
