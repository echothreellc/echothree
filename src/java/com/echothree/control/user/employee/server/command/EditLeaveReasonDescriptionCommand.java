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
import com.echothree.control.user.employee.common.edit.LeaveReasonDescriptionEdit;
import com.echothree.control.user.employee.common.form.EditLeaveReasonDescriptionForm;
import com.echothree.control.user.employee.common.result.EditLeaveReasonDescriptionResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.LeaveReasonDescriptionSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.employee.server.entity.LeaveReason;
import com.echothree.model.data.employee.server.entity.LeaveReasonDescription;
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
public class EditLeaveReasonDescriptionCommand
        extends BaseAbstractEditCommand<LeaveReasonDescriptionSpec, LeaveReasonDescriptionEdit, EditLeaveReasonDescriptionResult, LeaveReasonDescription, LeaveReason> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LeaveReason.name(), SecurityRoles.Description.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LeaveReasonName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LanguageIsoName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("Description", FieldType.STRING, true, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditLeaveReasonDescriptionCommand */
    public EditLeaveReasonDescriptionCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLeaveReasonDescriptionResult getResult() {
        return EmployeeResultFactory.getEditLeaveReasonDescriptionResult();
    }

    @Override
    public LeaveReasonDescriptionEdit getEdit() {
        return EmployeeEditFactory.getLeaveReasonDescriptionEdit();
    }

    @Override
    public LeaveReasonDescription getEntity(EditLeaveReasonDescriptionResult result) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        LeaveReasonDescription leaveReasonDescription = null;
        var leaveReasonName = spec.getLeaveReasonName();
        var leaveReason = employeeControl.getLeaveReasonByName(leaveReasonName);

        if(leaveReason != null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var languageIsoName = spec.getLanguageIsoName();
            var language = partyControl.getLanguageByIsoName(languageIsoName);

            if(language != null) {
                if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
                    leaveReasonDescription = employeeControl.getLeaveReasonDescription(leaveReason, language);
                } else { // EditMode.UPDATE
                    leaveReasonDescription = employeeControl.getLeaveReasonDescriptionForUpdate(leaveReason, language);
                }

                if(leaveReasonDescription == null) {
                    addExecutionError(ExecutionErrors.UnknownLeaveReasonDescription.name(), leaveReasonName, languageIsoName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownLanguageIsoName.name(), languageIsoName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownLeaveReasonName.name(), leaveReasonName);
        }

        return leaveReasonDescription;
    }

    @Override
    public LeaveReason getLockEntity(LeaveReasonDescription leaveReasonDescription) {
        return leaveReasonDescription.getLeaveReason();
    }

    @Override
    public void fillInResult(EditLeaveReasonDescriptionResult result, LeaveReasonDescription leaveReasonDescription) {
        var employeeControl = Session.getModelController(EmployeeControl.class);

        result.setLeaveReasonDescription(employeeControl.getLeaveReasonDescriptionTransfer(getUserVisit(), leaveReasonDescription));
    }

    @Override
    public void doLock(LeaveReasonDescriptionEdit edit, LeaveReasonDescription leaveReasonDescription) {
        edit.setDescription(leaveReasonDescription.getDescription());
    }

    @Override
    public void doUpdate(LeaveReasonDescription leaveReasonDescription) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var leaveReasonDescriptionValue = employeeControl.getLeaveReasonDescriptionValue(leaveReasonDescription);

        leaveReasonDescriptionValue.setDescription(edit.getDescription());

        employeeControl.updateLeaveReasonDescriptionFromValue(leaveReasonDescriptionValue, getPartyPK());
    }

}
