// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.vendor.server.transfer;

import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.uom.server.control.UomControl;
import com.echothree.model.control.vendor.common.transfer.VendorItemCostTransfer;
import com.echothree.model.control.vendor.server.control.VendorControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.VendorItemCost;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class VendorItemCostTransferCache
        extends BaseVendorTransferCache<VendorItemCost, VendorItemCostTransfer> {
    
    InventoryControl inventoryControl;
    PartyControl partyControl;
    UomControl uomControl;
    
    /** Creates a new instance of VendorItemCostTransferCache */
    public VendorItemCostTransferCache(VendorControl vendorControl) {
        super(vendorControl);
        
        inventoryControl = Session.getModelController(InventoryControl.class);
        partyControl = Session.getModelController(PartyControl.class);
        uomControl = Session.getModelController(UomControl.class);
    }
    
    public VendorItemCostTransfer getVendorItemCostTransfer(UserVisit userVisit, VendorItemCost vendorItemCost) {
        var vendorItemCostTransfer = get(vendorItemCost);
        
        if(vendorItemCostTransfer == null) {
            var vendorItem = vendorItemCost.getVendorItem();
            var vendorItemTransfer = vendorControl.getVendorItemTransfer(userVisit, vendorItem);
            var inventoryConditionTransfer = inventoryControl.getInventoryConditionTransfer(userVisit, vendorItemCost.getInventoryCondition());
            var unitOfMeasureTypeTransfer = uomControl.getUnitOfMeasureTypeTransfer(userVisit, vendorItemCost.getUnitOfMeasureType());
            var unformattedUnitCost = vendorItemCost.getUnitCost();
            var unitCost = AmountUtils.getInstance().formatCostUnit(partyControl.getPreferredCurrency(vendorItem.getLastDetail().getVendorParty()),
                    unformattedUnitCost);
            
            vendorItemCostTransfer = new VendorItemCostTransfer(vendorItemTransfer, inventoryConditionTransfer, unitOfMeasureTypeTransfer, unformattedUnitCost,
                    unitCost);
            put(userVisit, vendorItemCost, vendorItemCostTransfer);
        }
        
        return vendorItemCostTransfer;
    }
    
}
