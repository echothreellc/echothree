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
import com.echothree.model.control.inventory.common.transfer.InventoryLayerTransfer;
import com.echothree.model.control.inventory.server.transfer.InventoryLayerTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryLayerPK;
import com.echothree.model.data.inventory.server.entity.InventoryCostingPool;
import com.echothree.model.data.inventory.server.entity.InventoryLayer;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionLine;
import com.echothree.model.data.inventory.server.factory.InventoryLayerDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryLayerFactory;
import com.echothree.model.data.inventory.server.value.InventoryLayerDetailValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionDetails.InventoryConditionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditions.InventoryConditions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryCostingPoolDetails.InventoryCostingPoolDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryCostingPools.InventoryCostingPools;
import static com.echothree.model.jooq.server.tables.inventory.InventoryLayerDetails.InventoryLayerDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryLayers.InventoryLayers;
import static com.echothree.model.jooq.server.tables.item.ItemDetails.ItemDetails;
import static com.echothree.model.jooq.server.tables.item.Items.Items;
import static com.echothree.model.jooq.server.tables.party.PartyCompanies.PartyCompanies;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.jooq.Condition;
import org.jooq.OrderField;

@CommandScope
public class InventoryLayerControl
        extends BaseModelControl {

    // --------------------------------------------------------------------------------
    //   Inventory Layers
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryLayerDetailFactory inventoryLayerDetailFactory;

    @Inject
    protected InventoryLayerFactory inventoryLayerFactory;

    @Inject
    BucketControl bucketControl;

    @Inject
    InventoryCostingPoolControl inventoryCostingPoolControl;

    @Inject
    InventoryLayerTransferCache inventoryLayerTransferCache;

    protected InventoryLayerControl() {
        super();
    }

    public InventoryLayer createInventoryLayer(InventoryCostingPool inventoryCostingPool, Integer inventoryLayerSequence,
            InventoryTransactionLine inventoryTransactionLine, Long receiptQuantity, Long unitCost, String description, BasePK createdBy) {
        if(inventoryLayerSequence == null) {
            var inventoryCostingPoolStatus = inventoryCostingPoolControl.getInventoryCostingPoolStatusForUpdate(inventoryCostingPool);

            do {
                inventoryLayerSequence = inventoryCostingPoolStatus.getInventoryLayerSequence() + 1;
                inventoryCostingPoolStatus.setInventoryLayerSequence(inventoryLayerSequence);
            } while(inventoryLayerExists(inventoryCostingPool, inventoryLayerSequence));
        }

        var inventoryLayer = inventoryLayerFactory.create();
        var inventoryLayerDetail = inventoryLayerDetailFactory.create(inventoryLayer, inventoryCostingPool, inventoryLayerSequence,
                inventoryTransactionLine, receiptQuantity, unitCost, description, session.getStartTime(), Session.MAX_TIME);

        inventoryLayer = inventoryLayerFactory.getEntityFromPK(EntityPermission.READ_WRITE, inventoryLayer.getPrimaryKey());
        inventoryLayer.setActiveDetail(inventoryLayerDetail);
        inventoryLayer.setLastDetail(inventoryLayerDetail);
        inventoryLayer.store();

        sendEvent(inventoryLayer.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return inventoryLayer;
    }

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryLayer
     */
    public InventoryLayer getInventoryLayerByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        return inventoryLayerFactory.getEntityFromPK(entityPermission, new InventoryLayerPK(entityInstance.getEntityUniqueId()));
    }

    public InventoryLayer getInventoryLayerByEntityInstance(EntityInstance entityInstance) {
        return getInventoryLayerByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryLayer getInventoryLayerByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getInventoryLayerByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private long countInventoryLayers(Condition condition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryLayers)
                .join(InventoryLayerDetails).on(InventoryLayers.LAST_DETAIL.eq(InventoryLayerDetails.INVENTORY_LAYER_DETAIL))
                .where(condition, InventoryLayers.ACTIVE_DETAIL.isNotNull())
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryLayersByInventoryCostingPool(InventoryCostingPool inventoryCostingPool) {
        return countInventoryLayers(InventoryLayerDetails.INVENTORY_COSTING_POOL.eq(inventoryCostingPool.getPrimaryKey()));
    }

    public long countInventoryLayersByInventoryTransactionLine(InventoryTransactionLine inventoryTransactionLine) {
        return countInventoryLayers(InventoryLayerDetails.INVENTORY_TRANSACTION_LINE.eq(inventoryTransactionLine.getPrimaryKey()));
    }

    public boolean inventoryLayerExists(InventoryCostingPool inventoryCostingPool, Integer inventoryLayerSequence) {
        return countInventoryLayers(InventoryLayerDetails.INVENTORY_COSTING_POOL.eq(inventoryCostingPool.getPrimaryKey())
                .and(InventoryLayerDetails.INVENTORY_LAYER_SEQUENCE.eq(inventoryLayerSequence))) != 0;
    }

    public InventoryLayer getInventoryLayerBySequence(InventoryCostingPool inventoryCostingPool, Integer inventoryLayerSequence,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryLayers.fields())
                .from(InventoryLayers)
                .where(InventoryLayers.ACTIVE_DETAIL.in(session.getDslContext()
                        .select(InventoryLayerDetails.INVENTORY_LAYER_DETAIL)
                        .from(InventoryLayerDetails)
                        .where(InventoryLayerDetails.INVENTORY_COSTING_POOL.eq(inventoryCostingPool.getPrimaryKey()),
                                InventoryLayerDetails.INVENTORY_LAYER_SEQUENCE.eq(inventoryLayerSequence))));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryLayerFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryLayer getInventoryLayerBySequence(InventoryCostingPool inventoryCostingPool, Integer inventoryLayerSequence) {
        return getInventoryLayerBySequence(inventoryCostingPool, inventoryLayerSequence, EntityPermission.READ_ONLY);
    }

    public InventoryLayer getInventoryLayerBySequenceForUpdate(InventoryCostingPool inventoryCostingPool, Integer inventoryLayerSequence) {
        return getInventoryLayerBySequence(inventoryCostingPool, inventoryLayerSequence, EntityPermission.READ_WRITE);
    }

    public InventoryLayerDetailValue getInventoryLayerDetailValueForUpdate(InventoryLayer inventoryLayer) {
        return inventoryLayer == null ? null : inventoryLayer.getLastDetailForUpdate().getInventoryLayerDetailValue().clone();
    }

    public InventoryLayerDetailValue getInventoryLayerDetailValueBySequenceForUpdate(InventoryCostingPool inventoryCostingPool,
            Integer inventoryLayerSequence) {
        return getInventoryLayerDetailValueForUpdate(getInventoryLayerBySequenceForUpdate(inventoryCostingPool, inventoryLayerSequence));
    }

    private List<InventoryLayer> getInventoryLayers(Condition condition, EntityPermission entityPermission, OrderField<?>... orderFields) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryLayers.fields())
                    .from(InventoryLayers)
                    .join(InventoryLayerDetails).on(InventoryLayers.LAST_DETAIL.eq(InventoryLayerDetails.INVENTORY_LAYER_DETAIL))
                    .join(InventoryCostingPools).on(InventoryLayerDetails.INVENTORY_COSTING_POOL.eq(InventoryCostingPools.INVENTORY_COSTING_POOL))
                    .join(InventoryCostingPoolDetails).on(InventoryCostingPools.LAST_DETAIL.eq(InventoryCostingPoolDetails.INVENTORY_COSTING_POOL_DETAIL))
                    .join(PartyCompanies).on(InventoryCostingPoolDetails.COMPANY_PARTY.eq(PartyCompanies.PARTY))
                    .join(Items).on(InventoryCostingPoolDetails.ITEM.eq(Items.ITEM))
                    .join(ItemDetails).on(Items.LAST_DETAIL.eq(ItemDetails.ITEM_DETAIL))
                    .join(InventoryConditions).on(InventoryCostingPoolDetails.INVENTORY_CONDITION.eq(InventoryConditions.INVENTORY_CONDITION))
                    .join(InventoryConditionDetails).on(InventoryConditions.LAST_DETAIL.eq(InventoryConditionDetails.INVENTORY_CONDITION_DETAIL))
                    .where(condition, InventoryLayers.ACTIVE_DETAIL.isNotNull(), PartyCompanies.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(orderFields), InventoryLayerFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryLayers.fields())
                    .from(InventoryLayers)
                    .where(InventoryLayers.ACTIVE_DETAIL.in(session.getDslContext()
                            .select(InventoryLayerDetails.INVENTORY_LAYER_DETAIL)
                            .from(InventoryLayerDetails)
                            .where(condition)))
                    .forUpdate();
        };

        return inventoryLayerFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryLayer> getInventoryLayersByInventoryCostingPool(InventoryCostingPool inventoryCostingPool, EntityPermission entityPermission) {
        return getInventoryLayers(InventoryLayerDetails.INVENTORY_COSTING_POOL.eq(inventoryCostingPool.getPrimaryKey()),
                entityPermission, InventoryLayerDetails.INVENTORY_LAYER_SEQUENCE);
    }

    public List<InventoryLayer> getInventoryLayersByInventoryCostingPool(InventoryCostingPool inventoryCostingPool) {
        return getInventoryLayersByInventoryCostingPool(inventoryCostingPool, EntityPermission.READ_ONLY);
    }

    public List<InventoryLayer> getInventoryLayersByInventoryCostingPoolForUpdate(InventoryCostingPool inventoryCostingPool) {
        return getInventoryLayersByInventoryCostingPool(inventoryCostingPool, EntityPermission.READ_WRITE);
    }

    public List<InventoryLayerTransfer> getInventoryLayerTransfersByInventoryCostingPool(UserVisit userVisit, InventoryCostingPool inventoryCostingPool) {
        return getInventoryLayerTransfers(userVisit, getInventoryLayersByInventoryCostingPool(inventoryCostingPool));
    }

    public List<InventoryLayer> getInventoryLayersByInventoryTransactionLine(InventoryTransactionLine inventoryTransactionLine, EntityPermission entityPermission) {
        return getInventoryLayers(InventoryLayerDetails.INVENTORY_TRANSACTION_LINE.eq(inventoryTransactionLine.getPrimaryKey()),
                entityPermission, PartyCompanies.SORT_ORDER, PartyCompanies.PARTY_COMPANY_NAME, ItemDetails.ITEM_NAME,
                InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME,
                InventoryLayerDetails.INVENTORY_LAYER_SEQUENCE);
    }

    public List<InventoryLayer> getInventoryLayersByInventoryTransactionLine(InventoryTransactionLine inventoryTransactionLine) {
        return getInventoryLayersByInventoryTransactionLine(inventoryTransactionLine, EntityPermission.READ_ONLY);
    }

    public List<InventoryLayer> getInventoryLayersByInventoryTransactionLineForUpdate(InventoryTransactionLine inventoryTransactionLine) {
        return getInventoryLayersByInventoryTransactionLine(inventoryTransactionLine, EntityPermission.READ_WRITE);
    }

    public List<InventoryLayerTransfer> getInventoryLayerTransfersByInventoryTransactionLine(UserVisit userVisit, InventoryTransactionLine inventoryTransactionLine) {
        return getInventoryLayerTransfers(userVisit, getInventoryLayersByInventoryTransactionLine(inventoryTransactionLine));
    }

    public InventoryLayerTransfer getInventoryLayerTransfer(UserVisit userVisit, InventoryLayer inventoryLayer) {
        return inventoryLayer == null ? null : inventoryLayerTransferCache.getTransfer(userVisit, inventoryLayer);
    }

    public List<InventoryLayerTransfer> getInventoryLayerTransfers(UserVisit userVisit, Collection<InventoryLayer> inventoryLayers) {
        var transfers = new ArrayList<InventoryLayerTransfer>(inventoryLayers.size());
        inventoryLayers.forEach(inventoryLayer -> transfers.add(inventoryLayerTransferCache.getTransfer(userVisit, inventoryLayer)));
        return transfers;
    }

    public void updateInventoryLayerFromValue(InventoryLayerDetailValue value, BasePK updatedBy) {
        if(value.hasBeenModified()) {
            var inventoryLayer = inventoryLayerFactory.getEntityFromPK(EntityPermission.READ_WRITE, value.getInventoryLayerPK());
            var inventoryLayerDetail = inventoryLayer.getActiveDetailForUpdate();

            inventoryLayerDetail.setThruTime(session.getStartTime());
            inventoryLayerDetail.store();

            var inventoryLayerPK = inventoryLayerDetail.getInventoryLayerPK(); // Not updated
            var inventoryCostingPoolPK = inventoryLayerDetail.getInventoryCostingPoolPK(); // Not updated
            var inventoryLayerSequence = inventoryLayerDetail.getInventoryLayerSequence(); // Not updated
            var inventoryTransactionLinePK = inventoryLayerDetail.getInventoryTransactionLinePK(); // Not updated
            var receiptQuantity = value.getReceiptQuantity();
            var unitCost = value.getUnitCost();
            var description = value.getDescription();

            inventoryLayerDetail = inventoryLayerDetailFactory.create(inventoryLayerPK, inventoryCostingPoolPK, inventoryLayerSequence,
                    inventoryTransactionLinePK, receiptQuantity, unitCost, description, session.getStartTime(), Session.MAX_TIME);

            inventoryLayer.setActiveDetail(inventoryLayerDetail);
            inventoryLayer.setLastDetail(inventoryLayerDetail);

            sendEvent(inventoryLayerPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteInventoryLayer(InventoryLayer inventoryLayer, BasePK deletedBy) {
        inventoryLayer = inventoryLayerFactory.getEntityFromPK(EntityPermission.READ_WRITE, inventoryLayer.getPrimaryKey());
        var inventoryLayerDetail = inventoryLayer.getLastDetailForUpdate();

        bucketControl.removeInventoryLayerBucketsByInventoryLayer(inventoryLayer, deletedBy);

        inventoryLayerDetail.setThruTime(session.getStartTime());
        inventoryLayer.setActiveDetail(null);
        inventoryLayer.store();

        sendEvent(inventoryLayer.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteInventoryLayers(Collection<InventoryLayer> inventoryLayers, BasePK deletedBy) {
        inventoryLayers.forEach(inventoryLayer -> deleteInventoryLayer(inventoryLayer, deletedBy));
    }

    public void deleteInventoryLayersByInventoryCostingPool(InventoryCostingPool inventoryCostingPool, BasePK deletedBy) {
        deleteInventoryLayers(getInventoryLayersByInventoryCostingPoolForUpdate(inventoryCostingPool), deletedBy);
    }

    public void deleteInventoryLayersByInventoryTransactionLine(InventoryTransactionLine inventoryTransactionLine, BasePK deletedBy) {
        deleteInventoryLayers(getInventoryLayersByInventoryTransactionLineForUpdate(inventoryTransactionLine), deletedBy);
    }

}
