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
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.junit.jupiter.api.Test;

public class LinkCardinalityTest
        extends GraphQlTestCase {

    private static final String ORIGINAL_LINK_CARDINALITY_NAME = "TEST_LINK_CARDINALITY";
    private static final long ORIGINAL_SORT_ORDER = 10L;
    private static final String ORIGINAL_DESCRIPTION = "Test Link Cardinality";

    private static final String NEW_LINK_CARDINALITY_NAME = "TEST_LINK_CARDINALITY_AGAIN";
    private static final long NEW_SORT_ORDER = 20L;
    private static final String NEW_DESCRIPTION = "Test Again Link Cardinality";

    @Test
    public void createLinkCardinalityNoAuth()
            throws Exception {
        var createBody = executeUsingPost("""
                mutation {
                    createLinkCardinality(input: { linkCardinalityName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_CARDINALITY_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkCardinality.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(createBody, "data.createLinkCardinality.commandResult.hasSecurityMessages")).isTrue();
    }

    @Test
    public void deleteLinkCardinalityNoAuth()
            throws Exception {
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkCardinality(input: { linkCardinalityName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_CARDINALITY_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkCardinality.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(deleteBody, "data.deleteLinkCardinality.commandResult.hasSecurityMessages")).isTrue();
    }

    @Test
    public void createLinkCardinalityAndDelete()
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
                    createLinkCardinality(input: { linkCardinalityName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_CARDINALITY_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkCardinality.commandResult.hasErrors")).isFalse();

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkCardinality(input: { linkCardinalityName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_CARDINALITY_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkCardinality.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createLinkCardinalityAndQueryLinkCardinality()
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
                    createLinkCardinality(input: { linkCardinalityName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_CARDINALITY_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkCardinality.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createLinkCardinality.id");

        var queryBody = executeUsingPost("""
                query {
                    linkCardinality(id: "%s") {
                        id
                        linkCardinalityName
                        isDefault
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.linkCardinality.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.linkCardinality.linkCardinalityName")).isEqualTo(ORIGINAL_LINK_CARDINALITY_NAME);
        assertThat(getInteger(queryBody, "data.linkCardinality.sortOrder")).isEqualTo(ORIGINAL_SORT_ORDER);
        assertThat(getString(queryBody, "data.linkCardinality.description")).isEqualTo(ORIGINAL_DESCRIPTION);

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkCardinality(input: { linkCardinalityName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_CARDINALITY_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkCardinality.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createLinkCardinalityAndQueryLinkCardinalities()
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
                    createLinkCardinality(input: { linkCardinalityName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_CARDINALITY_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkCardinality.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createLinkCardinality.id");
        var queryBody = executeUsingPost("""
                query {
                    linkCardinalities {
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

        var linkCardinalityEdges = getList(queryBody, "data.linkCardinalities.edges");

        var foundLinkCardinality = false;
        for(var linkCardinalityEdge : linkCardinalityEdges) {
            var linkCardinalityNode = getMap(linkCardinalityEdge, "node");

            if(id.equals(getString(linkCardinalityNode, "id"))) {
                assertThat(getInteger(linkCardinalityNode, "sortOrder")).isEqualTo(ORIGINAL_SORT_ORDER);
                assertThat(getString(linkCardinalityNode, "description")).isEqualTo(ORIGINAL_DESCRIPTION);
                foundLinkCardinality = true;
            }
        }
        assertThat(foundLinkCardinality).isTrue();

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkCardinality(input: { linkCardinalityName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_CARDINALITY_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkCardinality.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createLinkCardinalityAndEditById()
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
                    createLinkCardinality(input: { linkCardinalityName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_CARDINALITY_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkCardinality.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createLinkCardinality.id");
        var editBody = executeUsingPost("""
                mutation {
                    editLinkCardinality(input: { id: "%s", linkCardinalityName: "%s", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id, NEW_LINK_CARDINALITY_NAME, NEW_SORT_ORDER, NEW_DESCRIPTION));

        assertThat(getBoolean(editBody, "data.editLinkCardinality.commandResult.hasErrors")).isFalse();

        var queryBody = executeUsingPost("""
                query {
                    linkCardinality(id: "%s") {
                        id
                        linkCardinalityName
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.linkCardinality.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.linkCardinality.linkCardinalityName")).isEqualTo(NEW_LINK_CARDINALITY_NAME);
        assertThat(getInteger(queryBody, "data.linkCardinality.sortOrder")).isEqualTo(NEW_SORT_ORDER);
        assertThat(getString(queryBody, "data.linkCardinality.description")).isEqualTo(NEW_DESCRIPTION);

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkCardinality(input: { linkCardinalityName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(NEW_LINK_CARDINALITY_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkCardinality.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createLinkCardinalityAndEditByName()
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
                    createLinkCardinality(input: { linkCardinalityName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_CARDINALITY_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkCardinality.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createLinkCardinality.id");
        var editBody = executeUsingPost("""
                mutation {
                    editLinkCardinality(input: {
                        originalLinkCardinalityName: "%s",
                        linkCardinalityName: "%s",
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
                """.formatted(ORIGINAL_LINK_CARDINALITY_NAME, NEW_LINK_CARDINALITY_NAME, NEW_SORT_ORDER, NEW_DESCRIPTION));

        assertThat(getBoolean(editBody, "data.editLinkCardinality.commandResult.hasErrors")).isFalse();

        var queryBody = executeUsingPost("""
                query {
                    linkCardinality(id: "%s") {
                        id
                        linkCardinalityName
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.linkCardinality.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.linkCardinality.linkCardinalityName")).isEqualTo(NEW_LINK_CARDINALITY_NAME);
        assertThat(getInteger(queryBody, "data.linkCardinality.sortOrder")).isEqualTo(NEW_SORT_ORDER);
        assertThat(getString(queryBody, "data.linkCardinality.description")).isEqualTo(NEW_DESCRIPTION);

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkCardinality(input: { linkCardinalityName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(NEW_LINK_CARDINALITY_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkCardinality.commandResult.hasErrors")).isFalse();
    }

}
