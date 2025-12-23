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
import com.echothree.model.data.search.server.entity.SearchResultActionType;
import com.echothree.model.data.search.server.entity.SearchResultActionTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("search result action type object")
@GraphQLName("SearchResultActionType")
public class SearchResultActionTypeObject
        extends BaseEntityInstanceObject {
    
    private final SearchResultActionType searchResultActionType; // Always Present
    
    public SearchResultActionTypeObject(SearchResultActionType searchResultActionType) {
        super(searchResultActionType.getPrimaryKey());
        
        this.searchResultActionType = searchResultActionType;
    }

    private SearchResultActionTypeDetail searchResultActionTypeDetail; // Optional, use getSearchResultActionTypeDetail()
    
    private SearchResultActionTypeDetail getSearchResultActionTypeDetail() {
        if(searchResultActionTypeDetail == null) {
            searchResultActionTypeDetail = searchResultActionType.getLastDetail();
        }
        
        return searchResultActionTypeDetail;
    }

    @GraphQLField
    @GraphQLDescription("search result action type name")
    @GraphQLNonNull
    public String getSearchResultActionTypeName() {
        return getSearchResultActionTypeDetail().getSearchResultActionTypeName();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSearchResultActionTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSearchResultActionTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var searchControl = Session.getModelController(SearchControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return searchControl.getBestSearchResultActionTypeDescription(searchResultActionType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
