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

public class CurrentForeignKey {

    private CurrentTable table;
    private String foreignKeyName;
    private CurrentColumn column;
    private CurrentColumn targetColumn;
    
    /** Creates a new instance of CurrentForeignKey */
    public CurrentForeignKey(CurrentTable table, String foreignKeyName, CurrentColumn column, CurrentColumn targetColumn) {
        this.table = table;
        this.foreignKeyName = foreignKeyName;
        this.column = column;
        this.targetColumn = targetColumn;
    }

    public CurrentTable getTable() {
        return table;
    }

    public void setTable(CurrentTable table) {
        this.table = table;
    }
    
    public CurrentColumn getTargetColumn() {
        return targetColumn;
    }

    public void setTargetColumn(CurrentColumn targetColumn) {
        this.targetColumn = targetColumn;
    }

    public String getForeignKeyName() {
        return foreignKeyName;
    }

    public void setForeignKeyName(String foreignKeyName) {
        this.foreignKeyName = foreignKeyName;
    }

    public CurrentColumn getColumn() {
        return column;
    }

    public void setColumn(CurrentColumn column) {
        this.column = column;
    }
    
}
