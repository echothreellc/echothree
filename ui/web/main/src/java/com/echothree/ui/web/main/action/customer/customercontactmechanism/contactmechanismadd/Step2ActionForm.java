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

package com.echothree.ui.web.main.action.customer.customercontactmechanism.contactmechanismadd;

import com.echothree.control.user.geo.common.GeoUtil;
import com.echothree.control.user.geo.common.result.GetCountryChoicesResult;
import com.echothree.model.control.geo.common.choice.CountryChoicesBean;
import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CustomerContactMechanismAddStep2")
public class Step2ActionForm
        extends BaseLanguageActionForm {

    private CountryChoicesBean countryChoices;
    private String partyName;
    private String contactMechanismTypeName;
    private String countryChoice;

    private void setupCountryChoices() {
        if(countryChoices == null) {
            try {
                var commandForm = GeoUtil.getHome().getGetCountryChoicesForm();

                commandForm.setDefaultCountryChoice(countryChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = GeoUtil.getHome().getCountryChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetCountryChoicesResult)executionResult.getResult();
                countryChoices = result.getCountryChoices();

                if(countryChoice == null) {
                    countryChoice = countryChoices.getDefaultValue();
                }
            } catch(NamingException ne) {
                // failed, countryChoices remains null, no default
            }
        }
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getContactMechanismTypeName() {
        return contactMechanismTypeName;
    }

    public void setContactMechanismTypeName(String contactMechanismTypeName) {
        this.contactMechanismTypeName = contactMechanismTypeName;
    }

    public List<LabelValueBean> getCountryChoices() {
        List<LabelValueBean> choices = null;

        setupCountryChoices();
        if(countryChoices != null) {
            choices = convertChoices(countryChoices);
        }

        return choices;
    }

    public void setCountryChoice(String countryChoice) {
        this.countryChoice = countryChoice;
    }

    public String getCountryChoice() {
        setupCountryChoices();

        return countryChoice;
    }

}
