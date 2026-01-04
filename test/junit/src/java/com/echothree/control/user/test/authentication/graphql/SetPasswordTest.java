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

package com.echothree.control.user.test.authentication.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.Test;

public class SetPasswordTest
        extends GraphQlTestCase {

    @Test
    public void setPasswordNoLoginFailureTest()
            throws Exception {
        var setPasswordBody = executeUsingPost("""
                mutation {
                    setPassword(input: { oldPassword: "password", newPassword1: "newpassword", newPassword2: "newpassword", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(setPasswordBody, "data.setPassword.commandResult.hasErrors")).isTrue();
    }

    @Test
    public void setPasswordForEmployeesTest()
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

        var setPassword1Body = executeUsingPost("""
                mutation {
                    setPassword(input: { oldPassword: "password", newPassword1: "newpassword", newPassword2: "newpassword", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(setPassword1Body, "data.setPassword.commandResult.hasErrors")).isFalse();

        var setPassword2Body = executeUsingPost("""
                mutation {
                    setPassword(input: { oldPassword: "newpassword", newPassword1: "password", newPassword2: "password", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(setPassword2Body, "data.setPassword.commandResult.hasErrors")).isFalse();
    }

    @Test
    public void setPasswordForCustomersTest()
            throws Exception {
        var loginBody = executeUsingPost("""
                mutation {
                    customerLogin(input: { username: "TestC@echothree.com", password: "password", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(loginBody, "data.customerLogin.commandResult.hasErrors")).isFalse();

        var setPassword1Body = executeUsingPost("""
                mutation {
                    setPassword(input: { oldPassword: "password", newPassword1: "newpassword", newPassword2: "newpassword", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(setPassword1Body, "data.setPassword.commandResult.hasErrors")).isFalse();

        var setPassword2Body = executeUsingPost("""
                mutation {
                    setPassword(input: { oldPassword: "newpassword", newPassword1: "password", newPassword2: "password", clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(setPassword2Body, "data.setPassword.commandResult.hasErrors")).isFalse();
    }
    
}
