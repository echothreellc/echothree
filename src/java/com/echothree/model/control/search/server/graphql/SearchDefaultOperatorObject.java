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
import com.echothree.model.data.search.server.entity.SearchDefaultOperator;
import com.echothree.model.data.search.server.entity.SearchDefaultOperatorDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("search default operator object")
@GraphQLName("SearchDefaultOperator")
public class SearchDefaultOperatorObject
        extends BaseEntityInstanceObject {

    private final SearchDefaultOperator searchDefaultOperator; // Always Present

    public SearchDefaultOperatorObject(SearchDefaultOperator searchDefaultOperator) {
        super(searchDefaultOperator.getPrimaryKey());
        
        this.searchDefaultOperator = searchDefaultOperator;
    }

    private SearchDefaultOperatorDetail searchDefaultOperatorDetail; // Optional, use getSearchDefaultOperatorDetail()
    
    private SearchDefaultOperatorDetail getSearchDefaultOperatorDetail() {
        if(searchDefaultOperatorDetail == null) {
            searchDefaultOperatorDetail = searchDefaultOperator.getLastDetail();
        }
        
        return searchDefaultOperatorDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("search default operator name")
    @GraphQLNonNull
    public String getSearchDefaultOperatorName() {
        return getSearchDefaultOperatorDetail().getSearchDefaultOperatorName();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSearchDefaultOperatorDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSearchDefaultOperatorDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var searchControl = Session.getModelController(SearchControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return searchControl.getBestSearchDefaultOperatorDescription(searchDefaultOperator, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
