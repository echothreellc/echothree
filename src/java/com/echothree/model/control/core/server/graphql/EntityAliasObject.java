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

import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.graphql.server.graphql.HistoryInterface;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.data.core.common.EntityAliasConstants;
import com.echothree.model.data.core.server.entity.EntityAlias;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;

@GraphQLDescription("entity alias object")
@GraphQLName("EntityAlias")
public class EntityAliasObject
        extends BaseEntityAliasObject
        implements AttributeInterface, HistoryInterface<EntityAliasHistoryObject> {

    public EntityAliasObject(EntityAlias entityAlias) {
        super(entityAlias);
    }

    @Override
    @GraphQLField
    @GraphQLDescription("history")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<EntityAliasHistoryObject> getHistory(final DataFetchingEnvironment env) {
        if(true) { // TODO: Security Check
            var entityAliasControl = Session.getModelController(EntityAliasControl.class);
            var entityInstance = entityAlias.getEntityInstance();
            var entityAliasType = entityAlias.getEntityAliasType();
            var totalCount = entityAliasControl.countEntityAliasHistory(entityInstance, entityAliasType);

            try(var objectLimiter = new ObjectLimiter(env, EntityAliasConstants.COMPONENT_VENDOR_NAME, EntityAliasConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = entityAliasControl.getEntityAliasHistory(entityInstance, entityAliasType);
                var entityObjects = new ArrayList<EntityAliasHistoryObject>(entities.size());

                for(var entity : entities) {
                    var entityObject = new EntityAliasHistoryObject(entity);

                    entityObjects.add(entityObject);
                }

                return new CountedObjects<>(objectLimiter, entityObjects);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
