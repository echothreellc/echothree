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

public class LinkTypeSchemaTest {

    @Test
    public void queriesExposeAlternativeIdentifiersAndCountedPagination() {
        for(var schema : List.of(GraphQlSchemaUtils.getInstance().getSchema(), GraphQlSchemaUtils.getInstance().getReadOnlySchema())) {
            var query = schema.getQueryType();
            var linkType = query.getFieldDefinition("linkType");

            assertEquals(2, linkType.getArguments().size());
            assertNotNull(query.getFieldDefinition("linkTypeTranslation"));
            assertNotNull(query.getFieldDefinition("linkTypeTranslations"));
            for(var argument : List.of("linkTypeName", "id")) {
                assertFalse(linkType.getArgument(argument).getType() instanceof GraphQLNonNull);
            }
            assertEquals("ID", GraphQLTypeUtil.unwrapAll(linkType.getArgument("id").getType()).getName());
            assertEquals("LinkType", GraphQLTypeUtil.unwrapAll(linkType.getType()).getName());

            var linkTypes = query.getFieldDefinition("linkTypes");
            assertNotNull(linkTypes.getArgument("first"));
            assertNotNull(linkTypes.getArgument("after"));
            var connection = (GraphQLObjectType)GraphQLTypeUtil.unwrapAll(linkTypes.getType());
            assertNotNull(connection.getFieldDefinition("totalCount"));
            assertNotNull(connection.getFieldDefinition("edges"));

            var linkTypeType = schema.getObjectType("LinkType");
            for(var field : List.of("id", "linkTypeName", "parentLinkType", "sourceEntityType", "targetEntityType", "linkCardinality", "linkDirectionality", "isTransitive", "inverseLinkType", "sortOrder", "linkTypeTranslation", "linkTypeTranslations")) {
                assertNotNull(linkTypeType.getFieldDefinition(field), field);
            }
            for(var field : List.of("parentLinkType", "inverseLinkType", "sourceEntityType", "targetEntityType", "linkCardinality", "linkDirectionality", "linkTypeTranslation")) {
                assertFalse(linkTypeType.getFieldDefinition(field).getType() instanceof GraphQLNonNull);
            }
            for(var field : List.of("description", "guidance", "guidanceMimeType")) {
                assertNull(linkTypeType.getFieldDefinition(field));
            }
        }
    }

    @Test
    public void mutationsExposeIdsAndOptionalEditFields() {
        var mutation = GraphQlSchemaUtils.getInstance().getSchema().getMutationType();
        var create = mutation.getFieldDefinition("createLinkType");
        var createInput = (GraphQLInputObjectType)GraphQLTypeUtil.unwrapAll(create.getArgument("input").getType());
        for(var required : List.of("linkTypeName", "sourceComponentVendorName", "sourceEntityTypeName", "targetComponentVendorName", "targetEntityTypeName", "linkCardinalityName", "linkDirectionalityName", "isTransitive", "sortOrder")) {
            assertInstanceOf(GraphQLNonNull.class, createInput.getFieldDefinition(required).getType());
        }
        assertFalse(createInput.getFieldDefinition("description").getType() instanceof GraphQLNonNull);
        assertNull(createInput.getFieldDefinition("entityRef"));
        assertNotNull(((GraphQLObjectType)GraphQLTypeUtil.unwrapAll(create.getType())).getFieldDefinition("id"));

        var edit = mutation.getFieldDefinition("editLinkType");
        var editInput = (GraphQLInputObjectType)GraphQLTypeUtil.unwrapAll(edit.getArgument("input").getType());
        for(var optional : List.of("originalLinkTypeName", "id", "linkTypeName", "isTransitive", "sortOrder", "description")) {
            assertFalse(editInput.getFieldDefinition(optional).getType() instanceof GraphQLNonNull);
        }
        assertNull(editInput.getFieldDefinition("entityRef"));
        assertNotNull(((GraphQLObjectType)GraphQLTypeUtil.unwrapAll(edit.getType())).getFieldDefinition("id"));

        var delete = mutation.getFieldDefinition("deleteLinkType");
        var deleteInput = (GraphQLInputObjectType)GraphQLTypeUtil.unwrapAll(delete.getArgument("input").getType());
        for(var optional : List.of("linkTypeName", "id")) {
            assertFalse(deleteInput.getFieldDefinition(optional).getType() instanceof GraphQLNonNull);
        }
        assertEquals("ID", GraphQLTypeUtil.unwrapAll(deleteInput.getFieldDefinition("id").getType()).getName());
        assertNull(deleteInput.getFieldDefinition("entityRef"));
        assertNull(createInput.getFieldDefinition("isDefault"));
        var linkTypeTranslation = mutation.getFieldDefinition("createLinkTypeTranslation");
        var translationInput = (GraphQLInputObjectType)GraphQLTypeUtil.unwrapAll(linkTypeTranslation.getArgument("input").getType());
        for(var required : List.of("linkTypeName", "languageIsoName", "description")) {
            assertInstanceOf(GraphQLNonNull.class, translationInput.getFieldDefinition(required).getType());
        }
        for(var optional : List.of("guidanceMimeTypeName", "guidance")) {
            assertFalse(translationInput.getFieldDefinition(optional).getType() instanceof GraphQLNonNull);
        }
        assertNotNull(mutation.getFieldDefinition("editLinkTypeTranslation"));
        assertNotNull(mutation.getFieldDefinition("deleteLinkTypeTranslation"));
    }

}
