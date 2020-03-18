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

package com.echothree.cucumber.authentication;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.cucumber.CustomerPersona;
import com.echothree.cucumber.CustomerPersonas;
import com.echothree.cucumber.LastCommandResult;
import io.cucumber.java8.En;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerLoginSteps implements En {

    public CustomerLoginSteps() {
        After(() -> {
                    for(Map.Entry<String, CustomerPersona> customerPersona : CustomerPersonas.getCustomerPersonas()) {
                        var authenticationService = AuthenticationUtil.getHome();
                        var commandResult = authenticationService.logout(customerPersona.getValue().userVisitPK);

                        assertThat(commandResult.hasErrors()).isFalse();
                    }
                });

        Given("^the customer ([^\"]*) is currently logged in$",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    assertThat(customerPersona).isNotNull();
                });

        Given("^the customer ([^\"]*) is not currently logged in$",
                (String persona) -> {
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    if(customerPersona != null) {
                        var authenticationService = AuthenticationUtil.getHome();
                        var commandResult = authenticationService.logout(customerPersona.userVisitPK);

                        assertThat(commandResult.hasErrors()).isFalse();
                    }
                });

        When("^the customer ([^\"]*) logs in with the username \"([^\"]*)\" and password \"([^\"]*)\"$",
                (String persona, String username, String password) -> {
                    var authenticationService = AuthenticationUtil.getHome();
                    var customerLoginForm = authenticationService.getCustomerLoginForm();
                    var customerPersona = CustomerPersonas.getCustomerPersona(persona);

                    customerLoginForm.setUsername(username);
                    customerLoginForm.setPassword(password);
                    customerLoginForm.setRemoteInet4Address("0.0.0.0");

                    LastCommandResult.commandResult = authenticationService.customerLogin(customerPersona.userVisitPK, customerLoginForm);
                });
    }

}
