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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class FinancialTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    FinancialAccountRoleTypeTransferCache financialAccountRoleTypeTransferCache;
    
    @Inject
    FinancialAccountTypeTransferCache financialAccountTypeTransferCache;
    
    @Inject
    FinancialAccountTypeDescriptionTransferCache financialAccountTypeDescriptionTransferCache;
    
    @Inject
    FinancialAccountTransactionTypeTransferCache financialAccountTransactionTypeTransferCache;
    
    @Inject
    FinancialAccountTransactionTypeDescriptionTransferCache financialAccountTransactionTypeDescriptionTransferCache;
    
    @Inject
    FinancialAccountAliasTypeTransferCache financialAccountAliasTypeTransferCache;
    
    @Inject
    FinancialAccountAliasTypeDescriptionTransferCache financialAccountAliasTypeDescriptionTransferCache;
    
    @Inject
    FinancialAccountRoleTransferCache financialAccountRoleTransferCache;
    
    @Inject
    FinancialAccountTransferCache financialAccountTransferCache;
    
    @Inject
    FinancialAccountAliasTransferCache financialAccountAliasTransferCache;
    
    @Inject
    FinancialAccountTransactionTransferCache financialAccountTransactionTransferCache;

    /** Creates a new instance of FinancialTransferCaches */
    protected FinancialTransferCaches() {
        super();
    }
    
    public FinancialAccountRoleTypeTransferCache getFinancialAccountRoleTypeTransferCache() {
        return financialAccountRoleTypeTransferCache;
    }
    
    public FinancialAccountTypeTransferCache getFinancialAccountTypeTransferCache() {
        return financialAccountTypeTransferCache;
    }
    
    public FinancialAccountTypeDescriptionTransferCache getFinancialAccountTypeDescriptionTransferCache() {
        return financialAccountTypeDescriptionTransferCache;
    }
    
    public FinancialAccountTransactionTypeTransferCache getFinancialAccountTransactionTypeTransferCache() {
        return financialAccountTransactionTypeTransferCache;
    }
    
    public FinancialAccountTransactionTypeDescriptionTransferCache getFinancialAccountTransactionTypeDescriptionTransferCache() {
        return financialAccountTransactionTypeDescriptionTransferCache;
    }
    
    public FinancialAccountAliasTypeTransferCache getFinancialAccountAliasTypeTransferCache() {
        return financialAccountAliasTypeTransferCache;
    }
    
    public FinancialAccountAliasTypeDescriptionTransferCache getFinancialAccountAliasTypeDescriptionTransferCache() {
        return financialAccountAliasTypeDescriptionTransferCache;
    }
    
    public FinancialAccountRoleTransferCache getFinancialAccountRoleTransferCache() {
        return financialAccountRoleTransferCache;
    }
    
    public FinancialAccountTransferCache getFinancialAccountTransferCache() {
        return financialAccountTransferCache;
    }
    
    public FinancialAccountAliasTransferCache getFinancialAccountAliasTransferCache() {
        return financialAccountAliasTransferCache;
    }
    
    public FinancialAccountTransactionTransferCache getFinancialAccountTransactionTransferCache() {
        return financialAccountTransactionTransferCache;
    }
    
}
