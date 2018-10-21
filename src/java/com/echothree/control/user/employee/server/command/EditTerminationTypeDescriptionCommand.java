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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.remote.edit.EmployeeEditFactory;
import com.echothree.control.user.employee.remote.edit.TerminationTypeDescriptionEdit;
import com.echothree.control.user.employee.remote.form.EditTerminationTypeDescriptionForm;
import com.echothree.control.user.employee.remote.result.EditTerminationTypeDescriptionResult;
import com.echothree.control.user.employee.remote.result.EmployeeResultFactory;
import com.echothree.control.user.employee.remote.spec.TerminationTypeDescriptionSpec;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.employee.server.entity.TerminationType;
import com.echothree.model.data.employee.server.entity.TerminationTypeDescription;
import com.echothree.model.data.employee.server.value.TerminationTypeDescriptionValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.remote.command.BaseResult;
import com.echothree.util.remote.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditTerminationTypeDescriptionCommand
        extends BaseEditCommand<TerminationTypeDescriptionSpec, TerminationTypeDescriptionEdit> {
    
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        List<FieldDefinition> temp = new ArrayList<>(2);
        temp.add(new FieldDefinition("TerminationTypeName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 80L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditTerminationTypeDescriptionCommand */
    public EditTerminationTypeDescriptionCommand(UserVisitPK userVisitPK, EditTerminationTypeDescriptionForm form) {
        super(userVisitPK, form, null, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        EmployeeControl employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
        EditTerminationTypeDescriptionResult result = EmployeeResultFactory.getEditTerminationTypeDescriptionResult();
        String terminationTypeName = spec.getTerminationTypeName();
        TerminationType terminationType = employeeControl.getTerminationTypeByName(terminationTypeName);
        
        if(terminationType != null) {
            PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
            String languageIsoName = spec.getLanguageIsoName();
            Language language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    TerminationTypeDescription terminationTypeDescription = employeeControl.getTerminationTypeDescription(terminationType, language);
                    
                    if(terminationTypeDescription != null) {
                        result.setTerminationTypeDescription(employeeControl.getTerminationTypeDescriptionTransfer(getUserVisit(), terminationTypeDescription));
                        
                        if(lockEntity(terminationType)) {
                            TerminationTypeDescriptionEdit edit = EmployeeEditFactory.getTerminationTypeDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(terminationTypeDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(terminationType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTerminationTypeDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    TerminationTypeDescriptionValue terminationTypeDescriptionValue = employeeControl.getTerminationTypeDescriptionValueForUpdate(terminationType, language);
                    
                    if(terminationTypeDescriptionValue != null) {
                        if(lockEntityForUpdate(terminationType)) {
                            try {
                                String description = edit.getDescription();
                                
                                terminationTypeDescriptionValue.setDescription(description);
                                
                                employeeControl.updateTerminationTypeDescriptionFromValue(terminationTypeDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(terminationType);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownTerminationTypeDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownTerminationTypeName.name(), terminationTypeName);
        }
        
        return result;
    }
    
}
