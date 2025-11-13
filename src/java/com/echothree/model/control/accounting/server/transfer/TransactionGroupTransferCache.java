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

import com.echothree.model.control.accounting.common.AccountingOptions;
import com.echothree.model.control.accounting.common.transfer.TransactionGroupTransfer;
import com.echothree.model.control.accounting.common.workflow.TransactionGroupStatusConstants;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.TransactionGroup;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;

public class TransactionGroupTransferCache
        extends BaseAccountingTransferCache<TransactionGroup, TransactionGroupTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);
    boolean includeTransactions;
    
    /** Creates a new instance of TransactionGroupTransferCache */
    public TransactionGroupTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeTransactions = options.contains(AccountingOptions.TransactionGroupIncludeTransactions);
        }
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public TransactionGroupTransfer getTransfer(UserVisit userVisit, TransactionGroup transactionGroup) {
        var transactionGroupTransfer = get(transactionGroup);
        
        if(transactionGroupTransfer == null) {
            var transactionGroupDetail = transactionGroup.getLastDetail();
            var transactionGroupName = transactionGroupDetail.getTransactionGroupName();

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(transactionGroup.getPrimaryKey());
            var transactionGroupStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    TransactionGroupStatusConstants.Workflow_TRANSACTION_GROUP_STATUS, entityInstance);
            
            transactionGroupTransfer = new TransactionGroupTransfer(transactionGroupName, transactionGroupStatusTransfer);
            put(userVisit, transactionGroup, transactionGroupTransfer, entityInstance);
            
            if(includeTransactions) {
                transactionGroupTransfer.setTransactions(new ListWrapper<>(accountingControl.getTransactionTransfersByTransactionGroup(userVisit, transactionGroup)));
            }
        }
        
        return transactionGroupTransfer;
    }
    
}
