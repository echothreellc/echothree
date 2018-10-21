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

package com.echothree.control.user.user.server.command;

import com.echothree.control.user.user.remote.edit.RecoveryQuestionDescriptionEdit;
import com.echothree.control.user.user.remote.edit.UserEditFactory;
import com.echothree.control.user.user.remote.form.EditRecoveryQuestionDescriptionForm;
import com.echothree.control.user.user.remote.result.EditRecoveryQuestionDescriptionResult;
import com.echothree.control.user.user.remote.result.UserResultFactory;
import com.echothree.control.user.user.remote.spec.RecoveryQuestionDescriptionSpec;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.user.server.UserControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.RecoveryQuestion;
import com.echothree.model.data.user.server.entity.RecoveryQuestionDescription;
import com.echothree.model.data.user.server.value.RecoveryQuestionDescriptionValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
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
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of EditRecoveryQuestionDescriptionCommand */
    public EditRecoveryQuestionDescriptionCommand(UserVisitPK userVisitPK, EditRecoveryQuestionDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        UserControl userControl = getUserControl();
        EditRecoveryQuestionDescriptionResult result = UserResultFactory.getEditRecoveryQuestionDescriptionResult();
        String recoveryQuestionName = spec.getRecoveryQuestionName();
        RecoveryQuestion recoveryQuestion = userControl.getRecoveryQuestionByName(recoveryQuestionName);
        
        if(recoveryQuestion != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    RecoveryQuestionDescription recoveryQuestionDescription = userControl.getRecoveryQuestionDescription(recoveryQuestion, language);
                    
                    if(recoveryQuestionDescription != null) {
                        result.setRecoveryQuestionDescription(userControl.getRecoveryQuestionDescriptionTransfer(getUserVisit(), recoveryQuestionDescription));
                        
                        if(lockEntity(recoveryQuestion)) {
                            RecoveryQuestionDescriptionEdit edit = UserEditFactory.getRecoveryQuestionDescriptionEdit();
                            
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
                    RecoveryQuestionDescriptionValue recoveryQuestionDescriptionValue = userControl.getRecoveryQuestionDescriptionValueForUpdate(recoveryQuestion, language);
                    
                    if(recoveryQuestionDescriptionValue != null) {
                        if(lockEntityForUpdate(recoveryQuestion)) {
                            try {
                                String description = edit.getDescription();
                                
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
