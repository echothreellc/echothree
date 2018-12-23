// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

public class ItemCategoryTest
        extends GraphQlTestCase {

    @Test
    public void createItemCategoryNoAuth()
            throws Exception {
        Map<String, Object> createBody = executeUsingPost("mutation { createItemCategory(input: { itemCategoryName: \"unit_test\", isDefault: \"false\", sortOrder: \"1\", description: \"Test Item Category\", clientMutationId: \"1\" }) { hasErrors hasSecurityMessages } }");
        Assert.assertTrue(getBoolean(createBody, "data.createItemCategory.hasErrors"));
        Assert.assertTrue(getBoolean(createBody, "data.createItemCategory.hasSecurityMessages"));
    }

    @Test
    public void deleteItemCategoryNoAuth()
            throws Exception {
        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteItemCategory(input: { itemCategoryName: \"unit_test\", clientMutationId: \"1\" }) { hasErrors hasSecurityMessages } }");
        Assert.assertTrue(getBoolean(deleteBody, "data.deleteItemCategory.hasErrors"));
        Assert.assertTrue(getBoolean(deleteBody, "data.deleteItemCategory.hasSecurityMessages"));
    }

    @Test
    public void createItemCategoryAndDeleteById()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> createBody = executeUsingPost("mutation { createItemCategory(input: { itemCategoryName: \"unit_test\", isDefault: \"false\", sortOrder: \"1\", description: \"Test Item Category\", clientMutationId: \"1\" }) { hasErrors id } }");
        Assert.assertFalse(getBoolean(createBody, "data.createItemCategory.hasErrors"));
        
        String id = getString(createBody, "data.createItemCategory.id");
        
        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteItemCategory(input: { id: \"" + id + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteBody, "data.deleteItemCategory.hasErrors"));
    }

    @Test
    public void createItemCategoryAndDeleteByName()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> createBody = executeUsingPost("mutation { createItemCategory(input: { itemCategoryName: \"unit_test\", isDefault: \"false\", sortOrder: \"1\", description: \"Test Item Category\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(createBody, "data.createItemCategory.hasErrors"));
        
        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteItemCategory(input: { itemCategoryName: \"unit_test\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteBody, "data.deleteItemCategory.hasErrors"));
    }
    
    @Test
    public void editItemCategoryById()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> createBody = executeUsingPost("mutation { createItemCategory(input: { itemCategoryName: \"unit_test1\", isDefault: \"false\", sortOrder: \"1\", description: \"Test Item Category 1\", clientMutationId: \"1\" }) { hasErrors id } }");
        Assert.assertFalse(getBoolean(createBody, "data.createItemCategory.hasErrors"));
        
        String createId = getString(createBody, "data.createItemCategory.id");
        
        Map<String, Object> editBody = executeUsingPost("mutation { editItemCategory(input: { id: \"" + createId + "\", itemCategoryName: \"unit_test2\", description: \"Test Item Category 2\", clientMutationId: \"1\" }) { hasErrors id } }");
        Assert.assertFalse(getBoolean(editBody, "data.editItemCategory.hasErrors"));
        Assert.assertEquals(createId, getString(editBody, "data.editItemCategory.id"));

        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteItemCategory(input: { id: \"" + createId + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteBody, "data.deleteItemCategory.hasErrors"));
    }

    @Test
    public void editItemCategoryByName()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> createBody = executeUsingPost("mutation { createItemCategory(input: { itemCategoryName: \"unit_test1\", isDefault: \"false\", sortOrder: \"1\", description: \"Test Item Category 1\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(createBody, "data.createItemCategory.hasErrors"));
        
        Map<String, Object> editBody = executeUsingPost("mutation { editItemCategory(input: { originalItemCategoryName: \"unit_test1\", itemCategoryName: \"unit_test2\", description: \"Test Item Category 2\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(editBody, "data.editItemCategory.hasErrors"));
        
        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteItemCategory(input: { itemCategoryName: \"unit_test2\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteBody, "data.deleteItemCategory.hasErrors"));
    }
    
}
