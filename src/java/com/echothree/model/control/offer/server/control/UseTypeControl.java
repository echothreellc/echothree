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

package com.echothree.model.control.offer.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.offer.common.choice.UseTypeChoicesBean;
import com.echothree.model.control.offer.common.transfer.UseTypeDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.UseTypeResultTransfer;
import com.echothree.model.control.offer.common.transfer.UseTypeTransfer;
import com.echothree.model.control.offer.server.transfer.UseTypeDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.UseTypeTransferCache;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.offer.common.pk.UseTypePK;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.entity.UseTypeDescription;
import com.echothree.model.data.offer.server.factory.UseTypeDescriptionFactory;
import com.echothree.model.data.offer.server.factory.UseTypeDetailFactory;
import com.echothree.model.data.offer.server.factory.UseTypeFactory;
import com.echothree.model.data.offer.server.value.UseTypeDescriptionValue;
import com.echothree.model.data.offer.server.value.UseTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.USE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.USE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.offer.UseTypeDescriptions.UseTypeDescriptions;
import static com.echothree.model.jooq.server.tables.offer.UseTypeDetails.UseTypeDetails;
import static com.echothree.model.jooq.server.tables.offer.UseTypes.UseTypes;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.search.CachedExecutedSearchResults.CachedExecutedSearchResults;
import static com.echothree.model.jooq.server.tables.search.SearchResults.SearchResults;
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
import org.jooq.Select;

