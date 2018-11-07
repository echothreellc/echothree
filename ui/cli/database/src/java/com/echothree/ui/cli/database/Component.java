// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.ui.cli.database;

import java.util.ArrayList;
import java.util.List;

public class Component {
    
    String attrName;
    String attrPackage;
    String attrLocalPackage;
    
    List<Table> tables;
    
    String pkPackage = null;
    String valuePackage = null;
    String entityPackage = null;
    String factoryPackage = null;
    String commonPackage = null;
    
    /** Creates a new instance of Component */
    public Component(Database database, String attrName) {
        this.attrName = attrName;
        
        tables = new ArrayList<>();
    }
    
    public String getName() {
        return attrName;
    }
    
    public List<Table> getTables() {
        return tables;
    }
    
    public void addTable(Table table) {
        tables.add(table);
    }
    
    public String getPKPackage() {
        if(pkPackage == null) {
            pkPackage = new StringBuilder("com.echothree.model.data.").append(attrName.toLowerCase()).append(".common.pk").toString();
        }
        
        return pkPackage;
    }
    
    public String getValuePackage() {
        if(valuePackage == null) {
            valuePackage = new StringBuilder("com.echothree.model.data.").append(attrName.toLowerCase()).append(".server.value").toString();
        }
        
        return valuePackage;
    }
    
    public String getEntityPackage() {
        if(entityPackage == null) {
            entityPackage = new StringBuilder("com.echothree.model.data.").append(attrName.toLowerCase()).append(".server.entity").toString();
        }
        
        return entityPackage;
    }
    
    public String getFactoryPackage() {
        if(factoryPackage == null) {
            factoryPackage = new StringBuilder("com.echothree.model.data.").append(attrName.toLowerCase()).append(".server.factory").toString();
        }
        
        return factoryPackage;
    }
    
    public String getCommonPackage() {
        if(commonPackage == null) {
            commonPackage = new StringBuilder("com.echothree.model.data.").append(attrName.toLowerCase()).append(".common").toString();
        }
        
        return commonPackage;
    }
    
}
