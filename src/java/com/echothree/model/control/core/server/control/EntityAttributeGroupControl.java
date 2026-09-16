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

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.choice.EntityAttributeGroupChoicesBean;
import com.echothree.model.control.core.common.transfer.EntityAttributeEntityAttributeGroupTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupResultTransfer;
import com.echothree.model.control.core.common.transfer.EntityAttributeGroupTransfer;
import com.echothree.model.control.core.server.graphql.EntityAttributeGroupObject;
import com.echothree.model.control.search.common.SearchOptions;
import com.echothree.model.control.search.server.control.SearchControl;
import com.echothree.model.data.core.common.pk.EntityAttributeGroupPK;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeGroupDescription;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.factory.EntityAttributeEntityAttributeGroupFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupDetailFactory;
import com.echothree.model.data.core.server.factory.EntityAttributeGroupFactory;
import com.echothree.model.data.core.server.value.EntityAttributeEntityAttributeGroupValue;
import com.echothree.model.data.core.server.value.EntityAttributeGroupDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAttributeGroupDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.search.common.CachedExecutedSearchResultConstants;
import com.echothree.model.data.search.common.SearchResultConstants;
import com.echothree.model.data.search.server.entity.UserVisitSearch;
import com.echothree.model.data.search.server.factory.CachedExecutedSearchResultFactory;
import com.echothree.model.data.search.server.factory.SearchResultFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTE_DETAILS_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTE_ENTITY_ATTRIBUTE_GROUPS_ENTITY_ATTRIBUTE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTE_ENTITY_ATTRIBUTE_GROUPS_ENTITY_ATTRIBUTE_GROUP_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTE_GROUPS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ATTRIBUTE_GROUP_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.CACHED_EXECUTED_SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.search.SearchForeignKeys.SEARCH_RESULTS_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeDetails.EntityAttributeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeEntityAttributeGroups.EntityAttributeEntityAttributeGroups;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeGroupDescriptions.EntityAttributeGroupDescriptions;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeGroupDetails.EntityAttributeGroupDetails;
import static com.echothree.model.jooq.server.tables.core.EntityAttributeGroups.EntityAttributeGroups;
import static com.echothree.model.jooq.server.tables.core.EntityAttributes.EntityAttributes;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.search.CachedExecutedSearchResults.CachedExecutedSearchResults;
import static com.echothree.model.jooq.server.tables.search.SearchResults.SearchResults;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.cdi.CommandScope;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.jooq.Condition;
import org.jooq.Select;
import static org.jooq.impl.DSL.exists;

