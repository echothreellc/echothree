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

import com.echothree.control.user.graphql.server.strategy.GraphQlExecutionStrategy;
import com.echothree.control.user.graphql.common.form.ExecuteGraphQlForm;
import com.echothree.control.user.graphql.common.form.GraphQlFormFactory;
import com.echothree.control.user.graphql.common.result.ExecuteGraphQlResult;
import com.echothree.model.control.graphql.server.graphql.MutationResultObject;
import com.echothree.model.control.graphql.server.control.IdempotentResponseControl;
import com.echothree.model.data.graphql.common.pk.IdempotentResponsePK;
import com.echothree.model.data.graphql.server.entity.IdempotentResponse;
import com.echothree.model.data.graphql.server.value.IdempotentResponseValue;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.model.data.user.server.value.UserVisitValue;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.persistence.valuecache.NullValueCacheImpl;
import com.echothree.util.server.persistence.valuecache.ValueCacheProvider;
import com.echothree.util.server.persistence.valuecache.ValueCacheProviderImpl;
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
import graphql.annotations.annotationTypes.GraphQLRelayMutation;
import graphql.schema.DataFetchingEnvironment;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExecuteGraphQlCommandTest {

    private static final String MUTATION = "mutation { rejected { commandResult { hasErrors } } }";

    private static class StoredKey extends IdempotentResponse {
        int stores;
        RuntimeException storeFailure;

        StoredKey(IdempotentResponseValue value) {
            super(value, EntityPermission.READ_WRITE);
        }

        @Override
        public void store() {
            if(storeFailure != null) {
                throw storeFailure;
            }
            stores++;
        }
    }

    private static class MemoryControl extends IdempotentResponseControl {
        private record Lookup(UserVisitPK visit, String hash) { }
        final Map<Lookup, StoredKey> entries = new HashMap<>();
        StoredKey entry;
        int creates;
        int removes;
        RuntimeException createFailure;

        @Override
        public IdempotentResponse getIdempotentResponseForUpdate(UserVisit visit, String hash) {
            return entries.get(new Lookup(visit.getPrimaryKey(), hash));
        }

        @Override
        public IdempotentResponse createIdempotentResponse(UserVisit visit, String keyHash, String queryHash, String variablesHash,
                String operationNameHash, Long startTime, Long completedTime, String response, Long retainUntilTime) {
            if(createFailure != null) {
                throw createFailure;
            }
            creates++;
            entry = new StoredKey(new IdempotentResponseValue(new IdempotentResponsePK((long)creates), visit.getPrimaryKey(),
                    keyHash, queryHash, variablesHash, operationNameHash, startTime, completedTime, response, retainUntilTime));
            entries.put(new Lookup(visit.getPrimaryKey(), keyHash), entry);
            // Generated factories return read-only entities from create(). A caller
            // must fetch for update before setting completion fields.
            return new IdempotentResponse(entry.getIdempotentResponseValue(), EntityPermission.READ_ONLY);
        }

        @Override
        public void removeIdempotentResponse(IdempotentResponse entry) {
            removes++;
            entries.values().remove(entry);
            this.entry = null;
        }

        // These command tests use in-memory persistence without the encryption key service.
        @Override
        public String encodeIdempotentResponse(String response) {
            return response;
        }

        @Override
        public String decodeIdempotentResponse(IdempotentResponse entry) {
            return entry.getResponse();
        }
    }

    private static class TestCommand extends ExecuteGraphQlCommand {
        final MemoryControl control;
        int locks;
        final UserVisit visit;

        TestCommand() {
            this(new MemoryControl(), 1L);
        }

        TestCommand(MemoryControl control, long visitId) {
            this.control = control;
            visit = new UserVisit(new UserVisitValue(new UserVisitPK(visitId), null, null, null, null,
                    null, null, 0L, null, null, null, 0L, Long.MAX_VALUE), EntityPermission.READ_WRITE);
            idempotentResponseControl = control;
            session = testSession();
        }

        ExecuteGraphQlResult executeForm(ExecuteGraphQlForm input) {
            form = input;
            return (ExecuteGraphQlResult)execute();
        }

        @Override
        public UserVisit getUserVisitForUpdate() {
            locks++;
            return visit;
        }
    }

    private static synchronized Session testSession() {
        Session result;
        // As in the mutex tests, avoid CDI only during Session construction.
        try {
            var holder = Class.forName(ValueCacheProviderImpl.class.getName() + "$ValueCacheProviderHolder");
            var field = holder.getDeclaredField("instance");
            field.setAccessible(true);
            var original = field.get(null);

            try {
                field.set(null, (ValueCacheProvider)NullValueCacheImpl::new);
                result = new Session();
            } finally {
                field.set(null, original);
            }
        } catch(ReflectiveOperationException e) {
            throw new LinkageError("Unable to construct the GraphQL test session", e);
        }

        return result;
    }

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
        @GraphQLRelayMutation
        public static MutationResultObject idle(DataFetchingEnvironment environment) {
            return rejected(environment);
        }

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
        return graphQl(command, key, new ExecuteGraphQlCommand.IdempotencyState());
    }

    private GraphQL graphQl(ExecuteGraphQlCommand command, String key, ExecuteGraphQlCommand.IdempotencyState state) {
        var schema = AnnotationsSchemaCreator.newAnnotationsSchema()
                .query(Queries.class)
                .mutation(Mutations.class)
                .subscription(Subscriptions.class)
                .setAlwaysPrettify(true)
                .build();
        return GraphQL.newGraphQL(schema)
                .queryExecutionStrategy(new GraphQlExecutionStrategy())
                .instrumentation(command.getInstrumentation(key, state)).build();
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
        var command = new TestCommand();
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

    private String executeMutation(TestCommand command, String document, String operationName, Map<String, Object> variables,
            AtomicInteger calls) {
        var state = new ExecuteGraphQlCommand.IdempotencyState();
        var result = graphQl(command, "retry-key", state).execute(ExecutionInput.newExecutionInput()
                .query(document).operationName(operationName).variables(variables)
                .graphQLContext(Map.of("calls", calls)).build());
        return command.finishExecution(result, state);
    }

    @Test
    public void completedMutationReplaysVerbatimWithoutExecutingOrExtendingRetention() {
        var command = new TestCommand();
        var calls = new AtomicInteger();
        var first = executeMutation(command, MUTATION, null, Map.of(), calls);
        var entry = command.control.entry;
        var retainedUntil = entry.getRetainUntilTime();

        assertEquals(first, entry.getResponse());
        assertEquals(24L * 60 * 60 * 1000, retainedUntil - entry.getCompletedTime());
        // Include formatting that a new serialization would change.
        entry.setResponse(" \n" + first);
        var replay = executeMutation(command, MUTATION, null, Map.of(), calls);

        assertEquals(" \n" + first, replay);
        assertEquals(1, calls.get());
        assertEquals(1, command.control.creates);
        assertEquals(1, entry.stores);
        assertEquals(retainedUntil, entry.getRetainUntilTime());
        assertEquals(2, command.locks);
        assertFalse(command.hasExecutionErrors());
    }

    @Test
    public void differentQueryVariablesOrOperationNameRejectBeforeResolvers() {
        for(var mismatch : List.of("query", "variables", "operationName")) {
            var command = new TestCommand();
            var calls = new AtomicInteger();
            var document = "mutation First { rejected { commandResult { hasErrors } } } "
                    + "mutation Second { rejected { commandResult { hasErrors } } }";
            executeMutation(command, document, "First", Map.of("input", 1), calls);
            var response = executeMutation(command, mismatch.equals("query") ? document + " " : document,
                    mismatch.equals("operationName") ? "Second" : "First",
                    Map.of("input", mismatch.equals("variables") ? 2 : 1), calls);

            assertTrue(response.contains("already used with a different request"), response);
            assertEquals(1, calls.get(), mismatch);
            assertEquals(1, command.control.creates);
            assertEquals(1, command.control.entry.stores);
        }
    }

    @Test
    public void expiredKeyReplaysUntilCleanupRemovesIt() {
        var command = new TestCommand();
        var calls = new AtomicInteger();
        var first = executeMutation(command, MUTATION, null, Map.of(), calls);
        command.control.entry.setRetainUntilTime(0L);
        var replay = executeMutation(command, MUTATION, null, Map.of(), calls);

        assertEquals(first, replay);
        assertEquals(1, calls.get());
        assertEquals(1, command.control.creates);
        assertEquals(0, command.control.removes);

        command.control.removeIdempotentResponse(command.control.entry);
        executeMutation(command, MUTATION + " ", null, Map.of(), calls);

        assertEquals(2, calls.get());
        assertEquals(2, command.control.creates);
        assertEquals(1, command.control.removes);
        assertNotNull(command.control.entry.getResponse());
    }

    @Test
    public void incompleteKeyDoesNotExecuteAgain() {
        var command = new TestCommand();
        var calls = new AtomicInteger();
        executeMutation(command, MUTATION, null, Map.of(), calls);
        command.control.entry.setCompletedTime(null);
        command.control.entry.setResponse(null);
        var response = executeMutation(command, MUTATION, null, Map.of(), calls);

        assertTrue(response.contains("has not completed"), response);
        assertEquals(1, calls.get());
        assertEquals(1, command.control.creates);
    }

    @Test
    public void canonicalVariablesIgnoreObjectOrderButPreserveArrayOrderAndNulls() {
        var command = new ExecuteGraphQlCommand();
        var utils = com.echothree.util.common.string.GraphQlUtils.getInstance();
        var first = utils.toMap("{\"a\":{\"x\":1,\"y\":null},\"b\":[1,2]}");
        var reordered = utils.toMap("{\"b\":[1,2],\"a\":{\"y\":null,\"x\":1}}");
        assertEquals(command.hashVariables(first), command.hashVariables(reordered));
        assertNotEquals(command.hashVariables(first), command.hashVariables(utils.toMap("{\"a\":{\"x\":1},\"b\":[1,2]}")));
        assertNotEquals(command.hashVariables(first), command.hashVariables(utils.toMap("{\"a\":{\"x\":1,\"y\":null},\"b\":[2,1]}")));
        assertNull(command.hashVariables(null));
        assertNull(command.hashVariables(Map.of()));
    }

    @Test
    public void invalidDocumentsAndVariablesDoNotClaimOrExecute() {
        var documents = List.of("mutation {", "mutation { unknownField }",
                "mutation($id: String!) { idle(input: {clientMutationId: $id}) { clientMutationId } }");
        for(var document : documents) {
            var command = new TestCommand();
            var calls = new AtomicInteger();
            var state = new ExecuteGraphQlCommand.IdempotencyState();
            var result = graphQl(command, "retry-key", state).execute(ExecutionInput.newExecutionInput()
                    .query(document).variables(Map.of()).root(new Object())
                    .graphQLContext(Map.of("calls", calls)).build());

            assertFalse(result.getErrors().isEmpty(), document);
            assertEquals(0, calls.get());
            assertEquals(0, command.locks);
            assertEquals(0, command.control.creates);
            assertNull(state.idempotentResponse);
        }
    }

    @Test
    public void selectedQueryInMixedDocumentRejectsKeyWithoutClaiming() {
        var command = new TestCommand();
        var calls = new AtomicInteger();
        var result = graphQl(command, "retry-key").execute(ExecutionInput.newExecutionInput()
                .query("mutation Write { rejected { commandResult { hasErrors } } } query Read { value }")
                .operationName("Read").graphQLContext(Map.of("calls", calls)).build());

        assertEquals(1, result.getErrors().size());
        assertEquals("Idempotency-Key is supported only for mutation operations.", result.getErrors().get(0).getMessage());
        assertEquals(0, calls.get());
        assertEquals(0, command.control.creates);
    }

    @Test
    public void eitherMissingCompletionFieldPreventsReplayAndReexecution() {
        for(var missingResponse : List.of(true, false)) {
            var command = new TestCommand();
            var calls = new AtomicInteger();
            executeMutation(command, MUTATION, null, Map.of(), calls);
            if(missingResponse) {
                command.control.entry.setResponse(null);
            } else {
                command.control.entry.setCompletedTime(null);
            }
            var response = executeMutation(command, MUTATION, null, Map.of(), calls);

            assertTrue(response.contains("has not completed"));
            assertEquals(1, calls.get());
            assertEquals(1, command.control.entry.stores);
        }
    }

    @Test
    public void replayDoesNotAttemptSerialization() {
        var command = new TestCommand() {
            @Override
            public String toJson(graphql.ExecutionResult result) {
                throw new AssertionError("Replay must bypass serialization");
            }
        };
        var state = new ExecuteGraphQlCommand.IdempotencyState();
        state.replayResponse = " {\"data\":{\"value\":\"original\"}} ";
        assertEquals(state.replayResponse, command.finishExecution(null, state));
    }

    @Test
    public void serializationFailuresPropagateWithoutSavingCompletion() {
        for(var failure : List.of(new com.google.gson.JsonParseException("serialization failed"),
                new graphql.GraphQLException("serialization failed"))) {
            var command = new TestCommand() {
                @Override
                public String toJson(graphql.ExecutionResult result) {
                    throw failure;
                }
            };
            var state = new ExecuteGraphQlCommand.IdempotencyState();
            command.beginIdempotency("retry-key", ExecutionInput.newExecutionInput().query(MUTATION).build(), state);

            assertSame(failure, assertThrows(RuntimeException.class, () -> command.finishExecution(null, state)));
            assertEquals(0, command.control.entry.stores);
            assertNull(command.control.entry.getCompletedTime());
            assertNull(command.control.entry.getResponse());
            assertEquals(0, command.control.removes);
        }
    }

    @Test
    public void completionStorageFailurePropagatesInsteadOfReturningResponse() {
        var command = new TestCommand();
        var state = new ExecuteGraphQlCommand.IdempotencyState();
        command.beginIdempotency("retry-key", ExecutionInput.newExecutionInput().query(MUTATION).build(), state);
        var failure = new IllegalStateException("Database write failed");
        command.control.entry.storeFailure = failure;
        var result = graphql.ExecutionResultImpl.newExecutionResult().data(Map.of("value", "ok")).build();

        assertSame(failure, assertThrows(IllegalStateException.class, () -> command.finishExecution(result, state)));
        assertEquals(0, command.control.entry.stores);
        assertEquals(0, command.control.removes);
    }

    @Test
    public void failedClaimNeverRunsResolvers() {
        var command = new TestCommand();
        command.control.createFailure = new IllegalStateException("Database claim failed");
        var calls = new AtomicInteger();

        assertThrows(RuntimeException.class, () -> executeMutation(command, MUTATION, null, Map.of(), calls));
        assertEquals(0, calls.get());
        assertEquals(0, command.control.creates);
    }

    @Test
    public void userVisitAndKeyScopeArePassedToPersistence() {
        var control = new MemoryControl();
        var input = ExecutionInput.newExecutionInput().query(MUTATION).build();
        for(var visitId : List.of(1L, 2L)) {
            for(var key : List.of("first-key", "second-key")) {
                var command = new TestCommand(control, visitId);
                var state = new ExecuteGraphQlCommand.IdempotencyState();
                command.beginIdempotency(key, input, state);
                command.finishExecution(graphql.ExecutionResultImpl.newExecutionResult()
                        .data(Map.of("owner", visitId + ":" + key)).build(), state);
            }
        }
        assertEquals(4, control.creates);
        for(var visitId : List.of(1L, 2L)) {
            for(var key : List.of("first-key", "second-key")) {
                var command = new TestCommand(control, visitId);
                var state = new ExecuteGraphQlCommand.IdempotencyState();
                assertThrows(graphql.execution.AbortExecutionException.class, () -> command.beginIdempotency(key, input, state));
                var response = JsonParser.parseString(command.finishExecution(null, state)).getAsJsonObject();
                assertEquals(visitId + ":" + key, response.getAsJsonObject("data").get("owner").getAsString());
            }
        }
        assertEquals(4, control.creates);
    }

    @Test
    public void mutationWithoutKeyDoesNotUsePersistence() {
        var command = new TestCommand();
        var state = new ExecuteGraphQlCommand.IdempotencyState();
        var calls = new AtomicInteger();
        var result = graphQl(command, null, state).execute(ExecutionInput.newExecutionInput()
                .query(MUTATION).graphQLContext(Map.of("calls", calls)).build());
        command.finishExecution(result, state);
        assertEquals(1, calls.get());
        assertEquals(0, command.locks);
        assertEquals(0, command.control.creates);
    }

    private com.google.gson.JsonObject relayResponse(String document, Map<String, Object> variables) {
        var command = new ExecuteGraphQlCommand();
        var result = graphQl(command, null).execute(ExecutionInput.newExecutionInput()
                .query(document).variables(variables).root(new Object())
                .graphQLContext(Map.of("calls", new AtomicInteger())).build());
        assertTrue(result.getErrors().isEmpty(), () -> result.getErrors().toString());
        return JsonParser.parseString(command.toJson(result)).getAsJsonObject().getAsJsonObject("data");
    }

    @Test
    public void malformedJsonReportsCommandErrorWithoutClaimingKey() {
        for(var bodyInput : List.of(true, false)) {
            var command = new TestCommand();
            var form = GraphQlFormFactory.getExecuteGraphQlForm();
            form.setReadOnly("false");
            form.setIdempotencyKey("retry-key");
            if(bodyInput) {
                form.setJson("""
                        {"query":"mutation { idle(input: {}) { clientMutationId } }",
                         "variables":{"clientMutationId":"test-id",}}
                        """);
            } else {
                form.setQuery(MUTATION);
                form.setVariables("{\"clientMutationId\":\"test-id\",}");
            }

            var response = command.executeForm(form).getExecutionResult();
            assertNull(response);
            assertTrue(command.hasExecutionErrors());
            assertEquals(0, command.locks);
            assertEquals(0, command.control.creates);
        }
    }

    @Test
    public void incorrectlyTypedBodyFieldsReportCommandErrorBeforeExecution() {
        for(var field : List.of("query", "operationName", "variables")) {
            var invalidValues = field.equals("variables") ? List.of("\"invalid\"", "[]", "42", "true")
                    : List.of("{}", "[]", "42", "true");

            for(var invalidValue : invalidValues) {
                for(var overrideWithForm : List.of(false, true)) {
                    var command = new TestCommand();
                    var form = GraphQlFormFactory.getExecuteGraphQlForm();
                    form.setReadOnly("false");
                    form.setIdempotencyKey("retry-key");
                    var body = new HashMap<String, Object>();
                    body.put("query", MUTATION);
                    var json = JsonParser.parseString(com.echothree.util.common.string.GraphQlUtils.getInstance().toJson(body))
                            .getAsJsonObject();
                    json.add(field, JsonParser.parseString(invalidValue));
                    form.setJson(json.toString());
                    if(overrideWithForm) {
                        form.setQuery(MUTATION);
                        form.setOperationName("SelectedMutation");
                        form.setVariables("{}");
                    }

                    assertNull(command.executeForm(form).getExecutionResult(), field + ": " + invalidValue);
                    assertTrue(command.hasExecutionErrors());
                    assertEquals(0, command.locks);
                    assertEquals(0, command.control.creates);
                }
            }
        }
    }

    @Test
    public void toMapPreservesObjectsAndNullWhileRejectingOtherJsonTypes() {
        var utils = com.echothree.util.common.string.GraphQlUtils.getInstance();
        var map = utils.toMap("""
                {"input":{"enabled":true},"values":[1,"two",null],"optional":null}
                """);

        assertEquals(Map.of("enabled", true), map.get("input"));
        assertEquals(java.util.Arrays.asList(1.0, "two", null), map.get("values"));
        assertTrue(map.containsKey("optional"));
        assertNull(map.get("optional"));
        assertTrue(utils.toMap("{}").isEmpty());
        assertTrue(utils.toMap("null").isEmpty());

        for(var json : List.of("[]", "[[\"name\",\"value\"]]", "\"text\"", "42", "true")) {
            assertThrows(graphql.GraphQLException.class, () -> utils.toMap(json), json);
        }
        assertThrows(com.google.gson.JsonParseException.class, () -> utils.toMap("{\"name\":}"));
    }

    @Test
    public void incorrectlyTypedFormVariablesReportCommandErrorBeforeExecution() {
        for(var variables : List.of("\"invalid\"", "[]", "[[\"name\",\"value\"]]", "42", "true")) {
            var command = new TestCommand();
            var form = GraphQlFormFactory.getExecuteGraphQlForm();
            form.setReadOnly("false");
            form.setIdempotencyKey("retry-key");
            form.setQuery(MUTATION);
            form.setVariables(variables);

            assertNull(command.executeForm(form).getExecutionResult());
            assertTrue(command.hasExecutionErrors());
            assertEquals(0, command.locks);
            assertEquals(0, command.control.creates);
        }
    }

    @Test
    public void missingDocumentReportsCommandErrorWithoutClaimingKey() {
        var command = new TestCommand();
        var form = GraphQlFormFactory.getExecuteGraphQlForm();
        form.setReadOnly("false");
        form.setIdempotencyKey("retry-key");
        form.setJson("{}");
        var response = command.executeForm(form).getExecutionResult();
        assertNull(response);
        assertTrue(command.hasExecutionErrors());
        assertEquals(0, command.control.creates);
    }

    @Test
    public void relayClientMutationIdSupportsVariableInsideInlineInput() {
        var data = relayResponse("""
                mutation IdleUsingVariable($clientMutationId: String) {
                    idle(input: {clientMutationId: $clientMutationId}) {
                        clientMutationId
                        commandResult { hasErrors }
                    }
                }
                """, Map.of("clientMutationId", "variable-id"));
        assertEquals("variable-id", data.getAsJsonObject("idle").get("clientMutationId").getAsString());
    }

    @Test
    public void relayClientMutationIdSupportsWholeInputVariable() {
        var engine = graphQl(new ExecuteGraphQlCommand(), null);
        var inputType = engine.getGraphQLSchema().getMutationType().getFieldDefinition("idle").getArgument("input").getType();
        var document = "mutation($input: " + graphql.schema.GraphQLTypeUtil.simplePrint(inputType)
                + ") { idle(input: $input) { clientMutationId } }";
        var data = relayResponse(document, Map.of("input", Map.of("clientMutationId", "input-id")));
        assertEquals("input-id", data.getAsJsonObject("idle").get("clientMutationId").getAsString());
    }

    @Test
    public void relayClientMutationIdsStayWithTheirAliasedMutationAndFragment() {
        var data = relayResponse("""
                mutation {
                    first: idle(input: {clientMutationId: "first-id"}) { echo: clientMutationId }
                    ...OtherMutation
                }
                fragment OtherMutation on TestMutation {
                    second: idle(input: {clientMutationId: "second-id"}) { clientMutationId }
                }
                """, Map.of());
        assertEquals("first-id", data.getAsJsonObject("first").get("echo").getAsString());
        assertEquals("second-id", data.getAsJsonObject("second").get("clientMutationId").getAsString());
    }

    @Test
    public void relayClientMutationIdSupportsDefaultOmittedAndNullValues() {
        var data = relayResponse("""
                mutation($id: String = "default-id") {
                    defaulted: idle(input: {clientMutationId: $id}) { clientMutationId }
                    omitted: idle(input: {}) { clientMutationId }
                    explicitNull: idle(input: {clientMutationId: null}) { clientMutationId }
                }
                """, Map.of());
        assertEquals("default-id", data.getAsJsonObject("defaulted").get("clientMutationId").getAsString());
        assertTrue(data.getAsJsonObject("omitted").get("clientMutationId").isJsonNull());
        assertTrue(data.getAsJsonObject("explicitNull").get("clientMutationId").isJsonNull());
    }

}
