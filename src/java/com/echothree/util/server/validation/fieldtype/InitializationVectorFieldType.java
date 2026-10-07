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
import com.echothree.util.server.validation.Validator;
import com.google.common.io.BaseEncoding;

public class InitializationVectorFieldType
        extends BaseFieldType {

    /** Creates a new instance of InitializationVectorFieldType */
    public InitializationVectorFieldType(Validator validator, BaseForm baseForm, Messages validationMessages, String fieldValue,
            String []splitFieldName, FieldDefinition fieldDefinition) {
        super(validator, baseForm, validationMessages, fieldValue, splitFieldName, fieldDefinition);
    }

    @Override
    public String validate() {
        var baseEncoding = BaseEncoding.base64();

        // A 16-byte IV has 22 unpadded or 24 padded Base64 characters.
        if(fieldValue.length() == 22 || fieldValue.length() == 24) {
            try {
                var iv = baseEncoding.decode(fieldValue);
                var encodedIv = baseEncoding.encode(iv);

                if(iv.length == 16 && (fieldValue.equals(encodedIv)
                        || fieldValue.equals(baseEncoding.omitPadding().encode(iv)))) {
                    return encodedIv;
                }
            } catch(IllegalArgumentException iae) {
                // Report malformed Base64 as a form validation error.
            }
        }

        validationMessages.add(fieldName, new Message(Validator.ERROR_INVALID_FORMAT));
        return null;
    }

}
