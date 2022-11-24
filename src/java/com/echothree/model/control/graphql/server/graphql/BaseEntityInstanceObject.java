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

package com.echothree.model.control.graphql.server.graphql;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.graphql.CoreSecurityUtils;
import com.echothree.model.control.core.server.graphql.EntityAttributeGroupObject;
import com.echothree.model.control.core.server.graphql.EntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.offer.server.graphql.OfferItemObject;
import com.echothree.model.control.offer.server.graphql.OfferSecurityUtils;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.control.tag.server.graphql.TagScopeObject;
import com.echothree.model.control.tag.server.graphql.TagSecurityUtils;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.offer.common.OfferItemConstants;
import com.echothree.model.data.tag.common.TagScopeConstants;
import com.echothree.model.data.tag.server.entity.TagScope;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.PersistenceUtils;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLID;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseEntityInstanceObject
        extends BaseObject {

    protected BaseEntityInstanceObject(BasePK basePrimaryKey) {
        super(basePrimaryKey);
    }

    protected BaseEntityInstanceObject(EntityInstance entityInstance) {
        super(PersistenceUtils.getInstance().getBasePKFromEntityInstance(entityInstance));

        this.entityInstance = entityInstance;
    }

    private EntityInstance entityInstance; // Optional, use getEntityInstanceByBasePK()
    
    protected EntityInstance getEntityInstanceByBasePK() {
        if(entityInstance == null) {
            var coreControl = Session.getModelController(CoreControl.class);

            entityInstance = coreControl.getEntityInstanceByBasePK(basePrimaryKey);
        }
        
        return entityInstance;
    }

    @GraphQLField
    @GraphQLDescription("id")
    @GraphQLNonNull
    @GraphQLID
    public String getId() {
        var coreControl = Session.getModelController(CoreControl.class);

        entityInstance = coreControl.ensureUlidForEntityInstance(getEntityInstanceByBasePK(), false);

        return entityInstance.getUlid();
    }
    
    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        // Allow user to see the EntityInstanceObject regardless of permissions for the GetEntityInstance UC.
        return new EntityInstanceObject(getEntityInstanceByBasePK());
    }

    @GraphQLField
    @GraphQLDescription("entity attribute groups")
    public List<EntityAttributeGroupObject> getEntityAttributeGroups() {
        var entityInstance = getEntityInstanceByBasePK();

        if(entityInstance != null) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityType = entityInstance.getEntityType();

            // Allow user to see all EntityAttributeGroups that have Entity Attributes in them
            // for the current object, regardless of permissions for the GetEntityAttributeGroups UC.
            var entities = coreControl.getEntityAttributeGroupsByEntityType(entityType);
            var entityAttributeGroups = new ArrayList<EntityAttributeGroupObject>(entities.size());

            entities.forEach((entity) -> entityAttributeGroups.add(new EntityAttributeGroupObject(entity, entityInstance)));

            return entityAttributeGroups;
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("tag scopes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<TagScopeObject> getTagScopes(final DataFetchingEnvironment env) {
        if(TagSecurityUtils.getInstance().getHasTagScopesAccess(env)) {
            var tagControl = Session.getModelController(TagControl.class);
            var totalCount = tagControl.countTagScopesByEntityType(getEntityInstanceByBasePK().getEntityType());

            try(var objectLimiter = new ObjectLimiter(env, TagScopeConstants.COMPONENT_VENDOR_NAME, TagScopeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = tagControl.getTagScopesByEntityType(getEntityInstanceByBasePK().getEntityType());
                var tagScopes = new ArrayList<TagScopeObject>(entities.size());

                for(var entity : entities) {
                    var tagScopeObject = new TagScopeObject(entity, entityInstance);

                    tagScopes.add(tagScopeObject);
                }

                return new CountedObjects<>(objectLimiter, tagScopes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}
