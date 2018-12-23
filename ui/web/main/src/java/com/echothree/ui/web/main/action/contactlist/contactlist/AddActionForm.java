// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.form.GetContactListFrequencyChoicesForm;
import com.echothree.control.user.contactlist.common.form.GetContactListGroupChoicesForm;
import com.echothree.control.user.contactlist.common.form.GetContactListTypeChoicesForm;
import com.echothree.control.user.contactlist.common.form.GetPartyContactListStatusChoicesForm;
import com.echothree.control.user.contactlist.common.result.GetContactListFrequencyChoicesResult;
import com.echothree.control.user.contactlist.common.result.GetContactListGroupChoicesResult;
import com.echothree.control.user.contactlist.common.result.GetContactListTypeChoicesResult;
import com.echothree.control.user.contactlist.common.result.GetPartyContactListStatusChoicesResult;
import com.echothree.model.control.contactlist.common.choice.ContactListFrequencyChoicesBean;
import com.echothree.model.control.contactlist.common.choice.ContactListGroupChoicesBean;
import com.echothree.model.control.contactlist.common.choice.ContactListTypeChoicesBean;
import com.echothree.model.control.contactlist.common.choice.PartyContactListStatusChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ContactListAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ContactListGroupChoicesBean contactListGroupChoices;
    private ContactListTypeChoicesBean contactListTypeChoices;
    private ContactListFrequencyChoicesBean contactListFrequencyChoices;
    private PartyContactListStatusChoicesBean defaultPartyContactListStatusChoices;

    private String contactListName;
    private String contactListGroupChoice;
    private String contactListTypeChoice;
    private String contactListFrequencyChoice;
    private String defaultPartyContactListStatusChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupContactListGroupChoices() {
        if(contactListGroupChoices == null) {
            try {
                GetContactListGroupChoicesForm commandForm = ContactListUtil.getHome().getGetContactListGroupChoicesForm();

                commandForm.setDefaultContactListGroupChoice(contactListGroupChoice);
                commandForm.setAllowNullChoice(Boolean.FALSE.toString());

                CommandResult commandResult = ContactListUtil.getHome().getContactListGroupChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetContactListGroupChoicesResult getContactListGroupChoicesResult = (GetContactListGroupChoicesResult)executionResult.getResult();
                contactListGroupChoices = getContactListGroupChoicesResult.getContactListGroupChoices();

                if(contactListGroupChoice == null) {
                    contactListGroupChoice = contactListGroupChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, contactListGroupChoices remains null, no default
            }
        }
    }

    private void setupContactListTypeChoices() {
        if(contactListTypeChoices == null) {
            try {
                GetContactListTypeChoicesForm commandForm = ContactListUtil.getHome().getGetContactListTypeChoicesForm();

                commandForm.setDefaultContactListTypeChoice(contactListTypeChoice);
                commandForm.setAllowNullChoice(Boolean.FALSE.toString());

                CommandResult commandResult = ContactListUtil.getHome().getContactListTypeChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetContactListTypeChoicesResult getContactListTypeChoicesResult = (GetContactListTypeChoicesResult)executionResult.getResult();
                contactListTypeChoices = getContactListTypeChoicesResult.getContactListTypeChoices();

                if(contactListTypeChoice == null) {
                    contactListTypeChoice = contactListTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, contactListTypeChoices remains null, no default
            }
        }
    }

    private void setupContactListFrequencyChoices() {
        if(contactListFrequencyChoices == null) {
            try {
                GetContactListFrequencyChoicesForm commandForm = ContactListUtil.getHome().getGetContactListFrequencyChoicesForm();

                commandForm.setDefaultContactListFrequencyChoice(contactListFrequencyChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());

                CommandResult commandResult = ContactListUtil.getHome().getContactListFrequencyChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetContactListFrequencyChoicesResult getContactListFrequencyChoicesResult = (GetContactListFrequencyChoicesResult)executionResult.getResult();
                contactListFrequencyChoices = getContactListFrequencyChoicesResult.getContactListFrequencyChoices();

                if(contactListFrequencyChoice == null) {
                    contactListFrequencyChoice = contactListFrequencyChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, contactListFrequencyChoices remains null, no default
            }
        }
    }

    private void setupDefaultPartyContactListStatusChoices() {
        if(defaultPartyContactListStatusChoices == null) {
            try {
                GetPartyContactListStatusChoicesForm commandForm = ContactListUtil.getHome().getGetPartyContactListStatusChoicesForm();

                commandForm.setDefaultPartyContactListStatusChoice(defaultPartyContactListStatusChoice);
                commandForm.setAllowNullChoice(Boolean.FALSE.toString());

                CommandResult commandResult = ContactListUtil.getHome().getPartyContactListStatusChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetPartyContactListStatusChoicesResult getPartyContactListStatusChoicesResult = (GetPartyContactListStatusChoicesResult)executionResult.getResult();
                defaultPartyContactListStatusChoices = getPartyContactListStatusChoicesResult.getPartyContactListStatusChoices();

                if(defaultPartyContactListStatusChoice == null) {
                    defaultPartyContactListStatusChoice = defaultPartyContactListStatusChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, partyContactListStatusChoices remains null, no default
            }
        }
    }

    public void setContactListName(String contactListName) {
        this.contactListName = contactListName;
    }
    
    public String getContactListName() {
        return contactListName;
    }

    public List<LabelValueBean> getContactListGroupChoices() {
        List<LabelValueBean> choices = null;

        setupContactListGroupChoices();
        if(contactListGroupChoices != null) {
            choices = convertChoices(contactListGroupChoices);
        }

        return choices;
    }

    public void setContactListGroupChoice(String contactListGroupChoice) {
        this.contactListGroupChoice = contactListGroupChoice;
    }

    public String getContactListGroupChoice() {
        setupContactListGroupChoices();

        return contactListGroupChoice;
    }

    public List<LabelValueBean> getContactListTypeChoices() {
        List<LabelValueBean> choices = null;

        setupContactListTypeChoices();
        if(contactListTypeChoices != null) {
            choices = convertChoices(contactListTypeChoices);
        }

        return choices;
    }

    public void setContactListTypeChoice(String contactListTypeChoice) {
        this.contactListTypeChoice = contactListTypeChoice;
    }

    public String getContactListTypeChoice() {
        setupContactListTypeChoices();

        return contactListTypeChoice;
    }

    public List<LabelValueBean> getContactListFrequencyChoices() {
        List<LabelValueBean> choices = null;

        setupContactListFrequencyChoices();
        if(contactListFrequencyChoices != null) {
            choices = convertChoices(contactListFrequencyChoices);
        }

        return choices;
    }

    public void setContactListFrequencyChoice(String contactListFrequencyChoice) {
        this.contactListFrequencyChoice = contactListFrequencyChoice;
    }

    public String getContactListFrequencyChoice() {
        setupContactListFrequencyChoices();

        return contactListFrequencyChoice;
    }

    public List<LabelValueBean> getDefaultPartyContactListStatusChoices() {
        List<LabelValueBean> choices = null;

        setupDefaultPartyContactListStatusChoices();
        if(defaultPartyContactListStatusChoices != null) {
            choices = convertChoices(defaultPartyContactListStatusChoices);
        }

        return choices;
    }

    public void setDefaultPartyContactListStatusChoice(String defaultPartyContactListStatusChoice) {
        this.defaultPartyContactListStatusChoice = defaultPartyContactListStatusChoice;
    }

    public String getDefaultPartyContactListStatusChoice() {
        setupDefaultPartyContactListStatusChoices();

        return defaultPartyContactListStatusChoice;
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
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        
        isDefault = Boolean.FALSE;
    }

}
