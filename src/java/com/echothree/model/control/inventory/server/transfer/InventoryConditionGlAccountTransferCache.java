// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.common.transfer.ItemAccountingCategoryTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionGlAccountTransfer;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.control.item.server.control.ItemControl;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.inventory.server.entity.InventoryConditionGlAccount;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class InventoryConditionGlAccountTransferCache
        extends BaseInventoryTransferCache<InventoryConditionGlAccount, InventoryConditionGlAccountTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    ItemControl itemControl = Session.getModelController(ItemControl.class);
    
    /** Creates a new instance of InventoryConditionGlAccountTransferCache */
    public InventoryConditionGlAccountTransferCache(UserVisit userVisit, InventoryControl inventoryControl) {
        super(userVisit, inventoryControl);
    }
    
    @Override
    public InventoryConditionGlAccountTransfer getTransfer(InventoryConditionGlAccount inventoryConditionGlAccount) {
        InventoryConditionGlAccountTransfer inventoryConditionGlAccountTransfer = get(inventoryConditionGlAccount);
        
        if(inventoryConditionGlAccountTransfer == null) {
            InventoryConditionTransfer inventoryConditionTransfer = inventoryControl.getInventoryConditionTransfer(userVisit, inventoryConditionGlAccount.getInventoryCondition());
            ItemAccountingCategoryTransfer itemAccountingCategoryTransfer = accountingControl.getItemAccountingCategoryTransfer(userVisit, inventoryConditionGlAccount.getItemAccountingCategory());
            GlAccount inventoryGlAccount = inventoryConditionGlAccount.getInventoryGlAccount();
            GlAccountTransfer inventoryGlAccountTransfer = inventoryGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, inventoryGlAccount);
            GlAccount salesGlAccount = inventoryConditionGlAccount.getSalesGlAccount();
            GlAccountTransfer salesGlAccountTransfer = salesGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, salesGlAccount);
            GlAccount returnsGlAccount = inventoryConditionGlAccount.getReturnsGlAccount();
            GlAccountTransfer returnsGlAccountTransfer = returnsGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, returnsGlAccount);
            GlAccount cogsGlAccount = inventoryConditionGlAccount.getCogsGlAccount();
            GlAccountTransfer cogsGlAccountTransfer = cogsGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, cogsGlAccount);
            GlAccount returnsCogsGlAccount = inventoryConditionGlAccount.getReturnsCogsGlAccount();
            GlAccountTransfer returnsCogsGlAccountTransfer = returnsCogsGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, returnsCogsGlAccount);
            
            inventoryConditionGlAccountTransfer = new InventoryConditionGlAccountTransfer(inventoryConditionTransfer,
                    itemAccountingCategoryTransfer, inventoryGlAccountTransfer, salesGlAccountTransfer, returnsGlAccountTransfer,
                    cogsGlAccountTransfer, returnsCogsGlAccountTransfer);
            put(inventoryConditionGlAccount, inventoryConditionGlAccountTransfer);
        }
        
        return inventoryConditionGlAccountTransfer;
    }
    
}
