// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityLockControl;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.server.persistence.EntityDescriptionUtils;
import com.echothree.util.server.persistence.EntityNamesUtils;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.translator.EntityInstanceAndNames;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("entity instance object")
@GraphQLName("EntityInstance")
public class EntityInstanceObject
        extends BaseGraphQl {
    
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
        var coreControl = Session.getModelController(CoreControl.class);

        entityInstance = coreControl.ensureKeyForEntityInstance(entityInstance, false);

        return entityInstance.getKey();
    }
    
    @GraphQLField
    @GraphQLDescription("guid")
    @GraphQLNonNull
    public String getGuid() {
        var coreControl = Session.getModelController(CoreControl.class);

        entityInstance = coreControl.ensureGuidForEntityInstance(entityInstance, false);

        return entityInstance.getGuid();
    }
    
    @GraphQLField
    @GraphQLDescription("ulid")
    @GraphQLNonNull
    public String getUlid() {
        var coreControl = Session.getModelController(CoreControl.class);

        entityInstance = coreControl.ensureUlidForEntityInstance(entityInstance, false);

        return entityInstance.getUlid();
    }
    
    @GraphQLField
    @GraphQLDescription("entity time")
    public EntityTimeObject getEntityTime() {
        var coreControl = Session.getModelController(CoreControl.class);
        var entityTime = coreControl.getEntityTime(entityInstance);
        
        return entityTime == null ? null : new EntityTimeObject(entityTime);
    }
    
    @GraphQLField
    @GraphQLDescription("entity visit")
    public EntityVisitObject getEntityVisit(final DataFetchingEnvironment env) {
        var coreControl = Session.getModelController(CoreControl.class);
        var userSession = getUserSession(env);
        var visitingEntityInstance = userSession == null ? null : coreControl.getEntityInstanceByBasePK(userSession.getPartyPK());
        var entityVisit = visitingEntityInstance == null ? null : coreControl.getEntityVisit(visitingEntityInstance, entityInstance);
        
        return entityVisit == null ? null : new EntityVisitObject(entityVisit);
    }
    
    @GraphQLField
    @GraphQLDescription("entity appearance")
    public EntityAppearanceObject getEntityAppearance() {
        var coreControl = Session.getModelController(CoreControl.class);
        var entityAppearance = coreControl.getEntityAppearance(entityInstance);
        
        return entityAppearance == null ? null : new EntityAppearanceObject(entityAppearance);
    }

    @GraphQLField
    @GraphQLDescription("entity lock")
    public EntityLockObject getEntityLock(final DataFetchingEnvironment env) {
        var entityLockControl = Session.getModelController(EntityLockControl.class);
        var userVisit = getUserVisit(env);
        var entityLockTransfer = entityLockControl.getEntityLockTransferByEntityInstance(userVisit, entityInstance);

        return entityLockTransfer == null ? null : new EntityLockObject(entityLockTransfer);
    }

    @GraphQLField
    @GraphQLDescription("entity names")
    public EntityNamesObject getEntityNames(final DataFetchingEnvironment env) {
        var entityNamesMapping = EntityNamesUtils.getInstance().getEntityNames(entityInstance);

        return entityNamesMapping == null ? null : new EntityNamesObject(entityNamesMapping.getEntityNames());
    }

    @GraphQLField
    @GraphQLDescription("description")
    public String getDescription(final DataFetchingEnvironment env) {
        var userVisit = getUserVisit(env);

        return EntityDescriptionUtils.getInstance().getDescription(userVisit, entityInstance);
    }

}
