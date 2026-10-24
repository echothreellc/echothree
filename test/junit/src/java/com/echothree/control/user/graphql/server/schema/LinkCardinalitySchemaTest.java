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

import com.echothree.control.user.graphql.server.schema.util.GraphQlSchemaUtils;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLTypeUtil;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkCardinalitySchemaTest {

    @Test
    public void queriesExposeAlternativeIdentifiersAndCountedPagination() {
        for(var schema : List.of(GraphQlSchemaUtils.getInstance().getSchema(), GraphQlSchemaUtils.getInstance().getReadOnlySchema())) {
            var query = schema.getQueryType();
            var linkCardinality = query.getFieldDefinition("linkCardinality");

            assertEquals(2, linkCardinality.getArguments().size());
            for(var argument : List.of("linkCardinalityName", "id")) {
                assertFalse(linkCardinality.getArgument(argument).getType() instanceof GraphQLNonNull);
            }
            assertEquals("ID", GraphQLTypeUtil.unwrapAll(linkCardinality.getArgument("id").getType()).getName());
            assertEquals("LinkCardinality", GraphQLTypeUtil.unwrapAll(linkCardinality.getType()).getName());

            var linkCardinalities = query.getFieldDefinition("linkCardinalities");
            assertNotNull(linkCardinalities.getArgument("first"));
            assertNotNull(linkCardinalities.getArgument("after"));
            var connection = (GraphQLObjectType)GraphQLTypeUtil.unwrapAll(linkCardinalities.getType());
            assertNotNull(connection.getFieldDefinition("totalCount"));
            assertNotNull(connection.getFieldDefinition("edges"));

            var linkCardinalityType = schema.getObjectType("LinkCardinality");
            for(var field : List.of("id", "linkCardinalityName", "isDefault", "sortOrder", "description")) {
                assertNotNull(linkCardinalityType.getFieldDefinition(field), field);
            }
        }
    }

    @Test
    public void mutationsExposeIdsAndOptionalEditFields() {
        var mutation = GraphQlSchemaUtils.getInstance().getSchema().getMutationType();
        var create = mutation.getFieldDefinition("createLinkCardinality");
        var createInput = (GraphQLInputObjectType)GraphQLTypeUtil.unwrapAll(create.getArgument("input").getType());
        for(var required : List.of("linkCardinalityName", "isDefault", "sortOrder")) {
            assertInstanceOf(GraphQLNonNull.class, createInput.getFieldDefinition(required).getType());
        }
        assertFalse(createInput.getFieldDefinition("description").getType() instanceof GraphQLNonNull);
        assertNull(createInput.getFieldDefinition("entityRef"));
        assertNotNull(((GraphQLObjectType)GraphQLTypeUtil.unwrapAll(create.getType())).getFieldDefinition("id"));

        var edit = mutation.getFieldDefinition("editLinkCardinality");
        var editInput = (GraphQLInputObjectType)GraphQLTypeUtil.unwrapAll(edit.getArgument("input").getType());
        for(var optional : List.of("originalLinkCardinalityName", "id", "linkCardinalityName", "isDefault", "sortOrder", "description")) {
            assertFalse(editInput.getFieldDefinition(optional).getType() instanceof GraphQLNonNull);
        }
        assertNull(editInput.getFieldDefinition("entityRef"));
        assertNotNull(((GraphQLObjectType)GraphQLTypeUtil.unwrapAll(edit.getType())).getFieldDefinition("id"));

        var delete = mutation.getFieldDefinition("deleteLinkCardinality");
        var deleteInput = (GraphQLInputObjectType)GraphQLTypeUtil.unwrapAll(delete.getArgument("input").getType());
        for(var optional : List.of("linkCardinalityName", "id")) {
            assertFalse(deleteInput.getFieldDefinition(optional).getType() instanceof GraphQLNonNull);
        }
        assertEquals("ID", GraphQLTypeUtil.unwrapAll(deleteInput.getFieldDefinition("id").getType()).getName());
        assertNull(deleteInput.getFieldDefinition("entityRef"));
    }

}
