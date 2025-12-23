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
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class EntityTypeTest
        extends GraphQlTestCase {

    @Test
    public void entityTypesQueryNoAuth()
            throws Exception {
        var entityTypesBody = executeUsingPost("""
                query {
                    entityTypes(componentVendorName: "%s") {
                        edges {
                            node {
                                entityTypeName
                            }
                        }
                    }
                }
                """.formatted(ComponentVendors.ECHO_THREE));

        assertThat(getList(entityTypesBody, "data.entityTypes.edges")).size().isEqualTo(0);
    }

    @Test
    public void entityTypeQueryNoAuth()
            throws Exception {
        var entityTypeBody = executeUsingPost("""
                query {
                    entityType(componentVendorName: "%s", entityTypeName: "%s") {
                        entityTypeName
                    }
                }
                """.formatted(ComponentVendors.ECHO_THREE, EntityTypes.GlAccount));

        assertThat(getMap(entityTypeBody, "data.entityType")).isNull();
    }

    @Test
    public void entityTypesQuery()
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

        var entityTypesBody = executeUsingPost("""
                query {
                    entityTypes(componentVendorName: "%s") {
                        edges {
                            node {
                                entityTypeName
                            }
                        }
                    }
                }
                """.formatted(ComponentVendors.ECHO_THREE));

        assertThat(getList(entityTypesBody, "data.entityTypes.edges")).size().isGreaterThan(0);
    }

    @Test
    public void entityTypeQueryUsingNames()
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

        var entityTypeBody = executeUsingPost("""
                query {
                    entityType(componentVendorName: "%s", entityTypeName: "%s") {
                        entityTypeName
                    }
                }
                """.formatted(ComponentVendors.ECHO_THREE, EntityTypes.GlAccount));

        assertThat(getString(entityTypeBody, "data.entityType.entityTypeName")).isEqualTo(EntityTypes.GlAccount.toString());
    }

    @Test
    public void entityTypeQueryUsingOnlyComponentVendorName()
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

        var entityTypeBody = executeUsingPost("""
                query {
                    entityType(componentVendorName: "%s") {
                        entityTypeName
                    }
                }
                """.formatted(ComponentVendors.ECHO_THREE));

        assertThat(getMap(entityTypeBody, "data.entityType")).isNull();
    }

    @Test
    public void entityTypeQueryUsingOnlyEntityTypeName()
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

        var entityTypeBody = executeUsingPost("""
                query {
                    entityType(entityTypeName: "%s") {
                        entityTypeName
                    }
                }
                """.formatted(EntityTypes.GlAccount));

        assertThat(getMap(entityTypeBody, "data.entityType")).isNull();
    }

    @Test
    public void entityTypeQueryUsingId()
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

        var entityTypeBodyUsingNames = executeUsingPost("""
                query {
                    entityType(componentVendorName: "%s", entityTypeName: "%s") {
                        id
                    }
                }
                """.formatted(ComponentVendors.ECHO_THREE, EntityTypes.GlAccount));

        var id = getString(entityTypeBodyUsingNames, "data.entityType.id");

        assertThat(id).isNotNull();

        var entityTypeBodyUsingId = executeUsingPost("""
                query { entityType(id: "%s") {
                    componentVendor {
                        componentVendorName
                    }
                    entityTypeName
                    }
                }
                """.formatted(id));

        assertThat(getString(entityTypeBodyUsingId, "data.entityType.componentVendor.componentVendorName")).isEqualTo(ComponentVendors.ECHO_THREE.toString());
        assertThat(getString(entityTypeBodyUsingId, "data.entityType.entityTypeName")).isEqualTo(EntityTypes.GlAccount.toString());
    }

    @Test
    public void entityTypeQueryUsingNonexistentId()
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

        var entityTypeBody = executeUsingPost("""
                query {
                    entityType(id: "non-existent") {
                        componentVendor {
                            componentVendorName
                        }
                        entityTypeName
                    }
                }
                """);

        var entityType = getMap(entityTypeBody, "data.entityType");

        assertThat(entityType).isNull();
    }

}
