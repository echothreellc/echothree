// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class DateTimeFormatTransferCache
        extends BasePartyTransferCache<DateTimeFormat, DateTimeFormatTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);

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
    protected DateTimeFormatTransferCache() {
        super();

        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(DateTimeFormatTransfer.class);
            
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

    @Override
    public DateTimeFormatTransfer getTransfer(UserVisit userVisit, DateTimeFormat dateTimeFormat) {
        var dateTimeFormatTransfer = get(dateTimeFormat);
        
        if(dateTimeFormatTransfer == null) {
            var dateTimeFormatDetail = dateTimeFormat.getLastDetail();
            var dateTimeFormatName = filterDateTimeFormatName ? null : dateTimeFormatDetail.getDateTimeFormatName();
            var javaShortDateFormat = filterJavaShortDateFormat ? null : dateTimeFormatDetail.getJavaShortDateFormat();
            var javaAbbrevDateFormat = filterJavaAbbrevDateFormat ? null : dateTimeFormatDetail.getJavaAbbrevDateFormat();
            var javaAbbrevDateFormatWeekday = filterJavaAbbrevDateFormatWeekday ? null : dateTimeFormatDetail.getJavaAbbrevDateFormatWeekday();
            var javaLongDateFormat = filterJavaLongDateFormat ? null : dateTimeFormatDetail.getJavaLongDateFormat();
            var javaLongDateFormatWeekday = filterJavaLongDateFormatWeekday ? null : dateTimeFormatDetail.getJavaLongDateFormatWeekday();
            var javaTimeFormat = filterJavaTimeFormat ? null : dateTimeFormatDetail.getJavaTimeFormat();
            var javaTimeFormatSeconds = filterJavaTimeFormatSeconds ? null : dateTimeFormatDetail.getJavaTimeFormatSeconds();
            var unixShortDateFormat = filterUnixShortDateFormat ? null : dateTimeFormatDetail.getUnixShortDateFormat();
            var unixAbbrevDateFormat = filterUnixAbbrevDateFormat ? null : dateTimeFormatDetail.getUnixAbbrevDateFormat();
            var unixAbbrevDateFormatWeekday = filterUnixAbbrevDateFormatWeekday ? null : dateTimeFormatDetail.getUnixAbbrevDateFormatWeekday();
            var unixLongDateFormat = filterUnixLongDateFormat ? null : dateTimeFormatDetail.getUnixLongDateFormat();
            var unixLongDateFormatWeekday = filterUnixLongDateFormatWeekday ? null : dateTimeFormatDetail.getUnixLongDateFormatWeekday();
            var unixTimeFormat = filterUnixTimeFormat ? null : dateTimeFormatDetail.getUnixTimeFormat();
            var unixTimeFormatSeconds = filterUnixTimeFormatSeconds ? null : dateTimeFormatDetail.getUnixTimeFormatSeconds();
            var shortDateSeparator = filterShortDateSeparator ? null : dateTimeFormatDetail.getShortDateSeparator();
            var timeSeparator = filterTimeSeparator ? null : dateTimeFormatDetail.getTimeSeparator();
            var isDefault = filterIsDefault ? null : dateTimeFormatDetail.getIsDefault();
            var sortOrder = filterSortOrder ? null : dateTimeFormatDetail.getSortOrder();
            var description = filterDescription ? null : partyControl.getBestDateTimeFormatDescription(dateTimeFormat, getLanguage(userVisit));
            
            dateTimeFormatTransfer = new DateTimeFormatTransfer(dateTimeFormatName, javaShortDateFormat, javaAbbrevDateFormat,
                    javaAbbrevDateFormatWeekday, javaLongDateFormat, javaLongDateFormatWeekday, javaTimeFormat,
                    javaTimeFormatSeconds, unixShortDateFormat, unixAbbrevDateFormat, unixAbbrevDateFormatWeekday,
                    unixLongDateFormat, unixLongDateFormatWeekday, unixTimeFormat, unixTimeFormatSeconds, shortDateSeparator,
                    timeSeparator, isDefault, sortOrder, description);
            put(userVisit, dateTimeFormat, dateTimeFormatTransfer);
        }
        
        return dateTimeFormatTransfer;
    }
    
}
