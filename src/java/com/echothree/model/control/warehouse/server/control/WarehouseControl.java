// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

package com.echothree.model.control.warehouse.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.warehouse.common.choice.LocationChoicesBean;
import com.echothree.model.control.warehouse.common.choice.LocationStatusChoicesBean;
import com.echothree.model.control.warehouse.common.choice.LocationTypeChoicesBean;
import com.echothree.model.control.warehouse.common.choice.LocationUseTypeChoicesBean;
import com.echothree.model.control.warehouse.common.choice.WarehouseChoicesBean;
import com.echothree.model.control.warehouse.common.transfer.LocationCapacityTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationDescriptionTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationNameElementDescriptionTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationNameElementTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationTypeDescriptionTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationTypeTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationUseTypeTransfer;
import com.echothree.model.control.warehouse.common.transfer.LocationVolumeTransfer;
import com.echothree.model.control.warehouse.common.transfer.WarehouseTransfer;
import com.echothree.model.control.warehouse.common.workflow.LocationStatusConstants;
import com.echothree.model.control.warehouse.server.transfer.LocationCapacityTransferCache;
import com.echothree.model.control.warehouse.server.transfer.LocationNameElementTransferCache;
import com.echothree.model.control.warehouse.server.transfer.WarehouseTransferCaches;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.inventory.common.pk.InventoryLocationGroupPK;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroup;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.party.server.entity.PartyGroup;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureTypePK;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.warehouse.common.pk.LocationNameElementPK;
import com.echothree.model.data.warehouse.common.pk.LocationPK;
import com.echothree.model.data.warehouse.common.pk.LocationTypePK;
import com.echothree.model.data.warehouse.common.pk.LocationUseTypePK;
import com.echothree.model.data.warehouse.server.entity.Location;
import com.echothree.model.data.warehouse.server.entity.LocationCapacity;
import com.echothree.model.data.warehouse.server.entity.LocationDescription;
import com.echothree.model.data.warehouse.server.entity.LocationDetail;
import com.echothree.model.data.warehouse.server.entity.LocationNameElement;
import com.echothree.model.data.warehouse.server.entity.LocationNameElementDescription;
import com.echothree.model.data.warehouse.server.entity.LocationNameElementDetail;
import com.echothree.model.data.warehouse.server.entity.LocationType;
import com.echothree.model.data.warehouse.server.entity.LocationTypeDescription;
import com.echothree.model.data.warehouse.server.entity.LocationTypeDetail;
import com.echothree.model.data.warehouse.server.entity.LocationUseType;
import com.echothree.model.data.warehouse.server.entity.LocationUseTypeDescription;
import com.echothree.model.data.warehouse.server.entity.LocationVolume;
import com.echothree.model.data.warehouse.server.entity.Warehouse;
import com.echothree.model.data.warehouse.server.factory.LocationCapacityFactory;
import com.echothree.model.data.warehouse.server.factory.LocationDescriptionFactory;
import com.echothree.model.data.warehouse.server.factory.LocationDetailFactory;
import com.echothree.model.data.warehouse.server.factory.LocationFactory;
import com.echothree.model.data.warehouse.server.factory.LocationNameElementDescriptionFactory;
import com.echothree.model.data.warehouse.server.factory.LocationNameElementDetailFactory;
import com.echothree.model.data.warehouse.server.factory.LocationNameElementFactory;
import com.echothree.model.data.warehouse.server.factory.LocationTypeDescriptionFactory;
import com.echothree.model.data.warehouse.server.factory.LocationTypeDetailFactory;
import com.echothree.model.data.warehouse.server.factory.LocationTypeFactory;
import com.echothree.model.data.warehouse.server.factory.LocationUseTypeDescriptionFactory;
import com.echothree.model.data.warehouse.server.factory.LocationUseTypeFactory;
import com.echothree.model.data.warehouse.server.factory.LocationVolumeFactory;
import com.echothree.model.data.warehouse.server.factory.WarehouseFactory;
import com.echothree.model.data.warehouse.server.value.LocationCapacityValue;
import com.echothree.model.data.warehouse.server.value.LocationDescriptionValue;
import com.echothree.model.data.warehouse.server.value.LocationDetailValue;
import com.echothree.model.data.warehouse.server.value.LocationNameElementDescriptionValue;
import com.echothree.model.data.warehouse.server.value.LocationNameElementDetailValue;
import com.echothree.model.data.warehouse.server.value.LocationNameElementValue;
import com.echothree.model.data.warehouse.server.value.LocationTypeDescriptionValue;
import com.echothree.model.data.warehouse.server.value.LocationTypeDetailValue;
import com.echothree.model.data.warehouse.server.value.LocationVolumeValue;
import com.echothree.model.data.warehouse.server.value.WarehouseValue;
import com.echothree.model.data.workflow.server.entity.WorkflowDestination;
import com.echothree.model.data.workflow.server.entity.WorkflowEntityStatus;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class WarehouseControl
        extends BaseModelControl {
    
    /** Creates a new instance of WarehouseControl */
    public WarehouseControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Warehouse Transfer Caches
    // --------------------------------------------------------------------------------
    
    private WarehouseTransferCaches warehouseTransferCaches;
    
    public WarehouseTransferCaches getWarehouseTransferCaches(UserVisit userVisit) {
        if(warehouseTransferCaches == null) {
            warehouseTransferCaches = new WarehouseTransferCaches(userVisit, this);
        }
        
        return warehouseTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Warehouses
    // --------------------------------------------------------------------------------
    
    public Warehouse createWarehouse(Party party, String warehouseName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Warehouse defaultWarehouse = getDefaultWarehouse();
        boolean defaultFound = defaultWarehouse != null;
        
        if(defaultFound && isDefault) {
            WarehouseValue defaultWarehouseValue = getDefaultWarehouseValueForUpdate();
            
            defaultWarehouseValue.setIsDefault(Boolean.FALSE);
            updateWarehouseFromValue(defaultWarehouseValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        Warehouse warehouse = WarehouseFactory.getInstance().create(party, warehouseName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(party.getPrimaryKey(), EventTypes.MODIFY, warehouse.getPrimaryKey(), null, createdBy);
        
        return warehouse;
    }

    public long countWarehouses() {
        return session.queryForLong("""
                SELECT COUNT(*)
                FROM warehouses
                WHERE whse_thrutime = ?
                """, Session.MAX_TIME);
    }

    public Warehouse getWarehouse(Party party, EntityPermission entityPermission) {
        Warehouse warehouse;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM warehouses " +
                        "WHERE whse_par_partyid = ? AND whse_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM warehouses " +
                        "WHERE whse_par_partyid = ? AND whse_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WarehouseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, party.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            warehouse = WarehouseFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return warehouse;
    }
    
    public Warehouse getWarehouse(Party party) {
        return getWarehouse(party, EntityPermission.READ_ONLY);
    }
    
    public Warehouse getWarehouseForUpdate(Party party) {
        return getWarehouse(party, EntityPermission.READ_WRITE);
    }
    
    public WarehouseValue getWarehouseValueUpdate(Party party) {
        return getWarehouseForUpdate(party).getWarehouseValue().clone();
    }
    
    public Warehouse getWarehouseByName(String warehouseName, EntityPermission entityPermission) {
        Warehouse warehouse;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM warehouses " +
                        "WHERE whse_warehousename = ? AND whse_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM warehouses " +
                        "WHERE whse_warehousename = ? AND whse_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WarehouseFactory.getInstance().prepareStatement(query);
            
            ps.setString(1, warehouseName);
            ps.setLong(2, Session.MAX_TIME);
            
            warehouse = WarehouseFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return warehouse;
    }
    
    public Warehouse getWarehouseByName(String warehouseName) {
        return getWarehouseByName(warehouseName, EntityPermission.READ_ONLY);
    }
    
    public Warehouse getWarehouseByNameForUpdate(String warehouseName) {
        return getWarehouseByName(warehouseName, EntityPermission.READ_WRITE);
    }
    
    private Warehouse getDefaultWarehouse(EntityPermission entityPermission) {
        Warehouse warehouse;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM warehouses " +
                        "WHERE whse_isdefault = 1 AND whse_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM warehouses " +
                        "WHERE whse_isdefault = 1 AND whse_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WarehouseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            
            warehouse = WarehouseFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return warehouse;
    }
    
    public Warehouse getDefaultWarehouse() {
        return getDefaultWarehouse(EntityPermission.READ_ONLY);
    }
    
    public Warehouse getDefaultWarehouseForUpdate() {
        return getDefaultWarehouse(EntityPermission.READ_WRITE);
    }
    
    public WarehouseValue getDefaultWarehouseValueForUpdate() {
        return getDefaultWarehouseForUpdate().getWarehouseValue().clone();
    }
    
    private List<Warehouse> getWarehouses(EntityPermission entityPermission) {
        List<Warehouse> warehouses;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM warehouses " +
                        "WHERE whse_thrutime = ? " +
                        "ORDER BY whse_sortorder, whse_warehousename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM warehouses " +
                        "WHERE whse_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = WarehouseFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, Session.MAX_TIME);
            
            warehouses = WarehouseFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return warehouses;
    }
    
    public List<Warehouse> getWarehouses() {
        return getWarehouses(EntityPermission.READ_ONLY);
    }
    
    public List<Warehouse> getWarehousesForUpdate() {
        return getWarehouses(EntityPermission.READ_WRITE);
    }
    
    public WarehouseValue getWarehouseValue(Warehouse warehouse) {
        return warehouse == null? null: warehouse.getWarehouseValue().clone();
    }
    
    public WarehouseValue getWarehouseValueByNameForUpdate(String warehouseName) {
        return getWarehouseValue(getWarehouseByNameForUpdate(warehouseName));
    }
    
    public WarehouseChoicesBean getWarehouseChoices(String defaultWarehouseChoice, boolean allowNullChoice) {
        List<Warehouse> partyCompanies = getWarehouses();
        var size = partyCompanies.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultWarehouseChoice == null) {
                defaultValue = "";
            }
        }

        for(var warehouse : partyCompanies) {
            PartyGroup partyGroup = getPartyControl().getPartyGroup(warehouse.getParty());

            var label = partyGroup.getName();
            var value = warehouse.getWarehouseName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultWarehouseChoice != null && defaultWarehouseChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && warehouse.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new WarehouseChoicesBean(labels, values, defaultValue);
    }

    public WarehouseTransfer getWarehouseTransfer(UserVisit userVisit, Warehouse warehouse) {
        return getWarehouseTransferCaches(userVisit).getWarehouseTransferCache().getWarehouseTransfer(warehouse);
    }
    
    public WarehouseTransfer getWarehouseTransfer(UserVisit userVisit, Party party) {
        return getWarehouseTransfer(userVisit, getWarehouse(party));
    }

    public List<WarehouseTransfer> getWarehouseTransfers(UserVisit userVisit, Collection<Warehouse> warehouses) {
        List<WarehouseTransfer> warehouseTransfers = new ArrayList<>(warehouses.size());

        warehouses.forEach((warehouse) -> {
            warehouseTransfers.add(getWarehouseTransferCaches(userVisit).getWarehouseTransferCache().getWarehouseTransfer(warehouse));
        });

        return warehouseTransfers;
    }

    public List<WarehouseTransfer> getWarehouseTransfers(UserVisit userVisit) {
        return getWarehouseTransfers(userVisit, getWarehouses());
    }

    private void updateWarehouseFromValue(WarehouseValue warehouseValue, boolean checkDefault, BasePK updatedBy) {
        if(warehouseValue.hasBeenModified()) {
            Warehouse warehouse = WarehouseFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    warehouseValue.getPrimaryKey());
            
            warehouse.setThruTime(session.START_TIME_LONG);
            warehouse.store();
            
            PartyPK partyPK = warehouse.getPartyPK();
            String warehouseName = warehouseValue.getWarehouseName();
            Boolean isDefault = warehouseValue.getIsDefault();
            Integer sortOrder = warehouseValue.getSortOrder();
            
            if(checkDefault) {
                Warehouse defaultWarehouse = getDefaultWarehouse();
                boolean defaultFound = defaultWarehouse != null && !defaultWarehouse.equals(warehouse);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    WarehouseValue defaultWarehouseValue = getDefaultWarehouseValueForUpdate();
                    
                    defaultWarehouseValue.setIsDefault(Boolean.FALSE);
                    updateWarehouseFromValue(defaultWarehouseValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            warehouse = WarehouseFactory.getInstance().create(partyPK, warehouseName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(partyPK, EventTypes.MODIFY, warehouse.getPrimaryKey(), null, updatedBy);
        }
    }
    
    public void updateWarehouseFromValue(WarehouseValue warehouseValue, BasePK updatedBy) {
        updateWarehouseFromValue(warehouseValue, true, updatedBy);
    }
    
    public void deleteWarehouse(Warehouse warehouse, BasePK deletedBy) {
        var inventoryControl = Session.getModelController(InventoryControl.class);
        Party party = warehouse.getPartyForUpdate();
        
        deleteLocationsByWarehouseParty(party, deletedBy);
        deleteLocationTypesByWarehouseParty(party, deletedBy);
        inventoryControl.deleteInventoryLocationGroupsByWarehouseParty(party, deletedBy);
        
        getPartyControl().deleteParty(party, deletedBy);
        
        warehouse.setThruTime(session.START_TIME_LONG);
        warehouse.store();
        
        // Check for default, and pick one if necessary
        Warehouse defaultWarehouse = getDefaultWarehouse();
        if(defaultWarehouse == null) {
            List<Warehouse> warehouses = getWarehousesForUpdate();
            
            if(!warehouses.isEmpty()) {
                Iterator<Warehouse> iter = warehouses.iterator();
                if(iter.hasNext()) {
                    defaultWarehouse = iter.next();
                }
                
                WarehouseValue defaultWarehouseValue = defaultWarehouse.getWarehouseValue();
                
                defaultWarehouseValue.setIsDefault(Boolean.TRUE);
                updateWarehouseFromValue(defaultWarehouseValue, false, deletedBy);
            }
        }
        
        sendEvent(warehouse.getPartyPK(), EventTypes.MODIFY, warehouse.getPrimaryKey(), null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Location Use Types
    // --------------------------------------------------------------------------------
    
    public LocationUseType createLocationUseType(String locationUseTypeName, Boolean allowMultiple, Boolean isDefault, Integer sortOrder) {
        return LocationUseTypeFactory.getInstance().create(locationUseTypeName, allowMultiple, isDefault, sortOrder);
    }
    
    public List<LocationUseType> getLocationUseTypes() {
        PreparedStatement ps = LocationUseTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM locationusetypes " +
                "ORDER BY locutyp_sortorder, locutyp_locationusetypename");
        
        return LocationUseTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public LocationUseType getLocationUseTypeByName(String locationUseTypeName) {
        LocationUseType locationUseType;
        
        try {
            PreparedStatement ps = LocationUseTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM locationusetypes " +
                    "WHERE locutyp_locationusetypename = ?");
            
            ps.setString(1, locationUseTypeName);
            
            locationUseType = LocationUseTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationUseType;
    }
    
    public LocationUseTypeTransfer getLocationUseTypeTransfer(UserVisit userVisit, LocationUseType locationUseType) {
        return getWarehouseTransferCaches(userVisit).getLocationUseTypeTransferCache().getLocationUseTypeTransfer(locationUseType);
    }
    
    public LocationUseTypeChoicesBean getLocationUseTypeChoices(String defaultLocationUseTypeChoice, Language language,
            boolean allowNullChoice) {
        List<LocationUseType> locationUseTypes = getLocationUseTypes();
        var size = locationUseTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultLocationUseTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var locationUseType : locationUseTypes) {
            var label = getBestLocationUseTypeDescription(locationUseType, language);
            var value = locationUseType.getLocationUseTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultLocationUseTypeChoice != null && defaultLocationUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && locationUseType.getIsDefault()))
                defaultValue = value;
        }
        
        return new LocationUseTypeChoicesBean(labels, values, defaultValue);
    }
    
    // --------------------------------------------------------------------------------
    //   Location Use Type Descriptions
    // --------------------------------------------------------------------------------
    
    public LocationUseTypeDescription createLocationUseTypeDescription(LocationUseType locationUseType, Language language, String description) {
        return LocationUseTypeDescriptionFactory.getInstance().create(locationUseType, language, description);
    }
    
    public LocationUseTypeDescription getLocationUseTypeDescription(LocationUseType locationUseType, Language language) {
        LocationUseTypeDescription locationUseTypeDescription;
        
        try {
            PreparedStatement ps = LocationUseTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM locationusetypedescriptions " +
                    "WHERE locutypd_locutyp_locationusetypeid = ? AND locutypd_lang_languageid = ?");
            
            ps.setLong(1, locationUseType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            locationUseTypeDescription = LocationUseTypeDescriptionFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationUseTypeDescription;
    }
    
    public String getBestLocationUseTypeDescription(LocationUseType locationUseType, Language language) {
        String description;
        LocationUseTypeDescription locationUseTypeDescription = getLocationUseTypeDescription(locationUseType, language);
        
        if(locationUseTypeDescription == null && !language.getIsDefault()) {
            locationUseTypeDescription = getLocationUseTypeDescription(locationUseType, getPartyControl().getDefaultLanguage());
        }
        
        if(locationUseTypeDescription == null) {
            description = locationUseType.getLocationUseTypeName();
        } else {
            description = locationUseTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Location Types
    // --------------------------------------------------------------------------------
    
    public LocationType createLocationType(Party warehouseParty, String locationTypeName, Boolean isDefault, Integer sortOrder,
            BasePK createdBy) {
        LocationType locationType = LocationTypeFactory.getInstance().create();
        LocationTypeDetail locationTypeDetail = LocationTypeDetailFactory.getInstance().create(locationType, warehouseParty,
                locationTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        locationType = LocationTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, locationType.getPrimaryKey());
        locationType.setActiveDetail(locationTypeDetail);
        locationType.setLastDetail(locationTypeDetail);
        locationType.store();
        
        sendEvent(locationType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return locationType;
    }
    
    private LocationType getLocationTypeByName(Party warehouseParty, String locationTypeName, EntityPermission entityPermission) {
        LocationType locationType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locationtypes, locationtypedetails " +
                        "WHERE loctyp_locationtypeid = loctypdt_loctyp_locationtypeid AND loctypdt_warehousepartyid = ? " +
                        "AND loctypdt_locationtypename = ? AND loctypdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locationtypes, locationtypedetails " +
                        "WHERE loctyp_locationtypeid = loctypdt_loctyp_locationtypeid AND loctypdt_warehousepartyid = ? " +
                        "AND loctypdt_locationtypename = ? AND loctypdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, warehouseParty.getPrimaryKey().getEntityId());
            ps.setString(2, locationTypeName);
            ps.setLong(3, Session.MAX_TIME);
            
            locationType = LocationTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationType;
    }
    
    public LocationType getLocationTypeByName(Party warehouseParty, String locationTypeName) {
        return getLocationTypeByName(warehouseParty, locationTypeName, EntityPermission.READ_ONLY);
    }
    
    public LocationType getLocationTypeByNameForUpdate(Party warehouseParty, String locationTypeName) {
        return getLocationTypeByName(warehouseParty, locationTypeName, EntityPermission.READ_WRITE);
    }
    
    public LocationTypeDetailValue getLocationTypeDetailValueForUpdate(LocationType locationType) {
        return locationType == null? null: locationType.getLastDetailForUpdate().getLocationTypeDetailValue().clone();
    }
    
    public LocationTypeDetailValue getLocationTypeDetailValueByNameForUpdate(Party warehouseParty, String locationTypeName) {
        return getLocationTypeDetailValueForUpdate(getLocationTypeByNameForUpdate(warehouseParty, locationTypeName));
    }
    
    private LocationType getDefaultLocationType(Party warehouseParty, EntityPermission entityPermission) {
        LocationType locationType;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locationtypes, locationtypedetails " +
                        "WHERE loctyp_locationtypeid = loctypdt_loctyp_locationtypeid AND loctypdt_warehousepartyid = ? " +
                        "AND loctypdt_isdefault = 1 AND loctypdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locationtypes, locationtypedetails " +
                        "WHERE loctyp_locationtypeid = loctypdt_loctyp_locationtypeid AND loctypdt_warehousepartyid = ? " +
                        "AND loctypdt_isdefault = 1 AND loctypdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, warehouseParty.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            locationType = LocationTypeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationType;
    }
    
    public LocationType getDefaultLocationType(Party warehouseParty) {
        return getDefaultLocationType(warehouseParty, EntityPermission.READ_ONLY);
    }
    
    public LocationType getDefaultLocationTypeForUpdate(Party warehouseParty) {
        return getDefaultLocationType(warehouseParty, EntityPermission.READ_WRITE);
    }
    
    public LocationTypeDetailValue getDefaultLocationTypeDetailValueForUpdate(Party warehouseParty) {
        return getDefaultLocationTypeForUpdate(warehouseParty).getLastDetailForUpdate().getLocationTypeDetailValue().clone();
    }
    
    private List<LocationType> getLocationTypesByWarehouseParty(Party warehouseParty, EntityPermission entityPermission) {
        List<LocationType> locationTypes;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locationtypes, locationtypedetails " +
                        "WHERE loctyp_locationtypeid = loctypdt_loctyp_locationtypeid AND loctypdt_warehousepartyid = ? " +
                        "AND loctypdt_thrutime = ? " +
                        "ORDER BY loctypdt_sortorder, loctypdt_locationtypename";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locationtypes, locationtypedetails " +
                        "WHERE loctyp_locationtypeid = loctypdt_loctyp_locationtypeid AND loctypdt_warehousepartyid = ? " +
                        "AND loctypdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationTypeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, warehouseParty.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            locationTypes = LocationTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationTypes;
    }
    
    public List<LocationType> getLocationTypesByWarehouseParty(Party warehouseParty) {
        return getLocationTypesByWarehouseParty(warehouseParty, EntityPermission.READ_ONLY);
    }
    
    public List<LocationType> getLocationTypesByWarehousePartyForUpdate(Party warehouseParty) {
        return getLocationTypesByWarehouseParty(warehouseParty, EntityPermission.READ_WRITE);
    }
    
    public LocationTypeTransfer getLocationTypeTransfer(UserVisit userVisit, LocationType locationType) {
        return getWarehouseTransferCaches(userVisit).getLocationTypeTransferCache().getLocationTypeTransfer(locationType);
    }
    
    public List<LocationTypeTransfer> getLocationTypeTransfersByWarehouseParty(UserVisit userVisit, Party warehouseParty) {
        List<LocationType> locationTypes = getLocationTypesByWarehouseParty(warehouseParty);
        List<LocationTypeTransfer> locationTypeTransfers = null;
        
        if(locationTypes != null) {
            locationTypeTransfers = new ArrayList<>(locationTypes.size());
            
            for(var locationType : locationTypes) {
                locationTypeTransfers.add(getWarehouseTransferCaches(userVisit).getLocationTypeTransferCache().getLocationTypeTransfer(locationType));
            }
        }
        
        return locationTypeTransfers;
    }
    
    public LocationTypeChoicesBean getLocationTypeChoicesByWarehouseParty(String defaultLocationTypeChoice, Language language,
            boolean allowNullChoice, Party warehouseParty) {
        List<LocationType> locationTypes = getLocationTypesByWarehouseParty(warehouseParty);
        var size = locationTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        Iterator iter = locationTypes.iterator();
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultLocationTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        while(iter.hasNext()) {
            LocationType locationType = (LocationType)iter.next();
            LocationTypeDetail locationTypeDetail = locationType.getLastDetail();
            
            var label = getBestLocationTypeDescription(locationType, language);
            var value = locationTypeDetail.getLocationTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultLocationTypeChoice != null && defaultLocationTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && locationTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new LocationTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateLocationTypeFromValue(LocationTypeDetailValue locationTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(locationTypeDetailValue.hasBeenModified()) {
            LocationType locationType = LocationTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, locationTypeDetailValue.getLocationTypePK());
            LocationTypeDetail locationTypeDetail = locationType.getActiveDetailForUpdate();
            
            locationTypeDetail.setThruTime(session.START_TIME_LONG);
            locationTypeDetail.store();
            
            LocationTypePK locationTypePK = locationTypeDetail.getLocationTypePK();
            Party warehouseParty = locationTypeDetail.getWarehouseParty();
            PartyPK warehousePartyPK = locationTypeDetail.getWarehousePartyPK();
            String locationTypeName = locationTypeDetailValue.getLocationTypeName();
            Boolean isDefault = locationTypeDetailValue.getIsDefault();
            Integer sortOrder = locationTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                LocationType defaultLocationType = getDefaultLocationType(warehouseParty);
                boolean defaultFound = defaultLocationType != null && !defaultLocationType.equals(locationType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    LocationTypeDetailValue defaultLocationTypeDetailValue = getDefaultLocationTypeDetailValueForUpdate(warehouseParty);
                    
                    defaultLocationTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateLocationTypeFromValue(defaultLocationTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            locationTypeDetail = LocationTypeDetailFactory.getInstance().create(locationTypePK, warehousePartyPK,
                    locationTypeName, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            locationType.setActiveDetail(locationTypeDetail);
            locationType.setLastDetail(locationTypeDetail);
            
            sendEvent(locationTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateLocationTypeFromValue(LocationTypeDetailValue locationTypeDetailValue, BasePK updatedBy) {
        updateLocationTypeFromValue(locationTypeDetailValue, true, updatedBy);
    }
    
    private void deleteLocationType(LocationType locationType, BasePK deletedBy, boolean adjustDefault) {
        deleteLocationNameElementsByLocationType(locationType, deletedBy);
        deleteLocationTypeDescriptionsByLocationType(locationType, deletedBy);
        
        LocationTypeDetail locationTypeDetail = locationType.getLastDetailForUpdate();
        locationTypeDetail.setThruTime(session.START_TIME_LONG);
        locationTypeDetail.store();
        locationType.setActiveDetail(null);
        
        if(adjustDefault) {
            // Check for default, and pick one if necessary
            Party warehouseParty = locationTypeDetail.getWarehouseParty();
            LocationType defaultLocationType = getDefaultLocationType(warehouseParty);
            
            if(defaultLocationType == null) {
                List<LocationType> locationTypes = getLocationTypesByWarehousePartyForUpdate(warehouseParty);
                
                if(!locationTypes.isEmpty()) {
                    Iterator iter = locationTypes.iterator();
                    if(iter.hasNext()) {
                        defaultLocationType = (LocationType)iter.next();
                    }
                    LocationTypeDetailValue locationTypeDetailValue = Objects.requireNonNull(defaultLocationType).getLastDetailForUpdate().getLocationTypeDetailValue().clone();
                    
                    locationTypeDetailValue.setIsDefault(Boolean.TRUE);
                    updateLocationTypeFromValue(locationTypeDetailValue, false, deletedBy);
                }
            }
        }
        
        sendEvent(locationType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteLocationType(LocationType locationType, BasePK deletedBy) {
        deleteLocationsByLocationType(locationType, deletedBy);
        deleteLocationType(locationType, deletedBy, true);
    }
    
    public void deleteLocationTypesByWarehouseParty(Party warehouseParty, BasePK deletedBy) {
        List<LocationType> locationTypes = getLocationTypesByWarehousePartyForUpdate(warehouseParty);
        
        locationTypes.forEach((locationType) -> {
            deleteLocationType(locationType, deletedBy, false);
        });
    }
    
    // --------------------------------------------------------------------------------
    //   Location Type Descriptions
    // --------------------------------------------------------------------------------
    
    public LocationTypeDescription createLocationTypeDescription(LocationType locationType, Language language, String description, BasePK createdBy) {
        LocationTypeDescription locationTypeDescription = LocationTypeDescriptionFactory.getInstance().create(locationType, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(locationType.getPrimaryKey(), EventTypes.MODIFY, locationTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return locationTypeDescription;
    }
    
    private LocationTypeDescription getLocationTypeDescription(LocationType locationType, Language language, EntityPermission entityPermission) {
        LocationTypeDescription locationTypeDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locationtypedescriptions " +
                        "WHERE loctypd_loctyp_locationtypeid = ? AND loctypd_lang_languageid = ? AND loctypd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locationtypedescriptions " +
                        "WHERE loctypd_loctyp_locationtypeid = ? AND loctypd_lang_languageid = ? AND loctypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, locationType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            locationTypeDescription = LocationTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationTypeDescription;
    }
    
    public LocationTypeDescription getLocationTypeDescription(LocationType locationType, Language language) {
        return getLocationTypeDescription(locationType, language, EntityPermission.READ_ONLY);
    }
    
    public LocationTypeDescription getLocationTypeDescriptionForUpdate(LocationType locationType, Language language) {
        return getLocationTypeDescription(locationType, language, EntityPermission.READ_WRITE);
    }
    
    public LocationTypeDescriptionValue getLocationTypeDescriptionValue(LocationTypeDescription locationTypeDescription) {
        return locationTypeDescription == null? null: locationTypeDescription.getLocationTypeDescriptionValue().clone();
    }
    
    public LocationTypeDescriptionValue getLocationTypeDescriptionValueForUpdate(LocationType locationType, Language language) {
        return getLocationTypeDescriptionValue(getLocationTypeDescriptionForUpdate(locationType, language));
    }
    
    private List<LocationTypeDescription> getLocationTypeDescriptionsByLocationType(LocationType locationType, EntityPermission entityPermission) {
        List<LocationTypeDescription> locationTypeDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locationtypedescriptions, languages " +
                        "WHERE loctypd_loctyp_locationtypeid = ? AND loctypd_thrutime = ? AND loctypd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locationtypedescriptions " +
                        "WHERE loctypd_loctyp_locationtypeid = ? AND loctypd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationTypeDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, locationType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            locationTypeDescriptions = LocationTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationTypeDescriptions;
    }
    
    public List<LocationTypeDescription> getLocationTypeDescriptionsByLocationType(LocationType locationType) {
        return getLocationTypeDescriptionsByLocationType(locationType, EntityPermission.READ_ONLY);
    }
    
    public List<LocationTypeDescription> getLocationTypeDescriptionsByLocationTypeForUpdate(LocationType locationType) {
        return getLocationTypeDescriptionsByLocationType(locationType, EntityPermission.READ_WRITE);
    }
    
    public String getBestLocationTypeDescription(LocationType locationType, Language language) {
        String description;
        LocationTypeDescription locationTypeDescription = getLocationTypeDescription(locationType, language);
        
        if(locationTypeDescription == null && !language.getIsDefault()) {
            locationTypeDescription = getLocationTypeDescription(locationType, getPartyControl().getDefaultLanguage());
        }
        
        if(locationTypeDescription == null) {
            description = locationType.getLastDetail().getLocationTypeName();
        } else {
            description = locationTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public LocationTypeDescriptionTransfer getLocationTypeDescriptionTransfer(UserVisit userVisit, LocationTypeDescription locationTypeDescription) {
        return getWarehouseTransferCaches(userVisit).getLocationTypeDescriptionTransferCache().getLocationTypeDescriptionTransfer(locationTypeDescription);
    }
    
    public List<LocationTypeDescriptionTransfer> getLocationTypeDescriptionTransfersByLocationType(UserVisit userVisit, LocationType locationType) {
        List<LocationTypeDescription> locationTypeDescriptions = getLocationTypeDescriptionsByLocationType(locationType);
        List<LocationTypeDescriptionTransfer> locationTypeDescriptionTransfers = null;
        
        if(locationTypeDescriptions != null) {
            locationTypeDescriptionTransfers = new ArrayList<>(locationTypeDescriptions.size());
            
            for(LocationTypeDescription locationTypeDescription  : locationTypeDescriptions) {
                locationTypeDescriptionTransfers.add(getWarehouseTransferCaches(userVisit).getLocationTypeDescriptionTransferCache().getLocationTypeDescriptionTransfer(locationTypeDescription));
            }
        }
        
        return locationTypeDescriptionTransfers;
    }
    
    public void updateLocationTypeDescriptionFromValue(LocationTypeDescriptionValue locationTypeDescriptionValue, BasePK updatedBy) {
        if(locationTypeDescriptionValue.hasBeenModified()) {
            LocationTypeDescription locationTypeDescription = LocationTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     locationTypeDescriptionValue.getPrimaryKey());
            
            locationTypeDescription.setThruTime(session.START_TIME_LONG);
            locationTypeDescription.store();
            
            LocationType locationType = locationTypeDescription.getLocationType();
            Language language = locationTypeDescription.getLanguage();
            String description = locationTypeDescriptionValue.getDescription();
            
            locationTypeDescription = LocationTypeDescriptionFactory.getInstance().create(locationType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(locationType.getPrimaryKey(), EventTypes.MODIFY, locationTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteLocationTypeDescription(LocationTypeDescription locationTypeDescription, BasePK deletedBy) {
        locationTypeDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(locationTypeDescription.getLocationTypePK(), EventTypes.MODIFY, locationTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteLocationTypeDescriptionsByLocationType(LocationType locationType, BasePK deletedBy) {
        List<LocationTypeDescription> locationTypeDescriptions = getLocationTypeDescriptionsByLocationTypeForUpdate(locationType);
        
        locationTypeDescriptions.forEach((locationTypeDescription) -> 
                deleteLocationTypeDescription(locationTypeDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Location Name Elements
    // --------------------------------------------------------------------------------
    
    public LocationNameElement createLocationNameElement(LocationType locationType, String locationNameElementName, Integer offset,
            Integer length, String validationPattern, BasePK createdBy) {
        LocationNameElement locationNameElement = LocationNameElementFactory.getInstance().create();
        LocationNameElementDetail locationNameElementDetail = LocationNameElementDetailFactory.getInstance().create(session,
                locationNameElement, locationType, locationNameElementName, offset, length, validationPattern, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        locationNameElement = LocationNameElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, locationNameElement.getPrimaryKey());
        locationNameElement.setActiveDetail(locationNameElementDetail);
        locationNameElement.setLastDetail(locationNameElementDetail);
        locationNameElement.store();
        
        sendEvent(locationNameElement.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return locationNameElement;
    }
    
    private LocationNameElement getLocationNameElementByName(LocationType locationType, String locationNameElementName, EntityPermission entityPermission) {
        LocationNameElement locationNameElement;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locationnameelements, locationnameelementdetails " +
                        "WHERE locne_locationnameelementid = locnedt_locne_locationnameelementid AND locnedt_loctyp_locationtypeid = ? " +
                        "AND locnedt_locationnameelementname = ? AND locnedt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locationnameelements, locationnameelementdetails " +
                        "WHERE locne_locationnameelementid = locnedt_locne_locationnameelementid AND locnedt_loctyp_locationtypeid = ? " +
                        "AND locnedt_locationnameelementname = ? AND locnedt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationNameElementFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, locationType.getPrimaryKey().getEntityId());
            ps.setString(2, locationNameElementName);
            ps.setLong(3, Session.MAX_TIME);
            
            locationNameElement = LocationNameElementFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationNameElement;
    }
    
    public LocationNameElement getLocationNameElementByName(LocationType locationType, String locationNameElementName) {
        return getLocationNameElementByName(locationType, locationNameElementName, EntityPermission.READ_ONLY);
    }
    
    public LocationNameElement getLocationNameElementByNameForUpdate(LocationType locationType, String locationNameElementName) {
        return getLocationNameElementByName(locationType, locationNameElementName, EntityPermission.READ_WRITE);
    }
    
    public LocationNameElementDetailValue getLocationNameElementDetailValueForUpdate(LocationNameElement locationNameElement) {
        return locationNameElement == null? null: locationNameElement.getLastDetailForUpdate().getLocationNameElementDetailValue().clone();
    }
    
    public LocationNameElementDetailValue getLocationNameElementDetailValueByNameForUpdate(LocationType locationType, String locationNameElementName) {
        return getLocationNameElementDetailValueForUpdate(getLocationNameElementByNameForUpdate(locationType, locationNameElementName));
    }
    
    private List<LocationNameElement> getLocationNameElementsByLocationType(LocationType locationType, EntityPermission entityPermission) {
        List<LocationNameElement> locationNameElements;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locationnameelements, locationnameelementdetails " +
                        "WHERE locne_locationnameelementid = locnedt_locne_locationnameelementid AND locnedt_loctyp_locationtypeid = ? " +
                        "AND locnedt_thrutime = ? " +
                        "ORDER BY locnedt_offset";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locationnameelements, locationnameelementdetails " +
                        "WHERE locne_locationnameelementid = locnedt_locne_locationnameelementid AND locnedt_loctyp_locationtypeid = ? " +
                        "AND locnedt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationNameElementFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, locationType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            locationNameElements = LocationNameElementFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationNameElements;
    }
    
    public List<LocationNameElement> getLocationNameElementsByLocationType(LocationType locationType) {
        return getLocationNameElementsByLocationType(locationType, EntityPermission.READ_ONLY);
    }
    
    public List<LocationNameElement> getLocationNameElementsByLocationTypeForUpdate(LocationType locationType) {
        return getLocationNameElementsByLocationType(locationType, EntityPermission.READ_WRITE);
    }
    
    public List<LocationNameElementValue> getLocationNameElementValuesByLocationTypeForUpdate(LocationType locationType) {
        List<LocationNameElement> locationNameElements = getLocationNameElementsByLocationTypeForUpdate(locationType);
        List<LocationNameElementValue> locationNameElementValues = new ArrayList<>(locationNameElements.size());
        
        locationNameElements.forEach((locationNameElement) -> {
            locationNameElementValues.add(locationNameElement.getLocationNameElementValue().clone());
        });
        
        return locationNameElementValues;
    }
    
    public LocationNameElementTransfer getLocationNameElementTransfer(UserVisit userVisit, LocationNameElement locationNameElement) {
        return getWarehouseTransferCaches(userVisit).getLocationNameElementTransferCache().getLocationNameElementTransfer(locationNameElement);
    }
    
    public List<LocationNameElementTransfer> getLocationNameElementTransfersByLocationType(UserVisit userVisit, LocationType locationType) {
        List<LocationNameElement> locationNameElements = getLocationNameElementsByLocationType(locationType);
        List<LocationNameElementTransfer> locationNameElementTransfers = new ArrayList<>(locationNameElements.size());
        LocationNameElementTransferCache locationNameElementTransferCache = getWarehouseTransferCaches(userVisit).getLocationNameElementTransferCache();
        
        locationNameElements.forEach((locationNameElement) ->
                locationNameElementTransfers.add(locationNameElementTransferCache.getLocationNameElementTransfer(locationNameElement))
        );
        
        return locationNameElementTransfers;
    }
    
    public void updateLocationNameElementFromValue(LocationNameElementDetailValue locationNameElementDetailValue, BasePK updatedBy) {
        if(locationNameElementDetailValue.hasBeenModified()) {
            LocationNameElement locationNameElement = LocationNameElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     locationNameElementDetailValue.getLocationNameElementPK());
            LocationNameElementDetail locationNameElementDetail = locationNameElement.getActiveDetailForUpdate();
            
            locationNameElementDetail.setThruTime(session.START_TIME_LONG);
            locationNameElementDetail.store();
            
            LocationNameElementPK locationNameElementPK = locationNameElementDetail.getLocationNameElementPK();
            LocationTypePK locationTypePK = locationNameElementDetail.getLocationTypePK(); // Not updated
            String locationNameElementName = locationNameElementDetailValue.getLocationNameElementName();
            Integer offset = locationNameElementDetailValue.getOffset();
            Integer length = locationNameElementDetailValue.getLength();
            String validationPattern = locationNameElementDetailValue.getValidationPattern();
            
            locationNameElementDetail = LocationNameElementDetailFactory.getInstance().create(locationNameElementPK,
                    locationTypePK, locationNameElementName, offset, length, validationPattern, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            locationNameElement.setActiveDetail(locationNameElementDetail);
            locationNameElement.setLastDetail(locationNameElementDetail);
            
            sendEvent(locationNameElementPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteLocationNameElement(LocationNameElement locationNameElement, BasePK deletedBy) {
        deleteLocationNameElementDescriptionsByLocationNameElement(locationNameElement, deletedBy);
        
        LocationNameElementDetail locationNameElementDetail = locationNameElement.getLastDetailForUpdate();
        locationNameElementDetail.setThruTime(session.START_TIME_LONG);
        locationNameElementDetail.store();
        locationNameElement.setActiveDetail(null);
        
        sendEvent(locationNameElement.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteLocationNameElementsByLocationType(LocationType locationType, BasePK deletedBy) {
        List<LocationNameElement> locationNameElements = getLocationNameElementsByLocationTypeForUpdate(locationType);
        
        locationNameElements.forEach((locationNameElement) -> 
                deleteLocationNameElement(locationNameElement, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Location Name Element Descriptions
    // --------------------------------------------------------------------------------
    
    public LocationNameElementDescription createLocationNameElementDescription(LocationNameElement locationNameElement, Language language,
            String description, BasePK createdBy) {
        LocationNameElementDescription locationNameElementDescription = LocationNameElementDescriptionFactory.getInstance().create(session,
                locationNameElement, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(locationNameElement.getPrimaryKey(), EventTypes.MODIFY, locationNameElementDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return locationNameElementDescription;
    }
    
    private LocationNameElementDescription getLocationNameElementDescription(LocationNameElement locationNameElement, Language language,
            EntityPermission entityPermission) {
        LocationNameElementDescription locationNameElementDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locationnameelementdescriptions " +
                        "WHERE locned_locne_locationnameelementid = ? AND locned_lang_languageid = ? AND locned_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locationnameelementdescriptions " +
                        "WHERE locned_locne_locationnameelementid = ? AND locned_lang_languageid = ? AND locned_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationNameElementDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, locationNameElement.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            locationNameElementDescription = LocationNameElementDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationNameElementDescription;
    }
    
    public LocationNameElementDescription getLocationNameElementDescription(LocationNameElement locationNameElement, Language language) {
        return getLocationNameElementDescription(locationNameElement, language, EntityPermission.READ_ONLY);
    }
    
    public LocationNameElementDescription getLocationNameElementDescriptionForUpdate(LocationNameElement locationNameElement, Language language) {
        return getLocationNameElementDescription(locationNameElement, language, EntityPermission.READ_WRITE);
    }
    
    public LocationNameElementDescriptionValue getLocationNameElementDescriptionValue(LocationNameElementDescription locationNameElementDescription) {
        return locationNameElementDescription == null? null: locationNameElementDescription.getLocationNameElementDescriptionValue().clone();
    }
    
    public LocationNameElementDescriptionValue getLocationNameElementDescriptionValueForUpdate(LocationNameElement locationNameElement, Language language) {
        return getLocationNameElementDescriptionValue(getLocationNameElementDescriptionForUpdate(locationNameElement, language));
    }
    
    private List<LocationNameElementDescription> getLocationNameElementDescriptionsByLocationNameElement(LocationNameElement locationNameElement, EntityPermission entityPermission) {
        List<LocationNameElementDescription> locationNameElementDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locationnameelementdescriptions, languages " +
                        "WHERE locned_locne_locationnameelementid = ? AND locned_thrutime = ? AND locned_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locationnameelementdescriptions " +
                        "WHERE locned_locne_locationnameelementid = ? AND locned_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationNameElementDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, locationNameElement.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            locationNameElementDescriptions = LocationNameElementDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationNameElementDescriptions;
    }
    
    public List<LocationNameElementDescription> getLocationNameElementDescriptionsByLocationNameElement(LocationNameElement locationNameElement) {
        return getLocationNameElementDescriptionsByLocationNameElement(locationNameElement, EntityPermission.READ_ONLY);
    }
    
    public List<LocationNameElementDescription> getLocationNameElementDescriptionsByLocationNameElementForUpdate(LocationNameElement locationNameElement) {
        return getLocationNameElementDescriptionsByLocationNameElement(locationNameElement, EntityPermission.READ_WRITE);
    }
    
    public String getBestLocationNameElementDescription(LocationNameElement locationNameElement, Language language) {
        String description;
        LocationNameElementDescription locationNameElementDescription = getLocationNameElementDescription(locationNameElement, language);
        
        if(locationNameElementDescription == null && !language.getIsDefault()) {
            locationNameElementDescription = getLocationNameElementDescription(locationNameElement, getPartyControl().getDefaultLanguage());
        }
        
        if(locationNameElementDescription == null) {
            description = locationNameElement.getLastDetail().getLocationNameElementName();
        } else {
            description = locationNameElementDescription.getDescription();
        }
        
        return description;
    }
    
    public LocationNameElementDescriptionTransfer getLocationNameElementDescriptionTransfer(UserVisit userVisit, LocationNameElementDescription locationNameElementDescription) {
        return getWarehouseTransferCaches(userVisit).getLocationNameElementDescriptionTransferCache().getLocationNameElementDescriptionTransfer(locationNameElementDescription);
    }
    
    public List<LocationNameElementDescriptionTransfer> getLocationNameElementDescriptionTransfersByLocationNameElement(UserVisit userVisit, LocationNameElement locationNameElement) {
        List<LocationNameElementDescription> locationNameElementDescriptions = getLocationNameElementDescriptionsByLocationNameElement(locationNameElement);
        List<LocationNameElementDescriptionTransfer> locationNameElementDescriptionTransfers = null;
        
        if(locationNameElementDescriptions != null) {
            locationNameElementDescriptionTransfers = new ArrayList<>(locationNameElementDescriptions.size());
            
            for(var locationNameElementDescription : locationNameElementDescriptions) {
                locationNameElementDescriptionTransfers.add(getWarehouseTransferCaches(userVisit).getLocationNameElementDescriptionTransferCache().getLocationNameElementDescriptionTransfer(locationNameElementDescription));
            }
        }
        
        return locationNameElementDescriptionTransfers;
    }
    
    public void updateLocationNameElementDescriptionFromValue(LocationNameElementDescriptionValue locationNameElementDescriptionValue, BasePK updatedBy) {
        if(locationNameElementDescriptionValue.hasBeenModified()) {
            LocationNameElementDescription locationNameElementDescription = LocationNameElementDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, locationNameElementDescriptionValue.getPrimaryKey());
            
            locationNameElementDescription.setThruTime(session.START_TIME_LONG);
            locationNameElementDescription.store();
            
            LocationNameElement locationNameElement = locationNameElementDescription.getLocationNameElement();
            Language language = locationNameElementDescription.getLanguage();
            String description = locationNameElementDescriptionValue.getDescription();
            
            locationNameElementDescription = LocationNameElementDescriptionFactory.getInstance().create(locationNameElement,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(locationNameElement.getPrimaryKey(), EventTypes.MODIFY, locationNameElementDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteLocationNameElementDescription(LocationNameElementDescription locationNameElementDescription, BasePK deletedBy) {
        locationNameElementDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(locationNameElementDescription.getLocationNameElementPK(), EventTypes.MODIFY, locationNameElementDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteLocationNameElementDescriptionsByLocationNameElement(LocationNameElement locationNameElement, BasePK deletedBy) {
        List<LocationNameElementDescription> locationNameElementDescriptions = getLocationNameElementDescriptionsByLocationNameElementForUpdate(locationNameElement);
        
        locationNameElementDescriptions.forEach((locationNameElementDescription) -> 
                deleteLocationNameElementDescription(locationNameElementDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Locations
    // --------------------------------------------------------------------------------
    
    public Location createLocation(Party warehouseParty, String locationName, LocationType locationType,
            LocationUseType locationUseType, Integer velocity, InventoryLocationGroup inventoryLocationGroup, BasePK createdBy) {
        Location location = LocationFactory.getInstance().create();
        LocationDetail locationDetail = LocationDetailFactory.getInstance().create(location, warehouseParty, locationName,
                locationType, locationUseType, velocity, inventoryLocationGroup, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        location = LocationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, location.getPrimaryKey());
        location.setActiveDetail(locationDetail);
        location.setLastDetail(locationDetail);
        location.store();
        
        sendEvent(location.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return location;
    }
    
    private Location getLocationByName(Party warehouseParty, String locationName, EntityPermission entityPermission) {
        Location location;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locations, locationdetails " +
                        "WHERE loc_locationid = locdt_loc_locationid AND locdt_warehousepartyid = ? AND locdt_locationname = ? " +
                        "AND locdt_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locations, locationdetails " +
                        "WHERE loc_locationid = locdt_loc_locationid AND locdt_warehousepartyid = ? AND locdt_locationname = ? " +
                        "AND locdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, warehouseParty.getPrimaryKey().getEntityId());
            ps.setString(2, locationName);
            ps.setLong(3, Session.MAX_TIME);
            
            location = LocationFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return location;
    }
    
    public Location getLocationByName(Party warehouseParty, String locationName) {
        return getLocationByName(warehouseParty, locationName, EntityPermission.READ_ONLY);
    }
    
    public Location getLocationByNameForUpdate(Party warehouseParty, String locationName) {
        return getLocationByName(warehouseParty, locationName, EntityPermission.READ_WRITE);
    }
    
    public LocationDetailValue getLocationDetailValueForUpdate(Location location) {
        return location == null? null: location.getLastDetailForUpdate().getLocationDetailValue().clone();
    }
    
    public LocationDetailValue getLocationDetailValueByNameForUpdate(Party warehouseParty, String locationName) {
        return getLocationDetailValueForUpdate(getLocationByNameForUpdate(warehouseParty, locationName));
    }
    
    private List<Location> getLocationsByWarehouseParty(Party warehouseParty, EntityPermission entityPermission) {
        List<Location> locations;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locations, locationdetails " +
                        "WHERE loc_locationid = locdt_loc_locationid AND locdt_warehousepartyid = ? AND locdt_thrutime = ? " +
                        "ORDER BY locdt_locationname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locations, locationdetails " +
                        "WHERE loc_locationid = locdt_loc_locationid AND locdt_warehousepartyid = ? AND locdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, warehouseParty.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            locations = LocationFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locations;
    }
    
    public List<Location> getLocationsByWarehouseParty(Party warehouseParty) {
        return getLocationsByWarehouseParty(warehouseParty, EntityPermission.READ_ONLY);
    }
    
    public List<Location> getLocationsByWarehousePartyForUpdate(Party warehouseParty) {
        return getLocationsByWarehouseParty(warehouseParty, EntityPermission.READ_WRITE);
    }
    
    private List<Location> getLocationsByLocationType(LocationType locationType, EntityPermission entityPermission) {
        List<Location> locations;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locations, locationdetails " +
                        "WHERE loc_locationid = locdt_loc_locationid AND locdt_loctyp_locationtypeid = ? AND locdt_thrutime = ? " +
                        "ORDER BY locdt_locationname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locations, locationdetails " +
                        "WHERE loc_locationid = locdt_loc_locationid AND locdt_loctyp_locationtypeid = ? AND locdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, locationType.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            locations = LocationFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locations;
    }
    
    public List<Location> getLocationsByLocationType(LocationType locationType) {
        return getLocationsByLocationType(locationType, EntityPermission.READ_ONLY);
    }
    
    public List<Location> getLocationsByLocationTypeForUpdate(LocationType locationType) {
        return getLocationsByLocationType(locationType, EntityPermission.READ_WRITE);
    }
    
    private List<Location> getLocationsByInventoryLocationGroup(InventoryLocationGroup inventoryLocationGroup, EntityPermission entityPermission) {
        List<Location> locations;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locations, locationdetails " +
                        "WHERE loc_locationid = locdt_loc_locationid AND locdt_invlocgrp_inventorylocationgroupid = ? AND locdt_thrutime = ? " +
                        "ORDER BY locdt_locationname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locations, locationdetails " +
                        "WHERE loc_locationid = locdt_loc_locationid AND locdt_invlocgrp_inventorylocationgroupid = ? AND locdt_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, inventoryLocationGroup.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            locations = LocationFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locations;
    }
    
    public List<Location> getLocationsByInventoryLocationGroup(InventoryLocationGroup inventoryLocationGroup) {
        return getLocationsByInventoryLocationGroup(inventoryLocationGroup, EntityPermission.READ_ONLY);
    }
    
    public List<Location> getLocationsByInventoryLocationGroupForUpdate(InventoryLocationGroup inventoryLocationGroup) {
        return getLocationsByInventoryLocationGroup(inventoryLocationGroup, EntityPermission.READ_WRITE);
    }
    
    public LocationTransfer getLocationTransfer(UserVisit userVisit, Location location) {
        return getWarehouseTransferCaches(userVisit).getLocationTransferCache().getLocationTransfer(location);
    }
    
    public List<LocationTransfer> getLocationTransfersByWarehouseParty(UserVisit userVisit, Party warehouseParty) {
        List<Location> locations = getLocationsByWarehouseParty(warehouseParty);
        List<LocationTransfer> locationTransfers = null;
        
        if(locations != null) {
            locationTransfers = new ArrayList<>(locations.size());
            
            for(var location : locations) {
                locationTransfers.add(getWarehouseTransferCaches(userVisit).getLocationTransferCache().getLocationTransfer(location));
            }
        }
        
        return locationTransfers;
    }
    
    public LocationChoicesBean getLocationChoicesByWarehouseParty(String defaultLocationChoice, Language language, Party warehouseParty) {
        List<Location> locations = getLocationsByWarehouseParty(warehouseParty);
        var size = locations.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        for(var location : locations) {
            LocationDetail locationDetail = location.getLastDetail();
            
            var label = getBestLocationDescription(location, language);
            var value = locationDetail.getLocationName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultLocationChoice != null && defaultLocationChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new LocationChoicesBean(labels, values, defaultValue);
    }
    
    public long countLocationsByLocationUseType(Party warehouseParty, LocationUseType locationUseType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                    "FROM locationdetails " +
                    "WHERE locdt_warehousepartyid = ? AND locdt_locutyp_locationusetypeid = ? AND locdt_thrutime = ? ",
                warehouseParty, locationUseType, Session.MAX_TIME);
    }
    
    public LocationStatusChoicesBean getLocationStatusChoices(String defaultLocationStatusChoice, Language language,
            Location location, PartyPK partyPK) {
        var workflowControl = getWorkflowControl();
        LocationStatusChoicesBean locationStatusChoicesBean = new LocationStatusChoicesBean();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(location);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceUsingNames(LocationStatusConstants.Workflow_LOCATION_STATUS,
                entityInstance);
        
        workflowControl.getWorkflowDestinationChoices(locationStatusChoicesBean, defaultLocationStatusChoice, language,
                false, workflowEntityStatus.getWorkflowStep(), partyPK);
        
        return locationStatusChoicesBean;
    }
    
    public void setLocationStatus(ExecutionErrorAccumulator eea, Location location, String locationStatusChoice, PartyPK modifiedBy) {
        var workflowControl = getWorkflowControl();
        EntityInstance entityInstance = getEntityInstanceByBaseEntity(location);
        WorkflowEntityStatus workflowEntityStatus = workflowControl.getWorkflowEntityStatusByEntityInstanceForUpdateUsingNames(LocationStatusConstants.Workflow_LOCATION_STATUS,
                entityInstance);
        WorkflowDestination workflowDestination = locationStatusChoice == null? null:
            workflowControl.getWorkflowDestinationByName(workflowEntityStatus.getWorkflowStep(), locationStatusChoice);
        
        if(workflowDestination != null || locationStatusChoice == null) {
            workflowControl.transitionEntityInWorkflow(eea, workflowEntityStatus, workflowDestination, null, modifiedBy);
        } else {
            eea.addExecutionError(ExecutionErrors.UnknownLocationStatusChoice.name(), locationStatusChoice);
        }
    }
    
    public void updateLocationFromValue(LocationDetailValue locationDetailValue, BasePK updatedBy) {
        if(locationDetailValue.hasBeenModified()) {
            Location location = LocationFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    locationDetailValue.getLocationPK());
            LocationDetail locationDetail = location.getActiveDetailForUpdate();
            
            locationDetail.setThruTime(session.START_TIME_LONG);
            locationDetail.store();
            
            LocationPK locationPK = locationDetail.getLocationPK();
            PartyPK warehousePartyPK = locationDetail.getWarehousePartyPK();
            String locationName = locationDetailValue.getLocationName();
            LocationTypePK locationTypePK = locationDetailValue.getLocationTypePK();
            LocationUseTypePK locationUseTypePK = locationDetailValue.getLocationUseTypePK();
            Integer velocity = locationDetailValue.getVelocity();
            InventoryLocationGroupPK inventoryLocationGroupPK = locationDetailValue.getInventoryLocationGroupPK();
            
            locationDetail = LocationDetailFactory.getInstance().create(locationPK, warehousePartyPK, locationName,
                    locationTypePK, locationUseTypePK, velocity, inventoryLocationGroupPK, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            location.setActiveDetail(locationDetail);
            location.setLastDetail(locationDetail);
            
            sendEvent(locationPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteLocation(Location location, BasePK deletedBy) {
        deleteLocationVolumeByLocation(location, deletedBy);
        deleteLocationCapacitiesByLocation(location, deletedBy);
        deleteLocationDescriptionsByLocation(location, deletedBy);
        
        LocationDetail locationDetail = location.getLastDetailForUpdate();
        locationDetail.setThruTime(session.START_TIME_LONG);
        locationDetail.store();
        location.setActiveDetail(null);
        
        sendEvent(location.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteLocationsByWarehouseParty(Party warehouseParty, BasePK deletedBy) {
        List<Location> locations = getLocationsByWarehousePartyForUpdate(warehouseParty);
        
        locations.forEach((location) -> 
                deleteLocation(location, deletedBy)
        );
    }
    
    public void deleteLocationsByLocationType(LocationType locationType, BasePK deletedBy) {
        List<Location> locations = getLocationsByLocationTypeForUpdate(locationType);
        
        locations.forEach((location) -> 
                deleteLocation(location, deletedBy)
        );
    }
    
    public void deleteLocationsByInventoryLocationGroup(InventoryLocationGroup inventoryLocationGroup, BasePK deletedBy) {
        List<Location> locations = getLocationsByInventoryLocationGroupForUpdate(inventoryLocationGroup);
        
        locations.forEach((location) -> 
                deleteLocation(location, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Location Descriptions
    // --------------------------------------------------------------------------------
    
    public LocationDescription createLocationDescription(Location location, Language language, String description, BasePK createdBy) {
        LocationDescription locationDescription = LocationDescriptionFactory.getInstance().create(location, language, description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(location.getPrimaryKey(), EventTypes.MODIFY, locationDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return locationDescription;
    }
    
    private LocationDescription getLocationDescription(Location location, Language language, EntityPermission entityPermission) {
        LocationDescription locationDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locationdescriptions " +
                        "WHERE locd_loc_locationid = ? AND locd_lang_languageid = ? AND locd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locationdescriptions " +
                        "WHERE locd_loc_locationid = ? AND locd_lang_languageid = ? AND locd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, location.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            locationDescription = LocationDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationDescription;
    }
    
    public LocationDescription getLocationDescription(Location location, Language language) {
        return getLocationDescription(location, language, EntityPermission.READ_ONLY);
    }
    
    public LocationDescription getLocationDescriptionForUpdate(Location location, Language language) {
        return getLocationDescription(location, language, EntityPermission.READ_WRITE);
    }
    
    public LocationDescriptionValue getLocationDescriptionValue(LocationDescription locationDescription) {
        return locationDescription == null? null: locationDescription.getLocationDescriptionValue().clone();
    }
    
    public LocationDescriptionValue getLocationDescriptionValueForUpdate(Location location, Language language) {
        return getLocationDescriptionValue(getLocationDescriptionForUpdate(location, language));
    }
    
    private List<LocationDescription> getLocationDescriptionsByLocation(Location location, EntityPermission entityPermission) {
        List<LocationDescription> locationDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locationdescriptions, languages " +
                        "WHERE locd_loc_locationid = ? AND locd_thrutime = ? AND locd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locationdescriptions " +
                        "WHERE locd_loc_locationid = ? AND locd_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, location.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            locationDescriptions = LocationDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationDescriptions;
    }
    
    public List<LocationDescription> getLocationDescriptionsByLocation(Location location) {
        return getLocationDescriptionsByLocation(location, EntityPermission.READ_ONLY);
    }
    
    public List<LocationDescription> getLocationDescriptionsByLocationForUpdate(Location location) {
        return getLocationDescriptionsByLocation(location, EntityPermission.READ_WRITE);
    }
    
    public String getBestLocationDescription(Location location, Language language) {
        String description;
        LocationDescription locationDescription = getLocationDescription(location, language);
        
        if(locationDescription == null && !language.getIsDefault()) {
            locationDescription = getLocationDescription(location, getPartyControl().getDefaultLanguage());
        }
        
        if(locationDescription == null) {
            description = location.getLastDetail().getLocationName();
        } else {
            description = locationDescription.getDescription();
        }
        
        return description;
    }
    
    public LocationDescriptionTransfer getLocationDescriptionTransfer(UserVisit userVisit, LocationDescription locationDescription) {
        return getWarehouseTransferCaches(userVisit).getLocationDescriptionTransferCache().getLocationDescriptionTransfer(locationDescription);
    }
    
    public List<LocationDescriptionTransfer> getLocationDescriptionTransfersByLocation(UserVisit userVisit, Location location) {
        List<LocationDescription> locationDescriptions = getLocationDescriptionsByLocation(location);
        List<LocationDescriptionTransfer> locationDescriptionTransfers = null;
        
        if(locationDescriptions != null) {
            locationDescriptionTransfers = new ArrayList<>(locationDescriptions.size());
            
            for(var locationDescription : locationDescriptions) {
                locationDescriptionTransfers.add(getWarehouseTransferCaches(userVisit).getLocationDescriptionTransferCache().getLocationDescriptionTransfer(locationDescription));
            }
        }
        
        return locationDescriptionTransfers;
    }
    
    public void updateLocationDescriptionFromValue(LocationDescriptionValue locationDescriptionValue, BasePK updatedBy) {
        if(locationDescriptionValue.hasBeenModified()) {
            LocationDescription locationDescription = LocationDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, locationDescriptionValue.getPrimaryKey());
            
            locationDescription.setThruTime(session.START_TIME_LONG);
            locationDescription.store();
            
            Location location = locationDescription.getLocation();
            Language language = locationDescription.getLanguage();
            String description = locationDescriptionValue.getDescription();
            
            locationDescription = LocationDescriptionFactory.getInstance().create(location, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(location.getPrimaryKey(), EventTypes.MODIFY, locationDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteLocationDescription(LocationDescription locationDescription, BasePK deletedBy) {
        locationDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(locationDescription.getLocationPK(), EventTypes.MODIFY, locationDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteLocationDescriptionsByLocation(Location location, BasePK deletedBy) {
        List<LocationDescription> locationDescriptions = getLocationDescriptionsByLocationForUpdate(location);
        
        locationDescriptions.forEach((locationDescription) -> 
                deleteLocationDescription(locationDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Location Volumes
    // --------------------------------------------------------------------------------
    
    public LocationVolume createLocationVolume(Location location, Long height, Long width, Long depth, BasePK createdBy) {
        LocationVolume locationVolume = LocationVolumeFactory.getInstance().create(location, height, width, depth,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(location.getPrimaryKey(), EventTypes.MODIFY, locationVolume.getPrimaryKey(), null, createdBy);
        
        return locationVolume;
    }
    
    private LocationVolume getLocationVolume(Location location, EntityPermission entityPermission) {
        LocationVolume locationVolume;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locationvolumes " +
                        "WHERE locvol_loc_locationid = ? AND locvol_thrutime = ? ";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locationvolumes " +
                        "WHERE locvol_loc_locationid = ? AND locvol_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationVolumeFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, location.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            locationVolume = LocationVolumeFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationVolume;
    }
    
    public LocationVolume getLocationVolume(Location location) {
        return getLocationVolume(location, EntityPermission.READ_ONLY);
    }
    
    public LocationVolume getLocationVolumeForUpdate(Location location) {
        return getLocationVolume(location, EntityPermission.READ_WRITE);
    }
    
    public LocationVolumeValue getLocationVolumeValueForUpdate(Location location) {
        LocationVolume locationVolume = getLocationVolumeForUpdate(location);
        
        return locationVolume == null? null: locationVolume.getLocationVolumeValue().clone();
    }
    
    public LocationVolumeTransfer getLocationVolumeTransfer(UserVisit userVisit, LocationVolume locationVolume) {
        return locationVolume == null? null: getWarehouseTransferCaches(userVisit).getLocationVolumeTransferCache().getLocationVolumeTransfer(locationVolume);
    }
    
    public LocationVolumeTransfer getLocationVolumeTransfer(UserVisit userVisit, Location location) {
        LocationVolume locationVolume = getLocationVolume(location);
        
        return locationVolume == null? null: getWarehouseTransferCaches(userVisit).getLocationVolumeTransferCache().getLocationVolumeTransfer(locationVolume);
    }
    
    public void updateLocationVolumeFromValue(LocationVolumeValue locationVolumeValue, BasePK updatedBy) {
        if(locationVolumeValue.hasBeenModified()) {
            LocationVolume locationVolume = LocationVolumeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     locationVolumeValue.getPrimaryKey());
            
            locationVolume.setThruTime(session.START_TIME_LONG);
            locationVolume.store();
            
            LocationPK locationPK = locationVolume.getLocationPK(); // Not updated
            Long height = locationVolumeValue.getHeight();
            Long width = locationVolumeValue.getWidth();
            Long depth = locationVolumeValue.getDepth();
            
            locationVolume = LocationVolumeFactory.getInstance().create(locationPK, height,
                    width, depth, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(locationPK, EventTypes.MODIFY, locationVolume.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteLocationVolume(LocationVolume locationVolume, BasePK deletedBy) {
        locationVolume.setThruTime(session.START_TIME_LONG);
        
        sendEvent(locationVolume.getLocation().getPrimaryKey(), EventTypes.MODIFY, locationVolume.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteLocationVolumeByLocation(Location location, BasePK deletedBy) {
        LocationVolume locationVolume = getLocationVolumeForUpdate(location);
        
        if(locationVolume != null)
            deleteLocationVolume(locationVolume, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Location Capacities
    // --------------------------------------------------------------------------------
    
    public LocationCapacity createLocationCapacity(Location location, UnitOfMeasureType unitOfMeasureType,
            Long capacity, BasePK createdBy) {
        LocationCapacity locationCapacity = LocationCapacityFactory.getInstance().create(location,
                unitOfMeasureType, capacity, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(location.getPrimaryKey(), EventTypes.MODIFY, locationCapacity.getPrimaryKey(), null, createdBy);
        
        return locationCapacity;
    }
    
    private List<LocationCapacity> getLocationCapacitiesByLocation(Location location, EntityPermission entityPermission) {
        List<LocationCapacity> locationCapacities;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locationcapacities, unitofmeasuretypedetails, unitofmeasurekinddetails " +
                        "WHERE loccap_loc_locationid = ? AND loccap_thrutime = ? " +
                        "AND loccap_uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ? " +
                        "AND uomtdt_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                        "ORDER BY uomtdt_sortorder, uomtdt_unitofmeasuretypename, uomkdt_sortorder, uomkdt_unitofmeasurekindname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locationcapacities " +
                        "WHERE loccap_loc_locationid = ? AND loccap_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationCapacityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, location.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
                ps.setLong(4, Session.MAX_TIME);
            }
            
            locationCapacities = LocationCapacityFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationCapacities;
    }
    
    public List<LocationCapacity> getLocationCapacitiesByLocation(Location location) {
        return getLocationCapacitiesByLocation(location, EntityPermission.READ_ONLY);
    }
    
    public List<LocationCapacity> getLocationCapacitiesByLocationForUpdate(Location location) {
        return getLocationCapacitiesByLocation(location, EntityPermission.READ_WRITE);
    }
    
    private LocationCapacity getLocationCapacity(Location location, UnitOfMeasureType unitOfMeasureType, EntityPermission entityPermission) {
        LocationCapacity locationCapacity;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM locationcapacities " +
                        "WHERE loccap_loc_locationid = ? AND loccap_uomt_unitofmeasuretypeid = ? AND loccap_thrutime = ? ";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM locationcapacities " +
                        "WHERE loccap_loc_locationid = ? AND loccap_uomt_unitofmeasuretypeid = ? AND loccap_thrutime = ? " +
                        "FOR UPDATE";
            }
            
            PreparedStatement ps = LocationCapacityFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, location.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            locationCapacity = LocationCapacityFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return locationCapacity;
    }
    
    public LocationCapacity getLocationCapacity(Location location, UnitOfMeasureType unitOfMeasureType) {
        return getLocationCapacity(location, unitOfMeasureType, EntityPermission.READ_ONLY);
    }
    
    public LocationCapacity getLocationCapacityForUpdate(Location location, UnitOfMeasureType unitOfMeasureType) {
        return getLocationCapacity(location, unitOfMeasureType, EntityPermission.READ_WRITE);
    }
    
    public LocationCapacityValue getLocationCapacityValueForUpdate(Location location, UnitOfMeasureType unitOfMeasureType) {
        LocationCapacity locationCapacity = getLocationCapacityForUpdate(location, unitOfMeasureType);
        
        return locationCapacity == null? null: locationCapacity.getLocationCapacityValue().clone();
    }
    
    public void updateLocationCapacityFromValue(LocationCapacityValue locationCapacityValue, BasePK updatedBy) {
        if(locationCapacityValue.hasBeenModified()) {
            LocationCapacity locationCapacity = LocationCapacityFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     locationCapacityValue.getPrimaryKey());
            
            locationCapacity.setThruTime(session.START_TIME_LONG);
            locationCapacity.store();
            
            UnitOfMeasureTypePK unitOfMeasureTypePK = locationCapacity.getUnitOfMeasureTypePK(); // Not updated
            LocationPK locationPK = locationCapacity.getLocationPK(); // Not updated
            Long capacity = locationCapacityValue.getCapacity();
            
            locationCapacity = LocationCapacityFactory.getInstance().create(locationPK, unitOfMeasureTypePK, capacity,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(unitOfMeasureTypePK, EventTypes.MODIFY, locationCapacity.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public LocationCapacityTransfer getLocationCapacityTransfer(UserVisit userVisit, LocationCapacity locationCapacity) {
        return getWarehouseTransferCaches(userVisit).getLocationCapacityTransferCache().getLocationCapacityTransfer(locationCapacity);
    }
    
    public List<LocationCapacityTransfer> getLocationCapacityTransfersByLocation(UserVisit userVisit, Location location) {
        List<LocationCapacity> locationCapacities = getLocationCapacitiesByLocation(location);
        List<LocationCapacityTransfer> locationCapacityTransfers = new ArrayList<>(locationCapacities.size());
        LocationCapacityTransferCache locationCapacityTransferCache = getWarehouseTransferCaches(userVisit).getLocationCapacityTransferCache();
        
        locationCapacities.forEach((locationCapacity) ->
                locationCapacityTransfers.add(locationCapacityTransferCache.getLocationCapacityTransfer(locationCapacity))
        );
        
        return locationCapacityTransfers;
    }
    
    public void deleteLocationCapacity(LocationCapacity locationCapacity, BasePK deletedBy) {
        locationCapacity.setThruTime(session.START_TIME_LONG);
        
        sendEvent(locationCapacity.getLocation().getPrimaryKey(), EventTypes.MODIFY, locationCapacity.getPrimaryKey(), null, deletedBy);
    }
    
    public void deleteLocationCapacitiesByLocation(Location location, BasePK deletedBy) {
        List<LocationCapacity> locationCapacities = getLocationCapacitiesByLocationForUpdate(location);
        
        locationCapacities.forEach((locationCapacity) -> 
                deleteLocationCapacity(locationCapacity, deletedBy)
        );
    }
    
}
