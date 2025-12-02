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
import com.echothree.control.user.employee.common.edit.LeaveReasonEdit;
import com.echothree.control.user.employee.common.form.EditLeaveReasonForm;
import com.echothree.control.user.employee.common.result.EditLeaveReasonResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.LeaveReasonSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.employee.server.entity.LeaveReason;
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
public class EditLeaveReasonCommand
        extends BaseAbstractEditCommand<LeaveReasonSpec, LeaveReasonEdit, EditLeaveReasonResult, LeaveReason, LeaveReason> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LeaveReason.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LeaveReasonName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LeaveReasonName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 132L)
                ));
    }

    /** Creates a new instance of EditLeaveReasonCommand */
    public EditLeaveReasonCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLeaveReasonResult getResult() {
        return EmployeeResultFactory.getEditLeaveReasonResult();
    }

    @Override
    public LeaveReasonEdit getEdit() {
        return EmployeeEditFactory.getLeaveReasonEdit();
    }

    @Override
    public LeaveReason getEntity(EditLeaveReasonResult result) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        LeaveReason leaveReason;
        var leaveReasonName = spec.getLeaveReasonName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            leaveReason = employeeControl.getLeaveReasonByName(leaveReasonName);
        } else { // EditMode.UPDATE
            leaveReason = employeeControl.getLeaveReasonByNameForUpdate(leaveReasonName);
        }

        if(leaveReason != null) {
            result.setLeaveReason(employeeControl.getLeaveReasonTransfer(getUserVisit(), leaveReason));
        } else {
            addExecutionError(ExecutionErrors.UnknownLeaveReasonName.name(), leaveReasonName);
        }

        return leaveReason;
    }

    @Override
    public LeaveReason getLockEntity(LeaveReason leaveReason) {
        return leaveReason;
    }

    @Override
    public void fillInResult(EditLeaveReasonResult result, LeaveReason leaveReason) {
        var employeeControl = Session.getModelController(EmployeeControl.class);

        result.setLeaveReason(employeeControl.getLeaveReasonTransfer(getUserVisit(), leaveReason));
    }

    @Override
    public void doLock(LeaveReasonEdit edit, LeaveReason leaveReason) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var leaveReasonDescription = employeeControl.getLeaveReasonDescription(leaveReason, getPreferredLanguage());
        var leaveReasonDetail = leaveReason.getLastDetail();

        edit.setLeaveReasonName(leaveReasonDetail.getLeaveReasonName());
        edit.setIsDefault(leaveReasonDetail.getIsDefault().toString());
        edit.setSortOrder(leaveReasonDetail.getSortOrder().toString());

        if(leaveReasonDescription != null) {
            edit.setDescription(leaveReasonDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(LeaveReason leaveReason) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var leaveReasonName = edit.getLeaveReasonName();
        var duplicateLeaveReason = employeeControl.getLeaveReasonByName(leaveReasonName);

        if(duplicateLeaveReason != null && !leaveReason.equals(duplicateLeaveReason)) {
            addExecutionError(ExecutionErrors.DuplicateLeaveReasonName.name(), leaveReasonName);
        }
    }

    @Override
    public void doUpdate(LeaveReason leaveReason) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var partyPK = getPartyPK();
        var leaveReasonDetailValue = employeeControl.getLeaveReasonDetailValueForUpdate(leaveReason);
        var leaveReasonDescription = employeeControl.getLeaveReasonDescriptionForUpdate(leaveReason, getPreferredLanguage());
        var description = edit.getDescription();

        leaveReasonDetailValue.setLeaveReasonName(edit.getLeaveReasonName());
        leaveReasonDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        leaveReasonDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        employeeControl.updateLeaveReasonFromValue(leaveReasonDetailValue, partyPK);

        if(leaveReasonDescription == null && description != null) {
            employeeControl.createLeaveReasonDescription(leaveReason, getPreferredLanguage(), description, partyPK);
        } else if(leaveReasonDescription != null && description == null) {
            employeeControl.deleteLeaveReasonDescription(leaveReasonDescription, partyPK);
        } else if(leaveReasonDescription != null && description != null) {
            var leaveReasonDescriptionValue = employeeControl.getLeaveReasonDescriptionValue(leaveReasonDescription);

            leaveReasonDescriptionValue.setDescription(description);
            employeeControl.updateLeaveReasonDescriptionFromValue(leaveReasonDescriptionValue, partyPK);
        }
    }

}
