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
import com.echothree.control.user.period.common.edit.PeriodEditFactory;
import com.echothree.control.user.period.common.form.PeriodFormFactory;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLTypeUtil;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PeriodTypeSchemaTest {

    @Test
    public void queriesExposeAlternativeIdentifiersAndCountedPagination() {
        for(var schema : List.of(GraphQlSchemaUtils.getInstance().getSchema(), GraphQlSchemaUtils.getInstance().getReadOnlySchema())) {
            var query = schema.getQueryType();
            var single = query.getFieldDefinition("periodType");
            for(var argument : List.of("periodKindName", "periodTypeName", "id")) {
                assertFalse(single.getArgument(argument).getType() instanceof GraphQLNonNull);
            }
            assertEquals("ID", GraphQLTypeUtil.unwrapAll(single.getArgument("id").getType()).getName());
            assertEquals("PeriodType", GraphQLTypeUtil.unwrapAll(single.getType()).getName());

            var multiple = query.getFieldDefinition("periodTypes");
            assertInstanceOf(GraphQLNonNull.class, multiple.getArgument("periodKindName").getType());
            assertNotNull(multiple.getArgument("first"));
            assertNotNull(multiple.getArgument("after"));
            var connection = (GraphQLObjectType)GraphQLTypeUtil.unwrapAll(multiple.getType());
            assertNotNull(connection.getFieldDefinition("totalCount"));
            assertNotNull(connection.getFieldDefinition("edges"));

            var type = schema.getObjectType("PeriodType");
            for(var field : List.of("periodKind", "periodTypeName", "parentPeriodType", "workflowEntrance", "isDefault", "sortOrder", "description")) {
                assertNotNull(type.getFieldDefinition(field), field);
            }
            assertNotNull(schema.getObjectType("PeriodKind").getFieldDefinition("periodTypes"));
        }
    }

    @Test
    public void mutationsExposeIdsAndOptionalEditFields() {
        var mutation = GraphQlSchemaUtils.getInstance().getSchema().getMutationType();
        var create = mutation.getFieldDefinition("createPeriodType");
        var createInput = (GraphQLInputObjectType)GraphQLTypeUtil.unwrapAll(create.getArgument("input").getType());
        for(var required : List.of("periodKindName", "periodTypeName", "isDefault", "sortOrder")) {
            assertInstanceOf(GraphQLNonNull.class, createInput.getFieldDefinition(required).getType());
        }
        for(var optional : List.of("parentPeriodTypeName", "workflowName", "workflowEntranceName", "description")) {
            assertFalse(createInput.getFieldDefinition(optional).getType() instanceof GraphQLNonNull);
        }
        assertNotNull(((GraphQLObjectType)GraphQLTypeUtil.unwrapAll(create.getType())).getFieldDefinition("id"));

        var edit = mutation.getFieldDefinition("editPeriodType");
        var editInput = (GraphQLInputObjectType)GraphQLTypeUtil.unwrapAll(edit.getArgument("input").getType());
        assertInstanceOf(GraphQLNonNull.class, editInput.getFieldDefinition("periodKindName").getType());
        assertInstanceOf(GraphQLNonNull.class, editInput.getFieldDefinition("originalPeriodTypeName").getType());
        for(var optional : List.of("periodTypeName", "parentPeriodTypeName", "workflowName", "workflowEntranceName", "isDefault", "sortOrder", "description")) {
            assertFalse(editInput.getFieldDefinition(optional).getType() instanceof GraphQLNonNull);
        }
        assertNotNull(((GraphQLObjectType)GraphQLTypeUtil.unwrapAll(edit.getType())).getFieldDefinition("id"));

        var delete = mutation.getFieldDefinition("deletePeriodType");
        var deleteInput = (GraphQLInputObjectType)GraphQLTypeUtil.unwrapAll(delete.getArgument("input").getType());
        assertInstanceOf(GraphQLNonNull.class, deleteInput.getFieldDefinition("periodKindName").getType());
        assertInstanceOf(GraphQLNonNull.class, deleteInput.getFieldDefinition("periodTypeName").getType());
    }

    @Test
    public void parentPeriodTypeNameRoundTripsThroughGeneratedFormsAndEdits() {
        var form = PeriodFormFactory.getCreatePeriodTypeForm();
        form.setParentPeriodTypeName("PARENT");
        assertEquals("PARENT", form.getParentPeriodTypeName());

        var edit = PeriodEditFactory.getPeriodTypeEdit();
        edit.setParentPeriodTypeName("PARENT");
        assertEquals("PARENT", edit.getParentPeriodTypeName());
        edit.setParentPeriodTypeName(null);
        assertNull(edit.getParentPeriodTypeName());
    }

    @Test
    public void queriesAndMutationsNeverAcceptEntityRef() {
        var schema = GraphQlSchemaUtils.getInstance().getSchema();
        for(var root : List.of(schema.getQueryType(), schema.getMutationType())) {
            for(var field : root.getFieldDefinitions()) {
                for(var argument : field.getArguments()) {
                    assertNotEquals("entityRef", argument.getName(), field.getName());
                    if(GraphQLTypeUtil.unwrapAll(argument.getType()) instanceof GraphQLInputObjectType input) {
                        assertNull(input.getFieldDefinition("entityRef"), field.getName());
                    }
                }
            }
        }
    }

}
