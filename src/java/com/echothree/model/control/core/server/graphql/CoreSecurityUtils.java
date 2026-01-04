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

package com.echothree.model.control.core.server.graphql;

import com.echothree.control.user.core.server.command.GetAppearanceCommand;
import com.echothree.control.user.core.server.command.GetAppearancesCommand;
import com.echothree.control.user.core.server.command.GetEntityAliasCommand;
import com.echothree.control.user.core.server.command.GetEntityAliasTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityAliasTypesCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeEntityAttributeGroupCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeEntityAttributeGroupsCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeGroupCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeGroupsCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributeTypesCommand;
import com.echothree.control.user.core.server.command.GetEntityAttributesCommand;
import com.echothree.control.user.core.server.command.GetEntityInstanceCommand;
import com.echothree.control.user.core.server.command.GetEntityInstancesCommand;
import com.echothree.control.user.core.server.command.GetEntityIntegerRangeCommand;
import com.echothree.control.user.core.server.command.GetEntityIntegerRangesCommand;
import com.echothree.control.user.core.server.command.GetEntityListItemCommand;
import com.echothree.control.user.core.server.command.GetEntityListItemsCommand;
import com.echothree.control.user.core.server.command.GetEntityLongRangeCommand;
import com.echothree.control.user.core.server.command.GetEntityLongRangesCommand;
import com.echothree.control.user.core.server.command.GetEntityTypeCommand;
import com.echothree.control.user.core.server.command.GetEntityTypesCommand;
import com.echothree.control.user.core.server.command.GetEventsCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeFileExtensionCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeFileExtensionsCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeUsageTypeCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeUsageTypesCommand;
import com.echothree.control.user.core.server.command.GetMimeTypeUsagesCommand;
import com.echothree.control.user.core.server.command.GetMimeTypesCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface CoreSecurityUtils {

    static boolean getHasEventsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEventsCommand.class);
    }

    static boolean getHasEntityInstanceAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityInstanceCommand.class);
    }

    static boolean getHasEntityInstancesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityInstancesCommand.class);
    }

    static boolean getHasAppearanceAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetAppearanceCommand.class);
    }

    static boolean getHasAppearancesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetAppearancesCommand.class);
    }

    static boolean getHasEntityAliasTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityAliasTypeCommand.class);
    }

    static boolean getHasEntityAliasTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityAliasTypesCommand.class);
    }

    static boolean getHasEntityAliasAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityAliasCommand.class);
    }

    static boolean getHasEntityAttributeTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityAttributeTypeCommand.class);
    }

    static boolean getHasEntityAttributeTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityAttributeTypesCommand.class);
    }

    static boolean getHasEntityAttributeGroupAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityAttributeGroupCommand.class);
    }

    static boolean getHasEntityAttributeGroupsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityAttributeGroupsCommand.class);
    }

    static boolean getHasEntityAttributeEntityAttributeGroupAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityAttributeEntityAttributeGroupCommand.class);
    }

    static boolean getHasEntityAttributeEntityAttributeGroupsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityAttributeEntityAttributeGroupsCommand.class);
    }

    static boolean getHasEntityAttributeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityAttributeCommand.class);
    }

    static boolean getHasEntityAttributesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityAttributesCommand.class);
    }

    static boolean getHasEntityTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityTypeCommand.class);
    }

    static boolean getHasEntityTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityTypesCommand.class);
    }

    static boolean getHasEntityListItemAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityListItemCommand.class);
    }

    static boolean getHasEntityListItemsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityListItemsCommand.class);
    }

    static boolean getHasEntityLongRangeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityLongRangeCommand.class);
    }

    static boolean getHasEntityLongRangesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityLongRangesCommand.class);
    }

    static boolean getHasEntityIntegerRangeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityIntegerRangeCommand.class);
    }

    static boolean getHasEntityIntegerRangesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetEntityIntegerRangesCommand.class);
    }

    static boolean getHasMimeTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetMimeTypeCommand.class);
    }

    static boolean getHasMimeTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetMimeTypesCommand.class);
    }

    static boolean getHasMimeTypeFileExtensionAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetMimeTypeFileExtensionCommand.class);
    }

    static boolean getHasMimeTypeFileExtensionsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetMimeTypeFileExtensionsCommand.class);
    }

    static boolean getHasMimeTypeUsageTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetMimeTypeUsageTypeCommand.class);
    }

    static boolean getHasMimeTypeUsageTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetMimeTypeUsageTypesCommand.class);
    }

//    static boolean getHasMimeTypeUsageAccess(final DataFetchingEnvironment env) {
//        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetMimeTypeUsageCommand.class);
//    }

    static boolean getHasMimeTypeUsagesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetMimeTypeUsagesCommand.class);
    }

}
