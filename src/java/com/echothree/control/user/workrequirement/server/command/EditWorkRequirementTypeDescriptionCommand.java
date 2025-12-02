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

package com.echothree.control.user.workrequirement.server.command;

import com.echothree.control.user.workrequirement.common.edit.WorkRequirementEditFactory;
import com.echothree.control.user.workrequirement.common.edit.WorkRequirementTypeDescriptionEdit;
import com.echothree.control.user.workrequirement.common.form.EditWorkRequirementTypeDescriptionForm;
import com.echothree.control.user.workrequirement.common.result.WorkRequirementResultFactory;
import com.echothree.control.user.workrequirement.common.spec.WorkRequirementTypeDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
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
import javax.enterprise.context.Dependent;

@Dependent
public class EditWorkRequirementTypeDescriptionCommand
        extends BaseEditCommand<WorkRequirementTypeDescriptionSpec, WorkRequirementTypeDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WorkEffortTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("WorkRequirementTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of EditWorkRequirementTypeDescriptionCommand */
    public EditWorkRequirementTypeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var workEffortControl = Session.getModelController(WorkEffortControl.class);
        var result = WorkRequirementResultFactory.getEditWorkRequirementTypeDescriptionResult();
        var workEffortTypeName = spec.getWorkEffortTypeName();
        var workEffortType = workEffortControl.getWorkEffortTypeByName(workEffortTypeName);
        
        if(workEffortType != null) {
            var workRequirementControl = Session.getModelController(WorkRequirementControl.class);
            var workRequirementTypeName = spec.getWorkRequirementTypeName();
            var workRequirementType = workRequirementControl.getWorkRequirementTypeByName(workEffortType, workRequirementTypeName);
            
            if(workRequirementType != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        var workRequirementTypeDescription = workRequirementControl.getWorkRequirementTypeDescription(workRequirementType, language);
                        
                        if(workRequirementTypeDescription != null) {
                            result.setWorkRequirementTypeDescription(workRequirementControl.getWorkRequirementTypeDescriptionTransfer(getUserVisit(), workRequirementTypeDescription));
                            
                            if(lockEntity(workRequirementType)) {
                                var edit = WorkRequirementEditFactory.getWorkRequirementTypeDescriptionEdit();
                                
                                result.setEdit(edit);
                                edit.setDescription(workRequirementTypeDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(workRequirementType));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWorkRequirementTypeDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var workRequirementTypeDescriptionValue = workRequirementControl.getWorkRequirementTypeDescriptionValueForUpdate(workRequirementType, language);
                        
                        if(workRequirementTypeDescriptionValue != null) {
                            if(lockEntityForUpdate(workRequirementType)) {
                                try {
                                    var description = edit.getDescription();
                                    
                                    workRequirementTypeDescriptionValue.setDescription(description);
                                    
                                    workRequirementControl.updateWorkRequirementTypeDescriptionFromValue(workRequirementTypeDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(workRequirementType);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWorkRequirementTypeDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkRequirementTypeName.name(), workRequirementTypeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkEffortTypeName.name(), workEffortTypeName);
        }
        
        return result;
    }
    
}
