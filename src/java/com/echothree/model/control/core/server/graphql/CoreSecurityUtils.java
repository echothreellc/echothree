// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.control.user.core.server.command.GetMimeTypeCommand;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import graphql.schema.DataFetchingEnvironment;

public final class CoreSecurityUtils {

    private static class CoreSecurityUtilsHolder {
        static CoreSecurityUtils instance = new CoreSecurityUtils();
    }
    
    public static CoreSecurityUtils getInstance() {
        return CoreSecurityUtilsHolder.instance;
    }
    
    public boolean getHasMimeTypeAccess(final DataFetchingEnvironment env) {
        return env.<GraphQlContext>getContext().hasAccess(GetMimeTypeCommand.class);
    }
    
}
