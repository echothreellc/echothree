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

package com.echothree.ui.web.main.action.accounting.companycarrieraccount;

import com.echothree.control.user.carrier.common.CarrierUtil;
import com.echothree.control.user.carrier.common.result.GetCarrierChoicesResult;
import com.echothree.model.control.carrier.common.choice.CarrierChoicesBean;
import com.echothree.view.client.web.struts.BaseLanguageActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

@SproutForm(name="CompanyCarrierAccountAdd")
public class AddActionForm
        extends BaseLanguageActionForm {

    private CarrierChoicesBean carrierChoices;

    private String partyName;
    private String carrierChoice;
    private String account;
    private Boolean alwaysUseThirdPartyBilling;

    private void setupCarrierChoices() {
        if(carrierChoices == null) {
            try {
                var commandForm = CarrierUtil.getHome().getGetCarrierChoicesForm();

                commandForm.setDefaultCarrierChoice(carrierChoice);
                commandForm.setAllowNullChoice(String.valueOf(false));

                var commandResult = CarrierUtil.getHome().getCarrierChoices(userVisitPK, commandForm);
                var executionResult = commandResult.getExecutionResult();
                var result = (GetCarrierChoicesResult)executionResult.getResult();
                carrierChoices = result.getCarrierChoices();

                if(carrierChoice == null) {
                    carrierChoice = carrierChoices.getDefaultValue();
                }
            } catch (NamingException ne) {
                // failed, carrierChoices remains null, no default
            }
        }
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public List<LabelValueBean> getCarrierChoices() {
        List<LabelValueBean> choices = null;

        setupCarrierChoices();
        if(carrierChoices != null) {
            choices = convertChoices(carrierChoices);
        }

        return choices;
    }

    public void setCarrierChoice(String carrierChoice) {
        this.carrierChoice = carrierChoice;
    }

    public String getCarrierChoice() {
        setupCarrierChoices();

        return carrierChoice;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Boolean getAlwaysUseThirdPartyBilling() {
        return alwaysUseThirdPartyBilling;
    }

    public void setAlwaysUseThirdPartyBilling(Boolean alwaysUseThirdPartyBilling) {
        this.alwaysUseThirdPartyBilling = alwaysUseThirdPartyBilling;
    }

    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);

        alwaysUseThirdPartyBilling = false;
    }

}
