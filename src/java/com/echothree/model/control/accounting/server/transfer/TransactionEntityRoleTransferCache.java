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

import com.echothree.model.control.accounting.common.transfer.TransactionEntityRoleTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRole;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TransactionEntityRoleTransferCache
        extends BaseAccountingTransferCache<TransactionEntityRole, TransactionEntityRoleTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    
    /** Creates a new instance of TransactionEntityRoleTransferCache */
    public TransactionEntityRoleTransferCache() {
        super();
    }
    
    @Override
    public TransactionEntityRoleTransfer getTransfer(UserVisit userVisit, TransactionEntityRole transactionEntityRole) {
        var transactionEntityRoleTransfer = get(transactionEntityRole);
        
        if(transactionEntityRoleTransfer == null) {
            var transaction = accountingControl.getTransactionTransfer(userVisit, transactionEntityRole.getTransaction());
            var transactionEntityRoleType = accountingControl.getTransactionEntityRoleTypeTransfer(userVisit, transactionEntityRole.getTransactionEntityRoleType());
            var entityInstance = entityInstanceControl.getEntityInstanceTransfer(userVisit, transactionEntityRole.getEntityInstance(), false, false, false, false);
            
            transactionEntityRoleTransfer = new TransactionEntityRoleTransfer(transaction, transactionEntityRoleType, entityInstance);
            put(userVisit, transactionEntityRole, transactionEntityRoleTransfer);
        }
        
        return transactionEntityRoleTransfer;
    }
    
}
