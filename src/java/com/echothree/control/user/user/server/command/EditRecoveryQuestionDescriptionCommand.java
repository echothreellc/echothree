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

import com.echothree.control.user.user.common.edit.RecoveryQuestionDescriptionEdit;
import com.echothree.control.user.user.common.edit.UserEditFactory;
import com.echothree.control.user.user.common.form.EditRecoveryQuestionDescriptionForm;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.control.user.user.common.spec.RecoveryQuestionDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EditRecoveryQuestionDescriptionCommand
        extends BaseEditCommand<RecoveryQuestionDescriptionSpec, RecoveryQuestionDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("RecoveryQuestionName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of EditRecoveryQuestionDescriptionCommand */
    public EditRecoveryQuestionDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var userControl = getUserControl();
        var result = UserResultFactory.getEditRecoveryQuestionDescriptionResult();
        var recoveryQuestionName = spec.getRecoveryQuestionName();
        var recoveryQuestion = userControl.getRecoveryQuestionByName(recoveryQuestionName);
        
        if(recoveryQuestion != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var recoveryQuestionDescription = userControl.getRecoveryQuestionDescription(recoveryQuestion, language);
                    
                    if(recoveryQuestionDescription != null) {
                        result.setRecoveryQuestionDescription(userControl.getRecoveryQuestionDescriptionTransfer(getUserVisit(), recoveryQuestionDescription));
                        
                        if(lockEntity(recoveryQuestion)) {
                            var edit = UserEditFactory.getRecoveryQuestionDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(recoveryQuestionDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(recoveryQuestion));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownRecoveryQuestionDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var recoveryQuestionDescriptionValue = userControl.getRecoveryQuestionDescriptionValueForUpdate(recoveryQuestion, language);
                    
                    if(recoveryQuestionDescriptionValue != null) {
                        if(lockEntityForUpdate(recoveryQuestion)) {
                            try {
                                var description = edit.getDescription();
                                
                                recoveryQuestionDescriptionValue.setDescription(description);
                                
                                userControl.updateRecoveryQuestionDescriptionFromValue(recoveryQuestionDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(recoveryQuestion);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownRecoveryQuestionDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownRecoveryQuestionName.name(), recoveryQuestionName);
        }
        
        return result;
    }
    
}
