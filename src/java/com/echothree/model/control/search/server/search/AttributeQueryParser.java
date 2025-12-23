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

import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

public class AttributeQueryParser
        extends QueryParser {

    protected AttributeQueryParserUtils attributeQueryParserUtils;

    public AttributeQueryParser(final ExecutionErrorAccumulator eea, final Set<String> dateFields, final Set<String> dateTimeFields,
            final Set<String> intFields, final Set<String> longFields, final Set<String> floatFields, final Set<String> doubleFields,
            final EntityType entityType, final UserVisit userVisit, final String field, final Analyzer a) {
        super(field, a);

        attributeQueryParserUtils = new AttributeQueryParserUtils(eea, dateFields, dateTimeFields, intFields, longFields,
                floatFields, doubleFields, entityType, userVisit);
    }
    
    @Override
    protected Query getFieldQuery(String field, String queryText, boolean quoted)
            throws ParseException {
        var query = attributeQueryParserUtils.getFieldQuery(field, null, null, queryText, quoted);

        return query == null ? super.getFieldQuery(field, queryText, quoted) : query;
    }

    @Override
    protected Query newTermQuery(final Term term, final float boost) {
        var query = attributeQueryParserUtils.newTermQuery(term);

        return query == null ? super.newTermQuery(term, boost) : query;
    }

    @Override
    protected Query getRangeQuery(final String field, final String min, final String max, final boolean startInclusive, final boolean endInclusive) {
        return newRangeQuery(field, min, max, startInclusive, endInclusive);
    }

    @Override
    protected Query newRangeQuery(final String field, final String min, final String max, final boolean startInclusive, final boolean endInclusive) {
        var query = attributeQueryParserUtils.newRangeQuery(field, null, min, max, startInclusive, endInclusive);
        
        return query == null ? super.newRangeQuery(field, min, max, startInclusive, endInclusive) : query;
    }

}
