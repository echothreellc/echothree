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

import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomerPersonas
        implements BasePersonas<CustomerPersona> {

    private CustomerPersonas() {
    }

    private static Map<String, CustomerPersona> personas = new HashMap<>();

    public static Set<Map.Entry<String, CustomerPersona>> getPersonaEntries() {
        return personas.entrySet();
    }

    public static CustomerPersona getPersona(String persona)
            throws NamingException {
        var customerPersona = personas.get(persona);

        if(customerPersona == null) {
            customerPersona = new CustomerPersona(persona);

            personas.put(persona, customerPersona);
        }

        return customerPersona;
    }
}
