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
import com.echothree.model.control.inventory.common.transfer.InventoryDispositionAdjustmentTransfer;
import com.echothree.model.control.inventory.server.control.InventoryDispositionAdjustmentControl;
import com.echothree.model.control.inventory.server.control.InventoryDispositionControl;
import com.echothree.model.control.inventory.server.control.InventoryAdjustmentTypeControl;
import com.echothree.model.control.inventory.server.control.InventoryBucketTypeControl;
import com.echothree.model.data.inventory.server.entity.InventoryDispositionAdjustment;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class InventoryDispositionAdjustmentTransferCache
        extends BaseInventoryTransferCache<InventoryDispositionAdjustment, InventoryDispositionAdjustmentTransfer> {

    @Inject
    InventoryDispositionAdjustmentControl inventoryDispositionAdjustmentControl;

    @Inject
    InventoryDispositionControl inventoryDispositionControl;

    @Inject InventoryAdjustmentTypeControl inventoryAdjustmentTypeControl;
    @Inject InventoryBucketTypeControl inventoryBucketTypeControl;

    /** Creates a new instance of InventoryDispositionAdjustmentTransferCache */
    protected InventoryDispositionAdjustmentTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public InventoryDispositionAdjustmentTransfer getTransfer(UserVisit userVisit, InventoryDispositionAdjustment inventoryDispositionAdjustment) {
        var inventoryDispositionAdjustmentTransfer = get(inventoryDispositionAdjustment);
        
        if(inventoryDispositionAdjustmentTransfer == null) {
            var inventoryDispositionAdjustmentDetail = inventoryDispositionAdjustment.getLastDetail();
            var inventoryDisposition = inventoryDispositionControl.getInventoryDispositionTransfer(userVisit,
                    inventoryDispositionAdjustmentDetail.getInventoryDisposition());
            var inventoryAdjustmentType = inventoryAdjustmentTypeControl.getInventoryAdjustmentTypeTransfer(userVisit,
                    inventoryDispositionAdjustmentDetail.getInventoryAdjustmentType());
            var inventoryBucketType = inventoryBucketTypeControl.getInventoryBucketTypeTransfer(userVisit,
                    inventoryDispositionAdjustmentDetail.getInventoryBucketType());
            var inventoryDispositionAdjustmentName = inventoryDispositionAdjustmentDetail.getInventoryDispositionAdjustmentName();
            var isDefault = inventoryDispositionAdjustmentDetail.getIsDefault();
            var sortOrder = inventoryDispositionAdjustmentDetail.getSortOrder();
            var description = inventoryDispositionAdjustmentControl.getBestInventoryDispositionAdjustmentDescription(inventoryDispositionAdjustment,
                    getLanguage(userVisit));
            
            inventoryDispositionAdjustmentTransfer = new InventoryDispositionAdjustmentTransfer(inventoryDisposition,
                    inventoryAdjustmentType, inventoryBucketType,
                    inventoryDispositionAdjustmentName, isDefault, sortOrder, description);
            put(userVisit, inventoryDispositionAdjustment, inventoryDispositionAdjustmentTransfer);
        }
        
        return inventoryDispositionAdjustmentTransfer;
    }
    
}
