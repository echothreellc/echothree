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

package com.echothree.model.control.offer.server.transfer;

import com.echothree.model.control.offer.common.transfer.OfferNameElementDescriptionTransfer;
import com.echothree.model.control.offer.server.control.OfferNameElementControl;
import com.echothree.model.data.offer.server.entity.OfferNameElementDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OfferNameElementDescriptionTransferCache
        extends BaseOfferDescriptionTransferCache<OfferNameElementDescription, OfferNameElementDescriptionTransfer> {

    OfferNameElementControl offerNameElementControl = Session.getModelController(OfferNameElementControl.class);

    /** Creates a new instance of OfferNameElementDescriptionTransferCache */
    protected OfferNameElementDescriptionTransferCache() {
        super();
    }
    
    public OfferNameElementDescriptionTransfer getOfferNameElementDescriptionTransfer(UserVisit userVisit, OfferNameElementDescription offerNameElementDescription) {
        var offerNameElementDescriptionTransfer = get(offerNameElementDescription);
        
        if(offerNameElementDescriptionTransfer == null) {
            var offerNameElementTransfer = offerNameElementControl.getOfferNameElementTransfer(userVisit, offerNameElementDescription.getOfferNameElement());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, offerNameElementDescription.getLanguage());
            
            offerNameElementDescriptionTransfer = new OfferNameElementDescriptionTransfer(languageTransfer, offerNameElementTransfer,
                    offerNameElementDescription.getDescription());
            put(userVisit, offerNameElementDescription, offerNameElementDescriptionTransfer);
        }
        
        return offerNameElementDescriptionTransfer;
    }
    
}
