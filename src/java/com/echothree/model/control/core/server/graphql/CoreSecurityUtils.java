// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.model.control.core.server.graphql;

import com.echothree.control.user.core.server.command.GetEntityAttributeCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypesCommand;
import com.echothree.control.user.core.server.command.GetEntityInstanceCommand;
import com.echothree.control.user.core.server.command.GetEntityListItemCommand;
import com.echothree.control.user.core.server.command.GetEntityListItemsCommand;
import com.echothree.control.user.core.server.command.GetEntityTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityTypesCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeUsageTypeCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeUsageTypesCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeUsagesCommand;
import com.echothree.control.user.core.server.command.GetMimeTypesCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public final class CoreSecurityUtils
        extends BaseGraphQl {

    private static class CoreSecurityUtilsHolder {
        static CoreSecurityUtils instance = new CoreSecurityUtils();
    }
    
    public static CoreSecurityUtils getInstance() {
        return CoreSecurityUtilsHolder.instance;
    }

    public boolean getHasEntityInstanceAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetEntityInstanceCommand.class);
    }

    public boolean getHasEntityAttributeTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetEntityAttributeTypeCommand.class);
    }

    public boolean getHasEntityAttributeTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetEntityAttributeTypesCommand.class);
    }

    public boolean getHasEntityAttributeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetEntityAttributeCommand.class);
    }

//    public boolean getHasEntityAttributesAccess(final DataFetchingEnvironment env) {
//        return getGraphQlExecutionContext(env).hasAccess(GetEntityAttributesCommand.class);
//    }

    public boolean getHasEntityTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetEntityTypeCommand.class);
    }

    public boolean getHasEntityTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetEntityTypesCommand.class);
    }

    public boolean getHasEntityListItemAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetEntityListItemCommand.class);
    }

    public boolean getHasEntityListItemsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetEntityListItemsCommand.class);
    }

    public boolean getHasMimeTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetMimeTypeCommand.class);
    }

    public boolean getHasMimeTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetMimeTypesCommand.class);
    }

    public boolean getHasMimeTypeUsageTypeAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetMimeTypeUsageTypeCommand.class);
    }

    public boolean getHasMimeTypeUsageTypesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetMimeTypeUsageTypesCommand.class);
    }

//    public boolean getHasMimeTypeUsageAccess(final DataFetchingEnvironment env) {
//        return getGraphQlExecutionContext(env).hasAccess(GetMimeTypeUsageCommand.class);
//    }

    public boolean getHasMimeTypeUsagesAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetMimeTypeUsagesCommand.class);
    }

}
