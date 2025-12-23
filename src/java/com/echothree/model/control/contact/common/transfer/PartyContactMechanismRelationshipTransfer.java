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

package com.echothree.model.control.contact.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class PartyContactMechanismRelationshipTransfer
        extends BaseTransfer {
   
    private PartyContactMechanismTransfer fromPartyContactMechanism;
    private PartyContactMechanismTransfer toPartyContactMechanism;
    
    /** Creates a new instance of PostalAddressLineTransfer */
    public PartyContactMechanismRelationshipTransfer(PartyContactMechanismTransfer fromPartyContactMechanism,
            PartyContactMechanismTransfer toPartyContactMechanism) {
        this.fromPartyContactMechanism = fromPartyContactMechanism;
        this.toPartyContactMechanism = toPartyContactMechanism;
    }

    /**
     * Returns the fromPartyContactMechanism.
     * @return the fromPartyContactMechanism
     */
    public PartyContactMechanismTransfer getFromPartyContactMechanism() {
        return fromPartyContactMechanism;
    }

    /**
     * Sets the fromPartyContactMechanism.
     * @param fromPartyContactMechanism the fromPartyContactMechanism to set
     */
    public void setFromPartyContactMechanism(PartyContactMechanismTransfer fromPartyContactMechanism) {
        this.fromPartyContactMechanism = fromPartyContactMechanism;
    }

    /**
     * Returns the toPartyContactMechanism.
     * @return the toPartyContactMechanism
     */
    public PartyContactMechanismTransfer getToPartyContactMechanism() {
        return toPartyContactMechanism;
    }

    /**
     * Sets the toPartyContactMechanism.
     * @param toPartyContactMechanism the toPartyContactMechanism to set
     */
    public void setToPartyContactMechanism(PartyContactMechanismTransfer toPartyContactMechanism) {
        this.toPartyContactMechanism = toPartyContactMechanism;
    }
    
}
