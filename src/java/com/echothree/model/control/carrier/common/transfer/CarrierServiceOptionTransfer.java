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

package com.echothree.model.control.carrier.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class CarrierServiceOptionTransfer
        extends BaseTransfer {
    
    private CarrierServiceTransfer carrierService;
    private CarrierOptionTransfer carrierOption;
    
    /** Creates a new instance of CarrierServiceOptionTransfer */
    public CarrierServiceOptionTransfer(CarrierServiceTransfer carrierService, CarrierOptionTransfer carrierOption) {
        this.carrierService = carrierService;
        this.carrierOption = carrierOption;
    }
    
    public CarrierServiceTransfer getCarrierService() {
        return carrierService;
    }
    
    public void setCarrierService(CarrierServiceTransfer carrierService) {
        this.carrierService = carrierService;
    }
    
    public CarrierOptionTransfer getCarrierOption() {
        return carrierOption;
    }
    
    public void setCarrierOption(CarrierOptionTransfer carrierOption) {
        this.carrierOption = carrierOption;
    }
    
}
