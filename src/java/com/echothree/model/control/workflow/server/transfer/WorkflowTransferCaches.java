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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class WorkflowTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    WorkflowDescriptionTransferCache workflowDescriptionTransferCache;
    
    @Inject
    WorkflowDestinationDescriptionTransferCache workflowDestinationDescriptionTransferCache;
    
    @Inject
    WorkflowDestinationSelectorTransferCache workflowDestinationSelectorTransferCache;
    
    @Inject
    WorkflowDestinationStepTransferCache workflowDestinationStepTransferCache;
    
    @Inject
    WorkflowDestinationTransferCache workflowDestinationTransferCache;
    
    @Inject
    WorkflowEntityStatusTransferCache workflowEntityStatusTransferCache;
    
    @Inject
    WorkflowEntityTypeTransferCache workflowEntityTypeTransferCache;
    
    @Inject
    WorkflowEntranceDescriptionTransferCache workflowEntranceDescriptionTransferCache;
    
    @Inject
    WorkflowEntranceSelectorTransferCache workflowEntranceSelectorTransferCache;
    
    @Inject
    WorkflowEntranceStepTransferCache workflowEntranceStepTransferCache;
    
    @Inject
    WorkflowEntranceTransferCache workflowEntranceTransferCache;
    
    @Inject
    WorkflowStepDescriptionTransferCache workflowStepDescriptionTransferCache;
    
    @Inject
    WorkflowStepTransferCache workflowStepTransferCache;
    
    @Inject
    WorkflowStepTypeTransferCache workflowStepTypeTransferCache;
    
    @Inject
    WorkflowTransferCache workflowTransferCache;
    
    @Inject
    WorkflowSelectorKindTransferCache workflowSelectorKindTransferCache;
    
    @Inject
    WorkflowDestinationPartyTypeTransferCache workflowDestinationPartyTypeTransferCache;
    
    @Inject
    WorkflowDestinationSecurityRoleTransferCache workflowDestinationSecurityRoleTransferCache;
    
    @Inject
    WorkflowEntrancePartyTypeTransferCache workflowEntrancePartyTypeTransferCache;
    
    @Inject
    WorkflowEntranceSecurityRoleTransferCache workflowEntranceSecurityRoleTransferCache;

    /** Creates a new instance of WorkflowTransferCaches */
    protected WorkflowTransferCaches() {
        super();
    }
    
    public WorkflowDescriptionTransferCache getWorkflowDescriptionTransferCache() {
        return workflowDescriptionTransferCache;
    }
    
    public WorkflowDestinationDescriptionTransferCache getWorkflowDestinationDescriptionTransferCache() {
        return workflowDestinationDescriptionTransferCache;
    }
    
    public WorkflowDestinationSelectorTransferCache getWorkflowDestinationSelectorTransferCache() {
        return workflowDestinationSelectorTransferCache;
    }
    
    public WorkflowDestinationStepTransferCache getWorkflowDestinationStepTransferCache() {
        return workflowDestinationStepTransferCache;
    }
    
    public WorkflowDestinationTransferCache getWorkflowDestinationTransferCache() {
        return workflowDestinationTransferCache;
    }
    
    public WorkflowEntityStatusTransferCache getWorkflowEntityStatusTransferCache() {
        return workflowEntityStatusTransferCache;
    }
    
    public WorkflowEntityTypeTransferCache getWorkflowEntityTypeTransferCache() {
        return workflowEntityTypeTransferCache;
    }
    
    public WorkflowEntranceDescriptionTransferCache getWorkflowEntranceDescriptionTransferCache() {
        return workflowEntranceDescriptionTransferCache;
    }
    
    public WorkflowEntranceSelectorTransferCache getWorkflowEntranceSelectorTransferCache() {
        return workflowEntranceSelectorTransferCache;
    }
    
    public WorkflowEntranceStepTransferCache getWorkflowEntranceStepTransferCache() {
        return workflowEntranceStepTransferCache;
    }
    
    public WorkflowEntranceTransferCache getWorkflowEntranceTransferCache() {
        return workflowEntranceTransferCache;
    }
    
    public WorkflowStepDescriptionTransferCache getWorkflowStepDescriptionTransferCache() {
        return workflowStepDescriptionTransferCache;
    }
    
    public WorkflowStepTransferCache getWorkflowStepTransferCache() {
        return workflowStepTransferCache;
    }
    
    public WorkflowStepTypeTransferCache getWorkflowStepTypeTransferCache() {
        return workflowStepTypeTransferCache;
    }
    
    public WorkflowTransferCache getWorkflowTransferCache() {
        return workflowTransferCache;
    }
    
    public WorkflowSelectorKindTransferCache getWorkflowSelectorKindTransferCache() {
        return workflowSelectorKindTransferCache;
    }
    
    public WorkflowDestinationPartyTypeTransferCache getWorkflowDestinationPartyTypeTransferCache() {
        return workflowDestinationPartyTypeTransferCache;
    }
    
    public WorkflowDestinationSecurityRoleTransferCache getWorkflowDestinationSecurityRoleTransferCache() {
        return workflowDestinationSecurityRoleTransferCache;
    }
    
    public WorkflowEntrancePartyTypeTransferCache getWorkflowEntrancePartyTypeTransferCache() {
        return workflowEntrancePartyTypeTransferCache;
    }
    
    public WorkflowEntranceSecurityRoleTransferCache getWorkflowEntranceSecurityRoleTransferCache() {
        return workflowEntranceSecurityRoleTransferCache;
    }
    
}
