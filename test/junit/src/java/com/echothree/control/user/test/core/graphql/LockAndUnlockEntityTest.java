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
package com.echothree.control.user.test.core.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.Test;

public class LockAndUnlockEntityTest
        extends GraphQlTestCase {

    @Test
    public void lockAndUnlockEntity()
            throws Exception {
        var loginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);
        
        assertThat(getBoolean(loginBody, "data.employeeLogin.commandResult.hasErrors")).isFalse();

        var itemCategoryBody = executeUsingPost("""
                query {
                    itemCategory(itemCategoryName: "default") {
                        id
                    }
                }
                """);
        
        var id = getString(itemCategoryBody, "data.itemCategory.id");

        var lockEntityBody = executeUsingPost("""
                mutation {
                    lockEntity(input: { id: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));

        assertThat(getBoolean(lockEntityBody, "data.lockEntity.commandResult.hasErrors")).isFalse();

        var unlockEntityBody = executeUsingPost("""
                mutation {
                    unlockEntity(input: { id: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));
        
        assertThat(getBoolean(unlockEntityBody, "data.unlockEntity.commandResult.hasErrors")).isFalse();
    }
    
    @Test
    public void lockEntityWithoutLogin()
            throws Exception {
        var loginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);
        
        assertThat(getBoolean(loginBody, "data.employeeLogin.commandResult.hasErrors")).isFalse();

        var itemCategoryBody = executeUsingPost("""
                query {
                    itemCategory(itemCategoryName: "default") {
                        id
                    }
                }
                """);
        
        var id = getString(itemCategoryBody, "data.itemCategory.id");
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

        var lockEntityBody = executeUsingPost("""
                mutation {
                    lockEntity(input: { id: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));
        
        assertThat(getBoolean(lockEntityBody, "data.lockEntity.commandResult.hasErrors")).isTrue();
    }
    
    @Test
    public void unlockEntityWithoutLogin()
            throws Exception {
        var loginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);
        
        assertThat(getBoolean(loginBody, "data.employeeLogin.commandResult.hasErrors")).isFalse();

        var itemCategoryBody = executeUsingPost("""
                query {
                    itemCategory(itemCategoryName: "default") {
                        id
                    }
                }
                """);
        
        var id = getString(itemCategoryBody, "data.itemCategory.id");
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

        var unlockEntityBody = executeUsingPost("""
                mutation {
                    unlockEntity(input: { id: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));
        
        assertThat(getBoolean(unlockEntityBody, "data.unlockEntity.commandResult.hasErrors")).isTrue();
    }
    
    @Test
    public void lockNonexistantEntity()
            throws Exception {
        var loginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);
        
        assertThat(getBoolean(loginBody, "data.employeeLogin.commandResult.hasErrors")).isFalse();

        var lockEntityBody = executeUsingPost("""
                mutation {
                    lockEntity(input: { id: "IDONOTEXIST", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);
        
        assertThat(getBoolean(lockEntityBody, "data.lockEntity.commandResult.hasErrors")).isTrue();
    }
    
    @Test
    public void unlockNonexistantEntity()
            throws Exception {
        var loginBody = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);
        
        assertThat(getBoolean(loginBody, "data.employeeLogin.commandResult.hasErrors")).isFalse();

        var lockEntityBody = executeUsingPost("""
                mutation {
                    unlockEntity(input: { id: "IDONOTEXIST", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);
        
        assertThat(getBoolean(lockEntityBody, "data.unlockEntity.commandResult.hasErrors")).isTrue();
    }
    
}
