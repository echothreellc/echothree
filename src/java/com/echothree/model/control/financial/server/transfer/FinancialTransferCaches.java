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

package com.echothree.model.control.financial.server.transfer;

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FinancialTransferCaches
        extends BaseTransferCaches {
    
    protected FinancialAccountRoleTypeTransferCache financialAccountRoleTypeTransferCache;
    protected FinancialAccountTypeTransferCache financialAccountTypeTransferCache;
    protected FinancialAccountTypeDescriptionTransferCache financialAccountTypeDescriptionTransferCache;
    protected FinancialAccountTransactionTypeTransferCache financialAccountTransactionTypeTransferCache;
    protected FinancialAccountTransactionTypeDescriptionTransferCache financialAccountTransactionTypeDescriptionTransferCache;
    protected FinancialAccountAliasTypeTransferCache financialAccountAliasTypeTransferCache;
    protected FinancialAccountAliasTypeDescriptionTransferCache financialAccountAliasTypeDescriptionTransferCache;
    protected FinancialAccountRoleTransferCache financialAccountRoleTransferCache;
    protected FinancialAccountTransferCache financialAccountTransferCache;
    protected FinancialAccountAliasTransferCache financialAccountAliasTransferCache;
    protected FinancialAccountTransactionTransferCache financialAccountTransactionTransferCache;
    
    /** Creates a new instance of FinancialTransferCaches */
    protected FinancialTransferCaches() {
        super();
    }
    
    public FinancialAccountRoleTypeTransferCache getFinancialAccountRoleTypeTransferCache() {
        if(financialAccountRoleTypeTransferCache == null)
            financialAccountRoleTypeTransferCache = CDI.current().select(FinancialAccountRoleTypeTransferCache.class).get();
        
        return financialAccountRoleTypeTransferCache;
    }
    
    public FinancialAccountTypeTransferCache getFinancialAccountTypeTransferCache() {
        if(financialAccountTypeTransferCache == null)
            financialAccountTypeTransferCache = CDI.current().select(FinancialAccountTypeTransferCache.class).get();
        
        return financialAccountTypeTransferCache;
    }
    
    public FinancialAccountTypeDescriptionTransferCache getFinancialAccountTypeDescriptionTransferCache() {
        if(financialAccountTypeDescriptionTransferCache == null)
            financialAccountTypeDescriptionTransferCache = CDI.current().select(FinancialAccountTypeDescriptionTransferCache.class).get();
        
        return financialAccountTypeDescriptionTransferCache;
    }
    
    public FinancialAccountTransactionTypeTransferCache getFinancialAccountTransactionTypeTransferCache() {
        if(financialAccountTransactionTypeTransferCache == null)
            financialAccountTransactionTypeTransferCache = CDI.current().select(FinancialAccountTransactionTypeTransferCache.class).get();
        
        return financialAccountTransactionTypeTransferCache;
    }
    
    public FinancialAccountTransactionTypeDescriptionTransferCache getFinancialAccountTransactionTypeDescriptionTransferCache() {
        if(financialAccountTransactionTypeDescriptionTransferCache == null)
            financialAccountTransactionTypeDescriptionTransferCache = CDI.current().select(FinancialAccountTransactionTypeDescriptionTransferCache.class).get();
        
        return financialAccountTransactionTypeDescriptionTransferCache;
    }
    
    public FinancialAccountAliasTypeTransferCache getFinancialAccountAliasTypeTransferCache() {
        if(financialAccountAliasTypeTransferCache == null)
            financialAccountAliasTypeTransferCache = CDI.current().select(FinancialAccountAliasTypeTransferCache.class).get();
        
        return financialAccountAliasTypeTransferCache;
    }
    
    public FinancialAccountAliasTypeDescriptionTransferCache getFinancialAccountAliasTypeDescriptionTransferCache() {
        if(financialAccountAliasTypeDescriptionTransferCache == null)
            financialAccountAliasTypeDescriptionTransferCache = CDI.current().select(FinancialAccountAliasTypeDescriptionTransferCache.class).get();
        
        return financialAccountAliasTypeDescriptionTransferCache;
    }
    
    public FinancialAccountRoleTransferCache getFinancialAccountRoleTransferCache() {
        if(financialAccountRoleTransferCache == null)
            financialAccountRoleTransferCache = CDI.current().select(FinancialAccountRoleTransferCache.class).get();
        
        return financialAccountRoleTransferCache;
    }
    
    public FinancialAccountTransferCache getFinancialAccountTransferCache() {
        if(financialAccountTransferCache == null)
            financialAccountTransferCache = CDI.current().select(FinancialAccountTransferCache.class).get();
        
        return financialAccountTransferCache;
    }
    
    public FinancialAccountAliasTransferCache getFinancialAccountAliasTransferCache() {
        if(financialAccountAliasTransferCache == null)
            financialAccountAliasTransferCache = CDI.current().select(FinancialAccountAliasTransferCache.class).get();
        
        return financialAccountAliasTransferCache;
    }
    
    public FinancialAccountTransactionTransferCache getFinancialAccountTransactionTransferCache() {
        if(financialAccountTransactionTransferCache == null)
            financialAccountTransactionTransferCache = CDI.current().select(FinancialAccountTransactionTransferCache.class).get();
        
        return financialAccountTransactionTransferCache;
    }
    
}
