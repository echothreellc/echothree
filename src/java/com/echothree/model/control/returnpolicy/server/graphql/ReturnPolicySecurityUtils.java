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

package com.echothree.model.control.returnpolicy.server.graphql;

import com.echothree.control.user.returnpolicy.server.command.GetReturnKindCommand;
import com.echothree.control.user.returnpolicy.server.command.GetReturnKindsCommand;
import com.echothree.control.user.returnpolicy.server.command.GetReturnPoliciesCommand;
import com.echothree.control.user.returnpolicy.server.command.GetReturnPolicyCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface ReturnPolicySecurityUtils {

    static boolean getHasReturnKindAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetReturnKindCommand.class);
    }

    static boolean getHasReturnKindsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetReturnKindsCommand.class);
    }

    static boolean getHasReturnPolicyAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetReturnPolicyCommand.class);
    }

    static boolean getHasReturnPoliciesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetReturnPoliciesCommand.class);
    }

}
