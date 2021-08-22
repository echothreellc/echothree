// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.search.common.form.BaseGetResultsForm;
import com.echothree.control.user.search.common.form.GetVendorResultsForm;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.search.common.SearchConstants;
import com.echothree.model.control.search.common.exception.BaseSearchException;
import com.echothree.model.control.search.server.logic.SearchLogic;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;
import java.util.Collections;
import java.util.List;

public abstract class BaseResultsObject<F extends BaseGetResultsForm> {

    private String searchKindName;

    private F form;

    protected BaseResultsObject(String searchKindName) {
        this.searchKindName = searchKindName;
    }

    public void setForm(F form) {
        this.form = form;
    }

    private UserVisitSearch userVisitSearch;

    protected UserVisitSearch getUserVisitSearch(final DataFetchingEnvironment env) {
        if(form != null && userVisitSearch == null) {
            try {
                GraphQlContext context = env.getContext();
                UserVisit userVisit = context.getUserVisit();
                
                userVisitSearch = SearchLogic.getInstance().getUserVisitSearchByName(null, userVisit,
                        searchKindName, form.getSearchTypeName());
            } catch (BaseSearchException bse) {
                // Leave userVisitSearch null.
            }
        }
        
        return userVisitSearch;
    }

    @GraphQLField
    @GraphQLDescription("count")
    @GraphQLNonNull
    public int getCount(final DataFetchingEnvironment env) {
        UserVisitSearch userVisitSearch = getUserVisitSearch(env);

        return userVisitSearch == null ? 0 : SearchLogic.getInstance().countSearchResults(userVisitSearch.getSearch());
    }

}