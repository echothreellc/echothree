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
import com.echothree.model.control.selector.server.graphql.SelectorObject;
import com.echothree.model.control.selector.server.graphql.SelectorSecurityUtils;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.filter.server.entity.FilterStepElement;
import com.echothree.model.data.filter.server.entity.FilterStepElementDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("filter step element object")
@GraphQLName("FilterStepElement")
public class FilterStepElementObject
        extends BaseEntityInstanceObject {

    private final FilterStepElement filterStepElement; // Always Present

    public FilterStepElementObject(FilterStepElement filterStepElement) {
        super(filterStepElement.getPrimaryKey());
        
        this.filterStepElement = filterStepElement;
    }

    private FilterStepElementDetail filterStepElementDetail; // Optional, use getFilterStepElementDetail()
    
    private FilterStepElementDetail getFilterStepElementDetail() {
        if(filterStepElementDetail == null) {
            filterStepElementDetail = filterStepElement.getLastDetail();
        }
        
        return filterStepElementDetail;
    }

    @GraphQLField
    @GraphQLDescription("filter step")
    public FilterStepObject getFilterStep(final DataFetchingEnvironment env) {
        return FilterSecurityUtils.getHasFilterStepAccess(env) ? new FilterStepObject(getFilterStepElementDetail().getFilterStep()) : null;
    }

    @GraphQLField
    @GraphQLDescription("filter step element name")
    @GraphQLNonNull
    public String getFilterStepElementName() {
        return getFilterStepElementDetail().getFilterStepElementName();
    }

    @GraphQLField
    @GraphQLDescription("filter item selector")
    public SelectorObject getFilterItemSelector(final DataFetchingEnvironment env) {
        if(SelectorSecurityUtils.getHasSelectorAccess(env)) {
            var selector = getFilterStepElementDetail().getFilterItemSelector();

            return selector == null ? null : new SelectorObject(selector);
        } else {
            return null;
        }
    }

    @GraphQLField
    @GraphQLDescription("filter adjustment")
    public FilterAdjustmentObject getFilterAdjustment(final DataFetchingEnvironment env) {
        return FilterSecurityUtils.getHasFilterAdjustmentAccess(env) ? new FilterAdjustmentObject(getFilterStepElementDetail().getFilterAdjustment()) : null;
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var filterControl = Session.getModelController(FilterControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return filterControl.getBestFilterStepElementDescription(filterStepElement, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
