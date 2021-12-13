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

package com.echothree.control.user.test.queue.graphql;

import com.echothree.control.user.test.common.graphql.GraphQlTestCase;
import com.echothree.model.control.queue.common.QueueConstants;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class QueueTypeTest
        extends GraphQlTestCase {

    @Test
    public void queueTypeByQueueTypeNameNoAuth()
            throws Exception {
        var queueTypeBody = executeUsingPost("query { queueType(queueTypeName: \"" + QueueConstants.QueueType_INDEXING + "\") { queueTypeName isDefault sortOrder description id unformattedOldestQueuedEntityTime oldestQueuedEntityTime unformattedLatestQueuedEntityTime latestQueuedEntityTime queuedEntityCount } }");
        Assert.assertNull(getMap(queueTypeBody, "data.queueType"));
    }

    @Test
    public void queueTypeByQueueTypeName()
            throws Exception {
        var loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        var queueTypeBody = executeUsingPost("query { queueType(queueTypeName: \"" + QueueConstants.QueueType_INDEXING + "\") { queueTypeName isDefault sortOrder description id unformattedOldestQueuedEntityTime oldestQueuedEntityTime unformattedLatestQueuedEntityTime latestQueuedEntityTime queuedEntityCount } }");

        var queueType = getMap(queueTypeBody, "data.queueType");

        Assert.assertNotNull(queueType);
    }

    @Test
    public void queueTypesNoAuth()
            throws Exception {
        var queueTypesBody = executeUsingPost("query { queueTypes { queueTypeName isDefault sortOrder description id unformattedOldestQueuedEntityTime oldestQueuedEntityTime unformattedLatestQueuedEntityTime latestQueuedEntityTime queuedEntityCount } }");
        List<Map<String, Object>> queueTypes = getList(queueTypesBody, "data.queueTypes");

        Assert.assertTrue(queueTypes.size() == 0);
    }

    @Test
    public void queueTypes()
            throws Exception {
        var loginBody = executeUsingPost("mutation { employeeLogin(input: { username: \"test e\", password: \"password\", companyName: \"TEST_COMPANY\", clientMutationId: \"1\" }) { hasErrors } }");
        Assert.assertFalse(getBoolean(loginBody, "data.employeeLogin.hasErrors"));

        var queueTypesBody = executeUsingPost("query { queueTypes { queueTypeName isDefault sortOrder description id unformattedOldestQueuedEntityTime oldestQueuedEntityTime unformattedLatestQueuedEntityTime latestQueuedEntityTime queuedEntityCount } }");
        List<Map<String, Object>> queueTypes = getList(queueTypesBody, "data.queueTypes");

        Assert.assertTrue(queueTypes.size() > 0);

        boolean foundIndexing = false;
        for(Map<String, Object> queueType : queueTypes) {
            if(getString(queueType, "queueTypeName").equals(QueueConstants.QueueType_INDEXING)) {
                foundIndexing = true;
                break;
            }
        }

        Assert.assertTrue(foundIndexing);
    }

}
