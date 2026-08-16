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

import java.util.LinkedHashSet;
import java.util.Set;

public class Index {

    static public String indexTypeToString(IndexType type) {
        return type.getDefinitionName();
    }
    
    Table table;
    
    IndexType type;
    String name;
    boolean nameWasSpecified;
    
    Set<Column> indexColumns;
    
    /** Creates a new instance of Index */
    public Index(Table table, String type, String name) throws Exception {
        this.table = table;
        this.name = name;
        this.nameWasSpecified = name != null;

        this.type = switch(type) {
            case "PrimaryKey" -> IndexType.PRIMARY_KEY;
            case "Unique" -> IndexType.UNIQUE;
            case "Multiple" -> IndexType.MULTIPLE;
            default -> throw new Exception("Illegal index type " + type);
        };
        
        indexColumns = new LinkedHashSet<>();
    }
    
    public Table getTable() {
        return table;
    }
    
    public IndexType getType() {
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
            if(!nameWasSpecified && type != IndexType.PRIMARY_KEY) {
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
        return indexColumns.stream().anyMatch((theColumn) -> theColumn.getName().equals(matchColumn.getName()));
    }
    
}
