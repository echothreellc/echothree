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

package com.echothree.model.control.filter.server.graphql;

import com.echothree.control.user.filter.server.command.GetFilterAdjustmentAmountCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentAmountsCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentFixedAmountCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentFixedAmountsCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentPercentCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentPercentsCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentSourceCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentTypeCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentsCommand;
import com.echothree.control.user.filter.server.command.GetFilterCommand;
import com.echothree.control.user.filter.server.command.GetFilterKindCommand;
import com.echothree.control.user.filter.server.command.GetFilterStepCommand;
import com.echothree.control.user.filter.server.command.GetFilterStepsCommand;
import com.echothree.control.user.filter.server.command.GetFilterTypeCommand;
import com.echothree.control.user.filter.server.command.GetFilterTypesCommand;
import com.echothree.control.user.filter.server.command.GetFiltersCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface FilterSecurityUtils {

    static boolean getHasFilterKindAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterKindCommand.class);
    }

    static boolean getHasFilterTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterTypesCommand.class);
    }

    static boolean getHasFilterTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterTypeCommand.class);
    }

    static boolean getHasFilterAdjustmentSourceAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentSourceCommand.class);
    }

    static boolean getHasFilterAdjustmentTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentTypeCommand.class);
    }

    static boolean getHasFilterAdjustmentsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentsCommand.class);
    }

    static boolean getHasFilterAdjustmentAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentCommand.class);
    }

    static boolean getHasFilterAdjustmentAmountsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentAmountsCommand.class);
    }

    static boolean getHasFilterAdjustmentAmountAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentAmountCommand.class);
    }

    static boolean getHasFilterAdjustmentFixedAmountsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentFixedAmountsCommand.class);
    }

    static boolean getHasFilterAdjustmentFixedAmountAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentFixedAmountCommand.class);
    }

    static boolean getHasFilterAdjustmentPercentsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentPercentsCommand.class);
    }

    static boolean getHasFilterAdjustmentPercentAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentPercentCommand.class);
    }

    static boolean getHasFiltersAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFiltersCommand.class);
    }

    static boolean getHasFilterAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterCommand.class);
    }

    static boolean getHasFilterStepsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterStepsCommand.class);
    }

    static boolean getHasFilterStepAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterStepCommand.class);
    }

}
