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
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    FilterControl filterControl;

    @Inject
    FilterKindDescriptionTransferCache filterKindDescriptionTransferCache;

    @Inject
    FilterKindTransferCache filterKindTransferCache;

    // --------------------------------------------------------------------------------
    //   Filter Kinds
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterKindDetailFactory filterKindDetailFactory;

    @Inject
    protected FilterKindFactory filterKindFactory;

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
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM filterkinds, filterkinddetails
                WHERE fltk_activedetailid = fltkdt_filterkinddetailid
                """);
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
    
    private static final Map<EntityPermission, String> getFilterKindByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                """
                SELECT _ALL_
                FROM filterkinds, filterkinddetails
                WHERE fltk_activedetailid = fltkdt_filterkinddetailid AND fltkdt_filterkindname = ?
                """);
        queryMap.put(EntityPermission.READ_WRITE,
                """
                SELECT _ALL_
                FROM filterkinds, filterkinddetails
                WHERE fltk_activedetailid = fltkdt_filterkinddetailid AND fltkdt_filterkindname = ?
                FOR UPDATE
                """);
        getFilterKindByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public FilterKind getFilterKindByName(String filterKindName, EntityPermission entityPermission) {
        return filterKindFactory.getEntityFromQuery(entityPermission, getFilterKindByNameQueries,
                filterKindName);
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

    private static final Map<EntityPermission, String> getDefaultFilterKindQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                """
                SELECT _ALL_
                FROM filterkinds, filterkinddetails
                WHERE fltk_activedetailid = fltkdt_filterkinddetailid AND fltkdt_isdefault = 1
                """);
        queryMap.put(EntityPermission.READ_WRITE,
                """
                SELECT _ALL_
                FROM filterkinds, filterkinddetails
                WHERE fltk_activedetailid = fltkdt_filterkinddetailid AND fltkdt_isdefault = 1
                FOR UPDATE
                """);
        getDefaultFilterKindQueries = Collections.unmodifiableMap(queryMap);
    }

    public FilterKind getDefaultFilterKind(EntityPermission entityPermission) {
        return filterKindFactory.getEntityFromQuery(entityPermission, getDefaultFilterKindQueries);
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

    private static final Map<EntityPermission, String> getFilterKindsQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                """
                SELECT _ALL_
                FROM filterkinds, filterkinddetails
                WHERE fltk_activedetailid = fltkdt_filterkinddetailid
                ORDER BY fltkdt_sortorder, fltkdt_filterkindname
                _LIMIT_
                """);
        queryMap.put(EntityPermission.READ_WRITE,
                """
                SELECT _ALL_
                FROM filterkinds, filterkinddetails
                WHERE fltk_activedetailid = fltkdt_filterkinddetailid
                FOR UPDATE
                """);
        getFilterKindsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FilterKind> getFilterKinds(EntityPermission entityPermission) {
        return filterKindFactory.getEntitiesFromQuery(entityPermission, getFilterKindsQueries);
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
        filterControl.deleteFilterTypesByFilterKind(filterKind, deletedBy);
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

    public FilterKindDescription createFilterKindDescription(FilterKind filterKind, Language language, String description,
            BasePK createdBy) {
        var filterKindDescription = filterKindDescriptionFactory.create(filterKind,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(filterKind.getPrimaryKey(), EventTypes.MODIFY, filterKindDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return filterKindDescription;
    }

    private static final Map<EntityPermission, String> getFilterKindDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                """
                SELECT _ALL_
                FROM filterkinddescriptions
                WHERE fltkd_fltk_filterkindid = ? AND fltkd_lang_languageid = ? AND fltkd_thrutime = ?
                """);
        queryMap.put(EntityPermission.READ_WRITE,
                """
                SELECT _ALL_
                FROM filterkinddescriptions
                WHERE fltkd_fltk_filterkindid = ? AND fltkd_lang_languageid = ? AND fltkd_thrutime = ?
                FOR UPDATE
                """);
        getFilterKindDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private FilterKindDescription getFilterKindDescription(FilterKind filterKind, Language language, EntityPermission entityPermission) {
        return filterKindDescriptionFactory.getEntityFromQuery(entityPermission, getFilterKindDescriptionQueries,
                filterKind, language, Session.MAX_TIME);
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

    private static final Map<EntityPermission, String> getFilterKindDescriptionsByFilterKindQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                """
                SELECT _ALL_
                FROM filterkinddescriptions, languages
                WHERE fltkd_fltk_filterkindid = ? AND fltkd_thrutime = ? AND fltkd_lang_languageid = lang_languageid
                ORDER BY lang_sortorder, lang_languageisoname
                """);
        queryMap.put(EntityPermission.READ_WRITE,
                """
                SELECT _ALL_
                FROM filterkinddescriptions
                WHERE fltkd_fltk_filterkindid = ? AND fltkd_thrutime = ?
                FOR UPDATE
                """);
        getFilterKindDescriptionsByFilterKindQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FilterKindDescription> getFilterKindDescriptionsByFilterKind(FilterKind filterKind, EntityPermission entityPermission) {
        return filterKindDescriptionFactory.getEntitiesFromQuery(entityPermission, getFilterKindDescriptionsByFilterKindQueries,
                filterKind, Session.MAX_TIME);
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

