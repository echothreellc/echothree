// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.financial.common.transfer.FinancialAccountTransactionTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountTransactionTypeTransfer;
import com.echothree.model.control.financial.common.transfer.FinancialAccountTransfer;
import com.echothree.model.control.financial.server.control.FinancialControl;
import com.echothree.model.data.financial.server.entity.FinancialAccount;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransaction;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransactionDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.string.AmountUtils;

public class FinancialAccountTransactionTransferCache
        extends BaseFinancialTransferCache<FinancialAccountTransaction, FinancialAccountTransactionTransfer> {
    
    /** Creates a new instance of FinancialAccountTransactionTransferCache */
    public FinancialAccountTransactionTransferCache(UserVisit userVisit, FinancialControl financialControl) {
        super(userVisit, financialControl);
        
        setIncludeEntityInstance(true);
    }
    
    public FinancialAccountTransactionTransfer getFinancialAccountTransactionTransfer(FinancialAccountTransaction financialAccountTransaction) {
        FinancialAccountTransactionTransfer financialAccountTransactionTransfer = get(financialAccountTransaction);
        
        if(financialAccountTransactionTransfer == null) {
            FinancialAccountTransactionDetail financialAccountTransactionDetail = financialAccountTransaction.getLastDetail();
            String financialAccountTransactionName = financialAccountTransactionDetail.getFinancialAccountTransactionName();
            FinancialAccount financialAccount = financialAccountTransactionDetail.getFinancialAccount();
            FinancialAccountTransfer financialAccountTransfer = financialControl.getFinancialAccountTransfer(userVisit, financialAccount);
            FinancialAccountTransactionTypeTransfer financialAccountTransactionTypeTransfer = financialControl.getFinancialAccountTransactionTypeTransfer(userVisit, financialAccountTransactionDetail.getFinancialAccountTransactionType());
            Long unformattedAmount = financialAccountTransactionDetail.getAmount();
            String amount = AmountUtils.getInstance().formatPriceLine(financialAccount.getLastDetail().getCurrency(), unformattedAmount);
            String comment = financialAccountTransactionDetail.getComment();
            
            financialAccountTransactionTransfer = new FinancialAccountTransactionTransfer(financialAccountTransactionName, financialAccountTransfer,
                    financialAccountTransactionTypeTransfer, unformattedAmount, amount, comment);
            put(financialAccountTransaction, financialAccountTransactionTransfer);
        }
        
        return financialAccountTransactionTransfer;
    }
    
}
