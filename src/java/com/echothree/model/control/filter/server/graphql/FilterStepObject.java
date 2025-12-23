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

package com.echothree.model.control.filter.server.graphql;

import com.echothree.model.control.filter.server.control.FilterControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("filterStep object")
@GraphQLName("FilterStep")
public class FilterStepObject
        extends BaseEntityInstanceObject {
    
    private final FilterStep filterStep; // Always Present
    
    public FilterStepObject(FilterStep filterStep) {
        super(filterStep.getPrimaryKey());
        
        this.filterStep = filterStep;
    }

    private FilterStepDetail filterStepDetail; // Optional, use getFilterStepDetail()
    
    private FilterStepDetail getFilterStepDetail() {
        if(filterStepDetail == null) {
            filterStepDetail = filterStep.getLastDetail();
        }
        
        return filterStepDetail;
    }

    @GraphQLField
    @GraphQLDescription("filterStep type")
    public FilterObject getFilter(final DataFetchingEnvironment env) {
        return FilterSecurityUtils.getHasFilterAccess(env) ? new FilterObject(getFilterStepDetail().getFilter()) : null;
    }

    @GraphQLField
    @GraphQLDescription("filter step name")
    @GraphQLNonNull
    public String getFilterStepName() {
        return getFilterStepDetail().getFilterStepName();
    }

//    @GraphQLField
//    @GraphQLDescription("filter item selector")
//    public SelectorObject getFilterItemSelector(final DataFetchingEnvironment env) {
//        SelectorObject result;
//
//        if(SelectorSecurityUtils.getHasSelectorAccess(env)) {
//            var selector = getFilterStepDetail().getFilterItemSelector();
//
//            result = selector == null ? null : new SelectorObject(selector);
//        } else {
//            result = null;
//        }
//
//        return result;
//    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var filterControl = Session.getModelController(FilterControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return filterControl.getBestFilterStepDescription(filterStep, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
