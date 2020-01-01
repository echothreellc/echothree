// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

package com.echothree.control.user.test.inventory.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class InventoryConditionTest
        extends GraphQlTestCase {

    @Test
    public void createInventoryConditionNoAuth()
            throws Exception {
        Map<String, Object> createBody = executeUsingPost("mutation { createInventoryCondition(input: { inventoryConditionName: \"" + ORIGINAL_INVENTORY_CONDITION_NAME + "\", isDefault: \"false\", sortOrder: \"" + ORIGINAL_SORT_ORDER + "\", description: \"" + ORIGINAL_DESCRIPTION + "\", clientMutationId: \"1\" }) { id hasErrors hasSecurityMessages } }");
        Assert.assertTrue(getBoolean(createBody, "data.createInventoryCondition.hasErrors"));
        Assert.assertTrue(getBoolean(createBody, "data.createInventoryCondition.hasSecurityMessages"));
    }

    @Test
    public void deleteInventoryConditionNoAuth()
            throws Exception {
        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteInventoryCondition(input: { inventoryConditionName: \"" + ORIGINAL_INVENTORY_CONDITION_NAME + "\", clientMutationId: \"1\" }) { hasErrors hasSecurityMessages } }");
        Assert.assertTrue(getBoolean(deleteBody, "data.deleteInventoryCondition.hasErrors"));
        Assert.assertTrue(getBoolean(deleteBody, "data.deleteInventoryCondition.hasSecurityMessages"));
    }

    @Test
    public void createInventoryConditionAndDelete()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        Map<String, Object> createBody = executeUsingPost("mutation { createInventoryCondition(input: { inventoryConditionName: \"" + ORIGINAL_INVENTORY_CONDITION_NAME + "\", isDefault: \"false\", sortOrder: \"" + ORIGINAL_SORT_ORDER + "\", description: \"" + ORIGINAL_DESCRIPTION + "\", clientMutationId: \"1\" }) { id hasErrors hasSecurityMessages } }");
        Assert.assertFalse(getBoolean(createBody, "data.createInventoryCondition.hasErrors"));
        
        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteInventoryCondition(input: { inventoryConditionName: \"" + ORIGINAL_INVENTORY_CONDITION_NAME + "\", clientMutationId: \"1\" }) { hasErrors hasSecurityMessages } }");
        Assert.assertFalse(getBoolean(deleteBody, "data.deleteInventoryCondition.hasErrors"));
    }
    
    private static final String ORIGINAL_INVENTORY_CONDITION_NAME = "TEST_NAME";
    private static final long ORIGINAL_SORT_ORDER = 10L;
    private static final String ORIGINAL_DESCRIPTION = "Test Inventory Condition";

    private static final String NEW_INVENTORY_CONDITION_NAME = "TEST_AGAIN_NAME";
    private static final long NEW_SORT_ORDER = 20L;
    private static final String NEW_DESCRIPTION = "Test Again Inventory Condition";
        
    @Test
    public void createInventoryConditionAndQueryInventoryCondition()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        Map<String, Object> createBody = executeUsingPost("mutation { createInventoryCondition(input: { inventoryConditionName: \"" + ORIGINAL_INVENTORY_CONDITION_NAME + "\", isDefault: \"false\", sortOrder: \"" + ORIGINAL_SORT_ORDER + "\", description: \"" + ORIGINAL_DESCRIPTION + "\", clientMutationId: \"1\" }) { id hasErrors hasSecurityMessages } }");
        Assert.assertFalse(getBoolean(createBody, "data.createInventoryCondition.hasErrors"));

        String id = getString(createBody, "data.createInventoryCondition.id");
        
        Map<String, Object> queryBody = executeUsingPost("query { inventoryCondition(id: \"" + id + "\") { id inventoryConditionName isDefault sortOrder description } }");
        Assert.assertEquals(id, getString(queryBody, "data.inventoryCondition.id"));
        Assert.assertEquals(ORIGINAL_INVENTORY_CONDITION_NAME, getString(queryBody, "data.inventoryCondition.inventoryConditionName"));
        Assert.assertEquals(ORIGINAL_SORT_ORDER, getInteger(queryBody, "data.inventoryCondition.sortOrder"));
        Assert.assertEquals(ORIGINAL_DESCRIPTION, getString(queryBody, "data.inventoryCondition.description"));
        
        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteInventoryCondition(input: { inventoryConditionName: \"" + ORIGINAL_INVENTORY_CONDITION_NAME + "\", clientMutationId: \"1\" }) { hasErrors hasSecurityMessages } }");
        Assert.assertFalse(getBoolean(deleteBody, "data.deleteInventoryCondition.hasErrors"));
    }
    
    @Test
    public void createInventoryConditionAndQueryInventoryConditions()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> createBody = executeUsingPost("mutation { createInventoryCondition(input: { inventoryConditionName: \"" + ORIGINAL_INVENTORY_CONDITION_NAME + "\", isDefault: \"false\", sortOrder: \"" + ORIGINAL_SORT_ORDER + "\", description: \"" + ORIGINAL_DESCRIPTION + "\", clientMutationId: \"1\" }) { id hasErrors hasSecurityMessages } }");
        Assert.assertFalse(getBoolean(createBody, "data.createInventoryCondition.hasErrors"));
        
        String id = getString(createBody, "data.createInventoryCondition.id");
        
        Map<String, Object> queryBody = executeUsingPost("query { inventoryConditions { id sortOrder description } }");
        
        List<Map<String, Object>> inventoryConditions = getList(queryBody, "data.inventoryConditions");
        
        boolean foundInventoryCondition = false;
        for(Map<String, Object> inventoryCondition : inventoryConditions) {
            if(id.equals(getString(inventoryCondition, "id"))) {
                Assert.assertEquals(ORIGINAL_SORT_ORDER, getInteger(inventoryCondition, "sortOrder"));
                Assert.assertEquals(ORIGINAL_DESCRIPTION, getString(inventoryCondition, "description"));
                foundInventoryCondition = true;
            }
        }
        Assert.assertTrue(foundInventoryCondition);
        
        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteInventoryCondition(input: { inventoryConditionName: \"" + ORIGINAL_INVENTORY_CONDITION_NAME + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteBody, "data.deleteInventoryCondition.hasErrors"));
    }
    
    @Test
    public void createInventoryConditionAndEditById()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> createBody = executeUsingPost("mutation { createInventoryCondition(input: { inventoryConditionName: \"" + ORIGINAL_INVENTORY_CONDITION_NAME + "\", isDefault: \"false\", sortOrder: \"" + ORIGINAL_SORT_ORDER + "\", description: \"" + ORIGINAL_DESCRIPTION + "\", clientMutationId: \"1\" }) { id hasErrors hasSecurityMessages } }");
        Assert.assertFalse(getBoolean(createBody, "data.createInventoryCondition.hasErrors"));
        
        String id = getString(createBody, "data.createInventoryCondition.id");
        
        Map<String, Object> editBody = executeUsingPost("mutation { editInventoryCondition(input: { id: \"" + id + "\", inventoryConditionName: \"" + NEW_INVENTORY_CONDITION_NAME + "\", sortOrder: \"" + NEW_SORT_ORDER + "\", description: \"" + NEW_DESCRIPTION + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(editBody, "data.editInventoryCondition.hasErrors"));
        
        Map<String, Object> queryBody = executeUsingPost("query { inventoryCondition(id: \"" + id + "\") { id inventoryConditionName, sortOrder description } }");
        Assert.assertEquals(id, getString(queryBody, "data.inventoryCondition.id"));
        Assert.assertEquals(NEW_INVENTORY_CONDITION_NAME, getString(queryBody, "data.inventoryCondition.inventoryConditionName"));
        Assert.assertEquals(NEW_SORT_ORDER, getInteger(queryBody, "data.inventoryCondition.sortOrder"));
        Assert.assertEquals(NEW_DESCRIPTION, getString(queryBody, "data.inventoryCondition.description"));
        
        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteInventoryCondition(input: { inventoryConditionName: \"" + NEW_INVENTORY_CONDITION_NAME + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteBody, "data.deleteInventoryCondition.hasErrors"));
    }

    @Test
    public void createInventoryConditionAndEditByName()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        Map<String, Object> createBody = executeUsingPost("mutation { createInventoryCondition(input: { inventoryConditionName: \"" + ORIGINAL_INVENTORY_CONDITION_NAME + "\", isDefault: \"false\", sortOrder: \"" + ORIGINAL_SORT_ORDER + "\", description: \"" + ORIGINAL_DESCRIPTION + "\", clientMutationId: \"1\" }) { id hasErrors hasSecurityMessages } }");
        Assert.assertFalse(getBoolean(createBody, "data.createInventoryCondition.hasErrors"));

        String id = getString(createBody, "data.createInventoryCondition.id");

        Map<String, Object> editBody = executeUsingPost("mutation { editInventoryCondition(input: { originalInventoryConditionName: \"" + ORIGINAL_INVENTORY_CONDITION_NAME + "\", inventoryConditionName: \"" + NEW_INVENTORY_CONDITION_NAME + "\", sortOrder: \"" + NEW_SORT_ORDER + "\", description: \"" + NEW_DESCRIPTION + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(editBody, "data.editInventoryCondition.hasErrors"));

        Map<String, Object> queryBody = executeUsingPost("query { inventoryCondition(id: \"" + id + "\") { id inventoryConditionName, sortOrder description } }");
        Assert.assertEquals(id, getString(queryBody, "data.inventoryCondition.id"));
        Assert.assertEquals(NEW_INVENTORY_CONDITION_NAME, getString(queryBody, "data.inventoryCondition.inventoryConditionName"));
        Assert.assertEquals(NEW_SORT_ORDER, getInteger(queryBody, "data.inventoryCondition.sortOrder"));
        Assert.assertEquals(NEW_DESCRIPTION, getString(queryBody, "data.inventoryCondition.description"));

        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteInventoryCondition(input: { inventoryConditionName: \"" + NEW_INVENTORY_CONDITION_NAME + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteBody, "data.deleteInventoryCondition.hasErrors"));
    }
}
