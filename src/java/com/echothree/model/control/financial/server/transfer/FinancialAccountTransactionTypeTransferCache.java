// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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

import com.echothree.model.control.accounting.common.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.financial.common.transfer.FinancialAccountTransactionTypeTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountTypeTransfer;
import com.echothree.model.control.financial.server.control.FinancialControl;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransactionType;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransactionTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class FinancialAccountTransactionTypeTransferCache
        extends BaseFinancialTransferCache<FinancialAccountTransactionType, FinancialAccountTransactionTypeTransfer> {
    
    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    
    /** Creates a new instance of FinancialAccountTransactionTypeTransferCache */
    public FinancialAccountTransactionTypeTransferCache(UserVisit userVisit, FinancialControl financialControl) {
        super(userVisit, financialControl);
        
        setIncludeEntityInstance(true);
    }
    
    public FinancialAccountTransactionTypeTransfer getFinancialAccountTransactionTypeTransfer(FinancialAccountTransactionType financialAccountTransactionType) {
        FinancialAccountTransactionTypeTransfer financialAccountTransactionTypeTransfer = get(financialAccountTransactionType);
        
        if(financialAccountTransactionTypeTransfer == null) {
            FinancialAccountTransactionTypeDetail financialAccountTransactionTypeDetail = financialAccountTransactionType.getLastDetail();
            FinancialAccountTypeTransfer financialAccountTypeTransfer = financialControl.getFinancialAccountTypeTransfer(userVisit, financialAccountTransactionTypeDetail.getFinancialAccountType());
            String financialAccountTransactionTypeName = financialAccountTransactionTypeDetail.getFinancialAccountTransactionTypeName();
            FinancialAccountTransactionType parentFinancialAccountTransactionType = financialAccountTransactionTypeDetail.getParentFinancialAccountTransactionType();
            FinancialAccountTransactionTypeTransfer parentFinancialAccountTransactionTypeTransfer = parentFinancialAccountTransactionType == null? null: getFinancialAccountTransactionTypeTransfer(parentFinancialAccountTransactionType);
            GlAccountTransfer glAccountTransfer = accountingControl.getGlAccountTransfer(userVisit, financialAccountTransactionTypeDetail.getGlAccount());
            Boolean isDefault = financialAccountTransactionTypeDetail.getIsDefault();
            Integer sortOrder = financialAccountTransactionTypeDetail.getSortOrder();
            String description = financialControl.getBestFinancialAccountTransactionTypeDescription(financialAccountTransactionType, getLanguage());
            
            financialAccountTransactionTypeTransfer = new FinancialAccountTransactionTypeTransfer(financialAccountTypeTransfer,
                    financialAccountTransactionTypeName, parentFinancialAccountTransactionTypeTransfer, glAccountTransfer, isDefault, sortOrder, description);
            put(financialAccountTransactionType, financialAccountTransactionTypeTransfer);
        }
        
        return financialAccountTransactionTypeTransfer;
    }
    
}
