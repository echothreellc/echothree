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
import com.echothree.model.data.accounting.server.entity.GlAccountClass;
import com.echothree.model.data.accounting.server.entity.GlAccountClassDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.connection.GraphQLConnection;
import graphql.schema.DataFetchingEnvironment;
import java.util.ArrayList;
import java.util.stream.Collectors;

@GraphQLDescription("GL account class object")
@GraphQLName("GlAccountClass")
public class GlAccountClassObject
        extends BaseEntityInstanceObject {
    
    private final GlAccountClass glAccountClass; // Always Present
    
    public GlAccountClassObject(GlAccountClass glAccountClass) {
        super(glAccountClass.getPrimaryKey());
        
        this.glAccountClass = glAccountClass;
    }

    private GlAccountClassDetail glAccountClassDetail; // Optional, use getGlAccountClassDetail()
    
    private GlAccountClassDetail getGlAccountClassDetail() {
        if(glAccountClassDetail == null) {
            glAccountClassDetail = glAccountClass.getLastDetail();
        }
        
        return glAccountClassDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("GL account class name")
    @GraphQLNonNull
    public String getGlAccountClassName() {
        return getGlAccountClassDetail().getGlAccountClassName();
    }

    @GraphQLField
    @GraphQLDescription("parent GL account class")
    public GlAccountClassObject getParentGlAccountClass() {
        var parentGlAccountClass = getGlAccountClassDetail().getParentGlAccountClass();
        
        return parentGlAccountClass == null ? null : new GlAccountClassObject(parentGlAccountClass);
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getGlAccountClassDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getGlAccountClassDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var accountingControl = Session.getModelController(AccountingControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return accountingControl.getBestGlAccountClassDescription(glAccountClass, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

    @GraphQLField
    @GraphQLDescription("GL accounts")
    @GraphQLNonNull
    @GraphQLConnection(connectionFetcher = CountingDataConnectionFetcher.class)
    public CountingPaginatedData<GlAccountObject> getGlAccounts(final DataFetchingEnvironment env) {
        if(AccountingSecurityUtils.getHasGlAccountsAccess(env)) {
            var accountingControl = Session.getModelController(AccountingControl.class);
            var totalCount = accountingControl.countGlAccountsByGlAccountClass(glAccountClass);

            try(var objectLimiter = new ObjectLimiter(env, GlAccountConstants.COMPONENT_VENDOR_NAME, GlAccountConstants.ENTITY_TYPE_NAME, totalCount)) {
                var entities = accountingControl.getGlAccountsByGlAccountClass(glAccountClass);
                var glAccounts = entities.stream().map(GlAccountObject::new).collect(Collectors.toCollection(() -> new ArrayList<>(entities.size())));

                return new CountedObjects<>(objectLimiter, glAccounts);
            }
        } else {
            return Connections.emptyConnection();
        }
    }
    
}
