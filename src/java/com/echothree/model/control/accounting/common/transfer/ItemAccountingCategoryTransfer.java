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

package com.echothree.model.control.accounting.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ItemAccountingCategoryTransfer
        extends BaseTransfer {
    
    private String itemAccountingCategoryName;
    private ItemAccountingCategoryTransfer parentItemAccountingCategory;
    private GlAccountTransfer inventoryGlAccount;
    private GlAccountTransfer salesGlAccount;
    private GlAccountTransfer returnsGlAccount;
    private GlAccountTransfer cogsGlAccount;
    private GlAccountTransfer returnsCogsGlAccount;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    /** Creates a new instance of ItemAccountingCategoryTransfer */
    public ItemAccountingCategoryTransfer(String itemAccountingCategoryName, ItemAccountingCategoryTransfer parentItemAccountingCategory,
            GlAccountTransfer inventoryGlAccount, GlAccountTransfer salesGlAccount, GlAccountTransfer returnsGlAccount,
            GlAccountTransfer cogsGlAccount, GlAccountTransfer returnsCogsGlAccount, Boolean isDefault, Integer sortOrder, String description) {
        this.itemAccountingCategoryName = itemAccountingCategoryName;
        this.parentItemAccountingCategory = parentItemAccountingCategory;
        this.inventoryGlAccount = inventoryGlAccount;
        this.salesGlAccount = salesGlAccount;
        this.returnsGlAccount = returnsGlAccount;
        this.cogsGlAccount = cogsGlAccount;
        this.returnsCogsGlAccount = returnsCogsGlAccount;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getItemAccountingCategoryName() {
        return itemAccountingCategoryName;
    }
    
    public void setItemAccountingCategoryName(String itemAccountingCategoryName) {
        this.itemAccountingCategoryName = itemAccountingCategoryName;
    }
    
    public ItemAccountingCategoryTransfer getParentItemAccountingCategory() {
        return parentItemAccountingCategory;
    }
    
    public void setParentItemAccountingCategory(ItemAccountingCategoryTransfer parentItemAccountingCategory) {
        this.parentItemAccountingCategory = parentItemAccountingCategory;
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
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
