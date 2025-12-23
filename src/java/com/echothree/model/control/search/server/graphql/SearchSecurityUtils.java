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

package com.echothree.model.control.search.server.graphql;

import com.echothree.control.user.search.server.command.GetSearchCheckSpellingActionTypeCommand;
import com.echothree.control.user.search.server.command.GetSearchCheckSpellingActionTypesCommand;
import com.echothree.control.user.search.server.command.GetSearchDefaultOperatorCommand;
import com.echothree.control.user.search.server.command.GetSearchDefaultOperatorsCommand;
import com.echothree.control.user.search.server.command.GetSearchKindCommand;
import com.echothree.control.user.search.server.command.GetSearchKindsCommand;
import com.echothree.control.user.search.server.command.GetSearchSortDirectionCommand;
import com.echothree.control.user.search.server.command.GetSearchSortDirectionsCommand;
import com.echothree.control.user.search.server.command.GetSearchSortOrderCommand;
import com.echothree.control.user.search.server.command.GetSearchSortOrdersCommand;
import com.echothree.control.user.search.server.command.GetSearchTypeCommand;
import com.echothree.control.user.search.server.command.GetSearchTypesCommand;
import com.echothree.control.user.search.server.command.GetSearchUseTypeCommand;
import com.echothree.control.user.search.server.command.GetSearchUseTypesCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface SearchSecurityUtils {

    static boolean getHasSearchCheckSpellingActionTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSearchCheckSpellingActionTypesCommand.class);
    }

    static boolean getHasSearchCheckSpellingActionTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSearchCheckSpellingActionTypeCommand.class);
    }

    static boolean getHasSearchKindsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSearchKindsCommand.class);
    }

    static boolean getHasSearchKindAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSearchKindCommand.class);
    }

    static boolean getHasSearchTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSearchTypesCommand.class);
    }

    static boolean getHasSearchTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSearchTypeCommand.class);
    }

    static boolean getHasSearchSortOrdersAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSearchSortOrdersCommand.class);
    }

    static boolean getHasSearchSortOrderAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSearchSortOrderCommand.class);
    }

    static boolean getHasSearchUseTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSearchUseTypesCommand.class);
    }

    static boolean getHasSearchUseTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSearchUseTypeCommand.class);
    }

    static boolean getHasSearchDefaultOperatorsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSearchDefaultOperatorsCommand.class);
    }

    static boolean getHasSearchDefaultOperatorAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSearchDefaultOperatorCommand.class);
    }

    static boolean getHasSearchSortDirectionsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSearchSortDirectionsCommand.class);
    }

    static boolean getHasSearchSortDirectionAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSearchSortDirectionCommand.class);
    }

}
