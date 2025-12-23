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

package com.echothree.model.control.offer.common.transfer;

import com.echothree.model.control.chain.common.transfer.ChainTransfer;
import com.echothree.model.control.chain.common.transfer.ChainTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class OfferChainTypeTransfer
        extends BaseTransfer {
    
    private OfferTransfer offer;
    private ChainTypeTransfer chainType;
    private ChainTransfer chain;
    
    /** Creates a new instance of OfferChainTypeTransfer */
    public OfferChainTypeTransfer(OfferTransfer offer, ChainTypeTransfer chainType, ChainTransfer chain) {
        this.offer = offer;
        this.chainType = chainType;
        this.chain = chain;
    }
    
    public OfferTransfer getOffer() {
        return offer;
    }
    
    public void setOffer(OfferTransfer offer) {
        this.offer = offer;
    }
    
    public ChainTypeTransfer getChainType() {
        return chainType;
    }
    
    public void setChainType(ChainTypeTransfer chainType) {
        this.chainType = chainType;
    }

    public ChainTransfer getChain() {
        return chain;
    }

    public void setChain(ChainTransfer chain) {
        this.chain = chain;
    }
    
}
