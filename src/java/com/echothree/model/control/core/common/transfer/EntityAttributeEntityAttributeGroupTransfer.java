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

public class EntityAttributeEntityAttributeGroupTransfer
        extends BaseTransfer {
    
    private EntityAttributeTransfer entityAttribute;
    private EntityAttributeGroupTransfer entityAttributeGroup;
    private Integer sortOrder;
    
    /** Creates a new instance of EntityAttributeEntityAttributeGroupTransfer */
    public EntityAttributeEntityAttributeGroupTransfer(EntityAttributeTransfer entityAttribute, EntityAttributeGroupTransfer entityAttributeGroup,
            Integer sortOrder) {
        this.entityAttribute = entityAttribute;
        this.entityAttributeGroup = entityAttributeGroup;
        this.sortOrder = sortOrder;
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
     * Returns the entityAttributeGroup.
     * @return the entityAttributeGroup
     */
    public EntityAttributeGroupTransfer getEntityAttributeGroup() {
        return entityAttributeGroup;
    }

    /**
     * Sets the entityAttributeGroup.
     * @param entityAttributeGroup the entityAttributeGroup to set
     */
    public void setEntityAttributeGroup(EntityAttributeGroupTransfer entityAttributeGroup) {
        this.entityAttributeGroup = entityAttributeGroup;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
}
