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

public class EntityIntegerRangeTransfer
        extends BaseTransfer {
    
    private EntityAttributeTransfer entityAttribute;
    private String entityIntegerRangeName;
    private Integer minimumIntegerValue;
    private Integer maximumIntegerValue;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of EntityIntegerRangeTransfer */
    public EntityIntegerRangeTransfer(EntityAttributeTransfer entityAttribute, String entityIntegerRangeName, Integer minimumIntegerValue,
            Integer maximumIntegerValue, Boolean isDefault, Integer sortOrder, String description) {
        this.entityAttribute = entityAttribute;
        this.entityIntegerRangeName = entityIntegerRangeName;
        this.minimumIntegerValue = minimumIntegerValue;
        this.maximumIntegerValue = maximumIntegerValue;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
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
     * Returns the entityIntegerRangeName.
     * @return the entityIntegerRangeName
     */
    public String getEntityIntegerRangeName() {
        return entityIntegerRangeName;
    }

    /**
     * Sets the entityIntegerRangeName.
     * @param entityIntegerRangeName the entityIntegerRangeName to set
     */
    public void setEntityIntegerRangeName(String entityIntegerRangeName) {
        this.entityIntegerRangeName = entityIntegerRangeName;
    }

    /**
     * Returns the minimumIntegerValue.
     * @return the minimumIntegerValue
     */
    public Integer getMinimumIntegerValue() {
        return minimumIntegerValue;
    }

    /**
     * Sets the minimumIntegerValue.
     * @param minimumIntegerValue the minimumIntegerValue to set
     */
    public void setMinimumIntegerValue(Integer minimumIntegerValue) {
        this.minimumIntegerValue = minimumIntegerValue;
    }

    /**
     * Returns the maximumIntegerValue.
     * @return the maximumIntegerValue
     */
    public Integer getMaximumIntegerValue() {
        return maximumIntegerValue;
    }

    /**
     * Sets the maximumIntegerValue.
     * @param maximumIntegerValue the maximumIntegerValue to set
     */
    public void setMaximumIntegerValue(Integer maximumIntegerValue) {
        this.maximumIntegerValue = maximumIntegerValue;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
