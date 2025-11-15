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

import com.echothree.model.control.communication.common.transfer.CommunicationEventPurposeTransfer;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.data.communication.server.entity.CommunicationEventPurpose;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CommunicationEventPurposeTransferCache
        extends BaseCommunicationTransferCache<CommunicationEventPurpose, CommunicationEventPurposeTransfer> {

    CommunicationControl communicationControl = Session.getModelController(CommunicationControl.class);

    /** Creates a new instance of CommunicationEventPurposeTransferCache */
    public CommunicationEventPurposeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public CommunicationEventPurposeTransfer getCommunicationEventPurposeTransfer(UserVisit userVisit, CommunicationEventPurpose communicationEventPurpose) {
        var communicationEventPurposeTransfer = get(communicationEventPurpose);
        
        if(communicationEventPurposeTransfer == null) {
            var communicationEventPurposeDetail = communicationEventPurpose.getLastDetail();
            var communicationEventPurposeName = communicationEventPurposeDetail.getCommunicationEventPurposeName();
            var isDefault = communicationEventPurposeDetail.getIsDefault();
            var sortOrder = communicationEventPurposeDetail.getSortOrder();
            var description = communicationControl.getBestCommunicationEventPurposeDescription(communicationEventPurpose, getLanguage(userVisit));
            
            communicationEventPurposeTransfer = new CommunicationEventPurposeTransfer(communicationEventPurposeName, isDefault, sortOrder, description);
            put(userVisit, communicationEventPurpose, communicationEventPurposeTransfer);
        }
        
        return communicationEventPurposeTransfer;
    }
    
}
