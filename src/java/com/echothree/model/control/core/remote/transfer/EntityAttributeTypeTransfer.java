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

public class EntityAttributeTypeTransfer
        extends BaseTransfer {
    
    private String entityAttributeTypeName;
    private String description;
    
    /** Creates a new instance of EntityAttributeTypeTransfer */
    public EntityAttributeTypeTransfer(String entityAttributeTypeName, String description) {
        this.entityAttributeTypeName = entityAttributeTypeName;
        this.description = description;
    }

    /**
     * @return the entityAttributeTypeName
     */
    public String getEntityAttributeTypeName() {
        return entityAttributeTypeName;
    }

    /**
     * @param entityAttributeTypeName the entityAttributeTypeName to set
     */
    public void setEntityAttributeTypeName(String entityAttributeTypeName) {
        this.entityAttributeTypeName = entityAttributeTypeName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
}
