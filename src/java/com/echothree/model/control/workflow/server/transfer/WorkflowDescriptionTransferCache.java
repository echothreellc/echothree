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

import com.echothree.model.control.workflow.common.transfer.WorkflowDescriptionTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowDescription;

public class WorkflowDescriptionTransferCache
        extends BaseWorkflowDescriptionTransferCache<WorkflowDescription, WorkflowDescriptionTransfer> {
    
    /** Creates a new instance of WorkflowDescriptionTransferCache */
    public WorkflowDescriptionTransferCache(WorkflowControl workflowControl) {
        super(workflowControl);
    }
    
    public WorkflowDescriptionTransfer getWorkflowDescriptionTransfer(WorkflowDescription workflowDescription) {
        var workflowDescriptionTransfer = get(workflowDescription);
        
        if(workflowDescriptionTransfer == null) {
            var workflowTransfer = workflowControl.getWorkflowTransfer(userVisit, workflowDescription.getWorkflow());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, workflowDescription.getLanguage());
            
            workflowDescriptionTransfer = new WorkflowDescriptionTransfer(languageTransfer, workflowTransfer, workflowDescription.getDescription());
            put(userVisit, workflowDescription, workflowDescriptionTransfer);
        }
        
        return workflowDescriptionTransfer;
    }
    
}
