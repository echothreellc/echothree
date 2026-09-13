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

import com.echothree.model.control.inventory.common.transfer.InventoryLayerTransfer;
import com.echothree.model.control.inventory.server.control.InventoryCostingPoolControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.inventory.server.entity.InventoryLayer;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.string.AmountUtils;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class InventoryLayerTransferCache
        extends BaseInventoryTransferCache<InventoryLayer, InventoryLayerTransfer> {

    @Inject
    InventoryCostingPoolControl inventoryCostingPoolControl;

    @Inject
    PartyControl partyControl;

    protected InventoryLayerTransferCache() {
        super();

        setIncludeEntityInstance(true);
    }

    @Override
    public InventoryLayerTransfer getTransfer(UserVisit userVisit, InventoryLayer inventoryLayer) {
        var transfer = get(inventoryLayer);

        if(transfer == null) {
            var inventoryLayerDetail = inventoryLayer.getLastDetail();
            var inventoryCostingPool = inventoryLayerDetail.getInventoryCostingPool();
            var inventoryCostingPoolDetail = inventoryCostingPool.getLastDetail();
            var inventoryCostingPoolTransfer = inventoryCostingPoolControl.getInventoryCostingPoolTransfer(userVisit, inventoryCostingPool);
            var inventoryLayerSequence = inventoryLayerDetail.getInventoryLayerSequence();
            var unformattedReceiptQuantity = inventoryLayerDetail.getReceiptQuantity();
            var receiptQuantity = formatUnitOfMeasure(userVisit, inventoryCostingPoolDetail.getItem().getLastDetail().getUnitOfMeasureKind(),
                    unformattedReceiptQuantity);
            var unformattedUnitCost = inventoryLayerDetail.getUnitCost();
            var unitCost = unformattedUnitCost == null ? null : AmountUtils.getInstance().formatCostUnit(
                    partyControl.getPreferredCurrency(inventoryCostingPoolDetail.getCompanyParty()), unformattedUnitCost);
            var description = inventoryLayerDetail.getDescription();

            // TODO: Include the InventoryTransactionLineTransfer when transaction-line transfers are implemented.
            transfer = new InventoryLayerTransfer(inventoryCostingPoolTransfer, inventoryLayerSequence, unformattedReceiptQuantity,
                    receiptQuantity, unformattedUnitCost, unitCost, description);
            put(userVisit, inventoryLayer, transfer);
        }

        return transfer;
    }

}
