// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

package com.echothree.model.control.accounting.common.transfer;

import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class TransactionGroupTransfer
        extends BaseTransfer {
    
    private String transactionGroupName;
    private WorkflowEntityStatusTransfer transactionGroupStatus;
    
    private ListWrapper<TransactionTransfer> transactions;
    
    /** Creates a new instance of TransactionGroupTransfer */
    public TransactionGroupTransfer(String transactionGroupName,
            WorkflowEntityStatusTransfer transactionGroupStatus) {
        this.transactionGroupName = transactionGroupName;
        this.transactionGroupStatus = transactionGroupStatus;
    }
    
    public String getTransactionGroupName() {
        return transactionGroupName;
    }
    
    public void setTransactionGroupName(String transactionGroupName) {
        this.transactionGroupName = transactionGroupName;
    }
    
    public WorkflowEntityStatusTransfer getTransactionGroupStatus() {
        return transactionGroupStatus;
    }
    
    public void setTransactionGroupStatus(WorkflowEntityStatusTransfer transactionGroupStatus) {
        this.transactionGroupStatus = transactionGroupStatus;
    }

    public ListWrapper<TransactionTransfer> getTransactions() {
        return transactions;
    }

    public void setTransactions(ListWrapper<TransactionTransfer> transactions) {
        this.transactions = transactions;
    }
    
}
