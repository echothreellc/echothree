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
import com.echothree.model.control.workrequirement.common.transfer.WorkTimeTransfer;
import com.echothree.model.control.workrequirement.common.workflow.WorkTimeStatusConstants;
import com.echothree.model.control.workrequirement.server.control.WorkRequirementControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workrequirement.server.entity.WorkTime;
import com.echothree.util.server.persistence.Session;

public class WorkTimeTransferCache
        extends BaseWorkRequirementTransferCache<WorkTime, WorkTimeTransfer> {
    
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of WorkTimeTransferCache */
    public WorkTimeTransferCache(UserVisit userVisit, WorkRequirementControl workRequirementControl) {
        super(userVisit, workRequirementControl);

        setIncludeEntityInstance(true);
    }
    
    public WorkTimeTransfer getWorkTimeTransfer(WorkTime workTime) {
        var workTimeTransfer = get(workTime);
        
        if(workTimeTransfer == null) {
            var workTimeDetail = workTime.getLastDetail();
            var workRequirement = workRequirementControl.getWorkRequirementTransfer(userVisit, workTimeDetail.getWorkRequirement());
            var workTimeSequence = workTimeDetail.getWorkTimeSequence();
            var party = partyControl.getPartyTransfer(userVisit, workTimeDetail.getParty());
            var unformattedStartTime = workTimeDetail.getStartTime();
            var startTime = formatTypicalDateTime(userVisit, unformattedStartTime);
            var unformattedEndTime = workTimeDetail.getEndTime();
            var endTime = formatTypicalDateTime(userVisit, unformattedEndTime);

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(workTime.getPrimaryKey());
            var workTimeStatus = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    WorkTimeStatusConstants.Workflow_WORK_TIME_STATUS, entityInstance);

            workTimeTransfer = new WorkTimeTransfer(workRequirement, workTimeSequence, party, unformattedStartTime, startTime, unformattedEndTime, endTime,
                    workTimeStatus);
            put(userVisit, workTime, workTimeTransfer);
        }
        
        return workTimeTransfer;
    }
    
}
