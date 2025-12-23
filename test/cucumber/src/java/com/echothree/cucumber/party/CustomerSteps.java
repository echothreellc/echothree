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

package com.echothree.cucumber.party;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.CreateCustomerWithLoginResult;
import com.echothree.cucumber.util.persona.BasePersona;
import com.echothree.cucumber.util.command.LastCommandResult;
import com.echothree.cucumber.util.persona.CurrentPersona;
import io.cucumber.java8.En;
import javax.naming.NamingException;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerSteps implements En {

    public CustomerSteps() {
        When("^the user begins adding a new customer$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createCustomerWithLoginForm).isNull();

                    persona.createCustomerWithLoginForm = PartyUtil.getHome().getCreateCustomerWithLoginForm();
                });


        When("^the user sets the new customer's first name to \"([^\"]*)\"$",
                (String firstName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createCustomerWithLoginForm).isNotNull();

                    persona.createCustomerWithLoginForm.setFirstName(firstName);
                });

        When("^the user sets the new customer's last name to \"([^\"]*)\"$",
                (String lastName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createCustomerWithLoginForm).isNotNull();

                    persona.createCustomerWithLoginForm.setLastName(lastName);
                });

        When("^the user sets the new customer's email address to \"([^\"]*)\"$",
                (String emailAddress) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createCustomerWithLoginForm).isNotNull();

                    persona.createCustomerWithLoginForm.setEmailAddress(emailAddress);
                });

        When("^the user (does|does not) allow solicitations to the new customer$",
                (String allowSolicitation) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createCustomerWithLoginForm).isNotNull();

                    persona.createCustomerWithLoginForm.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());
                });

        When("^the user sets the new customer's username to \"([^\"]*)\"$",
                (String username) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createCustomerWithLoginForm).isNotNull();

                    persona.createCustomerWithLoginForm.setUsername(username);
                });

        When("^the user sets the new customer's first password to \"([^\"]*)\"$",
                (String password1) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createCustomerWithLoginForm).isNotNull();

                    persona.createCustomerWithLoginForm.setPassword1(password1);
                });

        When("^the user sets the new customer's second password to \"([^\"]*)\"$",
                (String password2) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createCustomerWithLoginForm).isNotNull();

                    persona.createCustomerWithLoginForm.setPassword2(password2);
                });

        When("^the user sets the new customer's recovery question to ([^\"]*)$",
                (String recoveryQuestionName) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createCustomerWithLoginForm).isNotNull();

                    persona.createCustomerWithLoginForm.setRecoveryQuestionName(recoveryQuestionName);
                });

        When("^the user sets the new customer's answer to \"([^\"]*)\"$",
                (String answer) -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createCustomerWithLoginForm).isNotNull();

                    persona.createCustomerWithLoginForm.setAnswer(answer);
                });

        When("^the user adds the new customer$",
                () -> {
                    var persona = CurrentPersona.persona;

                    assertThat(persona.createCustomerWithLoginForm).isNotNull();

                    var partyService = PartyUtil.getHome();
                    var commandResult = partyService.createCustomerWithLogin(persona.userVisitPK, persona.createCustomerWithLoginForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateCustomerWithLoginResult)commandResult.getExecutionResult().getResult();

                    if(result != null) {
                        persona.lastCustomerName = commandResult.getHasErrors() ? null : result.getCustomerName();
                        persona.lastPartyName = commandResult.getHasErrors() ? null : result.getPartyName();
                        persona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }

                    persona.createCustomerWithLoginForm = null;
                });

        When("^the user sets the status of the last customer added to ([^\"]*)$",
                (String customerStatusChoice) -> {
                    var persona = CurrentPersona.persona;

                    setCustomerStatus(persona, persona.lastCustomerName, customerStatusChoice);
                });
    }

    private void setCustomerStatus(BasePersona persona, String customerName, String customerStatusChoice)
            throws NamingException {
        var customerService = CustomerUtil.getHome();
        var setCustomerStatusForm = customerService.getSetCustomerStatusForm();

        setCustomerStatusForm.setCustomerName(customerName);
        setCustomerStatusForm.setCustomerStatusChoice(customerStatusChoice);

        var commandResult = customerService.setCustomerStatus(persona.userVisitPK, setCustomerStatusForm);

        LastCommandResult.commandResult = commandResult;
    }

}
