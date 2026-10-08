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

import com.echothree.control.user.graphql.common.form.ExecuteGraphQlForm;
import com.echothree.control.user.graphql.common.result.GraphQlResultFactory;
import com.echothree.control.user.graphql.server.cache.GraphQlDocumentCache;
import com.echothree.control.user.graphql.server.schema.util.GraphQlSchemaUtils;
import com.echothree.control.user.graphql.server.strategy.GraphQlExecutionStrategy;
import com.echothree.model.control.graphql.server.control.IdempotentResponseControl;
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.GraphQlExecutionContext;
import com.echothree.model.data.graphql.server.entity.IdempotentResponse;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.string.GraphQlUtils;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.echothree.util.server.persistence.Sha256Utils;
import com.google.gson.JsonParseException;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLException;
import graphql.execution.AbortExecutionException;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.InstrumentationState;
import graphql.execution.instrumentation.SimplePerformantInstrumentation;
import graphql.execution.instrumentation.parameters.InstrumentationExecuteOperationParameters;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ExecuteGraphQlCommand
        extends BaseSimpleCommand<ExecuteGraphQlForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ReadOnly", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("IdempotencyKey", FieldType.STRING, false, null, null),
                new FieldDefinition("Query", FieldType.STRING, false, 1L, null),
                new FieldDefinition("Variables", FieldType.STRING, false, 1L, null),
                new FieldDefinition("OperationName", FieldType.STRING, false, 1L, null),
                new FieldDefinition("Json", FieldType.STRING, false, 1L, null),
                // RemoteInet4Address is purposefully validated only as a string, as it's passed
                // to other UCs that will validate it as an IPv4 address and format as necessary
                // for use.
                new FieldDefinition("RemoteInet4Address", FieldType.STRING, false, 1L, null)
        );
    }
    
    @Inject
    protected IdempotentResponseControl idempotentResponseControl;

    @Inject
    Sha256Utils sha256Utils;

    /** Creates a new instance of ExecuteGraphQlCommand */
    public ExecuteGraphQlCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    private static final String GRAPHQL_QUERY = "query";
    private static final String GRAPHQL_OPERATION_NAME = "operationName";
    private static final String GRAPHQL_VARIABLES = "variables";

    private static final long IDEMPOTENCY_RETENTION_MILLIS = 24 * 60 * 60 * 1000; // 24 Hours

    protected static class IdempotencyState {
        IdempotentResponse idempotentResponse;
        String replayResponse;
    }

    // Object member order is insignificant; array order and explicit nulls are preserved.
    private static Object canonicalize(Object value) {
        Object result = value;

        if(value instanceof Map<?, ?> map) {
            var sorted = new TreeMap<String, Object>();

            map.forEach((key, member) -> sorted.put((String)key, canonicalize(member)));
            result = sorted;
        } else if(value instanceof List<?> list) {
            result = list.stream().map(ExecuteGraphQlCommand::canonicalize).toList();
        }

        return result;
    }

    protected String hashVariables(Map<String, Object> variables) {
        String result = null;

        if(variables != null && !variables.isEmpty()) {
            @SuppressWarnings("unchecked")
            var canonicalVariables = (Map<String, Object>)canonicalize(variables);

            result = sha256Utils.hash(GraphQlUtils.getInstance().toJson(canonicalVariables));
        }

        return result;
    }

    protected void beginIdempotency(final String idempotencyKey, final ExecutionInput input, final IdempotencyState idempotencyState) {
        // This existing parent row provides a lock even when the idempotencyKey does not exist yet.
        // The database holds it through transaction completion, including response storage.
        var userVisit = getUserVisitForUpdate();

        var keyHash = sha256Utils.hash(idempotencyKey);
        var queryHash = sha256Utils.hash(input.getQuery());
        var variablesHash = hashVariables(input.getVariables());
        var operationNameHash = input.getOperationName() == null ? null : sha256Utils.hash(input.getOperationName());
        var existingIdempotentResponse = idempotentResponseControl.getIdempotentResponseForUpdate(userVisit, keyHash);

        if(existingIdempotentResponse != null) {
            if(!Objects.equals(existingIdempotentResponse.getQueryHash(), queryHash)
                    || !Objects.equals(existingIdempotentResponse.getVariablesHash(), variablesHash)
                    || !Objects.equals(existingIdempotentResponse.getOperationNameHash(), operationNameHash)) {
                throw new AbortExecutionException("Idempotency-Key was already used with a different request.");
            }

            if(existingIdempotentResponse.getCompletedTime() == null || existingIdempotentResponse.getResponse() == null) {
                throw new AbortExecutionException("Idempotency-Key request has not completed. Retry later.");
            }

            idempotencyState.replayResponse = idempotentResponseControl.decodeIdempotentResponse(existingIdempotentResponse);

            // GraphQL catches this before invoking any resolver. finishExecution replaces
            // its synthetic error result with the original response verbatim.
            throw new AbortExecutionException("Replaying completed idempotent request.");
        }

        idempotencyState.idempotentResponse = idempotentResponseControl.createIdempotentResponse(userVisit, keyHash, queryHash, variablesHash,
                operationNameHash, session.getStartTime(), null, null, null);

        // create() returns a read-only entity; retain a writable one for completion.
        idempotencyState.idempotentResponse = Objects.requireNonNull(idempotentResponseControl.getIdempotentResponseForUpdate(userVisit, keyHash),
                "Newly created idempotency claim was not found");
    }

    protected String finishExecution(final ExecutionResult executionResult, final IdempotencyState idempotencyState) {
        var replayResponse = idempotencyState.replayResponse;

        if(replayResponse == null) {
            var idempotentResponse = idempotencyState.idempotentResponse;
            replayResponse = toJson(executionResult);

            if(idempotentResponse != null) {
                var completedTime = System.currentTimeMillis();

                idempotentResponse.setResponse(idempotentResponseControl.encodeIdempotentResponse(idempotentResponse, replayResponse));
                idempotentResponse.setCompletedTime(completedTime);
                idempotentResponse.setRetainUntilTime(completedTime + IDEMPOTENCY_RETENTION_MILLIS);
                idempotentResponse.store();
            }
        }

        return replayResponse;
    }
    
    public String toJson(final ExecutionResult executionResult)  {
        return GraphQlUtils.getInstance().toJson(executionResult.toSpecification());
    }

    protected SimplePerformantInstrumentation getInstrumentation(final String idempotencyKey, final IdempotencyState idempotencyState) {
        return new SimplePerformantInstrumentation() {
            @Override
            public InstrumentationContext<ExecutionResult> beginExecuteOperation(
                    InstrumentationExecuteOperationParameters parameters,
                    InstrumentationState state) {
                var operation = parameters.getExecutionContext()
                        .getOperationDefinition()
                        .getOperation();

                switch(operation) {
                    case QUERY, SUBSCRIPTION -> {
                        // Selected operation is a query or subscription.
                        if(idempotencyKey != null) {
                            throw new AbortExecutionException("Idempotency-Key is supported only for mutation operations.");
                        }
                    }
                    case MUTATION -> {
                        // Selected operation is a mutation.
                        // Idempotency claim must succeed before execution continues.
                        if(idempotencyKey != null) {
                            beginIdempotency(idempotencyKey, parameters.getExecutionContext().getExecutionInput(), idempotencyState);
                        }
                    }
                }

                return super.beginExecuteOperation(parameters, state);
            }
        };
    }

    @Override
    protected BaseResult execute() {
        var result = GraphQlResultFactory.getExecuteGraphQlResult();
        var idempotencyState = new IdempotencyState();

        try {
            var readOnly = Boolean.parseBoolean(form.getReadOnly());
            var idempotencyKey = form.getIdempotencyKey();
            var query = form.getQuery();
            var variables = form.getVariables();
            var operationName = form.getOperationName();
            var json = form.getJson();

            Map<String, Object> parsedVariables = null;
            if(variables != null) {
                parsedVariables = GraphQlUtils.getInstance().toMap(variables);
            }

            if(json != null) {
                var body = GraphQlUtils.getInstance().toMap(json);
                var possibleQuery = body.get(GRAPHQL_QUERY);
                var possibleOperationName = body.get(GRAPHQL_OPERATION_NAME);
                var possibleVariables = body.get(GRAPHQL_VARIABLES);

                // Validate supplied fields even when a form field takes precedence.
                if(possibleQuery != null && !(possibleQuery instanceof String)) {
                    throw new GraphQLException("The query field must be a string.");
                }

                if(possibleOperationName != null && !(possibleOperationName instanceof String)) {
                    throw new GraphQLException("The operationName field must be a string or null.");
                }

                if(possibleVariables != null && !(possibleVariables instanceof Map)) {
                    throw new GraphQLException("The variables field must be a JSON object or null.");
                }

                // Query form field takes priority of Json's query.
                if(possibleQuery != null && query == null) {
                    query = (String)possibleQuery;
                }

                // OperationName form field takes priority of Json's operationName.
                if(possibleOperationName != null && operationName == null) {
                    operationName = (String)possibleOperationName;
                }

                // Variables form field takes priority of Json's variables.
                if(possibleVariables != null && variables == null) {
                    parsedVariables = (Map<String, Object>)possibleVariables;
                }
            }
            
            // query MUST be present.
            if(query != null) {
                var graphQL = GraphQL
                        .newGraphQL(readOnly? GraphQlSchemaUtils.getInstance().getReadOnlySchema() : GraphQlSchemaUtils.getInstance().getSchema())
                        .queryExecutionStrategy(new GraphQlExecutionStrategy())
                        .preparsedDocumentProvider(GraphQlDocumentCache.getInstance())
                        .instrumentation(getInstrumentation(idempotencyKey, idempotencyState))
                        .build();
                var graphQlExecutionContext = new GraphQlExecutionContext(getUserVisitPK(), getUserVisit(),
                        getUserSession(), form.getRemoteInet4Address());
                var builder = ExecutionInput.newExecutionInput()
                        .query(query)
                        .operationName(operationName)
                        .graphQLContext(Map.of(BaseGraphQl.GRAPHQL_EXECUTION_CONTEXT, graphQlExecutionContext))
                        .root(new Object());
                
                if(parsedVariables != null) {
                    builder.variables(parsedVariables);
                }

                var executionResult = graphQL.execute(builder.build());

                result.setExecutionResult(finishExecution(executionResult, idempotencyState));
            } else {
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
            }
        } catch (JsonParseException jpe) {
            if(idempotencyState.idempotentResponse != null) {
                // Let the EJB container roll back the claim and any participating mutation
                // changes together. Removing only the claim could commit changes that a retry repeats.
                throw jpe;
            }

            addExecutionError(ExecutionErrors.JsonParseError.name(), jpe.getMessage());
        } catch (GraphQLException gqle) {
            if(idempotencyState.idempotentResponse != null) {
                // Let the EJB container roll back the claim and any participating mutation
                // changes together. Returning an error normally could commit an incomplete claim.
                throw gqle;
            }

            addExecutionError(ExecutionErrors.GraphQlError.name(), gqle.getMessage());
        }

        return result;
    }
    
}
