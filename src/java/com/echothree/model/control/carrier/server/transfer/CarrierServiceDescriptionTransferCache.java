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

package com.echothree.model.control.carrier.server.transfer;

import com.echothree.model.control.carrier.common.transfer.CarrierServiceDescriptionTransfer;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.data.carrier.server.entity.CarrierServiceDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class CarrierServiceDescriptionTransferCache
        extends BaseCarrierDescriptionTransferCache<CarrierServiceDescription, CarrierServiceDescriptionTransfer> {

    CarrierControl carrierControl = Session.getModelController(CarrierControl.class);

    /** Creates a new instance of CarrierServiceDescriptionTransferCache */
    protected CarrierServiceDescriptionTransferCache() {
        super();
    }
    
    public CarrierServiceDescriptionTransfer getCarrierServiceDescriptionTransfer(UserVisit userVisit, CarrierServiceDescription carrierServiceDescription) {
        var carrierServiceDescriptionTransfer = get(carrierServiceDescription);
        
        if(carrierServiceDescriptionTransfer == null) {
            var carrierServiceTransfer = carrierControl.getCarrierServiceTransfer(userVisit, carrierServiceDescription.getCarrierService());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, carrierServiceDescription.getLanguage());
            var description = carrierServiceDescription.getDescription();
            
            carrierServiceDescriptionTransfer = new CarrierServiceDescriptionTransfer(languageTransfer, carrierServiceTransfer,
                    description);
            put(userVisit, carrierServiceDescription, carrierServiceDescriptionTransfer);
        }
        
        return carrierServiceDescriptionTransfer;
    }
    
}
