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

package com.echothree.control.user.test.user.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class UserLoginTest
        extends GraphQlTestCase {

    @Test
    public void editUserLoginNoAuth()
            throws Exception {
        Map<String, Object> createCustomerWithLoginBody = executeUsingPost("mutation { createCustomerWithLogin(input: { firstName: \"Unit\", lastName: \"Test\", emailAddress: \"jdoe@echothree.com\", allowSolicitation: \"true\", username: \"UnitTest\", password1: \"password\", password2: \"password\", recoveryQuestionName: \"PET_NAME\", answer: \"bird\", clientMutationId: \"1\" }) { hasErrors id } }");
        Assert.assertFalse(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.hasErrors"));
        
        String id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");

        Map<String, Object> editUserLoginBody1 = executeUsingPost("mutation { editUserLogin(input: { partyId: \"" + id + "\", username: \"UnitTest1\"clientMutationId: \"1\" }) { hasErrors hasSecurityMessages } }");
        Assert.assertTrue(getBoolean(editUserLoginBody1, "data.editUserLogin.hasErrors"));
        Assert.assertTrue(getBoolean(editUserLoginBody1, "data.editUserLogin.hasSecurityMessages"));
        
        Map<String, Object> employeeLoginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> editUserLoginBody2 = executeUsingPost("mutation { editUserLogin(input: { partyId: \"" + id + "\", username: \"UnitTest1\"clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(editUserLoginBody2, "data.editUserLogin.hasErrors"));
        
        Map<String, Object> deleteUserLoginBody = executeUsingPost("mutation { deleteUserLogin(input: { partyId: \"" + id + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.hasErrors"));
    }

    @Test
    public void editUserLogin()
            throws Exception {
        Map<String, Object> createCustomerWithLoginBody = executeUsingPost("mutation { createCustomerWithLogin(input: { firstName: \"Unit\", lastName: \"Test\", emailAddress: \"jdoe@echothree.com\", allowSolicitation: \"true\", username: \"UnitTest\", password1: \"password\", password2: \"password\", recoveryQuestionName: \"PET_NAME\", answer: \"bird\", clientMutationId: \"1\" }) { hasErrors id } }");
        Assert.assertFalse(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.hasErrors"));
        
        String id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");

        Map<String, Object> employeeLoginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> editUserLoginBody = executeUsingPost("mutation { editUserLogin(input: { partyId: \"" + id + "\", username: \"UnitTest1\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(editUserLoginBody, "data.editUserLogin.hasErrors"));
        
        Map<String, Object> deleteUserLoginBody = executeUsingPost("mutation { deleteUserLogin(input: { partyId: \"" + id + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.hasErrors"));
    }

    @Test
    public void deleteUserLoginNoAuth()
            throws Exception {
        Map<String, Object> createCustomerWithLoginBody = executeUsingPost("mutation { createCustomerWithLogin(input: { firstName: \"Unit\", lastName: \"Test\", emailAddress: \"jdoe@echothree.com\", allowSolicitation: \"true\", username: \"UnitTest\", password1: \"password\", password2: \"password\", recoveryQuestionName: \"PET_NAME\", answer: \"bird\", clientMutationId: \"1\" }) { hasErrors id } }");
        Assert.assertFalse(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.hasErrors"));
        
        String id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");
        
        Map<String, Object> deleteUserLoginBody1 = executeUsingPost("mutation { deleteUserLogin(input: { partyId: \"" + id + "\", clientMutationId: \"1\" }) { hasErrors hasSecurityMessages } }");
        Assert.assertTrue(getBoolean(deleteUserLoginBody1, "data.deleteUserLogin.hasErrors"));
        Assert.assertTrue(getBoolean(deleteUserLoginBody1, "data.deleteUserLogin.hasSecurityMessages"));

        Map<String, Object> employeeLoginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> deleteUserLoginBody2 = executeUsingPost("mutation { deleteUserLogin(input: { partyId: \"" + id + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteUserLoginBody2, "data.deleteUserLogin.hasErrors"));
    }

    @Test
    public void deleteUserLogin()
            throws Exception {
        Map<String, Object> createCustomerWithLoginBody = executeUsingPost("mutation { createCustomerWithLogin(input: { firstName: \"Unit\", lastName: \"Test\", emailAddress: \"jdoe@echothree.com\", allowSolicitation: \"true\", username: \"UnitTest\", password1: \"password\", password2: \"password\", recoveryQuestionName: \"PET_NAME\", answer: \"bird\", clientMutationId: \"1\" }) { hasErrors id } }");
        Assert.assertFalse(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.hasErrors"));
        
        String id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");

        Map<String, Object> employeeLoginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> deleteUserLoginBody = executeUsingPost("mutation { deleteUserLogin(input: { partyId: \"" + id + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.hasErrors"));
    }

    @Test
    public void createUserLoginNoAuth()
            throws Exception {
        Map<String, Object> employeeLoginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> createCustomerBody = executeUsingPost("mutation { createCustomer(input: { firstName: \"Unit\", lastName: \"Test\", emailAddress: \"jdoe@echothree.com\", allowSolicitation: \"true\", clientMutationId: \"1\" }) { hasErrors id } }");
        Assert.assertFalse(getBoolean(createCustomerBody, "data.createCustomer.hasErrors"));
        
        String id = getString(createCustomerBody, "data.createCustomer.id");

        Map<String, Object> logoutBody = executeUsingPost("mutation { logout(input: { clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(logoutBody, "data.logout.hasErrors"));

        Map<String, Object> createUserLoginBody = executeUsingPost("mutation { createUserLogin(input: { partyId: \"" + id + "\", username: \"UnitTest\", password1: \"password\", password2: \"password\", recoveryQuestionName: \"PET_NAME\", answer: \"bird\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertTrue(getBoolean(createUserLoginBody, "data.createUserLogin.hasErrors"));
    }

    @Test
    public void createUserLogin()
            throws Exception {
        Map<String, Object> employeeLoginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> createCustomerBody = executeUsingPost("mutation { createCustomer(input: { firstName: \"Unit\", lastName: \"Test\", emailAddress: \"jdoe@echothree.com\", allowSolicitation: \"true\", clientMutationId: \"1\" }) { hasErrors id } }");
        Assert.assertFalse(getBoolean(createCustomerBody, "data.createCustomer.hasErrors"));
        
        String id = getString(createCustomerBody, "data.createCustomer.id");

        Map<String, Object> createUserLoginBody = executeUsingPost("mutation { createUserLogin(input: { partyId: \"" + id + "\", username: \"UnitTest\", password1: \"password\", password2: \"password\", recoveryQuestionName: \"PET_NAME\", answer: \"bird\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(createUserLoginBody, "data.createUserLogin.hasErrors"));

        Map<String, Object> deleteUserLoginBody = executeUsingPost("mutation { deleteUserLogin(input: { partyId: \"" + id + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.hasErrors"));
    }
    
    @Test
    public void userLoginQueryNoAuth()
            throws Exception {
        Map<String, Object> createCustomerWithLoginBody = executeUsingPost("mutation { createCustomerWithLogin(input: { firstName: \"Unit\", lastName: \"Test\", emailAddress: \"jdoe@echothree.com\", allowSolicitation: \"true\", username: \"UnitTest\", password1: \"password\", password2: \"password\", recoveryQuestionName: \"PET_NAME\", answer: \"bird\", clientMutationId: \"1\" }) { hasErrors id } }");
        Assert.assertFalse(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.hasErrors"));
        
        String id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");

        // Verify returned string matches the string passed in with createCustomerWithLogin
        Map<String, Object> userLoginQuery1 = executeUsingPost("query { userLogin(username: \"unittest\") { username } }");
        Assert.assertTrue("UnitTest".equals(getString(userLoginQuery1, "data.userLogin.username")));

        // This should fail, partyId is not a permitted way to look up the userLogin without proper permissions
        Map<String, Object> userLoginQuery2 = executeUsingPost("query { userLogin(partyId: \"" + id + "\") { username } }");
        Assert.assertNull(getObject(userLoginQuery2, "data.userLogin"));

        Map<String, Object> employeeLoginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> deleteUserLoginBody = executeUsingPost("mutation { deleteUserLogin(input: { partyId: \"" + id + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.hasErrors"));
    }
    
    @Test
    public void userLoginQuery()
            throws Exception {
        Map<String, Object> createCustomerWithLoginBody = executeUsingPost("mutation { createCustomerWithLogin(input: { firstName: \"Unit\", lastName: \"Test\", emailAddress: \"jdoe@echothree.com\", allowSolicitation: \"true\", username: \"UnitTest\", password1: \"password\", password2: \"password\", recoveryQuestionName: \"PET_NAME\", answer: \"bird\", clientMutationId: \"1\" }) { hasErrors id } }");
        Assert.assertFalse(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.hasErrors"));
        
        String id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");

        Map<String, Object> employeeLoginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors"));
        
        // Verify returned string matches the string passed in with createCustomerWithLogin
        Map<String, Object> userLoginQuery1 = executeUsingPost("query { userLogin(username: \"UnitTest\") { username } }");
        Assert.assertTrue("UnitTest".equals(getString(userLoginQuery1, "data.userLogin.username")));

        // This should be possible because the employee has authenticated before attempting lookup by partyId.
        Map<String, Object> userLoginQuery2 = executeUsingPost("query { userLogin(partyId: \"" + id + "\") { username } }");
        Assert.assertTrue("UnitTest".equals(getString(userLoginQuery2, "data.userLogin.username")));

        Map<String, Object> deleteUserLoginBody = executeUsingPost("mutation { deleteUserLogin(input: { partyId: \"" + id + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.hasErrors"));
    }
    
}
