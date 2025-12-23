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

package com.echothree.model.control.party.server.graphql;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.control.user.server.control.UserControl;
import com.echothree.model.data.party.server.entity.RoleType;
import com.echothree.util.server.persistence.Session;
import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.schema.DataFetchingEnvironment;

@GraphQLDescription("role type object")
@GraphQLName("RoleType")
public class RoleTypeObject
        extends BaseEntityInstanceObject {

    private final RoleType roleType; // Always Present
    
    public RoleTypeObject(RoleType roleType) {
        super(roleType.getPrimaryKey());

        this.roleType = roleType;
    }
    
    @GraphQLField
    @GraphQLDescription("role type name")
    @GraphQLNonNull
    public String getRoleTypeName() {
        return roleType.getRoleTypeName();
    }

    @GraphQLField
    @GraphQLDescription("parent role type")
    public RoleTypeObject getParentRoleType() {
        var parentRoleType = roleType.getParentRoleType();

        return parentRoleType == null ? null : new RoleTypeObject(parentRoleType);
    }

    @GraphQLField
    @GraphQLDescription("description")
    @GraphQLNonNull
    public String getDescription(final DataFetchingEnvironment env) {
        var partyControl = Session.getModelController(PartyControl.class);
        var userControl = Session.getModelController(UserControl.class);

        return partyControl.getBestRoleTypeDescription(roleType, userControl.getPreferredLanguageFromUserVisit(BaseGraphQl.getUserVisit(env)));
    }

}
