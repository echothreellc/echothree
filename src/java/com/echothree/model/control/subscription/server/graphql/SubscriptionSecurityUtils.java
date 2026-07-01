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

package com.echothree.model.control.subscription.server.graphql;

import com.echothree.control.user.subscription.server.command.GetSubscriptionCommand;
import com.echothree.control.user.subscription.server.command.GetSubscriptionKindCommand;
import com.echothree.control.user.subscription.server.command.GetSubscriptionKindsCommand;
import com.echothree.control.user.subscription.server.command.GetSubscriptionTypeCommand;
import com.echothree.control.user.subscription.server.command.GetSubscriptionTypesCommand;
import com.echothree.control.user.subscription.server.command.GetSubscriptionsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface SubscriptionSecurityUtils {

    static boolean getHasSubscriptionKindsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSubscriptionKindsCommand.class);
    }

    static boolean getHasSubscriptionKindAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSubscriptionKindCommand.class);
    }

    static boolean getHasSubscriptionTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSubscriptionTypesCommand.class);
    }

    static boolean getHasSubscriptionTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSubscriptionTypeCommand.class);
    }

    static boolean getHasSubscriptionsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSubscriptionsCommand.class);
    }

    static boolean getHasSubscriptionAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetSubscriptionCommand.class);
    }

}
