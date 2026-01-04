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

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class PartyContactMechanismTransfer
        extends BaseTransfer {
    
    private PartyTransfer party;
    private ContactMechanismTransfer contactMechanism;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    private ListWrapper<PartyContactMechanismPurposeTransfer> partyContactMechanismPurposes;
    private ListWrapper<PartyContactMechanismRelationshipTransfer> partyContactMechanismRelationships;
    
    /** Creates a new instance of PartyContactMechanismTransfer */
    public PartyContactMechanismTransfer(PartyTransfer party, ContactMechanismTransfer contactMechanism, Boolean isDefault, Integer sortOrder,
            String description) {
        this.party = party;
        this.contactMechanism = contactMechanism;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }

    /**
     * Returns the party.
     * @return the party
     */
    public PartyTransfer getParty() {
        return party;
    }

    /**
     * Sets the party.
     * @param party the party to set
     */
    public void setParty(PartyTransfer party) {
        this.party = party;
    }

    /**
     * Returns the contactMechanism.
     * @return the contactMechanism
     */
    public ContactMechanismTransfer getContactMechanism() {
        return contactMechanism;
    }

    /**
     * Sets the contactMechanism.
     * @param contactMechanism the contactMechanism to set
     */
    public void setContactMechanism(ContactMechanismTransfer contactMechanism) {
        this.contactMechanism = contactMechanism;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Returns the sortOrder.
     * @return the sortOrder
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the sortOrder.
     * @param sortOrder the sortOrder to set
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the partyContactMechanismPurposes.
     * @return the partyContactMechanismPurposes
     */
    public ListWrapper<PartyContactMechanismPurposeTransfer> getPartyContactMechanismPurposes() {
        return partyContactMechanismPurposes;
    }

    /**
     * Sets the partyContactMechanismPurposes.
     * @param partyContactMechanismPurposes the partyContactMechanismPurposes to set
     */
    public void setPartyContactMechanismPurposes(ListWrapper<PartyContactMechanismPurposeTransfer> partyContactMechanismPurposes) {
        this.partyContactMechanismPurposes = partyContactMechanismPurposes;
    }

    /**
     * Returns the partyContactMechanismRelationships.
     * @return the partyContactMechanismRelationships
     */
    public ListWrapper<PartyContactMechanismRelationshipTransfer> getPartyContactMechanismRelationships() {
        return partyContactMechanismRelationships;
    }

    /**
     * Sets the partyContactMechanismRelationships.
     * @param partyContactMechanismRelationships the partyContactMechanismRelationships to set
     */
    public void setPartyContactMechanismRelationships(ListWrapper<PartyContactMechanismRelationshipTransfer> partyContactMechanismRelationships) {
        this.partyContactMechanismRelationships = partyContactMechanismRelationships;
    }
    
}
