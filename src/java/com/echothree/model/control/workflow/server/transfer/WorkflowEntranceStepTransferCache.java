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

import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceStepTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceStep;
import com.echothree.util.server.persistence.Session;

public class WorkflowEntranceStepTransferCache
        extends BaseWorkflowTransferCache<WorkflowEntranceStep, WorkflowEntranceStepTransfer> {

    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    /** Creates a new instance of WorkflowEntranceStepTransferCache */
    public WorkflowEntranceStepTransferCache() {
        super();
    }
    
    public WorkflowEntranceStepTransfer getWorkflowEntranceStepTransfer(UserVisit userVisit, WorkflowEntranceStep workflowEntranceStep) {
        var workflowEntranceStepTransfer = get(workflowEntranceStep);
        
        if(workflowEntranceStepTransfer == null) {
            var workflowEntrance = workflowControl.getWorkflowEntranceTransfer(userVisit, workflowEntranceStep.getWorkflowEntrance());
            var workflowStep = workflowControl.getWorkflowStepTransfer(userVisit, workflowEntranceStep.getWorkflowStep());
            
            workflowEntranceStepTransfer = new WorkflowEntranceStepTransfer(workflowEntrance, workflowStep);
            put(userVisit, workflowEntranceStep, workflowEntranceStepTransfer);
        }
        
        return workflowEntranceStepTransfer;
    }
    
}
