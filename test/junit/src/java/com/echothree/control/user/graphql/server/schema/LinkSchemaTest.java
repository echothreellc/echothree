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

package com.echothree.control.user.graphql.server.schema;

import com.echothree.control.user.core.server.command.CreateLinkCommand;
import com.echothree.control.user.core.server.command.DeleteLinkCommand;
import com.echothree.control.user.core.server.command.GetLinkCommand;
import com.echothree.control.user.core.server.command.GetLinksCommand;
import com.echothree.control.user.graphql.server.schema.util.GraphQlSchemaUtils;
import com.echothree.util.common.validation.FieldDefinition;
import graphql.parser.Parser;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLTypeUtil;
import graphql.validation.Validator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkSchemaTest {

    @SuppressWarnings("unchecked")
    private static Map<String, Boolean> expectedParameters(Class<?> command) throws ReflectiveOperationException {
        var definitions = command.getDeclaredField("FORM_FIELD_DEFINITIONS");
        definitions.setAccessible(true);
        return ((List<FieldDefinition>)definitions.get(null)).stream()
                .filter(definition -> !definition.getFieldName().endsWith("EntityRef"))
                .collect(Collectors.toMap(definition -> switch(definition.getFieldName()) {
                    case "SourceUuid" -> "sourceId";
                    case "TargetUuid" -> "targetId";
                    default -> Character.toLowerCase(definition.getFieldName().charAt(0)) + definition.getFieldName().substring(1);
                }, definition -> definition.getIsRequired() || (command != GetLinksCommand.class
                        && Set.of("SourceUuid", "TargetUuid").contains(definition.getFieldName()))));
    }

    @Test
    public void queriesMatchCommandFieldsAndExposeCountedPagination() throws ReflectiveOperationException {
        for(var schema : List.of(GraphQlSchemaUtils.getInstance().getSchema(), GraphQlSchemaUtils.getInstance().getReadOnlySchema())) {
            for(var entry : Map.of("link", GetLinkCommand.class, "links", GetLinksCommand.class).entrySet()) {
                var field = schema.getQueryType().getFieldDefinition(entry.getKey());
                assertNotNull(field);
                var expected = expectedParameters(entry.getValue());
                var actual = field.getArguments().stream()
                        .filter(argument -> !Set.of("first", "last", "before", "after").contains(argument.getName()))
                        .collect(Collectors.toMap(argument -> argument.getName(), argument -> argument.getType() instanceof GraphQLNonNull));
                assertEquals(expected, actual);
                for(var endpoint : List.of("sourceId", "targetId")) {
                    assertEquals("ID", GraphQLTypeUtil.unwrapAll(field.getArgument(endpoint).getType()).getName());
                }
                assertNull(field.getArgument("sourceEntityRef"));
                assertNull(field.getArgument("targetEntityRef"));
            }
            assertEquals("Link", GraphQLTypeUtil.unwrapAll(schema.getQueryType().getFieldDefinition("link").getType()).getName());
            var links = schema.getQueryType().getFieldDefinition("links");
            for(var pagination : List.of("first", "last", "before", "after")) {
                assertNotNull(links.getArgument(pagination));
            }
            var connection = (GraphQLObjectType)GraphQLTypeUtil.unwrapAll(links.getType());
            assertNotNull(connection.getFieldDefinition("totalCount"));
            assertNotNull(connection.getFieldDefinition("edges"));
        }
    }

    @Test
    public void mutationsMatchCommandFieldsAndUseStandardResults() throws ReflectiveOperationException {
        var schema = GraphQlSchemaUtils.getInstance().getSchema();
        for(var entry : Map.of("createLink", CreateLinkCommand.class, "deleteLink", DeleteLinkCommand.class).entrySet()) {
            var field = schema.getMutationType().getFieldDefinition(entry.getKey());
            assertNotNull(field);
            var input = (GraphQLInputObjectType)GraphQLTypeUtil.unwrapAll(field.getArgument("input").getType());
            var actual = input.getFieldDefinitions().stream()
                    .filter(definition -> !definition.getName().equals("clientMutationId"))
                    .collect(Collectors.toMap(definition -> definition.getName(), definition -> definition.getType() instanceof GraphQLNonNull));
            assertEquals(expectedParameters(entry.getValue()), actual);
            for(var endpoint : List.of("sourceId", "targetId")) {
                assertEquals("ID", GraphQLTypeUtil.unwrapAll(input.getFieldDefinition(endpoint).getType()).getName());
            }
            assertNull(input.getFieldDefinition("sourceEntityRef"));
            assertNull(input.getFieldDefinition("targetEntityRef"));
            var result = (GraphQLObjectType)GraphQLTypeUtil.unwrapAll(field.getType());
            assertNotNull(result.getFieldDefinition("commandResult"));
        }
        var readOnly = GraphQlSchemaUtils.getInstance().getReadOnlySchema();
        assertTrue(readOnly.getMutationType() == null || readOnly.getMutationType().getFieldDefinition("createLink") == null);
    }

    @Test
    public void representativeQueriesAndMutationsValidateAgainstTheSchema() {
        var queries = List.of(
                "query { link(linkTypeName: \"PART_OF\", sourceId: \"source\", targetId: \"target\") { linkType { linkTypeName } sourceEntityInstance { id } targetEntityInstance { id } } }",
                "query { links { totalCount edges { node { linkType { linkTypeName } } } } }",
                "query { links(linkTypeName: \"PART_OF\", sourceId: \"source\", targetId: \"target\", first: 10) { totalCount edges { node { targetEntityInstance { id } } } } }");
        var validator = new Validator();
        for(var schema : List.of(GraphQlSchemaUtils.getInstance().getSchema(), GraphQlSchemaUtils.getInstance().getReadOnlySchema())) {
            for(var query : queries) {
                assertTrue(validator.validateDocument(schema, Parser.parse(query), Locale.ROOT).isEmpty(), query);
            }
        }
        for(var mutation : List.of("createLink", "deleteLink")) {
            var document = "mutation { " + mutation + "(input: { linkTypeName: \"PART_OF\", sourceId: \"source\", targetId: \"target\" }) { commandResult { hasErrors } } }";
            assertTrue(validator.validateDocument(GraphQlSchemaUtils.getInstance().getSchema(), Parser.parse(document), Locale.ROOT).isEmpty(), document);
        }
    }

    @Test
    public void singularQueriesRejectMissingOrNullEndpointIds() {
        var validator = new Validator();
        for(var schema : List.of(GraphQlSchemaUtils.getInstance().getSchema(), GraphQlSchemaUtils.getInstance().getReadOnlySchema())) {
            for(var endpoint : List.of("sourceId", "targetId")) {
                var otherEndpoint = endpoint.equals("sourceId") ? "targetId" : "sourceId";
                for(var omitted : List.of(false, true)) {
                    var arguments = "linkTypeName: \"PART_OF\", " + otherEndpoint + ": \"other\""
                            + (omitted ? "" : ", " + endpoint + ": null");
                    var document = "query { link(" + arguments + ") { linkType { linkTypeName } } }";
                    assertFalse(validator.validateDocument(schema, Parser.parse(document), Locale.ROOT).isEmpty(), document);
                }
            }
        }
    }

    @Test
    public void createAndDeleteRejectMissingOrNullEndpointIds() {
        var schema = GraphQlSchemaUtils.getInstance().getSchema();
        var validator = new Validator();
        for(var mutation : List.of("createLink", "deleteLink")) {
            for(var endpoint : List.of("sourceId", "targetId")) {
                var otherEndpoint = endpoint.equals("sourceId") ? "targetId" : "sourceId";
                for(var omitted : List.of(false, true)) {
                    var input = "linkTypeName: \"PART_OF\", " + otherEndpoint + ": \"other\""
                            + (omitted ? "" : ", " + endpoint + ": null");
                    var document = "mutation { " + mutation + "(input: { " + input + " }) { commandResult { hasErrors } } }";
                    assertFalse(validator.validateDocument(schema, Parser.parse(document), Locale.ROOT).isEmpty(), document);
                }
            }
        }
    }

}
