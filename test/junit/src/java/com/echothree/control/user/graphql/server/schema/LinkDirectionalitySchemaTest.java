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

public class LinkDirectionalitySchemaTest {

    @Test
    public void queriesExposeAlternativeIdentifiersAndCountedPagination() {
        for(var schema : List.of(GraphQlSchemaUtils.getInstance().getSchema(), GraphQlSchemaUtils.getInstance().getReadOnlySchema())) {
            var query = schema.getQueryType();
            var linkDirectionality = query.getFieldDefinition("linkDirectionality");

            assertEquals(2, linkDirectionality.getArguments().size());
            for(var argument : List.of("linkDirectionalityName", "id")) {
                assertFalse(linkDirectionality.getArgument(argument).getType() instanceof GraphQLNonNull);
            }
            assertEquals("ID", GraphQLTypeUtil.unwrapAll(linkDirectionality.getArgument("id").getType()).getName());
            assertEquals("LinkDirectionality", GraphQLTypeUtil.unwrapAll(linkDirectionality.getType()).getName());

            var linkDirectionalities = query.getFieldDefinition("linkDirectionalities");
            assertNotNull(linkDirectionalities.getArgument("first"));
            assertNotNull(linkDirectionalities.getArgument("after"));
            var connection = (GraphQLObjectType)GraphQLTypeUtil.unwrapAll(linkDirectionalities.getType());
            assertNotNull(connection.getFieldDefinition("totalCount"));
            assertNotNull(connection.getFieldDefinition("edges"));

            var linkDirectionalityType = schema.getObjectType("LinkDirectionality");
            for(var field : List.of("id", "linkDirectionalityName", "isDefault", "sortOrder", "description")) {
                assertNotNull(linkDirectionalityType.getFieldDefinition(field), field);
            }
        }
    }

    @Test
    public void mutationsExposeIdsAndOptionalEditFields() {
        var mutation = GraphQlSchemaUtils.getInstance().getSchema().getMutationType();
        var create = mutation.getFieldDefinition("createLinkDirectionality");
        var createInput = (GraphQLInputObjectType)GraphQLTypeUtil.unwrapAll(create.getArgument("input").getType());
        for(var required : List.of("linkDirectionalityName", "isDefault", "sortOrder")) {
            assertInstanceOf(GraphQLNonNull.class, createInput.getFieldDefinition(required).getType());
        }
        assertFalse(createInput.getFieldDefinition("description").getType() instanceof GraphQLNonNull);
        assertNull(createInput.getFieldDefinition("entityRef"));
        assertNotNull(((GraphQLObjectType)GraphQLTypeUtil.unwrapAll(create.getType())).getFieldDefinition("id"));

        var edit = mutation.getFieldDefinition("editLinkDirectionality");
        var editInput = (GraphQLInputObjectType)GraphQLTypeUtil.unwrapAll(edit.getArgument("input").getType());
        for(var optional : List.of("originalLinkDirectionalityName", "id", "linkDirectionalityName", "isDefault", "sortOrder", "description")) {
            assertFalse(editInput.getFieldDefinition(optional).getType() instanceof GraphQLNonNull);
        }
        assertNull(editInput.getFieldDefinition("entityRef"));
        assertNotNull(((GraphQLObjectType)GraphQLTypeUtil.unwrapAll(edit.getType())).getFieldDefinition("id"));

        var delete = mutation.getFieldDefinition("deleteLinkDirectionality");
        var deleteInput = (GraphQLInputObjectType)GraphQLTypeUtil.unwrapAll(delete.getArgument("input").getType());
        for(var optional : List.of("linkDirectionalityName", "id")) {
            assertFalse(deleteInput.getFieldDefinition(optional).getType() instanceof GraphQLNonNull);
        }
        assertEquals("ID", GraphQLTypeUtil.unwrapAll(deleteInput.getFieldDefinition("id").getType()).getName());
        assertNull(deleteInput.getFieldDefinition("entityRef"));
    }

}
