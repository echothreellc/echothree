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

package com.echothree.control.user.employee.server.command;

import com.echothree.control.user.employee.common.edit.EmployeeEditFactory;
import com.echothree.control.user.employee.common.edit.TerminationTypeDescriptionEdit;
import com.echothree.control.user.employee.common.form.EditTerminationTypeDescriptionForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.TerminationTypeDescriptionSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditTerminationTypeDescriptionCommand
        extends BaseEditCommand<TerminationTypeDescriptionSpec, TerminationTypeDescriptionEdit> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.TerminationType.name(), SecurityRoles.Description.name())
                ))
        ));

        List<FieldDefinition> temp = new ArrayList<>(2);
        temp.add(new FieldDefinition("TerminationTypeName", FieldType.ENTITY_NAME, true, null, null));
        temp.add(new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null));
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
        
        temp = new ArrayList<>(1);
        temp.add(new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L));
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(temp);
    }
    
    /** Creates a new instance of EditTerminationTypeDescriptionCommand */
    public EditTerminationTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var result = EmployeeResultFactory.getEditTerminationTypeDescriptionResult();
        var terminationTypeName = spec.getTerminationTypeName();
        var terminationType = employeeControl.getTerminationTypeByName(terminationTypeName);
        
        if(terminationType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var terminationTypeDescription = employeeControl.getTerminationTypeDescription(terminationType, language);
                    
                    if(terminationTypeDescription != null) {
                        result.setTerminationTypeDescription(employeeControl.getTerminationTypeDescriptionTransfer(getUserVisit(), terminationTypeDescription));
                        
                        if(lockEntity(terminationType)) {
                            var edit = EmployeeEditFactory.getTerminationTypeDescriptionEdit();
                            
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
                    var terminationTypeDescriptionValue = employeeControl.getTerminationTypeDescriptionValueForUpdate(terminationType, language);
                    
                    if(terminationTypeDescriptionValue != null) {
                        if(lockEntityForUpdate(terminationType)) {
                            try {
                                var description = edit.getDescription();
                                
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
