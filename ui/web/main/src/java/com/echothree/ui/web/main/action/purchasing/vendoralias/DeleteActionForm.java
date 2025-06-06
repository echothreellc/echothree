// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.ui.web.main.action.purchasing.vendoralias;

import com.echothree.ui.web.main.framework.MainBaseDeleteActionForm;
import com.echothree.view.client.web.struts.sprout.annotation.SproutForm;

@SproutForm(name="VendorAliasDelete")
public class DeleteActionForm
        extends MainBaseDeleteActionForm {
    
    private String partyName;
    private String partyAliasTypeName;
    
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyName() {
        return partyName;
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

}
