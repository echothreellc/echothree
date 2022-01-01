// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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
import com.echothree.model.control.core.common.transfer.ServerTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeTransfer;
import com.echothree.model.control.workeffort.server.control.WorkEffortControl;
import com.echothree.model.data.communication.server.entity.CommunicationEmailSource;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CommunicationEmailSourceTransferCache
        extends BaseCommunicationTransferCache<CommunicationEmailSource, CommunicationEmailSourceTransfer> {
    
    CoreControl coreControl;
    SelectorControl selectorControl;
    WorkEffortControl workEffortControl;
    
    /** Creates a new instance of CommunicationEmailSourceTransferCache */
    public CommunicationEmailSourceTransferCache(UserVisit userVisit, CommunicationControl communicationControl) {
        super(userVisit, communicationControl);
        
        coreControl = Session.getModelController(CoreControl.class);
        selectorControl = Session.getModelController(SelectorControl.class);
        workEffortControl = Session.getModelController(WorkEffortControl.class);
    }
    
    public CommunicationEmailSourceTransfer getCommunicationEmailSourceTransfer(CommunicationEmailSource communicationEmailSource) {
        CommunicationEmailSourceTransfer communicationEmailSourceTransfer = get(communicationEmailSource);
        
        if(communicationEmailSourceTransfer == null) {
            ServerTransfer servertTransfer = coreControl.getServerTransfer(userVisit, communicationEmailSource.getServer());
            String username = communicationEmailSource.getUsername();
            String password = communicationControl.decodeCommunicationEmailSourcePassword(communicationEmailSource);
            WorkEffortScopeTransfer receiveWorkEffortScopeTransfer = workEffortControl.getWorkEffortScopeTransfer(userVisit, communicationEmailSource.getReceiveWorkEffortScope());
            WorkEffortScopeTransfer sendWorkEffortScopeTransfer = workEffortControl.getWorkEffortScopeTransfer(userVisit, communicationEmailSource.getSendWorkEffortScope());
            Selector reviewEmployeeSelector = communicationEmailSource.getReviewEmployeeSelector();
            SelectorTransfer reviewEmployeeSelectorTransfer = reviewEmployeeSelector == null? null: selectorControl.getSelectorTransfer(userVisit, reviewEmployeeSelector);
            
            communicationEmailSourceTransfer = new CommunicationEmailSourceTransfer(servertTransfer, username, password,
                    receiveWorkEffortScopeTransfer, sendWorkEffortScopeTransfer, reviewEmployeeSelectorTransfer);
            put(communicationEmailSource, communicationEmailSourceTransfer);
        }
        
        return communicationEmailSourceTransfer;
    }
    
}
