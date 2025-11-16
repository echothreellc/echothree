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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class AccountingTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    CurrencyTransferCache currencyTransferCache;
    
    @Inject
    CurrencyDescriptionTransferCache currencyDescriptionTransferCache;
    
    @Inject
    GlAccountClassDescriptionTransferCache glAccountClassDescriptionTransferCache;
    
    @Inject
    GlAccountClassTransferCache glAccountClassTransferCache;
    
    @Inject
    GlAccountDescriptionTransferCache glAccountDescriptionTransferCache;
    
    @Inject
    GlAccountTransferCache glAccountTransferCache;
    
    @Inject
    GlAccountCategoryDescriptionTransferCache glAccountCategoryDescriptionTransferCache;
    
    @Inject
    GlAccountCategoryTransferCache glAccountCategoryTransferCache;
    
    @Inject
    GlResourceTypeDescriptionTransferCache glResourceTypeDescriptionTransferCache;
    
    @Inject
    GlResourceTypeTransferCache glResourceTypeTransferCache;
    
    @Inject
    ItemAccountingCategoryDescriptionTransferCache itemAccountingCategoryDescriptionTransferCache;
    
    @Inject
    ItemAccountingCategoryTransferCache itemAccountingCategoryTransferCache;
    
    @Inject
    TransactionEntityRoleTransferCache transactionEntityRoleTransferCache;
    
    @Inject
    TransactionEntityRoleTypeTransferCache transactionEntityRoleTypeTransferCache;
    
    @Inject
    TransactionGlEntryTransferCache transactionGlEntryTransferCache;
    
    @Inject
    TransactionGlAccountTransferCache transactionGlAccountTransferCache;
    
    @Inject
    TransactionGlAccountCategoryTransferCache transactionGlAccountCategoryTransferCache;
    
    @Inject
    TransactionGroupTransferCache transactionGroupTransferCache;
    
    @Inject
    TransactionTransferCache transactionTransferCache;
    
    @Inject
    TransactionTypeTransferCache transactionTypeTransferCache;
    
    @Inject
    GlAccountTypeTransferCache glAccountTypeTransferCache;
    
    @Inject
    TransactionTypeDescriptionTransferCache transactionTypeDescriptionTransferCache;
    
    @Inject
    TransactionGlAccountCategoryDescriptionTransferCache transactionGlAccountCategoryDescriptionTransferCache;
    
    @Inject
    TransactionEntityRoleTypeDescriptionTransferCache transactionEntityRoleTypeDescriptionTransferCache;
    
    @Inject
    SymbolPositionDescriptionTransferCache symbolPositionDescriptionTransferCache;
    
    @Inject
    SymbolPositionTransferCache symbolPositionTransferCache;
    
    @Inject
    TransactionTimeTypeTransferCache transactionTimeTypeTransferCache;
    
    @Inject
    TransactionTimeTypeDescriptionTransferCache transactionTimeTypeDescriptionTransferCache;
    
    @Inject
    TransactionTimeTransferCache transactionTimeTransferCache;
    
    
    /** Creates a new instance of AccountingTransferCaches */
    protected AccountingTransferCaches() {
        super();
    }
    
    public CurrencyTransferCache getCurrencyTransferCache() {
        return currencyTransferCache;
    }
    
    public CurrencyDescriptionTransferCache getCurrencyDescriptionTransferCache() {
        return currencyDescriptionTransferCache;
    }
    
    public GlAccountClassDescriptionTransferCache getGlAccountClassDescriptionTransferCache() {
        return glAccountClassDescriptionTransferCache;
    }
    
    public GlAccountClassTransferCache getGlAccountClassTransferCache() {
        return glAccountClassTransferCache;
    }
    
    public GlAccountDescriptionTransferCache getGlAccountDescriptionTransferCache() {
        return glAccountDescriptionTransferCache;
    }
    
    public GlAccountTransferCache getGlAccountTransferCache() {
        return glAccountTransferCache;
    }
    
    public GlAccountCategoryDescriptionTransferCache getGlAccountCategoryDescriptionTransferCache() {
        return glAccountCategoryDescriptionTransferCache;
    }
    
    public GlAccountCategoryTransferCache getGlAccountCategoryTransferCache() {
        return glAccountCategoryTransferCache;
    }
    
    public GlResourceTypeDescriptionTransferCache getGlResourceTypeDescriptionTransferCache() {
        return glResourceTypeDescriptionTransferCache;
    }
    
    public GlResourceTypeTransferCache getGlResourceTypeTransferCache() {
        return glResourceTypeTransferCache;
    }
    
    public ItemAccountingCategoryDescriptionTransferCache getItemAccountingCategoryDescriptionTransferCache() {
        return itemAccountingCategoryDescriptionTransferCache;
    }
    
    public ItemAccountingCategoryTransferCache getItemAccountingCategoryTransferCache() {
        return itemAccountingCategoryTransferCache;
    }
    
    public TransactionEntityRoleTransferCache getTransactionEntityRoleTransferCache() {
        return transactionEntityRoleTransferCache;
    }
    
    public TransactionEntityRoleTypeTransferCache getTransactionEntityRoleTypeTransferCache() {
        return transactionEntityRoleTypeTransferCache;
    }
    
    public TransactionGlEntryTransferCache getTransactionGlEntryTransferCache() {
        return transactionGlEntryTransferCache;
    }
    
    public TransactionGlAccountTransferCache getTransactionGlAccountTransferCache() {
        return transactionGlAccountTransferCache;
    }
    
    public TransactionGlAccountCategoryTransferCache getTransactionGlAccountCategoryTransferCache() {
        return transactionGlAccountCategoryTransferCache;
    }
    
    public TransactionGroupTransferCache getTransactionGroupTransferCache() {
        return transactionGroupTransferCache;
    }
    
    public TransactionTransferCache getTransactionTransferCache() {
        return transactionTransferCache;
    }
    
    public TransactionTypeTransferCache getTransactionTypeTransferCache() {
        return transactionTypeTransferCache;
    }
    
    public GlAccountTypeTransferCache getGlAccountTypeTransferCache() {
        return glAccountTypeTransferCache;
    }
    
    public TransactionTypeDescriptionTransferCache getTransactionTypeDescriptionTransferCache() {
        return transactionTypeDescriptionTransferCache;
    }
    
    public TransactionGlAccountCategoryDescriptionTransferCache getTransactionGlAccountCategoryDescriptionTransferCache() {
        return transactionGlAccountCategoryDescriptionTransferCache;
    }
    
    public TransactionEntityRoleTypeDescriptionTransferCache getTransactionEntityRoleTypeDescriptionTransferCache() {
        return transactionEntityRoleTypeDescriptionTransferCache;
    }
    
    public SymbolPositionDescriptionTransferCache getSymbolPositionDescriptionTransferCache() {
        return symbolPositionDescriptionTransferCache;
    }
    
    public SymbolPositionTransferCache getSymbolPositionTransferCache() {
        return symbolPositionTransferCache;
    }

    public TransactionTimeTypeTransferCache getTransactionTimeTypeTransferCache() {
        return transactionTimeTypeTransferCache;
    }

    public TransactionTimeTypeDescriptionTransferCache getTransactionTimeTypeDescriptionTransferCache() {
        return transactionTimeTypeDescriptionTransferCache;
    }

    public TransactionTimeTransferCache getTransactionTimeTransferCache() {
        return transactionTimeTransferCache;
    }

}
