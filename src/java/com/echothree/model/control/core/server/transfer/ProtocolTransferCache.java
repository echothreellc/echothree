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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.ProtocolTransfer;
import com.echothree.model.control.core.server.control.ServerControl;
import com.echothree.model.data.core.server.entity.Protocol;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ProtocolTransferCache
        extends BaseCoreTransferCache<Protocol, ProtocolTransfer> {

    ServerControl serverControl = Session.getModelController(ServerControl.class);

    /** Creates a new instance of ProtocolTransferCache */
    public ProtocolTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public ProtocolTransfer getProtocolTransfer(UserVisit userVisit, Protocol protocol) {
        var protocolTransfer = get(protocol);
        
        if(protocolTransfer == null) {
            var protocolDetail = protocol.getLastDetail();
            var protocolName = protocolDetail.getProtocolName();
            var isDefault = protocolDetail.getIsDefault();
            var sortOrder = protocolDetail.getSortOrder();
            var description = serverControl.getBestProtocolDescription(protocol, getLanguage(userVisit));
    
            protocolTransfer = new ProtocolTransfer(protocolName, isDefault, sortOrder, description);
            put(userVisit, protocol, protocolTransfer);
        }
        
        return protocolTransfer;
    }
    
}
