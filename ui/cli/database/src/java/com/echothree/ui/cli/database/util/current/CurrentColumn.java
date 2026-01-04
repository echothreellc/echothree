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

import java.util.HashSet;
import java.util.Set;

public class CurrentColumn {

    private CurrentTable table;
    private String columnName;
    private int type;
    private int columnSize;
    private boolean nullable;
    private Set<CurrentIndex> indexes = new HashSet<>();
    private Set<CurrentForeignKey> foreignKeys = new HashSet<>();
    private Set<CurrentForeignKey> targetForeignKeys = new HashSet<>();

    /** Creates a new instance of CurrentColumn */
    public CurrentColumn(CurrentTable table, String columnName, int type, int columnSize, boolean nullable) {
        this.table = table;
        this.columnName = columnName;
        this.type = type;
        this.columnSize = columnSize;
        this.nullable = nullable;
    }

    public CurrentTable getTable() {
        return table;
    }

    public void setTable(CurrentTable table) {
        this.table = table;
    }
    
    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public Set<CurrentIndex> getIndexes() {
        return indexes;
    }

    public void setIndexes(Set<CurrentIndex> indexes) {
        this.indexes = indexes;
    }

    public void addIndex(CurrentIndex ci) {
        indexes.add(ci);
    }
    
    public Set<CurrentForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(Set<CurrentForeignKey> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }
    
    public void addForeignKey(CurrentForeignKey cfk) {
        foreignKeys.add(cfk);
    }

    public Set<CurrentForeignKey> getTargetForeignKeys() {
        return targetForeignKeys;
    }

    public void setTargetForeignKeys(Set<CurrentForeignKey> targetForeignKeys) {
        this.targetForeignKeys = targetForeignKeys;
    }
    
    public void addTargetForeignKey(CurrentForeignKey cfk) {
        targetForeignKeys.add(cfk);
    }

}
