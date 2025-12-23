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
import com.echothree.model.data.search.server.entity.SearchUseType;
import com.echothree.model.data.search.server.entity.SearchUseTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("search use type object")
@GraphQLName("SearchUseType")
public class SearchUseTypeObject
        extends BaseEntityInstanceObject {

    private final SearchUseType searchUseType; // Always Present

    public SearchUseTypeObject(SearchUseType searchUseType) {
        super(searchUseType.getPrimaryKey());
        
        this.searchUseType = searchUseType;
    }

    private SearchUseTypeDetail searchUseTypeDetail; // Optional, use getSearchUseTypeDetail()
    
    private SearchUseTypeDetail getSearchUseTypeDetail() {
        if(searchUseTypeDetail == null) {
            searchUseTypeDetail = searchUseType.getLastDetail();
        }
        
        return searchUseTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("search use type name")
    @GraphQLNonNull
    public String getSearchUseTypeName() {
        return getSearchUseTypeDetail().getSearchUseTypeName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSearchUseTypeDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSearchUseTypeDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var searchControl = Session.getModelController(SearchControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return searchControl.getBestSearchUseTypeDescription(searchUseType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
