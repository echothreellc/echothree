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
import com.echothree.model.control.filter.common.choice.FilterTypeChoicesBean;
import com.echothree.model.control.filter.common.transfer.FilterTypeDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterTypeTransfer;
import com.echothree.model.control.filter.server.transfer.FilterTypeDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterTypeTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.filter.common.pk.FilterTypePK;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.filter.server.entity.FilterTypeDescription;
import com.echothree.model.data.filter.server.factory.FilterTypeDescriptionFactory;
import com.echothree.model.data.filter.server.factory.FilterTypeDetailFactory;
import com.echothree.model.data.filter.server.factory.FilterTypeFactory;
import com.echothree.model.data.filter.server.value.FilterTypeDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterTypeDetailValue;
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
public class FilterTypeControl
        extends BaseModelControl {

    /** Creates a new instance of FilterTypeControl */
    protected FilterTypeControl() {
        super();
    }

    @Inject
    FilterControl filterControl;

    @Inject
    FilterTypeDescriptionTransferCache filterTypeDescriptionTransferCache;

    @Inject
    FilterTypeTransferCache filterTypeTransferCache;

    // --------------------------------------------------------------------------------
    //   Filter Types
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterTypeFactory filterTypeFactory;

    @Inject
    protected FilterTypeDetailFactory filterTypeDetailFactory;

    public FilterType createFilterType(FilterKind filterKind, String filterTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultFilterType = getDefaultFilterType(filterKind);
        var defaultFound = defaultFilterType != null;

        if(defaultFound && isDefault) {
            var defaultFilterTypeDetailValue = getDefaultFilterTypeDetailValueForUpdate(filterKind);

            defaultFilterTypeDetailValue.setIsDefault(false);
            updateFilterTypeFromValue(defaultFilterTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var filterType = filterTypeFactory.create();
        var filterTypeDetail = filterTypeDetailFactory.create( filterType, filterKind, filterTypeName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        filterType = filterTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                filterType.getPrimaryKey());
        filterType.setActiveDetail(filterTypeDetail);
        filterType.setLastDetail(filterTypeDetail);
        filterType.store();

        sendEvent(filterType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return filterType;
    }

    public long countFilterTypesByFilterKind(FilterKind filterKind) {
        return session.queryForLong(
                """
                SELECT COUNT(*)
                FROM filtertypes, filtertypedetails
                WHERE flttyp_activedetailid = flttypdt_filtertypedetailid AND flttypdt_fltk_filterkindid = ?
                """,
                filterKind);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.FilterType */
    public FilterType getFilterTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new FilterTypePK(entityInstance.getEntityUniqueId());

        return filterTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public FilterType getFilterTypeByEntityInstance(EntityInstance entityInstance) {
        return getFilterTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public FilterType getFilterTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getFilterTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getFilterTypesQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                """
                SELECT _ALL_
                FROM filtertypes, filtertypedetails
                WHERE flttyp_activedetailid = flttypdt_filtertypedetailid AND flttypdt_fltk_filterkindid = ?
                ORDER BY flttypdt_sortorder, flttypdt_filtertypename
                _LIMIT_
                """);
        queryMap.put(EntityPermission.READ_WRITE,
                """
                SELECT _ALL_
                FROM filtertypes, filtertypedetails
                WHERE flttyp_activedetailid = flttypdt_filtertypedetailid AND flttypdt_fltk_filterkindid = ?
                FOR UPDATE
                """);
        getFilterTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FilterType> getFilterTypes(FilterKind filterKind, EntityPermission entityPermission) {
        return filterTypeFactory.getEntitiesFromQuery(entityPermission, getFilterTypesQueries,
                filterKind);
    }

    public List<FilterType> getFilterTypes(FilterKind filterKind) {
        return getFilterTypes(filterKind, EntityPermission.READ_ONLY);
    }

    public List<FilterType> getFilterTypesForUpdate(FilterKind filterKind) {
        return getFilterTypes(filterKind, EntityPermission.READ_WRITE);
    }

    private static final Map<EntityPermission, String> getDefaultFilterTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                """
                SELECT _ALL_
                FROM filtertypes, filtertypedetails
                WHERE flttyp_activedetailid = flttypdt_filtertypedetailid
                AND flttypdt_fltk_filterkindid = ? AND flttypdt_isdefault = 1
                """);
        queryMap.put(EntityPermission.READ_WRITE,
                """
                SELECT _ALL_
                FROM filtertypes, filtertypedetails
                WHERE flttyp_activedetailid = flttypdt_filtertypedetailid
                AND flttypdt_fltk_filterkindid = ? AND flttypdt_isdefault = 1
                FOR UPDATE
                """);
        getDefaultFilterTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public FilterType getDefaultFilterType(FilterKind filterKind, EntityPermission entityPermission) {
        return filterTypeFactory.getEntityFromQuery(entityPermission, getDefaultFilterTypeQueries,
                filterKind);
    }

    public FilterType getDefaultFilterType(FilterKind filterKind) {
        return getDefaultFilterType(filterKind, EntityPermission.READ_ONLY);
    }

    public FilterType getDefaultFilterTypeForUpdate(FilterKind filterKind) {
        return getDefaultFilterType(filterKind, EntityPermission.READ_WRITE);
    }

    public FilterTypeDetailValue getDefaultFilterTypeDetailValueForUpdate(FilterKind filterKind) {
        return getDefaultFilterTypeForUpdate(filterKind).getLastDetailForUpdate().getFilterTypeDetailValue().clone();
    }

    private static final Map<EntityPermission, String> getFilterTypeByNameQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                """
                SELECT _ALL_
                FROM filtertypes, filtertypedetails
                WHERE flttyp_activedetailid = flttypdt_filtertypedetailid
                AND flttypdt_fltk_filterkindid = ? AND flttypdt_filtertypename = ?
                """);
        queryMap.put(EntityPermission.READ_WRITE,
                """
                SELECT _ALL_
                FROM filtertypes, filtertypedetails
                WHERE flttyp_activedetailid = flttypdt_filtertypedetailid
                AND flttypdt_fltk_filterkindid = ? AND flttypdt_filtertypename = ?
                FOR UPDATE
                """);
        getFilterTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public FilterType getFilterTypeByName(FilterKind filterKind, String filterTypeName, EntityPermission entityPermission) {
        return filterTypeFactory.getEntityFromQuery(entityPermission, getFilterTypeByNameQueries,
                filterKind, filterTypeName);
    }

    public FilterType getFilterTypeByName(FilterKind filterKind, String filterTypeName) {
        return getFilterTypeByName(filterKind, filterTypeName, EntityPermission.READ_ONLY);
    }

    public FilterType getFilterTypeByNameForUpdate(FilterKind filterKind, String filterTypeName) {
        return getFilterTypeByName(filterKind, filterTypeName, EntityPermission.READ_WRITE);
    }

    public FilterTypeDetailValue getFilterTypeDetailValueForUpdate(FilterType filterType) {
        return filterType == null? null: filterType.getLastDetailForUpdate().getFilterTypeDetailValue().clone();
    }

    public FilterTypeDetailValue getFilterTypeDetailValueByNameForUpdate(FilterKind filterKind, String filterTypeName) {
        return getFilterTypeDetailValueForUpdate(getFilterTypeByNameForUpdate(filterKind, filterTypeName));
    }

    public FilterTypeChoicesBean getFilterTypeChoices(String defaultFilterTypeChoice, Language language,
            boolean allowNullChoice, FilterKind filterKind) {
        var filterTypes = getFilterTypes(filterKind);
        var size = filterTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultFilterTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var filterType : filterTypes) {
            var filterTypeDetail = filterType.getLastDetail();
            var label = getBestFilterTypeDescription(filterType, language);
            var value = filterTypeDetail.getFilterTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultFilterTypeChoice != null && defaultFilterTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && filterTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new FilterTypeChoicesBean(labels, values, defaultValue);
    }

    public FilterTypeTransfer getFilterTypeTransfer(UserVisit userVisit, FilterType filterType) {
        return filterTypeTransferCache.getTransfer(userVisit, filterType);
    }

    public List<FilterTypeTransfer> getFilterTypeTransfers(UserVisit userVisit, Collection<FilterType> filterTypes) {
        var filterTypeTransfers = new ArrayList<FilterTypeTransfer>(filterTypes.size());

        filterTypes.forEach((filterType) ->
            filterTypeTransfers.add(filterTypeTransferCache.getTransfer(userVisit, filterType))
        );

        return filterTypeTransfers;
    }

    public List<FilterTypeTransfer> getFilterTypeTransfersByFilterKind(UserVisit userVisit, FilterKind filterKind) {
        return getFilterTypeTransfers(userVisit, getFilterTypes(filterKind));
    }

    private void updateFilterTypeFromValue(FilterTypeDetailValue filterTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(filterTypeDetailValue.hasBeenModified()) {
            var filterType = filterTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     filterTypeDetailValue.getFilterTypePK());
            var filterTypeDetail = filterType.getActiveDetailForUpdate();

            filterTypeDetail.setThruTime(session.getStartTime());
            filterTypeDetail.store();

            var filterTypePK = filterTypeDetail.getFilterTypePK();
            var filterKind = filterTypeDetail.getFilterKind();
            var filterKindPK = filterKind.getPrimaryKey();
            var filterTypeName = filterTypeDetailValue.getFilterTypeName();
            var isDefault = filterTypeDetailValue.getIsDefault();
            var sortOrder = filterTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultFilterType = getDefaultFilterType(filterKind);
                var defaultFound = defaultFilterType != null && !defaultFilterType.equals(filterType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultFilterTypeDetailValue = getDefaultFilterTypeDetailValueForUpdate(filterKind);

                    defaultFilterTypeDetailValue.setIsDefault(false);
                    updateFilterTypeFromValue(defaultFilterTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            filterTypeDetail = filterTypeDetailFactory.create(filterTypePK, filterKindPK, filterTypeName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            filterType.setActiveDetail(filterTypeDetail);
            filterType.setLastDetail(filterTypeDetail);

            sendEvent(filterTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateFilterTypeFromValue(FilterTypeDetailValue filterTypeDetailValue, BasePK updatedBy) {
        updateFilterTypeFromValue(filterTypeDetailValue, true, updatedBy);
    }

    public void deleteFilterType(FilterType filterType, BasePK deletedBy) {
        filterControl.deleteFiltersByFilterType(filterType, deletedBy);
        deleteFilterTypeDescriptionsByFilterType(filterType, deletedBy);

        var filterTypeDetail = filterType.getLastDetailForUpdate();
        filterTypeDetail.setThruTime(session.getStartTime());
        filterType.setActiveDetail(null);
        filterType.store();

        // Check for default, and pick one if necessary
        var filterKind = filterTypeDetail.getFilterKind();
        var defaultFilterType = getDefaultFilterType(filterKind);
        if(defaultFilterType == null) {
            var filterTypes = getFilterTypesForUpdate(filterKind);

            if(!filterTypes.isEmpty()) {
                var iter = filterTypes.iterator();
                if(iter.hasNext()) {
                    defaultFilterType = iter.next();
                }
                var filterTypeDetailValue = Objects.requireNonNull(defaultFilterType).getLastDetailForUpdate().getFilterTypeDetailValue().clone();

                filterTypeDetailValue.setIsDefault(true);
                updateFilterTypeFromValue(filterTypeDetailValue, false, deletedBy);
            }
        }

        sendEvent(filterType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteFilterTypesByFilterKind(FilterKind filterKind, BasePK deletedBy) {
        var filterTypes = getFilterTypesForUpdate(filterKind);

        filterTypes.forEach((filterType) -> 
                deleteFilterType(filterType, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Filter Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterTypeDescriptionFactory filterTypeDescriptionFactory;

    public FilterTypeDescription createFilterTypeDescription(FilterType filterType, Language language, String description,
            BasePK createdBy) {
        var filterTypeDescription = filterTypeDescriptionFactory.create(filterType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(filterType.getPrimaryKey(), EventTypes.MODIFY, filterTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return filterTypeDescription;
    }

    private static final Map<EntityPermission, String> getFilterTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                """
                SELECT _ALL_
                FROM filtertypedescriptions
                WHERE flttypd_flttyp_filtertypeid = ? AND flttypd_lang_languageid = ? AND flttypd_thrutime = ?
                """);
        queryMap.put(EntityPermission.READ_WRITE,
                """
                SELECT _ALL_
                FROM filtertypedescriptions
                WHERE flttypd_flttyp_filtertypeid = ? AND flttypd_lang_languageid = ? AND flttypd_thrutime = ?
                FOR UPDATE
                """);
        getFilterTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private FilterTypeDescription getFilterTypeDescription(FilterType filterType, Language language, EntityPermission entityPermission) {
        return filterTypeDescriptionFactory.getEntityFromQuery(entityPermission, getFilterTypeDescriptionQueries,
                filterType, language, Session.MAX_TIME);
    }

    public FilterTypeDescription getFilterTypeDescription(FilterType filterType, Language language) {
        return getFilterTypeDescription(filterType, language, EntityPermission.READ_ONLY);
    }

    public FilterTypeDescription getFilterTypeDescriptionForUpdate(FilterType filterType, Language language) {
        return getFilterTypeDescription(filterType, language, EntityPermission.READ_WRITE);
    }

    public FilterTypeDescriptionValue getFilterTypeDescriptionValue(FilterTypeDescription filterTypeDescription) {
        return filterTypeDescription == null? null: filterTypeDescription.getFilterTypeDescriptionValue().clone();
    }

    public FilterTypeDescriptionValue getFilterTypeDescriptionValueForUpdate(FilterType filterType, Language language) {
        return getFilterTypeDescriptionValue(getFilterTypeDescriptionForUpdate(filterType, language));
    }

    private static final Map<EntityPermission, String> getFilterTypeDescriptionsByFilterTypeQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                """
                SELECT _ALL_
                FROM filtertypedescriptions, languages
                WHERE flttypd_flttyp_filtertypeid = ? AND flttypd_thrutime = ? AND flttypd_lang_languageid = lang_languageid
                ORDER BY lang_sortorder, lang_languageisoname
                """);
        queryMap.put(EntityPermission.READ_WRITE,
                """
                SELECT _ALL_
                FROM filtertypedescriptions
                WHERE flttypd_flttyp_filtertypeid = ? AND flttypd_thrutime = ?
                FOR UPDATE
                """);
        getFilterTypeDescriptionsByFilterTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FilterTypeDescription> getFilterTypeDescriptionsByFilterType(FilterType filterType, EntityPermission entityPermission) {
        return filterTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, getFilterTypeDescriptionsByFilterTypeQueries,
                filterType, Session.MAX_TIME);
    }

    public List<FilterTypeDescription> getFilterTypeDescriptionsByFilterType(FilterType filterType) {
        return getFilterTypeDescriptionsByFilterType(filterType, EntityPermission.READ_ONLY);
    }

    public List<FilterTypeDescription> getFilterTypeDescriptionsByFilterTypeForUpdate(FilterType filterType) {
        return getFilterTypeDescriptionsByFilterType(filterType, EntityPermission.READ_WRITE);
    }

    public String getBestFilterTypeDescription(FilterType filterType, Language language) {
        String description;
        var filterTypeDescription = getFilterTypeDescription(filterType, language);

        if(filterTypeDescription == null && !language.getIsDefault()) {
            filterTypeDescription = getFilterTypeDescription(filterType, partyControl.getDefaultLanguage());
        }

        if(filterTypeDescription == null) {
            description = filterType.getLastDetail().getFilterTypeName();
        } else {
            description = filterTypeDescription.getDescription();
        }

        return description;
    }

    public FilterTypeDescriptionTransfer getFilterTypeDescriptionTransfer(UserVisit userVisit, FilterTypeDescription filterTypeDescription) {
        return filterTypeDescriptionTransferCache.getTransfer(userVisit, filterTypeDescription);
    }

    public List<FilterTypeDescriptionTransfer> getFilterTypeDescriptionTransfersByFilterType(UserVisit userVisit, FilterType filterType) {
        var filterTypeDescriptions = getFilterTypeDescriptionsByFilterType(filterType);
        List<FilterTypeDescriptionTransfer> filterTypeDescriptionTransfers = new ArrayList<>(filterTypeDescriptions.size());

        filterTypeDescriptions.forEach((filterTypeDescription) -> {
            filterTypeDescriptionTransfers.add(filterTypeDescriptionTransferCache.getTransfer(userVisit, filterTypeDescription));
        });

        return filterTypeDescriptionTransfers;
    }

    public void updateFilterTypeDescriptionFromValue(FilterTypeDescriptionValue filterTypeDescriptionValue, BasePK updatedBy) {
        if(filterTypeDescriptionValue.hasBeenModified()) {
            var filterTypeDescription = filterTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     filterTypeDescriptionValue.getPrimaryKey());

            filterTypeDescription.setThruTime(session.getStartTime());
            filterTypeDescription.store();

            var filterType = filterTypeDescription.getFilterType();
            var language = filterTypeDescription.getLanguage();
            var description = filterTypeDescriptionValue.getDescription();

            filterTypeDescription = filterTypeDescriptionFactory.create(filterType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(filterType.getPrimaryKey(), EventTypes.MODIFY, filterTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteFilterTypeDescription(FilterTypeDescription filterTypeDescription, BasePK deletedBy) {
        filterTypeDescription.setThruTime(session.getStartTime());

        sendEvent(filterTypeDescription.getFilterTypePK(), EventTypes.MODIFY, filterTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteFilterTypeDescriptionsByFilterType(FilterType filterType, BasePK deletedBy) {
        var filterTypeDescriptions = getFilterTypeDescriptionsByFilterTypeForUpdate(filterType);

        filterTypeDescriptions.forEach((filterTypeDescription) -> 
                deleteFilterTypeDescription(filterTypeDescription, deletedBy)
        );
    }

}

