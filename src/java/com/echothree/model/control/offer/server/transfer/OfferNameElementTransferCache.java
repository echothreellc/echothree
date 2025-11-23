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

import com.echothree.model.control.offer.common.transfer.OfferNameElementTransfer;
import com.echothree.model.control.offer.server.control.OfferNameElementControl;
import com.echothree.model.data.offer.server.entity.OfferNameElement;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OfferNameElementTransferCache
        extends BaseOfferTransferCache<OfferNameElement, OfferNameElementTransfer> {

    OfferNameElementControl offerNameElementControl = Session.getModelController(OfferNameElementControl.class);

    /** Creates a new instance of OfferNameElementTransferCache */
    protected OfferNameElementTransferCache() {
        super();

        setIncludeEntityInstance(true);
    }
    
    public OfferNameElementTransfer getOfferNameElementTransfer(UserVisit userVisit, OfferNameElement offerNameElement) {
        var offerNameElementTransfer = get(offerNameElement);
        
        if(offerNameElementTransfer == null) {
            var offerNameElementDetail = offerNameElement.getLastDetail();
            var offerNameElementName = offerNameElementDetail.getOfferNameElementName();
            var offset = offerNameElementDetail.getOffset();
            var length = offerNameElementDetail.getLength();
            var validationPattern = offerNameElementDetail.getValidationPattern();
            var description = offerNameElementControl.getBestOfferNameElementDescription(offerNameElement, getLanguage(userVisit));
            
            offerNameElementTransfer = new OfferNameElementTransfer(offerNameElementName, offset, length, validationPattern,
                    description);
            put(userVisit, offerNameElement, offerNameElementTransfer);
        }
        
        return offerNameElementTransfer;
    }
    
}
