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

package com.echothree.model.control.chain.server.graphql;

import com.echothree.control.user.chain.server.command.GetChainActionCommand;
import com.echothree.control.user.chain.server.command.GetChainActionSetCommand;
import com.echothree.control.user.chain.server.command.GetChainActionSetsCommand;
import com.echothree.control.user.chain.server.command.GetChainActionTypeCommand;
import com.echothree.control.user.chain.server.command.GetChainActionTypesCommand;
import com.echothree.control.user.chain.server.command.GetChainActionsCommand;
import com.echothree.control.user.chain.server.command.GetChainCommand;
import com.echothree.control.user.chain.server.command.GetChainKindCommand;
import com.echothree.control.user.chain.server.command.GetChainKindsCommand;
import com.echothree.control.user.chain.server.command.GetChainTypeCommand;
import com.echothree.control.user.chain.server.command.GetChainTypesCommand;
import com.echothree.control.user.chain.server.command.GetChainsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface ChainSecurityUtils {

    static boolean getHasChainKindsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetChainKindsCommand.class);
    }

    static boolean getHasChainKindAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetChainKindCommand.class);
    }

    static boolean getHasChainTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetChainTypesCommand.class);
    }

    static boolean getHasChainTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetChainTypeCommand.class);
    }

    static boolean getHasChainsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetChainsCommand.class);
    }

    static boolean getHasChainAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetChainCommand.class);
    }

    static boolean getHasChainActionSetsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetChainActionSetsCommand.class);
    }

    static boolean getHasChainActionSetAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetChainActionSetCommand.class);
    }

    static boolean getHasChainActionTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetChainActionTypesCommand.class);
    }

    static boolean getHasChainActionTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetChainActionTypeCommand.class);
    }

    static boolean getHasChainActionsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetChainActionsCommand.class);
    }

    static boolean getHasChainActionAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetChainActionCommand.class);
    }

}
