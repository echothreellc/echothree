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

import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class OfferUseTransfer
        extends BaseTransfer {
    
    private OfferTransfer offer;
    private UseTransfer use;
    private SequenceTransfer salesOrderSequence;
    
    /** Creates a new instance of OfferUseTransfer */
    public OfferUseTransfer(OfferTransfer offer, UseTransfer use, SequenceTransfer salesOrderSequence) {
        this.offer = offer;
        this.use = use;
        this.salesOrderSequence = salesOrderSequence;
    }
    
    public OfferTransfer getOffer() {
        return offer;
    }
    
    public void setOffer(OfferTransfer offer) {
        this.offer = offer;
    }
    
    public UseTransfer getUse() {
        return use;
    }
    
    public void setUse(UseTransfer use) {
        this.use = use;
    }
    
    public SequenceTransfer getSalesOrderSequence() {
        return salesOrderSequence;
    }
    
    public void setSalesOrderSequence(SequenceTransfer salesOrderSequence) {
        this.salesOrderSequence = salesOrderSequence;
    }
    
}
