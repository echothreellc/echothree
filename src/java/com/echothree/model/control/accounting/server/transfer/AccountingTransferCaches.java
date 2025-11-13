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

import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class AccountingTransferCaches
        extends BaseTransferCaches {
    
    protected CurrencyTransferCache currencyTransferCache;
    protected CurrencyDescriptionTransferCache currencyDescriptionTransferCache;
    protected GlAccountClassDescriptionTransferCache glAccountClassDescriptionTransferCache;
    protected GlAccountClassTransferCache glAccountClassTransferCache;
    protected GlAccountDescriptionTransferCache glAccountDescriptionTransferCache;
    protected GlAccountTransferCache glAccountTransferCache;
    protected GlAccountCategoryDescriptionTransferCache glAccountCategoryDescriptionTransferCache;
    protected GlAccountCategoryTransferCache glAccountCategoryTransferCache;
    protected GlResourceTypeDescriptionTransferCache glResourceTypeDescriptionTransferCache;
    protected GlResourceTypeTransferCache glResourceTypeTransferCache;
    protected ItemAccountingCategoryDescriptionTransferCache itemAccountingCategoryDescriptionTransferCache;
    protected ItemAccountingCategoryTransferCache itemAccountingCategoryTransferCache;
    protected TransactionEntityRoleTransferCache transactionEntityRoleTransferCache;
    protected TransactionEntityRoleTypeTransferCache transactionEntityRoleTypeTransferCache;
    protected TransactionGlEntryTransferCache transactionGlEntryTransferCache;
    protected TransactionGlAccountTransferCache transactionGlAccountTransferCache;
    protected TransactionGlAccountCategoryTransferCache transactionGlAccountCategoryTransferCache;
    protected TransactionGroupTransferCache transactionGroupTransferCache;
    protected TransactionTransferCache transactionTransferCache;
    protected TransactionTypeTransferCache transactionTypeTransferCache;
    protected GlAccountTypeTransferCache glAccountTypeTransferCache;
    protected TransactionTypeDescriptionTransferCache transactionTypeDescriptionTransferCache;
    protected TransactionGlAccountCategoryDescriptionTransferCache transactionGlAccountCategoryDescriptionTransferCache;
    protected TransactionEntityRoleTypeDescriptionTransferCache transactionEntityRoleTypeDescriptionTransferCache;
    protected SymbolPositionDescriptionTransferCache symbolPositionDescriptionTransferCache;
    protected SymbolPositionTransferCache symbolPositionTransferCache;
    protected TransactionTimeTypeTransferCache transactionTimeTypeTransferCache;
    protected TransactionTimeTypeDescriptionTransferCache transactionTimeTypeDescriptionTransferCache;
    protected TransactionTimeTransferCache transactionTimeTransferCache;
    
    /** Creates a new instance of AccountingTransferCaches */
    public AccountingTransferCaches() {
        super();
    }
    
    public CurrencyTransferCache getCurrencyTransferCache() {
        if(currencyTransferCache == null)
            currencyTransferCache = new CurrencyTransferCache();
        
        return currencyTransferCache;
    }
    
    public CurrencyDescriptionTransferCache getCurrencyDescriptionTransferCache() {
        if(currencyDescriptionTransferCache == null)
            currencyDescriptionTransferCache = new CurrencyDescriptionTransferCache();
        
        return currencyDescriptionTransferCache;
    }
    
    public GlAccountClassDescriptionTransferCache getGlAccountClassDescriptionTransferCache() {
        if(glAccountClassDescriptionTransferCache == null)
            glAccountClassDescriptionTransferCache = new GlAccountClassDescriptionTransferCache();
        
        return glAccountClassDescriptionTransferCache;
    }
    
    public GlAccountClassTransferCache getGlAccountClassTransferCache() {
        if(glAccountClassTransferCache == null)
            glAccountClassTransferCache = new GlAccountClassTransferCache();
        
        return glAccountClassTransferCache;
    }
    
    public GlAccountDescriptionTransferCache getGlAccountDescriptionTransferCache() {
        if(glAccountDescriptionTransferCache == null)
            glAccountDescriptionTransferCache = new GlAccountDescriptionTransferCache();
        
        return glAccountDescriptionTransferCache;
    }
    
    public GlAccountTransferCache getGlAccountTransferCache() {
        if(glAccountTransferCache == null)
            glAccountTransferCache = new GlAccountTransferCache();
        
        return glAccountTransferCache;
    }
    
    public GlAccountCategoryDescriptionTransferCache getGlAccountCategoryDescriptionTransferCache() {
        if(glAccountCategoryDescriptionTransferCache == null)
            glAccountCategoryDescriptionTransferCache = new GlAccountCategoryDescriptionTransferCache();
        
        return glAccountCategoryDescriptionTransferCache;
    }
    
    public GlAccountCategoryTransferCache getGlAccountCategoryTransferCache() {
        if(glAccountCategoryTransferCache == null)
            glAccountCategoryTransferCache = new GlAccountCategoryTransferCache();
        
        return glAccountCategoryTransferCache;
    }
    
    public GlResourceTypeDescriptionTransferCache getGlResourceTypeDescriptionTransferCache() {
        if(glResourceTypeDescriptionTransferCache == null)
            glResourceTypeDescriptionTransferCache = new GlResourceTypeDescriptionTransferCache();
        
        return glResourceTypeDescriptionTransferCache;
    }
    
    public GlResourceTypeTransferCache getGlResourceTypeTransferCache() {
        if(glResourceTypeTransferCache == null)
            glResourceTypeTransferCache = new GlResourceTypeTransferCache();
        
        return glResourceTypeTransferCache;
    }
    
    public ItemAccountingCategoryDescriptionTransferCache getItemAccountingCategoryDescriptionTransferCache() {
        if(itemAccountingCategoryDescriptionTransferCache == null)
            itemAccountingCategoryDescriptionTransferCache = new ItemAccountingCategoryDescriptionTransferCache();
        
        return itemAccountingCategoryDescriptionTransferCache;
    }
    
    public ItemAccountingCategoryTransferCache getItemAccountingCategoryTransferCache() {
        if(itemAccountingCategoryTransferCache == null)
            itemAccountingCategoryTransferCache = new ItemAccountingCategoryTransferCache();
        
        return itemAccountingCategoryTransferCache;
    }
    
    public TransactionEntityRoleTransferCache getTransactionEntityRoleTransferCache() {
        if(transactionEntityRoleTransferCache == null)
            transactionEntityRoleTransferCache = new TransactionEntityRoleTransferCache();
        
        return transactionEntityRoleTransferCache;
    }
    
    public TransactionEntityRoleTypeTransferCache getTransactionEntityRoleTypeTransferCache() {
        if(transactionEntityRoleTypeTransferCache == null)
            transactionEntityRoleTypeTransferCache = new TransactionEntityRoleTypeTransferCache();
        
        return transactionEntityRoleTypeTransferCache;
    }
    
    public TransactionGlEntryTransferCache getTransactionGlEntryTransferCache() {
        if(transactionGlEntryTransferCache == null)
            transactionGlEntryTransferCache = new TransactionGlEntryTransferCache();
        
        return transactionGlEntryTransferCache;
    }
    
    public TransactionGlAccountTransferCache getTransactionGlAccountTransferCache() {
        if(transactionGlAccountTransferCache == null)
            transactionGlAccountTransferCache = new TransactionGlAccountTransferCache();
        
        return transactionGlAccountTransferCache;
    }
    
    public TransactionGlAccountCategoryTransferCache getTransactionGlAccountCategoryTransferCache() {
        if(transactionGlAccountCategoryTransferCache == null)
            transactionGlAccountCategoryTransferCache = new TransactionGlAccountCategoryTransferCache();
        
        return transactionGlAccountCategoryTransferCache;
    }
    
    public TransactionGroupTransferCache getTransactionGroupTransferCache() {
        if(transactionGroupTransferCache == null)
            transactionGroupTransferCache = new TransactionGroupTransferCache();
        
        return transactionGroupTransferCache;
    }
    
    public TransactionTransferCache getTransactionTransferCache() {
        if(transactionTransferCache == null)
            transactionTransferCache = new TransactionTransferCache();
        
        return transactionTransferCache;
    }
    
    public TransactionTypeTransferCache getTransactionTypeTransferCache() {
        if(transactionTypeTransferCache == null)
            transactionTypeTransferCache = new TransactionTypeTransferCache();
        
        return transactionTypeTransferCache;
    }
    
    public GlAccountTypeTransferCache getGlAccountTypeTransferCache() {
        if(glAccountTypeTransferCache == null)
            glAccountTypeTransferCache = new GlAccountTypeTransferCache();
        
        return glAccountTypeTransferCache;
    }
    
    public TransactionTypeDescriptionTransferCache getTransactionTypeDescriptionTransferCache() {
        if(transactionTypeDescriptionTransferCache == null)
            transactionTypeDescriptionTransferCache = new TransactionTypeDescriptionTransferCache();
        
        return transactionTypeDescriptionTransferCache;
    }
    
    public TransactionGlAccountCategoryDescriptionTransferCache getTransactionGlAccountCategoryDescriptionTransferCache() {
        if(transactionGlAccountCategoryDescriptionTransferCache == null)
            transactionGlAccountCategoryDescriptionTransferCache = new TransactionGlAccountCategoryDescriptionTransferCache();
        
        return transactionGlAccountCategoryDescriptionTransferCache;
    }
    
    public TransactionEntityRoleTypeDescriptionTransferCache getTransactionEntityRoleTypeDescriptionTransferCache() {
        if(transactionEntityRoleTypeDescriptionTransferCache == null)
            transactionEntityRoleTypeDescriptionTransferCache = new TransactionEntityRoleTypeDescriptionTransferCache();
        
        return transactionEntityRoleTypeDescriptionTransferCache;
    }
    
    public SymbolPositionDescriptionTransferCache getSymbolPositionDescriptionTransferCache() {
        if(symbolPositionDescriptionTransferCache == null)
            symbolPositionDescriptionTransferCache = new SymbolPositionDescriptionTransferCache();
        
        return symbolPositionDescriptionTransferCache;
    }
    
    public SymbolPositionTransferCache getSymbolPositionTransferCache() {
        if(symbolPositionTransferCache == null)
            symbolPositionTransferCache = new SymbolPositionTransferCache();
        
        return symbolPositionTransferCache;
    }

    public TransactionTimeTypeTransferCache getTransactionTimeTypeTransferCache() {
        if(transactionTimeTypeTransferCache == null)
            transactionTimeTypeTransferCache = new TransactionTimeTypeTransferCache();

        return transactionTimeTypeTransferCache;
    }

    public TransactionTimeTypeDescriptionTransferCache getTransactionTimeTypeDescriptionTransferCache() {
        if(transactionTimeTypeDescriptionTransferCache == null)
            transactionTimeTypeDescriptionTransferCache = new TransactionTimeTypeDescriptionTransferCache();

        return transactionTimeTypeDescriptionTransferCache;
    }

    public TransactionTimeTransferCache getTransactionTimeTransferCache() {
        if(transactionTimeTransferCache == null)
            transactionTimeTransferCache = new TransactionTimeTransferCache();

        return transactionTimeTransferCache;
    }

}
