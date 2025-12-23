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
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.core.common.EntityAttributeConstants;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroupDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.Collection;

@GraphQLDescription("entity attribute group object")
@GraphQLName("EntityAttributeGroup")
public class EntityAttributeGroupObject
        extends BaseEntityInstanceObject {
    
    private final EntityAttributeGroup entityAttributeGroup; // Always Present
    private final EntityInstance entityInstance;
    
    public EntityAttributeGroupObject(EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        super(entityAttributeGroup.getPrimaryKey());

        this.entityAttributeGroup = entityAttributeGroup;
        this.entityInstance = entityInstance;
    }

    private EntityAttributeGroupDetail entityAttributeGroupDetail; // Optional, use getEntityAttributeGroupDetail()
    
    private EntityAttributeGroupDetail getEntityAttributeGroupDetail() {
        if(entityAttributeGroupDetail == null) {
            entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();
        }
        
        return entityAttributeGroupDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("entity attribute group name")
    @GraphQLNonNull
    public String getEntityAttributeGroupName() {
        return getEntityAttributeGroupDetail().getEntityAttributeGroupName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getEntityAttributeGroupDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getEntityAttributeGroupDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var coreControl = Session.getModelController(CoreControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return coreControl.getBestEntityAttributeGroupDescription(entityAttributeGroup, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }


    @GraphQLField
    @GraphQLDescription("entity attributes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<EntityAttributeObject> getEntityAttributes(final DataFetchingEnvironment env) {
//      if(CoreSecurityUtils.getHasEntityAttributesAccess(env)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityType = entityInstance == null ? null : entityInstance.getEntityType();

            long totalCount;
            if(entityType == null) {
                totalCount = coreControl.countEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup);
            } else {
                totalCount = coreControl.countEntityAttributesByEntityAttributeGroupAndEntityType(entityAttributeGroup, entityType);
            }

            try(var objectLimiter = new ObjectLimiter(env, EntityAttributeConstants.COMPONENT_VENDOR_NAME, EntityAttributeConstants.ENTITY_TYPE_NAME, totalCount)) {
                Collection<EntityAttribute> entities;
                if(entityType == null) {
                    var entityAttributeGroupEntityAttributes = coreControl.getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup);

                    entities = new ArrayList<>(entityAttributeGroupEntityAttributes.size());
                    entityAttributeGroupEntityAttributes.forEach(eagea -> entities.add(eagea.getEntityAttribute()));
                } else {
                    entities = coreControl.getEntityAttributesByEntityAttributeGroupAndEntityType(entityAttributeGroup, entityType);
                }

                var entityAttributes = new ArrayList<EntityAttributeObject>(entities.size());

                entities.forEach((entity) -> entityAttributes.add(new EntityAttributeObject(entity, entityInstance)));

                return new CountedObjects<>(objectLimiter, entityAttributes);
            }
//      } else {
//          return Connections.emptyConnection();
//      }
    }

    @GraphQLField
    @GraphQLDescription("entity attribute entity attribute groups")
    public Collection<EntityAttributeEntityAttributeGroupObject> getEntityAttributeEntityAttributeGroups(final DataFetchingEnvironment env) {
        Collection<EntityAttributeEntityAttributeGroupObject> entityAttributeEntityAttributeGroupObjects = null;

        if(CoreSecurityUtils.getHasEntityAttributeEntityAttributeGroupsAccess(env)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityAttributeEntityAttributeGroups = coreControl.getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup);

            entityAttributeEntityAttributeGroupObjects = new ArrayList<>(entityAttributeEntityAttributeGroups.size());

            for(var entityAttributeEntityAttributeGroup : entityAttributeEntityAttributeGroups) {
                entityAttributeEntityAttributeGroupObjects.add(new EntityAttributeEntityAttributeGroupObject(entityAttributeEntityAttributeGroup));
            }
        }

        return entityAttributeEntityAttributeGroupObjects;
    }

}
