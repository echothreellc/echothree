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

public class ColumnType {

    static public String columnTypeToString(ColumnDataType type) {
        return type.getDefinitionName();
    }
    
    String type;
    ColumnDataType realType;
    boolean hasMaxLength;
    long maxLength;
    boolean hasTotalDigits;
    String description;
    String destinationTable;
    String destinationColumn;
    ParentDeleteAction onParentDelete;
    
    /** Creates a new instance of ColumnType */
    public ColumnType(String type, String realType, String maxLength, String description, String destinationTable, String destinationColumn,
            String onParentDelete)
            throws Exception {
        
        this.type = type;
        if(maxLength != null) {
            hasMaxLength = true;
            this.maxLength = Long.parseLong(maxLength);
        } else {
            hasMaxLength = false;
        }
        this.description = description;
        this.destinationTable = destinationTable;
        this.destinationColumn = destinationColumn;
        
        this.onParentDelete = ParentDeleteAction.fromDefinitionName(onParentDelete);
        
        if(realType.equals("EID"))
            this.realType = ColumnDataType.EID;
        else if(realType.equals("Integer"))
            this.realType = ColumnDataType.INTEGER;
        else if(realType.equals("Long"))
            this.realType = ColumnDataType.LONG;
        else if(realType.equals("String")) {
            this.realType = ColumnDataType.STRING;
            if(!hasMaxLength)
                throw new Exception("String column type requires length");
        } else if(realType.equals("Boolean"))
            this.realType = ColumnDataType.BOOLEAN;
        else if(realType.equals("Date"))
            this.realType = ColumnDataType.DATE;
        else if(realType.equals("Time"))
            this.realType = ColumnDataType.TIME;
        else if(realType.equals("CLOB"))
            this.realType = ColumnDataType.CLOB;
        else if(realType.equals("BLOB"))
            this.realType = ColumnDataType.BLOB;
        else if(type.equals("ForeignKey")) {
            this.realType = ColumnDataType.FOREIGN_KEY;
            if(destinationTable == null || destinationColumn == null || onParentDelete == null)
                throw new Exception("Foreign Key missing one or more of destinationTable, destinationColumn or onParentDelete");
        } else if(type.equals("UUID"))
            this.realType = ColumnDataType.UUID;
        else
            throw new Exception("Illegal column type \"" + realType + "\"");
    }
    
    public String getType() {
        return type;
    }
    
    public ColumnDataType getRealType() {
        return realType;
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
