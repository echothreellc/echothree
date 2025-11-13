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

import com.echothree.model.control.customer.server.control.CustomerControl;
import com.echothree.model.control.offer.common.transfer.OfferCustomerTypeTransfer;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.data.offer.server.entity.OfferCustomerType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class OfferCustomerTypeTransferCache
        extends BaseOfferTransferCache<OfferCustomerType, OfferCustomerTypeTransfer> {
    
    CustomerControl customerControl = Session.getModelController(CustomerControl.class);
    OfferControl offerControl = Session.getModelController(OfferControl.class);

    /** Creates a new instance of OfferCustomerTypeTransferCache */
    public OfferCustomerTypeTransferCache() {
        super();
    }
    
    public OfferCustomerTypeTransfer getOfferCustomerTypeTransfer(OfferCustomerType offerCustomerType) {
        var offerCustomerTypeTransfer = get(offerCustomerType);
        
        if(offerCustomerTypeTransfer == null) {
            var offer = offerControl.getOfferTransfer(userVisit, offerCustomerType.getOffer());
            var customerType = customerControl.getCustomerTypeTransfer(userVisit, offerCustomerType.getCustomerType());
            var isDefault = offerCustomerType.getIsDefault();
            var sortOrder = offerCustomerType.getSortOrder();
            
            offerCustomerTypeTransfer = new OfferCustomerTypeTransfer(offer, customerType, isDefault, sortOrder);
            put(userVisit, offerCustomerType, offerCustomerTypeTransfer);
        }
        
        return offerCustomerTypeTransfer;
    }
    
}
