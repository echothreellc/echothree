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

package com.echothree.model.control.contactlist.common.transfer;

import com.echothree.model.control.party.common.transfer.PartyTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class PartyTypeContactListGroupTransfer
        extends BaseTransfer {
    
    private PartyTypeTransfer partyType;
    private ContactListGroupTransfer contactListGroup;
    private Boolean addWhenCreated;
    
    /** Creates a new instance of PartyTypeContactListGroupTransfer */
    public PartyTypeContactListGroupTransfer(PartyTypeTransfer partyType, ContactListGroupTransfer contactListGroup, Boolean addWhenCreated) {
        this.partyType = partyType;
        this.contactListGroup = contactListGroup;
        this.addWhenCreated = addWhenCreated;
    }

    /**
     * Returns the partyType.
     * @return the partyType
     */
    public PartyTypeTransfer getPartyType() {
        return partyType;
    }

    /**
     * Sets the partyType.
     * @param partyType the partyType to set
     */
    public void setPartyType(PartyTypeTransfer partyType) {
        this.partyType = partyType;
    }

    /**
     * Returns the contactListGroup.
     * @return the contactListGroup
     */
    public ContactListGroupTransfer getContactListGroup() {
        return contactListGroup;
    }

    /**
     * Sets the contactListGroup.
     * @param contactListGroup the contactListGroup to set
     */
    public void setContactListGroup(ContactListGroupTransfer contactListGroup) {
        this.contactListGroup = contactListGroup;
    }

    /**
     * Returns the addWhenCreated.
     * @return the addWhenCreated
     */
    public Boolean getAddWhenCreated() {
        return addWhenCreated;
    }

    /**
     * Sets the addWhenCreated.
     * @param addWhenCreated the addWhenCreated to set
     */
    public void setAddWhenCreated(Boolean addWhenCreated) {
        this.addWhenCreated = addWhenCreated;
    }
    
}
