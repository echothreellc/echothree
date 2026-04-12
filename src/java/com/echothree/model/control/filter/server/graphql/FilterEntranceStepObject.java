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
import com.echothree.model.data.filter.server.entity.FilterEntranceStep;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("filter entrance step object")
@GraphQLName("FilterEntranceStep")
public class FilterEntranceStepObject
        extends BaseObject {

    private final FilterEntranceStep filterEntranceStep; // Always Present

    public FilterEntranceStepObject(FilterEntranceStep filterEntranceStep) {
        this.filterEntranceStep = filterEntranceStep;
    }

    @GraphQLField
    @GraphQLDescription("filter")
    public FilterObject getFilter(final DataFetchingEnvironment env) {
        return FilterSecurityUtils.getHasFilterAccess(env) ? new FilterObject(filterEntranceStep.getFilter()) : null;
    }

    @GraphQLField
    @GraphQLDescription("filter step")
    public FilterStepObject getFilterStep(final DataFetchingEnvironment env) {
        return FilterSecurityUtils.getHasFilterStepAccess(env) ? new FilterStepObject(filterEntranceStep.getFilterStep()) : null;
    }

}
