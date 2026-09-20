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

package com.echothree.model.control.content.server.control;

import com.echothree.model.control.content.common.transfer.ContentCatalogItemResultTransfer;
import com.echothree.model.control.content.server.graphql.ContentCatalogItemObject;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.content.common.pk.ContentCatalogItemPK;
import com.echothree.model.data.content.server.factory.ContentCatalogItemFactory;
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
public class ContentCatalogItemControl
        extends BaseModelControl {

    @Inject
    protected ContentControl contentControl;

    @Inject
    protected SearchControl searchControl;

    /** Creates a new instance of ContentCatalogItemControl */
    protected ContentCatalogItemControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Content CatalogItem Searches
    // --------------------------------------------------------------------------------

    @Inject
    protected ContentCatalogItemFactory contentCatalogItemFactory;

    private List<Long> getContentCatalogItemIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
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

    public List<ContentCatalogItemResultTransfer> getContentCatalogItemResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var entityIds = getContentCatalogItemIdsFromUserVisitSearch(userVisitSearch);
        var contentCatalogItemResultTransfers = new ArrayList<ContentCatalogItemResultTransfer>(entityIds.size());
        var options = session.getOptions();
        var includeContentCatalogItem = options != null && options.contains(SearchOptions.ContentCatalogItemResultIncludeContentCatalogItem);

        for(var entityUniqueId : entityIds) {
            var contentCatalogItem = contentCatalogItemFactory.getEntityFromPK(EntityPermission.READ_ONLY, new ContentCatalogItemPK(entityUniqueId));
            var contentCatalogDetail = contentCatalogItem.getContentCatalog().getLastDetail();
            var itemDetail = contentCatalogItem.getItem().getLastDetail();

            contentCatalogItemResultTransfers.add(new ContentCatalogItemResultTransfer(contentCatalogDetail.getContentCollection().getLastDetail().getContentCollectionName(),
                    contentCatalogDetail.getContentCatalogName(), itemDetail.getItemName(),
                    includeContentCatalogItem ? contentControl.getContentCatalogItemTransfer(userVisit, contentCatalogItem) : null));
        }

        return contentCatalogItemResultTransfers;
    }

    public List<ContentCatalogItemObject> getContentCatalogItemObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var contentCatalogItemObjects = new ArrayList<ContentCatalogItemObject>();

        for(var entityUniqueId : getContentCatalogItemIdsFromUserVisitSearch(userVisitSearch)) {
            var contentCatalogItem = contentControl.getContentCatalogItemByPK(new ContentCatalogItemPK(entityUniqueId));

            contentCatalogItemObjects.add(new ContentCatalogItemObject(contentCatalogItem));
        }

        return contentCatalogItemObjects;
    }
    
}
