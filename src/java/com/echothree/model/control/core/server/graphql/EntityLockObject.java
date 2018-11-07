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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

@GraphQLDescription("entity lock object")
@GraphQLName("EntityLock")
public class EntityLockObject {
    
    private final EntityLockTransfer entityLockTransfer; // Always Present
    
    public EntityLockObject(EntityLockTransfer entityLockTransfer) {
        this.entityLockTransfer = entityLockTransfer;
    }

    @GraphQLField
    @GraphQLDescription("lock target entity instance")
    public EntityInstanceObject getLockTargetEntityInstance() {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        EntityInstance entityInstance = coreControl.getEntityInstanceByEntityRef(entityLockTransfer.getLockTargetEntityInstance().getEntityRef());
        
        return new EntityInstanceObject(entityInstance);
    }

    @GraphQLField
    @GraphQLDescription("locked by entity instance")
    public EntityInstanceObject getLockedByEntityInstance() {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        EntityInstance entityInstance = coreControl.getEntityInstanceByEntityRef(entityLockTransfer.getLockedByEntityInstance().getEntityRef());
        
        return new EntityInstanceObject(entityInstance);
    }
    
    @GraphQLField
    @GraphQLDescription("unformatted locked time")
    public Long getUnformattedLockedTime() {
        return entityLockTransfer.getUnformattedLockedTime();
    }

    @GraphQLField
    @GraphQLDescription("unformatted locked time")
    public String getLockedTime() {
        return entityLockTransfer.getLockedTime();
    }

    @GraphQLField
    @GraphQLDescription("unformatted expiration time")
    public Long getUnformattedExpirationTime() {
        return entityLockTransfer.getUnformattedExpirationTime();
    }

    @GraphQLField
    @GraphQLDescription("expiration time")
    public String getExpirationTime() {
        return entityLockTransfer.getExpirationTime();
    }

}
