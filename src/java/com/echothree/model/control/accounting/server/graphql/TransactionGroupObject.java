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

import com.echothree.model.control.accounting.common.workflow.TransactionGroupStatusConstants;
import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.accounting.common.TransactionConstants;
import com.echothree.model.data.accounting.server.entity.TransactionGroup;
import com.echothree.model.data.accounting.server.entity.TransactionGroupDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("transaction group object")
@GraphQLName("TransactionGroup")
public class TransactionGroupObject
        extends BaseEntityInstanceObject {
    
    private final TransactionGroup transactionGroup; // Always Present
    
    public TransactionGroupObject(TransactionGroup transactionGroup) {
        super(transactionGroup.getPrimaryKey());
        
        this.transactionGroup = transactionGroup;
    }

    private TransactionGroupDetail transactionGroupDetail; // Optional, use getTransactionGroupDetail()
    
    private TransactionGroupDetail getTransactionGroupDetail() {
        if(transactionGroupDetail == null) {
            transactionGroupDetail = transactionGroup.getLastDetail();
        }
        
        return transactionGroupDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("transaction group name")
    @GraphQLNonNull
    public String getTransactionGroupName() {
        return getTransactionGroupDetail().getTransactionGroupName();
    }

    @GraphQLField
    @GraphQLDescription("transaction group status")
    public WorkflowEntityStatusObject getTransactionGroupStatus(final DataFetchingEnvironment env) {
        return getWorkflowEntityStatusObject(env, TransactionGroupStatusConstants.Workflow_TRANSACTION_GROUP_STATUS);
    }

    @GraphQLField
    @GraphQLDescription("transactions")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<TransactionObject> getTransactions(final DataFetchingEnvironment env) {
        if(AccountingSecurityUtils.getHasTransactionsAccess(env)) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            var totalCount = accountingControl.countTransactionsByTransactionGroup(transactionGroup);
    
            try(var objectLimiter = new ObjectLimiter(env, TransactionConstants.COMPONENT_VENDOR_NAME, TransactionConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = accountingControl.getTransactionsByTransactionGroup(transactionGroup);
                var transactions = entities.stream().map(TransactionObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));
    
                return new CountedObjects<>(objectLimiter, transactions);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

}
