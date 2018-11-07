// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.party.server.transfer;

import com.echothree.model.control.party.common.PartyProperties;
import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.DateTimeFormatDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import java.util.Set;

public class DateTimeFormatTransferCache
        extends BasePartyTransferCache<DateTimeFormat, DateTimeFormatTransfer> {
    
    TransferProperties transferProperties;
    boolean filterDateTimeFormatName;
    boolean filterJavaShortDateFormat;
    boolean filterJavaAbbrevDateFormat;
    boolean filterJavaAbbrevDateFormatWeekday;
    boolean filterJavaLongDateFormat;
    boolean filterJavaLongDateFormatWeekday;
    boolean filterJavaTimeFormat;
    boolean filterJavaTimeFormatSeconds;
    boolean filterUnixShortDateFormat;
    boolean filterUnixAbbrevDateFormat;
    boolean filterUnixAbbrevDateFormatWeekday;
    boolean filterUnixLongDateFormat;
    boolean filterUnixLongDateFormatWeekday;
    boolean filterUnixTimeFormat;
    boolean filterUnixTimeFormatSeconds;
    boolean filterShortDateSeparator;
    boolean filterTimeSeparator;
    boolean filterIsDefault;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;
    
    /** Creates a new instance of DateTimeFormatTransferCache */
    public DateTimeFormatTransferCache(UserVisit userVisit, PartyControl partyControl) {
        super(userVisit, partyControl);

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            Set<String> properties = transferProperties.getProperties(DateTimeFormatTransfer.class);
            
            if(properties != null) {
                filterDateTimeFormatName = !properties.contains(PartyProperties.DATE_TIME_FORMAT_NAME);
                filterJavaShortDateFormat = !properties.contains(PartyProperties.JAVA_SHORT_DATE_FORMAT);
                filterJavaAbbrevDateFormat = !properties.contains(PartyProperties.JAVA_ABBREV_DATE_FORMAT);
                filterJavaAbbrevDateFormatWeekday = !properties.contains(PartyProperties.JAVA_ABBREV_DATE_FORMAT_WEEKDAY);
                filterJavaLongDateFormat = !properties.contains(PartyProperties.JAVA_LONG_DATE_FORMAT);
                filterJavaLongDateFormatWeekday = !properties.contains(PartyProperties.JAVA_LONG_DATE_FORMAT_WEEKDAY);
                filterJavaTimeFormat = !properties.contains(PartyProperties.JAVA_TIME_FORMAT);
                filterJavaTimeFormatSeconds = !properties.contains(PartyProperties.JAVA_TIME_FORMAT_SECONDS);
                filterUnixShortDateFormat = !properties.contains(PartyProperties.UNIX_SHORT_DATE_FORMAT);
                filterUnixAbbrevDateFormat = !properties.contains(PartyProperties.UNIX_ABBREV_DATE_FORMAT);
                filterUnixAbbrevDateFormatWeekday = !properties.contains(PartyProperties.UNIX_ABBREV_DATE_FORMAT_WEEKDAY);
                filterUnixLongDateFormat = !properties.contains(PartyProperties.UNIX_LONG_DATE_FORMAT);
                filterUnixLongDateFormatWeekday = !properties.contains(PartyProperties.UNIX_LONG_DATE_FORMAT_WEEKDAY);
                filterUnixTimeFormat = !properties.contains(PartyProperties.UNIX_TIME_FORMAT);
                filterUnixTimeFormatSeconds = !properties.contains(PartyProperties.UNIX_TIME_FORMAT_SECONDS);
                filterShortDateSeparator = !properties.contains(PartyProperties.SHORT_DATE_SEPARATOR);
                filterTimeSeparator = !properties.contains(PartyProperties.TIME_SEPARATOR);
                filterIsDefault = !properties.contains(PartyProperties.IS_DEFAULT);
                filterSortOrder = !properties.contains(PartyProperties.SORT_ORDER);
                filterDescription = !properties.contains(PartyProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(PartyProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public DateTimeFormatTransfer getDateTimeFormatTransfer(DateTimeFormat dateTimeFormat) {
        DateTimeFormatTransfer dateTimeFormatTransfer = get(dateTimeFormat);
        
        if(dateTimeFormatTransfer == null) {
            DateTimeFormatDetail dateTimeFormatDetail = dateTimeFormat.getLastDetail();
            String dateTimeFormatName = filterDateTimeFormatName ? null : dateTimeFormatDetail.getDateTimeFormatName();
            String javaShortDateFormat = filterJavaShortDateFormat ? null : dateTimeFormatDetail.getJavaShortDateFormat();
            String javaAbbrevDateFormat = filterJavaAbbrevDateFormat ? null : dateTimeFormatDetail.getJavaAbbrevDateFormat();
            String javaAbbrevDateFormatWeekday = filterJavaAbbrevDateFormatWeekday ? null : dateTimeFormatDetail.getJavaAbbrevDateFormatWeekday();
            String javaLongDateFormat = filterJavaLongDateFormat ? null : dateTimeFormatDetail.getJavaLongDateFormat();
            String javaLongDateFormatWeekday = filterJavaLongDateFormatWeekday ? null : dateTimeFormatDetail.getJavaLongDateFormatWeekday();
            String javaTimeFormat = filterJavaTimeFormat ? null : dateTimeFormatDetail.getJavaTimeFormat();
            String javaTimeFormatSeconds = filterJavaTimeFormatSeconds ? null : dateTimeFormatDetail.getJavaTimeFormatSeconds();
            String unixShortDateFormat = filterUnixShortDateFormat ? null : dateTimeFormatDetail.getUnixShortDateFormat();
            String unixAbbrevDateFormat = filterUnixAbbrevDateFormat ? null : dateTimeFormatDetail.getUnixAbbrevDateFormat();
            String unixAbbrevDateFormatWeekday = filterUnixAbbrevDateFormatWeekday ? null : dateTimeFormatDetail.getUnixAbbrevDateFormatWeekday();
            String unixLongDateFormat = filterUnixLongDateFormat ? null : dateTimeFormatDetail.getUnixLongDateFormat();
            String unixLongDateFormatWeekday = filterUnixLongDateFormatWeekday ? null : dateTimeFormatDetail.getUnixLongDateFormatWeekday();
            String unixTimeFormat = filterUnixTimeFormat ? null : dateTimeFormatDetail.getUnixTimeFormat();
            String unixTimeFormatSeconds = filterUnixTimeFormatSeconds ? null : dateTimeFormatDetail.getUnixTimeFormatSeconds();
            String shortDateSeparator = filterShortDateSeparator ? null : dateTimeFormatDetail.getShortDateSeparator();
            String timeSeparator = filterTimeSeparator ? null : dateTimeFormatDetail.getTimeSeparator();
            Boolean isDefault = filterIsDefault ? null : dateTimeFormatDetail.getIsDefault();
            Integer sortOrder = filterSortOrder ? null : dateTimeFormatDetail.getSortOrder();
            String description = filterDescription ? null : partyControl.getBestDateTimeFormatDescription(dateTimeFormat, getLanguage());
            
            dateTimeFormatTransfer = new DateTimeFormatTransfer(dateTimeFormatName, javaShortDateFormat, javaAbbrevDateFormat,
                    javaAbbrevDateFormatWeekday, javaLongDateFormat, javaLongDateFormatWeekday, javaTimeFormat,
                    javaTimeFormatSeconds, unixShortDateFormat, unixAbbrevDateFormat, unixAbbrevDateFormatWeekday,
                    unixLongDateFormat, unixLongDateFormatWeekday, unixTimeFormat, unixTimeFormatSeconds, shortDateSeparator,
                    timeSeparator, isDefault, sortOrder, description);
            put(dateTimeFormat, dateTimeFormatTransfer);
        }
        
        return dateTimeFormatTransfer;
    }
    
}
