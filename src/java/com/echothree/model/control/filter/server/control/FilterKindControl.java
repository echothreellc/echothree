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

package com.echothree.model.control.filter.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.filter.common.choice.FilterKindChoicesBean;
import com.echothree.model.control.filter.common.transfer.FilterKindDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterKindTransfer;
import com.echothree.model.control.filter.server.transfer.FilterKindDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterKindTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.filter.common.pk.FilterKindPK;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterKindDescription;
import com.echothree.model.data.filter.server.factory.FilterKindDescriptionFactory;
import com.echothree.model.data.filter.server.factory.FilterKindDetailFactory;
import com.echothree.model.data.filter.server.factory.FilterKindFactory;
import com.echothree.model.data.filter.server.value.FilterKindDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterKindDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_KINDS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_KIND_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.filter.FilterKindDescriptions.FilterKindDescriptions;
import static com.echothree.model.jooq.server.tables.filter.FilterKindDetails.FilterKindDetails;
import static com.echothree.model.jooq.server.tables.filter.FilterKinds.FilterKinds;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;

@CommandScope
public class FilterKindControl
        extends BaseModelControl {

    /** Creates a new instance of FilterKindControl */
    protected FilterKindControl() {
        super();
    }

    @Inject
    FilterTypeControl filterTypeControl;

    // --------------------------------------------------------------------------------
    //   Filter Kinds
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterKindDetailFactory filterKindDetailFactory;

    @Inject
    protected FilterKindFactory filterKindFactory;

    @Inject
    FilterKindTransferCache filterKindTransferCache;

    public FilterKind createFilterKind(String filterKindName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultFilterKind = getDefaultFilterKind();
        var defaultFound = defaultFilterKind != null;

        if(defaultFound && isDefault) {
            var defaultFilterKindDetailValue = getDefaultFilterKindDetailValueForUpdate();

            defaultFilterKindDetailValue.setIsDefault(false);
            updateFilterKindFromValue(defaultFilterKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var filterKind = filterKindFactory.create();
        var filterKindDetail = filterKindDetailFactory.create(filterKind, filterKindName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        filterKind = filterKindFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                filterKind.getPrimaryKey());
        filterKind.setActiveDetail(filterKindDetail);
        filterKind.setLastDetail(filterKindDetail);
        filterKind.store();

        sendEvent(filterKind.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return filterKind;
    }
    
    public long countFilterKinds() {
        return session.getDslContext()
                .selectCount()
                .from(FilterKinds)
                .join(FilterKindDetails).onKey(FILTER_KINDS_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.FilterKind */
    public FilterKind getFilterKindByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new FilterKindPK(entityInstance.getEntityUniqueId());

        return filterKindFactory.getEntityFromPK(entityPermission, pk);
    }

    public FilterKind getFilterKindByEntityInstance(EntityInstance entityInstance) {
        return getFilterKindByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public FilterKind getFilterKindByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getFilterKindByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public FilterKind getFilterKindByName(String filterKindName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FilterKinds.fields())
                .from(FilterKinds)
                .join(FilterKindDetails).onKey(FILTER_KINDS_ACTIVE_DETAIL_FK)
                .where(FilterKindDetails.FILTER_KIND_NAME.eq(filterKindName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return filterKindFactory.getEntityFromQuery(entityPermission, query);
    }

    public FilterKind getFilterKindByName(String filterKindName) {
        return getFilterKindByName(filterKindName, EntityPermission.READ_ONLY);
    }

    public FilterKind getFilterKindByNameForUpdate(String filterKindName) {
        return getFilterKindByName(filterKindName, EntityPermission.READ_WRITE);
    }

    public FilterKindDetailValue getFilterKindDetailValueForUpdate(FilterKind filterKind) {
        return filterKind == null? null: filterKind.getLastDetailForUpdate().getFilterKindDetailValue().clone();
    }

    public FilterKindDetailValue getFilterKindDetailValueByNameForUpdate(String filterKindName) {
        return getFilterKindDetailValueForUpdate(getFilterKindByNameForUpdate(filterKindName));
    }

    public FilterKind getDefaultFilterKind(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FilterKinds.fields())
                .from(FilterKinds)
                .join(FilterKindDetails).onKey(FILTER_KINDS_ACTIVE_DETAIL_FK)
                .where(FilterKindDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return filterKindFactory.getEntityFromQuery(entityPermission, query);
    }

    public FilterKind getDefaultFilterKind() {
        return getDefaultFilterKind(EntityPermission.READ_ONLY);
    }

    public FilterKind getDefaultFilterKindForUpdate() {
        return getDefaultFilterKind(EntityPermission.READ_WRITE);
    }

    public FilterKindDetailValue getDefaultFilterKindDetailValueForUpdate() {
        return getDefaultFilterKind(EntityPermission.READ_WRITE).getLastDetailForUpdate().getFilterKindDetailValue();
    }

    private List<FilterKind> getFilterKinds(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FilterKinds.fields())
                .from(FilterKinds)
                .join(FilterKindDetails).onKey(FILTER_KINDS_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(FilterKindDetails.SORT_ORDER, FilterKindDetails.FILTER_KIND_NAME),
                    FilterKindFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return filterKindFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<FilterKind> getFilterKinds() {
        return getFilterKinds(EntityPermission.READ_ONLY);
    }

    public List<FilterKind> getFilterKindsForUpdate() {
        return getFilterKinds(EntityPermission.READ_WRITE);
    }

    public FilterKindChoicesBean getFilterKindChoices(String defaultFilterKindChoice, Language language, boolean allowNullChoice) {
        var filterKinds = getFilterKinds();
        var size = filterKinds.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultFilterKindChoice == null) {
                defaultValue = "";
            }
        }

        for(var filterKind : filterKinds) {
            var filterKindDetail = filterKind.getLastDetail();

            var label = getBestFilterKindDescription(filterKind, language);
            var value = filterKindDetail.getFilterKindName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultFilterKindChoice != null && defaultFilterKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && filterKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new FilterKindChoicesBean(labels, values, defaultValue);
    }

    public FilterKindTransfer getFilterKindTransfer(UserVisit userVisit, FilterKind filterKind) {
        return filterKindTransferCache.getTransfer(userVisit, filterKind);
    }

    public List<FilterKindTransfer> getFilterKindTransfers(UserVisit userVisit, Collection<FilterKind> filterKinds) {
        List<FilterKindTransfer> filterKindTransfers = new ArrayList<>(filterKinds.size());

        filterKinds.forEach((filterKind) ->
                filterKindTransfers.add(filterKindTransferCache.getTransfer(userVisit, filterKind))
        );

        return filterKindTransfers;
    }

    public List<FilterKindTransfer> getFilterKindTransfers(UserVisit userVisit) {
        return getFilterKindTransfers(userVisit, getFilterKinds());
    }

    private void updateFilterKindFromValue(FilterKindDetailValue filterKindDetailValue, boolean checkDefault, BasePK updatedBy) {
        var filterKind = filterKindFactory.getEntityFromPK(
                EntityPermission.READ_WRITE, filterKindDetailValue.getFilterKindPK());
        var filterKindDetail = filterKind.getActiveDetailForUpdate();

        filterKindDetail.setThruTime(session.getStartTime());
        filterKindDetail.store();

        var filterKindPK = filterKindDetail.getFilterKindPK();
        var filterKindName = filterKindDetailValue.getFilterKindName();
        var isDefault = filterKindDetailValue.getIsDefault();
        var sortOrder = filterKindDetailValue.getSortOrder();

        if(checkDefault) {
            var defaultFilterKind = getDefaultFilterKind();
            var defaultFound = defaultFilterKind != null && !defaultFilterKind.equals(filterKind);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                var defaultFilterKindDetailValue = getDefaultFilterKindDetailValueForUpdate();

                defaultFilterKindDetailValue.setIsDefault(false);
                updateFilterKindFromValue(defaultFilterKindDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = true;
            }
        }

        filterKindDetail = filterKindDetailFactory.create(filterKindPK, filterKindName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        filterKind.setActiveDetail(filterKindDetail);
        filterKind.setLastDetail(filterKindDetail);
        filterKind.store();

        sendEvent(filterKindPK, EventTypes.MODIFY, null, null, updatedBy);
    }

    public void updateFilterKindFromValue(FilterKindDetailValue filterKindDetailValue, BasePK updatedBy) {
        updateFilterKindFromValue(filterKindDetailValue, true, updatedBy);
    }

    public void deleteFilterKind(FilterKind filterKind, BasePK deletedBy) {
        filterTypeControl.deleteFilterTypesByFilterKind(filterKind, deletedBy);
        deleteFilterKindDescriptionsByFilterKind(filterKind, deletedBy);

        var filterKindDetail = filterKind.getLastDetailForUpdate();
        filterKindDetail.setThruTime(session.getStartTime());
        filterKind.setActiveDetail(null);
        filterKind.store();

        // Check for default, and pick one if necessary
        var defaultFilterKind = getDefaultFilterKind();
        if(defaultFilterKind == null) {
            var filterKinds = getFilterKindsForUpdate();

            if(!filterKinds.isEmpty()) {
                var iter = filterKinds.iterator();
                if(iter.hasNext()) {
                    defaultFilterKind = iter.next();
                }
                var filterKindDetailValue = Objects.requireNonNull(defaultFilterKind).getLastDetailForUpdate().getFilterKindDetailValue().clone();

                filterKindDetailValue.setIsDefault(true);
                updateFilterKindFromValue(filterKindDetailValue, false, deletedBy);
            }
        }

        sendEvent(filterKind.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Filter Kind Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterKindDescriptionFactory filterKindDescriptionFactory;

    @Inject
    FilterKindDescriptionTransferCache filterKindDescriptionTransferCache;

    public FilterKindDescription createFilterKindDescription(FilterKind filterKind, Language language, String description,
            BasePK createdBy) {
        var filterKindDescription = filterKindDescriptionFactory.create(filterKind,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(filterKind.getPrimaryKey(), EventTypes.MODIFY, filterKindDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return filterKindDescription;
    }

    private FilterKindDescription getFilterKindDescription(FilterKind filterKind, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FilterKindDescriptions.fields())
                .from(FilterKindDescriptions)
                .where(FilterKindDescriptions.FILTER_KIND.eq(filterKind.getPrimaryKey()),
                        FilterKindDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        FilterKindDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return filterKindDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public FilterKindDescription getFilterKindDescription(FilterKind filterKind, Language language) {
        return getFilterKindDescription(filterKind, language, EntityPermission.READ_ONLY);
    }

    public FilterKindDescription getFilterKindDescriptionForUpdate(FilterKind filterKind, Language language) {
        return getFilterKindDescription(filterKind, language, EntityPermission.READ_WRITE);
    }

    public FilterKindDescriptionValue getFilterKindDescriptionValue(FilterKindDescription filterKindDescription) {
        return filterKindDescription == null? null: filterKindDescription.getFilterKindDescriptionValue().clone();
    }

    public FilterKindDescriptionValue getFilterKindDescriptionValueForUpdate(FilterKind filterKind, Language language) {
        return getFilterKindDescriptionValue(getFilterKindDescriptionForUpdate(filterKind, language));
    }

    private List<FilterKindDescription> getFilterKindDescriptionsByFilterKind(FilterKind filterKind, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FilterKindDescriptions.fields())
                    .from(FilterKindDescriptions)
                    .join(Languages).onKey(FILTER_KIND_DESCRIPTIONS_LANGUAGE_FK)
                    .where(FilterKindDescriptions.FILTER_KIND.eq(filterKind.getPrimaryKey()),
                            FilterKindDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    FilterKindDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FilterKindDescriptions.fields())
                    .from(FilterKindDescriptions)
                    .where(FilterKindDescriptions.FILTER_KIND.eq(filterKind.getPrimaryKey()),
                            FilterKindDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return filterKindDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<FilterKindDescription> getFilterKindDescriptionsByFilterKind(FilterKind filterKind) {
        return getFilterKindDescriptionsByFilterKind(filterKind, EntityPermission.READ_ONLY);
    }

    public List<FilterKindDescription> getFilterKindDescriptionsByFilterKindForUpdate(FilterKind filterKind) {
        return getFilterKindDescriptionsByFilterKind(filterKind, EntityPermission.READ_WRITE);
    }

    public String getBestFilterKindDescription(FilterKind filterKind, Language language) {
        String description;
        var filterKindDescription = getFilterKindDescription(filterKind, language);

        if(filterKindDescription == null && !language.getIsDefault()) {
            filterKindDescription = getFilterKindDescription(filterKind, partyControl.getDefaultLanguage());
        }

        if(filterKindDescription == null) {
            description = filterKind.getLastDetail().getFilterKindName();
        } else {
            description = filterKindDescription.getDescription();
        }

        return description;
    }

    public FilterKindDescriptionTransfer getFilterKindDescriptionTransfer(UserVisit userVisit, FilterKindDescription filterKindDescription) {
        return filterKindDescriptionTransferCache.getTransfer(userVisit, filterKindDescription);
    }

    public List<FilterKindDescriptionTransfer> getFilterKindDescriptionTransfersByFilterKind(UserVisit userVisit, FilterKind filterKind) {
        var filterKindDescriptions = getFilterKindDescriptionsByFilterKind(filterKind);
        List<FilterKindDescriptionTransfer> filterKindDescriptionTransfers = new ArrayList<>(filterKindDescriptions.size());

        filterKindDescriptions.forEach((filterKindDescription) ->
                filterKindDescriptionTransfers.add(filterKindDescriptionTransferCache.getTransfer(userVisit, filterKindDescription))
        );

        return filterKindDescriptionTransfers;
    }

    public void updateFilterKindDescriptionFromValue(FilterKindDescriptionValue filterKindDescriptionValue, BasePK updatedBy) {
        if(filterKindDescriptionValue.hasBeenModified()) {
            var filterKindDescription = filterKindDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     filterKindDescriptionValue.getPrimaryKey());

            filterKindDescription.setThruTime(session.getStartTime());
            filterKindDescription.store();

            var filterKind = filterKindDescription.getFilterKind();
            var language = filterKindDescription.getLanguage();
            var description = filterKindDescriptionValue.getDescription();

            filterKindDescription = filterKindDescriptionFactory.create(filterKind, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(filterKind.getPrimaryKey(), EventTypes.MODIFY, filterKindDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteFilterKindDescription(FilterKindDescription filterKindDescription, BasePK deletedBy) {
        filterKindDescription.setThruTime(session.getStartTime());

        sendEvent(filterKindDescription.getFilterKindPK(), EventTypes.MODIFY, filterKindDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteFilterKindDescriptionsByFilterKind(FilterKind filterKind, BasePK deletedBy) {
        var filterKindDescriptions = getFilterKindDescriptionsByFilterKindForUpdate(filterKind);

        filterKindDescriptions.forEach((filterKindDescription) -> 
                deleteFilterKindDescription(filterKindDescription, deletedBy)
        );
    }

}
