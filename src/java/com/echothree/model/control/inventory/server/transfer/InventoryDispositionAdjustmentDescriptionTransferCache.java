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

package com.echothree.model.control.inventory.server.transfer;

import javax.inject.Inject;
import com.echothree.model.control.inventory.common.transfer.InventoryDispositionAdjustmentDescriptionTransfer;
import com.echothree.model.control.inventory.server.control.InventoryDispositionAdjustmentControl;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionAdjustmentDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InventoryDispositionAdjustmentDescriptionTransferCache
        extends BaseInventoryDescriptionTransferCache<InventoryDispositionAdjustmentDescription, InventoryDispositionAdjustmentDescriptionTransfer> {

    @Inject
    InventoryDispositionAdjustmentControl inventoryDispositionAdjustmentControl;

    /** Creates a new instance of InventoryDispositionAdjustmentDescriptionTransferCache */
    protected InventoryDispositionAdjustmentDescriptionTransferCache() {
        super();
    }
    
    @Override
    public InventoryDispositionAdjustmentDescriptionTransfer getTransfer(UserVisit userVisit,
            InventoryDispositionAdjustmentDescription inventoryDispositionAdjustmentDescription) {
        var inventoryDispositionAdjustmentDescriptionTransfer = get(inventoryDispositionAdjustmentDescription);
        
        if(inventoryDispositionAdjustmentDescriptionTransfer == null) {
            var inventoryDispositionAdjustmentTransfer = inventoryDispositionAdjustmentControl.getInventoryDispositionAdjustmentTransfer(userVisit,
                    inventoryDispositionAdjustmentDescription.getInventoryDispositionAdjustment());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, inventoryDispositionAdjustmentDescription.getLanguage());
            
            inventoryDispositionAdjustmentDescriptionTransfer = new InventoryDispositionAdjustmentDescriptionTransfer(languageTransfer,
                    inventoryDispositionAdjustmentTransfer, inventoryDispositionAdjustmentDescription.getDescription());
            put(userVisit, inventoryDispositionAdjustmentDescription, inventoryDispositionAdjustmentDescriptionTransfer);
        }
        
        return inventoryDispositionAdjustmentDescriptionTransfer;
    }
    
}
