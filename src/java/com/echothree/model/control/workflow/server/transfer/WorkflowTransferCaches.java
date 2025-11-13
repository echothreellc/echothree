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

import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class WorkflowTransferCaches
        extends BaseTransferCaches {
    
    protected WorkflowControl workflowControl;
    
    protected WorkflowDescriptionTransferCache workflowDescriptionTransferCache;
    protected WorkflowDestinationDescriptionTransferCache workflowDestinationDescriptionTransferCache;
    protected WorkflowDestinationSelectorTransferCache workflowDestinationSelectorTransferCache;
    protected WorkflowDestinationStepTransferCache workflowDestinationStepTransferCache;
    protected WorkflowDestinationTransferCache workflowDestinationTransferCache;
    protected WorkflowEntityStatusTransferCache workflowEntityStatusTransferCache;
    protected WorkflowEntityTypeTransferCache workflowEntityTypeTransferCache;
    protected WorkflowEntranceDescriptionTransferCache workflowEntranceDescriptionTransferCache;
    protected WorkflowEntranceSelectorTransferCache workflowEntranceSelectorTransferCache;
    protected WorkflowEntranceStepTransferCache workflowEntranceStepTransferCache;
    protected WorkflowEntranceTransferCache workflowEntranceTransferCache;
    protected WorkflowStepDescriptionTransferCache workflowStepDescriptionTransferCache;
    protected WorkflowStepTransferCache workflowStepTransferCache;
    protected WorkflowStepTypeTransferCache workflowStepTypeTransferCache;
    protected WorkflowTransferCache workflowTransferCache;
    protected WorkflowSelectorKindTransferCache workflowSelectorKindTransferCache;
    protected WorkflowDestinationPartyTypeTransferCache workflowDestinationPartyTypeTransferCache;
    protected WorkflowDestinationSecurityRoleTransferCache workflowDestinationSecurityRoleTransferCache;
    protected WorkflowEntrancePartyTypeTransferCache workflowEntrancePartyTypeTransferCache;
    protected WorkflowEntranceSecurityRoleTransferCache workflowEntranceSecurityRoleTransferCache;
    
    /** Creates a new instance of WorkflowTransferCaches */
    public WorkflowTransferCaches(WorkflowControl workflowControl) {
        super();
        
        this.workflowControl = workflowControl;
    }
    
    public WorkflowDescriptionTransferCache getWorkflowDescriptionTransferCache() {
        if(workflowDescriptionTransferCache == null)
            workflowDescriptionTransferCache = new WorkflowDescriptionTransferCache(workflowControl);
        
        return workflowDescriptionTransferCache;
    }
    
    public WorkflowDestinationDescriptionTransferCache getWorkflowDestinationDescriptionTransferCache() {
        if(workflowDestinationDescriptionTransferCache == null)
            workflowDestinationDescriptionTransferCache = new WorkflowDestinationDescriptionTransferCache(workflowControl);
        
        return workflowDestinationDescriptionTransferCache;
    }
    
    public WorkflowDestinationSelectorTransferCache getWorkflowDestinationSelectorTransferCache() {
        if(workflowDestinationSelectorTransferCache == null)
            workflowDestinationSelectorTransferCache = new WorkflowDestinationSelectorTransferCache(workflowControl);
        
        return workflowDestinationSelectorTransferCache;
    }
    
    public WorkflowDestinationStepTransferCache getWorkflowDestinationStepTransferCache() {
        if(workflowDestinationStepTransferCache == null)
            workflowDestinationStepTransferCache = new WorkflowDestinationStepTransferCache(workflowControl);
        
        return workflowDestinationStepTransferCache;
    }
    
    public WorkflowDestinationTransferCache getWorkflowDestinationTransferCache() {
        if(workflowDestinationTransferCache == null)
            workflowDestinationTransferCache = new WorkflowDestinationTransferCache(workflowControl);
        
        return workflowDestinationTransferCache;
    }
    
    public WorkflowEntityStatusTransferCache getWorkflowEntityStatusTransferCache() {
        if(workflowEntityStatusTransferCache == null)
            workflowEntityStatusTransferCache = new WorkflowEntityStatusTransferCache(workflowControl);
        
        return workflowEntityStatusTransferCache;
    }
    
    public WorkflowEntityTypeTransferCache getWorkflowEntityTypeTransferCache() {
        if(workflowEntityTypeTransferCache == null)
            workflowEntityTypeTransferCache = new WorkflowEntityTypeTransferCache(workflowControl);
        
        return workflowEntityTypeTransferCache;
    }
    
    public WorkflowEntranceDescriptionTransferCache getWorkflowEntranceDescriptionTransferCache() {
        if(workflowEntranceDescriptionTransferCache == null)
            workflowEntranceDescriptionTransferCache = new WorkflowEntranceDescriptionTransferCache(workflowControl);
        
        return workflowEntranceDescriptionTransferCache;
    }
    
    public WorkflowEntranceSelectorTransferCache getWorkflowEntranceSelectorTransferCache() {
        if(workflowEntranceSelectorTransferCache == null)
            workflowEntranceSelectorTransferCache = new WorkflowEntranceSelectorTransferCache(workflowControl);
        
        return workflowEntranceSelectorTransferCache;
    }
    
    public WorkflowEntranceStepTransferCache getWorkflowEntranceStepTransferCache() {
        if(workflowEntranceStepTransferCache == null)
            workflowEntranceStepTransferCache = new WorkflowEntranceStepTransferCache(workflowControl);
        
        return workflowEntranceStepTransferCache;
    }
    
    public WorkflowEntranceTransferCache getWorkflowEntranceTransferCache() {
        if(workflowEntranceTransferCache == null)
            workflowEntranceTransferCache = new WorkflowEntranceTransferCache(workflowControl);
        
        return workflowEntranceTransferCache;
    }
    
    public WorkflowStepDescriptionTransferCache getWorkflowStepDescriptionTransferCache() {
        if(workflowStepDescriptionTransferCache == null)
            workflowStepDescriptionTransferCache = new WorkflowStepDescriptionTransferCache(workflowControl);
        
        return workflowStepDescriptionTransferCache;
    }
    
    public WorkflowStepTransferCache getWorkflowStepTransferCache() {
        if(workflowStepTransferCache == null)
            workflowStepTransferCache = new WorkflowStepTransferCache(workflowControl);
        
        return workflowStepTransferCache;
    }
    
    public WorkflowStepTypeTransferCache getWorkflowStepTypeTransferCache() {
        if(workflowStepTypeTransferCache == null)
            workflowStepTypeTransferCache = new WorkflowStepTypeTransferCache(workflowControl);
        
        return workflowStepTypeTransferCache;
    }
    
    public WorkflowTransferCache getWorkflowTransferCache() {
        if(workflowTransferCache == null)
            workflowTransferCache = new WorkflowTransferCache(workflowControl);
        
        return workflowTransferCache;
    }
    
    public WorkflowSelectorKindTransferCache getWorkflowSelectorKindTransferCache() {
        if(workflowSelectorKindTransferCache == null)
            workflowSelectorKindTransferCache = new WorkflowSelectorKindTransferCache(workflowControl);
        
        return workflowSelectorKindTransferCache;
    }
    
    public WorkflowDestinationPartyTypeTransferCache getWorkflowDestinationPartyTypeTransferCache() {
        if(workflowDestinationPartyTypeTransferCache == null)
            workflowDestinationPartyTypeTransferCache = new WorkflowDestinationPartyTypeTransferCache(workflowControl);
        
        return workflowDestinationPartyTypeTransferCache;
    }
    
    public WorkflowDestinationSecurityRoleTransferCache getWorkflowDestinationSecurityRoleTransferCache() {
        if(workflowDestinationSecurityRoleTransferCache == null)
            workflowDestinationSecurityRoleTransferCache = new WorkflowDestinationSecurityRoleTransferCache(workflowControl);
        
        return workflowDestinationSecurityRoleTransferCache;
    }
    
    public WorkflowEntrancePartyTypeTransferCache getWorkflowEntrancePartyTypeTransferCache() {
        if(workflowEntrancePartyTypeTransferCache == null)
            workflowEntrancePartyTypeTransferCache = new WorkflowEntrancePartyTypeTransferCache(workflowControl);
        
        return workflowEntrancePartyTypeTransferCache;
    }
    
    public WorkflowEntranceSecurityRoleTransferCache getWorkflowEntranceSecurityRoleTransferCache() {
        if(workflowEntranceSecurityRoleTransferCache == null)
            workflowEntranceSecurityRoleTransferCache = new WorkflowEntranceSecurityRoleTransferCache(workflowControl);
        
        return workflowEntranceSecurityRoleTransferCache;
    }
    
}
