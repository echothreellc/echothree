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

package com.echothree.model.control.party.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.DateTimeFormatDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("date time format object")
@GraphQLName("DateTimeFormat")
public class DateTimeFormatObject
        extends BaseEntityInstanceObject {
    
    private final DateTimeFormat dateTimeFormat; // Always Present
    
    public DateTimeFormatObject(DateTimeFormat dateTimeFormat) {
        super(dateTimeFormat.getPrimaryKey());
        
        this.dateTimeFormat = dateTimeFormat;
    }

    private DateTimeFormatDetail dateTimeFormatDetail; // Optional, use getDateTimeFormatDetail()
    
    private DateTimeFormatDetail getDateTimeFormatDetail() {
        if(dateTimeFormatDetail == null) {
            dateTimeFormatDetail = dateTimeFormat.getLastDetail();
        }
        
        return dateTimeFormatDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("date time format name")
    @GraphQLNonNull
    public String getDateTimeFormatName() {
        return getDateTimeFormatDetail().getDateTimeFormatName();
    }

    @GraphQLField
    @GraphQLDescription("java short date format")
    @GraphQLNonNull
    public String getJavaShortDateFormat() {
        return getDateTimeFormatDetail().getJavaShortDateFormat();
    }
    
    @GraphQLField
    @GraphQLDescription("java abbrev date format")
    @GraphQLNonNull
    public String getJavaAbbrevDateFormat() {
        return getDateTimeFormatDetail().getJavaAbbrevDateFormat();
    }
    
    @GraphQLField
    @GraphQLDescription("java abbrev date format weekday")
    @GraphQLNonNull
    public String getJavaAbbrevDateFormatWeekday() {
        return getDateTimeFormatDetail().getJavaAbbrevDateFormatWeekday();
    }
    
    @GraphQLField
    @GraphQLDescription("java long date format")
    @GraphQLNonNull
    public String getJavaLongDateFormat() {
        return getDateTimeFormatDetail().getJavaLongDateFormat();
    }
    
    @GraphQLField
    @GraphQLDescription("java long date format weekday")
    @GraphQLNonNull
    public String getJavaLongDateFormatWeekday() {
        return getDateTimeFormatDetail().getJavaLongDateFormatWeekday();
    }
    
    @GraphQLField
    @GraphQLDescription("java time format name")
    @GraphQLNonNull
    public String getJavaTimeFormat() {
        return getDateTimeFormatDetail().getJavaTimeFormat();
    }
    
    @GraphQLField
    @GraphQLDescription("java time format seconds")
    @GraphQLNonNull
    public String getJavaTimeFormatSeconds() {
        return getDateTimeFormatDetail().getJavaTimeFormatSeconds();
    }
    
    @GraphQLField
    @GraphQLDescription("unix short date format")
    @GraphQLNonNull
    public String getUnixShortDateFormat() {
        return getDateTimeFormatDetail().getUnixShortDateFormat();
    }
    
    @GraphQLField
    @GraphQLDescription("unix abbrev date format")
    @GraphQLNonNull
    public String getUnixAbbrevDateFormat() {
        return getDateTimeFormatDetail().getUnixAbbrevDateFormat();
    }
    
    @GraphQLField
    @GraphQLDescription("unix abbrev date format weekday")
    @GraphQLNonNull
    public String getUnixAbbrevDateFormatWeekday() {
        return getDateTimeFormatDetail().getUnixAbbrevDateFormatWeekday();
    }
    
    @GraphQLField
    @GraphQLDescription("unix long date format")
    @GraphQLNonNull
    public String getUnixLongDateFormat() {
        return getDateTimeFormatDetail().getUnixLongDateFormat();
    }
    
    @GraphQLField
    @GraphQLDescription("unix long date format weekday")
    @GraphQLNonNull
    public String getUnixLongDateFormatWeekday() {
        return getDateTimeFormatDetail().getUnixLongDateFormatWeekday();
    }
    
    @GraphQLField
    @GraphQLDescription("unix time format")
    @GraphQLNonNull
    public String getUnixTimeFormat() {
        return getDateTimeFormatDetail().getUnixTimeFormat();
    }
    
    @GraphQLField
    @GraphQLDescription("unix time format seconds")
    @GraphQLNonNull
    public String getUnixTimeFormatSeconds() {
        return getDateTimeFormatDetail().getUnixTimeFormatSeconds();
    }
    
    @GraphQLField
    @GraphQLDescription("short date separator")
    @GraphQLNonNull
    public String getShortDateSeparator() {
        return getDateTimeFormatDetail().getShortDateSeparator();
    }
    
    @GraphQLField
    @GraphQLDescription("time separator")
    @GraphQLNonNull
    public String getTimeSeparator() {
        return getDateTimeFormatDetail().getTimeSeparator();
    }
    
    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getDateTimeFormatDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getDateTimeFormatDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return partyControl.getBestDateTimeFormatDescription(dateTimeFormat, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }
    
}
