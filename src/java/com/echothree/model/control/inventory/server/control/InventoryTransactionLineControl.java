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
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryTransactionLinePK;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentType;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryTransaction;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionLine;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionLineSource;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionLineStatus;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionReason;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionLineDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionLineFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionLineSourceFactory;
import com.echothree.model.data.inventory.server.factory.InventoryTransactionLineStatusFactory;
import com.echothree.model.data.inventory.server.value.InventoryTransactionLineDetailValue;
import com.echothree.model.data.inventory.server.value.InventoryTransactionLineSourceValue;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.warehouse.server.entity.Location;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTIONS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTION_LINES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTION_LINES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.inventory.InventoryForeignKeys.INVENTORY_TRANSACTION_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionDetails.InventoryTransactionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionLineDetails.InventoryTransactionLineDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionLineSources.InventoryTransactionLineSources;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionLineStatuses.InventoryTransactionLineStatuses;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionLines.InventoryTransactionLines;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionTypeDetails.InventoryTransactionTypeDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactionTypes.InventoryTransactionTypes;
import static com.echothree.model.jooq.server.tables.inventory.InventoryTransactions.InventoryTransactions;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import javax.inject.Inject;
import org.jooq.Condition;

@CommandScope
public class InventoryTransactionLineControl
        extends BaseModelControl {

    @Inject
    InventoryTransactionControl inventoryTransactionControl;

    /** Creates a new instance of InventoryTransactionLineControl */
    protected InventoryTransactionLineControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transaction Lines
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryTransactionLineDetailFactory inventoryTransactionLineDetailFactory;

    @Inject
    protected InventoryTransactionLineFactory inventoryTransactionLineFactory;

    public InventoryTransactionLine createInventoryTransactionLine(InventoryTransaction inventoryTransaction, Integer inventoryTransactionLineSequence,
            InventoryTransactionReason inventoryTransactionReason, Item item, Lot lot, InventoryAdjustmentType inventoryAdjustmentType,
            Long unitAmount, String description, BasePK createdBy) {
        if(inventoryTransactionLineSequence == null) {
            var inventoryTransactionStatus = inventoryTransactionControl.getInventoryTransactionStatusForUpdate(inventoryTransaction);

            do {
                inventoryTransactionLineSequence = inventoryTransactionStatus.getInventoryTransactionLineSequence() + 1;
                inventoryTransactionStatus.setInventoryTransactionLineSequence(inventoryTransactionLineSequence);
            } while(inventoryTransactionLineExists(inventoryTransaction, inventoryTransactionLineSequence));
        }

        var inventoryTransactionLine = inventoryTransactionLineFactory.create();
        var inventoryTransactionLineDetail = inventoryTransactionLineDetailFactory.create(inventoryTransactionLine, inventoryTransaction,
                inventoryTransactionLineSequence, inventoryTransactionReason, item, lot, inventoryAdjustmentType, unitAmount, description,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        inventoryTransactionLine = inventoryTransactionLineFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryTransactionLine.getPrimaryKey());
        inventoryTransactionLine.setActiveDetail(inventoryTransactionLineDetail);
        inventoryTransactionLine.setLastDetail(inventoryTransactionLineDetail);
        inventoryTransactionLine.store();

        sendEvent(inventoryTransactionLine.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        createInventoryTransactionLineStatus(inventoryTransactionLine);

        return inventoryTransactionLine;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryTransactionLine */
    public InventoryTransactionLine getInventoryTransactionLineByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new InventoryTransactionLinePK(entityInstance.getEntityUniqueId());

        return inventoryTransactionLineFactory.getEntityFromPK(entityPermission, pk);
    }

    public InventoryTransactionLine getInventoryTransactionLineByEntityInstance(EntityInstance entityInstance) {
        return getInventoryTransactionLineByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionLine getInventoryTransactionLineByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getInventoryTransactionLineByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionLine getInventoryTransactionLineByPK(InventoryTransactionLinePK pk) {
        return inventoryTransactionLineFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    private long countInventoryTransactionLines(Condition condition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionLines)
                .join(InventoryTransactionLineDetails).onKey(INVENTORY_TRANSACTION_LINES_ACTIVE_DETAIL_FK)
                .where(condition)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public boolean inventoryTransactionLineExists(InventoryTransaction inventoryTransaction, Integer inventoryTransactionLineSequence) {
        return countInventoryTransactionLines(InventoryTransactionLineDetails.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey())
                .and(InventoryTransactionLineDetails.INVENTORY_TRANSACTION_LINE_SEQUENCE.eq(inventoryTransactionLineSequence))) != 0;
    }

    public InventoryTransactionLine getInventoryTransactionLineBySequence(InventoryTransaction inventoryTransaction,
            Integer inventoryTransactionLineSequence, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionLines.fields())
                .from(InventoryTransactionLines)
                .join(InventoryTransactionLineDetails).onKey(INVENTORY_TRANSACTION_LINES_ACTIVE_DETAIL_FK)
                .where(InventoryTransactionLineDetails.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey()),
                        InventoryTransactionLineDetails.INVENTORY_TRANSACTION_LINE_SEQUENCE.eq(inventoryTransactionLineSequence));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionLineFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionLine getInventoryTransactionLineBySequence(InventoryTransaction inventoryTransaction,
            Integer inventoryTransactionLineSequence) {
        return getInventoryTransactionLineBySequence(inventoryTransaction, inventoryTransactionLineSequence, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionLine getInventoryTransactionLineBySequenceForUpdate(InventoryTransaction inventoryTransaction,
            Integer inventoryTransactionLineSequence) {
        return getInventoryTransactionLineBySequence(inventoryTransaction, inventoryTransactionLineSequence, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionLineDetailValue getInventoryTransactionLineDetailValueForUpdate(InventoryTransactionLine inventoryTransactionLine) {
        return inventoryTransactionLine == null ? null
                : inventoryTransactionLine.getLastDetailForUpdate().getInventoryTransactionLineDetailValue().clone();
    }

    public InventoryTransactionLineDetailValue getInventoryTransactionLineDetailValueBySequenceForUpdate(InventoryTransaction inventoryTransaction,
            Integer inventoryTransactionLineSequence) {
        return getInventoryTransactionLineDetailValueForUpdate(getInventoryTransactionLineBySequenceForUpdate(inventoryTransaction,
                inventoryTransactionLineSequence));
    }

    private List<InventoryTransactionLine> getInventoryTransactionLines(Condition condition, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryTransactionLines.fields())
                    .from(InventoryTransactionLines)
                    .join(InventoryTransactionLineDetails).onKey(INVENTORY_TRANSACTION_LINES_ACTIVE_DETAIL_FK)
                    .join(InventoryTransactions).on(InventoryTransactionLineDetails.INVENTORY_TRANSACTION.eq(InventoryTransactions.INVENTORY_TRANSACTION))
                    .join(InventoryTransactionDetails).onKey(INVENTORY_TRANSACTIONS_LAST_DETAIL_FK)
                    .join(InventoryTransactionTypes).on(InventoryTransactionDetails.INVENTORY_TRANSACTION_TYPE.eq(InventoryTransactionTypes.INVENTORY_TRANSACTION_TYPE))
                    .join(InventoryTransactionTypeDetails).onKey(INVENTORY_TRANSACTION_TYPES_LAST_DETAIL_FK)
                    .where(condition)
                    .orderBy(InventoryTransactionTypeDetails.SORT_ORDER, InventoryTransactionTypeDetails.INVENTORY_TRANSACTION_TYPE_NAME,
                            InventoryTransactionDetails.INVENTORY_TRANSACTION_NAME, InventoryTransactionLineDetails.INVENTORY_TRANSACTION_LINE_SEQUENCE),
                    InventoryTransactionLineFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryTransactionLines.fields())
                    .from(InventoryTransactionLines)
                    .join(InventoryTransactionLineDetails).onKey(INVENTORY_TRANSACTION_LINES_ACTIVE_DETAIL_FK)
                    .where(condition)
                    .forUpdate();
        };

        return inventoryTransactionLineFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public long countInventoryTransactionLinesByInventoryTransaction(InventoryTransaction inventoryTransaction) {
        return countInventoryTransactionLines(InventoryTransactionLineDetails.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey()));
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByInventoryTransaction(InventoryTransaction inventoryTransaction, EntityPermission entityPermission) {
        return getInventoryTransactionLines(InventoryTransactionLineDetails.INVENTORY_TRANSACTION.eq(inventoryTransaction.getPrimaryKey()), entityPermission);
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByInventoryTransaction(InventoryTransaction inventoryTransaction) {
        return getInventoryTransactionLinesByInventoryTransaction(inventoryTransaction, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByInventoryTransactionForUpdate(InventoryTransaction inventoryTransaction) {
        return getInventoryTransactionLinesByInventoryTransaction(inventoryTransaction, EntityPermission.READ_WRITE);
    }

    public void deleteInventoryTransactionLinesByInventoryTransaction(InventoryTransaction inventoryTransaction, BasePK deletedBy) {
        getInventoryTransactionLinesByInventoryTransactionForUpdate(inventoryTransaction).forEach(inventoryTransactionLine ->
                deleteInventoryTransactionLine(inventoryTransactionLine, deletedBy));
    }

    public long countInventoryTransactionLinesByInventoryTransactionReason(InventoryTransactionReason inventoryTransactionReason) {
        return countInventoryTransactionLines(InventoryTransactionLineDetails.INVENTORY_TRANSACTION_REASON.eq(inventoryTransactionReason.getPrimaryKey()));
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByInventoryTransactionReason(InventoryTransactionReason inventoryTransactionReason, EntityPermission entityPermission) {
        return getInventoryTransactionLines(InventoryTransactionLineDetails.INVENTORY_TRANSACTION_REASON.eq(inventoryTransactionReason.getPrimaryKey()), entityPermission);
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByInventoryTransactionReason(InventoryTransactionReason inventoryTransactionReason) {
        return getInventoryTransactionLinesByInventoryTransactionReason(inventoryTransactionReason, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByInventoryTransactionReasonForUpdate(InventoryTransactionReason inventoryTransactionReason) {
        return getInventoryTransactionLinesByInventoryTransactionReason(inventoryTransactionReason, EntityPermission.READ_WRITE);
    }

    public void deleteInventoryTransactionLinesByInventoryTransactionReason(InventoryTransactionReason inventoryTransactionReason, BasePK deletedBy) {
        getInventoryTransactionLinesByInventoryTransactionReasonForUpdate(inventoryTransactionReason).forEach(inventoryTransactionLine ->
                deleteInventoryTransactionLine(inventoryTransactionLine, deletedBy));
    }

    public long countInventoryTransactionLinesByItem(Item item) {
        return countInventoryTransactionLines(InventoryTransactionLineDetails.ITEM.eq(item.getPrimaryKey()));
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByItem(Item item, EntityPermission entityPermission) {
        return getInventoryTransactionLines(InventoryTransactionLineDetails.ITEM.eq(item.getPrimaryKey()), entityPermission);
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByItem(Item item) {
        return getInventoryTransactionLinesByItem(item, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByItemForUpdate(Item item) {
        return getInventoryTransactionLinesByItem(item, EntityPermission.READ_WRITE);
    }

    public void deleteInventoryTransactionLinesByItem(Item item, BasePK deletedBy) {
        getInventoryTransactionLinesByItemForUpdate(item).forEach(inventoryTransactionLine ->
                deleteInventoryTransactionLine(inventoryTransactionLine, deletedBy));
    }

    public long countInventoryTransactionLinesByLot(Lot lot) {
        return countInventoryTransactionLines(InventoryTransactionLineDetails.LOT.eq(lot.getPrimaryKey()));
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByLot(Lot lot, EntityPermission entityPermission) {
        return getInventoryTransactionLines(InventoryTransactionLineDetails.LOT.eq(lot.getPrimaryKey()), entityPermission);
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByLot(Lot lot) {
        return getInventoryTransactionLinesByLot(lot, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByLotForUpdate(Lot lot) {
        return getInventoryTransactionLinesByLot(lot, EntityPermission.READ_WRITE);
    }

    public void deleteInventoryTransactionLinesByLot(Lot lot, BasePK deletedBy) {
        getInventoryTransactionLinesByLotForUpdate(lot).forEach(inventoryTransactionLine ->
                deleteInventoryTransactionLine(inventoryTransactionLine, deletedBy));
    }

    public long countInventoryTransactionLinesByInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType) {
        return countInventoryTransactionLines(InventoryTransactionLineDetails.INVENTORY_ADJUSTMENT_TYPE.eq(inventoryAdjustmentType.getPrimaryKey()));
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType, EntityPermission entityPermission) {
        return getInventoryTransactionLines(InventoryTransactionLineDetails.INVENTORY_ADJUSTMENT_TYPE.eq(inventoryAdjustmentType.getPrimaryKey()), entityPermission);
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType) {
        return getInventoryTransactionLinesByInventoryAdjustmentType(inventoryAdjustmentType, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionLine> getInventoryTransactionLinesByInventoryAdjustmentTypeForUpdate(InventoryAdjustmentType inventoryAdjustmentType) {
        return getInventoryTransactionLinesByInventoryAdjustmentType(inventoryAdjustmentType, EntityPermission.READ_WRITE);
    }

    public void deleteInventoryTransactionLinesByInventoryAdjustmentType(InventoryAdjustmentType inventoryAdjustmentType, BasePK deletedBy) {
        getInventoryTransactionLinesByInventoryAdjustmentTypeForUpdate(inventoryAdjustmentType).forEach(inventoryTransactionLine ->
                deleteInventoryTransactionLine(inventoryTransactionLine, deletedBy));
    }

    public void updateInventoryTransactionLineFromValue(InventoryTransactionLineDetailValue inventoryTransactionLineDetailValue, BasePK updatedBy) {
        if(inventoryTransactionLineDetailValue.hasBeenModified()) {
            var inventoryTransactionLine = inventoryTransactionLineFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryTransactionLineDetailValue.getInventoryTransactionLinePK());
            var inventoryTransactionLineDetail = inventoryTransactionLine.getActiveDetailForUpdate();

            inventoryTransactionLineDetail.setThruTime(session.getStartTime());
            inventoryTransactionLineDetail.store();

            var inventoryTransactionLinePK = inventoryTransactionLineDetail.getInventoryTransactionLinePK(); // Not updated
            var inventoryTransactionPK = inventoryTransactionLineDetail.getInventoryTransactionPK(); // Not updated
            var inventoryTransactionLineSequence = inventoryTransactionLineDetail.getInventoryTransactionLineSequence(); // Not updated
            var inventoryTransactionReasonPK = inventoryTransactionLineDetailValue.getInventoryTransactionReasonPK();
            var itemPK = inventoryTransactionLineDetailValue.getItemPK();
            var lotPK = inventoryTransactionLineDetailValue.getLotPK();
            var inventoryAdjustmentTypePK = inventoryTransactionLineDetailValue.getInventoryAdjustmentTypePK();
            var unitAmount = inventoryTransactionLineDetailValue.getUnitAmount();
            var description = inventoryTransactionLineDetailValue.getDescription();

            inventoryTransactionLineDetail = inventoryTransactionLineDetailFactory.create(inventoryTransactionLinePK, inventoryTransactionPK,
                    inventoryTransactionLineSequence, inventoryTransactionReasonPK, itemPK, lotPK, inventoryAdjustmentTypePK, unitAmount, description,
                    session.getStartTime(), Session.MAX_TIME);

            inventoryTransactionLine.setActiveDetail(inventoryTransactionLineDetail);
            inventoryTransactionLine.setLastDetail(inventoryTransactionLineDetail);

            sendEvent(inventoryTransactionLinePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteInventoryTransactionLine(InventoryTransactionLine inventoryTransactionLine, BasePK deletedBy) {
        var inventoryTransactionLineDetail = inventoryTransactionLine.getLastDetailForUpdate();

        removeInventoryTransactionLineStatusByInventoryTransactionLine(inventoryTransactionLine);
        deleteInventoryTransactionLineSourceByInventoryTransactionLine(inventoryTransactionLine, deletedBy);
        // TODO: deleteInventoryTransactionLineDestinationByInventoryTransactionLine(inventoryTransactionLine, deletedBy);
        // TODO: deleteInventoryTransactionLineTimesByInventoryTransactionLine(inventoryTransactionLine, deletedBy);
        // TODO: deleteInventoryTransactionLineRolesByInventoryTransactionLine(inventoryTransactionLine, deletedBy);
        // TODO: deleteInventoryLayersByInventoryTransactionLine(inventoryTransactionLine, deletedBy);

        inventoryTransactionLineDetail.setThruTime(session.getStartTime());
        inventoryTransactionLine.setActiveDetail(null);
        inventoryTransactionLine.store();

        sendEvent(inventoryTransactionLine.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transaction Line Statuses
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryTransactionLineStatusFactory inventoryTransactionLineStatusFactory;

    public InventoryTransactionLineStatus createInventoryTransactionLineStatus(InventoryTransactionLine inventoryTransactionLine) {
        return inventoryTransactionLineStatusFactory.create(inventoryTransactionLine, 0L);
    }

    private InventoryTransactionLineStatus getInventoryTransactionLineStatus(InventoryTransactionLine inventoryTransactionLine,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionLineStatuses.fields())
                .from(InventoryTransactionLineStatuses)
                .where(InventoryTransactionLineStatuses.INVENTORY_TRANSACTION_LINE.eq(inventoryTransactionLine.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionLineStatusFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionLineStatus getInventoryTransactionLineStatus(InventoryTransactionLine inventoryTransactionLine) {
        return getInventoryTransactionLineStatus(inventoryTransactionLine, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionLineStatus getInventoryTransactionLineStatusForUpdate(InventoryTransactionLine inventoryTransactionLine) {
        return getInventoryTransactionLineStatus(inventoryTransactionLine, EntityPermission.READ_WRITE);
    }

    public void removeInventoryTransactionLineStatusByInventoryTransactionLine(InventoryTransactionLine inventoryTransactionLine) {
        var inventoryTransactionLineStatus = getInventoryTransactionLineStatusForUpdate(inventoryTransactionLine);

        if(inventoryTransactionLineStatus != null) {
            inventoryTransactionLineStatus.remove();
        }
    }

    // --------------------------------------------------------------------------------
    //   Inventory Transaction Line Sources
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryTransactionLineSourceFactory inventoryTransactionLineSourceFactory;

    public InventoryTransactionLineSource createInventoryTransactionLineSource(InventoryTransactionLine inventoryTransactionLine,
            Party sourceOwnerParty, Location sourceLocation, InventoryCondition sourceInventoryCondition,
            UnitOfMeasureType sourceUnitOfMeasureType, BasePK createdBy) {
        var inventoryTransactionLineSource = inventoryTransactionLineSourceFactory.create(inventoryTransactionLine, sourceOwnerParty,
                sourceLocation, sourceInventoryCondition, sourceUnitOfMeasureType, session.getStartTime(), Session.MAX_TIME);

        sendEvent(inventoryTransactionLine.getPrimaryKey(), EventTypes.MODIFY, inventoryTransactionLineSource.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return inventoryTransactionLineSource;
    }

    private long countInventoryTransactionLineSources(Condition condition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryTransactionLineSources)
                .where(condition, InventoryTransactionLineSources.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryTransactionLineSourcesByInventoryTransactionLine(InventoryTransactionLine inventoryTransactionLine) {
        return countInventoryTransactionLineSources(InventoryTransactionLineSources.INVENTORY_TRANSACTION_LINE.eq(inventoryTransactionLine.getPrimaryKey()));
    }

    public boolean inventoryTransactionLineSourceExists(InventoryTransactionLine inventoryTransactionLine) {
        return countInventoryTransactionLineSourcesByInventoryTransactionLine(inventoryTransactionLine) != 0;
    }

    public InventoryTransactionLineSource getInventoryTransactionLineSource(InventoryTransactionLine inventoryTransactionLine,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryTransactionLineSources.fields())
                .from(InventoryTransactionLineSources)
                .where(InventoryTransactionLineSources.INVENTORY_TRANSACTION_LINE.eq(inventoryTransactionLine.getPrimaryKey()),
                        InventoryTransactionLineSources.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryTransactionLineSourceFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryTransactionLineSource getInventoryTransactionLineSource(InventoryTransactionLine inventoryTransactionLine) {
        return getInventoryTransactionLineSource(inventoryTransactionLine, EntityPermission.READ_ONLY);
    }

    public InventoryTransactionLineSource getInventoryTransactionLineSourceForUpdate(InventoryTransactionLine inventoryTransactionLine) {
        return getInventoryTransactionLineSource(inventoryTransactionLine, EntityPermission.READ_WRITE);
    }

    public InventoryTransactionLineSourceValue getInventoryTransactionLineSourceValue(InventoryTransactionLineSource inventoryTransactionLineSource) {
        return inventoryTransactionLineSource == null ? null : inventoryTransactionLineSource.getInventoryTransactionLineSourceValue().clone();
    }

    public InventoryTransactionLineSourceValue getInventoryTransactionLineSourceValueForUpdate(InventoryTransactionLine inventoryTransactionLine) {
        return getInventoryTransactionLineSourceValue(getInventoryTransactionLineSourceForUpdate(inventoryTransactionLine));
    }

    private List<InventoryTransactionLineSource> getInventoryTransactionLineSources(Condition condition, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryTransactionLineSources.fields())
                    .from(InventoryTransactionLineSources)
                    .join(InventoryTransactionLines).on(InventoryTransactionLineSources.INVENTORY_TRANSACTION_LINE.eq(InventoryTransactionLines.INVENTORY_TRANSACTION_LINE))
                    .join(InventoryTransactionLineDetails).onKey(INVENTORY_TRANSACTION_LINES_LAST_DETAIL_FK)
                    .join(InventoryTransactions).on(InventoryTransactionLineDetails.INVENTORY_TRANSACTION.eq(InventoryTransactions.INVENTORY_TRANSACTION))
                    .join(InventoryTransactionDetails).onKey(INVENTORY_TRANSACTIONS_LAST_DETAIL_FK)
                    .join(InventoryTransactionTypes).on(InventoryTransactionDetails.INVENTORY_TRANSACTION_TYPE.eq(InventoryTransactionTypes.INVENTORY_TRANSACTION_TYPE))
                    .join(InventoryTransactionTypeDetails).onKey(INVENTORY_TRANSACTION_TYPES_LAST_DETAIL_FK)
                    .where(condition, InventoryTransactionLineSources.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(InventoryTransactionTypeDetails.SORT_ORDER, InventoryTransactionTypeDetails.INVENTORY_TRANSACTION_TYPE_NAME,
                            InventoryTransactionDetails.INVENTORY_TRANSACTION_NAME, InventoryTransactionLineDetails.INVENTORY_TRANSACTION_LINE_SEQUENCE),
                    InventoryTransactionLineSourceFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryTransactionLineSources.fields())
                    .from(InventoryTransactionLineSources)
                    .where(condition, InventoryTransactionLineSources.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryTransactionLineSourceFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public long countInventoryTransactionLineSourcesBySourceOwnerParty(Party sourceOwnerParty) {
        return countInventoryTransactionLineSources(InventoryTransactionLineSources.SOURCE_OWNER_PARTY.eq(sourceOwnerParty.getPrimaryKey()));
    }

    public List<InventoryTransactionLineSource> getInventoryTransactionLineSourcesBySourceOwnerParty(Party sourceOwnerParty,
            EntityPermission entityPermission) {
        return getInventoryTransactionLineSources(InventoryTransactionLineSources.SOURCE_OWNER_PARTY.eq(sourceOwnerParty.getPrimaryKey()), entityPermission);
    }

    public List<InventoryTransactionLineSource> getInventoryTransactionLineSourcesBySourceOwnerParty(Party sourceOwnerParty) {
        return getInventoryTransactionLineSourcesBySourceOwnerParty(sourceOwnerParty, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionLineSource> getInventoryTransactionLineSourcesBySourceOwnerPartyForUpdate(Party sourceOwnerParty) {
        return getInventoryTransactionLineSourcesBySourceOwnerParty(sourceOwnerParty, EntityPermission.READ_WRITE);
    }

    public void deleteInventoryTransactionLineSourcesBySourceOwnerParty(Party sourceOwnerParty, BasePK deletedBy) {
        deleteInventoryTransactionLineSources(getInventoryTransactionLineSourcesBySourceOwnerPartyForUpdate(sourceOwnerParty), deletedBy);
    }

    public long countInventoryTransactionLineSourcesBySourceLocation(Location sourceLocation) {
        return countInventoryTransactionLineSources(InventoryTransactionLineSources.SOURCE_LOCATION.eq(sourceLocation.getPrimaryKey()));
    }

    public List<InventoryTransactionLineSource> getInventoryTransactionLineSourcesBySourceLocation(Location sourceLocation,
            EntityPermission entityPermission) {
        return getInventoryTransactionLineSources(InventoryTransactionLineSources.SOURCE_LOCATION.eq(sourceLocation.getPrimaryKey()), entityPermission);
    }

    public List<InventoryTransactionLineSource> getInventoryTransactionLineSourcesBySourceLocation(Location sourceLocation) {
        return getInventoryTransactionLineSourcesBySourceLocation(sourceLocation, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionLineSource> getInventoryTransactionLineSourcesBySourceLocationForUpdate(Location sourceLocation) {
        return getInventoryTransactionLineSourcesBySourceLocation(sourceLocation, EntityPermission.READ_WRITE);
    }

    public void deleteInventoryTransactionLineSourcesBySourceLocation(Location sourceLocation, BasePK deletedBy) {
        deleteInventoryTransactionLineSources(getInventoryTransactionLineSourcesBySourceLocationForUpdate(sourceLocation), deletedBy);
    }

    public long countInventoryTransactionLineSourcesBySourceInventoryCondition(InventoryCondition sourceInventoryCondition) {
        return countInventoryTransactionLineSources(InventoryTransactionLineSources.SOURCE_INVENTORY_CONDITION.eq(sourceInventoryCondition.getPrimaryKey()));
    }

    public List<InventoryTransactionLineSource> getInventoryTransactionLineSourcesBySourceInventoryCondition(InventoryCondition sourceInventoryCondition,
            EntityPermission entityPermission) {
        return getInventoryTransactionLineSources(InventoryTransactionLineSources.SOURCE_INVENTORY_CONDITION.eq(sourceInventoryCondition.getPrimaryKey()), entityPermission);
    }

    public List<InventoryTransactionLineSource> getInventoryTransactionLineSourcesBySourceInventoryCondition(InventoryCondition sourceInventoryCondition) {
        return getInventoryTransactionLineSourcesBySourceInventoryCondition(sourceInventoryCondition, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionLineSource> getInventoryTransactionLineSourcesBySourceInventoryConditionForUpdate(InventoryCondition sourceInventoryCondition) {
        return getInventoryTransactionLineSourcesBySourceInventoryCondition(sourceInventoryCondition, EntityPermission.READ_WRITE);
    }

    public void deleteInventoryTransactionLineSourcesBySourceInventoryCondition(InventoryCondition sourceInventoryCondition, BasePK deletedBy) {
        deleteInventoryTransactionLineSources(getInventoryTransactionLineSourcesBySourceInventoryConditionForUpdate(sourceInventoryCondition), deletedBy);
    }

    public long countInventoryTransactionLineSourcesBySourceUnitOfMeasureType(UnitOfMeasureType sourceUnitOfMeasureType) {
        return countInventoryTransactionLineSources(InventoryTransactionLineSources.SOURCE_UNIT_OF_MEASURE_TYPE.eq(sourceUnitOfMeasureType.getPrimaryKey()));
    }

    public List<InventoryTransactionLineSource> getInventoryTransactionLineSourcesBySourceUnitOfMeasureType(UnitOfMeasureType sourceUnitOfMeasureType,
            EntityPermission entityPermission) {
        return getInventoryTransactionLineSources(InventoryTransactionLineSources.SOURCE_UNIT_OF_MEASURE_TYPE.eq(sourceUnitOfMeasureType.getPrimaryKey()), entityPermission);
    }

    public List<InventoryTransactionLineSource> getInventoryTransactionLineSourcesBySourceUnitOfMeasureType(UnitOfMeasureType sourceUnitOfMeasureType) {
        return getInventoryTransactionLineSourcesBySourceUnitOfMeasureType(sourceUnitOfMeasureType, EntityPermission.READ_ONLY);
    }

    public List<InventoryTransactionLineSource> getInventoryTransactionLineSourcesBySourceUnitOfMeasureTypeForUpdate(UnitOfMeasureType sourceUnitOfMeasureType) {
        return getInventoryTransactionLineSourcesBySourceUnitOfMeasureType(sourceUnitOfMeasureType, EntityPermission.READ_WRITE);
    }

    public void deleteInventoryTransactionLineSourcesBySourceUnitOfMeasureType(UnitOfMeasureType sourceUnitOfMeasureType, BasePK deletedBy) {
        deleteInventoryTransactionLineSources(getInventoryTransactionLineSourcesBySourceUnitOfMeasureTypeForUpdate(sourceUnitOfMeasureType), deletedBy);
    }

    public void updateInventoryTransactionLineSourceFromValue(InventoryTransactionLineSourceValue inventoryTransactionLineSourceValue,
            BasePK updatedBy) {
        if(inventoryTransactionLineSourceValue.hasBeenModified()) {
            var inventoryTransactionLineSource = inventoryTransactionLineSourceFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    inventoryTransactionLineSourceValue.getPrimaryKey());

            inventoryTransactionLineSource.setThruTime(session.getStartTime());
            inventoryTransactionLineSource.store();

            var inventoryTransactionLinePK = inventoryTransactionLineSource.getInventoryTransactionLinePK(); // Not updated

            inventoryTransactionLineSource = inventoryTransactionLineSourceFactory.create(inventoryTransactionLinePK,
                    inventoryTransactionLineSourceValue.getSourceOwnerPartyPK(), inventoryTransactionLineSourceValue.getSourceLocationPK(),
                    inventoryTransactionLineSourceValue.getSourceInventoryConditionPK(), inventoryTransactionLineSourceValue.getSourceUnitOfMeasureTypePK(),
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(inventoryTransactionLinePK, EventTypes.MODIFY, inventoryTransactionLineSource.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteInventoryTransactionLineSource(InventoryTransactionLineSource inventoryTransactionLineSource, BasePK deletedBy) {
        inventoryTransactionLineSource.setThruTime(session.getStartTime());

        sendEvent(inventoryTransactionLineSource.getInventoryTransactionLinePK(), EventTypes.MODIFY,
                inventoryTransactionLineSource.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteInventoryTransactionLineSources(List<InventoryTransactionLineSource> inventoryTransactionLineSources, BasePK deletedBy) {
        inventoryTransactionLineSources.forEach(inventoryTransactionLineSource -> deleteInventoryTransactionLineSource(inventoryTransactionLineSource, deletedBy));
    }

    public void deleteInventoryTransactionLineSourceByInventoryTransactionLine(InventoryTransactionLine inventoryTransactionLine, BasePK deletedBy) {
        var inventoryTransactionLineSource = getInventoryTransactionLineSourceForUpdate(inventoryTransactionLine);

        if(inventoryTransactionLineSource != null) {
            deleteInventoryTransactionLineSource(inventoryTransactionLineSource, deletedBy);
        }
    }

}
