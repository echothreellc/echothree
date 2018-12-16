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

import com.echothree.control.user.authentication.common.AuthenticationService;
import com.echothree.control.user.authentication.common.AuthenticationUtil;
import com.echothree.control.user.authentication.common.form.GetUserVisitForm;
import com.echothree.control.user.authentication.common.result.GetUserVisitResult;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.CommandResult;

import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomerPersonas {

    private CustomerPersonas() {
    }

    private static Map<String, CustomerPersona> customerPersonas = new HashMap<>();
    public static CustomerPersona lastCustomerPersona;
    
    private static UserVisitPK getUserVisitPK()
            throws NamingException {
        AuthenticationService authenticationService = AuthenticationUtil.getHome();
        GetUserVisitForm getUserVisitForm = authenticationService.getGetUserVisitForm();
        CommandResult commandResult = authenticationService.getUserVisit(getUserVisitForm);
        GetUserVisitResult getUserVisitResult = (GetUserVisitResult)commandResult.getExecutionResult().getResult();
        
        return getUserVisitResult.getUserVisitPK();
    }

    public static Set<Map.Entry<String, CustomerPersona>> getCustomerPersonas() {
        return customerPersonas.entrySet();
    }

    public static CustomerPersona getCustomerPersona(String persona)
            throws NamingException {
        CustomerPersona customerPersona = customerPersonas.get(persona);

        if(customerPersona == null) {
            customerPersona = new CustomerPersona();
            customerPersona.persona = persona;
            customerPersona.userVisitPK = getUserVisitPK();

            customerPersonas.put(persona, customerPersona);
        }

        lastCustomerPersona = customerPersona;
        return customerPersona;
    }
}
