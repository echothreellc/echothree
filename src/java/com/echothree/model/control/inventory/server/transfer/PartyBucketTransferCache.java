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

import com.echothree.model.control.inventory.common.transfer.PartyBucketTransfer;
import com.echothree.model.control.inventory.server.control.InventoryBucketTypeControl;
import com.echothree.model.control.inventory.server.control.InventoryConditionControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.data.inventory.server.entity.PartyBucket;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class PartyBucketTransferCache
        extends BaseInventoryTransferCache<PartyBucket, PartyBucketTransfer> {

    @Inject
    PartyControl partyControl;

    @Inject
    ItemControl itemControl;

    @Inject
    UomControl uomControl;

    @Inject
    InventoryConditionControl inventoryConditionControl;

    @Inject
    InventoryBucketTypeControl inventoryBucketTypeControl;

    protected PartyBucketTransferCache() {
        super();
    }

    @Override
    public PartyBucketTransfer getTransfer(UserVisit userVisit, PartyBucket partyBucket) {
        var transfer = get(partyBucket);

        if(transfer == null) {
            var unitOfMeasureType = partyBucket.getUnitOfMeasureType();

            transfer = new PartyBucketTransfer(
                    partyControl.getPartyTransfer(userVisit, partyBucket.getParty()),
                    itemControl.getItemTransfer(userVisit, partyBucket.getItem()),
                    uomControl.getUnitOfMeasureTypeTransfer(userVisit, unitOfMeasureType),
                    inventoryConditionControl.getInventoryConditionTransfer(userVisit, partyBucket.getInventoryCondition()),
                    inventoryBucketTypeControl.getInventoryBucketTypeTransfer(userVisit, partyBucket.getInventoryBucketType()),
                    formatUnitOfMeasure(userVisit, unitOfMeasureType.getLastDetail().getUnitOfMeasureKind(), partyBucket.getQuantity()));

            put(userVisit, partyBucket, transfer);
        }

        return transfer;
    }

}
