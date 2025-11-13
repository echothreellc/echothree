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

import com.echothree.model.control.accounting.common.transfer.TransactionTypeTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TransactionTypeTransferCache
        extends BaseAccountingTransferCache<TransactionType, TransactionTypeTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);

    /** Creates a new instance of TransactionTypeTransferCache */
    public TransactionTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public TransactionTypeTransfer getTransfer(TransactionType transactionType) {
        var transactionTypeTransfer = get(transactionType);
        
        if(transactionTypeTransfer == null) {
            var transactionTypeDetail = transactionType.getLastDetail();
            var transactionTypeName = transactionTypeDetail.getTransactionTypeName();
            var sortOrder = transactionTypeDetail.getSortOrder();
            var description = accountingControl.getBestTransactionTypeDescription(transactionType, getLanguage(userVisit));
            
            transactionTypeTransfer = new TransactionTypeTransfer(transactionTypeName, sortOrder, description);
            put(userVisit, transactionType, transactionTypeTransfer);
        }
        
        return transactionTypeTransfer;
    }
    
}
