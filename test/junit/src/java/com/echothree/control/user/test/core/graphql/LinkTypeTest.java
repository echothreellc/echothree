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

public class LinkTypeTest
        extends GraphQlTestCase {

    private static final String ORIGINAL_LINK_TYPE_NAME = "TEST_LINK_TYPE";
    private static final long ORIGINAL_SORT_ORDER = 10L;
    private static final String ORIGINAL_DESCRIPTION = "Test Link Type";

    private static final String NEW_LINK_TYPE_NAME = "TEST_LINK_TYPE_AGAIN";
    private static final long NEW_SORT_ORDER = 20L;
    private static final String NEW_DESCRIPTION = "Test Again Link Type";

    @Test
    public void createLinkTypeNoAuth()
            throws Exception {
        var createBody = executeUsingPost("""
                mutation {
                    createLinkType(input: { linkTypeName: "%s", sourceComponentVendorName: "ECHO_THREE", sourceEntityTypeName: "Item", targetComponentVendorName: "ECHO_THREE", targetEntityTypeName: "Item", linkCardinalityName: "MANY_TO_MANY", linkDirectionalityName: "DIRECTED", isTransitive: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_TYPE_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkType.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(createBody, "data.createLinkType.commandResult.hasSecurityMessages")).isTrue();
    }

    @Test
    public void deleteLinkTypeNoAuth()
            throws Exception {
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkType(input: { linkTypeName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_TYPE_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkType.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(deleteBody, "data.deleteLinkType.commandResult.hasSecurityMessages")).isTrue();
    }

    @Test
    public void createLinkTypeAndDelete()
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
                    createLinkType(input: { linkTypeName: "%s", sourceComponentVendorName: "ECHO_THREE", sourceEntityTypeName: "Item", targetComponentVendorName: "ECHO_THREE", targetEntityTypeName: "Item", linkCardinalityName: "MANY_TO_MANY", linkDirectionalityName: "DIRECTED", isTransitive: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_TYPE_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkType.commandResult.hasErrors")).isFalse();

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkType(input: { linkTypeName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_TYPE_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkType.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createLinkTypeAndQueryLinkType()
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
                    createLinkType(input: { linkTypeName: "%s", sourceComponentVendorName: "ECHO_THREE", sourceEntityTypeName: "Item", targetComponentVendorName: "ECHO_THREE", targetEntityTypeName: "Item", linkCardinalityName: "MANY_TO_MANY", linkDirectionalityName: "DIRECTED", isTransitive: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_TYPE_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkType.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createLinkType.id");

        var queryBody = executeUsingPost("""
                query {
                    linkType(id: "%s") {
                        id
                        linkTypeName
                        isTransitive
                        sortOrder
                        linkTypeTranslation { description }
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.linkType.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.linkType.linkTypeName")).isEqualTo(ORIGINAL_LINK_TYPE_NAME);
        assertThat(getInteger(queryBody, "data.linkType.sortOrder")).isEqualTo(ORIGINAL_SORT_ORDER);
        assertThat(getString(queryBody, "data.linkType.linkTypeTranslation.description")).isEqualTo(ORIGINAL_DESCRIPTION);

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkType(input: { linkTypeName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_TYPE_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkType.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createLinkTypeAndQueryLinkTypes()
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
                    createLinkType(input: { linkTypeName: "%s", sourceComponentVendorName: "ECHO_THREE", sourceEntityTypeName: "Item", targetComponentVendorName: "ECHO_THREE", targetEntityTypeName: "Item", linkCardinalityName: "MANY_TO_MANY", linkDirectionalityName: "DIRECTED", isTransitive: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_TYPE_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkType.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createLinkType.id");
        var queryBody = executeUsingPost("""
                query {
                    linkTypes {
                        edges {
                            node {
                                id
                                sortOrder
                                linkTypeTranslation { description }
                            }
                        }
                    }
                }
                """);

        var linkTypeEdges = getList(queryBody, "data.linkTypes.edges");

        var foundLinkType = false;
        for(var linkTypeEdge : linkTypeEdges) {
            var linkTypeNode = getMap(linkTypeEdge, "node");

            if(id.equals(getString(linkTypeNode, "id"))) {
                assertThat(getInteger(linkTypeNode, "sortOrder")).isEqualTo(ORIGINAL_SORT_ORDER);
                assertThat(getString(linkTypeNode, "linkTypeTranslation.description")).isEqualTo(ORIGINAL_DESCRIPTION);
                foundLinkType = true;
            }
        }
        assertThat(foundLinkType).isTrue();

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkType(input: { linkTypeName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_TYPE_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkType.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createLinkTypeAndEditById()
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
                    createLinkType(input: { linkTypeName: "%s", sourceComponentVendorName: "ECHO_THREE", sourceEntityTypeName: "Item", targetComponentVendorName: "ECHO_THREE", targetEntityTypeName: "Item", linkCardinalityName: "MANY_TO_MANY", linkDirectionalityName: "DIRECTED", isTransitive: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_TYPE_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkType.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createLinkType.id");
        var editBody = executeUsingPost("""
                mutation {
                    editLinkType(input: { id: "%s", linkTypeName: "%s", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id, NEW_LINK_TYPE_NAME, NEW_SORT_ORDER, NEW_DESCRIPTION));

        assertThat(getBoolean(editBody, "data.editLinkType.commandResult.hasErrors")).isFalse();

        var queryBody = executeUsingPost("""
                query {
                    linkType(id: "%s") {
                        id
                        linkTypeName
                        sortOrder
                        linkTypeTranslation { description }
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.linkType.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.linkType.linkTypeName")).isEqualTo(NEW_LINK_TYPE_NAME);
        assertThat(getInteger(queryBody, "data.linkType.sortOrder")).isEqualTo(NEW_SORT_ORDER);
        assertThat(getString(queryBody, "data.linkType.linkTypeTranslation.description")).isEqualTo(NEW_DESCRIPTION);

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkType(input: { linkTypeName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(NEW_LINK_TYPE_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkType.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createLinkTypeAndEditByName()
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
                    createLinkType(input: { linkTypeName: "%s", sourceComponentVendorName: "ECHO_THREE", sourceEntityTypeName: "Item", targetComponentVendorName: "ECHO_THREE", targetEntityTypeName: "Item", linkCardinalityName: "MANY_TO_MANY", linkDirectionalityName: "DIRECTED", isTransitive: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_LINK_TYPE_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createLinkType.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createLinkType.id");
        var editBody = executeUsingPost("""
                mutation {
                    editLinkType(input: {
                        originalLinkTypeName: "%s",
                        linkTypeName: "%s",
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
                """.formatted(ORIGINAL_LINK_TYPE_NAME, NEW_LINK_TYPE_NAME, NEW_SORT_ORDER, NEW_DESCRIPTION));

        assertThat(getBoolean(editBody, "data.editLinkType.commandResult.hasErrors")).isFalse();

        var queryBody = executeUsingPost("""
                query {
                    linkType(id: "%s") {
                        id
                        linkTypeName
                        sortOrder
                        linkTypeTranslation { description }
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.linkType.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.linkType.linkTypeName")).isEqualTo(NEW_LINK_TYPE_NAME);
        assertThat(getInteger(queryBody, "data.linkType.sortOrder")).isEqualTo(NEW_SORT_ORDER);
        assertThat(getString(queryBody, "data.linkType.linkTypeTranslation.description")).isEqualTo(NEW_DESCRIPTION);

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteLinkType(input: { linkTypeName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(NEW_LINK_TYPE_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteLinkType.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void translationsPreserveGuidanceAcrossMainAndTranslationEdits()
            throws Exception {
        var login = executeUsingPost("""
                mutation {
                    employeeLogin(input: { username: "test e", password: "password", companyName: "TEST_COMPANY" }) {
                        commandResult { hasErrors }
                    }
                }
                """);
        assertThat(getBoolean(login, "data.employeeLogin.commandResult.hasErrors")).isFalse();
        var create = executeUsingPost("""
                mutation {
                    createLinkType(input: { linkTypeName: "TEST_LINK_TYPE_TRANSLATIONS",
                        sourceComponentVendorName: "ECHO_THREE", sourceEntityTypeName: "Item",
                        targetComponentVendorName: "ECHO_THREE", targetEntityTypeName: "Item",
                        linkCardinalityName: "MANY_TO_MANY", linkDirectionalityName: "DIRECTED",
                        isTransitive: "false", sortOrder: "1", description: "Example" }) {
                        commandResult { hasErrors }
                    }
                }
                """);
        assertThat(getBoolean(create, "data.createLinkType.commandResult.hasErrors")).isFalse();
        try {
            var createTranslation = executeUsingPost("""
                    mutation {
                        createLinkTypeTranslation(input: { linkTypeName: "TEST_LINK_TYPE_TRANSLATIONS", languageIsoName: "fr",
                            description: "Exemple", guidanceMimeTypeName: "text/plain", guidance: "French guidance" }) {
                            commandResult { hasErrors }
                        }
                    }
                    """);
            assertThat(getBoolean(createTranslation, "data.createLinkTypeTranslation.commandResult.hasErrors")).isFalse();
            var edit = executeUsingPost("""
                    mutation {
                        editLinkType(input: { originalLinkTypeName: "TEST_LINK_TYPE_TRANSLATIONS",
                            guidanceMimeTypeName: "text/plain", guidance: "English guidance" }) {
                            commandResult { hasErrors }
                        }
                    }
                    """);
            assertThat(getBoolean(edit, "data.editLinkType.commandResult.hasErrors")).isFalse();
            var query = executeUsingPost("""
                    query {
                        linkType(linkTypeName: "TEST_LINK_TYPE_TRANSLATIONS") {
                            linkTypeTranslation { description guidance guidanceMimeType { mimeTypeName } }
                        }
                        linkTypeTranslation(linkTypeName: "TEST_LINK_TYPE_TRANSLATIONS", languageIsoName: "fr") {
                            description guidance guidanceMimeType { mimeTypeName }
                        }
                    }
                    """);
            assertThat(getString(query, "data.linkType.linkTypeTranslation.description")).isEqualTo("Example");
            assertThat(getString(query, "data.linkType.linkTypeTranslation.guidance")).isEqualTo("English guidance");
            assertThat(getString(query, "data.linkType.linkTypeTranslation.guidanceMimeType.mimeTypeName")).isEqualTo("text/plain");
            assertThat(getString(query, "data.linkTypeTranslation.guidance")).isEqualTo("French guidance");
            var clearGuidance = executeUsingPost("""
                    mutation {
                        editLinkTypeTranslation(input: { linkTypeName: "TEST_LINK_TYPE_TRANSLATIONS", languageIsoName: "fr",
                            guidanceMimeTypeName: null, guidance: null }) {
                            commandResult { hasErrors }
                        }
                    }
                    """);
            assertThat(getBoolean(clearGuidance, "data.editLinkTypeTranslation.commandResult.hasErrors")).isFalse();
            var cleared = executeUsingPost("""
                    query {
                        linkTypeTranslation(linkTypeName: "TEST_LINK_TYPE_TRANSLATIONS", languageIsoName: "fr") {
                            description guidance guidanceMimeType { mimeTypeName }
                        }
                    }
                    """);
            assertThat(getString(cleared, "data.linkTypeTranslation.description")).isEqualTo("Exemple");
            assertThat(getObject(cleared, "data.linkTypeTranslation.guidance")).isNull();
            assertThat(getObject(cleared, "data.linkTypeTranslation.guidanceMimeType")).isNull();
            var deleteTranslation = executeUsingPost("""
                    mutation {
                        deleteLinkTypeTranslation(input: { linkTypeName: "TEST_LINK_TYPE_TRANSLATIONS", languageIsoName: "fr" }) {
                            commandResult { hasErrors }
                        }
                    }
                    """);
            assertThat(getBoolean(deleteTranslation, "data.deleteLinkTypeTranslation.commandResult.hasErrors")).isFalse();
        } finally {
            var delete = executeUsingPost("""
                    mutation {
                        deleteLinkType(input: { linkTypeName: "TEST_LINK_TYPE_TRANSLATIONS" }) {
                            commandResult { hasErrors }
                        }
                    }
                    """);
            assertThat(getBoolean(delete, "data.deleteLinkType.commandResult.hasErrors")).isFalse();
        }
    }

}
