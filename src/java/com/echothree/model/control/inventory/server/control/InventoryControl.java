// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.model.control.inventory.common.choice.AllocationPriorityChoicesBean;
import com.echothree.model.control.inventory.common.choice.InventoryConditionChoicesBean;
import com.echothree.model.control.inventory.common.choice.InventoryConditionUseTypeChoicesBean;
import com.echothree.model.control.inventory.common.choice.InventoryLocationGroupChoicesBean;
import com.echothree.model.control.inventory.common.choice.InventoryLocationGroupStatusChoicesBean;
import com.echothree.model.control.inventory.common.transfer.AllocationPriorityDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.AllocationPriorityTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionGlAccountTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionUseTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionUseTypeTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupCapacityTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupVolumeTransfer;
import com.echothree.model.control.inventory.common.transfer.PartyInventoryLevelTransfer;
import com.echothree.model.control.inventory.common.workflow.InventoryLocationGroupStatusConstants;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.ItemAccountingCategory;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.AllocationPriorityPK;
import com.echothree.model.data.inventory.common.pk.InventoryConditionPK;
import com.echothree.model.data.inventory.server.entity.AllocationPriority;
import com.echothree.model.data.inventory.server.entity.AllocationPriorityDescription;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.InventoryConditionDescription;
import com.echothree.model.data.inventory.server.entity.InventoryConditionGlAccount;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUse;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUseType;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUseTypeDescription;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupCapacity;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupDescription;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupVolume;
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
import com.echothree.model.data.inventory.server.value.PartyInventoryLevelValue;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class InventoryControl
        extends BaseInventoryControl {
    
    /** Creates a new instance of InventoryControl */
    protected InventoryControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Groups
    // --------------------------------------------------------------------------------
    
    public InventoryLocationGroup createInventoryLocationGroup(Party warehouseParty, String inventoryLocationGroupName,
            Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultInventoryLocationGroup = getDefaultInventoryLocationGroup(warehouseParty);
        var defaultFound = defaultInventoryLocationGroup != null;
        
        if(defaultFound && isDefault) {
            var defaultInventoryLocationGroupDetailValue = getDefaultInventoryLocationGroupDetailValueForUpdate(warehouseParty);
            
            defaultInventoryLocationGroupDetailValue.setIsDefault(false);
            updateInventoryLocationGroupFromValue(defaultInventoryLocationGroupDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryLocationGroup = InventoryLocationGroupFactory.getInstance().create();
        var inventoryLocationGroupDetail = InventoryLocationGroupDetailFactory.getInstance().create(session,
                inventoryLocationGroup, warehouseParty, inventoryLocationGroupName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        inventoryLocationGroup = InventoryLocationGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, inventoryLocationGroup.getPrimaryKey());
        inventoryLocationGroup.setActiveDetail(inventoryLocationGroupDetail);
        inventoryLocationGroup.setLastDetail(inventoryLocationGroupDetail);
        inventoryLocationGroup.store();
        
        sendEvent(inventoryLocationGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return inventoryLocationGroup;
    }
    
    private InventoryLocationGroup getInventoryLocationGroupByName(Party warehouseParty, String inventoryLocationGroupName, EntityPermission entityPermission) {
        InventoryLocationGroup inventoryLocationGroup;
        
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

            var ps = InventoryLocationGroupFactory.getInstance().prepareStatement(query);
            
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
        InventoryLocationGroup inventoryLocationGroup;
        
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

            var ps = InventoryLocationGroupFactory.getInstance().prepareStatement(query);
            
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
        List<InventoryLocationGroup> inventoryLocationGroups;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroups, inventorylocationgroupdetails " +
                        "WHERE invlocgrp_inventorylocationgroupid = invlocgrpdt_invlocgrp_inventorylocationgroupid " +
                        "AND invlocgrpdt_warehousepartyid = ? AND invlocgrpdt_thrutime = ? " +
                        "ORDER BY invlocgrpdt_sortorder, invlocgrpdt_inventorylocationgroupname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroups, inventorylocationgroupdetails " +
                        "WHERE invlocgrp_inventorylocationgroupid = invlocgrpdt_invlocgrp_inventorylocationgroupid " +
                        "AND invlocgrpdt_warehousepartyid = ? AND invlocgrpdt_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = InventoryLocationGroupFactory.getInstance().prepareStatement(query);
            
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
        return inventoryLocationGroupTransferCache.getTransfer(userVisit, inventoryLocationGroup);
    }
    
    public List<InventoryLocationGroupTransfer> getInventoryLocationGroupTransfersByWarehouseParty(UserVisit userVisit, Party warehouseParty) {
        var inventoryLocationGroups = getInventoryLocationGroupsByWarehouseParty(warehouseParty);
        List<InventoryLocationGroupTransfer> inventoryLocationGroupTransfers = null;
        
        if(inventoryLocationGroups != null) {
            inventoryLocationGroupTransfers = new ArrayList<>(inventoryLocationGroups.size());
            
            for(var inventoryLocationGroup : inventoryLocationGroups) {
                inventoryLocationGroupTransfers.add(inventoryLocationGroupTransferCache.getTransfer(userVisit, inventoryLocationGroup));
            }
        }
        
        return inventoryLocationGroupTransfers;
    }
    
    public InventoryLocationGroupChoicesBean getInventoryLocationGroupChoicesByWarehouseParty(String defaultInventoryLocationGroupChoice,
            Language language, boolean allowNullChoice, Party warehouseParty) {
        var inventoryLocationGroups = getInventoryLocationGroupsByWarehouseParty(warehouseParty);
        var size = inventoryLocationGroups.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
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
            var inventoryLocationGroup = (InventoryLocationGroup)iter.next();
            var inventoryLocationGroupDetail = inventoryLocationGroup.getLastDetail();
            
            var label = getBestInventoryLocationGroupDescription(inventoryLocationGroup, language);
            var value = inventoryLocationGroupDetail.getInventoryLocationGroupName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultInventoryLocationGroupChoice != null && defaultInventoryLocationGroupChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && inventoryLocationGroupDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new InventoryLocationGroupChoicesBean(labels, values, defaultValue);
    }
    
    private void updateInventoryLocationGroupFromValue(InventoryLocationGroupDetailValue inventoryLocationGroupDetailValue,
            boolean checkDefault, BasePK updatedBy) {
        var inventoryLocationGroup = InventoryLocationGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, inventoryLocationGroupDetailValue.getInventoryLocationGroupPK());
        var inventoryLocationGroupDetail = inventoryLocationGroup.getActiveDetailForUpdate();
        
        inventoryLocationGroupDetail.setThruTime(session.getStartTime());
        inventoryLocationGroupDetail.store();

        var inventoryLocationGroupPK = inventoryLocationGroupDetail.getInventoryLocationGroupPK();
        var warehouseParty = inventoryLocationGroupDetail.getWarehouseParty();
        var warehousePartyPK = inventoryLocationGroupDetail.getWarehousePartyPK();
        var inventoryLocationGroupName = inventoryLocationGroupDetailValue.getInventoryLocationGroupName();
        var isDefault = inventoryLocationGroupDetailValue.getIsDefault();
        var sortOrder = inventoryLocationGroupDetailValue.getSortOrder();
        
        if(checkDefault) {
            var defaultInventoryLocationGroup = getDefaultInventoryLocationGroup(warehouseParty);
            var defaultFound = defaultInventoryLocationGroup != null && !defaultInventoryLocationGroup.equals(inventoryLocationGroup);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultInventoryLocationGroupDetailValue = getDefaultInventoryLocationGroupDetailValueForUpdate(warehouseParty);
                
                defaultInventoryLocationGroupDetailValue.setIsDefault(false);
                updateInventoryLocationGroupFromValue(defaultInventoryLocationGroupDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        inventoryLocationGroupDetail = InventoryLocationGroupDetailFactory.getInstance().create(inventoryLocationGroupPK,
                warehousePartyPK, inventoryLocationGroupName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME_LONG);
        
        inventoryLocationGroup.setActiveDetail(inventoryLocationGroupDetail);
        inventoryLocationGroup.setLastDetail(inventoryLocationGroupDetail);
        inventoryLocationGroup.store();
        
        sendEvent(inventoryLocationGroupPK, EventTypes.MODIFY, null, null, updatedBy);
    }
    
    public void updateInventoryLocationGroupFromValue(InventoryLocationGroupDetailValue inventoryLocationGroupDetailValue, BasePK updatedBy) {
        updateInventoryLocationGroupFromValue(inventoryLocationGroupDetailValue, true, updatedBy);
    }
    
    public InventoryLocationGroupStatusChoicesBean getInventoryLocationGroupStatusChoices(String defaultInventoryLocationGroupStatusChoice, Language language,
            InventoryLocationGroup inventoryLocationGroup, PartyPK partyPK) {
        var inventoryLocationGroupStatusChoicesBean = new InventoryLocationGroupStatusChoicesBean();
        var entityInstance = getEntityInstanceByBaseEntity(inventoryLocationGroup);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(InventoryLocationGroupStatusConstants.Workflow_INVENTORY_LOCATION_GROUP_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(inventoryLocationGroupStatusChoicesBean, defaultInventoryLocationGroupStatusChoice, language,
                false, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return inventoryLocationGroupStatusChoicesBean;
    }
    
    public void setInventoryLocationGroupStatus(ExecutionErrorAccumulator eea, InventoryLocationGroup inventoryLocationGroup,
            String inventoryLocationGroupStatusChoice, PartyPK modifiedBy) {
        var entityInstance = getEntityInstanceByBaseEntity(inventoryLocationGroup);
        var workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(InventoryLocationGroupStatusConstants.Workflow_INVENTORY_LOCATION_GROUP_STATUS,
                entityInstance);
        var workflowDestination = inventoryLocationGroupStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), inventoryLocationGroupStatusChoice);
        
        if(workflowDestination != null || inventoryLocationGroupStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownInventoryLocationGroupStatusChoice.name(), inventoryLocationGroupStatusChoice);
        }
    }
    
    private void deleteInventoryLocationGroup(InventoryLocationGroup inventoryLocationGroup, BasePK deletedBy, boolean adjustDefault) {
        deleteInventoryLocationGroupDescriptionsByInventoryLocationGroup(inventoryLocationGroup, deletedBy);

        var inventoryLocationGroupDetail = inventoryLocationGroup.getLastDetailForUpdate();
        inventoryLocationGroupDetail.setThruTime(session.getStartTime());
        inventoryLocationGroupDetail.store();
        inventoryLocationGroup.setActiveDetail(null);
        
        if(adjustDefault) {
            // Check for default, and pick one if necessary
            var warehouseParty = inventoryLocationGroupDetail.getWarehouseParty();
            var defaultInventoryLocationGroup = getDefaultInventoryLocationGroup(warehouseParty);
            
            if(defaultInventoryLocationGroup == null) {
                var inventoryLocationGroups = getInventoryLocationGroupsByWarehousePartyForUpdate(warehouseParty);
                
                if(!inventoryLocationGroups.isEmpty()) {
                    Iterator iter = inventoryLocationGroups.iterator();
                    if(iter.hasNext()) {
                        defaultInventoryLocationGroup = (InventoryLocationGroup)iter.next();
                    }
                    var inventoryLocationGroupDetailValue = Objects.requireNonNull(defaultInventoryLocationGroup).getLastDetailForUpdate().getInventoryLocationGroupDetailValue().clone();
                    
                    inventoryLocationGroupDetailValue.setIsDefault(true);
                    updateInventoryLocationGroupFromValue(inventoryLocationGroupDetailValue, false, deletedBy);
                }
            }
        }
        
        sendEvent(inventoryLocationGroup.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteInventoryLocationGroup(InventoryLocationGroup inventoryLocationGroup, BasePK deletedBy) {
        deleteInventoryLocationGroupVolumeByInventoryLocationGroup(inventoryLocationGroup, deletedBy);
        deleteInventoryLocationGroupCapacitiesByInventoryLocationGroup(inventoryLocationGroup, deletedBy);
        
        ((WarehouseControl)Session.getModelController(WarehouseControl.class)).deleteLocationsByInventoryLocationGroup(inventoryLocationGroup, deletedBy);
        deleteInventoryLocationGroup(inventoryLocationGroup, deletedBy, true);
    }
    
    public void deleteInventoryLocationGroupsByWarehouseParty(Party warehouseParty, BasePK deletedBy) {
        var inventoryLocationGroups = getInventoryLocationGroupsByWarehousePartyForUpdate(warehouseParty);
        
        inventoryLocationGroups.forEach((inventoryLocationGroup) -> {
            deleteInventoryLocationGroup(inventoryLocationGroup, deletedBy, false);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Descriptions
    // --------------------------------------------------------------------------------
    
    public InventoryLocationGroupDescription createInventoryLocationGroupDescription(InventoryLocationGroup inventoryLocationGroup, Language language, String description, BasePK createdBy) {
        var inventoryLocationGroupDescription = InventoryLocationGroupDescriptionFactory.getInstance().create(inventoryLocationGroup, language, description, session.getStartTime(),
                Session.MAX_TIME_LONG);
        
        sendEvent(inventoryLocationGroup.getPrimaryKey(), EventTypes.MODIFY, inventoryLocationGroupDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return inventoryLocationGroupDescription;
    }
    
    private InventoryLocationGroupDescription getInventoryLocationGroupDescription(InventoryLocationGroup inventoryLocationGroup, Language language, EntityPermission entityPermission) {
        InventoryLocationGroupDescription inventoryLocationGroupDescription;
        
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

            var ps = InventoryLocationGroupDescriptionFactory.getInstance().prepareStatement(query);
            
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
        List<InventoryLocationGroupDescription> inventoryLocationGroupDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroupdescriptions, languages " +
                        "WHERE invlocgrpd_invlocgrp_inventorylocationgroupid = ? AND invlocgrpd_thrutime = ? AND invlocgrpd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroupdescriptions " +
                        "WHERE invlocgrpd_invlocgrp_inventorylocationgroupid = ? AND invlocgrpd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = InventoryLocationGroupDescriptionFactory.getInstance().prepareStatement(query);
            
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
        var inventoryLocationGroupDescription = getInventoryLocationGroupDescription(inventoryLocationGroup, language);
        
        if(inventoryLocationGroupDescription == null && !language.getIsDefault()) {
            inventoryLocationGroupDescription = getInventoryLocationGroupDescription(inventoryLocationGroup, partyControl.getDefaultLanguage());
        }
        
        if(inventoryLocationGroupDescription == null) {
            description = inventoryLocationGroup.getLastDetail().getInventoryLocationGroupName();
        } else {
            description = inventoryLocationGroupDescription.getDescription();
        }
        
        return description;
    }
    
    public InventoryLocationGroupDescriptionTransfer getInventoryLocationGroupDescriptionTransfer(UserVisit userVisit, InventoryLocationGroupDescription inventoryLocationGroupDescription) {
        return inventoryLocationGroupDescriptionTransferCache.getTransfer(userVisit, inventoryLocationGroupDescription);
    }
    
    public List<InventoryLocationGroupDescriptionTransfer> getInventoryLocationGroupDescriptionTransfersByInventoryLocationGroup(UserVisit userVisit, InventoryLocationGroup inventoryLocationGroup) {
        var inventoryLocationGroupDescriptions = getInventoryLocationGroupDescriptionsByInventoryLocationGroup(inventoryLocationGroup);
        List<InventoryLocationGroupDescriptionTransfer> inventoryLocationGroupDescriptionTransfers = null;
        
        if(inventoryLocationGroupDescriptions != null) {
            inventoryLocationGroupDescriptionTransfers = new ArrayList<>(inventoryLocationGroupDescriptions.size());
            
            for(var inventoryLocationGroupDescription : inventoryLocationGroupDescriptions) {
                inventoryLocationGroupDescriptionTransfers.add(inventoryLocationGroupDescriptionTransferCache.getTransfer(userVisit, inventoryLocationGroupDescription));
            }
        }
        
        return inventoryLocationGroupDescriptionTransfers;
    }
    
    public void updateInventoryLocationGroupDescriptionFromValue(InventoryLocationGroupDescriptionValue inventoryLocationGroupDescriptionValue, BasePK updatedBy) {
        if(inventoryLocationGroupDescriptionValue.hasBeenModified()) {
            var inventoryLocationGroupDescription = InventoryLocationGroupDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, inventoryLocationGroupDescriptionValue.getPrimaryKey());
            
            inventoryLocationGroupDescription.setThruTime(session.getStartTime());
            inventoryLocationGroupDescription.store();

            var inventoryLocationGroup = inventoryLocationGroupDescription.getInventoryLocationGroup();
            var language = inventoryLocationGroupDescription.getLanguage();
            var description = inventoryLocationGroupDescriptionValue.getDescription();
            
            inventoryLocationGroupDescription = InventoryLocationGroupDescriptionFactory.getInstance().create(inventoryLocationGroup, language, description,
                    session.getStartTime(), Session.MAX_TIME_LONG);
            
            sendEvent(inventoryLocationGroup.getPrimaryKey(), EventTypes.MODIFY, inventoryLocationGroupDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteInventoryLocationGroupDescription(InventoryLocationGroupDescription inventoryLocationGroupDescription, BasePK deletedBy) {
        inventoryLocationGroupDescription.setThruTime(session.getStartTime());
        
        sendEvent(inventoryLocationGroupDescription.getInventoryLocationGroupPK(), EventTypes.MODIFY, inventoryLocationGroupDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }
    
    public void deleteInventoryLocationGroupDescriptionsByInventoryLocationGroup(InventoryLocationGroup inventoryLocationGroup, BasePK deletedBy) {
        var inventoryLocationGroupDescriptions = getInventoryLocationGroupDescriptionsByInventoryLocationGroupForUpdate(inventoryLocationGroup);
        
        inventoryLocationGroupDescriptions.forEach((inventoryLocationGroupDescription) -> 
                deleteInventoryLocationGroupDescription(inventoryLocationGroupDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Volumes
    // --------------------------------------------------------------------------------
    
    public InventoryLocationGroupVolume createInventoryLocationGroupVolume(InventoryLocationGroup inventoryLocationGroup,
            Long height, Long width, Long depth, BasePK createdBy) {
        var inventoryLocationGroupVolume = InventoryLocationGroupVolumeFactory.getInstance().create(inventoryLocationGroup, height, width, depth,
                session.getStartTime(), Session.MAX_TIME_LONG);
        
        sendEvent(inventoryLocationGroup.getPrimaryKey(), EventTypes.MODIFY, inventoryLocationGroupVolume.getPrimaryKey(), null, createdBy);
        
        return inventoryLocationGroupVolume;
    }
    
    private InventoryLocationGroupVolume getInventoryLocationGroupVolume(InventoryLocationGroup inventoryLocationGroup, EntityPermission entityPermission) {
        InventoryLocationGroupVolume inventoryLocationGroupVolume;
        
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

            var ps = InventoryLocationGroupVolumeFactory.getInstance().prepareStatement(query);
            
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

    public InventoryLocationGroupVolumeValue getInventoryLocationGroupVolumeValueForUpdate(InventoryLocationGroupVolume inventoryLocationGroupVolume) {
        return inventoryLocationGroupVolume == null? null: inventoryLocationGroupVolume.getInventoryLocationGroupVolumeValue().clone();
    }

    public InventoryLocationGroupVolumeTransfer getInventoryLocationGroupVolumeTransfer(UserVisit userVisit, InventoryLocationGroupVolume inventoryInventoryLocationGroupGroupVolume) {
        return inventoryInventoryLocationGroupGroupVolume == null? null: inventoryLocationGroupVolumeTransferCache.getTransfer(userVisit, inventoryInventoryLocationGroupGroupVolume);
    }
    
    public InventoryLocationGroupVolumeTransfer getInventoryLocationGroupVolumeTransfer(UserVisit userVisit, InventoryLocationGroup inventoryInventoryLocationGroupGroup) {
        var inventoryInventoryLocationGroupGroupVolume = getInventoryLocationGroupVolume(inventoryInventoryLocationGroupGroup);
        
        return inventoryInventoryLocationGroupGroupVolume == null? null: inventoryLocationGroupVolumeTransferCache.getTransfer(userVisit, inventoryInventoryLocationGroupGroupVolume);
    }
    
    public void updateInventoryLocationGroupVolumeFromValue(InventoryLocationGroupVolumeValue inventoryInventoryLocationGroupGroupVolumeValue, BasePK updatedBy) {
        if(inventoryInventoryLocationGroupGroupVolumeValue.hasBeenModified()) {
            var inventoryInventoryLocationGroupGroupVolume = InventoryLocationGroupVolumeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryInventoryLocationGroupGroupVolumeValue.getPrimaryKey());
            
            inventoryInventoryLocationGroupGroupVolume.setThruTime(session.getStartTime());
            inventoryInventoryLocationGroupGroupVolume.store();

            var inventoryInventoryLocationGroupGroupPK = inventoryInventoryLocationGroupGroupVolume.getInventoryLocationGroupPK(); // Not updated
            var height = inventoryInventoryLocationGroupGroupVolumeValue.getHeight();
            var width = inventoryInventoryLocationGroupGroupVolumeValue.getWidth();
            var depth = inventoryInventoryLocationGroupGroupVolumeValue.getDepth();
            
            inventoryInventoryLocationGroupGroupVolume = InventoryLocationGroupVolumeFactory.getInstance().create(inventoryInventoryLocationGroupGroupPK, height,
                    width, depth, session.getStartTime(), Session.MAX_TIME_LONG);
            
            sendEvent(inventoryInventoryLocationGroupGroupPK, EventTypes.MODIFY, inventoryInventoryLocationGroupGroupVolume.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteInventoryLocationGroupVolume(InventoryLocationGroupVolume inventoryInventoryLocationGroupGroupVolume, BasePK deletedBy) {
        inventoryInventoryLocationGroupGroupVolume.setThruTime(session.getStartTime());
        
        sendEvent(inventoryInventoryLocationGroupGroupVolume.getInventoryLocationGroup().getPrimaryKey(), EventTypes.MODIFY, inventoryInventoryLocationGroupGroupVolume.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteInventoryLocationGroupVolumeByInventoryLocationGroup(InventoryLocationGroup inventoryInventoryLocationGroupGroup, BasePK deletedBy) {
        var inventoryInventoryLocationGroupGroupVolume = getInventoryLocationGroupVolumeForUpdate(inventoryInventoryLocationGroupGroup);
        
        if(inventoryInventoryLocationGroupGroupVolume != null)
            deleteInventoryLocationGroupVolume(inventoryInventoryLocationGroupGroupVolume, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Group Capacities
    // --------------------------------------------------------------------------------
    
    public InventoryLocationGroupCapacity createInventoryLocationGroupCapacity(InventoryLocationGroup inventoryInventoryLocationGroupGroup,
            UnitOfMeasureType unitOfMeasureType, Long capacity, BasePK createdBy) {
        var inventoryInventoryLocationGroupGroupCapacity = InventoryLocationGroupCapacityFactory.getInstance().create(inventoryInventoryLocationGroupGroup,
                unitOfMeasureType, capacity, session.getStartTime(), Session.MAX_TIME_LONG);
        
        sendEvent(inventoryInventoryLocationGroupGroup.getPrimaryKey(), EventTypes.MODIFY, inventoryInventoryLocationGroupGroupCapacity.getPrimaryKey(), null, createdBy);
        
        return inventoryInventoryLocationGroupGroupCapacity;
    }
    
    private List<InventoryLocationGroupCapacity> getInventoryLocationGroupCapacitiesByInventoryLocationGroup(InventoryLocationGroup inventoryInventoryLocationGroupGroup, EntityPermission entityPermission) {
        List<InventoryLocationGroupCapacity> inventoryInventoryLocationGroupGroupCapacities;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroupcapacities, unitofmeasuretypedetails, unitofmeasurekinddetails " +
                        "WHERE invlocgrpcap_invlocgrp_inventorylocationgroupid = ? AND invlocgrpcap_thrutime = ? " +
                        "AND invlocgrpcap_uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ? " +
                        "AND uomtdt_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename, uomkdt_sortorder, uomkdt_unitofmeasurekindname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventorylocationgroupcapacities " +
                        "WHERE invlocgrpcap_invlocgrp_inventorylocationgroupid = ? AND invlocgrpcap_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = InventoryLocationGroupCapacityFactory.getInstance().prepareStatement(query);
            
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
        InventoryLocationGroupCapacity inventoryInventoryLocationGroupGroupCapacity;
        
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

            var ps = InventoryLocationGroupCapacityFactory.getInstance().prepareStatement(query);
            
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
        var inventoryLocationGroupCapacity = getInventoryLocationGroupCapacityForUpdate(inventoryLocationGroup, unitOfMeasureType);
        
        return inventoryLocationGroupCapacity == null? null: inventoryLocationGroupCapacity.getInventoryLocationGroupCapacityValue().clone();
    }
    
    public void updateInventoryLocationGroupCapacityFromValue(InventoryLocationGroupCapacityValue inventoryLocationGroupCapacityValue, BasePK updatedBy) {
        if(inventoryLocationGroupCapacityValue.hasBeenModified()) {
            var inventoryLocationGroupCapacity = InventoryLocationGroupCapacityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryLocationGroupCapacityValue.getPrimaryKey());
            
            inventoryLocationGroupCapacity.setThruTime(session.getStartTime());
            inventoryLocationGroupCapacity.store();

            var unitOfMeasureTypePK = inventoryLocationGroupCapacity.getUnitOfMeasureTypePK(); // Not updated
            var inventoryLocationGroupPK = inventoryLocationGroupCapacity.getInventoryLocationGroupPK(); // Not updated
            var capacity = inventoryLocationGroupCapacityValue.getCapacity();
            
            inventoryLocationGroupCapacity = InventoryLocationGroupCapacityFactory.getInstance().create(inventoryLocationGroupPK, unitOfMeasureTypePK, capacity,
                    session.getStartTime(), Session.MAX_TIME_LONG);
            
            sendEvent(unitOfMeasureTypePK, EventTypes.MODIFY, inventoryLocationGroupCapacity.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public InventoryLocationGroupCapacityTransfer getInventoryLocationGroupCapacityTransfer(UserVisit userVisit, InventoryLocationGroupCapacity inventoryLocationGroupCapacity) {
        return inventoryLocationGroupCapacityTransferCache.getTransfer(userVisit, inventoryLocationGroupCapacity);
    }
    
    public List<InventoryLocationGroupCapacityTransfer> getInventoryLocationGroupCapacityTransfersByInventoryLocationGroup(UserVisit userVisit, InventoryLocationGroup inventoryLocationGroup) {
        var inventoryLocationGroupCapacities = getInventoryLocationGroupCapacitiesByInventoryLocationGroup(inventoryLocationGroup);
        List<InventoryLocationGroupCapacityTransfer> inventoryLocationGroupCapacityTransfers = new ArrayList<>(inventoryLocationGroupCapacities.size());
        
        inventoryLocationGroupCapacities.forEach((inventoryLocationGroupCapacity) ->
                inventoryLocationGroupCapacityTransfers.add(inventoryLocationGroupCapacityTransferCache.getTransfer(userVisit, inventoryLocationGroupCapacity))
        );
        
        return inventoryLocationGroupCapacityTransfers;
    }
    
    public void deleteInventoryLocationGroupCapacity(InventoryLocationGroupCapacity inventoryLocationGroupCapacity, BasePK deletedBy) {
        inventoryLocationGroupCapacity.setThruTime(session.getStartTime());
        
        sendEvent(inventoryLocationGroupCapacity.getInventoryLocationGroup().getPrimaryKey(), EventTypes.MODIFY, inventoryLocationGroupCapacity.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteInventoryLocationGroupCapacitiesByInventoryLocationGroup(InventoryLocationGroup inventoryLocationGroup, BasePK deletedBy) {
        var inventoryLocationGroupCapacities = getInventoryLocationGroupCapacitiesByInventoryLocationGroupForUpdate(inventoryLocationGroup);
        
        inventoryLocationGroupCapacities.forEach((inventoryLocationGroupCapacity) -> 
                deleteInventoryLocationGroupCapacity(inventoryLocationGroupCapacity, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Conditions
    // --------------------------------------------------------------------------------
    
    public InventoryCondition createInventoryCondition(final String inventoryConditionName, Boolean isDefault,
            final Integer sortOrder, final BasePK createdBy) {
        var defaultInventoryCondition = getDefaultInventoryCondition();
        var defaultFound = defaultInventoryCondition != null;
        
        if(defaultFound && isDefault) {
            var defaultInventoryConditionDetailValue = getDefaultInventoryConditionDetailValueForUpdate();
            
            defaultInventoryConditionDetailValue.setIsDefault(false);
            updateInventoryConditionFromValue(defaultInventoryConditionDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryCondition = InventoryConditionFactory.getInstance().create();
        var inventoryConditionDetail = InventoryConditionDetailFactory.getInstance().create(session,
                inventoryCondition, inventoryConditionName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME_LONG);
        
        // Convert to R/W
        inventoryCondition = InventoryConditionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, inventoryCondition.getPrimaryKey());
        inventoryCondition.setActiveDetail(inventoryConditionDetail);
        inventoryCondition.setLastDetail(inventoryConditionDetail);
        inventoryCondition.store();
        
        sendEvent(inventoryCondition.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return inventoryCondition;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryCondition */
    public InventoryCondition getInventoryConditionByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new InventoryConditionPK(entityInstance.getEntityUniqueId());

        return InventoryConditionFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public InventoryCondition getInventoryConditionByEntityInstance(final EntityInstance entityInstance) {
        return getInventoryConditionByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryCondition getInventoryConditionByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getInventoryConditionByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countInventoryConditions() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM inventoryconditions, inventoryconditiondetails " +
                "WHERE invcon_activedetailid = invcondt_inventoryconditiondetailid");
    }

    private static final Map<EntityPermission, String> getInventoryConditionByNameQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM inventoryconditions, inventoryconditiondetails " +
                    "WHERE invcon_inventoryconditionid = invcondt_invcon_inventoryconditionid AND invcondt_inventoryconditionname = ? AND invcondt_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM inventoryconditions, inventoryconditiondetails " +
                    "WHERE invcon_inventoryconditionid = invcondt_invcon_inventoryconditionid AND invcondt_inventoryconditionname = ? AND invcondt_thrutime = ? " +
                    "FOR UPDATE");

    public InventoryCondition getInventoryConditionByName(final String inventoryConditionName, final EntityPermission entityPermission) {
        return InventoryConditionFactory.getInstance().getEntityFromQuery(entityPermission, getInventoryConditionByNameQueries,
                inventoryConditionName, Session.MAX_TIME);
    }
    
    public InventoryCondition getInventoryConditionByName(final String inventoryConditionName) {
        return getInventoryConditionByName(inventoryConditionName, EntityPermission.READ_ONLY);
    }
    
    public InventoryCondition getInventoryConditionByNameForUpdate(final String inventoryConditionName) {
        return getInventoryConditionByName(inventoryConditionName, EntityPermission.READ_WRITE);
    }
    
    public InventoryConditionDetailValue getInventoryConditionDetailValueForUpdate(final InventoryCondition inventoryCondition) {
        return inventoryCondition == null ? null: inventoryCondition.getLastDetailForUpdate().getInventoryConditionDetailValue().clone();
    }
    
    public InventoryConditionDetailValue getInventoryConditionDetailValueByNameForUpdate(final String inventoryConditionName) {
        return getInventoryConditionDetailValueForUpdate(getInventoryConditionByNameForUpdate(inventoryConditionName));
    }
    
    private static final Map<EntityPermission, String> getDefaultInventoryConditionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM inventoryconditions, inventoryconditiondetails " +
                    "WHERE invcon_inventoryconditionid = invcondt_invcon_inventoryconditionid AND invcondt_isdefault = 1 AND invcondt_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM inventoryconditions, inventoryconditiondetails " +
                    "WHERE invcon_inventoryconditionid = invcondt_invcon_inventoryconditionid AND invcondt_isdefault = 1 AND invcondt_thrutime = ? " +
                    "FOR UPDATE");

    public InventoryCondition getDefaultInventoryCondition(final EntityPermission entityPermission) {
        return InventoryConditionFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultInventoryConditionQueries,
                Session.MAX_TIME);
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
    
    private static final Map<EntityPermission, String> getInventoryConditionsQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " + "FROM inventoryconditions, inventoryconditiondetails " +
                    "WHERE invcon_inventoryconditionid = invcondt_invcon_inventoryconditionid AND invcondt_thrutime = ? " +
                    "ORDER BY invcondt_sortorder, invcondt_inventoryconditionname " +
                    "_LIMIT_",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " + "FROM inventoryconditions, inventoryconditiondetails " +
                    "WHERE invcon_inventoryconditionid = invcondt_invcon_inventoryconditionid AND invcondt_thrutime = ? " +
                    "FOR UPDATE");

    private List<InventoryCondition> getInventoryConditions(final EntityPermission entityPermission) {
        return InventoryConditionFactory.getInstance().getEntitiesFromQuery(entityPermission, getInventoryConditionsQueries,
                Session.MAX_TIME);
    }
    
    public List<InventoryCondition> getInventoryConditions() {
        return getInventoryConditions(EntityPermission.READ_ONLY);
    }
    
    public List<InventoryCondition> getInventoryConditionsForUpdate() {
        return getInventoryConditions(EntityPermission.READ_WRITE);
    }
    
    public InventoryConditionTransfer getInventoryConditionTransfer(final UserVisit userVisit,
            final InventoryCondition inventoryCondition) {
        return inventoryConditionTransferCache.getTransfer(userVisit, inventoryCondition);
    }
    
    public List<InventoryConditionTransfer> getInventoryConditionTransfers(final UserVisit userVisit,
            final Collection<InventoryCondition> inventoryConditions) {
        var inventoryConditionTransfers = new ArrayList<InventoryConditionTransfer>(inventoryConditions.size());
        
        inventoryConditions.forEach((inventoryCondition) ->
                inventoryConditionTransfers.add(inventoryConditionTransferCache.getTransfer(userVisit, inventoryCondition))
        );
        
        return inventoryConditionTransfers;
    }
    
    public List<InventoryConditionTransfer> getInventoryConditionTransfers(final UserVisit userVisit) {
        return getInventoryConditionTransfers(userVisit, getInventoryConditions());
    }
    
    public InventoryConditionChoicesBean getInventoryConditionChoices(final String defaultInventoryConditionChoice,
            final Language language, final boolean allowNullChoice) {
        var inventoryConditions = getInventoryConditions();
        var size = inventoryConditions.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultInventoryConditionChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var inventoryCondition : inventoryConditions) {
            var inventoryConditionDetail = inventoryCondition.getLastDetail();

            var label = getBestInventoryConditionDescription(inventoryCondition, language);
            var value = inventoryConditionDetail.getInventoryConditionName();
            
            labels.add(label == null ? value : label);
            values.add(value);
            
            var usingDefaultChoice = Objects.equals(defaultInventoryConditionChoice, value);
            if(usingDefaultChoice || (defaultValue == null && inventoryConditionDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new InventoryConditionChoicesBean(labels, values, defaultValue);
    }
    
    public InventoryConditionChoicesBean getInventoryConditionChoicesByInventoryConditionUseType(final String defaultInventoryConditionChoice,
            final Language language, final boolean allowNullChoice, final InventoryConditionUseType inventoryConditionUseType) {
        var inventoryConditionUses = getInventoryConditionUsesByInventoryConditionUseType(inventoryConditionUseType);
        var size = inventoryConditionUses.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultInventoryConditionChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var inventoryConditionUse : inventoryConditionUses) {
            var inventoryCondition = inventoryConditionUse.getInventoryCondition();
            var inventoryConditionDetail = inventoryCondition.getLastDetail();

            var label = getBestInventoryConditionDescription(inventoryCondition, language);
            var value = inventoryConditionDetail.getInventoryConditionName();
            
            labels.add(label == null ? value : label);
            values.add(value);

            var usingDefaultChoice = Objects.equals(defaultInventoryConditionChoice, value);
            if(usingDefaultChoice || (defaultValue == null && inventoryConditionUse.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new InventoryConditionChoicesBean(labels, values, defaultValue);
    }
    
    private void updateInventoryConditionFromValue(final InventoryConditionDetailValue inventoryConditionDetailValue,
            final boolean checkDefault, final BasePK updatedBy) {
        if(inventoryConditionDetailValue.hasBeenModified()) {
            var inventoryCondition = InventoryConditionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryConditionDetailValue.getInventoryConditionPK());
            var inventoryConditionDetail = inventoryCondition.getActiveDetailForUpdate();
            
            inventoryConditionDetail.setThruTime(session.getStartTime());
            inventoryConditionDetail.store();

            var inventoryConditionPK = inventoryConditionDetail.getInventoryConditionPK();
            var inventoryConditionName = inventoryConditionDetailValue.getInventoryConditionName();
            var isDefault = inventoryConditionDetailValue.getIsDefault();
            var sortOrder = inventoryConditionDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultInventoryCondition = getDefaultInventoryCondition();
                var defaultFound = defaultInventoryCondition != null && !defaultInventoryCondition.equals(inventoryCondition);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultInventoryConditionDetailValue = getDefaultInventoryConditionDetailValueForUpdate();
                    
                    defaultInventoryConditionDetailValue.setIsDefault(false);
                    updateInventoryConditionFromValue(defaultInventoryConditionDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            inventoryConditionDetail = InventoryConditionDetailFactory.getInstance().create(inventoryConditionPK,
                    inventoryConditionName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME_LONG);
            
            inventoryCondition.setActiveDetail(inventoryConditionDetail);
            inventoryCondition.setLastDetail(inventoryConditionDetail);
            
            sendEvent(inventoryConditionPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateInventoryConditionFromValue(final InventoryConditionDetailValue inventoryConditionDetailValue,
            final BasePK updatedBy) {
        updateInventoryConditionFromValue(inventoryConditionDetailValue, true, updatedBy);
    }
    
    public void deleteInventoryCondition(final InventoryCondition inventoryCondition, final BasePK deletedBy) {
        var itemControl = Session.getModelController(ItemControl.class);
        var vendorControl = Session.getModelController(VendorControl.class);
        
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
        
        var inventoryConditionDetail = inventoryCondition.getLastDetailForUpdate();
        inventoryConditionDetail.setThruTime(session.getStartTime());
        inventoryConditionDetail.store();
        inventoryCondition.setActiveDetail(null);
        
        // Check for default, and pick one if necessary
        var defaultInventoryCondition = getDefaultInventoryCondition();
        if(defaultInventoryCondition == null) {
            var inventoryConditions = getInventoryConditionsForUpdate();
            
            if(!inventoryConditions.isEmpty()) {
                var iter = inventoryConditions.iterator();
                if(iter.hasNext()) {
                    defaultInventoryCondition = iter.next();
                }
                var inventoryConditionDetailValue = Objects.requireNonNull(defaultInventoryCondition).getLastDetailForUpdate().getInventoryConditionDetailValue().clone();
                
                inventoryConditionDetailValue.setIsDefault(true);
                updateInventoryConditionFromValue(inventoryConditionDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(inventoryCondition.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Descriptions
    // --------------------------------------------------------------------------------
    
    public InventoryConditionDescription createInventoryConditionDescription(final InventoryCondition inventoryCondition,
            final Language language, final String description, final BasePK createdBy) {
        var inventoryConditionDescription = InventoryConditionDescriptionFactory.getInstance().create(inventoryCondition,
                language, description, session.getStartTime(), Session.MAX_TIME_LONG);
        
        sendEvent(inventoryCondition.getPrimaryKey(), EventTypes.MODIFY, inventoryConditionDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return inventoryConditionDescription;
    }

    private static final Map<EntityPermission, String> getInventoryConditionDescriptionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM inventoryconditiondescriptions " +
                    "WHERE invcond_invcon_inventoryconditionid = ? AND invcond_lang_languageid = ? AND invcond_thrutime = ?",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM inventoryconditiondescriptions " +
                    "WHERE invcond_invcon_inventoryconditionid = ? AND invcond_lang_languageid = ? AND invcond_thrutime = ? " +
                    "FOR UPDATE");

    private InventoryConditionDescription getInventoryConditionDescription(final InventoryCondition inventoryCondition,
            final Language language, final EntityPermission entityPermission) {
        return InventoryConditionDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getInventoryConditionDescriptionQueries,
                inventoryCondition, language, Session.MAX_TIME);
    }

    public InventoryConditionDescription getInventoryConditionDescription(final InventoryCondition inventoryCondition,
            final Language language) {
        return getInventoryConditionDescription(inventoryCondition, language, EntityPermission.READ_ONLY);
    }
    
    public InventoryConditionDescription getInventoryConditionDescriptionForUpdate(final InventoryCondition inventoryCondition,
            final Language language) {
        return getInventoryConditionDescription(inventoryCondition, language, EntityPermission.READ_WRITE);
    }
    
    public InventoryConditionDescriptionValue getInventoryConditionDescriptionValue(final InventoryConditionDescription inventoryConditionDescription) {
        return inventoryConditionDescription == null ? null: inventoryConditionDescription.getInventoryConditionDescriptionValue().clone();
    }
    
    public InventoryConditionDescriptionValue getInventoryConditionDescriptionValueForUpdate(final InventoryCondition inventoryCondition,
            final Language language) {
        return getInventoryConditionDescriptionValue(getInventoryConditionDescriptionForUpdate(inventoryCondition, language));
    }

    private static final Map<EntityPermission, String> getInventoryConditionDescriptionsByInventoryConditionQueries = Map.of(
            EntityPermission.READ_ONLY,
            "SELECT _ALL_ " +
                    "FROM inventoryconditiondescriptions, languages " +
                    "WHERE invcond_invcon_inventoryconditionid = ? AND invcond_thrutime = ? AND invcond_lang_languageid = lang_languageid " +
                    "ORDER BY lang_sortorder, lang_languageisoname " +
                    "_LIMIT_",
            EntityPermission.READ_WRITE,
            "SELECT _ALL_ " +
                    "FROM inventoryconditiondescriptions " +
                    "WHERE invcond_invcon_inventoryconditionid = ? AND invcond_thrutime = ? " +
                    "FOR UPDATE");

    private List<InventoryConditionDescription> getInventoryConditionDescriptionsByInventoryCondition(final InventoryCondition inventoryCondition,
            final EntityPermission entityPermission) {
        return InventoryConditionDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission,
                getInventoryConditionDescriptionsByInventoryConditionQueries,
                inventoryCondition, Session.MAX_TIME);
    }
    
    public List<InventoryConditionDescription> getInventoryConditionDescriptionsByInventoryCondition(final InventoryCondition inventoryCondition) {
        return getInventoryConditionDescriptionsByInventoryCondition(inventoryCondition, EntityPermission.READ_ONLY);
    }
    
    public List<InventoryConditionDescription> getInventoryConditionDescriptionsByInventoryConditionForUpdate(final InventoryCondition inventoryCondition) {
        return getInventoryConditionDescriptionsByInventoryCondition(inventoryCondition, EntityPermission.READ_WRITE);
    }
    
    public String getBestInventoryConditionDescription(final InventoryCondition inventoryCondition, final Language language) {
        var inventoryConditionDescription = getInventoryConditionDescription(inventoryCondition, language);
        String description;

        if(inventoryConditionDescription == null && !language.getIsDefault()) {
            inventoryConditionDescription = getInventoryConditionDescription(inventoryCondition, partyControl.getDefaultLanguage());
        }
        
        if(inventoryConditionDescription == null) {
            description = inventoryCondition.getLastDetail().getInventoryConditionName();
        } else {
            description = inventoryConditionDescription.getDescription();
        }
        
        return description;
    }
    
    public InventoryConditionDescriptionTransfer getInventoryConditionDescriptionTransfer(final UserVisit userVisit,
            final InventoryConditionDescription inventoryConditionDescription) {
        return inventoryConditionDescriptionTransferCache.getTransfer(userVisit, inventoryConditionDescription);
    }
    
    public List<InventoryConditionDescriptionTransfer> getInventoryConditionDescriptionTransfersByInventoryCondition(final UserVisit userVisit,
            final InventoryCondition inventoryCondition) {
        var inventoryConditionDescriptions = getInventoryConditionDescriptionsByInventoryCondition(inventoryCondition);
        var inventoryConditionDescriptionTransfers = new ArrayList<InventoryConditionDescriptionTransfer>(inventoryConditionDescriptions.size());
        
        inventoryConditionDescriptions.forEach((inventoryConditionDescription) ->
                inventoryConditionDescriptionTransfers.add(inventoryConditionDescriptionTransferCache.getTransfer(userVisit, inventoryConditionDescription))
        );
        
        return inventoryConditionDescriptionTransfers;
    }
    
    public void updateInventoryConditionDescriptionFromValue(final InventoryConditionDescriptionValue inventoryConditionDescriptionValue,
            final BasePK updatedBy) {
        if(inventoryConditionDescriptionValue.hasBeenModified()) {
            var inventoryConditionDescription = InventoryConditionDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, inventoryConditionDescriptionValue.getPrimaryKey());
            
            inventoryConditionDescription.setThruTime(session.getStartTime());
            inventoryConditionDescription.store();
            
            var inventoryCondition = inventoryConditionDescription.getInventoryCondition();
            var language = inventoryConditionDescription.getLanguage();
            var description = inventoryConditionDescriptionValue.getDescription();
            
            inventoryConditionDescription = InventoryConditionDescriptionFactory.getInstance().create(inventoryCondition, language, description,
                    session.getStartTime(), Session.MAX_TIME_LONG);
            
            sendEvent(inventoryCondition.getPrimaryKey(), EventTypes.MODIFY, inventoryConditionDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteInventoryConditionDescription(final InventoryConditionDescription inventoryConditionDescription, final BasePK deletedBy) {
        inventoryConditionDescription.setThruTime(session.getStartTime());
        
        sendEvent(inventoryConditionDescription.getInventoryConditionPK(), EventTypes.MODIFY, inventoryConditionDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteInventoryConditionDescriptionsByInventoryCondition(final InventoryCondition inventoryCondition, final BasePK deletedBy) {
        var inventoryConditionDescriptions = getInventoryConditionDescriptionsByInventoryConditionForUpdate(inventoryCondition);
        
        inventoryConditionDescriptions.forEach((inventoryConditionDescription) -> {
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
        var ps = InventoryConditionUseTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM inventoryconditionusetypes " +
                "ORDER BY invconut_inventoryconditionusetypename " +
                "_LIMIT_");
        
        return InventoryConditionUseTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public InventoryConditionUseType getInventoryConditionUseTypeByName(String inventoryConditionUseTypeName) {
        InventoryConditionUseType inventoryConditionUseType;
        
        try {
            var ps = InventoryConditionUseTypeFactory.getInstance().prepareStatement(
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
        var inventoryConditionUseTypes = getInventoryConditionUseTypes();
        var size = inventoryConditionUseTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        Iterator iter = inventoryConditionUseTypes.iterator();
        
        while(iter.hasNext()) {
            var inventoryConditionUseType = (InventoryConditionUseType)iter.next();
            
            var label = getBestInventoryConditionUseTypeDescription(inventoryConditionUseType, language);
            var value = inventoryConditionUseType.getInventoryConditionUseTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultInventoryConditionUseTypeChoice != null && defaultInventoryConditionUseTypeChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new InventoryConditionUseTypeChoicesBean(labels, values, defaultValue);
    }
    
    public InventoryConditionUseTypeTransfer getInventoryConditionUseTypeTransfer(UserVisit userVisit,
            InventoryConditionUseType inventoryConditionUseType) {
        return inventoryConditionUseTypeTransferCache.getTransfer(userVisit, inventoryConditionUseType);
    }
    
    private List<InventoryConditionUseTypeTransfer> getInventoryConditionUseTypeTransfers(final UserVisit userVisit,
            final List<InventoryConditionUseType> inventoryConditionUseTypes) {
        List<InventoryConditionUseTypeTransfer> inventoryConditionUseTypeTransfers = null;
        
        if(inventoryConditionUseTypes != null) {
            
            inventoryConditionUseTypeTransfers = new ArrayList<>(inventoryConditionUseTypes.size());
            
            for(var inventoryConditionUseType : inventoryConditionUseTypes) {
                inventoryConditionUseTypeTransfers.add(inventoryConditionUseTypeTransferCache.getTransfer(userVisit, inventoryConditionUseType));
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
        InventoryConditionUseTypeDescription inventoryConditionUseTypeDescription;
        
        try {
            var ps = InventoryConditionUseTypeDescriptionFactory.getInstance().prepareStatement(
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
        var inventoryConditionUseTypeDescription = getInventoryConditionUseTypeDescription(inventoryConditionUseType, language);
        
        if(inventoryConditionUseTypeDescription == null && !language.getIsDefault()) {
            inventoryConditionUseTypeDescription = getInventoryConditionUseTypeDescription(inventoryConditionUseType, partyControl.getDefaultLanguage());
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
        var defaultInventoryConditionUse = getDefaultInventoryConditionUse(inventoryConditionUseType);
        var defaultFound = defaultInventoryConditionUse != null;
        
        if(defaultFound && isDefault) {
            var defaultInventoryConditionUseValue = getDefaultInventoryConditionUseValueForUpdate(inventoryConditionUseType);
            
            defaultInventoryConditionUseValue.setIsDefault(false);
            updateInventoryConditionUseFromValue(defaultInventoryConditionUseValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var inventoryConditionUse = InventoryConditionUseFactory.getInstance().create(inventoryConditionUseType,
                inventoryCondition, isDefault, session.getStartTime(), Session.MAX_TIME_LONG);
        
        sendEvent(inventoryCondition.getPrimaryKey(), EventTypes.MODIFY, inventoryConditionUse.getPrimaryKey(),
                null, createdBy);
        
        return inventoryConditionUse;
    }
    
    private InventoryConditionUse getInventoryConditionUse(InventoryConditionUseType inventoryConditionUseType,
            InventoryCondition inventoryCondition, EntityPermission entityPermission) {
        InventoryConditionUse inventoryConditionUse;
        
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
            var ps = InventoryConditionUseFactory.getInstance().prepareStatement(query);
            
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
        InventoryConditionUse inventoryConditionUse;
        
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
            var ps = InventoryConditionUseFactory.getInstance().prepareStatement(query);
            
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
        List<InventoryConditionUse> inventoryConditionUses;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionuses, inventoryconditionusetypes " +
                        "WHERE invconu_invcon_inventoryconditionid = ? AND invconu_thrutime = ? " +
                        "AND invconu_invconut_inventoryconditionusetypeid = invconut_inventoryconditionusetypeid " +
                        "ORDER BY invconut_sortorder, invconut_inventoryconditionusetypename " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionuses " +
                        "WHERE invconu_invcon_inventoryconditionid = ? AND invconu_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = InventoryConditionUseFactory.getInstance().prepareStatement(query);
            
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
        List<InventoryConditionUse> inventoryConditionUses;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionuses, inventoryconditions, inventoryconditiondetails " +
                        "WHERE invconu_invconut_inventoryconditionusetypeid = ? AND invconu_thrutime = ? " +
                        "AND invconu_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_activedetailid = invcondt_inventoryconditiondetailid " +
                        "ORDER BY invcondt_sortorder, invcondt_inventoryconditionname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionuses, inventoryconditions, inventoryconditiondetails " +
                        "WHERE invconu_invconut_inventoryconditionusetypeid = ? AND invconu_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = InventoryConditionUseFactory.getInstance().prepareStatement(query);
            
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
            
            inventoryConditionUseTransfers = new ArrayList<>(inventoryConditionUses.size());
            
            for(var inventoryConditionUse : inventoryConditionUses) {
                inventoryConditionUseTransfers.add(inventoryConditionUseTransferCache.getTransfer(userVisit, inventoryConditionUse));
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
    
    public long countInventoryConditionUsesByInventoryConditionUseType(InventoryConditionUseType inventoryConditionUseType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM inventoryconditionuses " +
                "WHERE invconu_invconut_inventoryconditionusetypeid = ? AND invconu_thrutime = ?",
                inventoryConditionUseType, Session.MAX_TIME);
    }
    
    public long countInventoryConditionUsesByInventoryCondition(InventoryCondition inventoryCondition) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM inventoryconditionuses " +
                "WHERE invconu_invcon_inventoryconditionid = ? AND invconu_thrutime = ?",
                inventoryCondition, Session.MAX_TIME);
    }
    
    private void updateInventoryConditionUseFromValue(InventoryConditionUseValue inventoryConditionUseValue, boolean checkDefault,
            BasePK updatedBy) {
        var inventoryConditionUse = InventoryConditionUseFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, inventoryConditionUseValue.getPrimaryKey());
        
        inventoryConditionUse.setThruTime(session.getStartTime());
        inventoryConditionUse.store();

        var inventoryConditionUseTypePK = inventoryConditionUse.getInventoryConditionUseTypePK();
        var inventoryConditionUseType = inventoryConditionUse.getInventoryConditionUseType();
        var inventoryConditionPK = inventoryConditionUse.getInventoryConditionPK();
        var isDefault = inventoryConditionUseValue.getIsDefault();
        
        if(checkDefault) {
            var defaultInventoryConditionUse = getDefaultInventoryConditionUse(inventoryConditionUseType);
            var defaultFound = defaultInventoryConditionUse != null && !defaultInventoryConditionUse.equals(inventoryConditionUse);
            
            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultInventoryConditionUseValue = getDefaultInventoryConditionUseValueForUpdate(inventoryConditionUseType);
                
                defaultInventoryConditionUseValue.setIsDefault(false);
                updateInventoryConditionUseFromValue(defaultInventoryConditionUseValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }
        
        inventoryConditionUse = InventoryConditionUseFactory.getInstance().create(inventoryConditionUseTypePK,
                inventoryConditionPK, isDefault, session.getStartTime(), Session.MAX_TIME_LONG);
        
        sendEvent(inventoryConditionPK, EventTypes.MODIFY, inventoryConditionUse.getPrimaryKey(), null, updatedBy);
    }
    
    /** Given a InventoryConditionUseValue, update only the isDefault property.
     */
    public void updateInventoryConditionUseFromValue(InventoryConditionUseValue inventoryConditionUseValue, BasePK updatedBy) {
        updateInventoryConditionUseFromValue(inventoryConditionUseValue, true, updatedBy);
    }
    
    public void deleteInventoryConditionUse(InventoryConditionUse inventoryConditionUse, BasePK deletedBy) {
        inventoryConditionUse.setThruTime(session.getStartTime());
        inventoryConditionUse.store();
        
        // Check for default, and pick one if necessary
        var inventoryConditionUseType = inventoryConditionUse.getInventoryConditionUseType();
        var defaultInventoryConditionUse = getDefaultInventoryConditionUse(inventoryConditionUseType);
        if(defaultInventoryConditionUse == null) {
            var inventoryConditionUses = getInventoryConditionUsesByInventoryConditionUseTypeForUpdate(inventoryConditionUseType);
            
            if(!inventoryConditionUses.isEmpty()) {
                Iterator iter = inventoryConditionUses.iterator();
                if(iter.hasNext()) {
                    defaultInventoryConditionUse = (InventoryConditionUse)iter.next();
                }
                var inventoryConditionUseValue = defaultInventoryConditionUse.getInventoryConditionUseValue().clone();
                
                inventoryConditionUseValue.setIsDefault(true);
                updateInventoryConditionUseFromValue(inventoryConditionUseValue, false, deletedBy);
            }
        }
        
        sendEvent(inventoryConditionUse.getInventoryConditionPK(), EventTypes.MODIFY,
                inventoryConditionUse.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteInventoryConditionUseByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        var inventoryConditionUses = getInventoryConditionUsesByInventoryConditionForUpdate(inventoryCondition);
        
        inventoryConditionUses.forEach((inventoryConditionUse) -> 
                deleteInventoryConditionUse(inventoryConditionUse, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Condition Gl Accounts
    // --------------------------------------------------------------------------------
    
    public InventoryConditionGlAccount createInventoryConditionGlAccount(InventoryCondition inventoryCondition,
            ItemAccountingCategory itemAccountingCategory, GlAccount inventoryGlAccount, GlAccount salesGlAccount,
            GlAccount returnsGlAccount, GlAccount cogsGlAccount, GlAccount returnsCogsGlAccount, BasePK createdBy) {
        var inventoryConditionGlAccount = InventoryConditionGlAccountFactory.getInstance().create(session,
                inventoryCondition, itemAccountingCategory, inventoryGlAccount, salesGlAccount, returnsGlAccount, cogsGlAccount,
                returnsCogsGlAccount, session.getStartTime(), Session.MAX_TIME_LONG);
        
        sendEvent(inventoryCondition.getPrimaryKey(), EventTypes.MODIFY, inventoryConditionGlAccount.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return inventoryConditionGlAccount;
    }
    
    private InventoryConditionGlAccount getInventoryConditionGlAccount(InventoryCondition inventoryCondition,
            ItemAccountingCategory itemAccountingCategory, EntityPermission entityPermission) {
        InventoryConditionGlAccount inventoryConditionGlAccount;
        
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

            var ps = InventoryConditionGlAccountFactory.getInstance().prepareStatement(query);
            
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
        List<InventoryConditionGlAccount> inventoryConditionGlAccounts;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionglaccounts, itemaccountingcategories, itemaccountingcategorydetails " +
                        "WHERE invcongla_invcon_inventoryconditionid = ? AND invcongla_thrutime = ? " +
                        "AND invcongla_iactgc_itemaccountingcategoryid = iactgc_itemaccountingcategoryid " +
                        "AND iactgc_lastdetailid = iactgcdt_itemaccountingcategorydetailid " +
                        "ORDER BY iactgcdt_sortorder, iactgcdt_itemaccountingcategoryname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionglaccounts " +
                        "WHERE invcongla_invcon_inventoryconditionid = ? AND invcongla_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = InventoryConditionGlAccountFactory.getInstance().prepareStatement(query);
            
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
        List<InventoryConditionGlAccount> inventoryConditionGlAccounts;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionglaccounts, inventoryconditions, inventoryconditiondetails " +
                        "WHERE invcongla_iactgc_itemaccountingcategoryid = ? AND invcongla_thrutime = ? " +
                        "AND invcongla_invcon_inventoryconditionid = invcon_inventoryconditionid " +
                        "AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "ORDER BY invcondt_sortorder, invcondt_inventoryconditionname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM inventoryconditionglaccounts " +
                        "WHERE invcongla_iactgc_itemaccountingcategoryid = ? AND invcongla_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = InventoryConditionGlAccountFactory.getInstance().prepareStatement(query);
            
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
            var inventoryConditionGlAccount = InventoryConditionGlAccountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryConditionGlAccountValue.getPrimaryKey());
            
            inventoryConditionGlAccount.setThruTime(session.getStartTime());
            inventoryConditionGlAccount.store();

            var inventoryConditionPK = inventoryConditionGlAccount.getInventoryConditionPK();
            var itemAccountingCategoryPK = inventoryConditionGlAccount.getItemAccountingCategoryPK();
            var inventoryGlAccountPK = inventoryConditionGlAccountValue.getInventoryGlAccountPK();
            var salesGlAccountPK = inventoryConditionGlAccountValue.getSalesGlAccountPK();
            var returnsGlAccountPK = inventoryConditionGlAccountValue.getReturnsGlAccountPK();
            var cogsGlAccountPK = inventoryConditionGlAccountValue.getCogsGlAccountPK();
            var returnsCogsGlAccountPK = inventoryConditionGlAccountValue.getReturnsCogsGlAccountPK();
            
            inventoryConditionGlAccount = InventoryConditionGlAccountFactory.getInstance().create(inventoryConditionPK,
                    itemAccountingCategoryPK, inventoryGlAccountPK, salesGlAccountPK, returnsGlAccountPK, cogsGlAccountPK,
                    returnsCogsGlAccountPK, session.getStartTime(), Session.MAX_TIME_LONG);
            
            sendEvent(inventoryConditionPK, EventTypes.MODIFY, inventoryConditionGlAccount.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public InventoryConditionGlAccountTransfer getInventoryConditionGlAccountTransfer(UserVisit userVisit, InventoryConditionGlAccount inventoryConditionGlAccount) {
        return inventoryConditionGlAccount == null? null: inventoryConditionGlAccountTransferCache.getTransfer(userVisit, inventoryConditionGlAccount);
    }
    
    public InventoryConditionGlAccountTransfer getInventoryConditionGlAccountTransfer(UserVisit userVisit, InventoryCondition inventoryCondition, ItemAccountingCategory itemAccountingCategory) {
        return getInventoryConditionGlAccountTransfer(userVisit, getInventoryConditionGlAccount(inventoryCondition, itemAccountingCategory));
    }
    
    public List<InventoryConditionGlAccountTransfer> getInventoryConditionGlAccountTransfersByInventoryCondition(UserVisit userVisit, InventoryCondition inventoryCondition) {
        var inventoryConditionGlAccounts = getInventoryConditionGlAccountsByInventoryCondition(inventoryCondition);
        List<InventoryConditionGlAccountTransfer> inventoryConditionGlAccountTransfers = new ArrayList<>(inventoryConditionGlAccounts.size());
        
        inventoryConditionGlAccounts.forEach((inventoryConditionGlAccount) ->
                inventoryConditionGlAccountTransfers.add(inventoryConditionGlAccountTransferCache.getTransfer(userVisit, inventoryConditionGlAccount))
        );
        
        return inventoryConditionGlAccountTransfers;
    }
    
    public void deleteInventoryConditionGlAccount(InventoryConditionGlAccount inventoryConditionGlAccount, BasePK deletedBy) {
        inventoryConditionGlAccount.setThruTime(session.getStartTime());
        
        sendEvent(inventoryConditionGlAccount.getInventoryConditionPK(), EventTypes.MODIFY, inventoryConditionGlAccount.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteInventoryConditionGlAccounts(List<InventoryConditionGlAccount> inventoryConditionGlAccounts, BasePK deletedBy) {
        inventoryConditionGlAccounts.forEach((inventoryConditionGlAccount) -> 
                deleteInventoryConditionGlAccount(inventoryConditionGlAccount, deletedBy)
        );
    }
    
    public void deleteInventoryConditionGlAccountsByInventoryCondition(InventoryCondition inventoryCondition, BasePK deletedBy) {
        deleteInventoryConditionGlAccounts(getInventoryConditionGlAccountsByInventoryConditionForUpdate(inventoryCondition), deletedBy);
    }
    
    public void deleteInventoryConditionGlAccountsByItemAccountingCategory(ItemAccountingCategory itemAccountingCategory, BasePK deletedBy) {
        deleteInventoryConditionGlAccounts(getInventoryConditionGlAccountsByItemAccountingCategoryForUpdate(itemAccountingCategory), deletedBy);
    }
    
    public void deleteInventoryConditionGlAccountByInventoryConditionAndItemAccountingCategory(InventoryCondition inventoryCondition, ItemAccountingCategory itemAccountingCategory, BasePK deletedBy) {
        var inventoryConditionGlAccount = getInventoryConditionGlAccountForUpdate(inventoryCondition, itemAccountingCategory);
        
        if(inventoryConditionGlAccount!= null) {
            deleteInventoryConditionGlAccount(inventoryConditionGlAccount, deletedBy);
        }
    }
    
    // --------------------------------------------------------------------------------
    //   Party Inventory Levels
    // --------------------------------------------------------------------------------
    
    public PartyInventoryLevel createPartyInventoryLevel(Party party, Item item, InventoryCondition inventoryCondition,
            Long minimumInventory, Long maximumInventory, Long reorderQuantity, BasePK createdBy) {
        var partyInventoryLevel = PartyInventoryLevelFactory.getInstance().create(party, item,
                inventoryCondition, minimumInventory, maximumInventory, reorderQuantity, session.getStartTime(),
                Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, partyInventoryLevel.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return partyInventoryLevel;
    }
    
    private PartyInventoryLevel getPartyInventoryLevel(Party party, Item item, InventoryCondition inventoryCondition,
            EntityPermission entityPermission) {
        PartyInventoryLevel partyInventoryLevel;
        
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

            var ps = PartyInventoryLevelFactory.getInstance().prepareStatement(query);
            
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
        List<PartyInventoryLevel> partyInventoryLevels;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyinventorylevels, items, itemdetails, inventoryconditions, inventoryconditiondetails " +
                        "WHERE parinvlvl_par_partyid = ? AND parinvlvl_thrutime = ? " +
                        "AND parinvlvl_itm_itemid = itm_itemid AND itm_activedetailid = itmdt_itemdetailid " +
                        "AND parinvlvl_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "ORDER BY itmdt_itemname, invcondt_sortorder, invcondt_inventoryconditionname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyinventorylevels " +
                        "WHERE parinvlvl_par_partyid = ? AND parinvlvl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyInventoryLevelFactory.getInstance().prepareStatement(query);
            
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
        List<PartyInventoryLevel> partyInventoryLevels;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyinventorylevels, parties, partydetails, partytypes, inventoryconditions, inventoryconditiondetails " +
                        "WHERE parinvlvl_itm_itemid = ? AND parinvlvl_thrutime = ? " +
                        "AND parinvlvl_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                        "AND pardt_ptyp_partytypeid = ptyp_partytypeid " +
                        "AND parinvlvl_invcon_inventoryconditionid = invcon_inventoryconditionid AND invcon_lastdetailid = invcondt_inventoryconditiondetailid " +
                        "ORDER BY ptyp_sortorder, ptyp_partytypename, pardt_partyname, invcondt_sortorder, invcondt_inventoryconditionname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyinventorylevels " +
                        "WHERE parinvlvl_itm_itemid = ? AND parinvlvl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyInventoryLevelFactory.getInstance().prepareStatement(query);
            
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
        List<PartyInventoryLevel> partyInventoryLevels;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM partyinventorylevels, parties, partydetails, partytypes, item, itemdetails " +
                        "WHERE parinvlvl_invcon_inventoryconditionid = ? AND parinvlvl_thrutime = ? " +
                        "AND parinvlvl_par_partyid = par_partyid AND par_activedetailid = pardt_partydetailid " +
                        "AND pardt_ptyp_partytypeid = ptyp_partytypeid " +
                        "AND parinvlvl_itm_itemid = itm_itemid AND itm_lastdetailid = itmdt_itemdetailid " +
                        "ORDER BY ptyp_sortorder, ptyp_partytypename, pardt_partyname, itmdt_itemname " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM partyinventorylevels " +
                        "WHERE parinvlvl_invcon_inventoryconditionid = ? AND parinvlvl_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = PartyInventoryLevelFactory.getInstance().prepareStatement(query);
            
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
            var partyInventoryLevel = PartyInventoryLevelFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     partyInventoryLevelValue.getPrimaryKey());
            
            partyInventoryLevel.setThruTime(session.getStartTime());
            partyInventoryLevel.store();

            var partyPK = partyInventoryLevel.getPartyPK(); // Not updated
            var itemPK = partyInventoryLevel.getItemPK(); // Not updated
            var inventoryConditionPK = partyInventoryLevel.getInventoryConditionPK(); // Not updated
            var minimumInventory = partyInventoryLevelValue.getMinimumInventory();
            var maximumInventory = partyInventoryLevelValue.getMaximumInventory();
            var reorderQuantity = partyInventoryLevelValue.getReorderQuantity();
            
            partyInventoryLevel = PartyInventoryLevelFactory.getInstance().create(partyPK, itemPK, inventoryConditionPK,
                    minimumInventory, maximumInventory, reorderQuantity, session.getStartTime(), Session.MAX_TIME_LONG);
            
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
    
    // --------------------------------------------------------------------------------
    //   Allocation Priorities
    // --------------------------------------------------------------------------------

    public AllocationPriority createAllocationPriority(String allocationPriorityName, Integer priority, Boolean isDefault, Integer sortPriority,
            BasePK createdBy) {
        var defaultAllocationPriority = getDefaultAllocationPriority();
        var defaultFound = defaultAllocationPriority != null;

        if(defaultFound && isDefault) {
            var defaultAllocationPriorityDetailValue = getDefaultAllocationPriorityDetailValueForUpdate();

            defaultAllocationPriorityDetailValue.setIsDefault(false);
            updateAllocationPriorityFromValue(defaultAllocationPriorityDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var allocationPriority = AllocationPriorityFactory.getInstance().create();
        var allocationPriorityDetail = AllocationPriorityDetailFactory.getInstance().create(allocationPriority, allocationPriorityName,
                priority, isDefault, sortPriority, session.getStartTime(), Session.MAX_TIME_LONG);

        // Convert to R/W
        allocationPriority = AllocationPriorityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                allocationPriority.getPrimaryKey());
        allocationPriority.setActiveDetail(allocationPriorityDetail);
        allocationPriority.setLastDetail(allocationPriorityDetail);
        allocationPriority.store();

        sendEvent(allocationPriority.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return allocationPriority;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.AllocationPriority */
    public AllocationPriority getAllocationPriorityByEntityInstance(final EntityInstance entityInstance,
            final EntityPermission entityPermission) {
        var pk = new AllocationPriorityPK(entityInstance.getEntityUniqueId());

        return AllocationPriorityFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public AllocationPriority getAllocationPriorityByEntityInstance(final EntityInstance entityInstance) {
        return getAllocationPriorityByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public AllocationPriority getAllocationPriorityByEntityInstanceForUpdate(final EntityInstance entityInstance) {
        return getAllocationPriorityByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countAllocationPriorities() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM allocationpriorities, allocationprioritydetails " +
                "WHERE allocpr_activedetailid = allocprdt_allocationprioritydetailid");
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

    public AllocationPriority getAllocationPriorityByName(String allocationPriorityName, EntityPermission entityPermission) {
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

    public AllocationPriority getDefaultAllocationPriority(EntityPermission entityPermission) {
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
                "ORDER BY allocprdt_sortorder, allocprdt_allocationpriorityname " +
                "_LIMIT_");
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
        return allocationPriorityTransferCache.getTransfer(userVisit, allocationPriority);
    }

    public List<AllocationPriorityTransfer> getAllocationPriorityTransfers(UserVisit userVisit, Collection<AllocationPriority> allocationPriorities) {
        var allocationPriorityTransfers = new ArrayList<AllocationPriorityTransfer>(allocationPriorities.size());

        allocationPriorities.forEach((allocationPriority) ->
                allocationPriorityTransfers.add(allocationPriorityTransferCache.getTransfer(userVisit, allocationPriority))
        );

        return allocationPriorityTransfers;
    }

    public List<AllocationPriorityTransfer> getAllocationPriorityTransfers(UserVisit userVisit) {
        return getAllocationPriorityTransfers(userVisit, getAllocationPriorities());
    }

    public AllocationPriorityChoicesBean getAllocationPriorityChoices(String defaultAllocationPriorityChoice, Language language, boolean allowNullChoice) {
        var allocationPriorities = getAllocationPriorities();
        var size = allocationPriorities.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultAllocationPriorityChoice == null) {
                defaultValue = "";
            }
        }

        for(var allocationPriority : allocationPriorities) {
            var allocationPriorityDetail = allocationPriority.getLastDetail();

            var label = getBestAllocationPriorityDescription(allocationPriority, language);
            var value = allocationPriorityDetail.getAllocationPriorityName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultAllocationPriorityChoice != null && defaultAllocationPriorityChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && allocationPriorityDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new AllocationPriorityChoicesBean(labels, values, defaultValue);
    }

    private void updateAllocationPriorityFromValue(AllocationPriorityDetailValue allocationPriorityDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(allocationPriorityDetailValue.hasBeenModified()) {
            var allocationPriority = AllocationPriorityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     allocationPriorityDetailValue.getAllocationPriorityPK());
            var allocationPriorityDetail = allocationPriority.getActiveDetailForUpdate();

            allocationPriorityDetail.setThruTime(session.getStartTime());
            allocationPriorityDetail.store();

            var allocationPriorityPK = allocationPriorityDetail.getAllocationPriorityPK(); // Not updated
            var allocationPriorityName = allocationPriorityDetailValue.getAllocationPriorityName();
            var priority = allocationPriorityDetailValue.getPriority();
            var isDefault = allocationPriorityDetailValue.getIsDefault();
            var sortOrder = allocationPriorityDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultAllocationPriority = getDefaultAllocationPriority();
                var defaultFound = defaultAllocationPriority != null && !defaultAllocationPriority.equals(allocationPriority);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultAllocationPriorityDetailValue = getDefaultAllocationPriorityDetailValueForUpdate();

                    defaultAllocationPriorityDetailValue.setIsDefault(false);
                    updateAllocationPriorityFromValue(defaultAllocationPriorityDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            allocationPriorityDetail = AllocationPriorityDetailFactory.getInstance().create(allocationPriorityPK, allocationPriorityName, priority, isDefault,
                    sortOrder, session.getStartTime(), Session.MAX_TIME_LONG);

            allocationPriority.setActiveDetail(allocationPriorityDetail);
            allocationPriority.setLastDetail(allocationPriorityDetail);

            sendEvent(allocationPriorityPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateAllocationPriorityFromValue(AllocationPriorityDetailValue allocationPriorityDetailValue, BasePK updatedBy) {
        updateAllocationPriorityFromValue(allocationPriorityDetailValue, true, updatedBy);
    }

    public void deleteAllocationPriority(AllocationPriority allocationPriority, BasePK deletedBy) {
        deleteAllocationPriorityDescriptionsByAllocationPriority(allocationPriority, deletedBy);

        var allocationPriorityDetail = allocationPriority.getLastDetailForUpdate();
        allocationPriorityDetail.setThruTime(session.getStartTime());
        allocationPriority.setActiveDetail(null);
        allocationPriority.store();

        // Check for default, and pick one if necessary
        var defaultAllocationPriority = getDefaultAllocationPriority();
        if(defaultAllocationPriority == null) {
            var allocationPriorities = getAllocationPrioritiesForUpdate();

            if(!allocationPriorities.isEmpty()) {
                var iter = allocationPriorities.iterator();
                if(iter.hasNext()) {
                    defaultAllocationPriority = iter.next();
                }
                var allocationPriorityDetailValue = Objects.requireNonNull(defaultAllocationPriority).getLastDetailForUpdate().getAllocationPriorityDetailValue().clone();

                allocationPriorityDetailValue.setIsDefault(true);
                updateAllocationPriorityFromValue(allocationPriorityDetailValue, false, deletedBy);
            }
        }

        sendEvent(allocationPriority.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Allocation Priority Descriptions
    // --------------------------------------------------------------------------------

    public AllocationPriorityDescription createAllocationPriorityDescription(AllocationPriority allocationPriority, Language language, String description, BasePK createdBy) {
        var allocationPriorityDescription = AllocationPriorityDescriptionFactory.getInstance().create(allocationPriority, language, description,
                session.getStartTime(), Session.MAX_TIME_LONG);

        sendEvent(allocationPriority.getPrimaryKey(), EventTypes.MODIFY, allocationPriorityDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

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
                "ORDER BY lang_sortallocation, lang_languageisoname " +
                "_LIMIT_");
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
        var allocationPriorityDescription = getAllocationPriorityDescription(allocationPriority, language);

        if(allocationPriorityDescription == null && !language.getIsDefault()) {
            allocationPriorityDescription = getAllocationPriorityDescription(allocationPriority, partyControl.getDefaultLanguage());
        }

        if(allocationPriorityDescription == null) {
            description = allocationPriority.getLastDetail().getAllocationPriorityName();
        } else {
            description = allocationPriorityDescription.getDescription();
        }

        return description;
    }

    public AllocationPriorityDescriptionTransfer getAllocationPriorityDescriptionTransfer(UserVisit userVisit, AllocationPriorityDescription allocationPriorityDescription) {
        return allocationPriorityDescriptionTransferCache.getTransfer(userVisit, allocationPriorityDescription);
    }

    public List<AllocationPriorityDescriptionTransfer> getAllocationPriorityDescriptionTransfersByAllocationPriority(UserVisit userVisit, AllocationPriority allocationPriority) {
        var allocationPriorityDescriptions = getAllocationPriorityDescriptionsByAllocationPriority(allocationPriority);
        List<AllocationPriorityDescriptionTransfer> allocationPriorityDescriptionTransfers = new ArrayList<>(allocationPriorityDescriptions.size());

        allocationPriorityDescriptions.forEach((allocationPriorityDescription) ->
                allocationPriorityDescriptionTransfers.add(allocationPriorityDescriptionTransferCache.getTransfer(userVisit, allocationPriorityDescription))
        );

        return allocationPriorityDescriptionTransfers;
    }

    public void updateAllocationPriorityDescriptionFromValue(AllocationPriorityDescriptionValue allocationPriorityDescriptionValue, BasePK updatedBy) {
        if(allocationPriorityDescriptionValue.hasBeenModified()) {
            var allocationPriorityDescription = AllocationPriorityDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    allocationPriorityDescriptionValue.getPrimaryKey());

            allocationPriorityDescription.setThruTime(session.getStartTime());
            allocationPriorityDescription.store();

            var allocationPriority = allocationPriorityDescription.getAllocationPriority();
            var language = allocationPriorityDescription.getLanguage();
            var description = allocationPriorityDescriptionValue.getDescription();

            allocationPriorityDescription = AllocationPriorityDescriptionFactory.getInstance().create(allocationPriority, language, description,
                    session.getStartTime(), Session.MAX_TIME_LONG);

            sendEvent(allocationPriority.getPrimaryKey(), EventTypes.MODIFY, allocationPriorityDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteAllocationPriorityDescription(AllocationPriorityDescription allocationPriorityDescription, BasePK deletedBy) {
        allocationPriorityDescription.setThruTime(session.getStartTime());

        sendEvent(allocationPriorityDescription.getAllocationPriorityPK(), EventTypes.MODIFY, allocationPriorityDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteAllocationPriorityDescriptionsByAllocationPriority(AllocationPriority allocationPriority, BasePK deletedBy) {
        var allocationPriorityDescriptions = getAllocationPriorityDescriptionsByAllocationPriorityForUpdate(allocationPriority);

        allocationPriorityDescriptions.forEach((allocationPriorityDescription) -> 
                deleteAllocationPriorityDescription(allocationPriorityDescription, deletedBy)
        );
    }

}
