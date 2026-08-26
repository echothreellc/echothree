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

import com.echothree.model.control.inventory.common.exception.PartyBucketInUseException;
import com.echothree.model.control.inventory.server.control.BucketControl;
import com.echothree.model.data.inventory.server.entity.InventoryBucketType;
import com.echothree.model.data.inventory.server.entity.InventoryCondition;
import com.echothree.model.data.inventory.server.entity.PartyBucket;
import com.echothree.model.data.inventory.server.value.PartyBucketValue;
import com.echothree.model.data.item.server.entity.Item;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PartyBucketLogic
        extends BaseLogic {

    @Inject
    BucketControl bucketControl;

    protected PartyBucketLogic() {
        super();
    }

    public PartyBucket createPartyBucket(final Party party, final Item item,
            final UnitOfMeasureType unitOfMeasureType, final InventoryCondition inventoryCondition,
            final InventoryBucketType inventoryBucketType, final Long quantity, final BasePK createdBy) {
        return bucketControl.createPartyBucket(party, item, unitOfMeasureType, inventoryCondition,
                inventoryBucketType, quantity, createdBy);
    }

    public void updatePartyBucketFromValue(final PartyBucketValue partyBucketValue, final BasePK updatedBy) {
        bucketControl.updatePartyBucketFromValue(partyBucketValue, updatedBy);
    }

    private void removePartyBuckets(final ExecutionErrorAccumulator eea, final List<PartyBucket> partyBuckets,
            final BasePK removedBy) {
        var partyBucketInUse = partyBuckets.stream().anyMatch(partyBucket -> partyBucket.getQuantity() != 0);

        if(partyBucketInUse) {
            handleExecutionError(PartyBucketInUseException.class, eea, ExecutionErrors.PartyBucketInUse.name());
        }

        if(eea == null || !eea.hasExecutionErrors()) {
            bucketControl.removePartyBuckets(partyBuckets, removedBy);
        }
    }

    public void removePartyBucket(final ExecutionErrorAccumulator eea, final PartyBucket partyBucket,
            final BasePK removedBy) {
        var partyBucketForUpdate = bucketControl.getPartyBucketForUpdate(partyBucket.getParty(), partyBucket.getItem(),
                partyBucket.getUnitOfMeasureType(), partyBucket.getInventoryCondition(), partyBucket.getInventoryBucketType());

        removePartyBuckets(eea, List.of(partyBucketForUpdate), removedBy);
    }

    public void removePartyBucketsByParty(final ExecutionErrorAccumulator eea, final Party party,
            final BasePK removedBy) {
        removePartyBuckets(eea, bucketControl.getPartyBucketsByPartyForUpdate(party), removedBy);
    }

    public void removePartyBucketsByItem(final ExecutionErrorAccumulator eea, final Item item,
            final BasePK removedBy) {
        removePartyBuckets(eea, bucketControl.getPartyBucketsByItemForUpdate(item), removedBy);
    }

    public void removePartyBucketsByUnitOfMeasureType(final ExecutionErrorAccumulator eea,
            final UnitOfMeasureType unitOfMeasureType, final BasePK removedBy) {
        removePartyBuckets(eea, bucketControl.getPartyBucketsByUnitOfMeasureTypeForUpdate(unitOfMeasureType), removedBy);
    }

    public void removePartyBucketsByInventoryCondition(final ExecutionErrorAccumulator eea,
            final InventoryCondition inventoryCondition, final BasePK removedBy) {
        removePartyBuckets(eea, bucketControl.getPartyBucketsByInventoryConditionForUpdate(inventoryCondition), removedBy);
    }

    public void removePartyBucketsByInventoryBucketType(final ExecutionErrorAccumulator eea,
            final InventoryBucketType inventoryBucketType, final BasePK removedBy) {
        removePartyBuckets(eea, bucketControl.getPartyBucketsByInventoryBucketTypeForUpdate(inventoryBucketType), removedBy);
    }

}
