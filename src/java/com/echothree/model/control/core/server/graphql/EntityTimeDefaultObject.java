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
import com.echothree.model.control.graphql.server.graphql.HistoryInterface;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.data.core.common.EntityTimeDefaultConstants;
import com.echothree.model.data.core.server.entity.EntityTimeDefault;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;

@GraphQLDescription("entity time default object")
@GraphQLName("EntityTimeDefault")
public class EntityTimeDefaultObject
        extends BaseEntityTimeDefaultObject
        implements DefaultInterface, HistoryInterface<EntityTimeDefaultHistoryObject> {

    public EntityTimeDefaultObject(EntityTimeDefault entityTimeDefault) {
        super(entityTimeDefault);
    }

    @Override
    @GraphQLField
    @GraphQLDescription("history")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<EntityTimeDefaultHistoryObject> getHistory(final DataFetchingEnvironment env) {
        if(true) { // TODO: Security Check
            var coreControl = Session.getModelController(CoreControl.class);
            var entityAttribute = entityTimeDefault.getEntityAttribute();
            var totalCount = coreControl.countEntityTimeDefaultHistory(entityAttribute);

            try(var objectLimiter = new ObjectLimiter(env, EntityTimeDefaultConstants.COMPONENT_VENDOR_NAME, EntityTimeDefaultConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = coreControl.getEntityTimeDefaultHistory(entityAttribute);
                var entityObjects = new ArrayList<EntityTimeDefaultHistoryObject>(entities.size());

                for(var entity : entities) {
                    var entityObject = new EntityTimeDefaultHistoryObject(entity);

                    entityObjects.add(entityObject);
                }

                return new CountedObjects<>(objectLimiter, entityObjects);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
