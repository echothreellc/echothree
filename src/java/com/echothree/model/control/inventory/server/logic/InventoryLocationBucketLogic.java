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

import com.echothree.model.control.inventory.common.exception.DuplicateInventoryLocationBucketException;
import com.echothree.model.control.inventory.common.exception.PartyBucketInUseException;
import com.echothree.model.control.inventory.server.control.BucketControl;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.model.data.inventory.server.entity.InventoryLocation;
import com.echothree.model.data.inventory.server.entity.InventoryLocationBucket;
import com.echothree.model.data.inventory.server.value.InventoryLocationBucketValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class InventoryLocationBucketLogic
        extends BaseLogic {

    @Inject
    BucketControl bucketControl;

    protected InventoryLocationBucketLogic() {
        super();
    }

    public InventoryLocationBucket createInventoryLocationBucket(final ExecutionErrorAccumulator eea,
            final InventoryLocation inventoryLocation, final InventoryBucketType inventoryBucketType,
            final Long quantity, final BasePK createdBy) {
        var inventoryLocationBucket = getInventoryLocationBucket(inventoryLocation, inventoryBucketType);

        if(inventoryLocationBucket == null) {
            inventoryLocationBucket = bucketControl.createInventoryLocationBucket(inventoryLocation,
                    inventoryBucketType, quantity, createdBy);
        } else {
            handleExecutionError(DuplicateInventoryLocationBucketException.class, eea,
                    ExecutionErrors.DuplicateInventoryLocationBucket.name());
        }

        return inventoryLocationBucket;
    }

    public InventoryLocationBucket getInventoryLocationBucket(final InventoryLocation inventoryLocation,
            final InventoryBucketType inventoryBucketType) {
        return bucketControl.getInventoryLocationBucket(inventoryLocation, inventoryBucketType);
    }

    public void updateInventoryLocationBucketFromValue(final InventoryLocationBucketValue inventoryLocationBucketValue,
            final BasePK updatedBy) {
        bucketControl.updateInventoryLocationBucketFromValue(inventoryLocationBucketValue, updatedBy);
    }

    private void removeInventoryLocationBuckets(final ExecutionErrorAccumulator eea,
            final List<InventoryLocationBucket> inventoryLocationBuckets, final BasePK removedBy) {
        var partyBucketInUse = inventoryLocationBuckets.stream()
                .anyMatch(inventoryLocationBucket -> inventoryLocationBucket.getQuantity() != 0);

        if(partyBucketInUse) {
            handleExecutionError(PartyBucketInUseException.class, eea, ExecutionErrors.PartyBucketInUse.name());
        }

        if(eea == null || !eea.hasExecutionErrors()) {
            bucketControl.removeInventoryLocationBuckets(inventoryLocationBuckets, removedBy);
        }
    }

    public void removeInventoryLocationBucket(final ExecutionErrorAccumulator eea,
            final InventoryLocationBucket inventoryLocationBucket, final BasePK removedBy) {
        var inventoryLocationBucketForUpdate = bucketControl.getInventoryLocationBucketForUpdate(
                inventoryLocationBucket.getInventoryLocation(), inventoryLocationBucket.getInventoryBucketType());

        removeInventoryLocationBuckets(eea, List.of(inventoryLocationBucketForUpdate), removedBy);
    }

    public void removeInventoryLocationBucketsByInventoryLocation(final ExecutionErrorAccumulator eea,
            final InventoryLocation inventoryLocation, final BasePK removedBy) {
        removeInventoryLocationBuckets(eea,
                bucketControl.getInventoryLocationBucketsByInventoryLocationForUpdate(inventoryLocation), removedBy);
    }

    public void removeInventoryLocationBucketsByInventoryBucketType(final ExecutionErrorAccumulator eea,
            final InventoryBucketType inventoryBucketType, final BasePK removedBy) {
        removeInventoryLocationBuckets(eea,
                bucketControl.getInventoryLocationBucketsByInventoryBucketTypeForUpdate(inventoryBucketType), removedBy);
    }

}
