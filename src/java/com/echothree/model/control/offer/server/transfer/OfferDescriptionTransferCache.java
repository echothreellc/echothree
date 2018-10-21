// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.offer.remote.transfer.OfferDescriptionTransfer;
import com.echothree.model.control.offer.remote.transfer.OfferTransfer;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.offer.server.entity.OfferDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class OfferDescriptionTransferCache
        extends BaseOfferDescriptionTransferCache<OfferDescription, OfferDescriptionTransfer> {
    
    /** Creates a new instance of OfferDescriptionTransferCache */
    public OfferDescriptionTransferCache(UserVisit userVisit, OfferControl offerControl) {
        super(userVisit, offerControl);
    }
    
    public OfferDescriptionTransfer getOfferDescriptionTransfer(OfferDescription offerDescription) {
        OfferDescriptionTransfer offerDescriptionTransfer = get(offerDescription);
        
        if(offerDescriptionTransfer == null) {
            OfferTransfer offerTransfer = offerControl.getOfferTransfer(userVisit, offerDescription.getOffer());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, offerDescription.getLanguage());
            
            offerDescriptionTransfer = new OfferDescriptionTransfer(languageTransfer, offerTransfer, offerDescription.getDescription());
            put(offerDescription, offerDescriptionTransfer);
        }
        
        return offerDescriptionTransfer;
    }
    
}
