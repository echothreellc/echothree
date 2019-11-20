// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.authentication.common.AuthenticationService;
import com.echothree.control.user.authentication.common.form.CustomerLoginForm;
import com.echothree.cucumber.CustomerPersona;
import com.echothree.cucumber.CustomerPersonas;
import com.echothree.util.common.command.CommandResult;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import javax.naming.NamingException;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class CustomerLoginSteps {

    @After
    public void ensureAllCustomersLoggedOut()
            throws NamingException {
        for(Map.Entry<String, CustomerPersona> customerPersona : CustomerPersonas.getCustomerPersonas()) {
            customerIsNotCurrentlyLoggedIn(customerPersona.getKey());
        }
    }

    @Given("^the customer ([^\"]*) is currently logged in")
    public void customerIsCurrentlyLoggedIn(String persona)
            throws NamingException {
        CustomerPersona customerPersona = CustomerPersonas.getCustomerPersona(persona);

        assertThat(customerPersona).isNotNull();
    }

    @Given("^the customer ([^\"]*) is not currently logged in")
    public void customerIsNotCurrentlyLoggedIn(String persona)
            throws NamingException {
        CustomerPersona customerPersona = CustomerPersonas.getCustomerPersona(persona);

        if(customerPersona != null) {
            AuthenticationService authenticationService = AuthenticationUtil.getHome();
            CommandResult commandResult = authenticationService.logout(customerPersona.userVisitPK);

            assertThat(commandResult.hasErrors()).isFalse();
        }
    }

    @When("^the customer ([^\"]*) logs in with the username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void theCustomerLogsInWithTheUsernameAndPassword(String persona, String username, String password)
            throws NamingException {
        AuthenticationService authenticationService = AuthenticationUtil.getHome();
        CustomerLoginForm customerLoginForm = authenticationService.getCustomerLoginForm();
        CustomerPersona customerPersona = CustomerPersonas.getCustomerPersona(persona);

        customerLoginForm.setUsername(username);
        customerLoginForm.setPassword(password);
        customerLoginForm.setRemoteInet4Address("0.0.0.0");

        customerPersona.commandResult = authenticationService.customerLogin(customerPersona.userVisitPK, customerLoginForm);
    }

    @Then("^no customer error should occur$")
    public void noErrorShouldOccur() {
        assertThat(CustomerPersonas.lastCustomerPersona.commandResult.hasErrors()).isFalse();
    }

    @Then("^a customer error should occur$")
    public void anErrorShouldOccur() {
        assertThat(CustomerPersonas.lastCustomerPersona.commandResult.hasErrors()).isTrue();
    }

}
