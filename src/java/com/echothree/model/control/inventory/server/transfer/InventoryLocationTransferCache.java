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

import com.echothree.model.control.inventory.common.transfer.InventoryLocationTransfer;
import com.echothree.model.control.inventory.server.control.InventoryConditionControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.warehouse.server.control.WarehouseControl;
import com.echothree.model.data.inventory.server.entity.InventoryLocation;
import com.echothree.model.data.user.server.entity.UserVisit;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class InventoryLocationTransferCache
        extends BaseInventoryTransferCache<InventoryLocation, InventoryLocationTransfer> {

    @Inject
    InventoryConditionControl inventoryConditionControl;

    @Inject
    ItemControl itemControl;

    @Inject
    PartyControl partyControl;

    @Inject
    UomControl uomControl;

    @Inject
    WarehouseControl warehouseControl;

    protected InventoryLocationTransferCache() {
        super();
    }

    @Override
    public InventoryLocationTransfer getTransfer(UserVisit userVisit, InventoryLocation inventoryLocation) {
        var transfer = get(inventoryLocation);

        if(transfer == null) {
            transfer = new InventoryLocationTransfer(
                    warehouseControl.getLocationTransfer(userVisit, inventoryLocation.getLocation()),
                    partyControl.getPartyTransfer(userVisit, inventoryLocation.getOwnerParty()),
                    itemControl.getItemTransfer(userVisit, inventoryLocation.getItem()),
                    uomControl.getUnitOfMeasureTypeTransfer(userVisit, inventoryLocation.getUnitOfMeasureType()),
                    inventoryConditionControl.getInventoryConditionTransfer(userVisit, inventoryLocation.getInventoryCondition()));
            put(userVisit, inventoryLocation, transfer);
        }

        return transfer;
    }

}
