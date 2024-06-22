// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.model.control.accounting.common.transfer.TransactionTransfer;
import com.echothree.model.control.accounting.common.transfer.TransactionTypeTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.model.data.accounting.server.entity.TransactionDetail;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class TransactionTransferCache
        extends BaseAccountingTransferCache<Transaction, TransactionTransfer> {

    PartyControl partyControl = Session.getModelController(PartyControl.class);
    boolean includeTransactionGlEntries;
    boolean includeTransactionEntityRoles;
    
    /** Creates a new instance of TransactionTransferCache */
    public TransactionTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeTransactionGlEntries = options.contains(AccountingOptions.TransactionIncludeTransactionGlEntries);
            includeTransactionEntityRoles = options.contains(AccountingOptions.TransactionIncludeTransactionEntityRoles);
        }
        
        setIncludeEntityInstance(true);
    }

    @Override
    public TransactionTransfer getTransfer(Transaction transaction) {
        TransactionTransfer transactionTransfer = get(transaction);

        if(transactionTransfer == null) {
            TransactionDetail transactionDetail = transaction.getLastDetail();
            String transactionName = transactionDetail.getTransactionName();
            Party groupParty = transactionDetail.getGroupParty();
            PartyTransfer groupPartyTransfer = groupParty == null? null: partyControl.getPartyTransfer(userVisit, groupParty);
            TransactionGroupTransfer transactionGroupTransfer = accountingControl.getTransactionGroupTransfer(userVisit, transactionDetail.getTransactionGroup());
            TransactionTypeTransfer transactionTypeTransfer = accountingControl.getTransactionTypeTransfer(userVisit, transactionDetail.getTransactionType());
            Long unformattedPostingTime = transactionDetail.getPostingTime();
            String postingTime = formatTypicalDateTime(unformattedPostingTime);

            transactionTransfer = new TransactionTransfer(transactionName, groupPartyTransfer, transactionGroupTransfer, transactionTypeTransfer, unformattedPostingTime, postingTime);
            put(transaction, transactionTransfer);
            
            if(includeTransactionGlEntries) {
                transactionTransfer.setTransactionGlEntries(new ListWrapper<>(accountingControl.getTransactionGlEntryTransfersByTransaction(userVisit, transaction)));
            }
            
            if(includeTransactionEntityRoles) {
                transactionTransfer.setTransactionEntityRoles(new ListWrapper<>(accountingControl.getTransactionEntityRoleTransfersByTransaction(userVisit, transaction)));
            }
        }

        return transactionTransfer;
    }

}
