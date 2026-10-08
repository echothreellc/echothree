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

package com.echothree.control.user.core.server.command;

import com.echothree.control.user.core.common.form.DecryptForm;
import com.echothree.control.user.core.common.result.CoreResultFactory;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.exception.DecryptionBadPaddingException;
import com.echothree.util.common.exception.UnknownEntityEncryptionKeyException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.EncryptionUtils;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DecryptCommand
        extends BaseSimpleCommand<DecryptForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EntityEncryptionKeyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("InitializationVector", FieldType.INITIALIZATION_VECTOR, true, null, null),
                new FieldDefinition("Value", FieldType.STRING, true, null, null)
        );
    }

    @Inject
    EncryptionUtils encryptionUtils;

    /** Creates a new instance of DecryptCommand */
    public DecryptCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var result = CoreResultFactory.getDecryptResult();
        var entityEncryptionKeyName = form.getEntityEncryptionKeyName();
        var initializationVector = form.getInitializationVector();
        var value = form.getValue();

        try {
            result.setValue(encryptionUtils.decrypt(entityEncryptionKeyName, initializationVector, true, value));
        } catch(UnknownEntityEncryptionKeyException e) {
            addExecutionError(ExecutionErrors.UnknownEntityEncryptionKeyName.name(), entityEncryptionKeyName);
        } catch(DecryptionBadPaddingException e) {
            addExecutionError(ExecutionErrors.UnableToDecryptValue.name(), entityEncryptionKeyName);
        }

        return result;
    }
    
}
