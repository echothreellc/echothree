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

import com.echothree.util.common.persistence.EntityNames;
import com.echothree.util.common.transfer.BaseTransfer;
import java.util.Objects;

public final class EntityInstanceTransfer
        extends BaseTransfer {

    private EntityTypeTransfer entityType;
    private Long entityUniqueId;
    private String uuid;
    private String entityRef;
    private EntityTimeTransfer entityTime;
    private String description;
    
    private EntityAppearanceTransfer entityAppearance;
    private EntityVisitTransfer entityVisit;
    private EntityNames entityNames;

    /** Creates a new instance of EntityInstanceTransfer */
    public EntityInstanceTransfer(EntityTypeTransfer entityType, Long entityUniqueId, String uuid,
            String entityRef, EntityTimeTransfer entityTime, String description) {
        this.entityType = entityType;
        this.entityUniqueId = entityUniqueId;
        this.uuid = uuid;
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
    
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public String getEntityRef() {
        if(entityRef == null) {
            entityRef = entityType.getComponentVendor().getComponentVendorName() + '.' + entityType.getEntityTypeName() + '.' + entityUniqueId;
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
            description = entityType.getDescription() + '-' + entityUniqueId;
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

    public EntityVisitTransfer getEntityVisit() {
        return entityVisit;
    }

    public void setEntityVisit(EntityVisitTransfer entityVisit) {
        this.entityVisit = entityVisit;
    }

    public EntityNames getEntityNames() {
        return entityNames;
    }
    
    public void setEntityNames(EntityNames entityNames) {
        this.entityNames = entityNames;
    }

    /** entityType and entityUniqueId must be present.
     */
    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        var that = (EntityInstanceTransfer) o;
        return entityType.equals(that.entityType) && entityUniqueId.equals(that.entityUniqueId);
    }

    /** entityType and entityUniqueId must be present.
     */
    @Override
    public int hashCode() {
        return Objects.hash(entityType, entityUniqueId);
    }
}
