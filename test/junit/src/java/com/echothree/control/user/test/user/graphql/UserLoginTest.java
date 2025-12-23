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
                        commandResult {
                            hasErrors
                        }
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.commandResult.hasErrors")).isFalse();
        
        var id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");

        var editUserLoginBody1 = executeUsingPost("""
                mutation {
                    editUserLogin(input: { id: "%s", username: "UnitTest1" clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(editUserLoginBody1, "data.editUserLogin.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(editUserLoginBody1, "data.editUserLogin.commandResult.hasSecurityMessages")).isTrue();
        
        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.commandResult.hasErrors")).isFalse();
        
        var editUserLoginBody2 = executeUsingPost("""
                mutation {
                    editUserLogin(input: { id: "%s", username: "UnitTest1", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(editUserLoginBody2, "data.editUserLogin.commandResult.hasErrors")).isFalse();
        
        var deleteUserLoginBody = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { id: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.commandResult.hasErrors")).isFalse();
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
                        commandResult {
                            hasErrors
                        }
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.commandResult.hasErrors")).isFalse();
        
        var id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");
        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.commandResult.hasErrors")).isFalse();
        
        var editUserLoginBody = executeUsingPost("""
                mutation {
                    editUserLogin(input: { id: "%s", username: "UnitTest1", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(editUserLoginBody, "data.editUserLogin.commandResult.hasErrors")).isFalse();
        
        var deleteUserLoginBody = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { id: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.commandResult.hasErrors")).isFalse();
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
                        commandResult {
                            hasErrors
                        }
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.commandResult.hasErrors")).isFalse();
        
        var id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");
        
        var deleteUserLoginBody1 = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { id: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody1, "data.deleteUserLogin.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(deleteUserLoginBody1, "data.deleteUserLogin.commandResult.hasSecurityMessages")).isTrue();

        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.commandResult.hasErrors")).isFalse();
        
        var deleteUserLoginBody2 = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { id: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody2, "data.deleteUserLogin.commandResult.hasErrors")).isFalse();
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
                        commandResult {
                            hasErrors
                        }
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.commandResult.hasErrors")).isFalse();
        
        var id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");

        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.commandResult.hasErrors")).isFalse();
        
        var deleteUserLoginBody = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { id: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createUserLoginNoAuth()
            throws Exception {
        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.commandResult.hasErrors")).isFalse();
        
        var createCustomerBody = executeUsingPost("""
                mutation {
                    createCustomer(input: { firstName: "Unit", lastName: "Test", emailAddress: "jdoe@echothree.com", allowSolicitation: "true", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerBody, "data.createCustomer.commandResult.hasErrors")).isFalse();
        
        var id = getString(createCustomerBody, "data.createCustomer.id");
        var logoutBody = executeUsingPost("""
                mutation {
                    logout(input: { clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(logoutBody, "data.logout.commandResult.hasErrors")).isFalse();

        var createUserLoginBody = executeUsingPost("""
                mutation {
                    createUserLogin(input: {
                        id: "%s",
                        username: "UnitTest",
                        password1: "password",
                        password2: "password",
                        recoveryQuestionName: "PET_NAME",
                        answer: "bird",
                        clientMutationId: "1"
                    })
                    {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(createUserLoginBody, "data.createUserLogin.commandResult.hasErrors")).isTrue();
    }

    @Test
    public void createUserLogin()
            throws Exception {
        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.commandResult.hasErrors")).isFalse();
        
        var createCustomerBody = executeUsingPost("""
                mutation {
                    createCustomer(input: { firstName: "Unit", lastName: "Test", emailAddress: "jdoe@echothree.com", allowSolicitation: "true", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerBody, "data.createCustomer.commandResult.hasErrors")).isFalse();
        
        var id = getString(createCustomerBody, "data.createCustomer.id");
        var createUserLoginBody = executeUsingPost("""
                mutation {
                    createUserLogin(input: {
                        id: "%s",
                        username: "UnitTest",
                        password1: "password",
                        password2: "password",
                        recoveryQuestionName: "PET_NAME",
                        answer: "bird",
                        clientMutationId: "1"
                    })
                    {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(createUserLoginBody, "data.createUserLogin.commandResult.hasErrors")).isFalse();

        var deleteUserLoginBody = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { id: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.commandResult.hasErrors")).isFalse();
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
                        commandResult {
                            hasErrors
                        }
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.commandResult.hasErrors")).isFalse();
        
        // Verify returned string matches the string passed in with createCustomerWithLogin
        var userLoginQuery1 = executeUsingPost("""
                query {
                    userLogin(username: "unittest") {
                        username
                    }
                }
                """);

        assertThat("UnitTest".equals(getString(userLoginQuery1, "data.userLogin.username"))).isTrue();

        // This should fail, id is not a permitted way to look up the userLogin without proper permissions
        var id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");
        var userLoginQuery2 = executeUsingPost("""
                query {
                    userLogin(id: "%s") {
                        username
                    }
                }
                """.formatted(id));

        assertThat(getObject(userLoginQuery2, "data.userLogin")).isNull();

        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.commandResult.hasErrors")).isFalse();
        
        var deleteUserLoginBody = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { id: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.commandResult.hasErrors")).isFalse();
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
                        commandResult {
                            hasErrors
                        }
                        id
                    }
                }
                """);

        assertThat(getBoolean(createCustomerWithLoginBody, "data.createCustomerWithLogin.commandResult.hasErrors")).isFalse();
        
        var employeeLoginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.commandResult.hasErrors")).isFalse();
        
        // Verify returned string matches the string passed in with createCustomerWithLogin
        var userLoginQuery1 = executeUsingPost("""
                query {
                    userLogin(username: "UnitTest") {
                        username
                    }
                }
                """);

        assertThat("UnitTest".equals(getString(userLoginQuery1, "data.userLogin.username"))).isTrue();

        // This should be possible because the employee has authenticated before attempting lookup by id.
        var id = getString(createCustomerWithLoginBody, "data.createCustomerWithLogin.id");
        var userLoginQuery2 = executeUsingPost("""
                query {
                    userLogin(id: "%s") {
                        username
                    }
                }
                """.formatted(id));

        assertThat("UnitTest".equals(getString(userLoginQuery2, "data.userLogin.username"))).isTrue();

        var deleteUserLoginBody = executeUsingPost("""
                mutation {
                    deleteUserLogin(input: { id: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(deleteUserLoginBody, "data.deleteUserLogin.commandResult.hasErrors")).isFalse();
    }
    
}
