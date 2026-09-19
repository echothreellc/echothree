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

import com.echothree.model.control.accounting.server.control.AccountingControl;
import com.echothree.model.control.batch.server.control.BatchControl;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.transfer.EntityTypeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityTypeResultTransfer;
import com.echothree.model.control.core.common.transfer.EntityTypeTransfer;
import com.echothree.model.control.core.server.graphql.EntityTypeObject;
import com.echothree.model.control.core.server.transfer.EntityTypeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityTypeTransferCache;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.message.server.control.MessageControl;
import com.echothree.model.control.rating.server.control.RatingControl;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.core.common.pk.EntityTypePK;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDescription;
import com.echothree.model.data.core.server.factory.EntityTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityTypeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityTypeFactory;
import com.echothree.model.data.core.server.value.EntityTypeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityTypeDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDescriptions.EntityTypeDescriptions;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.jooq.Select;

@CommandScope
public class EntityTypeControl
        extends BaseModelControl {

    @Inject
    protected AccountingControl accountingControl;

    @Inject
    protected BatchControl batchControl;

    @Inject
    protected CommentControl commentControl;

    @Inject
    protected CoreControl coreControl;

    @Inject
    protected EntityAliasControl entityAliasControl;

    @Inject
    protected EntityAttributeGroupControl entityAttributeGroupControl;

    @Inject
    protected IndexControl indexControl;

    @Inject
    protected MessageControl messageControl;

    @Inject
    protected RatingControl ratingControl;

    @Inject
    protected TagControl tagControl;

    /** Creates a new instance of EntityTypeControl */
    protected EntityTypeControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Entity Types
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityTypeDetailFactory entityTypeDetailFactory;

    @Inject
    protected EntityTypeFactory entityTypeFactory;

    @Inject
    EntityTypeTransferCache entityTypeTransferCache;

    public EntityType createEntityType(ComponentVendor componentVendor, String entityTypeName, Boolean keepAllHistory,
            Long lockTimeout, Boolean isExtensible, Integer sortOrder, BasePK createdBy) {
        var entityType = entityTypeFactory.create();
        var entityTypeDetail = entityTypeDetailFactory.create(entityType, componentVendor,
                entityTypeName, keepAllHistory, lockTimeout, isExtensible, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        entityType = entityTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, entityType.getPrimaryKey());
        entityType.setActiveDetail(entityTypeDetail);
        entityType.setLastDetail(entityTypeDetail);
        entityType.store();

        sendEvent(entityType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return entityType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityType */
    public EntityType getEntityTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityTypePK(entityInstance.getEntityUniqueId());

        return entityTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public EntityType getEntityTypeByEntityInstance(EntityInstance entityInstance) {
        return getEntityTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityType getEntityTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countEntityTypes() {
        return session.getDslContext()
                .selectCount()
                .from(EntityTypes)
                .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEntityTypesByComponentVendor(ComponentVendor componentVendor) {
        return session.getDslContext()
                .selectCount()
                .from(EntityTypes)
                .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                .where(EntityTypeDetails.COMPONENT_VENDOR.eq(componentVendor.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public EntityType getEntityTypeByName(ComponentVendor componentVendor, String entityTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityTypes.fields())
                .from(EntityTypes)
                .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                .where(EntityTypeDetails.COMPONENT_VENDOR.eq(componentVendor.getPrimaryKey()),
                        EntityTypeDetails.ENTITY_TYPE_NAME.eq(entityTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public EntityType getEntityTypeByName(ComponentVendor componentVendor, String entityTypeName) {
        return getEntityTypeByName(componentVendor, entityTypeName, EntityPermission.READ_ONLY);
    }

    public EntityType getEntityTypeByNameForUpdate(ComponentVendor componentVendor, String entityTypeName) {
        return getEntityTypeByName(componentVendor, entityTypeName, EntityPermission.READ_WRITE);
    }

    public EntityTypeDetailValue getEntityTypeDetailValueForUpdate(EntityType entityType) {
        return entityType == null? null: entityType.getLastDetailForUpdate().getEntityTypeDetailValue().clone();
    }

    public EntityTypeDetailValue getEntityTypeDetailValueByNameForUpdate(ComponentVendor componentVendor, String entityTypeName) {
        return getEntityTypeDetailValueForUpdate(getEntityTypeByNameForUpdate(componentVendor, entityTypeName));
    }

    private final Map<ComponentVendor, Map<String, EntityType>> entityTypeCache = new HashMap<>();

    public EntityType getEntityTypeByNameFromCache(ComponentVendor componentVendor, String entityTypeName) {
        var cacheByComponentVendor = entityTypeCache.computeIfAbsent(componentVendor, k -> new HashMap<>());

        var entityType = cacheByComponentVendor.get(entityTypeName);

        if(entityType == null) {
            entityType = getEntityTypeByName(componentVendor, entityTypeName);

            if(entityType != null) {
                cacheByComponentVendor.put(entityTypeName, entityType);
            }
        }

        return entityType;
    }

    private List<EntityType> getEntityTypesByComponentVendor(ComponentVendor componentVendor, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityTypes.fields())
                    .from(EntityTypes)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                    .where(EntityTypeDetails.COMPONENT_VENDOR.eq(componentVendor.getPrimaryKey()))
                    .orderBy(EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME),
                    EntityTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityTypes.fields())
                    .from(EntityTypes)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                    .where(EntityTypeDetails.COMPONENT_VENDOR.eq(componentVendor.getPrimaryKey()))
                    .forUpdate();
        };

        return entityTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityType> getEntityTypesByComponentVendor(ComponentVendor componentVendor) {
        return getEntityTypesByComponentVendor(componentVendor, EntityPermission.READ_ONLY);
    }

    public List<EntityType> getEntityTypesByComponentVendorForUpdate(ComponentVendor componentVendor) {
        return getEntityTypesByComponentVendor(componentVendor, EntityPermission.READ_WRITE);
    }

    private List<EntityType> getEntityTypesByName(String entityTypeName, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityTypes.fields())
                    .from(EntityTypes)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                    .where(EntityTypeDetails.ENTITY_TYPE_NAME.eq(entityTypeName))
                    .orderBy(EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME),
                    EntityTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityTypes.fields())
                    .from(EntityTypes)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                    .where(EntityTypeDetails.ENTITY_TYPE_NAME.eq(entityTypeName))
                    .forUpdate();
        };

        return entityTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityType> getEntityTypesByName(String entityTypeName) {
        return getEntityTypesByName(entityTypeName, EntityPermission.READ_ONLY);
    }

    public List<EntityType> getEntityTypesByNameForUpdate(String entityTypeName) {
        return getEntityTypesByName(entityTypeName, EntityPermission.READ_WRITE);
    }

    public List<EntityType> getEntityTypes() {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityTypes.fields())
                .from(EntityTypes)
                .join(EntityTypeDetails).onKey(ENTITY_TYPES_ACTIVE_DETAIL_FK)
                .orderBy(EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME),
                EntityTypeFactory.class);

        return entityTypeFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    public EntityTypeTransfer getEntityTypeTransfer(UserVisit userVisit, EntityType entityType) {
        return entityTypeTransferCache.getEntityTypeTransfer(userVisit, entityType);
    }

    public List<EntityTypeTransfer> getEntityTypeTransfers(UserVisit userVisit, Collection<EntityType> entityTypes) {
        List<EntityTypeTransfer> entityTypeTransfers = new ArrayList<>(entityTypes.size());

        entityTypes.forEach((entityType) ->
                entityTypeTransfers.add(entityTypeTransferCache.getEntityTypeTransfer(userVisit, entityType))
        );

        return entityTypeTransfers;
    }

    public List<EntityTypeTransfer> getEntityTypeTransfers(UserVisit userVisit) {
        return getEntityTypeTransfers(userVisit, getEntityTypes());
    }

    public List<EntityTypeTransfer> getEntityTypeTransfersByComponentVendor(UserVisit userVisit, ComponentVendor componentVendor) {
        return getEntityTypeTransfers(userVisit, getEntityTypesByComponentVendor(componentVendor));
    }

    public void updateEntityTypeFromValue(EntityTypeDetailValue entityTypeDetailValue, BasePK updatedBy) {
        if(entityTypeDetailValue.hasBeenModified()) {
            var entityType = entityTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    entityTypeDetailValue.getEntityTypePK());
            var entityTypeDetail = entityType.getActiveDetailForUpdate();

            entityTypeDetail.setThruTime(session.getStartTime());
            entityTypeDetail.store();

            var entityTypePK = entityTypeDetail.getEntityTypePK();
            var componentVendorPK = entityTypeDetail.getComponentVendorPK(); // Not updated
            var entityTypeName = entityTypeDetailValue.getEntityTypeName();
            var keepAllHistory = entityTypeDetailValue.getKeepAllHistory();
            var lockTimeout = entityTypeDetailValue.getLockTimeout();
            var isExtensible = entityTypeDetailValue.getIsExtensible();
            var sortOrder = entityTypeDetailValue.getSortOrder();

            entityTypeDetail = entityTypeDetailFactory.create(entityTypePK, componentVendorPK, entityTypeName,
                    keepAllHistory, lockTimeout, isExtensible, sortOrder, session.getStartTime(), Session.MAX_TIME);

            entityType.setActiveDetail(entityTypeDetail);
            entityType.setLastDetail(entityTypeDetail);

            sendEvent(entityTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public EntityType getEntityTypeByPK(EntityTypePK entityTypePK) {
        return entityTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, entityTypePK);
    }

    public void deleteEntityType(EntityType entityType, BasePK deletedBy) {
        entityInstanceControl.deleteEntityInstancesByEntityTypeWithNullDeletedTime(entityType, deletedBy);
        deleteEntityTypeDescriptionsByEntityType(entityType, deletedBy);
        entityAliasControl.deleteEntityAliasTypesByEntityType(entityType, deletedBy);
        entityAttributeGroupControl.deleteEntityAttributeGroupsByEntityType(entityType, deletedBy);
        coreControl.deleteEntityAttributesByEntityType(entityType, deletedBy);
        coreControl.deleteEntityAttributeEntityTypesByAllowedEntityType(entityType, deletedBy);
        accountingControl.deleteTransactionEntityRoleTypesByEntityType(entityType, deletedBy);
        batchControl.deleteBatchTypeEntityTypesByEntityType(entityType, deletedBy);
        commentControl.deleteCommentTypesByEntityType(entityType, deletedBy);
        indexControl.deleteIndexTypesByEntityType(entityType, deletedBy);
        messageControl.deleteMessageTypesByEntityType(entityType, deletedBy);
        ratingControl.deleteRatingTypesByEntityType(entityType, deletedBy);
        tagControl.deleteTagScopeEntityTypesByEntityType(entityType, deletedBy);
        workflowControl.deleteWorkflowEntityTypesByEntityType(entityType, deletedBy);

        var entityTypeDetail = entityType.getLastDetailForUpdate();
        entityTypeDetail.setThruTime(session.getStartTime());
        entityType.setActiveDetail(null);
        entityType.store();

        sendEvent(entityType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteEntityTypesByComponentVendor(ComponentVendor componentVendor, BasePK deletedBy) {
        var entityTypes = getEntityTypesByComponentVendorForUpdate(componentVendor);

        entityTypes.forEach((entityType) ->
                deleteEntityType(entityType, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Entity Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityTypeDescriptionFactory entityTypeDescriptionFactory;

    @Inject
    EntityTypeDescriptionTransferCache entityTypeDescriptionTransferCache;

    public EntityTypeDescription createEntityTypeDescription(EntityType entityType, Language language, String description,
            BasePK createdBy) {
        var entityTypeDescription = entityTypeDescriptionFactory.create(entityType,
                language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityType.getPrimaryKey(), EventTypes.MODIFY, entityTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityTypeDescription;
    }

    private EntityTypeDescription getEntityTypeDescription(EntityType entityType, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityTypeDescriptions.fields())
                .from(EntityTypeDescriptions)
                .where(EntityTypeDescriptions.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        EntityTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        EntityTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public EntityTypeDescription getEntityTypeDescription(EntityType entityType, Language language) {
        return getEntityTypeDescription(entityType, language, EntityPermission.READ_ONLY);
    }

    public EntityTypeDescription getEntityTypeDescriptionForUpdate(EntityType entityType, Language language) {
        return getEntityTypeDescription(entityType, language, EntityPermission.READ_WRITE);
    }

    public EntityTypeDescriptionValue getEntityTypeDescriptionValue(EntityTypeDescription entityTypeDescription) {
        return entityTypeDescription == null? null: entityTypeDescription.getEntityTypeDescriptionValue().clone();
    }

    public EntityTypeDescriptionValue getEntityTypeDescriptionValueForUpdate(EntityType entityType, Language language) {
        return getEntityTypeDescriptionValue(getEntityTypeDescriptionForUpdate(entityType, language));
    }

    private List<EntityTypeDescription> getEntityTypeDescriptionsByEntityType(EntityType entityType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityTypeDescriptions.fields())
                    .from(EntityTypeDescriptions)
                    .join(Languages).onKey(ENTITY_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(EntityTypeDescriptions.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                            EntityTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    EntityTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityTypeDescriptions.fields())
                    .from(EntityTypeDescriptions)
                    .where(EntityTypeDescriptions.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                            EntityTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityTypeDescription> getEntityTypeDescriptionsByEntityType(EntityType entityType) {
        return getEntityTypeDescriptionsByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<EntityTypeDescription> getEntityTypeDescriptionsByEntityTypeForUpdate(EntityType entityType) {
        return getEntityTypeDescriptionsByEntityType(entityType, EntityPermission.READ_WRITE);
    }

    public String getBestEntityTypeDescription(EntityType entityType, Language language) {
        String description;
        var entityTypeDescription = getEntityTypeDescription(entityType, language);

        if(entityTypeDescription == null && !language.getIsDefault()) {
            entityTypeDescription = getEntityTypeDescription(entityType, partyControl.getDefaultLanguage());
        }

        if(entityTypeDescription == null) {
            description = entityType.getLastDetail().getEntityTypeName();
        } else {
            description = entityTypeDescription.getDescription();
        }

        return description;
    }

    public EntityTypeDescriptionTransfer getEntityTypeDescriptionTransfer(UserVisit userVisit, EntityTypeDescription entityTypeDescription) {
        return entityTypeDescriptionTransferCache.getEntityTypeDescriptionTransfer(userVisit, entityTypeDescription);
    }

    public List<EntityTypeDescriptionTransfer> getEntityTypeDescriptionTransfersByEntityType(UserVisit userVisit,
            EntityType entityType) {
        var entityTypeDescriptions = getEntityTypeDescriptionsByEntityType(entityType);
        List<EntityTypeDescriptionTransfer> entityTypeDescriptionTransfers = new ArrayList<>(entityTypeDescriptions.size());

        entityTypeDescriptions.forEach((entityTypeDescription) ->
                entityTypeDescriptionTransfers.add(entityTypeDescriptionTransferCache.getEntityTypeDescriptionTransfer(userVisit, entityTypeDescription))
        );

        return entityTypeDescriptionTransfers;
    }

    public void updateEntityTypeDescriptionFromValue(EntityTypeDescriptionValue entityTypeDescriptionValue, BasePK updatedBy) {
        if(entityTypeDescriptionValue.hasBeenModified()) {
            var entityTypeDescription = entityTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    entityTypeDescriptionValue.getPrimaryKey());

            entityTypeDescription.setThruTime(session.getStartTime());
            entityTypeDescription.store();

            var entityType = entityTypeDescription.getEntityType();
            var language = entityTypeDescription.getLanguage();
            var description = entityTypeDescriptionValue.getDescription();

            entityTypeDescription = entityTypeDescriptionFactory.create(entityType, language,
                    description, session.getStartTime(), Session.MAX_TIME);

            sendEvent(entityType.getPrimaryKey(), EventTypes.MODIFY, entityTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityTypeDescription(EntityTypeDescription entityTypeDescription, BasePK deletedBy) {
        entityTypeDescription.setThruTime(session.getStartTime());

        sendEvent(entityTypeDescription.getEntityTypePK(), EventTypes.MODIFY, entityTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityTypeDescriptionsByEntityType(EntityType entityType, BasePK deletedBy) {
        var entityTypeDescriptions = getEntityTypeDescriptionsByEntityTypeForUpdate(entityType);

        entityTypeDescriptions.forEach((entityTypeDescription) ->
                deleteEntityTypeDescription(entityTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Entity Type Searches
    // --------------------------------------------------------------------------------

    @Inject
    protected SearchControl searchControl;

    private List<Long> getEntityTypeIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
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

    public List<EntityTypeResultTransfer> getEntityTypeResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var entityIds = getEntityTypeIdsFromUserVisitSearch(userVisitSearch);
        var entityTypeResultTransfers = new ArrayList<EntityTypeResultTransfer>(entityIds.size());
        var options = session.getOptions();
        var includeEntityType = options != null && options.contains(SearchOptions.EntityTypeResultIncludeEntityType);

        for(var entityUniqueId : entityIds) {
            var entityType = entityTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, new EntityTypePK(entityUniqueId));
            var entityTypeDetail = entityType.getLastDetail();

            entityTypeResultTransfers.add(new EntityTypeResultTransfer(entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), includeEntityType ? getEntityTypeTransfer(userVisit, entityType) : null));
        }

        return entityTypeResultTransfers;
    }

    public List<EntityTypeObject> getEntityTypeObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var entityTypeObjects = new ArrayList<EntityTypeObject>();

        for(var entityUniqueId : getEntityTypeIdsFromUserVisitSearch(userVisitSearch)) {
            var entityType = getEntityTypeByPK(new EntityTypePK(entityUniqueId));

            entityTypeObjects.add(new EntityTypeObject(entityType));
        }

        return entityTypeObjects;
    }

}
