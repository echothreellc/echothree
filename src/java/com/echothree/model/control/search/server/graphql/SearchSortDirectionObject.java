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
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.search.server.entity.SearchSortDirection;
import com.echothree.model.data.search.server.entity.SearchSortDirectionDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("search sort direction object")
@GraphQLName("SearchSortDirection")
public class SearchSortDirectionObject
        extends BaseEntityInstanceObject {

    private final SearchSortDirection searchSortDirection; // Always Present

    public SearchSortDirectionObject(SearchSortDirection searchSortDirection) {
        super(searchSortDirection.getPrimaryKey());
        
        this.searchSortDirection = searchSortDirection;
    }

    private SearchSortDirectionDetail searchSortDirectionDetail; // Optional, use getSearchSortDirectionDetail()
    
    private SearchSortDirectionDetail getSearchSortDirectionDetail() {
        if(searchSortDirectionDetail == null) {
            searchSortDirectionDetail = searchSortDirection.getLastDetail();
        }
        
        return searchSortDirectionDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("search sort direction name")
    @GraphQLNonNull
    public String getSearchSortDirectionName() {
        return getSearchSortDirectionDetail().getSearchSortDirectionName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSearchSortDirectionDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSearchSortDirectionDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var searchControl = Session.getModelController(SearchControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return searchControl.getBestSearchSortDirectionDescription(searchSortDirection, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
