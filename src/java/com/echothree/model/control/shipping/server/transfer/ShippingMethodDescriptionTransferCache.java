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

package com.echothree.model.control.shipping.server.transfer;

import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.control.shipping.remote.transfer.ShippingMethodDescriptionTransfer;
import com.echothree.model.control.shipping.remote.transfer.ShippingMethodTransfer;
import com.echothree.model.control.shipping.server.ShippingControl;
import com.echothree.model.data.shipping.server.entity.ShippingMethodDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ShippingMethodDescriptionTransferCache
        extends BaseShippingDescriptionTransferCache<ShippingMethodDescription, ShippingMethodDescriptionTransfer> {
    
    /** Creates a new instance of ShippingMethodDescriptionTransferCache */
    public ShippingMethodDescriptionTransferCache(UserVisit userVisit, ShippingControl shippingControl) {
        super(userVisit, shippingControl);
    }
    
    public ShippingMethodDescriptionTransfer getShippingMethodDescriptionTransfer(ShippingMethodDescription shippingMethodDescription) {
        ShippingMethodDescriptionTransfer shippingMethodDescriptionTransfer = get(shippingMethodDescription);
        
        if(shippingMethodDescriptionTransfer == null) {
            ShippingMethodTransfer shippingMethodTransfer = shippingControl.getShippingMethodTransfer(userVisit, shippingMethodDescription.getShippingMethod());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, shippingMethodDescription.getLanguage());
            String description = shippingMethodDescription.getDescription();
            
            shippingMethodDescriptionTransfer = new ShippingMethodDescriptionTransfer(languageTransfer, shippingMethodTransfer,
                    description);
            put(shippingMethodDescription, shippingMethodDescriptionTransfer);
        }
        
        return shippingMethodDescriptionTransfer;
    }
    
}
