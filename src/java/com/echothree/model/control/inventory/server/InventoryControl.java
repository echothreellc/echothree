// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.model.control.inventory.server;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.remote.choice.AllocationPriorityChoicesBean;
import com.echothree.model.control.inventory.remote.choice.InventoryConditionChoicesBean;
import com.echothree.model.control.inventory.remote.choice.InventoryConditionUseTypeChoicesBean;
import com.echothree.model.control.inventory.remote.choice.InventoryLocationGroupChoicesBean;
import com.echothree.model.control.inventory.remote.choice.InventoryLocationGroupStatusChoicesBean;
import com.echothree.model.control.inventory.remote.choice.LotAliasTypeChoicesBean;
import com.echothree.model.control.inventory.remote.choice.LotTimeTypeChoicesBean;
import com.echothree.model.control.inventory.remote.choice.LotTypeChoicesBean;
import com.echothree.model.control.inventory.remote.transfer.AllocationPriorityDescriptionTransfer;
import com.echothree.model.control.inventory.remote.transfer.AllocationPriorityTransfer;
import com.echothree.model.control.inventory.remote.transfer.InventoryConditionDescriptionTransfer;
import com.echothree.model.control.inventory.remote.transfer.InventoryConditionGlAccountTransfer;
import com.echothree.model.control.inventory.remote.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.remote.transfer.InventoryConditionUseTransfer;
import com.echothree.model.control.inventory.remote.transfer.InventoryConditionUseTypeTransfer;
import com.echothree.model.control.inventory.remote.transfer.InventoryLocationGroupCapacityTransfer;
import com.echothree.model.control.inventory.remote.transfer.InventoryLocationGroupDescriptionTransfer;
import com.echothree.model.control.inventory.remote.transfer.InventoryLocationGroupTransfer;
import com.echothree.model.control.inventory.remote.transfer.InventoryLocationGroupVolumeTransfer;
import com.echothree.model.control.inventory.remote.transfer.LotAliasTransfer;
import com.echothree.model.control.inventory.remote.transfer.LotAliasTypeDescriptionTransfer;
import com.echothree.model.control.inventory.remote.transfer.LotAliasTypeTransfer;
import com.echothree.model.control.inventory.remote.transfer.LotTimeTransfer;
import com.echothree.model.control.inventory.remote.transfer.LotTimeTypeDescriptionTransfer;
import com.echothree.model.control.inventory.remote.transfer.LotTimeTypeTransfer;
import com.echothree.model.control.inventory.remote.transfer.LotTransfer;
import com.echothree.model.control.inventory.remote.transfer.LotTypeDescriptionTransfer;
import com.echothree.model.control.inventory.remote.transfer.LotTypeTransfer;
import com.echothree.model.control.inventory.remote.transfer.PartyInventoryLevelTransfer;
import com.echothree.model.control.inventory.server.transfer.AllocationPriorityDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.AllocationPriorityTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryConditionGlAccountTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryConditionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryConditionUseTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryConditionUseTypeTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryLocationGroupCapacityTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryTransferCaches;
import com.echothree.model.control.inventory.server.transfer.LotAliasTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotAliasTypeDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotAliasTypeTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotTimeTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotTimeTypeDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotTimeTypeTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotTypeDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.LotTypeTransferCache;
import com.echothree.model.control.inventory.server.transfer.PartyInventoryLevelTransferCache;
import com.echothree.model.control.item.server.ItemControl;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.control.warehouse.server.WarehouseControl;
import com.echothree.model.control.inventory.common.workflow.InventoryLocationGroupStatusConstants;
import com.echothree.model.control.workflow.server.WorkflowControl;
import com.echothree.model.data.accounting.remote.pk.GlAccountPK;
import com.echothree.model.data.accounting.remote.pk.ItemAccountingCategoryPK;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.remote.pk.AllocationPriorityPK;
import com.echothree.model.data.inventory.remote.pk.InventoryConditionPK;
import com.echothree.model.data.inventory.remote.pk.InventoryConditionUseTypePK;
import com.echothree.model.data.inventory.remote.pk.InventoryLocationGroupPK;
import com.echothree.model.data.inventory.remote.pk.LotAliasTypePK;
import com.echothree.model.data.inventory.remote.pk.LotPK;
import com.echothree.model.data.inventory.remote.pk.LotTimeTypePK;
import com.echothree.model.data.inventory.remote.pk.LotTypePK;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.inventory.server.entity.AllocationPriorityDescription;
import com.echothree.model.data.inventory.server.entity.AllocationPriorityDetail;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionDescription;
import com.echothree.model.data.inventory.server.entity.InventoryConditionDetail;
import com.echothree.model.data.inventory.server.entity.InventoryConditionGlAccount;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUse;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUseType;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUseTypeDescription;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupCapacity;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupDescription;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupDetail;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupVolume;
import com.echothree.model.data.inventory.server.entity.Lot;
import com.echothree.model.data.inventory.server.entity.LotAlias;
import com.echothree.model.data.inventory.server.entity.LotAliasType;
import com.echothree.model.data.inventory.server.entity.LotAliasTypeDescription;
import com.echothree.model.data.inventory.server.entity.LotAliasTypeDetail;
import com.echothree.model.data.inventory.server.entity.LotTime;
import com.echothree.model.data.inventory.server.entity.LotTimeType;
import com.echothree.model.data.inventory.server.entity.LotTimeTypeDescription;
import com.echothree.model.data.inventory.server.entity.LotTimeTypeDetail;
import com.echothree.model.data.inventory.server.entity.LotType;
import com.echothree.model.data.inventory.server.entity.LotTypeDescription;
import com.echothree.model.data.inventory.server.entity.LotTypeDetail;
import com.echothree.model.data.inventory.server.entity.PartyInventoryLevel;
import com.echothree.model.data.inventory.server.factory.AllocationPriorityDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.AllocationPriorityDetailFactory;
import com.echothree.model.data.inventory.server.factory.AllocationPriorityFactory;
import com.echothree.model.data.inventory.server.factory.InventoryConditionDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryConditionDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryConditionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryConditionGlAccountFactory;
import com.echothree.model.data.inventory.server.factory.InventoryConditionUseFactory;
import com.echothree.model.data.inventory.server.factory.InventoryConditionUseTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryConditionUseTypeFactory;
import com.echothree.model.data.inventory.server.factory.InventoryLocationGroupCapacityFactory;
import com.echothree.model.data.inventory.server.factory.InventoryLocationGroupDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryLocationGroupDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryLocationGroupFactory;
import com.echothree.model.data.inventory.server.factory.InventoryLocationGroupVolumeFactory;
import com.echothree.model.data.inventory.server.factory.LotAliasFactory;
import com.echothree.model.data.inventory.server.factory.LotAliasTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.LotAliasTypeDetailFactory;
import com.echothree.model.data.inventory.server.factory.LotAliasTypeFactory;
import com.echothree.model.data.inventory.server.factory.LotTimeFactory;
import com.echothree.model.data.inventory.server.factory.LotTimeTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.LotTimeTypeDetailFactory;
import com.echothree.model.data.inventory.server.factory.LotTimeTypeFactory;
import com.echothree.model.data.inventory.server.factory.LotTypeDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.LotTypeDetailFactory;
import com.echothree.model.data.inventory.server.factory.LotTypeFactory;
import com.echothree.model.data.inventory.server.factory.PartyInventoryLevelFactory;
import com.echothree.model.data.inventory.server.value.AllocationPriorityDescriptionValue;
import com.echothree.model.data.inventory.server.value.AllocationPriorityDetailValue;
import com.echothree.model.data.inventory.server.value.InventoryConditionDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryConditionDetailValue;
import com.echothree.model.data.inventory.server.value.InventoryConditionGlAccountValue;
import com.echothree.model.data.inventory.server.value.InventoryConditionUseValue;
import com.echothree.model.data.inventory.server.value.InventoryLocationGroupCapacityValue;
import com.echothree.model.data.inventory.server.value.InventoryLocationGroupDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryLocationGroupDetailValue;
import com.echothree.model.data.inventory.server.value.InventoryLocationGroupVolumeValue;
import com.echothree.model.data.inventory.server.value.LotAliasTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.LotAliasTypeDetailValue;
import com.echothree.model.data.inventory.server.value.LotAliasValue;
import com.echothree.model.data.inventory.server.value.LotTimeTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.LotTimeTypeDetailValue;
import com.echothree.model.data.inventory.server.value.LotTimeValue;
import com.echothree.model.data.inventory.server.value.LotTypeDescriptionValue;
import com.echothree.model.data.inventory.server.value.LotTypeDetailValue;
import com.echothree.model.data.inventory.server.value.PartyInventoryLevelValue;
import com.echothree.model.data.item.remote.pk.ItemPK;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.remote.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.sequence.remote.pk.SequenceTypePK;
import com.echothree.model.data.sequence.server.entity.SequenceType;
import com.echothree.model.data.uom.remote.pk.UnitOfMeasureTypePK;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.workflow.remote.pk.WorkflowEntrancePK;
import com.echothree.model.data.workflow.remote.pk.WorkflowPK;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.model.data.workflow.server.entity.WorkflowEntrance;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.remote.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InventoryControl
        extends BaseModelControl {
    
    /** Creates a new instance of InventoryControl */
    public InventoryControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Transfer Caches
    // --------------------------------------------------------------------------------
    
    private InventoryTransferCaches inventoryTransferCaches = null;
    
    public InventoryTransferCaches getInventoryTransferCaches(UserVisit userVisit) {
        if(inventoryTransferCaches == null) {
            inventoryTransferCaches = new InventoryTransferCaches(userVisit, this);
        }
        
        return inventoryTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Groups
    // --------------------------------------------------------------------------------
    
    public InventoryLocationGroup createInventoryLocationGroup(Party warehouseParty, String inventoryLocationGroupName,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        InventoryLocationGroup defaultInventoryLocationGroup = getDefaultInventoryLocationGroup(warehouseParty);
        boolean defaultFound = defaultInventoryLocationGroup != null;
        
        if(defaultFound && isDefault) {
            InventoryLocationGroupDetailValue defaultInventoryLocationGroupDetailValue = getDefaultInventoryLocationGroupDetailValueForUpdate(warehouseParty);
            
            defaultInventoryLocationGroupDetailValue.setIsDefault(Boolean.FALSE);
            updateInventoryLocationGroupFromValue(defaultInventoryLocationGroupDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        InventoryLocationGroup inventoryLocationGroup = InventoryLocationGroupFactory.getInstance().create();
        InventoryLocationGroupDetail inventoryLocationGroupDetail = InventoryLocationGroupDetailFactory.getInstance().create(session,
                inventoryLocationGroup, warehouseParty, inventoryLocationGroupName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        inventoryLocationGroup = InventoryLocationGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, inventoryLocationGroup.getPrimaryKey());
        inventoryLocationGroup.setActiveDetail(inventoryLocationGroupDetail);
        inventoryLocationGroup.setLastDetail(inventoryLocationGroupDetail);
        inventoryLocationGroup.store();
        
        sendEventUsingNames(inventoryLocationGroup.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return inventoryLocationGroup;
    }
    
    private InventoryLocationGroup getInventoryLocationGroupByName(Party warehouseParty, String inventoryLocationGroupName, EntityPermission entityPermission) {
        InventoryLocationGroup inventoryLocationGroup = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroups, inventorylocationgroupdetails " +
                        "WHERE invlocgrp_inventorylocationgroupid = invlocgrpdt_invlocgrp_inventorylocationgroupid " +
                        "AND invlocgrpdt_warehousepartyid = ? AND invlocgrpdt_inventorylocationgroupname = ? AND invlocgrpdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroups, inventorylocationgroupdetails " +
                        "WHERE invlocgrp_inventorylocationgroupid = invlocgrpdt_invlocgrp_inventorylocationgroupid " +
                        "AND invlocgrpdt_warehousepartyid = ? AND invlocgrpdt_inventorylocationgroupname = ? AND invlocgrpdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryLocationGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, warehouseParty.getPrimaryKey().getEntityId());
            ps.setString(2, inventoryLocationGroupName);
            ps.setLong(3, Session.MAX_TIME);
            
            inventoryLocationGroup = InventoryLocationGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryLocationGroup;
    }
    
    public InventoryLocationGroup getInventoryLocationGroupByName(Party warehouseParty, String inventoryLocationGroupName) {
        return getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName, EntityPermission.READ_ONLY);
    }
    
    public InventoryLocationGroup getInventoryLocationGroupByNameForUpdate(Party warehouseParty, String inventoryLocationGroupName) {
        return getInventoryLocationGroupByName(warehouseParty, inventoryLocationGroupName, EntityPermission.READ_WRITE);
    }
    
    public InventoryLocationGroupDetailValue getInventoryLocationGroupDetailValueForUpdate(InventoryLocationGroup inventoryLocationGroup) {
        return inventoryLocationGroup == null? null: inventoryLocationGroup.getLastDetailForUpdate().getInventoryLocationGroupDetailValue().clone();
    }
    
    public InventoryLocationGroupDetailValue getInventoryLocationGroupDetailValueByNameForUpdate(Party warehouseParty,
            String inventoryLocationGroupName) {
        return getInventoryLocationGroupDetailValueForUpdate(getInventoryLocationGroupByNameForUpdate(warehouseParty,
                inventoryLocationGroupName));
    }
    
    private InventoryLocationGroup getDefaultInventoryLocationGroup(Party warehouseParty, EntityPermission entityPermission) {
        InventoryLocationGroup inventoryLocationGroup = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroups, inventorylocationgroupdetails " +
                        "WHERE invlocgrp_inventorylocationgroupid = invlocgrpdt_invlocgrp_inventorylocationgroupid " +
                        "AND invlocgrpdt_warehousepartyid = ? AND invlocgrpdt_isdefault = 1 AND invlocgrpdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroups, inventorylocationgroupdetails " +
                        "WHERE invlocgrp_inventorylocationgroupid = invlocgrpdt_invlocgrp_inventorylocationgroupid " +
                        "AND invlocgrpdt_warehousepartyid = ? AND invlocgrpdt_isdefault = 1 AND invlocgrpdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryLocationGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, warehouseParty.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            inventoryLocationGroup = InventoryLocationGroupFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryLocationGroup;
    }
    
    public InventoryLocationGroup getDefaultInventoryLocationGroup(Party warehouseParty) {
        return getDefaultInventoryLocationGroup(warehouseParty, EntityPermission.READ_ONLY);
    }
    
    public InventoryLocationGroup getDefaultInventoryLocationGroupForUpdate(Party warehouseParty) {
        return getDefaultInventoryLocationGroup(warehouseParty, EntityPermission.READ_WRITE);
    }
    
    public InventoryLocationGroupDetailValue getDefaultInventoryLocationGroupDetailValueForUpdate(Party warehouseParty) {
        return getDefaultInventoryLocationGroupForUpdate(warehouseParty).getLastDetailForUpdate().getInventoryLocationGroupDetailValue().clone();
    }
    
    private List<InventoryLocationGroup> getInventoryLocationGroupsByWarehouseParty(Party warehouseParty, EntityPermission entityPermission) {
        List<InventoryLocationGroup> inventoryLocationGroups = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroups, inventorylocationgroupdetails " +
                        "WHERE invlocgrp_inventorylocationgroupid = invlocgrpdt_invlocgrp_inventorylocationgroupid " +
                        "AND invlocgrpdt_warehousepartyid = ? AND invlocgrpdt_thrutime = ? " +
                        "ORDER BY invlocgrpdt_sortorder, invlocgrpdt_inventorylocationgroupname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroups, inventorylocationgroupdetails " +
                        "WHERE invlocgrp_inventorylocationgroupid = invlocgrpdt_invlocgrp_inventorylocationgroupid " +
                        "AND invlocgrpdt_warehousepartyid = ? AND invlocgrpdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryLocationGroupFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, warehouseParty.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            inventoryLocationGroups = InventoryLocationGroupFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryLocationGroups;
    }
    
    public List<InventoryLocationGroup> getInventoryLocationGroupsByWarehouseParty(Party warehouseParty) {
        return getInventoryLocationGroupsByWarehouseParty(warehouseParty, EntityPermission.READ_ONLY);
    }
    
    public List<InventoryLocationGroup> getInventoryLocationGroupsByWarehousePartyForUpdate(Party warehouseParty) {
        return getInventoryLocationGroupsByWarehouseParty(warehouseParty, EntityPermission.READ_WRITE);
    }
    
    public InventoryLocationGroupTransfer getInventoryLocationGroupTransfer(UserVisit userVisit, InventoryLocationGroup inventoryLocationGroup) {
        return getInventoryTransferCaches(userVisit).getInventoryLocationGroupTransferCache().getInventoryLocationGroupTransfer(inventoryLocationGroup);
    }
    
    public List<InventoryLocationGroupTransfer> getInventoryLocationGroupTransfersByWarehouseParty(UserVisit userVisit, Party warehouseParty) {
        List<InventoryLocationGroup> inventoryLocationGroups = getInventoryLocationGroupsByWarehouseParty(warehouseParty);
        List<InventoryLocationGroupTransfer> inventoryLocationGroupTransfers = null;
        
        if(inventoryLocationGroups != null) {
            inventoryLocationGroupTransfers = new ArrayList<>(inventoryLocationGroups.size());
            
            for(InventoryLocationGroup inventoryLocationGroup : inventoryLocationGroups) {
                inventoryLocationGroupTransfers.add(getInventoryTransferCaches(userVisit).getInventoryLocationGroupTransferCache().getInventoryLocationGroupTransfer(inventoryLocationGroup));
            }
        }
        
        return inventoryLocationGroupTransfers;
    }
    
    public InventoryLocationGroupChoicesBean getInventoryLocationGroupChoicesByWarehouseParty(String defaultInventoryLocationGroupChoice,
            Language language, boolean allowNullChoice, Party warehouseParty) {
        List<InventoryLocationGroup> inventoryLocationGroups = getInventoryLocationGroupsByWarehouseParty(warehouseParty);
        int size = inventoryLocationGroups.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        Iterator iter = inventoryLocationGroups.iterator();
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultInventoryLocationGroupChoice == null) {
                defaultValue = "";
            }
        }
        
        while(iter.hasNext()) {
            InventoryLocationGroup inventoryLocationGroup = (InventoryLocationGroup)iter.next();
            InventoryLocationGroupDetail inventoryLocationGroupDetail = inventoryLocationGroup.getLastDetail();
            
            String label = getBestInventoryLocationGroupDescription(inventoryLocationGroup, language);
            String value = inventoryLocationGroupDetail.getInventoryLocationGroupName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultInventoryLocationGroupChoice == null? false: defaultInventoryLocationGroupChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && inventoryLocationGroupDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new InventoryLocationGroupChoicesBean(labels, values, defaultValue);
    }
    
    private void updateInventoryLocationGroupFromValue(InventoryLocationGroupDetailValue inventoryLocationGroupDetailValue,
            boolean checkDefault, BasePK updatedBy) {
        InventoryLocationGroup inventoryLocationGroup = InventoryLocationGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, inventoryLocationGroupDetailValue.getInventoryLocationGroupPK());
        InventoryLocationGroupDetail inventoryLocationGroupDetail = inventoryLocationGroup.getActiveDetailForUpdate();
        
        inventoryLocationGroupDetail.setThruTime(session.START_TIME_LONG);
        inventoryLocationGroupDetail.store();
        
        InventoryLocationGroupPK inventoryLocationGroupPK = inventoryLocationGroupDetail.getInventoryLocationGroupPK();
        Party warehouseParty = inventoryLocationGroupDetail.getWarehouseParty();
        PartyPK warehousePartyPK = inventoryLocationGroupDetail.getWarehousePartyPK();
        String inventoryLocationGroupName = inventoryLocationGroupDetailValue.getInventoryLocationGroupName();
        Boolean isDefault = inventoryLocationGroupDetailValue.getIsDefault();
        Integer sortOrder = inventoryLocationGroupDetailValue.getSortOrder();
        
        if(checkDefault) {
            InventoryLocationGroup defaultInventoryLocationGroup = getDefaultInventoryLocationGroup(warehouseParty);
            boolean defaultFound = defaultInventoryLocationGroup != null && !defaultInventoryLocationGroup.equals(inventoryLocationGroup);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                InventoryLocationGroupDetailValue defaultInventoryLocationGroupDetailValue = getDefaultInventoryLocationGroupDetailValueForUpdate(warehouseParty);
                
                defaultInventoryLocationGroupDetailValue.setIsDefault(Boolean.FALSE);
                updateInventoryLocationGroupFromValue(defaultInventoryLocationGroupDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }
        
        inventoryLocationGroupDetail = InventoryLocationGroupDetailFactory.getInstance().create(inventoryLocationGroupPK,
                warehousePartyPK, inventoryLocationGroupName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        inventoryLocationGroup.setActiveDetail(inventoryLocationGroupDetail);
        inventoryLocationGroup.setLastDetail(inventoryLocationGroupDetail);
        inventoryLocationGroup.store();
        
        sendEventUsingNames(inventoryLocationGroupPK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }
    
    public void updateInventoryLocationGroupFromValue(InventoryLocationGroupDetailValue inventoryLocationGroupDetailValue, BasePK updatedBy) {
        updateInventoryLocationGroupFromValue(inventoryLocationGroupDetailValue, true, updatedBy);
    }
    
    public InventoryLocationGroupStatusChoicesBean getInventoryLocationGroupStatusChoices(String defaultInventoryLocationGroupStatusChoice, Language language,
            InventoryLocationGroup inventoryLocationGroup, PartyPK partyPK) {
        WorkflowControl workflowControl = getWorkflowControl();
        InventoryLocationGroupStatusChoicesBean inventoryLocationGroupStatusChoicesBean = new InventoryLocationGroupStatusChoicesBean();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(inventoryLocationGroup);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(InventoryLocationGroupStatusConstants.Workflow_INVENTORY_LOCATION_GROUP_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(inventoryLocationGroupStatusChoicesBean, defaultInventoryLocationGroupStatusChoice, language,
                false, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return inventoryLocationGroupStatusChoicesBean;
    }
    
    public void setInventoryLocationGroupStatus(ExecutionErrorAccumulator eea, InventoryLocationGroup inventoryLocationGroup,
            String inventoryLocationGroupStatusChoice, PartyPK modifiedBy) {
        WorkflowControl workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(inventoryLocationGroup);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(InventoryLocationGroupStatusConstants.Workflow_INVENTORY_LOCATION_GROUP_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = inventoryLocationGroupStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), inventoryLocationGroupStatusChoice);
        
        if(workflowDestination != null || inventoryLocationGroupStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupStatusChoice.name(), inventoryLocationGroupStatusChoice);
        }
    }
    
    private void deleteInventoryLocationGroup(InventoryLocationGroup inventoryLocationGroup, BasePK deletedBy, boolean adjustDefault) {
        WorkflowControl workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getCoreControl().getEntityInstanceByBasePK(inventoryLocationGroup.getPrimaryKey());
        
        deleteInventoryLocationGroupDescriptionsByInventoryLocationGroup(inventoryLocationGroup, deletedBy);
        
        InventoryLocationGroupDetail inventoryLocationGroupDetail = inventoryLocationGroup.getLastDetailForUpdate();
        inventoryLocationGroupDetail.setThruTime(session.START_TIME_LONG);
        inventoryLocationGroupDetail.store();
        inventoryLocationGroup.setActiveDetail(null);
        
        if(adjustDefault) {
            // Check for default, and pick one if necessary
            Party warehouseParty = inventoryLocationGroupDetail.getWarehouseParty();
            InventoryLocationGroup defaultInventoryLocationGroup = getDefaultInventoryLocationGroup(warehouseParty);
            
            if(defaultInventoryLocationGroup == null) {
                List<InventoryLocationGroup> inventoryLocationGroups = getInventoryLocationGroupsByWarehousePartyForUpdate(warehouseParty);
                
                if(!inventoryLocationGroups.isEmpty()) {
                    Iterator iter = inventoryLocationGroups.iterator();
                    if(iter.hasNext()) {
                        defaultInventoryLocationGroup = (InventoryLocationGroup)iter.next();
                    }
                    InventoryLocationGroupDetailValue inventoryLocationGroupDetailValue = defaultInventoryLocationGroup.getLastDetailForUpdate().getInventoryLocationGroupDetailValue().clone();
                    
                    inventoryLocationGroupDetailValue.setIsDefault(Boolean.TRUE);
                    updateInventoryLocationGroupFromValue(inventoryLocationGroupDetailValue, false, deletedBy);
                }
            }
        }
        
        sendEventUsingNames(inventoryLocationGroup.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    public void deleteInventoryLocationGroup(InventoryLocationGroup inventoryLocationGroup, BasePK deletedBy) {
        deleteInventoryLocationGroupVolumeByInventoryLocationGroup(inventoryLocationGroup, deletedBy);
        deleteInventoryLocationGroupCapacitiesByInventoryLocationGroup(inventoryLocationGroup, deletedBy);
        
        ((WarehouseControl)Session.getModelController(WarehouseControl.class)).deleteLocationsByInventoryLocationGroup(inventoryLocationGroup, deletedBy);
        deleteInventoryLocationGroup(inventoryLocationGroup, deletedBy, true);
    }
    
    public void deleteInventoryLocationGroupsByWarehouseParty(Party warehouseParty, BasePK deletedBy) {
        List<InventoryLocationGroup> inventoryLocationGroups = getInventoryLocationGroupsByWarehousePartyForUpdate(warehouseParty);
        
        inventoryLocationGroups.stream().forEach((inventoryLocationGroup) -> {
            deleteInventoryLocationGroup(inventoryLocationGroup, deletedBy, false);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Descriptions
    // --------------------------------------------------------------------------------
    
    public InventoryLocationGroupDescription createInventoryLocationGroupDescription(InventoryLocationGroup inventoryLocationGroup, Language language, String description, BasePK createdBy) {
        InventoryLocationGroupDescription inventoryLocationGroupDescription = InventoryLocationGroupDescriptionFactory.getInstance().create(inventoryLocationGroup, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(inventoryLocationGroup.getPrimaryKey(), EventTypes.MODIFY.name(), inventoryLocationGroupDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return inventoryLocationGroupDescription;
    }
    
    private InventoryLocationGroupDescription getInventoryLocationGroupDescription(InventoryLocationGroup inventoryLocationGroup, Language language, EntityPermission entityPermission) {
        InventoryLocationGroupDescription inventoryLocationGroupDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroupdescriptions " +
                        "WHERE invlocgrpd_invlocgrp_inventorylocationgroupid = ? AND invlocgrpd_lang_languageid = ? AND invlocgrpd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroupdescriptions " +
                        "WHERE invlocgrpd_invlocgrp_inventorylocationgroupid = ? AND invlocgrpd_lang_languageid = ? AND invlocgrpd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryLocationGroupDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryLocationGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            inventoryLocationGroupDescription = InventoryLocationGroupDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryLocationGroupDescription;
    }
    
    public InventoryLocationGroupDescription getInventoryLocationGroupDescription(InventoryLocationGroup inventoryLocationGroup, Language language) {
        return getInventoryLocationGroupDescription(inventoryLocationGroup, language, EntityPermission.READ_ONLY);
    }
    
    public InventoryLocationGroupDescription getInventoryLocationGroupDescriptionForUpdate(InventoryLocationGroup inventoryLocationGroup, Language language) {
        return getInventoryLocationGroupDescription(inventoryLocationGroup, language, EntityPermission.READ_WRITE);
    }
    
    public InventoryLocationGroupDescriptionValue getInventoryLocationGroupDescriptionValue(InventoryLocationGroupDescription inventoryLocationGroupDescription) {
        return inventoryLocationGroupDescription == null? null: inventoryLocationGroupDescription.getInventoryLocationGroupDescriptionValue().clone();
    }
    
    public InventoryLocationGroupDescriptionValue getInventoryLocationGroupDescriptionValueForUpdate(InventoryLocationGroup inventoryLocationGroup, Language language) {
        return getInventoryLocationGroupDescriptionValue(getInventoryLocationGroupDescriptionForUpdate(inventoryLocationGroup, language));
    }
    
    private List<InventoryLocationGroupDescription> getInventoryLocationGroupDescriptionsByInventoryLocationGroup(InventoryLocationGroup inventoryLocationGroup, EntityPermission entityPermission) {
        List<InventoryLocationGroupDescription> inventoryLocationGroupDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroupdescriptions, languages " +
                        "WHERE invlocgrpd_invlocgrp_inventorylocationgroupid = ? AND invlocgrpd_thrutime = ? AND invlocgrpd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroupdescriptions " +
                        "WHERE invlocgrpd_invlocgrp_inventorylocationgroupid = ? AND invlocgrpd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryLocationGroupDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryLocationGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            inventoryLocationGroupDescriptions = InventoryLocationGroupDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryLocationGroupDescriptions;
    }
    
    public List<InventoryLocationGroupDescription> getInventoryLocationGroupDescriptionsByInventoryLocationGroup(InventoryLocationGroup inventoryLocationGroup) {
        return getInventoryLocationGroupDescriptionsByInventoryLocationGroup(inventoryLocationGroup, EntityPermission.READ_ONLY);
    }
    
    public List<InventoryLocationGroupDescription> getInventoryLocationGroupDescriptionsByInventoryLocationGroupForUpdate(InventoryLocationGroup inventoryLocationGroup) {
        return getInventoryLocationGroupDescriptionsByInventoryLocationGroup(inventoryLocationGroup, EntityPermission.READ_WRITE);
    }
    
    public String getBestInventoryLocationGroupDescription(InventoryLocationGroup inventoryLocationGroup, Language language) {
        String description;
        InventoryLocationGroupDescription inventoryLocationGroupDescription = getInventoryLocationGroupDescription(inventoryLocationGroup, language);
        
        if(inventoryLocationGroupDescription == null && !language.getIsDefault()) {
            inventoryLocationGroupDescription = getInventoryLocationGroupDescription(inventoryLocationGroup, getPartyControl().getDefaultLanguage());
        }
        
        if(inventoryLocationGroupDescription == null) {
            description = inventoryLocationGroup.getLastDetail().getInventoryLocationGroupName();
        } else {
            description = inventoryLocationGroupDescription.getDescription();
        }
        
        return description;
    }
    
    public InventoryLocationGroupDescriptionTransfer getInventoryLocationGroupDescriptionTransfer(UserVisit userVisit, InventoryLocationGroupDescription inventoryLocationGroupDescription) {
        return getInventoryTransferCaches(userVisit).getInventoryLocationGroupDescriptionTransferCache().getInventoryLocationGroupDescriptionTransfer(inventoryLocationGroupDescription);
    }
    
    public List<InventoryLocationGroupDescriptionTransfer> getInventoryLocationGroupDescriptionTransfersByInventoryLocationGroup(UserVisit userVisit, InventoryLocationGroup inventoryLocationGroup) {
        List<InventoryLocationGroupDescription> inventoryLocationGroupDescriptions = getInventoryLocationGroupDescriptionsByInventoryLocationGroup(inventoryLocationGroup);
        List<InventoryLocationGroupDescriptionTransfer> inventoryLocationGroupDescriptionTransfers = null;
        
        if(inventoryLocationGroupDescriptions != null) {
            inventoryLocationGroupDescriptionTransfers = new ArrayList<>(inventoryLocationGroupDescriptions.size());
            
            for(InventoryLocationGroupDescription inventoryLocationGroupDescription : inventoryLocationGroupDescriptions) {
                inventoryLocationGroupDescriptionTransfers.add(getInventoryTransferCaches(userVisit).getInventoryLocationGroupDescriptionTransferCache().getInventoryLocationGroupDescriptionTransfer(inventoryLocationGroupDescription));
            }
        }
        
        return inventoryLocationGroupDescriptionTransfers;
    }
    
    public void updateInventoryLocationGroupDescriptionFromValue(InventoryLocationGroupDescriptionValue inventoryLocationGroupDescriptionValue, BasePK updatedBy) {
        if(inventoryLocationGroupDescriptionValue.hasBeenModified()) {
            InventoryLocationGroupDescription inventoryLocationGroupDescription = InventoryLocationGroupDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, inventoryLocationGroupDescriptionValue.getPrimaryKey());
            
            inventoryLocationGroupDescription.setThruTime(session.START_TIME_LONG);
            inventoryLocationGroupDescription.store();
            
            InventoryLocationGroup inventoryLocationGroup = inventoryLocationGroupDescription.getInventoryLocationGroup();
            Language language = inventoryLocationGroupDescription.getLanguage();
            String description = inventoryLocationGroupDescriptionValue.getDescription();
            
            inventoryLocationGroupDescription = InventoryLocationGroupDescriptionFactory.getInstance().create(inventoryLocationGroup, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(inventoryLocationGroup.getPrimaryKey(), EventTypes.MODIFY.name(), inventoryLocationGroupDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteInventoryLocationGroupDescription(InventoryLocationGroupDescription inventoryLocationGroupDescription, BasePK deletedBy) {
        inventoryLocationGroupDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(inventoryLocationGroupDescription.getInventoryLocationGroupPK(), EventTypes.MODIFY.name(), inventoryLocationGroupDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }
    
    public void deleteInventoryLocationGroupDescriptionsByInventoryLocationGroup(InventoryLocationGroup inventoryLocationGroup, BasePK deletedBy) {
        List<InventoryLocationGroupDescription> inventoryLocationGroupDescriptions = getInventoryLocationGroupDescriptionsByInventoryLocationGroupForUpdate(inventoryLocationGroup);
        
        inventoryLocationGroupDescriptions.stream().forEach((inventoryLocationGroupDescription) -> {
            deleteInventoryLocationGroupDescription(inventoryLocationGroupDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Volumes
    // --------------------------------------------------------------------------------
    
    public InventoryLocationGroupVolume createInventoryLocationGroupVolume(InventoryLocationGroup inventoryLocationGroup,
            Long height, Long width, Long depth, BasePK createdBy) {
        InventoryLocationGroupVolume inventoryLocationGroupVolume = InventoryLocationGroupVolumeFactory.getInstance().create(inventoryLocationGroup, height, width, depth,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(inventoryLocationGroup.getPrimaryKey(), EventTypes.MODIFY.name(), inventoryLocationGroupVolume.getPrimaryKey(), null, createdBy);
        
        return inventoryLocationGroupVolume;
    }
    
    private InventoryLocationGroupVolume getInventoryLocationGroupVolume(InventoryLocationGroup inventoryLocationGroup, EntityPermission entityPermission) {
        InventoryLocationGroupVolume inventoryLocationGroupVolume = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroupvolumes " +
                        "WHERE invlocgrpvol_invlocgrp_inventorylocationgroupid = ? AND invlocgrpvol_thrutime = ? ";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroupvolumes " +
                        "WHERE invlocgrpvol_invlocgrp_inventorylocationgroupid = ? AND invlocgrpvol_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryLocationGroupVolumeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryLocationGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            inventoryLocationGroupVolume = InventoryLocationGroupVolumeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryLocationGroupVolume;
    }
    
    public InventoryLocationGroupVolume getInventoryLocationGroupVolume(InventoryLocationGroup inventoryLocationGroup) {
        return getInventoryLocationGroupVolume(inventoryLocationGroup, EntityPermission.READ_ONLY);
    }
    
    public InventoryLocationGroupVolume getInventoryLocationGroupVolumeForUpdate(InventoryLocationGroup inventoryLocationGroup) {
        return getInventoryLocationGroupVolume(inventoryLocationGroup, EntityPermission.READ_WRITE);
    }
    
    public InventoryLocationGroupVolumeValue getInventoryLocationGroupVolumeValueForUpdate(InventoryLocationGroup inventoryInventoryLocationGroupGroup) {
        InventoryLocationGroupVolume inventoryInventoryLocationGroupGroupVolume = getInventoryLocationGroupVolumeForUpdate(inventoryInventoryLocationGroupGroup);
        
        return inventoryInventoryLocationGroupGroupVolume == null? null: inventoryInventoryLocationGroupGroupVolume.getInventoryLocationGroupVolumeValue().clone();
    }
    
    public InventoryLocationGroupVolumeTransfer getInventoryLocationGroupVolumeTransfer(UserVisit userVisit, InventoryLocationGroupVolume inventoryInventoryLocationGroupGroupVolume) {
        return inventoryInventoryLocationGroupGroupVolume == null? null: getInventoryTransferCaches(userVisit).getInventoryLocationGroupVolumeTransferCache().getInventoryLocationGroupVolumeTransfer(inventoryInventoryLocationGroupGroupVolume);
    }
    
    public InventoryLocationGroupVolumeTransfer getInventoryLocationGroupVolumeTransfer(UserVisit userVisit, InventoryLocationGroup inventoryInventoryLocationGroupGroup) {
        InventoryLocationGroupVolume inventoryInventoryLocationGroupGroupVolume = getInventoryLocationGroupVolume(inventoryInventoryLocationGroupGroup);
        
        return inventoryInventoryLocationGroupGroupVolume == null? null: getInventoryTransferCaches(userVisit).getInventoryLocationGroupVolumeTransferCache().getInventoryLocationGroupVolumeTransfer(inventoryInventoryLocationGroupGroupVolume);
    }
    
    public void updateInventoryLocationGroupVolumeFromValue(InventoryLocationGroupVolumeValue inventoryInventoryLocationGroupGroupVolumeValue, BasePK updatedBy) {
        if(inventoryInventoryLocationGroupGroupVolumeValue.hasBeenModified()) {
            InventoryLocationGroupVolume inventoryInventoryLocationGroupGroupVolume = InventoryLocationGroupVolumeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryInventoryLocationGroupGroupVolumeValue.getPrimaryKey());
            
            inventoryInventoryLocationGroupGroupVolume.setThruTime(session.START_TIME_LONG);
            inventoryInventoryLocationGroupGroupVolume.store();
            
            InventoryLocationGroupPK inventoryInventoryLocationGroupGroupPK = inventoryInventoryLocationGroupGroupVolume.getInventoryLocationGroupPK(); // Not updated
            Long height = inventoryInventoryLocationGroupGroupVolumeValue.getHeight();
            Long width = inventoryInventoryLocationGroupGroupVolumeValue.getWidth();
            Long depth = inventoryInventoryLocationGroupGroupVolumeValue.getDepth();
            
            inventoryInventoryLocationGroupGroupVolume = InventoryLocationGroupVolumeFactory.getInstance().create(inventoryInventoryLocationGroupGroupPK, height,
                    width, depth, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(inventoryInventoryLocationGroupGroupPK, EventTypes.MODIFY.name(), inventoryInventoryLocationGroupGroupVolume.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteInventoryLocationGroupVolume(InventoryLocationGroupVolume inventoryInventoryLocationGroupGroupVolume, BasePK deletedBy) {
        inventoryInventoryLocationGroupGroupVolume.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(inventoryInventoryLocationGroupGroupVolume.getInventoryLocationGroup().getPrimaryKey(), EventTypes.MODIFY.name(), inventoryInventoryLocationGroupGroupVolume.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteInventoryLocationGroupVolumeByInventoryLocationGroup(InventoryLocationGroup inventoryInventoryLocationGroupGroup, BasePK deletedBy) {
        InventoryLocationGroupVolume inventoryInventoryLocationGroupGroupVolume = getInventoryLocationGroupVolumeForUpdate(inventoryInventoryLocationGroupGroup);
        
        if(inventoryInventoryLocationGroupGroupVolume != null)
            deleteInventoryLocationGroupVolume(inventoryInventoryLocationGroupGroupVolume, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Capacities
    // --------------------------------------------------------------------------------
    
    public InventoryLocationGroupCapacity createInventoryLocationGroupCapacity(InventoryLocationGroup inventoryInventoryLocationGroupGroup,
            UnitOfMeasureType unitOfMeasureType, Long capacity, BasePK createdBy) {
        InventoryLocationGroupCapacity inventoryInventoryLocationGroupGroupCapacity = InventoryLocationGroupCapacityFactory.getInstance().create(inventoryInventoryLocationGroupGroup,
                unitOfMeasureType, capacity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(inventoryInventoryLocationGroupGroup.getPrimaryKey(), EventTypes.MODIFY.name(), inventoryInventoryLocationGroupGroupCapacity.getPrimaryKey(), null, createdBy);
        
        return inventoryInventoryLocationGroupGroupCapacity;
    }
    
    private List<InventoryLocationGroupCapacity> getInventoryLocationGroupCapacitiesByInventoryLocationGroup(InventoryLocationGroup inventoryInventoryLocationGroupGroup, EntityPermission entityPermission) {
        List<InventoryLocationGroupCapacity> inventoryInventoryLocationGroupGroupCapacities = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroupcapacities, unitofmeasuretypedetails, unitofmeasurekinddetails " +
                        "WHERE invlocgrpcap_invlocgrp_inventorylocationgroupid = ? AND invlocgrpcap_thrutime = ? " +
                        "AND invlocgrpcap_uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ? " +
                        "AND uomtdt_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename, uomkdt_sortorder, uomkdt_unitofmeasurekindname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroupcapacities " +
                        "WHERE invlocgrpcap_invlocgrp_inventorylocationgroupid = ? AND invlocgrpcap_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryLocationGroupCapacityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryInventoryLocationGroupGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
                ps.setLong(4, Session.MAX_TIME);
            }
            
            inventoryInventoryLocationGroupGroupCapacities = InventoryLocationGroupCapacityFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryInventoryLocationGroupGroupCapacities;
    }
    
    public List<InventoryLocationGroupCapacity> getInventoryLocationGroupCapacitiesByInventoryLocationGroup(InventoryLocationGroup inventoryInventoryLocationGroupGroup) {
        return getInventoryLocationGroupCapacitiesByInventoryLocationGroup(inventoryInventoryLocationGroupGroup, EntityPermission.READ_ONLY);
    }
    
    public List<InventoryLocationGroupCapacity> getInventoryLocationGroupCapacitiesByInventoryLocationGroupForUpdate(InventoryLocationGroup inventoryInventoryLocationGroupGroup) {
        return getInventoryLocationGroupCapacitiesByInventoryLocationGroup(inventoryInventoryLocationGroupGroup, EntityPermission.READ_WRITE);
    }
    
    private InventoryLocationGroupCapacity getInventoryLocationGroupCapacity(InventoryLocationGroup inventoryInventoryLocationGroupGroup, UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        InventoryLocationGroupCapacity inventoryInventoryLocationGroupGroupCapacity = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroupcapacities " +
                        "WHERE invlocgrpcap_invlocgrp_inventorylocationgroupid = ? AND invlocgrpcap_uomt_unitofmeasuretypeid = ? " +
                        "AND invlocgrpcap_thrutime = ? ";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroupcapacities " +
                        "WHERE invlocgrpcap_invlocgrp_inventorylocationgroupid = ? AND invlocgrpcap_uomt_unitofmeasuretypeid = ? " +
                        "AND invlocgrpcap_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryLocationGroupCapacityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryInventoryLocationGroupGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            inventoryInventoryLocationGroupGroupCapacity = InventoryLocationGroupCapacityFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryInventoryLocationGroupGroupCapacity;
    }
    
    public InventoryLocationGroupCapacity getInventoryLocationGroupCapacity(InventoryLocationGroup inventoryInventoryLocationGroupGroup, UnitOfMeasureType unitOfMeasureType) {
        return getInventoryLocationGroupCapacity(inventoryInventoryLocationGroupGroup, unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public InventoryLocationGroupCapacity getInventoryLocationGroupCapacityForUpdate(InventoryLocationGroup inventoryInventoryLocationGroupGroup, UnitOfMeasureType unitOfMeasureType) {
        return getInventoryLocationGroupCapacity(inventoryInventoryLocationGroupGroup, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public InventoryLocationGroupCapacityValue getInventoryLocationGroupCapacityValueForUpdate(InventoryLocationGroup inventoryLocationGroup, UnitOfMeasureType unitOfMeasureType) {
        InventoryLocationGroupCapacity inventoryLocationGroupCapacity = getInventoryLocationGroupCapacityForUpdate(inventoryLocationGroup, unitOfMeasureType);
        
        return inventoryLocationGroupCapacity == null? null: inventoryLocationGroupCapacity.getInventoryLocationGroupCapacityValue().clone();
    }
    
    public void updateInventoryLocationGroupCapacityFromValue(InventoryLocationGroupCapacityValue inventoryLocationGroupCapacityValue, BasePK updatedBy) {
        if(inventoryLocationGroupCapacityValue.hasBeenModified()) {
            InventoryLocationGroupCapacity inventoryLocationGroupCapacity = InventoryLocationGroupCapacityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryLocationGroupCapacityValue.getPrimaryKey());
            
            inventoryLocationGroupCapacity.setThruTime(session.START_TIME_LONG);
            inventoryLocationGroupCapacity.store();
            
            UnitOfMeasureTypePK unitOfMeasureTypePK = inventoryLocationGroupCapacity.getUnitOfMeasureTypePK(); // Not updated
            InventoryLocationGroupPK inventoryLocationGroupPK = inventoryLocationGroupCapacity.getInventoryLocationGroupPK(); // Not updated
            Long capacity = inventoryLocationGroupCapacityValue.getCapacity();
            
            inventoryLocationGroupCapacity = InventoryLocationGroupCapacityFactory.getInstance().create(inventoryLocationGroupPK, unitOfMeasureTypePK, capacity,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(unitOfMeasureTypePK, EventTypes.MODIFY.name(), inventoryLocationGroupCapacity.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public InventoryLocationGroupCapacityTransfer getInventoryLocationGroupCapacityTransfer(UserVisit userVisit, InventoryLocationGroupCapacity inventoryLocationGroupCapacity) {
        return getInventoryTransferCaches(userVisit).getInventoryLocationGroupCapacityTransferCache().getInventoryLocationGroupCapacityTransfer(inventoryLocationGroupCapacity);
    }
    
    public List<InventoryLocationGroupCapacityTransfer> getInventoryLocationGroupCapacityTransfersByInventoryLocationGroup(UserVisit userVisit, InventoryLocationGroup inventoryLocationGroup) {
        List<InventoryLocationGroupCapacity> inventoryLocationGroupCapacities = getInventoryLocationGroupCapacitiesByInventoryLocationGroup(inventoryLocationGroup);
        List<InventoryLocationGroupCapacityTransfer> inventoryLocationGroupCapacityTransfers = new ArrayList<>(inventoryLocationGroupCapacities.size());
        InventoryLocationGroupCapacityTransferCache inventoryLocationGroupCapacityTransferCache = getInventoryTransferCaches(userVisit).getInventoryLocationGroupCapacityTransferCache();
        
        inventoryLocationGroupCapacities.stream().forEach((inventoryLocationGroupCapacity) -> {
            inventoryLocationGroupCapacityTransfers.add(inventoryLocationGroupCapacityTransferCache.getInventoryLocationGroupCapacityTransfer(inventoryLocationGroupCapacity));
        });
        
        return inventoryLocationGroupCapacityTransfers;
    }
    
    public void deleteInventoryLocationGroupCapacity(InventoryLocationGroupCapacity inventoryLocationGroupCapacity, BasePK deletedBy) {
        inventoryLocationGroupCapacity.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(inventoryLocationGroupCapacity.getInventoryLocationGroup().getPrimaryKey(), EventTypes.MODIFY.name(), inventoryLocationGroupCapacity.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteInventoryLocationGroupCapacitiesByInventoryLocationGroup(InventoryLocationGroup inventoryLocationGroup, BasePK deletedBy) {
        List<InventoryLocationGroupCapacity> inventoryLocationGroupCapacities = getInventoryLocationGroupCapacitiesByInventoryLocationGroupForUpdate(inventoryLocationGroup);
        
        inventoryLocationGroupCapacities.stream().forEach((inventoryLocationGroupCapacity) -> {
            deleteInventoryLocationGroupCapacity(inventoryLocationGroupCapacity, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Conditions
    // --------------------------------------------------------------------------------
    
    public InventoryCondition createInventoryCondition(String inventoryConditionName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        InventoryCondition defaultInventoryCondition = getDefaultInventoryCondition();
        boolean defaultFound = defaultInventoryCondition != null;
        
        if(defaultFound && isDefault) {
            InventoryConditionDetailValue defaultInventoryConditionDetailValue = getDefaultInventoryConditionDetailValueForUpdate();
            
            defaultInventoryConditionDetailValue.setIsDefault(Boolean.FALSE);
            updateInventoryConditionFromValue(defaultInventoryConditionDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        InventoryCondition inventoryCondition = InventoryConditionFactory.getInstance().create();
        InventoryConditionDetail inventoryConditionDetail = InventoryConditionDetailFactory.getInstance().create(session,
                inventoryCondition, inventoryConditionName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        inventoryCondition = InventoryConditionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, inventoryCondition.getPrimaryKey());
        inventoryCondition.setActiveDetail(inventoryConditionDetail);
        inventoryCondition.setLastDetail(inventoryConditionDetail);
        inventoryCondition.store();
        
        sendEventUsingNames(inventoryCondition.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return inventoryCondition;
    }
    
    private InventoryCondition getInventoryConditionByName(String inventoryConditionName, EntityPermission entityPermission) {
        InventoryCondition inventoryCondition = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditions, inventoryconditiondetails " +
                        "WHERE invcon_activedetailid = invcondt_inventoryconditiondetailid AND invcondt_inventoryconditionname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditions, inventoryconditiondetails " +
                        "WHERE invcon_activedetailid = invcondt_inventoryconditiondetailid AND invcondt_inventoryconditionname = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryConditionFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, inventoryConditionName);
            
            inventoryCondition = InventoryConditionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryCondition;
    }
    
    public InventoryCondition getInventoryConditionByName(String inventoryConditionName) {
        return getInventoryConditionByName(inventoryConditionName, EntityPermission.READ_ONLY);
    }
    
    public InventoryCondition getInventoryConditionByNameForUpdate(String inventoryConditionName) {
        return getInventoryConditionByName(inventoryConditionName, EntityPermission.READ_WRITE);
    }
    
    public InventoryConditionDetailValue getInventoryConditionDetailValueForUpdate(InventoryCondition inventoryCondition) {
        return inventoryCondition == null? null: inventoryCondition.getLastDetailForUpdate().getInventoryConditionDetailValue().clone();
    }
    
    public InventoryConditionDetailValue getInventoryConditionDetailValueByNameForUpdate(String inventoryConditionName) {
        return getInventoryConditionDetailValueForUpdate(getInventoryConditionByNameForUpdate(inventoryConditionName));
    }
    
    private InventoryCondition getDefaultInventoryCondition(EntityPermission entityPermission) {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditions, inventoryconditiondetails " +
                        "WHERE invcon_activedetailid = invcondt_inventoryconditiondetailid AND invcondt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditions, inventoryconditiondetails " +
                        "WHERE invcon_activedetailid = invcondt_inventoryconditiondetailid AND invcondt_isdefault = 1 " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryConditionFactory.getInstance().prepareStatement(query);
            
            return InventoryConditionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
    }
    
    public InventoryCondition getDefaultInventoryCondition() {
        return getDefaultInventoryCondition(EntityPermission.READ_ONLY);
    }
    
    public InventoryCondition getDefaultInventoryConditionForUpdate() {
        return getDefaultInventoryCondition(EntityPermission.READ_WRITE);
    }
    
    public InventoryConditionDetailValue getDefaultInventoryConditionDetailValueForUpdate() {
        return getDefaultInventoryConditionForUpdate().getLastDetailForUpdate().getInventoryConditionDetailValue().clone();
    }
    
    private List<InventoryCondition> getInventoryConditions(EntityPermission entityPermission) {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditions, inventoryconditiondetails " +
                        "WHERE invcon_activedetailid = invcondt_inventoryconditiondetailid " +
                        "ORDER BY invcondt_sortorder, invcondt_inventoryconditionname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditions, inventoryconditiondetails " +
                        "WHERE invcon_activedetailid = invcondt_inventoryconditiondetailid " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryConditionFactory.getInstance().prepareStatement(query);
            
            return InventoryConditionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
    }
    
    public List<InventoryCondition> getInventoryConditions() {
        return getInventoryConditions(EntityPermission.READ_ONLY);
    }
    
    public List<InventoryCondition> getInventoryConditionsForUpdate() {
        return getInventoryConditions(EntityPermission.READ_WRITE);
    }
    
    public InventoryConditionTransfer getInventoryConditionTransfer(UserVisit userVisit, InventoryCondition inventoryCondition) {
        return getInventoryTransferCaches(userVisit).getInventoryConditionTransferCache().getInventoryConditionTransfer(inventoryCondition);
    }
    
    public List<InventoryConditionTransfer> getInventoryConditionTransfers(UserVisit userVisit) {
        List<InventoryCondition> inventoryConditions = getInventoryConditions();
        List<InventoryConditionTransfer> inventoryConditionTransfers = new ArrayList<>(inventoryConditions.size());
        InventoryConditionTransferCache inventoryConditionTransferCache = getInventoryTransferCaches(userVisit).getInventoryConditionTransferCache();
        
        inventoryConditions.stream().forEach((inventoryCondition) -> {
            inventoryConditionTransfers.add(inventoryConditionTransferCache.getInventoryConditionTransfer(inventoryCondition));
        });
        
        return inventoryConditionTransfers;
    }
    
    public InventoryConditionChoicesBean getInventoryConditionChoices(String defaultInventoryConditionChoice, Language language,
            boolean allowNullChoice) {
        List<InventoryCondition> inventoryConditions = getInventoryConditions();
        int size = inventoryConditions.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        Iterator iter = inventoryConditions.iterator();
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultInventoryConditionChoice == null) {
                defaultValue = "";
            }
        }
        
        while(iter.hasNext()) {
            InventoryCondition inventoryCondition = (InventoryCondition)iter.next();
            InventoryConditionDetail inventoryConditionDetail = inventoryCondition.getLastDetail();
            
            String label = getBestInventoryConditionDescription(inventoryCondition, language);
            String value = inventoryConditionDetail.getInventoryConditionName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultInventoryConditionChoice == null? false: defaultInventoryConditionChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && inventoryConditionDetail.getIsDefault()))
                defaultValue = value;
        }
        
        return new InventoryConditionChoicesBean(labels, values, defaultValue);
    }
    
    public InventoryConditionChoicesBean getInventoryConditionChoicesByInventoryConditionUseType(String defaultInventoryConditionChoice,
            Language language, boolean allowNullChoice, InventoryConditionUseType inventoryConditionUseType) {
        List<InventoryConditionUse> inventoryConditionUses = getInventoryConditionUsesByInventoryConditionUseType(inventoryConditionUseType);
        int size = inventoryConditionUses.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultInventoryConditionChoice == null) {
                defaultValue = "";
            }
        }
        
        for(InventoryConditionUse inventoryConditionUse: inventoryConditionUses) {
            InventoryCondition inventoryCondition = inventoryConditionUse.getInventoryCondition();
            InventoryConditionDetail inventoryConditionDetail = inventoryCondition.getLastDetail();
            
            String label = getBestInventoryConditionDescription(inventoryCondition, language);
            String value = inventoryConditionDetail.getInventoryConditionName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultInventoryConditionChoice == null? false: defaultInventoryConditionChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && inventoryConditionUse.getIsDefault()))
                defaultValue = value;
        }
        
        return new InventoryConditionChoicesBean(labels, values, defaultValue);
    }
    
    
    private void updateInventoryConditionFromValue(InventoryConditionDetailValue inventoryConditionDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        InventoryCondition inventoryCondition = InventoryConditionFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, inventoryConditionDetailValue.getInventoryConditionPK());
        InventoryConditionDetail inventoryConditionDetail = inventoryCondition.getActiveDetailForUpdate();
        
        inventoryConditionDetail.setThruTime(session.START_TIME_LONG);
        inventoryConditionDetail.store();
        
        InventoryConditionPK inventoryConditionPK = inventoryConditionDetail.getInventoryConditionPK();
        String inventoryConditionName = inventoryConditionDetailValue.getInventoryConditionName();
        Boolean isDefault = inventoryConditionDetailValue.getIsDefault();
        Integer sortOrder = inventoryConditionDetailValue.getSortOrder();
        
        if(checkDefault) {
            InventoryCondition defaultInventoryCondition = getDefaultInventoryCondition();
            boolean defaultFound = defaultInventoryCondition != null && !defaultInventoryCondition.equals(inventoryCondition);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                InventoryConditionDetailValue defaultInventoryConditionDetailValue = getDefaultInventoryConditionDetailValueForUpdate();
                
                defaultInventoryConditionDetailValue.setIsDefault(Boolean.FALSE);
                updateInventoryConditionFromValue(defaultInventoryConditionDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }
        
        inventoryConditionDetail = InventoryConditionDetailFactory.getInstance().create(inventoryConditionPK,
                inventoryConditionName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        inventoryCondition.setActiveDetail(inventoryConditionDetail);
        inventoryCondition.setLastDetail(inventoryConditionDetail);
        inventoryCondition.store();
        
        sendEventUsingNames(inventoryConditionPK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }
    
    public void updateInventoryConditionFromValue(InventoryConditionDetailValue inventoryConditionDetailValue, BasePK updatedBy) {
        updateInventoryConditionFromValue(inventoryConditionDetailValue, true, updatedBy);
    }
    
    public void deleteInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        ItemControl itemControl = (ItemControl)Session.getModelController(ItemControl.class);
        VendorControl vendorControl = (VendorControl)Session.getModelController(VendorControl.class);
        
        deleteInventoryConditionDescriptionsByInventoryCondition(inventoryCondition, deletedBy);
        deleteInventoryConditionGlAccountsByInventoryCondition(inventoryCondition, deletedBy);
        deleteInventoryConditionUseByInventoryCondition(inventoryCondition, deletedBy);
        deletePartyInventoryLevelsByInventoryCondition(inventoryCondition, deletedBy);
        itemControl.deleteItemPricesByInventoryCondition(inventoryCondition, deletedBy);
        itemControl.deleteItemKitMembersByInventoryCondition(inventoryCondition, deletedBy);
        itemControl.deleteItemUnitCustomerTypeLimitsByInventoryCondition(inventoryCondition, deletedBy);
        itemControl.deleteItemUnitLimitsByInventoryCondition(inventoryCondition, deletedBy);
        itemControl.deleteItemUnitPriceLimitsByInventoryCondition(inventoryCondition, deletedBy);
        vendorControl.deleteVendorItemCostsByInventoryCondition(inventoryCondition, deletedBy);
        
        InventoryConditionDetail inventoryConditionDetail = inventoryCondition.getLastDetailForUpdate();
        inventoryConditionDetail.setThruTime(session.START_TIME_LONG);
        inventoryCondition.setActiveDetail(null);
        inventoryCondition.store();
        
        // Check for default, and pick one if necessary
        InventoryCondition defaultInventoryCondition = getDefaultInventoryCondition();
        if(defaultInventoryCondition == null) {
            List<InventoryCondition> inventoryConditions = getInventoryConditionsForUpdate();
            
            if(!inventoryConditions.isEmpty()) {
                Iterator iter = inventoryConditions.iterator();
                if(iter.hasNext()) {
                    defaultInventoryCondition = (InventoryCondition)iter.next();
                }
                InventoryConditionDetailValue inventoryConditionDetailValue = defaultInventoryCondition.getLastDetailForUpdate().getInventoryConditionDetailValue().clone();
                
                inventoryConditionDetailValue.setIsDefault(Boolean.TRUE);
                updateInventoryConditionFromValue(inventoryConditionDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(inventoryCondition.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Descriptions
    // --------------------------------------------------------------------------------
    
    public InventoryConditionDescription createInventoryConditionDescription(InventoryCondition inventoryCondition, Language language, String description, BasePK createdBy) {
        InventoryConditionDescription inventoryConditionDescription = InventoryConditionDescriptionFactory.getInstance().create(inventoryCondition, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(inventoryCondition.getPrimaryKey(), EventTypes.MODIFY.name(), inventoryConditionDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return inventoryConditionDescription;
    }
    
    private InventoryConditionDescription getInventoryConditionDescription(InventoryCondition inventoryCondition, Language language, EntityPermission entityPermission) {
        InventoryConditionDescription inventoryConditionDescription = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditiondescriptions " +
                        "WHERE invcond_invcon_inventoryconditionid = ? AND invcond_lang_languageid = ? AND invcond_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditiondescriptions " +
                        "WHERE invcond_invcon_inventoryconditionid = ? AND invcond_lang_languageid = ? AND invcond_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryConditionDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            inventoryConditionDescription = InventoryConditionDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryConditionDescription;
    }
    
    public InventoryConditionDescription getInventoryConditionDescription(InventoryCondition inventoryCondition, Language language) {
        return getInventoryConditionDescription(inventoryCondition, language, EntityPermission.READ_ONLY);
    }
    
    public InventoryConditionDescription getInventoryConditionDescriptionForUpdate(InventoryCondition inventoryCondition, Language language) {
        return getInventoryConditionDescription(inventoryCondition, language, EntityPermission.READ_WRITE);
    }
    
    public InventoryConditionDescriptionValue getInventoryConditionDescriptionValue(InventoryConditionDescription inventoryConditionDescription) {
        return inventoryConditionDescription == null? null: inventoryConditionDescription.getInventoryConditionDescriptionValue().clone();
    }
    
    public InventoryConditionDescriptionValue getInventoryConditionDescriptionValueForUpdate(InventoryCondition inventoryCondition, Language language) {
        return getInventoryConditionDescriptionValue(getInventoryConditionDescriptionForUpdate(inventoryCondition, language));
    }
    
    private List<InventoryConditionDescription> getInventoryConditionDescriptionsByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        List<InventoryConditionDescription> inventoryConditionDescriptions = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditiondescriptions, languages " +
                        "WHERE invcond_invcon_inventoryconditionid = ? AND invcond_thrutime = ? AND invcond_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditiondescriptions " +
                        "WHERE invcond_invcon_inventoryconditionid = ? AND invcond_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryConditionDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            inventoryConditionDescriptions = InventoryConditionDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryConditionDescriptions;
    }
    
    public List<InventoryConditionDescription> getInventoryConditionDescriptionsByInventoryCondition(InventoryCondition inventoryCondition) {
        return getInventoryConditionDescriptionsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<InventoryConditionDescription> getInventoryConditionDescriptionsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getInventoryConditionDescriptionsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    public String getBestInventoryConditionDescription(InventoryCondition inventoryCondition, Language language) {
        String description;
        InventoryConditionDescription inventoryConditionDescription = getInventoryConditionDescription(inventoryCondition, language);
        
        if(inventoryConditionDescription == null && !language.getIsDefault()) {
            inventoryConditionDescription = getInventoryConditionDescription(inventoryCondition, getPartyControl().getDefaultLanguage());
        }
        
        if(inventoryConditionDescription == null) {
            description = inventoryCondition.getLastDetail().getInventoryConditionName();
        } else {
            description = inventoryConditionDescription.getDescription();
        }
        
        return description;
    }
    
    public InventoryConditionDescriptionTransfer getInventoryConditionDescriptionTransfer(UserVisit userVisit, InventoryConditionDescription inventoryConditionDescription) {
        return getInventoryTransferCaches(userVisit).getInventoryConditionDescriptionTransferCache().getInventoryConditionDescriptionTransfer(inventoryConditionDescription);
    }
    
    public List<InventoryConditionDescriptionTransfer> getInventoryConditionDescriptionTransfersByInventoryCondition(UserVisit userVisit, InventoryCondition inventoryCondition) {
        List<InventoryConditionDescription> inventoryConditionDescriptions = getInventoryConditionDescriptionsByInventoryCondition(inventoryCondition);
        List<InventoryConditionDescriptionTransfer> inventoryConditionDescriptionTransfers = null;
        
        if(inventoryConditionDescriptions != null) {
            inventoryConditionDescriptionTransfers = new ArrayList<>(inventoryConditionDescriptions.size());
            
            for(InventoryConditionDescription inventoryConditionDescription : inventoryConditionDescriptions) {
                inventoryConditionDescriptionTransfers.add(getInventoryTransferCaches(userVisit).getInventoryConditionDescriptionTransferCache().getInventoryConditionDescriptionTransfer(inventoryConditionDescription));
            }
        }
        
        return inventoryConditionDescriptionTransfers;
    }
    
    public void updateInventoryConditionDescriptionFromValue(InventoryConditionDescriptionValue inventoryConditionDescriptionValue, BasePK updatedBy) {
        if(inventoryConditionDescriptionValue.hasBeenModified()) {
            InventoryConditionDescription inventoryConditionDescription = InventoryConditionDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, inventoryConditionDescriptionValue.getPrimaryKey());
            
            inventoryConditionDescription.setThruTime(session.START_TIME_LONG);
            inventoryConditionDescription.store();
            
            InventoryCondition inventoryCondition = inventoryConditionDescription.getInventoryCondition();
            Language language = inventoryConditionDescription.getLanguage();
            String description = inventoryConditionDescriptionValue.getDescription();
            
            inventoryConditionDescription = InventoryConditionDescriptionFactory.getInstance().create(inventoryCondition, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(inventoryCondition.getPrimaryKey(), EventTypes.MODIFY.name(), inventoryConditionDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteInventoryConditionDescription(InventoryConditionDescription inventoryConditionDescription, BasePK deletedBy) {
        inventoryConditionDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(inventoryConditionDescription.getInventoryConditionPK(), EventTypes.MODIFY.name(), inventoryConditionDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }
    
    public void deleteInventoryConditionDescriptionsByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        List<InventoryConditionDescription> inventoryConditionDescriptions = getInventoryConditionDescriptionsByInventoryConditionForUpdate(inventoryCondition);
        
        inventoryConditionDescriptions.stream().forEach((inventoryConditionDescription) -> {
            deleteInventoryConditionDescription(inventoryConditionDescription, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Types
    // --------------------------------------------------------------------------------
    
    public InventoryConditionUseType createInventoryConditionUseType(String inventoryConditionUseTypeName, Boolean isDefault,
            Integer sortOrder) {
        return InventoryConditionUseTypeFactory.getInstance().create(inventoryConditionUseTypeName, isDefault, sortOrder);
    }
    
    public List<InventoryConditionUseType> getInventoryConditionUseTypes() {
        List<InventoryConditionUseType>inventoryConditionUseTypes = null;
        PreparedStatement ps = InventoryConditionUseTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM inventoryconditionusetypes " +
                "ORDER BY invconut_inventoryconditionusetypename");
        
        inventoryConditionUseTypes = InventoryConditionUseTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        
        return inventoryConditionUseTypes;
    }
    
    public InventoryConditionUseType getInventoryConditionUseTypeByName(String inventoryConditionUseTypeName) {
        InventoryConditionUseType inventoryConditionUseType = null;
        
        try {
            PreparedStatement ps = InventoryConditionUseTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM inventoryconditionusetypes " +
                    "WHERE invconut_inventoryconditionusetypename = ?");
            
            ps.setString(1, inventoryConditionUseTypeName);
            
            
            inventoryConditionUseType = InventoryConditionUseTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryConditionUseType;
    }
    
    public InventoryConditionUseTypeChoicesBean getInventoryConditionUseTypeChoices(String defaultInventoryConditionUseTypeChoice, Language language) {
        List<InventoryConditionUseType>inventoryConditionUseTypes = getInventoryConditionUseTypes();
        int size = inventoryConditionUseTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        Iterator iter = inventoryConditionUseTypes.iterator();
        
        while(iter.hasNext()) {
            InventoryConditionUseType inventoryConditionUseType = (InventoryConditionUseType)iter.next();
            
            String label = getBestInventoryConditionUseTypeDescription(inventoryConditionUseType, language);
            String value = inventoryConditionUseType.getInventoryConditionUseTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultInventoryConditionUseTypeChoice == null? false: defaultInventoryConditionUseTypeChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new InventoryConditionUseTypeChoicesBean(labels, values, defaultValue);
    }
    
    public InventoryConditionUseTypeTransfer getInventoryConditionUseTypeTransfer(UserVisit userVisit,
            InventoryConditionUseType inventoryConditionUseType) {
        return getInventoryTransferCaches(userVisit).getInventoryConditionUseTypeTransferCache().getInventoryConditionUseTypeTransfer(inventoryConditionUseType);
    }
    
    private List<InventoryConditionUseTypeTransfer> getInventoryConditionUseTypeTransfers(final UserVisit userVisit,
            final List<InventoryConditionUseType> inventoryConditionUseTypes) {
        List<InventoryConditionUseTypeTransfer> inventoryConditionUseTypeTransfers = null;
        
        if(inventoryConditionUseTypes != null) {
            InventoryConditionUseTypeTransferCache inventoryConditionUseTypeTransferCache = getInventoryTransferCaches(userVisit).getInventoryConditionUseTypeTransferCache();
            
            inventoryConditionUseTypeTransfers = new ArrayList<>(inventoryConditionUseTypes.size());
            
            for(InventoryConditionUseType inventoryConditionUseType: inventoryConditionUseTypes) {
                inventoryConditionUseTypeTransfers.add(inventoryConditionUseTypeTransferCache.getInventoryConditionUseTypeTransfer(inventoryConditionUseType));
            }
        }
        return inventoryConditionUseTypeTransfers;
    }
    
    public List<InventoryConditionUseTypeTransfer> getInventoryConditionUseTypeTransfers(UserVisit userVisit) {
        return getInventoryConditionUseTypeTransfers(userVisit, getInventoryConditionUseTypes());
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Use Type Description
    // --------------------------------------------------------------------------------
    
    public InventoryConditionUseTypeDescription createInventoryConditionUseTypeDescription(InventoryConditionUseType inventoryConditionUseType, Language language, String description) {
        return InventoryConditionUseTypeDescriptionFactory.getInstance().create(inventoryConditionUseType, language, description);
    }
    
    public InventoryConditionUseTypeDescription getInventoryConditionUseTypeDescription(InventoryConditionUseType inventoryConditionUseType, Language language) {
        InventoryConditionUseTypeDescription inventoryConditionUseTypeDescription = null;
        
        try {
            PreparedStatement ps = InventoryConditionUseTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM inventoryconditionusetypedescriptions " +
                    "WHERE invconutd_invconut_inventoryconditionusetypeid = ? AND invconutd_lang_languageid = ?");
            
            ps.setLong(1, inventoryConditionUseType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            inventoryConditionUseTypeDescription = InventoryConditionUseTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryConditionUseTypeDescription;
    }
    
    public String getBestInventoryConditionUseTypeDescription(InventoryConditionUseType inventoryConditionUseType, Language language) {
        String description;
        InventoryConditionUseTypeDescription inventoryConditionUseTypeDescription = getInventoryConditionUseTypeDescription(inventoryConditionUseType, language);
        
        if(inventoryConditionUseTypeDescription == null && !language.getIsDefault()) {
            inventoryConditionUseTypeDescription = getInventoryConditionUseTypeDescription(inventoryConditionUseType, getPartyControl().getDefaultLanguage());
        }
        
        if(inventoryConditionUseTypeDescription == null) {
            description = inventoryConditionUseType.getInventoryConditionUseTypeName();
        } else {
            description = inventoryConditionUseTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Uses
    // --------------------------------------------------------------------------------
    
    public InventoryConditionUse createInventoryConditionUse(InventoryConditionUseType inventoryConditionUseType,
            InventoryCondition inventoryCondition, Boolean isDefault, BasePK createdBy) {
        InventoryConditionUse defaultInventoryConditionUse = getDefaultInventoryConditionUse(inventoryConditionUseType);
        boolean defaultFound = defaultInventoryConditionUse != null;
        
        if(defaultFound && isDefault) {
            InventoryConditionUseValue defaultInventoryConditionUseValue = getDefaultInventoryConditionUseValueForUpdate(inventoryConditionUseType);
            
            defaultInventoryConditionUseValue.setIsDefault(Boolean.FALSE);
            updateInventoryConditionUseFromValue(defaultInventoryConditionUseValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        InventoryConditionUse inventoryConditionUse = InventoryConditionUseFactory.getInstance().create(inventoryConditionUseType,
                inventoryCondition, isDefault, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(inventoryCondition.getPrimaryKey(), EventTypes.MODIFY.name(), inventoryConditionUse.getPrimaryKey(),
                null, createdBy);
        
        return inventoryConditionUse;
    }
    
    private InventoryConditionUse getInventoryConditionUse(InventoryConditionUseType inventoryConditionUseType,
            InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        InventoryConditionUse inventoryConditionUse = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionuses " +
                        "WHERE invconu_invconut_inventoryconditionusetypeid = ? AND invconu_invcon_inventoryconditionid = ? " +
                        "AND invconu_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionuses " +
                        "WHERE invconu_invconut_inventoryconditionusetypeid = ? AND invconu_invcon_inventoryconditionid = ? " +
                        "AND invconu_thrutime = ? " +
                        "FOR UPDATE";
            }
            PreparedStatement ps = InventoryConditionUseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryConditionUseType.getPrimaryKey().getEntityId());
            ps.setLong(2, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            inventoryConditionUse = InventoryConditionUseFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryConditionUse;
    }
    
    public InventoryConditionUse getInventoryConditionUse(InventoryConditionUseType inventoryConditionUseType,
            InventoryCondition inventoryCondition) {
        return getInventoryConditionUse(inventoryConditionUseType, inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public InventoryConditionUse getInventoryConditionUseForUpdate(InventoryConditionUseType inventoryConditionUseType,
            InventoryCondition inventoryCondition) {
        return getInventoryConditionUse(inventoryConditionUseType, inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    public InventoryConditionUseValue getInventoryConditionUseValueForUpdate(InventoryConditionUseType inventoryConditionUseType,
            InventoryCondition inventoryCondition) {
        return getInventoryConditionUseForUpdate(inventoryConditionUseType, inventoryCondition).getInventoryConditionUseValue().clone();
    }
    
    private InventoryConditionUse getDefaultInventoryConditionUse(InventoryConditionUseType inventoryConditionUseType,
            EntityPermission entityPermission) {
        InventoryConditionUse inventoryConditionUse = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionuses " +
                        "WHERE invconu_invconut_inventoryconditionusetypeid = ? AND invconu_isdefault = 1 AND invconu_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionuses " +
                        "WHERE invconu_invconut_inventoryconditionusetypeid = ? AND invconu_isdefault = 1 AND invconu_thrutime = ? " +
                        "FOR UPDATE";
            }
            PreparedStatement ps = InventoryConditionUseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryConditionUseType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            inventoryConditionUse = InventoryConditionUseFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryConditionUse;
    }
    
    public InventoryConditionUse getDefaultInventoryConditionUse(InventoryConditionUseType inventoryConditionUseType) {
        return getDefaultInventoryConditionUse(inventoryConditionUseType, EntityPermission.READ_ONLY);
    }
    
    public InventoryConditionUse getDefaultInventoryConditionUseForUpdate(InventoryConditionUseType inventoryConditionUseType) {
        return getDefaultInventoryConditionUse(inventoryConditionUseType, EntityPermission.READ_WRITE);
    }
    
    public InventoryConditionUseValue getDefaultInventoryConditionUseValueForUpdate(InventoryConditionUseType inventoryConditionUseType) {
        return getDefaultInventoryConditionUseForUpdate(inventoryConditionUseType).getInventoryConditionUseValue().clone();
    }
    
    private List<InventoryConditionUse> getInventoryConditionUsesByInventoryCondition(InventoryCondition inventoryCondition,
            EntityPermission entityPermission) {
        List<InventoryConditionUse> inventoryConditionUses = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionuses, inventoryconditionusetypes " +
                        "WHERE invconu_invcon_inventoryconditionid = ? AND invconu_thrutime = ? " +
                        "AND invconu_invconut_inventoryconditionusetypeid = invconut_inventoryconditionusetypeid " +
                        "ORDER BY invconut_sortorder, invconut_inventoryconditionusetypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionuses " +
                        "WHERE invconu_invcon_inventoryconditionid = ? AND invconu_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryConditionUseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            inventoryConditionUses = InventoryConditionUseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryConditionUses;
    }
    
    public List<InventoryConditionUse> getInventoryConditionUsesByInventoryCondition(InventoryCondition inventoryCondition) {
        return getInventoryConditionUsesByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<InventoryConditionUse> getInventoryConditionUsesByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getInventoryConditionUsesByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    /** Get a List of InventoryConditionUses when the InventoryConditionUseType is allowed to be used by multiple
     * InventoryConditions.
     */
    private List<InventoryConditionUse> getInventoryConditionUsesByInventoryConditionUseType(InventoryConditionUseType inventoryConditionUseType,
            EntityPermission entityPermission) {
        List<InventoryConditionUse> inventoryConditionUses = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionuses, inventoryconditions, inventoryconditiondetails " +
                        "WHERE invconu_invconut_inventoryconditionusetypeid = ? AND invconu_thrutime = ? " +
                        "AND invconu_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_activedetailid = invcondt_inventoryconditiondetailid " +
                        "ORDER BY invcondt_sortorder, invcondt_inventoryconditionname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionuses, inventoryconditions, inventoryconditiondetails " +
                        "WHERE invconu_invconut_inventoryconditionusetypeid = ? AND invconu_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryConditionUseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryConditionUseType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            inventoryConditionUses = InventoryConditionUseFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryConditionUses;
    }
    
    public List<InventoryConditionUse> getInventoryConditionUsesByInventoryConditionUseType(InventoryConditionUseType inventoryConditionUseType) {
        return getInventoryConditionUsesByInventoryConditionUseType(inventoryConditionUseType, EntityPermission.READ_ONLY);
    }
    
    public List<InventoryConditionUse> getInventoryConditionUsesByInventoryConditionUseTypeForUpdate(InventoryConditionUseType inventoryConditionUseType) {
        return getInventoryConditionUsesByInventoryConditionUseType(inventoryConditionUseType, EntityPermission.READ_WRITE);
    }
    
    private List<InventoryConditionUseTransfer> getInventoryConditionUseTransfers(final UserVisit userVisit,
            final List<InventoryConditionUse> inventoryConditionUses) {
        List<InventoryConditionUseTransfer> inventoryConditionUseTransfers = null;
        
        if(inventoryConditionUses != null) {
            InventoryConditionUseTransferCache inventoryConditionUseTransferCache = getInventoryTransferCaches(userVisit).getInventoryConditionUseTransferCache();
            
            inventoryConditionUseTransfers = new ArrayList<>(inventoryConditionUses.size());
            
            for(InventoryConditionUse inventoryConditionUse: inventoryConditionUses) {
                inventoryConditionUseTransfers.add(inventoryConditionUseTransferCache.getInventoryConditionUseTransfer(inventoryConditionUse));
            }
        }
        return inventoryConditionUseTransfers;
    }
    
    public List<InventoryConditionUseTransfer> getInventoryConditionUseTransfersByInventoryCondition(UserVisit userVisit,
            InventoryCondition inventoryCondition) {
        return getInventoryConditionUseTransfers(userVisit, getInventoryConditionUsesByInventoryCondition(inventoryCondition));
    }
    
    public List<InventoryConditionUseTransfer> getInventoryConditionUseTransfersByInventoryConditionUseType(UserVisit userVisit,
            InventoryConditionUseType inventoryConditionUseType) {
        return getInventoryConditionUseTransfers(userVisit, getInventoryConditionUsesByInventoryConditionUseType(inventoryConditionUseType));
    }
    
    public int countInventoryConditionUsesByInventoryConditionUseType(InventoryConditionUseType inventoryConditionUseType) {
        return session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM inventoryconditionuses " +
                "WHERE invconu_invconut_inventoryconditionusetypeid = ? AND invconu_thrutime = ?",
                inventoryConditionUseType, Session.MAX_TIME);
    }
    
    public int countInventoryConditionUsesByInventoryCondition(InventoryCondition inventoryCondition) {
        return session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM inventoryconditionuses " +
                "WHERE invconu_invcon_inventoryconditionid = ? AND invconu_thrutime = ?",
                inventoryCondition, Session.MAX_TIME);
    }
    
    private void updateInventoryConditionUseFromValue(InventoryConditionUseValue inventoryConditionUseValue, boolean checkDefault,
            BasePK updatedBy) {
        InventoryConditionUse inventoryConditionUse = InventoryConditionUseFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, inventoryConditionUseValue.getPrimaryKey());
        
        inventoryConditionUse.setThruTime(session.START_TIME_LONG);
        inventoryConditionUse.store();
        
        InventoryConditionUseTypePK inventoryConditionUseTypePK = inventoryConditionUse.getInventoryConditionUseTypePK();
        InventoryConditionUseType inventoryConditionUseType = inventoryConditionUse.getInventoryConditionUseType();
        InventoryConditionPK inventoryConditionPK = inventoryConditionUse.getInventoryConditionPK();
        Boolean isDefault = inventoryConditionUseValue.getIsDefault();
        
        if(checkDefault) {
            InventoryConditionUse defaultInventoryConditionUse = getDefaultInventoryConditionUse(inventoryConditionUseType);
            boolean defaultFound = defaultInventoryConditionUse != null && !defaultInventoryConditionUse.equals(inventoryConditionUse);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                InventoryConditionUseValue defaultInventoryConditionUseValue = getDefaultInventoryConditionUseValueForUpdate(inventoryConditionUseType);
                
                defaultInventoryConditionUseValue.setIsDefault(Boolean.FALSE);
                updateInventoryConditionUseFromValue(defaultInventoryConditionUseValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }
        
        inventoryConditionUse = InventoryConditionUseFactory.getInstance().create(inventoryConditionUseTypePK,
                inventoryConditionPK, isDefault, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(inventoryConditionPK, EventTypes.MODIFY.name(), inventoryConditionUse.getPrimaryKey(), null, updatedBy);
    }
    
    /** Given a InventoryConditionUseValue, update only the isDefault property.
     */
    public void updateInventoryConditionUseFromValue(InventoryConditionUseValue inventoryConditionUseValue, BasePK updatedBy) {
        updateInventoryConditionUseFromValue(inventoryConditionUseValue, true, updatedBy);
    }
    
    public void deleteInventoryConditionUse(InventoryConditionUse inventoryConditionUse, BasePK deletedBy) {
        inventoryConditionUse.setThruTime(session.START_TIME_LONG);
        inventoryConditionUse.store();
        
        // Check for default, and pick one if necessary
        InventoryConditionUseType inventoryConditionUseType = inventoryConditionUse.getInventoryConditionUseType();
        InventoryConditionUse defaultInventoryConditionUse = getDefaultInventoryConditionUse(inventoryConditionUseType);
        if(defaultInventoryConditionUse == null) {
            List<InventoryConditionUse> inventoryConditionUses = getInventoryConditionUsesByInventoryConditionUseTypeForUpdate(inventoryConditionUseType);
            
            if(!inventoryConditionUses.isEmpty()) {
                Iterator iter = inventoryConditionUses.iterator();
                if(iter.hasNext()) {
                    defaultInventoryConditionUse = (InventoryConditionUse)iter.next();
                }
                InventoryConditionUseValue inventoryConditionUseValue = defaultInventoryConditionUse.getInventoryConditionUseValue().clone();
                
                inventoryConditionUseValue.setIsDefault(Boolean.TRUE);
                updateInventoryConditionUseFromValue(inventoryConditionUseValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(inventoryConditionUse.getInventoryConditionPK(), EventTypes.MODIFY.name(),
                inventoryConditionUse.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteInventoryConditionUseByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        List<InventoryConditionUse> inventoryConditionUses = getInventoryConditionUsesByInventoryConditionForUpdate(inventoryCondition);
        
        inventoryConditionUses.stream().forEach((inventoryConditionUse) -> {
            deleteInventoryConditionUse(inventoryConditionUse, deletedBy);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Gl Accounts
    // --------------------------------------------------------------------------------
    
    public InventoryConditionGlAccount createInventoryConditionGlAccount(InventoryCondition inventoryCondition,
            ItemAccountingCategory itemAccountingCategory, GlAccount inventoryGlAccount, GlAccount salesGlAccount,
            GlAccount returnsGlAccount, GlAccount cogsGlAccount, GlAccount returnsCogsGlAccount, BasePK createdBy) {
        InventoryConditionGlAccount inventoryConditionGlAccount = InventoryConditionGlAccountFactory.getInstance().create(session,
                inventoryCondition, itemAccountingCategory, inventoryGlAccount, salesGlAccount, returnsGlAccount, cogsGlAccount,
                returnsCogsGlAccount, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(inventoryCondition.getPrimaryKey(), EventTypes.MODIFY.name(), inventoryConditionGlAccount.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return inventoryConditionGlAccount;
    }
    
    private InventoryConditionGlAccount getInventoryConditionGlAccount(InventoryCondition inventoryCondition,
            ItemAccountingCategory itemAccountingCategory, EntityPermission entityPermission) {
        InventoryConditionGlAccount inventoryConditionGlAccount = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionglaccounts " +
                        "WHERE invcongla_invcon_inventoryconditionid = ? AND invcongla_iactgc_itemaccountingcategoryid = ? AND invcongla_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionglaccounts " +
                        "WHERE invcongla_invcon_inventoryconditionid = ? AND invcongla_iactgc_itemaccountingcategoryid = ? AND invcongla_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryConditionGlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(2, itemAccountingCategory.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            inventoryConditionGlAccount = InventoryConditionGlAccountFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryConditionGlAccount;
    }
    
    public InventoryConditionGlAccount getInventoryConditionGlAccount(InventoryCondition inventoryCondition, ItemAccountingCategory itemAccountingCategory) {
        return getInventoryConditionGlAccount(inventoryCondition, itemAccountingCategory, EntityPermission.READ_ONLY);
    }
    
    public InventoryConditionGlAccount getInventoryConditionGlAccountForUpdate(InventoryCondition inventoryCondition, ItemAccountingCategory itemAccountingCategory) {
        return getInventoryConditionGlAccount(inventoryCondition, itemAccountingCategory, EntityPermission.READ_WRITE);
    }
    
    public InventoryConditionGlAccountValue getInventoryConditionGlAccountValueForUpdate(InventoryCondition inventoryCondition, ItemAccountingCategory itemAccountingCategory) {
        return getInventoryConditionGlAccountForUpdate(inventoryCondition, itemAccountingCategory).getInventoryConditionGlAccountValue().clone();
    }
    
    private List<InventoryConditionGlAccount> getInventoryConditionGlAccountsByInventoryCondition(InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        List<InventoryConditionGlAccount> inventoryConditionGlAccounts = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionglaccounts, itemaccountingcategories, itemaccountingcategorydetails " +
                        "WHERE invcongla_invcon_inventoryconditionid = ? AND invcongla_thrutime = ? " +
                        "AND invcongla_iactgc_itemaccountingcategoryid = iactgc_itemaccountingcategoryid " +
                        "AND iactgc_lastdetailid = iactgcdt_itemaccountingcategorydetailid " +
                        "ORDER BY iactgcdt_sortorder, iactgcdt_itemaccountingcategoryname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionglaccounts " +
                        "WHERE invcongla_invcon_inventoryconditionid = ? AND invcongla_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryConditionGlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            inventoryConditionGlAccounts = InventoryConditionGlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryConditionGlAccounts;
    }
    
    public List<InventoryConditionGlAccount> getInventoryConditionGlAccountsByInventoryCondition(InventoryCondition inventoryCondition) {
        return getInventoryConditionGlAccountsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<InventoryConditionGlAccount> getInventoryConditionGlAccountsByInventoryConditionForUpdate(InventoryCondition inventoryCondition) {
        return getInventoryConditionGlAccountsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    private List<InventoryConditionGlAccount> getInventoryConditionGlAccountsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory, EntityPermission entityPermission) {
        List<InventoryConditionGlAccount> inventoryConditionGlAccounts = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionglaccounts, inventoryconditions, inventoryconditiondetails " +
                        "WHERE invcongla_iactgc_itemaccountingcategoryid = ? AND invcongla_thrutime = ? " +
                        "AND invcongla_invcon_inventoryconditionid = invcon_inventoryconditionid " +
                        "AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "ORDER BY invcondt_sortorder, invcondt_inventoryconditionname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionglaccounts " +
                        "WHERE invcongla_iactgc_itemaccountingcategoryid = ? AND invcongla_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = InventoryConditionGlAccountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, itemAccountingCategory.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            inventoryConditionGlAccounts = InventoryConditionGlAccountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return inventoryConditionGlAccounts;
    }
    
    public List<InventoryConditionGlAccount> getInventoryConditionGlAccountsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory) {
        return getInventoryConditionGlAccountsByItemAccountingCategory(itemAccountingCategory, EntityPermission.READ_ONLY);
    }
    
    public List<InventoryConditionGlAccount> getInventoryConditionGlAccountsByItemAccountingCategoryForUpdate(ItemAccountingCategory itemAccountingCategory) {
        return getInventoryConditionGlAccountsByItemAccountingCategory(itemAccountingCategory, EntityPermission.READ_WRITE);
    }
    
    public void updateInventoryConditionGlAccountFromValue(InventoryConditionGlAccountValue inventoryConditionGlAccountValue, BasePK updatedBy) {
        if(inventoryConditionGlAccountValue.hasBeenModified()) {
            InventoryConditionGlAccount inventoryConditionGlAccount = InventoryConditionGlAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryConditionGlAccountValue.getPrimaryKey());
            
            inventoryConditionGlAccount.setThruTime(session.START_TIME_LONG);
            inventoryConditionGlAccount.store();
            
            InventoryConditionPK inventoryConditionPK = inventoryConditionGlAccount.getInventoryConditionPK();
            ItemAccountingCategoryPK itemAccountingCategoryPK = inventoryConditionGlAccount.getItemAccountingCategoryPK();
            GlAccountPK inventoryGlAccountPK = inventoryConditionGlAccountValue.getInventoryGlAccountPK();
            GlAccountPK salesGlAccountPK = inventoryConditionGlAccountValue.getSalesGlAccountPK();
            GlAccountPK returnsGlAccountPK = inventoryConditionGlAccountValue.getReturnsGlAccountPK();
            GlAccountPK cogsGlAccountPK = inventoryConditionGlAccountValue.getCogsGlAccountPK();
            GlAccountPK returnsCogsGlAccountPK = inventoryConditionGlAccountValue.getReturnsCogsGlAccountPK();
            
            inventoryConditionGlAccount = InventoryConditionGlAccountFactory.getInstance().create(inventoryConditionPK,
                    itemAccountingCategoryPK, inventoryGlAccountPK, salesGlAccountPK, returnsGlAccountPK, cogsGlAccountPK,
                    returnsCogsGlAccountPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(inventoryConditionPK, EventTypes.MODIFY.name(), inventoryConditionGlAccount.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public InventoryConditionGlAccountTransfer getInventoryConditionGlAccountTransfer(UserVisit userVisit, InventoryConditionGlAccount inventoryConditionGlAccount) {
        return inventoryConditionGlAccount == null? null: getInventoryTransferCaches(userVisit).getInventoryConditionGlAccountTransferCache().getInventoryConditionGlAccountTransfer(inventoryConditionGlAccount);
    }
    
    public InventoryConditionGlAccountTransfer getInventoryConditionGlAccountTransfer(UserVisit userVisit, InventoryCondition inventoryCondition, ItemAccountingCategory itemAccountingCategory) {
        return getInventoryConditionGlAccountTransfer(userVisit, getInventoryConditionGlAccount(inventoryCondition, itemAccountingCategory));
    }
    
    public List<InventoryConditionGlAccountTransfer> getInventoryConditionGlAccountTransfersByInventoryCondition(UserVisit userVisit, InventoryCondition inventoryCondition) {
        List<InventoryConditionGlAccount> inventoryConditionGlAccounts = getInventoryConditionGlAccountsByInventoryCondition(inventoryCondition);
        List<InventoryConditionGlAccountTransfer> inventoryConditionGlAccountTransfers = new ArrayList<>(inventoryConditionGlAccounts.size());
        InventoryConditionGlAccountTransferCache inventoryConditionGlAccountTransferCache = getInventoryTransferCaches(userVisit).getInventoryConditionGlAccountTransferCache();
        
        inventoryConditionGlAccounts.stream().forEach((inventoryConditionGlAccount) -> {
            inventoryConditionGlAccountTransfers.add(inventoryConditionGlAccountTransferCache.getInventoryConditionGlAccountTransfer(inventoryConditionGlAccount));
        });
        
        return inventoryConditionGlAccountTransfers;
    }
    
    public void deleteInventoryConditionGlAccount(InventoryConditionGlAccount inventoryConditionGlAccount, BasePK deletedBy) {
        inventoryConditionGlAccount.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(inventoryConditionGlAccount.getInventoryConditionPK(), EventTypes.MODIFY.name(), inventoryConditionGlAccount.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteInventoryConditionGlAccounts(List<InventoryConditionGlAccount> inventoryConditionGlAccounts, BasePK deletedBy) {
        inventoryConditionGlAccounts.stream().forEach((inventoryConditionGlAccount) -> {
            deleteInventoryConditionGlAccount(inventoryConditionGlAccount, deletedBy);
        });
    }
    
    public void deleteInventoryConditionGlAccountsByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        deleteInventoryConditionGlAccounts(getInventoryConditionGlAccountsByInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }
    
    public void deleteInventoryConditionGlAccountsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory, BasePK deletedBy) {
        deleteInventoryConditionGlAccounts(getInventoryConditionGlAccountsByItemAccountingCategoryForUpdate(itemAccountingCategory), deletedBy);
    }
    
    public void deleteInventoryConditionGlAccountByInventoryConditionAndItemAccountingCategory(InventoryCondition inventoryCondition, ItemAccountingCategory itemAccountingCategory, BasePK deletedBy) {
        InventoryConditionGlAccount inventoryConditionGlAccount = getInventoryConditionGlAccountForUpdate(inventoryCondition, itemAccountingCategory);
        
        if(inventoryConditionGlAccount!= null) {
            deleteInventoryConditionGlAccount(inventoryConditionGlAccount, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Lot Types
    // --------------------------------------------------------------------------------

    public LotType createLotType(String lotTypeName, LotType parentLotType, SequenceType lotSequenceType, Workflow lotWorkflow,
            WorkflowEntrance lotWorkflowEntrance, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        LotType defaultLotType = getDefaultLotType();
        boolean defaultFound = defaultLotType != null;

        if(defaultFound && isDefault) {
            LotTypeDetailValue defaultLotTypeDetailValue = getDefaultLotTypeDetailValueForUpdate();

            defaultLotTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateLotTypeFromValue(defaultLotTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        LotType lotType = LotTypeFactory.getInstance().create();
        LotTypeDetail lotTypeDetail = LotTypeDetailFactory.getInstance().create(lotType, lotTypeName, parentLotType,
                lotSequenceType, lotWorkflow, lotWorkflowEntrance, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        // Convert to R/W
        lotType = LotTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                lotType.getPrimaryKey());
        lotType.setActiveDetail(lotTypeDetail);
        lotType.setLastDetail(lotTypeDetail);
        lotType.store();

        sendEventUsingNames(lotType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return lotType;
    }

    private static final Map<EntityPermission, String> getLotTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottypes, lottypedetails " +
                "WHERE lttyp_activedetailid = lttypdt_lottypedetailid " +
                "AND lttypdt_lottypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottypes, lottypedetails " +
                "WHERE lttyp_activedetailid = lttypdt_lottypedetailid " +
                "AND lttypdt_lottypename = ? " +
                "FOR UPDATE");
        getLotTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotType getLotTypeByName(String lotTypeName, EntityPermission entityPermission) {
        return LotTypeFactory.getInstance().getEntityFromQuery(entityPermission, getLotTypeByNameQueries, lotTypeName);
    }

    public LotType getLotTypeByName(String lotTypeName) {
        return getLotTypeByName(lotTypeName, EntityPermission.READ_ONLY);
    }

    public LotType getLotTypeByNameForUpdate(String lotTypeName) {
        return getLotTypeByName(lotTypeName, EntityPermission.READ_WRITE);
    }

    public LotTypeDetailValue getLotTypeDetailValueForUpdate(LotType lotType) {
        return lotType == null? null: lotType.getLastDetailForUpdate().getLotTypeDetailValue().clone();
    }

    public LotTypeDetailValue getLotTypeDetailValueByNameForUpdate(String lotTypeName) {
        return getLotTypeDetailValueForUpdate(getLotTypeByNameForUpdate(lotTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultLotTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottypes, lottypedetails " +
                "WHERE lttyp_activedetailid = lttypdt_lottypedetailid " +
                "AND lttypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottypes, lottypedetails " +
                "WHERE lttyp_activedetailid = lttypdt_lottypedetailid " +
                "AND lttypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultLotTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotType getDefaultLotType(EntityPermission entityPermission) {
        return LotTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultLotTypeQueries);
    }

    public LotType getDefaultLotType() {
        return getDefaultLotType(EntityPermission.READ_ONLY);
    }

    public LotType getDefaultLotTypeForUpdate() {
        return getDefaultLotType(EntityPermission.READ_WRITE);
    }

    public LotTypeDetailValue getDefaultLotTypeDetailValueForUpdate() {
        return getDefaultLotTypeForUpdate().getLastDetailForUpdate().getLotTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getLotTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottypes, lottypedetails " +
                "WHERE lttyp_activedetailid = lttypdt_lottypedetailid " +
                "ORDER BY lttypdt_sortorder, lttypdt_lottypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottypes, lottypedetails " +
                "WHERE lttyp_activedetailid = lttypdt_lottypedetailid " +
                "FOR UPDATE");
        getLotTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotType> getLotTypes(EntityPermission entityPermission) {
        return LotTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotTypesQueries);
    }

    public List<LotType> getLotTypes() {
        return getLotTypes(EntityPermission.READ_ONLY);
    }

    public List<LotType> getLotTypesForUpdate() {
        return getLotTypes(EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLotTypesByParentLotTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottypes, lottypedetails " +
                "WHERE lttyp_activedetailid = lttypdt_lottypedetailid AND lttypdt_parentlottypeid = ? " +
                "ORDER BY lttypdt_sortorder, lttypdt_lottypename " +
                "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottypes, lottypedetails " +
                "WHERE lttyp_activedetailid = lttypdt_lottypedetailid AND lttypdt_parentlottypeid = ? " +
                "FOR UPDATE");
        getLotTypesByParentLotTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotType> getLotTypesByParentLotType(LotType parentLotType,
            EntityPermission entityPermission) {
        return LotTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotTypesByParentLotTypeQueries,
                parentLotType);
    }

    public List<LotType> getLotTypesByParentLotType(LotType parentLotType) {
        return getLotTypesByParentLotType(parentLotType, EntityPermission.READ_ONLY);
    }

    public List<LotType> getLotTypesByParentLotTypeForUpdate(LotType parentLotType) {
        return getLotTypesByParentLotType(parentLotType, EntityPermission.READ_WRITE);
    }

    public LotTypeTransfer getLotTypeTransfer(UserVisit userVisit, LotType lotType) {
        return getInventoryTransferCaches(userVisit).getLotTypeTransferCache().getLotTypeTransfer(lotType);
    }

    public List<LotTypeTransfer> getLotTypeTransfers(UserVisit userVisit) {
        List<LotType> lotTypes = getLotTypes();
        List<LotTypeTransfer> lotTypeTransfers = new ArrayList<>(lotTypes.size());
        LotTypeTransferCache lotTypeTransferCache = getInventoryTransferCaches(userVisit).getLotTypeTransferCache();

        lotTypes.stream().forEach((lotType) -> {
            lotTypeTransfers.add(lotTypeTransferCache.getLotTypeTransfer(lotType));
        });

        return lotTypeTransfers;
    }

    public LotTypeChoicesBean getLotTypeChoices(String defaultLotTypeChoice,
            Language language, boolean allowNullChoice) {
        List<LotType> lotTypes = getLotTypes();
        int size = lotTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultLotTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(LotType lotType: lotTypes) {
            LotTypeDetail lotTypeDetail = lotType.getLastDetail();

            String label = getBestLotTypeDescription(lotType, language);
            String value = lotTypeDetail.getLotTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultLotTypeChoice == null? false: defaultLotTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && lotTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new LotTypeChoicesBean(labels, values, defaultValue);
    }

    public boolean isParentLotTypeSafe(LotType lotType,
            LotType parentLotType) {
        boolean safe = true;

        if(parentLotType != null) {
            Set<LotType> parentLotTypes = new HashSet<>();

            parentLotTypes.add(lotType);
            do {
                if(parentLotTypes.contains(parentLotType)) {
                    safe = false;
                    break;
                }

                parentLotTypes.add(parentLotType);
                parentLotType = parentLotType.getLastDetail().getParentLotType();
            } while(parentLotType != null);
        }

        return safe;
    }

    private void updateLotTypeFromValue(LotTypeDetailValue lotTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(lotTypeDetailValue.hasBeenModified()) {
            LotType lotType = LotTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     lotTypeDetailValue.getLotTypePK());
            LotTypeDetail lotTypeDetail = lotType.getActiveDetailForUpdate();

            lotTypeDetail.setThruTime(session.START_TIME_LONG);
            lotTypeDetail.store();

            LotTypePK lotTypePK = lotTypeDetail.getLotTypePK(); // Not updated
            String lotTypeName = lotTypeDetailValue.getLotTypeName();
            LotTypePK parentLotTypePK = lotTypeDetailValue.getParentLotTypePK();
            SequenceTypePK lotSequenceTypePK = lotTypeDetailValue.getLotSequenceTypePK();
            WorkflowPK lotWorkflowPK = lotTypeDetailValue.getLotWorkflowPK();
            WorkflowEntrancePK lotWorkflowEntrancePK = lotTypeDetailValue.getLotWorkflowEntrancePK();
            Boolean isDefault = lotTypeDetailValue.getIsDefault();
            Integer sortOrder = lotTypeDetailValue.getSortOrder();

            if(checkDefault) {
                LotType defaultLotType = getDefaultLotType();
                boolean defaultFound = defaultLotType != null && !defaultLotType.equals(lotType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    LotTypeDetailValue defaultLotTypeDetailValue = getDefaultLotTypeDetailValueForUpdate();

                    defaultLotTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateLotTypeFromValue(defaultLotTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            lotTypeDetail = LotTypeDetailFactory.getInstance().create(lotTypePK, lotTypeName, parentLotTypePK, lotSequenceTypePK, lotWorkflowPK,
                    lotWorkflowEntrancePK, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            lotType.setActiveDetail(lotTypeDetail);
            lotType.setLastDetail(lotTypeDetail);

            sendEventUsingNames(lotTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateLotTypeFromValue(LotTypeDetailValue lotTypeDetailValue, BasePK updatedBy) {
        updateLotTypeFromValue(lotTypeDetailValue, true, updatedBy);
    }

    private void deleteLotType(LotType lotType, boolean checkDefault, BasePK deletedBy) {
        LotTypeDetail lotTypeDetail = lotType.getLastDetailForUpdate();

        deleteLotTypesByParentLotType(lotType, deletedBy);
        deleteLotTypeDescriptionsByLotType(lotType, deletedBy);
        deleteLotAliasTypesByLotType(lotType, deletedBy);
        // TODO: deleteLotsByLotType(lotType, deletedBy);

        lotTypeDetail.setThruTime(session.START_TIME_LONG);
        lotType.setActiveDetail(null);
        lotType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            LotType defaultLotType = getDefaultLotType();

            if(defaultLotType == null) {
                List<LotType> lotTypes = getLotTypesForUpdate();

                if(!lotTypes.isEmpty()) {
                    Iterator<LotType> iter = lotTypes.iterator();
                    if(iter.hasNext()) {
                        defaultLotType = iter.next();
                    }
                    LotTypeDetailValue lotTypeDetailValue = defaultLotType.getLastDetailForUpdate().getLotTypeDetailValue().clone();

                    lotTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateLotTypeFromValue(lotTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEventUsingNames(lotType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteLotType(LotType lotType, BasePK deletedBy) {
        deleteLotType(lotType, true, deletedBy);
    }

    private void deleteLotTypes(List<LotType> lotTypes, boolean checkDefault, BasePK deletedBy) {
        lotTypes.stream().forEach((lotType) -> {
            deleteLotType(lotType, checkDefault, deletedBy);
        });
    }

    public void deleteLotTypes(List<LotType> lotTypes, BasePK deletedBy) {
        deleteLotTypes(lotTypes, true, deletedBy);
    }

    private void deleteLotTypesByParentLotType(LotType parentLotType, BasePK deletedBy) {
        deleteLotTypes(getLotTypesByParentLotTypeForUpdate(parentLotType), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Lot Type Descriptions
    // --------------------------------------------------------------------------------

    public LotTypeDescription createLotTypeDescription(LotType lotType, Language language, String description, BasePK createdBy) {
        LotTypeDescription lotTypeDescription = LotTypeDescriptionFactory.getInstance().create(lotType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(lotType.getPrimaryKey(), EventTypes.MODIFY.name(), lotTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return lotTypeDescription;
    }

    private static final Map<EntityPermission, String> getLotTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottypedescriptions " +
                "WHERE lttypd_lttyp_lottypeid = ? AND lttypd_lang_languageid = ? AND lttypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottypedescriptions " +
                "WHERE lttypd_lttyp_lottypeid = ? AND lttypd_lang_languageid = ? AND lttypd_thrutime = ? " +
                "FOR UPDATE");
        getLotTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotTypeDescription getLotTypeDescription(LotType lotType, Language language, EntityPermission entityPermission) {
        return LotTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getLotTypeDescriptionQueries,
                lotType, language, Session.MAX_TIME);
    }

    public LotTypeDescription getLotTypeDescription(LotType lotType, Language language) {
        return getLotTypeDescription(lotType, language, EntityPermission.READ_ONLY);
    }

    public LotTypeDescription getLotTypeDescriptionForUpdate(LotType lotType, Language language) {
        return getLotTypeDescription(lotType, language, EntityPermission.READ_WRITE);
    }

    public LotTypeDescriptionValue getLotTypeDescriptionValue(LotTypeDescription lotTypeDescription) {
        return lotTypeDescription == null? null: lotTypeDescription.getLotTypeDescriptionValue().clone();
    }

    public LotTypeDescriptionValue getLotTypeDescriptionValueForUpdate(LotType lotType, Language language) {
        return getLotTypeDescriptionValue(getLotTypeDescriptionForUpdate(lotType, language));
    }

    private static final Map<EntityPermission, String> getLotTypeDescriptionsByLotTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottypedescriptions, languages " +
                "WHERE lttypd_lttyp_lottypeid = ? AND lttypd_thrutime = ? AND lttypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottypedescriptions " +
                "WHERE lttypd_lttyp_lottypeid = ? AND lttypd_thrutime = ? " +
                "FOR UPDATE");
        getLotTypeDescriptionsByLotTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotTypeDescription> getLotTypeDescriptionsByLotType(LotType lotType, EntityPermission entityPermission) {
        return LotTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotTypeDescriptionsByLotTypeQueries,
                lotType, Session.MAX_TIME);
    }

    public List<LotTypeDescription> getLotTypeDescriptionsByLotType(LotType lotType) {
        return getLotTypeDescriptionsByLotType(lotType, EntityPermission.READ_ONLY);
    }

    public List<LotTypeDescription> getLotTypeDescriptionsByLotTypeForUpdate(LotType lotType) {
        return getLotTypeDescriptionsByLotType(lotType, EntityPermission.READ_WRITE);
    }

    public String getBestLotTypeDescription(LotType lotType, Language language) {
        String description;
        LotTypeDescription lotTypeDescription = getLotTypeDescription(lotType, language);

        if(lotTypeDescription == null && !language.getIsDefault()) {
            lotTypeDescription = getLotTypeDescription(lotType, getPartyControl().getDefaultLanguage());
        }

        if(lotTypeDescription == null) {
            description = lotType.getLastDetail().getLotTypeName();
        } else {
            description = lotTypeDescription.getDescription();
        }

        return description;
    }

    public LotTypeDescriptionTransfer getLotTypeDescriptionTransfer(UserVisit userVisit, LotTypeDescription lotTypeDescription) {
        return getInventoryTransferCaches(userVisit).getLotTypeDescriptionTransferCache().getLotTypeDescriptionTransfer(lotTypeDescription);
    }

    public List<LotTypeDescriptionTransfer> getLotTypeDescriptionTransfersByLotType(UserVisit userVisit, LotType lotType) {
        List<LotTypeDescription> lotTypeDescriptions = getLotTypeDescriptionsByLotType(lotType);
        List<LotTypeDescriptionTransfer> lotTypeDescriptionTransfers = new ArrayList<>(lotTypeDescriptions.size());
        LotTypeDescriptionTransferCache lotTypeDescriptionTransferCache = getInventoryTransferCaches(userVisit).getLotTypeDescriptionTransferCache();

        lotTypeDescriptions.stream().forEach((lotTypeDescription) -> {
            lotTypeDescriptionTransfers.add(lotTypeDescriptionTransferCache.getLotTypeDescriptionTransfer(lotTypeDescription));
        });

        return lotTypeDescriptionTransfers;
    }

    public void updateLotTypeDescriptionFromValue(LotTypeDescriptionValue lotTypeDescriptionValue, BasePK updatedBy) {
        if(lotTypeDescriptionValue.hasBeenModified()) {
            LotTypeDescription lotTypeDescription = LotTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    lotTypeDescriptionValue.getPrimaryKey());

            lotTypeDescription.setThruTime(session.START_TIME_LONG);
            lotTypeDescription.store();

            LotType lotType = lotTypeDescription.getLotType();
            Language language = lotTypeDescription.getLanguage();
            String description = lotTypeDescriptionValue.getDescription();

            lotTypeDescription = LotTypeDescriptionFactory.getInstance().create(lotType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(lotType.getPrimaryKey(), EventTypes.MODIFY.name(), lotTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteLotTypeDescription(LotTypeDescription lotTypeDescription, BasePK deletedBy) {
        lotTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(lotTypeDescription.getLotTypePK(), EventTypes.MODIFY.name(), lotTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteLotTypeDescriptionsByLotType(LotType lotType, BasePK deletedBy) {
        List<LotTypeDescription> lotTypeDescriptions = getLotTypeDescriptionsByLotTypeForUpdate(lotType);

        lotTypeDescriptions.stream().forEach((lotTypeDescription) -> {
            deleteLotTypeDescription(lotTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Lot Time Types
    // --------------------------------------------------------------------------------

    public LotTimeType createLotTimeType(LotType lotType, String lotTimeTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        LotTimeType defaultLotTimeType = getDefaultLotTimeType(lotType);
        boolean defaultFound = defaultLotTimeType != null;

        if(defaultFound && isDefault) {
            LotTimeTypeDetailValue defaultLotTimeTypeDetailValue = getDefaultLotTimeTypeDetailValueForUpdate(lotType);

            defaultLotTimeTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateLotTimeTypeFromValue(defaultLotTimeTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        LotTimeType lotTimeType = LotTimeTypeFactory.getInstance().create();
        LotTimeTypeDetail lotTimeTypeDetail = LotTimeTypeDetailFactory.getInstance().create(lotTimeType, lotType, lotTimeTypeName, isDefault,
                sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        lotTimeType = LotTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                lotTimeType.getPrimaryKey());
        lotTimeType.setActiveDetail(lotTimeTypeDetail);
        lotTimeType.setLastDetail(lotTimeTypeDetail);
        lotTimeType.store();

        sendEventUsingNames(lotTimeType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return lotTimeType;
    }

    private static final Map<EntityPermission, String> getLotTimeTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimetypes, lottimetypedetails " +
                "WHERE lttimtyp_activedetailid = lttimtypdt_lottimetypedetailid " +
                "AND lttimtypdt_lttyp_lottypeid = ? AND lttimtypdt_lottimetypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimetypes, lottimetypedetails " +
                "WHERE lttimtyp_activedetailid = lttimtypdt_lottimetypedetailid " +
                "AND lttimtypdt_lttyp_lottypeid = ? AND lttimtypdt_lottimetypename = ? " +
                "FOR UPDATE");
        getLotTimeTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotTimeType getLotTimeTypeByName(LotType lotType, String lotTimeTypeName, EntityPermission entityPermission) {
        return LotTimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getLotTimeTypeByNameQueries,
                lotType, lotTimeTypeName);
    }

    public LotTimeType getLotTimeTypeByName(LotType lotType, String lotTimeTypeName) {
        return getLotTimeTypeByName(lotType, lotTimeTypeName, EntityPermission.READ_ONLY);
    }

    public LotTimeType getLotTimeTypeByNameForUpdate(LotType lotType, String lotTimeTypeName) {
        return getLotTimeTypeByName(lotType, lotTimeTypeName, EntityPermission.READ_WRITE);
    }

    public LotTimeTypeDetailValue getLotTimeTypeDetailValueForUpdate(LotTimeType lotTimeType) {
        return lotTimeType == null? null: lotTimeType.getLastDetailForUpdate().getLotTimeTypeDetailValue().clone();
    }

    public LotTimeTypeDetailValue getLotTimeTypeDetailValueByNameForUpdate(LotType lotType, String lotTimeTypeName) {
        return getLotTimeTypeDetailValueForUpdate(getLotTimeTypeByNameForUpdate(lotType, lotTimeTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultLotTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimetypes, lottimetypedetails " +
                "WHERE lttimtyp_activedetailid = lttimtypdt_lottimetypedetailid " +
                "AND lttimtypdt_lttyp_lottypeid = ? AND lttimtypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimetypes, lottimetypedetails " +
                "WHERE lttimtyp_activedetailid = lttimtypdt_lottimetypedetailid " +
                "AND lttimtypdt_lttyp_lottypeid = ? AND lttimtypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultLotTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotTimeType getDefaultLotTimeType(LotType lotType, EntityPermission entityPermission) {
        return LotTimeTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultLotTimeTypeQueries,
                lotType);
    }

    public LotTimeType getDefaultLotTimeType(LotType lotType) {
        return getDefaultLotTimeType(lotType, EntityPermission.READ_ONLY);
    }

    public LotTimeType getDefaultLotTimeTypeForUpdate(LotType lotType) {
        return getDefaultLotTimeType(lotType, EntityPermission.READ_WRITE);
    }

    public LotTimeTypeDetailValue getDefaultLotTimeTypeDetailValueForUpdate(LotType lotType) {
        return getDefaultLotTimeTypeForUpdate(lotType).getLastDetailForUpdate().getLotTimeTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getLotTimeTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimetypes, lottimetypedetails " +
                "WHERE lttimtyp_activedetailid = lttimtypdt_lottimetypedetailid " +
                "AND lttimtypdt_lttyp_lottypeid = ? " +
                "ORDER BY lttimtypdt_sortorder, lttimtypdt_lottimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimetypes, lottimetypedetails " +
                "WHERE lttimtyp_activedetailid = lttimtypdt_lottimetypedetailid " +
                "AND lttimtypdt_lttyp_lottypeid = ? " +
                "FOR UPDATE");
        getLotTimeTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotTimeType> getLotTimeTypes(LotType lotType, EntityPermission entityPermission) {
        return LotTimeTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotTimeTypesQueries,
                lotType);
    }

    public List<LotTimeType> getLotTimeTypes(LotType lotType) {
        return getLotTimeTypes(lotType, EntityPermission.READ_ONLY);
    }

    public List<LotTimeType> getLotTimeTypesForUpdate(LotType lotType) {
        return getLotTimeTypes(lotType, EntityPermission.READ_WRITE);
    }

    public LotTimeTypeTransfer getLotTimeTypeTransfer(UserVisit userVisit, LotTimeType lotTimeType) {
        return getInventoryTransferCaches(userVisit).getLotTimeTypeTransferCache().getLotTimeTypeTransfer(lotTimeType);
    }

    public List<LotTimeTypeTransfer> getLotTimeTypeTransfers(UserVisit userVisit, LotType lotType) {
        List<LotTimeType> lotTimeTypes = getLotTimeTypes(lotType);
        List<LotTimeTypeTransfer> lotTimeTypeTransfers = new ArrayList<>(lotTimeTypes.size());
        LotTimeTypeTransferCache lotTimeTypeTransferCache = getInventoryTransferCaches(userVisit).getLotTimeTypeTransferCache();

        lotTimeTypes.stream().forEach((lotTimeType) -> {
            lotTimeTypeTransfers.add(lotTimeTypeTransferCache.getLotTimeTypeTransfer(lotTimeType));
        });

        return lotTimeTypeTransfers;
    }

    public LotTimeTypeChoicesBean getLotTimeTypeChoices(String defaultLotTimeTypeChoice, Language language, boolean allowNullChoice,
            LotType lotType) {
        List<LotTimeType> lotTimeTypes = getLotTimeTypes(lotType);
        int size = lotTimeTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultLotTimeTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(LotTimeType lotTimeType: lotTimeTypes) {
            LotTimeTypeDetail lotTimeTypeDetail = lotTimeType.getLastDetail();

            String label = getBestLotTimeTypeDescription(lotTimeType, language);
            String value = lotTimeTypeDetail.getLotTimeTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultLotTimeTypeChoice == null? false: defaultLotTimeTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && lotTimeTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new LotTimeTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateLotTimeTypeFromValue(LotTimeTypeDetailValue lotTimeTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(lotTimeTypeDetailValue.hasBeenModified()) {
            LotTimeType lotTimeType = LotTimeTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     lotTimeTypeDetailValue.getLotTimeTypePK());
            LotTimeTypeDetail lotTimeTypeDetail = lotTimeType.getActiveDetailForUpdate();

            lotTimeTypeDetail.setThruTime(session.START_TIME_LONG);
            lotTimeTypeDetail.store();

            LotType lotType = lotTimeTypeDetail.getLotType(); // Not updated
            LotTypePK lotTypePK = lotType.getPrimaryKey(); // Not updated
            LotTimeTypePK lotTimeTypePK = lotTimeTypeDetail.getLotTimeTypePK(); // Not updated
            String lotTimeTypeName = lotTimeTypeDetailValue.getLotTimeTypeName();
            Boolean isDefault = lotTimeTypeDetailValue.getIsDefault();
            Integer sortOrder = lotTimeTypeDetailValue.getSortOrder();

            if(checkDefault) {
                LotTimeType defaultLotTimeType = getDefaultLotTimeType(lotType);
                boolean defaultFound = defaultLotTimeType != null && !defaultLotTimeType.equals(lotTimeType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    LotTimeTypeDetailValue defaultLotTimeTypeDetailValue = getDefaultLotTimeTypeDetailValueForUpdate(lotType);

                    defaultLotTimeTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateLotTimeTypeFromValue(defaultLotTimeTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            lotTimeTypeDetail = LotTimeTypeDetailFactory.getInstance().create(lotTimeTypePK, lotTypePK, lotTimeTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            lotTimeType.setActiveDetail(lotTimeTypeDetail);
            lotTimeType.setLastDetail(lotTimeTypeDetail);

            sendEventUsingNames(lotTimeTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateLotTimeTypeFromValue(LotTimeTypeDetailValue lotTimeTypeDetailValue, BasePK updatedBy) {
        updateLotTimeTypeFromValue(lotTimeTypeDetailValue, true, updatedBy);
    }

    public void deleteLotTimeType(LotTimeType lotTimeType, BasePK deletedBy) {
        deleteLotTimesByLotTimeType(lotTimeType, deletedBy);
        deleteLotTimeTypeDescriptionsByLotTimeType(lotTimeType, deletedBy);

        LotTimeTypeDetail lotTimeTypeDetail = lotTimeType.getLastDetailForUpdate();
        lotTimeTypeDetail.setThruTime(session.START_TIME_LONG);
        lotTimeType.setActiveDetail(null);
        lotTimeType.store();

        // Check for default, and pick one if necessary
        LotType lotType = lotTimeTypeDetail.getLotType();
        LotTimeType defaultLotTimeType = getDefaultLotTimeType(lotType);
        if(defaultLotTimeType == null) {
            List<LotTimeType> lotTimeTypes = getLotTimeTypesForUpdate(lotType);

            if(!lotTimeTypes.isEmpty()) {
                Iterator<LotTimeType> iter = lotTimeTypes.iterator();
                if(iter.hasNext()) {
                    defaultLotTimeType = iter.next();
                }
                LotTimeTypeDetailValue lotTimeTypeDetailValue = defaultLotTimeType.getLastDetailForUpdate().getLotTimeTypeDetailValue().clone();

                lotTimeTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateLotTimeTypeFromValue(lotTimeTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(lotTimeType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Lot Time Type Descriptions
    // --------------------------------------------------------------------------------

    public LotTimeTypeDescription createLotTimeTypeDescription(LotTimeType lotTimeType, Language language, String description, BasePK createdBy) {
        LotTimeTypeDescription lotTimeTypeDescription = LotTimeTypeDescriptionFactory.getInstance().create(lotTimeType, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(lotTimeType.getPrimaryKey(), EventTypes.MODIFY.name(), lotTimeTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return lotTimeTypeDescription;
    }

    private static final Map<EntityPermission, String> getLotTimeTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimetypedescriptions " +
                "WHERE lttimtypd_lttimtyp_lottimetypeid = ? AND lttimtypd_lang_languageid = ? AND lttimtypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimetypedescriptions " +
                "WHERE lttimtypd_lttimtyp_lottimetypeid = ? AND lttimtypd_lang_languageid = ? AND lttimtypd_thrutime = ? " +
                "FOR UPDATE");
        getLotTimeTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotTimeTypeDescription getLotTimeTypeDescription(LotTimeType lotTimeType, Language language, EntityPermission entityPermission) {
        return LotTimeTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getLotTimeTypeDescriptionQueries,
                lotTimeType, language, Session.MAX_TIME);
    }

    public LotTimeTypeDescription getLotTimeTypeDescription(LotTimeType lotTimeType, Language language) {
        return getLotTimeTypeDescription(lotTimeType, language, EntityPermission.READ_ONLY);
    }

    public LotTimeTypeDescription getLotTimeTypeDescriptionForUpdate(LotTimeType lotTimeType, Language language) {
        return getLotTimeTypeDescription(lotTimeType, language, EntityPermission.READ_WRITE);
    }

    public LotTimeTypeDescriptionValue getLotTimeTypeDescriptionValue(LotTimeTypeDescription lotTimeTypeDescription) {
        return lotTimeTypeDescription == null? null: lotTimeTypeDescription.getLotTimeTypeDescriptionValue().clone();
    }

    public LotTimeTypeDescriptionValue getLotTimeTypeDescriptionValueForUpdate(LotTimeType lotTimeType, Language language) {
        return getLotTimeTypeDescriptionValue(getLotTimeTypeDescriptionForUpdate(lotTimeType, language));
    }

    private static final Map<EntityPermission, String> getLotTimeTypeDescriptionsByLotTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimetypedescriptions, languages " +
                "WHERE lttimtypd_lttimtyp_lottimetypeid = ? AND lttimtypd_thrutime = ? AND lttimtypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimetypedescriptions " +
                "WHERE lttimtypd_lttimtyp_lottimetypeid = ? AND lttimtypd_thrutime = ? " +
                "FOR UPDATE");
        getLotTimeTypeDescriptionsByLotTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotTimeTypeDescription> getLotTimeTypeDescriptionsByLotTimeType(LotTimeType lotTimeType, EntityPermission entityPermission) {
        return LotTimeTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotTimeTypeDescriptionsByLotTimeTypeQueries,
                lotTimeType, Session.MAX_TIME);
    }

    public List<LotTimeTypeDescription> getLotTimeTypeDescriptionsByLotTimeType(LotTimeType lotTimeType) {
        return getLotTimeTypeDescriptionsByLotTimeType(lotTimeType, EntityPermission.READ_ONLY);
    }

    public List<LotTimeTypeDescription> getLotTimeTypeDescriptionsByLotTimeTypeForUpdate(LotTimeType lotTimeType) {
        return getLotTimeTypeDescriptionsByLotTimeType(lotTimeType, EntityPermission.READ_WRITE);
    }

    public String getBestLotTimeTypeDescription(LotTimeType lotTimeType, Language language) {
        String description;
        LotTimeTypeDescription lotTimeTypeDescription = getLotTimeTypeDescription(lotTimeType, language);

        if(lotTimeTypeDescription == null && !language.getIsDefault()) {
            lotTimeTypeDescription = getLotTimeTypeDescription(lotTimeType, getPartyControl().getDefaultLanguage());
        }

        if(lotTimeTypeDescription == null) {
            description = lotTimeType.getLastDetail().getLotTimeTypeName();
        } else {
            description = lotTimeTypeDescription.getDescription();
        }

        return description;
    }

    public LotTimeTypeDescriptionTransfer getLotTimeTypeDescriptionTransfer(UserVisit userVisit, LotTimeTypeDescription lotTimeTypeDescription) {
        return getInventoryTransferCaches(userVisit).getLotTimeTypeDescriptionTransferCache().getLotTimeTypeDescriptionTransfer(lotTimeTypeDescription);
    }

    public List<LotTimeTypeDescriptionTransfer> getLotTimeTypeDescriptionTransfersByLotTimeType(UserVisit userVisit, LotTimeType lotTimeType) {
        List<LotTimeTypeDescription> lotTimeTypeDescriptions = getLotTimeTypeDescriptionsByLotTimeType(lotTimeType);
        List<LotTimeTypeDescriptionTransfer> lotTimeTypeDescriptionTransfers = new ArrayList<>(lotTimeTypeDescriptions.size());
        LotTimeTypeDescriptionTransferCache lotTimeTypeDescriptionTransferCache = getInventoryTransferCaches(userVisit).getLotTimeTypeDescriptionTransferCache();

        lotTimeTypeDescriptions.stream().forEach((lotTimeTypeDescription) -> {
            lotTimeTypeDescriptionTransfers.add(lotTimeTypeDescriptionTransferCache.getLotTimeTypeDescriptionTransfer(lotTimeTypeDescription));
        });

        return lotTimeTypeDescriptionTransfers;
    }

    public void updateLotTimeTypeDescriptionFromValue(LotTimeTypeDescriptionValue lotTimeTypeDescriptionValue, BasePK updatedBy) {
        if(lotTimeTypeDescriptionValue.hasBeenModified()) {
            LotTimeTypeDescription lotTimeTypeDescription = LotTimeTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    lotTimeTypeDescriptionValue.getPrimaryKey());

            lotTimeTypeDescription.setThruTime(session.START_TIME_LONG);
            lotTimeTypeDescription.store();

            LotTimeType lotTimeType = lotTimeTypeDescription.getLotTimeType();
            Language language = lotTimeTypeDescription.getLanguage();
            String description = lotTimeTypeDescriptionValue.getDescription();

            lotTimeTypeDescription = LotTimeTypeDescriptionFactory.getInstance().create(lotTimeType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(lotTimeType.getPrimaryKey(), EventTypes.MODIFY.name(), lotTimeTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteLotTimeTypeDescription(LotTimeTypeDescription lotTimeTypeDescription, BasePK deletedBy) {
        lotTimeTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(lotTimeTypeDescription.getLotTimeTypePK(), EventTypes.MODIFY.name(), lotTimeTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteLotTimeTypeDescriptionsByLotTimeType(LotTimeType lotTimeType, BasePK deletedBy) {
        List<LotTimeTypeDescription> lotTimeTypeDescriptions = getLotTimeTypeDescriptionsByLotTimeTypeForUpdate(lotTimeType);

        lotTimeTypeDescriptions.stream().forEach((lotTimeTypeDescription) -> {
            deleteLotTimeTypeDescription(lotTimeTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Lot Times
    // --------------------------------------------------------------------------------

    public LotTime createLotTime(Lot lot, LotTimeType lotTimeType, Long time, BasePK createdBy) {
        LotTime lotTime = LotTimeFactory.getInstance().create(lot, lotTimeType, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(lot.getPrimaryKey(), EventTypes.MODIFY.name(), lotTime.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return lotTime;
    }

    public long countLotTimesByLot(Lot lot) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM lottimes " +
                "WHERE lttim_lt_lotid = ? AND lttim_thrutime = ?",
                lot, Session.MAX_TIME_LONG);
    }

    public long countLotTimesByLotTimeType(LotTimeType lotTimeType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM lottimes " +
                "WHERE lttim_lttimtyp_lottimetypeid = ? AND lttim_thrutime = ?",
                lotTimeType, Session.MAX_TIME_LONG);
    }

    private static final Map<EntityPermission, String> getLotTimeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimes " +
                "WHERE lttim_lt_lotid = ? AND lttim_lttimtyp_lottimetypeid = ? AND lttim_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimes " +
                "WHERE lttim_lt_lotid = ? AND lttim_lttimtyp_lottimetypeid = ? AND lttim_thrutime = ? " +
                "FOR UPDATE");
        getLotTimeQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotTime getLotTime(Lot lot, LotTimeType lotTimeType, EntityPermission entityPermission) {
        return LotTimeFactory.getInstance().getEntityFromQuery(entityPermission, getLotTimeQueries, lot, lotTimeType, Session.MAX_TIME);
    }

    public LotTime getLotTime(Lot lot, LotTimeType lotTimeType) {
        return getLotTime(lot, lotTimeType, EntityPermission.READ_ONLY);
    }

    public LotTime getLotTimeForUpdate(Lot lot, LotTimeType lotTimeType) {
        return getLotTime(lot, lotTimeType, EntityPermission.READ_WRITE);
    }

    public LotTimeValue getLotTimeValue(LotTime lotTime) {
        return lotTime == null? null: lotTime.getLotTimeValue().clone();
    }

    public LotTimeValue getLotTimeValueForUpdate(Lot lot, LotTimeType lotTimeType) {
        return getLotTimeValue(getLotTimeForUpdate(lot, lotTimeType));
    }

    private static final Map<EntityPermission, String> getLotTimesByLotQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimes, lottimetypes, lottimetypedetails " +
                "WHERE lttim_lt_lotid = ? AND lttim_thrutime = ? " +
                "AND lttim_lttimtyp_lottimetypeid = lttimtyp_lottimetypeid AND lttimtyp_activedetailid = lttimtypdt_lottimetypedetailid " +
                "ORDER BY lttimtypdt_sortorder, lttimtypdt_lottimetypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimes " +
                "WHERE lttim_lt_lotid = ? AND lttim_thrutime = ? " +
                "FOR UPDATE");
        getLotTimesByLotQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotTime> getLotTimesByLot(Lot lot, EntityPermission entityPermission) {
        return LotTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotTimesByLotQueries, lot, Session.MAX_TIME);
    }

    public List<LotTime> getLotTimesByLot(Lot lot) {
        return getLotTimesByLot(lot, EntityPermission.READ_ONLY);
    }

    public List<LotTime> getLotTimesByLotForUpdate(Lot lot) {
        return getLotTimesByLot(lot, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLotTimesByLotTimeTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lottimes, lots, lotdetails " +
                "WHERE lttim_lttimtyp_lottimetypeid = ? AND lttim_thrutime = ? " +
                "AND lttim_lt_lotid = lttim_lt_lotid AND lt_activedetailid = ltdt_lotdetailid " +
                "ORDER BY ltdt_lotname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lottimes " +
                "WHERE lttim_lttimtyp_lottimetypeid = ? AND lttim_thrutime = ? " +
                "FOR UPDATE");
        getLotTimesByLotTimeTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotTime> getLotTimesByLotTimeType(LotTimeType lotTimeType, EntityPermission entityPermission) {
        return LotTimeFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotTimesByLotTimeTypeQueries, lotTimeType, Session.MAX_TIME);
    }

    public List<LotTime> getLotTimesByLotTimeType(LotTimeType lotTimeType) {
        return getLotTimesByLotTimeType(lotTimeType, EntityPermission.READ_ONLY);
    }

    public List<LotTime> getLotTimesByLotTimeTypeForUpdate(LotTimeType lotTimeType) {
        return getLotTimesByLotTimeType(lotTimeType, EntityPermission.READ_WRITE);
    }

    public LotTimeTransfer getLotTimeTransfer(UserVisit userVisit, LotTime lotTime) {
        return getInventoryTransferCaches(userVisit).getLotTimeTransferCache().getLotTimeTransfer(lotTime);
    }

    public List<LotTimeTransfer> getLotTimeTransfers(UserVisit userVisit, List<LotTime> lotTimes) {
        List<LotTimeTransfer> lotTimeTransfers = new ArrayList<>(lotTimes.size());
        LotTimeTransferCache lotTimeTransferCache = getInventoryTransferCaches(userVisit).getLotTimeTransferCache();

        lotTimes.stream().forEach((lotTime) -> {
            lotTimeTransfers.add(lotTimeTransferCache.getLotTimeTransfer(lotTime));
        });

        return lotTimeTransfers;
    }

    public List<LotTimeTransfer> getLotTimeTransfersByLot(UserVisit userVisit, Lot lot) {
        return getLotTimeTransfers(userVisit, getLotTimesByLot(lot));
    }

    public List<LotTimeTransfer> getLotTimeTransfersByLotTimeType(UserVisit userVisit, LotTimeType lotTimeType) {
        return getLotTimeTransfers(userVisit, getLotTimesByLotTimeType(lotTimeType));
    }

    public void updateLotTimeFromValue(LotTimeValue lotTimeValue, BasePK updatedBy) {
        if(lotTimeValue.hasBeenModified()) {
            LotTime lotTime = LotTimeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    lotTimeValue.getPrimaryKey());

            lotTime.setThruTime(session.START_TIME_LONG);
            lotTime.store();

            LotPK lotPK = lotTime.getLotPK(); // Not updated
            LotTimeTypePK lotTimeTypePK = lotTime.getLotTimeTypePK(); // Not updated
            Long time = lotTimeValue.getTime();

            lotTime = LotTimeFactory.getInstance().create(lotPK, lotTimeTypePK, time, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(lotPK, EventTypes.MODIFY.name(), lotTime.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteLotTime(LotTime lotTime, BasePK deletedBy) {
        lotTime.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(lotTime.getLotTimeTypePK(), EventTypes.MODIFY.name(), lotTime.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteLotTimes(List<LotTime> lotTimes, BasePK deletedBy) {
        lotTimes.stream().forEach((lotTime) -> {
            deleteLotTime(lotTime, deletedBy);
        });
    }

    public void deleteLotTimesByLot(Lot lot, BasePK deletedBy) {
        deleteLotTimes(getLotTimesByLotForUpdate(lot), deletedBy);
    }

    public void deleteLotTimesByLotTimeType(LotTimeType lotTimeType, BasePK deletedBy) {
        deleteLotTimes(getLotTimesByLotTimeTypeForUpdate(lotTimeType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Lot Alias Types
    // --------------------------------------------------------------------------------

    public LotAliasType createLotAliasType(LotType lotType, String lotAliasTypeName, String validationPattern, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        LotAliasType defaultLotAliasType = getDefaultLotAliasType(lotType);
        boolean defaultFound = defaultLotAliasType != null;

        if(defaultFound && isDefault) {
            LotAliasTypeDetailValue defaultLotAliasTypeDetailValue = getDefaultLotAliasTypeDetailValueForUpdate(lotType);

            defaultLotAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateLotAliasTypeFromValue(defaultLotAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        LotAliasType lotAliasType = LotAliasTypeFactory.getInstance().create();
        LotAliasTypeDetail lotAliasTypeDetail = LotAliasTypeDetailFactory.getInstance().create(lotAliasType, lotType, lotAliasTypeName,
                validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        lotAliasType = LotAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, lotAliasType.getPrimaryKey());
        lotAliasType.setActiveDetail(lotAliasTypeDetail);
        lotAliasType.setLastDetail(lotAliasTypeDetail);
        lotAliasType.store();

        sendEventUsingNames(lotAliasType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return lotAliasType;
    }

    private static final Map<EntityPermission, String> getLotAliasTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliastypes, lotaliastypedetails " +
                "WHERE ltaltyp_activedetailid = ltaltypdt_lotaliastypedetailid AND ltaltypdt_lttyp_lottypeid = ? " +
                "AND ltaltypdt_lotaliastypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliastypes, lotaliastypedetails " +
                "WHERE ltaltyp_activedetailid = ltaltypdt_lotaliastypedetailid AND ltaltypdt_lttyp_lottypeid = ? " +
                "AND ltaltypdt_lotaliastypename = ? " +
                "FOR UPDATE");
        getLotAliasTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotAliasType getLotAliasTypeByName(LotType lotType, String lotAliasTypeName, EntityPermission entityPermission) {
        return LotAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getLotAliasTypeByNameQueries,
                lotType, lotAliasTypeName);
    }

    public LotAliasType getLotAliasTypeByName(LotType lotType, String lotAliasTypeName) {
        return getLotAliasTypeByName(lotType, lotAliasTypeName, EntityPermission.READ_ONLY);
    }

    public LotAliasType getLotAliasTypeByNameForUpdate(LotType lotType, String lotAliasTypeName) {
        return getLotAliasTypeByName(lotType, lotAliasTypeName, EntityPermission.READ_WRITE);
    }

    public LotAliasTypeDetailValue getLotAliasTypeDetailValueForUpdate(LotAliasType lotAliasType) {
        return lotAliasType == null? null: lotAliasType.getLastDetailForUpdate().getLotAliasTypeDetailValue().clone();
    }

    public LotAliasTypeDetailValue getLotAliasTypeDetailValueByNameForUpdate(LotType lotType,
            String lotAliasTypeName) {
        return getLotAliasTypeDetailValueForUpdate(getLotAliasTypeByNameForUpdate(lotType, lotAliasTypeName));
    }

    private static final Map<EntityPermission, String> getDefaultLotAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliastypes, lotaliastypedetails " +
                "WHERE ltaltyp_activedetailid = ltaltypdt_lotaliastypedetailid AND ltaltypdt_lttyp_lottypeid = ? " +
                "AND ltaltypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliastypes, lotaliastypedetails " +
                "WHERE ltaltyp_activedetailid = ltaltypdt_lotaliastypedetailid AND ltaltypdt_lttyp_lottypeid = ? " +
                "AND ltaltypdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultLotAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotAliasType getDefaultLotAliasType(LotType lotType, EntityPermission entityPermission) {
        return LotAliasTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultLotAliasTypeQueries, lotType);
    }

    public LotAliasType getDefaultLotAliasType(LotType lotType) {
        return getDefaultLotAliasType(lotType, EntityPermission.READ_ONLY);
    }

    public LotAliasType getDefaultLotAliasTypeForUpdate(LotType lotType) {
        return getDefaultLotAliasType(lotType, EntityPermission.READ_WRITE);
    }

    public LotAliasTypeDetailValue getDefaultLotAliasTypeDetailValueForUpdate(LotType lotType) {
        return getDefaultLotAliasTypeForUpdate(lotType).getLastDetailForUpdate().getLotAliasTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getLotAliasTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliastypes, lotaliastypedetails " +
                "WHERE ltaltyp_activedetailid = ltaltypdt_lotaliastypedetailid AND ltaltypdt_lttyp_lottypeid = ? " +
                "ORDER BY ltaltypdt_sortorder, ltaltypdt_lotaliastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliastypes, lotaliastypedetails " +
                "WHERE ltaltyp_activedetailid = ltaltypdt_lotaliastypedetailid AND ltaltypdt_lttyp_lottypeid = ? " +
                "FOR UPDATE");
        getLotAliasTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotAliasType> getLotAliasTypes(LotType lotType, EntityPermission entityPermission) {
        return LotAliasTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotAliasTypesQueries, lotType);
    }

    public List<LotAliasType> getLotAliasTypes(LotType lotType) {
        return getLotAliasTypes(lotType, EntityPermission.READ_ONLY);
    }

    public List<LotAliasType> getLotAliasTypesForUpdate(LotType lotType) {
        return getLotAliasTypes(lotType, EntityPermission.READ_WRITE);
    }

    public LotAliasTypeTransfer getLotAliasTypeTransfer(UserVisit userVisit, LotAliasType lotAliasType) {
        return getInventoryTransferCaches(userVisit).getLotAliasTypeTransferCache().getLotAliasTypeTransfer(lotAliasType);
    }

    public List<LotAliasTypeTransfer> getLotAliasTypeTransfers(UserVisit userVisit, LotType lotType) {
        List<LotAliasType> lotAliasTypes = getLotAliasTypes(lotType);
        List<LotAliasTypeTransfer> lotAliasTypeTransfers = new ArrayList<>(lotAliasTypes.size());
        LotAliasTypeTransferCache lotAliasTypeTransferCache = getInventoryTransferCaches(userVisit).getLotAliasTypeTransferCache();

        lotAliasTypes.stream().forEach((lotAliasType) -> {
            lotAliasTypeTransfers.add(lotAliasTypeTransferCache.getLotAliasTypeTransfer(lotAliasType));
        });

        return lotAliasTypeTransfers;
    }

    public LotAliasTypeChoicesBean getLotAliasTypeChoices(String defaultLotAliasTypeChoice, Language language,
            boolean allowNullChoice, LotType lotType) {
        List<LotAliasType> lotAliasTypes = getLotAliasTypes(lotType);
        int size = lotAliasTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultLotAliasTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(LotAliasType lotAliasType: lotAliasTypes) {
            LotAliasTypeDetail lotAliasTypeDetail = lotAliasType.getLastDetail();

            String label = getBestLotAliasTypeDescription(lotAliasType, language);
            String value = lotAliasTypeDetail.getLotAliasTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultLotAliasTypeChoice == null? false: defaultLotAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && lotAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new LotAliasTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateLotAliasTypeFromValue(LotAliasTypeDetailValue lotAliasTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(lotAliasTypeDetailValue.hasBeenModified()) {
            LotAliasType lotAliasType = LotAliasTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    lotAliasTypeDetailValue.getLotAliasTypePK());
            LotAliasTypeDetail lotAliasTypeDetail = lotAliasType.getActiveDetailForUpdate();

            lotAliasTypeDetail.setThruTime(session.START_TIME_LONG);
            lotAliasTypeDetail.store();

            LotAliasTypePK lotAliasTypePK = lotAliasTypeDetail.getLotAliasTypePK();
            LotType lotType = lotAliasTypeDetail.getLotType();
            LotTypePK lotTypePK = lotType.getPrimaryKey();
            String lotAliasTypeName = lotAliasTypeDetailValue.getLotAliasTypeName();
            String validationPattern = lotAliasTypeDetailValue.getValidationPattern();
            Boolean isDefault = lotAliasTypeDetailValue.getIsDefault();
            Integer sortOrder = lotAliasTypeDetailValue.getSortOrder();

            if(checkDefault) {
                LotAliasType defaultLotAliasType = getDefaultLotAliasType(lotType);
                boolean defaultFound = defaultLotAliasType != null && !defaultLotAliasType.equals(lotAliasType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    LotAliasTypeDetailValue defaultLotAliasTypeDetailValue = getDefaultLotAliasTypeDetailValueForUpdate(lotType);

                    defaultLotAliasTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateLotAliasTypeFromValue(defaultLotAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            lotAliasTypeDetail = LotAliasTypeDetailFactory.getInstance().create(lotAliasTypePK, lotTypePK, lotAliasTypeName,
                    validationPattern, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            lotAliasType.setActiveDetail(lotAliasTypeDetail);
            lotAliasType.setLastDetail(lotAliasTypeDetail);

            sendEventUsingNames(lotAliasTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateLotAliasTypeFromValue(LotAliasTypeDetailValue lotAliasTypeDetailValue, BasePK updatedBy) {
        updateLotAliasTypeFromValue(lotAliasTypeDetailValue, true, updatedBy);
    }

    public void deleteLotAliasType(LotAliasType lotAliasType, BasePK deletedBy) {
        deleteLotAliasesByLotAliasType(lotAliasType, deletedBy);
        deleteLotAliasTypeDescriptionsByLotAliasType(lotAliasType, deletedBy);

        LotAliasTypeDetail lotAliasTypeDetail = lotAliasType.getLastDetailForUpdate();
        lotAliasTypeDetail.setThruTime(session.START_TIME_LONG);
        lotAliasType.setActiveDetail(null);
        lotAliasType.store();

        // Check for default, and pick one if necessary
        LotType lotType = lotAliasTypeDetail.getLotType();
        LotAliasType defaultLotAliasType = getDefaultLotAliasType(lotType);
        if(defaultLotAliasType == null) {
            List<LotAliasType> lotAliasTypes = getLotAliasTypesForUpdate(lotType);

            if(!lotAliasTypes.isEmpty()) {
                Iterator<LotAliasType> iter = lotAliasTypes.iterator();
                if(iter.hasNext()) {
                    defaultLotAliasType = iter.next();
                }
                LotAliasTypeDetailValue lotAliasTypeDetailValue = defaultLotAliasType.getLastDetailForUpdate().getLotAliasTypeDetailValue().clone();

                lotAliasTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateLotAliasTypeFromValue(lotAliasTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(lotAliasType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteLotAliasTypes(List<LotAliasType> lotAliasTypes, BasePK deletedBy) {
        lotAliasTypes.stream().forEach((lotAliasType) -> {
            deleteLotAliasType(lotAliasType, deletedBy);
        });
    }

    public void deleteLotAliasTypesByLotType(LotType lotType, BasePK deletedBy) {
        deleteLotAliasTypes(getLotAliasTypesForUpdate(lotType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Lot Alias Type Descriptions
    // --------------------------------------------------------------------------------

    public LotAliasTypeDescription createLotAliasTypeDescription(LotAliasType lotAliasType, Language language, String description, BasePK createdBy) {
        LotAliasTypeDescription lotAliasTypeDescription = LotAliasTypeDescriptionFactory.getInstance().create(lotAliasType, language,
                description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(lotAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), lotAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return lotAliasTypeDescription;
    }

    private static final Map<EntityPermission, String> getLotAliasTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliastypedescriptions " +
                "WHERE ltaltypd_ltaltyp_lotaliastypeid = ? AND ltaltypd_lang_languageid = ? AND ltaltypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliastypedescriptions " +
                "WHERE ltaltypd_ltaltyp_lotaliastypeid = ? AND ltaltypd_lang_languageid = ? AND ltaltypd_thrutime = ? " +
                "FOR UPDATE");
        getLotAliasTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotAliasTypeDescription getLotAliasTypeDescription(LotAliasType lotAliasType, Language language, EntityPermission entityPermission) {
        return LotAliasTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getLotAliasTypeDescriptionQueries,
                lotAliasType, language, Session.MAX_TIME);
    }

    public LotAliasTypeDescription getLotAliasTypeDescription(LotAliasType lotAliasType, Language language) {
        return getLotAliasTypeDescription(lotAliasType, language, EntityPermission.READ_ONLY);
    }

    public LotAliasTypeDescription getLotAliasTypeDescriptionForUpdate(LotAliasType lotAliasType, Language language) {
        return getLotAliasTypeDescription(lotAliasType, language, EntityPermission.READ_WRITE);
    }

    public LotAliasTypeDescriptionValue getLotAliasTypeDescriptionValue(LotAliasTypeDescription lotAliasTypeDescription) {
        return lotAliasTypeDescription == null? null: lotAliasTypeDescription.getLotAliasTypeDescriptionValue().clone();
    }

    public LotAliasTypeDescriptionValue getLotAliasTypeDescriptionValueForUpdate(LotAliasType lotAliasType, Language language) {
        return getLotAliasTypeDescriptionValue(getLotAliasTypeDescriptionForUpdate(lotAliasType, language));
    }

    private static final Map<EntityPermission, String> getLotAliasTypeDescriptionsByLotAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliastypedescriptions, languages " +
                "WHERE ltaltypd_ltaltyp_lotaliastypeid = ? AND ltaltypd_thrutime = ? AND ltaltypd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliastypedescriptions " +
                "WHERE ltaltypd_ltaltyp_lotaliastypeid = ? AND ltaltypd_thrutime = ? " +
                "FOR UPDATE");
        getLotAliasTypeDescriptionsByLotAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotAliasTypeDescription> getLotAliasTypeDescriptionsByLotAliasType(LotAliasType lotAliasType, EntityPermission entityPermission) {
        return LotAliasTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotAliasTypeDescriptionsByLotAliasTypeQueries,
                lotAliasType, Session.MAX_TIME);
    }

    public List<LotAliasTypeDescription> getLotAliasTypeDescriptionsByLotAliasType(LotAliasType lotAliasType) {
        return getLotAliasTypeDescriptionsByLotAliasType(lotAliasType, EntityPermission.READ_ONLY);
    }

    public List<LotAliasTypeDescription> getLotAliasTypeDescriptionsByLotAliasTypeForUpdate(LotAliasType lotAliasType) {
        return getLotAliasTypeDescriptionsByLotAliasType(lotAliasType, EntityPermission.READ_WRITE);
    }

    public String getBestLotAliasTypeDescription(LotAliasType lotAliasType, Language language) {
        String description;
        LotAliasTypeDescription lotAliasTypeDescription = getLotAliasTypeDescription(lotAliasType, language);

        if(lotAliasTypeDescription == null && !language.getIsDefault()) {
            lotAliasTypeDescription = getLotAliasTypeDescription(lotAliasType, getPartyControl().getDefaultLanguage());
        }

        if(lotAliasTypeDescription == null) {
            description = lotAliasType.getLastDetail().getLotAliasTypeName();
        } else {
            description = lotAliasTypeDescription.getDescription();
        }

        return description;
    }

    public LotAliasTypeDescriptionTransfer getLotAliasTypeDescriptionTransfer(UserVisit userVisit, LotAliasTypeDescription lotAliasTypeDescription) {
        return getInventoryTransferCaches(userVisit).getLotAliasTypeDescriptionTransferCache().getLotAliasTypeDescriptionTransfer(lotAliasTypeDescription);
    }

    public List<LotAliasTypeDescriptionTransfer> getLotAliasTypeDescriptionTransfersByLotAliasType(UserVisit userVisit, LotAliasType lotAliasType) {
        List<LotAliasTypeDescription> lotAliasTypeDescriptions = getLotAliasTypeDescriptionsByLotAliasType(lotAliasType);
        List<LotAliasTypeDescriptionTransfer> lotAliasTypeDescriptionTransfers = new ArrayList<>(lotAliasTypeDescriptions.size());
        LotAliasTypeDescriptionTransferCache lotAliasTypeDescriptionTransferCache = getInventoryTransferCaches(userVisit).getLotAliasTypeDescriptionTransferCache();

        lotAliasTypeDescriptions.stream().forEach((lotAliasTypeDescription) -> {
            lotAliasTypeDescriptionTransfers.add(lotAliasTypeDescriptionTransferCache.getLotAliasTypeDescriptionTransfer(lotAliasTypeDescription));
        });

        return lotAliasTypeDescriptionTransfers;
    }

    public void updateLotAliasTypeDescriptionFromValue(LotAliasTypeDescriptionValue lotAliasTypeDescriptionValue, BasePK updatedBy) {
        if(lotAliasTypeDescriptionValue.hasBeenModified()) {
            LotAliasTypeDescription lotAliasTypeDescription = LotAliasTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     lotAliasTypeDescriptionValue.getPrimaryKey());

            lotAliasTypeDescription.setThruTime(session.START_TIME_LONG);
            lotAliasTypeDescription.store();

            LotAliasType lotAliasType = lotAliasTypeDescription.getLotAliasType();
            Language language = lotAliasTypeDescription.getLanguage();
            String description = lotAliasTypeDescriptionValue.getDescription();

            lotAliasTypeDescription = LotAliasTypeDescriptionFactory.getInstance().create(lotAliasType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(lotAliasType.getPrimaryKey(), EventTypes.MODIFY.name(), lotAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteLotAliasTypeDescription(LotAliasTypeDescription lotAliasTypeDescription, BasePK deletedBy) {
        lotAliasTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(lotAliasTypeDescription.getLotAliasTypePK(), EventTypes.MODIFY.name(), lotAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteLotAliasTypeDescriptionsByLotAliasType(LotAliasType lotAliasType, BasePK deletedBy) {
        List<LotAliasTypeDescription> lotAliasTypeDescriptions = getLotAliasTypeDescriptionsByLotAliasTypeForUpdate(lotAliasType);

        lotAliasTypeDescriptions.stream().forEach((lotAliasTypeDescription) -> {
            deleteLotAliasTypeDescription(lotAliasTypeDescription, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Lot Aliases
    // --------------------------------------------------------------------------------

    public LotAlias createLotAlias(Lot lot, LotAliasType lotAliasType, String alias, BasePK createdBy) {
        LotAlias lotAlias = LotAliasFactory.getInstance().create(lot, lotAliasType, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(lot.getPrimaryKey(), EventTypes.MODIFY.name(), lotAlias.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return lotAlias;
    }

    private static final Map<EntityPermission, String> getLotAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliases " +
                "WHERE ltal_lt_lotid = ? AND ltal_ltaltyp_lotaliastypeid = ? AND ltal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliases " +
                "WHERE ltal_lt_lotid = ? AND ltal_ltaltyp_lotaliastypeid = ? AND ltal_thrutime = ? " +
                "FOR UPDATE");
        getLotAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotAlias getLotAlias(Lot lot, LotAliasType lotAliasType, EntityPermission entityPermission) {
        return LotAliasFactory.getInstance().getEntityFromQuery(entityPermission, getLotAliasQueries,
                lot, lotAliasType, Session.MAX_TIME);
    }

    public LotAlias getLotAlias(Lot lot, LotAliasType lotAliasType) {
        return getLotAlias(lot, lotAliasType, EntityPermission.READ_ONLY);
    }

    public LotAlias getLotAliasForUpdate(Lot lot, LotAliasType lotAliasType) {
        return getLotAlias(lot, lotAliasType, EntityPermission.READ_WRITE);
    }

    public LotAliasValue getLotAliasValue(LotAlias lotAlias) {
        return lotAlias == null? null: lotAlias.getLotAliasValue().clone();
    }

    public LotAliasValue getLotAliasValueForUpdate(Lot lot, LotAliasType lotAliasType) {
        return getLotAliasValue(getLotAliasForUpdate(lot, lotAliasType));
    }

    private static final Map<EntityPermission, String> getLotAliasByAliasQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliases " +
                "WHERE ltal_ltaltyp_lotaliastypeid = ? AND ltal_alias = ? AND ltal_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliases " +
                "WHERE ltal_ltaltyp_lotaliastypeid = ? AND ltal_alias = ? AND ltal_thrutime = ? " +
                "FOR UPDATE");
        getLotAliasByAliasQueries = Collections.unmodifiableMap(queryMap);
    }

    private LotAlias getLotAliasByAlias(LotAliasType lotAliasType, String alias, EntityPermission entityPermission) {
        return LotAliasFactory.getInstance().getEntityFromQuery(entityPermission, getLotAliasByAliasQueries, lotAliasType, alias, Session.MAX_TIME);
    }

    public LotAlias getLotAliasByAlias(LotAliasType lotAliasType, String alias) {
        return getLotAliasByAlias(lotAliasType, alias, EntityPermission.READ_ONLY);
    }

    public LotAlias getLotAliasByAliasForUpdate(LotAliasType lotAliasType, String alias) {
        return getLotAliasByAlias(lotAliasType, alias, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLotAliasesByLotQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliases, lotaliastypes, lotaliastypedetails " +
                "WHERE ltal_lt_lotid = ? AND ltal_thrutime = ? " +
                "AND ltal_ltaltyp_lotaliastypeid = ltaltyp_lotaliastypeid AND ltaltyp_lastdetailid = ltaltypdt_lotaliastypedetailid" +
                "ORDER BY ltaltypdt_sortorder, ltaltypdt_lotaliastypename");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliases " +
                "WHERE ltal_lt_lotid = ? AND ltal_thrutime = ? " +
                "FOR UPDATE");
        getLotAliasesByLotQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotAlias> getLotAliasesByLot(Lot lot, EntityPermission entityPermission) {
        return LotAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotAliasesByLotQueries,
                lot, Session.MAX_TIME);
    }

    public List<LotAlias> getLotAliasesByLot(Lot lot) {
        return getLotAliasesByLot(lot, EntityPermission.READ_ONLY);
    }

    public List<LotAlias> getLotAliasesByLotForUpdate(Lot lot) {
        return getLotAliasesByLot(lot, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getLotAliasesByLotAliasTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM lotaliases, lotes, lotdetails " +
                "WHERE ltal_ltaltyp_lotaliastypeid = ? AND ltal_thrutime = ? " +
                "AND ltal_lt_lotid = lt_lotid AND lt_lastdetailid = ltdt_lotdetailid " +
                "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM lotaliases " +
                "WHERE ltal_ltaltyp_lotaliastypeid = ? AND ltal_thrutime = ? " +
                "FOR UPDATE");
        getLotAliasesByLotAliasTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<LotAlias> getLotAliasesByLotAliasType(LotAliasType lotAliasType, EntityPermission entityPermission) {
        return LotAliasFactory.getInstance().getEntitiesFromQuery(entityPermission, getLotAliasesByLotAliasTypeQueries,
                lotAliasType, Session.MAX_TIME);
    }

    public List<LotAlias> getLotAliasesByLotAliasType(LotAliasType lotAliasType) {
        return getLotAliasesByLotAliasType(lotAliasType, EntityPermission.READ_ONLY);
    }

    public List<LotAlias> getLotAliasesByLotAliasTypeForUpdate(LotAliasType lotAliasType) {
        return getLotAliasesByLotAliasType(lotAliasType, EntityPermission.READ_WRITE);
    }

    public LotAliasTransfer getLotAliasTransfer(UserVisit userVisit, LotAlias lotAlias) {
        return getInventoryTransferCaches(userVisit).getLotAliasTransferCache().getLotAliasTransfer(lotAlias);
    }

    public List<LotAliasTransfer> getLotAliasTransfersByLot(UserVisit userVisit, Lot lot) {
        List<LotAlias> lotaliases = getLotAliasesByLot(lot);
        List<LotAliasTransfer> lotAliasTransfers = new ArrayList<>(lotaliases.size());
        LotAliasTransferCache lotAliasTransferCache = getInventoryTransferCaches(userVisit).getLotAliasTransferCache();

        lotaliases.stream().forEach((lotAlias) -> {
            lotAliasTransfers.add(lotAliasTransferCache.getLotAliasTransfer(lotAlias));
        });

        return lotAliasTransfers;
    }

    public void updateLotAliasFromValue(LotAliasValue lotAliasValue, BasePK updatedBy) {
        if(lotAliasValue.hasBeenModified()) {
            LotAlias lotAlias = LotAliasFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, lotAliasValue.getPrimaryKey());

            lotAlias.setThruTime(session.START_TIME_LONG);
            lotAlias.store();

            LotPK lotPK = lotAlias.getLotPK();
            LotAliasTypePK lotAliasTypePK = lotAlias.getLotAliasTypePK();
            String alias  = lotAliasValue.getAlias();

            lotAlias = LotAliasFactory.getInstance().create(lotPK, lotAliasTypePK, alias, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(lotPK, EventTypes.MODIFY.name(), lotAlias.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteLotAlias(LotAlias lotAlias, BasePK deletedBy) {
        lotAlias.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(lotAlias.getLotPK(), EventTypes.MODIFY.name(), lotAlias.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteLotAliasesByLotAliasType(LotAliasType lotAliasType, BasePK deletedBy) {
        List<LotAlias> lotaliases = getLotAliasesByLotAliasTypeForUpdate(lotAliasType);

        lotaliases.stream().forEach((lotAlias) -> {
            deleteLotAlias(lotAlias, deletedBy);
        });
    }

    public void deleteLotAliasesByLot(Lot lot, BasePK deletedBy) {
        List<LotAlias> lotaliases = getLotAliasesByLotForUpdate(lot);

        lotaliases.stream().forEach((lotAlias) -> {
            deleteLotAlias(lotAlias, deletedBy);
        });
    }

    // --------------------------------------------------------------------------------
    //   Lots
    // --------------------------------------------------------------------------------

    public Lot getLotByName(LotType lotType, String lotName) {
        // TODO
        return null;
    }

    public LotTransfer getLotTransfer(UserVisit userVisit, Lot lot) {
        // TODO
        return null;
    }

    // --------------------------------------------------------------------------------
    //   Party Inventory Levels
    // --------------------------------------------------------------------------------
    
    public PartyInventoryLevel createPartyInventoryLevel(Party party, Item item, InventoryCondition inventoryCondition,
            Long minimumInventory, Long maximumInventory, Long reorderQuantity, BasePK createdBy) {
        PartyInventoryLevel partyInventoryLevel = PartyInventoryLevelFactory.getInstance().create(party, item,
                inventoryCondition, minimumInventory, maximumInventory, reorderQuantity, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(party.getPrimaryKey(), EventTypes.MODIFY.name(), partyInventoryLevel.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return partyInventoryLevel;
    }
    
    private PartyInventoryLevel getPartyInventoryLevel(Party party, Item item, InventoryCondition inventoryCondition,
            EntityPermission entityPermission) {
        PartyInventoryLevel partyInventoryLevel = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyinventorylevels " +
                        "WHERE parinvlvl_par_partyid = ? AND parinvlvl_itm_itemid = ? AND parinvlvl_invcon_inventoryconditionid = ? AND parinvlvl_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyinventorylevels " +
                        "WHERE parinvlvl_par_partyid = ? AND parinvlvl_itm_itemid = ? AND parinvlvl_invcon_inventoryconditionid = ? AND parinvlvl_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyInventoryLevelFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, item.getPrimaryKey().getEntityId());
            ps.setLong(3, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            partyInventoryLevel = PartyInventoryLevelFactory.getInstance().getEntityFromQuery(session,
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
        List<PartyInventoryLevel> partyInventoryLevels = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyinventorylevels, items, itemdetails, inventoryconditions, inventoryconditiondetails " +
                        "WHERE parinvlvl_par_partyid = ? AND parinvlvl_thrutime = ? " +
                        "AND parinvlvl_itm_itemid = itm_itemid AND itm_activedetailid = itmdt_itemdetailid " +
                        "AND parinvlvl_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "ORDER BY itmdt_itemname, invcondt_sortorder, invcondt_inventoryconditionname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyinventorylevels " +
                        "WHERE parinvlvl_par_partyid = ? AND parinvlvl_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyInventoryLevelFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyInventoryLevels = PartyInventoryLevelFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        List<PartyInventoryLevel> partyInventoryLevels = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyinventorylevels, parties, partydetails, partytypes, inventoryconditions, inventoryconditiondetails " +
                        "WHERE parinvlvl_itm_itemid = ? AND parinvlvl_thrutime = ? " +
                        "AND parinvlvl_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                        "AND pardt_ptyp_partytypeid = ptyp_partytypeid " +
                        "AND parinvlvl_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "ORDER BY ptyp_sortorder, ptyp_partytypename, pardt_partyname, invcondt_sortorder, invcondt_inventoryconditionname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyinventorylevels " +
                        "WHERE parinvlvl_itm_itemid = ? AND parinvlvl_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyInventoryLevelFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, item.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyInventoryLevels = PartyInventoryLevelFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        List<PartyInventoryLevel> partyInventoryLevels = null;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyinventorylevels, parties, partydetails, partytypes, item, itemdetails " +
                        "WHERE parinvlvl_invcon_inventoryconditionid = ? AND parinvlvl_thrutime = ? " +
                        "AND parinvlvl_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                        "AND pardt_ptyp_partytypeid = ptyp_partytypeid " +
                        "AND parinvlvl_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "ORDER BY ptyp_sortorder, ptyp_partytypename, pardt_partyname, itmdt_itemname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyinventorylevels " +
                        "WHERE parinvlvl_invcon_inventoryconditionid = ? AND parinvlvl_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = PartyInventoryLevelFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryCondition.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            partyInventoryLevels = PartyInventoryLevelFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
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
        return getInventoryTransferCaches(userVisit).getPartyInventoryLevelTransferCache().getPartyInventoryLevelTransfer(partyInventoryLevel);
    }
    
    public List<PartyInventoryLevelTransfer> getPartyInventoryLevelTransfers(UserVisit userVisit, List<PartyInventoryLevel> partyInventoryLevels) {
        List<PartyInventoryLevelTransfer> partyInventoryLevelTransfers = new ArrayList<>(partyInventoryLevels.size());
        PartyInventoryLevelTransferCache partyInventoryLevelTransferCache = getInventoryTransferCaches(userVisit).getPartyInventoryLevelTransferCache();
        
        partyInventoryLevels.stream().forEach((partyInventoryLevel) -> {
            partyInventoryLevelTransfers.add(partyInventoryLevelTransferCache.getPartyInventoryLevelTransfer(partyInventoryLevel));
        });
        
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
            PartyInventoryLevel partyInventoryLevel = PartyInventoryLevelFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyInventoryLevelValue.getPrimaryKey());
            
            partyInventoryLevel.setThruTime(session.START_TIME_LONG);
            partyInventoryLevel.store();
            
            PartyPK partyPK = partyInventoryLevel.getPartyPK(); // Not updated
            ItemPK itemPK = partyInventoryLevel.getItemPK(); // Not updated
            InventoryConditionPK inventoryConditionPK = partyInventoryLevel.getInventoryConditionPK(); // Not updated
            Long minimumInventory = partyInventoryLevelValue.getMinimumInventory();
            Long maximumInventory = partyInventoryLevelValue.getMaximumInventory();
            Long reorderQuantity = partyInventoryLevelValue.getReorderQuantity();
            
            partyInventoryLevel = PartyInventoryLevelFactory.getInstance().create(partyPK, itemPK, inventoryConditionPK,
                    minimumInventory, maximumInventory, reorderQuantity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(partyPK, EventTypes.MODIFY.name(), partyInventoryLevel.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deletePartyInventoryLevel(PartyInventoryLevel partyInventoryLevel, BasePK deletedBy) {
        partyInventoryLevel.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(partyInventoryLevel.getPartyPK(), EventTypes.MODIFY.name(), partyInventoryLevel.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deletePartyInventoryLevels(List<PartyInventoryLevel> partyInventoryLevels, BasePK deletedBy) {
        partyInventoryLevels.stream().forEach((partyInventoryLevel) -> {
            deletePartyInventoryLevel(partyInventoryLevel, deletedBy);
        });
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
    
    // --------------------------------------------------------------------------------
    //   Allocation Priorities
    // --------------------------------------------------------------------------------

    public AllocationPriority createAllocationPriority(String allocationPriorityName, Integer priority, Boolean isDefault, Integer sortPriority,
            BasePK createdBy) {
        AllocationPriority defaultAllocationPriority = getDefaultAllocationPriority();
        boolean defaultFound = defaultAllocationPriority != null;

        if(defaultFound && isDefault) {
            AllocationPriorityDetailValue defaultAllocationPriorityDetailValue = getDefaultAllocationPriorityDetailValueForUpdate();

            defaultAllocationPriorityDetailValue.setIsDefault(Boolean.FALSE);
            updateAllocationPriorityFromValue(defaultAllocationPriorityDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        AllocationPriority allocationPriority = AllocationPriorityFactory.getInstance().create();
        AllocationPriorityDetail allocationPriorityDetail = AllocationPriorityDetailFactory.getInstance().create(allocationPriority, allocationPriorityName,
                priority, isDefault, sortPriority, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        allocationPriority = AllocationPriorityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                allocationPriority.getPrimaryKey());
        allocationPriority.setActiveDetail(allocationPriorityDetail);
        allocationPriority.setLastDetail(allocationPriorityDetail);
        allocationPriority.store();

        sendEventUsingNames(allocationPriority.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return allocationPriority;
    }

    private static final Map<EntityPermission, String> getAllocationPriorityByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM allocationpriorities, allocationprioritydetails " +
                "WHERE allocpr_activedetailid = allocprdt_allocationprioritydetailid " +
                "AND allocprdt_allocationpriorityname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM allocationpriorities, allocationprioritydetails " +
                "WHERE allocpr_activedetailid = allocprdt_allocationprioritydetailid " +
                "AND allocprdt_allocationpriorityname = ? " +
                "FOR UPDATE");
        getAllocationPriorityByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    private AllocationPriority getAllocationPriorityByName(String allocationPriorityName, EntityPermission entityPermission) {
        return AllocationPriorityFactory.getInstance().getEntityFromQuery(entityPermission, getAllocationPriorityByNameQueries,
                allocationPriorityName);
    }

    public AllocationPriority getAllocationPriorityByName(String allocationPriorityName) {
        return getAllocationPriorityByName(allocationPriorityName, EntityPermission.READ_ONLY);
    }

    public AllocationPriority getAllocationPriorityByNameForUpdate(String allocationPriorityName) {
        return getAllocationPriorityByName(allocationPriorityName, EntityPermission.READ_WRITE);
    }

    public AllocationPriorityDetailValue getAllocationPriorityDetailValueForUpdate(AllocationPriority allocationPriority) {
        return allocationPriority == null? null: allocationPriority.getLastDetailForUpdate().getAllocationPriorityDetailValue().clone();
    }

    public AllocationPriorityDetailValue getAllocationPriorityDetailValueByNameForUpdate(String allocationPriorityName) {
        return getAllocationPriorityDetailValueForUpdate(getAllocationPriorityByNameForUpdate(allocationPriorityName));
    }

    private static final Map<EntityPermission, String> getDefaultAllocationPriorityQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM allocationpriorities, allocationprioritydetails " +
                "WHERE allocpr_activedetailid = allocprdt_allocationprioritydetailid " +
                "AND allocprdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM allocationpriorities, allocationprioritydetails " +
                "WHERE allocpr_activedetailid = allocprdt_allocationprioritydetailid " +
                "AND allocprdt_isdefault = 1 " +
                "FOR UPDATE");
        getDefaultAllocationPriorityQueries = Collections.unmodifiableMap(queryMap);
    }

    private AllocationPriority getDefaultAllocationPriority(EntityPermission entityPermission) {
        return AllocationPriorityFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultAllocationPriorityQueries);
    }

    public AllocationPriority getDefaultAllocationPriority() {
        return getDefaultAllocationPriority(EntityPermission.READ_ONLY);
    }

    public AllocationPriority getDefaultAllocationPriorityForUpdate() {
        return getDefaultAllocationPriority(EntityPermission.READ_WRITE);
    }

    public AllocationPriorityDetailValue getDefaultAllocationPriorityDetailValueForUpdate() {
        return getDefaultAllocationPriorityForUpdate().getLastDetailForUpdate().getAllocationPriorityDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getAllocationPrioritiesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM allocationpriorities, allocationprioritydetails " +
                "WHERE allocpr_activedetailid = allocprdt_allocationprioritydetailid " +
                "ORDER BY allocprdt_sortorder, allocprdt_allocationpriorityname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM allocationpriorities, allocationprioritydetails " +
                "WHERE allocpr_activedetailid = allocprdt_allocationprioritydetailid " +
                "FOR UPDATE");
        getAllocationPrioritiesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<AllocationPriority> getAllocationPriorities(EntityPermission entityPermission) {
        return AllocationPriorityFactory.getInstance().getEntitiesFromQuery(entityPermission, getAllocationPrioritiesQueries);
    }

    public List<AllocationPriority> getAllocationPriorities() {
        return getAllocationPriorities(EntityPermission.READ_ONLY);
    }

    public List<AllocationPriority> getAllocationPrioritiesForUpdate() {
        return getAllocationPriorities(EntityPermission.READ_WRITE);
    }

    public AllocationPriorityTransfer getAllocationPriorityTransfer(UserVisit userVisit, AllocationPriority allocationPriority) {
        return getInventoryTransferCaches(userVisit).getAllocationPriorityTransferCache().getAllocationPriorityTransfer(allocationPriority);
    }

    public List<AllocationPriorityTransfer> getAllocationPriorityTransfers(UserVisit userVisit) {
        List<AllocationPriority> allocationPriorities = getAllocationPriorities();
        List<AllocationPriorityTransfer> allocationPriorityTransfers = new ArrayList<>(allocationPriorities.size());
        AllocationPriorityTransferCache allocationPriorityTransferCache = getInventoryTransferCaches(userVisit).getAllocationPriorityTransferCache();

        allocationPriorities.stream().forEach((allocationPriority) -> {
            allocationPriorityTransfers.add(allocationPriorityTransferCache.getAllocationPriorityTransfer(allocationPriority));
        });

        return allocationPriorityTransfers;
    }

    public AllocationPriorityChoicesBean getAllocationPriorityChoices(String defaultAllocationPriorityChoice, Language language, boolean allowNullChoice) {
        List<AllocationPriority> allocationPriorities = getAllocationPriorities();
        int size = allocationPriorities.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultAllocationPriorityChoice == null) {
                defaultValue = "";
            }
        }

        for(AllocationPriority allocationPriority: allocationPriorities) {
            AllocationPriorityDetail allocationPriorityDetail = allocationPriority.getLastDetail();

            String label = getBestAllocationPriorityDescription(allocationPriority, language);
            String value = allocationPriorityDetail.getAllocationPriorityName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultAllocationPriorityChoice == null? false: defaultAllocationPriorityChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && allocationPriorityDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new AllocationPriorityChoicesBean(labels, values, defaultValue);
    }

    private void updateAllocationPriorityFromValue(AllocationPriorityDetailValue allocationPriorityDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(allocationPriorityDetailValue.hasBeenModified()) {
            AllocationPriority allocationPriority = AllocationPriorityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     allocationPriorityDetailValue.getAllocationPriorityPK());
            AllocationPriorityDetail allocationPriorityDetail = allocationPriority.getActiveDetailForUpdate();

            allocationPriorityDetail.setThruTime(session.START_TIME_LONG);
            allocationPriorityDetail.store();

            AllocationPriorityPK allocationPriorityPK = allocationPriorityDetail.getAllocationPriorityPK(); // Not updated
            String allocationPriorityName = allocationPriorityDetailValue.getAllocationPriorityName();
            Integer priority = allocationPriorityDetailValue.getPriority();
            Boolean isDefault = allocationPriorityDetailValue.getIsDefault();
            Integer sortOrder = allocationPriorityDetailValue.getSortOrder();

            if(checkDefault) {
                AllocationPriority defaultAllocationPriority = getDefaultAllocationPriority();
                boolean defaultFound = defaultAllocationPriority != null && !defaultAllocationPriority.equals(allocationPriority);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    AllocationPriorityDetailValue defaultAllocationPriorityDetailValue = getDefaultAllocationPriorityDetailValueForUpdate();

                    defaultAllocationPriorityDetailValue.setIsDefault(Boolean.FALSE);
                    updateAllocationPriorityFromValue(defaultAllocationPriorityDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            allocationPriorityDetail = AllocationPriorityDetailFactory.getInstance().create(allocationPriorityPK, allocationPriorityName, priority, isDefault,
                    sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            allocationPriority.setActiveDetail(allocationPriorityDetail);
            allocationPriority.setLastDetail(allocationPriorityDetail);

            sendEventUsingNames(allocationPriorityPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateAllocationPriorityFromValue(AllocationPriorityDetailValue allocationPriorityDetailValue, BasePK updatedBy) {
        updateAllocationPriorityFromValue(allocationPriorityDetailValue, true, updatedBy);
    }

    public void deleteAllocationPriority(AllocationPriority allocationPriority, BasePK deletedBy) {
        deleteAllocationPriorityDescriptionsByAllocationPriority(allocationPriority, deletedBy);

        AllocationPriorityDetail allocationPriorityDetail = allocationPriority.getLastDetailForUpdate();
        allocationPriorityDetail.setThruTime(session.START_TIME_LONG);
        allocationPriority.setActiveDetail(null);
        allocationPriority.store();

        // Check for default, and pick one if necessary
        AllocationPriority defaultAllocationPriority = getDefaultAllocationPriority();
        if(defaultAllocationPriority == null) {
            List<AllocationPriority> allocationPriorities = getAllocationPrioritiesForUpdate();

            if(!allocationPriorities.isEmpty()) {
                Iterator<AllocationPriority> iter = allocationPriorities.iterator();
                if(iter.hasNext()) {
                    defaultAllocationPriority = iter.next();
                }
                AllocationPriorityDetailValue allocationPriorityDetailValue = defaultAllocationPriority.getLastDetailForUpdate().getAllocationPriorityDetailValue().clone();

                allocationPriorityDetailValue.setIsDefault(Boolean.TRUE);
                updateAllocationPriorityFromValue(allocationPriorityDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(allocationPriority.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Allocation Priority Descriptions
    // --------------------------------------------------------------------------------

    public AllocationPriorityDescription createAllocationPriorityDescription(AllocationPriority allocationPriority, Language language, String description, BasePK createdBy) {
        AllocationPriorityDescription allocationPriorityDescription = AllocationPriorityDescriptionFactory.getInstance().create(allocationPriority, language, description,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(allocationPriority.getPrimaryKey(), EventTypes.MODIFY.name(), allocationPriorityDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

        return allocationPriorityDescription;
    }

    private static final Map<EntityPermission, String> getAllocationPriorityDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM allocationprioritydescriptions " +
                "WHERE allocprd_allocpr_allocationpriorityid = ? AND allocprd_lang_languageid = ? AND allocprd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM allocationprioritydescriptions " +
                "WHERE allocprd_allocpr_allocationpriorityid = ? AND allocprd_lang_languageid = ? AND allocprd_thrutime = ? " +
                "FOR UPDATE");
        getAllocationPriorityDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private AllocationPriorityDescription getAllocationPriorityDescription(AllocationPriority allocationPriority, Language language, EntityPermission entityPermission) {
        return AllocationPriorityDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getAllocationPriorityDescriptionQueries,
                allocationPriority, language, Session.MAX_TIME);
    }

    public AllocationPriorityDescription getAllocationPriorityDescription(AllocationPriority allocationPriority, Language language) {
        return getAllocationPriorityDescription(allocationPriority, language, EntityPermission.READ_ONLY);
    }

    public AllocationPriorityDescription getAllocationPriorityDescriptionForUpdate(AllocationPriority allocationPriority, Language language) {
        return getAllocationPriorityDescription(allocationPriority, language, EntityPermission.READ_WRITE);
    }

    public AllocationPriorityDescriptionValue getAllocationPriorityDescriptionValue(AllocationPriorityDescription allocationPriorityDescription) {
        return allocationPriorityDescription == null? null: allocationPriorityDescription.getAllocationPriorityDescriptionValue().clone();
    }

    public AllocationPriorityDescriptionValue getAllocationPriorityDescriptionValueForUpdate(AllocationPriority allocationPriority, Language language) {
        return getAllocationPriorityDescriptionValue(getAllocationPriorityDescriptionForUpdate(allocationPriority, language));
    }

    private static final Map<EntityPermission, String> getAllocationPriorityDescriptionsByAllocationPriorityQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ " +
                "FROM allocationprioritydescriptions, languages " +
                "WHERE allocprd_allocpr_allocationpriorityid = ? AND allocprd_thrutime = ? AND allocprd_lang_languageid = lang_languageid " +
                "ORDER BY lang_sortallocation, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ " +
                "FROM allocationprioritydescriptions " +
                "WHERE allocprd_allocpr_allocationpriorityid = ? AND allocprd_thrutime = ? " +
                "FOR UPDATE");
        getAllocationPriorityDescriptionsByAllocationPriorityQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<AllocationPriorityDescription> getAllocationPriorityDescriptionsByAllocationPriority(AllocationPriority allocationPriority, EntityPermission entityPermission) {
        return AllocationPriorityDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getAllocationPriorityDescriptionsByAllocationPriorityQueries,
                allocationPriority, Session.MAX_TIME);
    }

    public List<AllocationPriorityDescription> getAllocationPriorityDescriptionsByAllocationPriority(AllocationPriority allocationPriority) {
        return getAllocationPriorityDescriptionsByAllocationPriority(allocationPriority, EntityPermission.READ_ONLY);
    }

    public List<AllocationPriorityDescription> getAllocationPriorityDescriptionsByAllocationPriorityForUpdate(AllocationPriority allocationPriority) {
        return getAllocationPriorityDescriptionsByAllocationPriority(allocationPriority, EntityPermission.READ_WRITE);
    }

    public String getBestAllocationPriorityDescription(AllocationPriority allocationPriority, Language language) {
        String description;
        AllocationPriorityDescription allocationPriorityDescription = getAllocationPriorityDescription(allocationPriority, language);

        if(allocationPriorityDescription == null && !language.getIsDefault()) {
            allocationPriorityDescription = getAllocationPriorityDescription(allocationPriority, getPartyControl().getDefaultLanguage());
        }

        if(allocationPriorityDescription == null) {
            description = allocationPriority.getLastDetail().getAllocationPriorityName();
        } else {
            description = allocationPriorityDescription.getDescription();
        }

        return description;
    }

    public AllocationPriorityDescriptionTransfer getAllocationPriorityDescriptionTransfer(UserVisit userVisit, AllocationPriorityDescription allocationPriorityDescription) {
        return getInventoryTransferCaches(userVisit).getAllocationPriorityDescriptionTransferCache().getAllocationPriorityDescriptionTransfer(allocationPriorityDescription);
    }

    public List<AllocationPriorityDescriptionTransfer> getAllocationPriorityDescriptionTransfersByAllocationPriority(UserVisit userVisit, AllocationPriority allocationPriority) {
        List<AllocationPriorityDescription> allocationPriorityDescriptions = getAllocationPriorityDescriptionsByAllocationPriority(allocationPriority);
        List<AllocationPriorityDescriptionTransfer> allocationPriorityDescriptionTransfers = new ArrayList<>(allocationPriorityDescriptions.size());
        AllocationPriorityDescriptionTransferCache allocationPriorityDescriptionTransferCache = getInventoryTransferCaches(userVisit).getAllocationPriorityDescriptionTransferCache();

        allocationPriorityDescriptions.stream().forEach((allocationPriorityDescription) -> {
            allocationPriorityDescriptionTransfers.add(allocationPriorityDescriptionTransferCache.getAllocationPriorityDescriptionTransfer(allocationPriorityDescription));
        });

        return allocationPriorityDescriptionTransfers;
    }

    public void updateAllocationPriorityDescriptionFromValue(AllocationPriorityDescriptionValue allocationPriorityDescriptionValue, BasePK updatedBy) {
        if(allocationPriorityDescriptionValue.hasBeenModified()) {
            AllocationPriorityDescription allocationPriorityDescription = AllocationPriorityDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    allocationPriorityDescriptionValue.getPrimaryKey());

            allocationPriorityDescription.setThruTime(session.START_TIME_LONG);
            allocationPriorityDescription.store();

            AllocationPriority allocationPriority = allocationPriorityDescription.getAllocationPriority();
            Language language = allocationPriorityDescription.getLanguage();
            String description = allocationPriorityDescriptionValue.getDescription();

            allocationPriorityDescription = AllocationPriorityDescriptionFactory.getInstance().create(allocationPriority, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(allocationPriority.getPrimaryKey(), EventTypes.MODIFY.name(), allocationPriorityDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteAllocationPriorityDescription(AllocationPriorityDescription allocationPriorityDescription, BasePK deletedBy) {
        allocationPriorityDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(allocationPriorityDescription.getAllocationPriorityPK(), EventTypes.MODIFY.name(), allocationPriorityDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteAllocationPriorityDescriptionsByAllocationPriority(AllocationPriority allocationPriority, BasePK deletedBy) {
        List<AllocationPriorityDescription> allocationPriorityDescriptions = getAllocationPriorityDescriptionsByAllocationPriorityForUpdate(allocationPriority);

        allocationPriorityDescriptions.stream().forEach((allocationPriorityDescription) -> {
            deleteAllocationPriorityDescription(allocationPriorityDescription, deletedBy);
        });
    }

}
