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
import com.echothree.model.data.accounting.server.entity.GlAccount;
import com.echothree.model.data.accounting.server.entity.GlAccountDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("GL account object")
@GraphQLName("GlAccount")
public class GlAccountObject
        extends BaseEntityInstanceObject {
    
    private final GlAccount glAccount; // Always Present
    
    public GlAccountObject(GlAccount glAccount) {
        super(glAccount.getPrimaryKey());
        
        this.glAccount = glAccount;
    }

    private GlAccountDetail glAccountDetail; // Optional, use getGlAccountDetail()
    
    private GlAccountDetail getGlAccountDetail() {
        if(glAccountDetail == null) {
            glAccountDetail = glAccount.getLastDetail();
        }
        
        return glAccountDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("gl account name")
    @GraphQLNonNull
    public String getGlAccountName() {
        return getGlAccountDetail().getGlAccountName();
    }

    @GraphQLField
    @GraphQLDescription("parent gl account")
    public GlAccountObject getParentGlAccount() {
        var parentGlAccount = getGlAccountDetail().getParentGlAccount();
        
        return parentGlAccount == null ? null : new GlAccountObject(parentGlAccount);
    }

    @GraphQLField
    @GraphQLDescription("GL account type")
    public GlAccountTypeObject getGlAccountType(final DataFetchingEnvironment env) {
        var glAccountType = getGlAccountDetail().getGlAccountType();

        return glAccountType == null ? null : AccountingSecurityUtils.getHasGlAccountTypeAccess(env) ?
                new GlAccountTypeObject(glAccountType) : null;
    }

    @GraphQLField
    @GraphQLDescription("GL account class")
    public GlAccountClassObject getGlAccountClass(final DataFetchingEnvironment env) {
        var glAccountClass = getGlAccountDetail().getGlAccountClass();

        return glAccountClass == null ? null : AccountingSecurityUtils.getHasGlAccountClassAccess(env) ?
                new GlAccountClassObject(glAccountClass) : null;
    }

    @GraphQLField
    @GraphQLDescription("GL account category")
    public GlAccountCategoryObject getGlAccountCategory(final DataFetchingEnvironment env) {
        var glAccountCategory = getGlAccountDetail().getGlAccountCategory();

        return glAccountCategory == null ? null : AccountingSecurityUtils.getHasGlAccountCategoryAccess(env) ?
                new GlAccountCategoryObject(glAccountCategory) : null;
    }

    @GraphQLField
    @GraphQLDescription("GL account type")
    public GlResourceTypeObject getGlResourceType(final DataFetchingEnvironment env) {
        var glResourceType = getGlAccountDetail().getGlResourceType();

        return glResourceType == null ? null : AccountingSecurityUtils.getHasGlResourceTypeAccess(env) ?
                new GlResourceTypeObject(glResourceType) : null;
    }

    @GraphQLField
    @GraphQLDescription("currency")
    public CurrencyObject getCurrency(final DataFetchingEnvironment env) {
        return AccountingSecurityUtils.getHasCurrencyAccess(env) ? new CurrencyObject(getGlAccountDetail().getCurrency()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    public Boolean getIsDefault() {
        return getGlAccountDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return accountingControl.getBestGlAccountDescription(glAccount, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
