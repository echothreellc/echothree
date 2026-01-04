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

package com.echothree.model.control.uom.server.graphql;

import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUseCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUseTypeCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUseTypesCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindUsesCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureKindsCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureTypeCommand;
import com.echothree.control.user.uom.server.command.GetUnitOfMeasureTypesCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface UomSecurityUtils {

    static boolean getHasUnitOfMeasureKindUseTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetUnitOfMeasureKindUseTypeCommand.class);
    }

    static boolean getHasUnitOfMeasureKindUseTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetUnitOfMeasureKindUseTypesCommand.class);
    }

    static boolean getHasUnitOfMeasureKindAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetUnitOfMeasureKindCommand.class);
    }

    static boolean getHasUnitOfMeasureKindsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetUnitOfMeasureKindsCommand.class);
    }

    static boolean getHasUnitOfMeasureKindUseAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetUnitOfMeasureKindUseCommand.class);
    }

    static boolean getHasUnitOfMeasureKindUsesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetUnitOfMeasureKindUsesCommand.class);
    }

    static boolean getHasUnitOfMeasureTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetUnitOfMeasureTypeCommand.class);
    }

    static boolean getHasUnitOfMeasureTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetUnitOfMeasureTypesCommand.class);
    }

}
