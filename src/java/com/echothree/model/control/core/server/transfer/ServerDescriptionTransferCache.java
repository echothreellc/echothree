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

import com.echothree.model.control.core.common.transfer.ServerDescriptionTransfer;
import com.echothree.model.control.core.server.control.ServerControl;
import com.echothree.model.data.core.server.entity.ServerDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ServerDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<ServerDescription, ServerDescriptionTransfer> {

    ServerControl serverControl = Session.getModelController(ServerControl.class);

    /** Creates a new instance of ServerDescriptionTransferCache */
    public ServerDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public ServerDescriptionTransfer getServerDescriptionTransfer(ServerDescription serverDescription) {
        var serverDescriptionTransfer = get(serverDescription);
        
        if(serverDescriptionTransfer == null) {
            var serverTransfer = serverControl.getServerTransfer(userVisit, serverDescription.getServer());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, serverDescription.getLanguage());
            
            serverDescriptionTransfer = new ServerDescriptionTransfer(languageTransfer, serverTransfer, serverDescription.getDescription());
            put(userVisit, serverDescription, serverDescriptionTransfer);
        }
        return serverDescriptionTransfer;
    }
    
}
