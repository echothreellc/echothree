// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class EntityAttributeTypeTest
        extends GraphQlTestCase {
    
    @Test
    public void entityAttributeTypes()
            throws Exception {
        var entityAttributeTypes = executeUsingPost("query { entityAttributeTypes { entityAttributeTypeName description id } }");
        
        List<Map<String, Object>> list = getList(entityAttributeTypes, "data.entityAttributeTypes");

        Assert.assertFalse(list.isEmpty());
    }
    
    @Test
    public void entityAttributeType()
            throws Exception {
        var entityAttributeTypes = executeUsingPost("query { entityAttributeTypes { entityAttributeTypeName description id } }");
        
        List<Map<String, Object>> list = getList(entityAttributeTypes, "data.entityAttributeTypes");
        
        Assert.assertFalse(list.isEmpty());
        
        var first = list.get(0);
        String entityAttributeTypeName = getString(first, "entityAttributeTypeName");
        String description = getString(first, "description");
        var id = getString(first, "id");
        
        var entityAttributeType = executeUsingPost("query { entityAttributeType(entityAttributeTypeName: \"" + entityAttributeTypeName + "\") { description, id } }");
        
        Assert.assertEquals(description, getString(entityAttributeType, "data.entityAttributeType.description"));
        Assert.assertEquals(id, getString(entityAttributeType, "data.entityAttributeType.id"));
    }
    
}
