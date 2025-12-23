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

import com.echothree.model.control.accounting.server.control.TransactionTimeControl;
import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.accounting.server.entity.TransactionTimeType;
import com.echothree.model.data.accounting.server.entity.TransactionTimeTypeDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("transaction time type object")
@GraphQLName("TransactionTimeType")
public class TransactionTimeTypeObject
        extends BaseEntityInstanceObject {

    private final TransactionTimeType transactionTimeType; // Always Present

    public TransactionTimeTypeObject(TransactionTimeType transactionTimeType) {
        super(transactionTimeType.getPrimaryKey());
        
        this.transactionTimeType = transactionTimeType;
    }

    private TransactionTimeTypeDetail transactionTimeTypeDetail; // Optional, use getTransactionTimeTypeDetail()
    
    private TransactionTimeTypeDetail getTransactionTimeTypeDetail() {
        if(transactionTimeTypeDetail == null) {
            transactionTimeTypeDetail = transactionTimeType.getLastDetail();
        }
        
        return transactionTimeTypeDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("transaction type name")
    @GraphQLNonNull
    public String getTransactionTimeTypeName() {
        return getTransactionTimeTypeDetail().getTransactionTimeTypeName();
    }


    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getTransactionTimeTypeDetail().getIsDefault();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getTransactionTimeTypeDetail().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var transactionTimeControl = Session.getModelController(TransactionTimeControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return transactionTimeControl.getBestTransactionTimeTypeDescription(transactionTimeType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
