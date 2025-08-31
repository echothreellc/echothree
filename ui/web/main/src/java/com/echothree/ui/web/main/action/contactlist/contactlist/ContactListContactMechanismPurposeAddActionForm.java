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

package com.echothree.ui.web.main.action.contactlist.contactlist;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.GetContactMechanismPurposeChoicesResult;
import com.echothree.model.control.contact.common.choice.ContactMechanismPurposeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ContactListContactMechanismPurposeAdd")
public class ContactListContactMechanismPurposeAddActionForm
        extends BaseActionForm {
    
    private ContactMechanismPurposeChoicesBean contactMechanismPurposeChoices;

    private String contactListName;
    private String contactMechanismPurposeChoice;
    private Boolean isDefault;
    private String sortOrder;
    
    private void setupContactMechanismPurposeChoices()
            throws NamingException {
        if(contactMechanismPurposeChoices == null) {
            var form = ContactUtil.getHome().getGetContactMechanismPurposeChoicesForm();

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

    public void setContactListName(String contactListName) {
        this.contactListName = contactListName;
    }
    
    public String getContactListName() {
        return contactListName;
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
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = false;
    }

}
