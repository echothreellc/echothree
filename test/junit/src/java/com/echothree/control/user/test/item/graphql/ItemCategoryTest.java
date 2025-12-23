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

package com.echothree.control.user.test.item.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.Test;

public class ItemCategoryTest
        extends GraphQlTestCase {

    @Test
    public void createItemCategoryNoAuth()
            throws Exception {
        var createBody = executeUsingPost("""
                mutation {
                    createItemCategory(input: { itemCategoryName: "unit_test", isDefault: "false", sortOrder: "1", description: "Test Item Category", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """);
        
        assertThat(getBoolean(createBody, "data.createItemCategory.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(createBody, "data.createItemCategory.commandResult.hasSecurityMessages")).isTrue();
    }

    @Test
    public void deleteItemCategoryNoAuth()
            throws Exception {
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteItemCategory(input: { itemCategoryName: "unit_test", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """);
        
        assertThat(getBoolean(deleteBody, "data.deleteItemCategory.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(deleteBody, "data.deleteItemCategory.commandResult.hasSecurityMessages")).isTrue();
    }

    @Test
    public void createItemCategoryAndDeleteById()
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
        
        var createBody = executeUsingPost("""
                mutation {
                    createItemCategory(input: { itemCategoryName: "unit_test", isDefault: "false", sortOrder: "1", description: "Test Item Category", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                        id
                    }
                }
                """);
        
        assertThat(getBoolean(createBody, "data.createItemCategory.commandResult.hasErrors")).isFalse();
        
        var id = getString(createBody, "data.createItemCategory.id");
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteItemCategory(input: { id: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id));
        
        assertThat(getBoolean(deleteBody, "data.deleteItemCategory.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createItemCategoryAndDeleteByName()
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
        
        var createBody = executeUsingPost("""
                mutation {
                    createItemCategory(input: { itemCategoryName: "unit_test", isDefault: "false", sortOrder: "1", description: "Test Item Category", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);
        
        assertThat(getBoolean(createBody, "data.createItemCategory.commandResult.hasErrors")).isFalse();
        
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteItemCategory(input: { itemCategoryName: "unit_test", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);
        
        assertThat(getBoolean(deleteBody, "data.deleteItemCategory.commandResult.hasErrors")).isFalse();
    }
    
    @Test
    public void editItemCategoryById()
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
        
        var createBody = executeUsingPost("""
                mutation {
                    createItemCategory(input: { itemCategoryName: "unit_test1", isDefault: "false", sortOrder: "1", description: "Test Item Category 1", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                        id
                    }
                }
                """);
        
        assertThat(getBoolean(createBody, "data.createItemCategory.commandResult.hasErrors")).isFalse();
        
        var createId = getString(createBody, "data.createItemCategory.id");
        var editBody = executeUsingPost("""
                mutation {
                    editItemCategory(input: { id: "%s", itemCategoryName: "unit_test2", description: "Test Item Category 2", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                        id
                    }
                }
                """.formatted(createId));
        
        assertThat(getBoolean(editBody, "data.editItemCategory.commandResult.hasErrors")).isFalse();
        assertThat(createId).isEqualTo(getString(editBody, "data.editItemCategory.id"));

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteItemCategory(input: { id: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(createId));
        
        assertThat(getBoolean(deleteBody, "data.deleteItemCategory.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void editItemCategoryByName()
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
        
        var createBody = executeUsingPost("""
                mutation {
                    createItemCategory(input: { itemCategoryName: "unit_test1", isDefault: "false", sortOrder: "1", description: "Test Item Category 1", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);
        
        assertThat(getBoolean(createBody, "data.createItemCategory.commandResult.hasErrors")).isFalse();
        
        var editBody = executeUsingPost("""
                mutation {
                    editItemCategory(input: { originalItemCategoryName: "unit_test1", itemCategoryName: "unit_test2", description: "Test Item Category 2", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);
        
        assertThat(getBoolean(editBody, "data.editItemCategory.commandResult.hasErrors")).isFalse();
        
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteItemCategory(input: { itemCategoryName: "unit_test2", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);
        
        assertThat(getBoolean(deleteBody, "data.deleteItemCategory.commandResult.hasErrors")).isFalse();
    }
    
}
