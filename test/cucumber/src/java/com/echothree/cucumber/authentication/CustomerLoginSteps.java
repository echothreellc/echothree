// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.cucumber.authentication;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.cucumber.util.persona.CustomerPersona;
import com.echothree.cucumber.util.persona.CustomerPersonas;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerLoginSteps implements En {

    public CustomerLoginSteps() {
        After(() -> {
                    for(Map.Entry<String, CustomerPersona> customerPersona : CustomerPersonas.getPersonaEntries()) {
                        var authenticationService = AuthenticationUtil.getHome();
                        var commandResult = authenticationService.logout(customerPersona.getValue().userVisitPK);

                        assertThat(commandResult.hasErrors()).isFalse();
                    }
                });

        When("^the user logs in as a customer with the username \"([^\"]*)\" and password \"([^\"]*)\"$",
                (String username, String password) -> {
                    var authenticationService = AuthenticationUtil.getHome();
                    var customerLoginForm = authenticationService.getCustomerLoginForm();

                    customerLoginForm.setUsername(username);
                    customerLoginForm.setPassword(password);
                    customerLoginForm.setRemoteInet4Address("0.0.0.0");

                    LastCommandResult.commandResult = authenticationService.customerLogin(CurrentPersona.persona.userVisitPK, customerLoginForm);
                });
    }

}
