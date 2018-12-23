// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.workflow.common.transfer.WorkflowTypeTransfer;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowType;

public class WorkflowTypeTransferCache
        extends BaseWorkflowTransferCache<WorkflowType, WorkflowTypeTransfer> {
    
    /** Creates a new instance of WorkflowTypeTransferCache */
    public WorkflowTypeTransferCache(UserVisit userVisit, WorkflowControl workflowControl) {
        super(userVisit, workflowControl);
    }
    
    public WorkflowTypeTransfer getWorkflowTypeTransfer(WorkflowType workflowType) {
        WorkflowTypeTransfer workflowTypeTransfer = get(workflowType);
        
        if(workflowTypeTransfer == null) {
            String workflowTypeName = workflowType.getWorkflowTypeName();
            String description = workflowControl.getBestWorkflowTypeDescription(workflowType, getLanguage());
            
            workflowTypeTransfer = new WorkflowTypeTransfer(workflowTypeName, description);
            put(workflowType, workflowTypeTransfer);
        }
        
        return workflowTypeTransfer;
    }
    
}
