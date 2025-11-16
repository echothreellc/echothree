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

import com.echothree.model.control.communication.common.transfer.CommunicationEventTypeTransfer;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.data.communication.server.entity.CommunicationEventType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CommunicationEventTypeTransferCache
        extends BaseCommunicationTransferCache<CommunicationEventType, CommunicationEventTypeTransfer> {

    CommunicationControl communicationControl = Session.getModelController(CommunicationControl.class);

    /** Creates a new instance of CommunicationEventTypeTransferCache */
    protected CommunicationEventTypeTransferCache() {
        super();
    }
    
    public CommunicationEventTypeTransfer getCommunicationEventTypeTransfer(UserVisit userVisit, CommunicationEventType communicationEventType) {
        var communicationEventTypeTransfer = get(communicationEventType);
        
        if(communicationEventTypeTransfer == null) {
            var communicationEventTypeName = communicationEventType.getCommunicationEventTypeName();
            var isDefault = communicationEventType.getIsDefault();
            var sortOrder = communicationEventType.getSortOrder();
            var description = communicationControl.getBestCommunicationEventTypeDescription(communicationEventType, getLanguage(userVisit));
            
            communicationEventTypeTransfer = new CommunicationEventTypeTransfer(communicationEventTypeName, isDefault, sortOrder, description);
            put(userVisit, communicationEventType, communicationEventTypeTransfer);
        }
        
        return communicationEventTypeTransfer;
    }
    
}
