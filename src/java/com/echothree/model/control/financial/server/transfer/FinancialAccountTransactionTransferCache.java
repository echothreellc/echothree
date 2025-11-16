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

import com.echothree.model.control.financial.common.transfer.FinancialAccountTransactionTransfer;
import com.echothree.model.control.financial.server.control.FinancialControl;
import com.echothree.model.data.financial.server.entity.FinancialAccountTransaction;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class FinancialAccountTransactionTransferCache
        extends BaseFinancialTransferCache<FinancialAccountTransaction, FinancialAccountTransactionTransfer> {

    FinancialControl financialControl = Session.getModelController(FinancialControl.class);

    /** Creates a new instance of FinancialAccountTransactionTransferCache */
    protected FinancialAccountTransactionTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    public FinancialAccountTransactionTransfer getFinancialAccountTransactionTransfer(UserVisit userVisit, FinancialAccountTransaction financialAccountTransaction) {
        var financialAccountTransactionTransfer = get(financialAccountTransaction);
        
        if(financialAccountTransactionTransfer == null) {
            var financialAccountTransactionDetail = financialAccountTransaction.getLastDetail();
            var financialAccountTransactionName = financialAccountTransactionDetail.getFinancialAccountTransactionName();
            var financialAccount = financialAccountTransactionDetail.getFinancialAccount();
            var financialAccountTransfer = financialControl.getFinancialAccountTransfer(userVisit, financialAccount);
            var financialAccountTransactionTypeTransfer = financialControl.getFinancialAccountTransactionTypeTransfer(userVisit, financialAccountTransactionDetail.getFinancialAccountTransactionType());
            var unformattedAmount = financialAccountTransactionDetail.getAmount();
            var amount = AmountUtils.getInstance().formatPriceLine(financialAccount.getLastDetail().getCurrency(), unformattedAmount);
            var comment = financialAccountTransactionDetail.getComment();
            
            financialAccountTransactionTransfer = new FinancialAccountTransactionTransfer(financialAccountTransactionName, financialAccountTransfer,
                    financialAccountTransactionTypeTransfer, unformattedAmount, amount, comment);
            put(userVisit, financialAccountTransaction, financialAccountTransactionTransfer);
        }
        
        return financialAccountTransactionTransfer;
    }
    
}
