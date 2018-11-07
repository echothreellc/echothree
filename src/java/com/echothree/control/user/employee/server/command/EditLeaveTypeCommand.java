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
import com.echothree.control.user.employee.common.edit.LeaveTypeEdit;
import com.echothree.control.user.employee.common.form.EditLeaveTypeForm;
import com.echothree.control.user.employee.common.result.EditLeaveTypeResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.LeaveTypeSpec;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.data.employee.server.entity.LeaveType;
import com.echothree.model.data.employee.server.entity.LeaveTypeDescription;
import com.echothree.model.data.employee.server.entity.LeaveTypeDetail;
import com.echothree.model.data.employee.server.value.LeaveTypeDescriptionValue;
import com.echothree.model.data.employee.server.value.LeaveTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
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

public class EditLeaveTypeCommand
        extends BaseAbstractEditCommand<LeaveTypeSpec, LeaveTypeEdit, EditLeaveTypeResult, LeaveType, LeaveType> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyConstants.PartyType_UTILITY, null),
                new PartyTypeDefinition(PartyConstants.PartyType_EMPLOYEE, Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.LeaveType.name(), SecurityRoles.Edit.name())
                        )))
                )));

        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LeaveTypeName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LeaveTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("IsDefault", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("SortOrder", FieldType.SIGNED_INTEGER, true, null, null),
                new FieldDefinition("Description", FieldType.STRING, false, 1L, 80L)
                ));
    }

    /** Creates a new instance of EditLeaveTypeCommand */
    public EditLeaveTypeCommand(UserVisitPK userVisitPK, EditLeaveTypeForm form) {
        super(userVisitPK, form, COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLeaveTypeResult getResult() {
        return EmployeeResultFactory.getEditLeaveTypeResult();
    }

    @Override
    public LeaveTypeEdit getEdit() {
        return EmployeeEditFactory.getLeaveTypeEdit();
    }

    @Override
    public LeaveType getEntity(EditLeaveTypeResult result) {
        EmployeeControl employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
        LeaveType leaveType = null;
        String leaveTypeName = spec.getLeaveTypeName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            leaveType = employeeControl.getLeaveTypeByName(leaveTypeName);
        } else { // EditMode.UPDATE
            leaveType = employeeControl.getLeaveTypeByNameForUpdate(leaveTypeName);
        }

        if(leaveType != null) {
            result.setLeaveType(employeeControl.getLeaveTypeTransfer(getUserVisit(), leaveType));
        } else {
            addExecutionError(ExecutionErrors.UnknownLeaveTypeName.name(), leaveTypeName);
        }

        return leaveType;
    }

    @Override
    public LeaveType getLockEntity(LeaveType leaveType) {
        return leaveType;
    }

    @Override
    public void fillInResult(EditLeaveTypeResult result, LeaveType leaveType) {
        EmployeeControl employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);

        result.setLeaveType(employeeControl.getLeaveTypeTransfer(getUserVisit(), leaveType));
    }

    @Override
    public void doLock(LeaveTypeEdit edit, LeaveType leaveType) {
        EmployeeControl employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
        LeaveTypeDescription leaveTypeDescription = employeeControl.getLeaveTypeDescription(leaveType, getPreferredLanguage());
        LeaveTypeDetail leaveTypeDetail = leaveType.getLastDetail();

        edit.setLeaveTypeName(leaveTypeDetail.getLeaveTypeName());
        edit.setIsDefault(leaveTypeDetail.getIsDefault().toString());
        edit.setSortOrder(leaveTypeDetail.getSortOrder().toString());

        if(leaveTypeDescription != null) {
            edit.setDescription(leaveTypeDescription.getDescription());
        }
    }

    @Override
    public void canUpdate(LeaveType leaveType) {
        EmployeeControl employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
        String leaveTypeName = edit.getLeaveTypeName();
        LeaveType duplicateLeaveType = employeeControl.getLeaveTypeByName(leaveTypeName);

        if(duplicateLeaveType != null && !leaveType.equals(duplicateLeaveType)) {
            addExecutionError(ExecutionErrors.DuplicateLeaveTypeName.name(), leaveTypeName);
        }
    }

    @Override
    public void doUpdate(LeaveType leaveType) {
        EmployeeControl employeeControl = (EmployeeControl)Session.getModelController(EmployeeControl.class);
        PartyPK partyPK = getPartyPK();
        LeaveTypeDetailValue leaveTypeDetailValue = employeeControl.getLeaveTypeDetailValueForUpdate(leaveType);
        LeaveTypeDescription leaveTypeDescription = employeeControl.getLeaveTypeDescriptionForUpdate(leaveType, getPreferredLanguage());
        String description = edit.getDescription();

        leaveTypeDetailValue.setLeaveTypeName(edit.getLeaveTypeName());
        leaveTypeDetailValue.setIsDefault(Boolean.valueOf(edit.getIsDefault()));
        leaveTypeDetailValue.setSortOrder(Integer.valueOf(edit.getSortOrder()));

        employeeControl.updateLeaveTypeFromValue(leaveTypeDetailValue, partyPK);

        if(leaveTypeDescription == null && description != null) {
            employeeControl.createLeaveTypeDescription(leaveType, getPreferredLanguage(), description, partyPK);
        } else if(leaveTypeDescription != null && description == null) {
            employeeControl.deleteLeaveTypeDescription(leaveTypeDescription, partyPK);
        } else if(leaveTypeDescription != null && description != null) {
            LeaveTypeDescriptionValue leaveTypeDescriptionValue = employeeControl.getLeaveTypeDescriptionValue(leaveTypeDescription);

            leaveTypeDescriptionValue.setDescription(description);
            employeeControl.updateLeaveTypeDescriptionFromValue(leaveTypeDescriptionValue, partyPK);
        }
    }

}
