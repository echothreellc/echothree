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

package com.echothree.model.control.carrier.server.transfer;

import com.echothree.model.control.carrier.remote.transfer.CarrierOptionTransfer;
import com.echothree.model.control.carrier.remote.transfer.CarrierTransfer;
import com.echothree.model.control.carrier.server.CarrierControl;
import com.echothree.model.control.selector.remote.transfer.SelectorTransfer;
import com.echothree.model.control.selector.server.SelectorControl;
import com.echothree.model.data.carrier.server.entity.CarrierOption;
import com.echothree.model.data.carrier.server.entity.CarrierOptionDetail;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CarrierOptionTransferCache
        extends BaseCarrierTransferCache<CarrierOption, CarrierOptionTransfer> {

    SelectorControl selectorControl = (SelectorControl)Session.getModelController(SelectorControl.class);

    /** Creates a new instance of CarrierOptionTransferCache */
    public CarrierOptionTransferCache(UserVisit userVisit, CarrierControl carrierControl) {
        super(userVisit, carrierControl);
        
        setIncludeEntityInstance(true);
    }
    
    public CarrierOptionTransfer getCarrierOptionTransfer(CarrierOption carrierOption) {
        CarrierOptionTransfer carrierOptionTransfer = get(carrierOption);
        
        if(carrierOptionTransfer == null) {
            CarrierOptionDetail carrierOptionDetail = carrierOption.getLastDetail();
            CarrierTransfer carrier = carrierControl.getCarrierTransfer(userVisit, carrierOptionDetail.getCarrierParty());
            String carrierOptionName = carrierOptionDetail.getCarrierOptionName();
            Boolean isRecommended = carrierOptionDetail.getIsRecommended();
            Boolean isRequired = carrierOptionDetail.getIsRequired();
            Selector recommendedGeoCodeSelector = carrierOptionDetail.getRecommendedGeoCodeSelector();
            SelectorTransfer recommendedGeoCodeSelectorTransfer = recommendedGeoCodeSelector == null? null: selectorControl.getSelectorTransfer(userVisit, recommendedGeoCodeSelector);
            Selector requiredGeoCodeSelector = carrierOptionDetail.getRequiredGeoCodeSelector();
            SelectorTransfer requiredGeoCodeSelectorTransfer = requiredGeoCodeSelector == null? null: selectorControl.getSelectorTransfer(userVisit, requiredGeoCodeSelector);
            Selector recommendedItemSelector = carrierOptionDetail.getRecommendedItemSelector();
            SelectorTransfer recommendedItemSelectorTransfer = recommendedItemSelector == null? null: selectorControl.getSelectorTransfer(userVisit, recommendedItemSelector);
            Selector requiredItemSelector = carrierOptionDetail.getRequiredItemSelector();
            SelectorTransfer requiredItemSelectorTransfer = requiredItemSelector == null? null: selectorControl.getSelectorTransfer(userVisit, requiredItemSelector);
            Selector recommendedOrderSelector = carrierOptionDetail.getRecommendedOrderSelector();
            SelectorTransfer recommendedOrderSelectorTransfer = recommendedOrderSelector == null? null: selectorControl.getSelectorTransfer(userVisit, recommendedOrderSelector);
            Selector requiredOrderSelector = carrierOptionDetail.getRequiredOrderSelector();
            SelectorTransfer requiredOrderSelectorTransfer = requiredOrderSelector == null? null: selectorControl.getSelectorTransfer(userVisit, requiredOrderSelector);
            Selector recommendedShipmentSelector = carrierOptionDetail.getRecommendedShipmentSelector();
            SelectorTransfer recommendedShipmentSelectorTransfer = recommendedShipmentSelector == null? null: selectorControl.getSelectorTransfer(userVisit, recommendedShipmentSelector);
            Selector requiredShipmentSelector = carrierOptionDetail.getRequiredShipmentSelector();
            SelectorTransfer requiredShipmentSelectorTransfer = requiredShipmentSelector == null? null: selectorControl.getSelectorTransfer(userVisit, requiredShipmentSelector);
            Boolean isDefault = carrierOptionDetail.getIsDefault();
            Integer sortOrder = carrierOptionDetail.getSortOrder();
            String description = carrierControl.getBestCarrierOptionDescription(carrierOption, getLanguage());
            
            carrierOptionTransfer = new CarrierOptionTransfer(carrier, carrierOptionName, isRecommended, isRequired, recommendedGeoCodeSelectorTransfer,
                    requiredGeoCodeSelectorTransfer, recommendedItemSelectorTransfer, requiredItemSelectorTransfer, recommendedOrderSelectorTransfer,
                    requiredOrderSelectorTransfer, recommendedShipmentSelectorTransfer, requiredShipmentSelectorTransfer, isDefault, sortOrder, description);
            put(carrierOption, carrierOptionTransfer);
        }
        
        return carrierOptionTransfer;
    }
    
}
