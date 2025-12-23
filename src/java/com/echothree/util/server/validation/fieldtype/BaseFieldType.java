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

import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.party.server.entity.DateTimeFormat;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.TimeZone;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.ThreadSession;
import com.echothree.util.server.validation.Validator;
import com.google.common.base.Splitter;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseFieldType {
    
    Validator validator;
    BaseForm baseForm;
    Messages validationMessages;
    String fieldValue;
    String []splitFieldName;
    FieldDefinition fieldDefinition;
    
    String fieldName;
    
    /** Creates a new instance of BaseFieldType */
    public BaseFieldType(Validator validator, BaseForm baseForm, Messages validationMessages, String fieldValue,
            String []splitFieldName, FieldDefinition fieldDefinition) {
        this.validator = validator;
        this.baseForm = baseForm;
        this.validationMessages = validationMessages;
        this.fieldValue = fieldValue;
        this.splitFieldName = splitFieldName;
        this.fieldDefinition = fieldDefinition;
        
        fieldName = splitFieldName[0];
    }
    
    public Session getSession() {
        return ThreadSession.currentSession();
    }
    
    public Map<String, String> getSplitFieldNameParameters() {
        var length = splitFieldName.length;
        Map<String, String> parameters = new HashMap<>(length - 1);
        
        for(var i = 1; i < length ; i++) {
            var split = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(splitFieldName[i]).toArray(new String[0]);
            parameters.put(split[0], split[1]);
        }
        
        return parameters;
    }
    
    public String getParameterValue(Map<String, String> parameters, String name) {
        return (String)baseForm.get(parameters.get(name));
    }
    
    public Language getPreferredLanguage() {
        return validator.getBaseCommand().getPreferredLanguage();
    }
    
    public Currency getPreferredCurrency() {
        return validator.getBaseCommand().getPreferredCurrency();
    }
    
    public TimeZone getPreferredTimeZone() {
        return validator.getBaseCommand().getPreferredTimeZone();
    }
    
    public DateTimeFormat getPreferredDateTimeFormat() {
        return validator.getBaseCommand().getPreferredDateTimeFormat();
    }
    
    protected String formatExampleValue(Long exampleValue) {
        return exampleValue.toString();
    }
    
    private boolean checkRange(final long testValue, Long minimumValue, Long maximumValue) {
        var valid = true;
        
        if(minimumValue == null) {
            minimumValue = fieldDefinition.getMinimumValue();
        }
        
        if(maximumValue == null) {
            maximumValue = fieldDefinition.getMaximumValue();
        }
        
        if(minimumValue != null) {
            if(testValue < minimumValue) {
                validationMessages.add(fieldName, new Message(Validator.ERROR_MINIMUM_VALUE, formatExampleValue(minimumValue)));
                valid = false;
            }
        }

        if(maximumValue != null) {
            if(testValue > maximumValue) {
                validationMessages.add(fieldName, new Message(Validator.ERROR_MAXIMUM_VALUE, formatExampleValue(maximumValue)));
                valid = false;
            }
        }
        
        return valid;
    }

    protected boolean isValidSignedInteger(final Long minimumValue, final Long maximumValue) {
        boolean valid;
        
        if(fieldValue.equalsIgnoreCase("MAX_VALUE")) {
            fieldValue = maximumValue == null ? Integer.toString(Integer.MAX_VALUE) : maximumValue.toString();
        } else if(fieldValue.equalsIgnoreCase("MIN_VALUE")) {
            fieldValue = minimumValue == null ? Integer.toString(Integer.MIN_VALUE) : minimumValue.toString();
        }
        
        try {
            var testInteger = Integer.valueOf(fieldValue);

            valid = checkRange(testInteger, minimumValue, maximumValue);
            fieldValue = testInteger.toString();
        } catch(NumberFormatException nfe) {
            validationMessages.add(fieldName, new Message(Validator.ERROR_INVALID_FORMAT));
            valid = false;
        }
        
        return valid;
    }
    
    protected boolean isValidUnsignedInteger(final Long minimumValue, final Long maximumValue) {
        boolean valid;
        
        if(fieldValue.equalsIgnoreCase("MAX_VALUE")) {
            fieldValue = maximumValue == null ? Integer.toString(Integer.MAX_VALUE) : maximumValue.toString();
        }

        try {
            var testInteger = Integer.valueOf(fieldValue);

            valid = checkRange(testInteger, minimumValue, maximumValue);
            fieldValue = testInteger.toString();
        } catch(NumberFormatException nfe) {
            validationMessages.add(fieldName, new Message(Validator.ERROR_INVALID_FORMAT));
            valid = false;
        }
        
        return valid;
    }
    
    protected boolean isValidSignedLong(final Long minimumValue, final Long maximumValue) {
        boolean valid;
        
        if(fieldValue.equalsIgnoreCase("MAX_VALUE")) {
            fieldValue = maximumValue == null ? Long.toString(Long.MAX_VALUE) : maximumValue.toString();
        } else if(fieldValue.equalsIgnoreCase("MIN_VALUE")) {
            fieldValue = minimumValue == null ? Long.toString(Long.MIN_VALUE) : minimumValue.toString();
        }
        
        try {
            var testLong = Long.valueOf(fieldValue);

            valid = checkRange(testLong, minimumValue, maximumValue);
            fieldValue = testLong.toString();
        } catch(NumberFormatException nfe) {
            validationMessages.add(fieldName, new Message(Validator.ERROR_INVALID_FORMAT));
            valid = false;
        }
        
        return valid;
    }
    
    protected boolean isValidUnsignedLong(final Long minimumValue, final Long maximumValue) {
        boolean valid;
        
        if(fieldValue.equalsIgnoreCase("MAX_VALUE")) {
            fieldValue = maximumValue == null ? Long.toString(Long.MAX_VALUE) : maximumValue.toString();
        }

        try {
            var testLong = Long.valueOf(fieldValue);

            valid = checkRange(testLong, minimumValue, maximumValue);
            fieldValue = testLong.toString();
        } catch(NumberFormatException nfe) {
            validationMessages.add(fieldName, new Message(Validator.ERROR_INVALID_FORMAT));
            valid = false;
        }
        
        return valid;
    }
    
    public abstract String validate();
    
}
