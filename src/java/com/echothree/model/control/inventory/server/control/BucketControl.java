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
import com.echothree.model.control.inventory.common.transfer.PartyBucketTransfer;
import com.echothree.model.control.inventory.server.transfer.PartyBucketTransferCache;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.PartyBucket;
import com.echothree.model.data.inventory.server.factory.PartyBucketFactory;
import com.echothree.model.data.inventory.server.value.PartyBucketValue;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.tables.inventory.InventoryBucketTypeDetails.InventoryBucketTypeDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryBucketTypes.InventoryBucketTypes;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionDetails.InventoryConditionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditions.InventoryConditions;
import static com.echothree.model.jooq.server.tables.inventory.PartyBuckets.PartyBuckets;
import static com.echothree.model.jooq.server.tables.item.ItemDetails.ItemDetails;
import static com.echothree.model.jooq.server.tables.item.Items.Items;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.party.PartyTypes.PartyTypes;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureKindDetails.UnitOfMeasureKindDetails;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypeDetails.UnitOfMeasureTypeDetails;
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

@CommandScope
public class BucketControl
        extends BaseModelControl {

    // --------------------------------------------------------------------------------
    //   Party Buckets
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyBucketFactory partyBucketFactory;

    @Inject
    PartyBucketTransferCache partyBucketTransferCache;

    protected BucketControl() {
        super();
    }

    public PartyBucket createPartyBucket(Party party, Item item, UnitOfMeasureType unitOfMeasureType,
            InventoryCondition inventoryCondition, InventoryBucketType inventoryBucketType, Long quantity, BasePK createdBy) {
        var partyBucket = partyBucketFactory.create(party, item, unitOfMeasureType, inventoryCondition, inventoryBucketType, quantity);

        sendEvent(item.getPrimaryKey(), EventTypes.TOUCH, null, null, createdBy);

        return partyBucket;
    }

    private long countPartyBuckets(Condition condition) {
        return session.getDslContext().selectCount().from(PartyBuckets).where(condition)
                .fetchOptional(0, Long.class).orElse(0L);
    }

    public long countPartyBucketsByParty(Party party) {
        return countPartyBuckets(PartyBuckets.PARTY.eq(party.getPrimaryKey()));
    }

    public long countPartyBucketsByItem(Item item) {
        return countPartyBuckets(PartyBuckets.ITEM.eq(item.getPrimaryKey()));
    }

    public long countPartyBucketsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return countPartyBuckets(PartyBuckets.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()));
    }

    public long countPartyBucketsByInventoryCondition(InventoryCondition inventoryCondition) {
        return countPartyBuckets(PartyBuckets.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()));
    }

    public long countPartyBucketsByInventoryBucketType(InventoryBucketType inventoryBucketType) {
        return countPartyBuckets(PartyBuckets.INVENTORY_BUCKET_TYPE.eq(inventoryBucketType.getPrimaryKey()));
    }

    private PartyBucket getPartyBucket(Party party, Item item, UnitOfMeasureType unitOfMeasureType,
            InventoryCondition inventoryCondition, InventoryBucketType inventoryBucketType, EntityPermission permission) {
        var baseQuery = session.getDslContext().select(PartyBuckets.fields())
                .from(PartyBuckets)
                .where(PartyBuckets.PARTY.eq(party.getPrimaryKey()), PartyBuckets.ITEM.eq(item.getPrimaryKey()),
                        PartyBuckets.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        PartyBuckets.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        PartyBuckets.INVENTORY_BUCKET_TYPE.eq(inventoryBucketType.getPrimaryKey()));

        var query = permission == EntityPermission.READ_WRITE ? baseQuery.forUpdate() : baseQuery;

        return partyBucketFactory.getEntityFromQuery(permission, query);
    }

    public PartyBucket getPartyBucket(Party party, Item item, UnitOfMeasureType unitOfMeasureType,
            InventoryCondition inventoryCondition, InventoryBucketType inventoryBucketType) {
        return getPartyBucket(party, item, unitOfMeasureType, inventoryCondition, inventoryBucketType, EntityPermission.READ_ONLY);
    }

    public PartyBucket getPartyBucketForUpdate(Party party, Item item, UnitOfMeasureType unitOfMeasureType,
            InventoryCondition inventoryCondition, InventoryBucketType inventoryBucketType) {
        return getPartyBucket(party, item, unitOfMeasureType, inventoryCondition, inventoryBucketType, EntityPermission.READ_WRITE);
    }

    public PartyBucketValue getPartyBucketValue(PartyBucket partyBucket) {
        return partyBucket == null ? null : partyBucket.getPartyBucketValue().clone();
    }

    public PartyBucketValue getPartyBucketValueForUpdate(Party party, Item item, UnitOfMeasureType unitOfMeasureType,
            InventoryCondition inventoryCondition, InventoryBucketType inventoryBucketType) {
        return getPartyBucketValue(getPartyBucketForUpdate(party, item, unitOfMeasureType, inventoryCondition, inventoryBucketType));
    }

    private List<PartyBucket> getPartyBucketsByParty(Party party, EntityPermission permission) {
        var query = switch(permission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyBuckets.fields())
                    .from(PartyBuckets)
                    .join(Items).on(PartyBuckets.ITEM.eq(Items.ITEM))
                    .join(ItemDetails).on(Items.LAST_DETAIL.eq(ItemDetails.ITEM_DETAIL))
                    .join(UnitOfMeasureTypeDetails).on(PartyBuckets.UNIT_OF_MEASURE_TYPE.eq(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE))
                    .join(UnitOfMeasureKindDetails).on(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND))
                    .join(InventoryConditions).on(PartyBuckets.INVENTORY_CONDITION.eq(InventoryConditions.INVENTORY_CONDITION))
                    .join(InventoryConditionDetails).on(InventoryConditions.LAST_DETAIL.eq(InventoryConditionDetails.INVENTORY_CONDITION_DETAIL))
                    .join(InventoryBucketTypes).on(PartyBuckets.INVENTORY_BUCKET_TYPE.eq(InventoryBucketTypes.INVENTORY_BUCKET_TYPE))
                    .join(InventoryBucketTypeDetails).on(InventoryBucketTypes.LAST_DETAIL.eq(InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_DETAIL))
                    .where(PartyBuckets.PARTY.eq(party.getPrimaryKey()),
                            UnitOfMeasureTypeDetails.THRU_TIME.eq(Session.MAX_TIME),
                            UnitOfMeasureKindDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME,
                            UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME,
                            UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME,
                            InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME,
                            InventoryBucketTypeDetails.SORT_ORDER, InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_NAME), PartyBucketFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyBuckets.fields())
                    .from(PartyBuckets)
                    .where(PartyBuckets.PARTY.eq(party.getPrimaryKey()))
                    .forUpdate();
        };

        return partyBucketFactory.getEntitiesFromQuery(permission, query);
    }

    public List<PartyBucket> getPartyBucketsByParty(Party value) {
        return getPartyBucketsByParty(value, EntityPermission.READ_ONLY);
    }

    public List<PartyBucket> getPartyBucketsByPartyForUpdate(Party value) {
        return getPartyBucketsByParty(value, EntityPermission.READ_WRITE);
    }

    private List<PartyBucket> getPartyBucketsByItem(Item value, EntityPermission permission) {
        var query = switch(permission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyBuckets.fields())
                    .from(PartyBuckets)
                    .join(Parties).on(PartyBuckets.PARTY.eq(Parties.PARTY))
                    .join(PartyDetails).on(Parties.LAST_DETAIL.eq(PartyDetails.PARTY_DETAIL))
                    .join(PartyTypes).on(PartyDetails.PARTY_TYPE.eq(PartyTypes.PARTY_TYPE))
                    .join(UnitOfMeasureTypeDetails).on(PartyBuckets.UNIT_OF_MEASURE_TYPE.eq(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE))
                    .join(UnitOfMeasureKindDetails).on(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND))
                    .join(InventoryConditions).on(PartyBuckets.INVENTORY_CONDITION.eq(InventoryConditions.INVENTORY_CONDITION))
                    .join(InventoryConditionDetails).on(InventoryConditions.LAST_DETAIL.eq(InventoryConditionDetails.INVENTORY_CONDITION_DETAIL))
                    .join(InventoryBucketTypes).on(PartyBuckets.INVENTORY_BUCKET_TYPE.eq(InventoryBucketTypes.INVENTORY_BUCKET_TYPE))
                    .join(InventoryBucketTypeDetails).on(InventoryBucketTypes.LAST_DETAIL.eq(InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_DETAIL))
                    .where(PartyBuckets.ITEM.eq(value.getPrimaryKey()),
                            UnitOfMeasureTypeDetails.THRU_TIME.eq(Session.MAX_TIME),
                            UnitOfMeasureKindDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME,
                            UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME,
                            UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME,
                            InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME,
                            InventoryBucketTypeDetails.SORT_ORDER, InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_NAME), PartyBucketFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyBuckets.fields())
                    .from(PartyBuckets)
                    .where(PartyBuckets.ITEM.eq(value.getPrimaryKey()))
                    .forUpdate();
        };

        return partyBucketFactory.getEntitiesFromQuery(permission, query);
    }

    public List<PartyBucket> getPartyBucketsByItem(Item value) {
        return getPartyBucketsByItem(value, EntityPermission.READ_ONLY);
    }

    public List<PartyBucket> getPartyBucketsByItemForUpdate(Item value) {
        return getPartyBucketsByItem(value, EntityPermission.READ_WRITE);
    }

    private List<PartyBucket> getPartyBucketsByUnitOfMeasureType(UnitOfMeasureType value, EntityPermission permission) {
        var query = switch(permission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyBuckets.fields())
                    .from(PartyBuckets)
                    .join(Parties).on(PartyBuckets.PARTY.eq(Parties.PARTY))
                    .join(PartyDetails).on(Parties.LAST_DETAIL.eq(PartyDetails.PARTY_DETAIL))
                    .join(PartyTypes).on(PartyDetails.PARTY_TYPE.eq(PartyTypes.PARTY_TYPE))
                    .join(Items).on(PartyBuckets.ITEM.eq(Items.ITEM))
                    .join(ItemDetails).on(Items.LAST_DETAIL.eq(ItemDetails.ITEM_DETAIL))
                    .join(InventoryConditions).on(PartyBuckets.INVENTORY_CONDITION.eq(InventoryConditions.INVENTORY_CONDITION))
                    .join(InventoryConditionDetails).on(InventoryConditions.LAST_DETAIL.eq(InventoryConditionDetails.INVENTORY_CONDITION_DETAIL))
                    .join(InventoryBucketTypes).on(PartyBuckets.INVENTORY_BUCKET_TYPE.eq(InventoryBucketTypes.INVENTORY_BUCKET_TYPE))
                    .join(InventoryBucketTypeDetails).on(InventoryBucketTypes.LAST_DETAIL.eq(InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_DETAIL))
                    .where(PartyBuckets.UNIT_OF_MEASURE_TYPE.eq(value.getPrimaryKey()))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME, ItemDetails.ITEM_NAME,
                            InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME,
                            InventoryBucketTypeDetails.SORT_ORDER, InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_NAME), PartyBucketFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyBuckets.fields())
                    .from(PartyBuckets)
                    .where(PartyBuckets.UNIT_OF_MEASURE_TYPE.eq(value.getPrimaryKey()))
                    .forUpdate();
        };

        return partyBucketFactory.getEntitiesFromQuery(permission, query);
    }

    public List<PartyBucket> getPartyBucketsByUnitOfMeasureType(UnitOfMeasureType value) {
        return getPartyBucketsByUnitOfMeasureType(value, EntityPermission.READ_ONLY);
    }

    public List<PartyBucket> getPartyBucketsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType value) {
        return getPartyBucketsByUnitOfMeasureType(value, EntityPermission.READ_WRITE);
    }

    private List<PartyBucket> getPartyBucketsByInventoryCondition(InventoryCondition value, EntityPermission permission) {
        var query = switch(permission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyBuckets.fields())
                    .from(PartyBuckets)
                    .join(Parties).on(PartyBuckets.PARTY.eq(Parties.PARTY))
                    .join(PartyDetails).on(Parties.LAST_DETAIL.eq(PartyDetails.PARTY_DETAIL))
                    .join(PartyTypes).on(PartyDetails.PARTY_TYPE.eq(PartyTypes.PARTY_TYPE))
                    .join(Items).on(PartyBuckets.ITEM.eq(Items.ITEM))
                    .join(ItemDetails).on(Items.LAST_DETAIL.eq(ItemDetails.ITEM_DETAIL))
                    .join(UnitOfMeasureTypeDetails).on(PartyBuckets.UNIT_OF_MEASURE_TYPE.eq(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE))
                    .join(UnitOfMeasureKindDetails).on(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND))
                    .join(InventoryBucketTypes).on(PartyBuckets.INVENTORY_BUCKET_TYPE.eq(InventoryBucketTypes.INVENTORY_BUCKET_TYPE))
                    .join(InventoryBucketTypeDetails).on(InventoryBucketTypes.LAST_DETAIL.eq(InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_DETAIL))
                    .where(PartyBuckets.INVENTORY_CONDITION.eq(value.getPrimaryKey()),
                            UnitOfMeasureTypeDetails.THRU_TIME.eq(Session.MAX_TIME),
                            UnitOfMeasureKindDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME,
                            ItemDetails.ITEM_NAME,
                            UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME,
                            UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME,
                            InventoryBucketTypeDetails.SORT_ORDER, InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_NAME), PartyBucketFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyBuckets.fields())
                    .from(PartyBuckets)
                    .where(PartyBuckets.INVENTORY_CONDITION.eq(value.getPrimaryKey()))
                    .forUpdate();
        };

        return partyBucketFactory.getEntitiesFromQuery(permission, query);
    }

    public List<PartyBucket> getPartyBucketsByInventoryCondition(InventoryCondition value) {
        return getPartyBucketsByInventoryCondition(value, EntityPermission.READ_ONLY);
    }

    public List<PartyBucket> getPartyBucketsByInventoryConditionForUpdate(InventoryCondition value) {
        return getPartyBucketsByInventoryCondition(value, EntityPermission.READ_WRITE);
    }

    private List<PartyBucket> getPartyBucketsByInventoryBucketType(InventoryBucketType value, EntityPermission permission) {
        var query = switch(permission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyBuckets.fields())
                    .from(PartyBuckets)
                    .join(Parties).on(PartyBuckets.PARTY.eq(Parties.PARTY))
                    .join(PartyDetails).on(Parties.LAST_DETAIL.eq(PartyDetails.PARTY_DETAIL))
                    .join(PartyTypes).on(PartyDetails.PARTY_TYPE.eq(PartyTypes.PARTY_TYPE))
                    .join(Items).on(PartyBuckets.ITEM.eq(Items.ITEM))
                    .join(ItemDetails).on(Items.LAST_DETAIL.eq(ItemDetails.ITEM_DETAIL))
                    .join(UnitOfMeasureTypeDetails).on(PartyBuckets.UNIT_OF_MEASURE_TYPE.eq(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE))
                    .join(UnitOfMeasureKindDetails).on(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND))
                    .join(InventoryConditions).on(PartyBuckets.INVENTORY_CONDITION.eq(InventoryConditions.INVENTORY_CONDITION))
                    .join(InventoryConditionDetails).on(InventoryConditions.LAST_DETAIL.eq(InventoryConditionDetails.INVENTORY_CONDITION_DETAIL))
                    .where(PartyBuckets.INVENTORY_BUCKET_TYPE.eq(value.getPrimaryKey()),
                            UnitOfMeasureTypeDetails.THRU_TIME.eq(Session.MAX_TIME), UnitOfMeasureKindDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME,
                            ItemDetails.ITEM_NAME,
                            UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME,
                            UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME,
                            InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME), PartyBucketFactory.class);
            case READ_WRITE -> session.getDslContext().
                    select(PartyBuckets.fields())
                    .from(PartyBuckets)
                    .where(PartyBuckets.INVENTORY_BUCKET_TYPE.eq(value.getPrimaryKey())).forUpdate();
        };

        return partyBucketFactory.getEntitiesFromQuery(permission, query);
    }

    public List<PartyBucket> getPartyBucketsByInventoryBucketType(InventoryBucketType value) {
        return getPartyBucketsByInventoryBucketType(value, EntityPermission.READ_ONLY);
    }

    public List<PartyBucket> getPartyBucketsByInventoryBucketTypeForUpdate(InventoryBucketType value) {
        return getPartyBucketsByInventoryBucketType(value, EntityPermission.READ_WRITE);
    }

    public PartyBucketTransfer getPartyBucketTransfer(UserVisit userVisit, PartyBucket partyBucket) {
        return partyBucket == null ? null : partyBucketTransferCache.getTransfer(userVisit, partyBucket);
    }

    public PartyBucketTransfer getPartyBucketTransfer(UserVisit userVisit, Party party, Item item,
            UnitOfMeasureType unitOfMeasureType, InventoryCondition inventoryCondition,
            InventoryBucketType inventoryBucketType) {
        return getPartyBucketTransfer(userVisit, getPartyBucket(party, item, unitOfMeasureType, inventoryCondition, inventoryBucketType));
    }

    public List<PartyBucketTransfer> getPartyBucketTransfers(UserVisit userVisit, Collection<PartyBucket> partyBuckets) {
        List<PartyBucketTransfer> transfers = new ArrayList<>(partyBuckets.size());
        partyBuckets.forEach(partyBucket -> transfers.add(partyBucketTransferCache.getTransfer(userVisit, partyBucket)));
        return transfers;
    }

    public List<PartyBucketTransfer> getPartyBucketTransfersByParty(UserVisit userVisit, Party value) {
        return getPartyBucketTransfers(userVisit, getPartyBucketsByParty(value));
    }

    public List<PartyBucketTransfer> getPartyBucketTransfersByItem(UserVisit userVisit, Item value) {
        return getPartyBucketTransfers(userVisit, getPartyBucketsByItem(value));
    }

    public List<PartyBucketTransfer> getPartyBucketTransfersByUnitOfMeasureType(UserVisit userVisit, UnitOfMeasureType value) {
        return getPartyBucketTransfers(userVisit, getPartyBucketsByUnitOfMeasureType(value));
    }

    public List<PartyBucketTransfer> getPartyBucketTransfersByInventoryCondition(UserVisit userVisit, InventoryCondition value) {
        return getPartyBucketTransfers(userVisit, getPartyBucketsByInventoryCondition(value));
    }

    public List<PartyBucketTransfer> getPartyBucketTransfersByInventoryBucketType(UserVisit userVisit, InventoryBucketType value) {
        return getPartyBucketTransfers(userVisit, getPartyBucketsByInventoryBucketType(value));
    }

    public void updatePartyBucketFromValue(PartyBucketValue value, BasePK updatedBy) {
        if(value.hasBeenModified()) {
            var partyBucket = partyBucketFactory.getEntityFromPK(EntityPermission.READ_WRITE, value.getPrimaryKey());

            partyBucket.setPartyBucketValue(value);
            partyBucket.store();

            sendEvent(partyBucket.getItemPK(), EventTypes.TOUCH, null, null, updatedBy);
        }
    }

    public void removePartyBucket(PartyBucket partyBucket, BasePK removedBy) {
        sendEvent(partyBucket.getItemPK(), EventTypes.TOUCH, null, null, removedBy);
        partyBucket.remove();
    }

    public void removePartyBuckets(List<PartyBucket> partyBuckets, BasePK removedBy) {
        partyBuckets.forEach(value -> removePartyBucket(value, removedBy));
    }

    public void removePartyBucketsByParty(Party value, BasePK removedBy) {
        removePartyBuckets(getPartyBucketsByPartyForUpdate(value), removedBy);
    }

    public void removePartyBucketsByItem(Item value, BasePK removedBy) {
        removePartyBuckets(getPartyBucketsByItemForUpdate(value), removedBy);
    }

    public void removePartyBucketsByUnitOfMeasureType(UnitOfMeasureType value, BasePK removedBy) {
        removePartyBuckets(getPartyBucketsByUnitOfMeasureTypeForUpdate(value), removedBy);
    }

    public void removePartyBucketsByInventoryCondition(InventoryCondition value, BasePK removedBy) {
        removePartyBuckets(getPartyBucketsByInventoryConditionForUpdate(value), removedBy);
    }

    public void removePartyBucketsByInventoryBucketType(InventoryBucketType value, BasePK removedBy) {
        removePartyBuckets(getPartyBucketsByInventoryBucketTypeForUpdate(value), removedBy);
    }

}
