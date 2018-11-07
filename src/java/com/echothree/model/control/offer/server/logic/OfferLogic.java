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

package com.echothree.model.control.offer.server.logic;

import com.echothree.model.control.content.server.logic.ContentLogic;
import com.echothree.model.control.offer.common.exception.UnknownOfferItemException;
import com.echothree.model.control.offer.common.exception.UnknownOfferItemPriceException;
import com.echothree.model.control.offer.common.exception.UnknownOfferNameException;
import com.echothree.model.control.offer.server.OfferControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemFixedPrice;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.OfferItemVariablePrice;
import com.echothree.model.data.offer.server.value.OfferItemFixedPriceValue;
import com.echothree.model.data.offer.server.value.OfferItemVariablePriceValue;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class OfferLogic
        extends BaseLogic {

    private OfferLogic() {
        super();
    }

    private static class OfferLogicHolder {

        static OfferLogic instance = new OfferLogic();
    }

    public static OfferLogic getInstance() {
        return OfferLogicHolder.instance;
    }
    
    public Offer getOfferByName(final ExecutionErrorAccumulator eea, final String offerName) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        Offer offer = offerControl.getOfferByName(offerName);

        if(offer == null) {
            handleExecutionError(UnknownOfferNameException.class, eea, ExecutionErrors.UnknownOfferName.name(), offerName);
        }

        return offer;
    }

    public OfferItem createOfferItem(final Offer offer, final Item item, final BasePK createdBy) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);

        return offerControl.createOfferItem(offer, item, createdBy);
    }

    public void deleteOfferItem(final OfferItem offerItem, final BasePK deletedBy) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);

        offerControl.deleteOfferItem(offerItem, deletedBy);
    }

    public OfferItemPrice createOfferItemPrice(final OfferItem offerItem, final InventoryCondition inventoryCondition,
            final UnitOfMeasureType unitOfMeasureType, final Currency currency, final BasePK createdBy) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);

        return offerControl.createOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency, createdBy);
    }

    public OfferItemPrice getOfferItemPrice(final ExecutionErrorAccumulator eea, final Offer offer, final Item item,
            final InventoryCondition inventoryCondition, final UnitOfMeasureType unitOfMeasureType, final Currency currency) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);
        OfferItemPrice offerItemPrice = null;
        OfferItem offerItem = offerControl.getOfferItem(offer, item);
        
        if(offerItem != null) {
            offerItemPrice = offerControl.getOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency);
            
            if(offerItemPrice == null) {
                handleExecutionError(UnknownOfferItemPriceException.class, eea, ExecutionErrors.UnknownOfferItemPrice.name(), offer.getLastDetail().getOfferName(),
                        item.getLastDetail().getItemName(), inventoryCondition.getLastDetail().getInventoryConditionName(),
                        unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName(), currency.getCurrencyIsoName());
            }
        } else {
            handleExecutionError(UnknownOfferItemException.class, eea, ExecutionErrors.UnknownOfferItem.name(), offer.getLastDetail().getOfferName(),
                    item.getLastDetail().getItemName());
        }
        
        return offerItemPrice;
    }
    
    public void deleteOfferItemPrice(final OfferItemPrice offerItemPrice, final BasePK deletedBy) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);

        ContentLogic.getInstance().deleteContentCategoryItemByOfferItemPrice(offerItemPrice, deletedBy);

        offerControl.deleteOfferItemPrice(offerItemPrice, deletedBy);
    }

    public OfferItemFixedPrice createOfferItemFixedPrice(final OfferItemPrice offerItemPrice, final Long unitPrice, final BasePK createdBy) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);

        return offerControl.createOfferItemFixedPrice(offerItemPrice, unitPrice, createdBy);
    }

    public void updateOfferItemFixedPriceFromValue(final OfferItemFixedPriceValue offerItemFixedPriceValue, final BasePK updatedBy) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);

        OfferItemFixedPrice offerItemFixedPrice = offerControl.updateOfferItemFixedPriceFromValue(offerItemFixedPriceValue, updatedBy);

        if(offerItemFixedPrice != null) {
            ContentLogic.getInstance().updateContentCatalogItemPricesByOfferItemPrice(offerItemFixedPrice.getOfferItemPrice(), updatedBy);
        }
    }

    public void deleteOfferItemFixedPrice(final OfferItemFixedPrice offerItemFixedPrice, final BasePK deletedBy) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);

        offerControl.deleteOfferItemFixedPrice(offerItemFixedPrice, deletedBy);
    }

    public OfferItemVariablePrice createOfferItemVariablePrice(final OfferItemPrice offerItemPrice, final Long minimumUnitPrice,
            final Long maximumUnitPrice, final Long unitPriceIncrement, final BasePK createdBy) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);

        return offerControl.createOfferItemVariablePrice(offerItemPrice, minimumUnitPrice, maximumUnitPrice, unitPriceIncrement, createdBy);
    }

    public void updateOfferItemVariablePriceFromValue(final OfferItemVariablePriceValue offerItemVariablePriceValue, final BasePK updatedBy) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);

        OfferItemVariablePrice offerItemVariablePrice = offerControl.updateOfferItemVariablePriceFromValue(offerItemVariablePriceValue, updatedBy);

        if(offerItemVariablePrice != null) {
            ContentLogic.getInstance().updateContentCatalogItemPricesByOfferItemPrice(offerItemVariablePrice.getOfferItemPrice(), updatedBy);
        }
    }

    public void deleteOfferItemVariablePrice(final OfferItemVariablePrice offerItemVariablePrice, final BasePK deletedBy) {
        OfferControl offerControl = (OfferControl)Session.getModelController(OfferControl.class);

        offerControl.deleteOfferItemVariablePrice(offerItemVariablePrice, deletedBy);
    }

}