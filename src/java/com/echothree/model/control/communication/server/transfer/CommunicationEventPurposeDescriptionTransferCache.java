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

package com.echothree.model.control.communication.server.transfer;

import com.echothree.model.control.communication.common.transfer.CommunicationEventPurposeDescriptionTransfer;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurposeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CommunicationEventPurposeDescriptionTransferCache
        extends BaseCommunicationDescriptionTransferCache<CommunicationEventPurposeDescription, CommunicationEventPurposeDescriptionTransfer> {
    
    /** Creates a new instance of CommunicationEventPurposeDescriptionTransferCache */
    public CommunicationEventPurposeDescriptionTransferCache(UserVisit userVisit, CommunicationControl communicationControl) {
        super(userVisit, communicationControl);
    }
    
    public CommunicationEventPurposeDescriptionTransfer getCommunicationEventPurposeDescriptionTransfer(CommunicationEventPurposeDescription communicationEventPurposeDescription) {
        var communicationEventPurposeDescriptionTransfer = get(communicationEventPurposeDescription);
        
        if(communicationEventPurposeDescriptionTransfer == null) {
            var communicationEventPurposeTransfer = communicationControl.getCommunicationEventPurposeTransfer(userVisit,
                    communicationEventPurposeDescription.getCommunicationEventPurpose());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, communicationEventPurposeDescription.getLanguage());
            
            communicationEventPurposeDescriptionTransfer = new CommunicationEventPurposeDescriptionTransfer(languageTransfer, communicationEventPurposeTransfer, communicationEventPurposeDescription.getDescription());
            put(userVisit, communicationEventPurposeDescription, communicationEventPurposeDescriptionTransfer);
        }
        
        return communicationEventPurposeDescriptionTransfer;
    }
    
}
