// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.accounting.remote.transfer.CurrencyTransfer;
import com.echothree.model.control.accounting.remote.transfer.GlAccountTransfer;
import com.echothree.model.control.accounting.remote.transfer.TransactionGlAccountCategoryTransfer;
import com.echothree.model.control.accounting.remote.transfer.TransactionGlEntryTransfer;
import com.echothree.model.control.accounting.remote.transfer.TransactionTransfer;
import com.echothree.model.control.accounting.server.AccountingControl;
import com.echothree.model.control.party.remote.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionGlEntry;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.AmountUtils;

public class TransactionGlEntryTransferCache
        extends BaseAccountingTransferCache<TransactionGlEntry, TransactionGlEntryTransfer> {

    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);

    /** Creates a new instance of TransactionGlEntryTransferCache */
    public TransactionGlEntryTransferCache(UserVisit userVisit, AccountingControl accountingControl) {
        super(userVisit, accountingControl);
    }

    @Override
    public TransactionGlEntryTransfer getTransfer(TransactionGlEntry transactionGlEntry) {
        TransactionGlEntryTransfer transactionGlEntryTransfer = get(transactionGlEntry);

        if(transactionGlEntryTransfer == null) {
            TransactionTransfer transactionTransfer = accountingControl.getTransactionTransfer(userVisit, transactionGlEntry.getTransaction());
            Integer transactionGlEntrySequence = transactionGlEntry.getTransactionGlEntrySequence();
            TransactionGlEntry parentTransactionGlEntry = transactionGlEntry.getParentTransactionGlEntry();
            TransactionGlEntryTransfer parentTransactionGlEntryTransfer = parentTransactionGlEntry == null ? null : accountingControl.getTransactionGlEntryTransfer(userVisit, parentTransactionGlEntry);
            PartyTransfer groupPartyTransfer = partyControl.getPartyTransfer(userVisit, transactionGlEntry.getGroupParty());
            TransactionGlAccountCategory transactionGlAccountCategory = transactionGlEntry.getTransactionGlAccountCategory();
            TransactionGlAccountCategoryTransfer transactionGlAccountCategoryTransfer = transactionGlAccountCategory == null ? null : accountingControl.getTransactionGlAccountCategoryTransfer(userVisit, transactionGlEntry.getTransactionGlAccountCategory());
            GlAccount glAccount = transactionGlEntry.getGlAccount();
            GlAccountTransfer glAccountTransfer = accountingControl.getGlAccountTransfer(userVisit, glAccount);
            Currency originalCurrency = transactionGlEntry.getOriginalCurrency();
            CurrencyTransfer originalCurrencyTransfer = originalCurrency == null ? null : accountingControl.getCurrencyTransfer(userVisit, originalCurrency);
            Long unformattedOriginalAmount = transactionGlEntry.getOriginalAmount();
            String originalDebit = unformattedOriginalAmount >= 0 ? null : AmountUtils.getInstance().formatAmount(originalCurrency, -unformattedOriginalAmount);
            String originalCredit = originalDebit == null ? AmountUtils.getInstance().formatAmount(originalCurrency, unformattedOriginalAmount) : null;
            Long unformattedAmount = transactionGlEntry.getAmount();
            String debit = unformattedAmount >= 0 ? null : AmountUtils.getInstance().formatAmount(originalCurrency, -unformattedAmount);
            String credit = debit == null ? AmountUtils.getInstance().formatAmount(originalCurrency, unformattedAmount) : null;

            transactionGlEntryTransfer = new TransactionGlEntryTransfer(transactionTransfer, transactionGlEntrySequence, parentTransactionGlEntryTransfer, groupPartyTransfer,
                    transactionGlAccountCategoryTransfer, glAccountTransfer, originalCurrencyTransfer, unformattedOriginalAmount, originalDebit, originalCredit, unformattedAmount, debit, credit);

            put(transactionGlEntry, transactionGlEntryTransfer);
        }

        return transactionGlEntryTransfer;
    }

}
