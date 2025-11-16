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

package com.echothree.model.control.workrequirement.server.transfer;

import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workrequirement.common.transfer.WorkAssignmentTransfer;
import com.echothree.model.control.workrequirement.common.workflow.WorkAssignmentStatusConstants;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workrequirement.server.entity.WorkAssignment;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WorkAssignmentTransferCache
        extends BaseWorkRequirementTransferCache<WorkAssignment, WorkAssignmentTransfer> {

    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    WorkRequirementControl workRequirementControl = Session.getModelController(WorkRequirementControl.class);

    /** Creates a new instance of WorkAssignmentTransferCache */
    public WorkAssignmentTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public WorkAssignmentTransfer getWorkAssignmentTransfer(UserVisit userVisit, WorkAssignment workAssignment) {
        var workAssignmentTransfer = get(workAssignment);
        
        if(workAssignmentTransfer == null) {
            var workAssignmentDetail = workAssignment.getLastDetail();
            var workRequirement = workRequirementControl.getWorkRequirementTransfer(userVisit, workAssignmentDetail.getWorkRequirement());
            var workAssignmentSequence = workAssignmentDetail.getWorkAssignmentSequence();
            var party = partyControl.getPartyTransfer(userVisit, workAssignmentDetail.getParty());
            var unformattedStartTime = workAssignmentDetail.getStartTime();
            var startTime = formatTypicalDateTime(userVisit, unformattedStartTime);
            var unformattedEndTime = workAssignmentDetail.getEndTime();
            var endTime = formatTypicalDateTime(userVisit, unformattedEndTime);

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(workAssignment.getPrimaryKey());
            var workAssignmentStatus = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    WorkAssignmentStatusConstants.Workflow_WORK_ASSIGNMENT_STATUS, entityInstance);

            workAssignmentTransfer = new WorkAssignmentTransfer(workRequirement, workAssignmentSequence, party, unformattedStartTime, startTime,
                    unformattedEndTime, endTime, workAssignmentStatus);
            put(userVisit, workAssignment, workAssignmentTransfer);
        }
        
        return workAssignmentTransfer;
    }
    
}
