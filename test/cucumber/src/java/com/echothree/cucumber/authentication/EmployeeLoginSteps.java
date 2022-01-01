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

import com.echothree.control.user.authentication.common.AuthenticationService;
import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.cucumber.util.persona.EmployeePersona;
import com.echothree.cucumber.util.persona.EmployeePersonas;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import com.echothree.util.common.command.CommandResult;
import io.cucumber.java8.En;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeLoginSteps implements En {

    public EmployeeLoginSteps() {
        After(() -> {
                    for(Map.Entry<String, EmployeePersona> employeePersona : EmployeePersonas.getPersonaEntries()) {
                        AuthenticationService authenticationService = AuthenticationUtil.getHome();
                        CommandResult commandResult = authenticationService.logout(employeePersona.getValue().userVisitPK);

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
    }

}
