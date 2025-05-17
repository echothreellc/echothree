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

package com.echothree.control.user.workeffort.server.command;

import com.echothree.control.user.workeffort.common.edit.WorkEffortEditFactory;
import com.echothree.control.user.workeffort.common.edit.WorkEffortTypeDescriptionEdit;
import com.echothree.control.user.workeffort.common.form.EditWorkEffortTypeDescriptionForm;
import com.echothree.control.user.workeffort.common.result.WorkEffortResultFactory;
import com.echothree.control.user.workeffort.common.spec.WorkEffortTypeDescriptionSpec;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditWorkEffortTypeDescriptionCommand
        extends BaseEditCommand<WorkEffortTypeDescriptionSpec, WorkEffortTypeDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(2);
        temp.add(new FieldDefinition("WorkEffortTypeName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditWorkEffortTypeDescriptionCommand */
    public EditWorkEffortTypeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var workEffortControl = Session.getModelController(WorkEffortControl.class);
        var result = WorkEffortResultFactory.getEditWorkEffortTypeDescriptionResult();
        var workEffortTypeName = spec.getWorkEffortTypeName();
        var workEffortType = workEffortControl.getWorkEffortTypeByName(workEffortTypeName);
        
        if(workEffortType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var workEffortTypeDescription = workEffortControl.getWorkEffortTypeDescription(workEffortType, language);
                    
                    if(workEffortTypeDescription != null) {
                        result.setWorkEffortTypeDescription(workEffortControl.getWorkEffortTypeDescriptionTransfer(getUserVisit(), workEffortTypeDescription));
                        
                        if(lockEntity(workEffortType)) {
                            var edit = WorkEffortEditFactory.getWorkEffortTypeDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(workEffortTypeDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(workEffortType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownWorkEffortTypeDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var workEffortTypeDescriptionValue = workEffortControl.getWorkEffortTypeDescriptionValueForUpdate(workEffortType, language);
                    
                    if(workEffortTypeDescriptionValue != null) {
                        if(lockEntityForUpdate(workEffortType)) {
                            try {
                                var description = edit.getDescription();
                                
                                workEffortTypeDescriptionValue.setDescription(description);
                                
                                workEffortControl.updateWorkEffortTypeDescriptionFromValue(workEffortTypeDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(workEffortType);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownWorkEffortTypeDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkEffortTypeName.name(), workEffortTypeName);
        }
        
        return result;
    }
    
}
