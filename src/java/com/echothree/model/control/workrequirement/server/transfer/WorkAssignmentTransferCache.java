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

package com.echothree.model.control.workrequirement.server.transfer;

import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workrequirement.common.workflow.WorkAssignmentStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workrequirement.common.transfer.WorkAssignmentTransfer;
import com.echothree.model.control.workrequirement.common.transfer.WorkRequirementTransfer;
import com.echothree.model.control.workrequirement.server.WorkRequirementControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workrequirement.server.entity.WorkAssignment;
import com.echothree.model.data.workrequirement.server.entity.WorkAssignmentDetail;
import com.echothree.util.server.persistence.Session;

public class WorkAssignmentTransferCache
        extends BaseWorkRequirementTransferCache<WorkAssignment, WorkAssignmentTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of WorkAssignmentTransferCache */
    public WorkAssignmentTransferCache(UserVisit userVisit, WorkRequirementControl workRequirementControl) {
        super(userVisit, workRequirementControl);
        
        setIncludeEntityInstance(true);
    }
    
    public WorkAssignmentTransfer getWorkAssignmentTransfer(WorkAssignment workAssignment) {
        WorkAssignmentTransfer workAssignmentTransfer = get(workAssignment);
        
        if(workAssignmentTransfer == null) {
            WorkAssignmentDetail workAssignmentDetail = workAssignment.getLastDetail();
            WorkRequirementTransfer workRequirement = workRequirementControl.getWorkRequirementTransfer(userVisit, workAssignmentDetail.getWorkRequirement());
            Integer workAssignmentSequence = workAssignmentDetail.getWorkAssignmentSequence();
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, workAssignmentDetail.getParty());
            Long unformattedStartTime = workAssignmentDetail.getStartTime();
            String startTime = formatTypicalDateTime(unformattedStartTime);
            Long unformattedEndTime = workAssignmentDetail.getEndTime();
            String endTime = formatTypicalDateTime(unformattedEndTime);

            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(workAssignment.getPrimaryKey());
            WorkflowEntityStatusTransfer workAssignmentStatus = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    WorkAssignmentStatusConstants.Workflow_WORK_ASSIGNMENT_STATUS, entityInstance);

            workAssignmentTransfer = new WorkAssignmentTransfer(workRequirement, workAssignmentSequence, party, unformattedStartTime, startTime,
                    unformattedEndTime, endTime, workAssignmentStatus);
            put(workAssignment, workAssignmentTransfer);
        }
        
        return workAssignmentTransfer;
    }
    
}
