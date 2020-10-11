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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.core.common.transfer.EntityLockTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityLockControl;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.data.core.server.entity.EntityAppearance;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.model.data.core.server.entity.EntityVisit;
import com.echothree.model.data.user.server.entity.UserSession;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import graphql.annotations.annotationTypes.GraphQLNonNull;

@GraphQLDescription("entity instance object")
@GraphQLName("EntityInstance")
public class EntityInstanceObject {
    
    private EntityInstance entityInstance; // Always Present
    
    public EntityInstanceObject(EntityInstance entityInstance) {
        this.entityInstance = entityInstance;
    }
    
    @GraphQLField
    @GraphQLDescription("entity type")
    @GraphQLNonNull
    public EntityTypeObject getEntityType() {
        return new EntityTypeObject(entityInstance.getEntityType());
    }
    
    @GraphQLField
    @GraphQLDescription("entity unique id")
    @GraphQLNonNull
    public Long getEntityUniqueId() {
        return entityInstance.getEntityUniqueId();
    }

    @GraphQLField
    @GraphQLDescription("key")
    @GraphQLNonNull
    public String getKey() {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);

        entityInstance = coreControl.ensureKeyForEntityInstance(entityInstance, false);

        return entityInstance.getKey();
    }
    
    @GraphQLField
    @GraphQLDescription("guid")
    @GraphQLNonNull
    public String getGuid() {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);

        entityInstance = coreControl.ensureGuidForEntityInstance(entityInstance, false);

        return entityInstance.getGuid();
    }
    
    @GraphQLField
    @GraphQLDescription("ulid")
    @GraphQLNonNull
    public String getUlid() {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);

        entityInstance = coreControl.ensureUlidForEntityInstance(entityInstance, false);

        return entityInstance.getUlid();
    }
    
    @GraphQLField
    @GraphQLDescription("entity time")
    public EntityTimeObject getEntityTime() {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        EntityTime entityTime = coreControl.getEntityTime(entityInstance);
        
        return entityTime == null ? null : new EntityTimeObject(entityTime);
    }
    
    @GraphQLField
    @GraphQLDescription("entity visit")
    public EntityVisitObject getEntityVisit(final DataFetchingEnvironment env) {
        GraphQlContext context = env.getContext();
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        UserSession userSession = context.getUserSession();
        EntityInstance visitingEntityInstance = userSession == null ? null : coreControl.getEntityInstanceByBasePK(userSession.getPartyPK());
        EntityVisit entityVisit = visitingEntityInstance == null ? null : coreControl.getEntityVisit(visitingEntityInstance, entityInstance);
        
        return entityVisit == null ? null : new EntityVisitObject(entityVisit);
    }
    
    @GraphQLField
    @GraphQLDescription("entity appearance")
    public EntityAppearanceObject getEntityAppearance() {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        EntityAppearance entityAppearance = coreControl.getEntityAppearance(entityInstance);
        
        return entityAppearance == null ? null : new EntityAppearanceObject(entityAppearance);
    }

    @GraphQLField
    @GraphQLDescription("entity lock")
    public EntityLockObject getEntityLock(final DataFetchingEnvironment env) {
        GraphQlContext context = env.getContext();
        var entityLockControl = (EntityLockControl)Session.getModelController(EntityLockControl.class);
        UserVisit userVisit = context.getUserVisit();
        EntityLockTransfer entityLockTransfer = entityLockControl.getEntityLockTransferByEntityInstance(userVisit, entityInstance);
        
        return entityLockTransfer == null ? null : new EntityLockObject(entityLockTransfer);
    }

}
