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
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class ComponentVendorTest
        extends GraphQlTestCase {

    @Test
    public void componentVendorsQueryNoAuth()
            throws Exception {
        var componentVendorsBody = executeUsingPost("""
                query {
                    componentVendors {
                        edges {
                            node {
                                componentVendorName
                            }
                        }
                    }
                }
        """);

        assertThat(getList(componentVendorsBody, "data.componentVendors.edges")).size().isEqualTo(0);
    }

    @Test
    public void componentVendorQueryNoAuth()
            throws Exception {
        var componentVendorBody = executeUsingPost("""
                query {
                    componentVendor(componentVendorName: "%s") {
                        componentVendorName
                    }
                }
                """.formatted(ComponentVendors.ECHO_THREE));

        assertThat(getMap(componentVendorBody, "data.componentVendor")).isNull();
    }

    @Test
    public void componentVendorsQuery()
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

        var componentVendorsBody = executeUsingPost("""
                query {
                    componentVendors {
                        edges {
                            node {
                                componentVendorName
                            }
                        }
                    }
                }
                """);

        assertThat(getList(componentVendorsBody, "data.componentVendors.edges")).size().isGreaterThan(0);
    }

    @Test
    public void componentVendorQuery()
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

        var componentVendorBody = executeUsingPost("""
                query {
                    componentVendor(componentVendorName: "%s") {
                        componentVendorName
                    }
                }
                """.formatted(ComponentVendors.ECHO_THREE));

        assertThat(getString(componentVendorBody, "data.componentVendor.componentVendorName")).isEqualTo(ComponentVendors.ECHO_THREE.toString());
    }

    @Test
    public void componentVendorQueryWithEntityTypes()
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

        var componentVendorBody = executeUsingPost("""
                query {
                    componentVendor(componentVendorName: "%s") {
                        componentVendorName
                        description
                        id
                        entityTypes {
                            totalCount
                            edges {
                                node {
                                    entityTypeName
                                    description
                                    id
                                }
                            }
                        }
                    }
                }
                """.formatted(ComponentVendors.ECHO_THREE));

        assertThat(getString(componentVendorBody, "data.componentVendor.componentVendorName")).isEqualTo(ComponentVendors.ECHO_THREE.toString());
        var entityTypeCount = getLong(componentVendorBody, "data.componentVendor.entityTypes.totalCount");
        assertThat(entityTypeCount).isGreaterThan(0);
        assertThat(getList(componentVendorBody, "data.componentVendor.entityTypes.edges")).size().isEqualTo(entityTypeCount);
    }

}
