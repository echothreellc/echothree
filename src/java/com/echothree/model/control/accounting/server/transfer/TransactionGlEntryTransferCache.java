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

import com.echothree.model.control.accounting.common.transfer.TransactionGlEntryTransfer;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.accounting.server.entity.TransactionGlEntry;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TransactionGlEntryTransferCache
        extends BaseAccountingTransferCache<TransactionGlEntry, TransactionGlEntryTransfer> {

    AccountingControl accountingControl = Session.getModelController(AccountingControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);

    /** Creates a new instance of TransactionGlEntryTransferCache */
    protected TransactionGlEntryTransferCache() {
        super();
    }

    @Override
    public TransactionGlEntryTransfer getTransfer(UserVisit userVisit, TransactionGlEntry transactionGlEntry) {
        var transactionGlEntryTransfer = get(transactionGlEntry);

        if(transactionGlEntryTransfer == null) {
            var transactionTransfer = accountingControl.getTransactionTransfer(userVisit, transactionGlEntry.getTransaction());
            var transactionGlEntrySequence = transactionGlEntry.getTransactionGlEntrySequence();
            var groupPartyTransfer = partyControl.getPartyTransfer(userVisit, transactionGlEntry.getGroupParty());
            var transactionGlAccountCategory = transactionGlEntry.getTransactionGlAccountCategory();
            var transactionGlAccountCategoryTransfer = transactionGlAccountCategory == null ? null : accountingControl.getTransactionGlAccountCategoryTransfer(userVisit, transactionGlEntry.getTransactionGlAccountCategory());
            var glAccount = transactionGlEntry.getGlAccount();
            var glAccountCurrency = glAccount.getLastDetail().getCurrency();
            var glAccountTransfer = accountingControl.getGlAccountTransfer(userVisit, glAccount);
            var originalCurrency = transactionGlEntry.getOriginalCurrency();
            var originalCurrencyTransfer = originalCurrency == null ? null : accountingControl.getCurrencyTransfer(userVisit, originalCurrency);
            var unformattedOriginalDebit = transactionGlEntry.getOriginalDebit();
            var originalDebit = unformattedOriginalDebit == null ? null : AmountUtils.getInstance().formatAmount(originalCurrency, unformattedOriginalDebit);
            var unformattedOriginalCredit = transactionGlEntry.getOriginalCredit();
            var originalCredit = unformattedOriginalCredit == null ? null : AmountUtils.getInstance().formatAmount(originalCurrency, unformattedOriginalCredit);
            var unformattedDebit = transactionGlEntry.getDebit();
            var debit = unformattedDebit == null ? null : AmountUtils.getInstance().formatAmount(glAccountCurrency, unformattedDebit);
            var unformattedCredit = transactionGlEntry.getCredit();
            var credit = unformattedCredit == null ? null : AmountUtils.getInstance().formatAmount(glAccountCurrency, unformattedCredit);

            transactionGlEntryTransfer = new TransactionGlEntryTransfer(transactionTransfer, transactionGlEntrySequence,
                    groupPartyTransfer, transactionGlAccountCategoryTransfer, glAccountTransfer, originalCurrencyTransfer,
                    unformattedOriginalDebit, originalDebit, unformattedOriginalCredit, originalCredit, unformattedDebit,
                    debit, unformattedCredit, credit);

            put(userVisit, transactionGlEntry, transactionGlEntryTransfer);
        }

        return transactionGlEntryTransfer;
    }

}
