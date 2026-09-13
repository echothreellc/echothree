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

import com.echothree.model.control.inventory.common.transfer.InventoryLayerBucketTransfer;
import com.echothree.model.control.inventory.server.control.InventoryBucketTypeControl;
import com.echothree.model.control.inventory.server.control.InventoryLayerControl;
import com.echothree.model.data.inventory.server.entity.InventoryLayerBucket;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class InventoryLayerBucketTransferCache
        extends BaseInventoryTransferCache<InventoryLayerBucket, InventoryLayerBucketTransfer> {

    @Inject
    InventoryBucketTypeControl inventoryBucketTypeControl;

    @Inject
    InventoryLayerControl inventoryLayerControl;

    protected InventoryLayerBucketTransferCache() {
        super();
    }

    @Override
    public InventoryLayerBucketTransfer getTransfer(UserVisit userVisit, InventoryLayerBucket inventoryLayerBucket) {
        var transfer = get(inventoryLayerBucket);

        if(transfer == null) {
            var inventoryLayer = inventoryLayerBucket.getInventoryLayer();
            var item = inventoryLayer.getLastDetail().getInventoryCostingPool().getLastDetail().getItem();

            transfer = new InventoryLayerBucketTransfer(
                    inventoryLayerControl.getInventoryLayerTransfer(userVisit, inventoryLayer),
                    inventoryBucketTypeControl.getInventoryBucketTypeTransfer(userVisit,
                            inventoryLayerBucket.getInventoryBucketType()),
                    formatUnitOfMeasure(userVisit,
                            item.getLastDetail().getUnitOfMeasureKind(),
                            inventoryLayerBucket.getQuantity()));
            put(userVisit, inventoryLayerBucket, transfer);
        }

        return transfer;
    }

}
