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

package com.echothree.model.control.graphql.server.graphql.count;

import com.echothree.model.control.graphql.server.graphql.BaseEntityInstanceObject;
import com.echothree.model.control.graphql.server.graphql.ObjectLimiter;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CountedObjects<BEIO extends BaseEntityInstanceObject>
        implements CountingPaginatedData<BEIO> {

    ObjectLimiter objectLimiter;
    Iterable<BEIO> data;

    boolean hasPreviousPage;
    boolean hasNextPage;
    long cursor;

    public CountedObjects(ObjectLimiter objectLimiter, Iterable<BEIO> data) {
        this.objectLimiter = objectLimiter;
        this.data = data;

        hasPreviousPage = objectLimiter.getLimitOffset() > 0;
        hasNextPage = objectLimiter.getLimitCount() < (objectLimiter.getTotalCount() - objectLimiter.getLimitOffset());
        cursor = objectLimiter.getLimitOffset();
    }

    @Override
    public long getTotalCount() {
        return objectLimiter.getTotalCount();
    }

    @Override
    public String getCursor(BEIO beio) {
        return Long.toString(cursor += 1);
    }

    @Override
    public List<Edge<BEIO>> getEdges() {
        var edges = new ArrayList<Edge<BEIO>>();

        for(final BEIO beio : this) {
            edges.add(new DefaultEdge<>(beio, new DefaultConnectionCursor(getCursor(null))));
        }

        return edges;
    }

    @Override
    public PageInfo getPageInfo() {
        var startCursor = new DefaultConnectionCursor(Long.toString(objectLimiter.getLimitOffset() + 1));
        var endCursor = new DefaultConnectionCursor(Long.toString(objectLimiter.getLimitOffset() + objectLimiter.getLimitCount()));

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
    public Iterator<BEIO> iterator() {
        return data.iterator();
    }

}
