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

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.transfer.ComponentVendorResultTransfer;
import com.echothree.model.control.core.common.transfer.ComponentVendorTransfer;
import com.echothree.model.control.core.server.graphql.ComponentVendorObject;
import com.echothree.model.control.core.server.transfer.ComponentVendorTransferCache;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.core.common.pk.ComponentVendorPK;
import com.echothree.model.data.core.server.entity.Component;
import com.echothree.model.data.core.server.entity.ComponentStage;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.ComponentVersion;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.factory.ComponentDetailFactory;
import com.echothree.model.data.core.server.factory.ComponentFactory;
import com.echothree.model.data.core.server.factory.ComponentStageFactory;
import com.echothree.model.data.core.server.factory.ComponentVendorDetailFactory;
import com.echothree.model.data.core.server.factory.ComponentVendorFactory;
import com.echothree.model.data.core.server.factory.ComponentVersionFactory;
import com.echothree.model.data.core.server.value.ComponentVendorDetailValue;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_DETAILS_COMPONENT_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentDetails.ComponentDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentStages.ComponentStages;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.ComponentVersions.ComponentVersions;
import static com.echothree.model.jooq.server.tables.core.Components.Components;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.search.CachedExecutedSearchResults.CachedExecutedSearchResults;
import static com.echothree.model.jooq.server.tables.search.SearchResults.SearchResults;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.jooq.Select;

