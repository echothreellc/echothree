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
import graphql.schema.GraphQLNonNull;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLTypeUtil;
import graphql.schema.GraphQLUnionType;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FiscalPeriodSchemaTest {

    @Test
    public void fiscalQueriesExposeAlternativeIdentifiersAndEveryFilter() {
        for(var schema : List.of(GraphQlSchemaUtils.getInstance().getSchema(), GraphQlSchemaUtils.getInstance().getReadOnlySchema())) {
            var query = schema.getQueryType();
            var single = query.getFieldDefinition("fiscalPeriod");
            assertNull(single.getArgument("periodKindName"));
            for(var name : List.of("periodName", "id")) {
                assertNotNull(single.getArgument(name));
                assertFalse(single.getArgument(name).getType() instanceof GraphQLNonNull);
            }
            assertEquals("ID", GraphQLTypeUtil.unwrapAll(single.getArgument("id").getType()).getName());
            assertNull(single.getArgument("entityRef"));
            assertEquals("FiscalPeriod", GraphQLTypeUtil.unwrapAll(single.getType()).getName());

            var list = query.getFieldDefinition("fiscalPeriods");
            assertNull(list.getArgument("periodKindName"));
            for(var name : List.of("parentPeriodName", "periodTypeName", "first", "after")) {
                assertNotNull(list.getArgument(name));
                assertFalse(list.getArgument(name).getType() instanceof GraphQLNonNull);
            }
            assertNull(list.getArgument("id"));
            assertNull(list.getArgument("uuid"));
            var connection = (GraphQLObjectType)GraphQLTypeUtil.unwrapAll(list.getType());
            assertNotNull(connection.getFieldDefinition("totalCount"));
            assertNotNull(connection.getFieldDefinition("pageInfo"));
        }
    }

    @Test
    public void kindAndTypeConnectionsUseTheExtensiblePeriodUnion() {
        var schema = GraphQlSchemaUtils.getInstance().getSchema();
        for(var owner : List.of("PeriodKind", "PeriodType")) {
            var periods = schema.getObjectType(owner).getFieldDefinition("periods");
            assertNotNull(periods.getArgument("parentPeriodName"));
            assertNull(periods.getArgument("id"));
            assertNull(periods.getArgument("uuid"));
            var connection = (GraphQLObjectType)GraphQLTypeUtil.unwrapAll(periods.getType());
            assertNotNull(connection.getFieldDefinition("totalCount"));
            var edge = (GraphQLObjectType)GraphQLTypeUtil.unwrapAll(connection.getFieldDefinition("edges").getType());
            var union = (GraphQLUnionType)GraphQLTypeUtil.unwrapAll(edge.getFieldDefinition("node").getType());
            assertEquals("PeriodInterface", union.getName());
            assertEquals(List.of("FiscalPeriod"), union.getTypes().stream().map(t -> t.getName()).toList());
        }
    }

    @Test
    public void fiscalObjectInheritsCommonFieldsAndAddsStatus() {
        var schema = GraphQlSchemaUtils.getInstance().getSchema();
        var type = schema.getObjectType("FiscalPeriod");
        for(var name : List.of("id", "periodName", "periodKind", "periodType", "parentPeriod", "startTime", "endTime", "description", "periods", "status")) {
            assertNotNull(type.getFieldDefinition(name), name);
        }
        assertInstanceOf(GraphQLUnionType.class, GraphQLTypeUtil.unwrapAll(type.getFieldDefinition("parentPeriod").getType()));
    }

}
