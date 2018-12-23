// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.Protocol;
import com.echothree.model.data.core.server.entity.ProtocolDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ProtocolTransferCache
        extends BaseCoreTransferCache<Protocol, ProtocolTransfer> {
    
    /** Creates a new instance of ProtocolTransferCache */
    public ProtocolTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ProtocolTransfer getProtocolTransfer(Protocol protocol) {
        ProtocolTransfer protocolTransfer = get(protocol);
        
        if(protocolTransfer == null) {
            ProtocolDetail protocolDetail = protocol.getLastDetail();
            String protocolName = protocolDetail.getProtocolName();
            Boolean isDefault = protocolDetail.getIsDefault();
            Integer sortOrder = protocolDetail.getSortOrder();
            String description = coreControl.getBestProtocolDescription(protocol, getLanguage());
    
            protocolTransfer = new ProtocolTransfer(protocolName, isDefault, sortOrder, description);
            put(protocol, protocolTransfer);
        }
        
        return protocolTransfer;
    }
    
}
