// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.ui.web.main.action.customer.customeralias;

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.form.GetPartyAliasTypeChoicesForm;
import com.echothree.control.user.party.common.result.GetPartyAliasTypeChoicesResult;
import com.echothree.model.control.party.common.PartyConstants;
import com.echothree.model.control.party.common.choice.PartyAliasTypeChoicesBean;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerAliasAdd")
public class AddActionForm
        extends BaseActionForm {
    
    private PartyAliasTypeChoicesBean partyAliasTypeChoices;

    private String partyName;
    private String partyAliasTypeChoice;
    private String alias;

    public void setupPartyAliasTypeChoices() {
        if(partyAliasTypeChoices == null) {
            try {
                GetPartyAliasTypeChoicesForm form = PartyUtil.getHome().getGetPartyAliasTypeChoicesForm();

                form.setPartyTypeName(PartyConstants.PartyType_CUSTOMER);
                form.setDefaultPartyAliasTypeChoice(partyAliasTypeChoice);
                form.setAllowNullChoice(Boolean.FALSE.toString());
                
                CommandResult commandResult = PartyUtil.getHome().getPartyAliasTypeChoices(userVisitPK, form);
                ExecutionResult executionResult = commandResult.getExecutionResult();
                GetPartyAliasTypeChoicesResult getPartyAliasTypeChoicesResult = (GetPartyAliasTypeChoicesResult)executionResult.getResult();
                partyAliasTypeChoices = getPartyAliasTypeChoicesResult.getPartyAliasTypeChoices();
                
                if(partyAliasTypeChoice == null) {
                    partyAliasTypeChoice = partyAliasTypeChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                // failed, partyAliasTypeChoices remains null, no default
            }
        }
    }
    
    /**
     * @return the partyName
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * @param partyName the partyName to set
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public List<LabelValueBean> getPartyAliasTypeChoices() {
        List<LabelValueBean> choices = null;
        
        setupPartyAliasTypeChoices();
        if(partyAliasTypeChoices != null)
            choices = convertChoices(partyAliasTypeChoices);
        
        return choices;
    }
    
    public void setPartyAliasTypeChoice(String partyAliasTypeChoice) {
        this.partyAliasTypeChoice = partyAliasTypeChoice;
    }
    
    public String getPartyAliasTypeChoice() {
        setupPartyAliasTypeChoices();
        return partyAliasTypeChoice;
    }

    /**
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
}
