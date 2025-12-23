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

public class GlAccountTransfer
        extends BaseTransfer {
    
    private String glAccountName;
    private GlAccountTransfer parentGlAccount;
    private GlAccountTypeTransfer glAccountType;
    private GlAccountClassTransfer glAccountClass;
    private GlAccountCategoryTransfer glAccountCategory;
    private GlResourceTypeTransfer glResourceType;
    private CurrencyTransfer currency;
    private Boolean isDefault;
    private String description;
    
    /** Creates a new instance of GlAccountTransfer */
    public GlAccountTransfer(String glAccountName, GlAccountTransfer parentGlAccount, GlAccountTypeTransfer glAccountType,
            GlAccountClassTransfer glAccountClass, GlAccountCategoryTransfer glAccountCategory,
            GlResourceTypeTransfer glResourceType, CurrencyTransfer currency, Boolean isDefault, String description) {
        this.glAccountName = glAccountName;
        this.parentGlAccount = parentGlAccount;
        this.glAccountType = glAccountType;
        this.glAccountClass = glAccountClass;
        this.glAccountCategory = glAccountCategory;
        this.glResourceType = glResourceType;
        this.currency = currency;
        this.isDefault = isDefault;
        this.description = description;
    }
    
    public String getGlAccountName() {
        return glAccountName;
    }
    
    public void setGlAccountName(String glAccountName) {
        this.glAccountName = glAccountName;
    }
    
    public GlAccountTransfer getParentGlAccount() {
        return parentGlAccount;
    }
    
    public void setParentGlAccount(GlAccountTransfer parentGlAccount) {
        this.parentGlAccount = parentGlAccount;
    }
    
    public GlAccountTypeTransfer getGlAccountType() {
        return glAccountType;
    }
    
    public void setGlAccountType(GlAccountTypeTransfer glAccountType) {
        this.glAccountType = glAccountType;
    }
    
    public GlAccountClassTransfer getGlAccountClass() {
        return glAccountClass;
    }
    
    public void setGlAccountClass(GlAccountClassTransfer glAccountClass) {
        this.glAccountClass = glAccountClass;
    }
    
    public GlAccountCategoryTransfer getGlAccountCategory() {
        return glAccountCategory;
    }
    
    public void setGlAccountCategory(GlAccountCategoryTransfer glAccountCategory) {
        this.glAccountCategory = glAccountCategory;
    }
    
    public GlResourceTypeTransfer getGlResourceType() {
        return glResourceType;
    }
    
    public void setGlResourceType(GlResourceTypeTransfer glResourceType) {
        this.glResourceType = glResourceType;
    }
    
    public CurrencyTransfer getCurrency() {
        return currency;
    }
    
    public void setCurrency(CurrencyTransfer currency) {
        this.currency = currency;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
