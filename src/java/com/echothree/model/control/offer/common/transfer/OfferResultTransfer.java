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

import com.echothree.model.control.offer.common.transfer.OfferTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class OfferResultTransfer
        extends BaseTransfer {
    
    private String offerName;
    private OfferTransfer offer;
    
    /** Creates a new instance of OfferResultTransfer */
    public OfferResultTransfer(String offerName, OfferTransfer offer) {
        this.offerName = offerName;
        this.offer = offer;
    }

    /**
     * Returns the offerName.
     * @return the offerName
     */
    public String getOfferName() {
        return offerName;
    }

    /**
     * Sets the offerName.
     * @param offerName the offerName to set
     */
    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    /**
     * Returns the offer.
     * @return the offer
     */
    public OfferTransfer getOffer() {
        return offer;
    }

    /**
     * Sets the offer.
     * @param offer the offer to set
     */
    public void setOffer(OfferTransfer offer) {
        this.offer = offer;
    }

 }
