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

package com.echothree.ui.cli.database.util.current;

import java.util.HashMap;
import java.util.Map;

public class CurrentTable {

    private String tableName;
    private Map<String, CurrentColumn> columns = new HashMap<>();
    private Map<String, CurrentIndex> indexes = new HashMap<>();
    private Map<String, CurrentForeignKey> foreignKeys = new HashMap<>();
    
    /** Creates a new instance of CurrentTable */
    public CurrentTable(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, CurrentColumn> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, CurrentColumn> columns) {
        this.columns = columns;
    }

    public void addColumn(CurrentColumn cc) {
        columns.put(cc.getColumnName(), cc);
    }
    
    public CurrentColumn getColumn(String columnName) {
        return columns.get(columnName);
    }
    
    public Map<String, CurrentIndex> getIndexes() {
        return indexes;
    }

    public void setIndexes(Map<String, CurrentIndex> indexes) {
        this.indexes = indexes;
    }

    public CurrentIndex addIndex(CurrentIndex ci) {
        return indexes.put(ci.getIndexName(), ci);
    }
    
    public CurrentIndex getIndex(String indexName) {
        return indexes.get(indexName);
    }
    
    public Map<String, CurrentForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(Map<String, CurrentForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }
    
    public CurrentForeignKey addForeignKey(CurrentForeignKey cfk) {
        return foreignKeys.put(cfk.getForeignKeyName(), cfk);
    }
    
    public CurrentForeignKey getForeignKeyByColumnName(String columnName) {
        CurrentForeignKey result = null;
        
        for(var cfk: foreignKeys.values()) {
            if(cfk.getColumn().getColumnName().equals(columnName)) {
                result = cfk;
                break;
            }
        }
        
        return result;
    }
    
}
