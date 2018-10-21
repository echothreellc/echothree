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

package com.echothree.model.control.core.remote.transfer;

import com.echothree.util.remote.transfer.BaseTransfer;

public class EntityEntityAttributeTransfer
        extends BaseTransfer {
    
    private EntityAttributeTransfer entityAttribute;
    private EntityInstanceTransfer entityInstance;
    private EntityInstanceTransfer entityInstanceAttribute;
    
    /** Creates a new instance of EntityEntityAttributeTransfer */
    public EntityEntityAttributeTransfer(EntityAttributeTransfer entityAttribute, EntityInstanceTransfer entityInstance,
            EntityInstanceTransfer entityInstanceAttribute) {
        this.entityAttribute = entityAttribute;
        this.entityInstance = entityInstance;
        this.entityInstanceAttribute = entityInstanceAttribute;
    }

    /**
     * @return the entityAttribute
     */
    public EntityAttributeTransfer getEntityAttribute() {
        return entityAttribute;
    }

    /**
     * @param entityAttribute the entityAttribute to set
     */
    public void setEntityAttribute(EntityAttributeTransfer entityAttribute) {
        this.entityAttribute = entityAttribute;
    }

    /**
     * @return the entityInstance
     */
    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }

    /**
     * @param entityInstance the entityInstance to set
     */
    @Override
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }

    /**
     * @return the entityInstanceAttribute
     */
    public EntityInstanceTransfer getEntityInstanceAttribute() {
        return entityInstanceAttribute;
    }

    /**
     * @param entityInstanceAttribute the entityInstanceAttribute to set
     */
    public void setEntityInstanceAttribute(EntityInstanceTransfer entityInstanceAttribute) {
        this.entityInstanceAttribute = entityInstanceAttribute;
    }
    
}
