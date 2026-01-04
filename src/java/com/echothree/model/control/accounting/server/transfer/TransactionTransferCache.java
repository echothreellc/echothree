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
package com.echothree.model.control.accounting.server.transfer;

import com.echothree.model.control.accounting.common.AccountingOptions;
import com.echothree.model.control.accounting.common.transfer.TransactionTimeTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionTransfer;
import com.echothree.model.control.accounting.common.workflow.TransactionStatusConstants;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.control.TransactionTimeControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TransactionTransferCache
        extends BaseAccountingTransferCache<Transaction, TransactionTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    TransactionTimeControl transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
    WorkflowControl workflowControl = Session.getModelController(WorkflowControl.class);

    boolean includeTransactionGlEntries;
    boolean includeTransactionEntityRoles;
    boolean includeTransactionTimes;

    /** Creates a new instance of TransactionTransferCache */
    protected TransactionTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeTransactionGlEntries = options.contains(AccountingOptions.TransactionIncludeTransactionGlEntries);
            includeTransactionEntityRoles = options.contains(AccountingOptions.TransactionIncludeTransactionEntityRoles);
            includeTransactionTimes = options.contains(AccountingOptions.TransactionIncludeTransactionTimes);
        }
        
        setIncludeEntityInstance(true);
    }

    @Override
    public TransactionTransfer getTransfer(UserVisit userVisit, Transaction transaction) {
        var transactionTransfer = get(transaction);

        if(transactionTransfer == null) {
            var transactionDetail = transaction.getLastDetail();
            var transactionName = transactionDetail.getTransactionName();
            var groupParty = transactionDetail.getGroupParty();
            var groupPartyTransfer = groupParty == null? null: partyControl.getPartyTransfer(userVisit, groupParty);
            var transactionGroupTransfer = accountingControl.getTransactionGroupTransfer(userVisit, transactionDetail.getTransactionGroup());
            var transactionTypeTransfer = accountingControl.getTransactionTypeTransfer(userVisit, transactionDetail.getTransactionType());

            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(transaction.getPrimaryKey());
            var transactionStatusTransfer = workflowControl.getWorkflowEntityStatusTransferByEntityInstanceUsingNames(userVisit,
                    TransactionStatusConstants.Workflow_TRANSACTION_STATUS, entityInstance);

            transactionTransfer = new TransactionTransfer(transactionName, groupPartyTransfer, transactionGroupTransfer,
                    transactionTypeTransfer, transactionStatusTransfer);
            put(userVisit, transaction, transactionTransfer);
            
            if(includeTransactionGlEntries) {
                transactionTransfer.setTransactionGlEntries(new ListWrapper<>(accountingControl.getTransactionGlEntryTransfersByTransaction(userVisit, transaction)));
            }

            if(includeTransactionEntityRoles) {
                transactionTransfer.setTransactionEntityRoles(new ListWrapper<>(accountingControl.getTransactionEntityRoleTransfersByTransaction(userVisit, transaction)));
            }

            if(includeTransactionTimes) {
                var transfers = transactionTimeControl.getTransactionTimeTransfersByTransaction(userVisit, transaction);
                var mapWrapper = new MapWrapper<TransactionTimeTransfer>(transfers.size());

                transfers.forEach((transfer) -> {
                    mapWrapper.put(transfer.getTransactionTimeType().getTransactionTimeTypeName(), transfer);
                });

                transactionTransfer.setTransactionTimes(mapWrapper);
            }
        }

        return transactionTransfer;
    }

}
