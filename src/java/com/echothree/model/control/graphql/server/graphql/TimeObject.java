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

@GraphQLDescription("time object")
@GraphQLName("Time")
public class TimeObject
        implements BaseGraphQl {

    private Long unformattedTime; // Always Present

    public TimeObject(Long unformattedTime) {
        this.unformattedTime = unformattedTime;
    }

    @GraphQLField
    @GraphQLDescription("unformatted time")
    @GraphQLNonNull
    public Long getUnformattedTime() {
        return unformattedTime;
    }

    @GraphQLField
    @GraphQLDescription("short date")
    @GraphQLNonNull
    public String getShortDate(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().getDateTimeFormatter(BaseGraphQl.getUserVisit(env), DateTimeFormatType.SHORT_DATE).format(unformattedTime);
    }

    @GraphQLField
    @GraphQLDescription("abbrev date")
    @GraphQLNonNull
    public String getAbbrevDate(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().getDateTimeFormatter(BaseGraphQl.getUserVisit(env), DateTimeFormatType.ABBREV_DATE).format(unformattedTime);
    }

    @GraphQLField
    @GraphQLDescription("abbrev date with weekday")
    @GraphQLNonNull
    public String getAbbrevDateWithWeekday(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().getDateTimeFormatter(BaseGraphQl.getUserVisit(env), DateTimeFormatType.ABBREV_DATE_WITH_WEEKDAY).format(unformattedTime);
    }

    @GraphQLField
    @GraphQLDescription("long date")
    @GraphQLNonNull
    public String getLongDate(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().getDateTimeFormatter(BaseGraphQl.getUserVisit(env), DateTimeFormatType.LONG_DATE).format(unformattedTime);
    }

    @GraphQLField
    @GraphQLDescription("long date with weekday")
    @GraphQLNonNull
    public String getLongDateWithWeekday(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().getDateTimeFormatter(BaseGraphQl.getUserVisit(env), DateTimeFormatType.LONG_DATE_WITH_WEEKDAY).format(unformattedTime);
    }

    @GraphQLField
    @GraphQLDescription("time")
    @GraphQLNonNull
    public String getTime(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().getDateTimeFormatter(BaseGraphQl.getUserVisit(env), DateTimeFormatType.TIME).format(unformattedTime);
    }

    @GraphQLField
    @GraphQLDescription("time with seconds")
    @GraphQLNonNull
    public String getTimeWithSeconds(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().getDateTimeFormatter(BaseGraphQl.getUserVisit(env), DateTimeFormatType.TIME_WITH_SECONDS).format(unformattedTime);
    }

    @GraphQLField
    @GraphQLDescription("typical date time")
    @GraphQLNonNull
    public String getTypicalDateTime(final DataFetchingEnvironment env) {
        return DateUtils.getInstance().formatTypicalDateTime(BaseGraphQl.getUserVisit(env), unformattedTime);
    }

}
