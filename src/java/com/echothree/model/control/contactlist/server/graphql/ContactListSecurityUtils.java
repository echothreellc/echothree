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

package com.echothree.model.control.contactlist.server.graphql;

import com.echothree.control.user.contactlist.server.command.GetContactListCommand;
import com.echothree.control.user.contactlist.server.command.GetContactListFrequenciesCommand;
import com.echothree.control.user.contactlist.server.command.GetContactListFrequencyCommand;
import com.echothree.control.user.contactlist.server.command.GetContactListGroupCommand;
import com.echothree.control.user.contactlist.server.command.GetContactListGroupsCommand;
import com.echothree.control.user.contactlist.server.command.GetContactListTypeCommand;
import com.echothree.control.user.contactlist.server.command.GetContactListTypesCommand;
import com.echothree.control.user.contactlist.server.command.GetContactListsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public interface ContactListSecurityUtils {

    static boolean getHasContactListTypesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContactListTypesCommand.class);
    }

    static boolean getHasContactListTypeAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContactListTypeCommand.class);
    }

    static boolean getHasContactListGroupsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContactListGroupsCommand.class);
    }

    static boolean getHasContactListGroupAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContactListGroupCommand.class);
    }

    static boolean getHasContactListFrequenciesAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContactListFrequenciesCommand.class);
    }

    static boolean getHasContactListFrequencyAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContactListFrequencyCommand.class);
    }

    static boolean getHasContactListsAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContactListsCommand.class);
    }

    static boolean getHasContactListAccess(final DataFetchingEnvironment env) {
        return BaseGraphQl.getGraphQlExecutionContext(env).hasAccess(GetContactListCommand.class);
    }

}
