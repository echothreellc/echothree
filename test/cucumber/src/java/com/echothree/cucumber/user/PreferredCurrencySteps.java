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

package com.echothree.cucumber.user;

import com.echothree.control.user.user.common.UserService;
import com.echothree.control.user.user.common.UserUtil;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;

public class PreferredCurrencySteps implements En {

    public PreferredCurrencySteps() {
        When("^the user sets their preferred currency to \"([^\"]*)\"$",
                (String currencyIsoName) -> {
                    UserService userService = UserUtil.getHome();
                    var userVisitPreferredCurrencyForm = userService.getSetUserVisitPreferredCurrencyForm();
                    var persona = CurrentPersona.persona;

                    userVisitPreferredCurrencyForm.setCurrencyIsoName(currencyIsoName);

                    LastCommandResult.commandResult = userService.setUserVisitPreferredCurrency(persona.userVisitPK,
                            userVisitPreferredCurrencyForm);
                });
    }

}
