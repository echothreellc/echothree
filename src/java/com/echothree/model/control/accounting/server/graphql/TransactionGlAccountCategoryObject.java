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
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategory;
import com.echothree.model.data.accounting.server.entity.TransactionGlAccountCategoryDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("transaction GL account category object")
@GraphQLName("TransactionGlAccountCategory")
public class TransactionGlAccountCategoryObject
        extends BaseEntityInstanceObject {
    
    private final TransactionGlAccountCategory transactionGlAccountCategory; // Always Present
    
    public TransactionGlAccountCategoryObject(TransactionGlAccountCategory transactionGlAccountCategory) {
        super(transactionGlAccountCategory.getPrimaryKey());

        this.transactionGlAccountCategory = transactionGlAccountCategory;
    }

    private TransactionGlAccountCategoryDetail transactionGlAccountCategoryDetail; // Optional, use getTransactionGlAccountCategoryDetail()
    
    private TransactionGlAccountCategoryDetail getTransactionGlAccountCategoryDetail() {
        if(transactionGlAccountCategoryDetail == null) {
            transactionGlAccountCategoryDetail = transactionGlAccountCategory.getLastDetail();
        }
        
        return transactionGlAccountCategoryDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("transaction type")
    @GraphQLNonNull
    public TransactionTypeObject getTransactionType(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getHasTransactionTypeAccess(env) ?
                new TransactionTypeObject(getTransactionGlAccountCategoryDetail().getTransactionType()) :
                null;
    }

    @GraphQLField
    @GraphQLDescription("transaction GL account category name")
    @GraphQLNonNull
    public String getTransactionGlAccountCategoryName() {
        return getTransactionGlAccountCategoryDetail().getTransactionGlAccountCategoryName();
    }

    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getTransactionGlAccountCategoryDetail().getSortOrder();
    }

    @GraphQLField
    @GraphQLDescription("GL account category")
    public GlAccountCategoryObject getGlAccountCategory(final DataFetchingEnvironment env) {
        var glaccountCategory = getTransactionGlAccountCategoryDetail().getGlAccountCategory();

        return glaccountCategory == null ? null :
                AccountingSecurityUtils.getHasGlAccountCategoryAccess(env) ?
                new GlAccountCategoryObject(glaccountCategory) :
                null;
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return accountingControl.getBestTransactionGlAccountCategoryDescription(transactionGlAccountCategory, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
