// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Assert;
import org.junit.Test;

public class EntityInstanceTest
        extends GraphQlTestCase {

    @Test
    public void entityTypeWithInstancesQueryUsingNames()
            throws Exception {
        var loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        assertThat(getBoolean(loginBody, "data.employeeLogin.hasErrors")).isFalse();

        var entityTypeBody = executeUsingPost("query { entityType(componentVendorName: \"" + ComponentVendors.ECHOTHREE + "\", entityTypeName: \"" + EntityTypes.GlAccount + "\") { componentVendor { componentVendorName } entityTypeName entityInstanceCount entityInstances { ulid } } }");

        var componentVendorName = getString(entityTypeBody, "data.entityType.componentVendor.componentVendorName");
        var entityTypeName = getString(entityTypeBody, "data.entityType.entityTypeName");
        var entityInstanceCount = getLong(entityTypeBody, "data.entityType.entityInstanceCount");
        var entityInstances = getList(entityTypeBody, "data.entityType.entityInstances");

        assertThat(componentVendorName).isEqualTo(ComponentVendors.ECHOTHREE.toString());
        assertThat(entityTypeName).isEqualTo(EntityTypes.GlAccount.toString());
        assertThat(entityInstanceCount).isGreaterThan(0);
        assertThat(entityInstances).size().isEqualTo(entityInstanceCount);
    }

    @Test
    public void entityInstancesQueryUsingNamesNoAuth()
            throws Exception {
        var entityInstancesBody = executeUsingPost("query { entityInstances(componentVendorName: \"" + ComponentVendors.ECHOTHREE + "\", entityTypeName: \"" + EntityTypes.GlAccount + "\") { ulid } }");

        var entityInstances = getList(entityInstancesBody, "data.entityInstances");

        assertThat(entityInstances).size().isEqualTo(0);
    }

    @Test
    public void entityInstancesQueryUsingNames()
            throws Exception {
        var loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        assertThat(getBoolean(loginBody, "data.employeeLogin.hasErrors")).isFalse();

        var entityInstancesBody = executeUsingPost("query { entityInstances(componentVendorName: \"" + ComponentVendors.ECHOTHREE + "\", entityTypeName: \"" + EntityTypes.GlAccount + "\") { ulid } }");

        var entityInstances = getList(entityInstancesBody, "data.entityInstances");

        assertThat(entityInstances).size().isGreaterThan(0);
    }

    @Test
    public void entityInstanceQueryUsingIdNoAuth()
            throws Exception {
        var loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        assertThat(getBoolean(loginBody, "data.employeeLogin.hasErrors")).isFalse();

        var entityTypeBody = executeUsingPost("query { entityType(componentVendorName: \"" + ComponentVendors.ECHOTHREE + "\", entityTypeName: \"" + EntityTypes.GlAccount + "\") { entityInstances { ulid } } }");

        var entityInstances = getList(entityTypeBody, "data.entityType.entityInstances");
        var entityInstance = getObject(entityInstances, "[0]");
        var ulid = getString(entityInstance, "ulid");

        var logoutBody = executeUsingPost("mutation { logout(input: { clientMutationId: \"1\" }) { hasErrors } }");
        assertThat(getBoolean(logoutBody, "data.logout.hasErrors")).isFalse();

        var entityInstanceBody = executeUsingPost("query { entityInstance(id: \"" + ulid + "\") { entityType { entityTypeName componentVendor { componentVendorName } } } }");

        var componentVendorName = getString(entityInstanceBody, "data.entityInstance.entityType.componentVendor.componentVendorName");
        var entityTypeName = getString(entityInstanceBody, "data.entityInstance.entityType.entityTypeName");

        assertThat(componentVendorName).isEqualTo(ComponentVendors.ECHOTHREE.toString());
        assertThat(entityTypeName).isEqualTo(EntityTypes.GlAccount.toString());
    }

    @Test
    public void entityInstanceQueryUsingId()
            throws Exception {
        var loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        assertThat(getBoolean(loginBody, "data.employeeLogin.hasErrors")).isFalse();

        var entityTypeBody = executeUsingPost("query { entityType(componentVendorName: \"" + ComponentVendors.ECHOTHREE + "\", entityTypeName: \"" + EntityTypes.GlAccount + "\") { entityInstances { ulid } } }");

        var entityInstances = getList(entityTypeBody, "data.entityType.entityInstances");
        var entityInstance = getObject(entityInstances, "[0]");
        var ulid = getString(entityInstance, "ulid");

        var entityInstanceBody = executeUsingPost("query { entityInstance(id: \"" + ulid + "\") { entityType { entityTypeName componentVendor { componentVendorName } } } }");

        var componentVendorName = getString(entityInstanceBody, "data.entityInstance.entityType.componentVendor.componentVendorName");
        var entityTypeName = getString(entityInstanceBody, "data.entityInstance.entityType.entityTypeName");

        assertThat(componentVendorName).isEqualTo(ComponentVendors.ECHOTHREE.toString());
        assertThat(entityTypeName).isEqualTo(EntityTypes.GlAccount.toString());
    }

}
