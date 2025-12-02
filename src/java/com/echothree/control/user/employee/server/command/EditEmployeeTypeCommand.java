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
import com.echothree.control.user.employee.common.edit.EmployeeTypeEdit;
import com.echothree.control.user.employee.common.result.EditEmployeeTypeResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.EmployeeTypeSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.employee.server.entity.EmployeeType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditEmployeeTypeCommand
        extends BaseAbstractEditCommand<EmployeeTypeSpec, EmployeeTypeEdit, EditEmployeeTypeResult, EmployeeType, EmployeeType> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EmployeeType.name(), SecurityRoles.Edit.name())
                ))
        ));
        
        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EmployeeTypeName", FieldType.ENTITY_NAME, true, null, null)
        );
        
        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EmployeeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
        );
    }
    
    /** Creates a new instance of EditEmployeeTypeCommand */
    public EditEmployeeTypeCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }
    
    @Override
    public EditEmployeeTypeResult getResult() {
        return EmployeeResultFactory.getEditEmployeeTypeResult();
    }

    @Override
    public EmployeeTypeEdit getEdit() {
        return EmployeeEditFactory.getEmployeeTypeEdit();
    }

    @Override
    public EmployeeType getEntity(EditEmployeeTypeResult result) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var employeeTypeName = spec.getEmployeeTypeName();
        var employeeType = employeeControl.getEmployeeTypeByName(employeeTypeName, editModeToEntityPermission(editMode));

        if(employeeType == null) {
            addExecutionError(ExecutionErrors.UnknownEmployeeTypeName.name(), employeeTypeName);
        }

        return employeeType;
    }

    @Override
    public EmployeeType getLockEntity(EmployeeType employeeType) {
        return employeeType;
    }

    @Override
    public void fillInResult(EditEmployeeTypeResult result, EmployeeType employeeType) {
        var employeeControl = Session.getModelController(EmployeeControl.class);

        result.setEmployeeType(employeeControl.getEmployeeTypeTransfer(getUserVisit(), employeeType));
    }

    @Override
    public void doLock(EmployeeTypeEdit edit, EmployeeType employeeType) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var employeeTypeDescription = employeeControl.getEmployeeTypeDescription(employeeType, getPreferredLanguage());
        var employeeTypeDetail = employeeType.getLastDetail();

        edit.setEmployeeTypeName(employeeTypeDetail.getEmployeeTypeName());
        edit.setIsDefault(employeeTypeDetail.getIsDefault().toString());
        edit.setSortOrder(employeeTypeDetail.getSortOrder().toString());

        if(employeeTypeDescription != null) {
            edit.setDescription(employeeTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(EmployeeType employeeType) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var employeeTypeName = edit.getEmployeeTypeName();
        var duplicateEmployeeType = employeeControl.getEmployeeTypeByName(employeeTypeName);

        if(duplicateEmployeeType != null && !employeeType.equals(duplicateEmployeeType)) {
            addExecutionError(ExecutionErrors.DuplicateEmployeeTypeName.name(), employeeTypeName);
        }
    }

    @Override
    public void doUpdate(EmployeeType employeeType) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var partyPK = getPartyPK();
        var employeeTypeDetailValue = employeeControl.getEmployeeTypeDetailValueForUpdate(employeeType);
        var employeeTypeDescription = employeeControl.getEmployeeTypeDescriptionForUpdate(employeeType, getPreferredLanguage());
        var description = edit.getDescription();

        employeeTypeDetailValue.setEmployeeTypeName(edit.getEmployeeTypeName());
        employeeTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        employeeTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        employeeControl.updateEmployeeTypeFromValue(employeeTypeDetailValue, partyPK);

        if(employeeTypeDescription == null && description != null) {
            employeeControl.createEmployeeTypeDescription(employeeType, getPreferredLanguage(), description, partyPK);
        } else {
            if(employeeTypeDescription != null && description == null) {
                employeeControl.deleteEmployeeTypeDescription(employeeTypeDescription, partyPK);
            } else {
                if(employeeTypeDescription != null && description != null) {
                    var employeeTypeDescriptionValue = employeeControl.getEmployeeTypeDescriptionValue(employeeTypeDescription);

                    employeeTypeDescriptionValue.setDescription(description);
                    employeeControl.updateEmployeeTypeDescriptionFromValue(employeeTypeDescriptionValue, partyPK);
                }
            }
        }
    }
    
}
