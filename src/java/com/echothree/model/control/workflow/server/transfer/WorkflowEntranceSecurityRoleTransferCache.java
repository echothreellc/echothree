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

import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntranceSecurityRoleTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.WorkflowEntranceSecurityRole;
import com.echothree.util.server.persistence.Session;

public class WorkflowEntranceSecurityRoleTransferCache
        extends BaseWorkflowTransferCache<WorkflowEntranceSecurityRole, WorkflowEntranceSecurityRoleTransfer> {
    
    SecurityControl securityControl;
    
    /** Creates a new instance of WorkflowEntranceSecurityRoleTransferCache */
    public WorkflowEntranceSecurityRoleTransferCache(WorkflowControl workflowControl) {
        super(workflowControl);
        
        securityControl = Session.getModelController(SecurityControl.class);
    }
    
    public WorkflowEntranceSecurityRoleTransfer getWorkflowEntranceSecurityRoleTransfer(WorkflowEntranceSecurityRole workflowEntranceSecurityRole) {
        var workflowEntranceSecurityRoleTransfer = get(workflowEntranceSecurityRole);
        
        if(workflowEntranceSecurityRoleTransfer == null) {
            var workflowEntrancePartyType = workflowControl.getWorkflowEntrancePartyTypeTransfer(userVisit, workflowEntranceSecurityRole.getWorkflowEntrancePartyType());
            var securityRole = securityControl.getSecurityRoleTransfer(userVisit, workflowEntranceSecurityRole.getSecurityRole());
            
            workflowEntranceSecurityRoleTransfer = new WorkflowEntranceSecurityRoleTransfer(workflowEntrancePartyType, securityRole);
            put(userVisit, workflowEntranceSecurityRole, workflowEntranceSecurityRoleTransfer);
        }
        
        return workflowEntranceSecurityRoleTransfer;
    }
    
}