@CommandScope
public class ComponentControl
        extends BaseModelControl {

    @Inject
    protected EntityTypeControl entityTypeControl;

    /** Creates a new instance of ComponentControl */
    protected ComponentControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Component Vendors
    // --------------------------------------------------------------------------------

    @Inject
    protected ComponentVendorDetailFactory componentVendorDetailFactory;

    @Inject
    protected ComponentVendorFactory componentVendorFactory;

    @Inject
    ComponentVendorTransferCache componentVendorTransferCache;

    public ComponentVendor createComponentVendor(String componentVendorName, String description, BasePK createdBy) {
        var componentVendor = componentVendorFactory.create();
        var componentVendorDetail = componentVendorDetailFactory.create(componentVendor,
                componentVendorName, description, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        componentVendor = componentVendorFactory.getEntityFromPK(EntityPermission.READ_WRITE,
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

        return componentVendorFactory.getEntityFromPK(entityPermission, pk);
    }

    public ComponentVendor getComponentVendorByEntityInstance(EntityInstance entityInstance) {
        return getComponentVendorByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public ComponentVendor getComponentVendorByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getComponentVendorByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countComponentVendors() {
        return session.getDslContext()
                .selectCount()
                .from(ComponentVendors)
                .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public ComponentVendor getComponentVendorByName(String componentVendorName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(ComponentVendors.fields())
                .from(ComponentVendors)
                .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_ACTIVE_DETAIL_FK)
                .where(ComponentVendorDetails.COMPONENT_VENDOR_NAME.eq(componentVendorName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return componentVendorFactory.getEntityFromQuery(entityPermission, query);
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
        var query = session.applyLimit(session.getDslContext()
                .select(ComponentVendors.fields())
                .from(ComponentVendors)
                .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_ACTIVE_DETAIL_FK)
                .orderBy(ComponentVendorDetails.COMPONENT_VENDOR_NAME),
                ComponentVendorFactory.class);

        return componentVendorFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public ComponentVendorTransfer getComponentVendorTransfer(UserVisit userVisit, ComponentVendor componentVendor) {
        return componentVendorTransferCache.getComponentVendorTransfer(userVisit, componentVendor);
    }

    public List<ComponentVendorTransfer> getComponentVendorTransfers(UserVisit userVisit, Collection<ComponentVendor> componentVendors) {
        var componentVendorTransfers = new ArrayList<ComponentVendorTransfer>(componentVendors.size());

        componentVendors.forEach((componentVendor) ->
                componentVendorTransfers.add(componentVendorTransferCache.getComponentVendorTransfer(userVisit, componentVendor))
        );

        return componentVendorTransfers;
    }

    public List<ComponentVendorTransfer> getComponentVendorTransfers(UserVisit userVisit) {
        return getComponentVendorTransfers(userVisit, getComponentVendors());
    }

    public void updateComponentVendorFromValue(ComponentVendorDetailValue componentVendorDetailValue, BasePK updatedBy) {
        if(componentVendorDetailValue.hasBeenModified()) {
            var componentVendor = componentVendorFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    componentVendorDetailValue.getComponentVendorPK());
            var componentVendorDetail = componentVendor.getActiveDetailForUpdate();

            componentVendorDetail.setThruTime(session.getStartTime());
            componentVendorDetail.store();

            var componentVendorPK = componentVendorDetail.getComponentVendorPK();
            var componentVendorName = componentVendorDetailValue.getComponentVendorName();
            var description = componentVendorDetailValue.getDescription();

            componentVendorDetail = componentVendorDetailFactory.create(componentVendorPK,
                    componentVendorName, description, session.getStartTime(), Session.MAX_TIME);

            componentVendor.setActiveDetail(componentVendorDetail);
            componentVendor.setLastDetail(componentVendorDetail);

            sendEvent(componentVendorPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public ComponentVendor getComponentVendorByPK(ComponentVendorPK pk) {
        return componentVendorFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public void deleteComponentVendor(ComponentVendor componentVendor, BasePK deletedBy) {
        entityTypeControl.deleteEntityTypesByComponentVendor(componentVendor, deletedBy);

        var componentVendorDetail = componentVendor.getLastDetailForUpdate();
        componentVendorDetail.setThruTime(session.getStartTime());
        componentVendor.setActiveDetail(null);
        componentVendor.store();

        sendEvent(componentVendor.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Component Vendor Searches
    // --------------------------------------------------------------------------------

    @Inject
    protected SearchControl searchControl;

    private List<Long> getComponentVendorIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
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

    public List<ComponentVendorResultTransfer> getComponentVendorResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var entityIds = getComponentVendorIdsFromUserVisitSearch(userVisitSearch);
        var componentVendorResultTransfers = new ArrayList<ComponentVendorResultTransfer>(entityIds.size());
        var options = session.getOptions();
        var includeComponentVendor = options != null && options.contains(SearchOptions.ComponentVendorResultIncludeComponentVendor);

        for(var entityUniqueId : entityIds) {
            var componentVendor = componentVendorFactory.getEntityFromPK(EntityPermission.READ_ONLY, new ComponentVendorPK(entityUniqueId));
            var componentVendorDetail = componentVendor.getLastDetail();

            componentVendorResultTransfers.add(new ComponentVendorResultTransfer(componentVendorDetail.getComponentVendorName(),
                    includeComponentVendor ? getComponentVendorTransfer(userVisit, componentVendor) : null));
        }

        return componentVendorResultTransfers;
    }

    public List<ComponentVendorObject> getComponentVendorObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var componentVendorObjects = new ArrayList<ComponentVendorObject>();

        for(var entityUniqueId : getComponentVendorIdsFromUserVisitSearch(userVisitSearch)) {
            var componentVendor = getComponentVendorByPK(new ComponentVendorPK(entityUniqueId));

            componentVendorObjects.add(new ComponentVendorObject(componentVendor));
        }

        return componentVendorObjects;
    }

    // --------------------------------------------------------------------------------
    //   Components
    // --------------------------------------------------------------------------------

    @Inject
    protected ComponentDetailFactory componentDetailFactory;

    @Inject
    protected ComponentFactory componentFactory;

    public Component createComponent(ComponentVendor componentVendor, String componentName, String description, BasePK createdBy) {
        var component = componentFactory.create();
        var componentDetail = componentDetailFactory.create(componentVendor, component,
                componentName, description, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        component = componentFactory.getEntityFromPK(EntityPermission.READ_WRITE, component.getPrimaryKey());
        component.setActiveDetail(componentDetail);
        component.setLastDetail(componentDetail);
        component.store();

        return component;
    }

    public Component getComponentByName(ComponentVendor componentVendor, String componentName) {
        var query = session.getDslContext()
                .select(Components.fields())
                .from(Components)
                .join(ComponentDetails).onKey(COMPONENT_DETAILS_COMPONENT_FK)
                .where(ComponentDetails.COMPONENT_VENDOR.eq(componentVendor.getPrimaryKey()),
                        ComponentDetails.COMPONENT_NAME.eq(componentName),
                        ComponentDetails.THRU_TIME.eq(Session.MAX_TIME));

        return componentFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    // --------------------------------------------------------------------------------
    //   Component Stages
    // --------------------------------------------------------------------------------

    @Inject
    protected ComponentStageFactory componentStageFactory;

    public ComponentStage createComponentStage(String componentStageName, String description, Integer relativeAge) {
        var componentStage = componentStageFactory.create(componentStageName, description, relativeAge);

        return componentStage;
    }

    public ComponentStage getComponentStageByName(String componentStageName) {
        var query = session.getDslContext()
                .select(ComponentStages.fields())
                .from(ComponentStages)
                .where(ComponentStages.COMPONENT_STAGE_NAME.eq(componentStageName));

        return componentStageFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

    // --------------------------------------------------------------------------------
    //   Component Versions
    // --------------------------------------------------------------------------------

    @Inject
    protected ComponentVersionFactory componentVersionFactory;

    public ComponentVersion createComponentVersion(Component component, Integer majorRevision, Integer minorRevision,
            ComponentStage componentStage, Integer buildNumber,
            BasePK createdBy) {

        return componentVersionFactory.create(component, majorRevision, minorRevision, componentStage,
                buildNumber, session.getStartTime(), Session.MAX_TIME);
    }

    public ComponentVersion getComponentVersion(Component component) {
        var query = session.getDslContext()
                .select(ComponentVersions.fields())
                .from(ComponentVersions)
                .where(ComponentVersions.COMPONENT.eq(component.getPrimaryKey()),
                        ComponentVersions.THRU_TIME.eq(Session.MAX_TIME));

        return componentVersionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

}
