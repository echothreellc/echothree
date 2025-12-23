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

package com.echothree.model.control.communication.common.transfer;

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CommunicationEventRoleTransfer
        extends BaseTransfer {
    
    private CommunicationEventTransfer communicationEvent;
    private PartyTransfer party;
    private CommunicationEventRoleTypeTransfer communicationEventRoleType;
    
    /** Creates a new instance of CommunicationEventRoleTransfer */
    public CommunicationEventRoleTransfer(CommunicationEventTransfer communicationEvent, PartyTransfer party,
            CommunicationEventRoleTypeTransfer communicationEventRoleType) {
        this.communicationEvent = communicationEvent;
        this.party = party;
        this.communicationEventRoleType = communicationEventRoleType;
    }

    public CommunicationEventTransfer getCommunicationEvent() {
        return communicationEvent;
    }

    public void setCommunicationEvent(CommunicationEventTransfer communicationEvent) {
        this.communicationEvent = communicationEvent;
    }

    public PartyTransfer getParty() {
        return party;
    }

    public void setParty(PartyTransfer party) {
        this.party = party;
    }

    public CommunicationEventRoleTypeTransfer getCommunicationEventRoleType() {
        return communicationEventRoleType;
    }

    public void setCommunicationEventRoleType(CommunicationEventRoleTypeTransfer communicationEventRoleType) {
        this.communicationEventRoleType = communicationEventRoleType;
    }
    
}