@CommandScope
public class EntityAttributeGroupControl
        extends BaseCoreControl {

    @Inject
    protected SearchControl searchControl;

    /** Creates a new instance of EntityAttributeGroupControl */
    protected EntityAttributeGroupControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityAttributeGroupDetailFactory entityAttributeGroupDetailFactory;

    @Inject
    protected EntityAttributeGroupFactory entityAttributeGroupFactory;

    public EntityAttributeGroup createEntityAttributeGroup(String entityAttributeGroupName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultEntityAttributeGroup = getDefaultEntityAttributeGroup();
        var defaultFound = defaultEntityAttributeGroup != null;

        if(defaultFound && isDefault) {
            var defaultEntityAttributeGroupDetailValue = getDefaultEntityAttributeGroupDetailValueForUpdate();

            defaultEntityAttributeGroupDetailValue.setIsDefault(false);
            updateEntityAttributeGroupFromValue(defaultEntityAttributeGroupDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var entityAttributeGroup = entityAttributeGroupFactory.create();
        var entityAttributeGroupDetail = entityAttributeGroupDetailFactory.create(entityAttributeGroup,
                entityAttributeGroupName, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        entityAttributeGroup = entityAttributeGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                entityAttributeGroup.getPrimaryKey());
        entityAttributeGroup.setActiveDetail(entityAttributeGroupDetail);
        entityAttributeGroup.setLastDetail(entityAttributeGroupDetail);
        entityAttributeGroup.store();

        sendEvent(entityAttributeGroup.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return entityAttributeGroup;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityAttributeGroup */
    public EntityAttributeGroup getEntityAttributeGroupByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityAttributeGroupPK(entityInstance.getEntityUniqueId());

        return entityAttributeGroupFactory.getEntityFromPK(entityPermission, pk);
    }

    public EntityAttributeGroup getEntityAttributeGroupByEntityInstance(EntityInstance entityInstance) {
        return getEntityAttributeGroupByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAttributeGroup getEntityAttributeGroupByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAttributeGroupByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countEntityAttributeGroups() {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributeGroups)
                .where(EntityAttributeGroups.ACTIVE_DETAIL.isNotNull())
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEntityAttributeGroupsByEntityType(EntityType entityType) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributeGroups)
                .where(EntityAttributeGroups.LAST_DETAIL.isNotNull(), entityAttributeGroupHasEntityType(entityType))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private Condition entityAttributeGroupHasEntityType(EntityType entityType) {
        return exists(session.getDslContext()
                .selectOne()
                .from(EntityAttributeEntityAttributeGroups)
                .join(EntityAttributes).onKey(ENTITY_ATTRIBUTE_ENTITY_ATTRIBUTE_GROUPS_ENTITY_ATTRIBUTE_FK)
                .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_LAST_DETAIL_FK)
                .where(EntityAttributeEntityAttributeGroups.ENTITY_ATTRIBUTE_GROUP.eq(EntityAttributeGroups.ENTITY_ATTRIBUTE_GROUP),
                        EntityAttributeEntityAttributeGroups.THRU_TIME.eq(Session.MAX_TIME),
                        EntityAttributeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey())));
    }

    private List<EntityAttributeGroup> getEntityAttributeGroups(Condition condition, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAttributeGroups.fields())
                    .from(EntityAttributeGroups)
                    .join(EntityAttributeGroupDetails).onKey(ENTITY_ATTRIBUTE_GROUPS_LAST_DETAIL_FK)
                    .where(condition)
                    .orderBy(EntityAttributeGroupDetails.SORT_ORDER, EntityAttributeGroupDetails.ENTITY_ATTRIBUTE_GROUP_NAME),
                    EntityAttributeGroupFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAttributeGroups.fields())
                    .from(EntityAttributeGroups)
                    .where(condition)
                    .forUpdate();
        };

        return entityAttributeGroupFactory.getEntitiesFromQuery(entityPermission, query);
    }

    private List<EntityAttributeGroup> getEntityAttributeGroups(EntityPermission entityPermission) {
        return getEntityAttributeGroups(EntityAttributeGroups.ACTIVE_DETAIL.isNotNull(), entityPermission);
    }

    public List<EntityAttributeGroup> getEntityAttributeGroups() {
        return getEntityAttributeGroups(EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeGroup> getEntityAttributeGroupsForUpdate() {
        return getEntityAttributeGroups(EntityPermission.READ_WRITE);
    }

    private List<EntityAttributeGroup> getEntityAttributeGroupsByEntityType(EntityType entityType, EntityPermission entityPermission) {
        return getEntityAttributeGroups(EntityAttributeGroups.LAST_DETAIL.isNotNull()
                .and(entityAttributeGroupHasEntityType(entityType)), entityPermission);
    }

    public List<EntityAttributeGroup> getEntityAttributeGroupsByEntityType(EntityType entityType) {
        return getEntityAttributeGroupsByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeGroup> getEntityAttributeGroupsByEntityTypeForUpdate(EntityType entityType) {
        return getEntityAttributeGroupsByEntityType(entityType, EntityPermission.READ_WRITE);
    }

    private EntityAttributeGroup getEntityAttributeGroup(Condition condition, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.getDslContext()
                    .select(EntityAttributeGroups.fields())
                    .from(EntityAttributeGroups)
                    .join(EntityAttributeGroupDetails).onKey(ENTITY_ATTRIBUTE_GROUPS_LAST_DETAIL_FK)
                    .where(EntityAttributeGroups.ACTIVE_DETAIL.isNotNull(), condition);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAttributeGroups.fields())
                    .from(EntityAttributeGroups)
                    .where(EntityAttributeGroups.ACTIVE_DETAIL.in(session.getDslContext()
                            .select(EntityAttributeGroupDetails.ENTITY_ATTRIBUTE_GROUP_DETAIL)
                            .from(EntityAttributeGroupDetails)
                            .where(condition)))
                    .forUpdate();
        };

        return entityAttributeGroupFactory.getEntityFromQuery(entityPermission, query);
    }

    private EntityAttributeGroup getDefaultEntityAttributeGroup(EntityPermission entityPermission) {
        return getEntityAttributeGroup(EntityAttributeGroupDetails.IS_DEFAULT.eq(true), entityPermission);
    }

    public EntityAttributeGroup getDefaultEntityAttributeGroup() {
        return getDefaultEntityAttributeGroup(EntityPermission.READ_ONLY);
    }

    public EntityAttributeGroup getDefaultEntityAttributeGroupForUpdate() {
        return getDefaultEntityAttributeGroup(EntityPermission.READ_WRITE);
    }

    public EntityAttributeGroupDetailValue getDefaultEntityAttributeGroupDetailValueForUpdate() {
        return getDefaultEntityAttributeGroupForUpdate().getLastDetailForUpdate().getEntityAttributeGroupDetailValue().clone();
    }

    public EntityAttributeGroup getEntityAttributeGroupByName(String entityAttributeGroupName, EntityPermission entityPermission) {
        return getEntityAttributeGroup(EntityAttributeGroupDetails.ENTITY_ATTRIBUTE_GROUP_NAME.eq(entityAttributeGroupName),
                entityPermission);
    }

    public EntityAttributeGroup getEntityAttributeGroupByName(String entityAttributeGroupName) {
        return getEntityAttributeGroupByName(entityAttributeGroupName, EntityPermission.READ_ONLY);
    }

    public EntityAttributeGroup getEntityAttributeGroupByNameForUpdate(String entityAttributeGroupName) {
        return getEntityAttributeGroupByName(entityAttributeGroupName, EntityPermission.READ_WRITE);
    }

    public EntityAttributeGroupDetailValue getEntityAttributeGroupDetailValueForUpdate(EntityAttributeGroup entityAttributeGroup) {
        return entityAttributeGroup == null? null: entityAttributeGroup.getLastDetailForUpdate().getEntityAttributeGroupDetailValue().clone();
    }

    public EntityAttributeGroupDetailValue getEntityAttributeGroupDetailValueByNameForUpdate(String entityAttributeGroupName) {
        return getEntityAttributeGroupDetailValueForUpdate(getEntityAttributeGroupByNameForUpdate(entityAttributeGroupName));
    }

    public EntityAttributeGroupChoicesBean getEntityAttributeGroupChoices(String defaultEntityAttributeGroupChoice, Language language,
            boolean allowNullChoice) {
        var entityAttributeGroups = getEntityAttributeGroups();
        var size = entityAttributeGroups.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultEntityAttributeGroupChoice == null) {
                defaultValue = "";
            }
        }

        for(var entityAttributeGroup : entityAttributeGroups) {
            var entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();
            var label = getBestEntityAttributeGroupDescription(entityAttributeGroup, language);
            var value = entityAttributeGroupDetail.getEntityAttributeGroupName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultEntityAttributeGroupChoice != null && defaultEntityAttributeGroupChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityAttributeGroupDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new EntityAttributeGroupChoicesBean(labels, values, defaultValue);
    }

    public EntityAttributeGroupTransfer getEntityAttributeGroupTransfer(UserVisit userVisit, EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        return entityAttributeGroupTransferCache.getEntityAttributeGroupTransfer(userVisit, entityAttributeGroup, entityInstance);
    }

    public List<EntityAttributeGroupTransfer> getEntityAttributeGroupTransfers(UserVisit userVisit, Collection<EntityAttributeGroup> entityAttributeGroups, EntityInstance entityInstance) {
        List<EntityAttributeGroupTransfer> entityAttributeGroupTransfers = new ArrayList<>(entityAttributeGroups.size());

        entityAttributeGroups.forEach((entityAttributeGroup) ->
                entityAttributeGroupTransfers.add(entityAttributeGroupTransferCache.getEntityAttributeGroupTransfer(userVisit, entityAttributeGroup, entityInstance))
        );

        return entityAttributeGroupTransfers;
    }

    public List<EntityAttributeGroupTransfer> getEntityAttributeGroupTransfers(UserVisit userVisit, EntityInstance entityInstance) {
        return getEntityAttributeGroupTransfers(userVisit, getEntityAttributeGroups(), entityInstance);
    }

    public List<EntityAttributeGroupTransfer> getEntityAttributeGroupTransfersByEntityType(UserVisit userVisit, EntityType entityType, EntityInstance entityInstance) {
        return getEntityAttributeGroupTransfers(userVisit, getEntityAttributeGroupsByEntityType(entityType), entityInstance);
    }

    private void updateEntityAttributeGroupFromValue(EntityAttributeGroupDetailValue entityAttributeGroupDetailValue, boolean checkDefault,
            BasePK updatedBy) {
        if(entityAttributeGroupDetailValue.hasBeenModified()) {
            var entityAttributeGroup = entityAttributeGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeGroupDetailValue.getEntityAttributeGroupPK());
            var entityAttributeGroupDetail = entityAttributeGroup.getActiveDetailForUpdate();

            entityAttributeGroupDetail.setThruTime(session.getStartTime());
            entityAttributeGroupDetail.store();

            var entityAttributeGroupPK = entityAttributeGroupDetail.getEntityAttributeGroupPK();
            var entityAttributeGroupName = entityAttributeGroupDetailValue.getEntityAttributeGroupName();
            var isDefault = entityAttributeGroupDetailValue.getIsDefault();
            var sortOrder = entityAttributeGroupDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultEntityAttributeGroup = getDefaultEntityAttributeGroup();
                var defaultFound = defaultEntityAttributeGroup != null && !defaultEntityAttributeGroup.equals(entityAttributeGroup);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultEntityAttributeGroupDetailValue = getDefaultEntityAttributeGroupDetailValueForUpdate();

                    defaultEntityAttributeGroupDetailValue.setIsDefault(false);
                    updateEntityAttributeGroupFromValue(defaultEntityAttributeGroupDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            entityAttributeGroupDetail = entityAttributeGroupDetailFactory.create(entityAttributeGroupPK, entityAttributeGroupName,
                    isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            entityAttributeGroup.setActiveDetail(entityAttributeGroupDetail);
            entityAttributeGroup.setLastDetail(entityAttributeGroupDetail);

            sendEvent(entityAttributeGroupPK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateEntityAttributeGroupFromValue(EntityAttributeGroupDetailValue entityAttributeGroupDetailValue, BasePK updatedBy) {
        updateEntityAttributeGroupFromValue(entityAttributeGroupDetailValue, true, updatedBy);
    }

    public EntityAttributeGroup getEntityAttributeGroupByPK(EntityAttributeGroupPK pk) {
        return entityAttributeGroupFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public void deleteEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, BasePK deletedBy) {
        deleteEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup, deletedBy);
        deleteEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup, deletedBy);

        var entityAttributeGroupDetail = entityAttributeGroup.getLastDetailForUpdate();
        entityAttributeGroupDetail.setThruTime(session.getStartTime());
        entityAttributeGroup.setActiveDetail(null);
        entityAttributeGroup.store();

        // Check for default, and pick one if necessary
        var defaultEntityAttributeGroup = getDefaultEntityAttributeGroup();
        if(defaultEntityAttributeGroup == null) {
            var entityAttributeGroups = getEntityAttributeGroupsForUpdate();

            if(!entityAttributeGroups.isEmpty()) {
                var iter = entityAttributeGroups.iterator();
                if(iter.hasNext()) {
                    defaultEntityAttributeGroup = iter.next();
                }
                var entityAttributeGroupDetailValue = Objects.requireNonNull(defaultEntityAttributeGroup).getLastDetailForUpdate().getEntityAttributeGroupDetailValue().clone();

                entityAttributeGroupDetailValue.setIsDefault(true);
                updateEntityAttributeGroupFromValue(entityAttributeGroupDetailValue, false, deletedBy);
            }
        }

        sendEvent(entityAttributeGroup.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Group Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityAttributeGroupDescriptionFactory entityAttributeGroupDescriptionFactory;

    public EntityAttributeGroupDescription createEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language, String description,
            BasePK createdBy) {
        var entityAttributeGroupDescription = entityAttributeGroupDescriptionFactory.create(entityAttributeGroup,
                language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttributeGroup.getPrimaryKey(), EventTypes.MODIFY, entityAttributeGroupDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityAttributeGroupDescription;
    }

    private EntityAttributeGroupDescription getEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAttributeGroupDescriptions.fields())
                .from(EntityAttributeGroupDescriptions)
                .where(EntityAttributeGroupDescriptions.ENTITY_ATTRIBUTE_GROUP.eq(entityAttributeGroup.getPrimaryKey()),
                        EntityAttributeGroupDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        EntityAttributeGroupDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAttributeGroupDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public EntityAttributeGroupDescription getEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language) {
        return getEntityAttributeGroupDescription(entityAttributeGroup, language, EntityPermission.READ_ONLY);
    }

    public EntityAttributeGroupDescription getEntityAttributeGroupDescriptionForUpdate(EntityAttributeGroup entityAttributeGroup, Language language) {
        return getEntityAttributeGroupDescription(entityAttributeGroup, language, EntityPermission.READ_WRITE);
    }

    public EntityAttributeGroupDescriptionValue getEntityAttributeGroupDescriptionValue(EntityAttributeGroupDescription entityAttributeGroupDescription) {
        return entityAttributeGroupDescription == null? null: entityAttributeGroupDescription.getEntityAttributeGroupDescriptionValue().clone();
    }

    public EntityAttributeGroupDescriptionValue getEntityAttributeGroupDescriptionValueForUpdate(EntityAttributeGroup entityAttributeGroup, Language language) {
        return getEntityAttributeGroupDescriptionValue(getEntityAttributeGroupDescriptionForUpdate(entityAttributeGroup, language));
    }

    private List<EntityAttributeGroupDescription> getEntityAttributeGroupDescriptionsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, EntityPermission entityPermission) {
        var condition = EntityAttributeGroupDescriptions.ENTITY_ATTRIBUTE_GROUP.eq(entityAttributeGroup.getPrimaryKey())
                .and(EntityAttributeGroupDescriptions.THRU_TIME.eq(Session.MAX_TIME));
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAttributeGroupDescriptions.fields())
                    .from(EntityAttributeGroupDescriptions)
                    .join(Languages).onKey(ENTITY_ATTRIBUTE_GROUP_DESCRIPTIONS_LANGUAGE_FK)
                    .where(condition)
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME), EntityAttributeGroupDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAttributeGroupDescriptions.fields())
                    .from(EntityAttributeGroupDescriptions)
                    .where(condition)
                    .forUpdate();
        };

        return entityAttributeGroupDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityAttributeGroupDescription> getEntityAttributeGroupDescriptionsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeGroupDescription> getEntityAttributeGroupDescriptionsByEntityAttributeGroupForUpdate(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_WRITE);
    }

    public String getBestEntityAttributeGroupDescription(EntityAttributeGroup entityAttributeGroup, Language language) {
        String description;
        var entityAttributeGroupDescription = getEntityAttributeGroupDescription(entityAttributeGroup, language);

        if(entityAttributeGroupDescription == null && !language.getIsDefault()) {
            entityAttributeGroupDescription = getEntityAttributeGroupDescription(entityAttributeGroup, partyControl.getDefaultLanguage());
        }

        if(entityAttributeGroupDescription == null) {
            description = entityAttributeGroup.getLastDetail().getEntityAttributeGroupName();
        } else {
            description = entityAttributeGroupDescription.getDescription();
        }

        return description;
    }

    public EntityAttributeGroupDescriptionTransfer getEntityAttributeGroupDescriptionTransfer(UserVisit userVisit, EntityAttributeGroupDescription entityAttributeGroupDescription, EntityInstance entityInstance) {
        return entityAttributeGroupDescriptionTransferCache.getEntityAttributeGroupDescriptionTransfer(userVisit, entityAttributeGroupDescription, entityInstance);
    }

    public List<EntityAttributeGroupDescriptionTransfer> getEntityAttributeGroupDescriptionTransfers(UserVisit userVisit, EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        var entityAttributeGroupDescriptions = getEntityAttributeGroupDescriptionsByEntityAttributeGroup(entityAttributeGroup);
        List<EntityAttributeGroupDescriptionTransfer> entityAttributeGroupDescriptionTransfers = new ArrayList<>(entityAttributeGroupDescriptions.size());

        entityAttributeGroupDescriptions.forEach((entityAttributeGroupDescription) ->
                entityAttributeGroupDescriptionTransfers.add(entityAttributeGroupDescriptionTransferCache.getEntityAttributeGroupDescriptionTransfer(userVisit, entityAttributeGroupDescription, entityInstance))
        );

        return entityAttributeGroupDescriptionTransfers;
    }

    public void updateEntityAttributeGroupDescriptionFromValue(EntityAttributeGroupDescriptionValue entityAttributeGroupDescriptionValue, BasePK updatedBy) {
        if(entityAttributeGroupDescriptionValue.hasBeenModified()) {
            var entityAttributeGroupDescription = entityAttributeGroupDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeGroupDescriptionValue.getPrimaryKey());

            entityAttributeGroupDescription.setThruTime(session.getStartTime());
            entityAttributeGroupDescription.store();

            var entityAttributeGroup = entityAttributeGroupDescription.getEntityAttributeGroup();
            var language = entityAttributeGroupDescription.getLanguage();
            var description = entityAttributeGroupDescriptionValue.getDescription();

            entityAttributeGroupDescription = entityAttributeGroupDescriptionFactory.create(entityAttributeGroup, language,
                    description, session.getStartTime(), Session.MAX_TIME);

            sendEvent(entityAttributeGroup.getPrimaryKey(), EventTypes.MODIFY, entityAttributeGroupDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityAttributeGroupDescription(EntityAttributeGroupDescription entityAttributeGroupDescription, BasePK deletedBy) {
        entityAttributeGroupDescription.setThruTime(session.getStartTime());

        sendEvent(entityAttributeGroupDescription.getEntityAttributeGroupPK(), EventTypes.MODIFY, entityAttributeGroupDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityAttributeGroupDescriptionsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, BasePK deletedBy) {
        var entityAttributeGroupDescriptions = getEntityAttributeGroupDescriptionsByEntityAttributeGroupForUpdate(entityAttributeGroup);

        entityAttributeGroupDescriptions.forEach((entityAttributeGroupDescription) ->
                deleteEntityAttributeGroupDescription(entityAttributeGroupDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Entity Attribute Groups
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityAttributeEntityAttributeGroupFactory entityAttributeEntityAttributeGroupFactory;

    public EntityAttributeEntityAttributeGroup createEntityAttributeEntityAttributeGroup(EntityAttribute entityAttribute,
            EntityAttributeGroup entityAttributeGroup, Integer sortOrder, BasePK createdBy) {
        var entityAttributeEntityAttributeGroup = entityAttributeEntityAttributeGroupFactory.create(
                entityAttribute, entityAttributeGroup, sortOrder, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeEntityAttributeGroup.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityAttributeEntityAttributeGroup;
    }

    public long countEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributeEntityAttributeGroups)
                .where(EntityAttributeEntityAttributeGroups.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityAttributeEntityAttributeGroups.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAttributeEntityAttributeGroups)
                .where(EntityAttributeEntityAttributeGroups.ENTITY_ATTRIBUTE_GROUP.eq(entityAttributeGroup.getPrimaryKey()),
                        EntityAttributeEntityAttributeGroups.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private EntityAttributeEntityAttributeGroup getEntityAttributeEntityAttributeGroup(EntityAttribute entityAttribute,
            EntityAttributeGroup entityAttributeGroup, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAttributeEntityAttributeGroups.fields())
                .from(EntityAttributeEntityAttributeGroups)
                .where(EntityAttributeEntityAttributeGroups.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey()),
                        EntityAttributeEntityAttributeGroups.ENTITY_ATTRIBUTE_GROUP.eq(entityAttributeGroup.getPrimaryKey()),
                        EntityAttributeEntityAttributeGroups.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAttributeEntityAttributeGroupFactory.getEntityFromQuery(entityPermission, query);
    }

    public EntityAttributeEntityAttributeGroup getEntityAttributeEntityAttributeGroup(EntityAttribute entityAttribute, EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroup(entityAttribute, entityAttributeGroup, EntityPermission.READ_ONLY);
    }

    public EntityAttributeEntityAttributeGroup getEntityAttributeEntityAttributeGroupForUpdate(EntityAttribute entityAttribute, EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroup(entityAttribute, entityAttributeGroup, EntityPermission.READ_WRITE);
    }

    public EntityAttributeEntityAttributeGroupValue getEntityAttributeEntityAttributeGroupValue(EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup) {
        return entityAttributeEntityAttributeGroup == null? null: entityAttributeEntityAttributeGroup.getEntityAttributeEntityAttributeGroupValue().clone();
    }

    public EntityAttributeEntityAttributeGroupValue getEntityAttributeEntityAttributeGroupValueForUpdate(EntityAttribute entityAttribute, EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroupValue(getEntityAttributeEntityAttributeGroupForUpdate(entityAttribute, entityAttributeGroup));
    }

    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute,
            EntityPermission entityPermission) {
        var condition = EntityAttributeEntityAttributeGroups.ENTITY_ATTRIBUTE.eq(entityAttribute.getPrimaryKey())
                .and(EntityAttributeEntityAttributeGroups.THRU_TIME.eq(Session.MAX_TIME));
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAttributeEntityAttributeGroups.fields())
                    .from(EntityAttributeEntityAttributeGroups)
                    .join(EntityAttributeGroups).onKey(ENTITY_ATTRIBUTE_ENTITY_ATTRIBUTE_GROUPS_ENTITY_ATTRIBUTE_GROUP_FK)
                    .join(EntityAttributeGroupDetails).onKey(ENTITY_ATTRIBUTE_GROUPS_LAST_DETAIL_FK)
                    .where(condition)
                    .orderBy(EntityAttributeEntityAttributeGroups.SORT_ORDER, EntityAttributeGroupDetails.SORT_ORDER, EntityAttributeGroupDetails.ENTITY_ATTRIBUTE_GROUP_NAME),
                    EntityAttributeEntityAttributeGroupFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAttributeEntityAttributeGroups.fields())
                    .from(EntityAttributeEntityAttributeGroups)
                    .where(condition)
                    .forUpdate();
        };

        return entityAttributeEntityAttributeGroupFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute, EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeForUpdate(EntityAttribute entityAttribute) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute, EntityPermission.READ_WRITE);
    }

    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup,
            EntityPermission entityPermission) {
        var condition = EntityAttributeEntityAttributeGroups.ENTITY_ATTRIBUTE_GROUP.eq(entityAttributeGroup.getPrimaryKey())
                .and(EntityAttributeEntityAttributeGroups.THRU_TIME.eq(Session.MAX_TIME));
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAttributeEntityAttributeGroups.fields())
                    .from(EntityAttributeEntityAttributeGroups)
                    .join(EntityAttributes).onKey(ENTITY_ATTRIBUTE_ENTITY_ATTRIBUTE_GROUPS_ENTITY_ATTRIBUTE_FK)
                    .join(EntityAttributeDetails).onKey(ENTITY_ATTRIBUTES_LAST_DETAIL_FK)
                    .join(EntityTypes).onKey(ENTITY_ATTRIBUTE_DETAILS_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                    .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                    .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_LAST_DETAIL_FK)
                    .where(condition)
                    .orderBy(EntityAttributeEntityAttributeGroups.SORT_ORDER, ComponentVendorDetails.COMPONENT_VENDOR_NAME,
                            EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME,
                            EntityAttributeDetails.SORT_ORDER, EntityAttributeDetails.ENTITY_ATTRIBUTE_NAME),
                    EntityAttributeEntityAttributeGroupFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAttributeEntityAttributeGroups.fields())
                    .from(EntityAttributeEntityAttributeGroups)
                    .where(condition)
                    .forUpdate();
        };

        return entityAttributeEntityAttributeGroupFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_ONLY);
    }

    public List<EntityAttributeEntityAttributeGroup> getEntityAttributeEntityAttributeGroupsByEntityAttributeGroupForUpdate(EntityAttributeGroup entityAttributeGroup) {
        return getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup, EntityPermission.READ_WRITE);
    }

    public EntityAttributeEntityAttributeGroupTransfer getEntityAttributeEntityAttributeGroupTransfer(UserVisit userVisit, EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup, EntityInstance entityInstance) {
        return entityAttributeEntityAttributeGroupTransferCache.getEntityAttributeEntityAttributeGroupTransfer(userVisit, entityAttributeEntityAttributeGroup, entityInstance);
    }

    public List<EntityAttributeEntityAttributeGroupTransfer> getEntityAttributeEntityAttributeGroupTransfers(UserVisit userVisit,
            Collection<EntityAttributeEntityAttributeGroup> entityAttributeEntityAttributeGroups, EntityInstance entityInstance) {
        List<EntityAttributeEntityAttributeGroupTransfer> entityAttributeEntityAttributeGroupTransfers = new ArrayList<>(entityAttributeEntityAttributeGroups.size());

        entityAttributeEntityAttributeGroups.forEach((entityAttributeEntityAttributeGroup) ->
                entityAttributeEntityAttributeGroupTransfers.add(entityAttributeEntityAttributeGroupTransferCache.getEntityAttributeEntityAttributeGroupTransfer(userVisit, entityAttributeEntityAttributeGroup, entityInstance))
        );

        return entityAttributeEntityAttributeGroupTransfers;
    }

    public List<EntityAttributeEntityAttributeGroupTransfer> getEntityAttributeEntityAttributeGroupTransfersByEntityAttribute(UserVisit userVisit,
            EntityAttribute entityAttribute, EntityInstance entityInstance) {
        return getEntityAttributeEntityAttributeGroupTransfers(userVisit, getEntityAttributeEntityAttributeGroupsByEntityAttribute(entityAttribute), entityInstance);
    }

    public List<EntityAttributeEntityAttributeGroupTransfer> getEntityAttributeEntityAttributeGroupTransfersByEntityAttributeGroup(UserVisit userVisit,
            EntityAttributeGroup entityAttributeGroup, EntityInstance entityInstance) {
        return getEntityAttributeEntityAttributeGroupTransfers(userVisit, getEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(entityAttributeGroup), entityInstance);
    }

    public void updateEntityAttributeEntityAttributeGroupFromValue(EntityAttributeEntityAttributeGroupValue entityAttributeEntityAttributeGroupValue, BasePK updatedBy) {
        if(entityAttributeEntityAttributeGroupValue.hasBeenModified()) {
            var entityAttributeEntityAttributeGroup = entityAttributeEntityAttributeGroupFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     entityAttributeEntityAttributeGroupValue.getPrimaryKey());

            entityAttributeEntityAttributeGroup.setThruTime(session.getStartTime());
            entityAttributeEntityAttributeGroup.store();

            var entityAttribute = entityAttributeEntityAttributeGroup.getEntityAttribute();
            var entityAttributeGroup = entityAttributeEntityAttributeGroup.getEntityAttributeGroup();
            var sortOrder = entityAttributeEntityAttributeGroupValue.getSortOrder();

            entityAttributeEntityAttributeGroup = entityAttributeEntityAttributeGroupFactory.create(entityAttribute, entityAttributeGroup,
                    sortOrder, session.getStartTime(), Session.MAX_TIME);

            sendEvent(entityAttribute.getPrimaryKey(), EventTypes.MODIFY, entityAttributeEntityAttributeGroup.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityAttributeEntityAttributeGroup(EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup, BasePK deletedBy) {
        entityAttributeEntityAttributeGroup.setThruTime(session.getStartTime());

        sendEvent(entityAttributeEntityAttributeGroup.getEntityAttributePK(), EventTypes.MODIFY, entityAttributeEntityAttributeGroup.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityAttributeEntityAttributeGroupsByEntityAttribute(EntityAttribute entityAttribute, BasePK deletedBy) {
        var entityAttributeEntityAttributeGroups = getEntityAttributeEntityAttributeGroupsByEntityAttributeForUpdate(entityAttribute);

        entityAttributeEntityAttributeGroups.forEach((entityAttributeEntityAttributeGroup) ->
                deleteEntityAttributeEntityAttributeGroup(entityAttributeEntityAttributeGroup, deletedBy)
        );
    }

    public void deleteEntityAttributeEntityAttributeGroupsByEntityAttributeGroup(EntityAttributeGroup entityAttributeGroup, BasePK deletedBy) {
        var entityAttributeEntityAttributeGroups = getEntityAttributeEntityAttributeGroupsByEntityAttributeGroupForUpdate(entityAttributeGroup);

        entityAttributeEntityAttributeGroups.forEach((entityAttributeEntityAttributeGroup) ->
                deleteEntityAttributeEntityAttributeGroup(entityAttributeEntityAttributeGroup, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Entity Attribute Group Searches
    // --------------------------------------------------------------------------------

    private List<Long> getEntityAttributeGroupIdsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
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

    public List<EntityAttributeGroupResultTransfer> getEntityAttributeGroupResultTransfers(UserVisit userVisit, UserVisitSearch userVisitSearch) {
        var entityAttributeGroupIds = getEntityAttributeGroupIdsFromUserVisitSearch(userVisitSearch);
        var entityAttributeGroupResultTransfers = new ArrayList<EntityAttributeGroupResultTransfer>(entityAttributeGroupIds.size());
        var options = session.getOptions();
        var includeEntityAttributeGroup = options != null
                && options.contains(SearchOptions.EntityAttributeGroupResultIncludeEntityAttributeGroup);

        for(var entityAttributeGroupId : entityAttributeGroupIds) {
            var entityAttributeGroup = getEntityAttributeGroupByPK(new EntityAttributeGroupPK(entityAttributeGroupId));
            var entityAttributeGroupDetail = entityAttributeGroup.getLastDetail();

            entityAttributeGroupResultTransfers.add(new EntityAttributeGroupResultTransfer(entityAttributeGroupDetail.getEntityAttributeGroupName(),
                    includeEntityAttributeGroup ? getEntityAttributeGroupTransfer(userVisit, entityAttributeGroup, null) : null));
        }

        return entityAttributeGroupResultTransfers;
    }

    public List<EntityAttributeGroupObject> getEntityAttributeGroupObjectsFromUserVisitSearch(UserVisitSearch userVisitSearch) {
        var entityAttributeGroupObjects = new ArrayList<EntityAttributeGroupObject>();

        for(var entityAttributeGroupId : getEntityAttributeGroupIdsFromUserVisitSearch(userVisitSearch)) {
            var entityAttributeGroup = getEntityAttributeGroupByPK(new EntityAttributeGroupPK(entityAttributeGroupId));

            entityAttributeGroupObjects.add(new EntityAttributeGroupObject(entityAttributeGroup, null));
        }

        return entityAttributeGroupObjects;
    }

}
