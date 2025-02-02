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

package com.echothree.model.control.accounting.server.graphql;

import com.echothree.model.control.accounting.common.workflow.TransactionGroupStatusConstants;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.workflow.server.graphql.WorkflowEntityStatusObject;
import com.echothree.model.data.accounting.server.entity.TransactionGroup;
import com.echothree.model.data.accounting.server.entity.TransactionGroupDetail;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

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

}
