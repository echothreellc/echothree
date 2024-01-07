// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.util.server.string;

import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.string.DateFormatter;
import com.echothree.util.common.string.DateTimeFormatType;
import com.echothree.util.common.string.DateTimeFormatter;
import com.echothree.util.server.persistence.Session;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class DateUtils {
    
    private DateUtils() {
        super();
    }
    
    private static class DateUtilsHolder {
        static DateUtils instance = new DateUtils();
    }
    
    public static DateUtils getInstance() {
        return DateUtilsHolder.instance;
    }
    
    private final java.time.format.DateTimeFormatter dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd");

    public String formatDate(UserVisit userVisit, Integer date) {
        String result = null;
        
        if(date != null) {
            var userControl = Session.getModelController(UserControl.class);
            LocalDate localDate = LocalDate.parse(date.toString(), dateTimeFormatter);
            java.time.format.DateTimeFormatter resultDateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern(userControl.getPreferredDateTimeFormatFromUserVisit(userVisit).getLastDetail().getJavaShortDateFormat());
            
            result = resultDateTimeFormatter.format(localDate);
        }
        
        return result;
    }
    
    protected UserControl getUserControl() {
        return Session.getModelController(UserControl.class);
    }
    
    protected TimeZone getTimeZone(UserVisit userVisit) {
        return getUserControl().getPreferredTimeZoneFromUserVisit(userVisit);
    }
    
    protected String getJavaTimeZoneName(UserVisit userVisit) {
        return getTimeZone(userVisit).getLastDetail().getJavaTimeZoneName();
    }
    
    protected java.util.TimeZone getJavaTimeZone(UserVisit userVisit) {
        return java.util.TimeZone.getTimeZone(getJavaTimeZoneName(userVisit));
    }
    
    protected DateTimeFormat getDateTimeFormat(UserVisit userVisit) {
        return getUserControl().getPreferredDateTimeFormatFromUserVisit(userVisit);
    }
    
    protected String formatDateUsingShortDateFormat(UserVisit userVisit, DateTimeFormat dateTimeFormat, Date time) {
        String result = null;

        if(dateTimeFormat != null) {
            var sdfShortDateFormat = new SimpleDateFormat(dateTimeFormat.getLastDetail().getJavaShortDateFormat());

            sdfShortDateFormat.setTimeZone(getJavaTimeZone(userVisit));

            result = sdfShortDateFormat.format(time);
        }

        return result;
    }
    
    protected String formatTimeUsingTimeFormatSeconds(UserVisit userVisit, DateTimeFormat dateTimeFormat, Date time) {
        String result = null;

        if(dateTimeFormat != null) {
            var sdfTimeFormatSeconds = new SimpleDateFormat(dateTimeFormat.getLastDetail().getJavaTimeFormatSeconds());

            sdfTimeFormatSeconds.setTimeZone(getJavaTimeZone(userVisit));

            result = sdfTimeFormatSeconds.format(time);
        }

        return result;
    }
    
    public String formatTypicalDateTime(UserVisit userVisit, DateTimeFormat dateTimeFormat, Date time) {
        return time == null || dateTimeFormat == null ? null : formatDateUsingShortDateFormat(userVisit, dateTimeFormat, time) + ' ' + formatTimeUsingTimeFormatSeconds(userVisit, dateTimeFormat, time);
    }
    
    public String formatTypicalDateTime(UserVisit userVisit, Date time) {
        return formatTypicalDateTime(userVisit, getDateTimeFormat(userVisit), time);
    }
    
    public String formatTypicalDateTime(UserVisit userVisit, DateTimeFormat dateTimeFormat, Long time) {
        return time == null || dateTimeFormat == null ? null : formatTypicalDateTime(userVisit, dateTimeFormat, new Date(time));
    }
    
    public String formatTypicalDateTime(UserVisit userVisit, Long time) {
        return formatTypicalDateTime(userVisit, getDateTimeFormat(userVisit), time);
    }
    
    public DateFormatter getDateFormatter(UserVisit userVisit, DateTimeFormatType dtft) {
        var dtfd = getDateTimeFormat(userVisit).getLastDetail();
        String pattern = null;

        switch(dtft) {
            case SHORT_DATE -> pattern = dtfd.getJavaShortDateFormat();
            case ABBREV_DATE -> pattern = dtfd.getJavaAbbrevDateFormat();
            case ABBREV_DATE_WITH_WEEKDAY -> pattern = dtfd.getJavaAbbrevDateFormatWeekday();
            case LONG_DATE -> pattern = dtfd.getJavaLongDateFormat();
            case LONG_DATE_WITH_WEEKDAY -> pattern = dtfd.getJavaLongDateFormatWeekday();
            default -> { throw new IllegalArgumentException(); }
        }
        
        return new DateFormatter(pattern);
    }
    
    public DateTimeFormatter getDateTimeFormatter(UserVisit userVisit, DateTimeFormatType dtft) {
        var dtfd = getDateTimeFormat(userVisit).getLastDetail();
        String pattern = null;

        switch(dtft) {
            case SHORT_DATE -> pattern = dtfd.getJavaShortDateFormat();
            case ABBREV_DATE -> pattern = dtfd.getJavaAbbrevDateFormat();
            case ABBREV_DATE_WITH_WEEKDAY -> pattern = dtfd.getJavaAbbrevDateFormatWeekday();
            case LONG_DATE -> pattern = dtfd.getJavaLongDateFormat();
            case LONG_DATE_WITH_WEEKDAY -> pattern = dtfd.getJavaLongDateFormatWeekday();
            case TIME -> pattern = dtfd.getJavaTimeFormat();
            case TIME_WITH_SECONDS -> pattern = dtfd.getJavaTimeFormatSeconds();
        }

        return new DateTimeFormatter(getJavaTimeZoneName(userVisit), pattern);
    }
    
}
