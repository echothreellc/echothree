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

public class EntityAliasTransfer
        extends BaseTransfer {
    
    private EntityAliasTypeTransfer entityAliasType;
    private String attribute;
    private EntityInstanceTransfer entityInstance;
    
    /** Creates a new instance of EntityAttributeTransfer */
    public EntityAliasTransfer(EntityAliasTypeTransfer entityAliasType, String attribute,
            EntityInstanceTransfer entityInstance) {
        this.entityAliasType = entityAliasType;
        this.attribute = attribute;
        this.entityInstance = entityInstance;
    }
    
    public EntityAliasTypeTransfer getEntityAliasType() {
        return entityAliasType;
    }
    
    public void setEntityAliasType(EntityAliasTypeTransfer entityAliasType) {
        this.entityAliasType = entityAliasType;
    }
    
    public String getAttribute() {
        return attribute;
    }
    
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    
    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }
    
    @Override
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }
    
}
