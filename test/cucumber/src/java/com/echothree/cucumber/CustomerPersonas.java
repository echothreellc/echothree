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

package com.echothree.cucumber;

import com.echothree.cucumber.authentication.UserVisits;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomerPersonas {

    private CustomerPersonas() {
    }

    private static Map<String, CustomerPersona> customerPersonas = new HashMap<>();
    public static CustomerPersona lastCustomerPersona;
    
    public static Set<Map.Entry<String, CustomerPersona>> getCustomerPersonas() {
        return customerPersonas.entrySet();
    }

    public static CustomerPersona getCustomerPersona(String persona)
            throws NamingException {
        var customerPersona = customerPersonas.get(persona);

        if(customerPersona == null) {
            customerPersona = new CustomerPersona();
            customerPersona.persona = persona;
            customerPersona.userVisitPK = UserVisits.getUserVisitPK();

            customerPersonas.put(persona, customerPersona);
        }

        lastCustomerPersona = customerPersona;

        return customerPersona;
    }
}
