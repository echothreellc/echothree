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

import com.echothree.model.control.inventory.common.transfer.InventoryConditionDescriptionTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.data.inventory.server.entity.InventoryConditionDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class InventoryConditionDescriptionTransferCache
        extends BaseInventoryDescriptionTransferCache<InventoryConditionDescription, InventoryConditionDescriptionTransfer> {
    
    /** Creates a new instance of InventoryConditionDescriptionTransferCache */
    public InventoryConditionDescriptionTransferCache(InventoryControl inventoryControl) {
        super(inventoryControl);
    }
    
    @Override
    public InventoryConditionDescriptionTransfer getTransfer(InventoryConditionDescription inventoryConditionDescription) {
        var inventoryConditionDescriptionTransfer = get(inventoryConditionDescription);
        
        if(inventoryConditionDescriptionTransfer == null) {
            var inventoryConditionTransferCache = inventoryControl.getInventoryTransferCaches(userVisit).getInventoryConditionTransferCache();
            var inventoryConditionTransfer = inventoryConditionTransferCache.getTransfer(inventoryConditionDescription.getInventoryCondition());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, inventoryConditionDescription.getLanguage());
            
            inventoryConditionDescriptionTransfer = new InventoryConditionDescriptionTransfer(languageTransfer, inventoryConditionTransfer, inventoryConditionDescription.getDescription());
            put(userVisit, inventoryConditionDescription, inventoryConditionDescriptionTransfer);
        }
        
        return inventoryConditionDescriptionTransfer;
    }
    
}
