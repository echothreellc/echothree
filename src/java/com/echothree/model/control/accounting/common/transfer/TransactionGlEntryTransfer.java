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

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class TransactionGlEntryTransfer
        extends BaseTransfer {

    private TransactionTransfer transaction;
    private Integer transactionGlEntrySequence;
    private PartyTransfer groupParty;
    private TransactionGlAccountCategoryTransfer transactionGlAccountCategory;
    private GlAccountTransfer glAccount;
    private CurrencyTransfer originalCurrency;
    private Long unformattedOriginalDebit;
    private String originalDebit;
    private Long unformattedOriginalCredit;
    private String originalCredit;
    private Long unformattedDebit;
    private String debit;
    private Long unformattedCredit;
    private String credit;

    public TransactionGlEntryTransfer(final TransactionTransfer transaction, final Integer transactionGlEntrySequence,
            final PartyTransfer groupParty, final TransactionGlAccountCategoryTransfer transactionGlAccountCategory,
            final GlAccountTransfer glAccount, final CurrencyTransfer originalCurrency, final Long unformattedOriginalDebit,
            final String originalDebit, final Long unformattedOriginalCredit, final String originalCredit, final Long unformattedDebit,
            final String debit, final Long unformattedCredit, final String credit) {
        this.transaction = transaction;
        this.transactionGlEntrySequence = transactionGlEntrySequence;
        this.groupParty = groupParty;
        this.transactionGlAccountCategory = transactionGlAccountCategory;
        this.glAccount = glAccount;
        this.originalCurrency = originalCurrency;
        this.unformattedOriginalDebit = unformattedOriginalDebit;
        this.originalDebit = originalDebit;
        this.unformattedOriginalCredit = unformattedOriginalCredit;
        this.originalCredit = originalCredit;
        this.unformattedDebit = unformattedDebit;
        this.debit = debit;
        this.unformattedCredit = unformattedCredit;
        this.credit = credit;
    }

    public TransactionTransfer getTransaction() {
        return transaction;
    }

    public void setTransaction(final TransactionTransfer transaction) {
        this.transaction = transaction;
    }

    public Integer getTransactionGlEntrySequence() {
        return transactionGlEntrySequence;
    }

    public void setTransactionGlEntrySequence(final Integer transactionGlEntrySequence) {
        this.transactionGlEntrySequence = transactionGlEntrySequence;
    }

    public PartyTransfer getGroupParty() {
        return groupParty;
    }

    public void setGroupParty(final PartyTransfer groupParty) {
        this.groupParty = groupParty;
    }

    public TransactionGlAccountCategoryTransfer getTransactionGlAccountCategory() {
        return transactionGlAccountCategory;
    }

    public void setTransactionGlAccountCategory(final TransactionGlAccountCategoryTransfer transactionGlAccountCategory) {
        this.transactionGlAccountCategory = transactionGlAccountCategory;
    }

    public GlAccountTransfer getGlAccount() {
        return glAccount;
    }

    public void setGlAccount(final GlAccountTransfer glAccount) {
        this.glAccount = glAccount;
    }

    public CurrencyTransfer getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(final CurrencyTransfer originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public Long getUnformattedOriginalDebit() {
        return unformattedOriginalDebit;
    }

    public void setUnformattedOriginalDebit(final Long unformattedOriginalDebit) {
        this.unformattedOriginalDebit = unformattedOriginalDebit;
    }

    public String getOriginalDebit() {
        return originalDebit;
    }

    public void setOriginalDebit(final String originalDebit) {
        this.originalDebit = originalDebit;
    }

    public Long getUnformattedOriginalCredit() {
        return unformattedOriginalCredit;
    }

    public void setUnformattedOriginalCredit(final Long unformattedOriginalCredit) {
        this.unformattedOriginalCredit = unformattedOriginalCredit;
    }

    public String getOriginalCredit() {
        return originalCredit;
    }

    public void setOriginalCredit(final String originalCredit) {
        this.originalCredit = originalCredit;
    }

    public Long getUnformattedDebit() {
        return unformattedDebit;
    }

    public void setUnformattedDebit(final Long unformattedDebit) {
        this.unformattedDebit = unformattedDebit;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(final String debit) {
        this.debit = debit;
    }

    public Long getUnformattedCredit() {
        return unformattedCredit;
    }

    public void setUnformattedCredit(final Long unformattedCredit) {
        this.unformattedCredit = unformattedCredit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(final String credit) {
        this.credit = credit;
    }

}
