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

package com.echothree.ui.web.main.action.customer.customercontactlist;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.result.GetPartyContactListStatusChoicesResult;
import com.echothree.model.control.contactlist.common.choice.PartyContactListStatusChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerContactListStatus")
public class PartyContactListStatusActionForm
        extends BaseActionForm {
    
    private PartyContactListStatusChoicesBean partyContactListStatusChoices;
    
    private String partyName;
    private String contactListName;
    private String partyContactListStatusChoice;
    
    public void setupPartyContactListStatusChoices()
            throws NamingException {
        if(partyContactListStatusChoices == null) {
            var form = ContactListUtil.getHome().getGetPartyContactListStatusChoicesForm();

            form.setPartyName(partyName);
            form.setContactListName(contactListName);
            form.setDefaultPartyContactListStatusChoice(partyContactListStatusChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = ContactListUtil.getHome().getPartyContactListStatusChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetPartyContactListStatusChoicesResult)executionResult.getResult();
            partyContactListStatusChoices = result.getPartyContactListStatusChoices();

            if(partyContactListStatusChoice == null) {
                partyContactListStatusChoice = partyContactListStatusChoices.getDefaultValue();
            }
        }
    }
    
    /**
     * Returns the partyName.
     * @return the partyName
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * Sets the partyName.
     * @param partyName the partyName to set
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    /**
     * Returns the contactListName.
     * @return the contactListName
     */
    public String getContactListName() {
        return contactListName;
    }

    /**
     * Sets the contactListName.
     * @param contactListName the contactListName to set
     */
    public void setContactListName(String contactListName) {
        this.contactListName = contactListName;
    }

    public String getPartyContactListStatusChoice()
            throws NamingException {
        setupPartyContactListStatusChoices();
        return partyContactListStatusChoice;
    }
    
    public void setPartyContactListStatusChoice(String partyContactListStatusChoice) {
        this.partyContactListStatusChoice = partyContactListStatusChoice;
    }
    
    public List<LabelValueBean> getPartyContactListStatusChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupPartyContactListStatusChoices();
        if(partyContactListStatusChoices != null) {
            choices = convertChoices(partyContactListStatusChoices);
        }
        
        return choices;
    }

}
