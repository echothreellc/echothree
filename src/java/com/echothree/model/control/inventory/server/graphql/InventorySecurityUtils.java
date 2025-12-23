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

package com.echothree.model.control.inventory.server.graphql;

import com.echothree.control.user.inventory.server.command.GetAllocationPrioritiesCommand;
import com.echothree.control.user.inventory.server.command.GetAllocationPriorityCommand;
import com.echothree.control.user.inventory.server.command.GetInventoryConditionCommand;
import com.echothree.control.user.inventory.server.command.GetInventoryConditionsCommand;
import com.echothree.control.user.inventory.server.command.GetInventoryTransactionTypeCommand;
import com.echothree.control.user.inventory.server.command.GetInventoryTransactionTypesCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface InventorySecurityUtils {

    static boolean getHasInventoryConditionAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetInventoryConditionCommand.class);
    }

    static boolean getHasInventoryConditionsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetInventoryConditionsCommand.class);
    }

    static boolean getHasAllocationPriorityAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetAllocationPriorityCommand.class);
    }

    static boolean getHasAllocationPrioritiesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetAllocationPrioritiesCommand.class);
    }

    static boolean getHasInventoryTransactionTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetInventoryTransactionTypeCommand.class);
    }

    static boolean getHasInventoryTransactionTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetInventoryTransactionTypesCommand.class);
    }

}
