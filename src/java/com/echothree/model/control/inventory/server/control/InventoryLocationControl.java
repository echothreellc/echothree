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
import com.echothree.model.control.inventory.common.transfer.InventoryLocationTransfer;
import com.echothree.model.control.inventory.server.transfer.InventoryLocationTransferCache;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryLocation;
import com.echothree.model.data.inventory.server.factory.InventoryLocationFactory;
import com.echothree.model.data.inventory.server.value.InventoryLocationValue;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.server.entity.Location;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionDetails.InventoryConditionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditions.InventoryConditions;
import static com.echothree.model.jooq.server.tables.inventory.InventoryLocations.InventoryLocations;
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
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.jooq.Condition;
import org.jooq.OrderField;

@CommandScope
public class InventoryLocationControl
        extends BaseModelControl {

    @Inject
    BucketControl bucketControl;

    @Inject
    InventoryLocationTransferCache inventoryLocationTransferCache;

    /**
     * Creates a new instance of InventoryLocationControl
     */
    protected InventoryLocationControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Inventory Locations
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryLocationFactory inventoryLocationFactory;

    public InventoryLocation createInventoryLocation(Location location, Party ownerParty, Item item,
            UnitOfMeasureType unitOfMeasureType, InventoryCondition inventoryCondition, BasePK createdBy) {
        var inventoryLocation = inventoryLocationFactory.create(location, ownerParty, item, unitOfMeasureType,
                inventoryCondition, session.getStartTime(), Session.MAX_TIME);

        sendEvent(location.getPrimaryKey(), EventTypes.MODIFY, inventoryLocation.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return inventoryLocation;
    }

    private long countInventoryLocations(Condition condition) {
        return session.getDslContext()
                .selectCount()
                .from(InventoryLocations)
                .where(condition, InventoryLocations.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countInventoryLocationsByLocation(Location location) {
        return countInventoryLocations(InventoryLocations.LOCATION.eq(location.getPrimaryKey()));
    }

    public long countInventoryLocationsByOwnerParty(Party ownerParty) {
        return countInventoryLocations(InventoryLocations.OWNER_PARTY.eq(ownerParty.getPrimaryKey()));
    }

    public long countInventoryLocationsByItem(Item item) {
        return countInventoryLocations(InventoryLocations.ITEM.eq(item.getPrimaryKey()));
    }

    public long countInventoryLocationsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return countInventoryLocations(InventoryLocations.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()));
    }

    public long countInventoryLocationsByInventoryCondition(InventoryCondition inventoryCondition) {
        return countInventoryLocations(InventoryLocations.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()));
    }

    private InventoryLocation getInventoryLocation(Location location, Item item, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryLocations.fields())
                .from(InventoryLocations)
                .where(InventoryLocations.LOCATION.eq(location.getPrimaryKey()),
                        InventoryLocations.ITEM.eq(item.getPrimaryKey()),
                        InventoryLocations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryLocationFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryLocation getInventoryLocation(Location location, Item item) {
        return getInventoryLocation(location, item, EntityPermission.READ_ONLY);
    }

    public InventoryLocation getInventoryLocationForUpdate(Location location, Item item) {
        return getInventoryLocation(location, item, EntityPermission.READ_WRITE);
    }

    private InventoryLocation getInventoryLocation(Location location, Party ownerParty, Item item,
            UnitOfMeasureType unitOfMeasureType, InventoryCondition inventoryCondition,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(InventoryLocations.fields())
                .from(InventoryLocations)
                .where(InventoryLocations.LOCATION.eq(location.getPrimaryKey()),
                        InventoryLocations.OWNER_PARTY.eq(ownerParty.getPrimaryKey()),
                        InventoryLocations.ITEM.eq(item.getPrimaryKey()),
                        InventoryLocations.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        InventoryLocations.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        InventoryLocations.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return inventoryLocationFactory.getEntityFromQuery(entityPermission, query);
    }

    public InventoryLocation getInventoryLocation(Location location, Party ownerParty, Item item,
            UnitOfMeasureType unitOfMeasureType, InventoryCondition inventoryCondition) {
        return getInventoryLocation(location, ownerParty, item, unitOfMeasureType, inventoryCondition,
                EntityPermission.READ_ONLY);
    }

    public InventoryLocation getInventoryLocationForUpdate(Location location, Party ownerParty, Item item,
            UnitOfMeasureType unitOfMeasureType, InventoryCondition inventoryCondition) {
        return getInventoryLocation(location, ownerParty, item, unitOfMeasureType, inventoryCondition,
                EntityPermission.READ_WRITE);
    }

    public InventoryLocationValue getInventoryLocationValue(InventoryLocation inventoryLocation) {
        return inventoryLocation == null ? null : inventoryLocation.getInventoryLocationValue().clone();
    }

    public InventoryLocationValue getInventoryLocationValueForUpdate(Location location, Item item) {
        return getInventoryLocationValue(getInventoryLocationForUpdate(location, item));
    }

    public InventoryLocationValue getInventoryLocationValueForUpdate(Location location, Party ownerParty, Item item,
            UnitOfMeasureType unitOfMeasureType, InventoryCondition inventoryCondition) {
        return getInventoryLocationValue(getInventoryLocationForUpdate(location, ownerParty, item, unitOfMeasureType,
                inventoryCondition));
    }

    private List<InventoryLocation> getInventoryLocations(Condition condition, EntityPermission entityPermission,
            OrderField<?>... orderFields) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(InventoryLocations.fields())
                    .from(InventoryLocations)
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
                    .where(condition, InventoryLocations.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(orderFields), InventoryLocationFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(InventoryLocations.fields())
                    .from(InventoryLocations)
                    .where(condition, InventoryLocations.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return inventoryLocationFactory.getEntitiesFromQuery(entityPermission, query);
    }

    private List<InventoryLocation> getInventoryLocationsByLocation(Location location, EntityPermission entityPermission) {
        return getInventoryLocations(InventoryLocations.LOCATION.eq(location.getPrimaryKey()), entityPermission,
                PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME,
                ItemDetails.ITEM_NAME, UnitOfMeasureKindDetails.SORT_ORDER,
                UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME, UnitOfMeasureTypeDetails.SORT_ORDER,
                UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, InventoryConditionDetails.SORT_ORDER,
                InventoryConditionDetails.INVENTORY_CONDITION_NAME);
    }

    public List<InventoryLocation> getInventoryLocationsByLocation(Location location) {
        return getInventoryLocationsByLocation(location, EntityPermission.READ_ONLY);
    }

    public List<InventoryLocation> getInventoryLocationsByLocationForUpdate(Location location) {
        return getInventoryLocationsByLocation(location, EntityPermission.READ_WRITE);
    }

    private List<InventoryLocation> getInventoryLocationsByOwnerParty(Party ownerParty, EntityPermission entityPermission) {
        return getInventoryLocations(InventoryLocations.OWNER_PARTY.eq(ownerParty.getPrimaryKey()), entityPermission,
                LocationDetails.LOCATION_NAME, ItemDetails.ITEM_NAME, UnitOfMeasureKindDetails.SORT_ORDER,
                UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME, UnitOfMeasureTypeDetails.SORT_ORDER,
                UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, InventoryConditionDetails.SORT_ORDER,
                InventoryConditionDetails.INVENTORY_CONDITION_NAME);
    }

    public List<InventoryLocation> getInventoryLocationsByOwnerParty(Party ownerParty) {
        return getInventoryLocationsByOwnerParty(ownerParty, EntityPermission.READ_ONLY);
    }

    public List<InventoryLocation> getInventoryLocationsByOwnerPartyForUpdate(Party ownerParty) {
        return getInventoryLocationsByOwnerParty(ownerParty, EntityPermission.READ_WRITE);
    }

    private List<InventoryLocation> getInventoryLocationsByItem(Item item, EntityPermission entityPermission) {
        return getInventoryLocations(InventoryLocations.ITEM.eq(item.getPrimaryKey()), entityPermission,
                LocationDetails.LOCATION_NAME, PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME,
                PartyDetails.PARTY_NAME, UnitOfMeasureKindDetails.SORT_ORDER,
                UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME, UnitOfMeasureTypeDetails.SORT_ORDER,
                UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME, InventoryConditionDetails.SORT_ORDER,
                InventoryConditionDetails.INVENTORY_CONDITION_NAME);
    }

    public List<InventoryLocation> getInventoryLocationsByItem(Item item) {
        return getInventoryLocationsByItem(item, EntityPermission.READ_ONLY);
    }

    public List<InventoryLocation> getInventoryLocationsByItemForUpdate(Item item) {
        return getInventoryLocationsByItem(item, EntityPermission.READ_WRITE);
    }

    private List<InventoryLocation> getInventoryLocationsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType,
            EntityPermission entityPermission) {
        return getInventoryLocations(InventoryLocations.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()), entityPermission,
                LocationDetails.LOCATION_NAME, PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME,
                PartyDetails.PARTY_NAME, ItemDetails.ITEM_NAME, InventoryConditionDetails.SORT_ORDER,
                InventoryConditionDetails.INVENTORY_CONDITION_NAME);
    }

    public List<InventoryLocation> getInventoryLocationsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType) {
        return getInventoryLocationsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_ONLY);
    }

    public List<InventoryLocation> getInventoryLocationsByUnitOfMeasureTypeForUpdate(UnitOfMeasureType unitOfMeasureType) {
        return getInventoryLocationsByUnitOfMeasureType(unitOfMeasureType, EntityPermission.READ_WRITE);
    }

    private List<InventoryLocation> getInventoryLocationsByInventoryCondition(InventoryCondition inventoryCondition,
            EntityPermission entityPermission) {
        return getInventoryLocations(InventoryLocations.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()), entityPermission,
                LocationDetails.LOCATION_NAME, PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME,
                PartyDetails.PARTY_NAME, ItemDetails.ITEM_NAME, UnitOfMeasureKindDetails.SORT_ORDER,
                UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND_NAME, UnitOfMeasureTypeDetails.SORT_ORDER,
                UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE_NAME);
    }

    public List<InventoryLocation> getInventoryLocationsByInventoryCondition(InventoryCondition inventoryCondition) {
        return getInventoryLocationsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }

    public List<InventoryLocation> getInventoryLocationsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getInventoryLocationsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }

    public InventoryLocationTransfer getInventoryLocationTransfer(UserVisit userVisit, InventoryLocation inventoryLocation) {
        return inventoryLocation == null ? null : inventoryLocationTransferCache.getTransfer(userVisit, inventoryLocation);
    }

    public InventoryLocationTransfer getInventoryLocationTransfer(UserVisit userVisit, Location location, Item item) {
        return getInventoryLocationTransfer(userVisit, getInventoryLocation(location, item));
    }

    public InventoryLocationTransfer getInventoryLocationTransfer(UserVisit userVisit, Location location, Party ownerParty,
            Item item, UnitOfMeasureType unitOfMeasureType, InventoryCondition inventoryCondition) {
        return getInventoryLocationTransfer(userVisit, getInventoryLocation(location, ownerParty, item, unitOfMeasureType,
                inventoryCondition));
    }

    public List<InventoryLocationTransfer> getInventoryLocationTransfers(UserVisit userVisit,
            Collection<InventoryLocation> inventoryLocations) {
        var transfers = new ArrayList<InventoryLocationTransfer>(inventoryLocations.size());

        inventoryLocations.forEach(inventoryLocation ->
                transfers.add(inventoryLocationTransferCache.getTransfer(userVisit, inventoryLocation))
        );

        return transfers;
    }

    public List<InventoryLocationTransfer> getInventoryLocationTransfersByLocation(UserVisit userVisit, Location location) {
        return getInventoryLocationTransfers(userVisit, getInventoryLocationsByLocation(location));
    }

    public List<InventoryLocationTransfer> getInventoryLocationTransfersByOwnerParty(UserVisit userVisit, Party ownerParty) {
        return getInventoryLocationTransfers(userVisit, getInventoryLocationsByOwnerParty(ownerParty));
    }

    public List<InventoryLocationTransfer> getInventoryLocationTransfersByItem(UserVisit userVisit, Item item) {
        return getInventoryLocationTransfers(userVisit, getInventoryLocationsByItem(item));
    }

    public List<InventoryLocationTransfer> getInventoryLocationTransfersByUnitOfMeasureType(UserVisit userVisit,
            UnitOfMeasureType unitOfMeasureType) {
        return getInventoryLocationTransfers(userVisit, getInventoryLocationsByUnitOfMeasureType(unitOfMeasureType));
    }

    public List<InventoryLocationTransfer> getInventoryLocationTransfersByInventoryCondition(UserVisit userVisit,
            InventoryCondition inventoryCondition) {
        return getInventoryLocationTransfers(userVisit, getInventoryLocationsByInventoryCondition(inventoryCondition));
    }

    public void deleteInventoryLocation(InventoryLocation inventoryLocation, BasePK deletedBy) {
        bucketControl.removeInventoryLocationBucketsByInventoryLocation(inventoryLocation, deletedBy);

        inventoryLocation.setThruTime(session.getStartTime());

        sendEvent(inventoryLocation.getLocationPK(), EventTypes.MODIFY, inventoryLocation.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteInventoryLocations(List<InventoryLocation> inventoryLocations, BasePK deletedBy) {
        inventoryLocations.forEach(inventoryLocation -> deleteInventoryLocation(inventoryLocation, deletedBy));
    }

    public void deleteInventoryLocationsByLocation(Location location, BasePK deletedBy) {
        deleteInventoryLocations(getInventoryLocationsByLocationForUpdate(location), deletedBy);
    }

    public void deleteInventoryLocationsByOwnerParty(Party ownerParty, BasePK deletedBy) {
        deleteInventoryLocations(getInventoryLocationsByOwnerPartyForUpdate(ownerParty), deletedBy);
    }

    public void deleteInventoryLocationsByItem(Item item, BasePK deletedBy) {
        deleteInventoryLocations(getInventoryLocationsByItemForUpdate(item), deletedBy);
    }

    public void deleteInventoryLocationsByUnitOfMeasureType(UnitOfMeasureType unitOfMeasureType, BasePK deletedBy) {
        deleteInventoryLocations(getInventoryLocationsByUnitOfMeasureTypeForUpdate(unitOfMeasureType), deletedBy);
    }

    public void deleteInventoryLocationsByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        deleteInventoryLocations(getInventoryLocationsByInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }

}
