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

public class IdleTest
        extends GraphQlTestCase {

    @Test
    public void idleTest()
            throws Exception {
        var body = executeUsingPost("""
                mutation {
                    idle(input: {clientMutationId: "1"}) { 
                        commandResult {
                            hasErrors
                        }
                    }
                }
                """);

        assertThat(getBoolean(body, "data.idle.commandResult.hasErrors")).isFalse();
    }
    
    @Test
    public void idleCompleteResultTest()
            throws Exception {
        var body = executeUsingPost("""
                mutation {
                    idle(input: { clientMutationId: "1" }) {
                        clientMutationId
                        commandResult {
                            hasWarnings
                            hasErrors
                            hasSecurityMessages
                            securityMessages {
                                key message
                            }
                            hasValidationErrors
                            validationErrors {
                                property key message
                            }
                            hasExecutionWarnings
                            executionWarnings {
                                key
                                message
                            }
                            hasExecutionErrors
                            executionErrors {
                                key
                                message
                            }
                        }
                    }
                }
                """);
        
        assertThat(getString(body, "data.idle.clientMutationId")).isEqualTo("1");
        assertThat(getBoolean(body, "data.idle.commandResult.hasWarnings")).isFalse();
        assertThat(getBoolean(body, "data.idle.commandResult.hasErrors")).isFalse();
        assertThat(getBoolean(body, "data.idle.commandResult.hasSecurityMessages")).isFalse();
        assertThat(getObject(body, "data.idle.commandResult.securityMessages")).isNull();
        assertThat(getBoolean(body, "data.idle.commandResult.hasValidationErrors")).isFalse();
        assertThat(getObject(body, "data.idle.commandResult.validationErrors")).isNull();
        assertThat(getBoolean(body, "data.idle.commandResult.hasExecutionWarnings")).isFalse();
        assertThat(getObject(body, "data.idle.commandResult.executionWarnings")).isNull();
        assertThat(getBoolean(body, "data.idle.commandResult.hasExecutionErrors")).isFalse();
        assertThat(getObject(body, "data.idle.commandResult.executionErrors")).isNull();
    }
    
}
