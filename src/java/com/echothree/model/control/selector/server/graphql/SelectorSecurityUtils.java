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

package com.echothree.model.control.selector.server.graphql;

import com.echothree.control.user.selector.server.command.GetSelectorCommand;
import com.echothree.control.user.selector.server.command.GetSelectorKindCommand;
import com.echothree.control.user.selector.server.command.GetSelectorKindsCommand;
import com.echothree.control.user.selector.server.command.GetSelectorTypeCommand;
import com.echothree.control.user.selector.server.command.GetSelectorTypesCommand;
import com.echothree.control.user.selector.server.command.GetSelectorsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface SelectorSecurityUtils {

    static boolean getHasSelectorKindAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSelectorKindCommand.class);
    }

    static boolean getHasSelectorKindsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSelectorKindsCommand.class);
    }

    static boolean getHasSelectorTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSelectorTypeCommand.class);
    }

    static boolean getHasSelectorTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSelectorTypesCommand.class);
    }

    static boolean getHasSelectorAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSelectorCommand.class);
    }

    static boolean getHasSelectorsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSelectorsCommand.class);
    }

}
