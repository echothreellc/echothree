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

package com.echothree.model.control.chain.server.transfer;

import com.echothree.model.control.chain.common.transfer.ChainEntityRoleTypeDescriptionTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ChainEntityRoleTypeDescriptionTransferCache
        extends BaseChainDescriptionTransferCache<ChainEntityRoleTypeDescription, ChainEntityRoleTypeDescriptionTransfer> {

    ChainControl chainControl = Session.getModelController(ChainControl.class);

    /** Creates a new instance of ChainEntityRoleTypeDescriptionTransferCache */
    protected ChainEntityRoleTypeDescriptionTransferCache() {
        super();
    }
    
    public ChainEntityRoleTypeDescriptionTransfer getChainEntityRoleTypeDescriptionTransfer(UserVisit userVisit, ChainEntityRoleTypeDescription chainEntityRoleTypeDescription) {
        var chainEntityRoleTypeDescriptionTransfer = get(chainEntityRoleTypeDescription);
        
        if(chainEntityRoleTypeDescriptionTransfer == null) {
            var chainEntityRoleTypeTransfer = chainControl.getChainEntityRoleTypeTransfer(userVisit, chainEntityRoleTypeDescription.getChainEntityRoleType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, chainEntityRoleTypeDescription.getLanguage());
            
            chainEntityRoleTypeDescriptionTransfer = new ChainEntityRoleTypeDescriptionTransfer(languageTransfer, chainEntityRoleTypeTransfer, chainEntityRoleTypeDescription.getDescription());
            put(userVisit, chainEntityRoleTypeDescription, chainEntityRoleTypeDescriptionTransfer);
        }
        
        return chainEntityRoleTypeDescriptionTransfer;
    }
    
}
