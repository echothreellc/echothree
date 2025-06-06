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

package com.echothree.model.control.workflow.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class WorkflowEntranceStepTransfer
        extends BaseTransfer {
    
    private WorkflowEntranceTransfer workflowEntrance;
    private WorkflowStepTransfer workflowStep;
    
    /** Creates a new instance of WorkflowEntranceStepTransfer */
    public WorkflowEntranceStepTransfer(WorkflowEntranceTransfer workflowEntrance, WorkflowStepTransfer workflowStep) {
        this.workflowEntrance = workflowEntrance;
        this.workflowStep = workflowStep;
    }
    
    public WorkflowEntranceTransfer getWorkflowEntrance() {
        return workflowEntrance;
    }
    
    public void setWorkflowEntrance(WorkflowEntranceTransfer workflowEntrance) {
        this.workflowEntrance = workflowEntrance;
    }
    
    public WorkflowStepTransfer getWorkflowStep() {
        return workflowStep;
    }
    
    public void setWorkflowStep(WorkflowStepTransfer workflowStep) {
        this.workflowStep = workflowStep;
    }
    
}
