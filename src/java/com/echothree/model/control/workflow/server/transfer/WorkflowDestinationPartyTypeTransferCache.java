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
import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationPartyTypeTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationPartyType;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WorkflowDestinationPartyTypeTransferCache
        extends BaseWorkflowTransferCache<WorkflowDestinationPartyType, WorkflowDestinationPartyTypeTransfer> {
    
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    /** Creates a new instance of WorkflowDestinationPartyTypeTransferCache */
    protected WorkflowDestinationPartyTypeTransferCache() {
        super();
    }
    
    public WorkflowDestinationPartyTypeTransfer getWorkflowDestinationPartyTypeTransfer(UserVisit userVisit, WorkflowDestinationPartyType workflowDestinationPartyType) {
        var workflowDestinationPartyTypeTransfer = get(workflowDestinationPartyType);
        
        if(workflowDestinationPartyTypeTransfer == null) {
            var workflowDestination = workflowControl.getWorkflowDestinationTransfer(userVisit, workflowDestinationPartyType.getWorkflowDestination());
            var partyType = partyControl.getPartyTypeTransfer(userVisit, workflowDestinationPartyType.getPartyType());
            
            workflowDestinationPartyTypeTransfer = new WorkflowDestinationPartyTypeTransfer(workflowDestination, partyType);
            put(userVisit, workflowDestinationPartyType, workflowDestinationPartyTypeTransfer);
        }
        
        return workflowDestinationPartyTypeTransfer;
    }
    
}
