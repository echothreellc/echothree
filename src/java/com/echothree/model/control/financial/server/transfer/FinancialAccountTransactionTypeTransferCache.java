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
import com.echothree.model.control.financial.common.transfer.FinancialAccountTransactionTypeTransfer;
import com.echothree.model.control.financial.server.control.FinancialControl;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransactionType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FinancialAccountTransactionTypeTransferCache
        extends BaseFinancialTransferCache<FinancialAccountTransactionType, FinancialAccountTransactionTypeTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    FinancialControl financialControl = Session.getModelController(FinancialControl.class);

    /** Creates a new instance of FinancialAccountTransactionTypeTransferCache */
    public FinancialAccountTransactionTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public FinancialAccountTransactionTypeTransfer getFinancialAccountTransactionTypeTransfer(UserVisit userVisit, FinancialAccountTransactionType financialAccountTransactionType) {
        var financialAccountTransactionTypeTransfer = get(financialAccountTransactionType);
        
        if(financialAccountTransactionTypeTransfer == null) {
            var financialAccountTransactionTypeDetail = financialAccountTransactionType.getLastDetail();
            var financialAccountTypeTransfer = financialControl.getFinancialAccountTypeTransfer(userVisit, financialAccountTransactionTypeDetail.getFinancialAccountType());
            var financialAccountTransactionTypeName = financialAccountTransactionTypeDetail.getFinancialAccountTransactionTypeName();
            var parentFinancialAccountTransactionType = financialAccountTransactionTypeDetail.getParentFinancialAccountTransactionType();
            var parentFinancialAccountTransactionTypeTransfer = parentFinancialAccountTransactionType == null ? null : getFinancialAccountTransactionTypeTransfer(userVisit, parentFinancialAccountTransactionType);
            var glAccountTransfer = accountingControl.getGlAccountTransfer(userVisit, financialAccountTransactionTypeDetail.getGlAccount());
            var isDefault = financialAccountTransactionTypeDetail.getIsDefault();
            var sortOrder = financialAccountTransactionTypeDetail.getSortOrder();
            var description = financialControl.getBestFinancialAccountTransactionTypeDescription(financialAccountTransactionType, getLanguage(userVisit));
            
            financialAccountTransactionTypeTransfer = new FinancialAccountTransactionTypeTransfer(financialAccountTypeTransfer,
                    financialAccountTransactionTypeName, parentFinancialAccountTransactionTypeTransfer, glAccountTransfer, isDefault, sortOrder, description);
            put(userVisit, financialAccountTransactionType, financialAccountTransactionTypeTransfer);
        }
        
        return financialAccountTransactionTypeTransfer;
    }
    
}
