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
// distributed under the License is distributed ON an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.model.control.filter.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.filter.common.transfer.FilterStepElementDescriptionTransfer;
import com.echothree.model.control.filter.common.transfer.FilterStepElementTransfer;
import com.echothree.model.control.filter.server.transfer.FilterStepElementDescriptionTransferCache;
import com.echothree.model.control.filter.server.transfer.FilterStepElementTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.filter.common.pk.FilterStepElementPK;
import com.echothree.model.data.filter.server.entity.FilterAdjustment;
import com.echothree.model.data.filter.server.entity.FilterStep;
import com.echothree.model.data.filter.server.entity.FilterStepElement;
import com.echothree.model.data.filter.server.entity.FilterStepElementDescription;
import com.echothree.model.data.filter.server.factory.FilterStepElementDescriptionFactory;
import com.echothree.model.data.filter.server.factory.FilterStepElementDetailFactory;
import com.echothree.model.data.filter.server.factory.FilterStepElementFactory;
import com.echothree.model.data.filter.server.value.FilterStepElementDescriptionValue;
import com.echothree.model.data.filter.server.value.FilterStepElementDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.selector.server.entity.Selector;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTERS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_DETAILS_FILTER_TYPE_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_KINDS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_STEPS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_STEP_DETAILS_FILTER_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_STEP_ELEMENTS_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_STEP_ELEMENT_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_STEP_ELEMENT_DETAILS_FILTER_STEP_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.filter.FilterForeignKeys.FILTER_TYPE_DETAILS_FILTER_KIND_FK;
import static com.echothree.model.jooq.server.tables.filter.FilterDetails.FilterDetails;
import static com.echothree.model.jooq.server.tables.filter.FilterKindDetails.FilterKindDetails;
import static com.echothree.model.jooq.server.tables.filter.FilterKinds.FilterKinds;
import static com.echothree.model.jooq.server.tables.filter.FilterStepDetails.FilterStepDetails;
import static com.echothree.model.jooq.server.tables.filter.FilterStepElementDescriptions.FilterStepElementDescriptions;
import static com.echothree.model.jooq.server.tables.filter.FilterStepElementDetails.FilterStepElementDetails;
import static com.echothree.model.jooq.server.tables.filter.FilterStepElements.FilterStepElements;
import static com.echothree.model.jooq.server.tables.filter.FilterSteps.FilterSteps;
import static com.echothree.model.jooq.server.tables.filter.FilterTypeDetails.FilterTypeDetails;
import static com.echothree.model.jooq.server.tables.filter.FilterTypes.FilterTypes;
import static com.echothree.model.jooq.server.tables.filter.Filters.Filters;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.control.BaseModelControl;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

