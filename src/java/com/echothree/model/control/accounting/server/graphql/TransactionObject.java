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

import com.echothree.model.control.accounting.common.workflow.TransactionStatusConstants;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.accounting.server.control.TransactionTimeControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.party.server.graphql.PartyObject;
import com.echothree.model.control.party.server.graphql.PartySecurityUtils;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.accounting.common.TransactionEntityRoleConstants;
import com.echothree.model.data.accounting.common.TransactionGlEntryConstants;
import com.echothree.model.data.accounting.common.TransactionTimeConstants;
import com.echothree.model.data.accounting.server.entity.Transaction;
import com.echothree.model.data.accounting.server.entity.TransactionDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("transaction object")
@GraphQLName("Transaction")
public class TransactionObject
        extends BaseEntityInstanceObject {
    
    private final Transaction transaction; // Always Present
    
    public TransactionObject(Transaction transaction) {
        super(transaction.getPrimaryKey());
        
        this.transaction = transaction;
    }

    private TransactionDetail transactionDetail; // Optional, use getTransactionDetail()
    
    private TransactionDetail getTransactionDetail() {
        if(transactionDetail == null) {
            transactionDetail = transaction.getLastDetail();
        }
        
        return transactionDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("transaction group name")
    @GraphQLNonNull
    public String getTransactionName() {
        return getTransactionDetail().getTransactionName();
    }

    @GraphQLField
    @GraphQLDescription("group party")
    public PartyObject getGroupParty(final DataFetchingEnvironment env) {
        var groupParty = getTransactionDetail().getGroupParty();

        return PartySecurityUtils.getHasPartyAccess(env, groupParty) ? new PartyObject(groupParty) : null;
    }

    @GraphQLField
    @GraphQLDescription("transaction group")
    public TransactionGroupObject getTransactionGroup(final DataFetchingEnvironment env) {
        var transactionGroup = getTransactionDetail().getTransactionGroup();

        return AccountingSecurityUtils.getHasTransactionGroupAccess(env) ? new TransactionGroupObject(transactionGroup) : null;
    }

    @GraphQLField
    @GraphQLDescription("transaction type")
    public TransactionTypeObject getTransactionType(final DataFetchingEnvironment env) {
        var transactionType = getTransactionDetail().getTransactionType();

        return AccountingSecurityUtils.getHasTransactionTypeAccess(env) ? new TransactionTypeObject(transactionType) : null;
    }

    @GraphQLField
    @GraphQLDescription("transaction status")
    public WorkflowEntityStatusObject getTransactionStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, TransactionStatusConstants.Workflow_TRANSACTION_STATUS);
    }

    @GraphQLField
    @GraphQLDescription("transaction times")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<TransactionTimeObject> getTransactionTimes(final DataFetchingEnvironment env) {
//        if(AccountingSecurityUtils.getHasTransactionTimesAccess(env)) {
            var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
            var totalCount = transactionTimeControl.countTransactionTimesByTransaction(transaction);
    
            try(var objectLimiter = new ObjectLimiter(env, TransactionTimeConstants.COMPONENT_VENDOR_NAME, TransactionTimeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = transactionTimeControl.getTransactionTimesByTransaction(transaction);
                var objects = entities.stream().map(TransactionTimeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));
    
                return new CountedObjects<>(objectLimiter, objects);
            }
//        } else {
//            return Connections.emptyConnection();
//        }
    }

    @GraphQLField
    @GraphQLDescription("transaction GL entries")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<TransactionGlEntryObject> getTransactionGlEntries(final DataFetchingEnvironment env) {
//        if(AccountingSecurityUtils.getHasTransactionGlEntriesAccess(env)) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            var totalCount = accountingControl.countTransactionGlEntryByTransaction(transaction);
    
            try(var objectLimiter = new ObjectLimiter(env, TransactionGlEntryConstants.COMPONENT_VENDOR_NAME, TransactionGlEntryConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = accountingControl.getTransactionGlEntriesByTransaction(transaction);
                var objects = entities.stream().map(TransactionGlEntryObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));
    
                return new CountedObjects<>(objectLimiter, objects);
            }
//        } else {
//            return Connections.emptyConnection();
//        }
    }

    @GraphQLField
    @GraphQLDescription("transaction entity roles")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<TransactionEntityRoleObject> getTransactionEntityRoles(final DataFetchingEnvironment env) {
//        if(AccountingSecurityUtils.getHasTransactionEntityRolesAccess(env)) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            var totalCount = accountingControl.countTransactionEntityRolesByTransaction(transaction);

            try(var objectLimiter = new ObjectLimiter(env, TransactionEntityRoleConstants.COMPONENT_VENDOR_NAME, TransactionEntityRoleConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = accountingControl.getTransactionEntityRolesByTransaction(transaction);
                var objects = entities.stream().map(TransactionEntityRoleObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, objects);
            }
//        } else {
//            return Connections.emptyConnection();
//        }
    }

}
