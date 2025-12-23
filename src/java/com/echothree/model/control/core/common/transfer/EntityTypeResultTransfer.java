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

import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class EntityTypeResultTransfer
        extends BaseTransfer {
    
    private String componentVendorName;
    private String entityTypeName;
    private EntityTypeTransfer entityType;
    
    /** Creates a new instance of EntityTypeResultTransfer */
    public EntityTypeResultTransfer(String componentVendorName, String entityTypeName, EntityTypeTransfer entityType) {
        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
        this.entityType = entityType;
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
     * Returns the entityType.
     * @return the entityType
     */
    public EntityTypeTransfer getEntityType() {
        return entityType;
    }

    /**
     * Sets the entityType.
     * @param entityType the entityType to set
     */
    public void setEntityType(EntityTypeTransfer entityType) {
        this.entityType = entityType;
    }

 }
