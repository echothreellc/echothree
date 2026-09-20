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

package com.echothree.model.control.search.server.database;

import static com.echothree.model.jooq.server.tables.core.EntityLongAttributes.EntityLongAttributes;
import static com.echothree.model.jooq.server.tables.search.CachedExecutedSearchResults.CachedExecutedSearchResults;
import static com.echothree.model.jooq.server.tables.search.SearchResults.SearchResults;
import static org.jooq.impl.DSL.count;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EntityLongAttributeFacetQuery
        extends BaseFacetQuery<EntityLongAttributeFacetResult> {
    
    public EntityLongAttributeFacetQuery(UserVisitSearch userVisitSearch) {
        super(userVisitSearch);
    }
    
    public List<EntityLongAttributeFacetResult> execute(EntityAttribute entityAttribute) {
        var query = cachedExecutedSearch == null
                ? getDslContext()
                    .select(EntityLongAttributes.LONG_ATTRIBUTE.as("LongAttribute"),
                            count().as("Count"))
                    .from(SearchResults)
                    .join(EntityLongAttributes).on(SearchResults.ENTITY_INSTANCE.eq(EntityLongAttributes.ENTITY_INSTANCE))
                    .where(SearchResults.SEARCH.eq(search.getPrimaryKey()),
                            EntityLongAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                            EntityLongAttributes.THRU_TIME.eq(Session.MAX_TIME))
                    .groupBy(EntityLongAttributes.LONG_ATTRIBUTE)
                : getDslContext()
                    .select(EntityLongAttributes.LONG_ATTRIBUTE.as("LongAttribute"),
                            count().as("Count"))
                    .from(CachedExecutedSearchResults)
                    .join(EntityLongAttributes).on(CachedExecutedSearchResults.ENTITY_INSTANCE.eq(EntityLongAttributes.ENTITY_INSTANCE))
                    .where(CachedExecutedSearchResults.CACHED_EXECUTED_SEARCH.eq(cachedExecutedSearch.getPrimaryKey()),
                            EntityLongAttributes.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                            EntityLongAttributes.THRU_TIME.eq(Session.MAX_TIME))
                    .groupBy(EntityLongAttributes.LONG_ATTRIBUTE);

        return super.execute(query);
    }
    
}
