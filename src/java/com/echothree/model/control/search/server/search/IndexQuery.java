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

package com.echothree.model.control.search.server.search;

import com.echothree.model.control.index.common.IndexFields;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.io.IOException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopScoreDocCollectorManager;
import org.apache.lucene.search.TotalHitCountCollectorManager;

public class IndexQuery
        extends BaseIndex<EntityInstancePKHolder> {
    
    private Sort sort;
    private Query query;
    
    private void init(Sort sort, Query query) {
        this.sort = sort;
        this.query = query;
    }
    
    protected IndexQuery(final ExecutionErrorAccumulator eea, IndexControl indexControl, Index index, Sort sort, Query query) {
        super(eea, indexControl, index);
        init(sort, query);
    }

    private int getNumHits(IndexSearcher is)
            throws IOException {
        var totalHitCountCollectorManager = new TotalHitCountCollectorManager(is.getSlices());

        return is.search(query, totalHitCountCollectorManager);
    }

    private static final ScoreDoc[] NO_HITS = {};

    private ScoreDoc[] getHits(final int numHits, IndexSearcher is)
            throws IOException {
        ScoreDoc[] hits;

        if(numHits == 0) {
            hits = NO_HITS;
        } else {
            if(sort == null) {
                var topScoreDocCollectorManager = new TopScoreDocCollectorManager(numHits, Integer.MAX_VALUE);
                var topDocs = is.search(query, topScoreDocCollectorManager);

                hits = topDocs.scoreDocs;
            } else {
                final var topFieldDocs = is.search(query, numHits, sort);

                hits = topFieldDocs.scoreDocs;
            }
        }

        return hits;
    }

    @Override
    protected EntityInstancePKHolder useIndex(IndexReader ir)
            throws IOException {
        final var is = new IndexSearcher(ir);
        EntityInstancePKHolder entityInstancePKHolder = null;

        if(!eea.hasExecutionErrors()) {
            final var numHits = getNumHits(is);
            final var hits = getHits(numHits, is);
            final var hitCount = hits.length;

            if(hitCount > 0) {
                var storedFields = is.storedFields();

                entityInstancePKHolder = new EntityInstancePKHolder(hitCount);

                for(var i = 0; i < hitCount ; i++) {
                    entityInstancePKHolder.add(new EntityInstancePK(storedFields.document(hits[i].doc).get(IndexFields.entityInstanceId.name())), i);
                }
            }
        }

        // If there were Execution Errors or if there were no hits then ensure we do not return a null.
        return entityInstancePKHolder == null ? new EntityInstancePKHolder(0) : entityInstancePKHolder;
    }

}
