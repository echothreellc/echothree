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

import java.time.LocalDate;

public class DateFormatter {
    
    private final java.time.format.DateTimeFormatter inDateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd");
    private final java.time.format.DateTimeFormatter outDateTimeFormatter;

    /** Creates a new instance of DateFormatter */
    public DateFormatter(String pattern) {
        outDateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern(pattern);
    }
    
    public String format(Integer date) {
        String result = null;
        
        if(date != null) {
            var localDate = LocalDate.parse(date.toString(), inDateTimeFormatter);

            result = outDateTimeFormatter.format(localDate);
        }
        
        return result;
    }
    
}
