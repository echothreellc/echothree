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

package com.echothree.control.user.user.server.command;

import com.echothree.control.user.user.common.form.SetDefaultRecoveryQuestionForm;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetDefaultRecoveryQuestionCommand
        extends BaseSimpleCommand<SetDefaultRecoveryQuestionForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("RecoveryQuestionName", FieldType.ENTITY_NAME, true, null, null)
        ));
    }
    
    /** Creates a new instance of SetDefaultRecoveryQuestionCommand */
    public SetDefaultRecoveryQuestionCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var userControl = getUserControl();
        var recoveryQuestionName = form.getRecoveryQuestionName();
        var recoveryQuestionDetailValue = userControl.getRecoveryQuestionDetailValueByNameForUpdate(recoveryQuestionName);
        
        if(recoveryQuestionDetailValue != null) {
            recoveryQuestionDetailValue.setIsDefault(Boolean.TRUE);
            userControl.updateRecoveryQuestionFromValue(recoveryQuestionDetailValue, getPartyPK());
        } else {
            addExecutionError(ExecutionErrors.UnknownRecoveryQuestionName.name(), recoveryQuestionName);
        }
        
        return null;
    }
    
}
