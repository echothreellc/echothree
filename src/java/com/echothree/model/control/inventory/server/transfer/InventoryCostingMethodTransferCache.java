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
import com.echothree.model.control.inventory.common.transfer.InventoryCostingMethodTransfer;
import com.echothree.model.control.inventory.server.control.InventoryCostingMethodControl;
import com.echothree.model.data.inventory.server.entity.InventoryCostingMethod;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InventoryCostingMethodTransferCache
        extends BaseInventoryTransferCache<InventoryCostingMethod, InventoryCostingMethodTransfer> {

    @Inject
    InventoryCostingMethodControl inventoryCostingMethodControl;

    /** Creates a new instance of InventoryCostingMethodTransferCache */
    protected InventoryCostingMethodTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }

    @Override
    public InventoryCostingMethodTransfer getTransfer(UserVisit userVisit, InventoryCostingMethod inventoryCostingMethod) {
        var inventoryCostingMethodTransfer = get(inventoryCostingMethod);
        
        if(inventoryCostingMethodTransfer == null) {
            var inventoryCostingMethodDetail = inventoryCostingMethod.getLastDetail();
            var inventoryCostingMethodName = inventoryCostingMethodDetail.getInventoryCostingMethodName();
            var isDefault = inventoryCostingMethodDetail.getIsDefault();
            var sortOrder = inventoryCostingMethodDetail.getSortOrder();
            var description = inventoryCostingMethodControl.getBestInventoryCostingMethodDescription(inventoryCostingMethod, getLanguage(userVisit));
            
            inventoryCostingMethodTransfer = new InventoryCostingMethodTransfer(inventoryCostingMethodName, isDefault,
                    sortOrder, description);
            put(userVisit, inventoryCostingMethod, inventoryCostingMethodTransfer);
        }
        
        return inventoryCostingMethodTransfer;
    }
    
}
