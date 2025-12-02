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

import com.echothree.control.user.employee.common.form.CreateLeaveForm;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.employee.server.logic.LeaveLogic;
import com.echothree.model.control.party.common.PartyTypes;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.security.common.SecurityRoleGroups;
import com.echothree.model.control.security.common.SecurityRoles;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.control.employee.common.workflow.LeaveStatusConstants;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowSecurityLogic;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.control.CommandSecurityDefinition;
import com.echothree.util.server.control.PartyTypeDefinition;
import com.echothree.util.server.control.SecurityRoleDefinition;
import com.echothree.util.server.persistence.Session;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class CreateLeaveCommand
        extends BaseSimpleCommand<CreateLeaveForm> {
    
    private final static CommandSecurityDefinition COMMAND_SECURITY_DEFINITION;
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        COMMAND_SECURITY_DEFINITION = new CommandSecurityDefinition(Collections.unmodifiableList(Arrays.asList(
                new PartyTypeDefinition(PartyTypes.UTILITY.name(), null),
                new PartyTypeDefinition(PartyTypes.EMPLOYEE.name(), Collections.unmodifiableList(Arrays.asList(
                        new SecurityRoleDefinition(SecurityRoleGroups.Leave.name(), SecurityRoles.Create.name())
                        )))
                )));
        
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("PartyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("CompanyName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LeaveTypeName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("LeaveReasonName", FieldType.ENTITY_NAME, true, null, null),
                new FieldDefinition("StartTime", FieldType.DATE_TIME, true, null, null),
                new FieldDefinition("EndTime", FieldType.DATE_TIME, false, null, null),
                new FieldDefinition("TotalTime", FieldType.UNSIGNED_LONG, false, null, null),
                new FieldDefinition("TotalTimeUnitOfMeasureTypeName", FieldType.ENTITY_NAME, false, null, null),
                new FieldDefinition("LeaveStatus", FieldType.ENTITY_NAME, false, null, null)
                ));
    }
    
    /** Creates a new instance of CreateLeaveCommand */
    public CreateLeaveCommand() {
        super(COMMAND_SECURITY_DEFINITION, FORM_FIELD_DEFINITIONS, false);
    }
    
    @Override
    protected BaseResult execute() {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyName = form.getPartyName();
        var party = partyControl.getPartyByName(partyName);

        if(party != null) {
            var companyName = form.getCompanyName();
            var partyCompany = partyControl.getPartyCompanyByName(companyName);

            if(partyCompany != null) {
                var employeeControl = Session.getModelController(EmployeeControl.class);
                var leaveTypeName = form.getLeaveTypeName();
                var leaveType = employeeControl.getLeaveTypeByName(leaveTypeName);

                if(leaveTypeName == null || leaveType != null) {
                    var leaveReasonName = form.getLeaveReasonName();
                    var leaveReason = employeeControl.getLeaveReasonByName(leaveReasonName);

                    if(leaveReasonName == null || leaveReason != null) {
                        var unitOfMeasureTypeLogic = UnitOfMeasureTypeLogic.getInstance();
                        var totalTime = unitOfMeasureTypeLogic.checkUnitOfMeasure(this, UomConstants.UnitOfMeasureKindUseType_TIME,
                                form.getTotalTime(), form.getTotalTimeUnitOfMeasureTypeName(),
                                null, ExecutionErrors.MissingRequiredTotalTime.name(), null, ExecutionErrors.MissingRequiredTotalTimeUnitOfMeasureTypeName.name(),
                                null, ExecutionErrors.UnknownTotalTimeUnitOfMeasureTypeName.name());

                        if(!hasExecutionErrors()) {
                            var createdBy = getPartyPK();
                            WorkflowEntrance leaveStatus = null;
                            var leaveStatusChoice = form.getLeaveStatus();

                            if(leaveStatusChoice != null) {
                                var workflowControl = Session.getModelController(WorkflowControl.class);
                                leaveStatus = workflowControl.getWorkflowEntranceUsingNames(this, LeaveStatusConstants.Workflow_LEAVE_STATUS,
                                        leaveStatusChoice);

                                if(!hasExecutionErrors()) {
                                    WorkflowSecurityLogic.getInstance().checkAddEntityToWorkflow(this, leaveStatus, createdBy);
                                }
                            }

                            if(!hasExecutionErrors()) {
                                var startTime = Long.valueOf(form.getStartTime());
                                var strEndTime = form.getEndTime();
                                var endTime = strEndTime == null ? null : Long.valueOf(strEndTime);

                                if(endTime == null || endTime > startTime) {
                                    LeaveLogic.getInstance().createLeave(session, party, partyCompany.getParty(), leaveType, leaveReason, startTime, endTime,
                                            totalTime, leaveStatus, createdBy);
                                } else {
                                    addExecutionError(endTime.equals(startTime) ? ExecutionErrors.EndTimeEqualToStartTime.name() : ExecutionErrors.EndTimeBeforeStartTime.name());
                                }
                            }
                        }
                    } else {
                        addExecutionError(ExecutionErrors.UnknownLeaveReasonName.name(), leaveReasonName);
                    }
                } else {
                    addExecutionError(ExecutionErrors.UnknownLeaveTypeName.name(), leaveTypeName);
                }
            } else {
                addExecutionError(ExecutionErrors.UnknownCompanyName.name(), companyName);
            }
        } else {
            addExecutionError(ExecutionErrors.UnknownPartyName.name(), partyName);
        }
        
        return null;
    }
    
}
