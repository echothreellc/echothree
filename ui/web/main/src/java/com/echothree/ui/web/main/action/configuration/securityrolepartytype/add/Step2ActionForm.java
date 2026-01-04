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

package com.echothree.ui.web.main.action.configuration.securityrolepartytype.add;

import com.echothree.control.user.selector.common.SelectorUtil;
import com.echothree.control.user.selector.common.result.GetSelectorChoicesResult;
import com.echothree.model.control.selector.common.SelectorTypes;
import com.echothree.model.control.selector.common.choice.SelectorChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="SecurityRolePartyTypeAddStep2")
public class Step2ActionForm
        extends BaseActionForm {
    
    SelectorChoicesBean partySelectorChoices;
    
    private String securityRoleGroupName;
    private String securityRoleName;
    private String partyTypeName;
    String partySelectorChoice;
    
    public void setupPartySelectorChoices()
            throws NamingException {
        if(partySelectorChoices == null) {
            var form = SelectorUtil.getHome().getGetSelectorChoicesForm();

            form.setSelectorKindName(getPartyTypeName());
            form.setSelectorTypeName(SelectorTypes.SECURITY_ROLE.name());
            form.setDefaultSelectorChoice(partySelectorChoice);
            form.setAllowNullChoice(String.valueOf(true));

            var commandResult = SelectorUtil.getHome().getSelectorChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetSelectorChoicesResult)executionResult.getResult();
            partySelectorChoices = result.getSelectorChoices();

            if(partySelectorChoice == null) {
                partySelectorChoice = partySelectorChoices.getDefaultValue();
            }
        }
    }
    
    public String getSecurityRoleGroupName() {
        return securityRoleGroupName;
    }

    public void setSecurityRoleGroupName(String securityRoleGroupName) {
        this.securityRoleGroupName = securityRoleGroupName;
    }

    public String getSecurityRoleName() {
        return securityRoleName;
    }

    public void setSecurityRoleName(String securityRoleName) {
        this.securityRoleName = securityRoleName;
    }

    public String getPartyTypeName() {
        return partyTypeName;
    }

    public void setPartyTypeName(String partyTypeName) {
        this.partyTypeName = partyTypeName;
    }
    
    public List<LabelValueBean> getPartySelectorChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupPartySelectorChoices();
        if(partySelectorChoices != null) {
            choices = convertChoices(partySelectorChoices);
        }
        
        return choices;
    }
    
    public void setPartySelectorChoice(String partySelectorChoice) {
        this.partySelectorChoice = partySelectorChoice;
    }
    
    public String getPartySelectorChoice()
            throws NamingException {
        setupPartySelectorChoices();
        
        return partySelectorChoice;
    }

}
