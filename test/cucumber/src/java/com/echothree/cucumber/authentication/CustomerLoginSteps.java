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

package com.echothree.cucumber.authentication;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.cucumber.util.persona.CustomerPersonas;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerLoginSteps implements En {

    public CustomerLoginSteps() {
        After(() -> {
                    for(var customerPersona : CustomerPersonas.getPersonaEntries()) {
                        var authenticationService = AuthenticationUtil.getHome();
                        var commandResult = authenticationService.logout(customerPersona.getValue().userVisitPK);

                        assertThat(commandResult.hasErrors()).isFalse();
                    }
                });

        When("^the user begins to log in as an customer$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.customerLoginForm).isNull();

                    persona.customerLoginForm = AuthenticationUtil.getHome().getCustomerLoginForm();
                });

        And("^the customer sets the username to \"([^\"]*)\"$",
                (String username) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.customerLoginForm).isNotNull();

                    persona.customerLoginForm.setUsername(username);
                });

        And("^the customer sets the password to \"([^\"]*)\"$",
                (String password) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.customerLoginForm).isNotNull();

                    persona.customerLoginForm.setPassword(password);
                });

        And("^the customer sets the remote IPv4 address to \"([^\"]*)\"$",
                (String remoteInet4Address) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.customerLoginForm).isNotNull();

                    persona.customerLoginForm.setRemoteInet4Address(remoteInet4Address);
                });

        And("^the customer logs in$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var customerLoginForm = persona.customerLoginForm;

                    assertThat(persona.customerLoginForm).isNotNull();

                    var authenticationService = AuthenticationUtil.getHome();

                    LastCommandResult.commandResult = authenticationService.customerLogin(persona.userVisitPK, customerLoginForm);

                    persona.customerLoginForm = null;
                });
    }

}
