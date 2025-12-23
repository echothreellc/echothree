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

package com.echothree.control.user.test.user.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.junit.Test;

public class UserSessionTest
        extends GraphQlTestCase {

    @Test
    public void userSessionQuery()
            throws Exception {
        var employeeLoginBody = executeUsingPost("""
                mutation { 
                    employeeLogin(input: { 
                        username: "test e", 
                        password: "password", 
                        companyName: "TEST_COMPANY", 
                        clientMutationId: "1"
                    })
                    {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(employeeLoginBody, "data.employeeLogin.commandResult.hasErrors")).isFalse();

        var userSessionBody = executeUsingPost("""
                query {
                    userSession {
                        identityVerifiedTime {
                          unformattedTime
                        }
                    }
                }
                """);

        assertThat(getLong(userSessionBody, "data.userSession.identityVerifiedTime.unformattedTime")).isNotZero();
    }

}
