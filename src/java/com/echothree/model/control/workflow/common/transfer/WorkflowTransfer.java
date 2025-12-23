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

import com.echothree.model.control.security.common.transfer.SecurityRoleGroupTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class WorkflowTransfer
        extends BaseTransfer {
    
    private String workflowName;
    private SelectorTypeTransfer selectorType;
    private SecurityRoleGroupTransfer securityRoleGroup;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of WorkflowTransfer */
    public WorkflowTransfer(String workflowName, SelectorTypeTransfer selectorType,
            SecurityRoleGroupTransfer securityRoleGroup, Integer sortOrder, String description) {
        this.workflowName = workflowName;
        this.securityRoleGroup = securityRoleGroup;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getWorkflowName() {
        return workflowName;
    }
    
    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }
    
    public SelectorTypeTransfer getSelectorType() {
        return selectorType;
    }
    
    public void setSelectorType(SelectorTypeTransfer selectorType) {
        this.selectorType = selectorType;
    }
    
    public SecurityRoleGroupTransfer getSecurityRoleGroup() {
        return securityRoleGroup;
    }
    
    public void setSecurityRoleGroup(SecurityRoleGroupTransfer securityRoleGroup) {
        this.securityRoleGroup = securityRoleGroup;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
