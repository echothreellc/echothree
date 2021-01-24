// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentFixedAmountCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentSourceCommand;
import com.echothree.control.user.filter.server.command.GetFilterAdjustmentTypeCommand;
import com.echothree.control.user.filter.server.command.GetFilterCommand;
import com.echothree.control.user.filter.server.command.GetFilterKindCommand;
import com.echothree.control.user.filter.server.command.GetFilterTypeCommand;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import graphql.schema.DataFetchingEnvironment;

public final class FilterSecurityUtils {

    private static class FilterSecurityUtilsHolder {
        static FilterSecurityUtils instance = new FilterSecurityUtils();
    }
    
    public static FilterSecurityUtils getInstance() {
        return FilterSecurityUtilsHolder.instance;
    }

    public boolean getHasFilterKindAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetFilterKindCommand.class);
    }

    public boolean getHasFilterTypeAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetFilterTypeCommand.class);
    }

    public boolean getHasFilterAdjustmentSourceAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetFilterAdjustmentSourceCommand.class);
    }

    public boolean getHasFilterAdjustmentTypeAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetFilterAdjustmentTypeCommand.class);
    }

    public boolean getHasFilterAdjustmentAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetFilterAdjustmentCommand.class);
    }

    public boolean getHasFilterAdjustmentAmountAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetFilterAdjustmentAmountCommand.class);
    }

    public boolean getHasFilterAdjustmentFixedAmountAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetFilterAdjustmentFixedAmountCommand.class);
    }

//    public boolean getHasFilterAccess(final DataFetchingEnvironment env) {
//        return env.<GraphQlContext>getContext().hasAccess(GetFilterCommand.class);
//    }

}
