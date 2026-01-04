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

package com.echothree.model.control.order.server.graphql;

import com.echothree.control.user.order.server.command.GetOrderPrioritiesCommand;
import com.echothree.control.user.order.server.command.GetOrderPriorityCommand;
import com.echothree.control.user.order.server.command.GetOrderTimeTypeCommand;
import com.echothree.control.user.order.server.command.GetOrderTimeTypesCommand;
import com.echothree.control.user.order.server.command.GetOrderTypeCommand;
import com.echothree.control.user.order.server.command.GetOrderTypesCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface OrderSecurityUtils {

    static boolean getHasOrderTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOrderTypeCommand.class);
    }

    static boolean getHasOrderTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOrderTypesCommand.class);
    }

    static boolean getHasOrderPriorityAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOrderPriorityCommand.class);
    }

    static boolean getHasOrderPrioritiesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOrderPrioritiesCommand.class);
    }

    static boolean getHasOrderTimeTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOrderTimeTypeCommand.class);
    }

    static boolean getHasOrderTimeTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetOrderTimeTypesCommand.class);
    }

    static boolean getHasOrderTimeAccess(final DataFetchingEnvironment env) {
        return true;
    }

    static boolean getHasOrderTimesAccess(final DataFetchingEnvironment env) {
        return true;
    }

    static boolean getHasOrderRoleTypeAccess(final DataFetchingEnvironment env) {
        return true;
    }

    static boolean getHasOrderRoleTypesAccess(final DataFetchingEnvironment env) {
        return true;
    }

    static boolean getHasOrderRoleAccess(final DataFetchingEnvironment env) {
        return true;
    }

    static boolean getHasOrderRolesAccess(final DataFetchingEnvironment env) {
        return true;
    }

}
