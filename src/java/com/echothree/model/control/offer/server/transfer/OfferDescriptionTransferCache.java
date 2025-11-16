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

import com.echothree.model.control.offer.common.transfer.OfferDescriptionTransfer;
import com.echothree.model.control.offer.server.control.OfferControl;
import com.echothree.model.data.offer.server.entity.OfferDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OfferDescriptionTransferCache
        extends BaseOfferDescriptionTransferCache<OfferDescription, OfferDescriptionTransfer> {

    OfferControl offerControl = Session.getModelController(OfferControl.class);

    /** Creates a new instance of OfferDescriptionTransferCache */
    protected OfferDescriptionTransferCache() {
        super();
    }
    
    public OfferDescriptionTransfer getOfferDescriptionTransfer(UserVisit userVisit, OfferDescription offerDescription) {
        var offerDescriptionTransfer = get(offerDescription);
        
        if(offerDescriptionTransfer == null) {
            var offerTransfer = offerControl.getOfferTransfer(userVisit, offerDescription.getOffer());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, offerDescription.getLanguage());
            
            offerDescriptionTransfer = new OfferDescriptionTransfer(languageTransfer, offerTransfer, offerDescription.getDescription());
            put(userVisit, offerDescription, offerDescriptionTransfer);
        }
        
        return offerDescriptionTransfer;
    }
    
}
