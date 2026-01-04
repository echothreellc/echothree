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

import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class InventoryLocationGroupCapacityTransfer
        extends BaseTransfer {
    
    private InventoryLocationGroupTransfer inventoryInventoryLocationGroupGroup;
    private UnitOfMeasureTypeTransfer unitOfMeasureType;
    private Long capacity;
    
    /** Creates a new instance of InventoryLocationGroupCapacityTransfer */
    public InventoryLocationGroupCapacityTransfer(InventoryLocationGroupTransfer inventoryInventoryLocationGroupGroup,
            UnitOfMeasureTypeTransfer unitOfMeasureType, Long capacity) {
        this.inventoryInventoryLocationGroupGroup = inventoryInventoryLocationGroupGroup;
        this.unitOfMeasureType = unitOfMeasureType;
        this.capacity = capacity;
    }
    
    public InventoryLocationGroupTransfer getInventoryLocationGroup() {
        return inventoryInventoryLocationGroupGroup;
    }
    
    public void setInventoryLocationGroup(InventoryLocationGroupTransfer inventoryInventoryLocationGroupGroup) {
        this.inventoryInventoryLocationGroupGroup = inventoryInventoryLocationGroupGroup;
    }
    
    public UnitOfMeasureTypeTransfer getUnitOfMeasureType() {
        return unitOfMeasureType;
    }
    
    public void setUnitOfMeasureType(UnitOfMeasureTypeTransfer unitOfMeasureType) {
        this.unitOfMeasureType = unitOfMeasureType;
    }
    
    public Long getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }
    
}
