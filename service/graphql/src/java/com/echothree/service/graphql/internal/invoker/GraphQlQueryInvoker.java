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

package com.echothree.service.graphql.internal.invoker;

import com.echothree.control.user.graphql.common.GraphQlUtil;
import com.echothree.control.user.graphql.common.form.ExecuteGraphQlForm;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.service.graphql.internal.invocation.GraphQlInvocationInput;
import java.util.concurrent.ExecutionException;
import javax.naming.NamingException;

public class GraphQlQueryInvoker {

    protected GraphQlQueryInvoker() {
    }

    private String execute(UserVisitPK userVisitPK, ExecuteGraphQlForm executeGraphQlForm, String idempotencyKey,
            String remoteInet4Address)
            throws NamingException, ExecutionException, InterruptedException {
        executeGraphQlForm.setIdempotencyKey(idempotencyKey);
        executeGraphQlForm.setRemoteInet4Address(remoteInet4Address);

        var futureCommandResult = GraphQlUtil.getHome().executeGraphQl(userVisitPK, executeGraphQlForm);
        var commandResult = futureCommandResult.get();

        var result = commandResult.getResult();
        if(result == null) {
            throw new IllegalStateException("GraphQL command completed without a result");
        }

        var executionResult = result.getExecutionResult();
        if(executionResult == null) {
            throw new IllegalStateException("GraphQL command completed without an execution result");
        }

        return executionResult;
    }


    public String query(UserVisitPK userVisitPK, GraphQlInvocationInput singleInvocationInput, String idempotencyKey,
            String remoteInet4Address)
            throws NamingException, ExecutionException, InterruptedException {
        var executeGraphQlForm = singleInvocationInput.getExecuteGraphQlForm();

        return execute(userVisitPK, executeGraphQlForm, idempotencyKey, remoteInet4Address);
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
