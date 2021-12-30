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

package com.echothree.model.control.graphql.server.util;

import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserSession;
import com.echothree.model.data.user.server.entity.UserVisit;
import graphql.schema.DataFetchingEnvironment;

public abstract class BaseGraphQl {

    public static final String GRAPHQL_EXECUTION_CONTEXT = "com.echothree.model.control.graphql.server.util.GraphQlExecutionContext";

    protected static GraphQlExecutionContext getGraphQlExecutionContext(final DataFetchingEnvironment env) {
        return env.getGraphQlContext().get(GRAPHQL_EXECUTION_CONTEXT);
    }

    protected static UserVisitPK getUserVisitPK(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).getUserVisitPK();
    }

    protected static UserVisit getUserVisit(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).getUserVisit();
    }

    protected static UserSession getUserSession(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).getUserSession();
    }

    protected static String getRemoteInet4Address(final DataFetchingEnvironment env) {
        return getGraphQlExecutionContext(env).getRemoteInet4Address();
    }

}
