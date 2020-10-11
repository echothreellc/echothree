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

package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.common.AccountingOptions;
import com.echothree.model.control.accounting.common.transfer.TransactionGroupTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.accounting.common.workflow.TransactionGroupStatusConstants;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.TransactionGroup;
import com.echothree.model.data.accounting.server.entity.TransactionGroupDetail;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class TransactionGroupTransferCache
        extends BaseAccountingTransferCache<TransactionGroup, TransactionGroupTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    WorkflowControl workflowControl = (WorkflowControl)Session.getModelController(WorkflowControl.class);
    boolean includeTransactions;
    
    /** Creates a new instance of TransactionGroupTransferCache */
    public TransactionGroupTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeTransactions = options.contains(AccountingOptions.TransactionGroupIncludeTransactions);
        }
        
        setIncludeEntityInstance(true);
    }
    
    @Override
    public TransactionGroupTransfer getTransfer(TransactionGroup transactionGroup) {
        TransactionGroupTransfer transactionGroupTransfer = get(transactionGroup);
        
        if(transactionGroupTransfer == null) {
            TransactionGroupDetail transactionGroupDetail = transactionGroup.getLastDetail();
            String transactionGroupName = transactionGroupDetail.getTransactionGroupName();
            
            EntityInstance entityInstance = coreControl.getEntityInstanceByBasePK(transactionGroup.getPrimaryKey());
            WorkflowEntityStatusTransfer transactionGroupStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    TransactionGroupStatusConstants.Workflow_TRANSACTION_GROUP_STATUS, entityInstance);
            
            transactionGroupTransfer = new TransactionGroupTransfer(transactionGroupName, transactionGroupStatusTransfer);
            put(transactionGroup, transactionGroupTransfer, entityInstance);
            
            if(includeTransactions) {
                transactionGroupTransfer.setTransactions(new ListWrapper<>(accountingControl.getTransactionTransfersByTransactionGroup(userVisit, transactionGroup)));
            }
        }
        
        return transactionGroupTransfer;
    }
    
}
