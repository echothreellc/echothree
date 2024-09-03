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

package com.echothree.model.control.accounting.server.logic;

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.model.data.accounting.server.entity.TransactionDetail;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRole;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleType;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccount;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionGlEntry;
import com.echothree.model.data.accounting.server.entity.TransactionStatus;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.Session;

public class TransactionLogic {

    private TransactionLogic() {
        super();
    }

    private static class TransactionLogicHolder {
        static TransactionLogic instance = new TransactionLogic();
    }

    public static TransactionLogic getInstance() {
        return TransactionLogicHolder.instance;
    }
    
    public Transaction createTransactionUsingNames(final Session session, final Party groupParty, final String transactionTypeName, final Long postingTime, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        
        return createTransaction(session, groupParty, accountingControl.getTransactionTypeByName(transactionTypeName), postingTime, createdBy);
    }
    
    public Transaction createTransaction(final Session session, final Party groupParty, final TransactionType transactionType, final Long postingTime, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        
        return accountingControl.createTransaction(groupParty, transactionType, postingTime == null? session.START_TIME_LONG: postingTime, createdBy);
    }
    
    public TransactionGlEntry createTransactionGlEntryUsingNames(final Transaction transaction, final Party groupParty, final String transactionGlAccountCategoryName,
            final GlAccount glAccount, final Currency originalCurrency, final Long originalAmount, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionDetail = transaction.getLastDetail();
        
        return createTransactionGlEntry(transaction, groupParty == null? transactionDetail.getGroupParty(): groupParty,
                accountingControl.getTransactionGlAccountCategoryByName(transactionDetail.getTransactionType(), transactionGlAccountCategoryName), glAccount, originalCurrency, originalAmount,
                createdBy);
    }
    
    private GlAccount getGlAccount(final AccountingControl accountingControl, final TransactionGlAccountCategory transactionGlAccountCategory, GlAccount glAccount) {
        if(glAccount == null) {
            var transactionGlAccount = accountingControl.getTransactionGlAccount(transactionGlAccountCategory);
            
            if(transactionGlAccount == null) {
                throw new IllegalArgumentException("glAccount is a required parameter");
            } else {
                glAccount = transactionGlAccount.getGlAccount();
            }
        }
        
        return glAccount;
    }
    
    private Integer getTransactionGlEntrySequence(final AccountingControl accountingControl, final Transaction transaction) {
        var transactionStatus = accountingControl.getTransactionStatusForUpdate(transaction);
        Integer transactionGlEntrySequence = transactionStatus.getTransactionGlEntrySequence() + 1;
        
        transactionStatus.setTransactionGlEntrySequence(transactionGlEntrySequence);
        
        return transactionGlEntrySequence;
    }
    
    private Long getAmount(final GlAccount glAccount, final Currency originalCurrency, final Long originalAmount) {
        var currency = glAccount.getLastDetail().getCurrency();
        
        Long amount = null;
        if(originalCurrency.equals(currency)) {
            amount = originalAmount;
        } else {
            throw new IllegalArgumentException("Currency conversion is not available");
        }
        
        return amount;
    }
    
    public TransactionGlEntry createTransactionGlEntry(final Transaction transaction, final Party groupParty, final TransactionGlAccountCategory transactionGlAccountCategory,
            GlAccount glAccount, final Currency originalCurrency, final Long originalAmount, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        
        glAccount = getGlAccount(accountingControl, transactionGlAccountCategory, glAccount);

        var amount = getAmount(glAccount, originalCurrency, originalAmount);
        
        return accountingControl.createTransactionGlEntry(transaction, getTransactionGlEntrySequence(accountingControl, transaction), null, groupParty, transactionGlAccountCategory, glAccount,
                originalCurrency, originalAmount, amount, createdBy);
    }
    
    public TransactionEntityRole createTransactionEntityRoleUsingNames(final Transaction transaction, final String transactionEntityRoleTypeName, final BasePK pk,
            final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionEntityRoleType = accountingControl.getTransactionEntityRoleTypeByName(transaction.getLastDetail().getTransactionType(),
                transactionEntityRoleTypeName);
        
        return createTransactionEntityRole(transaction, transactionEntityRoleType, pk, createdBy);
    }
    
    public TransactionEntityRole createTransactionEntityRole(final Transaction transaction, final TransactionEntityRoleType transactionEntityRoleType, final BasePK pk,
            final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var coreControl = Session.getModelController(CoreControl.class);
        var entityInstance = coreControl.getEntityInstanceByBasePK(pk);
        
        if(!transactionEntityRoleType.getLastDetail().getEntityType().equals(entityInstance.getEntityType())) {
            throw new IllegalArgumentException("entityInstance is not of the required EntityType");
        }
        
        return accountingControl.createTransactionEntityRole(transaction, transactionEntityRoleType, entityInstance, createdBy);
    }
    
    public void finishTransaction(final Transaction transaction) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        
        accountingControl.removeTransactionStatusByTransaction(transaction);
        
        PostingLogic.getInstance().postTransaction(transaction);
    }
    
    public void testTransaction(final Session session, final BasePK testedBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var partyControl = Session.getModelController(PartyControl.class);
        var companyParty = partyControl.getDefaultPartyCompany().getParty();
        var divisionParty = partyControl.getDefaultPartyDivision(companyParty).getParty();
        var departmentParty = partyControl.getDefaultPartyDepartment(divisionParty).getParty();
        var originalCurrency = accountingControl.getDefaultCurrency();

        var transaction = createTransactionUsingNames(session, departmentParty, "TEST", null, testedBy);
        createTransactionGlEntryUsingNames(transaction, null, "TEST_ACCOUNT_A", null, originalCurrency, 1999L, testedBy);
        createTransactionGlEntryUsingNames(transaction, null, "TEST_ACCOUNT_B", null, originalCurrency, -1999L, testedBy);
        createTransactionEntityRoleUsingNames(transaction, "TEST_ENTITY_INSTANCE_ROLE_TYPE", testedBy, testedBy);
        finishTransaction(transaction);
    }

}
