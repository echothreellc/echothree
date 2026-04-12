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

import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.data.filter.server.entity.FilterStepDestination;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("filter step destination object")
@GraphQLName("FilterStepDestination")
public class FilterStepDestinationObject
        extends BaseObject {

    private final FilterStepDestination filterStepDestination; // Always Present

    public FilterStepDestinationObject(FilterStepDestination filterStepDestination) {
        this.filterStepDestination = filterStepDestination;
    }

    @GraphQLField
    @GraphQLDescription("from filter step")
    public FilterStepObject getFromFilterStep(final DataFetchingEnvironment env) {
        return FilterSecurityUtils.getHasFilterStepAccess(env) ? new FilterStepObject(filterStepDestination.getFromFilterStep()) : null;
    }

    @GraphQLField
    @GraphQLDescription("to filter step")
    public FilterStepObject getToFilterStep(final DataFetchingEnvironment env) {
        return FilterSecurityUtils.getHasFilterStepAccess(env) ? new FilterStepObject(filterStepDestination.getToFilterStep()) : null;
    }

}
