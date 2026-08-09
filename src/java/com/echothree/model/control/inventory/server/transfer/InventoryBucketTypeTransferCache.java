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
import com.echothree.model.control.inventory.common.transfer.InventoryBucketTypeTransfer;
import com.echothree.model.control.inventory.server.control.InventoryBucketTypeControl;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InventoryBucketTypeTransferCache
        extends BaseInventoryTransferCache<InventoryBucketType, InventoryBucketTypeTransfer> {

    @Inject
    InventoryBucketTypeControl inventoryBucketTypeControl;

    /** Creates a new instance of InventoryBucketTypeTransferCache */
    protected InventoryBucketTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }

    @Override
    public InventoryBucketTypeTransfer getTransfer(UserVisit userVisit, InventoryBucketType inventoryBucketType) {
        var inventoryBucketTypeTransfer = get(inventoryBucketType);
        
        if(inventoryBucketTypeTransfer == null) {
            var inventoryBucketTypeDetail = inventoryBucketType.getLastDetail();
            var inventoryBucketTypeName = inventoryBucketTypeDetail.getInventoryBucketTypeName();
            var isDefault = inventoryBucketTypeDetail.getIsDefault();
            var sortOrder = inventoryBucketTypeDetail.getSortOrder();
            var description = inventoryBucketTypeControl.getBestInventoryBucketTypeDescription(inventoryBucketType, getLanguage(userVisit));
            
            inventoryBucketTypeTransfer = new InventoryBucketTypeTransfer(inventoryBucketTypeName, isDefault,
                    sortOrder, description);
            put(userVisit, inventoryBucketType, inventoryBucketTypeTransfer);
        }
        
        return inventoryBucketTypeTransfer;
    }
    
}
