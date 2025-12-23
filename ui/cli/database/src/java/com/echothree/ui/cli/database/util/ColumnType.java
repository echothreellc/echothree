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

public class ColumnType {
    
    static final int columnEID = 1;
    static final int columnInteger = 2;
    static final int columnLong = 3;
    static final int columnString = 5;
    static final int columnBoolean = 6;
    static final int columnDate = 7;
    static final int columnTime = 8;
    static final int columnCLOB = 9;
    static final int columnBLOB = 10;
    static final int columnForeignKey = 11;
    static final int columnUUID = 12;
    
    static public String columnTypeToString(int type) {
        return switch(type) {
            case columnEID -> "EID";
            case columnInteger -> "Integer";
            case columnLong -> "Long";
            case columnString -> "String";
            case columnBoolean -> "Boolean";
            case columnDate -> "Date";
            case columnTime -> "Time";
            case columnCLOB -> "CLOB";
            case columnBLOB -> "BLOB";
            case columnForeignKey -> "ForeignKey";
            case columnUUID -> "UUID";
            default -> null;
        };
    }
    
    String type;
    int realType;
    boolean hasMaxLength;
    long maxLength;
    boolean hasTotalDigits;
    String description;
    String destinationTable;
    String destinationColumn;
    int onParentDelete;
    
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
        
        if(onParentDelete != null)  {
            if(onParentDelete.equals("delete"))
                this.onParentDelete = Column.parentDelete;
            else if(onParentDelete.equals("setNull"))
                this.onParentDelete = Column.parentSetNull;
            else
                throw new Exception("Illegal onParentDelete \"" + onParentDelete + "\"");
        } else
            this.onParentDelete = Column.parentNone;
        
        if(realType.equals("EID"))
            this.realType = columnEID;
        else if(realType.equals("Integer"))
            this.realType = columnInteger;
        else if(realType.equals("Long"))
            this.realType = columnLong;
        else if(realType.equals("String")) {
            this.realType = columnString;
            if(!hasMaxLength)
                throw new Exception("String column type requires length");
        } else if(realType.equals("Boolean"))
            this.realType = columnBoolean;
        else if(realType.equals("Date"))
            this.realType = columnDate;
        else if(realType.equals("Time"))
            this.realType = columnTime;
        else if(realType.equals("CLOB"))
            this.realType = columnCLOB;
        else if(realType.equals("BLOB"))
            this.realType = columnBLOB;
        else if(type.equals("ForeignKey")) {
            this.realType = columnForeignKey;
            if(destinationTable == null || destinationColumn == null || onParentDelete == null)
                throw new Exception("Foreign Key missing one or more of destinationTable, destinationColumn or onParentDelete");
        } else if(type.equals("UUID"))
            this.realType = columnUUID;
        else
            throw new Exception("Illegal column type \"" + realType + "\"");
    }
    
    public String getType() {
        return type;
    }
    
    public int getRealType() {
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
    
    public int getOnParentDelete() {
        return onParentDelete;
    }
    
}
