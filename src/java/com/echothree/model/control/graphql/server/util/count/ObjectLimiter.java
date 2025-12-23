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

package com.echothree.model.control.graphql.server.util.count;

import com.echothree.util.common.transfer.Limit;
import com.echothree.util.server.persistence.ThreadSession;
import graphql.schema.DataFetchingEnvironment;
import java.util.Map;

public class ObjectLimiter
        implements AutoCloseable {

    private static final String PARAMETER_FIRST = "first";
    private static final String PARAMETER_AFTER = "after";
    private static final String PARAMETER_LAST = "last";
    private static final String PARAMETER_BEFORE = "before";

    private final DataFetchingEnvironment env;
    private final String componentVendorName;
    private final String entityTypeName;
    private final long totalCount;

    private Map<String, Limit> limits;
    private Limit savedLimit;
    private long limitOffset;
    private long limitCount;

    public String getComponentVendorName() {
        return componentVendorName;
    }

    public String getEntityTypeName() {
        return entityTypeName;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public long getLimitOffset() {
        return limitOffset;
    }

    public long getLimitCount() {
        return limitCount;
    }

    public ObjectLimiter(final DataFetchingEnvironment env, final String componentVendorName, final String entityTypeName,
            final long totalCount) {
        this.env = env;
        this.componentVendorName = componentVendorName;
        this.entityTypeName = entityTypeName;
        this.totalCount = totalCount;

        var session = ThreadSession.currentSession();
        var first = env.<Integer>getArgument(PARAMETER_FIRST);
        var after = GraphQlCursorUtils.getInstance().fromCursor(componentVendorName, entityTypeName, env.getArgument(PARAMETER_AFTER));
        var last = env.<Integer>getArgument(PARAMETER_LAST);
        var before = GraphQlCursorUtils.getInstance().fromCursor(componentVendorName, entityTypeName, env.getArgument(PARAMETER_BEFORE));

        // Initialize edges to be allEdges.
        limitOffset = 0;
        limitCount = totalCount;

        // Source: https://relay.dev/graphql/connections.htm
        // 4.4 Pagination algorithm
        if(first != null || after != null || last != null || before != null) {
            limits = session.getLimits();
            savedLimit = limits.get(entityTypeName);

            // If after is set: && If after exists:
            if(after != null && after <= totalCount) {
                // Remove all elements of edges before and including after.
                limitOffset = after;
                limitCount -= after;
            }

            // If before is set: && If before exists:
            if(before != null && before > 0 && before <= totalCount) {
                // Remove all elements of edges after and including before.
                limitCount = before - limitOffset - 1;
            }

            // TODO: If first is less than 0: Throw an error. (Currently no error is thrown.)

            // If first is set:
            if(first != null && first > 0) {
                // If edges has length greater than first:
                if(limitCount > first) {
                    // Slice edges to be of length first by removing edges from the end of edges.
                    limitCount = first;
                }
            }

            // TODO: If last is less than 0: Throw an error. (Currently no error is thrown.)

            // If last is set:
            if(last != null && last > 0) {
                // If edges has length greater than last:
                if(limitCount > last) {
                    // Slice edges to be of length last by removing edges from the start of edges.
                    limitOffset = limitOffset + limitCount - last;
                    limitCount = last;
                }
            }

            limits.put(entityTypeName, new Limit(Long.toString(limitCount), Long.toString(limitOffset)));
        }
    }

    @Override
    public void close() {
        if(limits != null) {
            // Restore previous Limit;
            limits.put(entityTypeName, savedLimit);
        }
    }

}
