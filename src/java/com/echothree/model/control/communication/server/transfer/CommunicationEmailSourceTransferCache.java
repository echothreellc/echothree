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

import com.echothree.model.control.communication.common.transfer.CommunicationEmailSourceTransfer;
import com.echothree.model.control.communication.server.control.CommunicationControl;
import com.echothree.model.control.core.server.control.ServerControl;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.communication.server.entity.CommunicationEmailSource;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CommunicationEmailSourceTransferCache
        extends BaseCommunicationTransferCache<CommunicationEmailSource, CommunicationEmailSourceTransfer> {

    ServerControl serverControl = Session.getModelController(ServerControl.class);
    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);
    WorkEffortControl workEffortControl = Session.getModelController(WorkEffortControl.class);
    
    /** Creates a new instance of CommunicationEmailSourceTransferCache */
    public CommunicationEmailSourceTransferCache(CommunicationControl communicationControl) {
        super(communicationControl);
    }
    
    public CommunicationEmailSourceTransfer getCommunicationEmailSourceTransfer(CommunicationEmailSource communicationEmailSource) {
        var communicationEmailSourceTransfer = get(communicationEmailSource);
        
        if(communicationEmailSourceTransfer == null) {
            var serverTransfer = serverControl.getServerTransfer(userVisit, communicationEmailSource.getServer());
            var username = communicationEmailSource.getUsername();
            var password = communicationControl.decodeCommunicationEmailSourcePassword(communicationEmailSource);
            var receiveWorkEffortScopeTransfer = workEffortControl.getWorkEffortScopeTransfer(userVisit, communicationEmailSource.getReceiveWorkEffortScope());
            var sendWorkEffortScopeTransfer = workEffortControl.getWorkEffortScopeTransfer(userVisit, communicationEmailSource.getSendWorkEffortScope());
            var reviewEmployeeSelector = communicationEmailSource.getReviewEmployeeSelector();
            var reviewEmployeeSelectorTransfer = reviewEmployeeSelector == null? null: selectorControl.getSelectorTransfer(userVisit, reviewEmployeeSelector);
            
            communicationEmailSourceTransfer = new CommunicationEmailSourceTransfer(serverTransfer, username, password,
                    receiveWorkEffortScopeTransfer, sendWorkEffortScopeTransfer, reviewEmployeeSelectorTransfer);
            put(userVisit, communicationEmailSource, communicationEmailSourceTransfer);
        }
        
        return communicationEmailSourceTransfer;
    }
    
}
