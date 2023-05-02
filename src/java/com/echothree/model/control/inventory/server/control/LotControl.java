// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.inventory.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.common.transfer.LotTransfer;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.LotPK;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.inventory.server.factory.LotDetailFactory;
import com.echothree.model.data.inventory.server.factory.LotFactory;
import com.echothree.model.data.inventory.server.value.LotDetailValue;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class LotControl
        extends BaseInventoryControl {

    /** Creates a new instance of LotControl */
    public LotControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Lots
    // --------------------------------------------------------------------------------

    public Lot createLot(final String lotName, final Party ownerParty, final Item item, final UnitOfMeasureType unitOfMeasureType,
            final InventoryCondition inventoryCondition, final Long quantity, final Currency currency, final Long unitCost,
            final BasePK createdBy) {

        var lot = LotFactory.getInstance().create();
        var lotDetail = LotDetailFactory.getInstance().create(session, lot, lotName, ownerParty, item, unitOfMeasureType,
                inventoryCondition, quantity, currency, unitCost, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        lot = LotFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, lot.getPrimaryKey());
        lot.setActiveDetail(lotDetail);
        lot.setLastDetail(lotDetail);
        lot.store();

        sendEvent(lot.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return lot;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Lot */
    public Lot getLotByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new LotPK(entityInstance.getEntityUniqueId());

        return LotFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public Lot getLotByEntityInstance(final EntityInstance entityInstance) {
        return getLotByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Lot getLotByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getLotByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countLots() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM lots, lotdetails " +
                "WHERE lt_activedetailid = ltdt_lotdetailid");
    }

    private static final Map<EntityPermission, String> getLotByNameQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM lots, lotdetails " +
                    "WHERE lt_activedetailid = ltdt_lotdetailid " +
                    "AND ltdt_lotname = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM lots, lotdetails " +
                    "WHERE lt_activedetailid = ltdt_lotdetailid " +
                    "AND ltdt_lotname = ? " +
                    "FOR UPDATE");

    public Lot getLotByName(final String lotName, final EntityPermission entityPermission) {
        return LotFactory.getInstance().getEntityFromQuery(entityPermission, getLotByNameQueries,
                lotName);
    }

    public Lot getLotByName(final String lotName) {
        return getLotByName(lotName, EntityPermission.READ_ONLY);
    }

    public Lot getLotByNameForUpdate(final String lotName) {
        return getLotByName(lotName, EntityPermission.READ_WRITE);
    }

    public LotDetailValue getLotDetailValueForUpdate(final Lot lot) {
        return lot == null ? null: lot.getLastDetailForUpdate().getLotDetailValue().clone();
    }

    public LotDetailValue getLotDetailValueByNameForUpdate(final String lotName) {
        return getLotDetailValueForUpdate(getLotByNameForUpdate(lotName));
    }

    private static final Map<EntityPermission, String> getLotsQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM lots, lotdetails " +
                    "WHERE lt_activedetailid = ltdt_lotdetailid " +
                    "ORDER BY ltdt_lotname " +
                    "_LIMIT_",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM lots, lotdetails " +
                    "WHERE lt_activedetailid = ltdt_lotdetailid " +
                    "FOR UPDATE");

    private List<Lot> getLots(final EntityPermission entityPermission) {
        return LotFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotsQueries);
    }

    public List<Lot> getLots() {
        return getLots(EntityPermission.READ_ONLY);
    }

    public List<Lot> getLotsForUpdate() {
        return getLots(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLotsByOwnerPartyQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM lots, lotdetails " +
                    "WHERE lt_activedetailid = ltdt_lotdetailid " +
                    "AND ltdt_ownerpartyid = ? " +
                    "ORDER BY ltdt_lotname " +
                    "_LIMIT_",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM lots, lotdetails " +
                    "WHERE lt_activedetailid = ltdt_lotdetailid " +
                    "AND ltdt_ownerpartyid = ? " +
                    "FOR UPDATE");

    private List<Lot> getLotsByOwnerParty(final Party ownerParty,
            final EntityPermission entityPermission) {
        return LotFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotsByOwnerPartyQueries,
                ownerParty, Session.MAX_TIME);
    }

    public List<Lot> getLotsByOwnerParty(final Party ownerParty) {
        return getLotsByOwnerParty(ownerParty, EntityPermission.READ_ONLY);
    }

    public List<Lot> getLotsByOwnerPartyForUpdate(final Party ownerParty) {
        return getLotsByOwnerParty(ownerParty, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLotsByItemQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM lots, lotdetails " +
                    "WHERE lt_activedetailid = ltdt_lotdetailid " +
                    "AND ltdt_itm_itemid = ? " +
                    "ORDER BY ltdt_lotname " +
                    "_LIMIT_",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM lots, lotdetails " +
                    "WHERE lt_activedetailid = ltdt_lotdetailid " +
                    "AND ltdt_itm_itemid = ? " +
                    "FOR UPDATE");

    private List<Lot> getLotsByItem(final Item item,
            final EntityPermission entityPermission) {
        return LotFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotsByItemQueries,
                item, Session.MAX_TIME);
    }

    public List<Lot> getLotsByItem(final Item item) {
        return getLotsByItem(item, EntityPermission.READ_ONLY);
    }

    public List<Lot> getLotsByItemForUpdate(final Item item) {
        return getLotsByItem(item, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLotsByUnitOfMeasureTypeQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM lots, lotdetails " +
                    "WHERE lt_activedetailid = ltdt_lotdetailid " +
                    "AND ltdt_uomt_unitofmeasuretypeid = ? " +
                    "ORDER BY ltdt_lotname " +
                    "_LIMIT_",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM lots, lotdetails " +
                    "WHERE lt_activedetailid = ltdt_lotdetailid " +
                    "AND ltdt_uomt_unitofmeasuretypeid = ? " +
                    "FOR UPDATE");

    private List<Lot> getLotsByUnitOfMeasureType(final UnitOfMeasureType unitOfMeasureType,
            final EntityPermission entityPermission) {
        return LotFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotsByUnitOfMeasureTypeQueries,
                unitOfMeasureType, Session.MAX_TIME);
    }

    public List<Lot> getLotsByUnitOfMeasureType(final UnitOfMeasureType unitOfMeasureType) {
        return getLotsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }

    public List<Lot> getLotsByUnitOfMeasureTypeForUpdate(final UnitOfMeasureType unitOfMeasureType) {
        return getLotsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLotsByInventoryConditionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM lots, lotdetails " +
                    "WHERE lt_activedetailid = ltdt_lotdetailid " +
                    "AND ltdt_invcon_inventoryconditionid = ? " +
                    "ORDER BY ltdt_lotname " +
                    "_LIMIT_",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM lots, lotdetails " +
                    "WHERE lt_activedetailid = ltdt_lotdetailid " +
                    "AND ltdt_invcon_inventoryconditionid = ? " +
                    "FOR UPDATE");

    private List<Lot> getLotsByInventoryCondition(final InventoryCondition inventoryCondition,
            final EntityPermission entityPermission) {
        return LotFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotsByInventoryConditionQueries,
                inventoryCondition, Session.MAX_TIME);
    }

    public List<Lot> getLotsByInventoryCondition(final InventoryCondition inventoryCondition) {
        return getLotsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }

    public List<Lot> getLotsByInventoryConditionForUpdate(final InventoryCondition inventoryCondition) {
        return getLotsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLotsByCurrencyQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM lots, lotdetails " +
                    "WHERE lt_activedetailid = ltdt_lotdetailid " +
                    "AND ltdt_cur_currencyid = ? " +
                    "ORDER BY ltdt_lotname " +
                    "_LIMIT_",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM lots, lotdetails " +
                    "WHERE lt_activedetailid = ltdt_lotdetailid " +
                    "AND ltdt_cur_currencyid = ? " +
                    "FOR UPDATE");

    private List<Lot> getLotsByCurrency(final Currency currency,
            final EntityPermission entityPermission) {
        return LotFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotsByCurrencyQueries,
                currency, Session.MAX_TIME);
    }

    public List<Lot> getLotsByCurrency(final Currency currency) {
        return getLotsByCurrency(currency, EntityPermission.READ_ONLY);
    }

    public List<Lot> getLotsByCurrencyForUpdate(final Currency currency) {
        return getLotsByCurrency(currency, EntityPermission.READ_WRITE);
    }

    public LotTransfer getLotTransfer(final UserVisit userVisit,
            final Lot lot) {
        return getInventoryTransferCaches(userVisit).getLotTransferCache().getTransfer(lot);
    }

    public List<LotTransfer> getLotTransfers(final UserVisit userVisit,
            final Collection<Lot> lots) {
        var lotTransfers = new ArrayList<LotTransfer>(lots.size());
        var lotTransferCache = getInventoryTransferCaches(userVisit).getLotTransferCache();

        lots.forEach((lot) ->
                lotTransfers.add(lotTransferCache.getTransfer(lot))
        );

        return lotTransfers;
    }

    public List<LotTransfer> getLotTransfers(final UserVisit userVisit) {
        return getLotTransfers(userVisit, getLots());
    }

    public void updateLotFromValue(final LotDetailValue lotDetailValue,
            final BasePK updatedBy) {
        if(lotDetailValue.hasBeenModified()) {
            var lot = LotFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    lotDetailValue.getLotPK());
            var lotDetail = lot.getActiveDetailForUpdate();

            lotDetail.setThruTime(session.START_TIME_LONG);
            lotDetail.store();

            var lotPK = lotDetail.getLotPK(); // R/O
            var lotName = lotDetailValue.getLotName(); // R/W
            var ownerPartyPK = lotDetailValue.getOwnerPartyPK(); // R/W
            var itemPK = lotDetailValue.getItemPK(); // R/W
            var unitOfMeasureTypePK = lotDetailValue.getUnitOfMeasureTypePK(); // R/W
            var inventoryConditionPK = lotDetailValue.getInventoryConditionPK(); // R/W
            var quantity = lotDetailValue.getQuantity(); // R/W
            var currencyPK = lotDetailValue.getCurrencyPK(); // R/W
            var unitCost = lotDetailValue.getUnitCost(); // R/W

            lotDetail = LotDetailFactory.getInstance().create(lotPK, lotName, ownerPartyPK, itemPK, unitOfMeasureTypePK,
                    inventoryConditionPK, quantity, currencyPK, unitCost, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            lot.setActiveDetail(lotDetail);
            lot.setLastDetail(lotDetail);

            sendEvent(lotPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteLot(final Lot lot, final BasePK deletedBy) {
        var lotDetail = lot.getLastDetailForUpdate();
        lotDetail.setThruTime(session.START_TIME_LONG);
        lotDetail.store();
        lot.setActiveDetail(null);

        sendEvent(lot.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteLots(final Collection<Lot> lots, final BasePK deletedBy) {
        lots.forEach(lot -> deleteLot(lot, deletedBy));
    }

    public void deleteLotsByOwnerParty(final Party ownerParty, final BasePK deletedBy) {
        deleteLots(getLotsByOwnerPartyForUpdate(ownerParty), deletedBy);
    }

    public void deleteLotsByItem(final Item item, final BasePK deletedBy) {
        deleteLots(getLotsByItemForUpdate(item), deletedBy);
    }

    public void deleteLotsByUnitOfMeasureType(final UnitOfMeasureType unitOfMeasureType, final BasePK deletedBy) {
        deleteLots(getLotsByUnitOfMeasureTypeForUpdate(unitOfMeasureType), deletedBy);
    }

    public void deleteLotsByInventoryCondition(final InventoryCondition inventoryCondition, final BasePK deletedBy) {
        deleteLots(getLotsByInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }

    public void deleteLotsByCurrency(final Currency currency, final BasePK deletedBy) {
        deleteLots(getLotsByCurrencyForUpdate(currency), deletedBy);
    }

}
