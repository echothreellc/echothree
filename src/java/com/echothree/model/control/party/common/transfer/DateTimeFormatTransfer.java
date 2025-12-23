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

package com.echothree.model.control.party.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class DateTimeFormatTransfer
        extends BaseTransfer {
    
    private String dateTimeFormatName;
    private String javaShortDateFormat;
    private String javaAbbrevDateFormat;
    private String javaAbbrevDateFormatWeekday;
    private String javaLongDateFormat;
    private String javaLongDateFormatWeekday;
    private String javaTimeFormat;
    private String javaTimeFormatSeconds;
    private String unixShortDateFormat;
    private String unixAbbrevDateFormat;
    private String unixAbbrevDateFormatWeekday;
    private String unixLongDateFormat;
    private String unixLongDateFormatWeekday;
    private String unixTimeFormat;
    private String unixTimeFormatSeconds;
    private String shortDateSeparator;
    private String timeSeparator;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of DateTimeFormatTransfer */
    public DateTimeFormatTransfer(String dateTimeFormatName, String javaShortDateFormat, String javaAbbrevDateFormat,
            String javaAbbrevDateFormatWeekday, String javaLongDateFormat, String javaLongDateFormatWeekday, String javaTimeFormat,
            String javaTimeFormatSeconds, String unixShortDateFormat, String unixAbbrevDateFormat,
            String unixAbbrevDateFormatWeekday, String unixLongDateFormat, String unixLongDateFormatWeekday, String unixTimeFormat,
            String unixTimeFormatSeconds, String shortDateSeparator, String timeSeparator, Boolean isDefault, Integer sortOrder, String description) {
        this.dateTimeFormatName = dateTimeFormatName;
        this.javaShortDateFormat = javaShortDateFormat;
        this.javaAbbrevDateFormat = javaAbbrevDateFormat;
        this.javaAbbrevDateFormatWeekday = javaAbbrevDateFormatWeekday;
        this.javaLongDateFormat = javaLongDateFormat;
        this.javaLongDateFormatWeekday = javaLongDateFormatWeekday;
        this.javaTimeFormat = javaTimeFormat;
        this.javaTimeFormatSeconds = javaTimeFormatSeconds;
        this.unixShortDateFormat = unixShortDateFormat;
        this.unixAbbrevDateFormat = unixAbbrevDateFormat;
        this.unixAbbrevDateFormatWeekday = unixAbbrevDateFormatWeekday;
        this.unixLongDateFormat = unixLongDateFormat;
        this.unixLongDateFormatWeekday = unixLongDateFormatWeekday;
        this.unixTimeFormat = unixTimeFormat;
        this.unixTimeFormatSeconds = unixTimeFormatSeconds;
        this.shortDateSeparator = shortDateSeparator;
        this.timeSeparator = timeSeparator;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getDateTimeFormatName() {
        return dateTimeFormatName;
    }
    
    public void setDateTimeFormatName(String dateTimeFormatName) {
        this.dateTimeFormatName = dateTimeFormatName;
    }
    
    public String getJavaShortDateFormat() {
        return javaShortDateFormat;
    }
    
    public void setJavaShortDateFormat(String javaShortDateFormat) {
        this.javaShortDateFormat = javaShortDateFormat;
    }
    
    public String getJavaAbbrevDateFormat() {
        return javaAbbrevDateFormat;
    }
    
    public void setJavaAbbrevDateFormat(String javaAbbrevDateFormat) {
        this.javaAbbrevDateFormat = javaAbbrevDateFormat;
    }
    
    public String getJavaAbbrevDateFormatWeekday() {
        return javaAbbrevDateFormatWeekday;
    }
    
    public void setJavaAbbrevDateFormatWeekday(String javaAbbrevDateFormatWeekday) {
        this.javaAbbrevDateFormatWeekday = javaAbbrevDateFormatWeekday;
    }
    
    public String getJavaLongDateFormat() {
        return javaLongDateFormat;
    }
    
    public void setJavaLongDateFormat(String javaLongDateFormat) {
        this.javaLongDateFormat = javaLongDateFormat;
    }
    
    public String getJavaLongDateFormatWeekday() {
        return javaLongDateFormatWeekday;
    }
    
    public void setJavaLongDateFormatWeekday(String javaLongDateFormatWeekday) {
        this.javaLongDateFormatWeekday = javaLongDateFormatWeekday;
    }
    
    public String getJavaTimeFormat() {
        return javaTimeFormat;
    }
    
    public void setJavaTimeFormat(String javaTimeFormat) {
        this.javaTimeFormat = javaTimeFormat;
    }
    
    public String getJavaTimeFormatSeconds() {
        return javaTimeFormatSeconds;
    }
    
    public void setJavaTimeFormatSeconds(String javaTimeFormatSeconds) {
        this.javaTimeFormatSeconds = javaTimeFormatSeconds;
    }
    
    public String getUnixShortDateFormat() {
        return unixShortDateFormat;
    }
    
    public void setUnixShortDateFormat(String unixShortDateFormat) {
        this.unixShortDateFormat = unixShortDateFormat;
    }
    
    public String getUnixAbbrevDateFormat() {
        return unixAbbrevDateFormat;
    }
    
    public void setUnixAbbrevDateFormat(String unixAbbrevDateFormat) {
        this.unixAbbrevDateFormat = unixAbbrevDateFormat;
    }
    
    public String getUnixAbbrevDateFormatWeekday() {
        return unixAbbrevDateFormatWeekday;
    }
    
    public void setUnixAbbrevDateFormatWeekday(String unixAbbrevDateFormatWeekday) {
        this.unixAbbrevDateFormatWeekday = unixAbbrevDateFormatWeekday;
    }
    
    public String getUnixLongDateFormat() {
        return unixLongDateFormat;
    }
    
    public void setUnixLongDateFormat(String unixLongDateFormat) {
        this.unixLongDateFormat = unixLongDateFormat;
    }
    
    public String getUnixLongDateFormatWeekday() {
        return unixLongDateFormatWeekday;
    }
    
    public void setUnixLongDateFormatWeekday(String unixLongDateFormatWeekday) {
        this.unixLongDateFormatWeekday = unixLongDateFormatWeekday;
    }
    
    public String getUnixTimeFormat() {
        return unixTimeFormat;
    }
    
    public void setUnixTimeFormat(String unixTimeFormat) {
        this.unixTimeFormat = unixTimeFormat;
    }
    
    public String getUnixTimeFormatSeconds() {
        return unixTimeFormatSeconds;
    }
    
    public void setUnixTimeFormatSeconds(String unixTimeFormatSeconds) {
        this.unixTimeFormatSeconds = unixTimeFormatSeconds;
    }
    
    public String getShortDateSeparator() {
        return shortDateSeparator;
    }
    
    public void setShortDateSeparator(String shortDateSeparator) {
        this.shortDateSeparator = shortDateSeparator;
    }
    
    public String getTimeSeparator() {
        return timeSeparator;
    }
    
    public void setTimeSeparator(String timeSeparator) {
        this.timeSeparator = timeSeparator;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
