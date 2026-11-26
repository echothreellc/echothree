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

package com.echothree.model.control.core.server.logic;

import com.echothree.control.user.core.common.edit.EntityListItemAttributeEdit;
import com.echothree.control.user.core.common.spec.EntityListItemUniversalSpec;
import com.echothree.control.user.core.common.spec.EntityListItemUuid;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.DuplicateEntityListItemNameException;
import com.echothree.model.control.core.common.exception.InvalidEntityAttributeTypeException;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.MismatchedEntityListItemException;
import com.echothree.model.control.core.common.exception.UnknownEntityListItemNameException;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.queue.common.QueueTypes;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.control.queue.server.logic.QueueTypeLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.exception.MissingDefaultSequenceException;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.value.EntityListItemDetailValue;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.queue.server.value.QueuedEntityValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EntityListItemLogic
        extends BaseLogic {

    @Inject
    protected IndexControl indexControl;

    @Inject
    protected QueueControl queueControl;

    @Inject
    protected EntityAttributeLogic entityAttributeLogic;

    @Inject
    protected EntityInstanceLogic entityInstanceLogic;

    @Inject
    protected QueueTypeLogic queueTypeLogic;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;

    protected EntityListItemLogic() {
        super();
    }

    public EntityListItem createEntityListItem(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            String entityListItemName, final Boolean isDefault, final Integer sortOrder, final BasePK createdBy,
            final Language language, final String description) {
        EntityListItem entityListItem = null;
        var entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

        if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())
                || entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
            if(entityListItemName == null) {
                var entityAttributeListItem = coreControl.getEntityAttributeListItem(entityAttribute);
                var entityListItemSequence = entityAttributeListItem == null ? null : entityAttributeListItem.getEntityListItemSequence();

                if(entityListItemSequence == null) {
                    entityListItemSequence = sequenceGeneratorLogic.getDefaultSequence(eea, SequenceTypes.ENTITY_LIST_ITEM.name());
                }

                if(eea != null && !hasExecutionErrors(eea)) {
                    entityListItemName = sequenceGeneratorLogic.getNextSequenceValue(eea, entityListItemSequence);
                } else {
                    handleExecutionError(MissingDefaultSequenceException.class, eea, ExecutionErrors.MissingDefaultSequence.name(),
                            SequenceTypes.ENTITY_LIST_ITEM.name());
                }
            }

            if(eea != null && !hasExecutionErrors(eea)) {
                entityListItem = entityListItemName == null ? null : coreControl.getEntityListItemByName(entityAttribute, entityListItemName);

                if(entityListItem == null) {
                    entityListItem = coreControl.createEntityListItem(entityAttribute, entityListItemName, isDefault, sortOrder,
                            createdBy);

                    if(description != null) {
                        coreControl.createEntityListItemDescription(entityListItem, language, description, createdBy);
                    }
                } else {
                    var entityAttributeDetail = entityAttribute.getLastDetail();
                    var entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();

                    handleExecutionError(DuplicateEntityListItemNameException.class, eea, ExecutionErrors.DuplicateEntityListItemName.name(),
                            entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                            entityTypeDetail.getEntityTypeName(), entityAttributeDetail.getEntityAttributeName(),
                            entityListItemName);
                }
            }
        } else {
            var entityAttributeDetail = entityAttribute.getLastDetail();
            var entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();

            handleExecutionError(InvalidEntityAttributeTypeException.class, eea, ExecutionErrors.InvalidEntityAttributeType.name(),
                            entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                            entityTypeDetail.getEntityTypeName(), entityAttributeDetail.getEntityAttributeName(),
                            entityAttributeTypeName);
        }

        return entityListItem;
    }

    public EntityListItem getEntityListItemByName(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final String entityListItemName, final EntityPermission entityPermission) {
        var entityListItem = coreControl.getEntityListItemByName(entityAttribute, entityListItemName, entityPermission);

        if(entityListItem == null) {
            var entityAttributeDetail = entityAttribute.getLastDetail();
            var entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityListItemNameException.class, eea, ExecutionErrors.UnknownEntityListItemName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityListItemName);
        }

        return entityListItem;
    }

    public EntityListItem getEntityListItemByName(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final String entityListItemName) {
        return getEntityListItemByName(eea, entityAttribute, entityListItemName, EntityPermission.READ_ONLY);
    }

    public EntityListItem getEntityListItemByNameForUpdate(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final String entityListItemName) {
        return getEntityListItemByName(eea, entityAttribute, entityListItemName, EntityPermission.READ_WRITE);
    }

    public EntityListItem getEntityListItemByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String entityAttributeName, final String entityListItemName,
            final EntityPermission entityPermission) {
        var entityAttribute = entityAttributeLogic.getEntityAttributeByName(eea, componentVendorName,
                entityTypeName, entityAttributeName);
        EntityListItem entityListItem = null;

        if(eea == null || !eea.hasExecutionErrors()) {
            entityListItem = getEntityListItemByName(eea, entityAttribute, entityListItemName, entityPermission);
        }

        return entityListItem;
    }

    public EntityListItem getEntityListItemByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String entityAttributeName, final String entityListItemName) {
        return getEntityListItemByName(eea, componentVendorName, entityTypeName, entityAttributeName, entityListItemName,
                EntityPermission.READ_ONLY);
    }

    public EntityListItem getEntityListItemByNameForUpdate(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String entityAttributeName, final String entityListItemName) {
        return getEntityListItemByName(eea, componentVendorName, entityTypeName, entityAttributeName, entityListItemName,
                EntityPermission.READ_WRITE);
    }

    public EntityListItem getEntityListItemByUuid(final ExecutionErrorAccumulator eea, final String uuid, final EntityPermission entityPermission) {
        EntityListItem entityListItem = null;

        var entityInstance = entityInstanceLogic.getEntityInstance(eea, (String)null, uuid,
                ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityListItem.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            entityListItem = coreControl.getEntityListItemByEntityInstance(entityInstance, entityPermission);
        }

        return entityListItem;
    }

    public EntityListItem getEntityListItemByUuid(final ExecutionErrorAccumulator eea, final String uuid) {
        return getEntityListItemByUuid(eea, uuid, EntityPermission.READ_ONLY);
    }

    public EntityListItem getEntityListItemByUuidForUpdate(final ExecutionErrorAccumulator eea, final String uuid) {
        return getEntityListItemByUuid(eea, uuid, EntityPermission.READ_WRITE);
    }

    public EntityListItem getEntityListItemByUniversalSpec(final ExecutionErrorAccumulator eea, final EntityListItemUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        EntityListItem entityListItem = null;
        var componentVendorName = universalSpec.getComponentVendorName();
        var entityTypeName = universalSpec.getEntityTypeName();
        var entityAttributeName = universalSpec.getEntityAttributeName();
        var entityListItemName = universalSpec.getEntityListItemName();
        var universalSpecCount = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        var parameterCount = (componentVendorName == null && entityTypeName == null && entityAttributeName == null && entityListItemName == null ? 0 : 1)
                + universalSpecCount;

        switch(parameterCount) {
            case 1 -> {
                if(universalSpecCount == 1) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityListItem.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        entityListItem = coreControl.getEntityListItemByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    entityListItem = getEntityListItemByName(eea, componentVendorName, entityTypeName, entityAttributeName,
                            entityListItemName, entityPermission);
                }
            }
            default -> {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }
        }

        return entityListItem;
    }

    public EntityListItem getEntityListItemByUniversalSpec(final ExecutionErrorAccumulator eea, final EntityListItemUniversalSpec universalSpec) {
        return getEntityListItemByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public EntityListItem getEntityListItemByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final EntityListItemUniversalSpec universalSpec) {
        return getEntityListItemByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    public EntityListItem getEntityListItem(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityListItemAttributeEdit edit, final EntityPermission entityPermission) {
        EntityListItem entityListItem = null;
        var entityListItemName = edit.getEntityListItemName();
        var entityListItemUuid = edit.getEntityListItemUuid();
        var parameterCount = (entityListItemName == null ? 0 : 1) + (entityListItemUuid == null ? 0 : 1);

        if (parameterCount == 1) {
            entityListItem = entityListItemName == null
                    ? getEntityListItemByUuid(eea, entityListItemUuid, entityPermission)
                    : getEntityListItemByName(eea, entityAttribute, entityListItemName, entityPermission);
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        if((eea == null || !eea.hasExecutionErrors()) && entityListItemUuid != null) {
            var foundEntityAttribute = entityListItem.getLastDetail().getEntityAttribute();

            if(!foundEntityAttribute.equals(entityAttribute)) {
                handleExecutionError(MismatchedEntityListItemException.class, eea, ExecutionErrors.MismatchedEntityListItem.name(),
                        entityAttribute.getLastDetail().getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName(),
                        entityAttribute.getLastDetail().getEntityType().getLastDetail().getEntityTypeName(),
                        entityAttribute.getLastDetail().getEntityAttributeName(),
                        foundEntityAttribute.getLastDetail().getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName(),
                        foundEntityAttribute.getLastDetail().getEntityType().getLastDetail().getEntityTypeName(),
                        foundEntityAttribute.getLastDetail().getEntityAttributeName(),
                        entityListItem.getLastDetail().getEntityListItem());
            }
        }

        return entityListItem;
    }

    public EntityListItem getEntityListItem(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityListItemAttributeEdit edit) {
        return getEntityListItem(eea, entityAttribute, edit, EntityPermission.READ_ONLY);
    }

    public EntityListItem getEntityListItemForUpdate(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityListItemAttributeEdit edit, final EntityListItemUuid uuid) {
        return getEntityListItem(eea, entityAttribute, edit, EntityPermission.READ_WRITE);
    }

    public void updateEntityListItemFromValue(final Session session, EntityListItemDetailValue entityListItemDetailValue, BasePK updatedBy) {
        if(entityListItemDetailValue.getEntityListItemNameHasBeenModified()) {
            var entityListItem = coreControl.getEntityListItemByPK(entityListItemDetailValue.getEntityListItemPK());
            var entityAttributeDetail = entityListItem.getLastDetail().getEntityAttribute().getLastDetail();

            if(indexControl.countIndexTypesByEntityType(entityAttributeDetail.getEntityType()) > 0) {
                var queueTypePK = queueTypeLogic.getQueueTypeByName(null, QueueTypes.INDEXING.name()).getPrimaryKey();
                var entityAttributeTypeName = entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName();

                if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
                    var entityListItemAttributes = coreControl.getEntityListItemAttributesByEntityListItem(entityListItem);
                    List<QueuedEntityValue> queuedEntities = new ArrayList<>(entityListItemAttributes.size());

                    entityListItemAttributes.forEach((entityListItemAttribute) -> {
                        queuedEntities.add(new QueuedEntityValue(queueTypePK, entityListItemAttribute.getEntityInstancePK(), session.getStartTime()));
                    });

                    queueControl.createQueuedEntities(queuedEntities);
                } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
                    var entityMultipleListItemAttributes = coreControl.getEntityMultipleListItemAttributesByEntityListItem(entityListItem);
                    List<QueuedEntityValue> queuedEntities = new ArrayList<>(entityMultipleListItemAttributes.size());

                    entityMultipleListItemAttributes.forEach((entityMultipleListItemAttribute) -> {
                        queuedEntities.add(new QueuedEntityValue(queueTypePK, entityMultipleListItemAttribute.getEntityInstancePK(), session.getStartTime()));
                    });

                    queueControl.createQueuedEntities(queuedEntities);
                }
            }
        }

        coreControl.updateEntityListItemFromValue(entityListItemDetailValue, updatedBy);
    }

    public void deleteEntityListItem(final ExecutionErrorAccumulator eea, EntityListItem entityListItem, BasePK deletedBy) {
        coreControl.deleteEntityListItem(entityListItem, deletedBy);
    }

}
