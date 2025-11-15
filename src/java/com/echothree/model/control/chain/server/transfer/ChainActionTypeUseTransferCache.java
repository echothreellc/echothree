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

import com.echothree.model.control.chain.common.transfer.ChainActionTypeTransfer;
import com.echothree.model.control.chain.common.transfer.ChainActionTypeUseTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.data.chain.server.entity.ChainActionTypeUse;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ChainActionTypeUseTransferCache
        extends BaseChainTransferCache<ChainActionTypeUse, ChainActionTypeUseTransfer> {

    ChainControl chainControl = Session.getModelController(ChainControl.class);

    /** Creates a new instance of ChainActionTypeUseTransferCache */
    public ChainActionTypeUseTransferCache() {
        super();
    }
    
    public ChainActionTypeUseTransfer getChainActionTypeUseTransfer(UserVisit userVisit, ChainActionTypeUse chainActionTypeUse) {
        var chainActionTypeUseTransfer = get(chainActionTypeUse);
        
        if(chainActionTypeUseTransfer == null) {
            var chainKind = chainControl.getChainKindTransfer(userVisit, chainActionTypeUse.getChainKind());
            ChainActionTypeTransfer chainActionType = null; // TODO: chainControl.getChainActionTypeTransfer(userVisit, chainActionTypeUse.getChainActionType());
            var isDefault = chainActionTypeUse.getIsDefault();
            
            chainActionTypeUseTransfer = new ChainActionTypeUseTransfer(chainKind, chainActionType, isDefault);
            put(userVisit, chainActionTypeUse, chainActionTypeUseTransfer);
        }
        return chainActionTypeUseTransfer;
    }
    
}
