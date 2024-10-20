// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.model.data.search.server.entity.SearchKind;
import com.echothree.model.data.search.server.entity.SearchKindDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

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
    
}
