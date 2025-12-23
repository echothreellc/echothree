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
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentSource;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("filter adjustment source object")
@GraphQLName("FilterAdjustmentSource")
public class FilterAdjustmentSourceObject
        implements BaseGraphQl {
    
    private final FilterAdjustmentSource filterAdjustmentSource; // Always Present
    
    public FilterAdjustmentSourceObject(FilterAdjustmentSource filterAdjustmentSource) {
        this.filterAdjustmentSource = filterAdjustmentSource;
    }

    @GraphQLField
    @GraphQLDescription("filter adjustment source name")
    @GraphQLNonNull
    public String getFilterAdjustmentSourceName() {
        return filterAdjustmentSource.getFilterAdjustmentSourceName();
    }

    @GraphQLField
    @GraphQLDescription("allowed for initial amount")
    @GraphQLNonNull
    public boolean getAllowedForInitialAmount() {
        return filterAdjustmentSource.getAllowedForInitialAmount();
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return filterAdjustmentSource.getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return filterAdjustmentSource.getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var filterControl = Session.getModelController(FilterControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return filterControl.getBestFilterAdjustmentSourceDescription(filterAdjustmentSource, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
