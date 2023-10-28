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

public final class FilterSecurityUtils
        implements BaseGraphQl {

    private static class FilterSecurityUtilsHolder {
        static FilterSecurityUtils instance = new FilterSecurityUtils();
    }
    
    public static FilterSecurityUtils getInstance() {
        return FilterSecurityUtilsHolder.instance;
    }

    public boolean getHasFilterKindAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterKindCommand.class);
    }

    public boolean getHasFilterTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterTypesCommand.class);
    }

    public boolean getHasFilterTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterTypeCommand.class);
    }

    public boolean getHasFilterAdjustmentSourceAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentSourceCommand.class);
    }

    public boolean getHasFilterAdjustmentTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentTypeCommand.class);
    }

    public boolean getHasFilterAdjustmentsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentsCommand.class);
    }

    public boolean getHasFilterAdjustmentAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentCommand.class);
    }

    public boolean getHasFilterAdjustmentAmountsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentAmountsCommand.class);
    }

    public boolean getHasFilterAdjustmentAmountAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentAmountCommand.class);
    }

    public boolean getHasFilterAdjustmentFixedAmountsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentFixedAmountsCommand.class);
    }

    public boolean getHasFilterAdjustmentFixedAmountAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentFixedAmountCommand.class);
    }

    public boolean getHasFilterAdjustmentPercentsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentPercentsCommand.class);
    }

    public boolean getHasFilterAdjustmentPercentAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterAdjustmentPercentCommand.class);
    }

    public boolean getHasFiltersAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFiltersCommand.class);
    }

    public boolean getHasFilterAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterCommand.class);
    }

    public boolean getHasFilterStepsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterStepsCommand.class);
    }

    public boolean getHasFilterStepAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetFilterStepCommand.class);
    }

}
