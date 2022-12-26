// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.ui.web.main.action.chain.letter;

import com.echothree.control.user.contactlist.common.ContactListUtil;
import com.echothree.control.user.contactlist.common.form.GetContactListChoicesForm;
import com.echothree.control.user.contactlist.common.result.GetContactListChoicesResult;
import com.echothree.control.user.letter.common.LetterUtil;
import com.echothree.control.user.letter.common.form.GetLetterSourceChoicesForm;
import com.echothree.control.user.letter.common.result.GetLetterSourceChoicesResult;
import com.echothree.model.control.contactlist.common.choice.ContactListChoicesBean;
import com.echothree.model.control.letter.common.choice.LetterSourceChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="LetterAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private LetterSourceChoicesBean letterSourceChoices;
    private ContactListChoicesBean contactListChoices;
    
    private String chainKindName;
    private String chainTypeName;
    private String letterName;
    private String letterSourceChoice;
    private String contactListChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupLetterSourceChoices() {
        if(letterSourceChoices == null) {
            try {
                GetLetterSourceChoicesForm commandForm = LetterUtil.getHome().getGetLetterSourceChoicesForm();
                
                commandForm.setDefaultLetterSourceChoice(letterSourceChoice);
                commandForm.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = LetterUtil.getHome().getLetterSourceChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetLetterSourceChoicesResult result = (GetLetterSourceChoicesResult)executionResult.getResult();
                letterSourceChoices = result.getLetterSourceChoices();
                
                if(letterSourceChoice == null) {
                    letterSourceChoice = letterSourceChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, letterSourceChoices remains null, no default
            }
        }
    }
    
    private void setupContactListChoices() {
        if(contactListChoices == null) {
            try {
                GetContactListChoicesForm commandForm = ContactListUtil.getHome().getGetContactListChoicesForm();
                
                commandForm.setDefaultContactListChoice(contactListChoice);
                commandForm.setAllowNullChoice(Boolean.TRUE.toString());
                
                CommandResult commandResult = ContactListUtil.getHome().getContactListChoices(userVisitPK, commandForm);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetContactListChoicesResult result = (GetContactListChoicesResult)executionResult.getResult();
                contactListChoices = result.getContactListChoices();
                
                if(contactListChoice == null) {
                    contactListChoice = contactListChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, contactListChoices remains null, no default
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
    
    public List<LabelValueBean> getLetterSourceChoices() {
        List<LabelValueBean> choices = null;
        
        setupLetterSourceChoices();
        if(letterSourceChoices != null) {
            choices = convertChoices(letterSourceChoices);
        }
        
        return choices;
    }
    
    public void setLetterSourceChoice(String letterSourceChoice) {
        this.letterSourceChoice = letterSourceChoice;
    }
    
    public String getLetterSourceChoice() {
        setupLetterSourceChoices();
        return letterSourceChoice;
    }
    
    public List<LabelValueBean> getContactListChoices() {
        List<LabelValueBean> choices = null;
        
        setupContactListChoices();
        if(contactListChoices != null) {
            choices = convertChoices(contactListChoices);
        }
        
        return choices;
    }
    
    public void setContactListChoice(String contactListChoice) {
        this.contactListChoice = contactListChoice;
    }
    
    public String getContactListChoice() {
        setupContactListChoices();
        return contactListChoice;
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
