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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.DecryptForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.control.user.core.common.result.DecryptResult;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.EncryptionUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DecryptCommand
        extends BaseSimpleCommand<DecryptForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ExternalEntityTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("Value", FieldType.STRING, true, null, null)
                ));
    }
    
    /** Creates a new instance of DecryptCommand */
    public DecryptCommand(UserVisitPK userVisitPK, DecryptForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        DecryptResult result = CoreResultFactory.getDecryptResult();
        String externalEntityTypeName = form.getExternalEntityTypeName();
        String value = form.getValue();

        result.setValue(EncryptionUtils.getInstance().decrypt(externalEntityTypeName, null, Boolean.TRUE, value));

        return result;
    }
    
}
