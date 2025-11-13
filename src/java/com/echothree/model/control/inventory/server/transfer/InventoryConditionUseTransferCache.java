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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.inventory.common.transfer.InventoryConditionUseTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.data.inventory.server.entity.InventoryConditionUse;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InventoryConditionUseTransferCache
        extends BaseInventoryTransferCache<InventoryConditionUse, InventoryConditionUseTransfer> {
    
    /** Creates a new instance of InventoryConditionUseTransferCache */
    public InventoryConditionUseTransferCache(InventoryControl inventoryControl) {
        super(inventoryControl);
    }
    
    @Override
    public InventoryConditionUseTransfer getTransfer(UserVisit userVisit, InventoryConditionUse inventoryConditionUse) {
        var inventoryConditionUseTransfer = get(inventoryConditionUse);
        
        if(inventoryConditionUseTransfer == null) {
            var inventoryConditionUseType = inventoryControl.getInventoryConditionUseTypeTransfer(userVisit,
                    inventoryConditionUse.getInventoryConditionUseType());
            var inventoryCondition = inventoryControl.getInventoryConditionTransfer(userVisit,
                    inventoryConditionUse.getInventoryCondition());
            var isDefault = inventoryConditionUse.getIsDefault();
            
            inventoryConditionUseTransfer = new InventoryConditionUseTransfer(inventoryConditionUseType, inventoryCondition, isDefault);
            put(userVisit, inventoryConditionUse, inventoryConditionUseTransfer);
        }
        
        return inventoryConditionUseTransfer;
    }
    
}
