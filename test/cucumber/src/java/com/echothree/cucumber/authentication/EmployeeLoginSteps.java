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

import com.echothree.control.user.authentication.common.AuthenticationService;
import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.cucumber.EmployeePersona;
import com.echothree.cucumber.EmployeePersonas;
import com.echothree.cucumber.LastCommandResult;
import com.echothree.util.common.command.CommandResult;
import io.cucumber.java8.En;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeLoginSteps implements En {

    public EmployeeLoginSteps() {
        After(() -> {
                    for(Map.Entry<String, EmployeePersona> employeePersona : EmployeePersonas.getEmployeePersonas()) {
                        AuthenticationService authenticationService = AuthenticationUtil.getHome();
                        CommandResult commandResult = authenticationService.logout(employeePersona.getValue().userVisitPK);

                        assertThat(commandResult.hasErrors()).isFalse();
                    }
                });

        Given("^the employee ([^\"]*) is currently logged in",
                (String persona) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    assertThat(employeePersona).isNotNull();
                });

        Given("^the employee ([^\"]*) is not currently logged in",
                (String persona) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    if(employeePersona != null) {
                        AuthenticationService authenticationService = AuthenticationUtil.getHome();
                        CommandResult commandResult = authenticationService.logout(employeePersona.userVisitPK);

                        assertThat(commandResult.hasErrors()).isFalse();
                    }
                });

        When("^the employee ([^\"]*) logs in with the username \"([^\"]*)\" and password \"([^\"]*)\" and company \"([^\"]*)\"$",
                (String persona, String username, String password, String companyName) -> {
                    var authenticationService = AuthenticationUtil.getHome();
                    var employeeLoginForm = authenticationService.getEmployeeLoginForm();
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    employeeLoginForm.setUsername(username);
                    employeeLoginForm.setPassword(password);
                    employeeLoginForm.setCompanyName(companyName);
                    employeeLoginForm.setRemoteInet4Address("0.0.0.0");

                    LastCommandResult.commandResult = authenticationService.employeeLogin(employeePersona.userVisitPK, employeeLoginForm);
                });
    }

}
