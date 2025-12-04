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

import com.echothree.model.control.core.common.transfer.EntityAttributeGroupResultTransfer;
import com.echothree.model.control.core.server.graphql.EntityAttributeGroupObject;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import static com.echothree.model.control.search.server.control.SearchControl.ENI_ENTITYUNIQUEID_COLUMN_INDEX;
import com.echothree.model.data.core.common.pk.EntityAttributeGroupPK;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupFactory;
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
public class EntityAttributeGroupControl
        extends BaseCoreControl {

    /** Creates a new instance of EntityAttributeGroupControl */
    protected EntityAttributeGroupControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Group Searches
    // --------------------------------------------------------------------------------

    public List<EntityAttributeGroupResultTransfer> getEntityAttributeGroupResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var searchControl = Session.getModelController(SearchControl.class);
        var search = userVisitSearch.getSearch();
        var cachedSearch = search.getCachedSearch();
        List<EntityAttributeGroupResultTransfer> entityAttributeGroupResultTransfers;
        var includeEntityAttributeGroup = false;

        var options = session.getOptions();
        if(options != null) {
            includeEntityAttributeGroup = options.contains(SearchOptions.EntityAttributeGroupResultIncludeEntityAttributeGroup);
        }

        if(cachedSearch == null) {
            entityAttributeGroupResultTransfers = new ArrayList<>(toIntExact(searchControl.countSearchResults(search)));

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
                        var entityAttributeGroup = EntityAttributeGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new EntityAttributeGroupPK(rs.getLong(1)));
                        var entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();

                        entityAttributeGroupResultTransfers.add(new EntityAttributeGroupResultTransfer(entityAttributeGroupDetail.getEntityAttributeGroupName(),
                                includeEntityAttributeGroup ? coreControl.getEntityAttributeGroupTransfer(userVisit, entityAttributeGroup, null) : null));
                    }
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } else {
            var cachedExecutedSearch = searchControl.getCachedExecutedSearch(cachedSearch);

            entityAttributeGroupResultTransfers = new ArrayList<>(toIntExact(searchControl.countCachedExecutedSearchResults(cachedExecutedSearch)));

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
                        var entityAttributeGroup = EntityAttributeGroupFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new EntityAttributeGroupPK(rs.getLong(1)));
                        var entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();

                        entityAttributeGroupResultTransfers.add(new EntityAttributeGroupResultTransfer(entityAttributeGroupDetail.getEntityAttributeGroupName(),
                                includeEntityAttributeGroup ? coreControl.getEntityAttributeGroupTransfer(userVisit, entityAttributeGroup, null) : null));
                    }
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        }

        return entityAttributeGroupResultTransfers;
    }

    public List<EntityAttributeGroupObject> getEntityAttributeGroupObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var coreControl = Session.getModelController(CoreControl.class);
        var searchControl = Session.getModelController(SearchControl.class);
        var entityAttributeGroupObjects = new ArrayList<EntityAttributeGroupObject>();

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            while(rs.next()) {
                var entityAttributeGroup = coreControl.getEntityAttributeGroupByPK(new EntityAttributeGroupPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                entityAttributeGroupObjects.add(new EntityAttributeGroupObject(entityAttributeGroup, null));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return entityAttributeGroupObjects;
    }

}
