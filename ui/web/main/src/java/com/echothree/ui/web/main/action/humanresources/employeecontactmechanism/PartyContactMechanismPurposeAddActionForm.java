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

package com.echothree.ui.web.main.action.humanresources.employeecontactmechanism;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.GetContactMechanismPurposeChoicesResult;
import com.echothree.model.control.contact.common.choice.ContactMechanismPurposeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="EmployeePartyContactMechanismPurposeAdd")
public class PartyContactMechanismPurposeAddActionForm
        extends BaseActionForm {
    
    private ContactMechanismPurposeChoicesBean contactMechanismPurposeChoices;
    
    private String partyName;
    private String contactMechanismName;
    private String contactMechanismPurposeChoice;
    
    public void setupContactMechanismPurposeChoices()
            throws NamingException {
        if(contactMechanismPurposeChoices == null) {
            var form = ContactUtil.getHome().getGetContactMechanismPurposeChoicesForm();

            form.setContactMechanismName(contactMechanismName);
            form.setDefaultContactMechanismPurposeChoice(contactMechanismPurposeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ContactUtil.getHome().getContactMechanismPurposeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getContactMechanismPurposeChoicesResult = (GetContactMechanismPurposeChoicesResult)executionResult.getResult();
            contactMechanismPurposeChoices = getContactMechanismPurposeChoicesResult.getContactMechanismPurposeChoices();

            if(contactMechanismPurposeChoice == null) {
                contactMechanismPurposeChoice = contactMechanismPurposeChoices.getDefaultValue();
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
    
    public String getContactMechanismPurposeChoice()
            throws NamingException {
        setupContactMechanismPurposeChoices();
        
        return contactMechanismPurposeChoice;
    }
    
    public void setContactMechanismPurposeChoice(String contactMechanismPurposeChoice) {
        this.contactMechanismPurposeChoice = contactMechanismPurposeChoice;
    }
    
    public List<LabelValueBean> getContactMechanismPurposeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupContactMechanismPurposeChoices();
        if(contactMechanismPurposeChoices != null) {
            choices = convertChoices(contactMechanismPurposeChoices);
        }
        
        return choices;
    }
    
}
