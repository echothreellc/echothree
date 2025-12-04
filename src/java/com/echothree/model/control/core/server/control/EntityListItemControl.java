// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import static com.echothree.model.control.search.server.control.SearchControl.ENI_ENTITYUNIQUEID_COLUMN_INDEX;
import com.echothree.model.data.core.common.pk.EntityListItemPK;
import com.echothree.model.data.core.server.factory.EntityListItemFactory;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import static java.lang.Math.toIntExact;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.echothree.util.server.cdi.CommandScope;

@CommandScope
public class EntityListItemControl
        extends BaseCoreControl {

    /** Creates a new instance of EntityListItemControl */
    protected EntityListItemControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Entity List Item Searches
    // --------------------------------------------------------------------------------

    public List<EntityListItemResultTransfer> getEntityListItemResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var searchControl = Session.getModelController(SearchControl.class);
        var search = userVisitSearch.getSearch();
        var cachedSearch = search.getCachedSearch();
        List<EntityListItemResultTransfer> entityListItemResultTransfers;
        var includeEntityListItem = false;

        var options = session.getOptions();
        if(options != null) {
            includeEntityListItem = options.contains(SearchOptions.EntityListItemResultIncludeEntityListItem);
        }

        if(cachedSearch == null) {
            entityListItemResultTransfers = new ArrayList<>(toIntExact(searchControl.countSearchResults(search)));

            try {
                var coreControl = Session.getModelController(CoreControl.class);
                var ps = SearchResultFactory.getInstance().prepareStatement(
                        "SELECT eni_entityuniqueid "
                                + "FROM searchresults, entityinstances "
                                + "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid "
                                + "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid "
                                + "_LIMIT_");

                ps.setLong(1, search.getPrimaryKey().getEntityId());

                try (var rs = ps.executeQuery()) {
                    while(rs.next()) {
                        var entityListItem = EntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new EntityListItemPK(rs.getLong(1)));
                        var entityListItemDetail = entityListItem.getLastDetail();
                        var entityAttributeDetail = entityListItemDetail.getEntityAttribute().getLastDetail();
                        var entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();

                        entityListItemResultTransfers.add(new EntityListItemResultTransfer(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                entityTypeDetail.getEntityTypeName(), entityAttributeDetail.getEntityAttributeName(), entityListItemDetail.getEntityListItemName(),
                                includeEntityListItem ? coreControl.getEntityListItemTransfer(userVisit, entityListItem, null) : null));
                    }
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } else {
            var cachedExecutedSearch = searchControl.getCachedExecutedSearch(cachedSearch);

            entityListItemResultTransfers = new ArrayList<>(toIntExact(searchControl.countCachedExecutedSearchResults(cachedExecutedSearch)));

            session.copyLimit(SearchResultConstants.ENTITY_TYPE_NAME, CachedExecutedSearchResultConstants.ENTITY_TYPE_NAME);

            try {
                var coreControl = Session.getModelController(CoreControl.class);
                var ps = CachedExecutedSearchResultFactory.getInstance().prepareStatement(
                        "SELECT eni_entityuniqueid "
                                + "FROM cachedexecutedsearchresults, entityinstances "
                                + "WHERE cxsrchr_cxsrch_cachedexecutedsearchid = ? AND cxsrchr_eni_entityinstanceid = eni_entityinstanceid "
                                + "ORDER BY cxsrchr_sortorder, cxsrchr_eni_entityinstanceid "
                                + "_LIMIT_");

                ps.setLong(1, cachedExecutedSearch.getPrimaryKey().getEntityId());

                try (var rs = ps.executeQuery()) {
                    while(rs.next()) {
                        var entityListItem = EntityListItemFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new EntityListItemPK(rs.getLong(1)));
                        var entityListItemDetail = entityListItem.getLastDetail();
                        var entityAttributeDetail = entityListItemDetail.getEntityAttribute().getLastDetail();
                        var entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();

                        entityListItemResultTransfers.add(new EntityListItemResultTransfer(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                entityTypeDetail.getEntityTypeName(), entityAttributeDetail.getEntityAttributeName(), entityListItemDetail.getEntityListItemName(),
                                includeEntityListItem ? coreControl.getEntityListItemTransfer(userVisit, entityListItem, null) : null));
                    }
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        }

        return entityListItemResultTransfers;
    }

    public List<EntityListItemObject> getEntityListItemObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var coreControl = Session.getModelController(CoreControl.class);
        var searchControl = Session.getModelController(SearchControl.class);
        var entityListItemObjects = new ArrayList<EntityListItemObject>();

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            while(rs.next()) {
                var entityListItem = coreControl.getEntityListItemByPK(new EntityListItemPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                entityListItemObjects.add(new EntityListItemObject(entityListItem));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityListItemObjects;
    }

}
