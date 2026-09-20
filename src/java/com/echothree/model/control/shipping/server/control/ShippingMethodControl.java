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

package com.echothree.model.control.shipping.server.control;

import com.echothree.model.control.search.common.SearchOptions;
import static com.echothree.model.control.search.server.control.SearchControl.ENI_ENTITYUNIQUEID_COLUMN_INDEX;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.shipping.common.transfer.ShippingMethodResultTransfer;
import com.echothree.model.control.shipping.server.graphql.ShippingMethodObject;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.shipping.common.pk.ShippingMethodPK;
import com.echothree.model.data.shipping.server.factory.ShippingMethodFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.search.CachedExecutedSearchResults.CachedExecutedSearchResults;
import static com.echothree.model.jooq.server.tables.search.SearchResults.SearchResults;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.jooq.Select;

@CommandScope
public class ShippingMethodControl
        extends BaseModelControl {

    @Inject
    protected SearchControl searchControl;

    @Inject
    protected ShippingControl shippingControl;

    /** Creates a new instance of ShippingMethodControl */
    protected ShippingMethodControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Shipping Method Searches
    // --------------------------------------------------------------------------------

    @Inject
    protected CachedExecutedSearchResultFactory cachedExecutedSearchResultFactory;

    @Inject
    protected SearchResultFactory searchResultFactory;

    @Inject
    protected ShippingMethodFactory shippingMethodFactory;

    private List<Long> getShippingMethodIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
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

    public List<ShippingMethodResultTransfer> getShippingMethodResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var entityIds = getShippingMethodIdsFromUserVisitSearch(userVisitSearch);
        var shippingMethodResultTransfers = new ArrayList<ShippingMethodResultTransfer>(entityIds.size());
        var options = session.getOptions();
        var includeShippingMethod = options != null && options.contains(SearchOptions.ShippingMethodResultIncludeShippingMethod);

        for(var entityUniqueId : entityIds) {
            var shippingMethod = shippingMethodFactory.getEntityFromPK(EntityPermission.READ_ONLY, new ShippingMethodPK(entityUniqueId));
            var shippingMethodDetail = shippingMethod.getLastDetail();

            shippingMethodResultTransfers.add(new ShippingMethodResultTransfer(shippingMethodDetail.getShippingMethodName(),
                    includeShippingMethod ? shippingControl.getShippingMethodTransfer(userVisit, shippingMethod) : null));
        }

        return shippingMethodResultTransfers;
    }

    public List<ShippingMethodObject> getShippingMethodObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var shippingMethodObjects = new ArrayList<ShippingMethodObject>();

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            while(rs.next()) {
                var shippingMethod = shippingControl.getShippingMethodByPK(new ShippingMethodPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                shippingMethodObjects.add(new ShippingMethodObject(shippingMethod));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return shippingMethodObjects;
    }

}
