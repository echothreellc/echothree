// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.accounting.server.graphql;

import com.echothree.control.user.accounting.server.command.GetCurrenciesCommand;
import com.echothree.control.user.accounting.server.command.GetCurrencyCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountCategoriesCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountCategoryCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountClassCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountClassesCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountTypeCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountTypesCommand;
import com.echothree.control.user.accounting.server.command.GetGlAccountsCommand;
import com.echothree.control.user.accounting.server.command.GetGlResourceTypeCommand;
import com.echothree.control.user.accounting.server.command.GetGlResourceTypesCommand;
import com.echothree.control.user.accounting.server.command.GetItemAccountingCategoriesCommand;
import com.echothree.control.user.accounting.server.command.GetItemAccountingCategoryCommand;
import com.echothree.control.user.accounting.server.command.GetSymbolPositionCommand;
import com.echothree.control.user.accounting.server.command.GetSymbolPositionsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface AccountingSecurityUtils {

    static boolean getHasSymbolPositionAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSymbolPositionCommand.class);
    }

    static boolean getHasSymbolPositionsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSymbolPositionsCommand.class);
    }

    static boolean getHasCurrencyAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetCurrencyCommand.class);
    }

    static boolean getHasCurrenciesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetCurrenciesCommand.class);
    }

    static boolean getHasItemAccountingCategoryAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemAccountingCategoryCommand.class);
    }

    static boolean getHasItemAccountingCategoriesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetItemAccountingCategoriesCommand.class);
    }

    static boolean getHasGlAccountAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGlAccountCommand.class);
    }

    static boolean getHasGlAccountsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGlAccountsCommand.class);
    }

    static boolean getHasGlAccountTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGlAccountTypeCommand.class);
    }

    static boolean getHasGlAccountTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGlAccountTypesCommand.class);
    }

    static boolean getHasGlAccountClassAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGlAccountClassCommand.class);
    }

    static boolean getHasGlAccountClassesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGlAccountClassesCommand.class);
    }

    static boolean getHasGlAccountCategoryAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGlAccountCategoryCommand.class);
    }

    static boolean getHasGlAccountCategoriesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGlAccountCategoriesCommand.class);
    }

    static boolean getHasGlResourceTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGlResourceTypeCommand.class);
    }

    static boolean getHasGlResourceTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetGlResourceTypesCommand.class);
    }

}
