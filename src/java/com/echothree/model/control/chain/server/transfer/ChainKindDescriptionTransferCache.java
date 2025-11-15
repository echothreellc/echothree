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

import com.echothree.model.control.chain.common.transfer.ChainKindDescriptionTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainKindDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ChainKindDescriptionTransferCache
        extends BaseChainDescriptionTransferCache<ChainKindDescription, ChainKindDescriptionTransfer> {

    ChainControl chainControl = Session.getModelController(ChainControl.class);

    /** Creates a new instance of ChainKindDescriptionTransferCache */
    public ChainKindDescriptionTransferCache() {
        super();
    }
    
    public ChainKindDescriptionTransfer getChainKindDescriptionTransfer(UserVisit userVisit, ChainKindDescription chainKindDescription) {
        var chainKindDescriptionTransfer = get(chainKindDescription);
        
        if(chainKindDescriptionTransfer == null) {
            var chainKindTransfer = chainControl.getChainKindTransfer(userVisit, chainKindDescription.getChainKind());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, chainKindDescription.getLanguage());
            
            chainKindDescriptionTransfer = new ChainKindDescriptionTransfer(languageTransfer, chainKindTransfer, chainKindDescription.getDescription());
            put(userVisit, chainKindDescription, chainKindDescriptionTransfer);
        }
        
        return chainKindDescriptionTransfer;
    }
    
}
