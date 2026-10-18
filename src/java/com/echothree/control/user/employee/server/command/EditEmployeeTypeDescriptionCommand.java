// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import com.echothree.control.user.employee.common.result.EditEmployeeTypeDescriptionResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.EmployeeTypeDescriptionSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.employee.server.logic.EmployeeTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.logic.LanguageLogic;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.employee.server.entity.EmployeeType;
import com.echothree.model.data.employee.server.entity.EmployeeTypeDescription;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class EditEmployeeTypeDescriptionCommand
        extends BaseAbstractEditCommand<EmployeeTypeDescriptionSpec, EmployeeTypeDescriptionEdit, EditEmployeeTypeDescriptionResult, EmployeeTypeDescription, EmployeeType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(List.of(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), List.of(
                        new SecurityRoleDefinition(SecurityRoleGroups.EmployeeType.name(), SecurityRoles.Description.name())
                ))
        ));

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("EmployeeTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    EmployeeControl employeeControl;

    @Inject
    EmployeeTypeLogic employeeTypeLogic;

    @Inject
    LanguageLogic languageLogic;

    /** Creates a new instance of EditEmployeeTypeDescriptionCommand */
    public EditEmployeeTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditEmployeeTypeDescriptionResult getResult() {
        return EmployeeResultFactory.getEditEmployeeTypeDescriptionResult();
    }

    @Override
    public EmployeeTypeDescriptionEdit getEdit() {
        return EmployeeEditFactory.getEmployeeTypeDescriptionEdit();
    }

    @Override
    public EmployeeTypeDescription getEntity(EditEmployeeTypeDescriptionResult result) {
        EmployeeTypeDescription employeeTypeDescription = null;
        var employeeTypeName = spec.getEmployeeTypeName();
        var employeeType = employeeTypeLogic.getEmployeeTypeByName(this, employeeTypeName);

        if(!hasExecutionErrors()) {
            var languageIsoName = spec.getLanguageIsoName();
            var language = languageLogic.getLanguageByName(this, languageIsoName);

            if(!hasExecutionErrors()) {
                employeeTypeDescription = employeeControl.getEmployeeTypeDescription(employeeType, language, editModeToEntityPermission(editMode));

                if(employeeTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownEmployeeTypeDescription.name(), employeeTypeName, languageIsoName);
                }
            }
        }

        return employeeTypeDescription;
    }

    @Override
    public EmployeeType getLockEntity(EmployeeTypeDescription employeeTypeDescription) {
        return employeeTypeDescription.getEmployeeType();
    }

    @Override
    public void fillInResult(EditEmployeeTypeDescriptionResult result, EmployeeTypeDescription employeeTypeDescription) {
        result.setEmployeeTypeDescription(employeeControl.getEmployeeTypeDescriptionTransfer(getUserVisit(), employeeTypeDescription));
    }

    @Override
    public void doLock(EmployeeTypeDescriptionEdit edit, EmployeeTypeDescription employeeTypeDescription) {
        edit.setDescription(employeeTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(EmployeeTypeDescription employeeTypeDescription) {
        var employeeTypeDescriptionValue = employeeControl.getEmployeeTypeDescriptionValue(employeeTypeDescription);

        employeeTypeDescriptionValue.setDescription(edit.getDescription());

        employeeControl.updateEmployeeTypeDescriptionFromValue(employeeTypeDescriptionValue, getPartyPK());
    }

}
