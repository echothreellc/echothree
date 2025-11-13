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

import com.echothree.model.control.inventory.common.transfer.InventoryAdjustmentTypeDescriptionTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.inventory.server.control.InventoryAdjustmentTypeControl;
import com.echothree.model.data.inventory.server.entity.InventoryAdjustmentTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class InventoryAdjustmentTypeDescriptionTransferCache
        extends BaseInventoryDescriptionTransferCache<InventoryAdjustmentTypeDescription, InventoryAdjustmentTypeDescriptionTransfer> {

    InventoryAdjustmentTypeControl inventoryAdjustmentTypeControl = Session.getModelController(InventoryAdjustmentTypeControl.class);

    /** Creates a new instance of InventoryAdjustmentTypeDescriptionTransferCache */
    public InventoryAdjustmentTypeDescriptionTransferCache(InventoryControl inventoryControl) {
        super(inventoryControl);
    }

    @Override
    public InventoryAdjustmentTypeDescriptionTransfer getTransfer(UserVisit userVisit, InventoryAdjustmentTypeDescription inventoryAdjustmentTypeDescription) {
        var inventoryAdjustmentTypeDescriptionTransfer = get(inventoryAdjustmentTypeDescription);
        
        if(inventoryAdjustmentTypeDescriptionTransfer == null) {
            var inventoryAdjustmentTypeTransfer = inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeTransfer(userVisit, inventoryAdjustmentTypeDescription.getInventoryAdjustmentType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, inventoryAdjustmentTypeDescription.getLanguage());
            
            inventoryAdjustmentTypeDescriptionTransfer = new InventoryAdjustmentTypeDescriptionTransfer(languageTransfer, inventoryAdjustmentTypeTransfer, inventoryAdjustmentTypeDescription.getDescription());
            put(userVisit, inventoryAdjustmentTypeDescription, inventoryAdjustmentTypeDescriptionTransfer);
        }
        
        return inventoryAdjustmentTypeDescriptionTransfer;
    }
    
}
