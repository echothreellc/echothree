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

package com.echothree.model.control.inventory.server.transfer;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.inventory.common.transfer.InventoryConditionGlAccountTransfer;
import com.echothree.model.control.inventory.server.control.InventoryControl;
import com.echothree.model.data.inventory.server.entity.InventoryConditionGlAccount;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class InventoryConditionGlAccountTransferCache
        extends BaseInventoryTransferCache<InventoryConditionGlAccount, InventoryConditionGlAccountTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    InventoryControl inventoryControl = Session.getModelController(InventoryControl.class);
    
    /** Creates a new instance of InventoryConditionGlAccountTransferCache */
    public InventoryConditionGlAccountTransferCache() {
        super();
    }
    
    @Override
    public InventoryConditionGlAccountTransfer getTransfer(UserVisit userVisit, InventoryConditionGlAccount inventoryConditionGlAccount) {
        var inventoryConditionGlAccountTransfer = get(inventoryConditionGlAccount);
        
        if(inventoryConditionGlAccountTransfer == null) {
            var inventoryConditionTransfer = inventoryControl.getInventoryConditionTransfer(userVisit, inventoryConditionGlAccount.getInventoryCondition());
            var itemAccountingCategoryTransfer = accountingControl.getItemAccountingCategoryTransfer(userVisit, inventoryConditionGlAccount.getItemAccountingCategory());
            var inventoryGlAccount = inventoryConditionGlAccount.getInventoryGlAccount();
            var inventoryGlAccountTransfer = inventoryGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, inventoryGlAccount);
            var salesGlAccount = inventoryConditionGlAccount.getSalesGlAccount();
            var salesGlAccountTransfer = salesGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, salesGlAccount);
            var returnsGlAccount = inventoryConditionGlAccount.getReturnsGlAccount();
            var returnsGlAccountTransfer = returnsGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, returnsGlAccount);
            var cogsGlAccount = inventoryConditionGlAccount.getCogsGlAccount();
            var cogsGlAccountTransfer = cogsGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, cogsGlAccount);
            var returnsCogsGlAccount = inventoryConditionGlAccount.getReturnsCogsGlAccount();
            var returnsCogsGlAccountTransfer = returnsCogsGlAccount == null? null: accountingControl.getGlAccountTransfer(userVisit, returnsCogsGlAccount);
            
            inventoryConditionGlAccountTransfer = new InventoryConditionGlAccountTransfer(inventoryConditionTransfer,
                    itemAccountingCategoryTransfer, inventoryGlAccountTransfer, salesGlAccountTransfer, returnsGlAccountTransfer,
                    cogsGlAccountTransfer, returnsCogsGlAccountTransfer);
            put(userVisit, inventoryConditionGlAccount, inventoryConditionGlAccountTransfer);
        }
        
        return inventoryConditionGlAccountTransfer;
    }
    
}
