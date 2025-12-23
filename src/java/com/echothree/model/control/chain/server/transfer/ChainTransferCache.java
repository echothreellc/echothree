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

import com.echothree.model.control.chain.common.transfer.ChainTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ChainTransferCache
        extends BaseChainTransferCache<Chain, ChainTransfer> {

    ChainControl chainControl = Session.getModelController(ChainControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    
    /** Creates a new instance of ChainTransferCache */
    protected ChainTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public ChainTransfer getChainTransfer(UserVisit userVisit, Chain chain) {
        var chainTransfer = get(chain);
        
        if(chainTransfer == null) {
            var chainDetail = chain.getLastDetail();
            var chainTypeTransfer = chainControl.getChainTypeTransfer(userVisit, chainDetail.getChainType());
            var chainName = chainDetail.getChainName();
            var chainInstanceSequence = chainDetail.getChainInstanceSequence();
            var chainInstanceSequenceTransfer = chainInstanceSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, chainInstanceSequence);
            var isDefault = chainDetail.getIsDefault();
            var sortOrder = chainDetail.getSortOrder();
            var description = chainControl.getBestChainDescription(chain, getLanguage(userVisit));
            
            chainTransfer = new ChainTransfer(chainTypeTransfer, chainName, chainInstanceSequenceTransfer, isDefault, sortOrder, description);
            put(userVisit, chain, chainTransfer);
        }
        
        return chainTransfer;
    }
    
}