@CommandScope
public class UseTypeControl
        extends BaseModelControl {

    @Inject
    private SearchControl searchControl;

    @Inject
    protected UseControl useControl;

    /** Creates a new instance of UseTypeControl */
    protected UseTypeControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Use Types
    // --------------------------------------------------------------------------------

    @Inject
    protected UseTypeDetailFactory useTypeDetailFactory;

    @Inject
    private UseTypeFactory useTypeFactory;

    @Inject
    private UseTypeTransferCache useTypeTransferCache;

    public UseType createUseType(String useTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultUseType = getDefaultUseType();
        var defaultFound = defaultUseType != null;
        
        if(defaultFound && isDefault) {
            var defaultUseTypeDetailValue = getDefaultUseTypeDetailValueForUpdate();
            
            defaultUseTypeDetailValue.setIsDefault(false);
            updateUseTypeFromValue(defaultUseTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var useType = useTypeFactory.create();
        var useTypeDetail = useTypeDetailFactory.create(useType, useTypeName, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);
        
        // Convert to R/W
        useType = useTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, useType.getPrimaryKey());
        useType.setActiveDetail(useTypeDetail);
        useType.setLastDetail(useTypeDetail);
        useType.store();
        
        sendEvent(useType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);
        
        return useType;
    }
    
    /** Assume that the entityInstance passed to this function is a ECHO_THREE.UseType */
    public UseType getUseTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UseTypePK(entityInstance.getEntityUniqueId());
        var useType = useTypeFactory.getEntityFromPK(entityPermission, pk);

        return useType;
    }

    public UseType getUseTypeByEntityInstance(EntityInstance entityInstance) {
        return getUseTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public UseType getUseTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUseTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countUseTypes() {
        return session.getDslContext()
                .selectCount()
                .from(UseTypes)
                .join(UseTypeDetails).onKey(USE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private List<UseType> getUseTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UseTypes.fields())
                .from(UseTypes)
                .join(UseTypeDetails).onKey(USE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(UseTypeDetails.SORT_ORDER, UseTypeDetails.USE_TYPE_NAME),
                    UseTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return useTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<UseType> getUseTypes() {
        return getUseTypes(EntityPermission.READ_ONLY);
    }
    
    public List<UseType> getUseTypesForUpdate() {
        return getUseTypes(EntityPermission.READ_WRITE);
    }

    public UseType getUseTypeByName(String useTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UseTypes.fields())
                .from(UseTypes)
                .join(UseTypeDetails).onKey(USE_TYPES_ACTIVE_DETAIL_FK)
                .where(UseTypeDetails.USE_TYPE_NAME.eq(useTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return useTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public UseType getUseTypeByName(String useTypeName) {
        return getUseTypeByName(useTypeName, EntityPermission.READ_ONLY);
    }
    
    public UseType getUseTypeByNameForUpdate(String useTypeName) {
        return getUseTypeByName(useTypeName, EntityPermission.READ_WRITE);
    }
    
    public UseTypeDetailValue getUseTypeDetailValueForUpdate(UseType useType) {
        return useType == null? null: useType.getLastDetailForUpdate().getUseTypeDetailValue().clone();
    }
    
    public UseTypeDetailValue getUseTypeDetailValueByNameForUpdate(String useTypeName) {
        return getUseTypeDetailValueForUpdate(getUseTypeByNameForUpdate(useTypeName));
    }
    
    public UseType getDefaultUseType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UseTypes.fields())
                .from(UseTypes)
                .join(UseTypeDetails).onKey(USE_TYPES_ACTIVE_DETAIL_FK)
                .where(UseTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return useTypeFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public UseType getDefaultUseType() {
        return getDefaultUseType(EntityPermission.READ_ONLY);
    }
    
    public UseType getDefaultUseTypeForUpdate() {
        return getDefaultUseType(EntityPermission.READ_WRITE);
    }
    
    public UseTypeDetailValue getDefaultUseTypeDetailValueForUpdate() {
        return getDefaultUseTypeForUpdate().getLastDetailForUpdate().getUseTypeDetailValue().clone();
    }
    
    public List<UseTypeTransfer> getUseTypeTransfers(UserVisit userVisit, Collection<UseType> useTypes) {
        List<UseTypeTransfer> useTypeTransfers = new ArrayList<>(useTypes.size());
        
        useTypes.forEach((useType) -> {
            useTypeTransfers.add(useTypeTransferCache.getUseTypeTransfer(userVisit, useType));
        });
        
        return useTypeTransfers;
    }
    
    public List<UseTypeTransfer> getUseTypeTransfers(UserVisit userVisit) {
        return getUseTypeTransfers(userVisit, getUseTypes());
    }
    
    public UseTypeTransfer getUseTypeTransfer(UserVisit userVisit, UseType useType) {
        return useTypeTransferCache.getUseTypeTransfer(userVisit, useType);
    }
    
    public UseTypeChoicesBean getUseTypeChoices(String defaultUseTypeChoice, Language language, boolean allowNullChoice) {
        var useTypes = getUseTypes();
        var size = useTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;
        
        if(allowNullChoice) {
            labels.add("");
            values.add("");
            
            if(defaultUseTypeChoice == null) {
                defaultValue = "";
            }
        }
        
        for(var useType : useTypes) {
            var useTypeDetail = useType.getLastDetail();
            
            var label = getBestUseTypeDescription(useType, language);
            var value = useTypeDetail.getUseTypeName();
            
            labels.add(label == null? value: label);
            values.add(value);
            
            var usingDefaultChoice = defaultUseTypeChoice != null && defaultUseTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && useTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }
        
        return new UseTypeChoicesBean(labels, values, defaultValue);
    }
    
    private void updateUseTypeFromValue(UseTypeDetailValue useTypeDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(useTypeDetailValue.hasBeenModified()) {
            var useType = useTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     useTypeDetailValue.getUseTypePK());
            var useTypeDetail = useType.getActiveDetailForUpdate();
            
            useTypeDetail.setThruTime(session.getStartTime());
            useTypeDetail.store();

            var useTypePK = useTypeDetail.getUseTypePK();
            var useTypeName = useTypeDetailValue.getUseTypeName();
            var isDefault = useTypeDetailValue.getIsDefault();
            var sortOrder = useTypeDetailValue.getSortOrder();
            
            if(checkDefault) {
                var defaultUseType = getDefaultUseType();
                var defaultFound = defaultUseType != null && !defaultUseType.equals(useType);
                
                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultUseTypeDetailValue = getDefaultUseTypeDetailValueForUpdate();
                    
                    defaultUseTypeDetailValue.setIsDefault(false);
                    updateUseTypeFromValue(defaultUseTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }
            
            useTypeDetail = useTypeDetailFactory.create(useTypePK, useTypeName, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);
            
            useType.setActiveDetail(useTypeDetail);
            useType.setLastDetail(useTypeDetail);
            
            sendEvent(useTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }
    
    public void updateUseTypeFromValue(UseTypeDetailValue useTypeDetailValue, BasePK updatedBy) {
        updateUseTypeFromValue(useTypeDetailValue, true, updatedBy);
    }
    
    public void deleteUseType(UseType useType, BasePK deletedBy) {

        deleteUseTypeDescriptionsByUseType(useType, deletedBy);
        useControl.deleteUsesByUseType(useType, deletedBy);

        var useTypeDetail = useType.getLastDetailForUpdate();
        useTypeDetail.setThruTime(session.getStartTime());
        useType.setActiveDetail(null);
        useType.store();
        
        // Check for default, and pick one if necessary
        var defaultUseType = getDefaultUseType();
        if(defaultUseType == null) {
            var useTypes = getUseTypesForUpdate();
            
            if(!useTypes.isEmpty()) {
                var iter = useTypes.iterator();
                if(iter.hasNext()) {
                    defaultUseType = iter.next();
                }
                var useTypeDetailValue = Objects.requireNonNull(defaultUseType).getLastDetailForUpdate().getUseTypeDetailValue().clone();
                
                useTypeDetailValue.setIsDefault(true);
                updateUseTypeFromValue(useTypeDetailValue, false, deletedBy);
            }
        }
        
        sendEvent(useType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }
    
    // --------------------------------------------------------------------------------
    //   Use Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private UseTypeDescriptionFactory useTypeDescriptionFactory;

    @Inject
    private UseTypeDescriptionTransferCache useTypeDescriptionTransferCache;

    public UseTypeDescription createUseTypeDescription(UseType useType, Language language, String description, BasePK createdBy) {
        var useTypeDescription = useTypeDescriptionFactory.create(useType, language,
                description, session.getStartTime(), Session.MAX_TIME);
        
        sendEvent(useType.getPrimaryKey(), EventTypes.MODIFY, useTypeDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);
        
        return useTypeDescription;
    }
    
    private UseTypeDescription getUseTypeDescription(UseType useType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UseTypeDescriptions.fields())
                .from(UseTypeDescriptions)
                .where(UseTypeDescriptions.USE_TYPE.eq(useType.getPrimaryKey()),
                        UseTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        UseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return useTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }
    
    public UseTypeDescription getUseTypeDescription(UseType useType, Language language) {
        return getUseTypeDescription(useType, language, EntityPermission.READ_ONLY);
    }
    
    public UseTypeDescription getUseTypeDescriptionForUpdate(UseType useType, Language language) {
        return getUseTypeDescription(useType, language, EntityPermission.READ_WRITE);
    }
    
    public UseTypeDescriptionValue getUseTypeDescriptionValue(UseTypeDescription useTypeDescription) {
        return useTypeDescription == null? null: useTypeDescription.getUseTypeDescriptionValue().clone();
    }
    
    public UseTypeDescriptionValue getUseTypeDescriptionValueForUpdate(UseType useType, Language language) {
        return getUseTypeDescriptionValue(getUseTypeDescriptionForUpdate(useType, language));
    }
    
    private List<UseTypeDescription> getUseTypeDescriptionsByUseType(UseType useType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(UseTypeDescriptions.fields())
                    .from(UseTypeDescriptions)
                    .join(Languages).onKey(USE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(UseTypeDescriptions.USE_TYPE.eq(useType.getPrimaryKey()),
                            UseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    UseTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(UseTypeDescriptions.fields())
                    .from(UseTypeDescriptions)
                    .where(UseTypeDescriptions.USE_TYPE.eq(useType.getPrimaryKey()),
                            UseTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return useTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<UseTypeDescription> getUseTypeDescriptionsByUseType(UseType useType) {
        return getUseTypeDescriptionsByUseType(useType, EntityPermission.READ_ONLY);
    }
    
    public List<UseTypeDescription> getUseTypeDescriptionsByUseTypeForUpdate(UseType useType) {
        return getUseTypeDescriptionsByUseType(useType, EntityPermission.READ_WRITE);
    }
    
    public String getBestUseTypeDescription(UseType useType, Language language) {
        String description;
        var useTypeDescription = getUseTypeDescription(useType, language);
        
        if(useTypeDescription == null && !language.getIsDefault()) {
            useTypeDescription = getUseTypeDescription(useType, partyControl.getDefaultLanguage());
        }
        
        if(useTypeDescription == null) {
            description = useType.getLastDetail().getUseTypeName();
        } else {
            description = useTypeDescription.getDescription();
        }
        
        return description;
    }
    
    public UseTypeDescriptionTransfer getUseTypeDescriptionTransfer(UserVisit userVisit, UseTypeDescription useTypeDescription) {
        return useTypeDescriptionTransferCache.getUseTypeDescriptionTransfer(userVisit, useTypeDescription);
    }
    
    public List<UseTypeDescriptionTransfer> getUseTypeDescriptionTransfersByUseType(UserVisit userVisit, UseType useType) {
        var useTypeDescriptions = getUseTypeDescriptionsByUseType(useType);
        List<UseTypeDescriptionTransfer> useTypeDescriptionTransfers = new ArrayList<>(useTypeDescriptions.size());
        
        useTypeDescriptions.forEach((useTypeDescription) -> {
            useTypeDescriptionTransfers.add(useTypeDescriptionTransferCache.getUseTypeDescriptionTransfer(userVisit, useTypeDescription));
        });
        
        return useTypeDescriptionTransfers;
    }
    
    public void updateUseTypeDescriptionFromValue(UseTypeDescriptionValue useTypeDescriptionValue, BasePK updatedBy) {
        if(useTypeDescriptionValue.hasBeenModified()) {
            var useTypeDescription = useTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     useTypeDescriptionValue.getPrimaryKey());
            
            useTypeDescription.setThruTime(session.getStartTime());
            useTypeDescription.store();

            var useType = useTypeDescription.getUseType();
            var language = useTypeDescription.getLanguage();
            var description = useTypeDescriptionValue.getDescription();
            
            useTypeDescription = useTypeDescriptionFactory.create(useType, language, description,
                    session.getStartTime(), Session.MAX_TIME);
            
            sendEvent(useType.getPrimaryKey(), EventTypes.MODIFY, useTypeDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }
    
    public void deleteUseTypeDescription(UseTypeDescription useTypeDescription, BasePK deletedBy) {
        useTypeDescription.setThruTime(session.getStartTime());
        
        sendEvent(useTypeDescription.getUseTypePK(), EventTypes.MODIFY,
                useTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }
    
    public void deleteUseTypeDescriptionsByUseType(UseType useType, BasePK deletedBy) {
        var useTypeDescriptions = getUseTypeDescriptionsByUseTypeForUpdate(useType);
        
        useTypeDescriptions.forEach((useTypeDescription) -> 
                deleteUseTypeDescription(useTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Use Type Searches
    // --------------------------------------------------------------------------------

    private List<Long> getUseTypeIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
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

    public List<UseTypeResultTransfer> getUseTypeResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var entityIds = getUseTypeIdsFromUserVisitSearch(userVisitSearch);
        var useTypeResultTransfers = new ArrayList<UseTypeResultTransfer>(entityIds.size());
        var options = session.getOptions();
        var includeUseType = options != null && options.contains(SearchOptions.UseTypeResultIncludeUseType);

        for(var entityUniqueId : entityIds) {
            var useType = useTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, new UseTypePK(entityUniqueId));
            var useTypeDetail = useType.getLastDetail();

            useTypeResultTransfers.add(new UseTypeResultTransfer(useTypeDetail.getUseTypeName(),
                    includeUseType ? getUseTypeTransfer(userVisit, useType) : null));
        }

        return useTypeResultTransfers;
    }
}
