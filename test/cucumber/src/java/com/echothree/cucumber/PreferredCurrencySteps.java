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

package com.echothree.cucumber;

import com.echothree.control.user.user.common.UserService;
import com.echothree.control.user.user.common.UserUtil;
import com.echothree.control.user.user.common.form.SetUserVisitPreferredCurrencyForm;
import cucumber.api.java.en.When;
import javax.naming.NamingException;

public class PreferredCurrencySteps {

    @When("^the customer ([^\"]*) sets their preferred currency to \"([^\"]*)\"$")
    public void theCustomerSetsTheirPreferredCurrencyTo(String persona, String currencyIsoName)
            throws NamingException {
        UserService userService = UserUtil.getHome();
        SetUserVisitPreferredCurrencyForm userVisitPreferredCurrencyForm = userService.getSetUserVisitPreferredCurrencyForm();
        CustomerPersona customerPersona = CustomerPersonas.getCustomerPersona(persona);

        userVisitPreferredCurrencyForm.setCurrencyIsoName(currencyIsoName);

        LastCommandResult.commandResult = userService.setUserVisitPreferredCurrency(customerPersona.userVisitPK,
                userVisitPreferredCurrencyForm);
    }
}
