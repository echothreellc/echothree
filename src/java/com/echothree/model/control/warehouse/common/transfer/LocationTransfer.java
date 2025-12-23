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

package com.echothree.model.control.warehouse.common.transfer;

import com.echothree.model.control.inventory.common.transfer.InventoryLocationGroupTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class LocationTransfer
        extends BaseTransfer {
    
    private WarehouseTransfer warehouse;
    private String locationName;
    private LocationTypeTransfer locationType;
    private LocationUseTypeTransfer locationUseType;
    private Integer velocity;
    private InventoryLocationGroupTransfer inventoryLocationGroup;
    private String description;
    private WorkflowEntityStatusTransfer locationStatus;
    
    private ListWrapper<LocationCapacityTransfer> locationCapacities;
    private LocationVolumeTransfer locationVolume;
    
    /** Creates a new instance of LocationTransfer */
    public LocationTransfer(WarehouseTransfer warehouse, String locationName, LocationTypeTransfer locationType,
            LocationUseTypeTransfer locationUseType, Integer velocity, InventoryLocationGroupTransfer inventoryLocationGroup, String description,
            WorkflowEntityStatusTransfer locationStatus) {
        this.warehouse = warehouse;
        this.locationName = locationName;
        this.locationType = locationType;
        this.locationUseType = locationUseType;
        this.velocity = velocity;
        this.inventoryLocationGroup = inventoryLocationGroup;
        this.description = description;
        this.locationStatus = locationStatus;
    }
    
    public WarehouseTransfer getWarehouse() {
        return warehouse;
    }
    
    public void setWarehouse(WarehouseTransfer warehouse) {
        this.warehouse = warehouse;
    }
    
    public String getLocationName() {
        return locationName;
    }
    
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    public LocationTypeTransfer getLocationType() {
        return locationType;
    }
    
    public void setLocationType(LocationTypeTransfer locationType) {
        this.locationType = locationType;
    }
    
    public LocationUseTypeTransfer getLocationUseType() {
        return locationUseType;
    }
    
    public void setLocationUseType(LocationUseTypeTransfer locationUseType) {
        this.locationUseType = locationUseType;
    }
    
    public Integer getVelocity() {
        return velocity;
    }
    
    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }
    
    public InventoryLocationGroupTransfer getInventoryLocationGroup() {
        return inventoryLocationGroup;
    }
    
    public void setInventoryLocationGroup(InventoryLocationGroupTransfer inventoryLocationGroup) {
        this.inventoryLocationGroup = inventoryLocationGroup;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public WorkflowEntityStatusTransfer getLocationStatus() {
        return locationStatus;
    }
    
    public void setLocationStatus(WorkflowEntityStatusTransfer locationStatus) {
        this.locationStatus = locationStatus;
    }
    
    public ListWrapper<LocationCapacityTransfer> getLocationCapacities() {
        return locationCapacities;
    }
    
    public void setLocationCapacities(ListWrapper<LocationCapacityTransfer> locationCapacities) {
        this.locationCapacities = locationCapacities;
    }
    
    public LocationVolumeTransfer getLocationVolume() {
        return locationVolume;
    }
    
    public void setLocationVolume(LocationVolumeTransfer locationVolume) {
        this.locationVolume = locationVolume;
    }
    
}
