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

package com.echothree.ui.web.main.action.contactlist.contactlisttype;

import com.echothree.control.user.chain.common.ChainUtil;
import com.echothree.control.user.chain.common.result.GetChainChoicesResult;
import com.echothree.model.control.chain.common.ChainConstants;
import com.echothree.model.control.chain.common.choice.ChainChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="ContactListTypeAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private ChainChoicesBean confirmationRequestChainChoices;
    private ChainChoicesBean subscribeChainChoices;
    private ChainChoicesBean unsubscribeChainChoices;

    private String contactListTypeName;
    private Boolean usedForSolicitation;
    private String confirmationRequestChainChoice;
    private String subscribeChainChoice;
    private String unsubscribeChainChoice;
    private Boolean isDefault;
    private String sortOrder;
    private String description;
    
    private void setupConfirmationRequestChainChoices() {
        if(confirmationRequestChainChoices == null) {
            try {
                var commandForm = ChainUtil.getHome().getGetChainChoicesForm();

                commandForm.setChainKindName(ChainConstants.ChainKind_CONTACT_LIST);
                commandForm.setChainTypeName(ChainConstants.ChainType_CONFIRMATION_REQUEST);
                commandForm.setDefaultChainChoice(confirmationRequestChainChoice);
                commandForm.setAllowNullChoice(String.valueOf(true));

                var commandResult = ChainUtil.getHome().getChainChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var getPartyContactListStatusChoicesResult = (GetChainChoicesResult)executionResult.getResult();
                confirmationRequestChainChoices = getPartyContactListStatusChoicesResult.getChainChoices();

                if(confirmationRequestChainChoice == null) {
                    confirmationRequestChainChoice = confirmationRequestChainChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                throw new RuntimeException(ne);
            }
        }
    }

    private void setupSubscribeChainChoices() {
        if(subscribeChainChoices == null) {
            try {
                var commandForm = ChainUtil.getHome().getGetChainChoicesForm();

                commandForm.setChainKindName(ChainConstants.ChainKind_CONTACT_LIST);
                commandForm.setChainTypeName(ChainConstants.ChainType_SUBSCRIBE);
                commandForm.setDefaultChainChoice(subscribeChainChoice);
                commandForm.setAllowNullChoice(String.valueOf(true));

                var commandResult = ChainUtil.getHome().getChainChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var getPartyContactListStatusChoicesResult = (GetChainChoicesResult)executionResult.getResult();
                subscribeChainChoices = getPartyContactListStatusChoicesResult.getChainChoices();

                if(subscribeChainChoice == null) {
                    subscribeChainChoice = subscribeChainChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                throw new RuntimeException(ne);
            }
        }
    }

    private void setupUnsubscribeChainChoices() {
        if(unsubscribeChainChoices == null) {
            try {
                var commandForm = ChainUtil.getHome().getGetChainChoicesForm();

                commandForm.setChainKindName(ChainConstants.ChainKind_CONTACT_LIST);
                commandForm.setChainTypeName(ChainConstants.ChainType_UNSUBSCRIBE);
                commandForm.setDefaultChainChoice(unsubscribeChainChoice);
                commandForm.setAllowNullChoice(String.valueOf(true));

                var commandResult = ChainUtil.getHome().getChainChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var getPartyContactListStatusChoicesResult = (GetChainChoicesResult)executionResult.getResult();
                unsubscribeChainChoices = getPartyContactListStatusChoicesResult.getChainChoices();

                if(unsubscribeChainChoice == null) {
                    unsubscribeChainChoice = unsubscribeChainChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                throw new RuntimeException(ne);
            }
        }
    }

    public void setContactListTypeName(String contactListTypeName) {
        this.contactListTypeName = contactListTypeName;
    }
    
    public String getContactListTypeName() {
        return contactListTypeName;
    }

    public List<LabelValueBean> getConfirmationRequestChainChoices() {
        List<LabelValueBean> choices = null;

        setupConfirmationRequestChainChoices();
        if(confirmationRequestChainChoices != null) {
            choices = convertChoices(confirmationRequestChainChoices);
        }

        return choices;
    }

    public void setConfirmationRequestChainChoice(String confirmationRequestChainChoice) {
        this.confirmationRequestChainChoice = confirmationRequestChainChoice;
    }

    public String getConfirmationRequestChainChoice() {
        setupConfirmationRequestChainChoices();

        return confirmationRequestChainChoice;
    }

    public List<LabelValueBean> getSubscribeChainChoices() {
        List<LabelValueBean> choices = null;

        setupSubscribeChainChoices();
        if(subscribeChainChoices != null) {
            choices = convertChoices(subscribeChainChoices);
        }

        return choices;
    }

    public void setSubscribeChainChoice(String subscribeChainChoice) {
        this.subscribeChainChoice = subscribeChainChoice;
    }

    public String getSubscribeChainChoice() {
        setupSubscribeChainChoices();

        return subscribeChainChoice;
    }

    public List<LabelValueBean> getUnsubscribeChainChoices() {
        List<LabelValueBean> choices = null;

        setupUnsubscribeChainChoices();
        if(unsubscribeChainChoices != null) {
            choices = convertChoices(unsubscribeChainChoices);
        }

        return choices;
    }

    public void setUnsubscribeChainChoice(String unsubscribeChainChoice) {
        this.unsubscribeChainChoice = unsubscribeChainChoice;
    }

    public String getUnsubscribeChainChoice() {
        setupUnsubscribeChainChoices();

        return unsubscribeChainChoice;
    }

    public Boolean getUsedForSolicitation() {
        return usedForSolicitation;
    }

    public void setUsedForSolicitation(Boolean usedForSolicitation) {
        this.usedForSolicitation = usedForSolicitation;
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
        
        usedForSolicitation = false;
        isDefault = false;
    }

}
