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
import com.echothree.model.control.offer.common.choice.UseChoicesBean;
import com.echothree.model.control.offer.common.transfer.UseDescriptionTransfer;
import com.echothree.model.control.offer.common.transfer.UseResultTransfer;
import com.echothree.model.control.offer.common.transfer.UseTransfer;
import com.echothree.model.control.offer.server.transfer.UseDescriptionTransferCache;
import com.echothree.model.control.offer.server.transfer.UseTransferCache;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.offer.common.pk.UsePK;
import com.echothree.model.data.offer.server.entity.Use;
import com.echothree.model.data.offer.server.entity.UseDescription;
import com.echothree.model.data.offer.server.entity.UseType;
import com.echothree.model.data.offer.server.factory.UseDescriptionFactory;
import com.echothree.model.data.offer.server.factory.UseDetailFactory;
import com.echothree.model.data.offer.server.factory.UseFactory;
import com.echothree.model.data.offer.server.value.UseDescriptionValue;
import com.echothree.model.data.offer.server.value.UseDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.USES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.offer.OfferForeignKeys.USE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.offer.UseDescriptions.UseDescriptions;
import static com.echothree.model.jooq.server.tables.offer.UseDetails.UseDetails;
import static com.echothree.model.jooq.server.tables.offer.Uses.Uses;
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
public class UseControl
        extends BaseModelControl {

    @Inject
    protected OfferUseControl offerUseControl;

    @Inject
    private SearchControl searchControl;

    /** Creates a new instance of UseControl */
    protected UseControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Uses
    // --------------------------------------------------------------------------------

    @Inject
    protected UseDetailFactory useDetailFactory;

    @Inject
    private UseFactory useFactory;

    @Inject
    private UseTransferCache useTransferCache;

    public Use createUse(String useName, UseType useType, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultUse = getDefaultUse();
        var defaultFound = defaultUse != null;

        if(defaultFound && isDefault) {
            var defaultUseDetailValue = getDefaultUseDetailValueForUpdate();

            defaultUseDetailValue.setIsDefault(false);
            updateUseFromValue(defaultUseDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var use = useFactory.create();
        var useDetail = useDetailFactory.create(use, useName, useType, isDefault, sortOrder,
                session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        use = useFactory.getEntityFromPK(EntityPermission.READ_WRITE, use.getPrimaryKey());
        use.setActiveDetail(useDetail);
        use.setLastDetail(useDetail);
        use.store();

        sendEvent(use.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return use;
    }

    public long countUses() {
        return session.getDslContext()
                .selectCount()
                .from(Uses)
                .join(UseDetails).onKey(USES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countUsesByUseType(final UseType useType) {
        return session.getDslContext()
                .selectCount()
                .from(Uses)
                .join(UseDetails).onKey(USES_ACTIVE_DETAIL_FK)
                .where(UseDetails.USE_TYPE.eq(useType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.Use */
    public Use getUseByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new UsePK(entityInstance.getEntityUniqueId());
        var use = useFactory.getEntityFromPK(entityPermission, pk);

        return use;
    }

    public Use getUseByEntityInstance(EntityInstance entityInstance) {
        return getUseByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public Use getUseByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getUseByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    private List<Use> getUses(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Uses.fields())
                .from(Uses)
                .join(UseDetails).onKey(USES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(UseDetails.SORT_ORDER, UseDetails.USE_NAME),
                    UseFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return useFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Use> getUses() {
        return getUses(EntityPermission.READ_ONLY);
    }

    public List<Use> getUsesForUpdate() {
        return getUses(EntityPermission.READ_WRITE);
    }

    public Use getUseByName(String useName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Uses.fields())
                .from(Uses)
                .join(UseDetails).onKey(USES_ACTIVE_DETAIL_FK)
                .where(UseDetails.USE_NAME.eq(useName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return useFactory.getEntityFromQuery(entityPermission, query);
    }

    public Use getUseByName(String useName) {
        return getUseByName(useName, EntityPermission.READ_ONLY);
    }

    public Use getUseByNameForUpdate(String useName) {
        return getUseByName(useName, EntityPermission.READ_WRITE);
    }

    public UseDetailValue getUseDetailValueForUpdate(Use use) {
        return use == null? null: use.getLastDetailForUpdate().getUseDetailValue().clone();
    }

    public UseDetailValue getUseDetailValueByNameForUpdate(String useName) {
        return getUseDetailValueForUpdate(getUseByNameForUpdate(useName));
    }

    public Use getDefaultUse(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Uses.fields())
                .from(Uses)
                .join(UseDetails).onKey(USES_ACTIVE_DETAIL_FK)
                .where(UseDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return useFactory.getEntityFromQuery(entityPermission, query);
    }

    public Use getDefaultUse() {
        return getDefaultUse(EntityPermission.READ_ONLY);
    }

    public Use getDefaultUseForUpdate() {
        return getDefaultUse(EntityPermission.READ_WRITE);
    }

    public UseDetailValue getDefaultUseDetailValueForUpdate() {
        return getDefaultUseForUpdate().getLastDetailForUpdate().getUseDetailValue().clone();
    }

    private List<Use> getUsesByUseType(UseType useType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(Uses.fields())
                .from(Uses)
                .join(UseDetails).onKey(USES_ACTIVE_DETAIL_FK)
                .where(UseDetails.USE_TYPE.eq(useType.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(UseDetails.SORT_ORDER, UseDetails.USE_NAME),
                    UseFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return useFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<Use> getUsesByUseType(UseType useType) {
        return getUsesByUseType(useType, EntityPermission.READ_ONLY);
    }

    public List<Use> getUsesByUseTypeForUpdate(UseType useType) {
        return getUsesByUseType(useType, EntityPermission.READ_WRITE);
    }

    public List<UseTransfer> getUseTransfers(UserVisit userVisit, Collection<Use> uses) {
        List<UseTransfer> useTransfers = new ArrayList<>(uses.size());

        uses.forEach((use) -> {
            useTransfers.add(useTransferCache.getUseTransfer(userVisit, use));
        });

        return useTransfers;
    }

    public List<UseTransfer> getUseTransfers(UserVisit userVisit) {
        return getUseTransfers(userVisit, getUses());
    }

    public UseTransfer getUseTransfer(UserVisit userVisit, Use use) {
        return useTransferCache.getUseTransfer(userVisit, use);
    }

    public UseChoicesBean getUseChoices(String defaultUseChoice, Language language, boolean allowNullChoice) {
        var uses = getUses();
        var size = uses.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultUseChoice == null) {
                defaultValue = "";
            }
        }

        for(var use : uses) {
            var useDetail = use.getLastDetail();

            var label = getBestUseDescription(use, language);
            var value = useDetail.getUseName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultUseChoice != null && defaultUseChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && useDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new UseChoicesBean(labels, values, defaultValue);
    }

    private void updateUseFromValue(UseDetailValue useDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(useDetailValue.hasBeenModified()) {
            var use = useFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    useDetailValue.getUsePK());
            var useDetail = use.getActiveDetailForUpdate();

            useDetail.setThruTime(session.getStartTime());
            useDetail.store();

            var usePK = useDetail.getUsePK();
            var useName = useDetailValue.getUseName();
            var useTypePK = useDetailValue.getUseTypePK();
            var isDefault = useDetailValue.getIsDefault();
            var sortOrder = useDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultUse = getDefaultUse();
                var defaultFound = defaultUse != null && !defaultUse.equals(use);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultUseDetailValue = getDefaultUseDetailValueForUpdate();

                    defaultUseDetailValue.setIsDefault(false);
                    updateUseFromValue(defaultUseDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            useDetail = useDetailFactory.create(usePK, useName, useTypePK, isDefault, sortOrder,
                    session.getStartTime(), Session.MAX_TIME);

            use.setActiveDetail(useDetail);
            use.setLastDetail(useDetail);

            sendEvent(usePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateUseFromValue(UseDetailValue useDetailValue, BasePK updatedBy) {
        updateUseFromValue(useDetailValue, true, updatedBy);
    }

    public void deleteUse(Use use, BasePK deletedBy) {
        var useControl = this;

        useControl.deleteUseDescriptionsByUse(use, deletedBy);
        offerUseControl.deleteOfferUsesByUse(use, deletedBy);

        var useDetail = use.getLastDetailForUpdate();
        useDetail.setThruTime(session.getStartTime());
        use.setActiveDetail(null);
        use.store();

        // Check for default, and pick one if necessary
        var defaultUse = getDefaultUse();
        if(defaultUse == null) {
            var uses = getUsesForUpdate();

            if(!uses.isEmpty()) {
                var iter = uses.iterator();
                if(iter.hasNext()) {
                    defaultUse = iter.next();
                }
                var useDetailValue = Objects.requireNonNull(defaultUse).getLastDetailForUpdate().getUseDetailValue().clone();

                useDetailValue.setIsDefault(true);
                updateUseFromValue(useDetailValue, false, deletedBy);
            }
        }

        sendEvent(use.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteUses(List<Use> uses, BasePK deletedBy) {
        uses.forEach((use) -> 
                deleteUse(use, deletedBy)
        );
    }

    public void deleteUsesByUseType(UseType useType, BasePK deletedBy) {
        deleteUses(getUsesByUseTypeForUpdate(useType), deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Use Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private UseDescriptionFactory useDescriptionFactory;

    @Inject
    private UseDescriptionTransferCache useDescriptionTransferCache;

    public UseDescription createUseDescription(Use use, Language language, String description, BasePK createdBy) {
        var useDescription = useDescriptionFactory.create(use, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(use.getPrimaryKey(), EventTypes.MODIFY, useDescription.getPrimaryKey(),
                EventTypes.CREATE, createdBy);

        return useDescription;
    }

    private UseDescription getUseDescription(Use use, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(UseDescriptions.fields())
                .from(UseDescriptions)
                .where(UseDescriptions.USE.eq(use.getPrimaryKey()),
                        UseDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        UseDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return useDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public UseDescription getUseDescription(Use use, Language language) {
        return getUseDescription(use, language, EntityPermission.READ_ONLY);
    }

    public UseDescription getUseDescriptionForUpdate(Use use, Language language) {
        return getUseDescription(use, language, EntityPermission.READ_WRITE);
    }

    public UseDescriptionValue getUseDescriptionValue(UseDescription useDescription) {
        return useDescription == null? null: useDescription.getUseDescriptionValue().clone();
    }

    public UseDescriptionValue getUseDescriptionValueForUpdate(Use use, Language language) {
        return getUseDescriptionValue(getUseDescriptionForUpdate(use, language));
    }

    private List<UseDescription> getUseDescriptionsByUse(Use use, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(UseDescriptions.fields())
                    .from(UseDescriptions)
                    .join(Languages).onKey(USE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(UseDescriptions.USE.eq(use.getPrimaryKey()),
                            UseDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    UseDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(UseDescriptions.fields())
                    .from(UseDescriptions)
                    .where(UseDescriptions.USE.eq(use.getPrimaryKey()),
                            UseDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return useDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<UseDescription> getUseDescriptionsByUse(Use use) {
        return getUseDescriptionsByUse(use, EntityPermission.READ_ONLY);
    }

    public List<UseDescription> getUseDescriptionsByUseForUpdate(Use use) {
        return getUseDescriptionsByUse(use, EntityPermission.READ_WRITE);
    }

    public String getBestUseDescription(Use use, Language language) {
        String description;
        var useDescription = getUseDescription(use, language);

        if(useDescription == null && !language.getIsDefault()) {
            useDescription = getUseDescription(use, partyControl.getDefaultLanguage());
        }

        if(useDescription == null) {
            description = use.getLastDetail().getUseName();
        } else {
            description = useDescription.getDescription();
        }

        return description;
    }

    public UseDescriptionTransfer getUseDescriptionTransfer(UserVisit userVisit, UseDescription useDescription) {
        return useDescriptionTransferCache.getUseDescriptionTransfer(userVisit, useDescription);
    }

    public List<UseDescriptionTransfer> getUseDescriptionTransfers(UserVisit userVisit, Use use) {
        var useDescriptions = getUseDescriptionsByUse(use);
        List<UseDescriptionTransfer> useDescriptionTransfers = new ArrayList<>(useDescriptions.size());

        useDescriptions.forEach((useDescription) -> {
            useDescriptionTransfers.add(useDescriptionTransferCache.getUseDescriptionTransfer(userVisit, useDescription));
        });

        return useDescriptionTransfers;
    }

    public void updateUseDescriptionFromValue(UseDescriptionValue useDescriptionValue, BasePK updatedBy) {
        if(useDescriptionValue.hasBeenModified()) {
            var useDescription = useDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    useDescriptionValue.getPrimaryKey());

            useDescription.setThruTime(session.getStartTime());
            useDescription.store();

            var use = useDescription.getUse();
            var language = useDescription.getLanguage();
            var description = useDescriptionValue.getDescription();

            useDescription = useDescriptionFactory.create(use, language, description, session.getStartTime(), Session.MAX_TIME);

            sendEvent(use.getPrimaryKey(), EventTypes.MODIFY, useDescription.getPrimaryKey(),
                    EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteUseDescription(UseDescription useDescription, BasePK deletedBy) {
        useDescription.setThruTime(session.getStartTime());

        sendEvent(useDescription.getUsePK(), EventTypes.MODIFY,
                useDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteUseDescriptionsByUse(Use use, BasePK deletedBy) {
        var useDescriptions = getUseDescriptionsByUseForUpdate(use);

        useDescriptions.forEach((useDescription) -> 
                deleteUseDescription(useDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Use Searches
    // --------------------------------------------------------------------------------

    @Inject
    private SearchResultFactory searchResultFactory;

    private List<Long> getUseIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
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

    public List<UseResultTransfer> getUseResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var entityIds = getUseIdsFromUserVisitSearch(userVisitSearch);
        var useResultTransfers = new ArrayList<UseResultTransfer>(entityIds.size());
        var options = session.getOptions();
        var includeUse = options != null && options.contains(SearchOptions.UseResultIncludeUse);

        for(var entityUniqueId : entityIds) {
            var use = useFactory.getEntityFromPK(EntityPermission.READ_ONLY, new UsePK(entityUniqueId));
            var useDetail = use.getLastDetail();

            useResultTransfers.add(new UseResultTransfer(useDetail.getUseName(),
                    includeUse ? getUseTransfer(userVisit, use) : null));
        }

        return useResultTransfers;
    }

}
