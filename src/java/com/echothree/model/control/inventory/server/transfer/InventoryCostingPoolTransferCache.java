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

import com.echothree.model.control.inventory.common.transfer.InventoryCostingPoolTransfer;
import com.echothree.model.control.inventory.server.control.InventoryConditionControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.inventory.server.entity.InventoryCostingPool;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class InventoryCostingPoolTransferCache
        extends BaseInventoryTransferCache<InventoryCostingPool, InventoryCostingPoolTransfer> {

    @Inject
    InventoryConditionControl inventoryConditionControl;

    @Inject
    ItemControl itemControl;

    @Inject
    PartyControl partyControl;

    protected InventoryCostingPoolTransferCache() {
        super();

        setIncludeEntityInstance(true);
    }

    @Override
    public InventoryCostingPoolTransfer getTransfer(UserVisit userVisit, InventoryCostingPool inventoryCostingPool) {
        var transfer = get(inventoryCostingPool);

        if(transfer == null) {
            var detail = inventoryCostingPool.getLastDetail();

            transfer = new InventoryCostingPoolTransfer(
                    partyControl.getCompanyTransfer(userVisit, detail.getCompanyParty()),
                    itemControl.getItemTransfer(userVisit, detail.getItem()),
                    inventoryConditionControl.getInventoryConditionTransfer(userVisit, detail.getInventoryCondition()));
            put(userVisit, inventoryCostingPool, transfer);
        }

        return transfer;
    }

}
