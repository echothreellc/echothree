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
import com.echothree.control.user.employee.common.edit.LeaveTypeDescriptionEdit;
import com.echothree.control.user.employee.common.form.EditLeaveTypeDescriptionForm;
import com.echothree.control.user.employee.common.result.EditLeaveTypeDescriptionResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.LeaveTypeDescriptionSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.employee.server.entity.LeaveType;
import com.echothree.model.data.employee.server.entity.LeaveTypeDescription;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.EditMode;
import com.echothree.util.server.control.BaseAbstractEditCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditLeaveTypeDescriptionCommand
        extends BaseAbstractEditCommand<LeaveTypeDescriptionSpec, LeaveTypeDescriptionEdit, EditLeaveTypeDescriptionResult, LeaveTypeDescription, LeaveType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LeaveType.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LeaveTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditLeaveTypeDescriptionCommand */
    public EditLeaveTypeDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLeaveTypeDescriptionResult getResult() {
        return EmployeeResultFactory.getEditLeaveTypeDescriptionResult();
    }

    @Override
    public LeaveTypeDescriptionEdit getEdit() {
        return EmployeeEditFactory.getLeaveTypeDescriptionEdit();
    }

    @Override
    public LeaveTypeDescription getEntity(EditLeaveTypeDescriptionResult result) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        LeaveTypeDescription leaveTypeDescription = null;
        var leaveTypeName = spec.getLeaveTypeName();
        var leaveType = employeeControl.getLeaveTypeByName(leaveTypeName);

        if(leaveType != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    leaveTypeDescription = employeeControl.getLeaveTypeDescription(leaveType, language);
                } else { // EditMode.UPDATE
                    leaveTypeDescription = employeeControl.getLeaveTypeDescriptionForUpdate(leaveType, language);
                }

                if(leaveTypeDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownLeaveTypeDescription.name(), leaveTypeName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownLeaveTypeName.name(), leaveTypeName);
        }

        return leaveTypeDescription;
    }

    @Override
    public LeaveType getLockEntity(LeaveTypeDescription leaveTypeDescription) {
        return leaveTypeDescription.getLeaveType();
    }

    @Override
    public void fillInResult(EditLeaveTypeDescriptionResult result, LeaveTypeDescription leaveTypeDescription) {
        var employeeControl = Session.getModelController(EmployeeControl.class);

        result.setLeaveTypeDescription(employeeControl.getLeaveTypeDescriptionTransfer(getUserVisit(), leaveTypeDescription));
    }

    @Override
    public void doLock(LeaveTypeDescriptionEdit edit, LeaveTypeDescription leaveTypeDescription) {
        edit.setDescription(leaveTypeDescription.getDescription());
    }

    @Override
    public void doUpdate(LeaveTypeDescription leaveTypeDescription) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var leaveTypeDescriptionValue = employeeControl.getLeaveTypeDescriptionValue(leaveTypeDescription);

        leaveTypeDescriptionValue.setDescription(edit.getDescription());

        employeeControl.updateLeaveTypeDescriptionFromValue(leaveTypeDescriptionValue, getPartyPK());
    }

}
