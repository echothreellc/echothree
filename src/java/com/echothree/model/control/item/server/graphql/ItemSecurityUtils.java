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

package com.echothree.model.control.item.server.graphql;

import com.echothree.control.user.item.server.command.GetItemCategoryCommand;
import com.echothree.control.user.item.server.command.GetItemCommand;
import com.echothree.control.user.item.server.command.GetItemsCommand;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import graphql.schema.DataFetchingEnvironment;

public final class ItemSecurityUtils
        extends BaseGraphQl {

    private static class ItemSecurityUtilsHolder {
        static ItemSecurityUtils instance = new ItemSecurityUtils();
    }
    
    public static ItemSecurityUtils getInstance() {
        return ItemSecurityUtilsHolder.instance;
    }

    public boolean getHasItemAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemCommand.class);
    }

    public boolean getHasItemsAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemsCommand.class);
    }

    public boolean getHasItemCategoryAccess(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).hasAccess(GetItemCategoryCommand.class);
    }
    
}
