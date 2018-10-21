// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

package com.echothree.control.user.test.item.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class ItemTest
        extends GraphQlTestCase {

    @Test
    public void itemByItemName()
            throws Exception {
        Map<String, Object> itemBody = executeUsingPost("query { item(itemName: \"minimal\") { itemName id } }");
        
        Map<String, Object> item = getMap(itemBody, "data.item");
        
        Assert.assertNotNull(item);
    }
    
    @Test
    public void itemByAlias()
            throws Exception {
        Map<String, Object> itemBody = executeUsingPost("query { item(itemNameOrAlias: \"test_other\") { itemName id } }");
        
        Map<String, Object> item = getMap(itemBody, "data.item");
        
        Assert.assertNotNull(item);
    }
    @Test
    public void itemByItemNameUsingAlias()
            throws Exception {
        Map<String, Object> itemBody = executeUsingPost("query { item(itemName: \"test_other\") { itemName id } }");
        
        Map<String, Object> item = getMap(itemBody, "data.item");
        
        Assert.assertNull(item);
    }
    
    @Test
    public void itemById()
            throws Exception {
        Map<String, Object> itemBodyByItemName = executeUsingPost("query { item(itemName: \"minimal\") { itemName id } }");
        
        String id = getString(itemBodyByItemName, "data.item.id");
        
        Map<String, Object> itemBodyById = executeUsingPost("query { item(id: \"" + id + "\") { itemName id } }");
        
        Map<String, Object> item = getMap(itemBodyById, "data.item");

        Assert.assertNotNull(item);
    }
    
    
}
