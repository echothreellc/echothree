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

package com.echothree.model.control.workflow.server.transfer;

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntrancePartyTypeTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrancePartyType;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WorkflowEntrancePartyTypeTransferCache
        extends BaseWorkflowTransferCache<WorkflowEntrancePartyType, WorkflowEntrancePartyTypeTransfer> {
    
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    /** Creates a new instance of WorkflowEntrancePartyTypeTransferCache */
    protected WorkflowEntrancePartyTypeTransferCache() {
        super();
    }
    
    public WorkflowEntrancePartyTypeTransfer getWorkflowEntrancePartyTypeTransfer(UserVisit userVisit, WorkflowEntrancePartyType workflowEntrancePartyType) {
        var workflowEntrancePartyTypeTransfer = get(workflowEntrancePartyType);
        
        if(workflowEntrancePartyTypeTransfer == null) {
            var workflowEntrance = workflowControl.getWorkflowEntranceTransfer(userVisit, workflowEntrancePartyType.getWorkflowEntrance());
            var partyType = partyControl.getPartyTypeTransfer(userVisit, workflowEntrancePartyType.getPartyType());
            
            workflowEntrancePartyTypeTransfer = new WorkflowEntrancePartyTypeTransfer(workflowEntrance, partyType);
            put(userVisit, workflowEntrancePartyType, workflowEntrancePartyTypeTransfer);
        }
        
        return workflowEntrancePartyTypeTransfer;
    }
    
}
