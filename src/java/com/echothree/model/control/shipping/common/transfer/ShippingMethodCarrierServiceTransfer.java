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

package com.echothree.model.control.shipping.common.transfer;

import com.echothree.model.control.carrier.common.transfer.CarrierServiceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ShippingMethodCarrierServiceTransfer
        extends BaseTransfer {
    
    private ShippingMethodTransfer shippingMethod;
    private CarrierServiceTransfer carrierService;
    
    /** Creates a new instance of ShippingMethodCarrierServiceTransfer */
    public ShippingMethodCarrierServiceTransfer(ShippingMethodTransfer shippingMethod, CarrierServiceTransfer carrierService) {
        this.shippingMethod = shippingMethod;
        this.carrierService = carrierService;
    }
    
    public ShippingMethodTransfer getShippingMethod() {
        return shippingMethod;
    }
    
    public void setShippingMethod(ShippingMethodTransfer shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
    
    public CarrierServiceTransfer getCarrierService() {
        return carrierService;
    }
    
    public void setCarrierService(CarrierServiceTransfer carrierService) {
        this.carrierService = carrierService;
    }
    
}
