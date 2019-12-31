// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.server.InventoryControl;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.control.uom.common.transfer.UnitOfMeasureTypeTransfer;
import com.echothree.model.control.uom.server.UomControl;
import com.echothree.model.control.vendor.common.transfer.VendorItemCostTransfer;
import com.echothree.model.control.vendor.common.transfer.VendorItemTransfer;
import com.echothree.model.control.vendor.server.VendorControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.vendor.server.entity.VendorItem;
import com.echothree.model.data.vendor.server.entity.VendorItemCost;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class VendorItemCostTransferCache
        extends BaseVendorTransferCache<VendorItemCost, VendorItemCostTransfer> {
    
    InventoryControl inventoryControl;
    PartyControl partyControl;
    UomControl uomControl;
    
    /** Creates a new instance of VendorItemCostTransferCache */
    public VendorItemCostTransferCache(UserVisit userVisit, VendorControl vendorControl) {
        super(userVisit, vendorControl);
        
        inventoryControl = (InventoryControl)Session.getModelController(InventoryControl.class);
        partyControl = (PartyControl)Session.getModelController(PartyControl.class);
        uomControl = (UomControl)Session.getModelController(UomControl.class);
    }
    
    public VendorItemCostTransfer getVendorItemCostTransfer(VendorItemCost vendorItemCost) {
        VendorItemCostTransfer vendorItemCostTransfer = get(vendorItemCost);
        
        if(vendorItemCostTransfer == null) {
            VendorItem vendorItem = vendorItemCost.getVendorItem();
            VendorItemTransfer vendorItemTransfer = vendorControl.getVendorItemTransfer(userVisit, vendorItem);
            InventoryConditionTransfer inventoryConditionTransfer = inventoryControl.getInventoryConditionTransfer(userVisit, vendorItemCost.getInventoryCondition());
            UnitOfMeasureTypeTransfer unitOfMeasureTypeTransfer = uomControl.getUnitOfMeasureTypeTransfer(userVisit, vendorItemCost.getUnitOfMeasureType());
            Long unformattedUnitCost = vendorItemCost.getUnitCost();
            String unitCost = AmountUtils.getInstance().formatCostUnit(partyControl.getPreferredCurrency(vendorItem.getLastDetail().getVendorParty()),
                    unformattedUnitCost);
            
            vendorItemCostTransfer = new VendorItemCostTransfer(vendorItemTransfer, inventoryConditionTransfer, unitOfMeasureTypeTransfer, unformattedUnitCost,
                    unitCost);
            put(vendorItemCost, vendorItemCostTransfer);
        }
        
        return vendorItemCostTransfer;
    }
    
}
