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
import com.echothree.model.control.inventory.common.transfer.InventoryTransactionRoleTypeTransfer;
import com.echothree.model.control.inventory.server.control.InventoryTransactionRoleControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionRoleType;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InventoryTransactionRoleTypeTransferCache
        extends BaseInventoryTransferCache<InventoryTransactionRoleType, InventoryTransactionRoleTypeTransfer> {

    @Inject
    InventoryTransactionRoleControl inventoryTransactionRoleControl;

    /** Creates a new instance of InventoryTransactionRoleTypeTransferCache */
    protected InventoryTransactionRoleTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public InventoryTransactionRoleTypeTransfer getTransfer(UserVisit userVisit, InventoryTransactionRoleType inventoryTransactionRoleType) {
        var inventoryTransactionRoleTypeTransfer = get(inventoryTransactionRoleType);
        
        if(inventoryTransactionRoleTypeTransfer == null) {
            var inventoryTransactionRoleTypeDetail = inventoryTransactionRoleType.getLastDetail();
            var inventoryTransactionRoleTypeName = inventoryTransactionRoleTypeDetail.getInventoryTransactionRoleTypeName();
            var isDefault = inventoryTransactionRoleTypeDetail.getIsDefault();
            var sortOrder = inventoryTransactionRoleTypeDetail.getSortOrder();
            var description = inventoryTransactionRoleControl.getBestInventoryTransactionRoleTypeDescription(inventoryTransactionRoleType,
                    getLanguage(userVisit));
            
            inventoryTransactionRoleTypeTransfer = new InventoryTransactionRoleTypeTransfer(inventoryTransactionRoleTypeName, isDefault,
                    sortOrder, description);
            put(userVisit, inventoryTransactionRoleType, inventoryTransactionRoleTypeTransfer);
        }
        
        return inventoryTransactionRoleTypeTransfer;
    }
    
}
