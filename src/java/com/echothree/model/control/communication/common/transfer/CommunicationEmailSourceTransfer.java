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

import com.echothree.model.control.core.common.transfer.ServerTransfer;
import com.echothree.model.control.selector.common.transfer.SelectorTransfer;
import com.echothree.model.control.workeffort.common.transfer.WorkEffortScopeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class CommunicationEmailSourceTransfer
        extends BaseTransfer {
    
    private ServerTransfer server;
    private String username;
    private String password;
    private WorkEffortScopeTransfer receiveWorkEffortScope;
    private WorkEffortScopeTransfer sendWorkEffortScope;
    private SelectorTransfer reviewEmployeeSelector;
    
    /** Creates a new instance of CommunicationEmailSourceTransfer */
    public CommunicationEmailSourceTransfer(ServerTransfer server, String username, String password,
            WorkEffortScopeTransfer receiveWorkEffortScope, WorkEffortScopeTransfer sendWorkEffortScope,
            SelectorTransfer reviewEmployeeSelector) {
        this.server = server;
        this.username = username;
        this.password = password;
        this.receiveWorkEffortScope = receiveWorkEffortScope;
        this.sendWorkEffortScope = sendWorkEffortScope;
        this.reviewEmployeeSelector = reviewEmployeeSelector;
    }
    
    public ServerTransfer getServer() {
        return server;
    }
    
    public void setServer(ServerTransfer server) {
        this.server = server;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public WorkEffortScopeTransfer getReceiveWorkEffortScope() {
        return receiveWorkEffortScope;
    }
    
    public void setReceiveWorkEffortScope(WorkEffortScopeTransfer receiveWorkEffortScope) {
        this.receiveWorkEffortScope = receiveWorkEffortScope;
    }
    
    public WorkEffortScopeTransfer getSendWorkEffortScope() {
        return sendWorkEffortScope;
    }
    
    public void setSendWorkEffortScope(WorkEffortScopeTransfer sendWorkEffortScope) {
        this.sendWorkEffortScope = sendWorkEffortScope;
    }
    
    public SelectorTransfer getReviewEmployeeSelector() {
        return reviewEmployeeSelector;
    }
    
    public void setReviewEmployeeSelector(SelectorTransfer reviewEmployeeSelector) {
        this.reviewEmployeeSelector = reviewEmployeeSelector;
    }
    
}
