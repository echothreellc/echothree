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
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Assert;
import org.junit.Test;

public class AppearanceTest
        extends GraphQlTestCase {

    private static final String TEST_APPEARANCE = "TEST_APPEARANCE";

    @Test
    public void appearancesQueryNoAuth()
            throws Exception {
        var appearancesBody = executeUsingPost("query { appearances { appearanceName } }");

        var appearances = getList(appearancesBody, "data.appearances");

        assertThat(appearances).size().isEqualTo(0);
    }

    @Test
    public void appearanceQueryNoAuth()
            throws Exception {
        var appearanceBody = executeUsingPost("query { appearance(appearanceName: \"" + TEST_APPEARANCE + "\") { appearanceName } }");

        var appearance = getMap(appearanceBody, "data.appearance");

        assertThat(appearance).isNull();
    }

    @Test
    public void appearancesQuery()
            throws Exception {
        var loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        var appearancesBody = executeUsingPost("query { appearances { appearanceName } }");

        var appearances = getList(appearancesBody, "data.appearances");

        assertThat(appearances).size().isGreaterThan(0);
    }

    @Test
    public void appearanceQuery()
            throws Exception {
        var loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        var appearanceBody = executeUsingPost("query { appearance(appearanceName: \"" + TEST_APPEARANCE + "\") { appearanceName } }");

        var appearanceName = getString(appearanceBody, "data.appearance.appearanceName");

        assertThat(appearanceName).isEqualTo(TEST_APPEARANCE);
    }

}
