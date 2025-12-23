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

package com.echothree.model.control.warehouse.server.graphql;

import com.echothree.control.user.warehouse.server.command.GetWarehouseCommand;
import com.echothree.control.user.warehouse.server.command.GetWarehouseTypeCommand;
import com.echothree.control.user.warehouse.server.command.GetWarehouseTypesCommand;
import com.echothree.control.user.warehouse.server.command.GetWarehousesCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface WarehouseSecurityUtils {

    static boolean getHasWarehouseAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWarehouseCommand.class);
    }

    static boolean getHasWarehousesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWarehousesCommand.class);
    }

    static boolean getHasWarehouseTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWarehouseTypeCommand.class);
    }

    static boolean getHasWarehouseTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetWarehouseTypesCommand.class);
    }

}
