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
import com.echothree.model.control.inventory.common.transfer.InventoryLocationBucketTransfer;
import com.echothree.model.control.inventory.common.transfer.PartyBucketTransfer;
import com.echothree.model.control.inventory.server.transfer.InventoryLocationBucketTransferCache;
import com.echothree.model.control.inventory.server.transfer.PartyBucketTransferCache;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryLocation;
import com.echothree.model.data.inventory.server.entity.InventoryLocationBucket;
import com.echothree.model.data.inventory.server.entity.PartyBucket;
import com.echothree.model.data.inventory.server.factory.InventoryLocationBucketFactory;
import com.echothree.model.data.inventory.server.factory.PartyBucketFactory;
import com.echothree.model.data.inventory.server.value.InventoryLocationBucketValue;
import com.echothree.model.data.inventory.server.value.PartyBucketValue;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.tables.inventory.InventoryBucketTypeDetails.InventoryBucketTypeDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryBucketTypes.InventoryBucketTypes;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionDetails.InventoryConditionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditions.InventoryConditions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryLocationBuckets.InventoryLocationBuckets;
import static com.echothree.model.jooq.server.tables.inventory.InventoryLocations.InventoryLocations;
import static com.echothree.model.jooq.server.tables.inventory.PartyBuckets.PartyBuckets;
import static com.echothree.model.jooq.server.tables.item.ItemDetails.ItemDetails;
import static com.echothree.model.jooq.server.tables.item.Items.Items;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.party.PartyTypes.PartyTypes;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureKindDetails.UnitOfMeasureKindDetails;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureKinds.UnitOfMeasureKinds;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypeDetails.UnitOfMeasureTypeDetails;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypes.UnitOfMeasureTypes;
import static com.echothree.model.jooq.server.tables.warehouse.LocationDetails.LocationDetails;
import static com.echothree.model.jooq.server.tables.warehouse.Locations.Locations;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.jooq.Condition;
import org.jooq.OrderField;

