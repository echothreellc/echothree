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

package com.echothree.model.control.inventory.server.logic;

import com.echothree.model.control.inventory.common.exception.DuplicateInventoryLayerBucketException;
import com.echothree.model.control.inventory.common.exception.InventoryLayerBucketInUseException;
import com.echothree.model.control.inventory.server.control.BucketControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.model.data.inventory.server.entity.InventoryLayer;
import com.echothree.model.data.inventory.server.entity.InventoryLayerBucket;
import com.echothree.model.data.inventory.server.value.InventoryLayerBucketValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryLayerBucketLogic
        extends BaseLogic {

    @Inject
    BucketControl bucketControl;

    @Inject
    PartyControl partyControl;

    protected InventoryLayerBucketLogic() {
        super();
    }

    public InventoryLayerBucket createInventoryLayerBucket(final ExecutionErrorAccumulator eea,
            final InventoryLayer inventoryLayer, final InventoryBucketType inventoryBucketType,
            final Long quantity, final BasePK createdBy) {
        var inventoryLayerBucket = getInventoryLayerBucket(inventoryLayer, inventoryBucketType);

        if(inventoryLayerBucket == null) {
            inventoryLayerBucket = bucketControl.createInventoryLayerBucket(inventoryLayer,
                    inventoryBucketType, quantity, createdBy);
        } else {
            var inventoryLayerDetail = inventoryLayer.getLastDetail();
            var inventoryCostingPoolDetail = inventoryLayerDetail.getInventoryCostingPool().getLastDetail();

            handleExecutionError(DuplicateInventoryLayerBucketException.class, eea,
                    ExecutionErrors.DuplicateInventoryLayerBucket.name(),
                    partyControl.getPartyCompany(inventoryCostingPoolDetail.getCompanyParty()).getPartyCompanyName(),
                    inventoryCostingPoolDetail.getItem().getLastDetail().getItemName(),
                    inventoryCostingPoolDetail.getInventoryCondition().getLastDetail().getInventoryConditionName(),
                    inventoryLayerDetail.getInventoryLayerSequence(),
                    inventoryBucketType.getLastDetail().getInventoryBucketTypeName());
        }

        return inventoryLayerBucket;
    }

    public InventoryLayerBucket getInventoryLayerBucket(final InventoryLayer inventoryLayer,
            final InventoryBucketType inventoryBucketType) {
        return bucketControl.getInventoryLayerBucket(inventoryLayer, inventoryBucketType);
    }

    public void updateInventoryLayerBucketFromValue(final InventoryLayerBucketValue inventoryLayerBucketValue,
            final BasePK updatedBy) {
        bucketControl.updateInventoryLayerBucketFromValue(inventoryLayerBucketValue, updatedBy);
    }

    private void removeInventoryLayerBuckets(final ExecutionErrorAccumulator eea,
            final List<InventoryLayerBucket> inventoryLayerBuckets, final BasePK removedBy) {
        var inventoryLayerBucketInUse = inventoryLayerBuckets.stream()
                .anyMatch(inventoryLayerBucket -> inventoryLayerBucket.getQuantity() != 0);

        if(inventoryLayerBucketInUse) {
            handleExecutionError(InventoryLayerBucketInUseException.class, eea, ExecutionErrors.InventoryLayerBucketInUse.name());
        }

        if(eea == null || !eea.hasExecutionErrors()) {
            bucketControl.removeInventoryLayerBuckets(inventoryLayerBuckets, removedBy);
        }
    }

    public void removeInventoryLayerBucket(final ExecutionErrorAccumulator eea,
            final InventoryLayerBucket inventoryLayerBucket, final BasePK removedBy) {
        var inventoryLayerBucketForUpdate = bucketControl.getInventoryLayerBucketForUpdate(
                inventoryLayerBucket.getInventoryLayer(), inventoryLayerBucket.getInventoryBucketType());

        removeInventoryLayerBuckets(eea, List.of(inventoryLayerBucketForUpdate), removedBy);
    }

    public void removeInventoryLayerBucketsByInventoryLayer(final ExecutionErrorAccumulator eea,
            final InventoryLayer inventoryLayer, final BasePK removedBy) {
        removeInventoryLayerBuckets(eea,
                bucketControl.getInventoryLayerBucketsByInventoryLayerForUpdate(inventoryLayer), removedBy);
    }

    public void removeInventoryLayerBucketsByInventoryBucketType(final ExecutionErrorAccumulator eea,
            final InventoryBucketType inventoryBucketType, final BasePK removedBy) {
        removeInventoryLayerBuckets(eea,
                bucketControl.getInventoryLayerBucketsByInventoryBucketTypeForUpdate(inventoryBucketType), removedBy);
    }

}
