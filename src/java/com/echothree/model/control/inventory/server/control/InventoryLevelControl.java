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
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class InventoryLevelControl
        extends BaseModelControl {

    @Inject
    PartyInventoryLevelTransferCache partyInventoryLevelTransferCache;

    /** Creates a new instance of InventoryLevelControl */
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
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM partyinventorylevels
                        WHERE parinvlvl_itm_itemid = ? AND parinvlvl_thrutime = ?
                        """, item, Session.MAX_TIME);
    }

    public long countPartyInventoryLevelsByInventoryCondition(final InventoryCondition inventoryCondition) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM partyinventorylevels
                        WHERE parinvlvl_invcon_inventoryconditionid = ? AND parinvlvl_thrutime = ?
                        """, inventoryCondition, Session.MAX_TIME);
    }

    public long countPartyInventoryLevelsByParty(final Party party) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM partyinventorylevels
                        WHERE parinvlvl_par_partyid = ? AND parinvlvl_thrutime = ?
                        """, party, Session.MAX_TIME);
    }

    private PartyInventoryLevel getPartyInventoryLevel(Party party, Item item, InventoryCondition inventoryCondition,
            EntityPermission entityPermission) {
        PartyInventoryLevel partyInventoryLevel;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM partyinventorylevels
                        WHERE parinvlvl_par_partyid = ? AND parinvlvl_itm_itemid = ? AND parinvlvl_invcon_inventoryconditionid = ? AND parinvlvl_thrutime = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM partyinventorylevels
                        WHERE parinvlvl_par_partyid = ? AND parinvlvl_itm_itemid = ? AND parinvlvl_invcon_inventoryconditionid = ? AND parinvlvl_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = partyInventoryLevelFactory.prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, item.getPrimaryKey().getEntityId());
            ps.setLong(3, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            partyInventoryLevel = partyInventoryLevelFactory.getEntityFromQuery(
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyInventoryLevel;
    }
    
    public PartyInventoryLevel getPartyInventoryLevel(Party party, Item item, InventoryCondition inventoryCondition) {
        return getPartyInventoryLevel(party, item, inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public PartyInventoryLevel getPartyInventoryLevelForUpdate(Party party, Item item, InventoryCondition inventoryCondition) {
        return getPartyInventoryLevel(party, item, inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    public PartyInventoryLevelValue getPartyInventoryLevelValue(PartyInventoryLevel partyInventoryLevel) {
        return partyInventoryLevel == null? null: partyInventoryLevel.getPartyInventoryLevelValue().clone();
    }
    
    public PartyInventoryLevelValue getPartyInventoryLevelValueForUpdate(Party party, Item item, InventoryCondition inventoryCondition) {
        return getPartyInventoryLevelValue(getPartyInventoryLevelForUpdate(party, item, inventoryCondition));
    }
    
    private List<PartyInventoryLevel> getPartyInventoryLevelsByParty(Party party, EntityPermission entityPermission) {
        List<PartyInventoryLevel> partyInventoryLevels;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM partyinventorylevels, items, itemdetails, inventoryconditions, inventoryconditiondetails
                        WHERE parinvlvl_par_partyid = ? AND parinvlvl_thrutime = ?
                        AND parinvlvl_itm_itemid = itm_itemid AND itm_activedetailid = itmdt_itemdetailid
                        AND parinvlvl_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid
                        ORDER BY itmdt_itemname, invcondt_sortorder, invcondt_inventoryconditionname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM partyinventorylevels
                        WHERE parinvlvl_par_partyid = ? AND parinvlvl_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = partyInventoryLevelFactory.prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyInventoryLevels = partyInventoryLevelFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyInventoryLevels;
    }
    
    public List<PartyInventoryLevel> getPartyInventoryLevelsByParty(Party party) {
        return getPartyInventoryLevelsByParty(party, EntityPermission.READ_ONLY);
    }
    
    public List<PartyInventoryLevel> getPartyInventoryLevelsByPartyForUpdate(Party party) {
        return getPartyInventoryLevelsByParty(party, EntityPermission.READ_WRITE);
    }
    
    private List<PartyInventoryLevel> getPartyInventoryLevelsByItem(Item item, EntityPermission entityPermission) {
        List<PartyInventoryLevel> partyInventoryLevels;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM partyinventorylevels, parties, partydetails, partytypes, inventoryconditions, inventoryconditiondetails
                        WHERE parinvlvl_itm_itemid = ? AND parinvlvl_thrutime = ?
                        AND parinvlvl_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid
                        AND pardt_ptyp_partytypeid = ptyp_partytypeid
                        AND parinvlvl_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid
                        ORDER BY ptyp_sortorder, ptyp_partytypename, pardt_partyname, invcondt_sortorder, invcondt_inventoryconditionname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM partyinventorylevels
                        WHERE parinvlvl_itm_itemid = ? AND parinvlvl_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = partyInventoryLevelFactory.prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyInventoryLevels = partyInventoryLevelFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyInventoryLevels;
    }
    
    public List<PartyInventoryLevel> getPartyInventoryLevelsByItem(Item item) {
        return getPartyInventoryLevelsByItem(item, EntityPermission.READ_ONLY);
    }
    
    public List<PartyInventoryLevel> getPartyInventoryLevelsByItemForUpdate(Item item) {
        return getPartyInventoryLevelsByItem(item, EntityPermission.READ_WRITE);
    }
    
    private List<PartyInventoryLevel> getPartyInventoryLevelsByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        List<PartyInventoryLevel> partyInventoryLevels;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM partyinventorylevels, parties, partydetails, partytypes, item, itemdetails
                        WHERE parinvlvl_invcon_inventoryconditionid = ? AND parinvlvl_thrutime = ?
                        AND parinvlvl_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid
                        AND pardt_ptyp_partytypeid = ptyp_partytypeid
                        AND parinvlvl_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid
                        ORDER BY ptyp_sortorder, ptyp_partytypename, pardt_partyname, itmdt_itemname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM partyinventorylevels
                        WHERE parinvlvl_invcon_inventoryconditionid = ? AND parinvlvl_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = partyInventoryLevelFactory.prepareStatement(query);
            
            ps.setLong(1, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyInventoryLevels = partyInventoryLevelFactory.getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return partyInventoryLevels;
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
