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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.common.transfer.TransactionTypeTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.accounting.server.entity.TransactionTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;

public class TransactionTypeTransferCache
        extends BaseAccountingTransferCache<TransactionType, TransactionTypeTransfer> {
    
    /** Creates a new instance of TransactionTypeTransferCache */
    public TransactionTypeTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public TransactionTypeTransfer getTransfer(TransactionType transactionType) {
        TransactionTypeTransfer transactionTypeTransfer = get(transactionType);
        
        if(transactionTypeTransfer == null) {
            TransactionTypeDetail transactionTypeDetail = transactionType.getLastDetail();
            String transactionTypeName = transactionTypeDetail.getTransactionTypeName();
            Integer sortOrder = transactionTypeDetail.getSortOrder();
            String description = accountingControl.getBestTransactionTypeDescription(transactionType, getLanguage());
            
            transactionTypeTransfer = new TransactionTypeTransfer(transactionTypeName, sortOrder, description);
            put(transactionType, transactionTypeTransfer);
        }
        
        return transactionTypeTransfer;
    }
    
}
