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

package com.echothree.model.control.search.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityListItemTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class EntityListItemResultTransfer
        extends BaseTransfer {
    
    private String componentVendorName;
    private String entityTypeName;
    private String entityAttributeName;
    private String entityListItemName;
    private EntityListItemTransfer entityListItem;
    
    /** Creates a new instance of EntityListItemResultTransfer */
    public EntityListItemResultTransfer(String componentVendorName, String entityTypeName, String entityAttributeName, String entityListItemName,
            EntityListItemTransfer entityListItem) {
        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
        this.entityAttributeName = entityAttributeName;
        this.entityListItemName = entityListItemName;
        this.entityListItem = entityListItem;
    }

    /**
     * @return the componentVendorName
     */
    public String getComponentVendorName() {
        return componentVendorName;
    }

    /**
     * @param componentVendorName the componentVendorName to set
     */
    public void setComponentVendorName(String componentVendorName) {
        this.componentVendorName = componentVendorName;
    }

    /**
     * @return the entityTypeName
     */
    public String getEntityTypeName() {
        return entityTypeName;
    }

    /**
     * @param entityTypeName the entityTypeName to set
     */
    public void setEntityTypeName(String entityTypeName) {
        this.entityTypeName = entityTypeName;
    }

    /**
     * @return the entityAttributeName
     */
    public String getEntityAttributeName() {
        return entityAttributeName;
    }

    /**
     * @param entityAttributeName the entityAttributeName to set
     */
    public void setEntityAttributeName(String entityAttributeName) {
        this.entityAttributeName = entityAttributeName;
    }

    /**
     * @return the entityListItemName
     */
    public String getEntityListItemName() {
        return entityListItemName;
    }

    /**
     * @param entityListItemName the entityListItemName to set
     */
    public void setEntityListItemName(String entityListItemName) {
        this.entityListItemName = entityListItemName;
    }

    /**
     * @return the entityListItem
     */
    public EntityListItemTransfer getEntityListItem() {
        return entityListItem;
    }

    /**
     * @param entityListItem the entityListItem to set
     */
    public void setEntityListItem(EntityListItemTransfer entityListItem) {
        this.entityListItem = entityListItem;
    }

 }
