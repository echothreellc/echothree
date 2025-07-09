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

package com.echothree.ui.web.main.action.configuration.securityrolegroup;

import com.echothree.control.user.security.common.SecurityUtil;
import com.echothree.control.user.security.common.result.GetSecurityRoleGroupChoicesResult;
import com.echothree.model.control.security.common.choice.SecurityRoleGroupChoicesBean;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="SecurityRoleGroupEdit")
public class EditActionForm
        extends AddActionForm {
    
    private SecurityRoleGroupChoicesBean parentSecurityRoleGroupChoices;
    
    private String originalSecurityRoleGroupName;
    private String parentSecurityRoleGroupChoice;
    
    private void setupParentSecurityRoleGroupChoices()
            throws NamingException {
        if(parentSecurityRoleGroupChoices == null) {
            var form = SecurityUtil.getHome().getGetSecurityRoleGroupChoicesForm();

            form.setDefaultSecurityRoleGroupChoice(parentSecurityRoleGroupChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = SecurityUtil.getHome().getSecurityRoleGroupChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getSecurityRoleGroupChoicesResult = (GetSecurityRoleGroupChoicesResult)executionResult.getResult();
            parentSecurityRoleGroupChoices = getSecurityRoleGroupChoicesResult.getSecurityRoleGroupChoices();

            if(parentSecurityRoleGroupChoice == null) {
                parentSecurityRoleGroupChoice = parentSecurityRoleGroupChoices.getDefaultValue();
            }
        }
    }
    
    public String getOriginalSecurityRoleGroupName() {
        return originalSecurityRoleGroupName;
    }
    
    public void setOriginalSecurityRoleGroupName(String originalSecurityRoleGroupName) {
        this.originalSecurityRoleGroupName = originalSecurityRoleGroupName;
    }
    
    public List<LabelValueBean> getParentSecurityRoleGroupChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupParentSecurityRoleGroupChoices();
        if(parentSecurityRoleGroupChoices != null) {
            choices = convertChoices(parentSecurityRoleGroupChoices);
        }
        
        return choices;
    }
    
    public String getParentSecurityRoleGroupChoice()
            throws NamingException {
        setupParentSecurityRoleGroupChoices();
        return parentSecurityRoleGroupChoice;
    }
    
    public void setParentSecurityRoleGroupChoice(String parentSecurityRoleGroupChoice) {
        this.parentSecurityRoleGroupChoice = parentSecurityRoleGroupChoice;
    }
    
}
