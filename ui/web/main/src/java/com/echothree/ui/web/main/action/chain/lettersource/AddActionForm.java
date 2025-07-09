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

package com.echothree.ui.web.main.action.chain.lettersource;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.GetContactMechanismChoicesResult;
import com.echothree.model.control.contact.common.ContactMechanismTypes;
import com.echothree.model.control.contact.common.choice.ContactMechanismChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="LetterSourceAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ContactMechanismChoicesBean emailAddressContactMechanismChoices;
    private ContactMechanismChoicesBean postalAddressContactMechanismChoices;
    private ContactMechanismChoicesBean letterSourceContactMechanismChoices;
    
    private String partyName;
    private String letterSourceName;
    private String emailAddressContactMechanismChoice;
    private String postalAddressContactMechanismChoice;
    private String letterSourceContactMechanismChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupEmailAddressContactMechanismChoices()
            throws NamingException {
        if(emailAddressContactMechanismChoices == null) {
            var commandForm = ContactUtil.getHome().getGetContactMechanismChoicesForm();

            commandForm.setPartyName(partyName);
            commandForm.setContactMechanismTypeName(ContactMechanismTypes.EMAIL_ADDRESS.name());
            commandForm.setDefaultContactMechanismChoice(emailAddressContactMechanismChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = ContactUtil.getHome().getContactMechanismChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContactMechanismChoicesResult)executionResult.getResult();
            emailAddressContactMechanismChoices = result.getContactMechanismChoices();

            if(emailAddressContactMechanismChoice == null) {
                emailAddressContactMechanismChoice = emailAddressContactMechanismChoices.getDefaultValue();
            }
        }
    }
    
    private void setupPostalAddressContactMechanismChoices()
            throws NamingException {
        if(postalAddressContactMechanismChoices == null) {
            var commandForm = ContactUtil.getHome().getGetContactMechanismChoicesForm();

            commandForm.setPartyName(partyName);
            commandForm.setContactMechanismTypeName(ContactMechanismTypes.POSTAL_ADDRESS.name());
            commandForm.setDefaultContactMechanismChoice(postalAddressContactMechanismChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = ContactUtil.getHome().getContactMechanismChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContactMechanismChoicesResult)executionResult.getResult();
            postalAddressContactMechanismChoices = result.getContactMechanismChoices();

            if(postalAddressContactMechanismChoice == null) {
                postalAddressContactMechanismChoice = postalAddressContactMechanismChoices.getDefaultValue();
            }
        }
    }
    
    private void setupLetterSourceContactMechanismChoices()
            throws NamingException {
        if(letterSourceContactMechanismChoices == null) {
            var commandForm = ContactUtil.getHome().getGetContactMechanismChoicesForm();

            commandForm.setPartyName(partyName);
            commandForm.setContactMechanismTypeName(ContactMechanismTypes.WEB_ADDRESS.name());
            commandForm.setDefaultContactMechanismChoice(letterSourceContactMechanismChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = ContactUtil.getHome().getContactMechanismChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContactMechanismChoicesResult)executionResult.getResult();
            letterSourceContactMechanismChoices = result.getContactMechanismChoices();

            if(letterSourceContactMechanismChoice == null) {
                letterSourceContactMechanismChoice = letterSourceContactMechanismChoices.getDefaultValue();
            }
        }
    }
    
    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    
    public void setLetterSourceName(String letterSourceName) {
        this.letterSourceName = letterSourceName;
    }
    
    public String getLetterSourceName() {
        return letterSourceName;
    }
    
    public List<LabelValueBean> getEmailAddressContactMechanismChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupEmailAddressContactMechanismChoices();
        if(emailAddressContactMechanismChoices != null) {
            choices = convertChoices(emailAddressContactMechanismChoices);
        }
        
        return choices;
    }
    
    public void setEmailAddressContactMechanismChoice(String emailAddressContactMechanismChoice) {
        this.emailAddressContactMechanismChoice = emailAddressContactMechanismChoice;
    }
    
    public String getEmailAddressContactMechanismChoice()
            throws NamingException {
        setupEmailAddressContactMechanismChoices();
        
        return emailAddressContactMechanismChoice;
    }
    
    public List<LabelValueBean> getPostalAddressContactMechanismChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupPostalAddressContactMechanismChoices();
        if(postalAddressContactMechanismChoices != null) {
            choices = convertChoices(postalAddressContactMechanismChoices);
        }
        
        return choices;
    }
    
    public void setPostalAddressContactMechanismChoice(String postalAddressContactMechanismChoice) {
        this.postalAddressContactMechanismChoice = postalAddressContactMechanismChoice;
    }
    
    public String getPostalAddressContactMechanismChoice()
            throws NamingException {
        setupPostalAddressContactMechanismChoices();
        
        return postalAddressContactMechanismChoice;
    }
    
    public List<LabelValueBean> getLetterSourceContactMechanismChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;
        
        setupLetterSourceContactMechanismChoices();
        if(letterSourceContactMechanismChoices != null) {
            choices = convertChoices(letterSourceContactMechanismChoices);
        }
        
        return choices;
    }
    
    public void setLetterSourceContactMechanismChoice(String letterSourceContactMechanismChoice) {
        this.letterSourceContactMechanismChoice = letterSourceContactMechanismChoice;
    }
    
    public String getLetterSourceContactMechanismChoice()
            throws NamingException {
        setupLetterSourceContactMechanismChoices();
        
        return letterSourceContactMechanismChoice;
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
        
        isDefault = false;
    }

}
