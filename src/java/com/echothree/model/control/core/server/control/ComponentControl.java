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

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.transfer.ComponentVendorResultTransfer;
import com.echothree.model.control.core.common.transfer.ComponentVendorTransfer;
import com.echothree.model.control.core.server.graphql.ComponentVendorObject;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import static com.echothree.model.control.search.server.control.SearchControl.ENI_ENTITYUNIQUEID_COLUMN_INDEX;
import com.echothree.model.data.core.common.pk.ComponentVendorPK;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.factory.ComponentVendorDetailFactory;
import com.echothree.model.data.core.server.factory.ComponentVendorFactory;
import com.echothree.model.data.core.server.value.ComponentVendorDetailValue;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import static java.lang.Math.toIntExact;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentControl
        extends BaseCoreControl {

    /** Creates a new instance of ComponentControl */
    public ComponentControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Component Vendors
    // --------------------------------------------------------------------------------

    public ComponentVendor createComponentVendor(String componentVendorName, String description, BasePK createdBy) {
        var componentVendor = ComponentVendorFactory.getInstance().create();
        var componentVendorDetail = ComponentVendorDetailFactory.getInstance().create(componentVendor,
                componentVendorName, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        componentVendor = ComponentVendorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                componentVendor.getPrimaryKey());
        componentVendor.setActiveDetail(componentVendorDetail);
        componentVendor.setLastDetail(componentVendorDetail);
        componentVendor.store();

        sendEvent(componentVendor.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return componentVendor;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.ComponentVendor */
    public ComponentVendor getComponentVendorByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new ComponentVendorPK(entityInstance.getEntityUniqueId());

        return ComponentVendorFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public ComponentVendor getComponentVendorByEntityInstance(EntityInstance entityInstance) {
        return getComponentVendorByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ComponentVendor getComponentVendorByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getComponentVendorByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countComponentVendors() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                        "FROM componentvendors, componentvendordetails " +
                        "WHERE cvnd_activedetailid = cvndd_componentvendordetailid");
    }

    public ComponentVendor getComponentVendorByName(String componentVendorName, EntityPermission entityPermission) {
        ComponentVendor componentVendor;

        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM componentvendors, componentvendordetails " +
                        "WHERE cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM componentvendors, componentvendordetails " +
                        "WHERE cvnd_activedetailid = cvndd_componentvendordetailid AND cvndd_componentvendorname = ? " +
                        "FOR UPDATE";
            }

            var ps = ComponentVendorFactory.getInstance().prepareStatement(query);

            ps.setString(1, componentVendorName);

            componentVendor = ComponentVendorFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return componentVendor;
    }

    public ComponentVendor getComponentVendorByName(String componentVendorName) {
        return getComponentVendorByName(componentVendorName, EntityPermission.READ_ONLY);
    }

    public ComponentVendor getComponentVendorByNameForUpdate(String componentVendorName) {
        return getComponentVendorByName(componentVendorName, EntityPermission.READ_WRITE);
    }

    public ComponentVendorDetailValue getComponentVendorDetailValueForUpdate(ComponentVendor componentVendor) {
        return componentVendor == null? null: componentVendor.getLastDetailForUpdate().getComponentVendorDetailValue().clone();
    }

    public ComponentVendorDetailValue getComponentVendorDetailValueByNameForUpdate(String componentVendorName) {
        return getComponentVendorDetailValueForUpdate(getComponentVendorByNameForUpdate(componentVendorName));
    }

    private final Map<String, ComponentVendor> componentVendorCache = new HashMap<>();

    public ComponentVendor getComponentVendorByNameFromCache(String componentVendorName) {
        var componentVendor = componentVendorCache.get(componentVendorName);

        if(componentVendor == null) {
            componentVendor = getComponentVendorByName(componentVendorName);

            if(componentVendor != null) {
                componentVendorCache.put(componentVendorName, componentVendor);
            }
        }

        return componentVendor;
    }

    public List<ComponentVendor> getComponentVendors() {
        var ps = ComponentVendorFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                        "FROM componentvendors, componentvendordetails " +
                        "WHERE cvnd_activedetailid = cvndd_componentvendordetailid " +
                        "ORDER BY cvndd_componentvendorname " +
                        "_LIMIT_");

        return ComponentVendorFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }

    public ComponentVendorTransfer getComponentVendorTransfer(UserVisit userVisit, ComponentVendor componentVendor) {
        return getCoreTransferCaches(userVisit).getComponentVendorTransferCache().getComponentVendorTransfer(componentVendor);
    }

    public List<ComponentVendorTransfer> getComponentVendorTransfers(UserVisit userVisit, Collection<ComponentVendor> componentVendors) {
        var componentVendorTransfers = new ArrayList<ComponentVendorTransfer>(componentVendors.size());
        var componentVendorTransferCache = getCoreTransferCaches(userVisit).getComponentVendorTransferCache();

        componentVendors.forEach((componentVendor) ->
                componentVendorTransfers.add(componentVendorTransferCache.getComponentVendorTransfer(componentVendor))
        );

        return componentVendorTransfers;
    }

    public List<ComponentVendorTransfer> getComponentVendorTransfers(UserVisit userVisit) {
        return getComponentVendorTransfers(userVisit, getComponentVendors());
    }

    public void updateComponentVendorFromValue(ComponentVendorDetailValue componentVendorDetailValue, BasePK updatedBy) {
        if(componentVendorDetailValue.hasBeenModified()) {
            var componentVendor = ComponentVendorFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                    componentVendorDetailValue.getComponentVendorPK());
            var componentVendorDetail = componentVendor.getActiveDetailForUpdate();

            componentVendorDetail.setThruTime(session.START_TIME_LONG);
            componentVendorDetail.store();

            var componentVendorPK = componentVendorDetail.getComponentVendorPK();
            var componentVendorName = componentVendorDetailValue.getComponentVendorName();
            var description = componentVendorDetailValue.getDescription();

            componentVendorDetail = ComponentVendorDetailFactory.getInstance().create(componentVendorPK,
                    componentVendorName, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

            componentVendor.setActiveDetail(componentVendorDetail);
            componentVendor.setLastDetail(componentVendorDetail);

            sendEvent(componentVendorPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public ComponentVendor getComponentVendorByPK(ComponentVendorPK pk) {
        return ComponentVendorFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public void deleteComponentVendor(ComponentVendor componentVendor, BasePK deletedBy) {
        var entityTypeControl = Session.getModelController(EntityTypeControl.class);

        entityTypeControl.deleteEntityTypesByComponentVendor(componentVendor, deletedBy);

        var componentVendorDetail = componentVendor.getLastDetailForUpdate();
        componentVendorDetail.setThruTime(session.START_TIME_LONG);
        componentVendor.setActiveDetail(null);
        componentVendor.store();

        sendEvent(componentVendor.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Component Vendor Searches
    // --------------------------------------------------------------------------------

    public List<ComponentVendorResultTransfer> getComponentVendorResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var searchControl = Session.getModelController(SearchControl.class);
        var search = userVisitSearch.getSearch();
        var cachedSearch = search.getCachedSearch();
        List<ComponentVendorResultTransfer> componentVendorResultTransfers;
        var includeComponentVendor = false;

        var options = session.getOptions();
        if(options != null) {
            includeComponentVendor = options.contains(SearchOptions.ComponentVendorResultIncludeComponentVendor);
        }

        if(cachedSearch == null) {
            componentVendorResultTransfers = new ArrayList<>(toIntExact(searchControl.countSearchResults(search)));

            try {
                var ps = SearchResultFactory.getInstance().prepareStatement(
                        "SELECT eni_entityuniqueid "
                                + "FROM searchresults, entityinstances "
                                + "WHERE srchr_srch_searchid = ? AND srchr_eni_entityinstanceid = eni_entityinstanceid "
                                + "ORDER BY srchr_sortorder, srchr_eni_entityinstanceid "
                                + "_LIMIT_");

                ps.setLong(1, search.getPrimaryKey().getEntityId());

                try (var rs = ps.executeQuery()) {
                    while(rs.next()) {
                        var componentVendor = ComponentVendorFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new ComponentVendorPK(rs.getLong(1)));
                        var componentVendorDetail = componentVendor.getLastDetail();

                        componentVendorResultTransfers.add(new ComponentVendorResultTransfer(componentVendorDetail.getComponentVendorName(),
                                includeComponentVendor ? getComponentVendorTransfer(userVisit, componentVendor) : null));
                    }
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        } else {
            var cachedExecutedSearch = searchControl.getCachedExecutedSearch(cachedSearch);

            componentVendorResultTransfers = new ArrayList<>(toIntExact(searchControl.countCachedExecutedSearchResults(cachedExecutedSearch)));

            session.copyLimit(SearchResultConstants.ENTITY_TYPE_NAME, CachedExecutedSearchResultConstants.ENTITY_TYPE_NAME);

            try {
                var ps = CachedExecutedSearchResultFactory.getInstance().prepareStatement(
                        "SELECT eni_entityuniqueid "
                                + "FROM cachedexecutedsearchresults, entityinstances "
                                + "WHERE cxsrchr_cxsrch_cachedexecutedsearchid = ? AND cxsrchr_eni_entityinstanceid = eni_entityinstanceid "
                                + "ORDER BY cxsrchr_sortorder, cxsrchr_eni_entityinstanceid "
                                + "_LIMIT_");

                ps.setLong(1, cachedExecutedSearch.getPrimaryKey().getEntityId());

                try (var rs = ps.executeQuery()) {
                    while(rs.next()) {
                        var componentVendor = ComponentVendorFactory.getInstance().getEntityFromPK(EntityPermission.READ_ONLY, new ComponentVendorPK(rs.getLong(1)));
                        var componentVendorDetail = componentVendor.getLastDetail();

                        componentVendorResultTransfers.add(new ComponentVendorResultTransfer(componentVendorDetail.getComponentVendorName(),
                                includeComponentVendor ? getComponentVendorTransfer(userVisit, componentVendor) : null));
                    }
                } catch (SQLException se) {
                    throw new PersistenceDatabaseException(se);
                }
            } catch (SQLException se) {
                throw new PersistenceDatabaseException(se);
            }
        }

        return componentVendorResultTransfers;
    }

    public List<ComponentVendorObject> getComponentVendorObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var searchControl = Session.getModelController(SearchControl.class);
        var componentVendorObjects = new ArrayList<ComponentVendorObject>();

        try (var rs = searchControl.getUserVisitSearchResultSet(userVisitSearch)) {
            while(rs.next()) {
                var componentVendor = getComponentVendorByPK(new ComponentVendorPK(rs.getLong(ENI_ENTITYUNIQUEID_COLUMN_INDEX)));

                componentVendorObjects.add(new ComponentVendorObject(componentVendor));
            }
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }

        return componentVendorObjects;
    }

}
