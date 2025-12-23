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
import com.echothree.control.user.contact.common.result.GetEmailAddressStatusChoicesResult;
import com.echothree.model.control.contact.common.choice.EmailAddressStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerContactEmailAddressStatus")
public class ContactEmailAddressStatusActionForm
        extends BaseActionForm {
    
    private EmailAddressStatusChoicesBean emailAddressStatusChoices;
    
    private String partyName;
    private String contactMechanismName;
    private String emailAddressStatusChoice;
    
    public void setupEmailAddressStatusChoices()
            throws NamingException {
        if(emailAddressStatusChoices == null) {
            var form = ContactUtil.getHome().getGetEmailAddressStatusChoicesForm();

            form.setContactMechanismName(contactMechanismName);
            form.setDefaultEmailAddressStatusChoice(emailAddressStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ContactUtil.getHome().getEmailAddressStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetEmailAddressStatusChoicesResult)executionResult.getResult();
            emailAddressStatusChoices = result.getEmailAddressStatusChoices();

            if(emailAddressStatusChoice == null) {
                emailAddressStatusChoice = emailAddressStatusChoices.getDefaultValue();
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
    
    public String getEmailAddressStatusChoice() {
        return emailAddressStatusChoice;
    }
    
    public void setEmailAddressStatusChoice(String emailAddressStatusChoice) {
        this.emailAddressStatusChoice = emailAddressStatusChoice;
    }
    
    public List<LabelValueBean> getEmailAddressStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEmailAddressStatusChoices();
        if(emailAddressStatusChoices != null) {
            choices = convertChoices(emailAddressStatusChoices);
        }
        
        return choices;
    }

}
