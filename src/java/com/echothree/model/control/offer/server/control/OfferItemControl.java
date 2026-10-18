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

package com.echothree.model.control.offer.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.offer.common.transfer.OfferItemPriceTransfer;
import com.echothree.model.control.offer.common.transfer.OfferItemTransfer;
import com.echothree.model.control.offer.server.logic.OfferItemLogic;
import com.echothree.model.control.offer.server.transfer.OfferItemPriceTransferCache;
import com.echothree.model.control.offer.server.transfer.OfferItemTransferCache;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.offer.common.pk.OfferItemPK;
import com.echothree.model.data.offer.server.entity.Offer;
import com.echothree.model.data.offer.server.entity.OfferItem;
import com.echothree.model.data.offer.server.entity.OfferItemFixedPrice;
import com.echothree.model.data.offer.server.entity.OfferItemPrice;
import com.echothree.model.data.offer.server.entity.OfferItemVariablePrice;
import com.echothree.model.data.offer.server.factory.OfferItemFactory;
import com.echothree.model.data.offer.server.factory.OfferItemFixedPriceFactory;
import com.echothree.model.data.offer.server.factory.OfferItemPriceFactory;
import com.echothree.model.data.offer.server.factory.OfferItemVariablePriceFactory;
import com.echothree.model.data.offer.server.value.OfferItemFixedPriceValue;
import com.echothree.model.data.offer.server.value.OfferItemVariablePriceValue;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_CONDITIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.item.ItemForeignKeys.ITEMS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFERS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_ITEMS_ITEM_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_ITEMS_OFFER_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_ITEM_PRICES_CURRENCY_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_ITEM_PRICES_INVENTORY_CONDITION_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_ITEM_PRICES_OFFER_ITEM_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.OFFER_ITEM_PRICES_UNIT_OF_MEASURE_TYPE_FK;
import static com.echothree.model.jooq.server.keys.uom.UomForeignKeys.UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.accounting.Currencies.Currencies;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionDetails.InventoryConditionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditions.InventoryConditions;
import static com.echothree.model.jooq.server.tables.item.ItemDetails.ItemDetails;
import static com.echothree.model.jooq.server.tables.item.Items.Items;
import static com.echothree.model.jooq.server.tables.offer.OfferDetails.OfferDetails;
import static com.echothree.model.jooq.server.tables.offer.OfferItemFixedPrices.OfferItemFixedPrices;
import static com.echothree.model.jooq.server.tables.offer.OfferItemPrices.OfferItemPrices;
import static com.echothree.model.jooq.server.tables.offer.OfferItemVariablePrices.OfferItemVariablePrices;
import static com.echothree.model.jooq.server.tables.offer.OfferItems.OfferItems;
import static com.echothree.model.jooq.server.tables.offer.Offers.Offers;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypeDetails.UnitOfMeasureTypeDetails;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypes.UnitOfMeasureTypes;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.transfer.HistoryTransfer;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class OfferItemControl
        extends BaseModelControl {

    @Inject
    private OfferItemLogic offerItemLogic;

    /** Creates a new instance of OfferItemControl */
    protected OfferItemControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Offer Items
    // --------------------------------------------------------------------------------

    @Inject
    private OfferItemFactory offerItemFactory;

    @Inject
    private OfferItemTransferCache offerItemTransferCache;

    /** Use the function in OfferLogic instead. */
    public OfferItem createOfferItem(Offer offer, Item item, BasePK createdBy) {
        var offerItem = offerItemFactory.create(offer, item, session.getStartTime(), Session.MAX_TIME);

        sendEvent(offerItem.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return offerItem;
    }

    public long countOfferItemsByOffer(Offer offer) {
        return session.getDslContext()
                .selectCount()
                .from(OfferItems)
                .where(OfferItems.OFFER.eq(offer.getPrimaryKey()),
                        OfferItems.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOfferItemsByItem(Item item) {
        return session.getDslContext()
                .selectCount()
                .from(OfferItems)
                .where(OfferItems.ITEM.eq(item.getPrimaryKey()),
                        OfferItems.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.OfferItem */
    public OfferItem getOfferItemByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new OfferItemPK(entityInstance.getEntityUniqueId());
        var offerItem = offerItemFactory.getEntityFromPK(entityPermission, pk);

        return offerItem;
    }

    public OfferItem getOfferItemByEntityInstance(EntityInstance entityInstance) {
        return getOfferItemByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public OfferItem getOfferItemByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getOfferItemByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public OfferItem getOfferItem(Offer offer, Item item, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferItems.fields())
                .from(OfferItems)
                .where(OfferItems.OFFER.eq(offer.getPrimaryKey()),
                        OfferItems.ITEM.eq(item.getPrimaryKey()),
                        OfferItems.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerItemFactory.getEntityFromQuery(entityPermission, query);
    }

    public OfferItem getOfferItem(Offer offer, Item item) {
        return getOfferItem(offer, item, EntityPermission.READ_ONLY);
    }

    public OfferItem getOfferItemForUpdate(Offer offer, Item item) {
        return getOfferItem(offer, item, EntityPermission.READ_WRITE);
    }

    private List<OfferItem> getOfferItemsByOffer(Offer offer, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferItems.fields())
                    .from(OfferItems)
                    .join(Items).onKey(OFFER_ITEMS_ITEM_FK)
                    .join(ItemDetails).onKey(ITEMS_LAST_DETAIL_FK)
                    .where(OfferItems.OFFER.eq(offer.getPrimaryKey()),
                            OfferItems.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME),
                    OfferItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OfferItems.fields())
                    .from(OfferItems)
                    .where(OfferItems.OFFER.eq(offer.getPrimaryKey()),
                            OfferItems.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return offerItemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OfferItem> getOfferItemsByOffer(Offer offer) {
        return getOfferItemsByOffer(offer, EntityPermission.READ_ONLY);
    }

    public List<OfferItem> getOfferItemsByOfferForUpdate(Offer offer) {
        return getOfferItemsByOffer(offer, EntityPermission.READ_WRITE);
    }

    private List<OfferItem> getOfferItemsByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferItems.fields())
                    .from(OfferItems)
                    .join(Offers).onKey(OFFER_ITEMS_OFFER_FK)
                    .join(OfferDetails).onKey(OFFERS_LAST_DETAIL_FK)
                    .where(OfferItems.ITEM.eq(item.getPrimaryKey()),
                            OfferItems.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OfferDetails.SORT_ORDER, OfferDetails.OFFER_NAME),
                    OfferItemFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(OfferItems.fields())
                    .from(OfferItems)
                    .where(OfferItems.ITEM.eq(item.getPrimaryKey()),
                            OfferItems.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return offerItemFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OfferItem> getOfferItemsByItem(Item item) {
        return getOfferItemsByItem(item, EntityPermission.READ_ONLY);
    }

    public List<OfferItem> getOfferItemsByItemForUpdate(Item item) {
        return getOfferItemsByItem(item, EntityPermission.READ_WRITE);
    }

    public OfferItemTransfer getOfferItemTransfer(UserVisit userVisit, OfferItem offerItem) {
        return offerItemTransferCache.getOfferItemTransfer(userVisit, offerItem);
    }

    public List<OfferItemTransfer> getOfferItemTransfers(UserVisit userVisit, Collection<OfferItem> offerItems) {
        List<OfferItemTransfer> offerItemTransfers = new ArrayList<>(offerItems.size());

        offerItems.forEach((offerItem) ->
                offerItemTransfers.add(offerItemTransferCache.getOfferItemTransfer(userVisit, offerItem))
        );

        return offerItemTransfers;
    }

    public List<OfferItemTransfer> getOfferItemTransfersByOffer(UserVisit userVisit, Offer offer) {
        return getOfferItemTransfers(userVisit, getOfferItemsByOffer(offer));
    }

    public List<OfferItemTransfer> getOfferItemTransfersByItem(UserVisit userVisit, Item item) {
        return getOfferItemTransfers(userVisit, getOfferItemsByItem(item));
    }

    /** Use the function in OfferItemLogic instead. */
    public void deleteOfferItem(OfferItem offerItem, BasePK deletedBy) {
        offerItemLogic.deleteOfferItemPricesByOfferItem(offerItem, deletedBy);

        offerItem.setThruTime(session.getStartTime());
        offerItem.store();

        sendEvent(offerItem.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Offer Item Prices
    // --------------------------------------------------------------------------------

    @Inject
    private OfferItemPriceFactory offerItemPriceFactory;

    @Inject
    private OfferItemPriceTransferCache offerItemPriceTransferCache;

    /** Use the function in OfferItemLogic instead. */
    public OfferItemPrice createOfferItemPrice(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency, BasePK createdBy) {
        var offerItemPrice = offerItemPriceFactory.create(offerItem, inventoryCondition, unitOfMeasureType, currency,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(offerItem.getPrimaryKey(), EventTypes.MODIFY, offerItemPrice.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return offerItemPrice;
    }

    public long countOfferItemPricesByItem(Item item) {
        return session.getDslContext()
                .selectCount()
                .from(OfferItems)
                .join(OfferItemPrices).onKey(OFFER_ITEM_PRICES_OFFER_ITEM_FK)
                .where(OfferItems.ITEM.eq(item.getPrimaryKey()),
                        OfferItems.THRU_TIME.eq(Session.MAX_TIME),
                        OfferItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOfferItemPricesByOfferItem(OfferItem offerItem) {
        return session.getDslContext()
                .selectCount()
                .from(OfferItemPrices)
                .where(OfferItemPrices.OFFER_ITEM.eq(offerItem.getPrimaryKey()),
                        OfferItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOfferItemPricesByInventoryCondition(InventoryCondition inventoryCondition) {
        return session.getDslContext()
                .selectCount()
                .from(OfferItemPrices)
                .where(OfferItemPrices.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        OfferItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOfferItemPricesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.getDslContext()
                .selectCount()
                .from(OfferItemPrices)
                .where(OfferItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        OfferItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countOfferItemPricesByCurrency(Currency currency) {
        return session.getDslContext()
                .selectCount()
                .from(OfferItemPrices)
                .where(OfferItemPrices.CURRENCY.eq(currency.getPrimaryKey()),
                        OfferItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<OfferItemPrice> getOfferItemPricesByOfferItem(OfferItem offerItem, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferItemPrices.fields())
                .from(OfferItemPrices)
                .where(OfferItemPrices.OFFER_ITEM.eq(offerItem.getPrimaryKey()),
                        OfferItemPrices.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferItemPrices.fields())
                    .from(OfferItemPrices)
                    .join(InventoryConditions).onKey(OFFER_ITEM_PRICES_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(UnitOfMeasureTypes).onKey(OFFER_ITEM_PRICES_UNIT_OF_MEASURE_TYPE_FK)
                    .join(UnitOfMeasureTypeDetails).onKey(UNIT_OF_MEASURE_TYPES_LAST_DETAIL_FK)
                    .join(Currencies).onKey(OFFER_ITEM_PRICES_CURRENCY_FK)
                    .where(OfferItemPrices.OFFER_ITEM.eq(offerItem.getPrimaryKey()),
                            OfferItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    OfferItemPriceFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerItemPriceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OfferItemPrice> getOfferItemPricesByOfferItem(OfferItem offerItem) {
        return getOfferItemPricesByOfferItem(offerItem, EntityPermission.READ_ONLY);
    }

    public List<OfferItemPrice> getOfferItemPricesByOfferItemForUpdate(OfferItem offerItem) {
        return getOfferItemPricesByOfferItem(offerItem, EntityPermission.READ_WRITE);
    }

    private List<OfferItemPrice> getOfferItemPricesByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferItemPrices.fields())
                .from(OfferItemPrices)
                .join(OfferItems).onKey(OFFER_ITEM_PRICES_OFFER_ITEM_FK)
                .where(OfferItems.ITEM.eq(item.getPrimaryKey()),
                        OfferItems.THRU_TIME.eq(Session.MAX_TIME),
                        OfferItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        OfferItemPrices.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferItemPrices.fields())
                    .from(OfferItemPrices)
                    .join(OfferItems).onKey(OFFER_ITEM_PRICES_OFFER_ITEM_FK)
                    .join(Offers).onKey(OFFER_ITEMS_OFFER_FK)
                    .join(OfferDetails).onKey(OFFERS_LAST_DETAIL_FK)
                    .join(InventoryConditions).onKey(OFFER_ITEM_PRICES_INVENTORY_CONDITION_FK)
                    .join(InventoryConditionDetails).onKey(INVENTORY_CONDITIONS_LAST_DETAIL_FK)
                    .join(Currencies).onKey(OFFER_ITEM_PRICES_CURRENCY_FK)
                    .where(OfferItems.ITEM.eq(item.getPrimaryKey()),
                            OfferItems.THRU_TIME.eq(Session.MAX_TIME),
                            OfferItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            OfferItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OfferDetails.SORT_ORDER, OfferDetails.OFFER_NAME, InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME, Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    OfferItemPriceFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerItemPriceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OfferItemPrice> getOfferItemPricesByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getOfferItemPricesByItemAndUnitOfMeasureType(item, unitOfMeasureType, EntityPermission.READ_ONLY);
    }

    public List<OfferItemPrice> getOfferItemPricesByItemAndUnitOfMeasureTypeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getOfferItemPricesByItemAndUnitOfMeasureType(item, unitOfMeasureType, EntityPermission.READ_WRITE);
    }

    private List<OfferItemPrice> getOfferItemPricesByOfferItem(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferItemPrices.fields())
                .from(OfferItemPrices)
                .where(OfferItemPrices.OFFER_ITEM.eq(offerItem.getPrimaryKey()),
                        OfferItemPrices.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        OfferItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        OfferItemPrices.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferItemPrices.fields())
                    .from(OfferItemPrices)
                    .join(Currencies).onKey(OFFER_ITEM_PRICES_CURRENCY_FK)
                    .where(OfferItemPrices.OFFER_ITEM.eq(offerItem.getPrimaryKey()),
                            OfferItemPrices.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            OfferItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            OfferItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Currencies.SORT_ORDER, Currencies.CURRENCY_ISO_NAME),
                    OfferItemPriceFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerItemPriceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OfferItemPrice> getOfferItemPricesByOfferItem(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType) {
        return getOfferItemPricesByOfferItem(offerItem, inventoryCondition, unitOfMeasureType, EntityPermission.READ_ONLY);
    }

    public List<OfferItemPrice> getOfferItemPricesByOfferItemForUpdate(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType) {
        return getOfferItemPricesByOfferItem(offerItem, inventoryCondition, unitOfMeasureType, EntityPermission.READ_WRITE);
    }

    private List<OfferItemPrice> getOfferItemPrices(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferItemPrices.fields())
                .from(OfferItemPrices)
                .join(OfferItems).onKey(OFFER_ITEM_PRICES_OFFER_ITEM_FK)
                .where(OfferItems.ITEM.eq(item.getPrimaryKey()),
                        OfferItems.THRU_TIME.eq(Session.MAX_TIME),
                        OfferItemPrices.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        OfferItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        OfferItemPrices.CURRENCY.eq(currency.getPrimaryKey()),
                        OfferItemPrices.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(OfferItemPrices.fields())
                    .from(OfferItemPrices)
                    .join(OfferItems).onKey(OFFER_ITEM_PRICES_OFFER_ITEM_FK)
                    .join(Offers).onKey(OFFER_ITEMS_OFFER_FK)
                    .join(OfferDetails).onKey(OFFERS_LAST_DETAIL_FK)
                    .where(OfferItems.ITEM.eq(item.getPrimaryKey()),
                            OfferItems.THRU_TIME.eq(Session.MAX_TIME),
                            OfferItemPrices.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            OfferItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                            OfferItemPrices.CURRENCY.eq(currency.getPrimaryKey()),
                            OfferItemPrices.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(OfferDetails.SORT_ORDER, OfferDetails.OFFER_NAME),
                    OfferItemPriceFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerItemPriceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<OfferItemPrice> getOfferItemPrices(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getOfferItemPrices(item, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_ONLY);
    }

    public List<OfferItemPrice> getOfferItemPricesForUpdate(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getOfferItemPrices(item, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_WRITE);
    }

    public OfferItemPrice getOfferItemPrice(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferItemPrices.fields())
                .from(OfferItemPrices)
                .where(OfferItemPrices.OFFER_ITEM.eq(offerItem.getPrimaryKey()),
                        OfferItemPrices.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        OfferItemPrices.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        OfferItemPrices.CURRENCY.eq(currency.getPrimaryKey()),
                        OfferItemPrices.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerItemPriceFactory.getEntityFromQuery(entityPermission, query);
    }

    public OfferItemPrice getOfferItemPrice(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency) {
        return getOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_ONLY);
    }

    public OfferItemPrice getOfferItemPriceForUpdate(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency) {
        return getOfferItemPrice(offerItem, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_WRITE);
    }

    public OfferItemPriceTransfer getOfferItemPriceTransfer(UserVisit userVisit, OfferItemPrice offerItemPrice) {
        return offerItemPriceTransferCache.getTransfer(userVisit, offerItemPrice);
    }

    public List<OfferItemPriceTransfer> getOfferItemPriceTransfers(UserVisit userVisit, Collection<OfferItemPrice> offerItemPrices) {
        List<OfferItemPriceTransfer> offerItemPriceTransfers = new ArrayList<>(offerItemPrices.size());

        offerItemPrices.forEach((offerItemPrice) ->
                offerItemPriceTransfers.add(offerItemPriceTransferCache.getTransfer(userVisit, offerItemPrice))
        );

        return offerItemPriceTransfers;
    }

    public ListWrapper<HistoryTransfer<OfferItemPriceTransfer>> getOfferItemPriceHistory(UserVisit userVisit, OfferItemPrice offerItemPrice) {
        return offerItemPriceTransferCache.getHistory(userVisit, offerItemPrice);
    }

    public List<OfferItemPriceTransfer> getOfferItemPriceTransfersByOfferItem(UserVisit userVisit, OfferItem offerItem) {
        return getOfferItemPriceTransfers(userVisit, getOfferItemPricesByOfferItem(offerItem));
    }

    /** Use the function in OfferItemLogic instead. */
    public void deleteOfferItemPrice(OfferItemPrice offerItemPrice, BasePK deletedBy) {
        offerItemPrice.setThruTime(session.getStartTime());
        offerItemPrice.store();

        var offerItem = offerItemPrice.getOfferItemForUpdate();
        var item = offerItem.getItem();
        var itemPriceType = item.getLastDetail().getItemPriceType();
        var itemPriceTypeName = itemPriceType.getItemPriceTypeName();

        if(itemPriceTypeName.equals(ItemPriceTypes.FIXED.name())) {
            var offerItemFixedPrice = getOfferItemFixedPriceForUpdate(offerItemPrice);

            deleteOfferItemFixedPrice(offerItemFixedPrice, deletedBy);
        } else if(itemPriceTypeName.equals(ItemPriceTypes.VARIABLE.name())) {
            var offerItemVariablePrice = getOfferItemVariablePriceForUpdate(offerItemPrice);

            deleteOfferItemVariablePrice(offerItemVariablePrice, deletedBy);
        }

        sendEvent(offerItem.getPrimaryKey(), EventTypes.MODIFY, offerItemPrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Offer Item Fixed Prices
    // --------------------------------------------------------------------------------

    @Inject
    private OfferItemFixedPriceFactory offerItemFixedPriceFactory;

    /** Use the function in OfferItemLogic instead. */
    public OfferItemFixedPrice createOfferItemFixedPrice(OfferItemPrice offerItemPrice, Long unitPrice, BasePK createdBy) {
        var offerItemFixedPrice = offerItemFixedPriceFactory.create(offerItemPrice,
                unitPrice, session.getStartTime(), Session.MAX_TIME);

        sendEvent(offerItemPrice.getOfferItemPK(), EventTypes.MODIFY, offerItemFixedPrice.getPrimaryKey(),
                EventTypes.CREATE, createdBy);

        return offerItemFixedPrice;
    }

    private OfferItemFixedPrice getOfferItemFixedPrice(OfferItemPrice offerItemPrice, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferItemFixedPrices.fields())
                .from(OfferItemFixedPrices)
                .where(OfferItemFixedPrices.OFFER_ITEM_PRICE.eq(offerItemPrice.getPrimaryKey()),
                        OfferItemFixedPrices.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerItemFixedPriceFactory.getEntityFromQuery(entityPermission, query);
    }

    public OfferItemFixedPrice getOfferItemFixedPrice(OfferItemPrice offerItemPrice) {
        return getOfferItemFixedPrice(offerItemPrice, EntityPermission.READ_ONLY);
    }

    public OfferItemFixedPrice getOfferItemFixedPriceForUpdate(OfferItemPrice offerItemPrice) {
        return getOfferItemFixedPrice(offerItemPrice, EntityPermission.READ_WRITE);
    }

    public OfferItemFixedPriceValue getOfferItemFixedPriceValueForUpdate(OfferItemPrice offerItemPrice) {
        var offerItemFixedPrice = getOfferItemFixedPriceForUpdate(offerItemPrice);

        return offerItemFixedPrice == null? null: offerItemFixedPrice.getOfferItemFixedPriceValue().clone();
    }

    public List<OfferItemFixedPrice> getOfferItemFixedPriceHistory(OfferItemPrice offerItemPrice) {
        var query = session.applyLimit(session.getDslContext()
                .select(OfferItemFixedPrices.fields())
                .from(OfferItemFixedPrices)
                .where(OfferItemFixedPrices.OFFER_ITEM_PRICE.eq(offerItemPrice.getPrimaryKey()))
                .orderBy(OfferItemFixedPrices.THRU_TIME),
                OfferItemFixedPriceFactory.class);

        return offerItemFixedPriceFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    /** Use the function in OfferItemLogic instead. */
    public OfferItemFixedPrice updateOfferItemFixedPriceFromValue(OfferItemFixedPriceValue offerItemFixedPriceValue, BasePK updatedBy) {
        OfferItemFixedPrice offerItemFixedPrice = null;

        if(offerItemFixedPriceValue.hasBeenModified()) {
            offerItemFixedPrice = offerItemFixedPriceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    offerItemFixedPriceValue.getPrimaryKey());

            offerItemFixedPrice.setThruTime(session.getStartTime());
            offerItemFixedPrice.store();

            var offerItemPricePK = offerItemFixedPrice.getOfferItemPricePK();
            var unitPrice = offerItemFixedPriceValue.getUnitPrice();

            offerItemFixedPrice = offerItemFixedPriceFactory.create(offerItemPricePK, unitPrice,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(offerItemFixedPrice.getOfferItemPrice().getOfferItemPK(), EventTypes.MODIFY,
                    offerItemFixedPrice.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }

        return offerItemFixedPrice;
    }

    private void deleteOfferItemFixedPrice(OfferItemFixedPrice offerItemFixedPrice, BasePK deletedBy) {
        offerItemFixedPrice.setThruTime(session.getStartTime());
        offerItemFixedPrice.store();

        sendEvent(offerItemFixedPrice.getOfferItemPrice().getOfferItemPK(), EventTypes.MODIFY,
                offerItemFixedPrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Offer Item Variable Prices
    // --------------------------------------------------------------------------------

    @Inject
    private OfferItemVariablePriceFactory offerItemVariablePriceFactory;

    /** Use the function in OfferItemLogic instead. */
    public OfferItemVariablePrice createOfferItemVariablePrice(OfferItemPrice offerItemPrice, Long minimumUnitPrice, Long maximumUnitPrice,
            Long unitPriceIncrement, BasePK createdBy) {
        var offerItemVariablePrice = offerItemVariablePriceFactory.create(offerItemPrice, minimumUnitPrice, maximumUnitPrice,
                unitPriceIncrement, session.getStartTime(), Session.MAX_TIME);

        sendEvent(offerItemPrice.getOfferItemPK(), EventTypes.MODIFY, offerItemVariablePrice.getPrimaryKey(),
                EventTypes.CREATE, createdBy);

        return offerItemVariablePrice;
    }

    private OfferItemVariablePrice getOfferItemVariablePrice(OfferItemPrice offerItemPrice, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(OfferItemVariablePrices.fields())
                .from(OfferItemVariablePrices)
                .where(OfferItemVariablePrices.OFFER_ITEM_PRICE.eq(offerItemPrice.getPrimaryKey()),
                        OfferItemVariablePrices.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return offerItemVariablePriceFactory.getEntityFromQuery(entityPermission, query);
    }

    public OfferItemVariablePrice getOfferItemVariablePrice(OfferItemPrice offerItemPrice) {
        return getOfferItemVariablePrice(offerItemPrice, EntityPermission.READ_ONLY);
    }

    public OfferItemVariablePrice getOfferItemVariablePriceForUpdate(OfferItemPrice offerItemPrice) {
        return getOfferItemVariablePrice(offerItemPrice, EntityPermission.READ_WRITE);
    }

    public OfferItemVariablePriceValue getOfferItemVariablePriceValueForUpdate(OfferItemPrice offerItemPrice) {
        var offerItemVariablePrice = getOfferItemVariablePriceForUpdate(offerItemPrice);

        return offerItemVariablePrice == null? null: offerItemVariablePrice.getOfferItemVariablePriceValue().clone();
    }

    public List<OfferItemVariablePrice> getOfferItemVariablePriceHistory(OfferItemPrice offerItemPrice) {
        var query = session.applyLimit(session.getDslContext()
                .select(OfferItemVariablePrices.fields())
                .from(OfferItemVariablePrices)
                .where(OfferItemVariablePrices.OFFER_ITEM_PRICE.eq(offerItemPrice.getPrimaryKey()))
                .orderBy(OfferItemVariablePrices.THRU_TIME),
                OfferItemVariablePriceFactory.class);

        return offerItemVariablePriceFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    /** Use the function in OfferItemLogic instead. */
    public OfferItemVariablePrice updateOfferItemVariablePriceFromValue(OfferItemVariablePriceValue offerItemVariablePriceValue, BasePK updatedBy) {
        OfferItemVariablePrice offerItemVariablePrice = null;

        if(offerItemVariablePriceValue.hasBeenModified()) {
            offerItemVariablePrice = offerItemVariablePriceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    offerItemVariablePriceValue.getPrimaryKey());

            offerItemVariablePrice.setThruTime(session.getStartTime());
            offerItemVariablePrice.store();

            var offerItemPricePK = offerItemVariablePrice.getOfferItemPricePK();
            var maximumUnitPrice = offerItemVariablePriceValue.getMaximumUnitPrice();
            var minimumUnitPrice = offerItemVariablePriceValue.getMinimumUnitPrice();
            var unitPriceIncrement = offerItemVariablePriceValue.getUnitPriceIncrement();

            offerItemVariablePrice = offerItemVariablePriceFactory.create(offerItemPricePK, maximumUnitPrice,
                    minimumUnitPrice, unitPriceIncrement, session.getStartTime(), Session.MAX_TIME);

            sendEvent(offerItemVariablePrice.getOfferItemPrice().getOfferItemPK(), EventTypes.MODIFY,
                    offerItemVariablePrice.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }

        return offerItemVariablePrice;
    }

    private void deleteOfferItemVariablePrice(OfferItemVariablePrice offerItemVariablePrice, BasePK deletedBy) {
        offerItemVariablePrice.setThruTime(session.getStartTime());
        offerItemVariablePrice.store();

        sendEvent(offerItemVariablePrice.getOfferItemPrice().getOfferItemPK(), EventTypes.MODIFY,
                offerItemVariablePrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

}
