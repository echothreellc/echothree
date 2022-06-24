// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.returnpolicy.server.graphql;

import com.echothree.control.user.returnpolicy.server.command.GetReturnKindCommand;
import com.echothree.control.user.returnpolicy.server.command.GetReturnKindsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public final class ReturnPolicySecurityUtils
        extends BaseGraphQl {

    private static class ReturnPolicySecurityUtilsHolder {
        static ReturnPolicySecurityUtils instance = new ReturnPolicySecurityUtils();
    }
    
    public static ReturnPolicySecurityUtils getInstance() {
        return ReturnPolicySecurityUtilsHolder.instance;
    }
    
    public boolean getHasReturnKindAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetReturnKindCommand.class);
    }

    public boolean getHasReturnKindsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetReturnKindsCommand.class);
    }

}
