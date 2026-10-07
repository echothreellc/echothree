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
import com.echothree.control.user.graphql.common.result.ExecuteGraphQlResult;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.service.graphql.internal.invocation.GraphQlInvocationInput;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.message.Messages;
import com.echothree.util.common.string.GraphQlUtils;
import graphql.ErrorType;
import graphql.ExecutionResultImpl;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import java.util.ArrayList;
import java.util.List;
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

        return toJson(commandResult);
    }

    protected String toJson(CommandResult<ExecuteGraphQlResult> commandResult) {
        var result = commandResult.getResult();
        var executionResult = result == null ? null : result.getExecutionResult();

        // A completed GraphQL response (including a replay) always takes precedence.
        if(executionResult == null) {
            List<GraphQLError> errors = new ArrayList<>();

            if(commandResult.hasSecurityMessages()) {
                addErrors(errors, commandResult.getSecurityResult().getSecurityMessages());
            }

            if(commandResult.hasValidationErrors()) {
                addErrors(errors, commandResult.getValidationResult().getValidationMessages());
            }

            if(commandResult.hasExecutionErrors()) {
                addErrors(errors, commandResult.getExecutionResult().getExecutionErrors());
            }

            if(errors.isEmpty()) {
                throw new IllegalStateException(result == null ?
                        "GraphQL command completed without a result" :
                        "GraphQL command completed without an execution result");
            }

            executionResult = GraphQlUtils.getInstance().toJson(ExecutionResultImpl.newExecutionResult()
                    .errors(errors)
                    .build()
                    .toSpecification());
        }

        return executionResult;
    }

    private void addErrors(List<GraphQLError> errors, Messages messages) {
        messages.get().forEachRemaining(message -> {
            // Sanitize to remove specific library name.
            var jsonParseError = ExecutionErrors.JsonParseError.name().equals(message.getKey());
            var text = jsonParseError ?
                    "Invalid JSON in the request. Check for trailing commas or missing property names." :
                    message.getMessage();

            errors.add(GraphqlErrorBuilder.newError()
                    .message("%s", text)
                    .errorType(jsonParseError ? ErrorType.InvalidSyntax : ErrorType.DataFetchingException)
                    .build());
        });
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
