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

import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.document.common.transfer.DocumentTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CommunicationEventTransfer
        extends BaseTransfer {
    
    private String communicationEventName;
    private CommunicationEventTypeTransfer communicationEventType;
    private CommunicationSourceTransfer communicationSource;
    private CommunicationEventPurposeTransfer communicationEventPurpose;
    private CommunicationEventTransfer originalCommunicationEvent;
    private CommunicationEventTransfer parentCommunicationEvent;
    private PartyContactMechanismTransfer partyContactMechanism;
    private DocumentTransfer document;
    
    /** Creates a new instance of CommunicationEventTransfer */
    public CommunicationEventTransfer(String communicationEventName, CommunicationEventTypeTransfer communicationEventType,
            CommunicationSourceTransfer communicationSource, CommunicationEventPurposeTransfer communicationEventPurpose,
            CommunicationEventTransfer originalCommunicationEvent, CommunicationEventTransfer parentCommunicationEvent,
            PartyContactMechanismTransfer partyContactMechanism, DocumentTransfer document) {
        this.communicationEventName = communicationEventName;
        this.communicationEventType = communicationEventType;
        this.communicationSource = communicationSource;
        this.communicationEventPurpose = communicationEventPurpose;
        this.originalCommunicationEvent = originalCommunicationEvent;
        this.parentCommunicationEvent = parentCommunicationEvent;
        this.partyContactMechanism = partyContactMechanism;
        this.document = document;
    }
    
    public String getCommunicationEventName() {
        return communicationEventName;
    }
    
    public void setCommunicationEventName(String communicationEventName) {
        this.communicationEventName = communicationEventName;
    }
    
    public CommunicationEventTypeTransfer getCommunicationEventType() {
        return communicationEventType;
    }
    
    public void setCommunicationEventType(CommunicationEventTypeTransfer communicationEventType) {
        this.communicationEventType = communicationEventType;
    }
    
    public CommunicationSourceTransfer getCommunicationSource() {
        return communicationSource;
    }
    
    public void setCommunicationSource(CommunicationSourceTransfer communicationSource) {
        this.communicationSource = communicationSource;
    }
    
    public CommunicationEventPurposeTransfer getCommunicationEventPurpose() {
        return communicationEventPurpose;
    }
    
    public void setCommunicationEventPurpose(CommunicationEventPurposeTransfer communicationEventPurpose) {
        this.communicationEventPurpose = communicationEventPurpose;
    }
    
    public CommunicationEventTransfer getOriginalCommunicationEvent() {
        return originalCommunicationEvent;
    }
    
    public void setOriginalCommunicationEvent(CommunicationEventTransfer originalCommunicationEvent) {
        this.originalCommunicationEvent = originalCommunicationEvent;
    }
    
    public CommunicationEventTransfer getParentCommunicationEvent() {
        return parentCommunicationEvent;
    }
    
    public void setParentCommunicationEvent(CommunicationEventTransfer parentCommunicationEvent) {
        this.parentCommunicationEvent = parentCommunicationEvent;
    }
    
    public PartyContactMechanismTransfer getPartyContactMechanism() {
        return partyContactMechanism;
    }
    
    public void setPartyContactMechanism(PartyContactMechanismTransfer partyContactMechanism) {
        this.partyContactMechanism = partyContactMechanism;
    }
    
    public DocumentTransfer getDocument() {
        return document;
    }
    
    public void setDocument(DocumentTransfer document) {
        this.document = document;
    }
    
}
