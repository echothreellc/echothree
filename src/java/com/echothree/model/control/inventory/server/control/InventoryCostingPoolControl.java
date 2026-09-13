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
import com.echothree.model.control.inventory.common.transfer.InventoryCostingPoolTransfer;
import com.echothree.model.control.inventory.server.transfer.InventoryCostingPoolTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryCostingPoolPK;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryCostingPool;
import com.echothree.model.data.inventory.server.entity.InventoryCostingPoolStatus;
import com.echothree.model.data.inventory.server.factory.InventoryCostingPoolDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryCostingPoolFactory;
import com.echothree.model.data.inventory.server.factory.InventoryCostingPoolStatusFactory;
import com.echothree.model.data.inventory.server.value.InventoryCostingPoolDetailValue;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionDetails.InventoryConditionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditions.InventoryConditions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryCostingPoolDetails.InventoryCostingPoolDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryCostingPoolStatuses.InventoryCostingPoolStatuses;
import static com.echothree.model.jooq.server.tables.inventory.InventoryCostingPools.InventoryCostingPools;
import static com.echothree.model.jooq.server.tables.item.ItemDetails.ItemDetails;
import static com.echothree.model.jooq.server.tables.item.Items.Items;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
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
public class InventoryCostingPoolControl
        extends BaseModelControl {

    // --------------------------------------------------------------------------------
    //   Inventory Costing Pools
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryCostingPoolDetailFactory inventoryCostingPoolDetailFactory;

    @Inject
    protected InventoryCostingPoolFactory inventoryCostingPoolFactory;

    @Inject
    InventoryLayerControl inventoryLayerControl;

    @Inject
    InventoryCostingPoolTransferCache inventoryCostingPoolTransferCache;

    protected InventoryCostingPoolControl() {
        super();
    }

    public InventoryCostingPool createInventoryCostingPool(Party companyParty, Item item,
            InventoryCondition inventoryCondition, BasePK createdBy) {
        var inventoryCostingPool = inventoryCostingPoolFactory.create();
        var detail = inventoryCostingPoolDetailFactory.create(inventoryCostingPool, companyParty, item,
                inventoryCondition, session.getStartTime(), Session.MAX_TIME);

        inventoryCostingPool = inventoryCostingPoolFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryCostingPool.getPrimaryKey());
        inventoryCostingPool.setActiveDetail(detail);
        inventoryCostingPool.setLastDetail(detail);
        inventoryCostingPool.store();

        createInventoryCostingPoolStatus(inventoryCostingPool);

        sendEvent(inventoryCostingPool.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return inventoryCostingPool;
    }

    /**
     * Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryCostingPool
     */
    public InventoryCostingPool getInventoryCostingPoolByEntityInstance(EntityInstance entityInstance,
            EntityPermission entityPermission) {
        return inventoryCostingPoolFactory.getEntityFromPK(entityPermission,
                new InventoryCostingPoolPK(entityInstance.getEntityUniqueId()));
    }

    public InventoryCostingPool getInventoryCostingPoolByEntityInstance(EntityInstance entityInstance) {
        return getInventoryCostingPoolByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryCostingPool getInventoryCostingPoolByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getInventoryCostingPoolByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public InventoryCostingPool getInventoryCostingPool(Party companyParty, Item item,
            InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryCostingPools.fields())
                .from(InventoryCostingPools)
                .where(InventoryCostingPools.ACTIVE_DETAIL.in(session.getDslContext()
                        .select(InventoryCostingPoolDetails.INVENTORY_COSTING_POOL_DETAIL)
                        .from(InventoryCostingPoolDetails)
                        .where(InventoryCostingPoolDetails.COMPANY_PARTY.eq(companyParty.getPrimaryKey()),
                                InventoryCostingPoolDetails.ITEM.eq(item.getPrimaryKey()),
                                InventoryCostingPoolDetails.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()))));

        var query = entityPermission == EntityPermission.READ_WRITE ? baseQuery.forUpdate() : baseQuery;

        return inventoryCostingPoolFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryCostingPool getInventoryCostingPool(Party companyParty, Item item,
            InventoryCondition inventoryCondition) {
        return getInventoryCostingPool(companyParty, item, inventoryCondition, EntityPermission.READ_ONLY);
    }

    public InventoryCostingPool getInventoryCostingPoolForUpdate(Party companyParty, Item item,
            InventoryCondition inventoryCondition) {
        return getInventoryCostingPool(companyParty, item, inventoryCondition, EntityPermission.READ_WRITE);
    }

    public InventoryCostingPoolDetailValue getInventoryCostingPoolDetailValueForUpdate(InventoryCostingPool inventoryCostingPool) {
        return inventoryCostingPool == null ? null
                : inventoryCostingPool.getLastDetailForUpdate().getInventoryCostingPoolDetailValue().clone();
    }

    public InventoryCostingPoolDetailValue getInventoryCostingPoolDetailValueForUpdate(Party companyParty, Item item,
            InventoryCondition inventoryCondition) {
        return getInventoryCostingPoolDetailValueForUpdate(getInventoryCostingPoolForUpdate(companyParty, item, inventoryCondition));
    }

    private long countInventoryCostingPools(Condition condition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryCostingPools)
                .join(InventoryCostingPoolDetails).on(InventoryCostingPools.LAST_DETAIL.eq(InventoryCostingPoolDetails.INVENTORY_COSTING_POOL_DETAIL))
                .where(condition, InventoryCostingPools.ACTIVE_DETAIL.isNotNull())
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryCostingPoolsByCompanyParty(Party companyParty) {
        return countInventoryCostingPools(InventoryCostingPoolDetails.COMPANY_PARTY.eq(companyParty.getPrimaryKey()));
    }

    public long countInventoryCostingPoolsByItem(Item item) {
        return countInventoryCostingPools(InventoryCostingPoolDetails.ITEM.eq(item.getPrimaryKey()));
    }

    public long countInventoryCostingPoolsByInventoryCondition(InventoryCondition inventoryCondition) {
        return countInventoryCostingPools(InventoryCostingPoolDetails.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()));
    }

    private List<InventoryCostingPool> getInventoryCostingPools(Condition condition, EntityPermission entityPermission,
            OrderField<?>... orderFields) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryCostingPools.fields())
                    .from(InventoryCostingPools)
                    .join(InventoryCostingPoolDetails).on(InventoryCostingPools.LAST_DETAIL.eq(InventoryCostingPoolDetails.INVENTORY_COSTING_POOL_DETAIL))
                    .join(Parties).on(InventoryCostingPoolDetails.COMPANY_PARTY.eq(Parties.PARTY))
                    .join(PartyCompanies).on(Parties.PARTY.eq(PartyCompanies.PARTY))
                    .join(Items).on(InventoryCostingPoolDetails.ITEM.eq(Items.ITEM))
                    .join(ItemDetails).on(Items.LAST_DETAIL.eq(ItemDetails.ITEM_DETAIL))
                    .join(InventoryConditions).on(InventoryCostingPoolDetails.INVENTORY_CONDITION.eq(InventoryConditions.INVENTORY_CONDITION))
                    .join(InventoryConditionDetails).on(InventoryConditions.LAST_DETAIL.eq(InventoryConditionDetails.INVENTORY_CONDITION_DETAIL))
                    .where(condition, InventoryCostingPools.ACTIVE_DETAIL.isNotNull(), PartyCompanies.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(orderFields), InventoryCostingPoolFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryCostingPools.fields())
                    .from(InventoryCostingPools)
                    .where(InventoryCostingPools.ACTIVE_DETAIL.in(session.getDslContext()
                            .select(InventoryCostingPoolDetails.INVENTORY_COSTING_POOL_DETAIL)
                            .from(InventoryCostingPoolDetails)
                            .where(condition)))
                    .forUpdate();
        };

        return inventoryCostingPoolFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<InventoryCostingPool> getInventoryCostingPoolsByCompanyParty(Party companyParty, EntityPermission entityPermission) {
        return getInventoryCostingPools(InventoryCostingPoolDetails.COMPANY_PARTY.eq(companyParty.getPrimaryKey()),
                entityPermission, ItemDetails.ITEM_NAME, InventoryConditionDetails.SORT_ORDER,
                InventoryConditionDetails.INVENTORY_CONDITION_NAME);
    }

    public List<InventoryCostingPool> getInventoryCostingPoolsByCompanyParty(Party companyParty) {
        return getInventoryCostingPoolsByCompanyParty(companyParty, EntityPermission.READ_ONLY);
    }

    public List<InventoryCostingPool> getInventoryCostingPoolsByCompanyPartyForUpdate(Party companyParty) {
        return getInventoryCostingPoolsByCompanyParty(companyParty, EntityPermission.READ_WRITE);
    }

    public List<InventoryCostingPoolTransfer> getInventoryCostingPoolTransfersByCompanyParty(UserVisit userVisit, Party companyParty) {
        return getInventoryCostingPoolTransfers(userVisit, getInventoryCostingPoolsByCompanyParty(companyParty));
    }

    public List<InventoryCostingPool> getInventoryCostingPoolsByItem(Item item, EntityPermission entityPermission) {
        return getInventoryCostingPools(InventoryCostingPoolDetails.ITEM.eq(item.getPrimaryKey()),
                entityPermission, PartyCompanies.SORT_ORDER, PartyCompanies.PARTY_COMPANY_NAME, InventoryConditionDetails.SORT_ORDER,
                InventoryConditionDetails.INVENTORY_CONDITION_NAME);
    }

    public List<InventoryCostingPool> getInventoryCostingPoolsByItem(Item item) {
        return getInventoryCostingPoolsByItem(item, EntityPermission.READ_ONLY);
    }

    public List<InventoryCostingPool> getInventoryCostingPoolsByItemForUpdate(Item item) {
        return getInventoryCostingPoolsByItem(item, EntityPermission.READ_WRITE);
    }

    public List<InventoryCostingPoolTransfer> getInventoryCostingPoolTransfersByItem(UserVisit userVisit, Item item) {
        return getInventoryCostingPoolTransfers(userVisit, getInventoryCostingPoolsByItem(item));
    }

    public List<InventoryCostingPool> getInventoryCostingPoolsByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        return getInventoryCostingPools(InventoryCostingPoolDetails.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                entityPermission, PartyCompanies.SORT_ORDER, PartyCompanies.PARTY_COMPANY_NAME, ItemDetails.ITEM_NAME);
    }

    public List<InventoryCostingPool> getInventoryCostingPoolsByInventoryCondition(InventoryCondition inventoryCondition) {
        return getInventoryCostingPoolsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }

    public List<InventoryCostingPool> getInventoryCostingPoolsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getInventoryCostingPoolsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }

    public List<InventoryCostingPoolTransfer> getInventoryCostingPoolTransfersByInventoryCondition(UserVisit userVisit, InventoryCondition inventoryCondition) {
        return getInventoryCostingPoolTransfers(userVisit, getInventoryCostingPoolsByInventoryCondition(inventoryCondition));
    }

    public InventoryCostingPoolTransfer getInventoryCostingPoolTransfer(UserVisit userVisit, InventoryCostingPool inventoryCostingPool) {
        return inventoryCostingPool == null ? null : inventoryCostingPoolTransferCache.getTransfer(userVisit, inventoryCostingPool);
    }

    public List<InventoryCostingPoolTransfer> getInventoryCostingPoolTransfers(UserVisit userVisit,
            Collection<InventoryCostingPool> inventoryCostingPools) {
        var transfers = new ArrayList<InventoryCostingPoolTransfer>(inventoryCostingPools.size());
        inventoryCostingPools.forEach(inventoryCostingPool ->
                transfers.add(inventoryCostingPoolTransferCache.getTransfer(userVisit, inventoryCostingPool)));
        return transfers;
    }

    public void updateInventoryCostingPoolFromValue(InventoryCostingPoolDetailValue value, BasePK updatedBy) {
        if(value.hasBeenModified()) {
            var inventoryCostingPool = inventoryCostingPoolFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    value.getInventoryCostingPoolPK());
            var inventoryCostingPoolDetail = inventoryCostingPool.getActiveDetailForUpdate();

            inventoryCostingPoolDetail.setThruTime(session.getStartTime());
            inventoryCostingPoolDetail.store();

            var inventoryCostingPoolPK = inventoryCostingPoolDetail.getInventoryCostingPoolPK(); // Not updated
            var companyPartyPK = inventoryCostingPoolDetail.getCompanyPartyPK(); // Not updated
            var itemPK = inventoryCostingPoolDetail.getItemPK(); // Not updated
            var inventoryConditionPK = inventoryCostingPoolDetail.getInventoryConditionPK(); // Not updated

            inventoryCostingPoolDetail = inventoryCostingPoolDetailFactory.create(inventoryCostingPoolPK, companyPartyPK,
                    itemPK, inventoryConditionPK, session.getStartTime(), Session.MAX_TIME);

            inventoryCostingPool.setActiveDetail(inventoryCostingPoolDetail);
            inventoryCostingPool.setLastDetail(inventoryCostingPoolDetail);

            sendEvent(inventoryCostingPool.getPrimaryKey(), EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void deleteInventoryCostingPool(InventoryCostingPool inventoryCostingPool, BasePK deletedBy) {
        inventoryCostingPool = inventoryCostingPoolFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                inventoryCostingPool.getPrimaryKey());
        var inventoryCostingPoolDetail = inventoryCostingPool.getLastDetailForUpdate();

        inventoryLayerControl.deleteInventoryLayersByInventoryCostingPool(inventoryCostingPool, deletedBy);
        removeInventoryCostingPoolStatusByInventoryCostingPool(inventoryCostingPool);

        inventoryCostingPoolDetail.setThruTime(session.getStartTime());
        inventoryCostingPool.setActiveDetail(null);
        inventoryCostingPool.store();

        sendEvent(inventoryCostingPool.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteInventoryCostingPools(Collection<InventoryCostingPool> inventoryCostingPools, BasePK deletedBy) {
        inventoryCostingPools.forEach(inventoryCostingPool -> deleteInventoryCostingPool(inventoryCostingPool, deletedBy));
    }

    public void deleteInventoryCostingPoolsByCompanyParty(Party companyParty, BasePK deletedBy) {
        deleteInventoryCostingPools(getInventoryCostingPoolsByCompanyPartyForUpdate(companyParty), deletedBy);
    }

    public void deleteInventoryCostingPoolsByItem(Item item, BasePK deletedBy) {
        deleteInventoryCostingPools(getInventoryCostingPoolsByItemForUpdate(item), deletedBy);
    }

    public void deleteInventoryCostingPoolsByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        deleteInventoryCostingPools(getInventoryCostingPoolsByInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Inventory Costing Pool Statuses
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryCostingPoolStatusFactory inventoryCostingPoolStatusFactory;

    public InventoryCostingPoolStatus createInventoryCostingPoolStatus(InventoryCostingPool inventoryCostingPool) {
        return inventoryCostingPoolStatusFactory.create(inventoryCostingPool, 0);
    }

    public InventoryCostingPoolStatus getInventoryCostingPoolStatus(InventoryCostingPool inventoryCostingPool,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryCostingPoolStatuses.fields())
                .from(InventoryCostingPoolStatuses)
                .where(InventoryCostingPoolStatuses.INVENTORY_COSTING_POOL.eq(inventoryCostingPool.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryCostingPoolStatusFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryCostingPoolStatus getInventoryCostingPoolStatus(InventoryCostingPool inventoryCostingPool) {
        return getInventoryCostingPoolStatus(inventoryCostingPool, EntityPermission.READ_ONLY);
    }

    public InventoryCostingPoolStatus getInventoryCostingPoolStatusForUpdate(InventoryCostingPool inventoryCostingPool) {
        return getInventoryCostingPoolStatus(inventoryCostingPool, EntityPermission.READ_WRITE);
    }

    private void removeInventoryCostingPoolStatusByInventoryCostingPool(InventoryCostingPool inventoryCostingPool) {
        var status = getInventoryCostingPoolStatusForUpdate(inventoryCostingPool);

        if(status != null) {
            status.remove();
        }
    }

}
