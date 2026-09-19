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

package com.echothree.model.control.core.server.control;

import com.echothree.model.control.core.common.transfer.EntityListItemResultTransfer;
import com.echothree.model.control.core.server.graphql.EntityListItemObject;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.core.common.pk.EntityListItemPK;
import com.echothree.model.data.core.server.factory.EntityListItemFactory;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.search.CachedExecutedSearchResults.CachedExecutedSearchResults;
import static com.echothree.model.jooq.server.tables.search.SearchResults.SearchResults;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.jooq.Select;

@CommandScope
public class EntityListItemControl
        extends BaseModelControl {

    @Inject
    protected CoreControl coreControl;

    @Inject
    protected SearchControl searchControl;

    /** Creates a new instance of EntityListItemControl */
    protected EntityListItemControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Entity List Item Searches
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityListItemFactory entityListItemFactory;

    private List<Long> getEntityListItemIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var search = userVisitSearch.getSearch();
        var cachedSearch = search.getCachedSearch();
        Select<?> query;

        if(cachedSearch == null) {
            query = session.applyLimit(session.getDslContext()
                    .select(EntityInstances.ENTITY_UNIQUE)
                    .from(SearchResults)
                    .join(EntityInstances).onKey(SEARCH_RESULTS_ENTITY_INSTANCE_FK)
                    .where(SearchResults.SEARCH.eq(search.getPrimaryKey()))
                    .orderBy(SearchResults.SORT_ORDER, SearchResults.ENTITY_INSTANCE), SearchResultFactory.class);
        } else {
            var cachedExecutedSearch = searchControl.getCachedExecutedSearch(cachedSearch);

            session.copyLimit(SearchResultConstants.ENTITY_TYPE_NAME, CachedExecutedSearchResultConstants.ENTITY_TYPE_NAME);

            query = session.applyLimit(session.getDslContext()
                    .select(EntityInstances.ENTITY_UNIQUE)
                    .from(CachedExecutedSearchResults)
                    .join(EntityInstances).onKey(CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK)
                    .where(CachedExecutedSearchResults.CACHED_EXECUTED_SEARCH.eq(cachedExecutedSearch.getPrimaryKey()))
                    .orderBy(CachedExecutedSearchResults.SORT_ORDER, CachedExecutedSearchResults.ENTITY_INSTANCE),
                    CachedExecutedSearchResultFactory.class);
        }

        return query.fetch(EntityInstances.ENTITY_UNIQUE);
    }

    public List<EntityListItemResultTransfer> getEntityListItemResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var entityIds = getEntityListItemIdsFromUserVisitSearch(userVisitSearch);
        var entityListItemResultTransfers = new ArrayList<EntityListItemResultTransfer>(entityIds.size());
        var options = session.getOptions();
        var includeEntityListItem = options != null && options.contains(SearchOptions.EntityListItemResultIncludeEntityListItem);

        for(var entityUniqueId : entityIds) {
            var entityListItem = entityListItemFactory.getEntityFromPK(EntityPermission.READ_ONLY, new EntityListItemPK(entityUniqueId));
            var entityListItemDetail = entityListItem.getLastDetail();
            var entityAttributeDetail = entityListItemDetail.getEntityAttribute().getLastDetail();
            var entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();

            entityListItemResultTransfers.add(new EntityListItemResultTransfer(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAttributeDetail.getEntityAttributeName(), entityListItemDetail.getEntityListItemName(),
                    includeEntityListItem ? coreControl.getEntityListItemTransfer(userVisit, entityListItem, null) : null));
        }

        return entityListItemResultTransfers;
    }

    public List<EntityListItemObject> getEntityListItemObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var entityListItemObjects = new ArrayList<EntityListItemObject>();

        for(var entityUniqueId : getEntityListItemIdsFromUserVisitSearch(userVisitSearch)) {
            var entityListItem = coreControl.getEntityListItemByPK(new EntityListItemPK(entityUniqueId));

            entityListItemObjects.add(new EntityListItemObject(entityListItem));
        }

        return entityListItemObjects;
    }

}
