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
import com.echothree.control.user.item.common.ItemUtil;
import com.echothree.control.user.item.common.result.CreateItemResult;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.cucumber.util.persona.EmployeePersona;
import com.echothree.cucumber.util.persona.EmployeePersonas;
import io.cucumber.java8.En;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeLoginSteps implements En {

    public EmployeeLoginSteps() {
        After(() -> {
                    for(Map.Entry<String, EmployeePersona> employeePersona : EmployeePersonas.getPersonaEntries()) {
                        var authenticationService = AuthenticationUtil.getHome();
                        var commandResult = authenticationService.logout(employeePersona.getValue().userVisitPK);

                        assertThat(commandResult.hasErrors()).isFalse();
                    }
                });

        When("^the user logs in as an employee with the username \"([^\"]*)\" and password \"([^\"]*)\" and company \"([^\"]*)\"$",
                (String username, String password, String companyName) -> {
                    var authenticationService = AuthenticationUtil.getHome();
                    var employeeLoginForm = authenticationService.getEmployeeLoginForm();

                    employeeLoginForm.setUsername(username);
                    employeeLoginForm.setPassword(password);
                    employeeLoginForm.setCompanyName(companyName);
                    employeeLoginForm.setRemoteInet4Address("0.0.0.0");

                    LastCommandResult.commandResult = authenticationService.employeeLogin(CurrentPersona.persona.userVisitPK, employeeLoginForm);
                });

        When("^the user begins to log in as an employee$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.employeeLoginForm).isNull();

                    persona.employeeLoginForm = AuthenticationUtil.getHome().getEmployeeLoginForm();
                });

        And("^the employee sets the username to \"([^\"]*)\"$",
                (String username) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.employeeLoginForm).isNotNull();

                    persona.employeeLoginForm.setUsername(username);
                });

        And("^the employee sets the password to \"([^\"]*)\"$",
                (String password) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.employeeLoginForm).isNotNull();

                    persona.employeeLoginForm.setPassword(password);
                });

        And("^the employee sets the company to \"([^\"]*)\"$",
                (String company) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.employeeLoginForm).isNotNull();

                    persona.employeeLoginForm.setCompanyName(company);
                });

        And("^the employee sets the remote IPv4 address to \"([^\"]*)\"$",
                (String remoteInet4Address) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.employeeLoginForm).isNotNull();

                    persona.employeeLoginForm.setRemoteInet4Address(remoteInet4Address);
                });

        And("^the employee logs in$",
                () -> {
                    var persona = CurrentPersona.persona;
                    var employeeLoginForm = persona.employeeLoginForm;

                    assertThat(persona.employeeLoginForm).isNotNull();

                    var authenticationService = AuthenticationUtil.getHome();

                    LastCommandResult.commandResult = authenticationService.employeeLogin(persona.userVisitPK, employeeLoginForm);

                    persona.employeeLoginForm = null;
                });
    }

}
