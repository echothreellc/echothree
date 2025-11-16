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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;

public class WorkflowTransferCaches
        extends BaseTransferCaches {
    
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
    public WorkflowTransferCaches() {
        super();
    }
    
    public WorkflowDescriptionTransferCache getWorkflowDescriptionTransferCache() {
        if(workflowDescriptionTransferCache == null)
            workflowDescriptionTransferCache = CDI.current().select(WorkflowDescriptionTransferCache.class).get();
        
        return workflowDescriptionTransferCache;
    }
    
    public WorkflowDestinationDescriptionTransferCache getWorkflowDestinationDescriptionTransferCache() {
        if(workflowDestinationDescriptionTransferCache == null)
            workflowDestinationDescriptionTransferCache = CDI.current().select(WorkflowDestinationDescriptionTransferCache.class).get();
        
        return workflowDestinationDescriptionTransferCache;
    }
    
    public WorkflowDestinationSelectorTransferCache getWorkflowDestinationSelectorTransferCache() {
        if(workflowDestinationSelectorTransferCache == null)
            workflowDestinationSelectorTransferCache = CDI.current().select(WorkflowDestinationSelectorTransferCache.class).get();
        
        return workflowDestinationSelectorTransferCache;
    }
    
    public WorkflowDestinationStepTransferCache getWorkflowDestinationStepTransferCache() {
        if(workflowDestinationStepTransferCache == null)
            workflowDestinationStepTransferCache = CDI.current().select(WorkflowDestinationStepTransferCache.class).get();
        
        return workflowDestinationStepTransferCache;
    }
    
    public WorkflowDestinationTransferCache getWorkflowDestinationTransferCache() {
        if(workflowDestinationTransferCache == null)
            workflowDestinationTransferCache = CDI.current().select(WorkflowDestinationTransferCache.class).get();
        
        return workflowDestinationTransferCache;
    }
    
    public WorkflowEntityStatusTransferCache getWorkflowEntityStatusTransferCache() {
        if(workflowEntityStatusTransferCache == null)
            workflowEntityStatusTransferCache = CDI.current().select(WorkflowEntityStatusTransferCache.class).get();
        
        return workflowEntityStatusTransferCache;
    }
    
    public WorkflowEntityTypeTransferCache getWorkflowEntityTypeTransferCache() {
        if(workflowEntityTypeTransferCache == null)
            workflowEntityTypeTransferCache = CDI.current().select(WorkflowEntityTypeTransferCache.class).get();
        
        return workflowEntityTypeTransferCache;
    }
    
    public WorkflowEntranceDescriptionTransferCache getWorkflowEntranceDescriptionTransferCache() {
        if(workflowEntranceDescriptionTransferCache == null)
            workflowEntranceDescriptionTransferCache = CDI.current().select(WorkflowEntranceDescriptionTransferCache.class).get();
        
        return workflowEntranceDescriptionTransferCache;
    }
    
    public WorkflowEntranceSelectorTransferCache getWorkflowEntranceSelectorTransferCache() {
        if(workflowEntranceSelectorTransferCache == null)
            workflowEntranceSelectorTransferCache = CDI.current().select(WorkflowEntranceSelectorTransferCache.class).get();
        
        return workflowEntranceSelectorTransferCache;
    }
    
    public WorkflowEntranceStepTransferCache getWorkflowEntranceStepTransferCache() {
        if(workflowEntranceStepTransferCache == null)
            workflowEntranceStepTransferCache = CDI.current().select(WorkflowEntranceStepTransferCache.class).get();
        
        return workflowEntranceStepTransferCache;
    }
    
    public WorkflowEntranceTransferCache getWorkflowEntranceTransferCache() {
        if(workflowEntranceTransferCache == null)
            workflowEntranceTransferCache = CDI.current().select(WorkflowEntranceTransferCache.class).get();
        
        return workflowEntranceTransferCache;
    }
    
    public WorkflowStepDescriptionTransferCache getWorkflowStepDescriptionTransferCache() {
        if(workflowStepDescriptionTransferCache == null)
            workflowStepDescriptionTransferCache = CDI.current().select(WorkflowStepDescriptionTransferCache.class).get();
        
        return workflowStepDescriptionTransferCache;
    }
    
    public WorkflowStepTransferCache getWorkflowStepTransferCache() {
        if(workflowStepTransferCache == null)
            workflowStepTransferCache = CDI.current().select(WorkflowStepTransferCache.class).get();
        
        return workflowStepTransferCache;
    }
    
    public WorkflowStepTypeTransferCache getWorkflowStepTypeTransferCache() {
        if(workflowStepTypeTransferCache == null)
            workflowStepTypeTransferCache = CDI.current().select(WorkflowStepTypeTransferCache.class).get();
        
        return workflowStepTypeTransferCache;
    }
    
    public WorkflowTransferCache getWorkflowTransferCache() {
        if(workflowTransferCache == null)
            workflowTransferCache = CDI.current().select(WorkflowTransferCache.class).get();
        
        return workflowTransferCache;
    }
    
    public WorkflowSelectorKindTransferCache getWorkflowSelectorKindTransferCache() {
        if(workflowSelectorKindTransferCache == null)
            workflowSelectorKindTransferCache = CDI.current().select(WorkflowSelectorKindTransferCache.class).get();
        
        return workflowSelectorKindTransferCache;
    }
    
    public WorkflowDestinationPartyTypeTransferCache getWorkflowDestinationPartyTypeTransferCache() {
        if(workflowDestinationPartyTypeTransferCache == null)
            workflowDestinationPartyTypeTransferCache = CDI.current().select(WorkflowDestinationPartyTypeTransferCache.class).get();
        
        return workflowDestinationPartyTypeTransferCache;
    }
    
    public WorkflowDestinationSecurityRoleTransferCache getWorkflowDestinationSecurityRoleTransferCache() {
        if(workflowDestinationSecurityRoleTransferCache == null)
            workflowDestinationSecurityRoleTransferCache = CDI.current().select(WorkflowDestinationSecurityRoleTransferCache.class).get();
        
        return workflowDestinationSecurityRoleTransferCache;
    }
    
    public WorkflowEntrancePartyTypeTransferCache getWorkflowEntrancePartyTypeTransferCache() {
        if(workflowEntrancePartyTypeTransferCache == null)
            workflowEntrancePartyTypeTransferCache = CDI.current().select(WorkflowEntrancePartyTypeTransferCache.class).get();
        
        return workflowEntrancePartyTypeTransferCache;
    }
    
    public WorkflowEntranceSecurityRoleTransferCache getWorkflowEntranceSecurityRoleTransferCache() {
        if(workflowEntranceSecurityRoleTransferCache == null)
            workflowEntranceSecurityRoleTransferCache = CDI.current().select(WorkflowEntranceSecurityRoleTransferCache.class).get();
        
        return workflowEntranceSecurityRoleTransferCache;
    }
    
}
