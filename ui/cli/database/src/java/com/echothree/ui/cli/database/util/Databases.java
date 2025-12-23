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

import java.util.HashMap;

public class Databases {
    
    HashMap<String, Database> myDatabases;
    
    /** Creates a new instance of Databases */
    public Databases() {
        myDatabases = new HashMap<>();
    }
    
    public Database addDatabase(String name) {
        var result = myDatabases.get(name);
        
        if(result == null) {
            result = new Database(name);
            myDatabases.put(name, result);
        }
        
        return result;
    }
    
    public Database getDatabase(String name) throws Exception {
        var result = myDatabases.get(name);
        
        if(result == null)
            throw new Exception("Database \"" + name + "\" doesn't exist");
        
        return result;
    }
    
}
