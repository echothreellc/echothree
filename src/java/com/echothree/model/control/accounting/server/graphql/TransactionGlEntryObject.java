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

package com.echothree.model.control.accounting.server.graphql;

import com.echothree.model.control.graphql.server.graphql.AmountObject;
import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.party.server.graphql.PartyObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.data.accounting.server.entity.TransactionGlEntry;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("transaction GL entry object")
@GraphQLName("TransactionGlEntry")
public class TransactionGlEntryObject
        extends BaseObject {

    private final TransactionGlEntry transactionGlEntry; // Always Present

    public TransactionGlEntryObject(TransactionGlEntry transactionGlEntry) {
        this.transactionGlEntry = transactionGlEntry;
    }

    @GraphQLField
    @GraphQLDescription("transaction")
    @GraphQLNonNull
    public TransactionObject getTransaction(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getHasTransactionAccess(env) ?
                new TransactionObject(transactionGlEntry.getTransaction()) :
                null;
    }

    @GraphQLField
    @GraphQLDescription("transaction GL entry sequence")
    @GraphQLNonNull
    public int getTransactionGlEntrySequence() {
        return transactionGlEntry.getTransactionGlEntrySequence();
    }

    @GraphQLField
    @GraphQLDescription("group party")
    @GraphQLNonNull
    public PartyObject getGroupParty(final DataFetchingEnvironment env) {
        var groupParty = transactionGlEntry.getGroupParty();

        return PartySecurityUtils.getHasPartyAccess(env, groupParty) ?
                new PartyObject(groupParty) :
                null;
    }

    @GraphQLField
    @GraphQLDescription("transaction GL account category")
    public TransactionGlAccountCategoryObject getTransactionGlAccountCategory(final DataFetchingEnvironment env) {
        var transactionGlAccountCategory = transactionGlEntry.getTransactionGlAccountCategory();

        return transactionGlAccountCategory == null ? null :
                AccountingSecurityUtils.getHasTransactionGlAccountCategoryAccess(env) ?
                new TransactionGlAccountCategoryObject(transactionGlAccountCategory) :
                null;
    }

    @GraphQLField
    @GraphQLDescription("GL account")
    @GraphQLNonNull
    public GlAccountObject getGlAccount(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getHasGlAccountAccess(env) ?
                new GlAccountObject(transactionGlEntry.getGlAccount()) :
                null;
    }

    @GraphQLField
    @GraphQLDescription("original currency")
    @GraphQLNonNull
    public CurrencyObject getOriginalCurrency(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getHasCurrencyAccess(env) ?
                new CurrencyObject(transactionGlEntry.getOriginalCurrency()) :
                null;
    }

    @GraphQLField
    @GraphQLDescription("original debit")
    public AmountObject getOriginalDebit() {
        var originalDebit = transactionGlEntry.getOriginalDebit();

        return originalDebit == null ? null :
                new AmountObject(transactionGlEntry.getOriginalCurrency(), transactionGlEntry.getOriginalDebit());
    }

    @GraphQLField
    @GraphQLDescription("original credit")
    public AmountObject getOriginalCredit() {
        var originalCredit = transactionGlEntry.getOriginalCredit();

        return originalCredit == null ? null :
                new AmountObject(transactionGlEntry.getOriginalCurrency(), transactionGlEntry.getOriginalCredit());
    }

    @GraphQLField
    @GraphQLDescription("original debit")
    public AmountObject getDebit() {
        var debit = transactionGlEntry.getDebit();

        return debit == null ? null :
                new AmountObject(transactionGlEntry.getGlAccount().getLastDetail().getCurrency(), transactionGlEntry.getDebit());
    }

    @GraphQLField
    @GraphQLDescription("original credit")
    public AmountObject getCredit() {
        var credit = transactionGlEntry.getCredit();

        return credit == null ? null :
                new AmountObject(transactionGlEntry.getGlAccount().getLastDetail().getCurrency(), transactionGlEntry.getCredit());
    }

}
