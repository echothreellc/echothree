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

import com.echothree.model.control.chain.common.transfer.ChainTransfer;
import com.echothree.model.control.chain.common.transfer.ChainTypeTransfer;
import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.chain.server.entity.Chain;
import com.echothree.model.data.chain.server.entity.ChainDetail;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ChainTransferCache
        extends BaseChainTransferCache<Chain, ChainTransfer> {
    
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);
    
    /** Creates a new instance of ChainTransferCache */
    public ChainTransferCache(UserVisit userVisit, ChainControl chainControl) {
        super(userVisit, chainControl);
        
        setIncludeEntityInstance(true);
    }
    
    public ChainTransfer getChainTransfer(Chain chain) {
        ChainTransfer chainTransfer = get(chain);
        
        if(chainTransfer == null) {
            ChainDetail chainDetail = chain.getLastDetail();
            ChainTypeTransfer chainTypeTransfer = chainControl.getChainTypeTransfer(userVisit, chainDetail.getChainType());
            String chainName = chainDetail.getChainName();
            Sequence chainInstanceSequence = chainDetail.getChainInstanceSequence();
            SequenceTransfer chainInstanceSequenceTransfer = chainInstanceSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, chainInstanceSequence);
            Boolean isDefault = chainDetail.getIsDefault();
            Integer sortOrder = chainDetail.getSortOrder();
            String description = chainControl.getBestChainDescription(chain, getLanguage());
            
            chainTransfer = new ChainTransfer(chainTypeTransfer, chainName, chainInstanceSequenceTransfer, isDefault, sortOrder, description);
            put(chain, chainTransfer);
        }
        
        return chainTransfer;
    }
    
}
