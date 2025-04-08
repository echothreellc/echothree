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

import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityTypeTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityType;
import com.echothree.util.server.persistence.Session;

public class WorkflowEntityTypeTransferCache
        extends BaseWorkflowTransferCache<WorkflowEntityType, WorkflowEntityTypeTransfer> {

    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);

    /** Creates a new instance of WorkflowEntityTypeTransferCache */
    public WorkflowEntityTypeTransferCache(UserVisit userVisit, WorkflowControl workflowControl) {
        super(userVisit, workflowControl);
    }
    
    public WorkflowEntityTypeTransfer getWorkflowEntityTypeTransfer(WorkflowEntityType workflowEntityType) {
        var workflowEntityTypeTransfer = get(workflowEntityType);
        
        if(workflowEntityTypeTransfer == null) {
            var workflow = workflowControl.getWorkflowTransfer(userVisit, workflowEntityType.getWorkflow());
            var entityType = entityTypeControl.getEntityTypeTransfer(userVisit, workflowEntityType.getEntityType());
            
            workflowEntityTypeTransfer = new WorkflowEntityTypeTransfer(workflow, entityType);
            put(workflowEntityType, workflowEntityTypeTransfer);
        }
        
        return workflowEntityTypeTransfer;
    }
    
}
