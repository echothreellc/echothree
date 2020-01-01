// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.chain.common.transfer.ChainTypeDescriptionTransfer;
import com.echothree.model.control.chain.common.transfer.ChainTypeTransfer;
import com.echothree.model.control.chain.server.ChainControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.chain.server.entity.ChainTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ChainTypeDescriptionTransferCache
        extends BaseChainDescriptionTransferCache<ChainTypeDescription, ChainTypeDescriptionTransfer> {
    
    /** Creates a new instance of ChainTypeDescriptionTransferCache */
    public ChainTypeDescriptionTransferCache(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit, chainControl);
    }
    
    public ChainTypeDescriptionTransfer getChainTypeDescriptionTransfer(ChainTypeDescription chainTypeDescription) {
        ChainTypeDescriptionTransfer chainTypeDescriptionTransfer = get(chainTypeDescription);
        
        if(chainTypeDescriptionTransfer == null) {
            ChainTypeTransfer chainTypeTransfer = chainControl.getChainTypeTransfer(userVisit, chainTypeDescription.getChainType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, chainTypeDescription.getLanguage());
            
            chainTypeDescriptionTransfer = new ChainTypeDescriptionTransfer(languageTransfer, chainTypeTransfer, chainTypeDescription.getDescription());
            put(chainTypeDescription, chainTypeDescriptionTransfer);
        }
        
        return chainTypeDescriptionTransfer;
    }
    
}
