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

package com.echothree.control.user.user.server.command;

import com.echothree.control.user.user.common.edit.RecoveryQuestionEdit;
import com.echothree.control.user.user.common.edit.UserEditFactory;
import com.echothree.control.user.user.common.form.EditRecoveryQuestionForm;
import com.echothree.control.user.user.common.result.UserResultFactory;
import com.echothree.control.user.user.common.spec.RecoveryQuestionSpec;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditRecoveryQuestionCommand
        extends BaseEditCommand<RecoveryQuestionSpec, RecoveryQuestionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("RecoveryQuestionName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("RecoveryQuestionName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
            new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
            new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of EditRecoveryQuestionCommand */
    public EditRecoveryQuestionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var userControl = getUserControl();
        var result = UserResultFactory.getEditRecoveryQuestionResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            var recoveryQuestionName = spec.getRecoveryQuestionName();
            var recoveryQuestion = userControl.getRecoveryQuestionByName(recoveryQuestionName);
            
            if(recoveryQuestion != null) {
                result.setRecoveryQuestion(userControl.getRecoveryQuestionTransfer(getUserVisit(), recoveryQuestion));
                
                if(lockEntity(recoveryQuestion)) {
                    var recoveryQuestionDescription = userControl.getRecoveryQuestionDescription(recoveryQuestion, getPreferredLanguage());
                    var edit = UserEditFactory.getRecoveryQuestionEdit();
                    var recoveryQuestionDetail = recoveryQuestion.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setRecoveryQuestionName(recoveryQuestionDetail.getRecoveryQuestionName());
                    edit.setIsDefault(recoveryQuestionDetail.getIsDefault().toString());
                    edit.setSortOrder(recoveryQuestionDetail.getSortOrder().toString());
                    
                    if(recoveryQuestionDescription != null)
                        edit.setDescription(recoveryQuestionDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(recoveryQuestion));
            } else {
                addExecutionError(ExecutionErrors.UnknownRecoveryQuestionName.name(), recoveryQuestionName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            var recoveryQuestionName = spec.getRecoveryQuestionName();
            var recoveryQuestion = userControl.getRecoveryQuestionByNameForUpdate(recoveryQuestionName);
            
            if(recoveryQuestion != null) {
                recoveryQuestionName = edit.getRecoveryQuestionName();
                var duplicateRecoveryQuestion = userControl.getRecoveryQuestionByName(recoveryQuestionName);
                
                if(duplicateRecoveryQuestion == null || recoveryQuestion.equals(duplicateRecoveryQuestion)) {
                    if(lockEntityForUpdate(recoveryQuestion)) {
                        try {
                            var partyPK = getPartyPK();
                            var recoveryQuestionDetailValue = userControl.getRecoveryQuestionDetailValueForUpdate(recoveryQuestion);
                            var recoveryQuestionDescription = userControl.getRecoveryQuestionDescriptionForUpdate(recoveryQuestion, getPreferredLanguage());
                            var description = edit.getDescription();
                            
                            recoveryQuestionDetailValue.setRecoveryQuestionName(edit.getRecoveryQuestionName());
                            recoveryQuestionDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            recoveryQuestionDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                            
                            userControl.updateRecoveryQuestionFromValue(recoveryQuestionDetailValue, partyPK);
                            
                            if(recoveryQuestionDescription == null && description != null) {
                                userControl.createRecoveryQuestionDescription(recoveryQuestion, getPreferredLanguage(), description, partyPK);
                            } else if(recoveryQuestionDescription != null && description == null) {
                                userControl.deleteRecoveryQuestionDescription(recoveryQuestionDescription, partyPK);
                            } else if(recoveryQuestionDescription != null && description != null) {
                                var recoveryQuestionDescriptionValue = userControl.getRecoveryQuestionDescriptionValue(recoveryQuestionDescription);
                                
                                recoveryQuestionDescriptionValue.setDescription(description);
                                userControl.updateRecoveryQuestionDescriptionFromValue(recoveryQuestionDescriptionValue, partyPK);
                            }
                        } finally {
                            unlockEntity(recoveryQuestion);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateRecoveryQuestionName.name(), recoveryQuestionName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownRecoveryQuestionName.name(), recoveryQuestionName);
            }
        }
        
        return result;
    }
    
}
