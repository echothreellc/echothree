// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.employee.common.transfer.LeaveReasonTransfer;
import com.echothree.model.control.employee.common.transfer.LeaveTransfer;
import com.echothree.model.control.employee.common.transfer.LeaveTypeTransfer;
import com.echothree.model.control.employee.server.EmployeeControl;
import com.echothree.model.control.party.common.transfer.CompanyTransfer;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.uom.common.UomConstants;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.employee.common.workflow.LeaveStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.employee.server.entity.Leave;
import com.echothree.model.data.employee.server.entity.LeaveDetail;
import com.echothree.model.data.employee.server.entity.LeaveReason;
import com.echothree.model.data.employee.server.entity.LeaveType;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureKind;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.UnitOfMeasureUtils;

public class LeaveTransferCache
        extends BaseEmployeeTransferCache<Leave, LeaveTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    UomControl uomControl = (UomControl)Session.getModelController(UomControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);

    UnitOfMeasureKind timeUnitOfMeasureKind;
    UnitOfMeasureUtils unitOfMeasureUtils;

    /** Creates a new instance of LeaveTransferCache */
    public LeaveTransferCache(UserVisit userVisit, EmployeeControl employeeControl) {
        super(userVisit, employeeControl);
        
        setIncludeEntityInstance(true);
        
        timeUnitOfMeasureKind = uomControl.getUnitOfMeasureKindByUnitOfMeasureKindUseTypeUsingNames(UomConstants.UnitOfMeasureKindUseType_TIME);
        unitOfMeasureUtils = UnitOfMeasureUtils.getInstance();
    }
    
    public LeaveTransfer getLeaveTransfer(Leave leave) {
        LeaveTransfer leaveTransfer = get(leave);
        
        if(leaveTransfer == null) {
            LeaveDetail leaveDetail = leave.getLastDetail();
            String leaveName = leaveDetail.getLeaveName();
            PartyTransfer partyTransfer = partyControl.getPartyTransfer(userVisit, leaveDetail.getParty());
            CompanyTransfer companyTransfer = partyControl.getCompanyTransfer(userVisit, leaveDetail.getCompanyParty());
            LeaveType leaveType = leaveDetail.getLeaveType();
            LeaveTypeTransfer leaveTypeTransfer = leaveType == null ? null : employeeControl.getLeaveTypeTransfer(userVisit, leaveType);
            LeaveReason leaveReason = leaveDetail.getLeaveReason();
            LeaveReasonTransfer leaveReasonTransfer = leaveReason == null ? null : employeeControl.getLeaveReasonTransfer(userVisit, leaveReason);
            Long unformattedStartTime = leaveDetail.getStartTime();
            String startTime = formatTypicalDateTime(unformattedStartTime);
            Long unformattedEndTime = leaveDetail.getEndTime();
            String endTime = formatTypicalDateTime(unformattedEndTime);
            Long unformattedTotalTime = leaveDetail.getTotalTime();
            String totalTime = unformattedTotalTime == null ? null : unitOfMeasureUtils.formatUnitOfMeasure(userVisit, timeUnitOfMeasureKind, unformattedTotalTime);
            
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(leave.getPrimaryKey());
            WorkflowEntityStatusTransfer leaveStatus = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    LeaveStatusConstants.Workflow_LEAVE_STATUS, entityInstance);

            leaveTransfer = new LeaveTransfer(leaveName, partyTransfer, companyTransfer, leaveTypeTransfer, leaveReasonTransfer, unformattedStartTime,
                    startTime, unformattedEndTime, endTime, unformattedTotalTime, totalTime, leaveStatus);
            put(leave, leaveTransfer);
        }
        
        return leaveTransfer;
    }
    
}
