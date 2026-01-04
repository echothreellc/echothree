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

public class TransactionGlAccountCategoryTransfer
        extends BaseTransfer {
    
    private TransactionTypeTransfer transactionType;
    private String transactionGlAccountCategoryName;
    private GlAccountCategoryTransfer glAccountCategory;
    private Integer sortOrder;
    private GlAccountTransfer glAccount;
    private String description;
    
    /** Creates a new instance of TransactionGlAccountCategoryTransfer */
    public TransactionGlAccountCategoryTransfer(TransactionTypeTransfer transactionType, String transactionGlAccountCategoryName,
            GlAccountCategoryTransfer glAccountCategory, Integer sortOrder, GlAccountTransfer glAccount, String description) {
        this.transactionType = transactionType;
        this.transactionGlAccountCategoryName = transactionGlAccountCategoryName;
        this.glAccountCategory = glAccountCategory;
        this.sortOrder = sortOrder;
        this.glAccount = glAccount;
        this.description = description;
    }
    
    public TransactionTypeTransfer getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(TransactionTypeTransfer transactionType) {
        this.transactionType = transactionType;
    }
    
    public String getTransactionGlAccountCategoryName() {
        return transactionGlAccountCategoryName;
    }
    
    public void setTransactionGlAccountCategoryName(String transactionGlAccountCategoryName) {
        this.transactionGlAccountCategoryName = transactionGlAccountCategoryName;
    }
    
    public GlAccountCategoryTransfer getGlAccountCategory() {
        return glAccountCategory;
    }
    
    public void setGlAccountCategory(GlAccountCategoryTransfer glAccountCategory) {
        this.glAccountCategory = glAccountCategory;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public GlAccountTransfer getGlAccount() {
        return glAccount;
    }
    
    public void setGlAccount(GlAccountTransfer glAccount) {
        this.glAccount = glAccount;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
