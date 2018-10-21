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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.inventory.remote.transfer.InventoryLocationGroupCapacityTransfer;
import com.echothree.model.control.inventory.remote.transfer.InventoryLocationGroupTransfer;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.uom.remote.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.uom.server.transfer.UnitOfMeasureTypeTransferCache;
import com.echothree.model.data.inventory.server.entity.InventoryLocationGroupCapacity;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class InventoryLocationGroupCapacityTransferCache
        extends BaseInventoryTransferCache<InventoryLocationGroupCapacity, InventoryLocationGroupCapacityTransfer> {
    
    /** Creates a new instance of InventoryLocationGroupCapacityTransferCache */
    public InventoryLocationGroupCapacityTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
    }
    
    public InventoryLocationGroupCapacityTransfer getInventoryLocationGroupCapacityTransfer(InventoryLocationGroupCapacity inventoryLocationGroupCapacity) {
        InventoryLocationGroupCapacityTransfer inventoryLocationGroupCapacityTransfer = get(inventoryLocationGroupCapacity);
        
        if(inventoryLocationGroupCapacityTransfer == null) {
            UomControl partyControl = (UomControl)Session.getModelController(UomControl.class);
            InventoryLocationGroupTransferCache inventoryLocationGroupTransferCache = inventoryControl.getInventoryTransferCaches(userVisit).getInventoryLocationGroupTransferCache();
            InventoryLocationGroupTransfer inventoryLocationGroupTransfer = inventoryLocationGroupTransferCache.getInventoryLocationGroupTransfer(inventoryLocationGroupCapacity.getInventoryLocationGroup());
            UnitOfMeasureTypeTransferCache unitOfMeasureTypeTransferCache = partyControl.getUomTransferCaches(userVisit).getUnitOfMeasureTypeTransferCache();
            UnitOfMeasureType unitOfMeasureType = inventoryLocationGroupCapacity.getUnitOfMeasureType();
            UnitOfMeasureTypeTransfer unitOfMeasureTypeTransfer = unitOfMeasureTypeTransferCache.getUnitOfMeasureTypeTransfer(unitOfMeasureType);
            Long capacity = inventoryLocationGroupCapacity.getCapacity();
            
            inventoryLocationGroupCapacityTransfer = new InventoryLocationGroupCapacityTransfer(inventoryLocationGroupTransfer, unitOfMeasureTypeTransfer,
            capacity);
            put(inventoryLocationGroupCapacity, inventoryLocationGroupCapacityTransfer);
        }
        
        return inventoryLocationGroupCapacityTransfer;
    }
    
}
