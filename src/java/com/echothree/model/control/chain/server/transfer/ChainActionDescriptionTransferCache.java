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

package com.echothree.model.control.chain.server.transfer;

import com.echothree.model.control.chain.common.transfer.ChainActionDescriptionTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainActionDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ChainActionDescriptionTransferCache
        extends BaseChainDescriptionTransferCache<ChainActionDescription, ChainActionDescriptionTransfer> {
    
    /** Creates a new instance of ChainActionDescriptionTransferCache */
    public ChainActionDescriptionTransferCache(ChainControl chainControl) {
        super(chainControl);
    }
    
    public ChainActionDescriptionTransfer getChainActionDescriptionTransfer(ChainActionDescription chainActionDescription) {
        var chainActionDescriptionTransfer = get(chainActionDescription);
        
        if(chainActionDescriptionTransfer == null) {
            var chainActionTransfer = chainControl.getChainActionTransfer(userVisit, chainActionDescription.getChainAction());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, chainActionDescription.getLanguage());
            
            chainActionDescriptionTransfer = new ChainActionDescriptionTransfer(languageTransfer, chainActionTransfer, chainActionDescription.getDescription());
            put(userVisit, chainActionDescription, chainActionDescriptionTransfer);
        }
        
        return chainActionDescriptionTransfer;
    }
    
}
