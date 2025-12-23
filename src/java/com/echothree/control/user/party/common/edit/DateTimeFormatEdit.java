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

package com.echothree.control.user.party.common.edit;

import com.echothree.control.user.party.common.spec.DateTimeFormatSpec;

public interface DateTimeFormatEdit
        extends DateTimeFormatSpec, DateTimeFormatDescriptionEdit {
    
    String getJavaShortDateFormat();
    void setJavaShortDateFormat(String javaShortDateFormat);
    
    String getJavaAbbrevDateFormat();
    void setJavaAbbrevDateFormat(String javaAbbrevDateFormat);
    
    String getJavaAbbrevDateFormatWeekday();
    void setJavaAbbrevDateFormatWeekday(String javaAbbrevDateFormatWeekday);
    
    String getJavaLongDateFormat();
    void setJavaLongDateFormat(String javaLongDateFormat);
    
    String getJavaLongDateFormatWeekday();
    void setJavaLongDateFormatWeekday(String javaLongDateFormatWeekday);
    
    String getJavaTimeFormat();
    void setJavaTimeFormat(String javaTimeFormat);
    
    String getJavaTimeFormatSeconds();
    void setJavaTimeFormatSeconds(String javaTimeFormatSeconds);
    
    String getUnixShortDateFormat();
    void setUnixShortDateFormat(String unixShortDateFormat);
    
    String getUnixAbbrevDateFormat();
    void setUnixAbbrevDateFormat(String unixAbbrevDateFormat);
    
    String getUnixAbbrevDateFormatWeekday();
    void setUnixAbbrevDateFormatWeekday(String unixAbbrevDateFormatWeekday);
    
    String getUnixLongDateFormat();
    void setUnixLongDateFormat(String unixLongDateFormat);
    
    String getUnixLongDateFormatWeekday();
    void setUnixLongDateFormatWeekday(String unixLongDateFormatWeekday);
    
    String getUnixTimeFormat();
    void setUnixTimeFormat(String unixTimeFormat);
    
    String getUnixTimeFormatSeconds();
    void setUnixTimeFormatSeconds(String unixTimeFormatSeconds);
    
    String getShortDateSeparator();
    void setShortDateSeparator(String shortDateSeparator);
    
    String getTimeSeparator();
    void setTimeSeparator(String timeSeparator);
    
    String getIsDefault();
    void setIsDefault(String isDefault);
    
    String getSortOrder();
    void setSortOrder(String sortOrder);
    
}
