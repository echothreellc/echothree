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

public class CurrentDatabase {

    private Map<String, CurrentTable> tables = new HashMap<>();

    public Map<String, CurrentTable> getTables() {
        return tables;
    }

    public void setTables(Map<String, CurrentTable> tables) {
        this.tables = tables;
    }
    
    public void addTable(CurrentTable table) {
        tables.put(table.getTableName(), table);
    }
    
    public CurrentTable getTable(String tableName) {
        return tables.get(tableName);
    }
    
}
