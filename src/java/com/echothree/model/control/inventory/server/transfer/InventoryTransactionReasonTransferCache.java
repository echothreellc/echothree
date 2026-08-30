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
import com.echothree.model.control.inventory.common.transfer.InventoryTransactionReasonTransfer;
import com.echothree.model.control.inventory.server.control.InventoryTransactionReasonControl;
import com.echothree.model.control.inventory.server.control.InventoryDispositionControl;
import com.echothree.model.data.inventory.server.entity.InventoryTransactionReason;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InventoryTransactionReasonTransferCache
        extends BaseInventoryTransferCache<InventoryTransactionReason, InventoryTransactionReasonTransfer> {

    @Inject
    InventoryTransactionReasonControl inventoryTransactionReasonControl;

    @Inject
    InventoryDispositionControl inventoryDispositionControl;

    /** Creates a new instance of InventoryTransactionReasonTransferCache */
    protected InventoryTransactionReasonTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public InventoryTransactionReasonTransfer getTransfer(UserVisit userVisit, InventoryTransactionReason inventoryTransactionReason) {
        var inventoryTransactionReasonTransfer = get(inventoryTransactionReason);
        
        if(inventoryTransactionReasonTransfer == null) {
            var inventoryTransactionReasonDetail = inventoryTransactionReason.getLastDetail();
            var inventoryDisposition = inventoryDispositionControl.getInventoryDispositionTransfer(userVisit,
                    inventoryTransactionReasonDetail.getInventoryDisposition());
            var inventoryTransactionReasonName = inventoryTransactionReasonDetail.getInventoryTransactionReasonName();
            var isDefault = inventoryTransactionReasonDetail.getIsDefault();
            var sortOrder = inventoryTransactionReasonDetail.getSortOrder();
            var description = inventoryTransactionReasonControl.getBestInventoryTransactionReasonDescription(inventoryTransactionReason,
                    getLanguage(userVisit));
            
            inventoryTransactionReasonTransfer = new InventoryTransactionReasonTransfer(inventoryDisposition,
                    inventoryTransactionReasonName, isDefault, sortOrder, description);
            put(userVisit, inventoryTransactionReason, inventoryTransactionReasonTransfer);
        }
        
        return inventoryTransactionReasonTransfer;
    }
    
}
