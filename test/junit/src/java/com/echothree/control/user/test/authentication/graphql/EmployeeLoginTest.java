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

package com.echothree.control.user.test.authentication.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class EmployeeLoginTest
        extends GraphQlTestCase {

    @Test
    public void employeeLoginSuccessfulTest()
            throws Exception {
        var body = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");

        Assert.assertFalse(getBoolean(body, "data.employeeLogin.hasErrors"));
    }
    
    @Test
    public void employeeLoginFailureTest()
            throws Exception {
        var body = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"not-the-password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");

        Assert.assertTrue(getBoolean(body, "data.employeeLogin.hasErrors"));
    }
    
}
