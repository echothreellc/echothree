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

package com.echothree.model.control.core.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class CommandTransfer
        extends BaseTransfer {
    
    private ComponentVendorTransfer componentVendor;
    private String commandName;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of CommandTransfer */
    public CommandTransfer(ComponentVendorTransfer componentVendor, String commandName, Integer sortOrder, String description) {
        this.componentVendor = componentVendor;
        this.commandName = commandName;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public ComponentVendorTransfer getComponentVendor() {
        return componentVendor;
    }
    
    public void setComponentVendor(ComponentVendorTransfer componentVendor) {
        this.componentVendor = componentVendor;
    }
    
    public String getCommandName() {
        return commandName;
    }
    
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
