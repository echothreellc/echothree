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

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.core.server.graphql.CoreSecurityUtils;
import com.echothree.model.control.core.server.graphql.EntityTypeObject;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleType;
import com.echothree.model.data.accounting.server.entity.TransactionEntityRoleTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("transaction entity role type object")
@GraphQLName("TransactionEntityRoleType")
public class TransactionEntityRoleTypeObject
        extends BaseEntityInstanceObject {
    
    private final TransactionEntityRoleType transactionEntityRoleType; // Always Present
    
    public TransactionEntityRoleTypeObject(TransactionEntityRoleType transactionEntityRoleType) {
        super(transactionEntityRoleType.getPrimaryKey());

        this.transactionEntityRoleType = transactionEntityRoleType;
    }

    private TransactionEntityRoleTypeDetail transactionEntityRoleTypeDetail; // Optional, use getTransactionEntityRoleTypeDetail()
    
    private TransactionEntityRoleTypeDetail getTransactionEntityRoleTypeDetail() {
        if(transactionEntityRoleTypeDetail == null) {
            transactionEntityRoleTypeDetail = transactionEntityRoleType.getLastDetail();
        }
        
        return transactionEntityRoleTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("transaction type")
    @GraphQLNonNull
    public TransactionTypeObject getTransactionType(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getHasTransactionTypeAccess(env) ?
                new TransactionTypeObject(getTransactionEntityRoleTypeDetail().getTransactionType()) :
                null;
    }

    @GraphQLField
    @GraphQLDescription("transaction entity role type name")
    @GraphQLNonNull
    public String getTransactionEntityRoleTypeName() {
        return getTransactionEntityRoleTypeDetail().getTransactionEntityRoleTypeName();
    }

    @GraphQLField
    @GraphQLDescription("entity type")
    public EntityTypeObject getEntityType(final DataFetchingEnvironment env) {
        return CoreSecurityUtils.getHasEntityTypeAccess(env) ?
                        new EntityTypeObject(getTransactionEntityRoleTypeDetail().getEntityType()) :
                        null;
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getTransactionEntityRoleTypeDetail().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return accountingControl.getBestTransactionEntityRoleTypeDescription(transactionEntityRoleType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
