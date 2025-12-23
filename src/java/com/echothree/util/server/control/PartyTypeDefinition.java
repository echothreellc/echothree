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

package com.echothree.util.server.control;

import java.util.List;

public class PartyTypeDefinition {
    
    private String partyTypeName;
    private List<SecurityRoleDefinition> securityRoleDefinitions;

    /** Creates a new instance of PartyTypeDefinition */
    public PartyTypeDefinition(String partyTypeName, List<SecurityRoleDefinition> securityRoleDefinitions) {
        this.partyTypeName = partyTypeName;
        this.securityRoleDefinitions = securityRoleDefinitions;
    }
    
    public String getPartyTypeName() {
        return partyTypeName;
    }

    public void setPartyTypeName(String partyTypeName) {
        this.partyTypeName = partyTypeName;
    }

    public List<SecurityRoleDefinition> getSecurityRoleDefinitions() {
        return securityRoleDefinitions;
    }

    public void setSecurityRoleDefinitions(List<SecurityRoleDefinition> securityRoleDefinitions) {
        this.securityRoleDefinitions = securityRoleDefinitions;
    }

}
