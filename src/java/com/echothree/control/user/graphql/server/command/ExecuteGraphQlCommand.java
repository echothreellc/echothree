// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.model.control.graphql.server.util.BaseGraphQl;
import com.echothree.model.control.graphql.server.util.GraphQlExecutionContext;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.string.GraphQlUtils;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.google.gson.JsonParseException;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLException;
import graphql.annotations.strategies.EnhancedExecutionStrategy;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;

@Dependent
public class ExecuteGraphQlCommand
        extends BaseSimpleCommand<ExecuteGraphQlForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = List.of(
                new FieldDefinition("ReadOnly", FieldType.BOOLEAN, true, null, null),
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
    
    /** Creates a new instance of ExecuteGraphQlCommand */
    public ExecuteGraphQlCommand() {
        super(null, FORM_FIELD_DEFINITIONS, false);
    }
    
    private static final String GRAPHQL_QUERY = "query";
    private static final String GRAPHQL_OPERATION_NAME = "operationName";
    private static final String GRAPHQL_VARIABLES = "variables";
    
    public String toJson(ExecutionResult executionResult)  {
        // Contents of the GraphQL Response are specified here:
        // http://graphql.org/learn/serving-over-http/
        var executionResultMap = new LinkedHashMap<String, Object>();
        
        if(!executionResult.getErrors().isEmpty()) {
            executionResultMap.put("errors", executionResult.getErrors());
        }
        executionResultMap.put("data", executionResult.getData());
        
        return GraphQlUtils.getInstance().toJson(executionResultMap);
    }
    
    @Override
    protected BaseResult execute() {
        var result = GraphQlResultFactory.getExecuteGraphQlResult();

        try {
            var readOnly = Boolean.parseBoolean(form.getReadOnly());
            var query = form.getQuery();
            var variables = form.getVariables();
            var operationName = form.getOperationName();
            var json = form.getJson();

            var graphQL = GraphQL
                    .newGraphQL(readOnly? GraphQlSchemaUtils.getInstance().getReadOnlySchema() : GraphQlSchemaUtils.getInstance().getSchema())
                    .queryExecutionStrategy(new EnhancedExecutionStrategy())
                    .preparsedDocumentProvider(GraphQlDocumentCache.getInstance())
                    .build();

            Map<String, Object> parsedVariables = null;
            if(variables != null) {
                Object possibleVariables = GraphQlUtils.getInstance().toMap(variables);

                if(possibleVariables instanceof Map) {
                    parsedVariables = (Map<String, Object>)possibleVariables;
                } else {
                    getLog().error("Discarding parsedVariables, not an instance of Map");
                }
            }

            if(json != null) {
                var body = GraphQlUtils.getInstance().toMap(json);
                var possibleQuery = body.get(GRAPHQL_QUERY);
                var possibleOperationName = body.get(GRAPHQL_OPERATION_NAME);
                var possibleVariables = body.get(GRAPHQL_VARIABLES);

                // Query form field takes priority of Json's query.
                if(possibleQuery != null && query == null) {
                    if(possibleQuery instanceof String string) {
                        query = string;
                    } else {
                        getLog().error("Discarding query, not an instance of String");
                    }
                }

                // OperationName form field takes priority of Json's operationName.
                if(possibleOperationName != null && operationName == null) {
                    if(possibleOperationName instanceof String string) {
                        operationName = string;
                    } else {
                        getLog().error("Discarding operationName, not an instance of String");
                    }
                }

                // Variables form field takes priority of Json's variables.
                if(possibleVariables != null && variables == null) {
                    if(possibleVariables instanceof Map) {
                        parsedVariables = (Map<String, Object>)possibleVariables;
                    } else {
                        getLog().error("Discarding parsedVariables, not an instance of Map");
                    }
                }
            }
            
            // query MUST be present.
            if(query != null) {
                var graphQlExecutionContext = new GraphQlExecutionContext(getUserVisitPK(), getUserVisit(),
                        getUserSession(), form.getRemoteInet4Address());
                var builder = ExecutionInput.newExecutionInput()
                        .query(query)
                        .operationName(operationName)
                        .graphQLContext(Map.of(
                                BaseGraphQl.GRAPHQL_EXECUTION_CONTEXT, graphQlExecutionContext))
                        .root(new Object());
                
                if(parsedVariables != null) {
                    builder.variables(parsedVariables);
                }

                var executionResult = graphQL.execute(builder.build());
                result.setExecutionResult(toJson(executionResult));
            } else {
                addExecutionError(ExecutionErrors.InvalidParameterCount.name());
            }
        } catch (JsonParseException jpe) {
            addExecutionError(ExecutionErrors.JsonParseError.name(), jpe.getMessage());
        } catch (GraphQLException gqle) {
            addExecutionError(ExecutionErrors.GraphQlError.name(), gqle.getMessage());
        }

        return result;
    }
    
}
