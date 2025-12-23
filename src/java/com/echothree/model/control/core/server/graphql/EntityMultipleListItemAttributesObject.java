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
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.data.core.common.EntityMultipleListItemAttributeConstants;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;

@GraphQLDescription("entity multiple list item attributes object")
@GraphQLName("EntityMultipleListItemAttributes")
public class EntityMultipleListItemAttributesObject
        implements BaseGraphQl, AttributeInterface {

    private final EntityAttribute entityAttribute; // Always Present
    private final EntityInstance entityInstance; // Always Present

    public EntityMultipleListItemAttributesObject(final EntityAttribute entityAttribute, final EntityInstance entityInstance) {
        this.entityAttribute = entityAttribute;
        this.entityInstance = entityInstance;
    }

    @GraphQLField
    @GraphQLDescription("entity attribute")
    public EntityAttributeObject getEntityAttribute(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityAttributeAccess(env) ? new EntityAttributeObject(entityAttribute, entityInstance) : null;
    }

    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityInstanceAccess(env) ? new EntityInstanceObject(entityInstance) : null;
    }

    @GraphQLField
    @GraphQLDescription("entity list items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<EntityListItemObject> getEntityListItems(final DataFetchingEnvironment env) {
        if(CoreSecurityUtils.getHasEntityListItemAccess(env)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var totalCount = coreControl.countEntityMultipleListItemAttributes(entityAttribute, entityInstance);

            try(var objectLimiter = new ObjectLimiter(env, EntityMultipleListItemAttributeConstants.COMPONENT_VENDOR_NAME, EntityMultipleListItemAttributeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = coreControl.getEntityMultipleListItemAttributes(entityAttribute, entityInstance);
                var entityMultipleListItemAttributes = new ArrayList<EntityListItemObject>(entities.size());

                entities.forEach((entity) -> entityMultipleListItemAttributes.add(new EntityListItemObject(entity.getEntityListItem())));

                return new CountedObjects<>(objectLimiter, entityMultipleListItemAttributes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}
