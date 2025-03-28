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

import com.echothree.model.control.accounting.server.control.TransactionTimeControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.control.graphql.server.graphql.TimeObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.accounting.server.entity.TransactionTime;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("transaction time object")
@GraphQLName("TransactionTime")
public class TransactionTimeObject
        extends BaseObject {

    private final TransactionTime transactionTime; // Always Present

    public TransactionTimeObject(TransactionTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    @GraphQLField
    @GraphQLDescription("transaction")
    public TransactionObject getTransaction(final DataFetchingEnvironment env) {
        var transaction = transactionTime.getTransaction();

        return AccountingSecurityUtils.getHasTransactionAccess(env) ? new TransactionObject(transaction) : null;
    }

    @GraphQLField
    @GraphQLDescription("transaction time type")
    public TransactionTimeTypeObject getTransactionTimeType(final DataFetchingEnvironment env) {
        var transactionTimeType = transactionTime.getTransactionTimeType();

        return AccountingSecurityUtils.getHasTransactionTimeTypeAccess(env) ? new TransactionTimeTypeObject(transactionTimeType) : null;
    }

    @GraphQLField
    @GraphQLDescription("time")
    @GraphQLNonNull
    public TimeObject getTime() {
        return new TimeObject(transactionTime.getTime());
    }

}
