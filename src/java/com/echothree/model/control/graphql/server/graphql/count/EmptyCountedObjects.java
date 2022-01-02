// --------------------------------------------------------------------------------
// Copyright 2002-2022 Echo Three, LLC
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

public class EmptyCountedObjects<T>
        implements CountingPaginatedData<T> {

    public static final EmptyCountedObjects EMPTY_COUNTED_OBJECTS = new EmptyCountedObjects<>();

    @SuppressWarnings("unchecked")
    public static final <T> CountingPaginatedData<T> emptyCountedObjects() {
        return (CountingPaginatedData<T>)EMPTY_COUNTED_OBJECTS;
    }

    private EmptyCountedObjects() {
    }

    @Override
    public long getTotalCount() {
        return 0;
    }

    @Override
    public String getCursor(T beio) {
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
