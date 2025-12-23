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

package com.echothree.util.server.validation.fieldtype;

import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.validation.Validator;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateFieldType
        extends BaseFieldType {
    
    /** Creates a new instance of DateFieldType */
    public DateFieldType(Validator validator, BaseForm baseForm, Messages validationMessages, String fieldValue, String[] splitFieldName, FieldDefinition fieldDefinition) {
        super(validator, baseForm, validationMessages, fieldValue, splitFieldName, fieldDefinition);
    }
    
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        
    @Override
    public String validate() {
        var dateTimeFormatDetail = getPreferredDateTimeFormat().getLastDetail();
        var zoneId = ZoneId.of(getPreferredTimeZone().getLastDetail().getJavaTimeZoneName());
        LocalDate localDate = null;
        
        if(fieldValue.equalsIgnoreCase("TODAY")) {
            var zonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(getSession().getStartTime()), zoneId);
            
            localDate = LocalDate.of(zonedDateTime.getYear(), zonedDateTime.getMonth(), zonedDateTime.getDayOfMonth());
        } else {
            var javaShortDateFormat = DateTimeFormatter.ofPattern(dateTimeFormatDetail.getJavaShortDateFormat()).withZone(zoneId);

            try {
                localDate = LocalDate.parse(fieldValue, javaShortDateFormat);
            } catch (DateTimeParseException dtpe) {
                validationMessages.add(fieldName, new Message(Validator.ERROR_INVALID_FORMAT));
            }
        }
        
        if(localDate != null) {
            
        }
        
        return localDate == null ? null : dateTimeFormatter.withZone(zoneId).format(localDate);
    }
    
}
