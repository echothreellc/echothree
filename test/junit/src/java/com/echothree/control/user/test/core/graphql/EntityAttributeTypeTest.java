// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

package com.echothree.control.user.test.core.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class EntityAttributeTypeTest
        extends GraphQlTestCase {
    
    @Test
    public void entityAttributeTypes()
            throws Exception {
        var entityAttributeTypesBody = executeUsingPost("""
                query {
                    entityAttributeTypes {
                        entityAttributeTypeName
                        description
                        id
                    }
                }
                """);
        
        var entityAttributeTypes = getList(entityAttributeTypesBody, "data.entityAttributeTypes");

        assertThat(entityAttributeTypes).isNotEmpty();
    }
    
    @Test
    public void entityAttributeType()
            throws Exception {
        var entityAttributeTypesBody = executeUsingPost("""
                query {
                    entityAttributeTypes {
                        entityAttributeTypeName
                        description
                        id
                    }
                }
                """);
                
        var entityAttributeTypes = getList(entityAttributeTypesBody, "data.entityAttributeTypes");

        assertThat(entityAttributeTypes).isNotEmpty();
        
        var first = entityAttributeTypes.get(0);
        var entityAttributeTypeName = getString(first, "entityAttributeTypeName");
        var description = getString(first, "description");
        var id = getString(first, "id");
        
        var entityAttributeType = executeUsingPost("""
                query {
                    entityAttributeType(entityAttributeTypeName: "%s") {
                        description,
                        id
                    }
                }
                """.formatted(entityAttributeTypeName));

        assertThat(getString(entityAttributeType, "data.entityAttributeType.description")).isEqualTo(description);
        assertThat(getString(entityAttributeType, "data.entityAttributeType.id")).isEqualTo(id);
    }
    
}
