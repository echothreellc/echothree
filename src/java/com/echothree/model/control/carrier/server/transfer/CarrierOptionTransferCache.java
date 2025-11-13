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

import com.echothree.model.control.carrier.common.transfer.CarrierOptionTransfer;
import com.echothree.model.control.carrier.server.control.CarrierControl;
import com.echothree.model.control.selector.server.control.SelectorControl;
import com.echothree.model.data.carrier.server.entity.CarrierOption;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CarrierOptionTransferCache
        extends BaseCarrierTransferCache<CarrierOption, CarrierOptionTransfer> {

    SelectorControl selectorControl = Session.getModelController(SelectorControl.class);

    /** Creates a new instance of CarrierOptionTransferCache */
    public CarrierOptionTransferCache(CarrierControl carrierControl) {
        super(carrierControl);
        
        setIncludeEntityInstance(true);
    }
    
    public CarrierOptionTransfer getCarrierOptionTransfer(UserVisit userVisit, CarrierOption carrierOption) {
        var carrierOptionTransfer = get(carrierOption);
        
        if(carrierOptionTransfer == null) {
            var carrierOptionDetail = carrierOption.getLastDetail();
            var carrier = carrierControl.getCarrierTransfer(userVisit, carrierOptionDetail.getCarrierParty());
            var carrierOptionName = carrierOptionDetail.getCarrierOptionName();
            var isRecommended = carrierOptionDetail.getIsRecommended();
            var isRequired = carrierOptionDetail.getIsRequired();
            var recommendedGeoCodeSelector = carrierOptionDetail.getRecommendedGeoCodeSelector();
            var recommendedGeoCodeSelectorTransfer = recommendedGeoCodeSelector == null? null: selectorControl.getSelectorTransfer(userVisit, recommendedGeoCodeSelector);
            var requiredGeoCodeSelector = carrierOptionDetail.getRequiredGeoCodeSelector();
            var requiredGeoCodeSelectorTransfer = requiredGeoCodeSelector == null? null: selectorControl.getSelectorTransfer(userVisit, requiredGeoCodeSelector);
            var recommendedItemSelector = carrierOptionDetail.getRecommendedItemSelector();
            var recommendedItemSelectorTransfer = recommendedItemSelector == null? null: selectorControl.getSelectorTransfer(userVisit, recommendedItemSelector);
            var requiredItemSelector = carrierOptionDetail.getRequiredItemSelector();
            var requiredItemSelectorTransfer = requiredItemSelector == null? null: selectorControl.getSelectorTransfer(userVisit, requiredItemSelector);
            var recommendedOrderSelector = carrierOptionDetail.getRecommendedOrderSelector();
            var recommendedOrderSelectorTransfer = recommendedOrderSelector == null? null: selectorControl.getSelectorTransfer(userVisit, recommendedOrderSelector);
            var requiredOrderSelector = carrierOptionDetail.getRequiredOrderSelector();
            var requiredOrderSelectorTransfer = requiredOrderSelector == null? null: selectorControl.getSelectorTransfer(userVisit, requiredOrderSelector);
            var recommendedShipmentSelector = carrierOptionDetail.getRecommendedShipmentSelector();
            var recommendedShipmentSelectorTransfer = recommendedShipmentSelector == null? null: selectorControl.getSelectorTransfer(userVisit, recommendedShipmentSelector);
            var requiredShipmentSelector = carrierOptionDetail.getRequiredShipmentSelector();
            var requiredShipmentSelectorTransfer = requiredShipmentSelector == null? null: selectorControl.getSelectorTransfer(userVisit, requiredShipmentSelector);
            var isDefault = carrierOptionDetail.getIsDefault();
            var sortOrder = carrierOptionDetail.getSortOrder();
            var description = carrierControl.getBestCarrierOptionDescription(carrierOption, getLanguage(userVisit));
            
            carrierOptionTransfer = new CarrierOptionTransfer(carrier, carrierOptionName, isRecommended, isRequired, recommendedGeoCodeSelectorTransfer,
                    requiredGeoCodeSelectorTransfer, recommendedItemSelectorTransfer, requiredItemSelectorTransfer, recommendedOrderSelectorTransfer,
                    requiredOrderSelectorTransfer, recommendedShipmentSelectorTransfer, requiredShipmentSelectorTransfer, isDefault, sortOrder, description);
            put(userVisit, carrierOption, carrierOptionTransfer);
        }
        
        return carrierOptionTransfer;
    }
    
}
