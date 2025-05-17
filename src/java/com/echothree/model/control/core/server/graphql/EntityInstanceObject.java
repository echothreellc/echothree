// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.EntityLockControl;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.data.core.common.EventConstants;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.server.persistence.EntityDescriptionUtils;
import com.echothree.util.server.persistence.EntityNamesUtils;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;

@GraphQLDescription("entity instance object")
@GraphQLName("EntityInstance")
public class EntityInstanceObject
        extends BaseEntityInstanceObject {
    
    private EntityInstance entityInstance; // Always Present
    
    public EntityInstanceObject(EntityInstance entityInstance) {
        super(entityInstance);

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
    @GraphQLDescription("uuid")
    @GraphQLNonNull
    public String getUuid() {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

        entityInstance = entityInstanceControl.ensureUuidForEntityInstance(entityInstance, false);

        return entityInstance.getUuid();
    }

    @GraphQLField
    @GraphQLDescription("entity ref")
    @GraphQLNonNull
    public String getEntityRef() {
        var entityTypeDetail = entityInstance.getEntityType().getLastDetail();

        return entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName() +
                '.' + entityTypeDetail.getEntityTypeName() +
                '.' + entityInstance.getEntityUniqueId();
    }

    @GraphQLField
    @GraphQLDescription("entity time")
    public EntityTimeObject getEntityTime() {
        var eventControl = Session.getModelController(EventControl.class);
        var entityTime = eventControl.getEntityTime(entityInstance);
        
        return entityTime == null ? null : new EntityTimeObject(entityTime);
    }
    
    @GraphQLField
    @GraphQLDescription("entity visit")
    public EntityVisitObject getEntityVisit(final DataFetchingEnvironment env) {
        var eventControl = Session.getModelController(EventControl.class);
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var userSession = BaseGraphQl.getUserSession(env);
        var visitingEntityInstance = userSession == null ? null : entityInstanceControl.getEntityInstanceByBasePK(userSession.getPartyPK());
        var entityVisit = visitingEntityInstance == null ? null : eventControl.getEntityVisit(visitingEntityInstance, entityInstance);
        
        return entityVisit == null ? null : new EntityVisitObject(entityVisit);
    }
    
    @GraphQLField
    @GraphQLDescription("entity appearance")
    public EntityAppearanceObject getEntityAppearance() {
        var appearanceControl = Session.getModelController(AppearanceControl.class);
        var entityAppearance = appearanceControl.getEntityAppearance(entityInstance);
        
        return entityAppearance == null ? null : new EntityAppearanceObject(entityAppearance);
    }

    @GraphQLField
    @GraphQLDescription("entity lock")
    public EntityLockObject getEntityLock(final DataFetchingEnvironment env) {
        var entityLockControl = Session.getModelController(EntityLockControl.class);
        var userVisit = BaseGraphQl.getUserVisit(env);
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
        var userVisit = BaseGraphQl.getUserVisit(env);

        return EntityDescriptionUtils.getInstance().getDescription(userVisit, entityInstance);
    }

    @GraphQLField
    @GraphQLDescription("events")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<EventObject> getEvents(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasEventsAccess(env)) {
            var eventControl = Session.getModelController(EventControl.class);
            var totalCount = eventControl.countEventsByEntityInstance(getEntityInstanceByBasePK());

            try(var objectLimiter = new ObjectLimiter(env, EventConstants.COMPONENT_VENDOR_NAME, EventConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = eventControl.getEventsByEntityInstance(getEntityInstanceByBasePK());
                var events = new ArrayList<EventObject>(entities.size());

                for(var entity : entities) {
                    var eventobject = new EventObject(entity);

                    events.add(eventobject);
                }

                return new CountedObjects<>(objectLimiter, events);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
