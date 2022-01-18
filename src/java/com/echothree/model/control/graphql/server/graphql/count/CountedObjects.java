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

import com.echothree.model.control.graphql.server.graphql.ObjectLimiter;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CountedObjects<T>
        implements CountingPaginatedData<T> {

    ObjectLimiter objectLimiter;
    List<T> entities;

    boolean hasPreviousPage;
    boolean hasNextPage;
    long cursor;

    public CountedObjects(ObjectLimiter objectLimiter, List<T> entities) {
        this.objectLimiter = objectLimiter;
        this.entities = entities;

        hasPreviousPage = objectLimiter.getLimitOffset() > 0;
        hasNextPage = objectLimiter.getLimitCount() < (objectLimiter.getTotalCount() - objectLimiter.getLimitOffset());
        cursor = objectLimiter.getLimitOffset();
    }

    @Override
    public long getTotalCount() {
        return objectLimiter.getTotalCount();
    }

    @Override
    public String getCursor(T entity) {
        return Long.toString(cursor += 1);
    }

    @Override
    public List<Edge<T>> getEdges() {
        var edges = new ArrayList<Edge<T>>();

        for(final T beio : this) {
            edges.add(new DefaultEdge<>(beio, new DefaultConnectionCursor(getCursor(null))));
        }

        return edges;
    }

    @Override
    public PageInfo getPageInfo() {
        var entitiesCount = entities.size();
        var startCursor = entitiesCount == 0 ? null : new DefaultConnectionCursor(Long.toString(objectLimiter.getLimitOffset() + 1));
        var endCursor = entitiesCount == 0 ? null : new DefaultConnectionCursor(Long.toString(objectLimiter.getLimitOffset() + objectLimiter.getLimitCount()));

        return new DefaultPageInfo(startCursor, endCursor, hasPreviousPage, hasNextPage);
    }

    @Override
    public boolean hasNextPage() {
        return hasNextPage;
    }

    @Override
    public boolean hasPreviousPage() {
        return hasPreviousPage;
    }

    @Override
    public Iterator<T> iterator() {
        return entities.iterator();
    }

}