@CommandScope
public class BucketControl
        extends BaseModelControl {

    // --------------------------------------------------------------------------------
    //   Inventory Location Buckets
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryLocationBucketFactory inventoryLocationBucketFactory;

    @Inject
    InventoryLocationBucketTransferCache inventoryLocationBucketTransferCache;

    public InventoryLocationBucket createInventoryLocationBucket(InventoryLocation inventoryLocation,
            InventoryBucketType inventoryBucketType, Long quantity, BasePK createdBy) {
        var inventoryLocationBucket = inventoryLocationBucketFactory.create(inventoryLocation, inventoryBucketType, quantity);

        sendEvent(inventoryLocation.getLocationPK(), EventTypes.TOUCH, null, null, createdBy);

        return inventoryLocationBucket;
    }

    private long countInventoryLocationBuckets(Condition condition) {
        return session.getDslContext().selectCount().from(InventoryLocationBuckets).where(condition)
                .fetchOptional(0, Long.class).orElse(0L);
    }

    public long countInventoryLocationBucketsByInventoryLocation(InventoryLocation inventoryLocation) {
        return countInventoryLocationBuckets(InventoryLocationBuckets.INVENTORY_LOCATION.eq(inventoryLocation.getPrimaryKey()));
    }

    public long countInventoryLocationBucketsByInventoryBucketType(InventoryBucketType inventoryBucketType) {
        return countInventoryLocationBuckets(InventoryLocationBuckets.INVENTORY_BUCKET_TYPE.eq(inventoryBucketType.getPrimaryKey()));
    }

    private InventoryLocationBucket getInventoryLocationBucket(InventoryLocation inventoryLocation,
            InventoryBucketType inventoryBucketType, EntityPermission permission) {
        var baseQuery = session.getDslContext().select(InventoryLocationBuckets.fields())
                .from(InventoryLocationBuckets)
                .where(InventoryLocationBuckets.INVENTORY_LOCATION.eq(inventoryLocation.getPrimaryKey()),
                        InventoryLocationBuckets.INVENTORY_BUCKET_TYPE.eq(inventoryBucketType.getPrimaryKey()));
        var query = permission == EntityPermission.READ_WRITE ? baseQuery.forUpdate() : baseQuery;

        return inventoryLocationBucketFactory.getEntityFromQuery(permission, query);
    }

    public InventoryLocationBucket getInventoryLocationBucket(InventoryLocation inventoryLocation,
            InventoryBucketType inventoryBucketType) {
        return getInventoryLocationBucket(inventoryLocation, inventoryBucketType, EntityPermission.READ_ONLY);
    }

    public InventoryLocationBucket getInventoryLocationBucketForUpdate(InventoryLocation inventoryLocation,
            InventoryBucketType inventoryBucketType) {
        return getInventoryLocationBucket(inventoryLocation, inventoryBucketType, EntityPermission.READ_WRITE);
    }

    public InventoryLocationBucketValue getInventoryLocationBucketValue(InventoryLocationBucket inventoryLocationBucket) {
        return inventoryLocationBucket == null ? null : inventoryLocationBucket.getInventoryLocationBucketValue().clone();
    }

    public InventoryLocationBucketValue getInventoryLocationBucketValueForUpdate(InventoryLocation inventoryLocation,
            InventoryBucketType inventoryBucketType) {
        return getInventoryLocationBucketValue(getInventoryLocationBucketForUpdate(inventoryLocation, inventoryBucketType));
    }

    private List<InventoryLocationBucket> getInventoryLocationBuckets(Condition condition, EntityPermission permission,
            OrderField<?>... orderFields) {
        var query = switch(permission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryLocationBuckets.fields())
                    .from(InventoryLocationBuckets)
                    .join(InventoryLocations).on(InventoryLocationBuckets.INVENTORY_LOCATION.eq(InventoryLocations.INVENTORY_LOCATION))
                    .join(Locations).on(InventoryLocations.LOCATION.eq(Locations.LOCATION))
                    .join(LocationDetails).on(Locations.LAST_DETAIL.eq(LocationDetails.LOCATION_DETAIL))
                    .join(Parties).on(InventoryLocations.OWNER_PARTY.eq(Parties.PARTY))
                    .join(PartyDetails).on(Parties.LAST_DETAIL.eq(PartyDetails.PARTY_DETAIL))
                    .join(PartyTypes).on(PartyDetails.PARTY_TYPE.eq(PartyTypes.PARTY_TYPE))
                    .join(Items).on(InventoryLocations.ITEM.eq(Items.ITEM))
                    .join(ItemDetails).on(Items.LAST_DETAIL.eq(ItemDetails.ITEM_DETAIL))
                    .join(UnitOfMeasureTypes).on(InventoryLocations.UNIT_OF_MEASURE_TYPE.eq(UnitOfMeasureTypes.UNIT_OF_MEASURE_TYPE))
                    .join(UnitOfMeasureTypeDetails).on(UnitOfMeasureTypes.LAST_DETAIL.eq(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_DETAIL))
                    .join(UnitOfMeasureKinds).on(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(UnitOfMeasureKinds.UNIT_OF_MEASURE_KIND))
                    .join(UnitOfMeasureKindDetails).on(UnitOfMeasureKinds.LAST_DETAIL.eq(UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_DETAIL))
                    .join(InventoryConditions).on(InventoryLocations.INVENTORY_CONDITION.eq(InventoryConditions.INVENTORY_CONDITION))
                    .join(InventoryConditionDetails).on(InventoryConditions.LAST_DETAIL.eq(InventoryConditionDetails.INVENTORY_CONDITION_DETAIL))
                    .join(InventoryBucketTypes).on(InventoryLocationBuckets.INVENTORY_BUCKET_TYPE.eq(InventoryBucketTypes.INVENTORY_BUCKET_TYPE))
                    .join(InventoryBucketTypeDetails).on(InventoryBucketTypes.LAST_DETAIL.eq(InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_DETAIL))
                    .where(condition)
                    .orderBy(orderFields),
                    InventoryLocationBucketFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryLocationBuckets.fields())
                    .from(InventoryLocationBuckets)
                    .where(condition)
                    .forUpdate();
        };

        return inventoryLocationBucketFactory.getEntitiesFromQuery(permission, query);
    }

    private List<InventoryLocationBucket> getInventoryLocationBucketsByInventoryLocation(InventoryLocation inventoryLocation,
            EntityPermission permission) {
        return getInventoryLocationBuckets(InventoryLocationBuckets.INVENTORY_LOCATION.eq(inventoryLocation.getPrimaryKey()),
                permission, InventoryBucketTypeDetails.SORT_ORDER,
                InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_NAME);
    }

    public List<InventoryLocationBucket> getInventoryLocationBucketsByInventoryLocation(InventoryLocation inventoryLocation) {
        return getInventoryLocationBucketsByInventoryLocation(inventoryLocation, EntityPermission.READ_ONLY);
    }

    public List<InventoryLocationBucket> getInventoryLocationBucketsByInventoryLocationForUpdate(InventoryLocation inventoryLocation) {
        return getInventoryLocationBucketsByInventoryLocation(inventoryLocation, EntityPermission.READ_WRITE);
    }

    private List<InventoryLocationBucket> getInventoryLocationBucketsByInventoryBucketType(InventoryBucketType inventoryBucketType,
            EntityPermission permission) {
        return getInventoryLocationBuckets(InventoryLocationBuckets.INVENTORY_BUCKET_TYPE.eq(inventoryBucketType.getPrimaryKey()),
                permission, LocationDetails.LOCATION_NAME, PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME,
                PartyDetails.PARTY_NAME, ItemDetails.ITEM_NAME, UnitOfMeasureKindDetails.SORT_ORDER,
                UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME, UnitOfMeasureTypeDetails.SORT_ORDER,
                UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, InventoryConditionDetails.SORT_ORDER,
                InventoryConditionDetails.INVENTORY_CONDITION_NAME);
    }

    public List<InventoryLocationBucket> getInventoryLocationBucketsByInventoryBucketType(InventoryBucketType inventoryBucketType) {
        return getInventoryLocationBucketsByInventoryBucketType(inventoryBucketType, EntityPermission.READ_ONLY);
    }

    public List<InventoryLocationBucket> getInventoryLocationBucketsByInventoryBucketTypeForUpdate(InventoryBucketType inventoryBucketType) {
        return getInventoryLocationBucketsByInventoryBucketType(inventoryBucketType, EntityPermission.READ_WRITE);
    }

    public InventoryLocationBucketTransfer getInventoryLocationBucketTransfer(UserVisit userVisit,
            InventoryLocationBucket inventoryLocationBucket) {
        return inventoryLocationBucket == null ? null : inventoryLocationBucketTransferCache.getTransfer(userVisit, inventoryLocationBucket);
    }

    public InventoryLocationBucketTransfer getInventoryLocationBucketTransfer(UserVisit userVisit,
            InventoryLocation inventoryLocation, InventoryBucketType inventoryBucketType) {
        return getInventoryLocationBucketTransfer(userVisit, getInventoryLocationBucket(inventoryLocation, inventoryBucketType));
    }

    public List<InventoryLocationBucketTransfer> getInventoryLocationBucketTransfers(UserVisit userVisit,
            Collection<InventoryLocationBucket> inventoryLocationBuckets) {
        var transfers = new ArrayList<InventoryLocationBucketTransfer>(inventoryLocationBuckets.size());
        inventoryLocationBuckets.forEach(inventoryLocationBucket ->
                transfers.add(inventoryLocationBucketTransferCache.getTransfer(userVisit, inventoryLocationBucket)));
        return transfers;
    }

    public List<InventoryLocationBucketTransfer> getInventoryLocationBucketTransfersByInventoryLocation(UserVisit userVisit,
            InventoryLocation inventoryLocation) {
        return getInventoryLocationBucketTransfers(userVisit, getInventoryLocationBucketsByInventoryLocation(inventoryLocation));
    }

    public List<InventoryLocationBucketTransfer> getInventoryLocationBucketTransfersByInventoryBucketType(UserVisit userVisit,
            InventoryBucketType inventoryBucketType) {
        return getInventoryLocationBucketTransfers(userVisit, getInventoryLocationBucketsByInventoryBucketType(inventoryBucketType));
    }

    public void updateInventoryLocationBucketFromValue(InventoryLocationBucketValue value, BasePK updatedBy) {
        if(value.hasBeenModified()) {
            var inventoryLocationBucket = inventoryLocationBucketFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    value.getPrimaryKey());

            inventoryLocationBucket.setInventoryLocationBucketValue(value);
            inventoryLocationBucket.store();

            sendEvent(inventoryLocationBucket.getInventoryLocation().getLocationPK(), EventTypes.TOUCH, null, null, updatedBy);
        }
    }

    public void removeInventoryLocationBucket(InventoryLocationBucket inventoryLocationBucket, BasePK removedBy) {
        inventoryLocationBucket.remove();

        sendEvent(inventoryLocationBucket.getInventoryLocation().getLocationPK(), EventTypes.TOUCH, null, null, removedBy);
    }

    public void removeInventoryLocationBuckets(List<InventoryLocationBucket> inventoryLocationBuckets, BasePK removedBy) {
        inventoryLocationBuckets.forEach(inventoryLocationBucket -> removeInventoryLocationBucket(inventoryLocationBucket, removedBy));
    }

    public void removeInventoryLocationBucketsByInventoryLocation(InventoryLocation inventoryLocation, BasePK removedBy) {
        removeInventoryLocationBuckets(getInventoryLocationBucketsByInventoryLocationForUpdate(inventoryLocation), removedBy);
    }

    public void removeInventoryLocationBucketsByInventoryBucketType(InventoryBucketType inventoryBucketType, BasePK removedBy) {
        removeInventoryLocationBuckets(getInventoryLocationBucketsByInventoryBucketTypeForUpdate(inventoryBucketType), removedBy);
    }

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

    private List<PartyBucket> getPartyBuckets(Condition condition, EntityPermission permission,
            OrderField<?>... orderFields) {
        var query = switch(permission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyBuckets.fields())
                    .from(PartyBuckets)
                    .join(Parties).on(PartyBuckets.PARTY.eq(Parties.PARTY))
                    .join(PartyDetails).on(Parties.LAST_DETAIL.eq(PartyDetails.PARTY_DETAIL))
                    .join(PartyTypes).on(PartyDetails.PARTY_TYPE.eq(PartyTypes.PARTY_TYPE))
                    .join(Items).on(PartyBuckets.ITEM.eq(Items.ITEM))
                    .join(ItemDetails).on(Items.LAST_DETAIL.eq(ItemDetails.ITEM_DETAIL))
                    .join(UnitOfMeasureTypes).on(PartyBuckets.UNIT_OF_MEASURE_TYPE.eq(UnitOfMeasureTypes.UNIT_OF_MEASURE_TYPE))
                    .join(UnitOfMeasureTypeDetails).on(UnitOfMeasureTypes.LAST_DETAIL.eq(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_DETAIL))
                    .join(UnitOfMeasureKinds).on(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(UnitOfMeasureKinds.UNIT_OF_MEASURE_KIND))
                    .join(UnitOfMeasureKindDetails).on(UnitOfMeasureKinds.LAST_DETAIL.eq(UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_DETAIL))
                    .join(InventoryConditions).on(PartyBuckets.INVENTORY_CONDITION.eq(InventoryConditions.INVENTORY_CONDITION))
                    .join(InventoryConditionDetails).on(InventoryConditions.LAST_DETAIL.eq(InventoryConditionDetails.INVENTORY_CONDITION_DETAIL))
                    .join(InventoryBucketTypes).on(PartyBuckets.INVENTORY_BUCKET_TYPE.eq(InventoryBucketTypes.INVENTORY_BUCKET_TYPE))
                    .join(InventoryBucketTypeDetails).on(InventoryBucketTypes.LAST_DETAIL.eq(InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_DETAIL))
                    .where(condition)
                    .orderBy(orderFields), PartyBucketFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyBuckets.fields())
                    .from(PartyBuckets)
                    .where(condition)
                    .forUpdate();
        };

        return partyBucketFactory.getEntitiesFromQuery(permission, query);
    }

    private List<PartyBucket> getPartyBucketsByParty(Party party, EntityPermission permission) {
        return getPartyBuckets(PartyBuckets.PARTY.eq(party.getPrimaryKey()), permission,
                ItemDetails.ITEM_NAME, UnitOfMeasureKindDetails.SORT_ORDER,
                UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME, UnitOfMeasureTypeDetails.SORT_ORDER,
                UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, InventoryConditionDetails.SORT_ORDER,
                InventoryConditionDetails.INVENTORY_CONDITION_NAME, InventoryBucketTypeDetails.SORT_ORDER,
                InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_NAME);
    }

    public List<PartyBucket> getPartyBucketsByParty(Party value) {
        return getPartyBucketsByParty(value, EntityPermission.READ_ONLY);
    }

    public List<PartyBucket> getPartyBucketsByPartyForUpdate(Party value) {
        return getPartyBucketsByParty(value, EntityPermission.READ_WRITE);
    }

    private List<PartyBucket> getPartyBucketsByItem(Item item, EntityPermission permission) {
        return getPartyBuckets(PartyBuckets.ITEM.eq(item.getPrimaryKey()), permission,
                PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME,
                UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME,
                UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME,
                InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME,
                InventoryBucketTypeDetails.SORT_ORDER, InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_NAME);
    }

    public List<PartyBucket> getPartyBucketsByItem(Item value) {
        return getPartyBucketsByItem(value, EntityPermission.READ_ONLY);
    }

    public List<PartyBucket> getPartyBucketsByItemForUpdate(Item value) {
        return getPartyBucketsByItem(value, EntityPermission.READ_WRITE);
    }

    private List<PartyBucket> getPartyBucketsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType,
            EntityPermission permission) {
        return getPartyBuckets(PartyBuckets.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()), permission,
                PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME, ItemDetails.ITEM_NAME,
                InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME,
                InventoryBucketTypeDetails.SORT_ORDER, InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_NAME);
    }

    public List<PartyBucket> getPartyBucketsByUnitOfMeasureType(UnitOfMeasureType value) {
        return getPartyBucketsByUnitOfMeasureType(value, EntityPermission.READ_ONLY);
    }

    public List<PartyBucket> getPartyBucketsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType value) {
        return getPartyBucketsByUnitOfMeasureType(value, EntityPermission.READ_WRITE);
    }

    private List<PartyBucket> getPartyBucketsByInventoryCondition(InventoryCondition inventoryCondition,
            EntityPermission permission) {
        return getPartyBuckets(PartyBuckets.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()), permission,
                PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME, ItemDetails.ITEM_NAME,
                UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME,
                UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME,
                InventoryBucketTypeDetails.SORT_ORDER, InventoryBucketTypeDetails.INVENTORY_BUCKET_TYPE_NAME);
    }

    public List<PartyBucket> getPartyBucketsByInventoryCondition(InventoryCondition value) {
        return getPartyBucketsByInventoryCondition(value, EntityPermission.READ_ONLY);
    }

    public List<PartyBucket> getPartyBucketsByInventoryConditionForUpdate(InventoryCondition value) {
        return getPartyBucketsByInventoryCondition(value, EntityPermission.READ_WRITE);
    }

    private List<PartyBucket> getPartyBucketsByInventoryBucketType(InventoryBucketType inventoryBucketType,
            EntityPermission permission) {
        return getPartyBuckets(PartyBuckets.INVENTORY_BUCKET_TYPE.eq(inventoryBucketType.getPrimaryKey()), permission,
                PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME, ItemDetails.ITEM_NAME,
                UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME,
                UnitOfMeasureTypeDetails.SORT_ORDER, UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME,
                InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME);
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
        partyBucket.remove();

        sendEvent(partyBucket.getItemPK(), EventTypes.TOUCH, null, null, removedBy);
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
