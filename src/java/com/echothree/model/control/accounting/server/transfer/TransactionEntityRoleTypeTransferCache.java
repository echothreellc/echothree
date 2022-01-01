// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

import com.echothree.model.control.accounting.common.transfer.TransactionEntityRoleTypeTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionTypeTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleType;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleTypeDetail;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TransactionEntityRoleTypeTransferCache
        extends BaseAccountingTransferCache<TransactionEntityRoleType, TransactionEntityRoleTypeTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of TransactionEntityRoleTypeTransferCache */
    public TransactionEntityRoleTypeTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public TransactionEntityRoleTypeTransfer getTransfer(TransactionEntityRoleType transactionEntityRoleType) {
        TransactionEntityRoleTypeTransfer transactionEntityRoleTypeTransfer = get(transactionEntityRoleType);
        
        if(transactionEntityRoleTypeTransfer == null) {
            TransactionEntityRoleTypeDetail transactionEntityRoleTypeDetail = transactionEntityRoleType.getLastDetail();
            TransactionTypeTransfer transactionType = accountingControl.getTransactionTypeTransfer(userVisit, transactionEntityRoleTypeDetail.getTransactionType());
            String transactionEntityRoleTypeName = transactionEntityRoleTypeDetail.getTransactionEntityRoleTypeName();
            EntityTypeTransfer entityType = coreControl.getEntityTypeTransfer(userVisit, transactionEntityRoleTypeDetail.getEntityType());
            Integer sortOrder = transactionEntityRoleTypeDetail.getSortOrder();
            String description = accountingControl.getBestTransactionEntityRoleTypeDescription(transactionEntityRoleType, getLanguage());
            
            transactionEntityRoleTypeTransfer = new TransactionEntityRoleTypeTransfer(transactionType, transactionEntityRoleTypeName, entityType, sortOrder, description);
            put(transactionEntityRoleType, transactionEntityRoleTypeTransfer);
        }
        
        return transactionEntityRoleTypeTransfer;
    }
    
}
