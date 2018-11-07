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

import com.echothree.control.user.employee.common.edit.EmployeeEditFactory;
import com.echothree.control.user.employee.common.edit.EmployeeTypeEdit;
import com.echothree.control.user.employee.common.form.EditEmployeeTypeForm;
import com.echothree.control.user.employee.common.result.EditEmployeeTypeResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.EmployeeTypeSpec;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.employee.server.entity.EmployeeType;
import com.echothree.model.data.employee.server.entity.EmployeeTypeDescription;
import com.echothree.model.data.employee.server.entity.EmployeeTypeDetail;
import com.echothree.model.data.employee.server.value.EmployeeTypeDescriptionValue;
import com.echothree.model.data.employee.server.value.EmployeeTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditEmployeeTypeCommand
        extends BaseEditCommand<EmployeeTypeSpec, EmployeeTypeEdit> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.EmployeeType.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EmployeeTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));
        
        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("EmployeeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }
    
    /** Creates a new instance of EditEmployeeTypeCommand */
    public EditEmployeeTypeCommand(UserVisitPK userVisitPK, EditEmployeeTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    protected BaseResult execute() {
        EmployeeControl employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
        EditEmployeeTypeResult result = EmployeeResultFactory.getEditEmployeeTypeResult();
        
        if(editMode.equals(EditMode.LOCK)) {
            String employeeTypeName = spec.getEmployeeTypeName();
            EmployeeType employeeType = employeeControl.getEmployeeTypeByName(employeeTypeName);
            
            if(employeeType != null) {
                result.setEmployeeType(employeeControl.getEmployeeTypeTransfer(getUserVisit(), employeeType));
                
                if(lockEntity(employeeType)) {
                    EmployeeTypeDescription employeeTypeDescription = employeeControl.getEmployeeTypeDescription(employeeType, getPreferredLanguage());
                    EmployeeTypeEdit edit = EmployeeEditFactory.getEmployeeTypeEdit();
                    EmployeeTypeDetail employeeTypeDetail = employeeType.getLastDetail();
                    
                    result.setEdit(edit);
                    edit.setEmployeeTypeName(employeeTypeDetail.getEmployeeTypeName());
                    edit.setIsDefault(employeeTypeDetail.getIsDefault().toString());
                    edit.setSortOrder(employeeTypeDetail.getSortOrder().toString());
                    
                    if(employeeTypeDescription != null)
                        edit.setDescription(employeeTypeDescription.getDescription());
                } else {
                    addExecutionError(ExecutionErrors.EntityLockFailed.name());
                }
                
                result.setEntityLock(getEntityLockTransfer(employeeType));
            } else {
                addExecutionError(ExecutionErrors.UnknownEmployeeTypeName.name(), employeeTypeName);
            }
        } else if(editMode.equals(EditMode.UPDATE)) {
            String employeeTypeName = spec.getEmployeeTypeName();
            EmployeeType employeeType = employeeControl.getEmployeeTypeByNameForUpdate(employeeTypeName);
            
            if(employeeType != null) {
                employeeTypeName = edit.getEmployeeTypeName();
                EmployeeType duplicateEmployeeType = employeeControl.getEmployeeTypeByName(employeeTypeName);
                
                if(duplicateEmployeeType == null || employeeType.equals(duplicateEmployeeType)) {
                    if(lockEntityForUpdate(employeeType)) {
                        try {
                            PartyPK partyPK = getPartyPK();
                            EmployeeTypeDetailValue employeeTypeDetailValue = employeeControl.getEmployeeTypeDetailValueForUpdate(employeeType);
                            EmployeeTypeDescription employeeTypeDescription = employeeControl.getEmployeeTypeDescriptionForUpdate(employeeType, getPreferredLanguage());
                            String description = edit.getDescription();
                            
                            employeeTypeDetailValue.setEmployeeTypeName(edit.getEmployeeTypeName());
                            employeeTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
                            employeeTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));
                            
                            employeeControl.updateEmployeeTypeFromValue(employeeTypeDetailValue, partyPK);
                            
                            if(employeeTypeDescription == null && description != null) {
                                employeeControl.createEmployeeTypeDescription(employeeType, getPreferredLanguage(), description, partyPK);
                            } else if(employeeTypeDescription != null && description == null) {
                                employeeControl.deleteEmployeeTypeDescription(employeeTypeDescription, partyPK);
                            } else if(employeeTypeDescription != null && description != null) {
                                EmployeeTypeDescriptionValue employeeTypeDescriptionValue = employeeControl.getEmployeeTypeDescriptionValue(employeeTypeDescription);
                                
                                employeeTypeDescriptionValue.setDescription(description);
                                employeeControl.updateEmployeeTypeDescriptionFromValue(employeeTypeDescriptionValue, partyPK);
                            }
                        } finally {
                            unlockEntity(employeeType);
                        }
                    } else {
                        addExecutionError(ExecutionErrors.EntityLockStale.name());
                    }
                } else {
                    addExecutionError(ExecutionErrors.DuplicateEmployeeTypeName.name(), employeeTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownEmployeeTypeName.name(), employeeTypeName);
            }
            
            if(hasExecutionErrors()) {
                result.setEmployeeType(employeeControl.getEmployeeTypeTransfer(getUserVisit(), employeeType));
                result.setEntityLock(getEntityLockTransfer(employeeType));
            }
        }
        
        return result;
    }
    
}
