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

package com.echothree.model.control.employee.server.logic;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.employee.common.exception.InvalidLeaveStatusException;
import com.echothree.model.control.employee.common.exception.UnknownLeaveReasonNameException;
import com.echothree.model.control.employee.common.exception.UnknownLeaveTypeNameException;
import com.echothree.model.control.employee.common.workflow.LeaveStatusConstants;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.employee.server.entity.Leave;
import com.echothree.model.data.employee.server.entity.LeaveReason;
import com.echothree.model.data.employee.server.entity.LeaveType;
import com.echothree.model.data.employee.server.value.LeaveDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class LeaveLogic
        extends BaseLogic {

    protected LeaveLogic() {
        super();
    }

    public static LeaveLogic getInstance() {
        return CDI.current().select(LeaveLogic.class).get();
    }
    
    public LeaveType getLeaveTypeByName(final ExecutionErrorAccumulator eea, final String leaveTypeName) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var leaveType = employeeControl.getLeaveTypeByName(leaveTypeName);

        if(leaveType == null) {
            handleExecutionError(UnknownLeaveTypeNameException.class, eea, ExecutionErrors.UnknownLeaveTypeName.name(), leaveTypeName);
        }

        return leaveType;
    }
    
    public LeaveReason getLeaveReasonByName(final ExecutionErrorAccumulator eea, final String leaveReasonName) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        var leaveReason = employeeControl.getLeaveReasonByName(leaveReasonName);

        if(leaveReason == null) {
            handleExecutionError(UnknownLeaveReasonNameException.class, eea, ExecutionErrors.UnknownLeaveReasonName.name(), leaveReasonName);
        }

        return leaveReason;
    }
    
    private void insertLeaveIntoWorkflow(final EntityInstance entityInstance, final WorkflowEntrance leaveStatus, final PartyPK createdBy) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        
        if(leaveStatus == null) {
            workflowControl.addEntityToWorkflowUsingNames(null, LeaveStatusConstants.Workflow_LEAVE_STATUS, LeaveStatusConstants.WorkflowEntrance_NEW_SUBMITTED,
                    entityInstance, null, null, createdBy);
        } else {
            workflowControl.addEntityToWorkflow(leaveStatus, entityInstance, null, null, createdBy);
        }
    }

    public Leave createLeave(final Session session, final Party party, final Party companyParty, final LeaveType leaveType, final LeaveReason leaveReason,
            final Long startTime, final Long endTime, final Long totalTime, final WorkflowEntrance leaveStatus, final PartyPK createdBy) {
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var employeeControl = Session.getModelController(EmployeeControl.class);

        var leave = employeeControl.createLeave(party, companyParty, leaveType, leaveReason, startTime, endTime, totalTime, createdBy);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(leave.getPrimaryKey());

        insertLeaveIntoWorkflow(entityInstance, leaveStatus, createdBy);

        return leave;
    }

    public void updateLeaveFromValue(final ExecutionErrorAccumulator eea, final LeaveDetailValue leaveDetailValue, final PartyPK updatedBy) {
        var workflowControl = Session.getModelController(WorkflowControl.class);
        var entityInstance = getEntityInstanceByBasePK(leaveDetailValue.getLeavePK());
        var workflowLogic = WorkflowLogic.getInstance();
        var workflow = workflowLogic.getWorkflowByName(null, LeaveStatusConstants.Workflow_LEAVE_STATUS);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdate(workflow, entityInstance);
        var workflowStepName = workflowEntityStatus.getWorkflowStep().getLastDetail().getWorkflowStepName();
        String workflowDestinationName = null;
        
        if(workflowStepName.equals(LeaveStatusConstants.WorkflowStep_APPROVED)) {
            workflowDestinationName = LeaveStatusConstants.WorkflowDestination_APPROVED_TO_REVISED;
        } else if(workflowStepName.equals(LeaveStatusConstants.WorkflowStep_DENIED)) {
            workflowDestinationName = LeaveStatusConstants.WorkflowDestination_DENIED_TO_REVISED;
        }
        
        if(workflowDestinationName == null && !workflowStepName.equals(LeaveStatusConstants.WorkflowStep_SUBMITTED)) {
            handleExecutionError(InvalidLeaveStatusException.class, eea, ExecutionErrors.InvalidLeaveStatus.name(), leaveDetailValue.getLeaveName(), workflowStepName);
        } else {
            var employeeControl = Session.getModelController(EmployeeControl.class);
            
            if(workflowDestinationName != null) {
                workflowControl.transitionEntityInWorkflowUsingNames(eea, workflowEntityStatus, workflowDestinationName, null, updatedBy);
            }
            
            employeeControl.updateLeaveFromValue(leaveDetailValue, updatedBy);
        }
    }
    
    public void deleteLeave(Leave leave, final BasePK deleteBy) {
        var employeeControl = Session.getModelController(EmployeeControl.class);
        
        employeeControl.deleteLeave(leave, deleteBy);
    }
    
}
