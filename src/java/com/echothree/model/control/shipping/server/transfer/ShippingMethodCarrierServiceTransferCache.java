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

package com.echothree.model.control.shipping.server.transfer;

import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodCarrierServiceTransfer;
import com.echothree.model.control.shipping.server.control.ShippingControl;
import com.echothree.model.data.shipping.server.entity.ShippingMethodCarrierService;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ShippingMethodCarrierServiceTransferCache
        extends BaseShippingTransferCache<ShippingMethodCarrierService, ShippingMethodCarrierServiceTransfer> {
    
    CarrierControl carrierControl = Session.getModelController(CarrierControl.class);
    ShippingControl shippingControl = Session.getModelController(ShippingControl.class);

    /** Creates a new instance of ShippingMethodCarrierServiceTransferCache */
    public ShippingMethodCarrierServiceTransferCache() {
        super();
    }
    
    public ShippingMethodCarrierServiceTransfer getShippingMethodCarrierServiceTransfer(ShippingMethodCarrierService shippingMethodCarrierService) {
        var shippingMethodCarrierServiceTransfer = get(shippingMethodCarrierService);
        
        if(shippingMethodCarrierServiceTransfer == null) {
            var shippingMethod = shippingControl.getShippingMethodTransfer(userVisit, shippingMethodCarrierService.getShippingMethod());
            var carrierService = carrierControl.getCarrierServiceTransfer(userVisit, shippingMethodCarrierService.getCarrierService());
            
            shippingMethodCarrierServiceTransfer = new ShippingMethodCarrierServiceTransfer(shippingMethod, carrierService);
            put(userVisit, shippingMethodCarrierService, shippingMethodCarrierServiceTransfer);
        }
        
        return shippingMethodCarrierServiceTransfer;
    }
    
}
