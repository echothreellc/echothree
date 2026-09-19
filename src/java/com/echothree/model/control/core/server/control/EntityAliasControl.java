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
import com.echothree.model.control.core.common.choice.EntityAliasTypeChoicesBean;
import com.echothree.model.control.core.common.transfer.EntityAliasTransfer;
import com.echothree.model.control.core.common.transfer.EntityAliasTypeDescriptionTransfer;
import com.echothree.model.control.core.common.transfer.EntityAliasTypeTransfer;
import com.echothree.model.control.core.server.transfer.EntityAliasTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAliasTypeDescriptionTransferCache;
import com.echothree.model.control.core.server.transfer.EntityAliasTypeTransferCache;
import com.echothree.model.data.core.common.pk.EntityAliasTypePK;
import com.echothree.model.data.core.server.entity.EntityAlias;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityAliasTypeDescription;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.factory.EntityAliasFactory;
import com.echothree.model.data.core.server.factory.EntityAliasTypeDescriptionFactory;
import com.echothree.model.data.core.server.factory.EntityAliasTypeDetailFactory;
import com.echothree.model.data.core.server.factory.EntityAliasTypeFactory;
import com.echothree.model.data.core.server.value.EntityAliasTypeDescriptionValue;
import com.echothree.model.data.core.server.value.EntityAliasTypeDetailValue;
import com.echothree.model.data.core.server.value.EntityAliasValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.COMPONENT_VENDORS_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ALIASES_ENTITY_ALIAS_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ALIAS_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ALIAS_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_ALIAS_TYPE_DETAILS_ENTITY_TYPE_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPES_LAST_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.core.CoreForeignKeys.ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK;
import static com.echothree.model.jooq.server.tables.core.ComponentVendorDetails.ComponentVendorDetails;
import static com.echothree.model.jooq.server.tables.core.ComponentVendors.ComponentVendors;
import static com.echothree.model.jooq.server.tables.core.EntityAliasTypeDescriptions.EntityAliasTypeDescriptions;
import static com.echothree.model.jooq.server.tables.core.EntityAliasTypeDetails.EntityAliasTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityAliasTypes.EntityAliasTypes;
import static com.echothree.model.jooq.server.tables.core.EntityAliases.EntityAliases;
import static com.echothree.model.jooq.server.tables.core.EntityTypeDetails.EntityTypeDetails;
import static com.echothree.model.jooq.server.tables.core.EntityTypes.EntityTypes;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
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
public class EntityAliasControl
        extends BaseModelControl {

    /** Creates a new instance of EntityAliasControl */
    protected EntityAliasControl() {
        super();
    }

    // --------------------------------------------------------------------------------
    //   Entity Alias Types
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityAliasTypeDetailFactory entityAliasTypeDetailFactory;

    @Inject
    protected EntityAliasTypeFactory entityAliasTypeFactory;

    @Inject
    EntityAliasTypeTransferCache entityAliasTypeTransferCache;

    public EntityAliasType createEntityAliasType(EntityType entityType, String entityAliasTypeName,
            String validationPattern, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultEntityAliasType = getDefaultEntityAliasType(entityType);
        var defaultFound = defaultEntityAliasType != null;

        if(defaultFound && isDefault) {
            var defaultEntityAliasTypeDetailValue = getDefaultEntityAliasTypeDetailValueForUpdate(entityType);

            defaultEntityAliasTypeDetailValue.setIsDefault(false);
            updateEntityAliasTypeFromValue(defaultEntityAliasTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var entityAliasType = entityAliasTypeFactory.create();
        var entityAliasTypeDetail = entityAliasTypeDetailFactory.create(entityAliasType, entityType,
                entityAliasTypeName, validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

        // Convert to R/W
        entityAliasType = entityAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                entityAliasType.getPrimaryKey());
        entityAliasType.setActiveDetail(entityAliasTypeDetail);
        entityAliasType.setLastDetail(entityAliasTypeDetail);
        entityAliasType.store();

        sendEvent(entityAliasType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return entityAliasType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.EntityAliasType */
    public EntityAliasType getEntityAliasTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new EntityAliasTypePK(entityInstance.getEntityUniqueId());

        return entityAliasTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public EntityAliasType getEntityAliasTypeByEntityInstance(EntityInstance entityInstance) {
        return getEntityAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public EntityAliasType getEntityAliasTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getEntityAliasTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public EntityAliasType getEntityAliasTypeByPK(EntityAliasTypePK pk) {
        return entityAliasTypeFactory.getEntityFromPK(EntityPermission.READ_ONLY, pk);
    }

    public long countEntityAliasTypesByEntityType(EntityType entityType) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAliasTypes)
                .join(EntityAliasTypeDetails).onKey(ENTITY_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(EntityAliasTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public EntityAliasType getEntityAliasTypeByName(EntityType entityType, String entityAliasTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAliasTypes.fields())
                .from(EntityAliasTypes)
                .join(EntityAliasTypeDetails).onKey(ENTITY_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(EntityAliasTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        EntityAliasTypeDetails.ENTITY_ALIAS_TYPE_NAME.eq(entityAliasTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAliasTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public EntityAliasType getEntityAliasTypeByName(EntityType entityType, String entityAliasTypeName) {
        return getEntityAliasTypeByName(entityType, entityAliasTypeName, EntityPermission.READ_ONLY);
    }

    public EntityAliasType getEntityAliasTypeByNameForUpdate(EntityType entityType, String entityAliasTypeName) {
        return getEntityAliasTypeByName(entityType, entityAliasTypeName, EntityPermission.READ_WRITE);
    }

    public EntityAliasTypeDetailValue getEntityAliasTypeDetailValueForUpdate(EntityAliasType entityAliasType) {
        return entityAliasType == null? null: entityAliasType.getLastDetailForUpdate().getEntityAliasTypeDetailValue().clone();
    }

    public EntityAliasTypeDetailValue getEntityAliasTypeDetailValueByNameForUpdate(EntityType entityType, String entityAliasTypeName) {
        return getEntityAliasTypeDetailValueForUpdate(getEntityAliasTypeByNameForUpdate(entityType, entityAliasTypeName));
    }

    private EntityAliasType getDefaultEntityAliasType(EntityPermission entityPermission, EntityType entityType) {
        var baseQuery = session.getDslContext()
                .select(EntityAliasTypes.fields())
                .from(EntityAliasTypes)
                .join(EntityAliasTypeDetails).onKey(ENTITY_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                .where(EntityAliasTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()),
                        EntityAliasTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAliasTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public EntityAliasType getDefaultEntityAliasType(EntityType entityType) {
        return getDefaultEntityAliasType(EntityPermission.READ_ONLY, entityType);
    }

    public EntityAliasType getDefaultEntityAliasTypeForUpdate(EntityType entityType) {
        return getDefaultEntityAliasType(EntityPermission.READ_WRITE, entityType);
    }

    public EntityAliasTypeDetailValue getDefaultEntityAliasTypeDetailValueForUpdate(EntityType entityType) {
        return getDefaultEntityAliasTypeForUpdate(entityType).getLastDetailForUpdate().getEntityAliasTypeDetailValue().clone();
    }

    private List<EntityAliasType> getEntityAliasTypesByEntityType(EntityType entityType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAliasTypes.fields())
                    .from(EntityAliasTypes)
                    .join(EntityAliasTypeDetails).onKey(ENTITY_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                    .where(EntityAliasTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()))
                    .orderBy(EntityAliasTypeDetails.SORT_ORDER, EntityAliasTypeDetails.ENTITY_ALIAS_TYPE_NAME),
                    EntityAliasTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAliasTypes.fields())
                    .from(EntityAliasTypes)
                    .join(EntityAliasTypeDetails).onKey(ENTITY_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                    .where(EntityAliasTypeDetails.ENTITY_TYPE.eq(entityType.getPrimaryKey()))
                    .forUpdate();
        };

        return entityAliasTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityAliasType> getEntityAliasTypesByEntityType(EntityType entityType) {
        return getEntityAliasTypesByEntityType(entityType, EntityPermission.READ_ONLY);
    }

    public List<EntityAliasType> getEntityAliasTypesByEntityTypeForUpdate(EntityType entityType) {
        return getEntityAliasTypesByEntityType(entityType, EntityPermission.READ_WRITE);
    }

    private List<EntityAliasType> getEntityAliasTypesByName(String entityAliasTypeName, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAliasTypes.fields())
                    .from(EntityAliasTypes)
                    .join(EntityAliasTypeDetails).onKey(ENTITY_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                    .join(EntityTypes).onKey(ENTITY_ALIAS_TYPE_DETAILS_ENTITY_TYPE_FK)
                    .join(EntityTypeDetails).onKey(ENTITY_TYPES_LAST_DETAIL_FK)
                    .join(ComponentVendors).onKey(ENTITY_TYPE_DETAILS_COMPONENT_VENDOR_FK)
                    .join(ComponentVendorDetails).onKey(COMPONENT_VENDORS_LAST_DETAIL_FK)
                    .where(EntityAliasTypeDetails.ENTITY_ALIAS_TYPE_NAME.eq(entityAliasTypeName))
                    .orderBy(ComponentVendorDetails.COMPONENT_VENDOR_NAME, EntityTypeDetails.SORT_ORDER, EntityTypeDetails.ENTITY_TYPE_NAME, EntityAliasTypeDetails.SORT_ORDER, EntityAliasTypeDetails.ENTITY_ALIAS_TYPE_NAME),
                    EntityAliasTypeFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAliasTypes.fields())
                    .from(EntityAliasTypes)
                    .join(EntityAliasTypeDetails).onKey(ENTITY_ALIAS_TYPES_ACTIVE_DETAIL_FK)
                    .where(EntityAliasTypeDetails.ENTITY_ALIAS_TYPE_NAME.eq(entityAliasTypeName))
                    .forUpdate();
        };

        return entityAliasTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityAliasType> getEntityAliasTypesByName(String entityAliasTypeName) {
        return getEntityAliasTypesByName(entityAliasTypeName, EntityPermission.READ_ONLY);
    }

    public List<EntityAliasType> getEntityAliasTypesByNameForUpdate(String entityAliasTypeName) {
        return getEntityAliasTypesByName(entityAliasTypeName, EntityPermission.READ_WRITE);
    }

    public EntityAliasTypeTransfer getEntityAliasTypeTransfer(UserVisit userVisit, EntityAliasType entityAliasType) {
        return entityAliasTypeTransferCache.getEntityAliasTypeTransfer(userVisit, entityAliasType);
    }

    public List<EntityAliasTypeTransfer> getEntityAliasTypeTransfers(UserVisit userVisit, Collection<EntityAliasType> entityAliasTypes) {
        List<EntityAliasTypeTransfer> entityAliasTypeTransfers = new ArrayList<>(entityAliasTypes.size());

        entityAliasTypes.forEach((entityAliasType) ->
                entityAliasTypeTransfers.add(entityAliasTypeTransferCache.getEntityAliasTypeTransfer(userVisit, entityAliasType))
        );

        return entityAliasTypeTransfers;
    }

    public List<EntityAliasTypeTransfer> getEntityAliasTypeTransfersByEntityType(UserVisit userVisit, EntityType entityType) {
        return getEntityAliasTypeTransfers(userVisit, getEntityAliasTypesByEntityType(entityType));
    }

    private void updateEntityAliasTypeFromValue(EntityAliasTypeDetailValue entityAliasTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(entityAliasTypeDetailValue.hasBeenModified()) {
            var entityAliasType = entityAliasTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    entityAliasTypeDetailValue.getEntityAliasTypePK());
            var entityAliasTypeDetail = entityAliasType.getActiveDetailForUpdate();

            entityAliasTypeDetail.setThruTime(session.getStartTime());
            entityAliasTypeDetail.store();

            var entityTypePK = entityAliasTypeDetail.getEntityTypePK();
            var entityAliasTypePK = entityAliasTypeDetail.getEntityAliasTypePK(); // Not updated
            var entityAliasTypeName = entityAliasTypeDetailValue.getEntityAliasTypeName();
            var validationPattern = entityAliasTypeDetailValue.getValidationPattern();
            var isDefault = entityAliasTypeDetailValue.getIsDefault();
            var sortOrder = entityAliasTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var entityType = entityAliasTypeDetail.getEntityType();
                var defaultEntityAliasType = getDefaultEntityAliasType(entityType);
                var defaultFound = defaultEntityAliasType != null && !defaultEntityAliasType.equals(entityAliasType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultEntityAliasTypeDetailValue = getDefaultEntityAliasTypeDetailValueForUpdate(entityType);

                    defaultEntityAliasTypeDetailValue.setIsDefault(false);
                    updateEntityAliasTypeFromValue(defaultEntityAliasTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            entityAliasTypeDetail = entityAliasTypeDetailFactory.create(entityAliasTypePK, entityTypePK,
                    entityAliasTypeName, validationPattern, isDefault, sortOrder, session.getStartTime(), Session.MAX_TIME);

            entityAliasType.setActiveDetail(entityAliasTypeDetail);
            entityAliasType.setLastDetail(entityAliasTypeDetail);

            sendEvent(entityAliasTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateEntityAliasTypeFromValue(EntityAliasTypeDetailValue entityAliasTypeDetailValue, BasePK updatedBy) {
        updateEntityAliasTypeFromValue(entityAliasTypeDetailValue, true, updatedBy);
    }

    public EntityAliasTypeChoicesBean getEntityAliasTypeChoices(String defaultEntityAliasTypeChoice, Language language,
            boolean allowNullChoice, EntityType entityType) {
        var entityAliasTypes = getEntityAliasTypesByEntityType(entityType);
        var size = entityAliasTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultEntityAliasTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var entityAliasType : entityAliasTypes) {
            var entityAliasTypeDetail = entityAliasType.getLastDetail();
            var label = getBestEntityAliasTypeDescription(entityAliasType, language);
            var value = entityAliasTypeDetail.getEntityAliasTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultEntityAliasTypeChoice != null && defaultEntityAliasTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && entityAliasTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new EntityAliasTypeChoicesBean(labels, values, defaultValue);
    }

    private void deleteEntityAliasType(EntityAliasType entityAliasType, boolean checkDefault, BasePK deletedBy) {
        var entityAliasTypeDetail = entityAliasType.getLastDetailForUpdate();

        deleteEntityAliasesByEntityAliasType(entityAliasType, deletedBy);
        deleteEntityAliasTypeDescriptionsByEntityAliasType(entityAliasType, deletedBy);

        entityAliasTypeDetail.setThruTime(session.getStartTime());
        entityAliasType.setActiveDetail(null);
        entityAliasType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultEntityAliasType = getDefaultEntityAliasType(entityAliasTypeDetail.getEntityType());

            if(defaultEntityAliasType == null) {
                var entityType = entityAliasTypeDetail.getEntityType();
                var entityAliasTypes = getEntityAliasTypesByEntityTypeForUpdate(entityType);

                if(!entityAliasTypes.isEmpty()) {
                    var iter = entityAliasTypes.iterator();
                    if(iter.hasNext()) {
                        defaultEntityAliasType = iter.next();
                    }
                    var entityAliasTypeDetailValue = Objects.requireNonNull(defaultEntityAliasType).getLastDetailForUpdate().getEntityAliasTypeDetailValue().clone();

                    entityAliasTypeDetailValue.setIsDefault(true);
                    updateEntityAliasTypeFromValue(entityAliasTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(entityAliasType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteEntityAliasType(EntityAliasType entityAliasType, BasePK deletedBy) {
        deleteEntityAliasType(entityAliasType, true, deletedBy);
    }

    private void deleteEntityAliasTypes(List<EntityAliasType> entityAliasTypes, boolean checkDefault, BasePK deletedBy) {
        entityAliasTypes.forEach((entityAliasType) -> deleteEntityAliasType(entityAliasType, checkDefault, deletedBy));
    }

    public void deleteEntityAliasTypesByEntityType(EntityType entityType, BasePK deletedBy) {
        deleteEntityAliasTypes(getEntityAliasTypesByEntityTypeForUpdate(entityType), false, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Entity Alias Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityAliasTypeDescriptionFactory entityAliasTypeDescriptionFactory;

    @Inject
    EntityAliasTypeDescriptionTransferCache entityAliasTypeDescriptionTransferCache;

    public EntityAliasTypeDescription createEntityAliasTypeDescription(EntityAliasType entityAliasType, Language language,
            String description, BasePK createdBy) {
        var entityAliasTypeDescription = entityAliasTypeDescriptionFactory.create(
                entityAliasType, language, description, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityAliasType.getPrimaryKey(), EventTypes.MODIFY, entityAliasTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityAliasTypeDescription;
    }

    private EntityAliasTypeDescription getEntityAliasTypeDescription(EntityAliasType entityAliasType, Language language,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAliasTypeDescriptions.fields())
                .from(EntityAliasTypeDescriptions)
                .where(EntityAliasTypeDescriptions.ENTITY_ALIAS_TYPE.eq(entityAliasType.getPrimaryKey()),
                        EntityAliasTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        EntityAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAliasTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public EntityAliasTypeDescription getEntityAliasTypeDescription(EntityAliasType entityAliasType, Language language) {
        return getEntityAliasTypeDescription(entityAliasType, language, EntityPermission.READ_ONLY);
    }

    public EntityAliasTypeDescription getEntityAliasTypeDescriptionForUpdate(EntityAliasType entityAliasType, Language language) {
        return getEntityAliasTypeDescription(entityAliasType, language, EntityPermission.READ_WRITE);
    }

    public EntityAliasTypeDescriptionValue getEntityAliasTypeDescriptionValue(EntityAliasTypeDescription entityAliasTypeDescription) {
        return entityAliasTypeDescription == null? null: entityAliasTypeDescription.getEntityAliasTypeDescriptionValue().clone();
    }

    public EntityAliasTypeDescriptionValue getEntityAliasTypeDescriptionValueForUpdate(EntityAliasType entityAliasType, Language language) {
        return getEntityAliasTypeDescriptionValue(getEntityAliasTypeDescriptionForUpdate(entityAliasType, language));
    }

    private List<EntityAliasTypeDescription> getEntityAliasTypeDescriptionsByEntityAliasType(EntityAliasType entityAliasType,
            EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAliasTypeDescriptions.fields())
                    .from(EntityAliasTypeDescriptions)
                    .join(Languages).onKey(ENTITY_ALIAS_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(EntityAliasTypeDescriptions.ENTITY_ALIAS_TYPE.eq(entityAliasType.getPrimaryKey()),
                            EntityAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    EntityAliasTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAliasTypeDescriptions.fields())
                    .from(EntityAliasTypeDescriptions)
                    .where(EntityAliasTypeDescriptions.ENTITY_ALIAS_TYPE.eq(entityAliasType.getPrimaryKey()),
                            EntityAliasTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityAliasTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityAliasTypeDescription> getEntityAliasTypeDescriptionsByEntityAliasType(EntityAliasType entityAliasType) {
        return getEntityAliasTypeDescriptionsByEntityAliasType(entityAliasType, EntityPermission.READ_ONLY);
    }

    public List<EntityAliasTypeDescription> getEntityAliasTypeDescriptionsByEntityAliasTypeForUpdate(EntityAliasType entityAliasType) {
        return getEntityAliasTypeDescriptionsByEntityAliasType(entityAliasType, EntityPermission.READ_WRITE);
    }

    public String getBestEntityAliasTypeDescription(EntityAliasType entityAliasType, Language language) {
        String description;
        var entityAliasTypeDescription = getEntityAliasTypeDescription(entityAliasType, language);

        if(entityAliasTypeDescription == null && !language.getIsDefault()) {
            entityAliasTypeDescription = getEntityAliasTypeDescription(entityAliasType, partyControl.getDefaultLanguage());
        }

        if(entityAliasTypeDescription == null) {
            description = entityAliasType.getLastDetail().getEntityAliasTypeName();
        } else {
            description = entityAliasTypeDescription.getDescription();
        }

        return description;
    }

    public EntityAliasTypeDescriptionTransfer getEntityAliasTypeDescriptionTransfer(UserVisit userVisit, EntityAliasTypeDescription entityAliasTypeDescription) {
        return entityAliasTypeDescriptionTransferCache.getEntityAliasTypeDescriptionTransfer(userVisit, entityAliasTypeDescription);
    }

    public List<EntityAliasTypeDescriptionTransfer> getEntityAliasTypeDescriptionTransfersByEntityAliasType(UserVisit userVisit,
            EntityAliasType entityAliasType, EntityInstance entityInstance) {
        var entityAliasTypeDescriptions = getEntityAliasTypeDescriptionsByEntityAliasType(entityAliasType);
        List<EntityAliasTypeDescriptionTransfer> entityAliasTypeDescriptionTransfers = new ArrayList<>(entityAliasTypeDescriptions.size());

        entityAliasTypeDescriptions.forEach((entityAliasTypeDescription) ->
                entityAliasTypeDescriptionTransfers.add(entityAliasTypeDescriptionTransferCache.getEntityAliasTypeDescriptionTransfer(userVisit, entityAliasTypeDescription))
        );

        return entityAliasTypeDescriptionTransfers;
    }

    public void updateEntityAliasTypeDescriptionFromValue(EntityAliasTypeDescriptionValue entityAliasTypeDescriptionValue, BasePK updatedBy) {
        if(entityAliasTypeDescriptionValue.hasBeenModified()) {
            var entityAliasTypeDescription = entityAliasTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    entityAliasTypeDescriptionValue.getPrimaryKey());

            entityAliasTypeDescription.setThruTime(session.getStartTime());
            entityAliasTypeDescription.store();

            var entityAliasType = entityAliasTypeDescription.getEntityAliasType();
            var language = entityAliasTypeDescription.getLanguage();
            var description = entityAliasTypeDescriptionValue.getDescription();

            entityAliasTypeDescription = entityAliasTypeDescriptionFactory.create(entityAliasType, language,
                    description, session.getStartTime(), Session.MAX_TIME);

            sendEvent(entityAliasType.getPrimaryKey(), EventTypes.MODIFY, entityAliasTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityAliasTypeDescription(EntityAliasTypeDescription entityAliasTypeDescription, BasePK deletedBy) {
        entityAliasTypeDescription.setThruTime(session.getStartTime());

        sendEvent(entityAliasTypeDescription.getEntityAliasTypePK(), EventTypes.MODIFY, entityAliasTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityAliasTypeDescriptionsByEntityAliasType(EntityAliasType entityAliasType, BasePK deletedBy) {
        var entityAliasTypeDescriptions = getEntityAliasTypeDescriptionsByEntityAliasTypeForUpdate(entityAliasType);

        entityAliasTypeDescriptions.forEach((entityAliasTypeDescription) ->
                deleteEntityAliasTypeDescription(entityAliasTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Entity Aliases
    // --------------------------------------------------------------------------------

    @Inject
    protected EntityAliasFactory entityAliasFactory;

    @Inject
    EntityAliasTransferCache entityAliasTransferCache;

    public EntityAlias createEntityAlias(EntityInstance entityInstance, EntityAliasType entityAliasType, String alias,
            BasePK createdBy) {
        var entityAlias = entityAliasFactory.create(entityInstance, entityAliasType,
                alias, session.getStartTime(), Session.MAX_TIME);

        sendEvent(entityInstance, EventTypes.MODIFY, entityAliasType.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return entityAlias;
    }

    public long countEntityAliasesByEntityInstance(EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAliases)
                .where(EntityAliases.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEntityAliasesByEntityAliasType(EntityAliasType entityAliasType) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAliases)
                .where(EntityAliases.ENTITY_ALIAS_TYPE.eq(entityAliasType.getPrimaryKey()),
                        EntityAliases.THRU_TIME.eq(Session.MAX_TIME))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public long countEntityAliasHistory(EntityInstance entityInstance, EntityAliasType entityAliasType) {
        return session.getDslContext()
                .selectCount()
                .from(EntityAliases)
                .where(EntityAliases.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityAliases.ENTITY_ALIAS_TYPE.eq(entityAliasType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public List<EntityAlias> getEntityAliasHistory(EntityInstance entityInstance, EntityAliasType entityAliasType) {
        var query = session.applyLimit(session.getDslContext()
                .select(EntityAliases.fields())
                .from(EntityAliases)
                .where(EntityAliases.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityAliases.ENTITY_ALIAS_TYPE.eq(entityAliasType.getPrimaryKey()))
                .orderBy(EntityAliases.THRU_TIME),
                EntityAliasFactory.class);

        return entityAliasFactory.getEntitiesFromQuery(EntityPermission.READ_ONLY, query);
    }

    private EntityAlias getEntityAlias(EntityInstance entityInstance, EntityAliasType entityAliasType,
            EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAliases.fields())
                .from(EntityAliases)
                .where(EntityAliases.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                        EntityAliases.ENTITY_ALIAS_TYPE.eq(entityAliasType.getPrimaryKey()),
                        EntityAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAliasFactory.getEntityFromQuery(entityPermission, query);
    }

    public EntityAlias getEntityAlias(EntityInstance entityInstance, EntityAliasType entityAliasType) {
        return getEntityAlias(entityInstance, entityAliasType, EntityPermission.READ_ONLY);
    }

    public EntityAlias getEntityAliasForUpdate(EntityInstance entityInstance, EntityAliasType entityAliasType) {
        return getEntityAlias(entityInstance, entityAliasType, EntityPermission.READ_WRITE);
    }

    public EntityAliasValue getEntityAliasValueForUpdate(EntityAlias entityAlias) {
        return entityAlias == null ? null : entityAlias.getEntityAliasValue().clone();
    }

    public EntityAliasValue getEntityAliasValueForUpdate(EntityInstance entityInstance, EntityAliasType entityAliasType) {
        return getEntityAliasValueForUpdate(getEntityAliasForUpdate(entityInstance, entityAliasType));
    }

    private List<EntityAlias> getEntityAliasesByEntityAliasType(EntityAliasType entityAliasType, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(EntityAliases.fields())
                .from(EntityAliases)
                .where(EntityAliases.ENTITY_ALIAS_TYPE.eq(entityAliasType.getPrimaryKey()),
                        EntityAliases.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(EntityAliases.ALIAS),
                    EntityAliasFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return entityAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<EntityAlias> getEntityAliasesByEntityAliasType(EntityAliasType entityAliasType) {
        return getEntityAliasesByEntityAliasType(entityAliasType, EntityPermission.READ_ONLY);
    }

    public List<EntityAlias> getEntityAliasesByEntityAliasTypeForUpdate(EntityAliasType entityAliasType) {
        return getEntityAliasesByEntityAliasType(entityAliasType, EntityPermission.READ_WRITE);
    }

    private List<EntityAlias> getEntityAliasesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(EntityAliases.fields())
                    .from(EntityAliases)
                    .join(EntityAliasTypes).onKey(ENTITY_ALIASES_ENTITY_ALIAS_TYPE_FK)
                    .join(EntityAliasTypeDetails).onKey(ENTITY_ALIAS_TYPES_LAST_DETAIL_FK)
                    .where(EntityAliases.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                            EntityAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(EntityAliasTypeDetails.SORT_ORDER, EntityAliasTypeDetails.ENTITY_ALIAS_TYPE_NAME),
                    EntityAliasFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(EntityAliases.fields())
                    .from(EntityAliases)
                    .where(EntityAliases.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()),
                            EntityAliases.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return entityAliasFactory.getEntitiesFromQuery(entityPermission, query);
    }
    public List<EntityAlias> getEntityAliasesByEntityInstance(EntityInstance entityInstance) {
        return getEntityAliasesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public List<EntityAlias> getEntityAliasesByEntityInstanceForUpdate(EntityInstance entityInstance){
        return getEntityAliasesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public EntityAliasTransfer getEntityAliasTransfer(UserVisit userVisit, EntityAlias entityAlias) {
        return entityAliasTransferCache.getEntityAliasTransfer(userVisit, entityAlias);
    }

    public List<EntityAliasTransfer> getEntityAliasTransfers(UserVisit userVisit, Collection<EntityAlias> entityAliases) {
        var entityAliasTransfers = new ArrayList<EntityAliasTransfer>(entityAliases.size());

        entityAliases.forEach((entityAlias) ->
                entityAliasTransfers.add(entityAliasTransferCache.getEntityAliasTransfer(userVisit, entityAlias))
        );

        return entityAliasTransfers;
    }

    public List<EntityAliasTransfer> getEntityAliasTransfersByEntityInstance(UserVisit userVisit, EntityInstance entityInstance) {
        return getEntityAliasTransfers(userVisit, getEntityAliasesByEntityInstance(entityInstance));
    }

    public void updateEntityAliasFromValue(EntityAliasValue entityAliasValue, BasePK updatedBy) {
        if(entityAliasValue.hasBeenModified()) {
            var entityAlias = entityAliasFactory.getEntityFromValue(EntityPermission.READ_WRITE, entityAliasValue);
            var entityAliasType = entityAlias.getEntityAliasType();
            var entityInstance = entityAlias.getEntityInstance();

            entityAlias.setThruTime(session.getStartTime());
            entityAlias.store();

            entityAliasFactory.create(entityInstance, entityAliasType, entityAliasValue.getAlias(), session.getStartTime(),
                    Session.MAX_TIME);

            sendEvent(entityInstance, EventTypes.MODIFY, entityAliasType.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteEntityAlias(EntityAlias entityAlias, BasePK deletedBy) {
        var entityAliasType = entityAlias.getEntityAliasType();
        var entityInstance = entityAlias.getEntityInstance();

        entityAlias.setThruTime(session.getStartTime());

        sendEvent(entityInstance, EventTypes.MODIFY, entityAliasType.getPrimaryKey(), EventTypes.DELETE, deletedBy);
    }

    public void deleteEntityAliases(List<EntityAlias> entityAliases, BasePK deletedBy) {
        entityAliases.forEach((entityAlias) ->
                deleteEntityAlias(entityAlias, deletedBy)
        );
    }

    public void deleteEntityAliasesByEntityAliasType(EntityAliasType entityAliasType, BasePK deletedBy) {
        deleteEntityAliases(getEntityAliasesByEntityAliasTypeForUpdate(entityAliasType), deletedBy);
    }

    public void deleteEntityAliasesByEntityInstance(EntityInstance entityInstance, BasePK deletedBy) {
        deleteEntityAliases(getEntityAliasesByEntityInstanceForUpdate(entityInstance), deletedBy);
    }

    public EntityAlias getEntityAliasByEntityAliasTypeAndAlias(EntityAliasType entityAliasType, String alias) {
        var query = session.getDslContext()
                .select(EntityAliases.fields())
                .from(EntityAliases)
                .where(EntityAliases.ENTITY_ALIAS_TYPE.eq(entityAliasType.getPrimaryKey()),
                        EntityAliases.ALIAS.eq(alias),
                        EntityAliases.THRU_TIME.eq(Session.MAX_TIME));

        return entityAliasFactory.getEntityFromQuery(EntityPermission.READ_ONLY, query);
    }

}
