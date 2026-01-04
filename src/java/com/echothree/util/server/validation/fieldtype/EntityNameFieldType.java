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

import com.echothree.util.common.form.BaseForm;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.server.validation.Patterns;
import com.echothree.util.server.validation.Validator;

public class EntityNameFieldType
        extends BaseFieldType {
    
    private static final long defaultMinimumLength = 1;
    private static final Long defaultMinimumLengthLong = defaultMinimumLength;
    
    private static final long defaultMaximumLength = 40;
    private static final Long defaultMaximumLengthLong = defaultMaximumLength;
    
    /** Creates a new instance of EntityNameFieldType */
    public EntityNameFieldType(Validator validator, BaseForm baseForm, Messages validationMessages, String fieldValue, String []splitFieldName, FieldDefinition fieldDefinition) {
        super(validator, baseForm, validationMessages, fieldValue, splitFieldName, fieldDefinition);
    }
    
    @Override
    public String validate() {
        return !isValidName(validationMessages, fieldName, fieldDefinition.getMinimumValue(), fieldDefinition.getMaximumValue(), fieldValue)? null: fieldValue;
    }

    // Abbreviated implementation used by the Identify UC
    public static boolean isValidName(String fieldValue) {
        return isValidName(null, null, null, null, fieldValue);
    }

    private static boolean isValidName(Messages validationMessages, String fieldName, Long minimumValue, Long maximumValue, String fieldValue) {
        var length = fieldValue.length();
        var errorMinimumLength = false;
        var errorMaximumLength = false;
        var errorInvalidFormat = false;

        if(length < (minimumValue == null? defaultMinimumLength: minimumValue)) {
            errorMinimumLength = true;
        }

        if(length > (maximumValue == null? defaultMaximumLength: maximumValue)) {
            errorMaximumLength = true;
        }

        var m = Patterns.EntityName.matcher(fieldValue);
        if(!m.matches()) {
            errorInvalidFormat = true;
        }

        var validName = !(errorMinimumLength || errorMaximumLength || errorInvalidFormat);
        if(!validName && validationMessages != null) {
            if(errorMinimumLength) {
                validationMessages.add(fieldName, new Message(Validator.ERROR_MINIMUM_LENGTH, minimumValue == null? defaultMinimumLengthLong: minimumValue));
            }
            if(errorMaximumLength) {
                validationMessages.add(fieldName, new Message(Validator.ERROR_MAXIMUM_LENGTH, maximumValue == null? defaultMaximumLengthLong: maximumValue));
            }
            if(errorInvalidFormat) {
                validationMessages.add(fieldName, new Message(Validator.ERROR_INVALID_FORMAT));
            }
        }

        return validName;
    }
    
}
