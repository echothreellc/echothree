// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.util.remote.form.BaseForm;
import com.echothree.util.remote.message.Message;
import com.echothree.util.remote.message.Messages;
import com.echothree.util.server.validation.Patterns;
import com.echothree.util.server.validation.Validator;
import java.util.regex.Matcher;

public class EmailAddressFieldType
        extends BaseFieldType {
    
    /** Creates a new instance of EmailAddressFieldType */
    public EmailAddressFieldType(Validator validator, BaseForm baseForm, Messages validationMessages, String fieldValue, String[] splitFieldName, FieldDefinition fieldDefinition) {
        super(validator, baseForm, validationMessages, fieldValue, splitFieldName, fieldDefinition);
    }
    
    @Override
    public String validate() {
        int length = fieldValue.length();
        boolean hadErrors = false;
        
        if(length > 80) {
            validationMessages.add(fieldName, new Message(Validator.ERROR_MAXIMUM_LENGTH));
            hadErrors = true;
        } else {
            Matcher m = Patterns.EmailAddress.matcher(fieldValue.toLowerCase());
            if(!m.matches()) {
                validationMessages.add(fieldName, new Message(Validator.ERROR_INVALID_FORMAT));
                hadErrors = true;
            }
        }
        
        return hadErrors? null: fieldValue;
    }
    
}
