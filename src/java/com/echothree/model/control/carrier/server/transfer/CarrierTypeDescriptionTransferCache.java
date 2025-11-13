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

import com.echothree.model.control.carrier.common.transfer.CarrierTypeDescriptionTransfer;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.data.carrier.server.entity.CarrierTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CarrierTypeDescriptionTransferCache
        extends BaseCarrierDescriptionTransferCache<CarrierTypeDescription, CarrierTypeDescriptionTransfer> {
    
    /** Creates a new instance of CarrierTypeDescriptionTransferCache */
    public CarrierTypeDescriptionTransferCache(CarrierControl carrierControl) {
        super(carrierControl);
    }
    
    public CarrierTypeDescriptionTransfer getCarrierTypeDescriptionTransfer(CarrierTypeDescription carrierTypeDescription) {
        var carrierTypeDescriptionTransfer = get(carrierTypeDescription);
        
        if(carrierTypeDescriptionTransfer == null) {
            var carrierTypeTransfer = carrierControl.getCarrierTypeTransfer(userVisit, carrierTypeDescription.getCarrierType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, carrierTypeDescription.getLanguage());
            var description = carrierTypeDescription.getDescription();
            
            carrierTypeDescriptionTransfer = new CarrierTypeDescriptionTransfer(languageTransfer, carrierTypeTransfer, description);
            put(userVisit, carrierTypeDescription, carrierTypeDescriptionTransfer);
        }
        
        return carrierTypeDescriptionTransfer;
    }
    
}
