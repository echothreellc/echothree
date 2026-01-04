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

package com.echothree.model.control.core.server.graphql;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.tag.server.graphql.TagScopeEntityTypeObject;
import com.echothree.model.control.tag.server.graphql.TagSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.common.EntityAttributeConstants;
import com.echothree.model.data.core.common.EntityInstanceConstants;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.tag.common.TagScopeEntityTypeConstants;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("entity type object")
@GraphQLName("EntityType")
public class EntityTypeObject
        extends BaseEntityInstanceObject {
    
    private final EntityType entityType; // Always Present
    
    public EntityTypeObject(EntityType entityType) {
        super(entityType.getPrimaryKey());
        
        this.entityType = entityType;
    }

    private EntityTypeDetail entityTypeDetail; // Optional, use getEntityTypeDetail()
    
    private EntityTypeDetail getEntityTypeDetail() {
        if(entityTypeDetail == null) {
            entityTypeDetail = entityType.getLastDetail();
        }
        
        return entityTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("entity type name")
    @GraphQLNonNull
    public String getEntityTypeName() {
        return getEntityTypeDetail().getEntityTypeName();
    }
    
    @GraphQLField
    @GraphQLDescription("component vendor")
    @GraphQLNonNull
    public ComponentVendorObject getComponentVendor() {
        return new ComponentVendorObject(getEntityTypeDetail().getComponentVendor());
    }
    
    @GraphQLField
    @GraphQLDescription("keep all history")
    @GraphQLNonNull
    public boolean getKeepAllHistory() {
        return getEntityTypeDetail().getKeepAllHistory();
    }
    
    @GraphQLField
    @GraphQLDescription("lock timeout")
    public Long getLockTimeout() {
        return getEntityTypeDetail().getLockTimeout();
    }

    @GraphQLField
    @GraphQLDescription("is extensible")
    @GraphQLNonNull
    public boolean getIsExtensible() {
        return getEntityTypeDetail().getIsExtensible();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getEntityTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var entityTypeControl = Session.getModelController(EntityTypeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return entityTypeControl.getBestEntityTypeDescription(entityType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("entity instances")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<EntityInstanceObject> getEntityInstances(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasEntityInstancesAccess(env)) {
            var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
            var totalCount = entityInstanceControl.countEntityInstancesByEntityType(entityType);

            try(var objectLimiter = new ObjectLimiter(env, EntityInstanceConstants.COMPONENT_VENDOR_NAME, EntityInstanceConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = entityInstanceControl.getEntityInstancesByEntityType(entityType);
                var entityInstances = entities.stream().map(EntityInstanceObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, entityInstances);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("entity attributes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<EntityAttributeObject> getEntityAttributes(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasEntityAttributesAccess(env)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var totalCount = coreControl.countEntityAttributesByEntityType(entityType);

            try(var objectLimiter = new ObjectLimiter(env, EntityAttributeConstants.COMPONENT_VENDOR_NAME, EntityAttributeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = coreControl.getEntityAttributesByEntityType(entityType);
                var entityAttributes = new ArrayList<EntityAttributeObject>(entities.size());

                for(var entity : entities) {
                    entityAttributes.add(new EntityAttributeObject(entity, null));
                }

                return new CountedObjects<>(objectLimiter, entityAttributes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("tag scope entity types")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<TagScopeEntityTypeObject> getTagScopeEntityTypes(final DataFetchingEnvironment env) {
        if(TagSecurityUtils.getHasTagScopeEntityTypesAccess(env)) {
            var tagControl = Session.getModelController(TagControl.class);
            var totalCount = tagControl.countTagScopeEntityTypesByEntityType(entityType);

            try(var objectLimiter = new ObjectLimiter(env, TagScopeEntityTypeConstants.COMPONENT_VENDOR_NAME, TagScopeEntityTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = tagControl.getTagScopeEntityTypesByEntityType(entityType);
                var entityTypes = entities.stream().map(TagScopeEntityTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, entityTypes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
