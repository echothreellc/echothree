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
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLUnionType;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GraphQlSchemaUtilsTest {

    @Test
    public void bothSchemasRegisterAllUnionsWithConcreteMembersAndResolvers() {
        var schemas = GraphQlSchemaUtils.getInstance();

        for(var schema : List.of(schemas.getSchema(), schemas.getReadOnlySchema())) {
            for(var name : List.of("AttributeInterface", "DefaultInterface", "ChainActionInterface",
                    "ContactMechanismInterface", "GeoCodeInterface", "PaymentMethodInterface",
                    "PeriodInterface", "TermInterface", "UnitAmountInterface", "LineAmountInterface")) {
                var union = assertInstanceOf(GraphQLUnionType.class, schema.getType(name), name);

                assertFalse(union.getTypes().isEmpty(), name);
                assertNotNull(schema.getCodeRegistry().getTypeResolver(union), name);
                for(var member : union.getTypes()) {
                    assertInstanceOf(GraphQLObjectType.class, member, name);
                    assertSame(schema.getObjectType(member.getName()), member);
                }
            }
        }
        assertNull(schemas.getReadOnlySchema().getMutationType());
        assertNotNull(schemas.getSchema().getMutationType());
    }

}
