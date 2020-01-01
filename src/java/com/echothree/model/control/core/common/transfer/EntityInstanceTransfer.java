// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.util.common.persistence.EntityNames;
import com.echothree.util.common.transfer.BaseTransfer;

public class EntityInstanceTransfer
        extends BaseTransfer {
    
    private EntityTypeTransfer entityType;
    private Long entityUniqueId;
    private String key;
    private String guid;
    private String ulid;
    private String entityRef;
    private EntityTimeTransfer entityTime;
    private String description;
    
    private EntityAppearanceTransfer entityAppearance;
    private EntityNames entityNames;
    
    /** Creates a new instance of EntityInstanceTransfer */
    public EntityInstanceTransfer(EntityTypeTransfer entityType, Long entityUniqueId, String key, String guid, String ulid,
            String entityRef, EntityTimeTransfer entityTime, String description) {
        this.entityType = entityType;
        this.entityUniqueId = entityUniqueId;
        this.key = key;
        this.guid = guid;
        this.ulid = ulid;
        this.entityRef = entityRef;
        this.entityTime = entityTime;
        this.description = description;
    }
    
    public EntityTypeTransfer getEntityType() {
        return entityType;
    }
    
    public void setEntityType(EntityTypeTransfer entityType) {
        this.entityType = entityType;
    }
    
    public Long getEntityUniqueId() {
        return entityUniqueId;
    }
    
    public void setEntityUniqueId(Long entityUniqueId) {
        this.entityUniqueId = entityUniqueId;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getGuid() {
        return guid;
    }
    
    public void setGuid(String guid) {
        this.guid = guid;
    }
    
    public String getUlid() {
        return ulid;
    }

    public void setUlid(String ulid) {
        this.ulid = ulid;
    }

    public String getEntityRef() {
        if(entityRef == null) {
            entityRef = new StringBuilder(entityType.getComponentVendor().getComponentVendorName()).append('.').append(entityType.getEntityTypeName()).append('.').append(entityUniqueId).toString();
        }
        
        return entityRef;
    }
    
    public void setEntityRef(String entityRef) {
        this.entityRef = entityRef;
    }
    
    public EntityTimeTransfer getEntityTime() {
        return entityTime;
    }
    
    public void setEntityTime(EntityTimeTransfer entityTime) {
        this.entityTime = entityTime;
    }
    
    public String getDescription() {
        if(description == null) {
            description = new StringBuilder(entityType.getDescription()).append('-').append(entityUniqueId).toString();
        }
        
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public EntityAppearanceTransfer getEntityAppearance() {
        return entityAppearance;
    }
    
    public void setEntityAppearance(EntityAppearanceTransfer entityAppearance) {
        this.entityAppearance = entityAppearance;
    }
    
    public EntityNames getEntityNames() {
        return entityNames;
    }
    
    public void setEntityNames(EntityNames entityNames) {
        this.entityNames = entityNames;
    }
    
}
