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

package com.echothree.cucumber;

import com.echothree.cucumber.authentication.UserVisits;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;

public class EmployeePersonas {

    private EmployeePersonas() {
    }

    private static Map<String, EmployeePersona> employeePersonas = new HashMap<>();
    public static EmployeePersona lastEmployeePersona;
    
    public static Set<Map.Entry<String, EmployeePersona>> getEmployeePersonas() {
        return employeePersonas.entrySet();
    }

    public static EmployeePersona getEmployeePersona(String persona)
            throws NamingException {
        var employeePersona = employeePersonas.get(persona);

        if(employeePersona == null) {
            employeePersona = new EmployeePersona();
            employeePersona.persona = persona;
            employeePersona.userVisitPK = UserVisits.getUserVisitPK();

            employeePersonas.put(persona, employeePersona);
        }

        lastEmployeePersona = employeePersona;

        return employeePersona;
    }
}
