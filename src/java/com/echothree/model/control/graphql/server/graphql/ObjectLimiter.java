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

package com.echothree.model.control.graphql.server.graphql;

import com.echothree.util.common.transfer.Limit;
import com.echothree.util.server.persistence.ThreadSession;
import com.echothree.util.server.validation.Validator;
import graphql.schema.DataFetchingEnvironment;
import java.util.HashMap;
import java.util.Map;

public class ObjectLimiter
        implements AutoCloseable {

    private static final String PARAMETER_FIRST = "first";
    private static final String PARAMETER_AFTER = "after";
    private static final String PARAMETER_LAST = "last";
    private static final String PARAMETER_BEFORE = "before";

    DataFetchingEnvironment env;
    String entityName;

    Map<String, Limit> limits;
    Limit savedLimit;
    long count;
    long limitOffset;
    long limitCount;

    public long getCount() {
        return count;
    }

    public long getLimitOffset() {
        return limitOffset;
    }

    public long getLimitCount() {
        return limitCount;
    }

    public ObjectLimiter(final DataFetchingEnvironment env, final String entityName, final long count) {
        this.env = env;
        this.entityName = entityName;
        this.count = count;

        var session = ThreadSession.currentSession();
        var after = Validator.validateUnsignedLong(env.getArgument(PARAMETER_AFTER));
        var before = Validator.validateUnsignedLong(env.getArgument(PARAMETER_BEFORE));
        var first = env.<Integer>getArgument(PARAMETER_FIRST);
        var afterEdge = after == null ? null : Long.valueOf(after);
        var last = env.<Integer>getArgument(PARAMETER_LAST);
        var beforeEdge = before == null ? null : Long.valueOf(before);

        // Initialize edges to be allEdges.
        limitOffset = 0;
        limitCount = count;

        // Source: https://relay.dev/graphql/connections.htm
        // 4.4 Pagination algorithm
        if(first != null || afterEdge != null || last != null || beforeEdge != null) {
            limits = session.getLimits();
            if(limits == null) {
                limits = new HashMap<>();
                session.setLimits(limits);
            }

            savedLimit = limits.get(entityName);

            // If after is set: && If afterEdge exists:
            if(afterEdge != null && afterEdge <= count) {
                // Remove all elements of edges before and including afterEdge.
                limitOffset = afterEdge;
                limitCount -= afterEdge;
            }

            // If before is set: && If beforeEdge exists:
            if(beforeEdge != null && beforeEdge > 0 && beforeEdge <= count) {
                // Remove all elements of edges after and including beforeEdge.
                limitCount = beforeEdge - limitOffset - 1;
            }

            // TODO: If first is less than 0: Throw an error. (Currently the value is ignored.)

            // If first is set:
            if(first != null && first > 0) {
                // If edges has length greater than first:
                if(limitCount > first) {
                    // Slice edges to be of length first by removing edges from the end of edges.
                    limitCount = first;
                }
            }

            // TODO: If last is less than 0: Throw an error. (Currently the value is ignored.)

            // If last is set:
            if(last != null && last > 0) {
                // If edges has length greater than last:
                if(limitCount > last) {
                    // Slice edges to be of length last by removing edges from the start of edges.
                    limitOffset = limitOffset + limitCount - last;
                    limitCount = last;
                }
            }

            limits.put(entityName, new Limit(Long.toString(limitCount), Long.toString(limitOffset)));
        }
    }

    @Override
    public void close() {
        if(savedLimit != null) {
            // Restore previous Limit;
            limits.put(entityName, savedLimit);
        }
    }

}
