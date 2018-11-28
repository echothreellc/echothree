// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.service.graphql.internal.invoker;

import com.echothree.control.user.graphql.common.GraphQlUtil;
import com.echothree.control.user.graphql.common.form.ExecuteGraphQlForm;
import com.echothree.control.user.graphql.common.result.ExecuteGraphQlResult;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.service.graphql.internal.invocation.GraphQlBatchedInvocationInput;
import com.echothree.service.graphql.internal.invocation.GraphQlSingleInvocationInput;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import javax.naming.NamingException;
import java.util.ArrayList;
import java.util.List;

public class GraphQlQueryInvoker {

    protected GraphQlQueryInvoker() {
    }

    private String execute(UserVisitPK userVisitPK, ExecuteGraphQlForm executeGraphQlForm)
            throws NamingException {
        String graphQlExecutionResult = null;
        CommandResult commandResult = GraphQlUtil.getHome().executeGraphQl(userVisitPK,
                executeGraphQlForm);

        if(!commandResult.hasErrors()) {
            ExecutionResult executionResult = commandResult.getExecutionResult();
            ExecuteGraphQlResult result = (ExecuteGraphQlResult)executionResult.getResult();

            graphQlExecutionResult = result.getExecutionResult();
        }

        return graphQlExecutionResult;
    }

    public String query(UserVisitPK userVisitPK, GraphQlSingleInvocationInput singleInvocationInput)
            throws NamingException {
        ExecuteGraphQlForm executeGraphQlForm = singleInvocationInput.getExecuteGraphQlForm();

        return execute(userVisitPK, executeGraphQlForm);
    }

    public List<String> query(UserVisitPK userVisitPK, GraphQlBatchedInvocationInput batchedInvocationInput)
            throws NamingException {
        List<ExecuteGraphQlForm> executeGraphQlForms = batchedInvocationInput.getExecuteGraphQlForms();
        List<String> graphQlExecutionResults = new ArrayList<>(executeGraphQlForms.size());

        for(ExecuteGraphQlForm executeGraphQlForm : executeGraphQlForms) {
            graphQlExecutionResults.add(execute(userVisitPK, executeGraphQlForm));
        }

        return graphQlExecutionResults;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        public GraphQlQueryInvoker build() {
            return new GraphQlQueryInvoker();
        }
    }
}
