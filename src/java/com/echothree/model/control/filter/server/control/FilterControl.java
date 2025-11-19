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

package com.echothree.model.control.filter.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.filter.common.choice.FilterAdjustmentChoicesBean;
import com.echothree.model.control.filter.common.choice.FilterAdjustmentSourceChoicesBean;
import com.echothree.model.control.filter.common.choice.FilterAdjustmentTypeChoicesBean;
import com.echothree.model.control.filter.common.choice.FilterChoicesBean;
import com.echothree.model.control.filter.common.choice.FilterKindChoicesBean;
import com.echothree.model.control.filter.common.choice.FilterStepChoicesBean;
import com.echothree.model.control.filter.common.choice.FilterTypeChoicesBean;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentAmountTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentFixedAmountTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentPercentTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentSourceTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentTypeTransfer;
import com.echothree.model.control.filter.common.transfer.FilterDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterEntranceStepTransfer;
import com.echothree.model.control.filter.common.transfer.FilterKindDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterKindTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepDestinationTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepElementDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepElementTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepTransfer;
import com.echothree.model.control.filter.common.transfer.FilterTransfer;
import com.echothree.model.control.filter.common.transfer.FilterTypeDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterTypeTransfer;
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentAmountTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentFixedAmountTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentPercentTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentSourceTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentTypeTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterEntranceStepTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterKindDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterKindTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepDestinationTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepElementDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepElementTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterTypeDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterTypeTransferCache;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.filter.common.pk.FilterAdjustmentPK;
import com.echothree.model.data.filter.common.pk.FilterKindPK;
import com.echothree.model.data.filter.common.pk.FilterPK;
import com.echothree.model.data.filter.common.pk.FilterStepPK;
import com.echothree.model.data.filter.common.pk.FilterTypePK;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentAmount;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentDescription;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentFixedAmount;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentPercent;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentSource;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentSourceDescription;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentType;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentTypeDescription;
import com.echothree.model.data.filter.server.entity.FilterDescription;
import com.echothree.model.data.filter.server.entity.FilterEntranceStep;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterKindDescription;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepDescription;
import com.echothree.model.data.filter.server.entity.FilterStepDestination;
import com.echothree.model.data.filter.server.entity.FilterStepElement;
import com.echothree.model.data.filter.server.entity.FilterStepElementDescription;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.filter.server.entity.FilterTypeDescription;
import com.echothree.model.data.filter.server.factory.FilterAdjustmentAmountFactory;
import com.echothree.model.data.filter.server.factory.FilterAdjustmentDescriptionFactory;
import com.echothree.model.data.filter.server.factory.FilterAdjustmentDetailFactory;
import com.echothree.model.data.filter.server.factory.FilterAdjustmentFactory;
import com.echothree.model.data.filter.server.factory.FilterAdjustmentFixedAmountFactory;
import com.echothree.model.data.filter.server.factory.FilterAdjustmentPercentFactory;
import com.echothree.model.data.filter.server.factory.FilterAdjustmentSourceDescriptionFactory;
import com.echothree.model.data.filter.server.factory.FilterAdjustmentSourceFactory;
import com.echothree.model.data.filter.server.factory.FilterAdjustmentTypeDescriptionFactory;
import com.echothree.model.data.filter.server.factory.FilterAdjustmentTypeFactory;
import com.echothree.model.data.filter.server.factory.FilterDescriptionFactory;
import com.echothree.model.data.filter.server.factory.FilterDetailFactory;
import com.echothree.model.data.filter.server.factory.FilterEntranceStepFactory;
import com.echothree.model.data.filter.server.factory.FilterFactory;
import com.echothree.model.data.filter.server.factory.FilterKindDescriptionFactory;
import com.echothree.model.data.filter.server.factory.FilterKindDetailFactory;
import com.echothree.model.data.filter.server.factory.FilterKindFactory;
import com.echothree.model.data.filter.server.factory.FilterStepDescriptionFactory;
import com.echothree.model.data.filter.server.factory.FilterStepDestinationFactory;
import com.echothree.model.data.filter.server.factory.FilterStepDetailFactory;
import com.echothree.model.data.filter.server.factory.FilterStepElementDescriptionFactory;
import com.echothree.model.data.filter.server.factory.FilterStepElementDetailFactory;
import com.echothree.model.data.filter.server.factory.FilterStepElementFactory;
import com.echothree.model.data.filter.server.factory.FilterStepFactory;
import com.echothree.model.data.filter.server.factory.FilterTypeDescriptionFactory;
import com.echothree.model.data.filter.server.factory.FilterTypeDetailFactory;
import com.echothree.model.data.filter.server.factory.FilterTypeFactory;
import com.echothree.model.data.filter.server.value.FilterAdjustmentAmountValue;
import com.echothree.model.data.filter.server.value.FilterAdjustmentDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterAdjustmentDetailValue;
import com.echothree.model.data.filter.server.value.FilterAdjustmentFixedAmountValue;
import com.echothree.model.data.filter.server.value.FilterAdjustmentPercentValue;
import com.echothree.model.data.filter.server.value.FilterDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterDetailValue;
import com.echothree.model.data.filter.server.value.FilterKindDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterKindDetailValue;
import com.echothree.model.data.filter.server.value.FilterStepDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterStepDetailValue;
import com.echothree.model.data.filter.server.value.FilterStepElementDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterStepElementDetailValue;
import com.echothree.model.data.filter.server.value.FilterTypeDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class FilterControl
        extends BaseModelControl {
    
    /** Creates a new instance of FilterControl */
    protected FilterControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Transfer Caches
    // --------------------------------------------------------------------------------

    @Inject
    FilterKindTransferCache filterKindTransferCache;

    @Inject
    FilterKindDescriptionTransferCache filterKindDescriptionTransferCache;

    @Inject
    FilterTypeTransferCache filterTypeTransferCache;

    @Inject
    FilterTypeDescriptionTransferCache filterTypeDescriptionTransferCache;

    @Inject
    FilterAdjustmentDescriptionTransferCache filterAdjustmentDescriptionTransferCache;

    @Inject
    FilterAdjustmentTypeTransferCache filterAdjustmentTypeTransferCache;

    @Inject
    FilterAdjustmentSourceTransferCache filterAdjustmentSourceTransferCache;

    @Inject
    FilterAdjustmentTransferCache filterAdjustmentTransferCache;

    @Inject
    FilterTransferCache filterTransferCache;

    @Inject
    FilterDescriptionTransferCache filterDescriptionTransferCache;

    @Inject
    FilterAdjustmentAmountTransferCache filterAdjustmentAmountTransferCache;

    @Inject
    FilterAdjustmentFixedAmountTransferCache filterAdjustmentFixedAmountTransferCache;

    @Inject
    FilterAdjustmentPercentTransferCache filterAdjustmentPercentTransferCache;

    @Inject
    FilterStepTransferCache filterStepTransferCache;

    @Inject
    FilterStepDescriptionTransferCache filterStepDescriptionTransferCache;

    @Inject
    FilterEntranceStepTransferCache filterEntranceStepTransferCache;

    @Inject
    FilterStepDestinationTransferCache filterStepDestinationTransferCache;

    @Inject
    FilterStepElementTransferCache filterStepElementTransferCache;

    @Inject
    FilterStepElementDescriptionTransferCache filterStepElementDescriptionTransferCache;

    // --------------------------------------------------------------------------------
    //   Filter Kinds
    // --------------------------------------------------------------------------------

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

        var filterKind = FilterKindFactory.getInstance().create();
        var filterKindDetail = FilterKindDetailFactory.getInstance().create(filterKind, filterKindName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        filterKind = FilterKindFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                filterKind.getPrimaryKey());
        filterKind.setActiveDetail(filterKindDetail);
        filterKind.setLastDetail(filterKindDetail);
        filterKind.store();

        sendEvent(filterKind.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return filterKind;
    }
    
    public long countFilterKinds() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM filterkinds, filterkinddetails " +
                "WHERE fltk_activedetailid = fltkdt_filterkinddetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.FilterKind */
    public FilterKind getFilterKindByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new FilterKindPK(entityInstance.getEntityUniqueId());

        return FilterKindFactory.getInstance().getEntityFromPK(entityPermission, pk);
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
                "SELECT _ALL_ "
                + "FROM filterkinds, filterkinddetails "
                + "WHERE fltk_activedetailid = fltkdt_filterkinddetailid AND fltkdt_filterkindname = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM filterkinds, filterkinddetails "
                + "WHERE fltk_activedetailid = fltkdt_filterkinddetailid AND fltkdt_filterkindname = ? "
                + "FOR UPDATE");
        getFilterKindByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public FilterKind getFilterKindByName(String filterKindName, EntityPermission entityPermission) {
        return FilterKindFactory.getInstance().getEntityFromQuery(entityPermission, getFilterKindByNameQueries,
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
                "SELECT _ALL_ "
                + "FROM filterkinds, filterkinddetails "
                + "WHERE fltk_activedetailid = fltkdt_filterkinddetailid AND fltkdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM filterkinds, filterkinddetails "
                + "WHERE fltk_activedetailid = fltkdt_filterkinddetailid AND fltkdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultFilterKindQueries = Collections.unmodifiableMap(queryMap);
    }

    public FilterKind getDefaultFilterKind(EntityPermission entityPermission) {
        return FilterKindFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultFilterKindQueries);
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
                "SELECT _ALL_ "
                + "FROM filterkinds, filterkinddetails "
                + "WHERE fltk_activedetailid = fltkdt_filterkinddetailid "
                + "ORDER BY fltkdt_sortorder, fltkdt_filterkindname "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM filterkinds, filterkinddetails "
                + "WHERE fltk_activedetailid = fltkdt_filterkinddetailid "
                + "FOR UPDATE");
        getFilterKindsQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FilterKind> getFilterKinds(EntityPermission entityPermission) {
        return FilterKindFactory.getInstance().getEntitiesFromQuery(entityPermission, getFilterKindsQueries);
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
        var filterKind = FilterKindFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, filterKindDetailValue.getFilterKindPK());
        var filterKindDetail = filterKind.getActiveDetailForUpdate();

        filterKindDetail.setThruTime(session.START_TIME_LONG);
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

        filterKindDetail = FilterKindDetailFactory.getInstance().create(filterKindPK, filterKindName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        filterKind.setActiveDetail(filterKindDetail);
        filterKind.setLastDetail(filterKindDetail);
        filterKind.store();

        sendEvent(filterKindPK, EventTypes.MODIFY, null, null, updatedBy);
    }

    public void updateFilterKindFromValue(FilterKindDetailValue filterKindDetailValue, BasePK updatedBy) {
        updateFilterKindFromValue(filterKindDetailValue, true, updatedBy);
    }

    public void deleteFilterKind(FilterKind filterKind, BasePK deletedBy) {
        deleteFilterKindDescriptionsByFilterKind(filterKind, deletedBy);

        var filterKindDetail = filterKind.getLastDetailForUpdate();
        filterKindDetail.setThruTime(session.START_TIME_LONG);
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

    public FilterKindDescription createFilterKindDescription(FilterKind filterKind, Language language, String description,
            BasePK createdBy) {
        var filterKindDescription = FilterKindDescriptionFactory.getInstance().create(filterKind,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(filterKind.getPrimaryKey(), EventTypes.MODIFY, filterKindDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return filterKindDescription;
    }

    private static final Map<EntityPermission, String> getFilterKindDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM filterkinddescriptions "
                + "WHERE fltkd_fltk_filterkindid = ? AND fltkd_lang_languageid = ? AND fltkd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM filterkinddescriptions "
                + "WHERE fltkd_fltk_filterkindid = ? AND fltkd_lang_languageid = ? AND fltkd_thrutime = ? "
                + "FOR UPDATE");
        getFilterKindDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private FilterKindDescription getFilterKindDescription(FilterKind filterKind, Language language, EntityPermission entityPermission) {
        return FilterKindDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getFilterKindDescriptionQueries,
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
                "SELECT _ALL_ "
                + "FROM filterkinddescriptions, languages "
                + "WHERE fltkd_fltk_filterkindid = ? AND fltkd_thrutime = ? AND fltkd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM filterkinddescriptions "
                + "WHERE fltkd_fltk_filterkindid = ? AND fltkd_thrutime = ? "
                + "FOR UPDATE");
        getFilterKindDescriptionsByFilterKindQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FilterKindDescription> getFilterKindDescriptionsByFilterKind(FilterKind filterKind, EntityPermission entityPermission) {
        return FilterKindDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getFilterKindDescriptionsByFilterKindQueries,
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
            var filterKindDescription = FilterKindDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterKindDescriptionValue.getPrimaryKey());

            filterKindDescription.setThruTime(session.START_TIME_LONG);
            filterKindDescription.store();

            var filterKind = filterKindDescription.getFilterKind();
            var language = filterKindDescription.getLanguage();
            var description = filterKindDescriptionValue.getDescription();

            filterKindDescription = FilterKindDescriptionFactory.getInstance().create(filterKind, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(filterKind.getPrimaryKey(), EventTypes.MODIFY, filterKindDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteFilterKindDescription(FilterKindDescription filterKindDescription, BasePK deletedBy) {
        filterKindDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(filterKindDescription.getFilterKindPK(), EventTypes.MODIFY, filterKindDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteFilterKindDescriptionsByFilterKind(FilterKind filterKind, BasePK deletedBy) {
        var filterKindDescriptions = getFilterKindDescriptionsByFilterKindForUpdate(filterKind);

        filterKindDescriptions.forEach((filterKindDescription) -> 
                deleteFilterKindDescription(filterKindDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Filter Types
    // --------------------------------------------------------------------------------

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

        var filterType = FilterTypeFactory.getInstance().create();
        var filterTypeDetail = FilterTypeDetailFactory.getInstance().create(session, filterType, filterKind, filterTypeName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        filterType = FilterTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                filterType.getPrimaryKey());
        filterType.setActiveDetail(filterTypeDetail);
        filterType.setLastDetail(filterTypeDetail);
        filterType.store();

        sendEvent(filterType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return filterType;
    }

    public long countFilterTypesByFilterKind(FilterKind filterKind) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM filtertypes, filtertypedetails "
                + "WHERE flttyp_activedetailid = flttypdt_filtertypedetailid AND flttypdt_fltk_filterkindid = ?",
                filterKind);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.FilterType */
    public FilterType getFilterTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new FilterTypePK(entityInstance.getEntityUniqueId());

        return FilterTypeFactory.getInstance().getEntityFromPK(entityPermission, pk);
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
                "SELECT _ALL_ "
                + "FROM filtertypes, filtertypedetails "
                + "WHERE flttyp_activedetailid = flttypdt_filtertypedetailid AND flttypdt_fltk_filterkindid = ? "
                + "ORDER BY flttypdt_sortorder, flttypdt_filtertypename "
                + "_LIMIT_");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM filtertypes, filtertypedetails "
                + "WHERE flttyp_activedetailid = flttypdt_filtertypedetailid AND flttypdt_fltk_filterkindid = ? "
                + "FOR UPDATE");
        getFilterTypesQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FilterType> getFilterTypes(FilterKind filterKind, EntityPermission entityPermission) {
        return FilterTypeFactory.getInstance().getEntitiesFromQuery(entityPermission, getFilterTypesQueries,
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
                "SELECT _ALL_ "
                + "FROM filtertypes, filtertypedetails "
                + "WHERE flttyp_activedetailid = flttypdt_filtertypedetailid "
                + "AND flttypdt_fltk_filterkindid = ? AND flttypdt_isdefault = 1");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM filtertypes, filtertypedetails "
                + "WHERE flttyp_activedetailid = flttypdt_filtertypedetailid "
                + "AND flttypdt_fltk_filterkindid = ? AND flttypdt_isdefault = 1 "
                + "FOR UPDATE");
        getDefaultFilterTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    public FilterType getDefaultFilterType(FilterKind filterKind, EntityPermission entityPermission) {
        return FilterTypeFactory.getInstance().getEntityFromQuery(entityPermission, getDefaultFilterTypeQueries,
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
                "SELECT _ALL_ "
                + "FROM filtertypes, filtertypedetails "
                + "WHERE flttyp_activedetailid = flttypdt_filtertypedetailid "
                + "AND flttypdt_fltk_filterkindid = ? AND flttypdt_filtertypename = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM filtertypes, filtertypedetails "
                + "WHERE flttyp_activedetailid = flttypdt_filtertypedetailid "
                + "AND flttypdt_fltk_filterkindid = ? AND flttypdt_filtertypename = ? "
                + "FOR UPDATE");
        getFilterTypeByNameQueries = Collections.unmodifiableMap(queryMap);
    }

    public FilterType getFilterTypeByName(FilterKind filterKind, String filterTypeName, EntityPermission entityPermission) {
        return FilterTypeFactory.getInstance().getEntityFromQuery(entityPermission, getFilterTypeByNameQueries,
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
            var filterType = FilterTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterTypeDetailValue.getFilterTypePK());
            var filterTypeDetail = filterType.getActiveDetailForUpdate();

            filterTypeDetail.setThruTime(session.START_TIME_LONG);
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

            filterTypeDetail = FilterTypeDetailFactory.getInstance().create(filterTypePK, filterKindPK, filterTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            filterType.setActiveDetail(filterTypeDetail);
            filterType.setLastDetail(filterTypeDetail);

            sendEvent(filterTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateFilterTypeFromValue(FilterTypeDetailValue filterTypeDetailValue, BasePK updatedBy) {
        updateFilterTypeFromValue(filterTypeDetailValue, true, updatedBy);
    }

    public void deleteFilterType(FilterType filterType, BasePK deletedBy) {
        deleteFilterTypeDescriptionsByFilterType(filterType, deletedBy);

        var filterTypeDetail = filterType.getLastDetailForUpdate();
        filterTypeDetail.setThruTime(session.START_TIME_LONG);
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

    public FilterTypeDescription createFilterTypeDescription(FilterType filterType, Language language, String description,
            BasePK createdBy) {
        var filterTypeDescription = FilterTypeDescriptionFactory.getInstance().create(filterType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEvent(filterType.getPrimaryKey(), EventTypes.MODIFY, filterTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return filterTypeDescription;
    }

    private static final Map<EntityPermission, String> getFilterTypeDescriptionQueries;

    static {
        Map<EntityPermission, String> queryMap = new HashMap<>(2);

        queryMap.put(EntityPermission.READ_ONLY,
                "SELECT _ALL_ "
                + "FROM filtertypedescriptions "
                + "WHERE flttypd_flttyp_filtertypeid = ? AND flttypd_lang_languageid = ? AND flttypd_thrutime = ?");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM filtertypedescriptions "
                + "WHERE flttypd_flttyp_filtertypeid = ? AND flttypd_lang_languageid = ? AND flttypd_thrutime = ? "
                + "FOR UPDATE");
        getFilterTypeDescriptionQueries = Collections.unmodifiableMap(queryMap);
    }

    private FilterTypeDescription getFilterTypeDescription(FilterType filterType, Language language, EntityPermission entityPermission) {
        return FilterTypeDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, getFilterTypeDescriptionQueries,
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
                "SELECT _ALL_ "
                + "FROM filtertypedescriptions, languages "
                + "WHERE flttypd_flttyp_filtertypeid = ? AND flttypd_thrutime = ? AND flttypd_lang_languageid = lang_languageid "
                + "ORDER BY lang_sortorder, lang_languageisoname");
        queryMap.put(EntityPermission.READ_WRITE,
                "SELECT _ALL_ "
                + "FROM filtertypedescriptions "
                + "WHERE flttypd_flttyp_filtertypeid = ? AND flttypd_thrutime = ? "
                + "FOR UPDATE");
        getFilterTypeDescriptionsByFilterTypeQueries = Collections.unmodifiableMap(queryMap);
    }

    private List<FilterTypeDescription> getFilterTypeDescriptionsByFilterType(FilterType filterType, EntityPermission entityPermission) {
        return FilterTypeDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, getFilterTypeDescriptionsByFilterTypeQueries,
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
            var filterTypeDescription = FilterTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterTypeDescriptionValue.getPrimaryKey());

            filterTypeDescription.setThruTime(session.START_TIME_LONG);
            filterTypeDescription.store();

            var filterType = filterTypeDescription.getFilterType();
            var language = filterTypeDescription.getLanguage();
            var description = filterTypeDescriptionValue.getDescription();

            filterTypeDescription = FilterTypeDescriptionFactory.getInstance().create(filterType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEvent(filterType.getPrimaryKey(), EventTypes.MODIFY, filterTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteFilterTypeDescription(FilterTypeDescription filterTypeDescription, BasePK deletedBy) {
        filterTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEvent(filterTypeDescription.getFilterTypePK(), EventTypes.MODIFY, filterTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteFilterTypeDescriptionsByFilterType(FilterType filterType, BasePK deletedBy) {
        var filterTypeDescriptions = getFilterTypeDescriptionsByFilterTypeForUpdate(filterType);

        filterTypeDescriptions.forEach((filterTypeDescription) -> 
                deleteFilterTypeDescription(filterTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Filter Adjustment Sources
    // --------------------------------------------------------------------------------
    
    public FilterAdjustmentSource createFilterAdjustmentSource(String filterAdjustmentSourceName, Boolean allowedForInitialAmount,
            Boolean isDefault, Integer sortOrder) {
        return FilterAdjustmentSourceFactory.getInstance().create(filterAdjustmentSourceName, allowedForInitialAmount,
                isDefault, sortOrder);
    }

    public long countFilterAdjustmentSources() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM filteradjustmentsources");
    }

    public List<FilterAdjustmentSource> getFilterAdjustmentSources() {
        var ps = FilterAdjustmentSourceFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM filteradjustmentsources " +
                "ORDER BY fltas_sortorder, fltas_filteradjustmentsourcename");
        
        return FilterAdjustmentSourceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public FilterAdjustmentSource getFilterAdjustmentSourceByName(String filterAdjustmentSourceName) {
        FilterAdjustmentSource filterAdjustmentSource;
        
        try {
            var ps = FilterAdjustmentSourceFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM filteradjustmentsources " +
                    "WHERE fltas_filteradjustmentsourcename = ?");
            
            ps.setString(1, filterAdjustmentSourceName);
            
            filterAdjustmentSource = FilterAdjustmentSourceFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustmentSource;
    }
    
    public FilterAdjustmentSourceChoicesBean getFilterAdjustmentSourceChoices(String defaultFilterAdjustmentSourceChoice,
            Language language) {
        var filterAdjustmentSources = getFilterAdjustmentSources();
        var size = filterAdjustmentSources.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        for(var filterAdjustmentSource : filterAdjustmentSources) {
            var label = getBestFilterAdjustmentSourceDescription(filterAdjustmentSource, language);
            var value = filterAdjustmentSource.getFilterAdjustmentSourceName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultFilterAdjustmentSourceChoice == null? false:
                defaultFilterAdjustmentSourceChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new FilterAdjustmentSourceChoicesBean(labels, values, defaultValue);
    }

    public FilterAdjustmentSourceTransfer getFilterAdjustmentSourceTransfer(UserVisit userVisit, FilterAdjustmentSource filterAdjustmentSource) {
        return filterAdjustmentSourceTransferCache.getTransfer(userVisit, filterAdjustmentSource);
    }

    public List<FilterAdjustmentSourceTransfer> getFilterAdjustmentSourceTransfers(UserVisit userVisit, Collection<FilterAdjustmentSource> filterAdjustmentSources) {
        var filterAdjustmentSourceTransfers = new ArrayList<FilterAdjustmentSourceTransfer>(filterAdjustmentSources.size());

        filterAdjustmentSources.forEach((filterAdjustmentSource) ->
                filterAdjustmentSourceTransfers.add(filterAdjustmentSourceTransferCache.getTransfer(userVisit, filterAdjustmentSource))
        );

        return filterAdjustmentSourceTransfers;
    }

    public List<FilterAdjustmentSourceTransfer> getFilterAdjustmentSourceTransfers(UserVisit userVisit) {
        return getFilterAdjustmentSourceTransfers(userVisit, getFilterAdjustmentSources());
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Adjustment Source Descriptions
    // --------------------------------------------------------------------------------
    
    public FilterAdjustmentSourceDescription createFilterAdjustmentSourceDescription(FilterAdjustmentSource filterAdjustmentSource,
            Language language, String description) {
        return FilterAdjustmentSourceDescriptionFactory.getInstance().create(filterAdjustmentSource, language, description);
    }
    
    public FilterAdjustmentSourceDescription getFilterAdjustmentSourceDescription(FilterAdjustmentSource filterAdjustmentSource,
            Language language) {
        FilterAdjustmentSourceDescription filterAdjustmentSourceDescription;
        
        try {
            var ps = FilterAdjustmentSourceDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM filteradjustmentsourcedescriptions " +
                    "WHERE fltasd_fltas_filteradjustmentsourceid = ? AND fltasd_lang_languageid = ?");
            
            ps.setLong(1, filterAdjustmentSource.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            filterAdjustmentSourceDescription = FilterAdjustmentSourceDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustmentSourceDescription;
    }
    
    public String getBestFilterAdjustmentSourceDescription(FilterAdjustmentSource filterAdjustmentSource, Language language) {
        String description;
        var filterAdjustmentSourceDescription = getFilterAdjustmentSourceDescription(filterAdjustmentSource,
                language);
        
        if(filterAdjustmentSourceDescription == null && !language.getIsDefault()) {
            filterAdjustmentSourceDescription = getFilterAdjustmentSourceDescription(filterAdjustmentSource,
                    partyControl.getDefaultLanguage());
        }
        
        if(filterAdjustmentSourceDescription == null) {
            description = filterAdjustmentSource.getFilterAdjustmentSourceName();
        } else {
            description = filterAdjustmentSourceDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Adjustment Types
    // --------------------------------------------------------------------------------
    
    public FilterAdjustmentType createFilterAdjustmentType(String filterAdjustmentTypeName, Boolean isDefault, Integer sortOrder) {
        return FilterAdjustmentTypeFactory.getInstance().create(filterAdjustmentTypeName, isDefault, sortOrder);
    }

    public long countFilterAdjustmentTypes() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM filteradjustmenttypes");
    }

    public List<FilterAdjustmentType> getFilterAdjustmentTypes() {
        var ps = FilterAdjustmentTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM filteradjustmenttypes " +
                "ORDER BY fltat_sortorder, fltat_filteradjustmenttypename");
        
        return FilterAdjustmentTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public FilterAdjustmentType getFilterAdjustmentTypeByName(String filterAdjustmentTypeName) {
        FilterAdjustmentType filterAdjustmentType;
        
        try {
            var ps = FilterAdjustmentTypeFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM filteradjustmenttypes " +
                    "WHERE fltat_filteradjustmenttypename = ?");
            
            ps.setString(1, filterAdjustmentTypeName);
            
            filterAdjustmentType = FilterAdjustmentTypeFactory.getInstance().getEntityFromQuery(EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustmentType;
    }
    
    public FilterAdjustmentTypeChoicesBean getFilterAdjustmentTypeChoices(String defaultFilterAdjustmentTypeChoice,
            Language language) {
        var filterAdjustmentTypes = getFilterAdjustmentTypes();
        var size = filterAdjustmentTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        labels.add("");
        values.add("");
        
        for(var filterAdjustmentType : filterAdjustmentTypes) {
            var label = getBestFilterAdjustmentTypeDescription(filterAdjustmentType, language);
            var value = filterAdjustmentType.getFilterAdjustmentTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultFilterAdjustmentTypeChoice == null? false:
                defaultFilterAdjustmentTypeChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new FilterAdjustmentTypeChoicesBean(labels, values, defaultValue);
    }

    public FilterAdjustmentTypeTransfer getFilterAdjustmentTypeTransfer(UserVisit userVisit, FilterAdjustmentType filterAdjustmentType) {
        return filterAdjustmentTypeTransferCache.getTransfer(userVisit, filterAdjustmentType);
    }

    public List<FilterAdjustmentTypeTransfer> getFilterAdjustmentTypeTransfers(UserVisit userVisit, Collection<FilterAdjustmentType> filterAdjustmentTypes) {
        var filterAdjustmentTypeTransfers = new ArrayList<FilterAdjustmentTypeTransfer>(filterAdjustmentTypes.size());

        filterAdjustmentTypes.forEach((filterAdjustmentType) ->
                filterAdjustmentTypeTransfers.add(filterAdjustmentTypeTransferCache.getTransfer(userVisit, filterAdjustmentType))
        );

        return filterAdjustmentTypeTransfers;
    }

    public List<FilterAdjustmentTypeTransfer> getFilterAdjustmentTypeTransfers(UserVisit userVisit) {
        return getFilterAdjustmentTypeTransfers(userVisit, getFilterAdjustmentTypes());
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Adjustment Type Descriptions
    // --------------------------------------------------------------------------------
    
    public FilterAdjustmentTypeDescription createFilterAdjustmentTypeDescription(FilterAdjustmentType filterAdjustmentType,
            Language language, String description) {
        return FilterAdjustmentTypeDescriptionFactory.getInstance().create(filterAdjustmentType, language, description);
    }
    
    public FilterAdjustmentTypeDescription getFilterAdjustmentTypeDescription(FilterAdjustmentType filterAdjustmentType,
            Language language) {
        FilterAdjustmentTypeDescription filterAdjustmentTypeDescription;
        
        try {
            var ps = FilterAdjustmentTypeDescriptionFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM filteradjustmenttypedescriptions " +
                    "WHERE fltatd_fltat_filteradjustmenttypeid = ? AND fltatd_lang_languageid = ?");
            
            ps.setLong(1, filterAdjustmentType.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            
            filterAdjustmentTypeDescription = FilterAdjustmentTypeDescriptionFactory.getInstance().getEntityFromQuery(session,
                    EntityPermission.READ_ONLY, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustmentTypeDescription;
    }
    
    public String getBestFilterAdjustmentTypeDescription(FilterAdjustmentType filterAdjustmentType, Language language) {
        String description;
        var filterAdjustmentTypeDescription = getFilterAdjustmentTypeDescription(filterAdjustmentType,
                language);
        
        if(filterAdjustmentTypeDescription == null && !language.getIsDefault()) {
            filterAdjustmentTypeDescription = getFilterAdjustmentTypeDescription(filterAdjustmentType, partyControl.getDefaultLanguage());
        }
        
        if(filterAdjustmentTypeDescription == null) {
            description = filterAdjustmentType.getFilterAdjustmentTypeName();
        } else {
            description = filterAdjustmentTypeDescription.getDescription();
        }
        
        return description;
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Adjustments
    // --------------------------------------------------------------------------------
    
    public FilterAdjustment createFilterAdjustment(FilterKind filterKind, String filterAdjustmentName,
            FilterAdjustmentSource filterAdjustmentSource, FilterAdjustmentType filterAdjustmentType, Boolean isDefault,
            Integer sortOrder, BasePK createdBy) {
        var defaultFilterAdjustment = getDefaultFilterAdjustment(filterKind);
        var defaultFound = defaultFilterAdjustment != null;
        
        if(defaultFound && isDefault) {
            var defaultFilterAdjustmentDetailValue = getDefaultFilterAdjustmentDetailValueForUpdate(filterKind);
            
            defaultFilterAdjustmentDetailValue.setIsDefault(false);
            updateFilterAdjustmentFromValue(defaultFilterAdjustmentDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var filterAdjustment = FilterAdjustmentFactory.getInstance().create();
        var filterAdjustmentDetail = FilterAdjustmentDetailFactory.getInstance().create(session,
                filterAdjustment, filterKind, filterAdjustmentName, filterAdjustmentSource, filterAdjustmentType,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        filterAdjustment = FilterAdjustmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                filterAdjustment.getPrimaryKey());
        filterAdjustment.setActiveDetail(filterAdjustmentDetail);
        filterAdjustment.setLastDetail(filterAdjustmentDetail);
        filterAdjustment.store();
        
        sendEvent(filterAdjustment.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return filterAdjustment;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.FilterAdjustment */
    public FilterAdjustment getFilterAdjustmentByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new FilterAdjustmentPK(entityInstance.getEntityUniqueId());

        return FilterAdjustmentFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public FilterAdjustment getFilterAdjustmentByEntityInstance(EntityInstance entityInstance) {
        return getFilterAdjustmentByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public FilterAdjustment getFilterAdjustmentByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getFilterAdjustmentByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public List<FilterAdjustment> getFilterAdjustments() {
        var ps = FilterAdjustmentFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM filteradjustments, filteradjustmentdetails, filterkinds " +
                "WHERE flta_activedetailid = fltadt_filteradjustmentdetailid " +
                "AND fltadt_fltk_filterkindid = fltk_filterkindid " +
                "ORDER BY fltk_sortorder, fltk_filterkindname, fltadt_filteradjustmentname");
        
        return FilterAdjustmentFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    private List<FilterAdjustment> getFilterAdjustmentsByFilterKind(FilterKind filterKind, EntityPermission entityPermission) {
        List<FilterAdjustment> filterAdjustments;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustments, filteradjustmentdetails " +
                        "WHERE flta_activedetailid = fltadt_filteradjustmentdetailid AND fltadt_fltk_filterkindid = ? " +
                        "ORDER BY fltadt_filteradjustmentname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustments, filteradjustmentdetails " +
                        "WHERE flta_activedetailid = fltadt_filteradjustmentdetailid AND fltadt_fltk_filterkindid = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterAdjustmentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterKind.getPrimaryKey().getEntityId());
            
            filterAdjustments = FilterAdjustmentFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustments;
    }
    
    public List<FilterAdjustment> getFilterAdjustmentsByFilterKind(FilterKind filterKind) {
        return getFilterAdjustmentsByFilterKind(filterKind, EntityPermission.READ_ONLY);
    }
    
    public List<FilterAdjustment> getFilterAdjustmentsByFilterKindForUpdate(FilterKind filterKind) {
        return getFilterAdjustmentsByFilterKind(filterKind, EntityPermission.READ_WRITE);
    }
    
    public FilterAdjustment getDefaultFilterAdjustment(FilterKind filterKind, EntityPermission entityPermission) {
        FilterAdjustment filterAdjustment;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustments, filteradjustmentdetails " +
                        "WHERE flta_activedetailid = fltadt_filteradjustmentdetailid AND fltadt_fltk_filterkindid = ? " +
                        "AND fltadt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustments, filteradjustmentdetails " +
                        "WHERE flta_activedetailid = fltadt_filteradjustmentdetailid AND fltadt_fltk_filterkindid = ? " +
                        "AND fltadt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = FilterAdjustmentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterKind.getPrimaryKey().getEntityId());
            
            filterAdjustment= FilterAdjustmentFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustment;
    }
    
    public FilterAdjustment getDefaultFilterAdjustment(FilterKind filterKind) {
        return getDefaultFilterAdjustment(filterKind, EntityPermission.READ_ONLY);
    }
    
    public FilterAdjustment getDefaultFilterAdjustmentForUpdate(FilterKind filterKind) {
        return getDefaultFilterAdjustment(filterKind, EntityPermission.READ_WRITE);
    }
    
    public FilterAdjustmentDetailValue getDefaultFilterAdjustmentDetailValueForUpdate(FilterKind filterKind) {
        return getDefaultFilterAdjustmentForUpdate(filterKind).getLastDetailForUpdate().getFilterAdjustmentDetailValue().clone();
    }

    public FilterAdjustment getFilterAdjustmentByName(FilterKind filterKind, String filterAdjustmentName,
            EntityPermission entityPermission) {
        FilterAdjustment filterAdjustment;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustments, filteradjustmentdetails " +
                        "WHERE flta_activedetailid = fltadt_filteradjustmentdetailid AND fltadt_fltk_filterkindid = ? " +
                        "AND fltadt_filteradjustmentname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustments, filteradjustmentdetails " +
                        "WHERE flta_activedetailid = fltadt_filteradjustmentdetailid AND fltadt_fltk_filterkindid = ? " +
                        "AND fltadt_filteradjustmentname = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterAdjustmentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterKind.getPrimaryKey().getEntityId());
            ps.setString(2, filterAdjustmentName);
            
            filterAdjustment= FilterAdjustmentFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustment;
    }
    
    public FilterAdjustment getFilterAdjustmentByName(FilterKind filterKind, String filterAdjustmentName) {
        return getFilterAdjustmentByName(filterKind, filterAdjustmentName, EntityPermission.READ_ONLY);
    }
    
    public FilterAdjustment getFilterAdjustmentByNameForUpdate(FilterKind filterKind, String filterAdjustmentName) {
        return getFilterAdjustmentByName(filterKind, filterAdjustmentName, EntityPermission.READ_WRITE);
    }
    
    public FilterAdjustmentDetailValue getFilterAdjustmentDetailValueForUpdate(FilterAdjustment filterAdjustment) {
        return filterAdjustment == null? null: filterAdjustment.getLastDetailForUpdate().getFilterAdjustmentDetailValue().clone();
    }
    
    public FilterAdjustmentDetailValue getFilterAdjustmentDetailValueByNameForUpdate(FilterKind filterKind, String filterAdjustmentName) {
        return getFilterAdjustmentDetailValueForUpdate(getFilterAdjustmentByNameForUpdate(filterKind, filterAdjustmentName));
    }
    
    public FilterAdjustmentTransfer getFilterAdjustmentTransfer(UserVisit userVisit, FilterAdjustment filterAdjustment) {
        return filterAdjustmentTransferCache.getTransfer(userVisit, filterAdjustment);
    }
    
    public List<FilterAdjustmentTransfer> getFilterAdjustmentTransfers(UserVisit userVisit,  Collection<FilterAdjustment> filterAdjustments) {
        List<FilterAdjustmentTransfer> filterAdjustmentTransfers = new ArrayList<>(filterAdjustments.size());
        
        filterAdjustments.forEach((filterAdjustment) ->
                filterAdjustmentTransfers.add(filterAdjustmentTransferCache.getTransfer(userVisit, filterAdjustment))
        );
        
        return filterAdjustmentTransfers;
    }
    
    public List<FilterAdjustmentTransfer> getFilterAdjustmentTransfers(UserVisit userVisit) {
        return getFilterAdjustmentTransfers(userVisit, getFilterAdjustments());
    }
    
    public List<FilterAdjustmentTransfer> getFilterAdjustmentTransfersByFilterKind(UserVisit userVisit, FilterKind filterKind) {
        return getFilterAdjustmentTransfers(userVisit, getFilterAdjustmentsByFilterKind(filterKind));
    }
    
    public FilterAdjustmentChoicesBean getFilterAdjustmentChoices(String defaultFilterAdjustmentChoice, Language language,
            FilterKind filterKind, boolean initialAdjustmentsOnly) {
        var filterAdjustments = getFilterAdjustmentsByFilterKind(filterKind);
        var size = filterAdjustments.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        for(var filterAdjustment : filterAdjustments) {
            if(!initialAdjustmentsOnly || filterAdjustment.getLastDetail().getFilterAdjustmentSource().getAllowedForInitialAmount()) {
                var label = getBestFilterAdjustmentDescription(filterAdjustment, language);
                var value = filterAdjustment.getLastDetail().getFilterAdjustmentName();
                
                labels.add(label == null? value: label);
                values.add(value);
                
                var usingDefaultChoice = Objects.equals(defaultFilterAdjustmentChoice, value);
                if(usingDefaultChoice || defaultValue == null)
                    defaultValue = value;
            }
        }
        
        return new FilterAdjustmentChoicesBean(labels, values, defaultValue);
    }
    
    private void updateFilterAdjustmentFromValue(FilterAdjustmentDetailValue filterAdjustmentDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(filterAdjustmentDetailValue.hasBeenModified()) {
            var filterAdjustment = FilterAdjustmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentDetailValue.getFilterAdjustmentPK());
            var filterAdjustmentDetail = filterAdjustment.getActiveDetailForUpdate();
            
            filterAdjustmentDetail.setThruTime(session.START_TIME_LONG);
            filterAdjustmentDetail.store();

            var filterAdjustmentPK = filterAdjustmentDetail.getFilterAdjustmentPK();
            var filterKind = filterAdjustmentDetail.getFilterKind();
            var filterKindPK = filterKind.getPrimaryKey();
            var filterAdjustmentName = filterAdjustmentDetailValue.getFilterAdjustmentName();
            var filterAdjustmentSourcePK = filterAdjustmentDetailValue.getFilterAdjustmentSourcePK();
            var filterAdjustmentTypePK = filterAdjustmentDetailValue.getFilterAdjustmentTypePK();
            var isDefault = filterAdjustmentDetailValue.getIsDefault();
            var sortOrder = filterAdjustmentDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultFilterAdjustment = getDefaultFilterAdjustment(filterKind);
                var defaultFound = defaultFilterAdjustment != null && !defaultFilterAdjustment.equals(filterAdjustment);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultFilterAdjustmentDetailValue = getDefaultFilterAdjustmentDetailValueForUpdate(filterKind);
                    
                    defaultFilterAdjustmentDetailValue.setIsDefault(false);
                    updateFilterAdjustmentFromValue(defaultFilterAdjustmentDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            filterAdjustmentDetail = FilterAdjustmentDetailFactory.getInstance().create(filterAdjustmentPK, filterKindPK,
                    filterAdjustmentName, filterAdjustmentSourcePK, filterAdjustmentTypePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            filterAdjustment.setActiveDetail(filterAdjustmentDetail);
            filterAdjustment.setLastDetail(filterAdjustmentDetail);
            
            sendEvent(filterAdjustmentPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateFilterAdjustmentFromValue(FilterAdjustmentDetailValue filterAdjustmentDetailValue, BasePK updatedBy) {
        updateFilterAdjustmentFromValue(filterAdjustmentDetailValue, true, updatedBy);
    }
    
    public void deleteFilterAdjustment(FilterAdjustment filterAdjustment, BasePK deletedBy) {
        deleteFilterAdjustmentAmountsByFilterAdjustment(filterAdjustment, deletedBy);
        deleteFilterAdjustmentFixedAmountsByFilterAdjustment(filterAdjustment, deletedBy);
        deleteFilterAdjustmentPercentsByFilterAdjustment(filterAdjustment, deletedBy);
        deleteFilterAdjustmentDescriptionsByFilterAdjustment(filterAdjustment, deletedBy);

        var filterAdjustmentDetail = filterAdjustment.getLastDetailForUpdate();
        filterAdjustmentDetail.setThruTime(session.START_TIME_LONG);
        filterAdjustment.setActiveDetail(null);
        filterAdjustment.store();
        
        // Check for default, and pick one if necessary
        var filterKind = filterAdjustmentDetail.getFilterKind();
        var defaultFilterAdjustment = getDefaultFilterAdjustment(filterKind);
        if(defaultFilterAdjustment == null) {
            var filterKindPriorities = getFilterAdjustmentsByFilterKindForUpdate(filterKind);
            
            if(!filterKindPriorities.isEmpty()) {
                var iter = filterKindPriorities.iterator();
                if(iter.hasNext()) {
                    defaultFilterAdjustment = iter.next();
                }
                var filterAdjustmentDetailValue = Objects.requireNonNull(defaultFilterAdjustment).getLastDetailForUpdate().getFilterAdjustmentDetailValue().clone();
                
                filterAdjustmentDetailValue.setIsDefault(true);
                updateFilterAdjustmentFromValue(filterAdjustmentDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(filterAdjustment.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Adjustment Amounts
    // --------------------------------------------------------------------------------
    
    public FilterAdjustmentAmount createFilterAdjustmentAmount(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency, Long amount, BasePK createdBy) {
        var filterAdjustmentAmount = FilterAdjustmentAmountFactory.getInstance().create(session,
                filterAdjustment, unitOfMeasureType, currency, amount, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY,
                filterAdjustmentAmount.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterAdjustmentAmount;
    }
    
    private List<FilterAdjustmentAmount> getFilterAdjustmentAmounts(FilterAdjustment filterAdjustment,
            EntityPermission entityPermission) {
        List<FilterAdjustmentAmount> filterAdjustmentAmounts;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentamounts, unitofmeasuretypedetails, unitofmeasurekinddetails, currencies " +
                        "WHERE fltaa_flta_filteradjustmentid = ? AND fltaa_thrutime = ? " +
                        "AND fltaa_uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ? " +
                        "AND uomtdt_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                        "AND fltaa_cur_currencyid = cur_currencyid " +
                        "ORDER BY uomkdt_sortorder, uomtdt_sortorder, cur_sortorder";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentamounts " +
                        "WHERE fltaa_flta_filteradjustmentid = ? AND fltaa_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterAdjustmentAmountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterAdjustment.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
                ps.setLong(4, Session.MAX_TIME);
            }
            
            filterAdjustmentAmounts = FilterAdjustmentAmountFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustmentAmounts;
    }
    
    public List<FilterAdjustmentAmount> getFilterAdjustmentAmounts(FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentAmounts(filterAdjustment, EntityPermission.READ_ONLY);
    }
    
    public List<FilterAdjustmentAmount> getFilterAdjustmentAmountsForUpdate(FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentAmounts(filterAdjustment, EntityPermission.READ_WRITE);
    }
    
    private FilterAdjustmentAmount getFilterAdjustmentAmount(FilterAdjustment filterAdjustment, UnitOfMeasureType unitOfMeasureType,
            Currency currency, EntityPermission entityPermission) {
        FilterAdjustmentAmount filterAdjustmentAmount;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentamounts " +
                        "WHERE fltaa_flta_filteradjustmentid = ? AND fltaa_uomt_unitofmeasuretypeid = ? AND fltaa_cur_currencyid = ? " +
                        "AND fltaa_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentamounts " +
                        "WHERE fltaa_flta_filteradjustmentid = ? AND fltaa_uomt_unitofmeasuretypeid = ? AND fltaa_cur_currencyid = ? " +
                        "AND fltaa_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterAdjustmentAmountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterAdjustment.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, currency.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            filterAdjustmentAmount = FilterAdjustmentAmountFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustmentAmount;
    }
    
    public FilterAdjustmentAmount getFilterAdjustmentAmount(FilterAdjustment filterAdjustment, UnitOfMeasureType unitOfMeasureType,
            Currency currency) {
        return getFilterAdjustmentAmount(filterAdjustment, unitOfMeasureType, currency, EntityPermission.READ_ONLY);
    }
    
    public FilterAdjustmentAmount getFilterAdjustmentAmountForUpdate(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getFilterAdjustmentAmount(filterAdjustment, unitOfMeasureType, currency, EntityPermission.READ_WRITE);
    }
    
    public FilterAdjustmentAmountValue getFilterAdjustmentAmountValue(FilterAdjustmentAmount filterAdjustmentAmount) {
        return filterAdjustmentAmount == null? null: filterAdjustmentAmount.getFilterAdjustmentAmountValue().clone();
    }
    
    public FilterAdjustmentAmountValue getFilterAdjustmentAmountValueForUpdate(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        var filterAdjustmentAmount = getFilterAdjustmentAmountForUpdate(filterAdjustment, unitOfMeasureType, currency);
        
        return filterAdjustmentAmount == null? null: filterAdjustmentAmount.getFilterAdjustmentAmountValue().clone();
    }
    
    public FilterAdjustmentAmountTransfer getFilterAdjustmentAmountTransfer(UserVisit userVisit,
            FilterAdjustmentAmount filterAdjustmentAmount) {
        return filterAdjustmentAmountTransferCache.getTransfer(userVisit, filterAdjustmentAmount);
    }

    public List<FilterAdjustmentAmountTransfer> getFilterAdjustmentAmountTransfers(UserVisit userVisit, Collection<FilterAdjustmentAmount> filterAdjustmentAmounts) {
        List<FilterAdjustmentAmountTransfer> filterAdjustmentAmountTransfers = new ArrayList<>(filterAdjustmentAmounts.size());

        filterAdjustmentAmounts.forEach((filterAdjustmentAmount) ->
                filterAdjustmentAmountTransfers.add(filterAdjustmentAmountTransferCache.getTransfer(userVisit, filterAdjustmentAmount))
        );

        return filterAdjustmentAmountTransfers;
    }

    public List<FilterAdjustmentAmountTransfer> getFilterAdjustmentAmountTransfers(UserVisit userVisit, FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentAmountTransfers(userVisit, getFilterAdjustmentAmounts(filterAdjustment));
    }

    public void updateFilterAdjustmentAmountFromValue(FilterAdjustmentAmountValue filterAdjustmentAmountValue, BasePK updatedBy) {
        if(filterAdjustmentAmountValue.hasBeenModified()) {
            var filterAdjustmentAmount = FilterAdjustmentAmountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentAmountValue.getPrimaryKey());
            
            filterAdjustmentAmount.setThruTime(session.START_TIME_LONG);
            filterAdjustmentAmount.store();

            var filterAdjustmentPK = filterAdjustmentAmount.getFilterAdjustmentPK(); // Not updated
            var unitOfMeasureTypePK = filterAdjustmentAmount.getUnitOfMeasureTypePK(); // Not updated
            var currencyPK = filterAdjustmentAmount.getCurrencyPK(); // Not updated
            var amount = filterAdjustmentAmountValue.getAmount();
            
            filterAdjustmentAmount = FilterAdjustmentAmountFactory.getInstance().create(filterAdjustmentPK,
                    unitOfMeasureTypePK, currencyPK, amount, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(filterAdjustmentPK, EventTypes.MODIFY,
                    filterAdjustmentAmount.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterAdjustmentAmount(FilterAdjustmentAmount filterAdjustmentAmount, BasePK deletedBy) {
        filterAdjustmentAmount.setThruTime(session.START_TIME_LONG);
        
        sendEvent(filterAdjustmentAmount.getFilterAdjustment().getPrimaryKey(), EventTypes.MODIFY,
                filterAdjustmentAmount.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteFilterAdjustmentAmountsByFilterAdjustment(FilterAdjustment filterAdjustment, BasePK deletedBy) {
        var filterAdjustmentAmounts = getFilterAdjustmentAmountsForUpdate(filterAdjustment);
        
        filterAdjustmentAmounts.forEach((deleteFilterAdjustmentAmount) -> 
                deleteFilterAdjustmentAmount(deleteFilterAdjustmentAmount, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Adjustment Fixed Amounts
    // --------------------------------------------------------------------------------
    
    public FilterAdjustmentFixedAmount createFilterAdjustmentFixedAmount(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency, Long unitAmount, BasePK createdBy) {
        var filterAdjustmentFixedAmount = FilterAdjustmentFixedAmountFactory.getInstance().create(session,
                filterAdjustment, unitOfMeasureType, currency, unitAmount, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY,
                filterAdjustmentFixedAmount.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterAdjustmentFixedAmount;
    }
    
    private List<FilterAdjustmentFixedAmount> getFilterAdjustmentFixedAmounts(FilterAdjustment filterAdjustment,
            EntityPermission entityPermission) {
        List<FilterAdjustmentFixedAmount> filterAdjustmentFixedAmounts;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentfixedamounts, unitofmeasuretypedetails, unitofmeasurekinddetails, currencies " +
                        "WHERE fltafa_flta_filteradjustmentid = ? AND fltafa_thrutime = ? " +
                        "AND fltafa_uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ? " +
                        "AND uomtdt_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                        "AND fltafa_cur_currencyid = cur_currencyid " +
                        "ORDER BY uomkdt_sortorder, uomtdt_sortorder, cur_sortorder";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentfixedamounts " +
                        "WHERE fltafa_flta_filteradjustmentid = ? AND fltafa_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterAdjustmentFixedAmountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterAdjustment.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
                ps.setLong(4, Session.MAX_TIME);
            }
            
            filterAdjustmentFixedAmounts = FilterAdjustmentFixedAmountFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustmentFixedAmounts;
    }
    
    public List<FilterAdjustmentFixedAmount> getFilterAdjustmentFixedAmounts(FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentFixedAmounts(filterAdjustment, EntityPermission.READ_ONLY);
    }
    
    public List<FilterAdjustmentFixedAmount> getFilterAdjustmentFixedAmountsForUpdate(FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentFixedAmounts(filterAdjustment, EntityPermission.READ_WRITE);
    }
    
    private FilterAdjustmentFixedAmount getFilterAdjustmentFixedAmount(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency, EntityPermission entityPermission) {
        FilterAdjustmentFixedAmount filterAdjustmentFixedAmount;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentfixedamounts " +
                        "WHERE fltafa_flta_filteradjustmentid = ? AND fltafa_uomt_unitofmeasuretypeid = ? " +
                        "AND fltafa_cur_currencyid = ? AND fltafa_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentfixedamounts " +
                        "WHERE fltafa_flta_filteradjustmentid = ? AND fltafa_uomt_unitofmeasuretypeid = ? " +
                        "AND fltafa_cur_currencyid = ? AND fltafa_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterAdjustmentFixedAmountFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterAdjustment.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, currency.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            filterAdjustmentFixedAmount = FilterAdjustmentFixedAmountFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustmentFixedAmount;
    }
    
    public FilterAdjustmentFixedAmount getFilterAdjustmentFixedAmount(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getFilterAdjustmentFixedAmount(filterAdjustment, unitOfMeasureType, currency, EntityPermission.READ_ONLY);
    }
    
    public FilterAdjustmentFixedAmount getFilterAdjustmentFixedAmountForUpdate(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getFilterAdjustmentFixedAmount(filterAdjustment, unitOfMeasureType, currency, EntityPermission.READ_WRITE);
    }
    
    public FilterAdjustmentFixedAmountValue getFilterAdjustmentFixedAmountValue(FilterAdjustmentFixedAmount filterAdjustmentFixedAmount) {
        return filterAdjustmentFixedAmount == null? null: filterAdjustmentFixedAmount.getFilterAdjustmentFixedAmountValue().clone();
    }
    
    public FilterAdjustmentFixedAmountValue getFilterAdjustmentFixedAmountValueForUpdate(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        var filterAdjustmentFixedAmount = getFilterAdjustmentFixedAmountForUpdate(filterAdjustment,
                unitOfMeasureType, currency);
        
        return filterAdjustmentFixedAmount == null? null: filterAdjustmentFixedAmount.getFilterAdjustmentFixedAmountValue().clone();
    }
    
    public FilterAdjustmentFixedAmountTransfer getFilterAdjustmentFixedAmountTransfer(UserVisit userVisit,
            FilterAdjustmentFixedAmount filterAdjustmentFixedAmount) {
        return filterAdjustmentFixedAmountTransferCache.getTransfer(userVisit, filterAdjustmentFixedAmount);
    }

    public List<FilterAdjustmentFixedAmountTransfer> getFilterAdjustmentFixedAmountTransfers(UserVisit userVisit, Collection<FilterAdjustmentFixedAmount> filterAdjustmentFixedAmounts) {
        var filterAdjustmentFixedAmountTransfers = new ArrayList<FilterAdjustmentFixedAmountTransfer>(filterAdjustmentFixedAmounts.size());

        filterAdjustmentFixedAmounts.forEach((filterAdjustmentFixedAmount) ->
                filterAdjustmentFixedAmountTransfers.add(filterAdjustmentFixedAmountTransferCache.getTransfer(userVisit, filterAdjustmentFixedAmount))
        );

        return filterAdjustmentFixedAmountTransfers;
    }

    public List<FilterAdjustmentFixedAmountTransfer> getFilterAdjustmentFixedAmountTransfers(UserVisit userVisit, FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentFixedAmountTransfers(userVisit, getFilterAdjustmentFixedAmounts(filterAdjustment));
    }

    public void updateFilterAdjustmentFixedAmountFromValue(FilterAdjustmentFixedAmountValue filterAdjustmentFixedAmountValue, BasePK updatedBy) {
        if(filterAdjustmentFixedAmountValue.hasBeenModified()) {
            var filterAdjustmentFixedAmount = FilterAdjustmentFixedAmountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentFixedAmountValue.getPrimaryKey());
            
            filterAdjustmentFixedAmount.setThruTime(session.START_TIME_LONG);
            filterAdjustmentFixedAmount.store();

            var filterAdjustmentPK = filterAdjustmentFixedAmount.getFilterAdjustmentPK(); // Not updated
            var unitOfMeasureTypePK = filterAdjustmentFixedAmount.getUnitOfMeasureTypePK(); // Not updated
            var currencyPK = filterAdjustmentFixedAmount.getCurrencyPK(); // Not updated
            var unitAmount = filterAdjustmentFixedAmountValue.getUnitAmount();
            
            filterAdjustmentFixedAmount = FilterAdjustmentFixedAmountFactory.getInstance().create(filterAdjustmentPK,
                    unitOfMeasureTypePK, currencyPK, unitAmount, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(filterAdjustmentPK, EventTypes.MODIFY,
                    filterAdjustmentFixedAmount.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterAdjustmentFixedAmount(FilterAdjustmentFixedAmount filterAdjustmentFixedAmount, BasePK deletedBy) {
        filterAdjustmentFixedAmount.setThruTime(session.START_TIME_LONG);
        
        sendEvent(filterAdjustmentFixedAmount.getFilterAdjustment().getPrimaryKey(),
                EventTypes.MODIFY, filterAdjustmentFixedAmount.getPrimaryKey(), EventTypes.DELETE,
                deletedBy);
    }
    
    public void deleteFilterAdjustmentFixedAmountsByFilterAdjustment(FilterAdjustment filterAdjustment, BasePK deletedBy) {
        var filterAdjustmentFixedAmounts = getFilterAdjustmentFixedAmountsForUpdate(filterAdjustment);
        
        filterAdjustmentFixedAmounts.forEach((filterAdjustmentFixedAmount) -> 
                deleteFilterAdjustmentFixedAmount(filterAdjustmentFixedAmount, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Adjustment Percents
    // --------------------------------------------------------------------------------
    
    public FilterAdjustmentPercent createFilterAdjustmentPercent(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency, Integer percent, BasePK createdBy) {
        var filterAdjustmentPercent = FilterAdjustmentPercentFactory.getInstance().create(session,
                filterAdjustment, unitOfMeasureType, currency, percent, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY,
                filterAdjustmentPercent.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterAdjustmentPercent;
    }
    
    private List<FilterAdjustmentPercent> getFilterAdjustmentPercents(FilterAdjustment filterAdjustment,
            EntityPermission entityPermission) {
        List<FilterAdjustmentPercent> filterAdjustmentPercents;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentpercents, unitofmeasuretypedetails, unitofmeasurekinddetails, currencies " +
                        "WHERE fltap_flta_filteradjustmentid = ? AND fltap_thrutime = ? " +
                        "AND fltap_uomt_unitofmeasuretypeid = uomtdt_uomt_unitofmeasuretypeid AND uomtdt_thrutime = ? " +
                        "AND uomtdt_uomk_unitofmeasurekindid = uomkdt_uomk_unitofmeasurekindid AND uomkdt_thrutime = ? " +
                        "AND fltap_cur_currencyid = cur_currencyid " +
                        "ORDER BY uomkdt_sortorder, uomtdt_sortorder, cur_sortorder";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentpercents " +
                        "WHERE fltap_flta_filteradjustmentid = ? AND fltap_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterAdjustmentPercentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterAdjustment.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
                ps.setLong(4, Session.MAX_TIME);
            }
            
            filterAdjustmentPercents = FilterAdjustmentPercentFactory.getInstance().getEntitiesFromQuery(entityPermission,
                    ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustmentPercents;
    }
    
    public List<FilterAdjustmentPercent> getFilterAdjustmentPercents(FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentPercents(filterAdjustment, EntityPermission.READ_ONLY);
    }
    
    public List<FilterAdjustmentPercent> getFilterAdjustmentPercentsForUpdate(FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentPercents(filterAdjustment, EntityPermission.READ_WRITE);
    }
    
    private FilterAdjustmentPercent getFilterAdjustmentPercent(FilterAdjustment filterAdjustment, UnitOfMeasureType unitOfMeasureType,
            Currency currency, EntityPermission entityPermission) {
        FilterAdjustmentPercent filterAdjustmentPercent;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentpercents " +
                        "WHERE fltap_flta_filteradjustmentid = ? AND fltap_uomt_unitofmeasuretypeid = ? AND fltap_cur_currencyid = ? " +
                        "AND fltap_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentpercents " +
                        "WHERE fltap_flta_filteradjustmentid = ? AND fltap_uomt_unitofmeasuretypeid = ? AND fltap_cur_currencyid = ? " +
                        "AND fltap_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterAdjustmentPercentFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterAdjustment.getPrimaryKey().getEntityId());
            ps.setLong(2, unitOfMeasureType.getPrimaryKey().getEntityId());
            ps.setLong(3, currency.getPrimaryKey().getEntityId());
            ps.setLong(4, Session.MAX_TIME);
            
            filterAdjustmentPercent = FilterAdjustmentPercentFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustmentPercent;
    }
    
    public FilterAdjustmentPercent getFilterAdjustmentPercent(FilterAdjustment filterAdjustment, UnitOfMeasureType unitOfMeasureType,
            Currency currency) {
        return getFilterAdjustmentPercent(filterAdjustment, unitOfMeasureType, currency, EntityPermission.READ_ONLY);
    }
    
    public FilterAdjustmentPercent getFilterAdjustmentPercentForUpdate(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        return getFilterAdjustmentPercent(filterAdjustment, unitOfMeasureType, currency, EntityPermission.READ_WRITE);
    }
    
    public FilterAdjustmentPercentValue getFilterAdjustmentPercentValue(FilterAdjustmentPercent filterAdjustmentPercent) {
        return filterAdjustmentPercent == null? null: filterAdjustmentPercent.getFilterAdjustmentPercentValue().clone();
    }
    
    public FilterAdjustmentPercentValue getFilterAdjustmentPercentValueForUpdate(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency) {
        var filterAdjustmentPercent = getFilterAdjustmentPercentForUpdate(filterAdjustment, unitOfMeasureType,
                currency);
        
        return filterAdjustmentPercent == null? null: filterAdjustmentPercent.getFilterAdjustmentPercentValue().clone();
    }
    
    public FilterAdjustmentPercentTransfer getFilterAdjustmentPercentTransfer(UserVisit userVisit,
            FilterAdjustmentPercent filterAdjustmentPercent) {
        return filterAdjustmentPercentTransferCache.getTransfer(userVisit, filterAdjustmentPercent);
    }

    public List<FilterAdjustmentPercentTransfer> getFilterAdjustmentPercentTransfers(UserVisit userVisit, Collection<FilterAdjustmentPercent> filterAdjustmentPercents) {
        var filterAdjustmentPercentTransfers = new ArrayList<FilterAdjustmentPercentTransfer>(filterAdjustmentPercents.size());

        filterAdjustmentPercents.forEach((filterAdjustmentPercent) ->
                filterAdjustmentPercentTransfers.add(filterAdjustmentPercentTransferCache.getTransfer(userVisit, filterAdjustmentPercent))
        );

        return filterAdjustmentPercentTransfers;
    }

    public List<FilterAdjustmentPercentTransfer> getFilterAdjustmentPercentTransfers(UserVisit userVisit, FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentPercentTransfers(userVisit, getFilterAdjustmentPercents(filterAdjustment));
    }

    public void updateFilterAdjustmentPercentFromValue(FilterAdjustmentPercentValue filterAdjustmentPercentValue, BasePK updatedBy) {
        if(filterAdjustmentPercentValue.hasBeenModified()) {
            var filterAdjustmentPercent = FilterAdjustmentPercentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentPercentValue.getPrimaryKey());
            
            filterAdjustmentPercent.setThruTime(session.START_TIME_LONG);
            filterAdjustmentPercent.store();

            var filterAdjustmentPK = filterAdjustmentPercent.getFilterAdjustmentPK(); // Not updated
            var unitOfMeasureTypePK = filterAdjustmentPercent.getUnitOfMeasureTypePK(); // Not updated
            var currencyPK = filterAdjustmentPercent.getCurrencyPK(); // Not updated
            var percent = filterAdjustmentPercentValue.getPercent();
            
            filterAdjustmentPercent = FilterAdjustmentPercentFactory.getInstance().create(filterAdjustmentPK,
                    unitOfMeasureTypePK, currencyPK, percent, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(filterAdjustmentPK, EventTypes.MODIFY,
                    filterAdjustmentPercent.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterAdjustmentPercent(FilterAdjustmentPercent filterAdjustmentPercent, BasePK deletedBy) {
        filterAdjustmentPercent.setThruTime(session.START_TIME_LONG);
        
        sendEvent(filterAdjustmentPercent.getFilterAdjustment().getPrimaryKey(), EventTypes.MODIFY,
                filterAdjustmentPercent.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteFilterAdjustmentPercentsByFilterAdjustment(FilterAdjustment filterAdjustment, BasePK deletedBy) {
        var filterAdjustmentPercents = getFilterAdjustmentPercentsForUpdate(filterAdjustment);
        
        filterAdjustmentPercents.forEach((filterAdjustmentPercent) -> 
                deleteFilterAdjustmentPercent(filterAdjustmentPercent, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Adjustment Descriptions
    // --------------------------------------------------------------------------------
    
    public FilterAdjustmentDescription createFilterAdjustmentDescription(FilterAdjustment filterAdjustment, Language language,
            String description,
            BasePK createdBy) {
        var filterAdjustmentDescription = FilterAdjustmentDescriptionFactory.getInstance().create(session,
                filterAdjustment, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY,
                filterAdjustmentDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterAdjustmentDescription;
    }
    
    private FilterAdjustmentDescription getFilterAdjustmentDescription(FilterAdjustment filterAdjustment, Language language,
            EntityPermission entityPermission) {
        FilterAdjustmentDescription filterAdjustmentDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentdescriptions " +
                        "WHERE fltad_flta_filteradjustmentid = ? AND fltad_lang_languageid = ? AND fltad_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentdescriptions " +
                        "WHERE fltad_flta_filteradjustmentid = ? AND fltad_lang_languageid = ? AND fltad_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterAdjustmentDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterAdjustment.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            filterAdjustmentDescription = FilterAdjustmentDescriptionFactory.getInstance().getEntityFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustmentDescription;
    }
    
    public FilterAdjustmentDescription getFilterAdjustmentDescription(FilterAdjustment filterAdjustment, Language language) {
        return getFilterAdjustmentDescription(filterAdjustment, language, EntityPermission.READ_ONLY);
    }
    
    public FilterAdjustmentDescription getFilterAdjustmentDescriptionForUpdate(FilterAdjustment filterAdjustment,
            Language language) {
        return getFilterAdjustmentDescription(filterAdjustment, language, EntityPermission.READ_WRITE);
    }
    
    public FilterAdjustmentDescriptionValue getFilterAdjustmentDescriptionValue(FilterAdjustmentDescription filterAdjustmentDescription) {
        return filterAdjustmentDescription == null? null: filterAdjustmentDescription.getFilterAdjustmentDescriptionValue().clone();
    }
    
    public FilterAdjustmentDescriptionValue getFilterAdjustmentDescriptionValueForUpdate(FilterAdjustment filterAdjustment,
            Language language) {
        var filterAdjustmentDescription = getFilterAdjustmentDescriptionForUpdate(filterAdjustment,
                language);
        
        return filterAdjustmentDescription == null? null: filterAdjustmentDescription.getFilterAdjustmentDescriptionValue().clone();
    }
    
    private List<FilterAdjustmentDescription> getFilterAdjustmentDescriptions(FilterAdjustment filterAdjustment,
            EntityPermission entityPermission) {
        List<FilterAdjustmentDescription> filterAdjustmentDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentdescriptions, languages " +
                        "WHERE fltad_flta_filteradjustmentid = ? AND fltad_thrutime = ? AND fltad_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filteradjustmentdescriptions " +
                        "WHERE fltad_flta_filteradjustmentid = ? AND fltad_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterAdjustmentDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterAdjustment.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            filterAdjustmentDescriptions = FilterAdjustmentDescriptionFactory.getInstance().getEntitiesFromQuery(session,
                    entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterAdjustmentDescriptions;
    }
    
    public List<FilterAdjustmentDescription> getFilterAdjustmentDescriptions(FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentDescriptions(filterAdjustment, EntityPermission.READ_ONLY);
    }
    
    public List<FilterAdjustmentDescription> getFilterAdjustmentDescriptionsForUpdate(FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentDescriptions(filterAdjustment, EntityPermission.READ_WRITE);
    }
    
    public String getBestFilterAdjustmentDescription(FilterAdjustment filterAdjustment, Language language) {
        String description;
        var filterAdjustmentDescription = getFilterAdjustmentDescription(filterAdjustment, language);
        
        if(filterAdjustmentDescription == null && !language.getIsDefault()) {
            filterAdjustmentDescription = getFilterAdjustmentDescription(filterAdjustment, partyControl.getDefaultLanguage());
        }
        
        if(filterAdjustmentDescription == null) {
            description = filterAdjustment.getLastDetail().getFilterAdjustmentName();
        } else {
            description = filterAdjustmentDescription.getDescription();
        }
        
        return description;
    }
    
    public FilterAdjustmentDescriptionTransfer getFilterAdjustmentDescriptionTransfer(UserVisit userVisit,
            FilterAdjustmentDescription filterAdjustmentDescription) {
        return filterAdjustmentDescriptionTransferCache.getTransfer(userVisit, filterAdjustmentDescription);
    }
    
    public List<FilterAdjustmentDescriptionTransfer> getFilterAdjustmentDescriptionTransfers(UserVisit userVisit, FilterAdjustment filterAdjustment) {
        var filterAdjustmentDescriptions = getFilterAdjustmentDescriptions(filterAdjustment);
        List<FilterAdjustmentDescriptionTransfer> filterAdjustmentDescriptionTransfers = new ArrayList<>(filterAdjustmentDescriptions.size());
        
        filterAdjustmentDescriptions.forEach((filterAdjustmentDescription) ->
                filterAdjustmentDescriptionTransfers.add(filterAdjustmentDescriptionTransferCache.getTransfer(userVisit, filterAdjustmentDescription))
        );
        
        return filterAdjustmentDescriptionTransfers;
    }
    
    public void updateFilterAdjustmentDescriptionFromValue(FilterAdjustmentDescriptionValue filterAdjustmentDescriptionValue,
            BasePK updatedBy) {
        if(filterAdjustmentDescriptionValue.hasBeenModified()) {
            var filterAdjustmentDescription = FilterAdjustmentDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentDescriptionValue.getPrimaryKey());
            
            filterAdjustmentDescription.setThruTime(session.START_TIME_LONG);
            filterAdjustmentDescription.store();

            var filterAdjustment = filterAdjustmentDescription.getFilterAdjustment();
            var language = filterAdjustmentDescription.getLanguage();
            var description = filterAdjustmentDescriptionValue.getDescription();
            
            filterAdjustmentDescription = FilterAdjustmentDescriptionFactory.getInstance().create(filterAdjustment,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY,
                    filterAdjustmentDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterAdjustmentDescription(FilterAdjustmentDescription filterAdjustmentDescription, BasePK deletedBy) {
        filterAdjustmentDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(filterAdjustmentDescription.getFilterAdjustmentPK(), EventTypes.MODIFY,
                filterAdjustmentDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteFilterAdjustmentDescriptionsByFilterAdjustment(FilterAdjustment filterAdjustment, BasePK deletedBy) {
        var filterAdjustmentDescriptions = getFilterAdjustmentDescriptionsForUpdate(filterAdjustment);
        
        filterAdjustmentDescriptions.forEach((filterAdjustmentDescription) -> 
                deleteFilterAdjustmentDescription(filterAdjustmentDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filters
    // --------------------------------------------------------------------------------
    
    public Filter createFilter(FilterType filterType, String filterName, FilterAdjustment initialFilterAdjustment,
            Selector filterItemSelector, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultFilter = getDefaultFilter(filterType);
        var defaultFound = defaultFilter != null;
        
        if(defaultFound && isDefault) {
            var defaultFilterDetailValue = getDefaultFilterDetailValueForUpdate(filterType);
            
            defaultFilterDetailValue.setIsDefault(false);
            updateFilterFromValue(defaultFilterDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var filter = FilterFactory.getInstance().create();
        var filterDetail = FilterDetailFactory.getInstance().create(filter, filterType, filterName,
                initialFilterAdjustment, filterItemSelector, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        filter = FilterFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, filter.getPrimaryKey());
        filter.setActiveDetail(filterDetail);
        filter.setLastDetail(filterDetail);
        filter.store();
        
        sendEvent(filter.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return filter;
    }

    public long countFiltersByFilterType(FilterType filterType) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM filters, filterdetails " +
                "WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ?",
                filterType);
    }

    public long countFiltersBySelector(Selector selector) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM filters, filterdetails " +
                "WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_filteritemselectorid = ?",
                selector);
    }

    public long countFiltersByFilterAdjustment(FilterAdjustment filterAdjustment) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM filters, filterdetails " +
                "WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_initialfilteradjustmentid = ?",
                filterAdjustment);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Filter */
    public Filter getFilterByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new FilterPK(entityInstance.getEntityUniqueId());

        return FilterFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public Filter getFilterByEntityInstance(EntityInstance entityInstance) {
        return getFilterByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Filter getFilterByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getFilterByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    private List<Filter> getFilters(FilterType filterType, EntityPermission entityPermission) {
        List<Filter> filters;
        
        try {
            String query = null;

            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filters, filterdetails " +
                        "WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ? " +
                        "ORDER BY fltdt_sortorder, fltdt_filtername " +
                        "_LIMIT_";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filters, filterdetails " +
                        "WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterFactory.getInstance().prepareStatement(query);

            ps.setLong(1, filterType.getPrimaryKey().getEntityId());
            
            filters = FilterFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filters;
    }
    
    public List<Filter> getFilters(FilterType filterType) {
        return getFilters(filterType, EntityPermission.READ_ONLY);
    }
    
    public List<Filter> getFiltersForUpdate(FilterType filterType) {
        return getFilters(filterType, EntityPermission.READ_WRITE);
    }
    
    public Filter getDefaultFilter(FilterType filterType, EntityPermission entityPermission) {
        Filter filter;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filters, filterdetails " +
                        "WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ? AND fltdt_isdefault = 1";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filters, filterdetails " +
                        "WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ? AND fltdt_isdefault = 1 " +
                        "FOR UPDATE";
            }

            var ps = FilterFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterType.getPrimaryKey().getEntityId());
            
            filter = FilterFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filter;
    }
    
    public Filter getDefaultFilter(FilterType filterType) {
        return getDefaultFilter(filterType, EntityPermission.READ_ONLY);
    }
    
    public Filter getDefaultFilterForUpdate(FilterType filterType) {
        return getDefaultFilter(filterType, EntityPermission.READ_WRITE);
    }
    
    public FilterDetailValue getDefaultFilterDetailValueForUpdate(FilterType filterType) {
        return getDefaultFilterForUpdate(filterType).getLastDetailForUpdate().getFilterDetailValue().clone();
    }
    
    public Filter getFilterByName(FilterType filterType, String filterName, EntityPermission entityPermission) {
        Filter filter;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filters, filterdetails " +
                        "WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ? AND fltdt_filtername = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filters, filterdetails " +
                        "WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ? AND fltdt_filtername = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterType.getPrimaryKey().getEntityId());
            ps.setString(2, filterName);
            
            filter = FilterFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filter;
    }
    
    public Filter getFilterByName(FilterType filterType, String filterName) {
        return getFilterByName(filterType, filterName, EntityPermission.READ_ONLY);
    }
    
    public Filter getFilterByNameForUpdate(FilterType filterType, String filterName) {
        return getFilterByName(filterType, filterName, EntityPermission.READ_WRITE);
    }
    
    public FilterDetailValue getFilterDetailValueForUpdate(Filter filter) {
        return filter == null? null: filter.getLastDetailForUpdate().getFilterDetailValue().clone();
    }
    
    public FilterDetailValue getFilterDetailValueByNameForUpdate(FilterType filterType, String filterName) {
        return getFilterDetailValueForUpdate(getFilterByNameForUpdate(filterType, filterName));
    }
    
    public FilterChoicesBean getFilterChoices(String defaultFilterChoice, Language language, boolean allowNullChoice,
            FilterType filterType) {
        var filters = getFilters(filterType);
        var size = filters.size() + (allowNullChoice? 1: 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultFilterChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var filter : filters) {
            var filterDetail = filter.getLastDetail();
            var label = getBestFilterDescription(filter, language);
            var value = filterDetail.getFilterName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultFilterChoice != null && defaultFilterChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && filterDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new FilterChoicesBean(labels, values, defaultValue);
    }
    
    public FilterTransfer getFilterTransfer(UserVisit userVisit, Filter filter) {
        return filterTransferCache.getTransfer(userVisit, filter);
    }
    
    public List<FilterTransfer> getFilterTransfers(UserVisit userVisit, Collection<Filter> filters) {
        List<FilterTransfer> filterTransfers = new ArrayList<>(filters.size());
        
        filters.forEach((filter) ->
                filterTransfers.add(filterTransferCache.getTransfer(userVisit, filter))
        );
        
        return filterTransfers;
    }
    
    public List<FilterTransfer> getFilterTransfers(UserVisit userVisit, FilterType filterType) {
        return getFilterTransfers(userVisit, getFilters(filterType));
    }
    
    private void updateFilterFromValue(FilterDetailValue filterDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(filterDetailValue.hasBeenModified()) {
            var filter = FilterFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterDetailValue.getFilterPK());
            var filterDetail = filter.getActiveDetailForUpdate();
            
            filterDetail.setThruTime(session.START_TIME_LONG);
            filterDetail.store();

            var filterPK = filterDetail.getFilterPK();
            var filterType = filterDetail.getFilterType();
            var filterTypePK = filterType.getPrimaryKey();
            var filterName = filterDetailValue.getFilterName();
            var initialFilterAdjustmentPK = filterDetailValue.getInitialFilterAdjustmentPK();
            var filterItemSelectorPK = filterDetailValue.getFilterItemSelectorPK();
            var isDefault = filterDetailValue.getIsDefault();
            var sortOrder = filterDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultFilter = getDefaultFilter(filterType);
                var defaultFound = defaultFilter != null && !defaultFilter.equals(filter);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultFilterDetailValue = getDefaultFilterDetailValueForUpdate(filterType);
                    
                    defaultFilterDetailValue.setIsDefault(false);
                    updateFilterFromValue(defaultFilterDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            filterDetail = FilterDetailFactory.getInstance().create(filterPK, filterTypePK, filterName,
                    initialFilterAdjustmentPK, filterItemSelectorPK,  isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            filter.setActiveDetail(filterDetail);
            filter.setLastDetail(filterDetail);
            
            sendEvent(filterPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateFilterFromValue(FilterDetailValue filterDetailValue, BasePK updatedBy) {
        updateFilterFromValue(filterDetailValue, true, updatedBy);
    }
    
    public void deleteFilter(Filter filter, BasePK deletedBy) {
        deleteFilterStepsByFilter(filter, deletedBy);
        deleteFilterDescriptionsByFilter(filter, deletedBy);

        var filterDetail = filter.getLastDetailForUpdate();
        filterDetail.setThruTime(session.START_TIME_LONG);
        filter.setActiveDetail(null);
        filter.store();
        
        // Check for default, and pick one if necessary
        var filterType = filterDetail.getFilterType();
        var defaultFilter = getDefaultFilter(filterType);
        if(defaultFilter == null) {
            var Filters = getFiltersForUpdate(filterType);
            
            if(!Filters.isEmpty()) {
                var iter = Filters.iterator();
                if(iter.hasNext()) {
                    defaultFilter = iter.next();
                }
                var filterDetailValue = Objects.requireNonNull(defaultFilter).getLastDetailForUpdate().getFilterDetailValue().clone();
                
                filterDetailValue.setIsDefault(true);
                updateFilterFromValue(filterDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(filter.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Descriptions
    // --------------------------------------------------------------------------------
    
    public FilterDescription createFilterDescription(Filter filter, Language language, String description, BasePK createdBy) {
        var filterDescription = FilterDescriptionFactory.getInstance().create(filter, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(filter.getPrimaryKey(), EventTypes.MODIFY, filterDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterDescription;
    }
    
    private FilterDescription getFilterDescription(Filter filter, Language language, EntityPermission entityPermission) {
        FilterDescription filterDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filterdescriptions " +
                        "WHERE fltd_flt_filterid = ? AND fltd_lang_languageid = ? AND fltd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filterdescriptions " +
                        "WHERE fltd_flt_filterid = ? AND fltd_lang_languageid = ? AND fltd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filter.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            filterDescription = FilterDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterDescription;
    }
    
    public FilterDescription getFilterDescription(Filter filter, Language language) {
        return getFilterDescription(filter, language, EntityPermission.READ_ONLY);
    }
    
    public FilterDescription getFilterDescriptionForUpdate(Filter filter, Language language) {
        return getFilterDescription(filter, language, EntityPermission.READ_WRITE);
    }
    
    public FilterDescriptionValue getFilterDescriptionValue(FilterDescription filterDescription) {
        return filterDescription == null? null: filterDescription.getFilterDescriptionValue().clone();
    }
    
    public FilterDescriptionValue getFilterDescriptionValueForUpdate(Filter filter, Language language) {
        var filterDescription = getFilterDescriptionForUpdate(filter, language);
        
        return filterDescription == null? null: filterDescription.getFilterDescriptionValue().clone();
    }
    
    private List<FilterDescription> getFilterDescriptions(Filter filter, EntityPermission entityPermission) {
        List<FilterDescription> filterDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filterdescriptions, languages " +
                        "WHERE fltd_flt_filterid = ? AND fltd_thrutime = ? AND fltd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filterdescriptions " +
                        "WHERE fltd_flt_filterid = ? AND fltd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filter.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            filterDescriptions = FilterDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterDescriptions;
    }
    
    public List<FilterDescription> getFilterDescriptions(Filter filter) {
        return getFilterDescriptions(filter, EntityPermission.READ_ONLY);
    }
    
    public List<FilterDescription> getFilterDescriptionsForUpdate(Filter filter) {
        return getFilterDescriptions(filter, EntityPermission.READ_WRITE);
    }
    
    public String getBestFilterDescription(Filter filter, Language language) {
        String description;
        var filterDescription = getFilterDescription(filter, language);
        
        if(filterDescription == null && !language.getIsDefault()) {
            filterDescription = getFilterDescription(filter, partyControl.getDefaultLanguage());
        }
        
        if(filterDescription == null) {
            description = filter.getLastDetail().getFilterName();
        } else {
            description = filterDescription.getDescription();
        }
        
        return description;
    }
    
    public FilterDescriptionTransfer getFilterDescriptionTransfer(UserVisit userVisit, FilterDescription filterDescription) {
        return filterDescriptionTransferCache.getTransfer(userVisit, filterDescription);
    }
    
    public List<FilterDescriptionTransfer> getFilterDescriptionTransfers(UserVisit userVisit, Filter filter) {
        var filterDescriptions = getFilterDescriptions(filter);
        List<FilterDescriptionTransfer> filterDescriptionTransfers = new ArrayList<>(filterDescriptions.size());
        
        filterDescriptions.forEach((filterDescription) ->
                filterDescriptionTransfers.add(filterDescriptionTransferCache.getTransfer(userVisit, filterDescription))
        );
        
        return filterDescriptionTransfers;
    }
    
    public void updateFilterDescriptionFromValue(FilterDescriptionValue filterDescriptionValue, BasePK updatedBy) {
        if(filterDescriptionValue.hasBeenModified()) {
            var filterDescription = FilterDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, filterDescriptionValue.getPrimaryKey());
            
            filterDescription.setThruTime(session.START_TIME_LONG);
            filterDescription.store();

            var filter = filterDescription.getFilter();
            var language = filterDescription.getLanguage();
            var description = filterDescriptionValue.getDescription();
            
            filterDescription = FilterDescriptionFactory.getInstance().create(filter, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(filter.getPrimaryKey(), EventTypes.MODIFY, filterDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterDescription(FilterDescription filterDescription, BasePK deletedBy) {
        filterDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(filterDescription.getFilterPK(), EventTypes.MODIFY, filterDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteFilterDescriptionsByFilter(Filter filter, BasePK deletedBy) {
        var filterDescriptions = getFilterDescriptionsForUpdate(filter);
        
        filterDescriptions.forEach((filterDescription) -> 
                deleteFilterDescription(filterDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Steps
    // --------------------------------------------------------------------------------
    
    public FilterStep createFilterStep(Filter filter, String filterStepName, Selector filterItemSelector, BasePK createdBy) {
        var filterStep = FilterStepFactory.getInstance().create();
        var filterStepDetail = FilterStepDetailFactory.getInstance().create(filterStep, filter, filterStepName, filterItemSelector, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        filterStep = FilterStepFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, filterStep.getPrimaryKey());
        filterStep.setActiveDetail(filterStepDetail);
        filterStep.setLastDetail(filterStepDetail);
        filterStep.store();
        
        sendEvent(filterStep.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return filterStep;
    }

    public long countFilterStepsByFilter(Filter filter) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM filtersteps, filterstepdetails " +
                "WHERE fltstp_activedetailid = fltstpdt_filterstepdetailid AND fltstpdt_flt_filterid = ?",
                filter);
    }

    public long countFilterStepsBySelector(Selector selector) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM filtersteps, filterstepdetails " +
                "WHERE fltstp_activedetailid = fltstpdt_filterstepdetailid AND fltstpdt_filteritemselectorid = ?",
                selector);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.FilterStep */
    public FilterStep getFilterStepByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new FilterStepPK(entityInstance.getEntityUniqueId());

        return FilterStepFactory.getInstance().getEntityFromPK(entityPermission, pk);
    }

    public FilterStep getFilterStepByEntityInstance(EntityInstance entityInstance) {
        return getFilterStepByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public FilterStep getFilterStepByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getFilterStepByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public FilterStep getFilterStepByName(Filter filter, String filterStepName, EntityPermission entityPermission) {
        FilterStep filterStep;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filtersteps, filterstepdetails " +
                        "WHERE fltstp_activedetailid = fltstpdt_filterstepdetailid AND fltstpdt_flt_filterid = ? " +
                        "AND fltstpdt_filterstepname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filtersteps, filterstepdetails " +
                        "WHERE fltstp_activedetailid = fltstpdt_filterstepdetailid AND fltstpdt_flt_filterid = ? " +
                        "AND fltstpdt_filterstepname = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterStepFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filter.getPrimaryKey().getEntityId());
            ps.setString(2, filterStepName);
            
            filterStep = FilterStepFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStep;
    }
    
    public FilterStep getFilterStepByName(Filter filter, String filterStepName) {
        return getFilterStepByName(filter, filterStepName, EntityPermission.READ_ONLY);
    }
    
    public FilterStep getFilterStepByNameForUpdate(Filter filter, String filterStepName) {
        return getFilterStepByName(filter, filterStepName, EntityPermission.READ_WRITE);
    }
    
    public FilterStepDetailValue getFilterStepDetailValueForUpdate(FilterStep filterStep) {
        return filterStep == null? null: filterStep.getLastDetailForUpdate().getFilterStepDetailValue().clone();
    }
    
    public FilterStepDetailValue getFilterStepDetailValueByNameForUpdate(Filter filter, String filterStepName) {
        return getFilterStepDetailValueForUpdate(getFilterStepByNameForUpdate(filter, filterStepName));
    }
    
    private List<FilterStep> getFilterStepsByFilter(Filter filter, EntityPermission entityPermission) {
        List<FilterStep> filterSteps;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filtersteps, filterstepdetails " +
                        "WHERE fltstp_activedetailid = fltstpdt_filterstepdetailid AND fltstpdt_flt_filterid = ? " +
                        "ORDER BY fltstpdt_filterstepname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filtersteps, filterstepdetails " +
                        "WHERE fltstp_activedetailid = fltstpdt_filterstepdetailid AND fltstpdt_flt_filterid = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterStepFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filter.getPrimaryKey().getEntityId());
            
            filterSteps = FilterStepFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterSteps;
    }
    
    public List<FilterStep> getFilterStepsByFilter(Filter filter) {
        return getFilterStepsByFilter(filter, EntityPermission.READ_ONLY);
    }
    
    public List<FilterStep> getFilterStepsByFilterForUpdate(Filter filter) {
        return getFilterStepsByFilter(filter, EntityPermission.READ_WRITE);
    }
    
    public FilterStepChoicesBean getFilterStepChoices(String defaultFilterStepChoice, Language language, boolean allowNullChoice,
            Filter filter) {
        var filterSteps = getFilterStepsByFilter(filter);
        var size = filterSteps.size() + (allowNullChoice? 1: 0);
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultFilterStepChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var filterStep : filterSteps) {
            var filterStepDetail = filterStep.getLastDetail();
            var label = getBestFilterStepDescription(filterStep, language);
            var value = filterStepDetail.getFilterStepName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultFilterStepChoice != null && defaultFilterStepChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new FilterStepChoicesBean(labels, values, defaultValue);
    }
    
    public List<FilterStepTransfer> getFilterStepTransfers(UserVisit userVisit, Collection<FilterStep> filterSteps) {
        List<FilterStepTransfer> filterStepTransfers = new ArrayList<>(filterSteps.size());
        
        filterSteps.forEach((filterStep) ->
                filterStepTransfers.add(filterStepTransferCache.getTransfer(userVisit, filterStep))
        );
        
        return filterStepTransfers;
    }
    
    public List<FilterStepTransfer> getFilterStepTransfersByFilter(UserVisit userVisit, Filter filter) {
        return getFilterStepTransfers(userVisit, getFilterStepsByFilter(filter));
    }
    
    public FilterStepTransfer getFilterStepTransfer(UserVisit userVisit, FilterStep filterStep) {
        return filterStepTransferCache.getTransfer(userVisit, filterStep);
    }
    
    public void updateFilterStepFromValue(FilterStepDetailValue filterStepDetailValue, BasePK updatedBy) {
        if(filterStepDetailValue.hasBeenModified()) {
            var filterStep = FilterStepFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterStepDetailValue.getFilterStepPK());
            var filterStepDetail = filterStep.getActiveDetailForUpdate();
            
            filterStepDetail.setThruTime(session.START_TIME_LONG);
            filterStepDetail.store();

            var filterStepPK = filterStepDetail.getFilterStepPK();
            var filter = filterStepDetail.getFilter();
            var filterPK = filter.getPrimaryKey();
            var filterStepName = filterStepDetailValue.getFilterStepName();
            var filterItemSelectorPK = filterStepDetailValue.getFilterItemSelectorPK();
            
            filterStepDetail = FilterStepDetailFactory.getInstance().create(filterStepPK, filterPK, filterStepName,
                    filterItemSelectorPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            filterStep.setActiveDetail(filterStepDetail);
            filterStep.setLastDetail(filterStepDetail);
            
            sendEvent(filterStepPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteFilterStep(FilterStep filterStep, BasePK deletedBy) {
        deleteFilterStepElementsByFilterStep(filterStep, deletedBy);
        deleteFilterStepDestinationsByFilterStep(filterStep, deletedBy);
        deleteFilterEntranceStepsByFilterStep(filterStep, deletedBy);
        deleteFilterStepDescriptionsByFilterStep(filterStep, deletedBy);

        var filterStepDetail = filterStep.getLastDetailForUpdate();
        filterStepDetail.setThruTime(session.START_TIME_LONG);
        filterStep.setActiveDetail(null);
        filterStep.store();
        
        sendEvent(filterStep.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    public void deleteFilterStepsByFilter(Filter filter, BasePK deletedBy) {
        var filterSteps = getFilterStepsByFilterForUpdate(filter);
        
        filterSteps.forEach((filterStep) -> 
                deleteFilterStep(filterStep, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Step Descriptions
    // --------------------------------------------------------------------------------
    
    public FilterStepDescription createFilterStepDescription(FilterStep filterStep, Language language, String description, BasePK createdBy) {
        var filterStepDescription = FilterStepDescriptionFactory.getInstance().create(filterStep, language,
                description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEvent(filterStep.getPrimaryKey(), EventTypes.MODIFY,
                filterStepDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterStepDescription;
    }
    
    private FilterStepDescription getFilterStepDescription(FilterStep filterStep, Language language, EntityPermission entityPermission) {
        FilterStepDescription filterStepDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepdescriptions " +
                        "WHERE fltstpd_fltstp_filterstepid = ? AND fltstpd_lang_languageid = ? AND fltstpd_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepdescriptions " +
                        "WHERE fltstpd_fltstp_filterstepid = ? AND fltstpd_lang_languageid = ? AND fltstpd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterStepDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterStep.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            filterStepDescription = FilterStepDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepDescription;
    }
    
    public FilterStepDescription getFilterStepDescription(FilterStep filterStep, Language language) {
        return getFilterStepDescription(filterStep, language, EntityPermission.READ_ONLY);
    }
    
    public FilterStepDescription getFilterStepDescriptionForUpdate(FilterStep filterStep, Language language) {
        return getFilterStepDescription(filterStep, language, EntityPermission.READ_WRITE);
    }
    
    public FilterStepDescriptionValue getFilterStepDescriptionValue(FilterStepDescription filterStepDescription) {
        return filterStepDescription == null? null: filterStepDescription.getFilterStepDescriptionValue().clone();
    }
    
    public FilterStepDescriptionValue getFilterStepDescriptionValueForUpdate(FilterStep filterStep, Language language) {
        var filterStepDescription = getFilterStepDescriptionForUpdate(filterStep, language);
        
        return filterStepDescription == null? null: filterStepDescription.getFilterStepDescriptionValue().clone();
    }
    
    private List<FilterStepDescription> getFilterStepDescriptions(FilterStep filterStep, EntityPermission entityPermission) {
        List<FilterStepDescription> filterStepDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepdescriptions, languages " +
                        "WHERE fltstpd_fltstp_filterstepid = ? AND fltstpd_thrutime = ? AND fltstpd_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepdescriptions " +
                        "WHERE fltstpd_fltstp_filterstepid = ? AND fltstpd_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterStepDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterStep.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            filterStepDescriptions = FilterStepDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepDescriptions;
    }
    
    public List<FilterStepDescription> getFilterStepDescriptions(FilterStep filterStep) {
        return getFilterStepDescriptions(filterStep, EntityPermission.READ_ONLY);
    }
    
    public List<FilterStepDescription> getFilterStepDescriptionsForUpdate(FilterStep filterStep) {
        return getFilterStepDescriptions(filterStep, EntityPermission.READ_WRITE);
    }
    
    public String getBestFilterStepDescription(FilterStep filterStep, Language language) {
        String description;
        var filterStepDescription = getFilterStepDescription(filterStep, language);
        
        if(filterStepDescription == null && !language.getIsDefault()) {
            filterStepDescription = getFilterStepDescription(filterStep, partyControl.getDefaultLanguage());
        }
        
        if(filterStepDescription == null) {
            description = filterStep.getLastDetail().getFilterStepName();
        } else {
            description = filterStepDescription.getDescription();
        }
        
        return description;
    }
    
    public FilterStepDescriptionTransfer getFilterStepDescriptionTransfer(UserVisit userVisit, FilterStepDescription filterStepDescription) {
        return filterStepDescriptionTransferCache.getTransfer(userVisit, filterStepDescription);
    }
    
    public List<FilterStepDescriptionTransfer> getFilterStepDescriptionTransfers(UserVisit userVisit, FilterStep filterStep) {
        var filterStepDescriptions = getFilterStepDescriptions(filterStep);
        List<FilterStepDescriptionTransfer> filterStepDescriptionTransfers = new ArrayList<>(filterStepDescriptions.size());
        
        filterStepDescriptions.forEach((filterStepDescription) ->
                filterStepDescriptionTransfers.add(filterStepDescriptionTransferCache.getTransfer(userVisit, filterStepDescription))
        );
        
        return filterStepDescriptionTransfers;
    }
    
    public void updateFilterStepDescriptionFromValue(FilterStepDescriptionValue filterStepDescriptionValue, BasePK updatedBy) {
        if(filterStepDescriptionValue.hasBeenModified()) {
            var filterStepDescription = FilterStepDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterStepDescriptionValue.getPrimaryKey());
            
            filterStepDescription.setThruTime(session.START_TIME_LONG);
            filterStepDescription.store();

            var filterStep = filterStepDescription.getFilterStep();
            var language = filterStepDescription.getLanguage();
            var description = filterStepDescriptionValue.getDescription();
            
            filterStepDescription = FilterStepDescriptionFactory.getInstance().create(filterStep, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(filterStep.getPrimaryKey(), EventTypes.MODIFY, filterStepDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterStepDescription(FilterStepDescription filterStepDescription, BasePK deletedBy) {
        filterStepDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(filterStepDescription.getFilterStep().getPrimaryKey(), EventTypes.MODIFY,
                filterStepDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteFilterStepDescriptionsByFilterStep(FilterStep filterStep, BasePK deletedBy) {
        var filterStepDescriptions = getFilterStepDescriptionsForUpdate(filterStep);
        
        filterStepDescriptions.forEach((filterStepDescription) -> 
                deleteFilterStepDescription(filterStepDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Entrance Steps
    // --------------------------------------------------------------------------------
    
    public FilterEntranceStep createFilterEntranceStep(Filter filter, FilterStep filterStep, BasePK createdBy) {
        var filterEntranceStep = FilterEntranceStepFactory.getInstance().create(filter, filterStep, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(filter.getPrimaryKey(), EventTypes.MODIFY, filterEntranceStep.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return filterEntranceStep;
    }
    
    private FilterEntranceStep getFilterEntranceStep(Filter filter, FilterStep filterStep, EntityPermission entityPermission) {
        FilterEntranceStep filterEntranceStep;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filterentrancesteps " +
                        "WHERE fltens_flt_filterid = ? AND fltens_fltstp_filterstepid = ? AND fltens_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filterentrancesteps " +
                        "WHERE fltens_flt_filterid = ? AND fltens_fltstp_filterstepid = ? AND fltens_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterEntranceStepFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filter.getPrimaryKey().getEntityId());
            ps.setLong(2, filterStep.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            filterEntranceStep = FilterEntranceStepFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterEntranceStep;
    }
    
    public FilterEntranceStep getFilterEntranceStep(Filter filter, FilterStep filterStep) {
        return getFilterEntranceStep(filter, filterStep, EntityPermission.READ_ONLY);
    }
    
    public FilterEntranceStep getFilterEntranceStepForUpdate(Filter filter, FilterStep filterStep) {
        return getFilterEntranceStep(filter, filterStep, EntityPermission.READ_WRITE);
    }
    
    private List<FilterEntranceStep> getFilterEntranceStepsByFilter(Filter filter, EntityPermission entityPermission) {
        List<FilterEntranceStep> filterEntranceSteps;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filterentrancesteps, filterstepdetails " +
                        "WHERE fltens_flt_filterid = ? AND fltens_thrutime = ? " +
                        "AND fltens_fltstp_filterstepid = fltstpdt_fltstp_filterstepid AND fltstpdt_thrutime = ? " +
                        "ORDER BY fltstpdt_filterstepname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filterentrancesteps " +
                        "WHERE fltens_flt_filterid = ? AND fltens_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterEntranceStepFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filter.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
            }
            
            filterEntranceSteps = FilterEntranceStepFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterEntranceSteps;
    }
    
    public List<FilterEntranceStep> getFilterEntranceStepsByFilter(Filter filter) {
        return getFilterEntranceStepsByFilter(filter, EntityPermission.READ_ONLY);
    }
    
    public List<FilterEntranceStep> getFilterEntranceStepsByFilterForUpdate(Filter filter) {
        return getFilterEntranceStepsByFilter(filter, EntityPermission.READ_WRITE);
    }
    
    private List<FilterEntranceStep> getFilterEntranceStepsByFilterStep(FilterStep filterStep, EntityPermission entityPermission) {
        List<FilterEntranceStep> filterEntranceSteps;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filterentrancesteps, filterstepdetails" +
                        "WHERE fltens_fltstp_filterstepid = ? AND fltens_thrutime = ? " +
                        "AND fltens_fltstp_filterstepid = fltstpdt_fltstp_filterstepid AND fltstpdt_thrutime = ? " +
                        "ORDER BY fltstpdt_filterstepname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filterentrancesteps " +
                        "WHERE fltens_fltstp_filterstepid = ? AND fltens_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterEntranceStepFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterStep.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
            }
            
            filterEntranceSteps = FilterEntranceStepFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterEntranceSteps;
    }
    
    public List<FilterEntranceStep> getFilterEntranceStepsByFilterStep(FilterStep filterStep) {
        return getFilterEntranceStepsByFilterStep(filterStep, EntityPermission.READ_ONLY);
    }
    
    public List<FilterEntranceStep> getFilterEntranceStepsByFilterStepForUpdate(FilterStep filterStep) {
        return getFilterEntranceStepsByFilterStep(filterStep, EntityPermission.READ_WRITE);
    }
    
    public List<FilterEntranceStepTransfer> getFilterEntranceStepTransfers(UserVisit userVisit, Collection<FilterEntranceStep> filterEntranceSteps) {
        List<FilterEntranceStepTransfer> filterEntranceStepTransfers = new ArrayList<>(filterEntranceSteps.size());
        
        filterEntranceSteps.forEach((filterEntranceStep) ->
                filterEntranceStepTransfers.add(filterEntranceStepTransferCache.getTransfer(userVisit, filterEntranceStep))
        );
        
        return filterEntranceStepTransfers;
    }
    
    public List<FilterEntranceStepTransfer> getFilterEntranceStepTransfersByFilter(UserVisit userVisit, Filter filter) {
        return getFilterEntranceStepTransfers(userVisit, getFilterEntranceStepsByFilter(filter));
    }
    
    public FilterEntranceStepTransfer getFilterEntranceStepTransfer(UserVisit userVisit, FilterEntranceStep filterEntranceStep) {
        return filterEntranceStepTransferCache.getTransfer(userVisit, filterEntranceStep);
    }
    
    public void deleteFilterEntranceStep(FilterEntranceStep filterEntranceStep, BasePK deletedBy) {
        filterEntranceStep.setThruTime(session.START_TIME_LONG);
        
        sendEvent(filterEntranceStep.getFilter().getPrimaryKey(), EventTypes.MODIFY,
                filterEntranceStep.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteFilterEntranceStepsByFilterStep(FilterStep filterStep, BasePK deletedBy) {
        var filterEntranceSteps = getFilterEntranceStepsByFilterStepForUpdate(filterStep);
        
        filterEntranceSteps.forEach((filterEntranceStep) -> 
                deleteFilterEntranceStep(filterEntranceStep, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Step Destinations
    // --------------------------------------------------------------------------------
    
    public FilterStepDestination createFilterStepDestination(FilterStep fromFilterStep, FilterStep toFilterStep, BasePK createdBy) {
        var filterStepDestination = FilterStepDestinationFactory.getInstance().create(fromFilterStep,
                toFilterStep, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(fromFilterStep.getLastDetail().getFilter().getPrimaryKey(), EventTypes.MODIFY,
                filterStepDestination.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterStepDestination;
    }
    
    private FilterStepDestination getFilterStepDestination(FilterStep fromFilterStep, FilterStep toFilterStep,
            EntityPermission entityPermission) {
        FilterStepDestination filterStepDestination;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepdestinations " +
                        "WHERE fltstpdn_fromfilterstepid = ? AND fltstpdn_tofilterstepid = ? AND fltstpdn_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepdestinations " +
                        "WHERE fltstpdn_fromfilterstepid = ? AND fltstpdn_tofilterstepid = ? AND fltstpdn_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterStepDestinationFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, fromFilterStep.getPrimaryKey().getEntityId());
            ps.setLong(2, toFilterStep.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            filterStepDestination = FilterStepDestinationFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepDestination;
    }
    
    public FilterStepDestination getFilterStepDestination(FilterStep fromFilterStep, FilterStep toFilterStep) {
        return getFilterStepDestination(fromFilterStep, toFilterStep, EntityPermission.READ_ONLY);
    }
    
    public FilterStepDestination getFilterStepDestinationForUpdate(FilterStep fromFilterStep, FilterStep toFilterStep) {
        return getFilterStepDestination(fromFilterStep, toFilterStep, EntityPermission.READ_WRITE);
    }
    
    private List<FilterStepDestination> getFilterStepDestinationsByFromFilterStep(FilterStep fromFilterStep, EntityPermission entityPermission) {
        List<FilterStepDestination> filterStepDestinations;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepdestinations, filterstepdetails " +
                        "WHERE fltstpdn_fromfilterstepid = ? AND fltstpdn_thrutime = ? " +
                        "AND fltstpdn_tofilterstepid = fltstpdt_fltstp_filterstepid AND fltstpdt_thrutime = ? " +
                        "ORDER BY fltstpdt_filterstepname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepdestinations " +
                        "WHERE fltstpdn_fromfilterstepid = ? AND fltstpdn_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterStepDestinationFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, fromFilterStep.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
            }
            
            filterStepDestinations = FilterStepDestinationFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepDestinations;
    }
    
    public List<FilterStepDestination> getFilterStepDestinationsByFromFilterStep(FilterStep fromFilterStep) {
        return getFilterStepDestinationsByFromFilterStep(fromFilterStep, EntityPermission.READ_ONLY);
    }
    
    public List<FilterStepDestination> getFilterStepDestinationsByFromFilterStepForUpdate(FilterStep fromFilterStep) {
        return getFilterStepDestinationsByFromFilterStep(fromFilterStep, EntityPermission.READ_WRITE);
    }
    
    private List<FilterStepDestination> getFilterStepDestinationsByToFilterStep(FilterStep toFilterStep, EntityPermission entityPermission) {
        List<FilterStepDestination> filterStepDestinations;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepdestinations, filterstepdetails " +
                        "WHERE fltstpdn_tofilterstepid = ? AND fltstpdn_thrutime = ? " +
                        "AND fltstpdn_tofilterstepid = fltstpdt_fltstp_filterstepid AND fltstpdt_thrutime = ? " +
                        "ORDER BY fltstpdt_filterstepname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepdestinations " +
                        "WHERE fltstpdn_tofilterstepid = ? AND fltstpdn_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterStepDestinationFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, toFilterStep.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                ps.setLong(3, Session.MAX_TIME);
            }
            
            filterStepDestinations = FilterStepDestinationFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepDestinations;
    }
    
    public List<FilterStepDestination> getFilterStepDestinationsByToFilterStep(FilterStep toFilterStep) {
        return getFilterStepDestinationsByToFilterStep(toFilterStep, EntityPermission.READ_ONLY);
    }
    
    public List<FilterStepDestination> getFilterStepDestinationsByToFilterStepForUpdate(FilterStep toFilterStep) {
        return getFilterStepDestinationsByToFilterStep(toFilterStep, EntityPermission.READ_WRITE);
    }
    
    public long countFilterStepDestinationsByFromFilterStep(FilterStep fromFilterStep) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM filterstepdestinations " +
                "WHERE fltstpdn_fromfilterstepid = ? AND fltstpdn_thrutime = ?",
                fromFilterStep, Session.MAX_TIME);
    }
    
    public long countFilterStepDestinationsByToFilterStep(FilterStep toFilterStep) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM filterstepdestinations " +
                "WHERE fltstpdn_tofilterstepid = ? AND fltstpdn_thrutime = ?",
                toFilterStep, Session.MAX_TIME);
    }
    
    public List<FilterStepDestinationTransfer> getFilterStepDestinationTransfers(UserVisit userVisit, Collection<FilterStepDestination> filterStepDestinations) {
        List<FilterStepDestinationTransfer> filterStepDestinationTransfers = new ArrayList<>(filterStepDestinations.size());
        
        filterStepDestinations.forEach((filterStepDestination) ->
                filterStepDestinationTransfers.add(filterStepDestinationTransferCache.getTransfer(userVisit, filterStepDestination))
        );
        
        return filterStepDestinationTransfers;
    }
    
    public List<FilterStepDestinationTransfer> getFilterStepDestinationTransfersByFromFilterStep(UserVisit userVisit,
            FilterStep fromFilterStep) {
        return getFilterStepDestinationTransfers(userVisit, getFilterStepDestinationsByFromFilterStep(fromFilterStep));
    }
    
    public List<FilterStepDestinationTransfer> getFilterStepDestinationTransfersByToFilterStep(UserVisit userVisit,
            FilterStep toFilterStep) {
        return getFilterStepDestinationTransfers(userVisit, getFilterStepDestinationsByFromFilterStep(toFilterStep));
    }
    
    public FilterStepDestinationTransfer getFilterStepDestinationTransfer(UserVisit userVisit, FilterStepDestination filterStepDestination) {
        return filterStepDestinationTransferCache.getTransfer(userVisit, filterStepDestination);
    }
    
    public void deleteFilterStepDestination(FilterStepDestination filterStepDestination, BasePK deletedBy) {
        filterStepDestination.setThruTime(session.START_TIME_LONG);
        filterStepDestination.store();
        
        sendEvent(filterStepDestination.getFromFilterStep().getLastDetail().getFilter().getPrimaryKey(),
                EventTypes.MODIFY, filterStepDestination.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteFilterStepDestinationsByFromFilterStep(FilterStep fromFilterStep, BasePK deletedBy) {
        var filterStepDestinations = getFilterStepDestinationsByFromFilterStepForUpdate(fromFilterStep);
        
        filterStepDestinations.forEach((filterStepDestination) -> 
                deleteFilterStepDestination(filterStepDestination, deletedBy)
        );
    }
    
    public void deleteFilterStepDestinationsByToFilterStep(FilterStep toFilterStep, BasePK deletedBy) {
        var filterStepDestinations = getFilterStepDestinationsByToFilterStepForUpdate(toFilterStep);
        
        filterStepDestinations.forEach((filterStepDestination) -> 
                deleteFilterStepDestination(filterStepDestination, deletedBy)
        );
    }
    
    public void deleteFilterStepDestinationsByFilterStep(FilterStep filterStep, BasePK deletedBy) {
        deleteFilterStepDestinationsByFromFilterStep(filterStep, deletedBy);
        deleteFilterStepDestinationsByToFilterStep(filterStep, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Step Elements
    // --------------------------------------------------------------------------------
    
    public FilterStepElement createFilterStepElement(FilterStep filterStep, String filterStepElementName, Selector filterItemSelector,
            FilterAdjustment filterAdjustment, BasePK createdBy) {
        var filterStepElement = FilterStepElementFactory.getInstance().create();
        var filterStepElementDetail = FilterStepElementDetailFactory.getInstance().create(filterStepElement, filterStep,
                filterStepElementName, filterItemSelector, filterAdjustment, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        filterStepElement = FilterStepElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, filterStepElement.getPrimaryKey());
        filterStepElement.setActiveDetail(filterStepElementDetail);
        filterStepElement.setLastDetail(filterStepElementDetail);
        filterStepElement.store();
        
        sendEvent(filterStep.getLastDetail().getFilter().getPrimaryKey(), EventTypes.MODIFY,
                filterStepElement.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterStepElement;
    }
    
    private FilterStepElement getFilterStepElementByName(FilterStep filterStep, String filterStepElementName,
            EntityPermission entityPermission) {
        FilterStepElement filterStepElement;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepelements, filterstepelementdetails " +
                        "WHERE fltstpe_activedetailid = fltstpedt_filterstepelementdetailid " +
                        "AND fltstpedt_fltstp_filterstepid = ? AND fltstpedt_filterstepelementname = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepelements, filterstepelementdetails " +
                        "WHERE fltstpe_activedetailid = fltstpedt_filterstepelementdetailid " +
                        "AND fltstpedt_fltstp_filterstepid = ? AND fltstpedt_filterstepelementname = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterStepElementFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterStep.getPrimaryKey().getEntityId());
            ps.setString(2, filterStepElementName);
            filterStepElement = FilterStepElementFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepElement;
    }
    
    public FilterStepElement getFilterStepElementByName(FilterStep filterStep, String filterStepElementName) {
        return getFilterStepElementByName(filterStep, filterStepElementName, EntityPermission.READ_ONLY);
    }
    
    public FilterStepElement getFilterStepElementByNameForUpdate(FilterStep filterStep, String filterStepElementName) {
        return getFilterStepElementByName(filterStep, filterStepElementName, EntityPermission.READ_WRITE);
    }
    
    public FilterStepElementDetailValue getFilterStepElementDetailValueForUpdate(FilterStepElement filterStepElement) {
        return filterStepElement == null? null: filterStepElement.getLastDetailForUpdate().getFilterStepElementDetailValue().clone();
    }
    
    public FilterStepElementDetailValue getFilterStepElementDetailValueByNameForUpdate(FilterStep filterStep, String filterStepElementName) {
        return getFilterStepElementDetailValueForUpdate(getFilterStepElementByNameForUpdate(filterStep, filterStepElementName));
    }
    
    private List<FilterStepElement> getFilterStepElementsByFilterStep(FilterStep filterStep, EntityPermission entityPermission) {
        List<FilterStepElement> filterStepElements;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepelements, filterstepelementdetails " +
                        "WHERE fltstpe_activedetailid = fltstpedt_filterstepelementdetailid AND fltstpedt_fltstp_filterstepid = ? " +
                        "ORDER BY fltstpedt_filterstepelementname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepelements, filterstepelementdetails " +
                        "WHERE fltstpe_activedetailid = fltstpedt_filterstepelementdetailid AND fltstpedt_fltstp_filterstepid = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterStepElementFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterStep.getPrimaryKey().getEntityId());
            
            filterStepElements = FilterStepElementFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepElements;
    }
    
    public List<FilterStepElement> getFilterStepElementsByFilterStep(FilterStep filterStep) {
        return getFilterStepElementsByFilterStep(filterStep, EntityPermission.READ_ONLY);
    }
    
    public List<FilterStepElement> getFilterStepElementsByFilterStepForUpdate(FilterStep filterStep) {
        return getFilterStepElementsByFilterStep(filterStep, EntityPermission.READ_WRITE);
    }
    
    public List<FilterStepElementTransfer> getFilterStepElementTransfers(UserVisit userVisit, Collection<FilterStepElement> filterStepElements) {
        List<FilterStepElementTransfer> filterStepElementTransfers = new ArrayList<>(filterStepElements.size());
        
        filterStepElements.forEach((filterStepElement) ->
                filterStepElementTransfers.add(filterStepElementTransferCache.getTransfer(userVisit, filterStepElement))
        );
        
        return filterStepElementTransfers;
    }
    
    public List<FilterStepElementTransfer> getFilterStepElementTransfersByFilterStep(UserVisit userVisit, FilterStep filterStep) {
        return getFilterStepElementTransfers(userVisit, getFilterStepElementsByFilterStep(filterStep));
    }
    
    public FilterStepElementTransfer getFilterStepElementTransfer(UserVisit userVisit, FilterStepElement filterStepElement) {
        return filterStepElementTransferCache.getTransfer(userVisit, filterStepElement);
    }
    
    public long countFilterStepElementsBySelector(Selector selector) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM filterstepelementdetails " +
                "WHERE fltstpedt_filteritemselectorid = ? AND fltstpedt_thrutime = ?",
                selector, Session.MAX_TIME);
    }
    
    public long countFilterStepElementsByFilterAdjustment(FilterAdjustment filterAdjustment) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM filterstepelementdetails " +
                "WHERE fltstpedt_flta_filteradjustmentid = ? AND fltstpedt_thrutime = ?",
                filterAdjustment, Session.MAX_TIME);
    }
    
    public void updateFilterStepElementFromValue(FilterStepElementDetailValue filterStepElementDetailValue, BasePK updatedBy) {
        if(filterStepElementDetailValue.hasBeenModified()) {
            var filterStepElement = FilterStepElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterStepElementDetailValue.getFilterStepElementPK());
            var filterStepElementDetail = filterStepElement.getActiveDetailForUpdate();
            
            filterStepElementDetail.setThruTime(session.START_TIME_LONG);
            filterStepElementDetail.store();

            var filterStepElementPK = filterStepElementDetail.getFilterStepElementPK();
            var filterStep = filterStepElementDetail.getFilterStep();
            var filterStepPK = filterStep.getPrimaryKey();
            var filterStepElementName = filterStepElementDetailValue.getFilterStepElementName();
            var filterItemSelectorPK = filterStepElementDetailValue.getFilterItemSelectorPK();
            var filterAdjustmentPK = filterStepElementDetailValue.getFilterAdjustmentPK();
            
            filterStepElementDetail = FilterStepElementDetailFactory.getInstance().create(filterStepElementPK,
                    filterStepPK, filterStepElementName, filterItemSelectorPK, filterAdjustmentPK, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            filterStepElement.setActiveDetail(filterStepElementDetail);
            filterStepElement.setLastDetail(filterStepElementDetail);
            
            sendEvent(filterStep.getLastDetail().getFilter().getPrimaryKey(), EventTypes.MODIFY,
                    filterStepElementPK, EventTypes.DELETE, updatedBy);
        }
    }
    
    public void deleteFilterStepElement(FilterStepElement filterStepElement, BasePK deletedBy) {
        deleteFilterStepElementDescriptionsByFilterStepElement(filterStepElement, deletedBy);

        var filterStepElementDetail = filterStepElement.getLastDetailForUpdate();
        filterStepElementDetail.setThruTime(session.START_TIME_LONG);
        filterStepElement.setActiveDetail(null);
        filterStepElement.store();
        
        sendEvent(filterStepElementDetail.getFilterStep().getLastDetail().getFilter().getPrimaryKey(),
                EventTypes.MODIFY, filterStepElement.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteFilterStepElementsByFilterStep(FilterStep filterStep, BasePK deletedBy) {
        var filterStepElements = getFilterStepElementsByFilterStepForUpdate(filterStep);
        
        filterStepElements.forEach((filterStepElement) -> 
                deleteFilterStepElement(filterStepElement, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Step Element Descriptions
    // --------------------------------------------------------------------------------
    
    public FilterStepElementDescription createFilterStepElementDescription(FilterStepElement filterStepElement, Language language,
            String description, BasePK createdBy) {
        var filterStepElementDescription = FilterStepElementDescriptionFactory.getInstance().create(session,
                filterStepElement, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEvent(filterStepElement.getLastDetail().getFilterStep().getLastDetail().getFilterPK(),
                EventTypes.MODIFY, filterStepElementDescription.getPrimaryKey(), EventTypes.CREATE,
                createdBy);
        
        return filterStepElementDescription;
    }
    
    private FilterStepElementDescription getFilterStepElementDescription(FilterStepElement filterStepElement, Language language,
            EntityPermission entityPermission) {
        FilterStepElementDescription filterStepElementDescription;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepelementdescriptions " +
                        "WHERE fltstped_fltstpe_filterstepelementid = ? AND fltstped_lang_languageid = ? AND fltstped_thrutime = ?";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepelementdescriptions " +
                        "WHERE fltstped_fltstpe_filterstepelementid = ? AND fltstped_lang_languageid = ? AND fltstped_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterStepElementDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterStepElement.getPrimaryKey().getEntityId());
            ps.setLong(2, language.getPrimaryKey().getEntityId());
            ps.setLong(3, Session.MAX_TIME);
            
            filterStepElementDescription = FilterStepElementDescriptionFactory.getInstance().getEntityFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepElementDescription;
    }
    
    public FilterStepElementDescription getFilterStepElementDescription(FilterStepElement filterStepElement, Language language) {
        return getFilterStepElementDescription(filterStepElement, language, EntityPermission.READ_ONLY);
    }
    
    public FilterStepElementDescription getFilterStepElementDescriptionForUpdate(FilterStepElement filterStepElement, Language language) {
        return getFilterStepElementDescription(filterStepElement, language, EntityPermission.READ_WRITE);
    }
    
    public FilterStepElementDescriptionValue getFilterStepElementDescriptionValue(FilterStepElementDescription filterStepElementDescription) {
        return filterStepElementDescription == null? null: filterStepElementDescription.getFilterStepElementDescriptionValue().clone();
    }
    
    public FilterStepElementDescriptionValue getFilterStepElementDescriptionValueForUpdate(FilterStepElement filterStepElement, Language language) {
        var filterStepElementDescription = getFilterStepElementDescriptionForUpdate(filterStepElement, language);
        
        return filterStepElementDescription == null? null: filterStepElementDescription.getFilterStepElementDescriptionValue().clone();
    }
    
    private List<FilterStepElementDescription> getFilterStepElementDescriptions(FilterStepElement filterStepElement, EntityPermission entityPermission) {
        List<FilterStepElementDescription> filterStepElementDescriptions;
        
        try {
            String query = null;
            
            if(entityPermission.equals(EntityPermission.READ_ONLY)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepelementdescriptions, languages " +
                        "WHERE fltstped_fltstpe_filterstepelementid = ? AND fltstped_thrutime = ? AND fltstped_lang_languageid = lang_languageid " +
                        "ORDER BY lang_sortorder, lang_languageisoname";
            } else if(entityPermission.equals(EntityPermission.READ_WRITE)) {
                query = "SELECT _ALL_ " +
                        "FROM filterstepelementdescriptions " +
                        "WHERE fltstped_fltstpe_filterstepelementid = ? AND fltstped_thrutime = ? " +
                        "FOR UPDATE";
            }

            var ps = FilterStepElementDescriptionFactory.getInstance().prepareStatement(query);
            
            ps.setLong(1, filterStepElement.getPrimaryKey().getEntityId());
            ps.setLong(2, Session.MAX_TIME);
            
            filterStepElementDescriptions = FilterStepElementDescriptionFactory.getInstance().getEntitiesFromQuery(entityPermission, ps);
        } catch (SQLException se) {
            throw new PersistenceDatabaseException(se);
        }
        
        return filterStepElementDescriptions;
    }
    
    public List<FilterStepElementDescription> getFilterStepElementDescriptions(FilterStepElement filterStepElement) {
        return getFilterStepElementDescriptions(filterStepElement, EntityPermission.READ_ONLY);
    }
    
    public List<FilterStepElementDescription> getFilterStepElementDescriptionsForUpdate(FilterStepElement filterStepElement) {
        return getFilterStepElementDescriptions(filterStepElement, EntityPermission.READ_WRITE);
    }
    
    public String getBestFilterStepElementDescription(FilterStepElement filterStepElement, Language language) {
        String description;
        var filterStepElementDescription = getFilterStepElementDescription(filterStepElement, language);
        
        if(filterStepElementDescription == null && !language.getIsDefault()) {
            filterStepElementDescription = getFilterStepElementDescription(filterStepElement, partyControl.getDefaultLanguage());
        }
        
        if(filterStepElementDescription == null) {
            description = filterStepElement.getLastDetail().getFilterStepElementName();
        } else {
            description = filterStepElementDescription.getDescription();
        }
        
        return description;
    }
    
    public FilterStepElementDescriptionTransfer getFilterStepElementDescriptionTransfer(UserVisit userVisit, FilterStepElementDescription filterStepElementDescription) {
        return filterStepElementDescriptionTransferCache.getTransfer(userVisit, filterStepElementDescription);
    }
    
    public List<FilterStepElementDescriptionTransfer> getFilterStepElementDescriptionTransfers(UserVisit userVisit, FilterStepElement filterStepElement) {
        var filterStepElementDescriptions = getFilterStepElementDescriptions(filterStepElement);
        List<FilterStepElementDescriptionTransfer> filterStepElementDescriptionTransfers = new ArrayList<>(filterStepElementDescriptions.size());
        
        filterStepElementDescriptions.forEach((filterStepElementDescription) ->
                filterStepElementDescriptionTransfers.add(filterStepElementDescriptionTransferCache.getTransfer(userVisit, filterStepElementDescription))
        );
        
        return filterStepElementDescriptionTransfers;
    }
    
    public void updateFilterStepElementDescriptionFromValue(FilterStepElementDescriptionValue filterStepElementDescriptionValue, BasePK updatedBy) {
        if(filterStepElementDescriptionValue.hasBeenModified()) {
            var filterStepElementDescription = FilterStepElementDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, filterStepElementDescriptionValue.getPrimaryKey());
            
            filterStepElementDescription.setThruTime(session.START_TIME_LONG);
            filterStepElementDescription.store();

            var filterStepElement = filterStepElementDescription.getFilterStepElement();
            var language = filterStepElementDescription.getLanguage();
            var description = filterStepElementDescriptionValue.getDescription();
            
            filterStepElementDescription = FilterStepElementDescriptionFactory.getInstance().create(filterStepElement,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEvent(filterStepElement.getLastDetail().getFilterStep().getLastDetail().getFilterPK(), EventTypes.MODIFY, filterStepElementDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterStepElementDescription(FilterStepElementDescription filterStepElementDescription, BasePK deletedBy) {
        filterStepElementDescription.setThruTime(session.START_TIME_LONG);
        
        sendEvent(filterStepElementDescription.getFilterStepElement().getLastDetail().getFilterStep().getLastDetail().getFilterPK(), EventTypes.MODIFY, filterStepElementDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteFilterStepElementDescriptionsByFilterStepElement(FilterStepElement filterStepElement, BasePK deletedBy) {
        var filterStepElementDescriptions = getFilterStepElementDescriptionsForUpdate(filterStepElement);
        
        filterStepElementDescriptions.forEach((filterStepElementDescription) -> 
                deleteFilterStepElementDescription(filterStepElementDescription, deletedBy)
        );
    }
    
}
