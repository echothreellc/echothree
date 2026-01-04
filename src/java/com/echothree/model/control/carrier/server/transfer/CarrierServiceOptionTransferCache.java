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

package com.echothree.model.control.carrier.server.transfer;

import com.echothree.model.control.carrier.common.transfer.CarrierServiceOptionTransfer;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.data.carrier.server.entity.CarrierServiceOption;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CarrierServiceOptionTransferCache
        extends BaseCarrierTransferCache<CarrierServiceOption, CarrierServiceOptionTransfer> {

    CarrierControl carrierControl = Session.getModelController(CarrierControl.class);

    /** Creates a new instance of CarrierServiceOptionTransferCache */
    protected CarrierServiceOptionTransferCache() {
        super();
    }
    
    public CarrierServiceOptionTransfer getCarrierServiceOptionTransfer(UserVisit userVisit, CarrierServiceOption carrierServiceOption) {
        var carrierServiceOptionTransfer = get(carrierServiceOption);
        
        if(carrierServiceOptionTransfer == null) {
            var carrierService = carrierControl.getCarrierServiceTransfer(userVisit, carrierServiceOption.getCarrierService());
            var carrierOption = carrierControl.getCarrierOptionTransfer(userVisit, carrierServiceOption.getCarrierOption());
            
            carrierServiceOptionTransfer = new CarrierServiceOptionTransfer(carrierService, carrierOption);
            put(userVisit, carrierServiceOption, carrierServiceOptionTransfer);
        }
        
        return carrierServiceOptionTransfer;
    }
    
}
