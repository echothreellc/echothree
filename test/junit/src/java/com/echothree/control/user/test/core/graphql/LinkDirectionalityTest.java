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

public class LinkDirectionalityTest
        extends GraphQlTestCase {

    private static final String ORIGINAL_LINK_DIRECTIONALITY_NAME = "TEST_LINK_DIRECTIONALITY";
    private static final long ORIGINAL_SORT_ORDER = 10L;
    private static final String ORIGINAL_DESCRIPTION = "Test Link Directionality";

    private static final String NEW_LINK_DIRECTIONALITY_NAME = "TEST_LINK_DIRECTIONALITY_AGAIN";
    private static final long NEW_SORT_ORDER = 20L;
    private static final String NEW_DESCRIPTION = "Test Again Link Directionality";

    @Test
    public void createLinkDirectionalityNoAuth()
            throws Exception {
        var createBody = executeUsingPost("""
                mutation {
                    createLinkDirectionality(input: { linkDirectionalityName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_DIRECTIONALITY_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkDirectionality.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(createBody, "data.createLinkDirectionality.commandResult.hasSecurityMessages")).isTrue();
    }

    @Test
    public void deleteLinkDirectionalityNoAuth()
            throws Exception {
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkDirectionality(input: { linkDirectionalityName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_DIRECTIONALITY_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkDirectionality.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(deleteBody, "data.deleteLinkDirectionality.commandResult.hasSecurityMessages")).isTrue();
    }

    @Test
    public void createLinkDirectionalityAndDelete()
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
                    createLinkDirectionality(input: { linkDirectionalityName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_DIRECTIONALITY_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkDirectionality.commandResult.hasErrors")).isFalse();

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkDirectionality(input: { linkDirectionalityName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_DIRECTIONALITY_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkDirectionality.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createLinkDirectionalityAndQueryLinkDirectionality()
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
                    createLinkDirectionality(input: { linkDirectionalityName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_DIRECTIONALITY_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkDirectionality.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createLinkDirectionality.id");

        var queryBody = executeUsingPost("""
                query {
                    linkDirectionality(id: "%s") {
                        id
                        linkDirectionalityName
                        isDefault
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.linkDirectionality.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.linkDirectionality.linkDirectionalityName")).isEqualTo(ORIGINAL_LINK_DIRECTIONALITY_NAME);
        assertThat(getInteger(queryBody, "data.linkDirectionality.sortOrder")).isEqualTo(ORIGINAL_SORT_ORDER);
        assertThat(getString(queryBody, "data.linkDirectionality.description")).isEqualTo(ORIGINAL_DESCRIPTION);

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkDirectionality(input: { linkDirectionalityName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_DIRECTIONALITY_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkDirectionality.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createLinkDirectionalityAndQueryLinkDirectionalities()
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
                    createLinkDirectionality(input: { linkDirectionalityName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_DIRECTIONALITY_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkDirectionality.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createLinkDirectionality.id");
        var queryBody = executeUsingPost("""
                query {
                    linkDirectionalities {
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

        var linkDirectionalityEdges = getList(queryBody, "data.linkDirectionalities.edges");

        var foundLinkDirectionality = false;
        for(var linkDirectionalityEdge : linkDirectionalityEdges) {
            var linkDirectionalityNode = getMap(linkDirectionalityEdge, "node");

            if(id.equals(getString(linkDirectionalityNode, "id"))) {
                assertThat(getInteger(linkDirectionalityNode, "sortOrder")).isEqualTo(ORIGINAL_SORT_ORDER);
                assertThat(getString(linkDirectionalityNode, "description")).isEqualTo(ORIGINAL_DESCRIPTION);
                foundLinkDirectionality = true;
            }
        }
        assertThat(foundLinkDirectionality).isTrue();

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkDirectionality(input: { linkDirectionalityName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_DIRECTIONALITY_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkDirectionality.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createLinkDirectionalityAndEditById()
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
                    createLinkDirectionality(input: { linkDirectionalityName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_DIRECTIONALITY_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkDirectionality.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createLinkDirectionality.id");
        var editBody = executeUsingPost("""
                mutation {
                    editLinkDirectionality(input: { id: "%s", linkDirectionalityName: "%s", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id, NEW_LINK_DIRECTIONALITY_NAME, NEW_SORT_ORDER, NEW_DESCRIPTION));

        assertThat(getBoolean(editBody, "data.editLinkDirectionality.commandResult.hasErrors")).isFalse();

        var queryBody = executeUsingPost("""
                query {
                    linkDirectionality(id: "%s") {
                        id
                        linkDirectionalityName
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.linkDirectionality.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.linkDirectionality.linkDirectionalityName")).isEqualTo(NEW_LINK_DIRECTIONALITY_NAME);
        assertThat(getInteger(queryBody, "data.linkDirectionality.sortOrder")).isEqualTo(NEW_SORT_ORDER);
        assertThat(getString(queryBody, "data.linkDirectionality.description")).isEqualTo(NEW_DESCRIPTION);

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkDirectionality(input: { linkDirectionalityName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(NEW_LINK_DIRECTIONALITY_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkDirectionality.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createLinkDirectionalityAndEditByName()
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
                    createLinkDirectionality(input: { linkDirectionalityName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_DIRECTIONALITY_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkDirectionality.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createLinkDirectionality.id");
        var editBody = executeUsingPost("""
                mutation {
                    editLinkDirectionality(input: {
                        originalLinkDirectionalityName: "%s",
                        linkDirectionalityName: "%s",
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
                """.formatted(ORIGINAL_LINK_DIRECTIONALITY_NAME, NEW_LINK_DIRECTIONALITY_NAME, NEW_SORT_ORDER, NEW_DESCRIPTION));

        assertThat(getBoolean(editBody, "data.editLinkDirectionality.commandResult.hasErrors")).isFalse();

        var queryBody = executeUsingPost("""
                query {
                    linkDirectionality(id: "%s") {
                        id
                        linkDirectionalityName
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.linkDirectionality.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.linkDirectionality.linkDirectionalityName")).isEqualTo(NEW_LINK_DIRECTIONALITY_NAME);
        assertThat(getInteger(queryBody, "data.linkDirectionality.sortOrder")).isEqualTo(NEW_SORT_ORDER);
        assertThat(getString(queryBody, "data.linkDirectionality.description")).isEqualTo(NEW_DESCRIPTION);

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkDirectionality(input: { linkDirectionalityName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(NEW_LINK_DIRECTIONALITY_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkDirectionality.commandResult.hasErrors")).isFalse();
    }

}
