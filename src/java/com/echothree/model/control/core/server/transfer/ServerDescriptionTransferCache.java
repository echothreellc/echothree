// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.core.remote.transfer.ServerDescriptionTransfer;
import com.echothree.model.control.core.remote.transfer.ServerTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.core.server.entity.ServerDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ServerDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<ServerDescription, ServerDescriptionTransfer> {
    
    /** Creates a new instance of ServerDescriptionTransferCache */
    public ServerDescriptionTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public ServerDescriptionTransfer getServerDescriptionTransfer(ServerDescription serverDescription) {
        ServerDescriptionTransfer serverDescriptionTransfer = get(serverDescription);
        
        if(serverDescriptionTransfer == null) {
            ServerTransfer serverTransfer = coreControl.getServerTransfer(userVisit, serverDescription.getServer());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, serverDescription.getLanguage());
            
            serverDescriptionTransfer = new ServerDescriptionTransfer(languageTransfer, serverTransfer, serverDescription.getDescription());
            put(serverDescription, serverDescriptionTransfer);
        }
        return serverDescriptionTransfer;
    }
    
}
