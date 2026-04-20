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

package com.echothree.model.control.workflow.server.transfer;

import javax.inject.Inject;
import com.echothree.model.control.workflow.common.transfer.WorkflowDestinationDescriptionTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowDestinationDescription;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class WorkflowDestinationDescriptionTransferCache
        extends BaseWorkflowDescriptionTransferCache<WorkflowDestinationDescription, WorkflowDestinationDescriptionTransfer> {
    @Inject
    WorkflowControl workflowControl;



    /** Creates a new instance of WorkflowDestinationDescriptionTransferCache */
    protected WorkflowDestinationDescriptionTransferCache() {
        super();
    }
    
    public WorkflowDestinationDescriptionTransfer getWorkflowDestinationDescriptionTransfer(UserVisit userVisit, WorkflowDestinationDescription workflowDestinationDescription) {
        var workflowDestinationDescriptionTransfer = get(workflowDestinationDescription);
        
        if(workflowDestinationDescriptionTransfer == null) {
            var workflowDestinationTransfer = workflowControl.getWorkflowDestinationTransfer(userVisit, workflowDestinationDescription.getWorkflowDestination());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, workflowDestinationDescription.getLanguage());
            
            workflowDestinationDescriptionTransfer = new WorkflowDestinationDescriptionTransfer(languageTransfer, workflowDestinationTransfer, workflowDestinationDescription.getDescription());
            put(userVisit, workflowDestinationDescription, workflowDestinationDescriptionTransfer);
        }
        
        return workflowDestinationDescriptionTransfer;
    }
    
}
