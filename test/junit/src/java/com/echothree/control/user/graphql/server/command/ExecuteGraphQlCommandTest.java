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

package com.echothree.control.user.graphql.server.command;

import com.echothree.model.control.graphql.server.graphql.MutationResultObject;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.command.CommandResult;
import com.echothree.util.common.command.ExecutionResult;
import com.echothree.util.common.message.Message;
import com.echothree.util.common.message.Messages;
import com.google.gson.JsonParser;
import graphql.ExecutionInput;
import graphql.GraphQL;
import graphql.annotations.AnnotationsSchemaCreator;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;
import graphql.schema.DataFetchingEnvironment;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExecuteGraphQlCommandTest {

    @GraphQLName("TestQuery")
    public static class Queries {
        @GraphQLField
        public static String value(DataFetchingEnvironment environment) {
            countCall(environment);
            return "ok";
        }
    }

    @GraphQLName("TestSubscription")
    public static class Subscriptions {
        @GraphQLField
        public static String value(DataFetchingEnvironment environment) {
            countCall(environment);
            throw new AssertionError("Rejected subscription must not execute its resolver");
        }
    }

    @GraphQLName("TestMutation")
    public static class Mutations {
        @GraphQLField
        public static MutationResultObject rejected(DataFetchingEnvironment environment) {
            countCall(environment);
            var message = new Message("BusinessRuleRejected");
            message.setMessage("Mutation refused");
            var errors = new Messages().add(Messages.EXECUTION_ERROR, message);
            var result = new MutationResultObject();
            result.setCommandResult(new CommandResult<BaseResult>(null, null, new ExecutionResult<>(null, errors, null)));
            return result;
        }
    }

    private static void countCall(DataFetchingEnvironment environment) {
        AtomicInteger calls = environment.getGraphQlContext().get("calls");
        calls.incrementAndGet();
    }

    private GraphQL graphQl(ExecuteGraphQlCommand command, String key) {
        var schema = AnnotationsSchemaCreator.newAnnotationsSchema()
                .query(Queries.class)
                .mutation(Mutations.class)
                .subscription(Subscriptions.class)
                .setAlwaysPrettify(true)
                .build();
        return GraphQL.newGraphQL(schema).instrumentation(command.getInstrumentation(key)).build();
    }

    private void assertRejected(String document) {
        var command = new ExecuteGraphQlCommand();
        var calls = new AtomicInteger();
        var result = graphQl(command, "retry-key").execute(ExecutionInput.newExecutionInput()
                .query(document).graphQLContext(Map.of("calls", calls)).build());

        assertEquals(0, calls.get());
        assertFalse(command.hasExecutionErrors());
        assertEquals(1, result.getErrors().size());
        var json = JsonParser.parseString(command.toJson(result)).getAsJsonObject();
        assertEquals("Idempotency-Key is supported only for mutation operations.",
                json.getAsJsonArray("errors").get(0).getAsJsonObject().get("message").getAsString());
        assertFalse(json.has("extensions"));
        assertFalse(json.has("data"));
    }

    @Test
    public void queryWithKeyAbortsBeforeResolverWithoutOuterCommandError() {
        assertRejected("{ value }");
    }

    @Test
    public void subscriptionWithKeyAbortsBeforeResolverWithoutOuterCommandError() {
        assertRejected("subscription { value }");
    }

    @Test
    public void queryWithoutKeyExecutesNormally() {
        var command = new ExecuteGraphQlCommand();
        var calls = new AtomicInteger();
        var result = graphQl(command, null).execute(ExecutionInput.newExecutionInput()
                .query("{ value }").graphQLContext(Map.of("calls", calls)).build());

        assertEquals(1, calls.get());
        assertTrue(result.getErrors().isEmpty(), () -> result.getErrors().toString());
        assertFalse(command.hasExecutionErrors());
        assertEquals("ok", JsonParser.parseString(command.toJson(result)).getAsJsonObject()
                .getAsJsonObject("data").get("value").getAsString());
    }

    @Test
    public void mutationErrorsUseExistingResultAndOnlyRequestedFields() {
        var command = new ExecuteGraphQlCommand();
        var calls = new AtomicInteger();
        var result = graphQl(command, "retry-key").execute(ExecutionInput.newExecutionInput()
                .query("mutation { rejected { commandResult { hasErrors executionErrors { key message } } } }")
                .graphQLContext(Map.of("calls", calls)).build());

        assertEquals(1, calls.get());
        assertTrue(result.getErrors().isEmpty(), () -> result.getErrors().toString());
        assertFalse(command.hasExecutionErrors());
        var json = JsonParser.parseString(command.toJson(result)).getAsJsonObject();
        assertFalse(json.has("errors"));
        assertFalse(json.has("extensions"));
        var commandResult = json.getAsJsonObject("data").getAsJsonObject("rejected").getAsJsonObject("commandResult");
        assertEquals(2, commandResult.size());
        assertTrue(commandResult.get("hasErrors").getAsBoolean());
        var error = commandResult.getAsJsonArray("executionErrors").get(0).getAsJsonObject();
        assertEquals(2, error.size());
        assertEquals("BusinessRuleRejected", error.get("key").getAsString());
        assertEquals("Mutation refused", error.get("message").getAsString());
    }

}
