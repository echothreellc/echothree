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

package com.echothree.model.control.communication.server.transfer;

import com.echothree.model.control.communication.common.transfer.CommunicationEventRoleTypeTransfer;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.data.communication.server.entity.CommunicationEventRoleType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CommunicationEventRoleTypeTransferCache
        extends BaseCommunicationTransferCache<CommunicationEventRoleType, CommunicationEventRoleTypeTransfer> {

    CommunicationControl communicationControl = Session.getModelController(CommunicationControl.class);

    /** Creates a new instance of CommunicationEventRoleTypeTransferCache */
    protected CommunicationEventRoleTypeTransferCache() {
        super();
    }
    
    public CommunicationEventRoleTypeTransfer getCommunicationEventRoleTypeTransfer(UserVisit userVisit, CommunicationEventRoleType communicationEventRoleType) {
        var communicationEventRoleTypeTransfer = get(communicationEventRoleType);
        
        if(communicationEventRoleTypeTransfer == null) {
            var communicationEventRoleTypeName = communicationEventRoleType.getCommunicationEventRoleTypeName();
            var sortOrder = communicationEventRoleType.getSortOrder();
            var description = communicationControl.getBestCommunicationEventRoleTypeDescription(communicationEventRoleType, getLanguage(userVisit));
            
            communicationEventRoleTypeTransfer = new CommunicationEventRoleTypeTransfer(communicationEventRoleTypeName, sortOrder,
                    description);
            put(userVisit, communicationEventRoleType, communicationEventRoleTypeTransfer);
        }
        
        return communicationEventRoleTypeTransfer;
    }
    
}
