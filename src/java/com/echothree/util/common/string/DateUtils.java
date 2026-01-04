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

package com.echothree.util.common.string;

import com.echothree.model.control.party.common.transfer.DateTimeFormatTransfer;
import com.echothree.model.control.party.common.transfer.TimeZoneTransfer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

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
    
    public String formatDate(final DateTimeFormatTransfer dateTimeFormat, final Integer date) {
        String result = null;
        
        if(date != null) {
            var localDate = LocalDate.parse(date.toString(), dateTimeFormatter);
            var resultDateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern(dateTimeFormat.getJavaShortDateFormat());

            result = resultDateTimeFormatter.format(localDate);
        }
        
        return result;
    }

    protected ZoneId getJavaTimeZone(final TimeZoneTransfer timeZone) {
        return ZoneId.of(timeZone.getJavaTimeZoneName());
    }

    protected String formatDateUsingShortDateFormat(final TimeZoneTransfer timeZone, final DateTimeFormatTransfer dateTimeFormat, final Instant instant) {
        var dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern(dateTimeFormat.getJavaShortDateFormat());
        var localDate = LocalDate.ofInstant(instant, getJavaTimeZone(timeZone));

        return localDate.format(dateTimeFormatter);
    }
    
    protected String formatTimeUsingTimeFormatSeconds(final TimeZoneTransfer timeZone, final DateTimeFormatTransfer dateTimeFormat, final Instant instant) {
        var dateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern(dateTimeFormat.getJavaTimeFormatSeconds());
        var localTime = LocalTime.ofInstant(instant, getJavaTimeZone(timeZone));

        return localTime.format(dateTimeFormatter);
    }
    
    public String formatTypicalDateTime(final TimeZoneTransfer timeZone, final DateTimeFormatTransfer dateTimeFormat, final Instant instant) {
        return instant == null || dateTimeFormat == null ? null : formatDateUsingShortDateFormat(timeZone, dateTimeFormat, instant) + ' ' + formatTimeUsingTimeFormatSeconds(timeZone, dateTimeFormat, instant);
    }
    
    public String formatTypicalDateTime(final TimeZoneTransfer timeZone, final DateTimeFormatTransfer dateTimeFormat, final Long time) {
        return time == null? null: formatTypicalDateTime(timeZone, dateTimeFormat, Instant.ofEpochMilli(time));
    }
    
    public DateFormatter getDateFormatter(DateTimeFormatTransfer dateTimeFormat, DateTimeFormatType dtft) {
        String pattern;

        switch(dtft) {
            case SHORT_DATE -> pattern = dateTimeFormat.getJavaShortDateFormat();
            case ABBREV_DATE -> pattern = dateTimeFormat.getJavaAbbrevDateFormat();
            case ABBREV_DATE_WITH_WEEKDAY -> pattern = dateTimeFormat.getJavaAbbrevDateFormatWeekday();
            case LONG_DATE -> pattern = dateTimeFormat.getJavaLongDateFormat();
            case LONG_DATE_WITH_WEEKDAY -> pattern = dateTimeFormat.getJavaLongDateFormatWeekday();
            default -> throw new IllegalArgumentException();
        }

        return new DateFormatter(pattern);
    }

    public DateTimeFormatter getDateTimeFormatter(final TimeZoneTransfer timeZone, final DateTimeFormatTransfer dateTimeFormat, final DateTimeFormatType dtft) {
        String pattern;

        switch(dtft) {
            case SHORT_DATE -> pattern = dateTimeFormat.getJavaShortDateFormat();
            case ABBREV_DATE -> pattern = dateTimeFormat.getJavaAbbrevDateFormat();
            case ABBREV_DATE_WITH_WEEKDAY -> pattern = dateTimeFormat.getJavaAbbrevDateFormatWeekday();
            case LONG_DATE -> pattern = dateTimeFormat.getJavaLongDateFormat();
            case LONG_DATE_WITH_WEEKDAY -> pattern = dateTimeFormat.getJavaLongDateFormatWeekday();
            case TIME -> pattern = dateTimeFormat.getJavaTimeFormat();
            case TIME_WITH_SECONDS -> pattern = dateTimeFormat.getJavaTimeFormatSeconds();
            default -> throw new IllegalArgumentException();
        }
        
        return new DateTimeFormatter(timeZone.getJavaTimeZoneName(), pattern);
    }
    
}
