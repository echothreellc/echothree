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
import com.echothree.model.control.inventory.common.choice.InventoryLocationGroupChoicesBean;
import com.echothree.model.control.inventory.common.choice.InventoryLocationGroupStatusChoicesBean;
import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupCapacityTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupDescriptionTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupVolumeTransfer;
import com.echothree.model.control.inventory.common.workflow.InventoryLocationGroupStatusConstants;
import com.echothree.model.control.inventory.server.transfer.InventoryLocationGroupCapacityTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryLocationGroupDescriptionTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryLocationGroupTransferCache;
import com.echothree.model.control.inventory.server.transfer.InventoryLocationGroupVolumeTransferCache;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryLocationGroupPK;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupCapacity;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupDescription;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupVolume;
import com.echothree.model.data.inventory.server.factory.InventoryLocationGroupCapacityFactory;
import com.echothree.model.data.inventory.server.factory.InventoryLocationGroupDescriptionFactory;
import com.echothree.model.data.inventory.server.factory.InventoryLocationGroupDetailFactory;
import com.echothree.model.data.inventory.server.factory.InventoryLocationGroupFactory;
import com.echothree.model.data.inventory.server.factory.InventoryLocationGroupVolumeFactory;
import com.echothree.model.data.inventory.server.value.InventoryLocationGroupCapacityValue;
import com.echothree.model.data.inventory.server.value.InventoryLocationGroupDescriptionValue;
import com.echothree.model.data.inventory.server.value.InventoryLocationGroupDetailValue;
import com.echothree.model.data.inventory.server.value.InventoryLocationGroupVolumeValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Inject;

