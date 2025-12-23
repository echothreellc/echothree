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
import com.echothree.control.user.authentication.common.result.GetUserVisitResult;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import javax.naming.NamingException;

public class UserVisits {

    private UserVisits() {
    }

    public static UserVisitPK getUserVisitPK()
            throws NamingException {
        var authenticationService = AuthenticationUtil.getHome();
        var getUserVisitForm = authenticationService.getGetUserVisitForm();
        var commandResult = authenticationService.getUserVisit(getUserVisitForm);
        var getUserVisitResult = (GetUserVisitResult)commandResult.getExecutionResult().getResult();
        
        return getUserVisitResult.getUserVisitPK();
    }

}