@CommandScope
public class FilterStepElementControl
        extends BaseModelControl {
    
    /** Creates a new instance of FilterStepElementControl */
    protected FilterStepElementControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Filter Step Elements
    // --------------------------------------------------------------------------------

    @Inject
    protected FilterStepElementDetailFactory filterStepElementDetailFactory;

    @Inject
    protected FilterStepElementFactory filterStepElementFactory;

    @Inject
    FilterStepElementTransferCache filterStepElementTransferCache;

    public FilterStepElement createFilterStepElement(FilterStep filterStep, String filterStepElementName, Selector filterItemSelector,
            FilterAdjustment filterAdjustment, BasePK createdBy) {
        var filterStepElement = filterStepElementFactory.create();
        var filterStepElementDetail = filterStepElementDetailFactory.create(filterStepElement, filterStep,
                filterStepElementName, filterItemSelector, filterAdjustment, session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        filterStepElement = filterStepElementFactory.getEntityFromPK(EntityPermission.READ_WRITE, filterStepElement.getPrimaryKey());
        filterStepElement.setActiveDetail(filterStepElementDetail);
        filterStepElement.setLastDetail(filterStepElementDetail);
        filterStepElement.store();
        
        sendEvent(filterStepElement.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return filterStepElement;
    }

    public long countFilterStepElementsByFilterStep(FilterStep filterStep) {
        return session.getDslContext()
                .selectCount()
                .from(FilterStepElements)
                .join(FilterStepElementDetails).onKey(FILTER_STEP_ELEMENTS_ACTIVE_DETAIL_FK)
                .where(FilterStepElementDetails.FILTER_STEP.eq(filterStep.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countFilterStepElementsBySelector(Selector selector) {
        return session.getDslContext()
                .selectCount()
                .from(FilterStepElements)
                .join(FilterStepElementDetails).onKey(FILTER_STEP_ELEMENTS_ACTIVE_DETAIL_FK)
                .where(FilterStepElementDetails.FILTER_ITEM_SELECTOR.eq(selector.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countFilterStepElementsByFilterAdjustment(FilterAdjustment filterAdjustment) {
        return session.getDslContext()
                .selectCount()
                .from(FilterStepElements)
                .join(FilterStepElementDetails).onKey(FILTER_STEP_ELEMENTS_ACTIVE_DETAIL_FK)
                .where(FilterStepElementDetails.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.FilterStepElement */
    public FilterStepElement getFilterStepElementByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new FilterStepElementPK(entityInstance.getEntityUniqueId());

        return filterStepElementFactory.getEntityFromPK(entityPermission, pk);
    }

    public FilterStepElement getFilterStepElementByEntityInstance(EntityInstance entityInstance) {
        return getFilterStepElementByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public FilterStepElement getFilterStepElementByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getFilterStepElementByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public FilterStepElement getFilterStepElementByName(FilterStep filterStep, String filterStepElementName,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FilterStepElements.fields())
                .from(FilterStepElements)
                .join(FilterStepElementDetails).onKey(FILTER_STEP_ELEMENTS_ACTIVE_DETAIL_FK)
                .where(FilterStepElementDetails.FILTER_STEP.eq(filterStep.getPrimaryKey()),
                        FilterStepElementDetails.FILTER_STEP_ELEMENT_NAME.eq(filterStepElementName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return filterStepElementFactory.getEntityFromQuery(entityPermission, query);
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
        var baseQuery = session.getDslContext()
                .select(FilterStepElements.fields())
                .from(FilterStepElements)
                .join(FilterStepElementDetails).onKey(FILTER_STEP_ELEMENTS_ACTIVE_DETAIL_FK)
                .where(FilterStepElementDetails.FILTER_STEP.eq(filterStep.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(FilterStepElementDetails.FILTER_STEP_ELEMENT_NAME),
                    FilterStepElementFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return filterStepElementFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<FilterStepElement> getFilterStepElementsByFilterStep(FilterStep filterStep) {
        return getFilterStepElementsByFilterStep(filterStep, EntityPermission.READ_ONLY);
    }
    
    public List<FilterStepElement> getFilterStepElementsByFilterStepForUpdate(FilterStep filterStep) {
        return getFilterStepElementsByFilterStep(filterStep, EntityPermission.READ_WRITE);
    }

    private List<FilterStepElement> getFilterStepElementsByFilterAdjustment(FilterAdjustment filterAdjustment, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FilterStepElements.fields())
                    .from(FilterStepElements)
                    .join(FilterStepElementDetails).onKey(FILTER_STEP_ELEMENTS_ACTIVE_DETAIL_FK)
                    .join(FilterSteps).onKey(FILTER_STEP_ELEMENT_DETAILS_FILTER_STEP_FK)
                    .join(FilterStepDetails).onKey(FILTER_STEPS_LAST_DETAIL_FK)
                    .join(Filters).onKey(FILTER_STEP_DETAILS_FILTER_FK)
                    .join(FilterDetails).onKey(FILTERS_LAST_DETAIL_FK)
                    .join(FilterTypes).onKey(FILTER_DETAILS_FILTER_TYPE_FK)
                    .join(FilterTypeDetails).onKey(FILTER_TYPES_LAST_DETAIL_FK)
                    .join(FilterKinds).onKey(FILTER_TYPE_DETAILS_FILTER_KIND_FK)
                    .join(FilterKindDetails).onKey(FILTER_KINDS_LAST_DETAIL_FK)
                    .where(FilterStepElementDetails.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()))
                    .orderBy(FilterKindDetails.SORT_ORDER, FilterKindDetails.FILTER_KIND_NAME, FilterTypeDetails.SORT_ORDER, FilterTypeDetails.FILTER_TYPE_NAME, FilterDetails.SORT_ORDER, FilterDetails.FILTER_NAME, FilterStepDetails.FILTER_STEP_NAME, FilterStepElementDetails.FILTER_STEP_ELEMENT_NAME),
                    FilterStepElementFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FilterStepElements.fields())
                    .from(FilterStepElements)
                    .join(FilterStepElementDetails).onKey(FILTER_STEP_ELEMENTS_ACTIVE_DETAIL_FK)
                    .where(FilterStepElementDetails.FILTER_ADJUSTMENT.eq(filterAdjustment.getPrimaryKey()))
                    .forUpdate();
        };

        return filterStepElementFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<FilterStepElement> getFilterStepElementsByFilterAdjustment(FilterAdjustment filterAdjustment) {
        return getFilterStepElementsByFilterAdjustment(filterAdjustment, EntityPermission.READ_ONLY);
    }

    public List<FilterStepElement> getFilterStepElementsByFilterAdjustmentForUpdate(FilterAdjustment filterAdjustment) {
        return getFilterStepElementsByFilterAdjustment(filterAdjustment, EntityPermission.READ_WRITE);
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
    
    public void updateFilterStepElementFromValue(FilterStepElementDetailValue filterStepElementDetailValue, BasePK updatedBy) {
        if(filterStepElementDetailValue.hasBeenModified()) {
            var filterStepElement = filterStepElementFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     filterStepElementDetailValue.getFilterStepElementPK());
            var filterStepElementDetail = filterStepElement.getActiveDetailForUpdate();
            
            filterStepElementDetail.setThruTime(session.getStartTime());
            filterStepElementDetail.store();

            var filterStepElementPK = filterStepElementDetail.getFilterStepElementPK();
            var filterStep = filterStepElementDetail.getFilterStep();
            var filterStepPK = filterStep.getPrimaryKey();
            var filterStepElementName = filterStepElementDetailValue.getFilterStepElementName();
            var filterItemSelectorPK = filterStepElementDetailValue.getFilterItemSelectorPK();
            var filterAdjustmentPK = filterStepElementDetailValue.getFilterAdjustmentPK();
            
            filterStepElementDetail = filterStepElementDetailFactory.create(filterStepElementPK,
                    filterStepPK, filterStepElementName, filterItemSelectorPK, filterAdjustmentPK, session.getStartTime(),
                    Session.MAX_TIME);
            
            filterStepElement.setActiveDetail(filterStepElementDetail);
            filterStepElement.setLastDetail(filterStepElementDetail);
            
            sendEvent(filterStepElementPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void deleteFilterStepElement(FilterStepElement filterStepElement, BasePK deletedBy) {
        deleteFilterStepElementDescriptionsByFilterStepElement(filterStepElement, deletedBy);

        var filterStepElementDetail = filterStepElement.getLastDetailForUpdate();
        filterStepElementDetail.setThruTime(session.getStartTime());
        filterStepElement.setActiveDetail(null);
        filterStepElement.store();
        
        sendEvent(filterStepElement.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
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

    @Inject
    protected FilterStepElementDescriptionFactory filterStepElementDescriptionFactory;

    @Inject
    FilterStepElementDescriptionTransferCache filterStepElementDescriptionTransferCache;

    public FilterStepElementDescription createFilterStepElementDescription(FilterStepElement filterStepElement, Language language,
            String description, BasePK createdBy) {
        var filterStepElementDescription = filterStepElementDescriptionFactory.create(
                filterStepElement, language, description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(filterStepElement.getPrimaryKey(), EventTypes.MODIFY, filterStepElementDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);
        
        return filterStepElementDescription;
    }
    
    private FilterStepElementDescription getFilterStepElementDescription(FilterStepElement filterStepElement, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(FilterStepElementDescriptions.fields())
                .from(FilterStepElementDescriptions)
                .where(FilterStepElementDescriptions.FILTER_STEP_ELEMENT.eq(filterStepElement.getPrimaryKey()),
                        FilterStepElementDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        FilterStepElementDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return filterStepElementDescriptionFactory.getEntityFromQuery(entityPermission, query);
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
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(FilterStepElementDescriptions.fields())
                    .from(FilterStepElementDescriptions)
                    .join(Languages).onKey(FILTER_STEP_ELEMENT_DESCRIPTIONS_LANGUAGE_FK)
                    .where(FilterStepElementDescriptions.FILTER_STEP_ELEMENT.eq(filterStepElement.getPrimaryKey()),
                            FilterStepElementDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    FilterStepElementDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(FilterStepElementDescriptions.fields())
                    .from(FilterStepElementDescriptions)
                    .where(FilterStepElementDescriptions.FILTER_STEP_ELEMENT.eq(filterStepElement.getPrimaryKey()),
                            FilterStepElementDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return filterStepElementDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
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
            var filterStepElementDescription = filterStepElementDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE, filterStepElementDescriptionValue.getPrimaryKey());
            
            filterStepElementDescription.setThruTime(session.getStartTime());
            filterStepElementDescription.store();

            var filterStepElement = filterStepElementDescription.getFilterStepElement();
            var language = filterStepElementDescription.getLanguage();
            var description = filterStepElementDescriptionValue.getDescription();
            
            filterStepElementDescription = filterStepElementDescriptionFactory.create(filterStepElement,
                    language, description, session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(filterStepElement.getPrimaryKey(), EventTypes.MODIFY, filterStepElementDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteFilterStepElementDescription(FilterStepElementDescription filterStepElementDescription, BasePK deletedBy) {
        filterStepElementDescription.setThruTime(session.getStartTime());
        
        sendEvent(filterStepElementDescription.getFilterStepElement().getPrimaryKey(), EventTypes.MODIFY, filterStepElementDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
        
    }
    
    public void deleteFilterStepElementDescriptionsByFilterStepElement(FilterStepElement filterStepElement, BasePK deletedBy) {
        var filterStepElementDescriptions = getFilterStepElementDescriptionsForUpdate(filterStepElement);
        
        filterStepElementDescriptions.forEach((filterStepElementDescription) -> 
                deleteFilterStepElementDescription(filterStepElementDescription, deletedBy)
        );
    }
    
}
