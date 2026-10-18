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

package com.echothree.model.control.offer.server.logic;

import com.echothree.control.user.offer.common.spec.OfferItemPriceSpec;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.inventory.server.logic.InventoryConditionLogic;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.offer.common.exception.UnknownOfferItemException;
import com.echothree.model.control.offer.common.exception.UnknownOfferItemPriceException;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class OfferItemPriceLogic
        extends BaseLogic {

    @Inject
    OfferItemControl offerItemControl;

    @Inject
    CurrencyLogic currencyLogic;

    @Inject
    InventoryConditionLogic inventoryConditionLogic;

    @Inject
    ItemLogic itemLogic;

    @Inject
    OfferLogic offerLogic;

    @Inject
    UnitOfMeasureTypeLogic unitOfMeasureTypeLogic;

    protected OfferItemPriceLogic() {
        super();
    }

    public OfferItemPrice getOfferItemPriceBySpec(final ExecutionErrorAccumulator eea,
            final OfferItemPriceSpec spec, final EntityPermission entityPermission) {
        OfferItemPrice offerItemPrice = null;
        var offerName = spec.getOfferName();
        var offer = offerLogic.getOfferByName(eea, offerName);

        if(!hasExecutionErrors(eea)) {
            var itemName = spec.getItemName();
            var inventoryConditionName = spec.getInventoryConditionName();
            var unitOfMeasureTypeName = spec.getUnitOfMeasureTypeName();
            var currencyIsoName = spec.getCurrencyIsoName();
            var item = itemLogic.getItemByName(eea, itemName);
            var inventoryCondition = inventoryConditionLogic.getInventoryConditionByName(eea, inventoryConditionName);
            var currency = currencyLogic.getCurrencyByName(eea, currencyIsoName);

            if(!hasExecutionErrors(eea)) {
                var offerItem = offerItemControl.getOfferItem(offer, item);

                if(offerItem != null) {
                    var unitOfMeasureType = unitOfMeasureTypeLogic.getUnitOfMeasureTypeByName(eea,
                            item.getLastDetail().getUnitOfMeasureKind(), unitOfMeasureTypeName);

                    if(!hasExecutionErrors(eea)) {
                        offerItemPrice = offerItemControl.getOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType,
                                currency, entityPermission);

                        if(offerItemPrice == null) {
                            handleExecutionError(UnknownOfferItemPriceException.class, eea, ExecutionErrors.UnknownOfferItemPrice.name(), offerName, itemName,
                                    inventoryConditionName, unitOfMeasureTypeName, currencyIsoName);
                        }
                    }
                } else {
                    handleExecutionError(UnknownOfferItemException.class, eea, ExecutionErrors.UnknownOfferItem.name(), offerName, itemName);
                }
            }
        }

        return offerItemPrice;
    }

}
