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

package com.echothree.control.user.test.core.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import com.echothree.model.control.core.common.ComponentVendors;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Assert;
import org.junit.Test;

public class ComponentVendorTest
        extends GraphQlTestCase {

    @Test
    public void componentVendorsQueryNoAuth()
            throws Exception {
        var componentVendorsBody = executeUsingPost("query { componentVendors { componentVendorName } }");

        var componentVendors = getList(componentVendorsBody, "data.componentVendors");

        assertThat(componentVendors).size().isEqualTo(0);
    }

    @Test
    public void componentVendorQueryNoAuth()
            throws Exception {
        var componentVendorBody = executeUsingPost("query { componentVendor(componentVendorName: \"" + ComponentVendors.ECHOTHREE + "\") { componentVendorName } }");

        var componentVendor = getMap(componentVendorBody, "data.componentVendor");

        assertThat(componentVendor).isNull();
    }

    @Test
    public void componentVendorsQuery()
            throws Exception {
        var loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        var componentVendorsBody = executeUsingPost("query { componentVendors { componentVendorName } }");

        var componentVendors = getList(componentVendorsBody, "data.componentVendors");

        assertThat(componentVendors).size().isGreaterThan(0);
    }

    @Test
    public void componentVendorQuery()
            throws Exception {
        var loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        var componentVendorBody = executeUsingPost("query { componentVendor(componentVendorName: \"" + ComponentVendors.ECHOTHREE + "\") { componentVendorName } }");

        var componentVendorName = getString(componentVendorBody, "data.componentVendor.componentVendorName");

        assertThat(componentVendorName).isEqualTo(ComponentVendors.ECHOTHREE.toString());
    }



}
