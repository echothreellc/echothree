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
import com.echothree.model.control.inventory.common.transfer.InventoryTransactionTimeTypeDescriptionTransfer;
import com.echothree.model.control.inventory.server.control.InventoryTransactionTimeControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionTimeTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InventoryTransactionTimeTypeDescriptionTransferCache
        extends BaseInventoryDescriptionTransferCache<InventoryTransactionTimeTypeDescription, InventoryTransactionTimeTypeDescriptionTransfer> {

    @Inject
    InventoryTransactionTimeControl inventoryTransactionTimeControl;

    /** Creates a new instance of InventoryTransactionTimeTypeDescriptionTransferCache */
    protected InventoryTransactionTimeTypeDescriptionTransferCache() {
        super();
    }
    
    @Override
    public InventoryTransactionTimeTypeDescriptionTransfer getTransfer(UserVisit userVisit,
            InventoryTransactionTimeTypeDescription inventoryTransactionTimeTypeDescription) {
        var inventoryTransactionTimeTypeDescriptionTransfer = get(inventoryTransactionTimeTypeDescription);
        
        if(inventoryTransactionTimeTypeDescriptionTransfer == null) {
            var inventoryTransactionTimeTypeTransfer = inventoryTransactionTimeControl.getInventoryTransactionTimeTypeTransfer(userVisit,
                    inventoryTransactionTimeTypeDescription.getInventoryTransactionTimeType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, inventoryTransactionTimeTypeDescription.getLanguage());
            
            inventoryTransactionTimeTypeDescriptionTransfer = new InventoryTransactionTimeTypeDescriptionTransfer(languageTransfer,
                    inventoryTransactionTimeTypeTransfer, inventoryTransactionTimeTypeDescription.getDescription());
            put(userVisit, inventoryTransactionTimeTypeDescription, inventoryTransactionTimeTypeDescriptionTransfer);
        }
        
        return inventoryTransactionTimeTypeDescriptionTransfer;
    }
    
}
