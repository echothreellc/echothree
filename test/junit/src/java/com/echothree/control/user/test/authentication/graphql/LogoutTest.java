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
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.junit.Test;

public class LogoutTest
        extends GraphQlTestCase {

    @Test
    public void singleLogoutTest()
            throws Exception {
        var body = executeUsingPost("""
                mutation {
                    logout(input: { clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(body, "data.logout.commandResult.hasErrors")).isFalse();
    }
    
    @Test
    public void multipleLogoutTest()
            throws Exception {
        var body1 = executeUsingPost("""
                mutation {
                    logout(input: { clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(body1, "data.logout.commandResult.hasErrors")).isFalse();

        var body2 = executeUsingPost("""
                mutation {
                    logout(input: { clientMutationId: "1" }) {
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(body2, "data.logout.commandResult.hasErrors")).isFalse();
    }
    
}
