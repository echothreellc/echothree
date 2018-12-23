// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.control.user.graphql.common.result.ExecuteGraphQlResult;
import com.echothree.control.user.graphql.common.result.GraphQlResultFactory;
import com.echothree.model.control.graphql.server.util.GraphQlContext;
import com.echothree.model.control.graphql.server.util.GraphQlSchemaUtils;
import com.echothree.model.data.user.common.pk.UserVisitPK;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.string.GraphQlUtils;
import com.echothree.util.common.validation.FieldDefinition;
import com.echothree.util.common.validation.FieldType;
import com.echothree.util.common.command.BaseResult;
import com.echothree.util.server.control.BaseSimpleCommand;
import com.google.gson.JsonParseException;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLException;
import graphql.annotations.strategies.EnhancedExecutionStrategy;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExecuteGraphQlCommand
        extends BaseSimpleCommand<ExecuteGraphQlForm> {
    
    private final static List<FieldDefinition> FORM_FIELD_DEFINITIONS;
    
    static {
        FORM_FIELD_DEFINITIONS = Collections.unmodifiableList(Arrays.asList(
                new FieldDefinition("ReadOnly", FieldType.BOOLEAN, true, null, null),
                new FieldDefinition("Query", FieldType.STRING, false, 1L, null),
                new FieldDefinition("Variables", FieldType.STRING, false, 1L, null),
                new FieldDefinition("OperationName", FieldType.STRING, false, 1L, null),
                new FieldDefinition("Json", FieldType.STRING, false, 1L, null),
                // RemoteInet4Address is purposefully validated only as a string, as it's passed
                // to other UCs that will validate it as an IPv4 address and format as necessary
                // for use.
                new FieldDefinition("RemoteInet4Address", FieldType.STRING, false, 1L, null)
                ));
    }
    
    /** Creates a new instance of ExecuteGraphQlCommand */
    public ExecuteGraphQlCommand(UserVisitPK userVisitPK, ExecuteGraphQlForm form) {
        super(userVisitPK, form, null, FORM_FIELD_DEFINITIONS, false);
    }
    
    private static final String GRAPHQL_QUERY = "query";
    private static final String GRAPHQL_OPERATION_NAME = "operationName";
    private static final String GRAPHQL_VARIABLES = "variables";
    
    public String toJson(ExecutionResult executionResult)  {
        // Contents of the GraphQL Response are specified here:
        // http://graphql.org/learn/serving-over-http/
        Map<String, Object> executionResultMap = new LinkedHashMap<>();
        
        if (executionResult.getErrors().size() > 0) {
            executionResultMap.put("errors", executionResult.getErrors());
        }
        executionResultMap.put("data", executionResult.getData());
        
        return GraphQlUtils.getInstance().toJson(executionResultMap);
    }
    
    @Override
    protected BaseResult execute() {
        ExecuteGraphQlResult result = GraphQlResultFactory.getExecuteGraphQlResult();

        try {
            boolean readOnly = Boolean.valueOf(form.getReadOnly());
            String query = form.getQuery();
            String variables = form.getVariables();
            String operationName = form.getOperationName();
            String json = form.getJson();

            GraphQL graphQL = GraphQL
                    .newGraphQL(readOnly? GraphQlSchemaUtils.getInstance().getReadOnlySchema() : GraphQlSchemaUtils.getInstance().getSchema())
                    .queryExecutionStrategy(new EnhancedExecutionStrategy())
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
                Map<String, Object> body = GraphQlUtils.getInstance().toMap(json);
                Object possibleQuery = body.get(GRAPHQL_QUERY);
                Object possibleOperationName = body.get(GRAPHQL_OPERATION_NAME);
                Object possibleVariables = body.get(GRAPHQL_VARIABLES);

                // Query form field takes priority of Json's query.
                if(possibleQuery != null && query == null) {
                    if(possibleQuery instanceof String) {
                        query = (String)possibleQuery;
                    } else {
                        getLog().error("Discarding query, not an instance of String");
                    }
                }

                // OperationName form field takes priority of Json's operationName.
                if(possibleOperationName != null && operationName == null) {
                    if(possibleOperationName instanceof String) {
                        operationName = (String)possibleOperationName;
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
                GraphQlContext context = new GraphQlContext(getUserVisitPK(), getUserVisit(), getUserSession(),
                        form.getRemoteInet4Address());

                ExecutionInput.Builder builder = ExecutionInput.newExecutionInput()
                        .query(query)
                        .operationName(operationName)
                        .context(context)
                        .root(context);
                
                if(parsedVariables != null) {
                    builder.variables(parsedVariables);
                }
                
                ExecutionResult executionResult = graphQL.execute(builder.build());
                
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
