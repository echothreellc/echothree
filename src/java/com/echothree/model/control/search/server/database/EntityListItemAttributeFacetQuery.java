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

import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_LIST_ITEMS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_LIST_ITEM_ATTRIBUTES_ENTITY_LIST_ITEM_FK;
import static com.echothree.model.jooq.server.tables.core.EntityListItemAttributes.EntityListItemAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityListItemDetails.EntityListItemDetails;
import static com.echothree.model.jooq.server.tables.core.EntityListItems.EntityListItems;
import static com.echothree.model.jooq.server.tables.search.CachedExecutedSearchResults.CachedExecutedSearchResults;
import static com.echothree.model.jooq.server.tables.search.SearchResults.SearchResults;
import static org.jooq.impl.DSL.count;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.util.server.persistence.Session;
import java.util.List;

public class EntityListItemAttributeFacetQuery
        extends BaseFacetQuery<EntityListItemAttributeFacetResult> {
    
    public EntityListItemAttributeFacetQuery(UserVisitSearch userVisitSearch) {
        super(userVisitSearch);
    }
    
    public List<EntityListItemAttributeFacetResult> execute(EntityAttribute entityAttribute) {
        var query = cachedExecutedSearch == null
                ? getDslContext()
                    .select(EntityListItems.ENTITY_LIST_ITEM.as("EntityListItem"),
                            count().as("Count"))
                    .from(SearchResults)
                    .join(EntityListItemAttributes).on(SearchResults.ENTITY_INSTANCE.eq(EntityListItemAttributes.ENTITY_INSTANCE))
                    .join(EntityListItems).onKey(ENTITY_LIST_ITEM_ATTRIBUTES_ENTITY_LIST_ITEM_FK)
                    .join(EntityListItemDetails).onKey(ENTITY_LIST_ITEMS_ACTIVE_DETAIL_FK)
                    .where(SearchResults.SEARCH.eq(search.getPrimaryKey()),
                            EntityListItemDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                            EntityListItemAttributes.THRU_TIME.eq(Session.MAX_TIME))
                    .groupBy(EntityListItems.ENTITY_LIST_ITEM)
                    .orderBy(count().desc())
                : getDslContext()
                    .select(EntityListItems.ENTITY_LIST_ITEM.as("EntityListItem"),
                            count().as("Count"))
                    .from(CachedExecutedSearchResults)
                    .join(EntityListItemAttributes).on(CachedExecutedSearchResults.ENTITY_INSTANCE.eq(EntityListItemAttributes.ENTITY_INSTANCE))
                    .join(EntityListItems).onKey(ENTITY_LIST_ITEM_ATTRIBUTES_ENTITY_LIST_ITEM_FK)
                    .join(EntityListItemDetails).onKey(ENTITY_LIST_ITEMS_ACTIVE_DETAIL_FK)
                    .where(CachedExecutedSearchResults.CACHED_EXECUTED_SEARCH.eq(cachedExecutedSearch.getPrimaryKey()),
                            EntityListItemDetails.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                            EntityListItemAttributes.THRU_TIME.eq(Session.MAX_TIME))
                    .groupBy(EntityListItems.ENTITY_LIST_ITEM)
                    .orderBy(count().desc());

        return super.execute(query);
    }
    
}
