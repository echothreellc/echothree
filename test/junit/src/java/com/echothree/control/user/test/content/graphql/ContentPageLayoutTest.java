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

package com.echothree.control.user.test.content.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.Test;

public class ContentPageLayoutTest
        extends GraphQlTestCase {

    private static final String ORIGINAL_CONTENT_PAGE_LAYOUT_NAME = "TEST_NAME";
    private static final long ORIGINAL_SORT_ORDER = 10L;
    private static final String ORIGINAL_DESCRIPTION = "Test Content Page Layout";

    private static final String NEW_CONTENT_PAGE_LAYOUT_NAME = "TEST_AGAIN_NAME";
    private static final long NEW_SORT_ORDER = 20L;
    private static final String NEW_DESCRIPTION = "Test Again Content Page Layout";

    @Test
    public void createContentPageLayoutNoAuth()
            throws Exception {
        var createBody = executeUsingPost("""
                mutation {
                    createContentPageLayout(input: { contentPageLayoutName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_CONTENT_PAGE_LAYOUT_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createContentPageLayout.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(createBody, "data.createContentPageLayout.commandResult.hasSecurityMessages")).isTrue();
    }

    @Test
    public void deleteContentPageLayoutNoAuth()
            throws Exception {
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteContentPageLayout(input: { contentPageLayoutName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_CONTENT_PAGE_LAYOUT_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteContentPageLayout.commandResult.hasErrors")).isTrue();
        assertThat(getBoolean(deleteBody, "data.deleteContentPageLayout.commandResult.hasSecurityMessages")).isTrue();
    }

    @Test
    public void createContentPageLayoutAndDelete()
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
                    createContentPageLayout(input: { contentPageLayoutName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_CONTENT_PAGE_LAYOUT_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createContentPageLayout.commandResult.hasErrors")).isFalse();
        
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteContentPageLayout(input: { contentPageLayoutName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_CONTENT_PAGE_LAYOUT_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteContentPageLayout.commandResult.hasErrors")).isFalse();
    }
    
    @Test
    public void createContentPageLayoutAndQueryContentPageLayout()
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
                    createContentPageLayout(input: { contentPageLayoutName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_CONTENT_PAGE_LAYOUT_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createContentPageLayout.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createContentPageLayout.id");
        var queryBody = executeUsingPost("""
                query {
                    contentPageLayout(id: "%s") {
                        id
                        contentPageLayoutName
                        isDefault
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.contentPageLayout.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.contentPageLayout.contentPageLayoutName")).isEqualTo(ORIGINAL_CONTENT_PAGE_LAYOUT_NAME);
        assertThat(getInteger(queryBody, "data.contentPageLayout.sortOrder")).isEqualTo(ORIGINAL_SORT_ORDER);
        assertThat(getString(queryBody, "data.contentPageLayout.description")).isEqualTo(ORIGINAL_DESCRIPTION);
        
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteContentPageLayout(input: { contentPageLayoutName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_CONTENT_PAGE_LAYOUT_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteContentPageLayout.commandResult.hasErrors")).isFalse();
    }
    
    @Test
    public void createContentPageLayoutAndQueryContentPageLayouts()
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
                    createContentPageLayout(input: { contentPageLayoutName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_CONTENT_PAGE_LAYOUT_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createContentPageLayout.commandResult.hasErrors")).isFalse();
        
        var id = getString(createBody, "data.createContentPageLayout.id");
        var queryBody = executeUsingPost("""
                query {
                    contentPageLayouts {
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
        
        var contentPageLayouts = getList(queryBody, "data.contentPageLayouts.edges");
        assertThat(contentPageLayouts.isEmpty()).isFalse();

        var foundContentPageLayout = false;
        for(var contentPageLayout : contentPageLayouts) {
            var node = getMap(contentPageLayout, "node");

            if(id.equals(getString(node, "id"))) {
                assertThat(getInteger(node, "sortOrder")).isEqualTo(ORIGINAL_SORT_ORDER);
                assertThat(getString(node, "description")).isEqualTo(ORIGINAL_DESCRIPTION);
                foundContentPageLayout = true;
            }
        }

        assertThat(foundContentPageLayout).isTrue();
        
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteContentPageLayout(input: { contentPageLayoutName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(ORIGINAL_CONTENT_PAGE_LAYOUT_NAME));
        
        assertThat(getBoolean(deleteBody, "data.deleteContentPageLayout.commandResult.hasErrors")).isFalse();
    }
    
    @Test
    public void createContentPageLayoutAndEditById()
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
                    createContentPageLayout(input: { contentPageLayoutName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_CONTENT_PAGE_LAYOUT_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createContentPageLayout.commandResult.hasErrors")).isFalse();
        
        var id = getString(createBody, "data.createContentPageLayout.id");
        var editBody = executeUsingPost("""
                mutation {
                    editContentPageLayout(input: { id: "%s", contentPageLayoutName: "%s", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(id, NEW_CONTENT_PAGE_LAYOUT_NAME, NEW_SORT_ORDER, NEW_DESCRIPTION));

        assertThat(getBoolean(editBody, "data.editContentPageLayout.commandResult.hasErrors")).isFalse();
        
        var queryBody = executeUsingPost("""
                query {
                    contentPageLayout(id: "%s") {
                        id
                        contentPageLayoutName
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.contentPageLayout.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.contentPageLayout.contentPageLayoutName")).isEqualTo(NEW_CONTENT_PAGE_LAYOUT_NAME);
        assertThat(getInteger(queryBody, "data.contentPageLayout.sortOrder")).isEqualTo(NEW_SORT_ORDER);
        assertThat(getString(queryBody, "data.contentPageLayout.description")).isEqualTo(NEW_DESCRIPTION);
        
        var deleteBody = executeUsingPost("""
                mutation {
                    deleteContentPageLayout(input: { contentPageLayoutName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(NEW_CONTENT_PAGE_LAYOUT_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteContentPageLayout.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void createContentPageLayoutAndEditByName()
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
                    createContentPageLayout(input: { contentPageLayoutName: "%s", isDefault: "false", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        id
                        commandResult {
                            hasErrors
                            hasSecurityMessages
                        }
                    }
                }
                """.formatted(ORIGINAL_CONTENT_PAGE_LAYOUT_NAME, ORIGINAL_SORT_ORDER, ORIGINAL_DESCRIPTION));

        assertThat(getBoolean(createBody, "data.createContentPageLayout.commandResult.hasErrors")).isFalse();

        var id = getString(createBody, "data.createContentPageLayout.id");
        var editBody = executeUsingPost("""
                mutation {
                    editContentPageLayout(input: { originalContentPageLayoutName: "%s", contentPageLayoutName: "%s", sortOrder: "%s", description: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(ORIGINAL_CONTENT_PAGE_LAYOUT_NAME, NEW_CONTENT_PAGE_LAYOUT_NAME, NEW_SORT_ORDER, NEW_DESCRIPTION));

        assertThat(getBoolean(editBody, "data.editContentPageLayout.commandResult.hasErrors")).isFalse();

        var queryBody = executeUsingPost("""
                query {
                    contentPageLayout(id: "%s") {
                        id
                        contentPageLayoutName
                        sortOrder
                        description
                    }
                }
                """.formatted(id));

        assertThat(getString(queryBody, "data.contentPageLayout.id")).isEqualTo(id);
        assertThat(getString(queryBody, "data.contentPageLayout.contentPageLayoutName")).isEqualTo(NEW_CONTENT_PAGE_LAYOUT_NAME);
        assertThat(getInteger(queryBody, "data.contentPageLayout.sortOrder")).isEqualTo(NEW_SORT_ORDER);
        assertThat(getString(queryBody, "data.contentPageLayout.description")).isEqualTo(NEW_DESCRIPTION);

        var deleteBody = executeUsingPost("""
                mutation {
                    deleteContentPageLayout(input: { contentPageLayoutName: "%s", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """.formatted(NEW_CONTENT_PAGE_LAYOUT_NAME));

        assertThat(getBoolean(deleteBody, "data.deleteContentPageLayout.commandResult.hasErrors")).isFalse();
    }

}
