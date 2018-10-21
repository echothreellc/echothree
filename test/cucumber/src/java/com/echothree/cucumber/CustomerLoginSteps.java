// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.cucumber;

import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.authentication.remote.AuthenticationService;
import com.echothree.control.user.authentication.remote.form.CustomerLoginForm;
import com.echothree.control.user.authentication.remote.form.GetUserVisitForm;
import com.echothree.control.user.authentication.remote.result.GetUserVisitResult;
import com.echothree.model.data.user.remote.pk.UserVisitPK;
import com.echothree.util.remote.command.CommandResult;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class CustomerLoginSteps {

    public class CustomerLogin {
        String persona;
        UserVisitPK userVisitPK;
        CommandResult commandResult;
    }
    
    public Map<String, CustomerLogin> customerLogins = new HashMap<>();
    public CustomerLogin lastCustomerLogin;
    
    public UserVisitPK getUserVisitPK()
            throws NamingException {
        AuthenticationService authenticationService = AuthenticationUtil.getHome();
        GetUserVisitForm getUserVisitForm = authenticationService.getGetUserVisitForm();
        CommandResult commandResult = authenticationService.getUserVisit(getUserVisitForm);
        GetUserVisitResult getUserVisitResult = (GetUserVisitResult)commandResult.getExecutionResult().getResult();
        
        return getUserVisitResult.getUserVisitPK();
    }
    
    @After
    public void ensureAllCustomersLoggedOut()
            throws NamingException {
        for(CustomerLogin customerLogin : customerLogins.values()) {
            customerIsNotCurrentlyLoggedIn(customerLogin.persona);
        }
    }
    
    @Given("^([^\"]*) is not currently logged in")
    public void customerIsNotCurrentlyLoggedIn(String persona)
            throws NamingException {
        CustomerLogin customerLogin = customerLogins.get(persona);
        
        if(customerLogin != null) {
            AuthenticationService authenticationService = AuthenticationUtil.getHome();
            CommandResult commandResult = authenticationService.logout(customerLogin.userVisitPK);
            
            if(!commandResult.hasErrors()) {
                customerLogins.remove(persona);
            }
        }
    }

    @When("^the customer ([^\"]*) logs in with the username \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void theCustomerLogsInWithTheUsernameAndPassword(String persona, String username, String password)
            throws NamingException {
        AuthenticationService authenticationService = AuthenticationUtil.getHome();
        CustomerLoginForm customerLoginForm = authenticationService.getCustomerLoginForm();
        UserVisitPK userVisitPK = getUserVisitPK();
        
        customerLoginForm.setUsername(username);
        customerLoginForm.setPassword(password);
        customerLoginForm.setRemoteInet4Address("0.0.0.0");
        
        CommandResult commandResult = authenticationService.customerLogin(userVisitPK, customerLoginForm);
        
        CustomerLogin customerLogin = new CustomerLogin();
        customerLogin.persona = persona;
        customerLogin.userVisitPK = userVisitPK;
        customerLogin.commandResult = commandResult;
        
        customerLogins.put(persona, customerLogin);
        lastCustomerLogin = customerLogin;
    }

    @Then("^no customer login errors should occur$")
    public void noCustomerLoginErrorsShouldOccur() {
        customerLogins.values().forEach((customerLogin) -> {
            assertThat(customerLogin.commandResult.hasErrors()).isFalse();
        });
    }

    @Then("^customer login errors should occur$")
    public void customerLoginErrorsShouldOccur() {
        customerLogins.values().forEach((customerLogin) -> {
            assertThat(customerLogin.commandResult.hasErrors()).isTrue();
        });
    }

    @Then("^no customer login error should occur$")
    public void noCustomerLoginErrorShouldOccur() {
        assertThat(lastCustomerLogin.commandResult.hasErrors()).isFalse();
    }

    @Then("^a customer login error should occur$")
    public void aCustomerLoginErrorShouldOccur() {
        assertThat(lastCustomerLogin.commandResult.hasErrors()).isTrue();
    }
}
