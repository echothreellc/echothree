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

package com.echothree.control.user.test.authentication.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class SetPasswordTest
        extends GraphQlTestCase {

    @Test
    public void setPasswordNoLoginFailureTest()
            throws Exception {
        Map<String, Object> setPasswordBody = executeUsingPost("mutation { setPassword(input: { oldPassword: \"password\", newPassword1: \"newpassword\", newPassword2: \"newpassword\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertTrue(getBoolean(setPasswordBody, "data.setPassword.hasErrors"));
    }

    @Test
    public void setPasswordForEmployeesTest()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        Map<String, Object> setPassword1Body = executeUsingPost("mutation { setPassword(input: { oldPassword: \"password\", newPassword1: \"newpassword\", newPassword2: \"newpassword\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(setPassword1Body, "data.setPassword.hasErrors"));

        Map<String, Object> setPassword2Body = executeUsingPost("mutation { setPassword(input: { oldPassword: \"newpassword\", newPassword1: \"password\", newPassword2: \"password\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(setPassword2Body, "data.setPassword.hasErrors"));
    }

    @Test
    public void setPasswordForCustomersTest()
            throws Exception {
        Map<String, Object> loginBody = executeUsingPost("mutation { customerLogin(input: { username: \"TestC@echothree.com\", password: \"password\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.customerLogin.hasErrors"));

        Map<String, Object> setPassword1Body = executeUsingPost("mutation { setPassword(input: { oldPassword: \"password\", newPassword1: \"newpassword\", newPassword2: \"newpassword\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(setPassword1Body, "data.setPassword.hasErrors"));

        Map<String, Object> setPassword2Body = executeUsingPost("mutation { setPassword(input: { oldPassword: \"newpassword\", newPassword1: \"password\", newPassword2: \"password\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(setPassword2Body, "data.setPassword.hasErrors"));
    }
    
}
