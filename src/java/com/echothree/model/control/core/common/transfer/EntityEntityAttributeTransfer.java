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
     * Returns the entityAttribute.
     * @return the entityAttribute
     */
    public EntityAttributeTransfer getEntityAttribute() {
        return entityAttribute;
    }

    /**
     * Sets the entityAttribute.
     * @param entityAttribute the entityAttribute to set
     */
    public void setEntityAttribute(EntityAttributeTransfer entityAttribute) {
        this.entityAttribute = entityAttribute;
    }

    /**
     * Returns the entityInstance.
     * @return the entityInstance
     */
    @Override
    public EntityInstanceTransfer getEntityInstance() {
        return entityInstance;
    }

    /**
     * Sets the entityInstance.
     * @param entityInstance the entityInstance to set
     */
    @Override
    public void setEntityInstance(EntityInstanceTransfer entityInstance) {
        this.entityInstance = entityInstance;
    }

    /**
     * Returns the entityInstanceAttribute.
     * @return the entityInstanceAttribute
     */
    public EntityInstanceTransfer getEntityInstanceAttribute() {
        return entityInstanceAttribute;
    }

    /**
     * Sets the entityInstanceAttribute.
     * @param entityInstanceAttribute the entityInstanceAttribute to set
     */
    public void setEntityInstanceAttribute(EntityInstanceTransfer entityInstanceAttribute) {
        this.entityInstanceAttribute = entityInstanceAttribute;
    }
    
}
