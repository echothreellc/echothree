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

import com.echothree.model.control.offer.common.transfer.UseNameElementDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.UseNameElementTransfer;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.data.offer.server.entity.UseNameElementDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class UseNameElementDescriptionTransferCache
        extends BaseOfferDescriptionTransferCache<UseNameElementDescription, UseNameElementDescriptionTransfer> {
    
    /** Creates a new instance of UseNameElementDescriptionTransferCache */
    public UseNameElementDescriptionTransferCache(UserVisit userVisit, OfferControl offerControl) {
        super(userVisit, offerControl);
    }
    
    public UseNameElementDescriptionTransfer getUseNameElementDescriptionTransfer(UseNameElementDescription useNameElementDescription) {
        UseNameElementDescriptionTransfer useNameElementDescriptionTransfer = get(useNameElementDescription);
        
        if(useNameElementDescriptionTransfer == null) {
            UseNameElementTransfer useNameElementTransfer = offerControl.getUseNameElementTransfer(userVisit,
                    useNameElementDescription.getUseNameElement());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, useNameElementDescription.getLanguage());
            
            useNameElementDescriptionTransfer = new UseNameElementDescriptionTransfer(languageTransfer, useNameElementTransfer,
                    useNameElementDescription.getDescription());
            put(useNameElementDescription, useNameElementDescriptionTransfer);
        }
        
        return useNameElementDescriptionTransfer;
    }
    
}
