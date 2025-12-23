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
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.count.Connections;
import com.echothree.model.control.graphql.server.graphql.count.CountedObjects;
import com.echothree.model.control.graphql.server.graphql.count.CountingDataConnectionFetcher;
import com.echothree.model.control.graphql.server.graphql.count.CountingPaginatedData;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.count.ObjectLimiter;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.accounting.common.TransactionConstants;
import com.echothree.model.data.accounting.common.TransactionEntityRoleTypeConstants;
import com.echothree.model.data.accounting.common.TransactionGlAccountCategoryConstants;
import com.echothree.model.data.accounting.server.entity.TransactionType;
import com.echothree.model.data.accounting.server.entity.TransactionTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("transaction type object")
@GraphQLName("TransactionType")
public class TransactionTypeObject
        extends BaseEntityInstanceObject {
    
    private final TransactionType transactionType; // Always Present
    
    public TransactionTypeObject(TransactionType transactionType) {
        super(transactionType.getPrimaryKey());
        
        this.transactionType = transactionType;
    }

    private TransactionTypeDetail transactionTypeDetail; // Optional, use getTransactionTypeDetail()
    
    private TransactionTypeDetail getTransactionTypeDetail() {
        if(transactionTypeDetail == null) {
            transactionTypeDetail = transactionType.getLastDetail();
        }
        
        return transactionTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("transaction type name")
    @GraphQLNonNull
    public String getTransactionTypeName() {
        return getTransactionTypeDetail().getTransactionTypeName();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getTransactionTypeDetail().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return accountingControl.getBestTransactionTypeDescription(transactionType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("transactionGlAccountCategories")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<TransactionGlAccountCategoryObject> getTransactionGlAccountCategories(final DataFetchingEnvironment env) {
        if(AccountingSecurityUtils.getHasTransactionGlAccountCategoriesAccess(env)) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            var totalCount = accountingControl.countTransactionGlAccountCategoriesByTransactionType(transactionType);

            try(var objectLimiter = new ObjectLimiter(env, TransactionGlAccountCategoryConstants.COMPONENT_VENDOR_NAME, TransactionGlAccountCategoryConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = accountingControl.getTransactionGlAccountCategoriesByTransactionType(transactionType);
                var transactionGlAccountCategories = entities.stream().map(TransactionGlAccountCategoryObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, transactionGlAccountCategories);
            }
        } else {
            return Connections.emptyConnection();
        }
    }

    @GraphQLField
    @GraphQLDescription("transactionEntityRoleTypes")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<TransactionEntityRoleTypeObject> getTransactionEntityRoleTypes(final DataFetchingEnvironment env) {
        if(AccountingSecurityUtils.getHasTransactionEntityRoleTypesAccess(env)) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            var totalCount = accountingControl.countTransactionEntityRoleTypesByTransactionType(transactionType);

            try(var objectLimiter = new ObjectLimiter(env, TransactionEntityRoleTypeConstants.COMPONENT_VENDOR_NAME, TransactionEntityRoleTypeConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = accountingControl.getTransactionEntityRoleTypesByTransactionType(transactionType);
                var transactionEntityRoleTypes = entities.stream().map(TransactionEntityRoleTypeObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, transactionEntityRoleTypes);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}
