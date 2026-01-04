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

package com.echothree.ui.web.main.action.humanresources.employeealias;

import com.echothree.view.client.web.struts.BaseActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="EmployeeAliasEdit")
public class EditActionForm
        extends BaseActionForm {
    
    private String partyName;
    private String partyAliasTypeName;
    private String alias;

    /**
     * Returns the partyName.
     * @return the partyName
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * Sets the partyName.
     * @param partyName the partyName to set
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    /**
     * Returns the partyAliasTypeName.
     * @return the partyAliasTypeName
     */
    public String getPartyAliasTypeName() {
        return partyAliasTypeName;
    }

    /**
     * Sets the partyAliasTypeName.
     * @param partyAliasTypeName the partyAliasTypeName to set
     */
    public void setPartyAliasTypeName(String partyAliasTypeName) {
        this.partyAliasTypeName = partyAliasTypeName;
    }

    /**
     * Returns the alias.
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the alias.
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

}
