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
import com.google.common.base.Splitter;

public class Inet4AddressFieldType
        extends BaseFieldType {
    
    /** Creates a new instance of Inet4AddressFieldType */
    public Inet4AddressFieldType(Validator validator, BaseForm baseForm, Messages validationMessages, String fieldValue,
            String []splitFieldName, FieldDefinition fieldDefinition) {
        super(validator, baseForm, validationMessages, fieldValue, splitFieldName, fieldDefinition);
    }
    
    @Override
    public String validate() {
        var hadErrors = false;

        var m = Patterns.Inet4Address.matcher(fieldValue);
        if(!m.matches()) {
            validationMessages.add(fieldName, new Message(Validator.ERROR_INVALID_FORMAT));
            hadErrors = true;
        }
        
        if(!hadErrors) {
            String splitAddress[] = Splitter.on('.').trimResults().omitEmptyStrings().splitToList(fieldValue).toArray(new String[0]);
            var inet4Address0 = Integer.parseInt(splitAddress[0]);
            var inet4Address1 = Integer.parseInt(splitAddress[1]);
            var inet4Address2 = Integer.parseInt(splitAddress[2]);
            var inet4Address3 = Integer.parseInt(splitAddress[3]);
            
            if(inet4Address0 < 256 && inet4Address1 < 256 && inet4Address2 < 256 && inet4Address3 < 256) {
                var inet4Address = (inet4Address0 << 24) + (inet4Address1 << 16) + (inet4Address2 << 8) + inet4Address3;
                
                fieldValue = Integer.toString(inet4Address);
            } else {
                validationMessages.add(fieldName, new Message(Validator.ERROR_INVALID_FORMAT));
                hadErrors = true;
            }
        }
        
        return hadErrors? null: fieldValue;
    }
    
}
