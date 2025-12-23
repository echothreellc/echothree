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

package com.echothree.model.control.term.server.graphql;

import com.echothree.model.control.graphql.server.graphql.PercentObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.data.term.server.entity.StandardTerm;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("standard term object")
@GraphQLName("StandardTerm")
public class StandardTermObject
        implements BaseGraphQl, TermInterface {

    private final StandardTerm standardTerm; // Always Present

    public StandardTermObject(StandardTerm standardTerm) {
        this.standardTerm = standardTerm;
    }

    @GraphQLField
    @GraphQLDescription("net due days")
    @GraphQLNonNull
    public Integer getNetDueDays(final DataFetchingEnvironment env) {
        return standardTerm.getNetDueDays();
    }

    @GraphQLField
    @GraphQLDescription("discount percentage")
    @GraphQLNonNull
    public PercentObject getDiscountPercentage(final DataFetchingEnvironment env) {
        return new PercentObject(standardTerm.getDiscountPercentage());
    }

    @GraphQLField
    @GraphQLDescription("discount days")
    @GraphQLNonNull
    public Integer getDiscountDays(final DataFetchingEnvironment env) {
        return standardTerm.getDiscountDays();
    }

}
