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

package com.echothree.cucumber.util.persona;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import io.cucumber.java8.En;
import static org.assertj.core.api.Assertions.assertThat;

public class CurrentPersonaSteps implements En {

    public CurrentPersonaSteps() {
        Then("^the customer ([^\"]*) begins using the application$",
                (String persona) -> {
                    CurrentPersona.persona = CustomerPersonas.getPersona(persona);
                });

        Then("^the employee ([^\"]*) begins using the application$",
                (String persona) -> {
                    CurrentPersona.persona = EmployeePersonas.getPersona(persona);
                });

        Then("^the anonymous user ([^\"]*) begins using the application$",
                (String persona) -> {
                    CurrentPersona.persona = AnonymousPersonas.getPersona(persona);
                });

        Given("^the user is not currently logged in$",
                () -> {
                    if(CurrentPersona.persona != null) {
                        var authenticationService = AuthenticationUtil.getHome();
                        var commandResult = authenticationService.logout(CurrentPersona.persona.userVisitPK);

                        assertThat(commandResult.hasErrors()).isFalse();
                    }
                });
    }

}
