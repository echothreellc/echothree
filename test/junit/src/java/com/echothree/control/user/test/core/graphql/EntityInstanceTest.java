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
import com.echothree.model.control.core.common.EntityTypes;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Assert;
import org.junit.Test;

public class EntityInstanceTest
        extends GraphQlTestCase {

    @Test
    public void entityTypeWithInstancesQueryUsingNames()
            throws Exception {
        var loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

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

}
