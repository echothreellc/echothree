// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterEntranceStepTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterKindTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepDestinationTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepElementDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepElementTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterTransferCaches;
import com.echothree.model.data.accounting.common.pk.CurrencyPK;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.filter.common.pk.FilterAdjustmentPK;
import com.echothree.model.data.filter.common.pk.FilterAdjustmentSourcePK;
import com.echothree.model.data.filter.common.pk.FilterAdjustmentTypePK;
import com.echothree.model.data.filter.common.pk.FilterKindPK;
import com.echothree.model.data.filter.common.pk.FilterPK;
import com.echothree.model.data.filter.common.pk.FilterStepElementPK;
import com.echothree.model.data.filter.common.pk.FilterStepPK;
import com.echothree.model.data.filter.common.pk.FilterTypePK;
import com.echothree.model.data.filter.server.entity.Filter;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentAmount;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentDescription;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentDetail;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentFixedAmount;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentPercent;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentSource;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentSourceDescription;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentType;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentTypeDescription;
import com.echothree.model.data.filter.server.entity.FilterDescription;
import com.echothree.model.data.filter.server.entity.FilterDetail;
import com.echothree.model.data.filter.server.entity.FilterEntranceStep;
import com.echothree.model.data.filter.server.entity.FilterKind;
import com.echothree.model.data.filter.server.entity.FilterKindDescription;
import com.echothree.model.data.filter.server.entity.FilterKindDetail;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepDescription;
import com.echothree.model.data.filter.server.entity.FilterStepDestination;
import com.echothree.model.data.filter.server.entity.FilterStepDetail;
import com.echothree.model.data.filter.server.entity.FilterStepElement;
import com.echothree.model.data.filter.server.entity.FilterStepElementDescription;
import com.echothree.model.data.filter.server.entity.FilterStepElementDetail;
import com.echothree.model.data.filter.server.entity.FilterType;
import com.echothree.model.data.filter.server.entity.FilterTypeDescription;
import com.echothree.model.data.filter.server.entity.FilterTypeDetail;
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
import com.echothree.model.data.selector.common.pk.SelectorPK;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.uom.common.pk.UnitOfMeasureTypePK;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.exception.PersistenceDatabaseException;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FilterControl
        extends BaseModelControl {
    
    /** Creates a new instance of FilterControl */
    public FilterControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Transfer Caches
    // --------------------------------------------------------------------------------
    
    private FilterTransferCaches filterTransferCaches;
    
    public FilterTransferCaches getFilterTransferCaches(UserVisit userVisit) {
        if(filterTransferCaches == null) {
            filterTransferCaches = new FilterTransferCaches(userVisit);
        }
        
        return filterTransferCaches;
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Kinds
    // --------------------------------------------------------------------------------

    public FilterKind createFilterKind(String filterKindName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        FilterKind defaultFilterKind = getDefaultFilterKind();
        boolean defaultFound = defaultFilterKind != null;

        if(defaultFound && isDefault) {
            FilterKindDetailValue defaultFilterKindDetailValue = getDefaultFilterKindDetailValueForUpdate();

            defaultFilterKindDetailValue.setIsDefault(Boolean.FALSE);
            updateFilterKindFromValue(defaultFilterKindDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        FilterKind filterKind = FilterKindFactory.getInstance().create();
        FilterKindDetail filterKindDetail = FilterKindDetailFactory.getInstance().create(filterKind, filterKindName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        filterKind = FilterKindFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                filterKind.getPrimaryKey());
        filterKind.setActiveDetail(filterKindDetail);
        filterKind.setLastDetail(filterKindDetail);
        filterKind.store();

        sendEventUsingNames(filterKind.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return filterKind;
    }
    
    public long countFilterKinds() {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                    "FROM filterkinds, filterkinddetails " +
                    "WHERE fltk_activedetailid = fltkdt_filterkinddetailid");
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.FilterKind */
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
                + "ORDER BY fltkdt_sortorder, fltkdt_filterkindname");
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
        List<FilterKind> filterKinds = getFilterKinds();
        int size = filterKinds.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultFilterKindChoice == null) {
                defaultValue = "";
            }
        }

        for(var filterKind : filterKinds) {
            FilterKindDetail filterKindDetail = filterKind.getLastDetail();

            String label = getBestFilterKindDescription(filterKind, language);
            String value = filterKindDetail.getFilterKindName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultFilterKindChoice != null && defaultFilterKindChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && filterKindDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new FilterKindChoicesBean(labels, values, defaultValue);
    }

    public FilterKindTransfer getFilterKindTransfer(UserVisit userVisit, FilterKind filterKind) {
        return getFilterTransferCaches(userVisit).getFilterKindTransferCache().getTransfer(filterKind);
    }

    public List<FilterKindTransfer> getFilterKindTransfers(UserVisit userVisit, Collection<FilterKind> filterKinds) {
        List<FilterKindTransfer> filterKindTransfers = new ArrayList<>(filterKinds.size());
        FilterKindTransferCache filterKindTransferCache = getFilterTransferCaches(userVisit).getFilterKindTransferCache();

        filterKinds.forEach((filterKind) ->
                filterKindTransfers.add(filterKindTransferCache.getTransfer(filterKind))
        );

        return filterKindTransfers;
    }

    public List<FilterKindTransfer> getFilterKindTransfers(UserVisit userVisit) {
        return getFilterKindTransfers(userVisit, getFilterKinds());
    }

    private void updateFilterKindFromValue(FilterKindDetailValue filterKindDetailValue, boolean checkDefault, BasePK updatedBy) {
        FilterKind filterKind = FilterKindFactory.getInstance().getEntityFromPK(session,
                EntityPermission.READ_WRITE, filterKindDetailValue.getFilterKindPK());
        FilterKindDetail filterKindDetail = filterKind.getActiveDetailForUpdate();

        filterKindDetail.setThruTime(session.START_TIME_LONG);
        filterKindDetail.store();

        FilterKindPK filterKindPK = filterKindDetail.getFilterKindPK();
        String filterKindName = filterKindDetailValue.getFilterKindName();
        Boolean isDefault = filterKindDetailValue.getIsDefault();
        Integer sortOrder = filterKindDetailValue.getSortOrder();

        if(checkDefault) {
            FilterKind defaultFilterKind = getDefaultFilterKind();
            boolean defaultFound = defaultFilterKind != null && !defaultFilterKind.equals(filterKind);

            if(isDefault && defaultFound) {
                // If I'm the default, and a default already existed...
                FilterKindDetailValue defaultFilterKindDetailValue = getDefaultFilterKindDetailValueForUpdate();

                defaultFilterKindDetailValue.setIsDefault(Boolean.FALSE);
                updateFilterKindFromValue(defaultFilterKindDetailValue, false, updatedBy);
            } else if(!isDefault && !defaultFound) {
                // If I'm not the default, and no other default exists...
                isDefault = Boolean.TRUE;
            }
        }

        filterKindDetail = FilterKindDetailFactory.getInstance().create(filterKindPK, filterKindName, isDefault, sortOrder, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);

        filterKind.setActiveDetail(filterKindDetail);
        filterKind.setLastDetail(filterKindDetail);
        filterKind.store();

        sendEventUsingNames(filterKindPK, EventTypes.MODIFY.name(), null, null, updatedBy);
    }

    public void updateFilterKindFromValue(FilterKindDetailValue filterKindDetailValue, BasePK updatedBy) {
        updateFilterKindFromValue(filterKindDetailValue, true, updatedBy);
    }

    public void deleteFilterKind(FilterKind filterKind, BasePK deletedBy) {
        deleteFilterKindDescriptionsByFilterKind(filterKind, deletedBy);

        FilterKindDetail filterKindDetail = filterKind.getLastDetailForUpdate();
        filterKindDetail.setThruTime(session.START_TIME_LONG);
        filterKind.setActiveDetail(null);
        filterKind.store();

        // Check for default, and pick one if necessary
        FilterKind defaultFilterKind = getDefaultFilterKind();
        if(defaultFilterKind == null) {
            List<FilterKind> filterKinds = getFilterKindsForUpdate();

            if(!filterKinds.isEmpty()) {
                Iterator<FilterKind> iter = filterKinds.iterator();
                if(iter.hasNext()) {
                    defaultFilterKind = iter.next();
                }
                FilterKindDetailValue filterKindDetailValue = Objects.requireNonNull(defaultFilterKind).getLastDetailForUpdate().getFilterKindDetailValue().clone();

                filterKindDetailValue.setIsDefault(Boolean.TRUE);
                updateFilterKindFromValue(filterKindDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(filterKind.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Filter Kind Descriptions
    // --------------------------------------------------------------------------------

    public FilterKindDescription createFilterKindDescription(FilterKind filterKind, Language language, String description,
            BasePK createdBy) {
        FilterKindDescription filterKindDescription = FilterKindDescriptionFactory.getInstance().create(filterKind,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(filterKind.getPrimaryKey(), EventTypes.MODIFY.name(), filterKindDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        FilterKindDescription filterKindDescription = getFilterKindDescription(filterKind, language);

        if(filterKindDescription == null && !language.getIsDefault()) {
            filterKindDescription = getFilterKindDescription(filterKind, getPartyControl().getDefaultLanguage());
        }

        if(filterKindDescription == null) {
            description = filterKind.getLastDetail().getFilterKindName();
        } else {
            description = filterKindDescription.getDescription();
        }

        return description;
    }

    public FilterKindDescriptionTransfer getFilterKindDescriptionTransfer(UserVisit userVisit, FilterKindDescription filterKindDescription) {
        return getFilterTransferCaches(userVisit).getFilterKindDescriptionTransferCache().getTransfer(filterKindDescription);
    }

    public List<FilterKindDescriptionTransfer> getFilterKindDescriptionTransfersByFilterKind(UserVisit userVisit, FilterKind filterKind) {
        List<FilterKindDescription> filterKindDescriptions = getFilterKindDescriptionsByFilterKind(filterKind);
        List<FilterKindDescriptionTransfer> filterKindDescriptionTransfers = new ArrayList<>(filterKindDescriptions.size());

        filterKindDescriptions.forEach((filterKindDescription) ->
                filterKindDescriptionTransfers.add(getFilterTransferCaches(userVisit).getFilterKindDescriptionTransferCache().getTransfer(filterKindDescription))
        );

        return filterKindDescriptionTransfers;
    }

    public void updateFilterKindDescriptionFromValue(FilterKindDescriptionValue filterKindDescriptionValue, BasePK updatedBy) {
        if(filterKindDescriptionValue.hasBeenModified()) {
            FilterKindDescription filterKindDescription = FilterKindDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterKindDescriptionValue.getPrimaryKey());

            filterKindDescription.setThruTime(session.START_TIME_LONG);
            filterKindDescription.store();

            FilterKind filterKind = filterKindDescription.getFilterKind();
            Language language = filterKindDescription.getLanguage();
            String description = filterKindDescriptionValue.getDescription();

            filterKindDescription = FilterKindDescriptionFactory.getInstance().create(filterKind, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(filterKind.getPrimaryKey(), EventTypes.MODIFY.name(), filterKindDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteFilterKindDescription(FilterKindDescription filterKindDescription, BasePK deletedBy) {
        filterKindDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(filterKindDescription.getFilterKindPK(), EventTypes.MODIFY.name(), filterKindDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteFilterKindDescriptionsByFilterKind(FilterKind filterKind, BasePK deletedBy) {
        List<FilterKindDescription> filterKindDescriptions = getFilterKindDescriptionsByFilterKindForUpdate(filterKind);

        filterKindDescriptions.forEach((filterKindDescription) -> 
                deleteFilterKindDescription(filterKindDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Filter Types
    // --------------------------------------------------------------------------------

    public FilterType createFilterType(FilterKind filterKind, String filterTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        FilterType defaultFilterType = getDefaultFilterType(filterKind);
        boolean defaultFound = defaultFilterType != null;

        if(defaultFound && isDefault) {
            FilterTypeDetailValue defaultFilterTypeDetailValue = getDefaultFilterTypeDetailValueForUpdate(filterKind);

            defaultFilterTypeDetailValue.setIsDefault(Boolean.FALSE);
            updateFilterTypeFromValue(defaultFilterTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }

        FilterType filterType = FilterTypeFactory.getInstance().create();
        FilterTypeDetail filterTypeDetail = FilterTypeDetailFactory.getInstance().create(session, filterType, filterKind, filterTypeName, isDefault, sortOrder,
                session.START_TIME_LONG, Session.MAX_TIME_LONG);

        // Convert to R/W
        filterType = FilterTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                filterType.getPrimaryKey());
        filterType.setActiveDetail(filterTypeDetail);
        filterType.setLastDetail(filterTypeDetail);
        filterType.store();

        sendEventUsingNames(filterType.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);

        return filterType;
    }

    public long countFilterTypesByFilterKind(FilterKind filterKind) {
        return session.queryForLong(
                "SELECT COUNT(*) "
                + "FROM filtertypes, filtertypedetails "
                + "WHERE flttyp_activedetailid = flttypdt_filtertypedetailid AND flttypdt_fltk_filterkindid = ?",
                filterKind);
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.FilterType */
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
                + "ORDER BY flttypdt_sortorder, flttypdt_filtertypename");
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
        List<FilterType> filterTypes = getFilterTypes(filterKind);
        int size = filterTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultFilterTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var filterType : filterTypes) {
            FilterTypeDetail filterTypeDetail = filterType.getLastDetail();
            String label = getBestFilterTypeDescription(filterType, language);
            String value = filterTypeDetail.getFilterTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            boolean usingDefaultChoice = defaultFilterTypeChoice != null && defaultFilterTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && filterTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new FilterTypeChoicesBean(labels, values, defaultValue);
    }

    public FilterTypeTransfer getFilterTypeTransfer(UserVisit userVisit, FilterType filterType) {
        return getFilterTransferCaches(userVisit).getFilterTypeTransferCache().getTransfer(filterType);
    }

    public List<FilterTypeTransfer> getFilterTypeTransfers(UserVisit userVisit, Collection<FilterType> filterTypes) {
        var filterTypeTransfers = new ArrayList<FilterTypeTransfer>(filterTypes.size());
        var filterTypeTransferCache = getFilterTransferCaches(userVisit).getFilterTypeTransferCache();

        filterTypes.forEach((filterType) ->
            filterTypeTransfers.add(filterTypeTransferCache.getTransfer(filterType))
        );

        return filterTypeTransfers;
    }

    public List<FilterTypeTransfer> getFilterTypeTransfersByFilterKind(UserVisit userVisit, FilterKind filterKind) {
        return getFilterTypeTransfers(userVisit, getFilterTypes(filterKind));
    }

    private void updateFilterTypeFromValue(FilterTypeDetailValue filterTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(filterTypeDetailValue.hasBeenModified()) {
            FilterType filterType = FilterTypeFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterTypeDetailValue.getFilterTypePK());
            FilterTypeDetail filterTypeDetail = filterType.getActiveDetailForUpdate();

            filterTypeDetail.setThruTime(session.START_TIME_LONG);
            filterTypeDetail.store();

            FilterTypePK filterTypePK = filterTypeDetail.getFilterTypePK();
            FilterKind filterKind = filterTypeDetail.getFilterKind();
            FilterKindPK filterKindPK = filterKind.getPrimaryKey();
            String filterTypeName = filterTypeDetailValue.getFilterTypeName();
            Boolean isDefault = filterTypeDetailValue.getIsDefault();
            Integer sortOrder = filterTypeDetailValue.getSortOrder();

            if(checkDefault) {
                FilterType defaultFilterType = getDefaultFilterType(filterKind);
                boolean defaultFound = defaultFilterType != null && !defaultFilterType.equals(filterType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    FilterTypeDetailValue defaultFilterTypeDetailValue = getDefaultFilterTypeDetailValueForUpdate(filterKind);

                    defaultFilterTypeDetailValue.setIsDefault(Boolean.FALSE);
                    updateFilterTypeFromValue(defaultFilterTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }

            filterTypeDetail = FilterTypeDetailFactory.getInstance().create(filterTypePK, filterKindPK, filterTypeName, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            filterType.setActiveDetail(filterTypeDetail);
            filterType.setLastDetail(filterTypeDetail);

            sendEventUsingNames(filterTypePK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }

    public void updateFilterTypeFromValue(FilterTypeDetailValue filterTypeDetailValue, BasePK updatedBy) {
        updateFilterTypeFromValue(filterTypeDetailValue, true, updatedBy);
    }

    public void deleteFilterType(FilterType filterType, BasePK deletedBy) {
        deleteFilterTypeDescriptionsByFilterType(filterType, deletedBy);

        FilterTypeDetail filterTypeDetail = filterType.getLastDetailForUpdate();
        filterTypeDetail.setThruTime(session.START_TIME_LONG);
        filterType.setActiveDetail(null);
        filterType.store();

        // Check for default, and pick one if necessary
        FilterKind filterKind = filterTypeDetail.getFilterKind();
        FilterType defaultFilterType = getDefaultFilterType(filterKind);
        if(defaultFilterType == null) {
            List<FilterType> filterTypes = getFilterTypesForUpdate(filterKind);

            if(!filterTypes.isEmpty()) {
                Iterator<FilterType> iter = filterTypes.iterator();
                if(iter.hasNext()) {
                    defaultFilterType = iter.next();
                }
                FilterTypeDetailValue filterTypeDetailValue = Objects.requireNonNull(defaultFilterType).getLastDetailForUpdate().getFilterTypeDetailValue().clone();

                filterTypeDetailValue.setIsDefault(Boolean.TRUE);
                updateFilterTypeFromValue(filterTypeDetailValue, false, deletedBy);
            }
        }

        sendEventUsingNames(filterType.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }

    public void deleteFilterTypesByFilterKind(FilterKind filterKind, BasePK deletedBy) {
        List<FilterType> filterTypes = getFilterTypesForUpdate(filterKind);

        filterTypes.forEach((filterType) -> 
                deleteFilterType(filterType, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Filter Type Descriptions
    // --------------------------------------------------------------------------------

    public FilterTypeDescription createFilterTypeDescription(FilterType filterType, Language language, String description,
            BasePK createdBy) {
        FilterTypeDescription filterTypeDescription = FilterTypeDescriptionFactory.getInstance().create(filterType,
                language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);

        sendEventUsingNames(filterType.getPrimaryKey(), EventTypes.MODIFY.name(), filterTypeDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);

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
        FilterTypeDescription filterTypeDescription = getFilterTypeDescription(filterType, language);

        if(filterTypeDescription == null && !language.getIsDefault()) {
            filterTypeDescription = getFilterTypeDescription(filterType, getPartyControl().getDefaultLanguage());
        }

        if(filterTypeDescription == null) {
            description = filterType.getLastDetail().getFilterTypeName();
        } else {
            description = filterTypeDescription.getDescription();
        }

        return description;
    }

    public FilterTypeDescriptionTransfer getFilterTypeDescriptionTransfer(UserVisit userVisit, FilterTypeDescription filterTypeDescription) {
        return getFilterTransferCaches(userVisit).getFilterTypeDescriptionTransferCache().getTransfer(filterTypeDescription);
    }

    public List<FilterTypeDescriptionTransfer> getFilterTypeDescriptionTransfersByFilterType(UserVisit userVisit, FilterType filterType) {
        List<FilterTypeDescription> filterTypeDescriptions = getFilterTypeDescriptionsByFilterType(filterType);
        List<FilterTypeDescriptionTransfer> filterTypeDescriptionTransfers = new ArrayList<>(filterTypeDescriptions.size());

        filterTypeDescriptions.forEach((filterTypeDescription) -> {
            filterTypeDescriptionTransfers.add(getFilterTransferCaches(userVisit).getFilterTypeDescriptionTransferCache().getTransfer(filterTypeDescription));
        });

        return filterTypeDescriptionTransfers;
    }

    public void updateFilterTypeDescriptionFromValue(FilterTypeDescriptionValue filterTypeDescriptionValue, BasePK updatedBy) {
        if(filterTypeDescriptionValue.hasBeenModified()) {
            FilterTypeDescription filterTypeDescription = FilterTypeDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterTypeDescriptionValue.getPrimaryKey());

            filterTypeDescription.setThruTime(session.START_TIME_LONG);
            filterTypeDescription.store();

            FilterType filterType = filterTypeDescription.getFilterType();
            Language language = filterTypeDescription.getLanguage();
            String description = filterTypeDescriptionValue.getDescription();

            filterTypeDescription = FilterTypeDescriptionFactory.getInstance().create(filterType, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);

            sendEventUsingNames(filterType.getPrimaryKey(), EventTypes.MODIFY.name(), filterTypeDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }

    public void deleteFilterTypeDescription(FilterTypeDescription filterTypeDescription, BasePK deletedBy) {
        filterTypeDescription.setThruTime(session.START_TIME_LONG);

        sendEventUsingNames(filterTypeDescription.getFilterTypePK(), EventTypes.MODIFY.name(), filterTypeDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);

    }

    public void deleteFilterTypeDescriptionsByFilterType(FilterType filterType, BasePK deletedBy) {
        List<FilterTypeDescription> filterTypeDescriptions = getFilterTypeDescriptionsByFilterTypeForUpdate(filterType);

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
        PreparedStatement ps = FilterAdjustmentSourceFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM filteradjustmentsources " +
                "ORDER BY fltas_sortorder, fltas_filteradjustmentsourcename");
        
        return FilterAdjustmentSourceFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public FilterAdjustmentSource getFilterAdjustmentSourceByName(String filterAdjustmentSourceName) {
        FilterAdjustmentSource filterAdjustmentSource;
        
        try {
            PreparedStatement ps = FilterAdjustmentSourceFactory.getInstance().prepareStatement(
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
        List<FilterAdjustmentSource> filterAdjustmentSources = getFilterAdjustmentSources();
        int size = filterAdjustmentSources.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        for(var filterAdjustmentSource : filterAdjustmentSources) {
            String label = getBestFilterAdjustmentSourceDescription(filterAdjustmentSource, language);
            String value = filterAdjustmentSource.getFilterAdjustmentSourceName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultFilterAdjustmentSourceChoice == null? false:
                defaultFilterAdjustmentSourceChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new FilterAdjustmentSourceChoicesBean(labels, values, defaultValue);
    }

    public FilterAdjustmentSourceTransfer getFilterAdjustmentSourceTransfer(UserVisit userVisit, FilterAdjustmentSource filterAdjustmentSource) {
        return getFilterTransferCaches(userVisit).getFilterAdjustmentSourceTransferCache().getTransfer(filterAdjustmentSource);
    }

    public List<FilterAdjustmentSourceTransfer> getFilterAdjustmentSourceTransfers(UserVisit userVisit, Collection<FilterAdjustmentSource> filterAdjustmentSources) {
        var filterAdjustmentSourceTransfers = new ArrayList<FilterAdjustmentSourceTransfer>(filterAdjustmentSources.size());
        var filterAdjustmentSourceTransferCache = getFilterTransferCaches(userVisit).getFilterAdjustmentSourceTransferCache();

        filterAdjustmentSources.forEach((filterAdjustmentSource) ->
                filterAdjustmentSourceTransfers.add(filterAdjustmentSourceTransferCache.getTransfer(filterAdjustmentSource))
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
            PreparedStatement ps = FilterAdjustmentSourceDescriptionFactory.getInstance().prepareStatement(
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
        FilterAdjustmentSourceDescription filterAdjustmentSourceDescription = getFilterAdjustmentSourceDescription(filterAdjustmentSource,
                language);
        
        if(filterAdjustmentSourceDescription == null && !language.getIsDefault()) {
            filterAdjustmentSourceDescription = getFilterAdjustmentSourceDescription(filterAdjustmentSource,
                    getPartyControl().getDefaultLanguage());
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
        PreparedStatement ps = FilterAdjustmentTypeFactory.getInstance().prepareStatement(
                "SELECT _ALL_ " +
                "FROM filteradjustmenttypes " +
                "ORDER BY fltat_sortorder, fltat_filteradjustmenttypename");
        
        return FilterAdjustmentTypeFactory.getInstance().getEntitiesFromQuery(EntityPermission.READ_ONLY, ps);
    }
    
    public FilterAdjustmentType getFilterAdjustmentTypeByName(String filterAdjustmentTypeName) {
        FilterAdjustmentType filterAdjustmentType;
        
        try {
            PreparedStatement ps = FilterAdjustmentTypeFactory.getInstance().prepareStatement(
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
        List<FilterAdjustmentType> filterAdjustmentTypes = getFilterAdjustmentTypes();
        int size = filterAdjustmentTypes.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        labels.add("");
        values.add("");
        
        for(var filterAdjustmentType : filterAdjustmentTypes) {
            String label = getBestFilterAdjustmentTypeDescription(filterAdjustmentType, language);
            String value = filterAdjustmentType.getFilterAdjustmentTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultFilterAdjustmentTypeChoice == null? false:
                defaultFilterAdjustmentTypeChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null)
                defaultValue = value;
        }
        
        return new FilterAdjustmentTypeChoicesBean(labels, values, defaultValue);
    }

    public FilterAdjustmentTypeTransfer getFilterAdjustmentTypeTransfer(UserVisit userVisit, FilterAdjustmentType filterAdjustmentType) {
        return getFilterTransferCaches(userVisit).getFilterAdjustmentTypeTransferCache().getTransfer(filterAdjustmentType);
    }

    public List<FilterAdjustmentTypeTransfer> getFilterAdjustmentTypeTransfers(UserVisit userVisit, Collection<FilterAdjustmentType> filterAdjustmentTypes) {
        var filterAdjustmentTypeTransfers = new ArrayList<FilterAdjustmentTypeTransfer>(filterAdjustmentTypes.size());
        var filterAdjustmentTypeTransferCache = getFilterTransferCaches(userVisit).getFilterAdjustmentTypeTransferCache();

        filterAdjustmentTypes.forEach((filterAdjustmentType) ->
                filterAdjustmentTypeTransfers.add(filterAdjustmentTypeTransferCache.getTransfer(filterAdjustmentType))
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
            PreparedStatement ps = FilterAdjustmentTypeDescriptionFactory.getInstance().prepareStatement(
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
        FilterAdjustmentTypeDescription filterAdjustmentTypeDescription = getFilterAdjustmentTypeDescription(filterAdjustmentType,
                language);
        
        if(filterAdjustmentTypeDescription == null && !language.getIsDefault()) {
            filterAdjustmentTypeDescription = getFilterAdjustmentTypeDescription(filterAdjustmentType, getPartyControl().getDefaultLanguage());
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
        FilterAdjustment defaultFilterAdjustment = getDefaultFilterAdjustment(filterKind);
        boolean defaultFound = defaultFilterAdjustment != null;
        
        if(defaultFound && isDefault) {
            FilterAdjustmentDetailValue defaultFilterAdjustmentDetailValue = getDefaultFilterAdjustmentDetailValueForUpdate(filterKind);
            
            defaultFilterAdjustmentDetailValue.setIsDefault(Boolean.FALSE);
            updateFilterAdjustmentFromValue(defaultFilterAdjustmentDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        FilterAdjustment filterAdjustment = FilterAdjustmentFactory.getInstance().create();
        FilterAdjustmentDetail filterAdjustmentDetail = FilterAdjustmentDetailFactory.getInstance().create(session,
                filterAdjustment, filterKind, filterAdjustmentName, filterAdjustmentSource, filterAdjustmentType,
                isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        filterAdjustment = FilterAdjustmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                filterAdjustment.getPrimaryKey());
        filterAdjustment.setActiveDetail(filterAdjustmentDetail);
        filterAdjustment.setLastDetail(filterAdjustmentDetail);
        filterAdjustment.store();
        
        sendEventUsingNames(filterAdjustment.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return filterAdjustment;
    }

    /** Assume that the entityInstance passed to this function is a ECHOTHREE.FilterAdjustment */
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
        PreparedStatement ps = FilterAdjustmentFactory.getInstance().prepareStatement(
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
            
            PreparedStatement ps = FilterAdjustmentFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = FilterAdjustmentFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = FilterAdjustmentFactory.getInstance().prepareStatement(query);
            
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
        return getFilterTransferCaches(userVisit).getFilterAdjustmentTransferCache().getTransfer(filterAdjustment);
    }
    
    public List<FilterAdjustmentTransfer> getFilterAdjustmentTransfers(UserVisit userVisit,  Collection<FilterAdjustment> filterAdjustments) {
        List<FilterAdjustmentTransfer> filterAdjustmentTransfers = new ArrayList<>(filterAdjustments.size());
        FilterAdjustmentTransferCache filterAdjustmentTransferCache = getFilterTransferCaches(userVisit).getFilterAdjustmentTransferCache();
        
        filterAdjustments.forEach((filterAdjustment) ->
                filterAdjustmentTransfers.add(filterAdjustmentTransferCache.getTransfer(filterAdjustment))
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
        List<FilterAdjustment> filterAdjustments = getFilterAdjustmentsByFilterKind(filterKind);
        int size = filterAdjustments.size();
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        for(var filterAdjustment : filterAdjustments) {
            if(!initialAdjustmentsOnly || filterAdjustment.getLastDetail().getFilterAdjustmentSource().getAllowedForInitialAmount()) {
                String label = getBestFilterAdjustmentDescription(filterAdjustment, language);
                String value = filterAdjustment.getLastDetail().getFilterAdjustmentName();
                
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
            FilterAdjustment filterAdjustment = FilterAdjustmentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentDetailValue.getFilterAdjustmentPK());
            FilterAdjustmentDetail filterAdjustmentDetail = filterAdjustment.getActiveDetailForUpdate();
            
            filterAdjustmentDetail.setThruTime(session.START_TIME_LONG);
            filterAdjustmentDetail.store();
            
            FilterAdjustmentPK filterAdjustmentPK = filterAdjustmentDetail.getFilterAdjustmentPK();
            FilterKind filterKind = filterAdjustmentDetail.getFilterKind();
            FilterKindPK filterKindPK = filterKind.getPrimaryKey();
            String filterAdjustmentName = filterAdjustmentDetailValue.getFilterAdjustmentName();
            FilterAdjustmentSourcePK filterAdjustmentSourcePK = filterAdjustmentDetailValue.getFilterAdjustmentSourcePK();
            FilterAdjustmentTypePK filterAdjustmentTypePK = filterAdjustmentDetailValue.getFilterAdjustmentTypePK();
            Boolean isDefault = filterAdjustmentDetailValue.getIsDefault();
            Integer sortOrder = filterAdjustmentDetailValue.getSortOrder();
            
            if(checkDefault) {
                FilterAdjustment defaultFilterAdjustment = getDefaultFilterAdjustment(filterKind);
                boolean defaultFound = defaultFilterAdjustment != null && !defaultFilterAdjustment.equals(filterAdjustment);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    FilterAdjustmentDetailValue defaultFilterAdjustmentDetailValue = getDefaultFilterAdjustmentDetailValueForUpdate(filterKind);
                    
                    defaultFilterAdjustmentDetailValue.setIsDefault(Boolean.FALSE);
                    updateFilterAdjustmentFromValue(defaultFilterAdjustmentDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            filterAdjustmentDetail = FilterAdjustmentDetailFactory.getInstance().create(filterAdjustmentPK, filterKindPK,
                    filterAdjustmentName, filterAdjustmentSourcePK, filterAdjustmentTypePK, isDefault, sortOrder,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            filterAdjustment.setActiveDetail(filterAdjustmentDetail);
            filterAdjustment.setLastDetail(filterAdjustmentDetail);
            
            sendEventUsingNames(filterAdjustmentPK, EventTypes.MODIFY.name(), null, null, updatedBy);
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
        
        FilterAdjustmentDetail filterAdjustmentDetail = filterAdjustment.getLastDetailForUpdate();
        filterAdjustmentDetail.setThruTime(session.START_TIME_LONG);
        filterAdjustment.setActiveDetail(null);
        filterAdjustment.store();
        
        // Check for default, and pick one if necessary
        FilterKind filterKind = filterAdjustmentDetail.getFilterKind();
        FilterAdjustment defaultFilterAdjustment = getDefaultFilterAdjustment(filterKind);
        if(defaultFilterAdjustment == null) {
            List<FilterAdjustment> filterKindPriorities = getFilterAdjustmentsByFilterKindForUpdate(filterKind);
            
            if(!filterKindPriorities.isEmpty()) {
                Iterator<FilterAdjustment> iter = filterKindPriorities.iterator();
                if(iter.hasNext()) {
                    defaultFilterAdjustment = iter.next();
                }
                FilterAdjustmentDetailValue filterAdjustmentDetailValue = Objects.requireNonNull(defaultFilterAdjustment).getLastDetailForUpdate().getFilterAdjustmentDetailValue().clone();
                
                filterAdjustmentDetailValue.setIsDefault(Boolean.TRUE);
                updateFilterAdjustmentFromValue(filterAdjustmentDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(filterAdjustment.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Adjustment Amounts
    // --------------------------------------------------------------------------------
    
    public FilterAdjustmentAmount createFilterAdjustmentAmount(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency, Long amount, BasePK createdBy) {
        FilterAdjustmentAmount filterAdjustmentAmount = FilterAdjustmentAmountFactory.getInstance().create(session,
                filterAdjustment, unitOfMeasureType, currency, amount, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY.name(),
                filterAdjustmentAmount.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = FilterAdjustmentAmountFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = FilterAdjustmentAmountFactory.getInstance().prepareStatement(query);
            
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
        FilterAdjustmentAmount filterAdjustmentAmount = getFilterAdjustmentAmountForUpdate(filterAdjustment, unitOfMeasureType, currency);
        
        return filterAdjustmentAmount == null? null: filterAdjustmentAmount.getFilterAdjustmentAmountValue().clone();
    }
    
    public FilterAdjustmentAmountTransfer getFilterAdjustmentAmountTransfer(UserVisit userVisit,
            FilterAdjustmentAmount filterAdjustmentAmount) {
        return getFilterTransferCaches(userVisit).getFilterAdjustmentAmountTransferCache().getTransfer(filterAdjustmentAmount);
    }
    
    public List<FilterAdjustmentAmountTransfer> getFilterAdjustmentAmountTransfers(UserVisit userVisit, FilterAdjustment filterAdjustment) {
        List<FilterAdjustmentAmount> filterAdjustmentAmounts = getFilterAdjustmentAmounts(filterAdjustment);
        List<FilterAdjustmentAmountTransfer> filterAdjustmentAmountTransfers = new ArrayList<>(filterAdjustmentAmounts.size());
        FilterAdjustmentAmountTransferCache filterAdjustmentAmountTransferCache = getFilterTransferCaches(userVisit).getFilterAdjustmentAmountTransferCache();
        
        filterAdjustmentAmounts.forEach((filterAdjustmentAmount) ->
                filterAdjustmentAmountTransfers.add(filterAdjustmentAmountTransferCache.getTransfer(filterAdjustmentAmount))
        );
        
        return filterAdjustmentAmountTransfers;
    }
    
    public void updateFilterAdjustmentAmountFromValue(FilterAdjustmentAmountValue filterAdjustmentAmountValue, BasePK updatedBy) {
        if(filterAdjustmentAmountValue.hasBeenModified()) {
            FilterAdjustmentAmount filterAdjustmentAmount = FilterAdjustmentAmountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentAmountValue.getPrimaryKey());
            
            filterAdjustmentAmount.setThruTime(session.START_TIME_LONG);
            filterAdjustmentAmount.store();
            
            FilterAdjustmentPK filterAdjustmentPK = filterAdjustmentAmount.getFilterAdjustmentPK(); // Not updated
            UnitOfMeasureTypePK unitOfMeasureTypePK = filterAdjustmentAmount.getUnitOfMeasureTypePK(); // Not updated
            CurrencyPK currencyPK = filterAdjustmentAmount.getCurrencyPK(); // Not updated
            Long amount = filterAdjustmentAmountValue.getAmount();
            
            filterAdjustmentAmount = FilterAdjustmentAmountFactory.getInstance().create(filterAdjustmentPK,
                    unitOfMeasureTypePK, currencyPK, amount, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(filterAdjustmentPK, EventTypes.MODIFY.name(),
                    filterAdjustmentAmount.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteFilterAdjustmentAmount(FilterAdjustmentAmount filterAdjustmentAmount, BasePK deletedBy) {
        filterAdjustmentAmount.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(filterAdjustmentAmount.getFilterAdjustment().getPrimaryKey(), EventTypes.MODIFY.name(),
                filterAdjustmentAmount.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteFilterAdjustmentAmountsByFilterAdjustment(FilterAdjustment filterAdjustment, BasePK deletedBy) {
        List<FilterAdjustmentAmount> filterAdjustmentAmounts = getFilterAdjustmentAmountsForUpdate(filterAdjustment);
        
        filterAdjustmentAmounts.forEach((deleteFilterAdjustmentAmount) -> 
                deleteFilterAdjustmentAmount(deleteFilterAdjustmentAmount, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Adjustment Fixed Amounts
    // --------------------------------------------------------------------------------
    
    public FilterAdjustmentFixedAmount createFilterAdjustmentFixedAmount(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency, Long unitAmount, BasePK createdBy) {
        FilterAdjustmentFixedAmount filterAdjustmentFixedAmount = FilterAdjustmentFixedAmountFactory.getInstance().create(session,
                filterAdjustment, unitOfMeasureType, currency, unitAmount, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY.name(),
                filterAdjustmentFixedAmount.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = FilterAdjustmentFixedAmountFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = FilterAdjustmentFixedAmountFactory.getInstance().prepareStatement(query);
            
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
        FilterAdjustmentFixedAmount filterAdjustmentFixedAmount = getFilterAdjustmentFixedAmountForUpdate(filterAdjustment,
                unitOfMeasureType, currency);
        
        return filterAdjustmentFixedAmount == null? null: filterAdjustmentFixedAmount.getFilterAdjustmentFixedAmountValue().clone();
    }
    
    public FilterAdjustmentFixedAmountTransfer getFilterAdjustmentFixedAmountTransfer(UserVisit userVisit,
            FilterAdjustmentFixedAmount filterAdjustmentFixedAmount) {
        return getFilterTransferCaches(userVisit).getFilterAdjustmentFixedAmountTransferCache().getTransfer(filterAdjustmentFixedAmount);
    }
    
    public List<FilterAdjustmentFixedAmountTransfer> getFilterAdjustmentFixedAmountTransfers(UserVisit userVisit, FilterAdjustment filterAdjustment) {
        List<FilterAdjustmentFixedAmount> filterAdjustmentFixedAmounts = getFilterAdjustmentFixedAmounts(filterAdjustment);
        List<FilterAdjustmentFixedAmountTransfer> filterAdjustmentFixedAmountTransfers = new ArrayList<>(filterAdjustmentFixedAmounts.size());
        FilterAdjustmentFixedAmountTransferCache filterAdjustmentFixedAmountTransferCache = getFilterTransferCaches(userVisit).getFilterAdjustmentFixedAmountTransferCache();
        
        filterAdjustmentFixedAmounts.forEach((filterAdjustmentFixedAmount) ->
                filterAdjustmentFixedAmountTransfers.add(filterAdjustmentFixedAmountTransferCache.getTransfer(filterAdjustmentFixedAmount))
        );
        
        return filterAdjustmentFixedAmountTransfers;
    }
    
    public void updateFilterAdjustmentFixedAmountFromValue(FilterAdjustmentFixedAmountValue filterAdjustmentFixedAmountValue, BasePK updatedBy) {
        if(filterAdjustmentFixedAmountValue.hasBeenModified()) {
            FilterAdjustmentFixedAmount filterAdjustmentFixedAmount = FilterAdjustmentFixedAmountFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentFixedAmountValue.getPrimaryKey());
            
            filterAdjustmentFixedAmount.setThruTime(session.START_TIME_LONG);
            filterAdjustmentFixedAmount.store();
            
            FilterAdjustmentPK filterAdjustmentPK = filterAdjustmentFixedAmount.getFilterAdjustmentPK(); // Not updated
            UnitOfMeasureTypePK unitOfMeasureTypePK = filterAdjustmentFixedAmount.getUnitOfMeasureTypePK(); // Not updated
            CurrencyPK currencyPK = filterAdjustmentFixedAmount.getCurrencyPK(); // Not updated
            Long unitAmount = filterAdjustmentFixedAmountValue.getUnitAmount();
            
            filterAdjustmentFixedAmount = FilterAdjustmentFixedAmountFactory.getInstance().create(filterAdjustmentPK,
                    unitOfMeasureTypePK, currencyPK, unitAmount, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(filterAdjustmentPK, EventTypes.MODIFY.name(),
                    filterAdjustmentFixedAmount.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteFilterAdjustmentFixedAmount(FilterAdjustmentFixedAmount filterAdjustmentFixedAmount, BasePK deletedBy) {
        filterAdjustmentFixedAmount.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(filterAdjustmentFixedAmount.getFilterAdjustment().getPrimaryKey(),
                EventTypes.MODIFY.name(), filterAdjustmentFixedAmount.getPrimaryKey(), EventTypes.DELETE.name(),
                deletedBy);
    }
    
    public void deleteFilterAdjustmentFixedAmountsByFilterAdjustment(FilterAdjustment filterAdjustment, BasePK deletedBy) {
        List<FilterAdjustmentFixedAmount> filterAdjustmentFixedAmounts = getFilterAdjustmentFixedAmountsForUpdate(filterAdjustment);
        
        filterAdjustmentFixedAmounts.forEach((filterAdjustmentFixedAmount) -> 
                deleteFilterAdjustmentFixedAmount(filterAdjustmentFixedAmount, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Adjustment Percents
    // --------------------------------------------------------------------------------
    
    public FilterAdjustmentPercent createFilterAdjustmentPercent(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency, Integer percent, BasePK createdBy) {
        FilterAdjustmentPercent filterAdjustmentPercent = FilterAdjustmentPercentFactory.getInstance().create(session,
                filterAdjustment, unitOfMeasureType, currency, percent, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY.name(),
                filterAdjustmentPercent.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = FilterAdjustmentPercentFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = FilterAdjustmentPercentFactory.getInstance().prepareStatement(query);
            
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
        FilterAdjustmentPercent filterAdjustmentPercent = getFilterAdjustmentPercentForUpdate(filterAdjustment, unitOfMeasureType,
                currency);
        
        return filterAdjustmentPercent == null? null: filterAdjustmentPercent.getFilterAdjustmentPercentValue().clone();
    }
    
    public FilterAdjustmentPercentTransfer getFilterAdjustmentPercentTransfer(UserVisit userVisit,
            FilterAdjustmentPercent filterAdjustmentPercent) {
        return getFilterTransferCaches(userVisit).getFilterAdjustmentPercentTransferCache().getTransfer(filterAdjustmentPercent);
    }
    
    public List<FilterAdjustmentPercentTransfer> getFilterAdjustmentPercentTransfers(UserVisit userVisit, FilterAdjustment filterAdjustment) {
        List<FilterAdjustmentPercent> filterAdjustmentPercents = getFilterAdjustmentPercents(filterAdjustment);
        List<FilterAdjustmentPercentTransfer> filterAdjustmentPercentTransfers = new ArrayList<>(filterAdjustmentPercents.size());
        FilterAdjustmentPercentTransferCache filterAdjustmentPercentTransferCache = getFilterTransferCaches(userVisit).getFilterAdjustmentPercentTransferCache();
        
        filterAdjustmentPercents.forEach((filterAdjustmentPercent) ->
                filterAdjustmentPercentTransfers.add(filterAdjustmentPercentTransferCache.getTransfer(filterAdjustmentPercent))
        );
        
        return filterAdjustmentPercentTransfers;
    }
    
    public void updateFilterAdjustmentPercentFromValue(FilterAdjustmentPercentValue filterAdjustmentPercentValue, BasePK updatedBy) {
        if(filterAdjustmentPercentValue.hasBeenModified()) {
            FilterAdjustmentPercent filterAdjustmentPercent = FilterAdjustmentPercentFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentPercentValue.getPrimaryKey());
            
            filterAdjustmentPercent.setThruTime(session.START_TIME_LONG);
            filterAdjustmentPercent.store();
            
            FilterAdjustmentPK filterAdjustmentPK = filterAdjustmentPercent.getFilterAdjustmentPK(); // Not updated
            UnitOfMeasureTypePK unitOfMeasureTypePK = filterAdjustmentPercent.getUnitOfMeasureTypePK(); // Not updated
            CurrencyPK currencyPK = filterAdjustmentPercent.getCurrencyPK(); // Not updated
            Integer percent = filterAdjustmentPercentValue.getPercent();
            
            filterAdjustmentPercent = FilterAdjustmentPercentFactory.getInstance().create(filterAdjustmentPK,
                    unitOfMeasureTypePK, currencyPK, percent, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(filterAdjustmentPK, EventTypes.MODIFY.name(),
                    filterAdjustmentPercent.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteFilterAdjustmentPercent(FilterAdjustmentPercent filterAdjustmentPercent, BasePK deletedBy) {
        filterAdjustmentPercent.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(filterAdjustmentPercent.getFilterAdjustment().getPrimaryKey(), EventTypes.MODIFY.name(),
                filterAdjustmentPercent.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteFilterAdjustmentPercentsByFilterAdjustment(FilterAdjustment filterAdjustment, BasePK deletedBy) {
        List<FilterAdjustmentPercent> filterAdjustmentPercents = getFilterAdjustmentPercentsForUpdate(filterAdjustment);
        
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
        FilterAdjustmentDescription filterAdjustmentDescription = FilterAdjustmentDescriptionFactory.getInstance().create(session,
                filterAdjustment, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY.name(),
                filterAdjustmentDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = FilterAdjustmentDescriptionFactory.getInstance().prepareStatement(query);
            
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
        FilterAdjustmentDescription filterAdjustmentDescription = getFilterAdjustmentDescriptionForUpdate(filterAdjustment,
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
            
            PreparedStatement ps = FilterAdjustmentDescriptionFactory.getInstance().prepareStatement(query);
            
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
        FilterAdjustmentDescription filterAdjustmentDescription = getFilterAdjustmentDescription(filterAdjustment, language);
        
        if(filterAdjustmentDescription == null && !language.getIsDefault()) {
            filterAdjustmentDescription = getFilterAdjustmentDescription(filterAdjustment, getPartyControl().getDefaultLanguage());
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
        return getFilterTransferCaches(userVisit).getFilterAdjustmentDescriptionTransferCache().getTransfer(filterAdjustmentDescription);
    }
    
    public List<FilterAdjustmentDescriptionTransfer> getFilterAdjustmentDescriptionTransfers(UserVisit userVisit, FilterAdjustment filterAdjustment) {
        List<FilterAdjustmentDescription> filterAdjustmentDescriptions = getFilterAdjustmentDescriptions(filterAdjustment);
        List<FilterAdjustmentDescriptionTransfer> filterAdjustmentDescriptionTransfers = new ArrayList<>(filterAdjustmentDescriptions.size());
        FilterAdjustmentDescriptionTransferCache filterAdjustmentDescriptionTransferCache = getFilterTransferCaches(userVisit).getFilterAdjustmentDescriptionTransferCache();
        
        filterAdjustmentDescriptions.forEach((filterAdjustmentDescription) ->
                filterAdjustmentDescriptionTransfers.add(filterAdjustmentDescriptionTransferCache.getTransfer(filterAdjustmentDescription))
        );
        
        return filterAdjustmentDescriptionTransfers;
    }
    
    public void updateFilterAdjustmentDescriptionFromValue(FilterAdjustmentDescriptionValue filterAdjustmentDescriptionValue,
            BasePK updatedBy) {
        if(filterAdjustmentDescriptionValue.hasBeenModified()) {
            FilterAdjustmentDescription filterAdjustmentDescription = FilterAdjustmentDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentDescriptionValue.getPrimaryKey());
            
            filterAdjustmentDescription.setThruTime(session.START_TIME_LONG);
            filterAdjustmentDescription.store();
            
            FilterAdjustment filterAdjustment = filterAdjustmentDescription.getFilterAdjustment();
            Language language = filterAdjustmentDescription.getLanguage();
            String description = filterAdjustmentDescriptionValue.getDescription();
            
            filterAdjustmentDescription = FilterAdjustmentDescriptionFactory.getInstance().create(filterAdjustment,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY.name(),
                    filterAdjustmentDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteFilterAdjustmentDescription(FilterAdjustmentDescription filterAdjustmentDescription, BasePK deletedBy) {
        filterAdjustmentDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(filterAdjustmentDescription.getFilterAdjustmentPK(), EventTypes.MODIFY.name(),
                filterAdjustmentDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteFilterAdjustmentDescriptionsByFilterAdjustment(FilterAdjustment filterAdjustment, BasePK deletedBy) {
        List<FilterAdjustmentDescription> filterAdjustmentDescriptions = getFilterAdjustmentDescriptionsForUpdate(filterAdjustment);
        
        filterAdjustmentDescriptions.forEach((filterAdjustmentDescription) -> 
                deleteFilterAdjustmentDescription(filterAdjustmentDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filters
    // --------------------------------------------------------------------------------
    
    public Filter createFilter(FilterType filterType, String filterName, FilterAdjustment initialFilterAdjustment,
            Selector filterItemSelector, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        Filter defaultFilter = getDefaultFilter(filterType);
        boolean defaultFound = defaultFilter != null;
        
        if(defaultFound && isDefault) {
            FilterDetailValue defaultFilterDetailValue = getDefaultFilterDetailValueForUpdate(filterType);
            
            defaultFilterDetailValue.setIsDefault(Boolean.FALSE);
            updateFilterFromValue(defaultFilterDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = Boolean.TRUE;
        }
        
        Filter filter = FilterFactory.getInstance().create();
        FilterDetail filterDetail = FilterDetailFactory.getInstance().create(filter, filterType, filterName,
                initialFilterAdjustment, filterItemSelector, isDefault, sortOrder, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        filter = FilterFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, filter.getPrimaryKey());
        filter.setActiveDetail(filterDetail);
        filter.setLastDetail(filterDetail);
        filter.store();
        
        sendEventUsingNames(filter.getPrimaryKey(), EventTypes.CREATE.name(), null, null, createdBy);
        
        return filter;
    }
    
    private List<Filter> getFilters(FilterType filterType, EntityPermission entityPermission) {
        List<Filter> filters;
        
        try {
            PreparedStatement ps = FilterFactory.getInstance().prepareStatement(
                    "SELECT _ALL_ " +
                    "FROM filters, filterdetails " +
                    "WHERE flt_activedetailid = fltdt_filterdetailid AND fltdt_flttyp_filtertypeid = ?");
            
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
    
    private Filter getDefaultFilter(FilterType filterType, EntityPermission entityPermission) {
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
            
            PreparedStatement ps = FilterFactory.getInstance().prepareStatement(query);
            
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
    
    private Filter getFilterByName(FilterType filterType, String filterName, EntityPermission entityPermission) {
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
            
            PreparedStatement ps = FilterFactory.getInstance().prepareStatement(query);
            
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
        List<Filter> filters = getFilters(filterType);
        int size = filters.size() + (allowNullChoice? 1: 0);
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultFilterChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var filter : filters) {
            FilterDetail filterDetail = filter.getLastDetail();
            String label = getBestFilterDescription(filter, language);
            String value = filterDetail.getFilterName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultFilterChoice != null && defaultFilterChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && filterDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new FilterChoicesBean(labels, values, defaultValue);
    }
    
    public FilterTransfer getFilterTransfer(UserVisit userVisit, Filter filter) {
        return getFilterTransferCaches(userVisit).getFilterTransferCache().getTransfer(filter);
    }
    
    public List<FilterTransfer> getFilterTransfers(UserVisit userVisit, List<Filter> filters) {
        List<FilterTransfer> filterTransfers = new ArrayList<>(filters.size());
        FilterTransferCache filterTransferCache = getFilterTransferCaches(userVisit).getFilterTransferCache();
        
        filters.forEach((filter) ->
                filterTransfers.add(filterTransferCache.getTransfer(filter))
        );
        
        return filterTransfers;
    }
    
    public List<FilterTransfer> getFilterTransfers(UserVisit userVisit, FilterType filterType) {
        return getFilterTransfers(userVisit, getFilters(filterType));
    }
    
    public long countFiltersBySelector(Selector selector) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM filterdetails " +
                "WHERE fltdt_filteritemselectorid = ? AND fltdt_thrutime = ?",
                selector, Session.MAX_TIME);
    }
    
    public long countFiltersByFilterAdjustment(FilterAdjustment filterAdjustment) {
        return session.queryForLong(
                "SELECT COUNT(*) " +
                "FROM filterdetails " +
                "WHERE fltdt_initialfilteradjustmentid = ? AND fltdt_thrutime = ?",
                filterAdjustment, Session.MAX_TIME);
    }
    
    private void updateFilterFromValue(FilterDetailValue filterDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(filterDetailValue.hasBeenModified()) {
            Filter filter = FilterFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterDetailValue.getFilterPK());
            FilterDetail filterDetail = filter.getActiveDetailForUpdate();
            
            filterDetail.setThruTime(session.START_TIME_LONG);
            filterDetail.store();
            
            FilterPK filterPK = filterDetail.getFilterPK();
            FilterType filterType = filterDetail.getFilterType();
            FilterTypePK filterTypePK = filterType.getPrimaryKey();
            String filterName = filterDetailValue.getFilterName();
            FilterAdjustmentPK initialFilterAdjustmentPK = filterDetailValue.getInitialFilterAdjustmentPK();
            SelectorPK filterItemSelectorPK = filterDetailValue.getFilterItemSelectorPK();
            Boolean isDefault = filterDetailValue.getIsDefault();
            Integer sortOrder = filterDetailValue.getSortOrder();
            
            if(checkDefault) {
                Filter defaultFilter = getDefaultFilter(filterType);
                boolean defaultFound = defaultFilter != null && !defaultFilter.equals(filter);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    FilterDetailValue defaultFilterDetailValue = getDefaultFilterDetailValueForUpdate(filterType);
                    
                    defaultFilterDetailValue.setIsDefault(Boolean.FALSE);
                    updateFilterFromValue(defaultFilterDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = Boolean.TRUE;
                }
            }
            
            filterDetail = FilterDetailFactory.getInstance().create(filterPK, filterTypePK, filterName,
                    initialFilterAdjustmentPK, filterItemSelectorPK,  isDefault, sortOrder, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            filter.setActiveDetail(filterDetail);
            filter.setLastDetail(filterDetail);
            
            sendEventUsingNames(filterPK, EventTypes.MODIFY.name(), null, null, updatedBy);
        }
    }
    
    public void updateFilterFromValue(FilterDetailValue filterDetailValue, BasePK updatedBy) {
        updateFilterFromValue(filterDetailValue, true, updatedBy);
    }
    
    public void deleteFilter(Filter filter, BasePK deletedBy) {
        deleteFilterStepsByFilter(filter, deletedBy);
        deleteFilterDescriptionsByFilter(filter, deletedBy);
        
        FilterDetail filterDetail = filter.getLastDetailForUpdate();
        filterDetail.setThruTime(session.START_TIME_LONG);
        filter.setActiveDetail(null);
        filter.store();
        
        // Check for default, and pick one if necessary
        FilterType filterType = filterDetail.getFilterType();
        Filter defaultFilter = getDefaultFilter(filterType);
        if(defaultFilter == null) {
            List<Filter> Filters = getFiltersForUpdate(filterType);
            
            if(!Filters.isEmpty()) {
                Iterator<Filter> iter = Filters.iterator();
                if(iter.hasNext()) {
                    defaultFilter = iter.next();
                }
                FilterDetailValue filterDetailValue = Objects.requireNonNull(defaultFilter).getLastDetailForUpdate().getFilterDetailValue().clone();
                
                filterDetailValue.setIsDefault(Boolean.TRUE);
                updateFilterFromValue(filterDetailValue, false, deletedBy);
            }
        }
        
        sendEventUsingNames(filter.getPrimaryKey(), EventTypes.DELETE.name(), null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Descriptions
    // --------------------------------------------------------------------------------
    
    public FilterDescription createFilterDescription(Filter filter, Language language, String description, BasePK createdBy) {
        FilterDescription filterDescription = FilterDescriptionFactory.getInstance().create(filter, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(filter.getPrimaryKey(), EventTypes.MODIFY.name(), filterDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = FilterDescriptionFactory.getInstance().prepareStatement(query);
            
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
        FilterDescription filterDescription = getFilterDescriptionForUpdate(filter, language);
        
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
            
            PreparedStatement ps = FilterDescriptionFactory.getInstance().prepareStatement(query);
            
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
        FilterDescription filterDescription = getFilterDescription(filter, language);
        
        if(filterDescription == null && !language.getIsDefault()) {
            filterDescription = getFilterDescription(filter, getPartyControl().getDefaultLanguage());
        }
        
        if(filterDescription == null) {
            description = filter.getLastDetail().getFilterName();
        } else {
            description = filterDescription.getDescription();
        }
        
        return description;
    }
    
    public FilterDescriptionTransfer getFilterDescriptionTransfer(UserVisit userVisit, FilterDescription filterDescription) {
        return getFilterTransferCaches(userVisit).getFilterDescriptionTransferCache().getTransfer(filterDescription);
    }
    
    public List<FilterDescriptionTransfer> getFilterDescriptionTransfers(UserVisit userVisit, Filter filter) {
        List<FilterDescription> filterDescriptions = getFilterDescriptions(filter);
        List<FilterDescriptionTransfer> filterDescriptionTransfers = new ArrayList<>(filterDescriptions.size());
        FilterDescriptionTransferCache filterDescriptionTransferCache = getFilterTransferCaches(userVisit).getFilterDescriptionTransferCache();
        
        filterDescriptions.forEach((filterDescription) ->
                filterDescriptionTransfers.add(filterDescriptionTransferCache.getTransfer(filterDescription))
        );
        
        return filterDescriptionTransfers;
    }
    
    public void updateFilterDescriptionFromValue(FilterDescriptionValue filterDescriptionValue, BasePK updatedBy) {
        if(filterDescriptionValue.hasBeenModified()) {
            FilterDescription filterDescription = FilterDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, filterDescriptionValue.getPrimaryKey());
            
            filterDescription.setThruTime(session.START_TIME_LONG);
            filterDescription.store();
            
            Filter filter = filterDescription.getFilter();
            Language language = filterDescription.getLanguage();
            String description = filterDescriptionValue.getDescription();
            
            filterDescription = FilterDescriptionFactory.getInstance().create(filter, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(filter.getPrimaryKey(), EventTypes.MODIFY.name(), filterDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteFilterDescription(FilterDescription filterDescription, BasePK deletedBy) {
        filterDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(filterDescription.getFilterPK(), EventTypes.MODIFY.name(), filterDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteFilterDescriptionsByFilter(Filter filter, BasePK deletedBy) {
        List<FilterDescription> filterDescriptions = getFilterDescriptionsForUpdate(filter);
        
        filterDescriptions.forEach((filterDescription) -> 
                deleteFilterDescription(filterDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Steps
    // --------------------------------------------------------------------------------
    
    public FilterStep createFilterStep(Filter filter, String filterStepName, Selector filterItemSelector, BasePK createdBy) {
        FilterStep filterStep = FilterStepFactory.getInstance().create();
        FilterStepDetail filterStepDetail = FilterStepDetailFactory.getInstance().create(filterStep, filter, filterStepName, filterItemSelector, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        // Convert to R/W
        filterStep = FilterStepFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, filterStep.getPrimaryKey());
        filterStep.setActiveDetail(filterStepDetail);
        filterStep.setLastDetail(filterStepDetail);
        filterStep.store();
        
        sendEventUsingNames(filter.getPrimaryKey(), EventTypes.MODIFY.name(), filterStep.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
        return filterStep;
    }
    
    private FilterStep getFilterStepByName(Filter filter, String filterStepName, EntityPermission entityPermission) {
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
            
            PreparedStatement ps = FilterStepFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = FilterStepFactory.getInstance().prepareStatement(query);
            
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
        List<FilterStep> filterSteps = getFilterStepsByFilter(filter);
        int size = filterSteps.size() + (allowNullChoice? 1: 0);
        List<String> labels = new ArrayList<>(size);
        List<String> values = new ArrayList<>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultFilterStepChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var filterStep : filterSteps) {
            FilterStepDetail filterStepDetail = filterStep.getLastDetail();
            String label = getBestFilterStepDescription(filterStep, language);
            String value = filterStepDetail.getFilterStepName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            boolean usingDefaultChoice = defaultFilterStepChoice != null && defaultFilterStepChoice.equals(value);
            if(usingDefaultChoice || defaultValue == null) {
                defaultValue = value;
            }
        }
        
        return new FilterStepChoicesBean(labels, values, defaultValue);
    }
    
    public List<FilterStepTransfer> getFilterStepTransfers(UserVisit userVisit, List<FilterStep> filterSteps) {
        List<FilterStepTransfer> filterStepTransfers = new ArrayList<>(filterSteps.size());
        FilterStepTransferCache filterStepTransferCache = getFilterTransferCaches(userVisit).getFilterStepTransferCache();
        
        filterSteps.forEach((filterStep) ->
                filterStepTransfers.add(filterStepTransferCache.getTransfer(filterStep))
        );
        
        return filterStepTransfers;
    }
    
    public List<FilterStepTransfer> getFilterStepTransfersByFilter(UserVisit userVisit, Filter filter) {
        return getFilterStepTransfers(userVisit, getFilterStepsByFilter(filter));
    }
    
    public FilterStepTransfer getFilterStepTransfer(UserVisit userVisit, FilterStep filterStep) {
        return getFilterTransferCaches(userVisit).getFilterStepTransferCache().getTransfer(filterStep);
    }
    
    public void updateFilterStepFromValue(FilterStepDetailValue filterStepDetailValue, BasePK updatedBy) {
        if(filterStepDetailValue.hasBeenModified()) {
            FilterStep filterStep = FilterStepFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterStepDetailValue.getFilterStepPK());
            FilterStepDetail filterStepDetail = filterStep.getActiveDetailForUpdate();
            
            filterStepDetail.setThruTime(session.START_TIME_LONG);
            filterStepDetail.store();
            
            FilterStepPK filterStepPK = filterStepDetail.getFilterStepPK();
            Filter filter = filterStepDetail.getFilter();
            FilterPK filterPK = filter.getPrimaryKey();
            String filterStepName = filterStepDetailValue.getFilterStepName();
            SelectorPK filterItemSelectorPK = filterStepDetailValue.getFilterItemSelectorPK();
            
            filterStepDetail = FilterStepDetailFactory.getInstance().create(filterStepPK, filterPK, filterStepName,
                    filterItemSelectorPK, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            filterStep.setActiveDetail(filterStepDetail);
            filterStep.setLastDetail(filterStepDetail);
            
            sendEventUsingNames(filterPK, EventTypes.MODIFY.name(), filterStepPK, EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteFilterStep(FilterStep filterStep, BasePK deletedBy) {
        deleteFilterStepElementsByFilterStep(filterStep, deletedBy);
        deleteFilterStepDestinationsByFilterStep(filterStep, deletedBy);
        deleteFilterEntranceStepsByFilterStep(filterStep, deletedBy);
        deleteFilterStepDescriptionsByFilterStep(filterStep, deletedBy);
        
        FilterStepDetail filterStepDetail = filterStep.getLastDetailForUpdate();
        filterStepDetail.setThruTime(session.START_TIME_LONG);
        filterStep.setActiveDetail(null);
        filterStep.store();
        
        sendEventUsingNames(filterStepDetail.getFilter().getPrimaryKey(), EventTypes.MODIFY.name(),
                filterStep.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteFilterStepsByFilter(Filter filter, BasePK deletedBy) {
        List<FilterStep> filterSteps = getFilterStepsByFilterForUpdate(filter);
        
        filterSteps.forEach((filterStep) -> 
                deleteFilterStep(filterStep, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Step Descriptions
    // --------------------------------------------------------------------------------
    
    public FilterStepDescription createFilterStepDescription(FilterStep filterStep, Language language, String description, BasePK createdBy) {
        FilterStepDescription filterStepDescription = FilterStepDescriptionFactory.getInstance().create(filterStep, language,
                description, session.START_TIME_LONG,
                Session.MAX_TIME_LONG);
        
        sendEventUsingNames(filterStep.getLastDetail().getFilter().getPrimaryKey(), EventTypes.MODIFY.name(),
                filterStepDescription.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = FilterStepDescriptionFactory.getInstance().prepareStatement(query);
            
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
        FilterStepDescription filterStepDescription = getFilterStepDescriptionForUpdate(filterStep, language);
        
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
            
            PreparedStatement ps = FilterStepDescriptionFactory.getInstance().prepareStatement(query);
            
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
        FilterStepDescription filterStepDescription = getFilterStepDescription(filterStep, language);
        
        if(filterStepDescription == null && !language.getIsDefault()) {
            filterStepDescription = getFilterStepDescription(filterStep, getPartyControl().getDefaultLanguage());
        }
        
        if(filterStepDescription == null) {
            description = filterStep.getLastDetail().getFilterStepName();
        } else {
            description = filterStepDescription.getDescription();
        }
        
        return description;
    }
    
    public FilterStepDescriptionTransfer getFilterStepDescriptionTransfer(UserVisit userVisit, FilterStepDescription filterStepDescription) {
        return getFilterTransferCaches(userVisit).getFilterStepDescriptionTransferCache().getTransfer(filterStepDescription);
    }
    
    public List<FilterStepDescriptionTransfer> getFilterStepDescriptionTransfers(UserVisit userVisit, FilterStep filterStep) {
        List<FilterStepDescription> filterStepDescriptions = getFilterStepDescriptions(filterStep);
        List<FilterStepDescriptionTransfer> filterStepDescriptionTransfers = new ArrayList<>(filterStepDescriptions.size());
        FilterStepDescriptionTransferCache filterStepDescriptionTransferCache = getFilterTransferCaches(userVisit).getFilterStepDescriptionTransferCache();
        
        filterStepDescriptions.forEach((filterStepDescription) ->
                filterStepDescriptionTransfers.add(filterStepDescriptionTransferCache.getTransfer(filterStepDescription))
        );
        
        return filterStepDescriptionTransfers;
    }
    
    public void updateFilterStepDescriptionFromValue(FilterStepDescriptionValue filterStepDescriptionValue, BasePK updatedBy) {
        if(filterStepDescriptionValue.hasBeenModified()) {
            FilterStepDescription filterStepDescription = FilterStepDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterStepDescriptionValue.getPrimaryKey());
            
            filterStepDescription.setThruTime(session.START_TIME_LONG);
            filterStepDescription.store();
            
            FilterStep filterStep = filterStepDescription.getFilterStep();
            Language language = filterStepDescription.getLanguage();
            String description = filterStepDescriptionValue.getDescription();
            
            filterStepDescription = FilterStepDescriptionFactory.getInstance().create(filterStep, language, description,
                    session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(filterStep.getLastDetail().getFilter().getPrimaryKey(), EventTypes.MODIFY.name(), filterStepDescription.getPrimaryKey(),
                    EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteFilterStepDescription(FilterStepDescription filterStepDescription, BasePK deletedBy) {
        filterStepDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(filterStepDescription.getFilterStep().getLastDetail().getFilter().getPrimaryKey(), EventTypes.MODIFY.name(),
                filterStepDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteFilterStepDescriptionsByFilterStep(FilterStep filterStep, BasePK deletedBy) {
        List<FilterStepDescription> filterStepDescriptions = getFilterStepDescriptionsForUpdate(filterStep);
        
        filterStepDescriptions.forEach((filterStepDescription) -> 
                deleteFilterStepDescription(filterStepDescription, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Entrance Steps
    // --------------------------------------------------------------------------------
    
    public FilterEntranceStep createFilterEntranceStep(Filter filter, FilterStep filterStep, BasePK createdBy) {
        FilterEntranceStep filterEntranceStep = FilterEntranceStepFactory.getInstance().create(filter, filterStep, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(filter.getPrimaryKey(), EventTypes.MODIFY.name(), filterEntranceStep.getPrimaryKey(),
                EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = FilterEntranceStepFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = FilterEntranceStepFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = FilterEntranceStepFactory.getInstance().prepareStatement(query);
            
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
    
    public List<FilterEntranceStepTransfer> getFilterEntranceStepTransfers(UserVisit userVisit, List<FilterEntranceStep> filterEntranceSteps) {
        List<FilterEntranceStepTransfer> filterEntranceStepTransfers = new ArrayList<>(filterEntranceSteps.size());
        FilterEntranceStepTransferCache filterEntranceStepTransferCache = getFilterTransferCaches(userVisit).getFilterEntranceStepTransferCache();
        
        filterEntranceSteps.forEach((filterEntranceStep) ->
                filterEntranceStepTransfers.add(filterEntranceStepTransferCache.getTransfer(filterEntranceStep))
        );
        
        return filterEntranceStepTransfers;
    }
    
    public List<FilterEntranceStepTransfer> getFilterEntranceStepTransfersByFilter(UserVisit userVisit, Filter filter) {
        return getFilterEntranceStepTransfers(userVisit, getFilterEntranceStepsByFilter(filter));
    }
    
    public FilterEntranceStepTransfer getFilterEntranceStepTransfer(UserVisit userVisit, FilterEntranceStep filterEntranceStep) {
        return getFilterTransferCaches(userVisit).getFilterEntranceStepTransferCache().getTransfer(filterEntranceStep);
    }
    
    public void deleteFilterEntranceStep(FilterEntranceStep filterEntranceStep, BasePK deletedBy) {
        filterEntranceStep.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(filterEntranceStep.getFilter().getPrimaryKey(), EventTypes.MODIFY.name(),
                filterEntranceStep.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteFilterEntranceStepsByFilterStep(FilterStep filterStep, BasePK deletedBy) {
        List<FilterEntranceStep> filterEntranceSteps = getFilterEntranceStepsByFilterStepForUpdate(filterStep);
        
        filterEntranceSteps.forEach((filterEntranceStep) -> 
                deleteFilterEntranceStep(filterEntranceStep, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Step Destinations
    // --------------------------------------------------------------------------------
    
    public FilterStepDestination createFilterStepDestination(FilterStep fromFilterStep, FilterStep toFilterStep, BasePK createdBy) {
        FilterStepDestination filterStepDestination = FilterStepDestinationFactory.getInstance().create(fromFilterStep,
                toFilterStep, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(fromFilterStep.getLastDetail().getFilter().getPrimaryKey(), EventTypes.MODIFY.name(),
                filterStepDestination.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = FilterStepDestinationFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = FilterStepDestinationFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = FilterStepDestinationFactory.getInstance().prepareStatement(query);
            
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
    
    public int countFilterStepDestinationsByFromFilterStep(FilterStep fromFilterStep) {
        return session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM filterstepdestinations " +
                "WHERE fltstpdn_fromfilterstepid = ? AND fltstpdn_thrutime = ?",
                fromFilterStep, Session.MAX_TIME);
    }
    
    public int countFilterStepDestinationsByToFilterStep(FilterStep toFilterStep) {
        return session.queryForInteger(
                "SELECT COUNT(*) " +
                "FROM filterstepdestinations " +
                "WHERE fltstpdn_tofilterstepid = ? AND fltstpdn_thrutime = ?",
                toFilterStep, Session.MAX_TIME);
    }
    
    public List<FilterStepDestinationTransfer> getFilterStepDestinationTransfers(UserVisit userVisit, List<FilterStepDestination> filterStepDestinations) {
        List<FilterStepDestinationTransfer> filterStepDestinationTransfers = new ArrayList<>(filterStepDestinations.size());
        FilterStepDestinationTransferCache filterStepDestinationTransferCache = getFilterTransferCaches(userVisit).getFilterStepDestinationTransferCache();
        
        filterStepDestinations.forEach((filterStepDestination) ->
                filterStepDestinationTransfers.add(filterStepDestinationTransferCache.getTransfer(filterStepDestination))
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
        return getFilterTransferCaches(userVisit).getFilterStepDestinationTransferCache().getTransfer(filterStepDestination);
    }
    
    public void deleteFilterStepDestination(FilterStepDestination filterStepDestination, BasePK deletedBy) {
        filterStepDestination.setThruTime(session.START_TIME_LONG);
        filterStepDestination.store();
        
        sendEventUsingNames(filterStepDestination.getFromFilterStep().getLastDetail().getFilter().getPrimaryKey(),
                EventTypes.MODIFY.name(), filterStepDestination.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteFilterStepDestinationsByFromFilterStep(FilterStep fromFilterStep, BasePK deletedBy) {
        List<FilterStepDestination> filterStepDestinations = getFilterStepDestinationsByFromFilterStepForUpdate(fromFilterStep);
        
        filterStepDestinations.forEach((filterStepDestination) -> 
                deleteFilterStepDestination(filterStepDestination, deletedBy)
        );
    }
    
    public void deleteFilterStepDestinationsByToFilterStep(FilterStep toFilterStep, BasePK deletedBy) {
        List<FilterStepDestination> filterStepDestinations = getFilterStepDestinationsByToFilterStepForUpdate(toFilterStep);
        
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
        FilterStepElement filterStepElement = FilterStepElementFactory.getInstance().create();
        FilterStepElementDetail filterStepElementDetail = FilterStepElementDetailFactory.getInstance().create(filterStepElement, filterStep,
                filterStepElementName, filterItemSelector, filterAdjustment, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        // Convert to R/W
        filterStepElement = FilterStepElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, filterStepElement.getPrimaryKey());
        filterStepElement.setActiveDetail(filterStepElementDetail);
        filterStepElement.setLastDetail(filterStepElementDetail);
        filterStepElement.store();
        
        sendEventUsingNames(filterStep.getLastDetail().getFilter().getPrimaryKey(), EventTypes.MODIFY.name(),
                filterStepElement.getPrimaryKey(), EventTypes.CREATE.name(), createdBy);
        
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
            
            PreparedStatement ps = FilterStepElementFactory.getInstance().prepareStatement(query);
            
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
            
            PreparedStatement ps = FilterStepElementFactory.getInstance().prepareStatement(query);
            
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
    
    public List<FilterStepElementTransfer> getFilterStepElementTransfers(UserVisit userVisit, List<FilterStepElement> filterStepElements) {
        List<FilterStepElementTransfer> filterStepElementTransfers = new ArrayList<>(filterStepElements.size());
        FilterStepElementTransferCache filterStepElementTransferCache = getFilterTransferCaches(userVisit).getFilterStepElementTransferCache();
        
        filterStepElements.forEach((filterStepElement) ->
                filterStepElementTransfers.add(filterStepElementTransferCache.getTransfer(filterStepElement))
        );
        
        return filterStepElementTransfers;
    }
    
    public List<FilterStepElementTransfer> getFilterStepElementTransfersByFilterStep(UserVisit userVisit, FilterStep filterStep) {
        return getFilterStepElementTransfers(userVisit, getFilterStepElementsByFilterStep(filterStep));
    }
    
    public FilterStepElementTransfer getFilterStepElementTransfer(UserVisit userVisit, FilterStepElement filterStepElement) {
        return getFilterTransferCaches(userVisit).getFilterStepElementTransferCache().getTransfer(filterStepElement);
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
            FilterStepElement filterStepElement = FilterStepElementFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE,
                     filterStepElementDetailValue.getFilterStepElementPK());
            FilterStepElementDetail filterStepElementDetail = filterStepElement.getActiveDetailForUpdate();
            
            filterStepElementDetail.setThruTime(session.START_TIME_LONG);
            filterStepElementDetail.store();
            
            FilterStepElementPK filterStepElementPK = filterStepElementDetail.getFilterStepElementPK();
            FilterStep filterStep = filterStepElementDetail.getFilterStep();
            FilterStepPK filterStepPK = filterStep.getPrimaryKey();
            String filterStepElementName = filterStepElementDetailValue.getFilterStepElementName();
            SelectorPK filterItemSelectorPK = filterStepElementDetailValue.getFilterItemSelectorPK();
            FilterAdjustmentPK filterAdjustmentPK = filterStepElementDetailValue.getFilterAdjustmentPK();
            
            filterStepElementDetail = FilterStepElementDetailFactory.getInstance().create(filterStepElementPK,
                    filterStepPK, filterStepElementName, filterItemSelectorPK, filterAdjustmentPK, session.START_TIME_LONG,
                    Session.MAX_TIME_LONG);
            
            filterStepElement.setActiveDetail(filterStepElementDetail);
            filterStepElement.setLastDetail(filterStepElementDetail);
            
            sendEventUsingNames(filterStep.getLastDetail().getFilter().getPrimaryKey(), EventTypes.MODIFY.name(),
                    filterStepElementPK, EventTypes.DELETE.name(), updatedBy);
        }
    }
    
    public void deleteFilterStepElement(FilterStepElement filterStepElement, BasePK deletedBy) {
        deleteFilterStepElementDescriptionsByFilterStepElement(filterStepElement, deletedBy);
        
        FilterStepElementDetail filterStepElementDetail = filterStepElement.getLastDetailForUpdate();
        filterStepElementDetail.setThruTime(session.START_TIME_LONG);
        filterStepElement.setActiveDetail(null);
        filterStepElement.store();
        
        sendEventUsingNames(filterStepElementDetail.getFilterStep().getLastDetail().getFilter().getPrimaryKey(),
                EventTypes.MODIFY.name(), filterStepElement.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
    }
    
    public void deleteFilterStepElementsByFilterStep(FilterStep filterStep, BasePK deletedBy) {
        List<FilterStepElement> filterStepElements = getFilterStepElementsByFilterStepForUpdate(filterStep);
        
        filterStepElements.forEach((filterStepElement) -> 
                deleteFilterStepElement(filterStepElement, deletedBy)
        );
    }
    
    // --------------------------------------------------------------------------------
    //   Filter Step Element Descriptions
    // --------------------------------------------------------------------------------
    
    public FilterStepElementDescription createFilterStepElementDescription(FilterStepElement filterStepElement, Language language,
            String description, BasePK createdBy) {
        FilterStepElementDescription filterStepElementDescription = FilterStepElementDescriptionFactory.getInstance().create(session,
                filterStepElement, language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
        
        sendEventUsingNames(filterStepElement.getLastDetail().getFilterStep().getLastDetail().getFilterPK(),
                EventTypes.MODIFY.name(), filterStepElementDescription.getPrimaryKey(), EventTypes.CREATE.name(),
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
            
            PreparedStatement ps = FilterStepElementDescriptionFactory.getInstance().prepareStatement(query);
            
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
        FilterStepElementDescription filterStepElementDescription = getFilterStepElementDescriptionForUpdate(filterStepElement, language);
        
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
            
            PreparedStatement ps = FilterStepElementDescriptionFactory.getInstance().prepareStatement(query);
            
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
        FilterStepElementDescription filterStepElementDescription = getFilterStepElementDescription(filterStepElement, language);
        
        if(filterStepElementDescription == null && !language.getIsDefault()) {
            filterStepElementDescription = getFilterStepElementDescription(filterStepElement, getPartyControl().getDefaultLanguage());
        }
        
        if(filterStepElementDescription == null) {
            description = filterStepElement.getLastDetail().getFilterStepElementName();
        } else {
            description = filterStepElementDescription.getDescription();
        }
        
        return description;
    }
    
    public FilterStepElementDescriptionTransfer getFilterStepElementDescriptionTransfer(UserVisit userVisit, FilterStepElementDescription filterStepElementDescription) {
        return getFilterTransferCaches(userVisit).getFilterStepElementDescriptionTransferCache().getTransfer(filterStepElementDescription);
    }
    
    public List<FilterStepElementDescriptionTransfer> getFilterStepElementDescriptionTransfers(UserVisit userVisit, FilterStepElement filterStepElement) {
        List<FilterStepElementDescription> filterStepElementDescriptions = getFilterStepElementDescriptions(filterStepElement);
        List<FilterStepElementDescriptionTransfer> filterStepElementDescriptionTransfers = new ArrayList<>(filterStepElementDescriptions.size());
        FilterStepElementDescriptionTransferCache filterStepElementDescriptionTransferCache = getFilterTransferCaches(userVisit).getFilterStepElementDescriptionTransferCache();
        
        filterStepElementDescriptions.forEach((filterStepElementDescription) ->
                filterStepElementDescriptionTransfers.add(filterStepElementDescriptionTransferCache.getTransfer(filterStepElementDescription))
        );
        
        return filterStepElementDescriptionTransfers;
    }
    
    public void updateFilterStepElementDescriptionFromValue(FilterStepElementDescriptionValue filterStepElementDescriptionValue, BasePK updatedBy) {
        if(filterStepElementDescriptionValue.hasBeenModified()) {
            FilterStepElementDescription filterStepElementDescription = FilterStepElementDescriptionFactory.getInstance().getEntityFromPK(EntityPermission.READ_WRITE, filterStepElementDescriptionValue.getPrimaryKey());
            
            filterStepElementDescription.setThruTime(session.START_TIME_LONG);
            filterStepElementDescription.store();
            
            FilterStepElement filterStepElement = filterStepElementDescription.getFilterStepElement();
            Language language = filterStepElementDescription.getLanguage();
            String description = filterStepElementDescriptionValue.getDescription();
            
            filterStepElementDescription = FilterStepElementDescriptionFactory.getInstance().create(filterStepElement,
                    language, description, session.START_TIME_LONG, Session.MAX_TIME_LONG);
            
            sendEventUsingNames(filterStepElement.getLastDetail().getFilterStep().getLastDetail().getFilterPK(), EventTypes.MODIFY.name(), filterStepElementDescription.getPrimaryKey(), EventTypes.MODIFY.name(), updatedBy);
        }
    }
    
    public void deleteFilterStepElementDescription(FilterStepElementDescription filterStepElementDescription, BasePK deletedBy) {
        filterStepElementDescription.setThruTime(session.START_TIME_LONG);
        
        sendEventUsingNames(filterStepElementDescription.getFilterStepElement().getLastDetail().getFilterStep().getLastDetail().getFilterPK(), EventTypes.MODIFY.name(), filterStepElementDescription.getPrimaryKey(), EventTypes.DELETE.name(), deletedBy);
        
    }
    
    public void deleteFilterStepElementDescriptionsByFilterStepElement(FilterStepElement filterStepElement, BasePK deletedBy) {
        List<FilterStepElementDescription> filterStepElementDescriptions = getFilterStepElementDescriptionsForUpdate(filterStepElement);
        
        filterStepElementDescriptions.forEach((filterStepElementDescription) -> 
                deleteFilterStepElementDescription(filterStepElementDescription, deletedBy)
        );
    }
    
}
