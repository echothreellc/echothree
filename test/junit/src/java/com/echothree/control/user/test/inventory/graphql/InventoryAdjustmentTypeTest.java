// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

public class InventoryAdjustmentTypeTest
        extends GraphQlTestCase {

    private static final String ORIGINAL_INVENTORY_CONDITION_NAME = "TEST_NAME";
    private static final long ORIGINAL_SORT_ORDER = 10L;
    private static final String ORIGINAL_DESCRIPTION = "Test Inventory AdjustmentType";

    private static final String NEW_INVENTORY_CONDITION_NAME = "TEST_AGAIN_NAME";
    private static final long NEW_SORT_ORDER = 20L;
    private static final String NEW_DESCRIPTION = "Test Again Inventory AdjustmentType";

    @Test
    public void createInventoryAdjustmentTypeNoAuth()
            throws Exception {
        var createBody = executeUsingPost("""
                mutation {
                    createInventoryAdjustmentType(input: { inventoryAdjustmentTypeName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createInventoryAdjustmentType.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(createBody, "data.createInventoryAdjustmentType.commandResult.hasSecurityMessages")).isTrue();
    }

    @Test
    public void deleteInventoryAdjustmentTypeNoAuth()
            throws Exception {
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteInventoryAdjustmentType(input: { inventoryAdjustmentTypeName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteInventoryAdjustmentType.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(deleteBody, "data.deleteInventoryAdjustmentType.commandResult.hasSecurityMessages")).isTrue();
    }

    @Test
    public void createInventoryAdjustmentTypeAndDelete()
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
                    createInventoryAdjustmentType(input: { inventoryAdjustmentTypeName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createInventoryAdjustmentType.commandResult.hasErrors")).isFalse();
        
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteInventoryAdjustmentType(input: { inventoryAdjustmentTypeName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteInventoryAdjustmentType.commandResult.hasErrors")).isFalse();
    }
    
    @Test
    public void createInventoryAdjustmentTypeAndQueryInventoryAdjustmentType()
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
                    createInventoryAdjustmentType(input: { inventoryAdjustmentTypeName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createInventoryAdjustmentType.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createInventoryAdjustmentType.id");
        
        var queryBody = executeUsingPost("""
                query {
                    inventoryAdjustmentType(id: "%s") {
                        id
                        inventoryAdjustmentTypeName
                        isDefault
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.inventoryAdjustmentType.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.inventoryAdjustmentType.inventoryAdjustmentTypeName")).isEqualTo(ORIGINAL_INVENTORY_CONDITION_NAME);
        assertThat(getInteger(queryBody, "data.inventoryAdjustmentType.sortOrder")).isEqualTo(ORIGINAL_SORT_ORDER);
        assertThat(getString(queryBody, "data.inventoryAdjustmentType.description")).isEqualTo(ORIGINAL_DESCRIPTION);
        
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteInventoryAdjustmentType(input: { inventoryAdjustmentTypeName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteInventoryAdjustmentType.commandResult.hasErrors")).isFalse();
    }
    
    @Test
    public void createInventoryAdjustmentTypeAndQueryInventoryAdjustmentTypes()
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
                    createInventoryAdjustmentType(input: { inventoryAdjustmentTypeName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createInventoryAdjustmentType.commandResult.hasErrors")).isFalse();
        
        var id = getString(createBody, "data.createInventoryAdjustmentType.id");
        var queryBody = executeUsingPost("""
                query {
                    inventoryAdjustmentTypes {
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
        
        var inventoryAdjustmentTypeEdges = getList(queryBody, "data.inventoryAdjustmentTypes.edges");
        
        var foundInventoryAdjustmentType = false;
        for(var inventoryAdjustmentTypeEdge : inventoryAdjustmentTypeEdges) {
            var inventoryAdjustmentTypeNode = getMap(inventoryAdjustmentTypeEdge, "node");

            if(id.equals(getString(inventoryAdjustmentTypeNode, "id"))) {
                assertThat(getInteger(inventoryAdjustmentTypeNode, "sortOrder")).isEqualTo(ORIGINAL_SORT_ORDER);
                assertThat(getString(inventoryAdjustmentTypeNode, "description")).isEqualTo(ORIGINAL_DESCRIPTION);
                foundInventoryAdjustmentType = true;
            }
        }
        assertThat(foundInventoryAdjustmentType).isTrue();
        
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteInventoryAdjustmentType(input: { inventoryAdjustmentTypeName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteInventoryAdjustmentType.commandResult.hasErrors")).isFalse();
    }
    
    @Test
    public void createInventoryAdjustmentTypeAndEditById()
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
                    createInventoryAdjustmentType(input: { inventoryAdjustmentTypeName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createInventoryAdjustmentType.commandResult.hasErrors")).isFalse();
        
        var id = getString(createBody, "data.createInventoryAdjustmentType.id");
        var editBody = executeUsingPost("""
                mutation {
                    editInventoryAdjustmentType(input: { id: "%s", inventoryAdjustmentTypeName: "%s", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id, NEW_INVENTORY_CONDITION_NAME, NEW_SORT_ORDER, NEW_DESCRIPTION));

        assertThat(getBoolean(editBody, "data.editInventoryAdjustmentType.commandResult.hasErrors")).isFalse();
        
        var queryBody = executeUsingPost("""
                query {
                    inventoryAdjustmentType(id: "%s") {
                        id
                        inventoryAdjustmentTypeName
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.inventoryAdjustmentType.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.inventoryAdjustmentType.inventoryAdjustmentTypeName")).isEqualTo(NEW_INVENTORY_CONDITION_NAME);
        assertThat(getInteger(queryBody, "data.inventoryAdjustmentType.sortOrder")).isEqualTo(NEW_SORT_ORDER);
        assertThat(getString(queryBody, "data.inventoryAdjustmentType.description")).isEqualTo(NEW_DESCRIPTION);
        
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteInventoryAdjustmentType(input: { inventoryAdjustmentTypeName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(NEW_INVENTORY_CONDITION_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteInventoryAdjustmentType.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createInventoryAdjustmentTypeAndEditByName()
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
                    createInventoryAdjustmentType(input: { inventoryAdjustmentTypeName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_INVENTORY_CONDITION_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createInventoryAdjustmentType.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createInventoryAdjustmentType.id");
        var editBody = executeUsingPost("""
                mutation {
                    editInventoryAdjustmentType(input: {
                        originalInventoryAdjustmentTypeName: "%s",
                        inventoryAdjustmentTypeName: "%s",
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

        assertThat(getBoolean(editBody, "data.editInventoryAdjustmentType.commandResult.hasErrors")).isFalse();

        var queryBody = executeUsingPost("""
                query {
                    inventoryAdjustmentType(id: "%s") {
                        id
                        inventoryAdjustmentTypeName
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.inventoryAdjustmentType.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.inventoryAdjustmentType.inventoryAdjustmentTypeName")).isEqualTo(NEW_INVENTORY_CONDITION_NAME);
        assertThat(getInteger(queryBody, "data.inventoryAdjustmentType.sortOrder")).isEqualTo(NEW_SORT_ORDER);
        assertThat(getString(queryBody, "data.inventoryAdjustmentType.description")).isEqualTo(NEW_DESCRIPTION);

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteInventoryAdjustmentType(input: { inventoryAdjustmentTypeName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(NEW_INVENTORY_CONDITION_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteInventoryAdjustmentType.commandResult.hasErrors")).isFalse();
    }

}
