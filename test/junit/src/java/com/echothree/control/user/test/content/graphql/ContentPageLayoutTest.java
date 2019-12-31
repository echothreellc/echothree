// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class ContentPageLayoutTest
        extends GraphQlTestCase {

    @Test
    public void createContentPageLayoutNoAuth()
            throws Exception {
        Map<String, Object> createBody = executeUsingPost("mutation { createContentPageLayout(input: { contentPageLayoutName: \"" + ORIGINAL_CONTENT_PAGE_LAYOUT_NAME + "\", isDefault: \"false\", sortOrder: \"" + ORIGINAL_SORT_ORDER + "\", description: \"" + ORIGINAL_DESCRIPTION + "\", clientMutationId: \"1\" }) { id hasErrors hasSecurityMessages } }");
        Assert.assertTrue(getBoolean(createBody, "data.createContentPageLayout.hasErrors"));
        Assert.assertTrue(getBoolean(createBody, "data.createContentPageLayout.hasSecurityMessages"));
    }

    @Test
    public void deleteContentPageLayoutNoAuth()
            throws Exception {
        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteContentPageLayout(input: { contentPageLayoutName: \"" + ORIGINAL_CONTENT_PAGE_LAYOUT_NAME + "\", clientMutationId: \"1\" }) { hasErrors hasSecurityMessages } }");
        Assert.assertTrue(getBoolean(deleteBody, "data.deleteContentPageLayout.hasErrors"));
        Assert.assertTrue(getBoolean(deleteBody, "data.deleteContentPageLayout.hasSecurityMessages"));
    }

    @Test
    public void createContentPageLayoutAndDelete()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        Map<String, Object> createBody = executeUsingPost("mutation { createContentPageLayout(input: { contentPageLayoutName: \"" + ORIGINAL_CONTENT_PAGE_LAYOUT_NAME + "\", isDefault: \"false\", sortOrder: \"" + ORIGINAL_SORT_ORDER + "\", description: \"" + ORIGINAL_DESCRIPTION + "\", clientMutationId: \"1\" }) { id hasErrors hasSecurityMessages } }");
        Assert.assertFalse(getBoolean(createBody, "data.createContentPageLayout.hasErrors"));
        
        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteContentPageLayout(input: { contentPageLayoutName: \"" + ORIGINAL_CONTENT_PAGE_LAYOUT_NAME + "\", clientMutationId: \"1\" }) { hasErrors hasSecurityMessages } }");
        Assert.assertFalse(getBoolean(deleteBody, "data.deleteContentPageLayout.hasErrors"));
    }
    
    private static final String ORIGINAL_CONTENT_PAGE_LAYOUT_NAME = "TEST_NAME";
    private static final long ORIGINAL_SORT_ORDER = 10L;
    private static final String ORIGINAL_DESCRIPTION = "Test Content Page Layout";

    private static final String NEW_CONTENT_PAGE_LAYOUT_NAME = "TEST_AGAIN_NAME";
    private static final long NEW_SORT_ORDER = 20L;
    private static final String NEW_DESCRIPTION = "Test Again Content Page Layout";
        
    @Test
    public void createContentPageLayoutAndQueryContentPageLayout()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        Map<String, Object> createBody = executeUsingPost("mutation { createContentPageLayout(input: { contentPageLayoutName: \"" + ORIGINAL_CONTENT_PAGE_LAYOUT_NAME + "\", isDefault: \"false\", sortOrder: \"" + ORIGINAL_SORT_ORDER + "\", description: \"" + ORIGINAL_DESCRIPTION + "\", clientMutationId: \"1\" }) { id hasErrors hasSecurityMessages } }");
        Assert.assertFalse(getBoolean(createBody, "data.createContentPageLayout.hasErrors"));

        String id = getString(createBody, "data.createContentPageLayout.id");
        
        Map<String, Object> queryBody = executeUsingPost("query { contentPageLayout(id: \"" + id + "\") { id contentPageLayoutName isDefault sortOrder description } }");
        Assert.assertEquals(id, getString(queryBody, "data.contentPageLayout.id"));
        Assert.assertEquals(ORIGINAL_CONTENT_PAGE_LAYOUT_NAME, getString(queryBody, "data.contentPageLayout.contentPageLayoutName"));
        Assert.assertEquals(ORIGINAL_SORT_ORDER, getInteger(queryBody, "data.contentPageLayout.sortOrder"));
        Assert.assertEquals(ORIGINAL_DESCRIPTION, getString(queryBody, "data.contentPageLayout.description"));
        
        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteContentPageLayout(input: { contentPageLayoutName: \"" + ORIGINAL_CONTENT_PAGE_LAYOUT_NAME + "\", clientMutationId: \"1\" }) { hasErrors hasSecurityMessages } }");
        Assert.assertFalse(getBoolean(deleteBody, "data.deleteContentPageLayout.hasErrors"));
    }
    
    @Test
    public void createContentPageLayoutAndQueryContentPageLayouts()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> createBody = executeUsingPost("mutation { createContentPageLayout(input: { contentPageLayoutName: \"" + ORIGINAL_CONTENT_PAGE_LAYOUT_NAME + "\", isDefault: \"false\", sortOrder: \"" + ORIGINAL_SORT_ORDER + "\", description: \"" + ORIGINAL_DESCRIPTION + "\", clientMutationId: \"1\" }) { id hasErrors hasSecurityMessages } }");
        Assert.assertFalse(getBoolean(createBody, "data.createContentPageLayout.hasErrors"));
        
        String id = getString(createBody, "data.createContentPageLayout.id");
        
        Map<String, Object> queryBody = executeUsingPost("query { contentPageLayouts { id sortOrder description } }");
        
        List<Map<String, Object>> contentPageLayouts = getList(queryBody, "data.contentPageLayouts");
        
        boolean foundContentPageLayout = false;
        for(Map<String, Object> contentPageLayout : contentPageLayouts) {
            if(id.equals(getString(contentPageLayout, "id"))) {
                Assert.assertEquals(ORIGINAL_SORT_ORDER, getInteger(contentPageLayout, "sortOrder"));
                Assert.assertEquals(ORIGINAL_DESCRIPTION, getString(contentPageLayout, "description"));
                foundContentPageLayout = true;
            }
        }
        Assert.assertTrue(foundContentPageLayout);
        
        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteContentPageLayout(input: { contentPageLayoutName: \"" + ORIGINAL_CONTENT_PAGE_LAYOUT_NAME + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteBody, "data.deleteContentPageLayout.hasErrors"));
    }
    
    @Test
    public void createContentPageLayoutAndEditById()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));
        
        Map<String, Object> createBody = executeUsingPost("mutation { createContentPageLayout(input: { contentPageLayoutName: \"" + ORIGINAL_CONTENT_PAGE_LAYOUT_NAME + "\", isDefault: \"false\", sortOrder: \"" + ORIGINAL_SORT_ORDER + "\", description: \"" + ORIGINAL_DESCRIPTION + "\", clientMutationId: \"1\" }) { id hasErrors hasSecurityMessages } }");
        Assert.assertFalse(getBoolean(createBody, "data.createContentPageLayout.hasErrors"));
        
        String id = getString(createBody, "data.createContentPageLayout.id");
        
        Map<String, Object> editBody = executeUsingPost("mutation { editContentPageLayout(input: { id: \"" + id + "\", contentPageLayoutName: \"" + NEW_CONTENT_PAGE_LAYOUT_NAME + "\", sortOrder: \"" + NEW_SORT_ORDER + "\", description: \"" + NEW_DESCRIPTION + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(editBody, "data.editContentPageLayout.hasErrors"));
        
        Map<String, Object> queryBody = executeUsingPost("query { contentPageLayout(id: \"" + id + "\") { id contentPageLayoutName, sortOrder description } }");
        Assert.assertEquals(id, getString(queryBody, "data.contentPageLayout.id"));
        Assert.assertEquals(NEW_CONTENT_PAGE_LAYOUT_NAME, getString(queryBody, "data.contentPageLayout.contentPageLayoutName"));
        Assert.assertEquals(NEW_SORT_ORDER, getInteger(queryBody, "data.contentPageLayout.sortOrder"));
        Assert.assertEquals(NEW_DESCRIPTION, getString(queryBody, "data.contentPageLayout.description"));
        
        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteContentPageLayout(input: { contentPageLayoutName: \"" + NEW_CONTENT_PAGE_LAYOUT_NAME + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteBody, "data.deleteContentPageLayout.hasErrors"));
    }

    @Test
    public void createContentPageLayoutAndEditByName()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        Map<String, Object> createBody = executeUsingPost("mutation { createContentPageLayout(input: { contentPageLayoutName: \"" + ORIGINAL_CONTENT_PAGE_LAYOUT_NAME + "\", isDefault: \"false\", sortOrder: \"" + ORIGINAL_SORT_ORDER + "\", description: \"" + ORIGINAL_DESCRIPTION + "\", clientMutationId: \"1\" }) { id hasErrors hasSecurityMessages } }");
        Assert.assertFalse(getBoolean(createBody, "data.createContentPageLayout.hasErrors"));

        String id = getString(createBody, "data.createContentPageLayout.id");

        Map<String, Object> editBody = executeUsingPost("mutation { editContentPageLayout(input: { originalContentPageLayoutName: \"" + ORIGINAL_CONTENT_PAGE_LAYOUT_NAME + "\", contentPageLayoutName: \"" + NEW_CONTENT_PAGE_LAYOUT_NAME + "\", sortOrder: \"" + NEW_SORT_ORDER + "\", description: \"" + NEW_DESCRIPTION + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(editBody, "data.editContentPageLayout.hasErrors"));

        Map<String, Object> queryBody = executeUsingPost("query { contentPageLayout(id: \"" + id + "\") { id contentPageLayoutName, sortOrder description } }");
        Assert.assertEquals(id, getString(queryBody, "data.contentPageLayout.id"));
        Assert.assertEquals(NEW_CONTENT_PAGE_LAYOUT_NAME, getString(queryBody, "data.contentPageLayout.contentPageLayoutName"));
        Assert.assertEquals(NEW_SORT_ORDER, getInteger(queryBody, "data.contentPageLayout.sortOrder"));
        Assert.assertEquals(NEW_DESCRIPTION, getString(queryBody, "data.contentPageLayout.description"));

        Map<String, Object> deleteBody = executeUsingPost("mutation { deleteContentPageLayout(input: { contentPageLayoutName: \"" + NEW_CONTENT_PAGE_LAYOUT_NAME + "\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(deleteBody, "data.deleteContentPageLayout.hasErrors"));
    }
}
