// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
// Copyright 2016 Yurii Rashkovskii and Contributors
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

package com.echothree.service.graphql.internal.invocation;

import com.echothree.control.user.graphql.common.GraphQlUtil;
import com.echothree.control.user.graphql.common.form.ExecuteGraphQlForm;
import com.echothree.service.graphql.internal.GraphQlRequest;
import javax.naming.NamingException;

public class GraphQlInvocationInput {

    private final boolean readOnly;
    private final GraphQlRequest graphQLRequest;

    public GraphQlInvocationInput(boolean readOnly, GraphQlRequest graphQLRequest) {
        this.readOnly = readOnly;
        this.graphQLRequest = graphQLRequest;
    }

    public ExecuteGraphQlForm getExecuteGraphQlForm()
            throws NamingException {
        var commandForm = GraphQlUtil.getHome().getExecuteGraphQlForm();

        commandForm.setReadOnly(Boolean.toString(readOnly));
        commandForm.setQuery(graphQLRequest.getQuery());
        commandForm.setVariables(graphQLRequest.getVariables());
        commandForm.setOperationName(graphQLRequest.getOperationName());
        commandForm.setJson(graphQLRequest.getJson());

        return commandForm;
    }
}
