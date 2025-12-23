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
import com.echothree.util.server.validation.Patterns;
import com.echothree.util.server.validation.Validator;

public class HostNameFieldType
        extends BaseFieldType {
    
    private static final long defaultMinimumLength = 1;
    private static final Long defaultMinimumLengthLong = defaultMinimumLength;
    
    private static final long defaultMaximumLength = 40;
    private static final Long defaultMaximumLengthLong = defaultMaximumLength;
    
    /** Creates a new instance of HostNameFieldType */
    public HostNameFieldType(Validator validator, BaseForm baseForm, Messages validationMessages, String fieldValue, String []splitFieldName, FieldDefinition fieldDefinition) {
        super(validator, baseForm, validationMessages, fieldValue, splitFieldName, fieldDefinition);
    }
    
    @Override
    public String validate() {
        var length = fieldValue.length();
        var hadErrors = false;

        var minimumValue = fieldDefinition.getMinimumValue();
        if(length < (minimumValue == null? defaultMinimumLength: minimumValue)) {
            validationMessages.add(fieldName, new Message(Validator.ERROR_MINIMUM_LENGTH, minimumValue == null? defaultMinimumLengthLong: minimumValue));
            hadErrors = true;
        }

        var maximumValue = fieldDefinition.getMaximumValue();
        if(length > (maximumValue == null? defaultMaximumLength: maximumValue)) {
            validationMessages.add(fieldName, new Message(Validator.ERROR_MAXIMUM_LENGTH, maximumValue == null? defaultMaximumLengthLong: maximumValue));
            hadErrors = true;
        }

        var m = Patterns.HostName.matcher(fieldValue);
        if(!m.matches()) {
            validationMessages.add(fieldName, new Message(Validator.ERROR_INVALID_FORMAT));
            hadErrors = true;
        }
        
        return hadErrors? null: fieldValue;
    }
    
}
