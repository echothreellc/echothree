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

package com.echothree.model.control.graphql.server.graphql.count;

import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Connections {

    private Connections() {
    }

    public static final EmptyConnection EMPTY_CONNECTION = new EmptyConnection<>();

    @SuppressWarnings("unchecked")
    public static <T> CountingPaginatedData<T> emptyConnection() {
        return (CountingPaginatedData<T>)EMPTY_CONNECTION;
    }

    private static class EmptyConnection<T>
            implements CountingPaginatedData<T> {

        @Override
        public long getTotalCount() {
            return 0;
        }

        @Override
        public String getCursor(T t) {
            return null;
        }

        @Override
        public List<Edge<T>> getEdges() {
            return Collections.emptyList();
        }

        @Override
        public PageInfo getPageInfo() {
            return new DefaultPageInfo(null, null, false, false);
        }

        @Override
        public boolean hasNextPage() {
            return false;
        }

        @Override
        public boolean hasPreviousPage() {
            return false;
        }

        @Override
        public Iterator<T> iterator() {
            return Collections.<T>emptyList().iterator();
        }

    }


}
