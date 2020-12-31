// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.chain.common.transfer.ChainEntityRoleTypeTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.chain.server.entity.ChainEntityRoleTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ChainEntityRoleTypeDescriptionTransferCache
        extends BaseChainDescriptionTransferCache<ChainEntityRoleTypeDescription, ChainEntityRoleTypeDescriptionTransfer> {
    
    /** Creates a new instance of ChainEntityRoleTypeDescriptionTransferCache */
    public ChainEntityRoleTypeDescriptionTransferCache(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit, chainControl);
    }
    
    public ChainEntityRoleTypeDescriptionTransfer getChainEntityRoleTypeDescriptionTransfer(ChainEntityRoleTypeDescription chainEntityRoleTypeDescription) {
        ChainEntityRoleTypeDescriptionTransfer chainEntityRoleTypeDescriptionTransfer = get(chainEntityRoleTypeDescription);
        
        if(chainEntityRoleTypeDescriptionTransfer == null) {
            ChainEntityRoleTypeTransfer chainEntityRoleTypeTransfer = chainControl.getChainEntityRoleTypeTransfer(userVisit, chainEntityRoleTypeDescription.getChainEntityRoleType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, chainEntityRoleTypeDescription.getLanguage());
            
            chainEntityRoleTypeDescriptionTransfer = new ChainEntityRoleTypeDescriptionTransfer(languageTransfer, chainEntityRoleTypeTransfer, chainEntityRoleTypeDescription.getDescription());
            put(chainEntityRoleTypeDescription, chainEntityRoleTypeDescriptionTransfer);
        }
        
        return chainEntityRoleTypeDescriptionTransfer;
    }
    
}
