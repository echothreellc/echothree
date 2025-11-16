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

package com.echothree.model.control.employee.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.employee.common.transfer.LeaveTransfer;
import com.echothree.model.control.employee.common.workflow.LeaveStatusConstants;
import com.echothree.model.control.employee.server.control.EmployeeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.employee.server.entity.Leave;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.UnitOfMeasureUtils;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class LeaveTransferCache
        extends BaseEmployeeTransferCache<Leave, LeaveTransfer> {

    EmployeeControl employeeControl = Session.getModelController(EmployeeControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    UomControl uomControl = Session.getModelController(UomControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    UnitOfMeasureKind timeUnitOfMeasureKind;
    UnitOfMeasureUtils unitOfMeasureUtils;

    /** Creates a new instance of LeaveTransferCache */
    protected LeaveTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
        
        timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
        unitOfMeasureUtils = UnitOfMeasureUtils.getInstance();
    }
    
    public LeaveTransfer getLeaveTransfer(UserVisit userVisit, Leave leave) {
        var leaveTransfer = get(leave);
        
        if(leaveTransfer == null) {
            var leaveDetail = leave.getLastDetail();
            var leaveName = leaveDetail.getLeaveName();
            var partyTransfer = partyControl.getPartyTransfer(userVisit, leaveDetail.getParty());
            var companyTransfer = partyControl.getCompanyTransfer(userVisit, leaveDetail.getCompanyParty());
            var leaveType = leaveDetail.getLeaveType();
            var leaveTypeTransfer = leaveType == null ? null : employeeControl.getLeaveTypeTransfer(userVisit, leaveType);
            var leaveReason = leaveDetail.getLeaveReason();
            var leaveReasonTransfer = leaveReason == null ? null : employeeControl.getLeaveReasonTransfer(userVisit, leaveReason);
            var unformattedStartTime = leaveDetail.getStartTime();
            var startTime = formatTypicalDateTime(userVisit, unformattedStartTime);
            var unformattedEndTime = leaveDetail.getEndTime();
            var endTime = formatTypicalDateTime(userVisit, unformattedEndTime);
            var unformattedTotalTime = leaveDetail.getTotalTime();
            var totalTime = unformattedTotalTime == null ? null : unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedTotalTime);

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(leave.getPrimaryKey());
            var leaveStatus = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    LeaveStatusConstants.Workflow_LEAVE_STATUS, entityInstance);

            leaveTransfer = new LeaveTransfer(leaveName, partyTransfer, companyTransfer, leaveTypeTransfer, leaveReasonTransfer, unformattedStartTime,
                    startTime, unformattedEndTime, endTime, unformattedTotalTime, totalTime, leaveStatus);
            put(userVisit, leave, leaveTransfer);
        }
        
        return leaveTransfer;
    }
    
}
