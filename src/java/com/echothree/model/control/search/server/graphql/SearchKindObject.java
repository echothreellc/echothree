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

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.search.common.SearchSortOrderConstants;
import com.echothree.model.data.search.common.SearchTypeConstants;
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchKindDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("search kind object")
@GraphQLName("SearchKind")
public class SearchKindObject
        extends BaseEntityInstanceObject {

    private final SearchKind searchKind; // Always Present

    public SearchKindObject(SearchKind searchKind) {
        super(searchKind.getPrimaryKey());
        
        this.searchKind = searchKind;
    }

    private SearchKindDetail searchKindDetail; // Optional, use getSearchKindDetail()
    
    private SearchKindDetail getSearchKindDetail() {
        if(searchKindDetail == null) {
            searchKindDetail = searchKind.getLastDetail();
        }
        
        return searchKindDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("search kind name")
    @GraphQLNonNull
    public String getSearchKindName() {
        return getSearchKindDetail().getSearchKindName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSearchKindDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSearchKindDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var searchControl = Session.getModelController(SearchControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return searchControl.getBestSearchKindDescription(searchKind, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("search types")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<SearchTypeObject> getSearchTypes(final DataFetchingEnvironment env) {
        if(SearchSecurityUtils.getHasSearchTypesAccess(env)) {
            var searchControl = Session.getModelController(SearchControl.class);
            var totalCount = searchControl.countSearchTypesBySearchKind(searchKind);

            try(var objectLimiter = new ObjectLimiter(env, SearchTypeConstants.COMPONENT_VENDOR_NAME, SearchTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = searchControl.getSearchTypes(searchKind);
                var searchTypes = entities.stream().map(SearchTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, searchTypes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("search sort orders")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<SearchSortOrderObject> getSearchSortOrders(final DataFetchingEnvironment env) {
        if(SearchSecurityUtils.getHasSearchSortOrdersAccess(env)) {
            var searchControl = Session.getModelController(SearchControl.class);
            var totalCount = searchControl.countSearchSortOrdersBySearchKind(searchKind);

            try(var objectLimiter = new ObjectLimiter(env, SearchSortOrderConstants.COMPONENT_VENDOR_NAME, SearchSortOrderConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = searchControl.getSearchSortOrders(searchKind);
                var searchSortOrders = entities.stream().map(SearchSortOrderObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, searchSortOrders);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
