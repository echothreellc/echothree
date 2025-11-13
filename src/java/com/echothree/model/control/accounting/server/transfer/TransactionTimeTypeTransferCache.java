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

import com.echothree.model.control.accounting.common.transfer.TransactionTimeTypeTransfer;
import com.echothree.model.control.accounting.server.control.TransactionTimeControl;
import com.echothree.model.data.accounting.server.entity.TransactionTimeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TransactionTimeTypeTransferCache
        extends BaseAccountingTransferCache<TransactionTimeType, TransactionTimeTypeTransfer> {

    TransactionTimeControl transactionTimeControl = Session.getModelController(TransactionTimeControl.class);

    /** Creates a new instance of TransactionTimeTypeTransferCache */
    public TransactionTimeTypeTransferCache() {
        super();
        
        setIncludeEntityInstance(true);
    }

    @Override
    public TransactionTimeTypeTransfer getTransfer(UserVisit userVisit, TransactionTimeType transactionTimeType) {
        var transactionTimeTypeTransfer = get(transactionTimeType);
        
        if(transactionTimeTypeTransfer == null) {
            var transactionTimeTypeDetail = transactionTimeType.getLastDetail();
            var transactionTimeTypeName = transactionTimeTypeDetail.getTransactionTimeTypeName();
            var isDefault = transactionTimeTypeDetail.getIsDefault();
            var sortOrder = transactionTimeTypeDetail.getSortOrder();
            var description = transactionTimeControl.getBestTransactionTimeTypeDescription(transactionTimeType, getLanguage(userVisit));
            
            transactionTimeTypeTransfer = new TransactionTimeTypeTransfer(transactionTimeTypeName, isDefault, sortOrder, description);
            put(userVisit, transactionTimeType, transactionTimeTypeTransfer);
        }
        
        return transactionTimeTypeTransfer;
    }
    
}
