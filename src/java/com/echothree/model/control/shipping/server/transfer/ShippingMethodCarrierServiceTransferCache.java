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

import com.echothree.model.control.carrier.common.transfer.CarrierServiceTransfer;
import com.echothree.model.control.carrier.server.CarrierControl;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodCarrierServiceTransfer;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodTransfer;
import com.echothree.model.control.shipping.server.ShippingControl;
import com.echothree.model.data.shipping.server.entity.ShippingMethodCarrierService;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ShippingMethodCarrierServiceTransferCache
        extends BaseShippingTransferCache<ShippingMethodCarrierService, ShippingMethodCarrierServiceTransfer> {
    
    CarrierControl carrierControl;
    
    /** Creates a new instance of ShippingMethodCarrierServiceTransferCache */
    public ShippingMethodCarrierServiceTransferCache(UserVisit userVisit, ShippingControl shippingControl) {
        super(userVisit, shippingControl);
        
        carrierControl = (CarrierControl)Session.getModelController(CarrierControl.class);
    }
    
    public ShippingMethodCarrierServiceTransfer getShippingMethodCarrierServiceTransfer(ShippingMethodCarrierService shippingMethodCarrierService) {
        ShippingMethodCarrierServiceTransfer shippingMethodCarrierServiceTransfer = get(shippingMethodCarrierService);
        
        if(shippingMethodCarrierServiceTransfer == null) {
            ShippingMethodTransfer shippingMethod = shippingControl.getShippingMethodTransfer(userVisit, shippingMethodCarrierService.getShippingMethod());
            CarrierServiceTransfer carrierService = carrierControl.getCarrierServiceTransfer(userVisit, shippingMethodCarrierService.getCarrierService());
            
            shippingMethodCarrierServiceTransfer = new ShippingMethodCarrierServiceTransfer(shippingMethod, carrierService);
            put(shippingMethodCarrierService, shippingMethodCarrierServiceTransfer);
        }
        
        return shippingMethodCarrierServiceTransfer;
    }
    
}
