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
package com.echothree.control.user.test.core.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class LockAndUnlockEntityTest
        extends GraphQlTestCase {

    @Test
    public void lockAndUnlockEntity()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        Map<String, Object> itemCategoryBody = executeUsingPost("query { itemCategory(itemCategoryName: \"default\") { id } }");
        String id = getString(itemCategoryBody, "data.itemCategory.id");

        Map<String, Object> lockEntityBody = executeUsingPost("mutation { lockEntity(input: { id: \"" + id + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(lockEntityBody, "data.lockEntity.hasErrors"));

        Map<String, Object> unlockEntityBody = executeUsingPost("mutation { unlockEntity(input: { id: \"" + id + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(unlockEntityBody, "data.unlockEntity.hasErrors"));
    }
    
    @Test
    public void lockEntityWithoutLogin()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        Map<String, Object> itemCategoryBody = executeUsingPost("query { itemCategory(itemCategoryName: \"default\") { id } }");
        String id = getString(itemCategoryBody, "data.itemCategory.id");

        Map<String, Object> logoutBody = executeUsingPost("mutation { logout(input: { clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(logoutBody, "data.logout.hasErrors"));

        Map<String, Object> lockEntityBody = executeUsingPost("mutation { lockEntity(input: { id: \"" + id + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertTrue(getBoolean(lockEntityBody, "data.lockEntity.hasErrors"));
    }
    
    @Test
    public void unlockEntityWithoutLogin()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        Map<String, Object> itemCategoryBody = executeUsingPost("query { itemCategory(itemCategoryName: \"default\") { id } }");
        String id = getString(itemCategoryBody, "data.itemCategory.id");

        Map<String, Object> logoutBody = executeUsingPost("mutation { logout(input: { clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(logoutBody, "data.logout.hasErrors"));

        Map<String, Object> unlockEntityBody = executeUsingPost("mutation { unlockEntity(input: { id: \"" + id + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertTrue(getBoolean(unlockEntityBody, "data.unlockEntity.hasErrors"));
    }
    
    @Test
    public void lockNonexistantEntity()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        Map<String, Object> lockEntityBody = executeUsingPost("mutation { lockEntity(input: { id: \"IDONOTEXIST\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertTrue(getBoolean(lockEntityBody, "data.lockEntity.hasErrors"));
    }
    
    @Test
    public void unlockNonexistantEntity()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        Map<String, Object> lockEntityBody = executeUsingPost("mutation { unlockEntity(input: { id: \"IDONOTEXIST\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertTrue(getBoolean(lockEntityBody, "data.unlockEntity.hasErrors"));
    }
    
}
