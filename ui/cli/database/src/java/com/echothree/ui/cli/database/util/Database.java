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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Database {
    
    String name;
    
    List<ColumnType> myColumnTypes;
    Map<String, ColumnType> myColumnTypesByType;
    
    List<Table> myTables;
    Map<String, Table> myTablesByPlural;
    Map<String, Table> myTablesByPluralLowerCase;
    Map<String, Table> myTablesBySingular;
    Map<String, Table> myTablesByColumnPrefix;
    
    List<Component> myComponents;
    Map<String, Component> myComponentsByName;
    
    /** Creates a new instance of Database */
    public Database(String name) {
        this.name = name;
        
        myColumnTypes = new ArrayList<>();
        myColumnTypesByType = new HashMap<>();
        
        myTables = new ArrayList<>();
        myTablesByPlural = new HashMap<>();
        myTablesByPluralLowerCase = new HashMap<>();
        myTablesBySingular = new HashMap<>();
        myTablesByColumnPrefix = new HashMap<>();
        
        myComponents = new ArrayList<>();
        myComponentsByName = new HashMap<>();
    }
    
    public String getName() {
        return name;
    }
    
    public Component addComponent(String attrName) {
        var result = myComponentsByName.get(attrName);
        
        if(result == null) {
            result = new Component(this, attrName);
            myComponents.add(result);
            myComponentsByName.put(attrName, result);
        }
        
        return result;
    }
    
    public List<Component> getComponents() {
        return myComponents;
    }
    
    public Component getComponentByName(String attrName) throws Exception {
        var result = myComponentsByName.get(attrName);
        
        if(result == null)
            throw new Exception("Component \"" + attrName + "\" doesn't exist");
        
        return result;
    }
    
    public void addTableToComponent(Component component, Table table) {
        component.addTable(table);
    }
    
    public Table addTable(Component component, String namePlural, String nameSingular,
            String columnPrefix, String chunkSize, String description)
            throws Exception {
        var result = new Table(this, component, namePlural, nameSingular, columnPrefix, chunkSize, description);
        
        if(myTablesByPlural.containsKey(namePlural))
            throw new Exception("Error adding table, duplicate namePlural \"" + namePlural + "\"");
        if(myTablesBySingular.containsKey(nameSingular))
            throw new Exception("Error adding table, duplicate nameSingular \"" + nameSingular + "\"");
        if(myTablesByColumnPrefix.containsKey(columnPrefix))
            throw new Exception("Error adding table, duplicate columnPrefix \"" + columnPrefix + "\"");
        
        myTables.add(result);
        myTablesByPlural.put(namePlural, result);
        myTablesByPluralLowerCase.put(namePlural.toLowerCase(Locale.getDefault()), result);
        myTablesBySingular.put(nameSingular, result);
        myTablesByColumnPrefix.put(columnPrefix, result);
        
        addTableToComponent(component, result);
        
        return result;
    }
    
    public List<Table> getTables() {
        return myTables;
    }
    
    public Table getTable(String namePlural) throws Exception {
        var result = myTablesByPlural.get(namePlural);
        if(result == null)
            throw new Exception("Table \"" + namePlural + "\" doesn't exist");
        return result;
    }
    
    public Table getTableLowerCase(String namePlural) throws Exception {
        var result = myTablesByPluralLowerCase.get(namePlural);
        if(result == null)
            throw new Exception("Table \"" + namePlural + "\" doesn't exist");
        return result;
    }
    
    public Table getTableByPlural(String namePlural) throws Exception {
        return getTable(namePlural);
    }
    
    public Table getTableBySingular(String nameSingular) throws Exception {
        var result = myTablesBySingular.get(nameSingular);
        if(result == null)
            throw new Exception("Table \"" + nameSingular + "\" doesn't exist");
        return result;
    }
    
    public Table getTableByColumnPrefix(String columnPrefix) throws Exception {
        var result = myTablesByColumnPrefix.get(columnPrefix);
        if(result == null)
            throw new Exception("Table \"" + columnPrefix + "\" doesn't exist");
        return result;
    }
    
    public ColumnType addColumnType(String type, String realType, String maxLength, String description, String destinationTable, String destinationColumn,
            String onParentDelete) throws Exception {
        var result = new ColumnType(type, realType, maxLength, description, destinationTable, destinationColumn, onParentDelete);
        myColumnTypes.add(result);
        myColumnTypesByType.put(type, result);
        return result;
    }
    
    public ColumnType getColumnType(String type) throws Exception {
        var result = myColumnTypesByType.get(type);
        if(result == null)
            throw new Exception("Column type \"" + type + "\" doesn't exist");
        return result;
    }
    
}
