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

package com.echothree.model.control.inventory.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.common.transfer.LotTransfer;
import com.echothree.model.control.inventory.server.transfer.LotTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.LotPK;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.inventory.server.factory.LotDetailFactory;
import com.echothree.model.data.inventory.server.factory.LotFactory;
import com.echothree.model.data.inventory.server.value.LotDetailValue;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.LOTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.LotDetails.LotDetails;
import static com.echothree.model.jooq.server.tables.inventory.Lots.Lots;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class LotControl
        extends BaseModelControl {

    @Inject
    LotTransferCache lotTransferCache;

    /**
     * Creates a new instance of LotControl
     */
    protected LotControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Lots
    // --------------------------------------------------------------------------------

    @Inject
    protected LotFactory lotFactory;

    @Inject
    protected LotDetailFactory lotDetailFactory;

    public Lot createLot(final Item item, final String lotIdentifier, final BasePK createdBy) {

        var lot = lotFactory.create();
        var lotDetail = lotDetailFactory.create(lot, item, lotIdentifier, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        lot = lotFactory.getEntityFromPK(EntityPermission.READ_WRITE, lot.getPrimaryKey());
        lot.setActiveDetail(lotDetail);
        lot.setLastDetail(lotDetail);
        lot.store();

        sendEvent(lot.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return lot;
    }

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.Lot
     */
    public Lot getLotByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new LotPK(entityInstance.getEntityUniqueId());

        return lotFactory.getEntityFromPK(entityPermission, pk);
    }

    public Lot getLotByEntityInstance(final EntityInstance entityInstance) {
        return getLotByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Lot getLotByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getLotByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countLots() {
        return session.getDslContext()
                .selectCount()
                .from(Lots)
                .join(LotDetails).onKey(LOTS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countLotsByItem(Item item) {
        return session.getDslContext()
                .selectCount()
                .from(Lots)
                .join(LotDetails).onKey(LOTS_ACTIVE_DETAIL_FK)
                .where(LotDetails.ITEM.eq(item.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public Lot getLotByIdentifier(final Item item, final String lotIdentifier, final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Lots.fields())
                .from(Lots)
                .join(LotDetails).onKey(LOTS_ACTIVE_DETAIL_FK)
                .where(LotDetails.ITEM.eq(item.getPrimaryKey()), LotDetails.LOT_IDENTIFIER.eq(lotIdentifier));

        var query = entityPermission == EntityPermission.READ_ONLY ? baseQuery : baseQuery.forUpdate();

        return lotFactory.getEntityFromQuery(entityPermission,
                lotFactory.prepareStatement(query.getSQL()), query.getBindValues().toArray());
    }

    public Lot getLotByIdentifier(final Item item, final String lotIdentifier) {
        return getLotByIdentifier(item, lotIdentifier, EntityPermission.READ_ONLY);
    }

    public Lot getLotByIdentifierForUpdate(final Item item, final String lotIdentifier) {
        return getLotByIdentifier(item, lotIdentifier, EntityPermission.READ_WRITE);
    }

    public LotDetailValue getLotDetailValueForUpdate(final Lot lot) {
        return lot == null ? null : lot.getLastDetailForUpdate().getLotDetailValue().clone();
    }

    public LotDetailValue getLotDetailValueByIdentifierForUpdate(final Item item, final String lotIdentifier) {
        return getLotDetailValueForUpdate(getLotByIdentifierForUpdate(item, lotIdentifier));
    }

    private List<Lot> getLotsByItem(final Item item,
            final EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Lots.fields())
                .from(Lots)
                .join(LotDetails).onKey(LOTS_ACTIVE_DETAIL_FK)
                .where(LotDetails.ITEM.eq(item.getPrimaryKey()));

        var query = entityPermission == EntityPermission.READ_ONLY ? baseQuery.orderBy(LotDetails.LOT_IDENTIFIER) : baseQuery.forUpdate();

        var sql = query.getSQL() + (entityPermission == EntityPermission.READ_ONLY ? " _LIMIT_" : "");

        return lotFactory.getEntitiesFromQuery(entityPermission,
                lotFactory.prepareStatement(sql), query.getBindValues().toArray());
    }

    public List<Lot> getLotsByItem(final Item item) {
        return getLotsByItem(item, EntityPermission.READ_ONLY);
    }

    public List<Lot> getLotsByItemForUpdate(final Item item) {
        return getLotsByItem(item, EntityPermission.READ_WRITE);
    }

    public LotTransfer getLotTransfer(final UserVisit userVisit,
            final Lot lot) {
        return lotTransferCache.getTransfer(userVisit, lot);
    }

    public List<LotTransfer> getLotTransfers(final UserVisit userVisit, final Collection<Lot> lots) {
        var lotTransfers = new ArrayList<LotTransfer>(lots.size());

        lots.forEach((lot) ->
                lotTransfers.add(lotTransferCache.getTransfer(userVisit, lot))
        );

        return lotTransfers;
    }

    public List<LotTransfer> getLotTransfersByItem(final UserVisit userVisit, final Item item) {
        return getLotTransfers(userVisit, getLotsByItem(item));
    }

    public void updateLotFromValue(final LotDetailValue lotDetailValue,
            final BasePK updatedBy) {
        if(lotDetailValue.hasBeenModified()) {
            var lot = lotFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    lotDetailValue.getLotPK());
            var lotDetail = lot.getActiveDetailForUpdate();

            lotDetail.setThruTime(session.getStartTime());
            lotDetail.store();

            var lotPK = lotDetail.getLotPK(); // R/O
            var itemPK = lotDetailValue.getItemPK(); // R/O
            var lotIdentifier = lotDetailValue.getLotIdentifier(); // R/W

            lotDetail = lotDetailFactory.create(lotPK, itemPK, lotIdentifier, session.getStartTime(),
                    Session.MAX_TIME);

            lot.setActiveDetail(lotDetail);
            lot.setLastDetail(lotDetail);

            sendEvent(lotPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteLot(final Lot lot, final BasePK deletedBy) {
        var lotDetail = lot.getLastDetailForUpdate();
        lotDetail.setThruTime(session.getStartTime());
        lotDetail.store();
        lot.setActiveDetail(null);

        sendEvent(lot.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteLots(final Collection<Lot> lots, final BasePK deletedBy) {
        lots.forEach(lot -> deleteLot(lot, deletedBy));
    }

    public void deleteLotsByItem(final Item item, final BasePK deletedBy) {
        deleteLots(getLotsByItemForUpdate(item), deletedBy);
    }

}
