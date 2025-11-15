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

import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationStepTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationStep;
import com.echothree.util.server.persistence.Session;

public class WorkflowDestinationStepTransferCache
        extends BaseWorkflowTransferCache<WorkflowDestinationStep, WorkflowDestinationStepTransfer> {

    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    /** Creates a new instance of WorkflowDestinationStepTransferCache */
    public WorkflowDestinationStepTransferCache() {
        super();
    }
    
    public WorkflowDestinationStepTransfer getWorkflowDestinationStepTransfer(UserVisit userVisit, WorkflowDestinationStep workflowDestinationStep) {
        var workflowDestinationStepTransfer = get(workflowDestinationStep);
        
        if(workflowDestinationStepTransfer == null) {
            var workflowDestination = workflowControl.getWorkflowDestinationTransfer(userVisit, workflowDestinationStep.getWorkflowDestination());
            var workflowStep = workflowControl.getWorkflowStepTransfer(userVisit, workflowDestinationStep.getWorkflowStep());
            
            workflowDestinationStepTransfer = new WorkflowDestinationStepTransfer(workflowDestination, workflowStep);
            put(userVisit, workflowDestinationStep, workflowDestinationStepTransfer);
        }
        
        return workflowDestinationStepTransfer;
    }
    
}
