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
import com.echothree.model.data.accounting.common.GlAccountConstants;
import com.echothree.model.data.accounting.server.entity.GlAccountCategory;
import com.echothree.model.data.accounting.server.entity.GlAccountCategoryDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("GL account category object")
@GraphQLName("GlAccountCategory")
public class GlAccountCategoryObject
        extends BaseEntityInstanceObject {
    
    private final GlAccountCategory glAccountCategory; // Always Present
    
    public GlAccountCategoryObject(GlAccountCategory glAccountCategory) {
        super(glAccountCategory.getPrimaryKey());
        
        this.glAccountCategory = glAccountCategory;
    }

    private GlAccountCategoryDetail glAccountCategoryDetail; // Optional, use getGlAccountCategoryDetail()
    
    private GlAccountCategoryDetail getGlAccountCategoryDetail() {
        if(glAccountCategoryDetail == null) {
            glAccountCategoryDetail = glAccountCategory.getLastDetail();
        }
        
        return glAccountCategoryDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("GL account category name")
    @GraphQLNonNull
    public String getGlAccountCategoryName() {
        return getGlAccountCategoryDetail().getGlAccountCategoryName();
    }

    @GraphQLField
    @GraphQLDescription("parent GL account category")
    public GlAccountCategoryObject getParentGlAccountCategory() {
        var parentGlAccountCategory = getGlAccountCategoryDetail().getParentGlAccountCategory();
        
        return parentGlAccountCategory == null ? null : new GlAccountCategoryObject(parentGlAccountCategory);
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getGlAccountCategoryDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getGlAccountCategoryDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return accountingControl.getBestGlAccountCategoryDescription(glAccountCategory, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("GL accounts")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<GlAccountObject> getGlAccounts(final DataFetchingEnvironment env) {
        if(AccountingSecurityUtils.getHasGlAccountsAccess(env)) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            var totalCount = accountingControl.countGlAccountsByGlAccountCategory(glAccountCategory);

            try(var objectLimiter = new ObjectLimiter(env, GlAccountConstants.COMPONENT_VENDOR_NAME, GlAccountConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = accountingControl.getGlAccountsByGlAccountCategory(glAccountCategory);
                var glAccounts = entities.stream().map(GlAccountObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, glAccounts);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}
