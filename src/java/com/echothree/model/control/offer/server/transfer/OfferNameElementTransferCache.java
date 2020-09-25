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

package com.echothree.model.control.offer.server.transfer;

import com.echothree.model.control.offer.common.transfer.OfferNameElementTransfer;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.control.offer.server.control.OfferNameElementControl;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
import com.echothree.model.data.offer.server.entity.OfferNameElementDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class OfferNameElementTransferCache
        extends BaseOfferTransferCache<OfferNameElement, OfferNameElementTransfer> {

    OfferNameElementControl offerNameElementControl = (OfferNameElementControl)Session.getModelController(OfferNameElementControl.class);

    /** Creates a new instance of OfferNameElementTransferCache */
    public OfferNameElementTransferCache(UserVisit userVisit) {
        super(userVisit);

        setIncludeEntityInstance(true);
    }
    
    public OfferNameElementTransfer getOfferNameElementTransfer(OfferNameElement offerNameElement) {
        OfferNameElementTransfer offerNameElementTransfer = get(offerNameElement);
        
        if(offerNameElementTransfer == null) {
            OfferNameElementDetail offerNameElementDetail = offerNameElement.getLastDetail();
            String offerNameElementName = offerNameElementDetail.getOfferNameElementName();
            Integer offset = offerNameElementDetail.getOffset();
            Integer length = offerNameElementDetail.getLength();
            String validationPattern = offerNameElementDetail.getValidationPattern();
            String description = offerNameElementControl.getBestOfferNameElementDescription(offerNameElement, getLanguage());
            
            offerNameElementTransfer = new OfferNameElementTransfer(offerNameElementName, offset, length, validationPattern,
                    description);
            put(offerNameElement, offerNameElementTransfer);
        }
        
        return offerNameElementTransfer;
    }
    
}
