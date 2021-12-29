// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

import com.echothree.control.user.security.server.command.GetSecurityRoleGroupCommand;
import com.echothree.control.user.security.server.command.GetSecurityRoleGroupsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public final class SecuritySecurityUtils
        extends BaseGraphQl {

    private static class FilterSecurityUtilsHolder {
        static SecuritySecurityUtils instance = new SecuritySecurityUtils();
    }
    
    public static SecuritySecurityUtils getInstance() {
        return FilterSecurityUtilsHolder.instance;
    }

    public boolean getHasSecurityRoleGroupsAccess(final DataFetchingEnvironment env) {
        return getGraphQlContext(env).hasAccess(GetSecurityRoleGroupsCommand.class);
    }

    public boolean getHasSecurityRoleGroupAccess(final DataFetchingEnvironment env) {
        return getGraphQlContext(env).hasAccess(GetSecurityRoleGroupCommand.class);
    }

}
