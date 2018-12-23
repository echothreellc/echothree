// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.index.common.IndexConstants;
import com.echothree.model.control.index.server.IndexControl;
import com.echothree.model.data.core.common.pk.EntityInstancePK;
import com.echothree.model.data.index.server.entity.Index;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.io.IOException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PositiveScoresOnlyCollector;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.TotalHitCountCollector;

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
    
    protected static final ScoreDoc[] NO_HITS = {};
    
    protected int getNumHits(IndexSearcher is)
            throws IOException {
        TotalHitCountCollector collector = new TotalHitCountCollector();
        
        is.search(query, collector);
        
        return collector.getTotalHits();
    }
    
    @Override
    protected EntityInstancePKHolder useIndex(IndexReader ir)
            throws IOException {
        final IndexSearcher is = new IndexSearcher(ir);
        EntityInstancePKHolder entityInstancePKHolder = null;

        if(!eea.hasExecutionErrors()) {
            final int numHits = getNumHits(is);
            ScoreDoc[] hits;

            if(numHits == 0) {
                hits = NO_HITS;
            } else {
                if(sort == null) {
                    final TopDocsCollector topDocsCollector = TopScoreDocCollector.create(numHits);
                    final Collector collector = new PositiveScoresOnlyCollector(topDocsCollector);

                    is.search(query, collector);

                    hits = topDocsCollector.topDocs().scoreDocs;
                } else {
                    final TopFieldDocs topFieldDocs = is.search(query, numHits, sort);

                    hits = topFieldDocs.scoreDocs;
                }
            }

            final int hitCount = hits.length;
            if(hitCount > 0) {
                entityInstancePKHolder = new EntityInstancePKHolder(hitCount);

                for(int i = 0 ; i < hitCount ; i++) {
                    entityInstancePKHolder.add(new EntityInstancePK(is.doc(hits[i].doc).get(IndexConstants.IndexField_EntityInstanceId)), i);
                }
            }
        }
        
        return entityInstancePKHolder == null ? new EntityInstancePKHolder(0) : entityInstancePKHolder;
    }

}
