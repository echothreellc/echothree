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

package com.echothree.model.control.selector.server.graphql;

import com.echothree.control.user.selector.server.command.GetSelectorCommand;
import com.echothree.control.user.selector.server.command.GetSelectorKindCommand;
import com.echothree.control.user.selector.server.command.GetSelectorKindsCommand;
import com.echothree.control.user.selector.server.command.GetSelectorTypeCommand;
import com.echothree.control.user.selector.server.command.GetSelectorTypesCommand;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import graphql.schema.DataFetchingEnvironment;

public final class SelectorSecurityUtils {

    private static class SelectorSecurityUtilsHolder {
        static SelectorSecurityUtils instance = new SelectorSecurityUtils();
    }
    
    public static SelectorSecurityUtils getInstance() {
        return SelectorSecurityUtilsHolder.instance;
    }

    public boolean getHasSelectorKindAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetSelectorKindCommand.class);
    }

    public boolean getHasSelectorTypeAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetSelectorTypeCommand.class);
    }

    public boolean getHasSelectorTypesAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetSelectorTypesCommand.class);
    }

    public boolean getHasSelectorAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetSelectorCommand.class);
    }

}
