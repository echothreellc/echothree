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

package com.echothree.model.control.offer.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.item.common.ItemPriceTypes;
import com.echothree.model.control.offer.common.transfer.OfferItemPriceTransfer;
import com.echothree.model.control.offer.common.transfer.OfferItemTransfer;
import com.echothree.model.control.offer.server.logic.OfferItemLogic;
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
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.common.transfer.HistoryTransfer;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class OfferItemControl
        extends BaseOfferControl {

    /** Creates a new instance of OfferItemControl */
    protected OfferItemControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Offer Items
    // --------------------------------------------------------------------------------

    /** Use the function in OfferLogic instead. */
    public OfferItem createOfferItem(Offer offer, Item item, BasePK createdBy) {
        var offerItem = OfferItemFactory.getInstance().create(offer, item, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(offerItem.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return offerItem;
    }

    public long countOfferItemsByOffer(Offer offer) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                        + "FROM offeritems "
                        + "WHERE ofri_ofr_offerid = ? AND ofri_thrutime = ?",
                offer, Session.MAX_TIME);
    }

    public long countOfferItemsByItem(Item item) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                        + "FROM offeritems "
                        + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ?",
                item, Session.MAX_TIME);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.OfferItem */
    public OfferItem getOfferItemByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new OfferItemPK(entityInstance.getEntityUniqueId());
        var offerItem = OfferItemFactory.getInstance().getEntityFromPK(entityPermission, pk);

        return offerItem;
    }

    public OfferItem getOfferItemByEntityInstance(EntityInstance entityInstance) {
        return getOfferItemByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public OfferItem getOfferItemByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getOfferItemByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOfferItemQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM offeritems "
                        + "WHERE ofri_ofr_offerid = ? AND ofri_itm_itemid = ? AND ofri_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM offeritems "
                        + "WHERE ofri_ofr_offerid = ? AND ofri_itm_itemid = ? AND ofri_thrutime = ? "
                        + "FOR UPDATE");
        getOfferItemQueries = Collections.unmodifiableMap(queryMap);
    }

    public OfferItem getOfferItem(Offer offer, Item item, EntityPermission entityPermission) {
        return OfferItemFactory.getInstance().getEntityFromQuery(entityPermission, getOfferItemQueries,
                offer, item, Session.MAX_TIME);
    }

    public OfferItem getOfferItem(Offer offer, Item item) {
        return getOfferItem(offer, item, EntityPermission.READ_ONLY);
    }

    public OfferItem getOfferItemForUpdate(Offer offer, Item item) {
        return getOfferItem(offer, item, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOfferItemsByOfferQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM offeritems, items, itemdetails "
                        + "WHERE ofri_ofr_offerid = ? AND ofri_thrutime = ? "
                        + "AND ofri_itm_itemid = itm_itemid AND itm_activedetailid = itmdt_itemdetailid "
                        + "ORDER BY itmdt_itemname "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM offeritems "
                        + "WHERE ofri_ofr_offerid = ? AND ofri_thrutime = ? "
                        + "FOR UPDATE");
        getOfferItemsByOfferQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OfferItem> getOfferItemsByOffer(Offer offer, EntityPermission entityPermission) {
        return OfferItemFactory.getInstance().getEntitiesFromQuery(entityPermission, getOfferItemsByOfferQueries,
                offer, Session.MAX_TIME);
    }

    public List<OfferItem> getOfferItemsByOffer(Offer offer) {
        return getOfferItemsByOffer(offer, EntityPermission.READ_ONLY);
    }

    public List<OfferItem> getOfferItemsByOfferForUpdate(Offer offer) {
        return getOfferItemsByOffer(offer, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOfferItemsByItemQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM offeritems, offers, offerdetails "
                        + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ? "
                        + "AND ofri_ofr_offerid = ofr_offerid AND ofr_activedetailid = ofrdt_offerdetailid "
                        + "ORDER BY ofrdt_sortorder, ofrdt_offername "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM offeritems "
                        + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ? "
                        + "FOR UPDATE");
        getOfferItemsByItemQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OfferItem> getOfferItemsByItem(Item item, EntityPermission entityPermission) {
        return OfferItemFactory.getInstance().getEntitiesFromQuery(entityPermission, getOfferItemsByItemQueries,
                item, Session.MAX_TIME);
    }

    public List<OfferItem> getOfferItemsByItem(Item item) {
        return getOfferItemsByItem(item, EntityPermission.READ_ONLY);
    }

    public List<OfferItem> getOfferItemsByItemForUpdate(Item item) {
        return getOfferItemsByItem(item, EntityPermission.READ_WRITE);
    }

    public OfferItemTransfer getOfferItemTransfer(UserVisit userVisit, OfferItem offerItem) {
        return getOfferTransferCaches(userVisit).getOfferItemTransferCache().getOfferItemTransfer(offerItem);
    }

    public List<OfferItemTransfer> getOfferItemTransfers(UserVisit userVisit, Collection<OfferItem> offerItems) {
        List<OfferItemTransfer> offerItemTransfers = new ArrayList<>(offerItems.size());
        var offerItemTransferCache = getOfferTransferCaches(userVisit).getOfferItemTransferCache();

        offerItems.forEach((offerItem) ->
                offerItemTransfers.add(offerItemTransferCache.getOfferItemTransfer(offerItem))
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
        OfferItemLogic.getInstance().deleteOfferItemPricesByOfferItem(offerItem, deletedBy);

        offerItem.setThruTime(session.START_TIME_LONG);
        offerItem.store();

        sendEvent(offerItem.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Offer Item Prices
    // --------------------------------------------------------------------------------

    /** Use the function in OfferItemLogic instead. */
    public OfferItemPrice createOfferItemPrice(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency, BasePK createdBy) {
        var offerItemPrice = OfferItemPriceFactory.getInstance().create(offerItem, inventoryCondition, unitOfMeasureType, currency,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(offerItem.getPrimaryKey(), EventTypes.MODIFY, offerItemPrice.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return offerItemPrice;
    }

    public long countOfferItemPricesByItem(Item item) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                        + "FROM offeritems, offeritemprices "
                        + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ? "
                        + "AND ofri_offeritemid = ofritmp_ofri_offeritemid AND ofritmp_thrutime = ?",
                item, Session.MAX_TIME, Session.MAX_TIME);
    }

    public long countOfferItemPricesByOfferItem(OfferItem offerItem) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM offeritemprices
                WHERE ofritmp_ofri_offeritemid = ? AND ofritmp_thrutime = ?""",
                offerItem, Session.MAX_TIME);
    }

    public long countOfferItemPricesByInventoryCondition(InventoryCondition inventoryCondition) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM offeritemprices
                WHERE ofritmp_invcon_inventoryconditionid = ? AND ofritmp_thrutime = ?""",
                inventoryCondition, Session.MAX_TIME);
    }

    public long countOfferItemPricesByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM itempofferitempricesrices
                WHERE ofritmp_uomt_unitofmeasuretypeid = ? AND ofritmp_thrutime = ?""",
                unitOfMeasureType, Session.MAX_TIME);
    }

    public long countOfferItemPricesByCurrency(Currency currency) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM offeritemprices
                WHERE ofritmp_cur_currencyid = ? AND ofritmp_thrutime = ?""",
                currency, Session.MAX_TIME);
    }

    private static final Map<EntityPermission, String> getOfferItemPricesByOfferItemQueries1;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM offeritemprices "
                        + "WHERE ofritmp_ofri_offeritemid = ? AND ofritmp_thrutime = ? " +
                        "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM offeritemprices "
                        + "WHERE ofritmp_ofri_offeritemid = ? AND ofritmp_thrutime = ? "
                        + "FOR UPDATE");
        getOfferItemPricesByOfferItemQueries1 = Collections.unmodifiableMap(queryMap);
    }

    private List<OfferItemPrice> getOfferItemPricesByOfferItem(OfferItem offerItem, EntityPermission entityPermission) {
        return OfferItemPriceFactory.getInstance().getEntitiesFromQuery(entityPermission, getOfferItemPricesByOfferItemQueries1,
                offerItem, Session.MAX_TIME);
    }

    public List<OfferItemPrice> getOfferItemPricesByOfferItem(OfferItem offerItem) {
        return getOfferItemPricesByOfferItem(offerItem, EntityPermission.READ_ONLY);
    }

    public List<OfferItemPrice> getOfferItemPricesByOfferItemForUpdate(OfferItem offerItem) {
        return getOfferItemPricesByOfferItem(offerItem, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOfferItemPricesByItemAndUnitOfMeasureTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM offeritems, offeritemprices "
                        + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ? "
                        + "AND ofri_offeritemid = ofritmp_ofri_offeritemid AND ofritmp_uomt_unitofmeasuretypeid = ? AND ofritmp_thrutime = ? "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM offeritems, offeritemprices "
                        + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ? "
                        + "AND ofri_offeritemid = ofritmp_ofri_offeritemid AND ofritmp_uomt_unitofmeasuretypeid = ? AND ofritmp_thrutime = ? "
                        + "FOR UPDATE");
        getOfferItemPricesByItemAndUnitOfMeasureTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OfferItemPrice> getOfferItemPricesByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        return OfferItemPriceFactory.getInstance().getEntitiesFromQuery(entityPermission, getOfferItemPricesByItemAndUnitOfMeasureTypeQueries,
                item, Session.MAX_TIME, unitOfMeasureType, Session.MAX_TIME);
    }

    public List<OfferItemPrice> getOfferItemPricesByItemAndUnitOfMeasureType(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getOfferItemPricesByItemAndUnitOfMeasureType(item, unitOfMeasureType, EntityPermission.READ_ONLY);
    }

    public List<OfferItemPrice> getOfferItemPricesByItemAndUnitOfMeasureTypeForUpdate(Item item, UnitOfMeasureType unitOfMeasureType) {
        return getOfferItemPricesByItemAndUnitOfMeasureType(item, unitOfMeasureType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOfferItemPricesByOfferItemQueries2;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM offeritemprices "
                        + "WHERE ofritmp_ofri_offeritemid = ? AND ofritmp_invcon_inventoryconditionid = ? "
                        + "AND ofritmp_uomt_unitofmeasuretypeid = ? AND ofritmp_thrutime = ? "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM offeritemprices "
                        + "WHERE ofritmp_ofri_offeritemid = ? AND ofritmp_invcon_inventoryconditionid = ? "
                        + "AND ofritmp_uomt_unitofmeasuretypeid = ? AND ofritmp_thrutime = ? "
                        + "FOR UPDATE");
        getOfferItemPricesByOfferItemQueries2 = Collections.unmodifiableMap(queryMap);
    }

    private List<OfferItemPrice> getOfferItemPricesByOfferItem(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            EntityPermission entityPermission) {
        return OfferItemPriceFactory.getInstance().getEntitiesFromQuery(entityPermission, getOfferItemPricesByOfferItemQueries2,
                offerItem, inventoryCondition, unitOfMeasureType, Session.MAX_TIME);
    }

    public List<OfferItemPrice> getOfferItemPricesByOfferItem(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType) {
        return getOfferItemPricesByOfferItem(offerItem, inventoryCondition, unitOfMeasureType, EntityPermission.READ_ONLY);
    }

    public List<OfferItemPrice> getOfferItemPricesByOfferItemForUpdate(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType) {
        return getOfferItemPricesByOfferItem(offerItem, inventoryCondition, unitOfMeasureType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOfferItemPricesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM offeritems, offeritemprices "
                        + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ? AND ofri_offeritemid = ofritmp_ofri_offeritemid "
                        + "AND ofritmp_invcon_inventoryconditionid = ? AND ofritmp_uomt_unitofmeasuretypeid = ? "
                        + "AND ofritmp_cur_currencyid = ? AND ofritmp_thrutime = ? "
                        + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM offeritems, offeritemprices "
                        + "WHERE ofri_itm_itemid = ? AND ofri_thrutime = ? AND ofri_offeritemid = ofritmp_ofri_offeritemid "
                        + "AND ofritmp_invcon_inventoryconditionid = ? AND ofritmp_uomt_unitofmeasuretypeid = ? "
                        + "AND ofritmp_cur_currencyid = ? AND ofritmp_thrutime = ? "
                        + "FOR UPDATE");
        getOfferItemPricesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<OfferItemPrice> getOfferItemPrices(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency, EntityPermission entityPermission) {
        return OfferItemPriceFactory.getInstance().getEntitiesFromQuery(entityPermission, getOfferItemPricesQueries,
                item, Session.MAX_TIME, inventoryCondition, unitOfMeasureType, currency, Session.MAX_TIME);
    }

    public List<OfferItemPrice> getOfferItemPrices(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getOfferItemPrices(item, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_ONLY);
    }

    public List<OfferItemPrice> getOfferItemPricesForUpdate(Item item, InventoryCondition inventoryCondition,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getOfferItemPrices(item, inventoryCondition, unitOfMeasureType, currency, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getOfferItemPriceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM offeritemprices "
                        + "WHERE ofritmp_ofri_offeritemid = ? AND ofritmp_invcon_inventoryconditionid = ? "
                        + "AND ofritmp_uomt_unitofmeasuretypeid = ? AND ofritmp_cur_currencyid = ? AND ofritmp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM offeritemprices "
                        + "WHERE ofritmp_ofri_offeritemid = ? AND ofritmp_invcon_inventoryconditionid = ? "
                        + "AND ofritmp_uomt_unitofmeasuretypeid = ? AND ofritmp_cur_currencyid = ? AND ofritmp_thrutime = ? "
                        + "FOR UPDATE");
        getOfferItemPriceQueries = Collections.unmodifiableMap(queryMap);
    }

    private OfferItemPrice getOfferItemPrice(OfferItem offerItem, InventoryCondition inventoryCondition, UnitOfMeasureType unitOfMeasureType,
            Currency currency, EntityPermission entityPermission) {
        return OfferItemPriceFactory.getInstance().getEntityFromQuery(entityPermission, getOfferItemPriceQueries,
                offerItem, inventoryCondition, unitOfMeasureType, currency, Session.MAX_TIME);
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
        return getOfferTransferCaches(userVisit).getOfferItemPriceTransferCache().getTransfer(offerItemPrice);
    }

    public List<OfferItemPriceTransfer> getOfferItemPriceTransfers(UserVisit userVisit, Collection<OfferItemPrice> offerItemPrices) {
        List<OfferItemPriceTransfer> offerItemPriceTransfers = new ArrayList<>(offerItemPrices.size());
        var offerItemPriceTransferCache = getOfferTransferCaches(userVisit).getOfferItemPriceTransferCache();

        offerItemPrices.forEach((offerItemPrice) ->
                offerItemPriceTransfers.add(offerItemPriceTransferCache.getTransfer(offerItemPrice))
        );

        return offerItemPriceTransfers;
    }

    public ListWrapper<HistoryTransfer<OfferItemPriceTransfer>> getOfferItemPriceHistory(UserVisit userVisit, OfferItemPrice offerItemPrice) {
        return getOfferTransferCaches(userVisit).getOfferItemPriceTransferCache().getHistory(offerItemPrice);
    }

    public List<OfferItemPriceTransfer> getOfferItemPriceTransfersByOfferItem(UserVisit userVisit, OfferItem offerItem) {
        return getOfferItemPriceTransfers(userVisit, getOfferItemPricesByOfferItem(offerItem));
    }

    /** Use the function in OfferItemLogic instead. */
    public void deleteOfferItemPrice(OfferItemPrice offerItemPrice, BasePK deletedBy) {
        offerItemPrice.setThruTime(session.START_TIME_LONG);
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

    /** Use the function in OfferItemLogic instead. */
    public OfferItemFixedPrice createOfferItemFixedPrice(OfferItemPrice offerItemPrice, Long unitPrice, BasePK createdBy) {
        var offerItemFixedPrice = OfferItemFixedPriceFactory.getInstance().create(offerItemPrice,
                unitPrice, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(offerItemPrice.getOfferItemPK(), EventTypes.MODIFY, offerItemFixedPrice.getPrimaryKey(),
                EventTypes.CREATE, createdBy);

        return offerItemFixedPrice;
    }

    private static final Map<EntityPermission, String> getOfferItemFixedPriceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM offeritemfixedprices "
                        + "WHERE ofritmfp_ofritmp_offeritempriceid = ? AND ofritmfp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM offeritemfixedprices "
                        + "WHERE ofritmfp_ofritmp_offeritempriceid = ? AND ofritmfp_thrutime = ? "
                        + "FOR UPDATE");
        getOfferItemFixedPriceQueries = Collections.unmodifiableMap(queryMap);
    }

    private OfferItemFixedPrice getOfferItemFixedPrice(OfferItemPrice offerItemPrice, EntityPermission entityPermission) {
        return OfferItemFixedPriceFactory.getInstance().getEntityFromQuery(entityPermission, getOfferItemFixedPriceQueries,
                offerItemPrice, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getOfferItemFixedPriceHistoryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM offeritemfixedprices "
                        + "WHERE ofritmfp_ofritmp_offeritempriceid = ? "
                        + "ORDER BY ofritmfp_thrutime");
        getOfferItemFixedPriceHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<OfferItemFixedPrice> getOfferItemFixedPriceHistory(OfferItemPrice offerItemPrice) {
        return OfferItemFixedPriceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getOfferItemFixedPriceHistoryQueries,
                offerItemPrice);
    }

    /** Use the function in OfferItemLogic instead. */
    public OfferItemFixedPrice updateOfferItemFixedPriceFromValue(OfferItemFixedPriceValue offerItemFixedPriceValue, BasePK updatedBy) {
        OfferItemFixedPrice offerItemFixedPrice = null;

        if(offerItemFixedPriceValue.hasBeenModified()) {
            offerItemFixedPrice = OfferItemFixedPriceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    offerItemFixedPriceValue.getPrimaryKey());

            offerItemFixedPrice.setThruTime(session.START_TIME_LONG);
            offerItemFixedPrice.store();

            var offerItemPricePK = offerItemFixedPrice.getOfferItemPricePK();
            var unitPrice = offerItemFixedPriceValue.getUnitPrice();

            offerItemFixedPrice = OfferItemFixedPriceFactory.getInstance().create(offerItemPricePK, unitPrice,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(offerItemFixedPrice.getOfferItemPrice().getOfferItemPK(), EventTypes.MODIFY,
                    offerItemFixedPrice.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }

        return offerItemFixedPrice;
    }

    private void deleteOfferItemFixedPrice(OfferItemFixedPrice offerItemFixedPrice, BasePK deletedBy) {
        offerItemFixedPrice.setThruTime(session.START_TIME_LONG);
        offerItemFixedPrice.store();

        sendEvent(offerItemFixedPrice.getOfferItemPrice().getOfferItemPK(), EventTypes.MODIFY,
                offerItemFixedPrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Offer Item Variable Prices
    // --------------------------------------------------------------------------------

    /** Use the function in OfferItemLogic instead. */
    public OfferItemVariablePrice createOfferItemVariablePrice(OfferItemPrice offerItemPrice, Long minimumUnitPrice, Long maximumUnitPrice,
            Long unitPriceIncrement, BasePK createdBy) {
        var offerItemVariablePrice = OfferItemVariablePriceFactory.getInstance().create(offerItemPrice, minimumUnitPrice, maximumUnitPrice,
                unitPriceIncrement, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(offerItemPrice.getOfferItemPK(), EventTypes.MODIFY, offerItemVariablePrice.getPrimaryKey(),
                EventTypes.CREATE, createdBy);

        return offerItemVariablePrice;
    }

    private static final Map<EntityPermission, String> getOfferItemVariablePriceQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM offeritemvariableprices "
                        + "WHERE ofritmvp_ofritmp_offeritempriceid = ? AND ofritmvp_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                        + "FROM offeritemvariableprices "
                        + "WHERE ofritmvp_ofritmp_offeritempriceid = ? AND ofritmvp_thrutime = ? "
                        + "FOR UPDATE");
        getOfferItemVariablePriceQueries = Collections.unmodifiableMap(queryMap);
    }

    private OfferItemVariablePrice getOfferItemVariablePrice(OfferItemPrice offerItemPrice, EntityPermission entityPermission) {
        return OfferItemVariablePriceFactory.getInstance().getEntityFromQuery(entityPermission, getOfferItemVariablePriceQueries,
                offerItemPrice, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getOfferItemVariablePriceHistoryQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(1);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                        + "FROM offeritemvariableprices "
                        + "WHERE ofritmvp_ofritmp_offeritempriceid = ? "
                        + "ORDER BY ofritmvp_thrutime");
        getOfferItemVariablePriceHistoryQueries = Collections.unmodifiableMap(queryMap);
    }

    public List<OfferItemVariablePrice> getOfferItemVariablePriceHistory(OfferItemPrice offerItemPrice) {
        return OfferItemVariablePriceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, getOfferItemVariablePriceHistoryQueries,
                offerItemPrice);
    }

    /** Use the function in OfferItemLogic instead. */
    public OfferItemVariablePrice updateOfferItemVariablePriceFromValue(OfferItemVariablePriceValue offerItemVariablePriceValue, BasePK updatedBy) {
        OfferItemVariablePrice offerItemVariablePrice = null;

        if(offerItemVariablePriceValue.hasBeenModified()) {
            offerItemVariablePrice = OfferItemVariablePriceFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    offerItemVariablePriceValue.getPrimaryKey());

            offerItemVariablePrice.setThruTime(session.START_TIME_LONG);
            offerItemVariablePrice.store();

            var offerItemPricePK = offerItemVariablePrice.getOfferItemPricePK();
            var maximumUnitPrice = offerItemVariablePriceValue.getMaximumUnitPrice();
            var minimumUnitPrice = offerItemVariablePriceValue.getMinimumUnitPrice();
            var unitPriceIncrement = offerItemVariablePriceValue.getUnitPriceIncrement();

            offerItemVariablePrice = OfferItemVariablePriceFactory.getInstance().create(offerItemPricePK, maximumUnitPrice,
                    minimumUnitPrice, unitPriceIncrement, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(offerItemVariablePrice.getOfferItemPrice().getOfferItemPK(), EventTypes.MODIFY,
                    offerItemVariablePrice.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }

        return offerItemVariablePrice;
    }

    private void deleteOfferItemVariablePrice(OfferItemVariablePrice offerItemVariablePrice, BasePK deletedBy) {
        offerItemVariablePrice.setThruTime(session.START_TIME_LONG);
        offerItemVariablePrice.store();

        sendEvent(offerItemVariablePrice.getOfferItemPrice().getOfferItemPK(), EventTypes.MODIFY,
                offerItemVariablePrice.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

}
