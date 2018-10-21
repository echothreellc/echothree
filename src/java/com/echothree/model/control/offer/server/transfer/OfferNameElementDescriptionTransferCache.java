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

import com.echothree.model.control.offer.remote.transfer.OfferNameElementDescriptionTransfer;
import com.echothree.model.control.offer.remote.transfer.OfferNameElementTransfer;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.offer.server.entity.OfferNameElementDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class OfferNameElementDescriptionTransferCache
        extends BaseOfferDescriptionTransferCache<OfferNameElementDescription, OfferNameElementDescriptionTransfer> {
    
    /** Creates a new instance of OfferNameElementDescriptionTransferCache */
    public OfferNameElementDescriptionTransferCache(UserVisit userVisit, OfferControl offerControl) {
        super(userVisit, offerControl);
    }
    
    public OfferNameElementDescriptionTransfer getOfferNameElementDescriptionTransfer(OfferNameElementDescription offerNameElementDescription) {
        OfferNameElementDescriptionTransfer offerNameElementDescriptionTransfer = get(offerNameElementDescription);
        
        if(offerNameElementDescriptionTransfer == null) {
            OfferNameElementTransfer offerNameElementTransfer = offerControl.getOfferNameElementTransfer(userVisit,
                    offerNameElementDescription.getOfferNameElement());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, offerNameElementDescription.getLanguage());
            
            offerNameElementDescriptionTransfer = new OfferNameElementDescriptionTransfer(languageTransfer, offerNameElementTransfer,
                    offerNameElementDescription.getDescription());
            put(offerNameElementDescription, offerNameElementDescriptionTransfer);
        }
        
        return offerNameElementDescriptionTransfer;
    }
    
}
