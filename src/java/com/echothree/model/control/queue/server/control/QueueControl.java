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

package com.echothree.model.control.queue.server.control;

import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.queue.common.choice.QueueTypeChoicesBean;
import com.echothree.model.control.queue.common.transfer.QueueTypeDescriptionTransfer;
import com.echothree.model.control.queue.common.transfer.QueueTypeTransfer;
import com.echothree.model.control.queue.common.transfer.QueuedEntityTransfer;
import com.echothree.model.control.queue.server.transfer.QueueTypeDescriptionTransferCache;
import com.echothree.model.control.queue.server.transfer.QueueTypeTransferCache;
import com.echothree.model.control.queue.server.transfer.QueuedEntityTransferCache;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.queue.common.pk.QueueTypePK;
import com.echothree.model.data.queue.server.entity.QueueType;
import com.echothree.model.data.queue.server.entity.QueueTypeDescription;
import com.echothree.model.data.queue.server.entity.QueuedEntity;
import com.echothree.model.data.queue.server.factory.QueueTypeDescriptionFactory;
import com.echothree.model.data.queue.server.factory.QueueTypeDetailFactory;
import com.echothree.model.data.queue.server.factory.QueueTypeFactory;
import com.echothree.model.data.queue.server.factory.QueuedEntityFactory;
import com.echothree.model.data.queue.server.value.QueueTypeDescriptionValue;
import com.echothree.model.data.queue.server.value.QueueTypeDetailValue;
import com.echothree.model.data.queue.server.value.QueuedEntityValue;
import com.echothree.model.data.user.server.entity.UserVisit;
import static com.echothree.model.jooq.server.keys.queue.QueueForeignKeys.QUEUED_ENTITIES_ENTITY_INSTANCE_FK;
import static com.echothree.model.jooq.server.keys.queue.QueueForeignKeys.QUEUE_TYPES_ACTIVE_DETAIL_FK;
import static com.echothree.model.jooq.server.keys.queue.QueueForeignKeys.QUEUE_TYPE_DESCRIPTIONS_LANGUAGE_FK;
import static com.echothree.model.jooq.server.tables.core.EntityInstances.EntityInstances;
import static com.echothree.model.jooq.server.tables.party.Languages.Languages;
import static com.echothree.model.jooq.server.tables.queue.QueueTypeDescriptions.QueueTypeDescriptions;
import static com.echothree.model.jooq.server.tables.queue.QueueTypeDetails.QueueTypeDetails;
import static com.echothree.model.jooq.server.tables.queue.QueueTypes.QueueTypes;
import static com.echothree.model.jooq.server.tables.queue.QueuedEntities.QueuedEntities;
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
public class QueueControl
        extends BaseModelControl {
    
    /** Creates a new instance of QueueControl */
    protected QueueControl() {
        super();
    }
    
    // --------------------------------------------------------------------------------
    //   Queue Types
    // --------------------------------------------------------------------------------

    @Inject
    protected QueueTypeDetailFactory queueTypeDetailFactory;

    @Inject
    private QueueTypeFactory queueTypeFactory;

    @Inject
    private QueueTypeTransferCache queueTypeTransferCache;

    public QueueType createQueueType(String queueTypeName, Boolean isDefault, Integer sortOrder, BasePK createdBy) {
        var defaultQueueType = getDefaultQueueType();
        var defaultFound = defaultQueueType != null;

        if(defaultFound && isDefault) {
            var defaultQueueTypeDetailValue = getDefaultQueueTypeDetailValueForUpdate();

            defaultQueueTypeDetailValue.setIsDefault(false);
            updateQueueTypeFromValue(defaultQueueTypeDetailValue, false, createdBy);
        } else if(!defaultFound) {
            isDefault = true;
        }

        var queueType = queueTypeFactory.create();
        var queueTypeDetail = queueTypeDetailFactory.create(queueType, queueTypeName, isDefault, sortOrder, session.getStartTime(),
                Session.MAX_TIME);

        // Convert to R/W
        queueType = queueTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE, queueType.getPrimaryKey());
        queueType.setActiveDetail(queueTypeDetail);
        queueType.setLastDetail(queueTypeDetail);
        queueType.store();

        sendEvent(queueType.getPrimaryKey(), EventTypes.CREATE, null, null, createdBy);

        return queueType;
    }

    /** Assume that the entityInstance passed to this function is a ECHO_THREE.QueueType */
    public QueueType getQueueTypeByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var pk = new QueueTypePK(entityInstance.getEntityUniqueId());

        return queueTypeFactory.getEntityFromPK(entityPermission, pk);
    }

    public QueueType getQueueTypeByEntityInstance(EntityInstance entityInstance) {
        return getQueueTypeByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }

    public QueueType getQueueTypeByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getQueueTypeByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }

    public long countQueueTypes() {
        return session.getDslContext()
                .selectCount()
                .from(QueueTypes)
                .join(QueueTypeDetails).onKey(QUEUE_TYPES_ACTIVE_DETAIL_FK)
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    private QueueType getQueueTypeByName(String queueTypeName, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(QueueTypes.fields())
                .from(QueueTypes)
                .join(QueueTypeDetails).onKey(QUEUE_TYPES_ACTIVE_DETAIL_FK)
                .where(QueueTypeDetails.QUEUE_TYPE_NAME.eq(queueTypeName));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return queueTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public QueueType getQueueTypeByName(String queueTypeName) {
        return getQueueTypeByName(queueTypeName, EntityPermission.READ_ONLY);
    }

    public QueueType getQueueTypeByNameForUpdate(String queueTypeName) {
        return getQueueTypeByName(queueTypeName, EntityPermission.READ_WRITE);
    }

    public QueueTypeDetailValue getQueueTypeDetailValueForUpdate(QueueType queueType) {
        return queueType == null? null: queueType.getLastDetailForUpdate().getQueueTypeDetailValue().clone();
    }

    public QueueTypeDetailValue getQueueTypeDetailValueByNameForUpdate(String queueTypeName) {
        return getQueueTypeDetailValueForUpdate(getQueueTypeByNameForUpdate(queueTypeName));
    }

    private QueueType getDefaultQueueType(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(QueueTypes.fields())
                .from(QueueTypes)
                .join(QueueTypeDetails).onKey(QUEUE_TYPES_ACTIVE_DETAIL_FK)
                .where(QueueTypeDetails.IS_DEFAULT.eq(true));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return queueTypeFactory.getEntityFromQuery(entityPermission, query);
    }

    public QueueType getDefaultQueueType() {
        return getDefaultQueueType(EntityPermission.READ_ONLY);
    }

    public QueueType getDefaultQueueTypeForUpdate() {
        return getDefaultQueueType(EntityPermission.READ_WRITE);
    }

    public QueueTypeDetailValue getDefaultQueueTypeDetailValueForUpdate() {
        return getDefaultQueueTypeForUpdate().getLastDetailForUpdate().getQueueTypeDetailValue().clone();
    }

    private List<QueueType> getQueueTypes(EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(QueueTypes.fields())
                .from(QueueTypes)
                .join(QueueTypeDetails).onKey(QUEUE_TYPES_ACTIVE_DETAIL_FK);

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(QueueTypeDetails.SORT_ORDER, QueueTypeDetails.QUEUE_TYPE_NAME),
                    QueueTypeFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return queueTypeFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<QueueType> getQueueTypes() {
        return getQueueTypes(EntityPermission.READ_ONLY);
    }

    public List<QueueType> getQueueTypesForUpdate() {
        return getQueueTypes(EntityPermission.READ_WRITE);
    }

   public QueueTypeTransfer getQueueTypeTransfer(UserVisit userVisit, QueueType queueType) {
        return queueTypeTransferCache.getQueueTypeTransfer(userVisit, queueType);
    }

    public List<QueueTypeTransfer> getQueueTypeTransfers(UserVisit userVisit, Collection<QueueType> queueTypes) {
        List<QueueTypeTransfer> queueTypeTransfers = new ArrayList<>(queueTypes.size());

        queueTypes.forEach((queueType) ->
                queueTypeTransfers.add(queueTypeTransferCache.getQueueTypeTransfer(userVisit, queueType))
        );

        return queueTypeTransfers;
    }

    public List<QueueTypeTransfer> getQueueTypeTransfers(UserVisit userVisit) {
        return getQueueTypeTransfers(userVisit, getQueueTypes());
    }

    public QueueTypeChoicesBean getQueueTypeChoices(String defaultQueueTypeChoice, Language language, boolean allowNullChoice) {
        var queueTypes = getQueueTypes();
        var size = queueTypes.size();
        var labels = new ArrayList<String>(size);
        var values = new ArrayList<String>(size);
        String defaultValue = null;

        if(allowNullChoice) {
            labels.add("");
            values.add("");

            if(defaultQueueTypeChoice == null) {
                defaultValue = "";
            }
        }

        for(var queueType : queueTypes) {
            var queueTypeDetail = queueType.getLastDetail();

            var label = getBestQueueTypeDescription(queueType, language);
            var value = queueTypeDetail.getQueueTypeName();

            labels.add(label == null? value: label);
            values.add(value);

            var usingDefaultChoice = defaultQueueTypeChoice != null && defaultQueueTypeChoice.equals(value);
            if(usingDefaultChoice || (defaultValue == null && queueTypeDetail.getIsDefault())) {
                defaultValue = value;
            }
        }

        return new QueueTypeChoicesBean(labels, values, defaultValue);
    }

    private void updateQueueTypeFromValue(QueueTypeDetailValue queueTypeDetailValue, boolean checkDefault, BasePK updatedBy) {
        if(queueTypeDetailValue.hasBeenModified()) {
            var queueType = queueTypeFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                     queueTypeDetailValue.getQueueTypePK());
            var queueTypeDetail = queueType.getActiveDetailForUpdate();

            queueTypeDetail.setThruTime(session.getStartTime());
            queueTypeDetail.store();

            var queueTypePK = queueTypeDetail.getQueueTypePK(); // Not updated
            var queueTypeName = queueTypeDetailValue.getQueueTypeName();
            var isDefault = queueTypeDetailValue.getIsDefault();
            var sortOrder = queueTypeDetailValue.getSortOrder();

            if(checkDefault) {
                var defaultQueueType = getDefaultQueueType();
                var defaultFound = defaultQueueType != null && !defaultQueueType.equals(queueType);

                if(isDefault && defaultFound) {
                    // If I'm the default, and a default already existed...
                    var defaultQueueTypeDetailValue = getDefaultQueueTypeDetailValueForUpdate();

                    defaultQueueTypeDetailValue.setIsDefault(false);
                    updateQueueTypeFromValue(defaultQueueTypeDetailValue, false, updatedBy);
                } else if(!isDefault && !defaultFound) {
                    // If I'm not the default, and no other default exists...
                    isDefault = true;
                }
            }

            queueTypeDetail = queueTypeDetailFactory.create(queueTypePK, queueTypeName, isDefault, sortOrder, session.getStartTime(),
                    Session.MAX_TIME);

            queueType.setActiveDetail(queueTypeDetail);
            queueType.setLastDetail(queueTypeDetail);

            sendEvent(queueTypePK, EventTypes.MODIFY, null, null, updatedBy);
        }
    }

    public void updateQueueTypeFromValue(QueueTypeDetailValue queueTypeDetailValue, BasePK updatedBy) {
        updateQueueTypeFromValue(queueTypeDetailValue, true, updatedBy);
    }

    private void deleteQueueType(QueueType queueType, boolean checkDefault, BasePK deletedBy) {
        var queueTypeDetail = queueType.getLastDetailForUpdate();

        removeQueuedEntitiesByQueueType(queueType);
        deleteQueueTypeDescriptionsByQueueType(queueType, deletedBy);

        queueTypeDetail.setThruTime(session.getStartTime());
        queueType.setActiveDetail(null);
        queueType.store();

        if(checkDefault) {
            // Check for default, and pick one if necessary
            var defaultQueueType = getDefaultQueueType();

            if(defaultQueueType == null) {
                var queueTypes = getQueueTypesForUpdate();

                if(!queueTypes.isEmpty()) {
                    var iter = queueTypes.iterator();
                    if(iter.hasNext()) {
                        defaultQueueType = iter.next();
                    }
                    var queueTypeDetailValue = Objects.requireNonNull(defaultQueueType).getLastDetailForUpdate().getQueueTypeDetailValue().clone();

                    queueTypeDetailValue.setIsDefault(true);
                    updateQueueTypeFromValue(queueTypeDetailValue, false, deletedBy);
                }
            }
        }

        sendEvent(queueType.getPrimaryKey(), EventTypes.DELETE, null, null, deletedBy);
    }

    public void deleteQueueType(QueueType queueType, BasePK deletedBy) {
        deleteQueueType(queueType, true, deletedBy);
    }

    private void deleteQueueTypes(List<QueueType> queueTypes, boolean checkDefault, BasePK deletedBy) {
        queueTypes.forEach((queueType) -> deleteQueueType(queueType, checkDefault, deletedBy));
    }

    public void deleteQueueTypes(List<QueueType> queueTypes, BasePK deletedBy) {
        deleteQueueTypes(queueTypes, true, deletedBy);
    }

    // --------------------------------------------------------------------------------
    //   Queue Type Descriptions
    // --------------------------------------------------------------------------------

    @Inject
    private QueueTypeDescriptionFactory queueTypeDescriptionFactory;

    @Inject
    private QueueTypeDescriptionTransferCache queueTypeDescriptionTransferCache;

    public QueueTypeDescription createQueueTypeDescription(QueueType queueType, Language language, String description, BasePK createdBy) {
        var queueTypeDescription = queueTypeDescriptionFactory.create(queueType, language, description,
                session.getStartTime(), Session.MAX_TIME);

        sendEvent(queueType.getPrimaryKey(), EventTypes.MODIFY, queueTypeDescription.getPrimaryKey(), EventTypes.CREATE, createdBy);

        return queueTypeDescription;
    }

    private QueueTypeDescription getQueueTypeDescription(QueueType queueType, Language language, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(QueueTypeDescriptions.fields())
                .from(QueueTypeDescriptions)
                .where(QueueTypeDescriptions.QUEUE_TYPE.eq(queueType.getPrimaryKey()),
                        QueueTypeDescriptions.LANGUAGE.eq(language.getPrimaryKey()),
                        QueueTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME));

        var query = switch(entityPermission) {
            case READ_ONLY -> baseQuery;
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return queueTypeDescriptionFactory.getEntityFromQuery(entityPermission, query);
    }

    public QueueTypeDescription getQueueTypeDescription(QueueType queueType, Language language) {
        return getQueueTypeDescription(queueType, language, EntityPermission.READ_ONLY);
    }

    public QueueTypeDescription getQueueTypeDescriptionForUpdate(QueueType queueType, Language language) {
        return getQueueTypeDescription(queueType, language, EntityPermission.READ_WRITE);
    }

    public QueueTypeDescriptionValue getQueueTypeDescriptionValue(QueueTypeDescription queueTypeDescription) {
        return queueTypeDescription == null? null: queueTypeDescription.getQueueTypeDescriptionValue().clone();
    }

    public QueueTypeDescriptionValue getQueueTypeDescriptionValueForUpdate(QueueType queueType, Language language) {
        return getQueueTypeDescriptionValue(getQueueTypeDescriptionForUpdate(queueType, language));
    }

    private List<QueueTypeDescription> getQueueTypeDescriptionsByQueueType(QueueType queueType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(QueueTypeDescriptions.fields())
                    .from(QueueTypeDescriptions)
                    .join(Languages).onKey(QUEUE_TYPE_DESCRIPTIONS_LANGUAGE_FK)
                    .where(QueueTypeDescriptions.QUEUE_TYPE.eq(queueType.getPrimaryKey()),
                            QueueTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .orderBy(Languages.SORT_ORDER, Languages.LANGUAGE_ISO_NAME),
                    QueueTypeDescriptionFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(QueueTypeDescriptions.fields())
                    .from(QueueTypeDescriptions)
                    .where(QueueTypeDescriptions.QUEUE_TYPE.eq(queueType.getPrimaryKey()),
                            QueueTypeDescriptions.THRU_TIME.eq(Session.MAX_TIME))
                    .forUpdate();
        };

        return queueTypeDescriptionFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<QueueTypeDescription> getQueueTypeDescriptionsByQueueType(QueueType queueType) {
        return getQueueTypeDescriptionsByQueueType(queueType, EntityPermission.READ_ONLY);
    }

    public List<QueueTypeDescription> getQueueTypeDescriptionsByQueueTypeForUpdate(QueueType queueType) {
        return getQueueTypeDescriptionsByQueueType(queueType, EntityPermission.READ_WRITE);
    }

    public String getBestQueueTypeDescription(QueueType queueType, Language language) {
        String description;
        var queueTypeDescription = getQueueTypeDescription(queueType, language);

        if(queueTypeDescription == null && !language.getIsDefault()) {
            queueTypeDescription = getQueueTypeDescription(queueType, partyControl.getDefaultLanguage());
        }

        if(queueTypeDescription == null) {
            description = queueType.getLastDetail().getQueueTypeName();
        } else {
            description = queueTypeDescription.getDescription();
        }

        return description;
    }

    public QueueTypeDescriptionTransfer getQueueTypeDescriptionTransfer(UserVisit userVisit, QueueTypeDescription queueTypeDescription) {
        return queueTypeDescriptionTransferCache.getQueueTypeDescriptionTransfer(userVisit, queueTypeDescription);
    }

    public List<QueueTypeDescriptionTransfer> getQueueTypeDescriptionTransfersByQueueType(UserVisit userVisit, QueueType queueType) {
        var queueTypeDescriptions = getQueueTypeDescriptionsByQueueType(queueType);
        List<QueueTypeDescriptionTransfer> queueTypeDescriptionTransfers = new ArrayList<>(queueTypeDescriptions.size());

        queueTypeDescriptions.forEach((queueTypeDescription) ->
                queueTypeDescriptionTransfers.add(queueTypeDescriptionTransferCache.getQueueTypeDescriptionTransfer(userVisit, queueTypeDescription))
        );

        return queueTypeDescriptionTransfers;
    }

    public void updateQueueTypeDescriptionFromValue(QueueTypeDescriptionValue queueTypeDescriptionValue, BasePK updatedBy) {
        if(queueTypeDescriptionValue.hasBeenModified()) {
            var queueTypeDescription = queueTypeDescriptionFactory.getEntityFromPK(EntityPermission.READ_WRITE,
                    queueTypeDescriptionValue.getPrimaryKey());

            queueTypeDescription.setThruTime(session.getStartTime());
            queueTypeDescription.store();

            var queueType = queueTypeDescription.getQueueType();
            var language = queueTypeDescription.getLanguage();
            var description = queueTypeDescriptionValue.getDescription();

            queueTypeDescription = queueTypeDescriptionFactory.create(queueType, language, description,
                    session.getStartTime(), Session.MAX_TIME);

            sendEvent(queueType.getPrimaryKey(), EventTypes.MODIFY, queueTypeDescription.getPrimaryKey(), EventTypes.MODIFY, updatedBy);
        }
    }

    public void deleteQueueTypeDescription(QueueTypeDescription queueTypeDescription, BasePK deletedBy) {
        queueTypeDescription.setThruTime(session.getStartTime());

        sendEvent(queueTypeDescription.getQueueTypePK(), EventTypes.MODIFY, queueTypeDescription.getPrimaryKey(), EventTypes.DELETE, deletedBy);

    }

    public void deleteQueueTypeDescriptionsByQueueType(QueueType queueType, BasePK deletedBy) {
        var queueTypeDescriptions = getQueueTypeDescriptionsByQueueTypeForUpdate(queueType);

        queueTypeDescriptions.forEach((queueTypeDescription) -> 
                deleteQueueTypeDescription(queueTypeDescription, deletedBy)
        );
    }

    // --------------------------------------------------------------------------------
    //   Queued Entities
    // --------------------------------------------------------------------------------

    @Inject
    private QueuedEntityFactory queuedEntityFactory;

    @Inject
    private QueuedEntityTransferCache queuedEntityTransferCache;

    public QueuedEntity createQueuedEntity(QueueType queueType, EntityInstance entityInstance) {
        return queuedEntityFactory.create(queueType, entityInstance, session.getStartTime());
    }

    public void createQueuedEntities(Collection<QueuedEntityValue> queuedEntities) {
        queuedEntityFactory.create(queuedEntities);
    }
    
    public Long countQueuedEntitiesByQueueType(final QueueType queueType) {
        return session.getDslContext()
                .selectCount()
                .from(QueuedEntities)
                .where(QueuedEntities.QUEUE_TYPE.eq(queueType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public Long oldestQueuedEntityTimeByQueueType(final QueueType queueType) {
        return session.getDslContext()
                .select(org.jooq.impl.DSL.min(QueuedEntities.TIME))
                .from(QueuedEntities)
                .where(QueuedEntities.QUEUE_TYPE.eq(queueType.getPrimaryKey()))
                .fetchOne(0, Long.class);
    }

    public Long latestQueuedEntityTimeByQueueType(final QueueType queueType) {
        return session.getDslContext()
                .select(org.jooq.impl.DSL.max(QueuedEntities.TIME))
                .from(QueuedEntities)
                .where(QueuedEntities.QUEUE_TYPE.eq(queueType.getPrimaryKey()))
                .fetchOne(0, Long.class);
    }

    public Long countQueuedEntitiesByEntityInstance(final EntityInstance entityInstance) {
        return session.getDslContext()
                .selectCount()
                .from(QueuedEntities)
                .where(QueuedEntities.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public Long countQueuedEntitiesByEntityType(final QueueType queueType, final EntityType entityType) {
        return session.getDslContext()
                .selectCount()
                .from(QueuedEntities)
                .join(EntityInstances).onKey(QUEUED_ENTITIES_ENTITY_INSTANCE_FK)
                .where(QueuedEntities.QUEUE_TYPE.eq(queueType.getPrimaryKey()),
                        EntityInstances.ENTITY_TYPE.eq(entityType.getPrimaryKey()))
                .fetchOptional(0, Long.class)
                .orElse(0L);
    }

    public boolean isQueueTypeUsedByQueuedEntities(QueueType queueType) {
        return countQueuedEntitiesByQueueType(queueType) != 0;
    }

    public boolean isEntityInstanceUsedByQueuedEntities(EntityInstance entityInstance) {
        return countQueuedEntitiesByEntityInstance(entityInstance) != 0;
    }

    private List<QueuedEntity> getQueuedEntities(QueueType queueType, EntityInstance entityInstance, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(QueuedEntities.fields())
                .from(QueuedEntities)
                .where(QueuedEntities.QUEUE_TYPE.eq(queueType.getPrimaryKey()),
                        QueuedEntities.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(QueuedEntities.TIME),
                    QueuedEntityFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return queuedEntityFactory.getEntitiesFromQuery(entityPermission, query);
    }

    public List<QueuedEntity> getQueuedEntities(QueueType queueType, EntityInstance entityInstance) {
        return getQueuedEntities(queueType, entityInstance, EntityPermission.READ_ONLY);
    }

    public List<QueuedEntity> getQueuedEntitiesForUpdate(QueueType queueType, EntityInstance entityInstance) {
        return getQueuedEntities(queueType, entityInstance, EntityPermission.READ_WRITE);
    }

    private List<QueuedEntity> getQueuedEntitiesByQueueType(QueueType queueType, EntityPermission entityPermission) {
        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(session.getDslContext()
                    .select(QueuedEntities.fields())
                    .from(QueuedEntities)
                    .where(QueuedEntities.QUEUE_TYPE.eq(queueType.getPrimaryKey()))
                    .orderBy(QueuedEntities.QUEUED_ENTITY),
                    QueuedEntityFactory.class);
            case READ_WRITE -> session.getDslContext()
                    .select(QueuedEntities.fields())
                    .from(QueuedEntities)
                    .where(QueuedEntities.QUEUE_TYPE.eq(queueType.getPrimaryKey()))
                    .orderBy(QueuedEntities.QUEUED_ENTITY)
                    .forUpdate();
        };

        return queuedEntityFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<QueuedEntity> getQueuedEntitiesByQueueType(QueueType queueType) {
        return getQueuedEntitiesByQueueType(queueType, EntityPermission.READ_ONLY);
    }
    
    public List<QueuedEntity> getQueuedEntitiesByQueueTypeForUpdate(QueueType queueType) {
        return getQueuedEntitiesByQueueType(queueType, EntityPermission.READ_WRITE);
    }
    
    private List<QueuedEntity> getQueuedEntitiesByEntityInstance(EntityInstance entityInstance, EntityPermission entityPermission) {
        var baseQuery = session.getDslContext()
                .select(QueuedEntities.fields())
                .from(QueuedEntities)
                .where(QueuedEntities.ENTITY_INSTANCE.eq(entityInstance.getPrimaryKey()));

        var query = switch(entityPermission) {
            case READ_ONLY -> session.applyLimit(baseQuery
                    .orderBy(QueuedEntities.QUEUED_ENTITY),
                    QueuedEntityFactory.class);
            case READ_WRITE -> baseQuery.forUpdate();
        };

        return queuedEntityFactory.getEntitiesFromQuery(entityPermission, query);
    }
    
    public List<QueuedEntity> getQueuedEntitiesByEntityInstance(EntityInstance entityInstance) {
        return getQueuedEntitiesByEntityInstance(entityInstance, EntityPermission.READ_ONLY);
    }
    
    public List<QueuedEntity> getQueuedEntitiesByEntityInstanceForUpdate(EntityInstance entityInstance) {
        return getQueuedEntitiesByEntityInstance(entityInstance, EntityPermission.READ_WRITE);
    }
    
    public QueuedEntityTransfer getQueuedEntityTransfer(UserVisit userVisit, QueuedEntity queuedEntity) {
        return queuedEntityTransferCache.getQueuedEntityTransfer(userVisit, queuedEntity);
    }
    
    public List<QueuedEntityTransfer> getQueuedEntityTransfers(UserVisit userVisit, Collection<QueuedEntity> queuedEntities) {
        List<QueuedEntityTransfer> queuedEntityTransfers = new ArrayList<>(queuedEntities.size());

        queuedEntities.forEach((queuedEntity) ->
                queuedEntityTransfers.add(queuedEntityTransferCache.getQueuedEntityTransfer(userVisit, queuedEntity))
        );

        return queuedEntityTransfers;
    }
    
    public List<QueuedEntityTransfer> getQueuedEntityTransfers(UserVisit userVisit, QueueType queueType) {
        return getQueuedEntityTransfers(userVisit, getQueuedEntitiesByQueueType(queueType));
    }

    public List<QueuedEntityTransfer> getQueuedEntityTransfersByEntityInstance(UserVisit userVisit, EntityInstance entityInstance) {
        return getQueuedEntityTransfers(userVisit, getQueuedEntitiesByEntityInstance(entityInstance));
    }

    public void removeQueuedEntity(QueuedEntity queuedEntity) {
        queuedEntity.remove();
    }

    private void removedQueuedEntities(List<QueuedEntity> queuedentities) {
        queuedentities.forEach((queuedEntity) -> {
            removeQueuedEntity(queuedEntity);
        });
    }
    
    public void removeQueuedEntitiesByQueueType(QueueType queueType) {
        removedQueuedEntities(getQueuedEntitiesByQueueTypeForUpdate(queueType));
    }
    
    public void removeQueuedEntitiesByEntityInstance(EntityInstance entityInstance) {
        removedQueuedEntities(getQueuedEntitiesByEntityInstanceForUpdate(entityInstance));
    }
    
}
