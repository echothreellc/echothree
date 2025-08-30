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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class UrlFieldType
        extends BaseFieldType {
    
    /** Creates a new instance of UriFieldType */
    public UrlFieldType(Validator validator, BaseForm baseForm, Messages validationMessages, String fieldValue, String[] splitFieldName, FieldDefinition fieldDefinition) {
        super(validator, baseForm, validationMessages, fieldValue, splitFieldName, fieldDefinition);
    }
    
    @Override
    public String validate() {
        var hadErrors = false;
        
        try {
            var uri = new URI(fieldValue);
            uri.toURL();
        } catch(URISyntaxException | MalformedURLException ex) {
            validationMessages.add(fieldName, new Message(Validator.ERROR_INVALID_FORMAT));
            hadErrors = true;
        }
        
        return hadErrors? null: fieldValue;
    }
    
}
