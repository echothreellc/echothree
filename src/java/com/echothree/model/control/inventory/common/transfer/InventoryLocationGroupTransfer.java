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

package com.echothree.model.control.inventory.common.transfer;

import com.echothree.model.control.warehouse.common.transfer.WarehouseTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class InventoryLocationGroupTransfer
        extends BaseTransfer {
    
    private WarehouseTransfer warehouse;
    private String inventoryLocationGroupName;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    private WorkflowEntityStatusTransfer inventoryLocationGroupStatus;
    
    private ListWrapper<InventoryLocationGroupCapacityTransfer> inventoryLocationGroupCapacities;
    private InventoryLocationGroupVolumeTransfer inventoryLocationGroupVolume;
    
    /** Creates a new instance of InventoryLocationGroupTransfer */
    public InventoryLocationGroupTransfer(WarehouseTransfer warehouse, String inventoryLocationGroupName, Boolean isDefault, Integer sortOrder,
            String description, WorkflowEntityStatusTransfer inventoryLocationGroupStatus) {
        this.warehouse = warehouse;
        this.inventoryLocationGroupName = inventoryLocationGroupName;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
        this.inventoryLocationGroupStatus = inventoryLocationGroupStatus;
    }
    
    public WarehouseTransfer getWarehouse() {
        return warehouse;
    }
    
    public void setWarehouse(WarehouseTransfer warehouse) {
        this.warehouse = warehouse;
    }
    
    public String getInventoryLocationGroupName() {
        return inventoryLocationGroupName;
    }
    
    public void setInventoryLocationGroupName(String inventoryLocationGroupName) {
        this.inventoryLocationGroupName = inventoryLocationGroupName;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public WorkflowEntityStatusTransfer getInventoryLocationGroupStatus() {
        return inventoryLocationGroupStatus;
    }
    
    public void setInventoryLocationGroupStatus(WorkflowEntityStatusTransfer inventoryLocationGroupStatus) {
        this.inventoryLocationGroupStatus = inventoryLocationGroupStatus;
    }
    
    public ListWrapper<InventoryLocationGroupCapacityTransfer> getInventoryLocationGroupCapacities() {
        return inventoryLocationGroupCapacities;
    }
    
    public void setInventoryLocationGroupCapacities(ListWrapper<InventoryLocationGroupCapacityTransfer> inventoryLocationGroupCapacities) {
        this.inventoryLocationGroupCapacities = inventoryLocationGroupCapacities;
    }
    
    public InventoryLocationGroupVolumeTransfer getInventoryLocationGroupVolume() {
        return inventoryLocationGroupVolume;
    }
    
    public void setInventoryLocationGroupVolume(InventoryLocationGroupVolumeTransfer inventoryLocationGroupVolume) {
        this.inventoryLocationGroupVolume = inventoryLocationGroupVolume;
    }
    
}
