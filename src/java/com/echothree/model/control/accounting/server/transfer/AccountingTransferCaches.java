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
import javax.enterprise.inject.spi.CDI;

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
            currencyTransferCache = CDI.current().select(CurrencyTransferCache.class).get();
        
        return currencyTransferCache;
    }
    
    public CurrencyDescriptionTransferCache getCurrencyDescriptionTransferCache() {
        if(currencyDescriptionTransferCache == null)
            currencyDescriptionTransferCache = CDI.current().select(CurrencyDescriptionTransferCache.class).get();
        
        return currencyDescriptionTransferCache;
    }
    
    public GlAccountClassDescriptionTransferCache getGlAccountClassDescriptionTransferCache() {
        if(glAccountClassDescriptionTransferCache == null)
            glAccountClassDescriptionTransferCache = CDI.current().select(GlAccountClassDescriptionTransferCache.class).get();
        
        return glAccountClassDescriptionTransferCache;
    }
    
    public GlAccountClassTransferCache getGlAccountClassTransferCache() {
        if(glAccountClassTransferCache == null)
            glAccountClassTransferCache = CDI.current().select(GlAccountClassTransferCache.class).get();
        
        return glAccountClassTransferCache;
    }
    
    public GlAccountDescriptionTransferCache getGlAccountDescriptionTransferCache() {
        if(glAccountDescriptionTransferCache == null)
            glAccountDescriptionTransferCache = CDI.current().select(GlAccountDescriptionTransferCache.class).get();
        
        return glAccountDescriptionTransferCache;
    }
    
    public GlAccountTransferCache getGlAccountTransferCache() {
        if(glAccountTransferCache == null)
            glAccountTransferCache = CDI.current().select(GlAccountTransferCache.class).get();
        
        return glAccountTransferCache;
    }
    
    public GlAccountCategoryDescriptionTransferCache getGlAccountCategoryDescriptionTransferCache() {
        if(glAccountCategoryDescriptionTransferCache == null)
            glAccountCategoryDescriptionTransferCache = CDI.current().select(GlAccountCategoryDescriptionTransferCache.class).get();
        
        return glAccountCategoryDescriptionTransferCache;
    }
    
    public GlAccountCategoryTransferCache getGlAccountCategoryTransferCache() {
        if(glAccountCategoryTransferCache == null)
            glAccountCategoryTransferCache = CDI.current().select(GlAccountCategoryTransferCache.class).get();
        
        return glAccountCategoryTransferCache;
    }
    
    public GlResourceTypeDescriptionTransferCache getGlResourceTypeDescriptionTransferCache() {
        if(glResourceTypeDescriptionTransferCache == null)
            glResourceTypeDescriptionTransferCache = CDI.current().select(GlResourceTypeDescriptionTransferCache.class).get();
        
        return glResourceTypeDescriptionTransferCache;
    }
    
    public GlResourceTypeTransferCache getGlResourceTypeTransferCache() {
        if(glResourceTypeTransferCache == null)
            glResourceTypeTransferCache = CDI.current().select(GlResourceTypeTransferCache.class).get();
        
        return glResourceTypeTransferCache;
    }
    
    public ItemAccountingCategoryDescriptionTransferCache getItemAccountingCategoryDescriptionTransferCache() {
        if(itemAccountingCategoryDescriptionTransferCache == null)
            itemAccountingCategoryDescriptionTransferCache = CDI.current().select(ItemAccountingCategoryDescriptionTransferCache.class).get();
        
        return itemAccountingCategoryDescriptionTransferCache;
    }
    
    public ItemAccountingCategoryTransferCache getItemAccountingCategoryTransferCache() {
        if(itemAccountingCategoryTransferCache == null)
            itemAccountingCategoryTransferCache = CDI.current().select(ItemAccountingCategoryTransferCache.class).get();
        
        return itemAccountingCategoryTransferCache;
    }
    
    public TransactionEntityRoleTransferCache getTransactionEntityRoleTransferCache() {
        if(transactionEntityRoleTransferCache == null)
            transactionEntityRoleTransferCache = CDI.current().select(TransactionEntityRoleTransferCache.class).get();
        
        return transactionEntityRoleTransferCache;
    }
    
    public TransactionEntityRoleTypeTransferCache getTransactionEntityRoleTypeTransferCache() {
        if(transactionEntityRoleTypeTransferCache == null)
            transactionEntityRoleTypeTransferCache = CDI.current().select(TransactionEntityRoleTypeTransferCache.class).get();
        
        return transactionEntityRoleTypeTransferCache;
    }
    
    public TransactionGlEntryTransferCache getTransactionGlEntryTransferCache() {
        if(transactionGlEntryTransferCache == null)
            transactionGlEntryTransferCache = CDI.current().select(TransactionGlEntryTransferCache.class).get();
        
        return transactionGlEntryTransferCache;
    }
    
    public TransactionGlAccountTransferCache getTransactionGlAccountTransferCache() {
        if(transactionGlAccountTransferCache == null)
            transactionGlAccountTransferCache = CDI.current().select(TransactionGlAccountTransferCache.class).get();
        
        return transactionGlAccountTransferCache;
    }
    
    public TransactionGlAccountCategoryTransferCache getTransactionGlAccountCategoryTransferCache() {
        if(transactionGlAccountCategoryTransferCache == null)
            transactionGlAccountCategoryTransferCache = CDI.current().select(TransactionGlAccountCategoryTransferCache.class).get();
        
        return transactionGlAccountCategoryTransferCache;
    }
    
    public TransactionGroupTransferCache getTransactionGroupTransferCache() {
        if(transactionGroupTransferCache == null)
            transactionGroupTransferCache = CDI.current().select(TransactionGroupTransferCache.class).get();
        
        return transactionGroupTransferCache;
    }
    
    public TransactionTransferCache getTransactionTransferCache() {
        if(transactionTransferCache == null)
            transactionTransferCache = CDI.current().select(TransactionTransferCache.class).get();
        
        return transactionTransferCache;
    }
    
    public TransactionTypeTransferCache getTransactionTypeTransferCache() {
        if(transactionTypeTransferCache == null)
            transactionTypeTransferCache = CDI.current().select(TransactionTypeTransferCache.class).get();
        
        return transactionTypeTransferCache;
    }
    
    public GlAccountTypeTransferCache getGlAccountTypeTransferCache() {
        if(glAccountTypeTransferCache == null)
            glAccountTypeTransferCache = CDI.current().select(GlAccountTypeTransferCache.class).get();
        
        return glAccountTypeTransferCache;
    }
    
    public TransactionTypeDescriptionTransferCache getTransactionTypeDescriptionTransferCache() {
        if(transactionTypeDescriptionTransferCache == null)
            transactionTypeDescriptionTransferCache = CDI.current().select(TransactionTypeDescriptionTransferCache.class).get();
        
        return transactionTypeDescriptionTransferCache;
    }
    
    public TransactionGlAccountCategoryDescriptionTransferCache getTransactionGlAccountCategoryDescriptionTransferCache() {
        if(transactionGlAccountCategoryDescriptionTransferCache == null)
            transactionGlAccountCategoryDescriptionTransferCache = CDI.current().select(TransactionGlAccountCategoryDescriptionTransferCache.class).get();
        
        return transactionGlAccountCategoryDescriptionTransferCache;
    }
    
    public TransactionEntityRoleTypeDescriptionTransferCache getTransactionEntityRoleTypeDescriptionTransferCache() {
        if(transactionEntityRoleTypeDescriptionTransferCache == null)
            transactionEntityRoleTypeDescriptionTransferCache = CDI.current().select(TransactionEntityRoleTypeDescriptionTransferCache.class).get();
        
        return transactionEntityRoleTypeDescriptionTransferCache;
    }
    
    public SymbolPositionDescriptionTransferCache getSymbolPositionDescriptionTransferCache() {
        if(symbolPositionDescriptionTransferCache == null)
            symbolPositionDescriptionTransferCache = CDI.current().select(SymbolPositionDescriptionTransferCache.class).get();
        
        return symbolPositionDescriptionTransferCache;
    }
    
    public SymbolPositionTransferCache getSymbolPositionTransferCache() {
        if(symbolPositionTransferCache == null)
            symbolPositionTransferCache = CDI.current().select(SymbolPositionTransferCache.class).get();
        
        return symbolPositionTransferCache;
    }

    public TransactionTimeTypeTransferCache getTransactionTimeTypeTransferCache() {
        if(transactionTimeTypeTransferCache == null)
            transactionTimeTypeTransferCache = CDI.current().select(TransactionTimeTypeTransferCache.class).get();

        return transactionTimeTypeTransferCache;
    }

    public TransactionTimeTypeDescriptionTransferCache getTransactionTimeTypeDescriptionTransferCache() {
        if(transactionTimeTypeDescriptionTransferCache == null)
            transactionTimeTypeDescriptionTransferCache = CDI.current().select(TransactionTimeTypeDescriptionTransferCache.class).get();

        return transactionTimeTypeDescriptionTransferCache;
    }

    public TransactionTimeTransferCache getTransactionTimeTransferCache() {
        if(transactionTimeTransferCache == null)
            transactionTimeTransferCache = CDI.current().select(TransactionTimeTransferCache.class).get();

        return transactionTimeTransferCache;
    }

}
