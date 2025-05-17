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

import com.echothree.model.control.core.common.transfer.ProtocolDescriptionTransfer;
import com.echothree.model.control.core.server.control.ServerControl;
import com.echothree.model.data.core.server.entity.ProtocolDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ProtocolDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<ProtocolDescription, ProtocolDescriptionTransfer> {

    ServerControl serverControl = Session.getModelController(ServerControl.class);

    /** Creates a new instance of ProtocolDescriptionTransferCache */
    public ProtocolDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public ProtocolDescriptionTransfer getProtocolDescriptionTransfer(ProtocolDescription protocolDescription) {
        var protocolDescriptionTransfer = get(protocolDescription);
        
        if(protocolDescriptionTransfer == null) {
            var protocolTransfer = serverControl.getProtocolTransfer(userVisit, protocolDescription.getProtocol());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, protocolDescription.getLanguage());
            
            protocolDescriptionTransfer = new ProtocolDescriptionTransfer(languageTransfer, protocolTransfer,
                    protocolDescription.getDescription());
            put(protocolDescription, protocolDescriptionTransfer);
        }
        return protocolDescriptionTransfer;
    }
    
}
