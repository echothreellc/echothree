// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
    @GraphQLDescription("item accounting category name")
    @GraphQLNonNull
    public String getGlAccountName() {
        return getGlAccountDetail().getGlAccountName();
    }

    @GraphQLField
    @GraphQLDescription("parent item accounting category")
    public GlAccountObject getParentGlAccount() {
        GlAccount parentGlAccount = getGlAccountDetail().getParentGlAccount();
        
        return parentGlAccount == null ? null : new GlAccountObject(parentGlAccount);
    }

    // TODO:
    //| gladt_glatyp_glaccounttypeid   | bigint      | NO   | MUL | NULL    |       |
    //| gladt_glacls_glaccountclassid  | bigint      | NO   | MUL | NULL    |       |
    //| gladt_glac_glaccountcategoryid | bigint      | YES  | MUL | NULL    |       |
    //| gladt_glrtyp_glresourcetypeid  | bigint      | NO   | MUL | NULL    |       |

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
