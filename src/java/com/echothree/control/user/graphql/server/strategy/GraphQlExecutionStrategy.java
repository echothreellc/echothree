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

package com.echothree.control.user.graphql.server.strategy;

import graphql.ExecutionResult;
import graphql.annotations.strategies.EnhancedExecutionStrategy;
import graphql.execution.ExecutionContext;
import graphql.execution.ExecutionStrategyParameters;
import graphql.execution.NonNullableFieldValidator;
import graphql.schema.GraphQLObjectType;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class GraphQlExecutionStrategy
        extends EnhancedExecutionStrategy {

    private static final String CLIENT_MUTATION_ID = "clientMutationId";

    @Override
    protected CompletableFuture<ExecutionResult> resolveField(ExecutionContext executionContext,
            ExecutionStrategyParameters parameters) {
        CompletableFuture<ExecutionResult> result;
        var parentInfo = parameters.getExecutionStepInfo();
        var parentType = (GraphQLObjectType)parentInfo.getUnwrappedNonNullType();
        var fieldDefinition = getFieldDef(executionContext.getGraphQLSchema(), parentType, parameters.getField().getSingleField());

        if(fieldDefinition != null && CLIENT_MUTATION_ID.equals(fieldDefinition.getName())) {
            // Use the current mutation's coerced arguments, not the first AST field.
            // This handles variables, defaults, aliases, fragments, and omitted IDs.
            var input = parentInfo.getArgument("input");
            var clientMutationId = input instanceof Map<?, ?> map ? map.get(CLIENT_MUTATION_ID) : null;
            var fieldInfo = createExecutionStepInfo(executionContext, parameters, fieldDefinition, parentType);
            var valueParameters = parameters.transform(builder -> builder
                    .executionStepInfo(fieldInfo)
                    .nonNullFieldValidator(new NonNullableFieldValidator(executionContext, fieldInfo))
                    .source(clientMutationId));

            result = completeValue(executionContext, valueParameters).getFieldValue();
        } else {
            result = super.resolveField(executionContext, parameters);
        }

        return result;
    }

}
