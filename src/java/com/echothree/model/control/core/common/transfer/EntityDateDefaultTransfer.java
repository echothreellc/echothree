// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

public class EntityDateDefaultTransfer
        extends BaseTransfer {
    
    private EntityAttributeTransfer entityAttribute;
    private String dateAttribute;
    private Integer unformattedDateAttribute;
    
    /** Creates a new instance of EntityDateDefaultTransfer */
    public EntityDateDefaultTransfer(EntityAttributeTransfer entityAttribute, String dateAttribute,
            Integer unformattedDateAttribute) {
        this.entityAttribute = entityAttribute;
        this.dateAttribute = dateAttribute;
        this.unformattedDateAttribute = unformattedDateAttribute;
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
     * Returns the dateAttribute.
     * @return the dateAttribute
     */
    public String getDateAttribute() {
        return dateAttribute;
    }

    /**
     * Sets the dateAttribute.
     * @param dateAttribute the dateAttribute to set
     */
    public void setDateAttribute(String dateAttribute) {
        this.dateAttribute = dateAttribute;
    }

    /**
     * Returns the unformattedDateAttribute.
     * @return the unformattedDateAttribute
     */
    public Integer getUnformattedDateAttribute() {
        return unformattedDateAttribute;
    }

    /**
     * Sets the unformattedDateAttribute.
     * @param unformattedDateAttribute the unformattedDateAttribute to set
     */
    public void setUnformattedDateAttribute(Integer unformattedDateAttribute) {
        this.unformattedDateAttribute = unformattedDateAttribute;
    }
    
}
