// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class WorkflowEntranceSelectorTransfer
        extends BaseTransfer {
    
    private WorkflowEntranceTransfer workflowEntrance;
    private SelectorTransfer selector;
    
    /** Creates a new instance of WorkflowEntranceSelectorTransfer */
    public WorkflowEntranceSelectorTransfer(WorkflowEntranceTransfer workflowEntrance, SelectorTransfer selector) {
        this.workflowEntrance = workflowEntrance;
        this.selector = selector;
    }
    
    public WorkflowEntranceTransfer getWorkflowEntrance() {
        return workflowEntrance;
    }
    
    public void setWorkflowEntrance(WorkflowEntranceTransfer workflowEntrance) {
        this.workflowEntrance = workflowEntrance;
    }
    
    public SelectorTransfer getSelector() {
        return selector;
    }
    
    public void setSelector(SelectorTransfer selector) {
        this.selector = selector;
    }
    
}
