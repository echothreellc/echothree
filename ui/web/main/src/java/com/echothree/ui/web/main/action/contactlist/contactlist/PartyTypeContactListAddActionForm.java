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

import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.GetPartyTypeChoicesResult;
import com.echothree.model.control.party.common.choice.PartyTypeChoicesBean;
import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="PartyTypeContactListAdd")
public class PartyTypeContactListAddActionForm
        extends BaseActionForm {
    
    private PartyTypeChoicesBean partyTypeChoices;

    private String contactListName;
    private String partyTypeChoice;
    private Boolean addWhenCreated;
    
    private void setupPartyTypeChoices()
            throws NamingException {
        if(partyTypeChoices == null) {
            var form = PartyUtil.getHome().getGetPartyTypeChoicesForm();

            form.setDefaultPartyTypeChoice(partyTypeChoice);
            form.setAllowNullChoice(String.valueOf(false));

            var commandResult = PartyUtil.getHome().getPartyTypeChoices(userVisitPK, form);
            var executionResult = commandResult.getExecutionResult();
            var getPartyTypeChoicesResult = (GetPartyTypeChoicesResult)executionResult.getResult();
            partyTypeChoices = getPartyTypeChoicesResult.getPartyTypeChoices();

            if(partyTypeChoice == null) {
                partyTypeChoice = partyTypeChoices.getDefaultValue();
            }
        }
    }

    public void setContactListName(String contactListName) {
        this.contactListName = contactListName;
    }
    
    public String getContactListName() {
        return contactListName;
    }
    
    public String getPartyTypeChoice()
            throws NamingException {
        setupPartyTypeChoices();

        return partyTypeChoice;
    }

    public void setPartyTypeChoice(String partyTypeChoice) {
        this.partyTypeChoice = partyTypeChoice;
    }

    public List<LabelValueBean> getPartyTypeChoices()
            throws NamingException {
        List<LabelValueBean> choices = null;

        setupPartyTypeChoices();
        if(partyTypeChoices != null) {
            choices = convertChoices(partyTypeChoices);
        }

        return choices;
    }
    
    public Boolean getAddWhenCreated() {
        return addWhenCreated;
    }

    public void setAddWhenCreated(Boolean addWhenCreated) {
        this.addWhenCreated = addWhenCreated;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        addWhenCreated = false;
    }

}
