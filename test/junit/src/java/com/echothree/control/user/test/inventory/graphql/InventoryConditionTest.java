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

package com.echothree.control.user.test.inventory.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.junit.Test;

public class InventoryConditionTest
        extends GraphQlTestCase {

    private static final String ORIGINAL_INVENTORY_CONDITION_NAME = "TEST_NAME";
    private static final long ORIGINAL_SORT_ORDER = 10L;
    private static final String ORIGINAL_DESCRIPTION = "Test Inventory Condition";

    private static final String NEW_INVENTORY_CONDITION_NAME = "TEST_AGAIN_NAME";
    private static final long NEW_SORT_ORDER = 20L;
    private static final String NEW_DESCRIPTION = "Test Again Inventory Condition";

    @Test
    public void createInventoryConditionNoAuth()
            throws Exception {
        var createBody = executeUsingPost("""
                mutation {
                    createInventoryCondition(input: { inventoryConditionName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createInventoryCondition.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(createBody, "data.createInventoryCondition.commandResult.hasSecurityMessages")).isTrue();
    }

    @Test
    public void deleteInventoryConditionNoAuth()
            throws Exception {
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteInventoryCondition(input: { inventoryConditionName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteInventoryCondition.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(deleteBody, "data.deleteInventoryCondition.commandResult.hasSecurityMessages")).isTrue();
    }

    @Test
    public void createInventoryConditionAndDelete()
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
                    createInventoryCondition(input: { inventoryConditionName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createInventoryCondition.commandResult.hasErrors")).isFalse();
        
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteInventoryCondition(input: { inventoryConditionName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteInventoryCondition.commandResult.hasErrors")).isFalse();
    }
    
    @Test
    public void createInventoryConditionAndQueryInventoryCondition()
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
                    createInventoryCondition(input: { inventoryConditionName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createInventoryCondition.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createInventoryCondition.id");
        
        var queryBody = executeUsingPost("""
                query {
                    inventoryCondition(id: "%s") {
                        id
                        inventoryConditionName
                        isDefault
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.inventoryCondition.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.inventoryCondition.inventoryConditionName")).isEqualTo(ORIGINAL_INVENTORY_CONDITION_NAME);
        assertThat(getInteger(queryBody, "data.inventoryCondition.sortOrder")).isEqualTo(ORIGINAL_SORT_ORDER);
        assertThat(getString(queryBody, "data.inventoryCondition.description")).isEqualTo(ORIGINAL_DESCRIPTION);
        
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteInventoryCondition(input: { inventoryConditionName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteInventoryCondition.commandResult.hasErrors")).isFalse();
    }
    
    @Test
    public void createInventoryConditionAndQueryInventoryConditions()
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
                    createInventoryCondition(input: { inventoryConditionName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createInventoryCondition.commandResult.hasErrors")).isFalse();
        
        var id = getString(createBody, "data.createInventoryCondition.id");
        var queryBody = executeUsingPost("""
                query {
                    inventoryConditions {
                        edges {
                            node {
                                id
                                sortOrder
                                description
                            }
                        }
                    }
                }
                """);
        
        var inventoryConditionEdges = getList(queryBody, "data.inventoryConditions.edges");
        
        var foundInventoryCondition = false;
        for(var inventoryConditionEdge : inventoryConditionEdges) {
            var inventoryConditionNode = getMap(inventoryConditionEdge, "node");

            if(id.equals(getString(inventoryConditionNode, "id"))) {
                assertThat(getInteger(inventoryConditionNode, "sortOrder")).isEqualTo(ORIGINAL_SORT_ORDER);
                assertThat(getString(inventoryConditionNode, "description")).isEqualTo(ORIGINAL_DESCRIPTION);
                foundInventoryCondition = true;
            }
        }
        assertThat(foundInventoryCondition).isTrue();
        
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteInventoryCondition(input: { inventoryConditionName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteInventoryCondition.commandResult.hasErrors")).isFalse();
    }
    
    @Test
    public void createInventoryConditionAndEditById()
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
                    createInventoryCondition(input: { inventoryConditionName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createInventoryCondition.commandResult.hasErrors")).isFalse();
        
        var id = getString(createBody, "data.createInventoryCondition.id");
        var editBody = executeUsingPost("""
                mutation {
                    editInventoryCondition(input: { id: "%s", inventoryConditionName: "%s", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id, NEW_INVENTORY_CONDITION_NAME, NEW_SORT_ORDER, NEW_DESCRIPTION));

        assertThat(getBoolean(editBody, "data.editInventoryCondition.commandResult.hasErrors")).isFalse();
        
        var queryBody = executeUsingPost("""
                query {
                    inventoryCondition(id: "%s") {
                        id
                        inventoryConditionName
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.inventoryCondition.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.inventoryCondition.inventoryConditionName")).isEqualTo(NEW_INVENTORY_CONDITION_NAME);
        assertThat(getInteger(queryBody, "data.inventoryCondition.sortOrder")).isEqualTo(NEW_SORT_ORDER);
        assertThat(getString(queryBody, "data.inventoryCondition.description")).isEqualTo(NEW_DESCRIPTION);
        
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteInventoryCondition(input: { inventoryConditionName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(NEW_INVENTORY_CONDITION_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteInventoryCondition.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createInventoryConditionAndEditByName()
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
                    createInventoryCondition(input: { inventoryConditionName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createInventoryCondition.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createInventoryCondition.id");
        var editBody = executeUsingPost("""
                mutation {
                    editInventoryCondition(input: {
                        originalInventoryConditionName: "%s",
                        inventoryConditionName: "%s",
                        sortOrder: "%s",
                        description: "%s",
                        clientMutationId: "1"
                    })
                    {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME, NEW_INVENTORY_CONDITION_NAME, NEW_SORT_ORDER, NEW_DESCRIPTION));

        assertThat(getBoolean(editBody, "data.editInventoryCondition.commandResult.hasErrors")).isFalse();

        var queryBody = executeUsingPost("""
                query {
                    inventoryCondition(id: "%s") {
                        id
                        inventoryConditionName
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.inventoryCondition.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.inventoryCondition.inventoryConditionName")).isEqualTo(NEW_INVENTORY_CONDITION_NAME);
        assertThat(getInteger(queryBody, "data.inventoryCondition.sortOrder")).isEqualTo(NEW_SORT_ORDER);
        assertThat(getString(queryBody, "data.inventoryCondition.description")).isEqualTo(NEW_DESCRIPTION);

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteInventoryCondition(input: { inventoryConditionName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(NEW_INVENTORY_CONDITION_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteInventoryCondition.commandResult.hasErrors")).isFalse();
    }

}
