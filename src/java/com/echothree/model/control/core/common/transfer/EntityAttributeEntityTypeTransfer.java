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

public class EntityAttributeEntityTypeTransfer
        extends BaseTransfer {
    
    private EntityAttributeTransfer entityAttribute;
    private EntityTypeTransfer allowedEntityType;
    
    /** Creates a new instance of EntityAttributeEntityTypeTransfer */
    public EntityAttributeEntityTypeTransfer(EntityAttributeTransfer entityAttribute, EntityTypeTransfer allowedEntityType) {
        this.entityAttribute = entityAttribute;
        this.allowedEntityType = allowedEntityType;
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
     * Returns the allowedEntityType.
     * @return the allowedEntityType
     */
    public EntityTypeTransfer getAllowedEntityType() {
        return allowedEntityType;
    }

    /**
     * Sets the allowedEntityType.
     * @param allowedEntityType the allowedEntityType to set
     */
    public void setAllowedEntityType(EntityTypeTransfer allowedEntityType) {
        this.allowedEntityType = allowedEntityType;
    }
    
}
