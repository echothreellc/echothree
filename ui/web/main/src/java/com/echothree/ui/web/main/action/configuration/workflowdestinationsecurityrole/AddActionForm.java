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

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.result.GetSecurityRoleChoicesResult;
import com.echothree.model.control.security.common.choice.SecurityRoleChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="WorkflowDestinationSecurityRoleAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private SecurityRoleChoicesBean securityRoleChoices;
    
    private String workflowName;
    private String workflowStepName;
    private String workflowDestinationName;
    private String partyTypeName;
    private String securityRoleChoice;
    
    private void setupSecurityRoleChoices() {
        if(securityRoleChoices == null) {
            try {
                var commandForm = SecurityUtil.getHome().getGetSecurityRoleChoicesForm();
                
                commandForm.setWorkflowName(workflowName);
                commandForm.setDefaultSecurityRoleChoice(securityRoleChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = SecurityUtil.getHome().getSecurityRoleChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetSecurityRoleChoicesResult)executionResult.getResult();
                securityRoleChoices = result.getSecurityRoleChoices();
                
                if(securityRoleChoice == null) {
                    securityRoleChoice = securityRoleChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, securityRoleChoices remains null, no default
            }
        }
    }
    
    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }
    
    public String getWorkflowName() {
        return workflowName;
    }
    
    public void setWorkflowStepName(String workflowStepName) {
        this.workflowStepName = workflowStepName;
    }
    
    public String getWorkflowStepName() {
        return workflowStepName;
    }
    
    public void setWorkflowDestinationName(String workflowDestinationName) {
        this.workflowDestinationName = workflowDestinationName;
    }
    
    public String getWorkflowDestinationName() {
        return workflowDestinationName;
    }
    
    public String getPartyTypeName() {
        return partyTypeName;
    }

    public void setPartyTypeName(String partyTypeName) {
        this.partyTypeName = partyTypeName;
    }

    public List<LabelValueBean> getSecurityRoleChoices() {
        List<LabelValueBean> choices = null;
        
        setupSecurityRoleChoices();
        if(securityRoleChoices != null) {
            choices = convertChoices(securityRoleChoices);
        }
        
        return choices;
    }
    
    public void setSecurityRoleChoice(String securityRoleChoice) {
        this.securityRoleChoice = securityRoleChoice;
    }
    
    public String getSecurityRoleChoice() {
        setupSecurityRoleChoices();
        
        return securityRoleChoice;
    }

}
