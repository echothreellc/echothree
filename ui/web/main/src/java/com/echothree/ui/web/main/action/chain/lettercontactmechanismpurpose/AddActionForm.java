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

package com.echothree.ui.web.main.action.chain.lettercontactmechanismpurpose;

import com.echothree.control.user.contact.common.ContactUtil;
import com.echothree.control.user.contact.common.result.GetContactMechanismPurposeChoicesResult;
import com.echothree.model.control.contact.common.choice.ContactMechanismPurposeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="LetterContactMechanismPurposeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ContactMechanismPurposeChoicesBean contactMechanismPurposeChoices;
    
    private String chainKindName;
    private String chainTypeName;
    private String letterName;
    private String priority;
    private String contactMechanismPurposeChoice;
    
    private void setupContactMechanismPurposeChoices()
            throws NamingException {
        if(contactMechanismPurposeChoices == null) {
            var commandForm = ContactUtil.getHome().getGetContactMechanismPurposeChoicesForm();

            commandForm.setDefaultContactMechanismPurposeChoice(contactMechanismPurposeChoice);
            commandForm.setAllowNullChoice(String.valueOf(false));

            var commandResult = ContactUtil.getHome().getContactMechanismPurposeChoices(userVisitPK, commandForm);
            var executionResult = commandResult.getExecutionResult();
            var result = (GetContactMechanismPurposeChoicesResult)executionResult.getResult();
            contactMechanismPurposeChoices = result.getContactMechanismPurposeChoices();

            if(contactMechanismPurposeChoice == null) {
                contactMechanismPurposeChoice = contactMechanismPurposeChoices.getDefaultValue();
            }
        }
    }
    
    public void setChainKindName(String chainKindName) {
        this.chainKindName = chainKindName;
    }
    
    public String getChainKindName() {
        return chainKindName;
    }
    
    public void setChainTypeName(String chainTypeName) {
        this.chainTypeName = chainTypeName;
    }
    
    public String getChainTypeName() {
        return chainTypeName;
    }
    
    public void setLetterName(String letterName) {
        this.letterName = letterName;
    }
    
    public String getLetterName() {
        return letterName;
    }
    
    public String getPriority() {
        return priority;
    }
    
    public void setPriority(String priority) {
        this.priority = priority;
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
    
    public void setContactMechanismPurposeChoice(String contactMechanismPurposeChoice) {
        this.contactMechanismPurposeChoice = contactMechanismPurposeChoice;
    }
    
    public String getContactMechanismPurposeChoice()
            throws NamingException {
        setupContactMechanismPurposeChoices();
        
        return contactMechanismPurposeChoice;
    }
    
}
