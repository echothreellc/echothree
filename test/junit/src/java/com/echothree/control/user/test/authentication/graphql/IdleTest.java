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

public class IdleTest
        extends GraphQlTestCase {

    @Test
    public void idleTest()
            throws Exception {
        Map<String, Object> body = executeUsingPost("mutation {idle(input: {clientMutationId: \"1\"}) { hasErrors } }");
        
        Assert.assertFalse(getBoolean(body, "data.idle.hasErrors"));
    }
    
    @Test
    public void idleCompleteResultTest()
            throws Exception {
        Map<String, Object> body = executeUsingPost("mutation { idle(input: { clientMutationId: \"1\" }) { clientMutationId hasWarnings hasErrors hasSecurityMessages securityMessages { key message } hasValidationErrors validationErrors { property key message } hasExecutionWarnings executionWarnings { key message } hasExecutionErrors executionErrors { key message } } }");
        
        Assert.assertEquals("1", getString(body, "data.idle.clientMutationId"));
        Assert.assertFalse(getBoolean(body, "data.idle.hasWarnings"));
        Assert.assertFalse(getBoolean(body, "data.idle.hasErrors"));
        Assert.assertFalse(getBoolean(body, "data.idle.hasSecurityMessages"));
        Assert.assertNull(getObject(body, "data.idle.securityMessages"));
        Assert.assertFalse(getBoolean(body, "data.idle.hasValidationErrors"));
        Assert.assertNull(getObject(body, "data.idle.validationErrors"));
        Assert.assertFalse(getBoolean(body, "data.idle.hasExecutionWarnings"));
        Assert.assertNull(getObject(body, "data.idle.executionWarnings"));
        Assert.assertFalse(getBoolean(body, "data.idle.hasExecutionErrors"));
        Assert.assertNull(getObject(body, "data.idle.executionErrors"));
    }
    
}
