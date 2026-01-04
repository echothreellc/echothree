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

package com.echothree.model.control.inventory.common.transfer;

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.common.transfer.ItemAccountingCategoryTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class InventoryConditionGlAccountTransfer
        extends BaseTransfer {
    
    private InventoryConditionTransfer inventoryCondition;
    private ItemAccountingCategoryTransfer itemAccountingCategory;
    private GlAccountTransfer inventoryGlAccount;
    private GlAccountTransfer salesGlAccount;
    private GlAccountTransfer returnsGlAccount;
    private GlAccountTransfer cogsGlAccount;
    private GlAccountTransfer returnsCogsGlAccount;
    
    /** Creates a new instance of InventoryConditionGlAccountTransfer */
    public InventoryConditionGlAccountTransfer(InventoryConditionTransfer inventoryCondition, ItemAccountingCategoryTransfer itemAccountingCategory,
            GlAccountTransfer inventoryGlAccount, GlAccountTransfer salesGlAccount, GlAccountTransfer returnsGlAccount,
            GlAccountTransfer cogsGlAccount, GlAccountTransfer returnsCogsGlAccount) {
        this.inventoryCondition = inventoryCondition;
        this.itemAccountingCategory = itemAccountingCategory;
        this.inventoryGlAccount = inventoryGlAccount;
        this.salesGlAccount = salesGlAccount;
        this.returnsGlAccount = returnsGlAccount;
        this.cogsGlAccount = cogsGlAccount;
        this.returnsCogsGlAccount = returnsCogsGlAccount;
    }
    
    public InventoryConditionTransfer getInventoryCondition() {
        return inventoryCondition;
    }
    
    public void setInventoryCondition(InventoryConditionTransfer inventoryCondition) {
        this.inventoryCondition = inventoryCondition;
    }
    
    public ItemAccountingCategoryTransfer getItemAccountingCategory() {
        return itemAccountingCategory;
    }
    
    public void setItemAccountingCategory(ItemAccountingCategoryTransfer itemAccountingCategory) {
        this.itemAccountingCategory = itemAccountingCategory;
    }
    
    public GlAccountTransfer getInventoryGlAccount() {
        return inventoryGlAccount;
    }
    
    public void setInventoryGlAccount(GlAccountTransfer inventoryGlAccount) {
        this.inventoryGlAccount = inventoryGlAccount;
    }
    
    public GlAccountTransfer getSalesGlAccount() {
        return salesGlAccount;
    }
    
    public void setSalesGlAccount(GlAccountTransfer salesGlAccount) {
        this.salesGlAccount = salesGlAccount;
    }
    
    public GlAccountTransfer getReturnsGlAccount() {
        return returnsGlAccount;
    }
    
    public void setReturnsGlAccount(GlAccountTransfer returnsGlAccount) {
        this.returnsGlAccount = returnsGlAccount;
    }
    
    public GlAccountTransfer getCogsGlAccount() {
        return cogsGlAccount;
    }
    
    public void setCogsGlAccount(GlAccountTransfer cogsGlAccount) {
        this.cogsGlAccount = cogsGlAccount;
    }
    
    public GlAccountTransfer getReturnsCogsGlAccount() {
        return returnsCogsGlAccount;
    }
    
    public void setReturnsCogsGlAccount(GlAccountTransfer returnsCogsGlAccount) {
        this.returnsCogsGlAccount = returnsCogsGlAccount;
    }
    
}
