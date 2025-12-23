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

import com.echothree.model.control.chain.common.transfer.ChainTypeDescriptionTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ChainTypeDescriptionTransferCache
        extends BaseChainDescriptionTransferCache<ChainTypeDescription, ChainTypeDescriptionTransfer> {

    ChainControl chainControl = Session.getModelController(ChainControl.class);

    /** Creates a new instance of ChainTypeDescriptionTransferCache */
    protected ChainTypeDescriptionTransferCache() {
        super();
    }
    
    public ChainTypeDescriptionTransfer getChainTypeDescriptionTransfer(UserVisit userVisit, ChainTypeDescription chainTypeDescription) {
        var chainTypeDescriptionTransfer = get(chainTypeDescription);
        
        if(chainTypeDescriptionTransfer == null) {
            var chainTypeTransfer = chainControl.getChainTypeTransfer(userVisit, chainTypeDescription.getChainType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, chainTypeDescription.getLanguage());
            
            chainTypeDescriptionTransfer = new ChainTypeDescriptionTransfer(languageTransfer, chainTypeTransfer, chainTypeDescription.getDescription());
            put(userVisit, chainTypeDescription, chainTypeDescriptionTransfer);
        }
        
        return chainTypeDescriptionTransfer;
    }
    
}
