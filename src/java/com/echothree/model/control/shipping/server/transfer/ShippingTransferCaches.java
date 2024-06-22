// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class ShippingTransferCaches
        extends BaseTransferCaches {
    
    protected ShippingControl shippingControl;
    
    ShippingMethodDescriptionTransferCache shippingMethodDescriptionTransferCache;
    ShippingMethodTransferCache shippingMethodTransferCache;
    ShippingMethodCarrierServiceTransferCache shippingMethodCarrierServiceTransferCache;
    
    /** Creates a new instance of ShippingTransferCaches */
    public ShippingTransferCaches(UserVisit userVisit, ShippingControl shippingControl) {
        super(userVisit);
        
        this.shippingControl = shippingControl;
    }
    
    public ShippingMethodDescriptionTransferCache getShippingMethodDescriptionTransferCache() {
        if(shippingMethodDescriptionTransferCache == null)
            shippingMethodDescriptionTransferCache = new ShippingMethodDescriptionTransferCache(userVisit, shippingControl);
        
        return shippingMethodDescriptionTransferCache;
    }
    
    public ShippingMethodTransferCache getShippingMethodTransferCache() {
        if(shippingMethodTransferCache == null)
            shippingMethodTransferCache = new ShippingMethodTransferCache(userVisit, shippingControl);
        
        return shippingMethodTransferCache;
    }
    
    public ShippingMethodCarrierServiceTransferCache getShippingMethodCarrierServiceTransferCache() {
        if(shippingMethodCarrierServiceTransferCache == null)
            shippingMethodCarrierServiceTransferCache = new ShippingMethodCarrierServiceTransferCache(userVisit, shippingControl);
        
        return shippingMethodCarrierServiceTransferCache;
    }
    
}
