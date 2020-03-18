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

package com.echothree.cucumber.party;

import com.echothree.control.user.customer.common.CustomerUtil;
import com.echothree.control.user.party.common.PartyUtil;
import com.echothree.control.user.party.common.result.CreateCustomerWithLoginResult;
import com.echothree.cucumber.AnonymousPersonas;
import com.echothree.cucumber.BasePersona;
import com.echothree.cucumber.EmployeePersonas;
import com.echothree.cucumber.LastCommandResult;
import io.cucumber.java8.En;
import javax.naming.NamingException;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerSteps implements En {

    public CustomerSteps() {
        When("^the anonymous user ([^\"]*) begins adding a new customer$",
                (String persona) -> {
                    var anonymousPersona = AnonymousPersonas.getAnonymousPersona(persona);

                    assertThat(anonymousPersona.createCustomerWithLoginForm).isNull();

                    anonymousPersona.createCustomerWithLoginForm = PartyUtil.getHome().getCreateCustomerWithLoginForm();
                });


        When("^the anonymous user ([^\"]*) sets the new customer's first name to \"([^\"]*)\"$",
                (String persona, String firstName) -> {
                    var anonymousPersona = AnonymousPersonas.getAnonymousPersona(persona);

                    assertThat(anonymousPersona.createCustomerWithLoginForm).isNotNull();

                    anonymousPersona.createCustomerWithLoginForm.setFirstName(firstName);
                });

        When("^the anonymous user ([^\"]*) sets the new customer's last name to \"([^\"]*)\"$",
                (String persona, String lastName) -> {
                    var anonymousPersona = AnonymousPersonas.getAnonymousPersona(persona);

                    assertThat(anonymousPersona.createCustomerWithLoginForm).isNotNull();

                    anonymousPersona.createCustomerWithLoginForm.setLastName(lastName);
                });

        When("^the anonymous user ([^\"]*) sets the new customer's email address to \"([^\"]*)\"$",
                (String persona, String emailAddress) -> {
                    var anonymousPersona = AnonymousPersonas.getAnonymousPersona(persona);

                    assertThat(anonymousPersona.createCustomerWithLoginForm).isNotNull();

                    anonymousPersona.createCustomerWithLoginForm.setEmailAddress(emailAddress);
                });

        When("^the anonymous user ([^\"]*) (does|does not) allow solicitations to the new customer$",
                (String persona, String allowSolicitation) -> {
                    var anonymousPersona = AnonymousPersonas.getAnonymousPersona(persona);

                    assertThat(anonymousPersona.createCustomerWithLoginForm).isNotNull();

                    anonymousPersona.createCustomerWithLoginForm.setAllowSolicitation(Boolean.valueOf(allowSolicitation.equals("does")).toString());
                });

        When("^the anonymous user ([^\"]*) sets the new customer's username to \"([^\"]*)\"$",
                (String persona, String username) -> {
                    var anonymousPersona = AnonymousPersonas.getAnonymousPersona(persona);

                    assertThat(anonymousPersona.createCustomerWithLoginForm).isNotNull();

                    anonymousPersona.createCustomerWithLoginForm.setUsername(username);
                });

        When("^the anonymous user ([^\"]*) sets the new customer's first password to \"([^\"]*)\"$",
                (String persona, String password1) -> {
                    var anonymousPersona = AnonymousPersonas.getAnonymousPersona(persona);

                    assertThat(anonymousPersona.createCustomerWithLoginForm).isNotNull();

                    anonymousPersona.createCustomerWithLoginForm.setPassword1(password1);
                });

        When("^the anonymous user ([^\"]*) sets the new customer's second password to \"([^\"]*)\"$",
                (String persona, String password2) -> {
                    var anonymousPersona = AnonymousPersonas.getAnonymousPersona(persona);

                    assertThat(anonymousPersona.createCustomerWithLoginForm).isNotNull();

                    anonymousPersona.createCustomerWithLoginForm.setPassword2(password2);
                });

        When("^the anonymous user ([^\"]*) sets the new customer's recovery question to ([^\"]*)$",
                (String persona, String recoveryQuestionName) -> {
                    var anonymousPersona = AnonymousPersonas.getAnonymousPersona(persona);

                    assertThat(anonymousPersona.createCustomerWithLoginForm).isNotNull();

                    anonymousPersona.createCustomerWithLoginForm.setRecoveryQuestionName(recoveryQuestionName);
                });

        When("^the anonymous user ([^\"]*) sets the new customer's answer to \"([^\"]*)\"$",
                (String persona, String answer) -> {
                    var anonymousPersona = AnonymousPersonas.getAnonymousPersona(persona);

                    assertThat(anonymousPersona.createCustomerWithLoginForm).isNotNull();

                    anonymousPersona.createCustomerWithLoginForm.setAnswer(answer);
                });

        When("^the anonymous user ([^\"]*) adds the new customer$",
                (String persona) -> {
                    var anonymousPersona = AnonymousPersonas.getAnonymousPersona(persona);

                    assertThat(anonymousPersona.createCustomerWithLoginForm).isNotNull();

                    var partyService = PartyUtil.getHome();
                    var commandResult = partyService.createCustomerWithLogin(anonymousPersona.userVisitPK, anonymousPersona.createCustomerWithLoginForm);

                    LastCommandResult.commandResult = commandResult;
                    var result = (CreateCustomerWithLoginResult)commandResult.getExecutionResult().getResult();


                    if(persona != null) {
                        anonymousPersona.lastCustomerName = commandResult.getHasErrors() ? null : result.getCustomerName();
                        anonymousPersona.lastPartyName = commandResult.getHasErrors() ? null : result.getPartyName();
                        anonymousPersona.lastEntityRef = commandResult.getHasErrors() ? null : result.getEntityRef();
                    }
                });

        When("^the employee ([^\"]*) sets the status of the last customer added to ([^\"]*)$",
                (String persona, String customerStatusChoice) -> {
                    var employeePersona = EmployeePersonas.getEmployeePersona(persona);

                    setCustomerStatus(employeePersona, employeePersona.lastCustomerName, customerStatusChoice);
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
