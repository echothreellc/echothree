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
import com.echothree.model.control.inventory.common.transfer.InventoryTransactionRoleTypeDescriptionTransfer;
import com.echothree.model.control.inventory.server.control.InventoryTransactionRoleControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InventoryTransactionRoleTypeDescriptionTransferCache
        extends BaseInventoryDescriptionTransferCache<InventoryTransactionRoleTypeDescription, InventoryTransactionRoleTypeDescriptionTransfer> {

    @Inject
    InventoryTransactionRoleControl inventoryTransactionRoleControl;

    /** Creates a new instance of InventoryTransactionRoleTypeDescriptionTransferCache */
    protected InventoryTransactionRoleTypeDescriptionTransferCache() {
        super();
    }
    
    @Override
    public InventoryTransactionRoleTypeDescriptionTransfer getTransfer(UserVisit userVisit,
            InventoryTransactionRoleTypeDescription inventoryTransactionRoleTypeDescription) {
        var inventoryTransactionRoleTypeDescriptionTransfer = get(inventoryTransactionRoleTypeDescription);
        
        if(inventoryTransactionRoleTypeDescriptionTransfer == null) {
            var inventoryTransactionRoleTypeTransfer = inventoryTransactionRoleControl.getInventoryTransactionRoleTypeTransfer(userVisit,
                    inventoryTransactionRoleTypeDescription.getInventoryTransactionRoleType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, inventoryTransactionRoleTypeDescription.getLanguage());
            
            inventoryTransactionRoleTypeDescriptionTransfer = new InventoryTransactionRoleTypeDescriptionTransfer(languageTransfer,
                    inventoryTransactionRoleTypeTransfer, inventoryTransactionRoleTypeDescription.getDescription());
            put(userVisit, inventoryTransactionRoleTypeDescription, inventoryTransactionRoleTypeDescriptionTransfer);
        }
        
        return inventoryTransactionRoleTypeDescriptionTransfer;
    }
    
}
