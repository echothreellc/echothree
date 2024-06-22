// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

package com.echothree.model.control.communication.server.transfer;

import com.echothree.model.control.communication.common.transfer.CommunicationEventPurposeTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationEventTypeTransfer;
import com.echothree.model.control.communication.common.transfer.CommunicationSourceTransfer;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.contact.common.transfer.PartyContactMechanismTransfer;
import com.echothree.model.control.contact.server.control.ContactControl;
import com.echothree.model.control.document.common.transfer.DocumentTransfer;
import com.echothree.model.control.document.server.control.DocumentControl;
import com.echothree.model.data.communication.server.entity.CommunicationEvent;
import com.echothree.model.data.communication.server.entity.CommunicationEventDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CommunicationEventTransferCache
        extends BaseCommunicationTransferCache<CommunicationEvent, CommunicationEventTransfer> {
    
    ContactControl contactControl = Session.getModelController(ContactControl.class);
    DocumentControl documentControl = Session.getModelController(DocumentControl.class);
    
    /** Creates a new instance of CommunicationEventTransferCache */
    public CommunicationEventTransferCache(UserVisit userVisit, CommunicationControl communicationControl) {
        super(userVisit, communicationControl);
        
        setIncludeEntityInstance(true);
    }
    
    public CommunicationEventTransfer getCommunicationEventTransfer(CommunicationEvent communicationEvent) {
        CommunicationEventTransfer communicationEventTransfer = get(communicationEvent);
        
        if(communicationEventTransfer == null) {
            CommunicationEventDetail communicationEventDetail = communicationEvent.getLastDetail();
            String communicationEventName = communicationEventDetail.getCommunicationEventName();
            CommunicationEventTypeTransfer communicationEventTypeTransfer = communicationControl.getCommunicationEventTypeTransfer(userVisit, 
                    communicationEventDetail.getCommunicationEventType());
            CommunicationSourceTransfer communicationSourceTransfer = communicationControl.getCommunicationSourceTransfer(userVisit, 
                    communicationEventDetail.getCommunicationSource());
            CommunicationEventPurposeTransfer communicationEventPurposeTransfer = communicationControl.getCommunicationEventPurposeTransfer(userVisit, 
                    communicationEventDetail.getCommunicationEventPurpose());
            CommunicationEvent originalCommunicationEvent = communicationEventDetail.getOriginalCommunicationEvent();
            CommunicationEventTransfer originalCommunicationEventTransfer = originalCommunicationEvent == null? null: communicationControl.getCommunicationEventTransfer(userVisit, originalCommunicationEvent);
            CommunicationEvent parentCommunicationEvent = communicationEventDetail.getParentCommunicationEvent();
            CommunicationEventTransfer parentCommunicationEventTransfer = parentCommunicationEvent == null? null: communicationControl.getCommunicationEventTransfer(userVisit, parentCommunicationEvent);
            PartyContactMechanismTransfer partyContactMechanismTransfer = contactControl.getPartyContactMechanismTransfer(userVisit, communicationEventDetail.getPartyContactMechanism());
            DocumentTransfer documentTransfer = documentControl.getDocumentTransfer(userVisit, communicationEventDetail.getDocument());
            
            communicationEventTransfer = new CommunicationEventTransfer(communicationEventName, communicationEventTypeTransfer,
                    communicationSourceTransfer, communicationEventPurposeTransfer, originalCommunicationEventTransfer,
                    parentCommunicationEventTransfer, partyContactMechanismTransfer, documentTransfer);
            put(communicationEvent, communicationEventTransfer);
            setupOwnedWorkEfforts(communicationEvent, null, communicationEventTransfer);
        }
        
        return communicationEventTransfer;
    }
    
}
