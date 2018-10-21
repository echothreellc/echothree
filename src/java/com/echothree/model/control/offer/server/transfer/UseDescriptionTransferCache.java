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

import com.echothree.model.control.offer.remote.transfer.UseDescriptionTransfer;
import com.echothree.model.control.offer.remote.transfer.UseTransfer;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.offer.server.entity.UseDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class UseDescriptionTransferCache
        extends BaseOfferDescriptionTransferCache<UseDescription, UseDescriptionTransfer> {
    
    /** Creates a new instance of UseDescriptionTransferCache */
    public UseDescriptionTransferCache(UserVisit userVisit, OfferControl offerControl) {
        super(userVisit, offerControl);
    }
    
    public UseDescriptionTransfer getUseDescriptionTransfer(UseDescription useDescription) {
        UseDescriptionTransfer useDescriptionTransfer = get(useDescription);
        
        if(useDescriptionTransfer == null) {
            UseTransfer useTransfer = offerControl.getUseTransfer(userVisit, useDescription.getUse());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, useDescription.getLanguage());
            
            useDescriptionTransfer = new UseDescriptionTransfer(languageTransfer, useTransfer, useDescription.getDescription());
            put(useDescription, useDescriptionTransfer);
        }
        
        return useDescriptionTransfer;
    }
    
}
