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
import com.echothree.model.data.term.server.entity.DateDrivenTerm;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("date driven term object")
@GraphQLName("DateDrivenTerm")
public class DateDrivenTermObject
        implements BaseGraphQl, TermInterface {

    private final DateDrivenTerm dateDrivenTerm; // Always Present

    public DateDrivenTermObject(DateDrivenTerm dateDrivenTerm) {
        this.dateDrivenTerm = dateDrivenTerm;
    }

    @GraphQLField
    @GraphQLDescription("net due day of month")
    @GraphQLNonNull
    public Integer getNetDueDayOfMonth(final DataFetchingEnvironment env) {
        return dateDrivenTerm.getNetDueDayOfMonth();
    }

    @GraphQLField
    @GraphQLDescription("due next month days")
    @GraphQLNonNull
    public Integer getDueNextMonthDays(final DataFetchingEnvironment env) {
        return dateDrivenTerm.getDueNextMonthDays();
    }

    @GraphQLField
    @GraphQLDescription("discount percentage")
    @GraphQLNonNull
    public PercentObject getDiscountPercentage(final DataFetchingEnvironment env) {
        return new PercentObject(dateDrivenTerm.getDiscountPercentage());
    }

    @GraphQLField
    @GraphQLDescription("discount before day of month")
    @GraphQLNonNull
    public Integer getDiscountBeforeDayOfMonth(final DataFetchingEnvironment env) {
        return dateDrivenTerm.getDiscountBeforeDayOfMonth();
    }

}
