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
import com.echothree.model.control.inventory.common.transfer.PartyInventoryLevelTransfer;
import com.echothree.model.control.inventory.server.transfer.PartyInventoryLevelTransferCache;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.PartyInventoryLevel;
import com.echothree.model.data.inventory.server.factory.PartyInventoryLevelFactory;
import com.echothree.model.data.inventory.server.value.PartyInventoryLevelValue;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditionDetails.InventoryConditionDetails;
import static com.echothree.model.jooq.server.tables.inventory.InventoryConditions.InventoryConditions;
import static com.echothree.model.jooq.server.tables.inventory.PartyInventoryLevels.PartyInventoryLevels;
import static com.echothree.model.jooq.server.tables.item.ItemDetails.ItemDetails;
import static com.echothree.model.jooq.server.tables.item.Items.Items;
import static com.echothree.model.jooq.server.tables.party.Parties.Parties;
import static com.echothree.model.jooq.server.tables.party.PartyDetails.PartyDetails;
import static com.echothree.model.jooq.server.tables.party.PartyTypes.PartyTypes;
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
public class InventoryLevelControl
        extends BaseModelControl {

    @Inject
    PartyInventoryLevelTransferCache partyInventoryLevelTransferCache;

    /**
     * Creates a new instance of InventoryLevelControl
     */
    protected InventoryLevelControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Party Inventory Levels
    // --------------------------------------------------------------------------------

    @Inject
    protected PartyInventoryLevelFactory partyInventoryLevelFactory;

    public PartyInventoryLevel createPartyInventoryLevel(Party party, Item item, InventoryCondition inventoryCondition,
            Long minimumInventory, Long maximumInventory, Long reorderQuantity, BasePK createdBy) {
        var partyInventoryLevel = partyInventoryLevelFactory.create(party, item,
                inventoryCondition, minimumInventory, maximumInventory, reorderQuantity, session.getStartTime(),
                Session.MAX_TIME);

        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyInventoryLevel.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return partyInventoryLevel;
    }

    public long countPartyInventoryLevelsByItem(final Item item) {
        return session.getDslContext()
                .selectCount()
                .from(PartyInventoryLevels)
                .where(PartyInventoryLevels.ITEM.eq(item.getPrimaryKey()), PartyInventoryLevels.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyInventoryLevelsByInventoryCondition(final InventoryCondition inventoryCondition) {
        return session.getDslContext()
                .selectCount()
                .from(PartyInventoryLevels)
                .where(PartyInventoryLevels.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        PartyInventoryLevels.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countPartyInventoryLevelsByParty(final Party party) {
        return session.getDslContext()
                .selectCount()
                .from(PartyInventoryLevels)
                .where(PartyInventoryLevels.PARTY.eq(party.getPrimaryKey()), PartyInventoryLevels.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private PartyInventoryLevel getPartyInventoryLevel(Party party, Item item, InventoryCondition inventoryCondition,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(PartyInventoryLevels.fields())
                .from(PartyInventoryLevels)
                .where(PartyInventoryLevels.PARTY.eq(party.getPrimaryKey()), PartyInventoryLevels.ITEM.eq(item.getPrimaryKey()),
                        PartyInventoryLevels.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                        PartyInventoryLevels.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return partyInventoryLevelFactory.getEntityFromQuery(entityPermission, query);
    }

    public PartyInventoryLevel getPartyInventoryLevel(Party party, Item item, InventoryCondition inventoryCondition) {
        return getPartyInventoryLevel(party, item, inventoryCondition, EntityPermission.READ_ONLY);
    }

    public PartyInventoryLevel getPartyInventoryLevelForUpdate(Party party, Item item, InventoryCondition inventoryCondition) {
        return getPartyInventoryLevel(party, item, inventoryCondition, EntityPermission.READ_WRITE);
    }

    public PartyInventoryLevelValue getPartyInventoryLevelValue(PartyInventoryLevel partyInventoryLevel) {
        return partyInventoryLevel == null ? null : partyInventoryLevel.getPartyInventoryLevelValue().clone();
    }

    public PartyInventoryLevelValue getPartyInventoryLevelValueForUpdate(Party party, Item item, InventoryCondition inventoryCondition) {
        return getPartyInventoryLevelValue(getPartyInventoryLevelForUpdate(party, item, inventoryCondition));
    }

    private List<PartyInventoryLevel> getPartyInventoryLevelsByParty(Party party, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyInventoryLevels.fields())
                    .from(PartyInventoryLevels)
                    .join(Items)
                    .on(PartyInventoryLevels.ITEM.eq(Items.ITEM))
                    .join(ItemDetails)
                    .on(Items.ACTIVE_DETAIL.eq(ItemDetails.ITEM_DETAIL))
                    .join(InventoryConditions)
                    .on(PartyInventoryLevels.INVENTORY_CONDITION.eq(InventoryConditions.INVENTORY_CONDITION))
                    .join(InventoryConditionDetails)
                    .on(InventoryConditions.LAST_DETAIL.eq(InventoryConditionDetails.INVENTORY_CONDITION_DETAIL))
                    .where(PartyInventoryLevels.PARTY.eq(party.getPrimaryKey()), PartyInventoryLevels.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(ItemDetails.ITEM_NAME, InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME),
                    PartyInventoryLevelFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyInventoryLevels.fields())
                    .from(PartyInventoryLevels)
                    .where(PartyInventoryLevels.PARTY.eq(party.getPrimaryKey()), PartyInventoryLevels.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyInventoryLevelFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyInventoryLevel> getPartyInventoryLevelsByParty(Party party) {
        return getPartyInventoryLevelsByParty(party, EntityPermission.READ_ONLY);
    }

    public List<PartyInventoryLevel> getPartyInventoryLevelsByPartyForUpdate(Party party) {
        return getPartyInventoryLevelsByParty(party, EntityPermission.READ_WRITE);
    }

    private List<PartyInventoryLevel> getPartyInventoryLevelsByItem(Item item, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyInventoryLevels.fields())
                    .from(PartyInventoryLevels)
                    .join(Parties)
                    .on(PartyInventoryLevels.PARTY.eq(Parties.PARTY))
                    .join(PartyDetails)
                    .on(Parties.ACTIVE_DETAIL.eq(PartyDetails.PARTY_DETAIL))
                    .join(PartyTypes)
                    .on(PartyDetails.PARTY_TYPE.eq(PartyTypes.PARTY_TYPE))
                    .join(InventoryConditions)
                    .on(PartyInventoryLevels.INVENTORY_CONDITION.eq(InventoryConditions.INVENTORY_CONDITION))
                    .join(InventoryConditionDetails)
                    .on(InventoryConditions.LAST_DETAIL.eq(InventoryConditionDetails.INVENTORY_CONDITION_DETAIL))
                    .where(PartyInventoryLevels.ITEM.eq(item.getPrimaryKey()), PartyInventoryLevels.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME,
                            InventoryConditionDetails.SORT_ORDER, InventoryConditionDetails.INVENTORY_CONDITION_NAME), PartyInventoryLevelFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyInventoryLevels.fields())
                    .from(PartyInventoryLevels)
                    .where(PartyInventoryLevels.ITEM.eq(item.getPrimaryKey()), PartyInventoryLevels.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyInventoryLevelFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyInventoryLevel> getPartyInventoryLevelsByItem(Item item) {
        return getPartyInventoryLevelsByItem(item, EntityPermission.READ_ONLY);
    }

    public List<PartyInventoryLevel> getPartyInventoryLevelsByItemForUpdate(Item item) {
        return getPartyInventoryLevelsByItem(item, EntityPermission.READ_WRITE);
    }

    private List<PartyInventoryLevel> getPartyInventoryLevelsByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(PartyInventoryLevels.fields())
                    .from(PartyInventoryLevels)
                    .join(Parties)
                    .on(PartyInventoryLevels.PARTY.eq(Parties.PARTY))
                    .join(PartyDetails)
                    .on(Parties.ACTIVE_DETAIL.eq(PartyDetails.PARTY_DETAIL))
                    .join(PartyTypes)
                    .on(PartyDetails.PARTY_TYPE.eq(PartyTypes.PARTY_TYPE))
                    .join(Items)
                    .on(PartyInventoryLevels.ITEM.eq(Items.ITEM))
                    .join(ItemDetails)
                    .on(Items.LAST_DETAIL.eq(ItemDetails.ITEM_DETAIL))
                    .where(PartyInventoryLevels.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            PartyInventoryLevels.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(PartyTypes.SORT_ORDER, PartyTypes.PARTY_TYPE_NAME, PartyDetails.PARTY_NAME, ItemDetails.ITEM_NAME),
                    PartyInventoryLevelFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(PartyInventoryLevels.fields())
                    .from(PartyInventoryLevels)
                    .where(PartyInventoryLevels.INVENTORY_CONDITION.eq(inventoryCondition.getPrimaryKey()),
                            PartyInventoryLevels.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return partyInventoryLevelFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<PartyInventoryLevel> getPartyInventoryLevelsByInventoryCondition(InventoryCondition inventoryCondition) {
        return getPartyInventoryLevelsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }

    public List<PartyInventoryLevel> getPartyInventoryLevelsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getPartyInventoryLevelsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }

    public PartyInventoryLevelTransfer getPartyInventoryLevelTransfer(UserVisit userVisit, PartyInventoryLevel partyInventoryLevel) {
        return partyInventoryLevelTransferCache.getTransfer(userVisit, partyInventoryLevel);
    }

    public List<PartyInventoryLevelTransfer> getPartyInventoryLevelTransfers(UserVisit userVisit, Collection<PartyInventoryLevel> partyInventoryLevels) {
        List<PartyInventoryLevelTransfer> partyInventoryLevelTransfers = new ArrayList<>(partyInventoryLevels.size());

        partyInventoryLevels.forEach((partyInventoryLevel) ->
                partyInventoryLevelTransfers.add(partyInventoryLevelTransferCache.getTransfer(userVisit, partyInventoryLevel))
        );

        return partyInventoryLevelTransfers;
    }

    public List<PartyInventoryLevelTransfer> getPartyInventoryLevelTransfersByParty(UserVisit userVisit, Party party) {
        return getPartyInventoryLevelTransfers(userVisit, getPartyInventoryLevelsByParty(party));
    }

    public List<PartyInventoryLevelTransfer> getPartyInventoryLevelTransfersByItem(UserVisit userVisit, Item item) {
        return getPartyInventoryLevelTransfers(userVisit, getPartyInventoryLevelsByItem(item));
    }

    public List<PartyInventoryLevelTransfer> getPartyInventoryLevelTransfersByInventoryCondition(UserVisit userVisit, InventoryCondition inventoryCondition) {
        return getPartyInventoryLevelTransfers(userVisit, getPartyInventoryLevelsByInventoryCondition(inventoryCondition));
    }

    public void updatePartyInventoryLevelFromValue(PartyInventoryLevelValue partyInventoryLevelValue, BasePK updatedBy) {
        if(partyInventoryLevelValue.hasBeenModified()) {
            var partyInventoryLevel = partyInventoryLevelFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    partyInventoryLevelValue.getPrimaryKey());

            partyInventoryLevel.setThruTime(session.getStartTime());
            partyInventoryLevel.store();

            var partyPK = partyInventoryLevel.getPartyPK(); // Not updated
            var itemPK = partyInventoryLevel.getItemPK(); // Not updated
            var inventoryConditionPK = partyInventoryLevel.getInventoryConditionPK(); // Not updated
            var minimumInventory = partyInventoryLevelValue.getMinimumInventory();
            var maximumInventory = partyInventoryLevelValue.getMaximumInventory();
            var reorderQuantity = partyInventoryLevelValue.getReorderQuantity();

            partyInventoryLevel = partyInventoryLevelFactory.create(partyPK, itemPK, inventoryConditionPK,
                    minimumInventory, maximumInventory, reorderQuantity, session.getStartTime(), Session.MAX_TIME);

            sendEvent(partyPK, EventTypes.MODIFY, partyInventoryLevel.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deletePartyInventoryLevel(PartyInventoryLevel partyInventoryLevel, BasePK deletedBy) {
        partyInventoryLevel.setThruTime(session.getStartTime());

        sendEvent(partyInventoryLevel.getPartyPK(), EventTypes.MODIFY, partyInventoryLevel.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deletePartyInventoryLevels(List<PartyInventoryLevel> partyInventoryLevels, BasePK deletedBy) {
        partyInventoryLevels.forEach((partyInventoryLevel) ->
                deletePartyInventoryLevel(partyInventoryLevel, deletedBy)
        );
    }

    public void deletePartyInventoryLevelsByParty(Party party, BasePK deletedBy) {
        deletePartyInventoryLevels(getPartyInventoryLevelsByPartyForUpdate(party), deletedBy);
    }

    public void deletePartyInventoryLevelsByItem(Item item, BasePK deletedBy) {
        deletePartyInventoryLevels(getPartyInventoryLevelsByItemForUpdate(item), deletedBy);
    }

    public void deletePartyInventoryLevelsByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        deletePartyInventoryLevels(getPartyInventoryLevelsByInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }

}
