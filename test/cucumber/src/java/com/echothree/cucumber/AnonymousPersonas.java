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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.naming.NamingException;

public class AnonymousPersonas {

    private AnonymousPersonas() {
    }

    private static Map<String, AnonymousPersona> anonymousPersonas = new HashMap<>();

    public static Set<Map.Entry<String, AnonymousPersona>> getAnonymousPersonas() {
        return anonymousPersonas.entrySet();
    }

    public static AnonymousPersona getAnonymousPersona(String persona)
            throws NamingException {
        var anonymousPersona = anonymousPersonas.get(persona);

        if(anonymousPersona == null) {
            anonymousPersona = new AnonymousPersona(persona);

            anonymousPersonas.put(persona, anonymousPersona);
        }

        return anonymousPersona;
    }
}
