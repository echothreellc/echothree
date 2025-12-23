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

package com.echothree.ui.web.main.action.configuration.workflowdestinationsecurityrole;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="WorkflowDestinationSecurityRoleDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String workflowName;
    private String workflowStepName;
    private String workflowDestinationName;
    private String partyTypeName;
    private String securityRoleName;

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getWorkflowStepName() {
        return workflowStepName;
    }

    public void setWorkflowStepName(String workflowStepName) {
        this.workflowStepName = workflowStepName;
    }

    public String getWorkflowDestinationName() {
        return workflowDestinationName;
    }

    public void setWorkflowDestinationName(String workflowDestinationName) {
        this.workflowDestinationName = workflowDestinationName;
    }

    public String getPartyTypeName() {
        return partyTypeName;
    }

    public void setPartyTypeName(String partyTypeName) {
        this.partyTypeName = partyTypeName;
    }

    public String getSecurityRoleName() {
        return securityRoleName;
    }

    public void setSecurityRoleName(String securityRoleName) {
        this.securityRoleName = securityRoleName;
    }
    
}
