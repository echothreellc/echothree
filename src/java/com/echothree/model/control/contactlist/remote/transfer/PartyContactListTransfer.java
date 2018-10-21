// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.contactlist.remote.transfer;

import com.echothree.model.control.party.remote.transfer.PartyTransfer;
import com.echothree.model.control.workflow.remote.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.remote.transfer.BaseTransfer;

public class PartyContactListTransfer
        extends BaseTransfer {
    
    private PartyTransfer party;
    private ContactListTransfer contactList;
    private ContactListContactMechanismPurposeTransfer preferredContactListContactMechanismPurpose;
    
    private WorkflowEntityStatusTransfer partyContactListStatus;
    
    /** Creates a new instance of PartyContactListTransfer */
    public PartyContactListTransfer(PartyTransfer party, ContactListTransfer contactList, ContactListContactMechanismPurposeTransfer preferredContactListContactMechanismPurpose) {
        this.party = party;
        this.contactList = contactList;
        this.preferredContactListContactMechanismPurpose = preferredContactListContactMechanismPurpose;
    }

    /**
     * @return the party
     */
    public PartyTransfer getParty() {
        return party;
    }

    /**
     * @param party the party to set
     */
    public void setParty(PartyTransfer party) {
        this.party = party;
    }

    /**
     * @return the contactList
     */
    public ContactListTransfer getContactList() {
        return contactList;
    }

    /**
     * @param contactList the contactList to set
     */
    public void setContactList(ContactListTransfer contactList) {
        this.contactList = contactList;
    }

    /**
     * @return the preferredContactListContactMechanismPurpose
     */
    public ContactListContactMechanismPurposeTransfer getPreferredContactListContactMechanismPurpose() {
        return preferredContactListContactMechanismPurpose;
    }

    /**
     * @param preferredContactListContactMechanismPurpose the preferredContactListContactMechanismPurpose to set
     */
    public void setPreferredContactListContactMechanismPurpose(ContactListContactMechanismPurposeTransfer preferredContactListContactMechanismPurpose) {
        this.preferredContactListContactMechanismPurpose = preferredContactListContactMechanismPurpose;
    }

    /**
     * @return the partyContactListStatus
     */
    public WorkflowEntityStatusTransfer getPartyContactListStatus() {
        return partyContactListStatus;
    }

    /**
     * @param partyContactListStatus the partyContactListStatus to set
     */
    public void setPartyContactListStatus(WorkflowEntityStatusTransfer partyContactListStatus) {
        this.partyContactListStatus = partyContactListStatus;
    }
    
}
