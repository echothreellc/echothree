// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.control.user.order.server.command.GetOrderTypeCommand;
import com.echothree.control.user.order.server.command.GetOrderTypesCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public final class OrderSecurityUtils
        extends BaseGraphQl {

    private static class ItemSecurityUtilsHolder {
        static OrderSecurityUtils instance = new OrderSecurityUtils();
    }
    
    public static OrderSecurityUtils getInstance() {
        return ItemSecurityUtilsHolder.instance;
    }

    public boolean getHasOrderTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetOrderTypeCommand.class);
    }

    public boolean getHasOrderTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetOrderTypesCommand.class);
    }

    public boolean getHasOrderPriorityAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetOrderPriorityCommand.class);
    }

    public boolean getHasOrderPrioritiesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetOrderPrioritiesCommand.class);
    }

}
