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

package com.echothree.model.control.period.server.graphql;

import com.echothree.control.user.period.server.command.GetPeriodKindCommand;
import com.echothree.control.user.period.server.command.GetPeriodKindsCommand;
import com.echothree.control.user.period.server.command.GetPeriodTypeCommand;
import com.echothree.control.user.period.server.command.GetPeriodTypesCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface PeriodSecurityUtils {

    static boolean getHasPeriodKindAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPeriodKindCommand.class);
    }

    static boolean getHasPeriodKindsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPeriodKindsCommand.class);
    }

    static boolean getHasPeriodTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPeriodTypeCommand.class);
    }

    static boolean getHasPeriodTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetPeriodTypesCommand.class);
    }

}
