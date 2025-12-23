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

package com.echothree.model.control.graphql.server.graphql;

import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.util.server.string.PercentUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("percent object")
@GraphQLName("Percent")
public class PercentObject
        implements BaseGraphQl {

    private Integer unformattedPercent; // Always Present

    public PercentObject(Integer unformattedPercent) {
        this.unformattedPercent = unformattedPercent;
    }

    @GraphQLField
    @GraphQLDescription("unformatted percent")
    @GraphQLNonNull
    public Integer getUnformattedPercent() {
        return unformattedPercent;
    }

    @GraphQLField
    @GraphQLDescription("percent")
    @GraphQLNonNull
    public String getPercent(final DataFetchingEnvironment env) {
        return PercentUtils.getInstance().formatFractionalPercent(unformattedPercent);
    }

}
