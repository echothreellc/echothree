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
import com.echothree.util.common.string.DateTimeFormatType;
import com.echothree.util.server.string.DateUtils;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("date object")
@GraphQLName("Date")
public class DateObject
        implements BaseGraphQl {

    private Integer unformattedDate; // Always Present

    public DateObject(Integer unformattedDate) {
        this.unformattedDate = unformattedDate;
    }

    @GraphQLField
    @GraphQLDescription("unformatted date")
    @GraphQLNonNull
    public Integer getUnformattedDate() {
        return unformattedDate;
    }

    @GraphQLField
    @GraphQLDescription("short date")
    @GraphQLNonNull
    public String getShortDate(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().getDateFormatter(BaseGraphQl.getUserVisit(env), DateTimeFormatType.SHORT_DATE).format(unformattedDate);
    }

    @GraphQLField
    @GraphQLDescription("abbrev date")
    @GraphQLNonNull
    public String getAbbrevDate(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().getDateFormatter(BaseGraphQl.getUserVisit(env), DateTimeFormatType.ABBREV_DATE).format(unformattedDate);
    }

    @GraphQLField
    @GraphQLDescription("abbrev date with weekday")
    @GraphQLNonNull
    public String getAbbrevDateWithWeekday(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().getDateFormatter(BaseGraphQl.getUserVisit(env), DateTimeFormatType.ABBREV_DATE_WITH_WEEKDAY).format(unformattedDate);
    }

    @GraphQLField
    @GraphQLDescription("long date")
    @GraphQLNonNull
    public String getLongDate(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().getDateFormatter(BaseGraphQl.getUserVisit(env), DateTimeFormatType.LONG_DATE).format(unformattedDate);
    }

    @GraphQLField
    @GraphQLDescription("long date with weekday")
    @GraphQLNonNull
    public String getLongDateWithWeekday(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().getDateFormatter(BaseGraphQl.getUserVisit(env), DateTimeFormatType.LONG_DATE_WITH_WEEKDAY).format(unformattedDate);
    }


}
