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

package com.echothree.cucumber.user;

import com.echothree.control.user.user.common.UserUtil;
import com.echothree.cucumber.AnonymousPersonas;
import com.echothree.cucumber.BasePersona;
import com.echothree.cucumber.EmployeePersonas;
import com.echothree.cucumber.LastCommandResult;
import cucumber.api.java.en.When;
import javax.naming.NamingException;

public class UserLoginSteps {

    private void deleteUserLogin(BasePersona persona, String partyNsame)
            throws NamingException {
        var userService = UserUtil.getHome();
        var deleteUserLoginForm = userService.getDeleteUserLoginForm();

        deleteUserLoginForm.setPartyName(partyNsame);

        var commandResult = userService.deleteUserLogin(persona.userVisitPK, deleteUserLoginForm);

        LastCommandResult.commandResult = commandResult;
    }
    
    @When("^the employee ([^\"]*) deletes the user login added by the anonymous user ([^\"]*)$")
    public void theEmployeeDeletesTheUserLogin(String persona, String anonymous)
            throws NamingException {
        var employeePersona = EmployeePersonas.getEmployeePersona(persona);
        var anonymousPersona = AnonymousPersonas.getAnonymousPersona(anonymous);

        deleteUserLogin(employeePersona, anonymousPersona.lastPartyName);
    }
}
