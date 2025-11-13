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

import com.echothree.model.control.chain.common.transfer.ChainActionTypeDescriptionTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainActionTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ChainActionTypeDescriptionTransferCache
        extends BaseChainDescriptionTransferCache<ChainActionTypeDescription, ChainActionTypeDescriptionTransfer> {
    
    /** Creates a new instance of ChainActionTypeDescriptionTransferCache */
    public ChainActionTypeDescriptionTransferCache(ChainControl chainControl) {
        super(chainControl);
    }
    
    public ChainActionTypeDescriptionTransfer getChainActionTypeDescriptionTransfer(UserVisit userVisit, ChainActionTypeDescription chainActionTypeDescription) {
        var chainActionTypeDescriptionTransfer = get(chainActionTypeDescription);
        
        if(chainActionTypeDescriptionTransfer == null) {
            var chainActionTypeTransfer = chainControl.getChainActionTypeTransfer(userVisit, chainActionTypeDescription.getChainActionType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, chainActionTypeDescription.getLanguage());
            
            chainActionTypeDescriptionTransfer = new ChainActionTypeDescriptionTransfer(languageTransfer, chainActionTypeTransfer, chainActionTypeDescription.getDescription());
            put(userVisit, chainActionTypeDescription, chainActionTypeDescriptionTransfer);
        }
        
        return chainActionTypeDescriptionTransfer;
    }
    
}
