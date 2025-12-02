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
import com.echothree.control.user.workeffort.common.edit.WorkEffortScopeDescriptionEdit;
import com.echothree.control.user.workeffort.common.form.EditWorkEffortScopeDescriptionForm;
import com.echothree.control.user.workeffort.common.result.WorkEffortResultFactory;
import com.echothree.control.user.workeffort.common.spec.WorkEffortScopeDescriptionSpec;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditWorkEffortScopeDescriptionCommand
        extends BaseEditCommand<WorkEffortScopeDescriptionSpec, WorkEffortScopeDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("WorkEffortTypeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("WorkEffortScopeName", FieldType.ENTITY_NAME, true, null, null),
            new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
            new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        ));
    }
    
    /** Creates a new instance of EditWorkEffortScopeDescriptionCommand */
    public EditWorkEffortScopeDescriptionCommand() {
        super(null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var workEffortControl = Session.getModelController(WorkEffortControl.class);
        var result = WorkEffortResultFactory.getEditWorkEffortScopeDescriptionResult();
        var workEffortTypeName = spec.getWorkEffortTypeName();
        var workEffortType = workEffortControl.getWorkEffortTypeByName(workEffortTypeName);
        
        if(workEffortType != null) {
            var workEffortScopeName = spec.getWorkEffortScopeName();
            var workEffortScope = workEffortControl.getWorkEffortScopeByName(workEffortType, workEffortScopeName);
            
            if(workEffortScope != null) {
                var partyControl = Session.getModelController(PartyControl.class);
                var languageIsoName = spec.getLanguageIsoName();
                var language = partyControl.getLanguageByIsoName(languageIsoName);
                
                if(language != null) {
                    if(editMode.equals(EditMode.LOCK)) {
                        var workEffortScopeDescription = workEffortControl.getWorkEffortScopeDescription(workEffortScope, language);
                        
                        if(workEffortScopeDescription != null) {
                            result.setWorkEffortScopeDescription(workEffortControl.getWorkEffortScopeDescriptionTransfer(getUserVisit(), workEffortScopeDescription));
                            
                            if(lockEntity(workEffortScope)) {
                                var edit = WorkEffortEditFactory.getWorkEffortScopeDescriptionEdit();
                                
                                result.setEdit(edit);
                                edit.setDescription(workEffortScopeDescription.getDescription());
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockFailed.name());
                            }
                            
                            result.setEntityLock(getEntityLockTransfer(workEffortScope));
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWorkEffortScopeDescription.name());
                        }
                    } else if(editMode.equals(EditMode.UPDATE)) {
                        var workEffortScopeDescriptionValue = workEffortControl.getWorkEffortScopeDescriptionValueForUpdate(workEffortScope, language);
                        
                        if(workEffortScopeDescriptionValue != null) {
                            if(lockEntityForUpdate(workEffortScope)) {
                                try {
                                    var description = edit.getDescription();
                                    
                                    workEffortScopeDescriptionValue.setDescription(description);
                                    
                                    workEffortControl.updateWorkEffortScopeDescriptionFromValue(workEffortScopeDescriptionValue, getPartyPK());
                                } finally {
                                    unlockEntity(workEffortScope);
                                }
                            } else {
                                addExecutionError(ExecutionErrors.EntityLockStale.name());
                            }
                        } else {
                            addExecutionError(ExecutionErrors.UnknownWorkEffortScopeDescription.name());
                        }
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownWorkEffortScopeName.name(), workEffortScopeName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownWorkEffortTypeName.name(), workEffortTypeName);
        }
        
        return result;
    }
    
}
