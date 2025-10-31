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
import com.echothree.control.user.employee.common.edit.EmployeeTypeDescriptionEdit;
import com.echothree.control.user.employee.common.form.EditEmployeeTypeDescriptionForm;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.EmployeeTypeDescriptionSpec;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EditEmployeeTypeDescriptionCommand
        extends BaseEditCommand<EmployeeTypeDescriptionSpec, EmployeeTypeDescriptionEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.EmployeeType.name(), SecurityRoles.Description.name())
                        )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EmployeeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }
    
    /** Creates a new instance of EditEmployeeTypeDescriptionCommand */
    public EditEmployeeTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var result = EmployeeResultFactory.getEditEmployeeTypeDescriptionResult();
        var employeeTypeName = spec.getEmployeeTypeName();
        var employeeType = employeeControl.getEmployeeTypeByName(employeeTypeName);
        
        if(employeeType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);
            
            if(language != null) {
                if(editMode.equals(EditMode.LOCK)) {
                    var employeeTypeDescription = employeeControl.getEmployeeTypeDescription(employeeType, language);
                    
                    if(employeeTypeDescription != null) {
                        result.setEmployeeTypeDescription(employeeControl.getEmployeeTypeDescriptionTransfer(getUserVisit(), employeeTypeDescription));
                        
                        if(lockEntity(employeeType)) {
                            var edit = EmployeeEditFactory.getEmployeeTypeDescriptionEdit();
                            
                            result.setEdit(edit);
                            edit.setDescription(employeeTypeDescription.getDescription());
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockFailed.name());
                        }
                        
                        result.setEntityLock(getEntityLockTransfer(employeeType));
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEmployeeTypeDescription.name());
                    }
                } else if(editMode.equals(EditMode.UPDATE)) {
                    var employeeTypeDescriptionValue = employeeControl.getEmployeeTypeDescriptionValueForUpdate(employeeType, language);
                    
                    if(employeeTypeDescriptionValue != null) {
                        if(lockEntityForUpdate(employeeType)) {
                            try {
                                var description = edit.getDescription();
                                
                                employeeTypeDescriptionValue.setDescription(description);
                                
                                employeeControl.updateEmployeeTypeDescriptionFromValue(employeeTypeDescriptionValue, getPartyPK());
                            } finally {
                                unlockEntity(employeeType);
                            }
                        } else {
                            addExecutionError(ExecutionErrors.EntityLockStale.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownEmployeeTypeDescription.name());
                    }
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownEmployeeTypeName.name(), employeeTypeName);
        }
        
        return result;
    }
    
}
