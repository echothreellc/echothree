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

package com.echothree.model.control.workrequirement.server.transfer;

import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.remote.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.workrequirement.common.workflow.WorkTimeStatusConstants;
import com.echothree.model.control.workflow.remote.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.control.workrequirement.remote.transfer.WorkRequirementTransfer;
import com.echothree.model.control.workrequirement.remote.transfer.WorkTimeTransfer;
import com.echothree.model.control.workrequirement.server.WorkRequirementControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workrequirement.server.entity.WorkTime;
import com.echothree.model.data.workrequirement.server.entity.WorkTimeDetail;
import com.echothree.util.server.persistence.Session;

public class WorkTimeTransferCache
        extends BaseWorkRequirementTransferCache<WorkTime, WorkTimeTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    
    /** Creates a new instance of WorkTimeTransferCache */
    public WorkTimeTransferCache(UserVisit userVisit, WorkRequirementControl workRequirementControl) {
        super(userVisit, workRequirementControl);

        setIncludeEntityInstance(true);
    }
    
    public WorkTimeTransfer getWorkTimeTransfer(WorkTime workTime) {
        WorkTimeTransfer workTimeTransfer = get(workTime);
        
        if(workTimeTransfer == null) {
            WorkTimeDetail workTimeDetail = workTime.getLastDetail();
            WorkRequirementTransfer workRequirement = workRequirementControl.getWorkRequirementTransfer(userVisit, workTimeDetail.getWorkRequirement());
            Integer workTimeSequence = workTimeDetail.getWorkTimeSequence();
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, workTimeDetail.getParty());
            Long unformattedStartTime = workTimeDetail.getStartTime();
            String startTime = formatTypicalDateTime(unformattedStartTime);
            Long unformattedEndTime = workTimeDetail.getEndTime();
            String endTime = formatTypicalDateTime(unformattedEndTime);

            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(workTime.getPrimaryKey());
            WorkflowEntityStatusTransfer workTimeStatus = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    WorkTimeStatusConstants.Workflow_WORK_TIME_STATUS, entityInstance);

            workTimeTransfer = new WorkTimeTransfer(workRequirement, workTimeSequence, party, unformattedStartTime, startTime, unformattedEndTime, endTime,
                    workTimeStatus);
            put(workTime, workTimeTransfer);
        }
        
        return workTimeTransfer;
    }
    
}
