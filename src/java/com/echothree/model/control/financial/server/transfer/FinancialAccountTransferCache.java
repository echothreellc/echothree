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

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.financial.common.FinancialOptions;
import com.echothree.model.control.financial.common.transfer.FinancialAccountRoleTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountTransfer;
import com.echothree.model.control.financial.server.control.FinancialControl;
import com.echothree.model.data.financial.server.entity.FinancialAccount;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class FinancialAccountTransferCache
        extends BaseFinancialTransferCache<FinancialAccount, FinancialAccountTransfer> {
    
    AccountingControl accountingControl;
    boolean includeRoles;
    boolean includeTransactions;
    
    /** Creates a new instance of FinancialAccountTransferCache */
    public FinancialAccountTransferCache(FinancialControl financialControl) {
        super(financialControl);
        
        accountingControl = Session.getModelController(AccountingControl.class);

        var options = session.getOptions();
        if(options != null) {
            includeRoles = options.contains(FinancialOptions.FinancialAccountIncludeRoles);
            includeTransactions = options.contains(FinancialOptions.FinancialAccountIncludeTransactions);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public FinancialAccountTransfer getFinancialAccountTransfer(UserVisit userVisit, FinancialAccount financialAccount) {
        var financialAccountTransfer = get(financialAccount);
        
        if(financialAccountTransfer == null) {
            var financialAccountDetail = financialAccount.getLastDetail();
            var financialAccountType = financialControl.getFinancialAccountTypeTransfer(userVisit, financialAccountDetail.getFinancialAccountType());
            var financialAccountName = financialAccountDetail.getFinancialAccountName();
            var currency = financialAccountDetail.getCurrency();
            var currencyTransfer = accountingControl.getCurrencyTransfer(userVisit, currency);
            var glAccountTransfer = accountingControl.getGlAccountTransfer(userVisit, financialAccountDetail.getGlAccount());
            var reference = financialAccountDetail.getReference();
            var description = financialAccountDetail.getDescription();
            var financialAccountStatus = financialControl.createFinancialAccountStatus(financialAccount);
            var unformattedActualBalance = financialAccountStatus.getActualBalance();
            var amountUtils = AmountUtils.getInstance();
            var actualBalance = amountUtils.formatPriceLine(currency, unformattedActualBalance);
            var unformattedAvailableBalance = financialAccountStatus.getAvailableBalance();
            var availableBalance = amountUtils.formatPriceLine(currency, unformattedAvailableBalance);
            
            financialAccountTransfer = new FinancialAccountTransfer(financialAccountType, financialAccountName, currencyTransfer, glAccountTransfer, reference,
                    description, unformattedActualBalance, actualBalance, unformattedAvailableBalance, availableBalance);
            put(userVisit, financialAccount, financialAccountTransfer);
            
            if(includeRoles) {
                var financialAccountRoleTransfers = financialControl.getFinancialAccountRoleTransfersByFinancialAccount(userVisit, financialAccount);
                var financialAccountRolesMap = new MapWrapper<FinancialAccountRoleTransfer>(financialAccountRoleTransfers.size());

                financialAccountRoleTransfers.forEach((financialAccountRoleTransfer) -> {
                    financialAccountRolesMap.put(financialAccountRoleTransfer.getFinancialAccountRoleType().getFinancialAccountRoleTypeName(), financialAccountRoleTransfer);
                });

                financialAccountTransfer.setFinancialAccountRoles(financialAccountRolesMap);
            }
            
            if(includeTransactions) {
                 financialAccountTransfer.setFinancialAccountTransactions(new ListWrapper<>(financialControl.getFinancialAccountTransactionTransfersByFinancialAccount(userVisit, financialAccount)));
            }
        }
        
        return financialAccountTransfer;
    }
    
}
