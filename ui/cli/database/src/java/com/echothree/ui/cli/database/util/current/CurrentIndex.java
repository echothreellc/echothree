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

import java.util.Set;

public class CurrentIndex {

    private CurrentTable table;
    private String indexName;
    private boolean unique;
    private boolean primaryKey;
    private Set<CurrentColumn> columns;
    
    /** Creates a new instance of CurrentIndex */
    public CurrentIndex(CurrentTable table, String indexName, boolean unique, Set<CurrentColumn> columns) {
        this.table = table;
        this.indexName = indexName;
        this.unique = unique;
        this.primaryKey = false;
        this.columns = columns;
    }

    public CurrentTable getTable() {
        return table;
    }

    public void setTable(CurrentTable table) {
        this.table = table;
    }
    
    public Set<CurrentColumn> getColumns() {
        return columns;
    }

    public void setColumns(Set<CurrentColumn> columns) {
        this.columns = columns;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public boolean isUnique() {
        return unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
    
}
