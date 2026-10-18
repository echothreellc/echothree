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
import com.echothree.control.user.employee.common.edit.TerminationTypeDescriptionEdit;
import com.echothree.control.user.employee.common.result.EditTerminationTypeDescriptionResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.TerminationTypeDescriptionSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.employee.server.logic.TerminationTypeLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.employee.server.entity.TerminationType;
import com.echothree.model.data.employee.server.entity.TerminationTypeDescription;
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
public class EditTerminationTypeDescriptionCommand
        extends BaseAbstractEditCommand<TerminationTypeDescriptionSpec, TerminationTypeDescriptionEdit, EditTerminationTypeDescriptionResult, TerminationTypeDescription, TerminationType> {

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

        SPEC_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("TerminationTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
        );

        EDIT_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
        );
    }

    @Inject
    EmployeeControl employeeControl;

    @Inject
    TerminationTypeLogic terminationTypeLogic;

    /** Creates a new instance of EditTerminationTypeDescriptionCommand */
    public EditTerminationTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditTerminationTypeDescriptionResult getResult() {
        return EmployeeResultFactory.getEditTerminationTypeDescriptionResult();
    }

    @Override
    public TerminationTypeDescriptionEdit getEdit() {
        return EmployeeEditFactory.getTerminationTypeDescriptionEdit();
    }

    @Override
    public TerminationTypeDescription getEntity(EditTerminationTypeDescriptionResult result) {
        return terminationTypeLogic.getTerminationTypeDescriptionBySpec(this, spec, editModeToEntityPermission(editMode));
    }

    @Override
    public TerminationType getLockEntity(TerminationTypeDescription terminationTypeDescription) {
        return terminationTypeDescription.getTerminationType();
    }

    @Override
    public void fillInResult(EditTerminationTypeDescriptionResult result, TerminationTypeDescription terminationTypeDescription) {
        result.setTerminationTypeDescription(employeeControl.getTerminationTypeDescriptionTransfer(getUserVisit(), terminationTypeDescription));
    }

    @Override
    public void doLock(TerminationTypeDescriptionEdit edit, TerminationTypeDescription terminationTypeDescription) {
        edit.setDescription(terminationTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(TerminationTypeDescription terminationTypeDescription) {
        var terminationTypeDescriptionValue = employeeControl.getTerminationTypeDescriptionValue(terminationTypeDescription);

        terminationTypeDescriptionValue.setDescription(edit.getDescription());

        employeeControl.updateTerminationTypeDescriptionFromValue(terminationTypeDescriptionValue, getPartyPK());
    }

}
