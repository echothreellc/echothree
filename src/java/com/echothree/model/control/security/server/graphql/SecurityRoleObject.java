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

package com.echothree.model.control.security.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.security.server.control.SecurityControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.security.server.entity.SecurityRole;
import com.echothree.model.data.security.server.entity.SecurityRoleDetail;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("security role object")
@GraphQLName("SecurityRole")
public class SecurityRoleObject
        extends BaseEntityInstanceObject {
    
    private final SecurityRole securityRole; // Always Present
    
    public SecurityRoleObject(SecurityRole securityRole) {
        super(securityRole.getPrimaryKey());
        
        this.securityRole = securityRole;
    }

    private SecurityRoleDetail securityRoleDetail; // Optional, use getSecurityRoleDetail()
    
    private SecurityRoleDetail getSecurityRoleDetail() {
        if(securityRoleDetail == null) {
            securityRoleDetail = securityRole.getLastDetail();
        }
        
        return securityRoleDetail;
    }
    
    @GraphQLField
    @GraphQLDescription("security role name")
    @GraphQLNonNull
    public String getSecurityRoleName() {
        return getSecurityRoleDetail().getSecurityRoleName();
    }

    @GraphQLField
    @GraphQLDescription("security role group")
    public SecurityRoleGroupObject getSecurityRoleGroup(final DataFetchingEnvironment env) {
        return SecuritySecurityUtils.getHasSecurityRoleGroupAccess(env) ? new SecurityRoleGroupObject(getSecurityRoleDetail().getSecurityRoleGroup()) : null;
    }

    @GraphQLField
    @GraphQLDescription("is default")
    @GraphQLNonNull
    public boolean getIsDefault() {
        return getSecurityRoleDetail().getIsDefault();
    }
    
    @GraphQLField
    @GraphQLDescription("sort order")
    @GraphQLNonNull
    public int getSortOrder() {
        return getSecurityRoleDetail().getSortOrder();
    }
    
    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var securityControl = Session.getModelController(SecurityControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return securityControl.getBestSecurityRoleDescription(securityRole, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
