// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.data.party.server.entity.DateTimeFormatDetail;
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
        return (UserControl)Session.getModelController(UserControl.class);
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
        SimpleDateFormat sdfShortDateFormat = new SimpleDateFormat(dateTimeFormat.getLastDetail().getJavaShortDateFormat());
        sdfShortDateFormat.setTimeZone(getJavaTimeZone(userVisit));
        
        return sdfShortDateFormat.format(time);
    }
    
    protected String formatTimeUsingTimeFormatSeconds(UserVisit userVisit, DateTimeFormat dateTimeFormat, Date time) {
        SimpleDateFormat sdfTimeFormatSeconds = new SimpleDateFormat(dateTimeFormat.getLastDetail().getJavaTimeFormatSeconds());
        sdfTimeFormatSeconds.setTimeZone(getJavaTimeZone(userVisit));
        
        return sdfTimeFormatSeconds.format(time);
    }
    
    public String formatTypicalDateTime(UserVisit userVisit, DateTimeFormat dateTimeFormat, Date time) {
        return new StringBuilder(formatDateUsingShortDateFormat(userVisit, dateTimeFormat, time)).append(' ').append(formatTimeUsingTimeFormatSeconds(userVisit, dateTimeFormat, time)).toString();
    }
    
    public String formatTypicalDateTime(UserVisit userVisit, Date time) {
        return formatTypicalDateTime(userVisit, getDateTimeFormat(userVisit), time);
    }
    
    public String formatTypicalDateTime(UserVisit userVisit, DateTimeFormat dateTimeFormat, Long time) {
        return time == null? null: formatTypicalDateTime(userVisit, dateTimeFormat, new Date(time));
    }
    
    public String formatTypicalDateTime(UserVisit userVisit, Long time) {
        return formatTypicalDateTime(userVisit, getDateTimeFormat(userVisit), time);
    }
    
    public DateFormatter getDateFormatter(UserVisit userVisit, DateTimeFormatType dtft) {
        String pattern = null;
        DateTimeFormatDetail dtfd = getDateTimeFormat(userVisit).getLastDetail();
        
        switch(dtft) {
            case SHORT_DATE:
                pattern = dtfd.getJavaShortDateFormat();
                break;
            case ABBREV_DATE:
                pattern = dtfd.getJavaAbbrevDateFormat();
                break;
            case ABBREV_DATE_WITH_WEEKDAY:
                pattern = dtfd.getJavaAbbrevDateFormatWeekday();
                break;
            case LONG_DATE:
                pattern = dtfd.getJavaLongDateFormat();
                break;
            case LONG_DATE_WITH_WEEKDAY:
                pattern = dtfd.getJavaLongDateFormatWeekday();
                break;
            default:
                break;
        }
        
        return new DateFormatter(pattern);
    }
    
    public DateTimeFormatter getDateTimeFormatter(UserVisit userVisit, DateTimeFormatType dtft) {
        String pattern = null;
        DateTimeFormatDetail dtfd = getDateTimeFormat(userVisit).getLastDetail();
        
        if(dtft.equals(DateTimeFormatType.SHORT_DATE)) {
            pattern = dtfd.getJavaShortDateFormat();
        } else if(dtft.equals(DateTimeFormatType.ABBREV_DATE)) {
            pattern = dtfd.getJavaAbbrevDateFormat();
        } else if(dtft.equals(DateTimeFormatType.ABBREV_DATE_WITH_WEEKDAY)) {
            pattern = dtfd.getJavaAbbrevDateFormatWeekday();
        } else if(dtft.equals(DateTimeFormatType.LONG_DATE)) {
            pattern = dtfd.getJavaLongDateFormat();
        } else if(dtft.equals(DateTimeFormatType.LONG_DATE_WITH_WEEKDAY)) {
            pattern = dtfd.getJavaLongDateFormatWeekday();
        } else if(dtft.equals(DateTimeFormatType.TIME)) {
            pattern = dtfd.getJavaTimeFormat();
        } else if(dtft.equals(DateTimeFormatType.TIME_WITH_SECONDS)) {
            pattern = dtfd.getJavaTimeFormatSeconds();
        }
        
        return new DateTimeFormatter(getJavaTimeZoneName(userVisit), pattern);
    }
    
}
