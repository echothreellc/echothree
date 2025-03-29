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

import com.echothree.model.control.core.server.graphql.CoreSecurityUtils;
import com.echothree.model.control.core.server.graphql.EntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.BaseObject;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRole;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("transaction entity role object")
@GraphQLName("TransactionEntityRole")
public class TransactionEntityRoleObject
        extends BaseObject {

    private final TransactionEntityRole transactionEntityRole; // Always Present

    public TransactionEntityRoleObject(TransactionEntityRole transactionEntityRole) {
        this.transactionEntityRole = transactionEntityRole;
    }

    @GraphQLField
    @GraphQLDescription("transaction")
    public TransactionObject getTransaction(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getHasTransactionAccess(env) ?
                new TransactionObject(transactionEntityRole.getTransaction()) :
                null;
    }

    @GraphQLField
    @GraphQLDescription("transaction time type")
    public TransactionEntityRoleTypeObject getTransactionEntityRoleType(final DataFetchingEnvironment env) {
        var transactionEntityRoleType = transactionEntityRole.getTransactionEntityRoleType();

        return AccountingSecurityUtils.getHasTransactionEntityRoleTypeAccess(env) ?
                new TransactionEntityRoleTypeObject(transactionEntityRoleType) :
                null;
    }

    @GraphQLField
    @GraphQLDescription("entity instance")
    public EntityInstanceObject getEntityInstance(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityInstanceAccess(env) ?
                new EntityInstanceObject(transactionEntityRole.getEntityInstance()) :
                null;
    }

}
