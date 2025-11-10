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
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.workflow.common.WorkflowProperties;
import com.echothree.model.control.workflow.common.transfer.WorkflowTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.util.common.form.TransferProperties;
import com.echothree.util.server.persistence.Session;

public class WorkflowTransferCache
        extends BaseWorkflowTransferCache<Workflow, WorkflowTransfer> {
    
    SecurityControl securityControl = Session.getModelController(SecurityControl.class);
    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);
    
    TransferProperties transferProperties;
    boolean filterWorkflowName;
    boolean filterSelectorType;
    boolean filterSecurityRoleGroup;
    boolean filterSortOrder;
    boolean filterDescription;
    boolean filterEntityInstance;

    /** Creates a new instance of WorkflowTransferCache */
    public WorkflowTransferCache(UserVisit userVisit, WorkflowControl workflowControl) {
        super(userVisit, workflowControl);
        
        transferProperties = session.getTransferProperties();
        if(transferProperties != null) {
            var properties = transferProperties.getProperties(WorkflowTransfer.class);
            
            if(properties != null) {
                filterWorkflowName = !properties.contains(WorkflowProperties.WORKFLOW_NAME);
                filterSelectorType = !properties.contains(WorkflowProperties.SELECTOR_TYPE);
                filterSecurityRoleGroup = !properties.contains(WorkflowProperties.SECURITY_ROLE_GROUP);
                filterSortOrder = !properties.contains(WorkflowProperties.SORT_ORDER);
                filterDescription = !properties.contains(WorkflowProperties.DESCRIPTION);
                filterEntityInstance = !properties.contains(WorkflowProperties.ENTITY_INSTANCE);
            }
        }
        
        setIncludeEntityInstance(!filterEntityInstance);
    }
    
    public WorkflowTransfer getWorkflowTransfer(Workflow workflow) {
        var workflowTransfer = get(workflow);
        
        if(workflowTransfer == null) {
            var workflowDetail = workflow.getLastDetail();
            var workflowName = filterWorkflowName ? null : workflowDetail.getWorkflowName();
            var selectorType = filterSelectorType ? null : workflowDetail.getSelectorType();
            var selectorTypeTransfer = selectorType == null? null: selectorControl.getSelectorTypeTransfer(userVisit, selectorType);
            var securityRoleGroup = filterSecurityRoleGroup ? null : workflowDetail.getSecurityRoleGroup();
            var securityRoleGroupTransfer = securityRoleGroup == null? null: securityControl.getSecurityRoleGroupTransfer(userVisit, securityRoleGroup);
            var sortOrder = filterSortOrder ? null : workflowDetail.getSortOrder();
            var description = filterDescription ? null : workflowControl.getBestWorkflowDescription(workflow, getLanguage(userVisit));
            
            workflowTransfer = new WorkflowTransfer(workflowName, selectorTypeTransfer,
                    securityRoleGroupTransfer, sortOrder, description);
            put(userVisit, workflow, workflowTransfer);
        }
        
        return workflowTransfer;
    }
    
}
