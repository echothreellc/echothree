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

import com.echothree.model.control.carrier.common.transfer.CarrierServiceTransfer;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.carrier.server.entity.CarrierService;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CarrierServiceTransferCache
        extends BaseCarrierTransferCache<CarrierService, CarrierServiceTransfer> {

    CarrierControl carrierControl = Session.getModelController(CarrierControl.class);
    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);
    
    /** Creates a new instance of CarrierServiceTransferCache */
    public CarrierServiceTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public CarrierServiceTransfer getCarrierServiceTransfer(UserVisit userVisit, CarrierService carrierService) {
        var carrierServiceTransfer = get(carrierService);
        
        if(carrierServiceTransfer == null) {
            var carrierServiceDetail = carrierService.getLastDetail();
            var carrier = carrierControl.getCarrierTransfer(userVisit, carrierServiceDetail.getCarrierParty());
            var carrierServiceName = carrierServiceDetail.getCarrierServiceName();
            var geoCodeSelector = carrierServiceDetail.getGeoCodeSelector();
            var geoCodeSelectorTransfer = geoCodeSelector == null? null: selectorControl.getSelectorTransfer(userVisit, geoCodeSelector);
            var itemSelector = carrierServiceDetail.getItemSelector();
            var itemSelectorTransfer = itemSelector == null? null: selectorControl.getSelectorTransfer(userVisit, itemSelector);
            var isDefault = carrierServiceDetail.getIsDefault();
            var sortOrder = carrierServiceDetail.getSortOrder();
            var description = carrierControl.getBestCarrierServiceDescription(carrierService, getLanguage(userVisit));
            
            carrierServiceTransfer = new CarrierServiceTransfer(carrier, carrierServiceName, geoCodeSelectorTransfer, itemSelectorTransfer, isDefault,
                    sortOrder, description);
            put(userVisit, carrierService, carrierServiceTransfer);
        }
        
        return carrierServiceTransfer;
    }
    
}
