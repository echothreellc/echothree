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
import com.echothree.control.user.employee.common.edit.LeaveEdit;
import com.echothree.control.user.employee.common.form.EditLeaveForm;
import com.echothree.control.user.employee.common.result.EditLeaveResult;
import com.echothree.control.user.employee.common.result.EmployeeResultFactory;
import com.echothree.control.user.employee.common.spec.LeaveSpec;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.employee.server.logic.LeaveLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.control.employee.common.workflow.LeaveStatusConstants;
import com.echothree.model.control.workflow.server.logic.WorkflowStepLogic;
import com.echothree.model.data.employee.server.entity.Leave;
import com.echothree.model.data.employee.server.entity.LeaveReason;
import com.echothree.model.data.employee.server.entity.LeaveType;
import com.echothree.model.data.party.server.entity.PartyCompany;
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
import com.echothree.util.server.string.DateUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class EditLeaveCommand
        extends BaseAbstractEditCommand<LeaveSpec, LeaveEdit, EditLeaveResult, Leave, Leave> {

    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> SPEC_FIELD_DEFINITIONS;
    private final static List<FieldDefinition> EDIT_FIELD_DEFINITIONS;

    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                    new SecurityRoleDefinition(SecurityRoleGroups.Leave.name(), SecurityRoles.Edit.name())
                    )))
                )));
        
        SPEC_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("LeaveName", FieldType.ENTITY_NAME, true, null, null)
                ));

        EDIT_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("StartTime", FieldType.DATE_TIME, true, null, null),
                new FieldDefinition("EndTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("TotalTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("TotalTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LeaveTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LeaveReasonName", FieldType.ENTITY_NAME, true, null, null)
                ));
    }

    /** Creates a new instance of EditLeaveCommand */
    public EditLeaveCommand() {
        super(COMMAND_SECURITY_DEFINITION, SPEC_FIELD_DEFINITIONS, EDIT_FIELD_DEFINITIONS);
    }

    @Override
    public EditLeaveResult getResult() {
        return EmployeeResultFactory.getEditLeaveResult();
    }

    @Override
    public LeaveEdit getEdit() {
        return EmployeeEditFactory.getLeaveEdit();
    }

    @Override
    public Leave getEntity(EditLeaveResult result) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        Leave leave;
        var leaveName = spec.getLeaveName();

        if(editMode.equals(EditMode.LOCK) || editMode.equals(EditMode.ABANDON)) {
            leave = employeeControl.getLeaveByName(leaveName);
        } else { // EditMode.UPDATE
            leave = employeeControl.getLeaveByNameForUpdate(leaveName);
        }

        if(leave == null) {
            addExecutionError(ExecutionErrors.UnknownLeave.name(), leaveName);
        }

        return leave;
    }

    @Override
    public Leave getLockEntity(Leave leave) {
        return leave;
    }

    @Override
    public void fillInResult(EditLeaveResult result, Leave leave) {
        var employeeControl = Session.getModelController(EmployeeControl.class);

        result.setLeave(employeeControl.getLeaveTransfer(getUserVisit(), leave));
    }

    Long endTime;
    
    @Override
    public void doLock(LeaveEdit edit, Leave leave) {
        var partyControl = Session.getModelController(PartyControl.class);
        var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();
        var leaveDetail = leave.getLastDetail();
        UnitOfMeasureTypeLogic.StringUnitOfMeasure stringUnitOfMeasure;
        
        endTime = leaveDetail.getEndTime();

        edit.setCompanyName(partyControl.getPartyCompany(leaveDetail.getCompanyParty()).getPartyCompanyName());
        edit.setStartTime(DateUtils.getInstance().formatTypicalDateTime(getUserVisit(), getPreferredDateTimeFormat(), leaveDetail.getStartTime()));
        edit.setEndTime(endTime == null ? null : DateUtils.getInstance().formatTypicalDateTime(getUserVisit(), getPreferredDateTimeFormat(), endTime));
        stringUnitOfMeasure = unitOfMeasureTypeLogic.unitOfMeasureToString(this, UomConstants.UnitOfMeasureKindUseType_TIME, leaveDetail.getTotalTime());
        edit.setTotalTimeUnitOfMeasureTypeName(stringUnitOfMeasure.getUnitOfMeasureTypeName());
        edit.setTotalTime(stringUnitOfMeasure.getValue());
        edit.setLeaveTypeName(leaveDetail.getLeaveType().getLastDetail().getLeaveTypeName());
        edit.setLeaveReasonName(leaveDetail.getLeaveReason().getLastDetail().getLeaveReasonName());
    }

    @Override
    public void canEdit(Leave leave) {
        if(WorkflowStepLogic.getInstance().isEntityInWorkflowStepsForUpdate(null, LeaveStatusConstants.Workflow_LEAVE_STATUS, leave,
                LeaveStatusConstants.WorkflowStep_APPROVED, LeaveStatusConstants.WorkflowStep_DENIED, LeaveStatusConstants.WorkflowStep_SUBMITTED).isEmpty()) {
            addExecutionError(ExecutionErrors.InvalidLeaveStatus.name(), leave.getLastDetail().getLeaveName());
        }
    }
    
    PartyCompany partyCompany;
    LeaveType leaveType;
    LeaveReason leaveReason;
    Long startTime;
    Long totalTime;

    @Override
    public void canUpdate(Leave leave) {
        var partyControl = Session.getModelController(PartyControl.class);
        var companyName = edit.getCompanyName();

        partyCompany = partyControl.getPartyCompanyByName(companyName);

        if(partyCompany != null) {
            var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();
            
            totalTime = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                    edit.getTotalTime(), edit.getTotalTimeUnitOfMeasureTypeName(),
                    null, ExecutionErrors.MissingRequiredTotalTime.name(), null, ExecutionErrors.MissingRequiredTotalTimeUnitOfMeasureTypeName.name(),
                    null, ExecutionErrors.UnknownTotalTimeUnitOfMeasureTypeName.name());

            if(!hasExecutionErrors()) {
                var employeeControl = Session.getModelController(EmployeeControl.class);
                var leaveTypeName = edit.getLeaveTypeName();

                leaveType = employeeControl.getLeaveTypeByName(leaveTypeName);

                if(leaveTypeName == null || leaveType != null) {
                    var leaveReasonName = edit.getLeaveReasonName();

                    leaveReason = employeeControl.getLeaveReasonByName(leaveReasonName);

                    if(leaveReasonName == null || leaveReason != null) {
                        var strEndTime = edit.getEndTime();

                        startTime = Long.valueOf(edit.getStartTime());
                        endTime = strEndTime == null ? null : Long.valueOf(strEndTime);

                        if(endTime != null && !(endTime > startTime)) {
                            addExecutionError(endTime.equals(startTime) ? ExecutionErrors.EndTimeEqualToStartTime.name() : ExecutionErrors.EndTimeBeforeStartTime.name());
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLeaveReasonName.name(), leaveReasonName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLeaveTypeName.name(), leaveTypeName);
                }
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
        }
    }

    @Override
    public void doUpdate(Leave leave) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var leaveDetailValue = employeeControl.getLeaveDetailValueForUpdate(leave);

        leaveDetailValue.setCompanyPartyPK(partyCompany.getPartyPK());
        leaveDetailValue.setLeaveTypePK(leaveType.getPrimaryKey());
        leaveDetailValue.setLeaveReasonPK(leaveReason.getPrimaryKey());
        leaveDetailValue.setStartTime(startTime);
        leaveDetailValue.setEndTime(endTime);
        leaveDetailValue.setTotalTime(totalTime);

        LeaveLogic.getInstance().updateLeaveFromValue(null, leaveDetailValue, getPartyPK());
    }

}
