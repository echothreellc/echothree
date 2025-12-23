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

import java.util.LinkedHashSet;
import java.util.Set;

public class Index {
    
    static final int indexPrimaryKey = 1;
    static final int indexUnique = 2;
    static final int indexMultiple = 3;
    
    static public String indexTypeToString(int type) {
        return switch(type) {
            case indexPrimaryKey -> "PrimaryKey";
            case indexUnique -> "Unique";
            case indexMultiple -> "Multiple";
            default -> null;
        };
    }
    
    Table table;
    
    int type;
    String name;
    boolean nameWasSpecified;
    
    Set<Column> indexColumns;
    
    /** Creates a new instance of Index */
    public Index(Table table, String type, String name) throws Exception {
        this.table = table;
        this.name = name;
        this.nameWasSpecified = name != null;

        switch(type) {
            case "PrimaryKey" -> this.type = indexPrimaryKey;
            case "Unique" -> this.type = indexUnique;
            case "Multiple" -> this.type = indexMultiple;
            default -> throw new Exception("Illegal index type " + type);
        }
        
        indexColumns = new LinkedHashSet<>();
    }
    
    public Table getTable() {
        return table;
    }
    
    public int getType() {
        return type;
    }
    
    public String getName() {
        if(this.name == null && !indexColumns.isEmpty()) {
            this.name = indexColumns.iterator().next().getNameLowerCase();
        }
        
        return name;
    }
    
    public void addIndexColumn(String columnName) throws Exception {
        if(!table.hasColumn(columnName)) {
            throw new Exception((name == null? "Unnamed index": "Index " + name) + " trying to use a nonexistant column " + columnName + " in table " + table.getNamePlural());
        }
        
        if(name == null) {
            name = columnName;
        }

        var column = table.getColumn(columnName);
        
        if(indexColumns.contains(column)) {
            throw new Exception("Index " + name + " trying to use the column " + columnName + " more than once in table " + table.getNamePlural());
        }
        
        indexColumns.add(column);
        
        if(indexColumns.size() > 1) {
            if(!nameWasSpecified && type != indexPrimaryKey) {
                throw new Exception("Index with more than one column was not given a name in " + table.getNamePlural());
            }
        }
    }
    
    public Set<Column> getIndexColumns() {
        return indexColumns;
    }
    
    public int countIndexColumns() {
        return indexColumns.size();
    }
    
    public boolean isColumnInIndex(Column matchColumn) {
        return indexColumns.stream().anyMatch((theColumn) -> (theColumn == matchColumn));
    }
    
}
