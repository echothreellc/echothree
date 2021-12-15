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

package com.echothree.control.user.test.user.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.Test;

public class UserLoginTest
        extends GraphQlTestCase {

    @Test
    public void editUserLoginNoAuth()
            throws Exception {
        var createCustomerWithLoginBody = executeUsingPost("""
                mutation {
                    createCustomerWithLogin(input: {
                        firstName: "Unit", lastName: "Test",
                        emailAddress: "jdoe@echothree.com",
                        allowSolicitation: "true",
                        username: "UnitTest",
                        password1: "password",
                        password2: "password",
                        recoveryQuestionName: "PET_NAME",
                        answer: "bird",
                        clientMutationId: "1"
                    })
                    {
                        hasErrors
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.hasErrors")).isFalse();
        
        var id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");

        var editUserLoginBody1 = executeUsingPost("""
                mutation {
                    editUserLogin(input: { partyId: %s, username: "UnitTest1" clientMutationId: "1" }) {
                        hasErrors
                        hasSecurityMessages
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(editUserLoginBody1, "data.editUserLogin.hasErrors")).isTrue();
        assertThat(getBoolean(editUserLoginBody1, "data.editUserLogin.hasSecurityMessages")).isTrue();
        
        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors")).isFalse();
        
        var editUserLoginBody2 = executeUsingPost("""
                mutation {
                    editUserLogin(input: { partyId: %s, username: "UnitTest1"clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(editUserLoginBody2, "data.editUserLogin.hasErrors")).isFalse();
        
        var deleteUserLoginBody = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { partyId: %s, clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.hasErrors")).isFalse();
    }

    @Test
    public void editUserLogin()
            throws Exception {
        var createCustomerWithLoginBody = executeUsingPost("""
                mutation {
                    createCustomerWithLogin(input: {
                        firstName: "Unit",
                        lastName: "Test",
                        emailAddress: "jdoe@echothree.com",
                        allowSolicitation: "true",
                        username: "UnitTest",
                        password1: "password",
                        password2: "password",
                        recoveryQuestionName: "PET_NAME",
                        answer: "bird",
                        clientMutationId: "1"
                    })
                    {
                        hasErrors
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.hasErrors")).isFalse();
        
        var id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");
        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors")).isFalse();
        
        var editUserLoginBody = executeUsingPost("""
                mutation {
                    editUserLogin(input: { partyId: %s, username: "UnitTest1", clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(editUserLoginBody, "data.editUserLogin.hasErrors")).isFalse();
        
        var deleteUserLoginBody = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { partyId: %s, clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.hasErrors")).isFalse();
    }

    @Test
    public void deleteUserLoginNoAuth()
            throws Exception {
        var createCustomerWithLoginBody = executeUsingPost("""
                mutation {
                    createCustomerWithLogin(input: {
                        firstName: "Unit",
                        lastName: "Test",
                        emailAddress: "jdoe@echothree.com",
                        allowSolicitation: "true",
                        username: "UnitTest",
                        password1: "password",
                        password2: "password",
                        recoveryQuestionName: "PET_NAME",
                        answer: "bird",
                        clientMutationId: "1"
                    })
                    {
                        hasErrors
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.hasErrors")).isFalse();
        
        var id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");
        
        var deleteUserLoginBody1 = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { partyId: %s, clientMutationId: "1" }) {
                        hasErrors
                        hasSecurityMessages
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody1, "data.deleteUserLogin.hasErrors")).isTrue();
        assertThat(getBoolean(deleteUserLoginBody1, "data.deleteUserLogin.hasSecurityMessages")).isTrue();

        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors")).isFalse();
        
        var deleteUserLoginBody2 = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { partyId: %s, clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody2, "data.deleteUserLogin.hasErrors")).isFalse();
    }

    @Test
    public void deleteUserLogin()
            throws Exception {
        var createCustomerWithLoginBody = executeUsingPost("""
                mutation {
                    createCustomerWithLogin(input: {
                        firstName: "Unit",
                        lastName: "Test",
                        emailAddress: "jdoe@echothree.com",
                        allowSolicitation: "true",
                        username: "UnitTest",
                        password1: "password",
                        password2: "password",
                        recoveryQuestionName: "PET_NAME",
                        answer: "bird",
                        clientMutationId: "1"
                    })
                    {
                        hasErrors
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.hasErrors")).isFalse();
        
        var id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");

        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors")).isFalse();
        
        var deleteUserLoginBody = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { partyId: %s, clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.hasErrors")).isFalse();
    }

    @Test
    public void createUserLoginNoAuth()
            throws Exception {
        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors")).isFalse();
        
        var createCustomerBody = executeUsingPost("""
                mutation {
                    createCustomer(input: { firstName: "Unit", lastName: "Test", emailAddress: "jdoe@echothree.com", allowSolicitation: "true", clientMutationId: "1" }) {
                        hasErrors
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerBody, "data.createCustomer.hasErrors")).isFalse();
        
        var id = getString(createCustomerBody, "data.createCustomer.id");
        var logoutBody = executeUsingPost("""
                mutation {
                    logout(input: { clientMutationId: "1" }) {
                        hasErrors 
                    }
                }
                """);

        assertThat(getBoolean(logoutBody, "data.logout.hasErrors")).isFalse();

        var createUserLoginBody = executeUsingPost("""
                mutation {
                    createUserLogin(input: {
                        partyId: %s,
                        username: "UnitTest",
                        password1: "password",
                        password2: "password",
                        recoveryQuestionName: "PET_NAME",
                        answer: "bird",
                        clientMutationId: "1"
                    })
                    {
                        hasErrors
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(createUserLoginBody, "data.createUserLogin.hasErrors")).isTrue();
    }

    @Test
    public void createUserLogin()
            throws Exception {
        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors")).isFalse();
        
        var createCustomerBody = executeUsingPost("""
                mutation {
                    createCustomer(input: { firstName: "Unit", lastName: "Test", emailAddress: "jdoe@echothree.com", allowSolicitation: "true", clientMutationId: "1" }) {
                        hasErrors
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerBody, "data.createCustomer.hasErrors")).isFalse();
        
        var id = getString(createCustomerBody, "data.createCustomer.id");
        var createUserLoginBody = executeUsingPost("""
                mutation {
                    createUserLogin(input: {
                        partyId: %s,
                        username: "UnitTest",
                        password1: "password",
                        password2: "password",
                        recoveryQuestionName: "PET_NAME",
                        answer: "bird",
                        clientMutationId: "1"
                    })
                    {
                        hasErrors
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(createUserLoginBody, "data.createUserLogin.hasErrors")).isFalse();

        var deleteUserLoginBody = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { partyId: %s, clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.hasErrors")).isFalse();
    }
    
    @Test
    public void userLoginQueryNoAuth()
            throws Exception {
        var createCustomerWithLoginBody = executeUsingPost("""
                mutation {
                    createCustomerWithLogin(input: {
                        firstName: "Unit",
                        lastName: "Test",
                        emailAddress: "jdoe@echothree.com",
                        allowSolicitation: "true",
                        username: "UnitTest",
                        password1: "password",
                        password2: "password",
                        recoveryQuestionName: "PET_NAME",
                        answer: "bird", clientMutationId: "1"
                    })
                    {
                        hasErrors
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.hasErrors")).isFalse();
        
        // Verify returned string matches the string passed in with createCustomerWithLogin
        var userLoginQuery1 = executeUsingPost("""
                query {
                    userLogin(username: "unittest") {
                        username
                    }
                }
                """);

        assertThat("UnitTest".equals(getString(userLoginQuery1, "data.userLogin.username"))).isTrue();

        // This should fail, partyId is not a permitted way to look up the userLogin without proper permissions
        var id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");
        var userLoginQuery2 = executeUsingPost("""
                query {
                    userLogin(partyId: %s) {
                        username
                    }
                }
                """.formatted(id));

        assertThat(getObject(userLoginQuery2, "data.userLogin")).isNull();

        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors")).isFalse();
        
        var deleteUserLoginBody = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { partyId: %s, clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.hasErrors")).isFalse();
    }
    
    @Test
    public void userLoginQuery()
            throws Exception {
        var createCustomerWithLoginBody = executeUsingPost("""
                mutation {
                    createCustomerWithLogin(input: {
                        firstName: "Unit",
                        lastName: "Test",
                        emailAddress: "jdoe@echothree.com",
                        allowSolicitation: "true",
                        username: "UnitTest",
                        password1: "password",
                        password2: "password",
                        recoveryQuestionName: "PET_NAME",
                        answer: "bird",
                        clientMutationId: "1"
                    })
                    {
                        hasErrors
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.hasErrors")).isFalse();
        
        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.hasErrors")).isFalse();
        
        // Verify returned string matches the string passed in with createCustomerWithLogin
        var userLoginQuery1 = executeUsingPost("""
                query {
                    userLogin(username: "UnitTest") {
                        username
                    }
                }
                """);

        assertThat("UnitTest".equals(getString(userLoginQuery1, "data.userLogin.username"))).isTrue();

        // This should be possible because the employee has authenticated before attempting lookup by partyId.
        var id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");
        var userLoginQuery2 = executeUsingPost("""
                query {
                    userLogin(partyId: %s) {
                        username
                    }
                }
                """.formatted(id));

        assertThat("UnitTest".equals(getString(userLoginQuery2, "data.userLogin.username"))).isTrue();

        var deleteUserLoginBody = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { partyId: %s, clientMutationId: "1" }) {
                        hasErrors
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.hasErrors")).isFalse();
    }
    
}
