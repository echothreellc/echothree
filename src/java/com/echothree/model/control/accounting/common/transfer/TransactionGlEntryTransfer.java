// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
    private TransactionGlEntryTransfer parentTransactionGlEntry;
    private PartyTransfer groupParty;
    private TransactionGlAccountCategoryTransfer transactionGlAccountCategory;
    private GlAccountTransfer glAccount;
    private CurrencyTransfer originalCurrency;
    private Long unformattedOriginalAmount;
    private String originalDebit;
    private String originalCredit;
    private Long unformattedAmount;
    private String debit;
    private String credit;

    /** Creates a new instance of TransactionGlEntryTransfer */
    public TransactionGlEntryTransfer(TransactionTransfer transaction, Integer transactionGlEntrySequence, TransactionGlEntryTransfer parentTransactionGlEntry,
            PartyTransfer groupParty, TransactionGlAccountCategoryTransfer transactionGlAccountCategory, GlAccountTransfer glAccount,
            CurrencyTransfer originalCurrency, Long unformattedOriginalAmount, String originalDebit, String originalCredit, Long unformattedAmount,
            String debit, String credit) {
        this.transaction = transaction;
        this.transactionGlEntrySequence = transactionGlEntrySequence;
        this.parentTransactionGlEntry = parentTransactionGlEntry;
        this.groupParty = groupParty;
        this.transactionGlAccountCategory = transactionGlAccountCategory;
        this.glAccount = glAccount;
        this.originalCurrency = originalCurrency;
        this.unformattedOriginalAmount = unformattedOriginalAmount;
        this.originalDebit = originalDebit;
        this.originalCredit = originalCredit;
        this.unformattedAmount = unformattedAmount;
        this.debit = debit;
        this.credit = credit;
    }

    public TransactionTransfer getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionTransfer transaction) {
        this.transaction = transaction;
    }

    public Integer getTransactionGlEntrySequence() {
        return transactionGlEntrySequence;
    }

    public void setTransactionGlEntrySequence(Integer transactionGlEntrySequence) {
        this.transactionGlEntrySequence = transactionGlEntrySequence;
    }

    public TransactionGlEntryTransfer getParentTransactionGlEntry() {
        return parentTransactionGlEntry;
    }

    public void setParentTransactionGlEntry(TransactionGlEntryTransfer parentTransactionGlEntry) {
        this.parentTransactionGlEntry = parentTransactionGlEntry;
    }

    public PartyTransfer getGroupParty() {
        return groupParty;
    }

    public void setGroupParty(PartyTransfer groupParty) {
        this.groupParty = groupParty;
    }

    public TransactionGlAccountCategoryTransfer getTransactionGlAccountCategory() {
        return transactionGlAccountCategory;
    }

    public void setTransactionGlAccountCategory(TransactionGlAccountCategoryTransfer transactionGlAccountCategory) {
        this.transactionGlAccountCategory = transactionGlAccountCategory;
    }

    public GlAccountTransfer getGlAccount() {
        return glAccount;
    }

    public void setGlAccount(GlAccountTransfer glAccount) {
        this.glAccount = glAccount;
    }

    public CurrencyTransfer getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(CurrencyTransfer originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public Long getUnformattedOriginalAmount() {
        return unformattedOriginalAmount;
    }

    public void setUnformattedOriginalAmount(Long unformattedOriginalAmount) {
        this.unformattedOriginalAmount = unformattedOriginalAmount;
    }

    public String getOriginalDebit() {
        return originalDebit;
    }

    public void setOriginalDebit(String originalDebit) {
        this.originalDebit = originalDebit;
    }

    public String getOriginalCredit() {
        return originalCredit;
    }

    public void setOriginalCredit(String originalCredit) {
        this.originalCredit = originalCredit;
    }

    public Long getUnformattedAmount() {
        return unformattedAmount;
    }

    public void setUnformattedAmount(Long unformattedAmount) {
        this.unformattedAmount = unformattedAmount;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

}
