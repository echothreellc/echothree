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

package com.echothree.model.control.search.server.graphql;

import com.echothree.control.user.search.common.form.GetItemResultsForm;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.graphql.ItemObject;
import com.echothree.model.control.search.common.SearchKinds;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("item results object")
@GraphQLName("ItemResults")
public class ItemResultsObject
        extends BaseResultsObject<GetItemResultsForm> {

    public ItemResultsObject(GetItemResultsForm form) {
        super(ComponentVendors.ECHO_THREE.name(), EntityTypes.Item.name(), SearchKinds.ITEM.name(), form);
    }

    @GraphQLField
    @GraphQLDescription("items")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<ItemObject> getItems(final DataFetchingEnvironment env) {
        var userVisitSearch = getUserVisitSearch(env);

        if(userVisitSearch == null) {
            return Connections.emptyConnection();
        } else {
            var totalCount = getTotalCount(env);

            try(var objectLimiter = new ObjectLimiter(env, SearchResultConstants.COMPONENT_VENDOR_NAME, SearchResultConstants.ENTITY_TYPE_NAME, totalCount)) {
                var itemControl = Session.getModelController(ItemControl.class);
                var items = itemControl.getItemObjectsFromUserVisitSearch(userVisitSearch);

                return new CountedObjects<>(objectLimiter, items);
            }
        }
    }

}
