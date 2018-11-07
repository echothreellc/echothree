// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.control.user.core.common.spec.EntityAttributeSpec;
import com.echothree.control.user.core.common.spec.EntityAttributeUlid;
import com.echothree.control.user.core.common.spec.EntityListItemSpec;
import com.echothree.control.user.core.common.spec.EntityListItemUlid;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.DuplicateEntityAttributeNameException;
import com.echothree.model.control.core.common.exception.DuplicateEntityBooleanAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityIntegerAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityListItemAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityListItemNameException;
import com.echothree.model.control.core.common.exception.DuplicateEntityLongAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityMultipleListItemAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityStringAttributeException;
import com.echothree.model.control.core.common.exception.InvalidEntityAttributeTypeException;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.InvalidStringAttributeException;
import com.echothree.model.control.core.common.exception.LowerRangeExceededException;
import com.echothree.model.control.core.common.exception.MismatchedEntityAttributeTypeException;
import com.echothree.model.control.core.common.exception.MismatchedEntityListItemException;
import com.echothree.model.control.core.common.exception.MismatchedEntityTypeException;
import com.echothree.model.control.core.common.exception.UnknownEntityAttributeGroupNameException;
import com.echothree.model.control.core.common.exception.UnknownEntityAttributeNameException;
import com.echothree.model.control.core.common.exception.UnknownEntityAttributeTypeNameException;
import com.echothree.model.control.core.common.exception.UnknownEntityListItemNameException;
import com.echothree.model.control.core.common.exception.UpperRangeExceededException;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.core.server.database.EntityInstanceResult;
import com.echothree.model.control.core.server.database.EntityInstancesByBlobEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesByBooleanEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesByClobEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesByCollectionEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesByDateEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesByEntityEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesByGeoPointEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesByIntegerEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesByListItemEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesByLongEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesByMultipleListItemEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesByNameEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesByStringEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesByTimeEntityAttributeQuery;
import com.echothree.model.control.index.server.IndexControl;
import com.echothree.model.control.queue.common.QueueConstants;
import com.echothree.model.control.queue.server.QueueControl;
import com.echothree.model.control.queue.server.logic.QueueTypeLogic;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.common.exception.MissingDefaultSequenceException;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceLogic;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeDetail;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeInteger;
import com.echothree.model.data.core.server.entity.EntityAttributeListItem;
import com.echothree.model.data.core.server.entity.EntityAttributeLong;
import com.echothree.model.data.core.server.entity.EntityAttributeString;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityBooleanAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityIntegerAttribute;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityLongAttribute;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.value.EntityAttributeDetailValue;
import com.echothree.model.data.core.server.value.EntityListItemDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.queue.common.pk.QueueTypePK;
import com.echothree.model.data.queue.server.value.QueuedEntityValue;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntityAttributeLogic
        extends BaseLogic {
    
    private EntityAttributeLogic() {
        super();
    }
    
    private static class EntityAttributeLogicHolder {
        static EntityAttributeLogic instance = new EntityAttributeLogic();
    }
    
    public static EntityAttributeLogic getInstance() {
        return EntityAttributeLogicHolder.instance;
    }
    
    public EntityAttributeType getEntityAttributeTypeByName(final ExecutionErrorAccumulator eea, final String entityAttributeTypeName) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        EntityAttributeType entityAttributeType = coreControl.getEntityAttributeTypeByName(entityAttributeTypeName);

        if(entityAttributeType == null) {
            handleExecutionError(UnknownEntityAttributeTypeNameException.class, eea, ExecutionErrors.UnknownEntityAttributeTypeName.name(), entityAttributeTypeName);
        }

        return entityAttributeType;
    }

    public EntityAttributeType getEntityAttributeTypeByUlid(final ExecutionErrorAccumulator eea, final String ulid, final EntityPermission entityPermission) {
        EntityAttributeType entityAttributeType = null;
        
        EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, null, null, ulid,
                ComponentVendors.ECHOTHREE.name(), EntityTypes.EntityAttributeType.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            
            entityAttributeType = coreControl.getEntityAttributeTypeByEntityInstance(entityInstance, entityPermission);
        }

        return entityAttributeType;
    }
    
    public EntityAttributeType getEntityAttributeTypeByUlid(final ExecutionErrorAccumulator eea, final String ulid) {
        return getEntityAttributeTypeByUlid(eea, ulid, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeType getEntityAttributeTypeByUlidForUpdate(final ExecutionErrorAccumulator eea, final String ulid) {
        return getEntityAttributeTypeByUlid(eea, ulid, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeGroup getEntityAttributeGroupByName(final ExecutionErrorAccumulator eea, final String entityAttributeGroupName) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        EntityAttributeGroup entityAttributeGroup = coreControl.getEntityAttributeGroupByName(entityAttributeGroupName);

        if(entityAttributeGroup == null) {
            handleExecutionError(UnknownEntityAttributeGroupNameException.class, eea, ExecutionErrors.UnknownEntityAttributeGroupName.name(),
                    entityAttributeGroupName);
        }

        return entityAttributeGroup;
    }

    public EntityAttributeGroup getEntityAttributeGroupByUlid(final ExecutionErrorAccumulator eea, final String ulid, final EntityPermission entityPermission) {
        EntityAttributeGroup entityAttributeGroup = null;
        
        EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, null, null, ulid,
                ComponentVendors.ECHOTHREE.name(), EntityTypes.EntityAttributeGroup.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            
            entityAttributeGroup = coreControl.getEntityAttributeGroupByEntityInstance(entityInstance, entityPermission);
        }

        return entityAttributeGroup;
    }
    
    public EntityAttributeGroup getEntityAttributeGroupByUlid(final ExecutionErrorAccumulator eea, final String ulid) {
        return getEntityAttributeGroupByUlid(eea, ulid, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeGroup getEntityAttributeGroupByUlidForUpdate(final ExecutionErrorAccumulator eea, final String ulid) {
        return getEntityAttributeGroupByUlid(eea, ulid, EntityPermission.READ_WRITE);
    }
    
    public EntityAttribute createEntityAttribute(final ExecutionErrorAccumulator eea, final EntityType entityType,
            String entityAttributeName, final EntityAttributeType entityAttributeType, final Boolean trackRevisions,
            final Boolean checkContentWebAddress, final String validationPattern, final Integer upperRangeIntegerValue,
            final Integer upperLimitIntegerValue, final Integer lowerLimitIntegerValue, final Integer lowerRangeIntegerValue,
            final Long upperRangeLongValue, final Long upperLimitLongValue, final Long lowerLimitLongValue,
            final Long lowerRangeLongValue, final Sequence entityListItemSequence, final UnitOfMeasureType unitOfMeasureType,
            final Integer sortOrder, final PartyPK createdByPK, final Language language, final String description) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        
        if(entityAttributeName == null) {
            SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
            Sequence sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceConstants.SequenceType_ENTITY_ATTRIBUTE);

            entityAttributeName = sequenceControl.getNextSequenceValue(sequence);
        }

        EntityAttribute entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);

        if(entityAttribute == null) {
            entityAttribute = coreControl.createEntityAttribute(entityType, entityAttributeName, entityAttributeType,
                    trackRevisions, sortOrder, createdByPK);

            if(description != null) {
                coreControl.createEntityAttributeDescription(entityAttribute, language, description, createdByPK);
            }
            
            switch(EntityAttributeTypes.valueOf(entityAttributeType.getEntityAttributeTypeName())) {
                case BLOB:
                    coreControl.createEntityAttributeBlob(entityAttribute, checkContentWebAddress, createdByPK);
                    break;
                case STRING:
                    if(validationPattern != null) {
                        coreControl.createEntityAttributeString(entityAttribute, validationPattern, createdByPK);
                    }
                    break;
                case INTEGER:
                    if(upperRangeIntegerValue != null || upperLimitIntegerValue != null || lowerLimitIntegerValue != null || lowerRangeIntegerValue != null) {
                        coreControl.createEntityAttributeInteger(entityAttribute, upperRangeIntegerValue, upperLimitIntegerValue,
                                lowerLimitIntegerValue, lowerRangeIntegerValue, createdByPK);
                    }
                    if(unitOfMeasureType != null) {
                        coreControl.createEntityAttributeNumeric(entityAttribute, unitOfMeasureType, createdByPK);
                    }
                    break;
                case LONG:
                    if(upperRangeLongValue != null || upperLimitLongValue != null || lowerLimitLongValue != null || lowerRangeLongValue != null) {
                        coreControl.createEntityAttributeLong(entityAttribute, upperRangeLongValue, upperLimitLongValue,
                                lowerLimitLongValue, lowerRangeLongValue, createdByPK);
                    }
                    if(unitOfMeasureType != null) {
                        coreControl.createEntityAttributeNumeric(entityAttribute, unitOfMeasureType, createdByPK);
                    }
                    break;
                case LISTITEM:
                case MULTIPLELISTITEM:
                    if(entityListItemSequence != null) {
                        coreControl.createEntityAttributeListItem(entityAttribute, entityListItemSequence, createdByPK);
                    }
                    break;
                default:
                    break;
            }
        } else {
            EntityTypeDetail entityTypeDetail = entityType.getLastDetail();
            
            handleExecutionError(DuplicateEntityAttributeNameException.class, eea, ExecutionErrors.DuplicateEntityAttributeName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(), entityAttributeName);
        }
        
        return entityAttribute;
    }
    
    public EntityAttribute getEntityAttributeByName(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String entityAttributeName, EntityPermission entityPermission) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        EntityAttribute entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName, entityPermission);

        if(entityAttribute == null) {
            EntityTypeDetail entityTypeDetail = entityType.getLastDetail();
            
            handleExecutionError(UnknownEntityAttributeNameException.class, eea, ExecutionErrors.UnknownEntityAttributeName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(), entityAttributeName);
        }

        return entityAttribute;
    }

    public EntityAttribute getEntityAttributeByName(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String entityAttributeName) {
        return getEntityAttributeByName(eea, entityType, entityAttributeName, EntityPermission.READ_ONLY);
    }
    
    public EntityAttribute getEntityAttributeByNameForUpdate(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String entityAttributeName) {
        return getEntityAttributeByName(eea, entityType, entityAttributeName, EntityPermission.READ_WRITE);
    }
    
    public EntityAttribute getEntityAttributeByName(final ExecutionErrorAccumulator eea, final ComponentVendor componentVendor,
            final String entityTypeName, final String entityAttributeName, EntityPermission entityPermission) {
        EntityType entityType = EntityTypeLogic.getInstance().getEntityTypeByName(eea, componentVendor, entityTypeName);
        EntityAttribute entityAttribute = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            entityAttribute = getEntityAttributeByName(eea, entityType, entityAttributeName, entityPermission);
        }

        return entityAttribute;
    }

    public EntityAttribute getEntityAttributeByName(final ExecutionErrorAccumulator eea, final ComponentVendor componentVendor,
            final String entityTypeName, final String entityAttributeName) {
        return getEntityAttributeByName(eea, componentVendor, entityTypeName, entityAttributeName, EntityPermission.READ_ONLY);
    }
    
    public EntityAttribute getEntityAttributeByNameForUpdate(final ExecutionErrorAccumulator eea, final ComponentVendor componentVendor,
            final String entityTypeName, final String entityAttributeName) {
        return getEntityAttributeByName(eea, componentVendor, entityTypeName, entityAttributeName, EntityPermission.READ_WRITE);
    }
    
    public EntityAttribute getEntityAttributeByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String entityAttributeName, EntityPermission entityPermission) {
        EntityType entityType = EntityTypeLogic.getInstance().getEntityTypeByName(eea, componentVendorName, entityTypeName);
        EntityAttribute entityAttribute = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            entityAttribute = getEntityAttributeByName(eea, entityType, entityAttributeName, entityPermission);
        }

        return entityAttribute;
    }
    
    public EntityAttribute getEntityAttributeByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String entityAttributeName) {
        return getEntityAttributeByName(eea, componentVendorName, entityTypeName, entityAttributeName, EntityPermission.READ_ONLY);
    }
    
    public EntityAttribute getEntityAttributeByNameForUpdate(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String entityAttributeName) {
        return getEntityAttributeByName(eea, componentVendorName, entityTypeName, entityAttributeName, EntityPermission.READ_WRITE);
    }
    
    public EntityAttribute getEntityAttributeByUlid(final ExecutionErrorAccumulator eea, final String ulid,
            final EntityPermission entityPermission) {
        EntityAttribute entityAttribute = null;
        
        EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, null, null, ulid,
                ComponentVendors.ECHOTHREE.name(), EntityTypes.EntityAttribute.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            
            entityAttribute = coreControl.getEntityAttributeByEntityInstance(entityInstance, entityPermission);
        }

        return entityAttribute;
    }
    
    public EntityAttribute getEntityAttributeByUlid(final ExecutionErrorAccumulator eea, final String ulid) {
        return getEntityAttributeByUlid(eea, ulid, EntityPermission.READ_ONLY);
    }
    
    public EntityAttribute getEntityAttributeByUlidForUpdate(final ExecutionErrorAccumulator eea, final String ulid) {
        return getEntityAttributeByUlid(eea, ulid, EntityPermission.READ_WRITE);
    }
    
    public EntityAttribute getEntityAttribute(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance,
            final EntityAttributeSpec spec, final EntityAttributeUlid ulid, final EntityPermission entityPermission,
            final EntityAttributeTypes... entityAttributeTypes) {
        EntityAttribute entityAttribute = null;
        String entityAttributeName = spec.getEntityAttributeName();
        String entityAttributeUlid = ulid.getEntityAttributeUlid();
        int parameterCount = (entityAttributeName == null ? 0 : 1) + (entityAttributeUlid == null ? 0 : 1);

        if (parameterCount == 1) {
            entityAttribute = entityAttributeName == null
                    ? getEntityAttributeByUlid(eea, entityAttributeUlid, entityPermission)
                    : getEntityAttributeByName(eea, entityInstance.getEntityType(), entityAttributeName, entityPermission);
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }
        
        // If there are no other errors, and the EntityAttribute was specified by ULID, then verify the EntityType...
        if((eea == null || !eea.hasExecutionErrors()) && entityAttributeUlid != null) {
            if(!entityInstance.getEntityType().equals(entityAttribute.getLastDetail().getEntityType())) {
                EntityTypeDetail expectedEntityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();
                EntityTypeDetail suppliedEntityTypeDetail = entityInstance.getEntityType().getLastDetail();

                handleExecutionError(MismatchedEntityTypeException.class, eea, ExecutionErrors.MismatchedEntityType.name(),
                        expectedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        expectedEntityTypeDetail.getEntityTypeName(),
                        suppliedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        suppliedEntityTypeDetail.getEntityTypeName());
            }
        }
        
        // If there are no other errors, and there are one of more entityAttributeTypes specified, then verify the EntityAttributeType...
        if((eea == null || !eea.hasExecutionErrors()) && entityAttributeTypes.length > 0) {
            String entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
            boolean found = false;
            
            for(EntityAttributeTypes entityAttributeType : entityAttributeTypes) {
                if(entityAttributeTypeName.equals(entityAttributeType.name())) {
                    found = true;
                    break;
                }
            }
            
            if(!found) {
                handleExecutionError(MismatchedEntityAttributeTypeException.class, eea, ExecutionErrors.MismatchedEntityAttributeType.name(),
                        entityAttributeTypeName);
                entityAttribute = null;
            }
        }
        
        return entityAttribute;
    }
    
    public EntityAttribute getEntityAttribute(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance,
            final EntityAttributeSpec spec, final EntityAttributeUlid ulid, final EntityAttributeTypes... entityAttributeTypes) {
        return getEntityAttribute(eea, entityInstance, spec, ulid, EntityPermission.READ_ONLY);
    }
    
    public EntityAttribute getEntityAttributeForUpdate(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance,
            final EntityAttributeSpec spec, final EntityAttributeUlid ulid, final EntityAttributeTypes... entityAttributeTypes) {
        return getEntityAttribute(eea, entityInstance, spec, ulid, EntityPermission.READ_WRITE);
    }
    
    private List<EntityInstanceResult> getEntityInstanceResultsByEntityAttributeTypeName(EntityAttribute entityAttribute) {
        List<EntityInstanceResult> entityInstanceResults = null;
        String entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        
        if(entityAttributeTypeName.equals(EntityAttributeTypes.BOOLEAN.name())) {
            entityInstanceResults = new EntityInstancesByBooleanEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.NAME.name())) {
            entityInstanceResults = new EntityInstancesByNameEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
            entityInstanceResults = new EntityInstancesByIntegerEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
            entityInstanceResults = new EntityInstancesByLongEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.STRING.name())) {
            entityInstanceResults = new EntityInstancesByStringEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.GEOPOINT.name())) {
            entityInstanceResults = new EntityInstancesByGeoPointEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
            entityInstanceResults = new EntityInstancesByBlobEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
            entityInstanceResults = new EntityInstancesByClobEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.DATE.name())) {
            entityInstanceResults = new EntityInstancesByDateEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.TIME.name())) {
            entityInstanceResults = new EntityInstancesByTimeEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
            entityInstanceResults = new EntityInstancesByListItemEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
            entityInstanceResults = new EntityInstancesByMultipleListItemEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.ENTITY.name())) {
            entityInstanceResults = new EntityInstancesByEntityEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.COLLECTION.name())) {
            entityInstanceResults = new EntityInstancesByCollectionEntityAttributeQuery().execute(entityAttribute);
        }

        return entityInstanceResults;
    }
    
    public void updateEntityAttributeFromValue(final Session session, EntityAttributeDetailValue entityAttributeDetailValue, BasePK updatedBy) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);

        if(entityAttributeDetailValue.getEntityAttributeNameHasBeenModified()) {
            IndexControl indexControl = (IndexControl)Session.getModelController(IndexControl.class);
            EntityAttribute entityAttribute = coreControl.getEntityAttributeByPK(entityAttributeDetailValue.getEntityAttributePK());
            
            if(indexControl.countIndexTypesByEntityType(entityAttribute.getLastDetail().getEntityType()) > 0) {
                QueueControl queueControl = (QueueControl)Session.getModelController(QueueControl.class);
                QueueTypePK queueTypePK = QueueTypeLogic.getInstance().getQueueTypeByName(null, QueueConstants.QueueType_INDEXING).getPrimaryKey();
                List<EntityInstanceResult> entityInstanceResults = getEntityInstanceResultsByEntityAttributeTypeName(entityAttribute);
                List<QueuedEntityValue> queuedEntities = new ArrayList<>(entityInstanceResults.size());

                for(EntityInstanceResult entityInstanceResult : entityInstanceResults) {
                    queuedEntities.add(new QueuedEntityValue(queueTypePK, entityInstanceResult.getEntityInstancePK(), session.START_TIME_LONG));
                }

                queueControl.createQueuedEntities(queuedEntities);
            }
        }
        
        coreControl.updateEntityAttributeFromValue(entityAttributeDetailValue, updatedBy);
    }
    
    public void deleteEntityAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final PartyPK deletedByPK) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);

        coreControl.deleteEntityAttribute(entityAttribute, deletedByPK);
    }

    public EntityListItem createEntityListItem(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            String entityListItemName, final Boolean isDefault, final Integer sortOrder, final BasePK createdBy,
            final Language language, final String description) {
        EntityListItem entityListItem = null;
        String entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();

        if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())
                || entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            
            if(entityListItemName == null) {
                EntityAttributeListItem entityAttributeListItem = coreControl.getEntityAttributeListItem(entityAttribute);
                Sequence entityListItemSequence = entityAttributeListItem == null ? null : entityAttributeListItem.getEntityListItemSequence();

                if(entityListItemSequence == null) {
                    entityListItemSequence = SequenceLogic.getInstance().getDefaultSequence(eea, SequenceConstants.SequenceType_ENTITY_LIST_ITEM);
                }

                if(eea != null && !hasExecutionErrors(eea)) {
                    entityListItemName = SequenceLogic.getInstance().getNextSequenceValue(eea, entityListItemSequence);
                } else {
                    handleExecutionError(MissingDefaultSequenceException.class, eea, ExecutionErrors.MissingDefaultSequence.name(),
                            SequenceConstants.SequenceType_ENTITY_LIST_ITEM);
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
                    EntityAttributeDetail entityAttributeDetail = entityAttribute.getLastDetail();
                    EntityTypeDetail entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();
                    
                    handleExecutionError(DuplicateEntityListItemNameException.class, eea, ExecutionErrors.DuplicateEntityListItemName.name(),
                            entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                            entityTypeDetail.getEntityTypeName(), entityAttributeDetail.getEntityAttributeName(),
                            entityListItemName);
                }
            }
        } else {
            EntityAttributeDetail entityAttributeDetail = entityAttribute.getLastDetail();
            EntityTypeDetail entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();
                    
            handleExecutionError(InvalidEntityAttributeTypeException.class, eea, ExecutionErrors.InvalidEntityAttributeType.name(), 
                            entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                            entityTypeDetail.getEntityTypeName(), entityAttributeDetail.getEntityAttributeName(),
                            entityAttributeTypeName);
        }
        
        return entityListItem;
    }
    
    public EntityListItem getEntityListItemByName(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final String entityListItemName, final EntityPermission entityPermission) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        EntityListItem entityListItem = coreControl.getEntityListItemByName(entityAttribute, entityListItemName, entityPermission);

        if(entityListItem == null) {
            EntityAttributeDetail entityAttributeDetail = entityAttribute.getLastDetail();
            EntityTypeDetail entityTypeDetail = entityAttributeDetail.getEntityType().getLastDetail();
            
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
        EntityAttribute entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByName(eea, componentVendorName,
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
        
    public EntityListItem getEntityListItemByUlid(final ExecutionErrorAccumulator eea, final String ulid, final EntityPermission entityPermission) {
        EntityListItem entityListItem = null;
        
        EntityInstance entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, null, null, ulid,
                ComponentVendors.ECHOTHREE.name(), EntityTypes.EntityListItem.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            
            entityListItem = coreControl.getEntityListItemByEntityInstance(entityInstance, entityPermission);
        }

        return entityListItem;
    }
    
    public EntityListItem getEntityListItemByUlid(final ExecutionErrorAccumulator eea, final String ulid) {
        return getEntityListItemByUlid(eea, ulid, EntityPermission.READ_ONLY);
    }
    
    public EntityListItem getEntityListItemByUlidForUpdate(final ExecutionErrorAccumulator eea, final String ulid) {
        return getEntityListItemByUlid(eea, ulid, EntityPermission.READ_WRITE);
    }
    
    public EntityListItem getEntityListItem(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityListItemSpec spec, final EntityListItemUlid ulid, final EntityPermission entityPermission) {
        EntityListItem entityListItem = null;
        String entityListItemName = spec.getEntityListItemName();
        String entityListItemUlid = ulid.getEntityListItemUlid();
        int parameterCount = (entityListItemName == null ? 0 : 1) + (entityListItemUlid == null ? 0 : 1);

        if (parameterCount == 1) {
            entityListItem = entityListItemName == null
                    ? getEntityListItemByUlid(eea, entityListItemUlid, entityPermission)
                    : getEntityListItemByName(eea, entityAttribute, entityListItemName, entityPermission);
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }
        
        if((eea == null || !eea.hasExecutionErrors()) && entityListItemUlid != null) {
            EntityAttribute foundEntityAttribute = entityListItem.getLastDetail().getEntityAttribute();

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
            final EntityListItemSpec spec, final EntityListItemUlid ulid) {
        return getEntityListItem(eea, entityAttribute, spec, ulid, EntityPermission.READ_ONLY);
    }
    
    public EntityListItem getEntityListItemForUpdate(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityListItemSpec spec, final EntityListItemUlid ulid) {
        return getEntityListItem(eea, entityAttribute, spec, ulid, EntityPermission.READ_WRITE);
    }
    
    public void updateEntityListItemFromValue(final Session session, EntityListItemDetailValue entityListItemDetailValue, BasePK updatedBy) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        
        if(entityListItemDetailValue.getEntityListItemNameHasBeenModified()) {
            IndexControl indexControl = (IndexControl)Session.getModelController(IndexControl.class);
            EntityListItem entityListItem = coreControl.getEntityListItemByPK(entityListItemDetailValue.getEntityListItemPK());
            EntityAttributeDetail entityAttributeDetail = entityListItem.getLastDetail().getEntityAttribute().getLastDetail();
            
            if(indexControl.countIndexTypesByEntityType(entityAttributeDetail.getEntityType()) > 0) {
                QueueControl queueControl = (QueueControl)Session.getModelController(QueueControl.class);
                QueueTypePK queueTypePK = QueueTypeLogic.getInstance().getQueueTypeByName(null, QueueConstants.QueueType_INDEXING).getPrimaryKey();
                String entityAttributeTypeName = entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName();

                if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
                    List<EntityListItemAttribute> entityListItemAttributes = coreControl.getEntityListItemAttributesByEntityListItem(entityListItem);
                    List<QueuedEntityValue> queuedEntities = new ArrayList<>(entityListItemAttributes.size());

                    entityListItemAttributes.forEach((entityListItemAttribute) -> {
                        queuedEntities.add(new QueuedEntityValue(queueTypePK, entityListItemAttribute.getEntityInstancePK(), session.START_TIME_LONG));
                    });

                    queueControl.createQueuedEntities(queuedEntities);
                } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
                    List<EntityMultipleListItemAttribute> entityMultipleListItemAttributes = coreControl.getEntityMultipleListItemAttributesByEntityListItem(entityListItem);
                    List<QueuedEntityValue> queuedEntities = new ArrayList<>(entityMultipleListItemAttributes.size());

                    entityMultipleListItemAttributes.forEach((entityMultipleListItemAttribute) -> {
                        queuedEntities.add(new QueuedEntityValue(queueTypePK, entityMultipleListItemAttribute.getEntityInstancePK(), session.START_TIME_LONG));
                    });

                    queueControl.createQueuedEntities(queuedEntities);
                }
            }
        }

        coreControl.updateEntityListItemFromValue(entityListItemDetailValue, updatedBy);
    }
    
    public void deleteEntityListItem(final ExecutionErrorAccumulator eea, EntityListItem entityListItem, BasePK deletedBy) {
        CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);

        coreControl.deleteEntityListItem(entityListItem, deletedBy);
    }

    private void checkEntityType(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance) {
        if(!entityInstance.getEntityType().equals(entityAttribute.getLastDetail().getEntityType())) {
            EntityTypeDetail expectedEntityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();
            EntityTypeDetail suppliedEntityTypeDetail = entityInstance.getEntityType().getLastDetail();

            handleExecutionError(MismatchedEntityTypeException.class, eea, ExecutionErrors.MismatchedEntityType.name(),
                    expectedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    expectedEntityTypeDetail.getEntityTypeName(),
                    suppliedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    suppliedEntityTypeDetail.getEntityTypeName());
        }
    }
    
    public EntityBooleanAttribute createEntityBooleanAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Boolean booleanAttribute, final BasePK createdBy) {
        EntityBooleanAttribute entityBooleanAttribute = null;
        
        checkEntityType(eea, entityAttribute, entityInstance);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            
            entityBooleanAttribute = coreControl.getEntityBooleanAttribute(entityAttribute, entityInstance);

            if(entityBooleanAttribute == null) {
                coreControl.createEntityBooleanAttribute(entityAttribute, entityInstance, booleanAttribute, createdBy);
            } else {
                handleExecutionError(DuplicateEntityBooleanAttributeException.class, eea, ExecutionErrors.DuplicateEntityBooleanAttribute.name(),
                        EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityBooleanAttribute;
    }
    
    public EntityIntegerAttribute createEntityIntegerAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Integer integerAttribute, final BasePK createdBy) {
        EntityIntegerAttribute entityIntegerAttribute = null;
        
        checkEntityType(eea, entityAttribute, entityInstance);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            EntityAttributeInteger entityAttributeInteger = coreControl.getEntityAttributeInteger(entityAttribute);
            
            if(entityAttributeInteger != null) {
                Integer upperRangeIntegerValue = entityAttributeInteger.getUpperRangeIntegerValue();
                Integer lowerRangeIntegerValue = entityAttributeInteger.getLowerRangeIntegerValue();
                
                if(upperRangeIntegerValue != null && integerAttribute > upperRangeIntegerValue){
                    handleExecutionError(UpperRangeExceededException.class, eea, ExecutionErrors.UpperRangeExceeded.name(),
                            upperRangeIntegerValue, integerAttribute);
                }
                
                if(lowerRangeIntegerValue != null && integerAttribute < lowerRangeIntegerValue) {
                    handleExecutionError(LowerRangeExceededException.class, eea, ExecutionErrors.LowerRangeExceeded.name(),
                            lowerRangeIntegerValue, integerAttribute);
                }
            }
            
            if(eea == null || !eea.hasExecutionErrors()) {
                entityIntegerAttribute = coreControl.getEntityIntegerAttribute(entityAttribute, entityInstance);

                if(entityIntegerAttribute == null) {
                    coreControl.createEntityIntegerAttribute(entityAttribute, entityInstance, integerAttribute, createdBy);
                } else {
                    handleExecutionError(DuplicateEntityIntegerAttributeException.class, eea, ExecutionErrors.DuplicateEntityIntegerAttribute.name(),
                            EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                            entityAttribute.getLastDetail().getEntityAttributeName());
                }
            }
        }

        return entityIntegerAttribute;
    }
    
    public EntityLongAttribute createEntityLongAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Long longAttribute, final BasePK createdBy) {
        EntityLongAttribute entityLongAttribute = null;
        
        checkEntityType(eea, entityAttribute, entityInstance);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            EntityAttributeLong entityAttributeLong = coreControl.getEntityAttributeLong(entityAttribute);
            
            if(entityAttributeLong != null) {
                Long upperRangeLongValue = entityAttributeLong.getUpperRangeLongValue();
                Long lowerRangeLongValue = entityAttributeLong.getLowerRangeLongValue();
                
                if(upperRangeLongValue != null && longAttribute > upperRangeLongValue){
                    handleExecutionError(UpperRangeExceededException.class, eea, ExecutionErrors.UpperRangeExceeded.name(),
                            upperRangeLongValue, longAttribute);
                }
                
                if(lowerRangeLongValue != null && longAttribute < lowerRangeLongValue) {
                    handleExecutionError(LowerRangeExceededException.class, eea, ExecutionErrors.LowerRangeExceeded.name(),
                            lowerRangeLongValue, longAttribute);
                }
            }
            
            if(eea == null || !eea.hasExecutionErrors()) {
                entityLongAttribute = coreControl.getEntityLongAttribute(entityAttribute, entityInstance);

                if(entityLongAttribute == null) {
                    coreControl.createEntityLongAttribute(entityAttribute, entityInstance, longAttribute, createdBy);
                } else {
                    handleExecutionError(DuplicateEntityLongAttributeException.class, eea, ExecutionErrors.DuplicateEntityLongAttribute.name(),
                            EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                            entityAttribute.getLastDetail().getEntityAttributeName());
                }
            }
        }

        return entityLongAttribute;
    }
    
    public EntityStringAttribute createEntityStringAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
        final EntityInstance entityInstance, final Language language, final String stringAttribute, final BasePK createdBy) {
        EntityStringAttribute entityStringAttribute = null;
        
        checkEntityType(eea, entityAttribute, entityInstance);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            EntityAttributeString entityAttributeString = coreControl.getEntityAttributeString(entityAttribute);
            String validationPattern = entityAttributeString == null ? null : entityAttributeString.getValidationPattern();

            if(validationPattern != null) {
                Pattern pattern = Pattern.compile(validationPattern);
                Matcher m = pattern.matcher(stringAttribute);

                if(!m.matches()) {
                    handleExecutionError(InvalidStringAttributeException.class, eea, ExecutionErrors.InvalidStringAttribute.name(),
                            stringAttribute);
                }
            }

            if(eea == null || !eea.hasExecutionErrors()) {
                entityStringAttribute = coreControl.getEntityStringAttribute(entityAttribute, entityInstance, language);

                if(entityStringAttribute == null) {
                    coreControl.createEntityStringAttribute(entityAttribute, entityInstance, language, stringAttribute, createdBy);
                } else {
                    handleExecutionError(DuplicateEntityStringAttributeException.class, eea, ExecutionErrors.DuplicateEntityStringAttribute.name(),
                            EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                            entityAttribute.getLastDetail().getEntityAttributeName(),
                            language.getLanguageIsoName());
                }
            }
        }

        return entityStringAttribute;
    }
    
    public EntityListItemAttribute createEntityListItemAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
        final EntityInstance entityInstance, final EntityListItem entityListItem, final BasePK createdBy) {
        EntityListItemAttribute entityListItemAttribute = null;
        
        checkEntityType(eea, entityAttribute, entityInstance);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            
            entityListItemAttribute = coreControl.getEntityListItemAttribute(entityAttribute, entityInstance);

            if(entityListItemAttribute == null) {
                entityListItemAttribute = coreControl.createEntityListItemAttribute(entityAttribute, entityInstance, entityListItem, createdBy);
            } else {
                handleExecutionError(DuplicateEntityListItemAttributeException.class, eea, ExecutionErrors.DuplicateEntityListItemAttribute.name(),
                        EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }
        
        return entityListItemAttribute;
    }
    
    public EntityMultipleListItemAttribute createEntityMultipleListItemAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
        final EntityInstance entityInstance, final EntityListItem entityListItem, final BasePK createdBy) {
        EntityMultipleListItemAttribute entityMultipleListItemAttribute = null;
        
        checkEntityType(eea, entityAttribute, entityInstance);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
            
            entityMultipleListItemAttribute = coreControl.getEntityMultipleListItemAttribute(entityAttribute, entityInstance, entityListItem);

            if(entityMultipleListItemAttribute == null) {
                entityMultipleListItemAttribute = coreControl.createEntityMultipleListItemAttribute(entityAttribute, entityInstance, entityListItem, createdBy);
            } else {
                handleExecutionError(DuplicateEntityMultipleListItemAttributeException.class, eea, ExecutionErrors.DuplicateEntityMultipleListItemAttribute.name(),
                    EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                    entityAttribute.getLastDetail().getEntityAttributeName(),
                    entityListItem.getLastDetail().getEntityListItemName());
            }
        }

        return entityMultipleListItemAttribute;
    }
    
}
