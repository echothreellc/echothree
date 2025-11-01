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

package com.echothree.model.control.offer.server.logic;

import com.echothree.control.user.offer.common.spec.OfferItemUniversalSpec;
import com.echothree.model.control.accounting.server.logic.CurrencyLogic;
import com.echothree.model.control.content.server.logic.ContentLogic;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.inventory.server.logic.InventoryConditionLogic;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.item.common.exception.MissingMaximumUnitPriceException;
import com.echothree.model.control.item.common.exception.MissingMinimumUnitPriceException;
import com.echothree.model.control.item.common.exception.MissingUnitPriceException;
import com.echothree.model.control.item.common.exception.MissingUnitPriceIncrementException;
import com.echothree.model.control.item.common.exception.UnknownItemPriceException;
import com.echothree.model.control.item.common.exception.UnknownItemPriceTypeException;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.item.server.logic.ItemLogic;
import com.echothree.model.control.offer.common.exception.CannotManuallyCreateOfferItemPriceWhenOfferItemPriceFilterSetException;
import com.echothree.model.control.offer.common.exception.CannotManuallyCreateOfferItemWhenOfferItemSelectorSetException;
import com.echothree.model.control.offer.common.exception.CannotManuallyDeleteOfferItemPriceWhenOfferItemPriceFilterSetException;
import com.echothree.model.control.offer.common.exception.CannotManuallyDeleteOfferItemWhenOfferItemSelectorSetException;
import com.echothree.model.control.offer.common.exception.DuplicateOfferItemException;
import com.echothree.model.control.offer.common.exception.DuplicateOfferItemPriceException;
import com.echothree.model.control.offer.common.exception.InvalidItemCompanyException;
import com.echothree.model.control.offer.common.exception.UnknownOfferItemException;
import com.echothree.model.control.offer.common.exception.UnknownOfferItemPriceException;
import com.echothree.model.control.offer.server.control.OfferItemControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.server.logic.UnitOfMeasureTypeLogic;
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
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.validation.ParameterUtils;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class OfferItemLogic
        extends BaseLogic {

    protected OfferItemLogic() {
        super();
    }

    public static OfferItemLogic getInstance() {
        return CDI.current().select(OfferItemLogic.class).get();
    }

    // --------------------------------------------------------------------------------
    //   Offer Items
    // --------------------------------------------------------------------------------

    // This one is intended to be used internally to ensure any dependent actions occur.
    public OfferItem createOfferItem(final Offer offer, final Item item, final BasePK createdBy) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);

        return offerItemControl.createOfferItem(offer, item, createdBy);
    }

    // This one is intended to be used by interactive users of the application to ensure all necessary
    // validation occurs.
    public OfferItem createOfferItem(final ExecutionErrorAccumulator eea, final Offer offer, final Item item,
        final BasePK createdBy) {
        OfferItem offerItem = null;
        final var offerDetail = offer.getLastDetail();

        if(offerDetail.getOfferItemSelector() == null) {
            var partyControl = Session.getModelController(PartyControl.class);
            var partyDepartment = partyControl.getPartyDepartment(offerDetail.getDepartmentParty());
            var partyDivision = partyControl.getPartyDivision(partyDepartment.getDivisionParty());
            var partyCompany = partyControl.getPartyCompany(partyDivision.getCompanyParty());

            if(partyCompany.getParty().equals(item.getLastDetail().getCompanyParty())) {
                var offerItemControl = Session.getModelController(OfferItemControl.class);

                offerItem = offerItemControl.getOfferItem(offer, item);

                if(offerItem == null) {
                    offerItem = createOfferItem(offer, item, createdBy);
                } else {
                    handleExecutionError(DuplicateOfferItemException.class, eea, ExecutionErrors.DuplicateOfferItem.name(),
                            offerDetail.getOfferName(), item.getLastDetail().getItemName());
                }
            } else {
                handleExecutionError(InvalidItemCompanyException.class, eea, ExecutionErrors.InvalidItemCompany.name(),
                        partyCompany.getPartyCompanyName(),
                        partyControl.getPartyCompany(item.getLastDetail().getCompanyParty()).getPartyCompanyName());
            }
        } else {
            handleExecutionError(CannotManuallyCreateOfferItemWhenOfferItemSelectorSetException.class, eea,
                    ExecutionErrors.CannotManuallyCreateOfferItemWhenOfferItemSelectorSet.name(),
                    offerDetail.getOfferName());
        }

        return offerItem;
    }

    public OfferItem getOfferItem(final ExecutionErrorAccumulator eea, final Offer offer, final Item item,
            final EntityPermission entityPermission) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);
        var offerItem = offerItemControl.getOfferItem(offer, item, entityPermission);

        if(offerItem == null) {
            handleExecutionError(UnknownOfferItemException.class, eea, ExecutionErrors.UnknownOfferItem.name(),
                    offer.getLastDetail().getOfferName(), item.getLastDetail().getItemName());
        }

        return offerItem;
    }

    public OfferItem getOfferItem(final ExecutionErrorAccumulator eea, final Offer offer, final Item item) {
        return getOfferItem(eea, offer, item, EntityPermission.READ_ONLY);
    }

    public OfferItem getOfferItemForUpdate(final ExecutionErrorAccumulator eea, final Offer offer, final Item item) {
        return getOfferItem(eea, offer, item, EntityPermission.READ_WRITE);
    }

    public OfferItem getOfferItemByUniversalSpec(final ExecutionErrorAccumulator eea, final OfferItemUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        var offerName = universalSpec.getOfferName();
        var itemName = universalSpec.getItemName();
        var nameParameterCount= ParameterUtils.getInstance().countNonNullParameters(offerName, itemName);
        var possibleEntitySpecs= EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        OfferItem offerItem = null;

        if(nameParameterCount == 2 && possibleEntitySpecs == 0) {
            var offer = OfferLogic.getInstance().getOfferByName(eea, offerName);
            var item = ItemLogic.getInstance().getItemByName(eea, itemName);

            if(!eea.hasExecutionErrors()) {
                offerItem = getOfferItem(eea, offer, item);
            }
        } else if(nameParameterCount == 0 && possibleEntitySpecs == 1) {
            var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                    ComponentVendors.ECHO_THREE.name(), EntityTypes.OfferItem.name());

            if(!eea.hasExecutionErrors()) {
                var offerItemControl = Session.getModelController(OfferItemControl.class);

                offerItem = offerItemControl.getOfferItemByEntityInstance(entityInstance, entityPermission);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return offerItem;
    }

    public OfferItem getOfferItemByUniversalSpec(final ExecutionErrorAccumulator eea, final OfferItemUniversalSpec universalSpec) {
        return getOfferItemByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public OfferItem getOfferItemByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final OfferItemUniversalSpec universalSpec) {
        return getOfferItemByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public void deleteOfferItem(final OfferItem offerItem, final BasePK deletedBy) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);

        offerItemControl.deleteOfferItem(offerItem, deletedBy);
    }

    public void deleteOfferItem(final ExecutionErrorAccumulator eea, final OfferItem offerItem, final BasePK deletedBy) {
        final var offerDetail = offerItem.getOffer().getLastDetail();

        if(offerDetail.getOfferItemSelector() == null) {
            deleteOfferItem(offerItem, deletedBy);
        } else {
            handleExecutionError(CannotManuallyDeleteOfferItemWhenOfferItemSelectorSetException.class, eea,
                    ExecutionErrors.CannotManuallyDeleteOfferItemWhenOfferItemSelectorSet.name(),
                    offerDetail.getOfferName());
        }
    }

    public void deleteOfferItems(List<OfferItem> offerItems, BasePK deletedBy) {
        offerItems.forEach((offerItem) -> 
                deleteOfferItem(offerItem, deletedBy)
        );
    }

    public void deleteOfferItemsByOffer(Offer offer, BasePK deletedBy) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);

        deleteOfferItems(offerItemControl.getOfferItemsByOfferForUpdate(offer), deletedBy);
    }

    public void deleteOfferItemsByOffers(List<Offer> offers, BasePK deletedBy) {
        offers.forEach((offer) -> 
                deleteOfferItemsByOffer(offer, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Offer Item Prices
    // --------------------------------------------------------------------------------

    public OfferItemPrice createOfferItemPrice(final OfferItem offerItem, final InventoryCondition inventoryCondition,
            final UnitOfMeasureType unitOfMeasureType, final Currency currency, final BasePK createdBy) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);

        return offerItemControl.createOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency, createdBy);
    }

    public void createOfferItemPrice(final ExecutionErrorAccumulator eea, final String offerName, final String itemName, final String inventoryConditionName,
            final String unitOfMeasureTypeName, final String currencyIsoName, final String strUnitPrice, final String strMinimumUnitPrice,
            final String strMaximumUnitPrice, final String strUnitPriceIncrement, final BasePK createdBy) {
        var offer = OfferLogic.getInstance().getOfferByName(eea, offerName);

        if(eea == null || !eea.hasExecutionErrors()) {
            final var offerDetail = offer.getLastDetail();

            if(offerDetail.getOfferItemPriceFilter() == null) {
                var item = ItemLogic.getInstance().getItemByName(eea, itemName);
                var inventoryCondition = InventoryConditionLogic.getInstance().getInventoryConditionByName(eea, inventoryConditionName);
                var currency = CurrencyLogic.getInstance().getCurrencyByName(eea, currencyIsoName);

                if(eea == null || !eea.hasExecutionErrors()) {
                    var itemDetail = item.getLastDetail();
                    var unitOfMeasureKind = itemDetail.getUnitOfMeasureKind();
                    var unitOfMeasureType = UnitOfMeasureTypeLogic.getInstance().getUnitOfMeasureTypeByName(eea,
                            unitOfMeasureKind, unitOfMeasureTypeName);

                    if(eea == null || !eea.hasExecutionErrors()) {
                        var offerItemControl = Session.getModelController(OfferItemControl.class);
                        var offerItem = offerItemControl.getOfferItem(offer, item);

                        if(offerItem != null) {
                            var itemControl = Session.getModelController(ItemControl.class);
                            var itemPrice = itemControl.getItemPrice(item, inventoryCondition, unitOfMeasureType, currency);

                            if(itemPrice != null) {
                                var offerItemPrice = offerItemControl.getOfferItemPrice(offerItem, inventoryCondition,
                                        unitOfMeasureType, currency);

                                if(offerItemPrice == null) {
                                    var itemPriceTypeName = itemDetail.getItemPriceType().getItemPriceTypeName();

                                    if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
                                        if(strUnitPrice != null) {
                                            var unitPrice = Long.valueOf(strUnitPrice);

                                            offerItemPrice = OfferItemLogic.getInstance().createOfferItemPrice(offerItem, inventoryCondition,
                                                    unitOfMeasureType, currency, createdBy);
                                            OfferItemLogic.getInstance().createOfferItemFixedPrice(offerItemPrice, unitPrice, createdBy);
                                        } else {
                                            handleExecutionError(MissingUnitPriceException.class, eea, ExecutionErrors.MissingUnitPrice.name());
                                        }
                                    } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
                                        Long minimumUnitPrice = null;
                                        Long maximumUnitPrice = null;
                                        Long unitPriceIncrement = null;

                                        if(strMinimumUnitPrice != null) {
                                            minimumUnitPrice = Long.valueOf(strMinimumUnitPrice);
                                        } else {
                                            handleExecutionError(MissingMinimumUnitPriceException.class, eea, ExecutionErrors.MissingMinimumUnitPrice.name());
                                        }

                                        if(strMaximumUnitPrice != null) {
                                            maximumUnitPrice = Long.valueOf(strMaximumUnitPrice);
                                        } else {
                                            handleExecutionError(MissingMaximumUnitPriceException.class, eea, ExecutionErrors.MissingMaximumUnitPrice.name());
                                        }

                                        if(strUnitPriceIncrement != null) {
                                            unitPriceIncrement = Long.valueOf(strUnitPriceIncrement);
                                        } else {
                                            handleExecutionError(MissingUnitPriceIncrementException.class, eea, ExecutionErrors.MissingUnitPriceIncrement.name());
                                        }

                                        if(minimumUnitPrice != null && maximumUnitPrice != null && unitPriceIncrement != null) {
                                            offerItemPrice = OfferItemLogic.getInstance().createOfferItemPrice(offerItem, inventoryCondition,
                                                    unitOfMeasureType, currency, createdBy);
                                            OfferItemLogic.getInstance().createOfferItemVariablePrice(offerItemPrice, minimumUnitPrice, maximumUnitPrice,
                                                    unitPriceIncrement, createdBy);
                                        }
                                    } else {
                                        handleExecutionError(UnknownItemPriceTypeException.class, eea, ExecutionErrors.UnknownItemPriceType.name());
                                    }
                                } else {
                                    handleExecutionError(DuplicateOfferItemPriceException.class, eea, ExecutionErrors.DuplicateOfferItemPrice.name());
                                }
                            } else {
                                handleExecutionError(UnknownItemPriceException.class, eea, ExecutionErrors.UnknownItemPrice.name());
                            }
                        } else {
                            handleExecutionError(UnknownOfferItemException.class, eea, ExecutionErrors.UnknownOfferItem.name(), offerName, itemName);
                        }
                    }
                }
            } else {
                handleExecutionError(CannotManuallyCreateOfferItemPriceWhenOfferItemPriceFilterSetException.class, eea,
                        ExecutionErrors.CannotManuallyCreateOfferItemPriceWhenOfferItemPriceFilterSet.name(),
                        offerDetail.getOfferName());
            }
        }
    }

    public OfferItemPrice getOfferItemPrice(final ExecutionErrorAccumulator eea, final Offer offer, final Item item,
            final InventoryCondition inventoryCondition, final UnitOfMeasureType unitOfMeasureType, final Currency currency) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);
        var offerItem = offerItemControl.getOfferItem(offer, item);
        OfferItemPrice offerItemPrice = null;

        if(offerItem != null) {
            offerItemPrice = offerItemControl.getOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency);

            if(offerItemPrice == null) {
                handleExecutionError(UnknownOfferItemPriceException.class, eea, ExecutionErrors.UnknownOfferItemPrice.name(), offer.getLastDetail().getOfferName(),
                        item.getLastDetail().getItemName(), inventoryCondition.getLastDetail().getInventoryConditionName(),
                        unitOfMeasureType.getLastDetail().getUnitOfMeasureTypeName(), currency.getCurrencyIsoName());
            }
        } else {
            handleExecutionError(UnknownOfferItemException.class, eea, ExecutionErrors.UnknownOfferItem.name(),
                    offer.getLastDetail().getOfferName(), item.getLastDetail().getItemName());
        }

        return offerItemPrice;
    }

    // This one is intended to be used internally to ensure any dependent actions occur.
    public void deleteOfferItemPrice(final OfferItemPrice offerItemPrice, final BasePK deletedBy) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);
        var offerItem = offerItemPrice.getOfferItemForUpdate();
        var item = offerItem.getItem();

        ContentLogic.getInstance().deleteContentCategoryItemByOfferItemPrice(offerItemPrice, deletedBy);

        offerItemControl.deleteOfferItemPrice(offerItemPrice, deletedBy);

        // If all OfferItemPrices have been deleted, delete the OfferItem as well.
        if(offerItemControl.countOfferItemPricesByItem(item) == 0) {
            deleteOfferItem(offerItem, deletedBy);
        }
    }

    // This one is intended to be used by interactive users of the application to ensure all necessary
    // validation occurs.
    public void deleteOfferItemPrice(final ExecutionErrorAccumulator eea, final String offerName, final String itemName,
            final String inventoryConditionName, final String unitOfMeasureTypeName, final String currencyIsoName,
            final BasePK deletedBy) {
        var offer = OfferLogic.getInstance().getOfferByName(eea, offerName);

        if(eea == null || !eea.hasExecutionErrors()) {
            final var offerDetail = offer.getLastDetail();

            if(offerDetail.getOfferItemPriceFilter() == null) {
                var item = ItemLogic.getInstance().getItemByName(eea, itemName);
                var inventoryCondition = InventoryConditionLogic.getInstance().getInventoryConditionByName(eea, inventoryConditionName);
                var currency = CurrencyLogic.getInstance().getCurrencyByName(eea, currencyIsoName);

                if(eea == null || !eea.hasExecutionErrors()) {
                    var unitOfMeasureKind = item.getLastDetail().getUnitOfMeasureKind();
                    var unitOfMeasureType = UnitOfMeasureTypeLogic.getInstance().getUnitOfMeasureTypeByName(eea,
                            unitOfMeasureKind, unitOfMeasureTypeName);

                    if(eea == null || !eea.hasExecutionErrors()) {
                        var offerItemControl = Session.getModelController(OfferItemControl.class);
                        var offerItem = offerItemControl.getOfferItem(offer, item);

                        if(offerItem != null) {
                            var offerItemPrice = offerItemControl.getOfferItemPriceForUpdate(offerItem, inventoryCondition,
                                    unitOfMeasureType, currency);

                            if(offerItemPrice != null) {
                                deleteOfferItemPrice(offerItemPrice, deletedBy);
                            } else {
                                handleExecutionError(UnknownOfferItemPriceException.class, eea, ExecutionErrors.UnknownOfferItemPrice.name(),
                                        offerName, itemName, inventoryConditionName, unitOfMeasureTypeName, currencyIsoName);
                            }
                        } else {
                            handleExecutionError(UnknownOfferItemException.class, eea, ExecutionErrors.UnknownOfferItem.name(),
                                    offerName, itemName);
                        }
                    }
                }
            } else {
                handleExecutionError(CannotManuallyDeleteOfferItemPriceWhenOfferItemPriceFilterSetException.class, eea,
                        ExecutionErrors.CannotManuallyDeleteOfferItemPriceWhenOfferItemPriceFilterSet.name(),
                        offerDetail.getOfferName());
            }
        }
    }

    public void deleteOfferItemPrices(List<OfferItemPrice> offerItemPrices, BasePK deletedBy) {
        offerItemPrices.forEach((offerItemPrice) -> 
                deleteOfferItemPrice(offerItemPrice, deletedBy)
        );
    }

    public void deleteOfferItemPricesByOfferItem(OfferItem offerItem, BasePK deletedBy) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);

        deleteOfferItemPrices(offerItemControl.getOfferItemPricesByOfferItemForUpdate(offerItem), deletedBy);
    }

    public void deleteOfferItemPricesByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);

        deleteOfferItemPrices(offerItemControl.getOfferItemPricesByItemAndUnitOfMeasureTypeForUpdate(item, unitOfMeasureType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Offer Item Fixed Prices
    // --------------------------------------------------------------------------------

    public OfferItemFixedPrice createOfferItemFixedPrice(final OfferItemPrice offerItemPrice, final Long unitPrice, final BasePK createdBy) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);

        return offerItemControl.createOfferItemFixedPrice(offerItemPrice, unitPrice, createdBy);
    }

    public void updateOfferItemFixedPriceFromValue(final OfferItemFixedPriceValue offerItemFixedPriceValue, final BasePK updatedBy) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);
        var offerItemFixedPrice = offerItemControl.updateOfferItemFixedPriceFromValue(offerItemFixedPriceValue, updatedBy);

        if(offerItemFixedPrice != null) {
            ContentLogic.getInstance().updateContentCatalogItemPricesByOfferItemPrice(offerItemFixedPrice.getOfferItemPrice(), updatedBy);
        }
    }

    // --------------------------------------------------------------------------------
    //   Offer Item Variable Prices
    // --------------------------------------------------------------------------------

    public OfferItemVariablePrice createOfferItemVariablePrice(final OfferItemPrice offerItemPrice, final Long minimumUnitPrice,
            final Long maximumUnitPrice, final Long unitPriceIncrement, final BasePK createdBy) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);

        return offerItemControl.createOfferItemVariablePrice(offerItemPrice, minimumUnitPrice, maximumUnitPrice, unitPriceIncrement, createdBy);
    }

    public void updateOfferItemVariablePriceFromValue(final OfferItemVariablePriceValue offerItemVariablePriceValue, final BasePK updatedBy) {
        var offerItemControl = Session.getModelController(OfferItemControl.class);
        var offerItemVariablePrice = offerItemControl.updateOfferItemVariablePriceFromValue(offerItemVariablePriceValue, updatedBy);

        if(offerItemVariablePrice != null) {
            ContentLogic.getInstance().updateContentCatalogItemPricesByOfferItemPrice(offerItemVariablePrice.getOfferItemPrice(), updatedBy);
        }
    }

}
