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
import com.echothree.model.control.filter.common.choice.FilterAdjustmentChoicesBean;
import com.echothree.model.control.filter.common.choice.FilterAdjustmentSourceChoicesBean;
import com.echothree.model.control.filter.common.choice.FilterAdjustmentTypeChoicesBean;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentAmountTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentFixedAmountTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentPercentTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentSourceTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentTransfer;
import com.echothree.model.control.filter.common.transfer.FilterAdjustmentTypeTransfer;
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentAmountTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentFixedAmountTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentPercentTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentSourceTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterAdjustmentTypeTransferCache;
import com.echothree.model.data.accounting.server.entity.Currency;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.filter.common.pk.FilterAdjustmentPK;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentAmount;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentDescription;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentFixedAmount;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentPercent;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentSource;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentSourceDescription;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentType;
import com.echothree.model.data.filter.server.entity.FilterAdjustmentTypeDescription;
import com.echothree.model.data.filter.server.entity.FilterKind;
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
import com.echothree.model.data.filter.server.value.FilterAdjustmentAmountValue;
import com.echothree.model.data.filter.server.value.FilterAdjustmentDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterAdjustmentDetailValue;
import com.echothree.model.data.filter.server.value.FilterAdjustmentFixedAmountValue;
import com.echothree.model.data.filter.server.value.FilterAdjustmentPercentValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_ADJUSTMENTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_ADJUSTMENT_AMOUNTS_CURRENCY_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_ADJUSTMENT_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_ADJUSTMENT_DETAILS_FILTER_KIND_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_ADJUSTMENT_FIXED_AMOUNTS_CURRENCY_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_ADJUSTMENT_PERCENTS_CURRENCY_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_KINDS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.tables.accounting.Currencies.Currencies;
import static com.echothree.model.jooq.server.tables.filter.FilterAdjustmentAmounts.FilterAdjustmentAmounts;
import static com.echothree.model.jooq.server.tables.filter.FilterAdjustmentDescriptions.FilterAdjustmentDescriptions;
import static com.echothree.model.jooq.server.tables.filter.FilterAdjustmentDetails.FilterAdjustmentDetails;
import static com.echothree.model.jooq.server.tables.filter.FilterAdjustmentFixedAmounts.FilterAdjustmentFixedAmounts;
import static com.echothree.model.jooq.server.tables.filter.FilterAdjustmentPercents.FilterAdjustmentPercents;
import static com.echothree.model.jooq.server.tables.filter.FilterAdjustmentSourceDescriptions.FilterAdjustmentSourceDescriptions;
import static com.echothree.model.jooq.server.tables.filter.FilterAdjustmentSources.FilterAdjustmentSources;
import static com.echothree.model.jooq.server.tables.filter.FilterAdjustmentTypeDescriptions.FilterAdjustmentTypeDescriptions;
import static com.echothree.model.jooq.server.tables.filter.FilterAdjustmentTypes.FilterAdjustmentTypes;
import static com.echothree.model.jooq.server.tables.filter.FilterAdjustments.FilterAdjustments;
import static com.echothree.model.jooq.server.tables.filter.FilterKindDetails.FilterKindDetails;
import static com.echothree.model.jooq.server.tables.filter.FilterKinds.FilterKinds;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureKindDetails.UnitOfMeasureKindDetails;
import static com.echothree.model.jooq.server.tables.uom.UnitOfMeasureTypeDetails.UnitOfMeasureTypeDetails;
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
public class FilterAdjustmentControl
        extends BaseModelControl {
    
    /** Creates a new instance of FilterAdjustmentControl */
    protected FilterAdjustmentControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Filter Adjustment Sources
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterAdjustmentSourceFactory filterAdjustmentSourceFactory;

    @Inject
    FilterAdjustmentSourceTransferCache filterAdjustmentSourceTransferCache;

    public FilterAdjustmentSource createFilterAdjustmentSource(String filterAdjustmentSourceName, Boolean allowedForInitialAmount,
            Boolean isDefault, Integer sortOrder) {
        return filterAdjustmentSourceFactory.create(filterAdjustmentSourceName, allowedForInitialAmount,
                isDefault, sortOrder);
    }

    public long countFilterAdjustmentSources() {
        return session.getDslContext()
                .selectCount()
                .from(FilterAdjustmentSources)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<FilterAdjustmentSource> getFilterAdjustmentSources() {
        var query = session.applyLimit(session.getDslContext()
                .select(FilterAdjustmentSources.fields())
                .from(FilterAdjustmentSources)
                .orderBy(FilterAdjustmentSources.SORT_ORDER, FilterAdjustmentSources.FILTER_ADJUSTMENT_SOURCE_NAME),
                FilterAdjustmentSourceFactory.class);

        return filterAdjustmentSourceFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public FilterAdjustmentSource getFilterAdjustmentSourceByName(String filterAdjustmentSourceName) {
        var query = session.getDslContext()
                .select(FilterAdjustmentSources.fields())
                .from(FilterAdjustmentSources)
                .where(FilterAdjustmentSources.FILTER_ADJUSTMENT_SOURCE_NAME.eq(filterAdjustmentSourceName));

        return filterAdjustmentSourceFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
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

    @Inject
    protected FilterAdjustmentSourceDescriptionFactory filterAdjustmentSourceDescriptionFactory;

    public FilterAdjustmentSourceDescription createFilterAdjustmentSourceDescription(FilterAdjustmentSource filterAdjustmentSource,
            Language language, String description) {
        return filterAdjustmentSourceDescriptionFactory.create(filterAdjustmentSource, language, description);
    }
    
    public FilterAdjustmentSourceDescription getFilterAdjustmentSourceDescription(FilterAdjustmentSource filterAdjustmentSource,
            Language language) {
        var query = session.getDslContext()
                .select(FilterAdjustmentSourceDescriptions.fields())
                .from(FilterAdjustmentSourceDescriptions)
                .where(FilterAdjustmentSourceDescriptions.FILTER_ADJUSTMENT_SOURCE.eq(filterAdjustmentSource.getPrimaryKey()),
                        FilterAdjustmentSourceDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return filterAdjustmentSourceDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
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

    @Inject
    protected FilterAdjustmentTypeFactory filterAdjustmentTypeFactory;

    @Inject
    FilterAdjustmentTypeTransferCache filterAdjustmentTypeTransferCache;

    public FilterAdjustmentType createFilterAdjustmentType(String filterAdjustmentTypeName, Boolean isDefault, Integer sortOrder) {
        return filterAdjustmentTypeFactory.create(filterAdjustmentTypeName, isDefault, sortOrder);
    }

    public long countFilterAdjustmentTypes() {
        return session.getDslContext()
                .selectCount()
                .from(FilterAdjustmentTypes)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<FilterAdjustmentType> getFilterAdjustmentTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(FilterAdjustmentTypes.fields())
                .from(FilterAdjustmentTypes)
                .orderBy(FilterAdjustmentTypes.SORT_ORDER, FilterAdjustmentTypes.FILTER_ADJUSTMENT_TYPE_NAME),
                FilterAdjustmentTypeFactory.class);

        return filterAdjustmentTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    public FilterAdjustmentType getFilterAdjustmentTypeByName(String filterAdjustmentTypeName) {
        var query = session.getDslContext()
                .select(FilterAdjustmentTypes.fields())
                .from(FilterAdjustmentTypes)
                .where(FilterAdjustmentTypes.FILTER_ADJUSTMENT_TYPE_NAME.eq(filterAdjustmentTypeName));

        return filterAdjustmentTypeFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
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

    @Inject
    protected FilterAdjustmentTypeDescriptionFactory filterAdjustmentTypeDescriptionFactory;

    public FilterAdjustmentTypeDescription createFilterAdjustmentTypeDescription(FilterAdjustmentType filterAdjustmentType,
            Language language, String description) {
        return filterAdjustmentTypeDescriptionFactory.create(filterAdjustmentType, language, description);
    }
    
    public FilterAdjustmentTypeDescription getFilterAdjustmentTypeDescription(FilterAdjustmentType filterAdjustmentType,
            Language language) {
        var query = session.getDslContext()
                .select(FilterAdjustmentTypeDescriptions.fields())
                .from(FilterAdjustmentTypeDescriptions)
                .where(FilterAdjustmentTypeDescriptions.FILTER_ADJUSTMENT_TYPE.eq(filterAdjustmentType.getPrimaryKey()),
                        FilterAdjustmentTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()));

        return filterAdjustmentTypeDescriptionFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
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

    @Inject
    protected FilterAdjustmentDetailFactory filterAdjustmentDetailFactory;

    @Inject
    protected FilterAdjustmentFactory filterAdjustmentFactory;

    @Inject
    FilterAdjustmentTransferCache filterAdjustmentTransferCache;

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

        var filterAdjustment = filterAdjustmentFactory.create();
        var filterAdjustmentDetail = filterAdjustmentDetailFactory.create(
                filterAdjustment, filterKind, filterAdjustmentName, filterAdjustmentSource, filterAdjustmentType,
                isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        filterAdjustment = filterAdjustmentFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                filterAdjustment.getPrimaryKey());
        filterAdjustment.setActiveDetail(filterAdjustmentDetail);
        filterAdjustment.setLastDetail(filterAdjustmentDetail);
        filterAdjustment.store();
        
        sendEvent(filterAdjustment.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return filterAdjustment;
    }

    public long countFilterAdjustmentsByFilterKind(FilterKind filterKind) {
        return session.getDslContext()
                .selectCount()
                .from(FilterAdjustments)
                .join(FilterAdjustmentDetails).onKey(FILTER_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .where(FilterAdjustmentDetails.FILTER_KIND.eq(filterKind.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.FilterAdjustment */
    public FilterAdjustment getFilterAdjustmentByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new FilterAdjustmentPK(entityInstance.getEntityUniqueId());

        return filterAdjustmentFactory.getEntityFromPK(entityPermission, pk);
    }

    public FilterAdjustment getFilterAdjustmentByEntityInstance(EntityInstance entityInstance) {
        return getFilterAdjustmentByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public FilterAdjustment getFilterAdjustmentByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getFilterAdjustmentByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public List<FilterAdjustment> getFilterAdjustments() {
        var query = session.applyLimit(session.getDslContext()
                .select(FilterAdjustments.fields())
                .from(FilterAdjustments)
                .join(FilterAdjustmentDetails).onKey(FILTER_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .join(FilterKinds).onKey(FILTER_ADJUSTMENT_DETAILS_FILTER_KIND_FK)
                .join(FilterKindDetails).onKey(FILTER_KINDS_LAST_DETAIL_FK)
                .orderBy(FilterKindDetails.SORT_ORDER, FilterKindDetails.FILTER_KIND_NAME, FilterAdjustmentDetails.FILTER_ADJUSTMENT_NAME),
                FilterAdjustmentFactory.class);

        return filterAdjustmentFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }
    
    private List<FilterAdjustment> getFilterAdjustmentsByFilterKind(FilterKind filterKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FilterAdjustments.fields())
                .from(FilterAdjustments)
                .join(FilterAdjustmentDetails).onKey(FILTER_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .where(FilterAdjustmentDetails.FILTER_KIND.eq(filterKind.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(FilterAdjustmentDetails.FILTER_ADJUSTMENT_NAME),
                    FilterAdjustmentFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return filterAdjustmentFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FilterAdjustment> getFilterAdjustmentsByFilterKind(FilterKind filterKind) {
        return getFilterAdjustmentsByFilterKind(filterKind, EntityPermission.READ_ONLY);
    }
    
    public List<FilterAdjustment> getFilterAdjustmentsByFilterKindForUpdate(FilterKind filterKind) {
        return getFilterAdjustmentsByFilterKind(filterKind, EntityPermission.READ_WRITE);
    }
    
    public FilterAdjustment getDefaultFilterAdjustment(FilterKind filterKind, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FilterAdjustments.fields())
                .from(FilterAdjustments)
                .join(FilterAdjustmentDetails).onKey(FILTER_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .where(FilterAdjustmentDetails.FILTER_KIND.eq(filterKind.getPrimaryKey()),
                        FilterAdjustmentDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return filterAdjustmentFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(FilterAdjustments.fields())
                .from(FilterAdjustments)
                .join(FilterAdjustmentDetails).onKey(FILTER_ADJUSTMENTS_ACTIVE_DETAIL_FK)
                .where(FilterAdjustmentDetails.FILTER_KIND.eq(filterKind.getPrimaryKey()),
                        FilterAdjustmentDetails.FILTER_ADJUSTMENT_NAME.eq(filterAdjustmentName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return filterAdjustmentFactory.getEntityFromQuery(entityPermission, query);
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
            var filterAdjustment = filterAdjustmentFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentDetailValue.getFilterAdjustmentPK());
            var filterAdjustmentDetail = filterAdjustment.getActiveDetailForUpdate();
            
            filterAdjustmentDetail.setThruTime(session.getStartTime());
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
            
            filterAdjustmentDetail = filterAdjustmentDetailFactory.create(filterAdjustmentPK, filterKindPK,
                    filterAdjustmentName, filterAdjustmentSourcePK, filterAdjustmentTypePK, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);
            
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
        filterAdjustmentDetail.setThruTime(session.getStartTime());
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

    @Inject
    protected FilterAdjustmentAmountFactory filterAdjustmentAmountFactory;

    @Inject
    FilterAdjustmentAmountTransferCache filterAdjustmentAmountTransferCache;

    public FilterAdjustmentAmount createFilterAdjustmentAmount(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency, Long amount, BasePK createdBy) {
        var filterAdjustmentAmount = filterAdjustmentAmountFactory.create(
                filterAdjustment, unitOfMeasureType, currency, amount, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY,
                filterAdjustmentAmount.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterAdjustmentAmount;
    }
    
    public long countFilterAdjustmentAmountsByFilterAdjustment(FilterAdjustment filterAdjustment) {
        return session.getDslContext()
                .selectCount()
                .from(FilterAdjustmentAmounts)
                .where(FilterAdjustmentAmounts.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                        FilterAdjustmentAmounts.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<FilterAdjustmentAmount> getFilterAdjustmentAmounts(FilterAdjustment filterAdjustment,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FilterAdjustmentAmounts.fields())
                    .from(FilterAdjustmentAmounts)
                    .join(UnitOfMeasureTypeDetails).on(FilterAdjustmentAmounts.UNIT_OF_MEASURE_TYPE.eq(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE))
                    .join(UnitOfMeasureKindDetails).on(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND))
                    .join(Currencies).onKey(FILTER_ADJUSTMENT_AMOUNTS_CURRENCY_FK)
                    .where(FilterAdjustmentAmounts.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                            FilterAdjustmentAmounts.THRU_TIME.eq(Session.MAX_TIME),
                            UnitOfMeasureTypeDetails.THRU_TIME.eq(Session.MAX_TIME),
                            UnitOfMeasureKindDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureTypeDetails.SORT_ORDER, Currencies.SORT_ORDER),
                    FilterAdjustmentAmountFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FilterAdjustmentAmounts.fields())
                    .from(FilterAdjustmentAmounts)
                    .where(FilterAdjustmentAmounts.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                            FilterAdjustmentAmounts.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return filterAdjustmentAmountFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FilterAdjustmentAmount> getFilterAdjustmentAmounts(FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentAmounts(filterAdjustment, EntityPermission.READ_ONLY);
    }
    
    public List<FilterAdjustmentAmount> getFilterAdjustmentAmountsForUpdate(FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentAmounts(filterAdjustment, EntityPermission.READ_WRITE);
    }
    
    private FilterAdjustmentAmount getFilterAdjustmentAmount(FilterAdjustment filterAdjustment, UnitOfMeasureType unitOfMeasureType,
            Currency currency, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FilterAdjustmentAmounts.fields())
                .from(FilterAdjustmentAmounts)
                .where(FilterAdjustmentAmounts.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                        FilterAdjustmentAmounts.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        FilterAdjustmentAmounts.CURRENCY.eq(currency.getPrimaryKey()),
                        FilterAdjustmentAmounts.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return filterAdjustmentAmountFactory.getEntityFromQuery(entityPermission, query);
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
            var filterAdjustmentAmount = filterAdjustmentAmountFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentAmountValue.getPrimaryKey());
            
            filterAdjustmentAmount.setThruTime(session.getStartTime());
            filterAdjustmentAmount.store();

            var filterAdjustmentPK = filterAdjustmentAmount.getFilterAdjustmentPK(); // Not updated
            var unitOfMeasureTypePK = filterAdjustmentAmount.getUnitOfMeasureTypePK(); // Not updated
            var currencyPK = filterAdjustmentAmount.getCurrencyPK(); // Not updated
            var amount = filterAdjustmentAmountValue.getAmount();
            
            filterAdjustmentAmount = filterAdjustmentAmountFactory.create(filterAdjustmentPK,
                    unitOfMeasureTypePK, currencyPK, amount, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(filterAdjustmentPK, EventTypes.MODIFY,
                    filterAdjustmentAmount.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterAdjustmentAmount(FilterAdjustmentAmount filterAdjustmentAmount, BasePK deletedBy) {
        filterAdjustmentAmount.setThruTime(session.getStartTime());
        
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

    @Inject
    protected FilterAdjustmentFixedAmountFactory filterAdjustmentFixedAmountFactory;

    @Inject
    FilterAdjustmentFixedAmountTransferCache filterAdjustmentFixedAmountTransferCache;

    public FilterAdjustmentFixedAmount createFilterAdjustmentFixedAmount(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency, Long unitAmount, BasePK createdBy) {
        var filterAdjustmentFixedAmount = filterAdjustmentFixedAmountFactory.create(
                filterAdjustment, unitOfMeasureType, currency, unitAmount, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY,
                filterAdjustmentFixedAmount.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterAdjustmentFixedAmount;
    }
    
    public long countFilterAdjustmentFixedAmountsByFilterAdjustment(FilterAdjustment filterAdjustment) {
        return session.getDslContext()
                .selectCount()
                .from(FilterAdjustmentFixedAmounts)
                .where(FilterAdjustmentFixedAmounts.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                        FilterAdjustmentFixedAmounts.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<FilterAdjustmentFixedAmount> getFilterAdjustmentFixedAmounts(FilterAdjustment filterAdjustment,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FilterAdjustmentFixedAmounts.fields())
                    .from(FilterAdjustmentFixedAmounts)
                    .join(UnitOfMeasureTypeDetails).on(FilterAdjustmentFixedAmounts.UNIT_OF_MEASURE_TYPE.eq(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE))
                    .join(UnitOfMeasureKindDetails).on(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND))
                    .join(Currencies).onKey(FILTER_ADJUSTMENT_FIXED_AMOUNTS_CURRENCY_FK)
                    .where(FilterAdjustmentFixedAmounts.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                            FilterAdjustmentFixedAmounts.THRU_TIME.eq(Session.MAX_TIME),
                            UnitOfMeasureTypeDetails.THRU_TIME.eq(Session.MAX_TIME),
                            UnitOfMeasureKindDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureTypeDetails.SORT_ORDER, Currencies.SORT_ORDER),
                    FilterAdjustmentFixedAmountFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FilterAdjustmentFixedAmounts.fields())
                    .from(FilterAdjustmentFixedAmounts)
                    .where(FilterAdjustmentFixedAmounts.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                            FilterAdjustmentFixedAmounts.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return filterAdjustmentFixedAmountFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FilterAdjustmentFixedAmount> getFilterAdjustmentFixedAmounts(FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentFixedAmounts(filterAdjustment, EntityPermission.READ_ONLY);
    }
    
    public List<FilterAdjustmentFixedAmount> getFilterAdjustmentFixedAmountsForUpdate(FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentFixedAmounts(filterAdjustment, EntityPermission.READ_WRITE);
    }
    
    private FilterAdjustmentFixedAmount getFilterAdjustmentFixedAmount(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FilterAdjustmentFixedAmounts.fields())
                .from(FilterAdjustmentFixedAmounts)
                .where(FilterAdjustmentFixedAmounts.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                        FilterAdjustmentFixedAmounts.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        FilterAdjustmentFixedAmounts.CURRENCY.eq(currency.getPrimaryKey()),
                        FilterAdjustmentFixedAmounts.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return filterAdjustmentFixedAmountFactory.getEntityFromQuery(entityPermission, query);
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
            var filterAdjustmentFixedAmount = filterAdjustmentFixedAmountFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentFixedAmountValue.getPrimaryKey());
            
            filterAdjustmentFixedAmount.setThruTime(session.getStartTime());
            filterAdjustmentFixedAmount.store();

            var filterAdjustmentPK = filterAdjustmentFixedAmount.getFilterAdjustmentPK(); // Not updated
            var unitOfMeasureTypePK = filterAdjustmentFixedAmount.getUnitOfMeasureTypePK(); // Not updated
            var currencyPK = filterAdjustmentFixedAmount.getCurrencyPK(); // Not updated
            var unitAmount = filterAdjustmentFixedAmountValue.getUnitAmount();
            
            filterAdjustmentFixedAmount = filterAdjustmentFixedAmountFactory.create(filterAdjustmentPK,
                    unitOfMeasureTypePK, currencyPK, unitAmount, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(filterAdjustmentPK, EventTypes.MODIFY,
                    filterAdjustmentFixedAmount.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterAdjustmentFixedAmount(FilterAdjustmentFixedAmount filterAdjustmentFixedAmount, BasePK deletedBy) {
        filterAdjustmentFixedAmount.setThruTime(session.getStartTime());
        
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

    @Inject
    protected FilterAdjustmentPercentFactory filterAdjustmentPercentFactory;

    @Inject
    FilterAdjustmentPercentTransferCache filterAdjustmentPercentTransferCache;

    public FilterAdjustmentPercent createFilterAdjustmentPercent(FilterAdjustment filterAdjustment,
            UnitOfMeasureType unitOfMeasureType, Currency currency, Integer percent, BasePK createdBy) {
        var filterAdjustmentPercent = filterAdjustmentPercentFactory.create(
                filterAdjustment, unitOfMeasureType, currency, percent, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY,
                filterAdjustmentPercent.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterAdjustmentPercent;
    }

    public long countFilterAdjustmentPercentsByFilterAdjustment(FilterAdjustment filterAdjustment) {
        return session.getDslContext()
                .selectCount()
                .from(FilterAdjustmentPercents)
                .where(FilterAdjustmentPercents.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                        FilterAdjustmentPercents.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<FilterAdjustmentPercent> getFilterAdjustmentPercents(FilterAdjustment filterAdjustment,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FilterAdjustmentPercents.fields())
                    .from(FilterAdjustmentPercents)
                    .join(UnitOfMeasureTypeDetails).on(FilterAdjustmentPercents.UNIT_OF_MEASURE_TYPE.eq(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_TYPE))
                    .join(UnitOfMeasureKindDetails).on(UnitOfMeasureTypeDetails.UNIT_OF_MEASURE_KIND.eq(UnitOfMeasureKindDetails.UNIT_OF_MEASURE_KIND))
                    .join(Currencies).onKey(FILTER_ADJUSTMENT_PERCENTS_CURRENCY_FK)
                    .where(FilterAdjustmentPercents.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                            FilterAdjustmentPercents.THRU_TIME.eq(Session.MAX_TIME),
                            UnitOfMeasureTypeDetails.THRU_TIME.eq(Session.MAX_TIME),
                            UnitOfMeasureKindDetails.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(UnitOfMeasureKindDetails.SORT_ORDER, UnitOfMeasureTypeDetails.SORT_ORDER, Currencies.SORT_ORDER),
                    FilterAdjustmentPercentFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FilterAdjustmentPercents.fields())
                    .from(FilterAdjustmentPercents)
                    .where(FilterAdjustmentPercents.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                            FilterAdjustmentPercents.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return filterAdjustmentPercentFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FilterAdjustmentPercent> getFilterAdjustmentPercents(FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentPercents(filterAdjustment, EntityPermission.READ_ONLY);
    }
    
    public List<FilterAdjustmentPercent> getFilterAdjustmentPercentsForUpdate(FilterAdjustment filterAdjustment) {
        return getFilterAdjustmentPercents(filterAdjustment, EntityPermission.READ_WRITE);
    }

    private FilterAdjustmentPercent getFilterAdjustmentPercent(FilterAdjustment filterAdjustment, UnitOfMeasureType unitOfMeasureType,
            Currency currency, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FilterAdjustmentPercents.fields())
                .from(FilterAdjustmentPercents)
                .where(FilterAdjustmentPercents.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                        FilterAdjustmentPercents.UNIT_OF_MEASURE_TYPE.eq(unitOfMeasureType.getPrimaryKey()),
                        FilterAdjustmentPercents.CURRENCY.eq(currency.getPrimaryKey()),
                        FilterAdjustmentPercents.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return filterAdjustmentPercentFactory.getEntityFromQuery(entityPermission, query);
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
            var filterAdjustmentPercent = filterAdjustmentPercentFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentPercentValue.getPrimaryKey());
            
            filterAdjustmentPercent.setThruTime(session.getStartTime());
            filterAdjustmentPercent.store();

            var filterAdjustmentPK = filterAdjustmentPercent.getFilterAdjustmentPK(); // Not updated
            var unitOfMeasureTypePK = filterAdjustmentPercent.getUnitOfMeasureTypePK(); // Not updated
            var currencyPK = filterAdjustmentPercent.getCurrencyPK(); // Not updated
            var percent = filterAdjustmentPercentValue.getPercent();
            
            filterAdjustmentPercent = filterAdjustmentPercentFactory.create(filterAdjustmentPK,
                    unitOfMeasureTypePK, currencyPK, percent, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(filterAdjustmentPK, EventTypes.MODIFY,
                    filterAdjustmentPercent.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterAdjustmentPercent(FilterAdjustmentPercent filterAdjustmentPercent, BasePK deletedBy) {
        filterAdjustmentPercent.setThruTime(session.getStartTime());
        
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

    @Inject
    protected FilterAdjustmentDescriptionFactory filterAdjustmentDescriptionFactory;

    @Inject
    FilterAdjustmentDescriptionTransferCache filterAdjustmentDescriptionTransferCache;

    public FilterAdjustmentDescription createFilterAdjustmentDescription(FilterAdjustment filterAdjustment, Language language,
            String description,
            BasePK createdBy) {
        var filterAdjustmentDescription = filterAdjustmentDescriptionFactory.create(
                filterAdjustment, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY,
                filterAdjustmentDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterAdjustmentDescription;
    }
    
    private FilterAdjustmentDescription getFilterAdjustmentDescription(FilterAdjustment filterAdjustment, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FilterAdjustmentDescriptions.fields())
                .from(FilterAdjustmentDescriptions)
                .where(FilterAdjustmentDescriptions.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                        FilterAdjustmentDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        FilterAdjustmentDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return filterAdjustmentDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FilterAdjustmentDescriptions.fields())
                    .from(FilterAdjustmentDescriptions)
                    .join(Languages).onKey(FILTER_ADJUSTMENT_DESCRIPTIONS_LANGUAGE_FK)
                    .where(FilterAdjustmentDescriptions.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                            FilterAdjustmentDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    FilterAdjustmentDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FilterAdjustmentDescriptions.fields())
                    .from(FilterAdjustmentDescriptions)
                    .where(FilterAdjustmentDescriptions.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()),
                            FilterAdjustmentDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return filterAdjustmentDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            var filterAdjustmentDescription = filterAdjustmentDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     filterAdjustmentDescriptionValue.getPrimaryKey());
            
            filterAdjustmentDescription.setThruTime(session.getStartTime());
            filterAdjustmentDescription.store();

            var filterAdjustment = filterAdjustmentDescription.getFilterAdjustment();
            var language = filterAdjustmentDescription.getLanguage();
            var description = filterAdjustmentDescriptionValue.getDescription();
            
            filterAdjustmentDescription = filterAdjustmentDescriptionFactory.create(filterAdjustment,
                    language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(filterAdjustment.getPrimaryKey(), EventTypes.MODIFY,
                    filterAdjustmentDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterAdjustmentDescription(FilterAdjustmentDescription filterAdjustmentDescription, BasePK deletedBy) {
        filterAdjustmentDescription.setThruTime(session.getStartTime());
        
        sendEvent(filterAdjustmentDescription.getFilterAdjustmentPK(), EventTypes.MODIFY,
                filterAdjustmentDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteFilterAdjustmentDescriptionsByFilterAdjustment(FilterAdjustment filterAdjustment, BasePK deletedBy) {
        var filterAdjustmentDescriptions = getFilterAdjustmentDescriptionsForUpdate(filterAdjustment);
        
        filterAdjustmentDescriptions.forEach((filterAdjustmentDescription) -> 
                deleteFilterAdjustmentDescription(filterAdjustmentDescription, deletedBy)
        );
    }
    
}
