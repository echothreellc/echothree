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

package com.echothree.util.server.validation.fieldtype;

import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.validation.Validator;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeFieldType
        extends BaseFieldType {
    
    /** Creates a new instance of DateTimeFieldType */
    public DateTimeFieldType(Validator validator, BaseForm baseForm, Messages validationMessages, String fieldValue, String[] splitFieldName, FieldDefinition fieldDefinition) {
        super(validator, baseForm, validationMessages, fieldValue, splitFieldName, fieldDefinition);
    }
    
    @Override
    public String validate() {
        String result = null;

        if(fieldValue.equalsIgnoreCase("NOW")) {
            result = String.valueOf(getSession().getStartTime());
        } else {
            var dateTimeFormatDetail = getPreferredDateTimeFormat().getLastDetail();
            var zoneId = ZoneId.of(getPreferredTimeZone().getLastDetail().getJavaTimeZoneName());
            var dtf = DateTimeFormatter.ofPattern(dateTimeFormatDetail.getJavaShortDateFormat() + " " + dateTimeFormatDetail.getJavaTimeFormatSeconds()).withZone(zoneId);
            ZonedDateTime zdt = null;
            
            try {
                zdt = ZonedDateTime.parse(fieldValue, dtf);
            } catch(DateTimeParseException dtpe1) {
                try {
                    dtf = DateTimeFormatter.ofPattern(dateTimeFormatDetail.getJavaShortDateFormat() + " " + dateTimeFormatDetail.getJavaTimeFormat()).withZone(zoneId);
                    zdt = ZonedDateTime.parse(fieldValue, dtf);
                } catch(DateTimeParseException dtpe2) {
                    try {
                        dtf = DateTimeFormatter.ofPattern(dateTimeFormatDetail.getJavaShortDateFormat()).withZone(zoneId);
                        zdt = LocalDate.parse(fieldValue, dtf).atStartOfDay(zoneId);
                    } catch(DateTimeParseException dtpe3) {
                        validationMessages.add(fieldName, new Message(Validator.ERROR_INVALID_FORMAT));
                    }
                }
            }
            
            if(zdt != null) {
                result = Long.toString(zdt.toInstant().toEpochMilli());
            }
        }

        return result;
    }
    
}
