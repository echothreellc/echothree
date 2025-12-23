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

package com.echothree.ui.web.main.action.customer.customercontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.GetWebAddressStatusChoicesResult;
import com.echothree.model.control.contact.common.choice.WebAddressStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerContactWebAddressStatus")
public class ContactWebAddressStatusActionForm
        extends BaseActionForm {
    
    private WebAddressStatusChoicesBean webAddressStatusChoices;
    
    private String partyName;
    private String contactMechanismName;
    private String webAddressStatusChoice;
    
    public void setupWebAddressStatusChoices()
            throws NamingException {
        if(webAddressStatusChoices == null) {
            var form = ContactUtil.getHome().getGetWebAddressStatusChoicesForm();

            form.setContactMechanismName(contactMechanismName);
            form.setDefaultWebAddressStatusChoice(webAddressStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ContactUtil.getHome().getWebAddressStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetWebAddressStatusChoicesResult)executionResult.getResult();
            webAddressStatusChoices = result.getWebAddressStatusChoices();

            if(webAddressStatusChoice == null) {
                webAddressStatusChoice = webAddressStatusChoices.getDefaultValue();
            }
        }
    }
    
    public String getPartyName() {
        return partyName;
    }
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public String getContactMechanismName() {
        return contactMechanismName;
    }

    public void setContactMechanismName(String contactMechanismName) {
        this.contactMechanismName = contactMechanismName;
    }
    
    public String getWebAddressStatusChoice() {
        return webAddressStatusChoice;
    }
    
    public void setWebAddressStatusChoice(String webAddressStatusChoice) {
        this.webAddressStatusChoice = webAddressStatusChoice;
    }
    
    public List<LabelValueBean> getWebAddressStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupWebAddressStatusChoices();
        if(webAddressStatusChoices != null) {
            choices = convertChoices(webAddressStatusChoices);
        }
        
        return choices;
    }

}
