// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.util.common.string;

import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
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
    
    public String formatDate(DateTimeFormatTransfer dateTimeFormat, Integer date) {
        String result = null;
        
        if(date != null) {
            LocalDate localDate = LocalDate.parse(date.toString(), dateTimeFormatter);
            java.time.format.DateTimeFormatter resultDateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern(dateTimeFormat.getJavaShortDateFormat());

            result = resultDateTimeFormatter.format(localDate);
        }
        
        return result;
    }
    
    protected java.util.TimeZone getJavaTimeZone(TimeZoneTransfer timeZone) {
        return java.util.TimeZone.getTimeZone(timeZone.getJavaTimeZoneName());
    }
    
    protected String formatDateUsingShortDateFormat(TimeZoneTransfer timeZone, DateTimeFormatTransfer dateTimeFormat, Date time) {
        SimpleDateFormat sdfShortDateFormat = new SimpleDateFormat(dateTimeFormat.getJavaShortDateFormat());
        sdfShortDateFormat.setTimeZone(getJavaTimeZone(timeZone));
        
        return sdfShortDateFormat.format(time);
    }
    
    protected String formatTimeUsingTimeFormatSeconds(TimeZoneTransfer timeZone, DateTimeFormatTransfer dateTimeFormat, Date time) {
        SimpleDateFormat sdfTimeFormatSeconds = new SimpleDateFormat(dateTimeFormat.getJavaTimeFormatSeconds());
        sdfTimeFormatSeconds.setTimeZone(getJavaTimeZone(timeZone));
        
        return sdfTimeFormatSeconds.format(time);
    }
    
    public String formatTypicalDateTime(TimeZoneTransfer timeZone, DateTimeFormatTransfer dateTimeFormat, Date time) {
        return new StringBuilder(formatDateUsingShortDateFormat(timeZone, dateTimeFormat, time)).append(' ').append(formatTimeUsingTimeFormatSeconds(timeZone, dateTimeFormat, time)).toString();
    }
    
    public String formatTypicalDateTime(TimeZoneTransfer timeZone, DateTimeFormatTransfer dateTimeFormat, Long time) {
        return time == null? null: formatTypicalDateTime(timeZone, dateTimeFormat, new Date(time));
    }
    
    public DateFormatter getDateFormatter(DateTimeFormatTransfer dateTimeFormat, DateTimeFormatType dtft) {
        String pattern = null;
        
        if(dtft.equals(DateTimeFormatType.SHORT_DATE)) {
            pattern = dateTimeFormat.getJavaShortDateFormat();
        } else if(dtft.equals(DateTimeFormatType.ABBREV_DATE)) {
            pattern = dateTimeFormat.getJavaAbbrevDateFormat();
        } else if(dtft.equals(DateTimeFormatType.ABBREV_DATE_WITH_WEEKDAY)) {
            pattern = dateTimeFormat.getJavaAbbrevDateFormatWeekday();
        } else if(dtft.equals(DateTimeFormatType.LONG_DATE)) {
            pattern = dateTimeFormat.getJavaLongDateFormat();
        } else if(dtft.equals(DateTimeFormatType.LONG_DATE_WITH_WEEKDAY)) {
            pattern = dateTimeFormat.getJavaLongDateFormatWeekday();
        }
        
        return new DateFormatter(pattern);
    }
    
    public DateTimeFormatter getDateTimeFormatter(TimeZoneTransfer timeZone, DateTimeFormatTransfer dateTimeFormat, DateTimeFormatType dtft) {
        String pattern = null;
        
        if(dtft.equals(DateTimeFormatType.SHORT_DATE)) {
            pattern = dateTimeFormat.getJavaShortDateFormat();
        } else if(dtft.equals(DateTimeFormatType.ABBREV_DATE)) {
            pattern = dateTimeFormat.getJavaAbbrevDateFormat();
        } else if(dtft.equals(DateTimeFormatType.ABBREV_DATE_WITH_WEEKDAY)) {
            pattern = dateTimeFormat.getJavaAbbrevDateFormatWeekday();
        } else if(dtft.equals(DateTimeFormatType.LONG_DATE)) {
            pattern = dateTimeFormat.getJavaLongDateFormat();
        } else if(dtft.equals(DateTimeFormatType.LONG_DATE_WITH_WEEKDAY)) {
            pattern = dateTimeFormat.getJavaLongDateFormatWeekday();
        } else if(dtft.equals(DateTimeFormatType.TIME)) {
            pattern = dateTimeFormat.getJavaTimeFormat();
        } else if(dtft.equals(DateTimeFormatType.TIME_WITH_SECONDS)) {
            pattern = dateTimeFormat.getJavaTimeFormatSeconds();
        }
        
        return new DateTimeFormatter(timeZone.getJavaTimeZoneName(), pattern);
    }
    
}
