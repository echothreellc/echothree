// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.common.edit.EmployeeEditFactory;
import com.echothree.control.user.employee.common.edit.TerminationReasonDescriptionEdit;
import com.echothree.control.user.employee.common.form.EditTerminationReasonDescriptionForm;
import com.echothree.control.user.employee.common.result.EditTerminationReasonDescriptionResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.TerminationReasonDescriptionSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.employee.server.entity.TerminationReason;
import com.echothree.model.data.employee.server.entity.TerminationReasonDescription;
import com.echothree.model.data.employee.server.value.TerminationReasonDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
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

public class EditTerminationReasonDescriptionCommand
        extends BaseEditCommand<TerminationReasonDescriptionSpec, TerminationReasonDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(2);
        temp.add(new FieldDefinition("TerminationReasonName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditTerminationReasonDescriptionCommand */
    public EditTerminationReasonDescriptionCommand(UserVisitPK userVisitPK, EditTerminationReasonDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        EditTerminationReasonDescriptionResult result = EmployeeResultFactory.getEditTerminationReasonDescriptionResult();
        String terminationReasonName = spec.getTerminationReasonName();
        TerminationReason terminationReason = employeeControl.getTerminationReasonByName(terminationReasonName);
        
        if(terminationReason != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    TerminationReasonDescription terminationReasonDescription = employeeControl.getTerminationReasonDescription(terminationReason, language);
                    
                    if(terminationReasonDescription != null) {
                        result.setTerminationReasonDescription(employeeControl.getTerminationReasonDescriptionTransfer(getUserVisit(), terminationReasonDescription));
                        
                        if(lockEntity(terminationReason)) {
                            TerminationReasonDescriptionEdit edit = EmployeeEditFactory.getTerminationReasonDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(terminationReasonDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(terminationReason));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTerminationReasonDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    TerminationReasonDescriptionValue terminationReasonDescriptionValue = employeeControl.getTerminationReasonDescriptionValueForUpdate(terminationReason, language);
                    
                    if(terminationReasonDescriptionValue != null) {
                        if(lockEntityForUpdate(terminationReason)) {
                            try {
                                String description = edit.getDescription();
                                
                                terminationReasonDescriptionValue.setDescription(description);
                                
                                employeeControl.updateTerminationReasonDescriptionFromValue(terminationReasonDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(terminationReason);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTerminationReasonDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTerminationReasonName.name(), terminationReasonName);
        }
        
        return result;
    }
    
}
