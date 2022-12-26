// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
                        hasErrors
                    }
                }
                """);

        assertThat(getBoolean(body, "data.idle.hasErrors")).isFalse();
    }
    
    @Test
    public void idleCompleteResultTest()
            throws Exception {
        var body = executeUsingPost("""
                mutation {
                    idle(input: { clientMutationId: "1" }) {
                        clientMutationId
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
                """);
        
        assertThat(getString(body, "data.idle.clientMutationId")).isEqualTo("1");
        assertThat(getBoolean(body, "data.idle.hasWarnings")).isFalse();
        assertThat(getBoolean(body, "data.idle.hasErrors")).isFalse();
        assertThat(getBoolean(body, "data.idle.hasSecurityMessages")).isFalse();
        assertThat(getObject(body, "data.idle.securityMessages")).isNull();
        assertThat(getBoolean(body, "data.idle.hasValidationErrors")).isFalse();
        assertThat(getObject(body, "data.idle.validationErrors")).isNull();
        assertThat(getBoolean(body, "data.idle.hasExecutionWarnings")).isFalse();
        assertThat(getObject(body, "data.idle.executionWarnings")).isNull();
        assertThat(getBoolean(body, "data.idle.hasExecutionErrors")).isFalse();
        assertThat(getObject(body, "data.idle.executionErrors")).isNull();
    }
    
}
