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

package com.echothree.model.control.accounting.server.logic;

import com.echothree.control.user.accounting.common.spec.TransactionUniversalSpec;
import com.echothree.model.control.accounting.common.TransactionTimeTypes;
import com.echothree.model.control.accounting.common.exception.TransactionNotBalancedException;
import com.echothree.model.control.accounting.common.exception.UnknownTransactionNameException;
import com.echothree.model.control.accounting.common.workflow.TransactionStatusConstants;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.database.TransactionBalancedQuery;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.logic.EntityInstanceLogic;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.control.workflow.server.logic.WorkflowEntranceLogic;
import com.echothree.model.control.workflow.server.logic.WorkflowLogic;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRole;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleType;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionGlEntry;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.party.server.entity.Party;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class TransactionLogic
        extends BaseLogic {

    protected TransactionLogic() {
        super();
    }

    public static TransactionLogic getInstance() {
        return CDI.current().select(TransactionLogic.class).get();
    }
    
    public Transaction createTransactionUsingNames(final Session session, final Party groupParty, final String transactionTypeName,
            final Long transactionTime, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        
        return createTransaction(session, groupParty, accountingControl.getTransactionTypeByName(transactionTypeName),
                transactionTime, createdBy);
    }
    
    public Transaction createTransaction(final Session session, final Party groupParty, final TransactionType transactionType,
            final Long transactionTime, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transaction = accountingControl.createTransaction(groupParty, transactionType, createdBy);

        TransactionTimeLogic.getInstance().createTransactionTime(null, transaction, TransactionTimeTypes.TRANSACTION_TIME.name(),
                transactionTime == null ? session.getStartTimeLong() : transactionTime, createdBy);

        return transaction;
    }

    public Transaction getTransactionByName(final ExecutionErrorAccumulator eea, final String transactionName,
            final EntityPermission entityPermission) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transaction = accountingControl.getTransactionByName(transactionName, entityPermission);

        if(transaction == null) {
            handleExecutionError(UnknownTransactionNameException.class, eea, ExecutionErrors.UnknownTransactionName.name(), transactionName);
        }

        return transaction;
    }

    public Transaction getTransactionByName(final ExecutionErrorAccumulator eea, final String transactionName) {
        return getTransactionByName(eea, transactionName, EntityPermission.READ_ONLY);
    }

    public Transaction getTransactionByNameForUpdate(final ExecutionErrorAccumulator eea, final String transactionName) {
        return getTransactionByName(eea, transactionName, EntityPermission.READ_WRITE);
    }

    public Transaction getTransactionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TransactionUniversalSpec universalSpec, final EntityPermission entityPermission) {
        Transaction transaction = null;
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionName = universalSpec.getTransactionName();
        var parameterCount = (transactionName == null ? 0 : 1) + EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(transactionName == null) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.Transaction.name());

                    if(!eea.hasExecutionErrors()) {
                        transaction = accountingControl.getTransactionByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    transaction = getTransactionByName(eea, transactionName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return transaction;
    }

    public Transaction getTransactionByUniversalSpec(final ExecutionErrorAccumulator eea,
            final TransactionUniversalSpec universalSpec) {
        return getTransactionByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public Transaction getTransactionByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final TransactionUniversalSpec universalSpec) {
        return getTransactionByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }
    
    public TransactionGlEntry createTransactionGlEntryUsingNames(final Transaction transaction, final Party groupParty,
            final String transactionGlAccountCategoryName, final GlAccount glAccount, final Currency originalCurrency,
            final Long originalDebit, final Long originalCredit, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionDetail = transaction.getLastDetail();
        
        return createTransactionGlEntry(transaction, groupParty == null ? transactionDetail.getGroupParty() : groupParty,
                accountingControl.getTransactionGlAccountCategoryByName(transactionDetail.getTransactionType(), transactionGlAccountCategoryName),
                glAccount, originalCurrency, originalDebit, originalCredit, createdBy);
    }
    
    private GlAccount getGlAccount(final AccountingControl accountingControl, final TransactionGlAccountCategory transactionGlAccountCategory,
            GlAccount glAccount) {
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
        
        Long amount;
        if(originalCurrency.equals(currency)) {
            amount = originalAmount;
        } else {
            throw new IllegalArgumentException("Currency conversion is not available");
        }
        
        return amount;
    }
    
    public TransactionGlEntry createTransactionGlEntry(final Transaction transaction, final Party groupParty,
            final TransactionGlAccountCategory transactionGlAccountCategory, GlAccount glAccount, final Currency originalCurrency,
            final Long originalDebit, final Long originalCredit, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        
        glAccount = getGlAccount(accountingControl, transactionGlAccountCategory, glAccount);

        var debit = originalDebit == null ? null : getAmount(glAccount, originalCurrency, originalDebit);
        var credit = originalCredit == null ? null : getAmount(glAccount, originalCurrency, originalCredit);

        return accountingControl.createTransactionGlEntry(transaction, getTransactionGlEntrySequence(accountingControl, transaction),
                groupParty, transactionGlAccountCategory, glAccount, originalCurrency, originalDebit,
                originalCredit, debit, credit, createdBy);
    }
    
    public TransactionEntityRole createTransactionEntityRoleUsingNames(final Transaction transaction,
            final String transactionEntityRoleTypeName, final BasePK pk, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var transactionEntityRoleType = accountingControl.getTransactionEntityRoleTypeByName(transaction.getLastDetail().getTransactionType(),
                transactionEntityRoleTypeName);
        
        return createTransactionEntityRole(transaction, transactionEntityRoleType, pk, createdBy);
    }
    
    public TransactionEntityRole createTransactionEntityRole(final Transaction transaction,
            final TransactionEntityRoleType transactionEntityRoleType, final BasePK pk, final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(pk);
        
        if(!transactionEntityRoleType.getLastDetail().getEntityType().equals(entityInstance.getEntityType())) {
            throw new IllegalArgumentException("entityInstance is not of the required EntityType");
        }
        
        return accountingControl.createTransactionEntityRole(transaction, transactionEntityRoleType, entityInstance, createdBy);
    }

    private void validateTransactionBalanced(final ExecutionErrorAccumulator eea, final Transaction transaction) {
        var transactionBalancedResults = new TransactionBalancedQuery().execute(transaction);

        if(!transactionBalancedResults.isEmpty()) {
            var transactionBalancedResult = transactionBalancedResults.getFirst();
            var originalDifference = transactionBalancedResult.getOriginalDifference();
            var difference = transactionBalancedResult.getDifference();

            if(originalDifference != 0 || difference != 0) {
                handleExecutionError(TransactionNotBalancedException.class, eea, ExecutionErrors.TransactionNotBalanced.name(),
                        originalDifference, difference);
            }
        }
    }
    
    public void postTransaction(final ExecutionErrorAccumulator eea, final Session session, final Transaction transaction,
            final BasePK createdBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
        var workflowControl = Session.getModelController(WorkflowControl.class);

        validateTransactionBalanced(eea, transaction);

        if(eea != null && !hasExecutionErrors(eea)) {
            accountingControl.removeTransactionStatusByTransaction(transaction);

            PostingLogic.getInstance().postTransaction(session, transaction, createdBy);

            // If it isn't in the Transaction Status workflow, assume this is a system generated transaction and
            // we've gone directly to posting it.
            var workflow = WorkflowLogic.getInstance().getWorkflowByName(null, TransactionStatusConstants.Workflow_TRANSACTION_STATUS);
            var entityInstance = entityInstanceControl.getEntityInstanceByBasePK(transaction.getPrimaryKey());
            if(!workflowControl.isEntityInWorkflow(workflow, entityInstance)) {
                var workflowEntrance = WorkflowEntranceLogic.getInstance().getWorkflowEntranceByName(null, workflow,
                        TransactionStatusConstants.WorkflowEntrance_TRANSACTION_STATUS_NEW_POSTED);

                workflowControl.addEntityToWorkflow(workflowEntrance, entityInstance, null, null, createdBy);
            }
        }
    }
    
    public void testTransaction(final ExecutionErrorAccumulator eea, final Session session, final BasePK testedBy) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var partyControl = Session.getModelController(PartyControl.class);
        var companyParty = partyControl.getDefaultPartyCompany().getParty();
        var divisionParty = partyControl.getDefaultPartyDivision(companyParty).getParty();
        var departmentParty = partyControl.getDefaultPartyDepartment(divisionParty).getParty();
        var originalCurrency = accountingControl.getDefaultCurrency();

        var transaction = createTransactionUsingNames(session, departmentParty, "TEST", null, testedBy);
        createTransactionGlEntryUsingNames(transaction, null, "TEST_ACCOUNT_A", null, originalCurrency, null, 1999L, testedBy);
        createTransactionGlEntryUsingNames(transaction, null, "TEST_ACCOUNT_B", null, originalCurrency, 1999L, null, testedBy);
        createTransactionEntityRoleUsingNames(transaction, "TEST_ENTITY_INSTANCE_ROLE_TYPE", testedBy, testedBy);
        postTransaction(eea, session, transaction, testedBy);
    }

}
