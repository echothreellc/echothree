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
import com.echothree.ui.cli.database.util.current.CurrentForeignKey;
import com.echothree.ui.cli.database.util.current.CurrentIndex;
import com.echothree.ui.cli.database.util.current.CurrentTable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatabaseUpdateTasks {
    
    List<Table> tablesNeeded;
    List<Column> columnsNeeded;
    List<Column> incorrectColumns;
    Set<Index> indexesNeeded;
    Set<Column> foreignKeysNeeded;
    
    Set<CurrentForeignKey> extraForeignKeys;
    Set<CurrentIndex> extraIndexes;
    List<CurrentTable> extraTables;
    List<CurrentColumn> extraColumns;
    
    /** Creates a new instance of DatabaseUpdateTasks */
    public DatabaseUpdateTasks() {
        tablesNeeded = new ArrayList<>();
        columnsNeeded = new ArrayList<>();
        incorrectColumns = new ArrayList<>();
        indexesNeeded = new HashSet<>();
        foreignKeysNeeded = new HashSet<>();
        
        extraForeignKeys = new HashSet<>();
        extraIndexes = new HashSet<>();
        extraTables = new ArrayList<>();
        extraColumns = new ArrayList<>();
    }
    
    public void addTable(Table table) {
        tablesNeeded.add(table);
        indexesNeeded.addAll(table.getIndexes());
        foreignKeysNeeded.addAll(table.getForeignKeys());
    }
    
    public List<Table> getTables() {
        return tablesNeeded;
    }
    
    public void addColumn(Column column) {
        columnsNeeded.add(column);
        
        if(column.getType() == ColumnType.columnForeignKey) {
            addForeignKey(column);
        }
    }
    
    public List<Column> getColumns() {
        return columnsNeeded;
    }
    
    public void addIndex(Index index) {
        indexesNeeded.add(index);
    }
    
    public List<Column> getIncorrectColumns() {
        return incorrectColumns;
    }
    
    public void addIncorrectColumn(Column column) {
        incorrectColumns.add(column);
    }
    
    public Set<Index> getIndexes() {
        return indexesNeeded;
    }
    
    public void addForeignKey(Column foreignKey) {
        foreignKeysNeeded.add(foreignKey);
    }
    
    public Set<Column> getForeignKeys() {
        return foreignKeysNeeded;
    }
    
    public void addExtraForeignKey(CurrentForeignKey cfk) {
        extraForeignKeys.add(cfk);
    }
    
    public Set<CurrentForeignKey> getExtraForeignKeys() {
        return extraForeignKeys;
    }
    
    public void addExtraIndex(CurrentIndex ci) {
        extraIndexes.add(ci);
    }
    
    public Set<CurrentIndex> getExtraIndexes() {
        return extraIndexes;
    }
    
    public void addExtraTable(CurrentTable ct) {
        // Also need to treat FKs targeting columns in ct as extras.
        ct.getColumns().values().forEach((cc) -> {
            extraForeignKeys.addAll(cc.getTargetForeignKeys());
        });
        
        extraTables.add(ct);
    }
    
    public List<CurrentTable> getExtraTables() {
        return extraTables;
    }
    
    public void addExtraColumn(CurrentColumn cc) {
        cc.getIndexes().forEach(this::addExtraIndex);
        cc.getForeignKeys().forEach(this::addExtraForeignKey);
        cc.getTargetForeignKeys().forEach(this::addExtraForeignKey);
        
        extraColumns.add(cc);
    }
    
    public void addExtraColumns(List<CurrentColumn> ccs) {
        ccs.forEach(this::addExtraColumn);
    }
    
    public List<CurrentColumn> getExtraColumns() {
        return extraColumns;
    }
    
}