@CommandScope
public class InventoryLocationGroupControl
        extends BaseModelControl {

    @Inject
    WarehouseControl warehouseControl;
    
    @Inject
    InventoryLocationGroupTransferCache inventoryLocationGroupTransferCache;

    @Inject
    InventoryLocationGroupDescriptionTransferCache inventoryLocationGroupDescriptionTransferCache;

    @Inject
    InventoryLocationGroupCapacityTransferCache inventoryLocationGroupCapacityTransferCache;

    @Inject
    InventoryLocationGroupVolumeTransferCache inventoryLocationGroupVolumeTransferCache;

    /** Creates a new instance of InventoryLocationGroupControl */
    protected InventoryLocationGroupControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Inventory Location Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected InventoryLocationGroupFactory inventoryLocationGroupFactory;

    @Inject
    protected InventoryLocationGroupDetailFactory inventoryLocationGroupDetailFactory;

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

        var inventoryLocationGroup = inventoryLocationGroupFactory.create();
        var inventoryLocationGroupDetail = inventoryLocationGroupDetailFactory.create(
                inventoryLocationGroup, warehouseParty, inventoryLocationGroupName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);
        
        // Convert to R/W
        inventoryLocationGroup = inventoryLocationGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE, inventoryLocationGroup.getPrimaryKey());
        inventoryLocationGroup.setActiveDetail(inventoryLocationGroupDetail);
        inventoryLocationGroup.setLastDetail(inventoryLocationGroupDetail);
        inventoryLocationGroup.store();
        
        sendEvent(inventoryLocationGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return inventoryLocationGroup;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.InventoryLocationGroup */
    public InventoryLocationGroup getInventoryLocationGroupByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new InventoryLocationGroupPK(entityInstance.getEntityUniqueId());

        return inventoryLocationGroupFactory.getEntityFromPK(entityPermission, pk);
    }

    public InventoryLocationGroup getInventoryLocationGroupByEntityInstance(EntityInstance entityInstance) {
        return getInventoryLocationGroupByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public InventoryLocationGroup getInventoryLocationGroupByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getInventoryLocationGroupByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countInventoryLocationGroupsByWarehouseParty(Party warehouseParty) {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM inventorylocationgroups, inventorylocationgroupdetails
                WHERE invlocgrp_activedetailid = invlocgrpdt_inventorylocationgroupdetailid
                AND invlocgrpdt_warehousepartyid = ?
                """, warehouseParty);
    }

    private InventoryLocationGroup getInventoryLocationGroupByName(Party warehouseParty, String inventoryLocationGroupName, EntityPermission entityPermission) {
        InventoryLocationGroup inventoryLocationGroup;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroups, inventorylocationgroupdetails
                        WHERE invlocgrp_activedetailid = invlocgrpdt_inventorylocationgroupdetailid
                        AND invlocgrpdt_warehousepartyid = ? AND invlocgrpdt_inventorylocationgroupname = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroups, inventorylocationgroupdetails
                        WHERE invlocgrp_activedetailid = invlocgrpdt_inventorylocationgroupdetailid
                        AND invlocgrpdt_warehousepartyid = ? AND invlocgrpdt_inventorylocationgroupname = ?
                        FOR UPDATE
                        """;
            }

            var ps = inventoryLocationGroupFactory.prepareStatement(query);
            
            ps.setLong(1, warehouseParty.getPrimaryKey().getEntityId());
            ps.setString(2, inventoryLocationGroupName);

            inventoryLocationGroup = inventoryLocationGroupFactory.getEntityFromQuery(entityPermission, ps);
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
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroups, inventorylocationgroupdetails
                        WHERE invlocgrp_activedetailid = invlocgrpdt_inventorylocationgroupdetailid
                        AND invlocgrpdt_warehousepartyid = ? AND invlocgrpdt_isdefault = 1
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroups, inventorylocationgroupdetails
                        WHERE invlocgrp_activedetailid = invlocgrpdt_inventorylocationgroupdetailid
                        AND invlocgrpdt_warehousepartyid = ? AND invlocgrpdt_isdefault = 1
                        FOR UPDATE
                        """;
            }

            var ps = inventoryLocationGroupFactory.prepareStatement(query);
            
            ps.setLong(1, warehouseParty.getPrimaryKey().getEntityId());

            inventoryLocationGroup = inventoryLocationGroupFactory.getEntityFromQuery(entityPermission, ps);
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
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroups, inventorylocationgroupdetails
                        WHERE invlocgrp_activedetailid = invlocgrpdt_inventorylocationgroupdetailid
                        AND invlocgrpdt_warehousepartyid = ?
                        ORDER BY invlocgrpdt_sortorder, invlocgrpdt_inventorylocationgroupname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroups, inventorylocationgroupdetails
                        WHERE invlocgrp_activedetailid = invlocgrpdt_inventorylocationgroupdetailid
                        AND invlocgrpdt_warehousepartyid = ?
                        FOR UPDATE
                        """;
            }

            var ps = inventoryLocationGroupFactory.prepareStatement(query);
            
            ps.setLong(1, warehouseParty.getPrimaryKey().getEntityId());

            inventoryLocationGroups = inventoryLocationGroupFactory.getEntitiesFromQuery(entityPermission, ps);
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
    
    public List<InventoryLocationGroupTransfer> getInventoryLocationGroupTransfers(UserVisit userVisit, Collection<InventoryLocationGroup> inventoryLocationGroups) {
        return inventoryLocationGroups.stream().map(inventoryLocationGroup ->
                inventoryLocationGroupTransferCache.getTransfer(userVisit, inventoryLocationGroup)).collect(Collectors.toCollection(() -> new ArrayList<>(inventoryLocationGroups.size())));
    }

    public List<InventoryLocationGroupTransfer> getInventoryLocationGroupTransfersByWarehouseParty(UserVisit userVisit, Party warehouseParty) {
        var inventoryLocationGroups = getInventoryLocationGroupsByWarehouseParty(warehouseParty);

        return getInventoryLocationGroupTransfers(userVisit, inventoryLocationGroups);
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
        var inventoryLocationGroup = inventoryLocationGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE, inventoryLocationGroupDetailValue.getInventoryLocationGroupPK());
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
        
        inventoryLocationGroupDetail = inventoryLocationGroupDetailFactory.create(inventoryLocationGroupPK,
                warehousePartyPK, inventoryLocationGroupName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
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
        
        warehouseControl.deleteLocationsByInventoryLocationGroup(inventoryLocationGroup, deletedBy);
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

    @Inject
    protected InventoryLocationGroupDescriptionFactory inventoryLocationGroupDescriptionFactory;

    public InventoryLocationGroupDescription createInventoryLocationGroupDescription(InventoryLocationGroup inventoryLocationGroup, Language language, String description, BasePK createdBy) {
        var inventoryLocationGroupDescription = inventoryLocationGroupDescriptionFactory.create(inventoryLocationGroup, language, description, session.getStartTime(),
                Session.MAX_TIME);
        
        sendEvent(inventoryLocationGroup.getPrimaryKey(), EventTypes.MODIFY, inventoryLocationGroupDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return inventoryLocationGroupDescription;
    }
    
    private InventoryLocationGroupDescription getInventoryLocationGroupDescription(InventoryLocationGroup inventoryLocationGroup, Language language, EntityPermission entityPermission) {
        InventoryLocationGroupDescription inventoryLocationGroupDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroupdescriptions
                        WHERE invlocgrpd_invlocgrp_inventorylocationgroupid = ? AND invlocgrpd_lang_languageid = ? AND invlocgrpd_thrutime = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroupdescriptions
                        WHERE invlocgrpd_invlocgrp_inventorylocationgroupid = ? AND invlocgrpd_lang_languageid = ? AND invlocgrpd_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = inventoryLocationGroupDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, inventoryLocationGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            inventoryLocationGroupDescription = inventoryLocationGroupDescriptionFactory.getEntityFromQuery(entityPermission, ps);
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
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroupdescriptions, languages
                        WHERE invlocgrpd_invlocgrp_inventorylocationgroupid = ? AND invlocgrpd_thrutime = ? AND invlocgrpd_lang_languageid = lang_languageid
                        ORDER BY lang_sortorder, lang_languageisoname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroupdescriptions
                        WHERE invlocgrpd_invlocgrp_inventorylocationgroupid = ? AND invlocgrpd_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = inventoryLocationGroupDescriptionFactory.prepareStatement(query);
            
            ps.setLong(1, inventoryLocationGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            inventoryLocationGroupDescriptions = inventoryLocationGroupDescriptionFactory.getEntitiesFromQuery(entityPermission, ps);
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
            var inventoryLocationGroupDescription = inventoryLocationGroupDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, inventoryLocationGroupDescriptionValue.getPrimaryKey());
            
            inventoryLocationGroupDescription.setThruTime(session.getStartTime());
            inventoryLocationGroupDescription.store();

            var inventoryLocationGroup = inventoryLocationGroupDescription.getInventoryLocationGroup();
            var language = inventoryLocationGroupDescription.getLanguage();
            var description = inventoryLocationGroupDescriptionValue.getDescription();
            
            inventoryLocationGroupDescription = inventoryLocationGroupDescriptionFactory.create(inventoryLocationGroup, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
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

    @Inject
    protected InventoryLocationGroupVolumeFactory inventoryLocationGroupVolumeFactory;

    public InventoryLocationGroupVolume createInventoryLocationGroupVolume(InventoryLocationGroup inventoryLocationGroup,
            Long height, Long width, Long depth, BasePK createdBy) {
        var inventoryLocationGroupVolume = inventoryLocationGroupVolumeFactory.create(inventoryLocationGroup, height, width, depth,
                session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(inventoryLocationGroup.getPrimaryKey(), EventTypes.MODIFY, inventoryLocationGroupVolume.getPrimaryKey(), null, createdBy);
        
        return inventoryLocationGroupVolume;
    }

    public long countInventoryLocationGroupVolumesByInventoryLocationGroup(final InventoryLocationGroup inventoryLocationGroup) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM inventorylocationgroupvolumes
                        WHERE invlocgrpvol_invlocgrp_inventorylocationgroupid = ? AND invlocgrpvol_thrutime = ?
                        """, inventoryLocationGroup, Session.MAX_TIME);
    }

    private InventoryLocationGroupVolume getInventoryLocationGroupVolume(InventoryLocationGroup inventoryLocationGroup, EntityPermission entityPermission) {
        InventoryLocationGroupVolume inventoryLocationGroupVolume;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroupvolumes
                        WHERE invlocgrpvol_invlocgrp_inventorylocationgroupid = ? AND invlocgrpvol_thrutime = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroupvolumes
                        WHERE invlocgrpvol_invlocgrp_inventorylocationgroupid = ? AND invlocgrpvol_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = inventoryLocationGroupVolumeFactory.prepareStatement(query);
            
            ps.setLong(1, inventoryLocationGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            inventoryLocationGroupVolume = inventoryLocationGroupVolumeFactory.getEntityFromQuery(entityPermission, ps);
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
            var inventoryInventoryLocationGroupGroupVolume = inventoryLocationGroupVolumeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryInventoryLocationGroupGroupVolumeValue.getPrimaryKey());
            
            inventoryInventoryLocationGroupGroupVolume.setThruTime(session.getStartTime());
            inventoryInventoryLocationGroupGroupVolume.store();

            var inventoryInventoryLocationGroupGroupPK = inventoryInventoryLocationGroupGroupVolume.getInventoryLocationGroupPK(); // Not updated
            var height = inventoryInventoryLocationGroupGroupVolumeValue.getHeight();
            var width = inventoryInventoryLocationGroupGroupVolumeValue.getWidth();
            var depth = inventoryInventoryLocationGroupGroupVolumeValue.getDepth();
            
            inventoryInventoryLocationGroupGroupVolume = inventoryLocationGroupVolumeFactory.create(inventoryInventoryLocationGroupGroupPK, height,
                    width, depth, session.getStartTime(), Session.MAX_TIME);
            
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

    @Inject
    protected InventoryLocationGroupCapacityFactory inventoryLocationGroupCapacityFactory;

    public InventoryLocationGroupCapacity createInventoryLocationGroupCapacity(InventoryLocationGroup inventoryLocationGroupGroup,
            UnitOfMeasureType unitOfMeasureType, Long capacity, BasePK createdBy) {
        var inventoryLocationGroupGroupCapacity = inventoryLocationGroupCapacityFactory.create(inventoryLocationGroupGroup,
                unitOfMeasureType, capacity, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(inventoryLocationGroupGroup.getPrimaryKey(), EventTypes.MODIFY, inventoryLocationGroupGroupCapacity.getPrimaryKey(), null, createdBy);
        
        return inventoryLocationGroupGroupCapacity;
    }

    public long countInventoryLocationGroupCapacitiesByInventoryLocationGroup(final InventoryLocationGroup inventoryLocationGroup) {
        return session.queryForLong("""
                        SELECT COUNT(*)
                        FROM inventorylocationgroupcapacities
                        WHERE invlocgrpcap_invlocgrp_inventorylocationgroupid = ? AND invlocgrpcap_thrutime = ?
                        """, inventoryLocationGroup, Session.MAX_TIME);
    }

    private List<InventoryLocationGroupCapacity> getInventoryLocationGroupCapacitiesByInventoryLocationGroup(InventoryLocationGroup inventoryInventoryLocationGroupGroup, EntityPermission entityPermission) {
        List<InventoryLocationGroupCapacity> inventoryInventoryLocationGroupGroupCapacities;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroupcapacities, unitofmeasuretypedetails, unitofmeasurekinddetails
                        WHERE invlocgrpcap_invlocgrp_inventorylocationgroupid = ? AND invlocgrpcap_thrutime = ?
                        AND invlocgrpcap_uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ?
                        AND uomtdt_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ?
                        ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename, uomkdt_sortorder, uomkdt_unitofmeasurekindname
                        _LIMIT_
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroupcapacities
                        WHERE invlocgrpcap_invlocgrp_inventorylocationgroupid = ? AND invlocgrpcap_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = inventoryLocationGroupCapacityFactory.prepareStatement(query);
            
            ps.setLong(1, inventoryInventoryLocationGroupGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
                ps.setLong(4, Session.MAX_TIME);
            }
            
            inventoryInventoryLocationGroupGroupCapacities = inventoryLocationGroupCapacityFactory.getEntitiesFromQuery(entityPermission, ps);
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
    
    public InventoryLocationGroupCapacity getInventoryLocationGroupCapacity(InventoryLocationGroup inventoryInventoryLocationGroupGroup, UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        InventoryLocationGroupCapacity inventoryInventoryLocationGroupGroupCapacity;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroupcapacities
                        WHERE invlocgrpcap_invlocgrp_inventorylocationgroupid = ? AND invlocgrpcap_uomt_unitofmeasuretypeid = ?
                        AND invlocgrpcap_thrutime = ?
                        """;
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = """
                        SELECT _ALL_
                        FROM inventorylocationgroupcapacities
                        WHERE invlocgrpcap_invlocgrp_inventorylocationgroupid = ? AND invlocgrpcap_uomt_unitofmeasuretypeid = ?
                        AND invlocgrpcap_thrutime = ?
                        FOR UPDATE
                        """;
            }

            var ps = inventoryLocationGroupCapacityFactory.prepareStatement(query);
            
            ps.setLong(1, inventoryInventoryLocationGroupGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            inventoryInventoryLocationGroupGroupCapacity = inventoryLocationGroupCapacityFactory.getEntityFromQuery(entityPermission, ps);
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
            var inventoryLocationGroupCapacity = inventoryLocationGroupCapacityFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     inventoryLocationGroupCapacityValue.getPrimaryKey());
            
            inventoryLocationGroupCapacity.setThruTime(session.getStartTime());
            inventoryLocationGroupCapacity.store();

            var unitOfMeasureTypePK = inventoryLocationGroupCapacity.getUnitOfMeasureTypePK(); // Not updated
            var inventoryLocationGroupPK = inventoryLocationGroupCapacity.getInventoryLocationGroupPK(); // Not updated
            var capacity = inventoryLocationGroupCapacityValue.getCapacity();
            
            inventoryLocationGroupCapacity = inventoryLocationGroupCapacityFactory.create(inventoryLocationGroupPK, unitOfMeasureTypePK, capacity,
                    session.getStartTime(), Session.MAX_TIME);
            
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
    

}
