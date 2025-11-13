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

package com.echothree.model.control.offer.server.transfer;

import com.echothree.model.control.chain.server.control.ChainControl;
import com.echothree.model.control.offer.common.transfer.OfferChainTypeTransfer;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.data.offer.server.entity.OfferChainType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class OfferChainTypeTransferCache
        extends BaseOfferTransferCache<OfferChainType, OfferChainTypeTransfer> {
    
    ChainControl chainControl = Session.getModelController(ChainControl.class);
    OfferControl offerControl = Session.getModelController(OfferControl.class);

    /** Creates a new instance of OfferChainTypeTransferCache */
    public OfferChainTypeTransferCache() {
        super();
    }
    
    public OfferChainTypeTransfer getOfferChainTypeTransfer(UserVisit userVisit, OfferChainType offerChainType) {
        var offerChainTypeTransfer = get(offerChainType);
        
        if(offerChainTypeTransfer == null) {
            var offerTransfer = offerControl.getOfferTransfer(userVisit, offerChainType.getOffer());
            var chainTypeTransfer = chainControl.getChainTypeTransfer(userVisit, offerChainType.getChainType());
            var chain = offerChainType.getChain();
            var chainTransfer = chain == null? null: chainControl.getChainTransfer(userVisit, chain);
            
            offerChainTypeTransfer = new OfferChainTypeTransfer(offerTransfer, chainTypeTransfer, chainTransfer);
            put(userVisit, offerChainType, offerChainTypeTransfer);
        }
        
        return offerChainTypeTransfer;
    }
    
}
