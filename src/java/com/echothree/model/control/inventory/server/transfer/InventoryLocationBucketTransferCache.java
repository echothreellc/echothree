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

import com.echothree.model.control.inventory.common.transfer.InventoryLocationBucketTransfer;
import com.echothree.model.control.inventory.server.control.InventoryBucketTypeControl;
import com.echothree.model.control.inventory.server.control.InventoryLocationControl;
import com.echothree.model.data.inventory.server.entity.InventoryLocationBucket;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class InventoryLocationBucketTransferCache
        extends BaseInventoryTransferCache<InventoryLocationBucket, InventoryLocationBucketTransfer> {

    @Inject
    InventoryLocationControl inventoryLocationControl;

    @Inject
    InventoryBucketTypeControl inventoryBucketTypeControl;

    protected InventoryLocationBucketTransferCache() {
        super();
    }

    @Override
    public InventoryLocationBucketTransfer getTransfer(UserVisit userVisit, InventoryLocationBucket inventoryLocationBucket) {
        var transfer = get(inventoryLocationBucket);

        if(transfer == null) {
            var inventoryLocation = inventoryLocationBucket.getInventoryLocation();

            transfer = new InventoryLocationBucketTransfer(
                    inventoryLocationControl.getInventoryLocationTransfer(userVisit, inventoryLocation),
                    inventoryBucketTypeControl.getInventoryBucketTypeTransfer(userVisit,
                            inventoryLocationBucket.getInventoryBucketType()),
                    formatUnitOfMeasure(userVisit,
                            inventoryLocation.getUnitOfMeasureType().getLastDetail().getUnitOfMeasureKind(),
                            inventoryLocationBucket.getQuantity()));
            put(userVisit, inventoryLocationBucket, transfer);
        }

        return transfer;
    }

}
