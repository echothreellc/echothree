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

public class EntityAttributeResultTransfer
        extends BaseTransfer {
    
    private String componentVendorName;
    private String entityTypeName;
    private String entityAttributeName;
    private EntityAttributeTransfer entityAttribute;
    
    /** Creates a new instance of EntityAttributeResultTransfer */
    public EntityAttributeResultTransfer(String componentVendorName, String entityTypeName, String entityAttributeName,
            EntityAttributeTransfer entityAttribute) {
        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
        this.entityAttributeName = entityAttributeName;
        this.entityAttribute = entityAttribute;
    }

    /**
     * Returns the componentVendorName.
     * @return the componentVendorName
     */
    public String getComponentVendorName() {
        return componentVendorName;
    }

    /**
     * Sets the componentVendorName.
     * @param componentVendorName the componentVendorName to set
     */
    public void setComponentVendorName(String componentVendorName) {
        this.componentVendorName = componentVendorName;
    }

    /**
     * Returns the entityTypeName.
     * @return the entityTypeName
     */
    public String getEntityTypeName() {
        return entityTypeName;
    }

    /**
     * Sets the entityTypeName.
     * @param entityTypeName the entityTypeName to set
     */
    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    /**
     * Returns the entityAttributeName.
     * @return the entityAttributeName
     */
    public String getEntityAttributeName() {
        return entityAttributeName;
    }

    /**
     * Sets the entityAttributeName.
     * @param entityAttributeName the entityAttributeName to set
     */
    public void setEntityAttributeName(String entityAttributeName) {
        this.entityAttributeName = entityAttributeName;
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

 }
