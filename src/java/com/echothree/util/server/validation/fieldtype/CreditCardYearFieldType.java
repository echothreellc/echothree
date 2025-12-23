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

public class CreditCardYearFieldType
        extends BaseFieldType {
    
    /** Creates a new instance of CreditCardYearFieldType */
    public CreditCardYearFieldType(Validator validator, BaseForm baseForm, Messages validationMessages, String fieldValue,
            String[] splitFieldName, FieldDefinition fieldDefinition) {
        super(validator, baseForm, validationMessages, fieldValue, splitFieldName, fieldDefinition);
    }
    
    @Override
    public String validate() {
        var hadErrors = false;

        var m = Patterns.CreditCardYear.matcher(fieldValue);
        if(!m.matches()) {
            validationMessages.add(fieldName, new Message(Validator.ERROR_INVALID_FORMAT));
            hadErrors = true;
        } else {
            var creditCardYear = Integer.parseInt(fieldValue);

            if(creditCardYear < 100) {
                fieldValue = Integer.toString(creditCardYear + 2000);
            }
        }
        
        return hadErrors? null: fieldValue;
    }
    
}
