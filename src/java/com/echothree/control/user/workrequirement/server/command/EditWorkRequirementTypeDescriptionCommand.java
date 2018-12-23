// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.workrequirement.common.result.EditWorkRequirementTypeDescriptionResult;
import com.echothree.control.user.workrequirement.common.result.WorkRequirementResultFactory;
import com.echothree.control.user.workrequirement.common.spec.WorkRequirementTypeDescriptionSpec;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.workeffort.server.WorkEffortControl;
import com.echothree.model.control.workrequirement.server.WorkRequirementControl;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workeffort.server.entity.WorkEffortType;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementType;
import com.echothree.model.data.workrequirement.server.entity.WorkRequirementTypeDescription;
import com.echothree.model.data.workrequirement.server.value.WorkRequirementTypeDescriptionValue;
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
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L)
        ));
    }
    
    /** Creates a new instance of EditWorkRequirementTypeDescriptionCommand */
    public EditWorkRequirementTypeDescriptionCommand(UserVisitPK userVisitPK, EditWorkRequirementTypeDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        WorkEffortControl workEffortControl = (WorkEffortControl)Session.getModelController(WorkEffortControl.class);
        EditWorkRequirementTypeDescriptionResult result = WorkRequirementResultFactory.getEditWorkRequirementTypeDescriptionResult();
        String workEffortTypeName = spec.getWorkEffortTypeName();
        WorkEffortType workEffortType = workEffortControl.getWorkEffortTypeByName(workEffortTypeName);
        
        if(workEffortType != null) {
            WorkRequirementControl workRequirementControl = (WorkRequirementControl)Session.getModelController(WorkRequirementControl.class);
            String workRequirementTypeName = spec.getWorkRequirementTypeName();
            WorkRequirementType workRequirementType = workRequirementControl.getWorkRequirementTypeByName(workEffortType, workRequirementTypeName);
            
            if(workRequirementType != null) {
                PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
                String languageIsoName = spec.getLanguageIsoName();
                Language language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        WorkRequirementTypeDescription workRequirementTypeDescription = workRequirementControl.getWorkRequirementTypeDescription(workRequirementType, language);
                        
                        if(workRequirementTypeDescription != null) {
                            result.setWorkRequirementTypeDescription(workRequirementControl.getWorkRequirementTypeDescriptionTransfer(getUserVisit(), workRequirementTypeDescription));
                            
                            if(lockEntity(workRequirementType)) {
                                WorkRequirementTypeDescriptionEdit edit = WorkRequirementEditFactory.getWorkRequirementTypeDescriptionEdit();
                                
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
                        WorkRequirementTypeDescriptionValue workRequirementTypeDescriptionValue = workRequirementControl.getWorkRequirementTypeDescriptionValueForUpdate(workRequirementType, language);
                        
                        if(workRequirementTypeDescriptionValue != null) {
                            if(lockEntityForUpdate(workRequirementType)) {
                                try {
                                    String description = edit.getDescription();
                                    
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
