// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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
import com.echothree.control.user.core.common.spec.EntityAttributeSpec;
import com.echothree.control.user.core.common.spec.EntityAttributeUniversalSpec;
import com.echothree.control.user.core.common.spec.EntityAttributeUuid;
import com.echothree.control.user.core.common.spec.EntityInstanceAttributeSpec;
import com.echothree.control.user.core.common.spec.EntityListItemUniversalSpec;
import com.echothree.control.user.core.common.spec.EntityListItemUuid;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.DuplicateEntityAttributeNameException;
import com.echothree.model.control.core.common.exception.DuplicateEntityBooleanAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityBooleanDefaultException;
import com.echothree.model.control.core.common.exception.DuplicateEntityClobAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityDateAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityDateDefaultException;
import com.echothree.model.control.core.common.exception.DuplicateEntityGeoPointAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityGeoPointDefaultException;
import com.echothree.model.control.core.common.exception.DuplicateEntityIntegerAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityIntegerDefaultException;
import com.echothree.model.control.core.common.exception.DuplicateEntityListItemAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityListItemDefaultException;
import com.echothree.model.control.core.common.exception.DuplicateEntityListItemNameException;
import com.echothree.model.control.core.common.exception.DuplicateEntityLongAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityLongDefaultException;
import com.echothree.model.control.core.common.exception.DuplicateEntityMultipleListItemAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityMultipleListItemDefaultException;
import com.echothree.model.control.core.common.exception.DuplicateEntityNameAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityStringAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityStringDefaultException;
import com.echothree.model.control.core.common.exception.DuplicateEntityTimeAttributeException;
import com.echothree.model.control.core.common.exception.DuplicateEntityTimeDefaultException;
import com.echothree.model.control.core.common.exception.DuplicateWorkflowUsageInEntityAttributeException;
import com.echothree.model.control.core.common.exception.EntityTypeIsNotExtensibleException;
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
import com.echothree.model.control.core.common.exception.UnknownEntityBooleanDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityDateDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityGeoPointDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityIntegerDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityListItemDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityListItemNameException;
import com.echothree.model.control.core.common.exception.UnknownEntityLongDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityMultipleListItemDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityStringDefaultException;
import com.echothree.model.control.core.common.exception.UnknownEntityTimeDefaultException;
import com.echothree.model.control.core.common.exception.UpperRangeExceededException;
import com.echothree.model.control.core.server.database.EntityInstancePKResult;
import com.echothree.model.control.core.server.database.EntityInstancePKsByBlobEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancePKsByBooleanEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancePKsByClobEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancePKsByCollectionEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancePKsByDateEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancePKsByEntityEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancePKsByGeoPointEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancePKsByIntegerEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancePKsByListItemEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancePKsByLongEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancePKsByMultipleListItemEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancePKsByNameEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancePKsByStringEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancePKsByTimeEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesMissingBooleanEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesMissingDateEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesMissingGeoPointEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesMissingIntegerEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesMissingListItemEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesMissingLongEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesMissingMultipleListItemEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesMissingStringEntityAttributeQuery;
import com.echothree.model.control.core.server.database.EntityInstancesMissingTimeEntityAttributeQuery;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.queue.common.QueueTypes;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.control.queue.server.logic.QueueTypeLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.common.exception.MissingDefaultSequenceException;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowEntityTypeException;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityBooleanAttribute;
import com.echothree.model.data.core.server.entity.EntityBooleanDefault;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.model.data.core.server.entity.EntityDateAttribute;
import com.echothree.model.data.core.server.entity.EntityDateDefault;
import com.echothree.model.data.core.server.entity.EntityGeoPointAttribute;
import com.echothree.model.data.core.server.entity.EntityGeoPointDefault;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityIntegerAttribute;
import com.echothree.model.data.core.server.entity.EntityIntegerDefault;
import com.echothree.model.data.core.server.entity.EntityListItem;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityListItemDefault;
import com.echothree.model.data.core.server.entity.EntityLongAttribute;
import com.echothree.model.data.core.server.entity.EntityLongDefault;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemAttribute;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemDefault;
import com.echothree.model.data.core.server.entity.EntityNameAttribute;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.core.server.entity.EntityStringDefault;
import com.echothree.model.data.core.server.entity.EntityTimeAttribute;
import com.echothree.model.data.core.server.entity.EntityTimeDefault;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.MimeType;
import com.echothree.model.data.core.server.value.EntityAttributeDetailValue;
import com.echothree.model.data.core.server.value.EntityListItemDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.queue.server.value.QueuedEntityValue;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.uom.server.entity.UnitOfMeasureType;
import com.echothree.model.data.workflow.server.entity.Workflow;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

@ApplicationScoped
public class EntityAttributeLogic
        extends BaseLogic {

    protected EntityAttributeLogic() {
        super();
    }

    public static EntityAttributeLogic getInstance() {
        return CDI.current().select(EntityAttributeLogic.class).get();
    }
    
    public EntityAttributeType getEntityAttributeTypeByName(final ExecutionErrorAccumulator eea, final String entityAttributeTypeName) {
        var entityAttributeType = coreControl.getEntityAttributeTypeByName(entityAttributeTypeName);

        if(entityAttributeType == null) {
            handleExecutionError(UnknownEntityAttributeTypeNameException.class, eea, ExecutionErrors.UnknownEntityAttributeTypeName.name(), entityAttributeTypeName);
        }

        return entityAttributeType;
    }

    public EntityAttributeType getEntityAttributeTypeByUuid(final ExecutionErrorAccumulator eea, final String uuid, final EntityPermission entityPermission) {
        EntityAttributeType entityAttributeType = null;
        
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, uuid,
                ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityAttributeType.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            entityAttributeType = coreControl.getEntityAttributeTypeByEntityInstance(entityInstance, entityPermission);
        }

        return entityAttributeType;
    }
    
    public EntityAttributeType getEntityAttributeTypeByUuid(final ExecutionErrorAccumulator eea, final String uuid) {
        return getEntityAttributeTypeByUuid(eea, uuid, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeType getEntityAttributeTypeByUuidForUpdate(final ExecutionErrorAccumulator eea, final String uuid) {
        return getEntityAttributeTypeByUuid(eea, uuid, EntityPermission.READ_WRITE);
    }
    
    public EntityAttributeGroup getEntityAttributeGroupByName(final ExecutionErrorAccumulator eea, final String entityAttributeGroupName) {
        var entityAttributeGroup = coreControl.getEntityAttributeGroupByName(entityAttributeGroupName);

        if(entityAttributeGroup == null) {
            handleExecutionError(UnknownEntityAttributeGroupNameException.class, eea, ExecutionErrors.UnknownEntityAttributeGroupName.name(),
                    entityAttributeGroupName);
        }

        return entityAttributeGroup;
    }

    public EntityAttributeGroup getEntityAttributeGroupByUuid(final ExecutionErrorAccumulator eea, final String uuid, final EntityPermission entityPermission) {
        EntityAttributeGroup entityAttributeGroup = null;
        
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, uuid,
                ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityAttributeGroup.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            entityAttributeGroup = coreControl.getEntityAttributeGroupByEntityInstance(entityInstance, entityPermission);
        }

        return entityAttributeGroup;
    }
    
    public EntityAttributeGroup getEntityAttributeGroupByUuid(final ExecutionErrorAccumulator eea, final String uuid) {
        return getEntityAttributeGroupByUuid(eea, uuid, EntityPermission.READ_ONLY);
    }
    
    public EntityAttributeGroup getEntityAttributeGroupByUuidForUpdate(final ExecutionErrorAccumulator eea, final String uuid) {
        return getEntityAttributeGroupByUuid(eea, uuid, EntityPermission.READ_WRITE);
    }
    
    public EntityAttribute createEntityAttribute(final ExecutionErrorAccumulator eea, final EntityType entityType,
            String entityAttributeName, final EntityAttributeType entityAttributeType, final Boolean trackRevisions,
            final Boolean checkContentWebAddress, final String validationPattern, final Integer upperRangeIntegerValue,
            final Integer upperLimitIntegerValue, final Integer lowerLimitIntegerValue, final Integer lowerRangeIntegerValue,
            final Long upperRangeLongValue, final Long upperLimitLongValue, final Long lowerLimitLongValue,
            final Long lowerRangeLongValue, final Sequence entityListItemSequence, final UnitOfMeasureType unitOfMeasureType,
            final Workflow workflow, final Integer sortOrder, final BasePK createdBy, final Language language,
            final String description) {
        EntityAttribute entityAttribute = null;
        var entityTypeDetail = entityType.getLastDetail();

        if(entityTypeDetail.getIsExtensible()) {
            if(entityAttributeName == null) {
                var sequenceControl = Session.getModelController(SequenceControl.class);
                var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.ENTITY_ATTRIBUTE.name());

                entityAttributeName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(sequence);
            }

            entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName);

            if(entityAttribute == null) {
                var entityAttributeTypeEnum = EntityAttributeTypes.valueOf(entityAttributeType.getEntityAttributeTypeName());

                switch(entityAttributeTypeEnum) {
                    case WORKFLOW -> {
                        if(coreControl.countEntityAttributesByEntityTypeAndWorkflow(entityType, workflow) != 0) {
                            handleExecutionError(DuplicateWorkflowUsageInEntityAttributeException.class, eea, ExecutionErrors.DuplicateWorkflowUsageInEntityAttribute.name(),
                                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(),
                                    workflow.getLastDetail().getWorkflowName());
                        } else {
                            var workflowControl = Session.getModelController(WorkflowControl.class);

                            if(!workflowControl.workflowEntityTypeExists(workflow, entityType)) {
                                handleExecutionError(UnknownWorkflowEntityTypeException.class, eea, ExecutionErrors.UnknownWorkflowEntityType.name(),
                                        workflow.getLastDetail().getWorkflowName(), entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                                        entityTypeDetail.getEntityTypeName());
                            }

                        }
                    }
                    default -> {
                        // Nothing required for other EntityAttributeTypes
                    }
                }

                if(!hasExecutionErrors(eea)) {
                    entityAttribute = coreControl.createEntityAttribute(entityType, entityAttributeName, entityAttributeType,
                            trackRevisions, sortOrder, createdBy);

                    if(description != null) {
                        coreControl.createEntityAttributeDescription(entityAttribute, language, description, createdBy);
                    }

                    switch(entityAttributeTypeEnum) {
                        case BLOB ->
                                coreControl.createEntityAttributeBlob(entityAttribute, checkContentWebAddress, createdBy);
                        case STRING -> {
                            if(validationPattern != null) {
                                coreControl.createEntityAttributeString(entityAttribute, validationPattern, createdBy);
                            }
                        }
                        case INTEGER -> {
                            if(upperRangeIntegerValue != null || upperLimitIntegerValue != null || lowerLimitIntegerValue != null || lowerRangeIntegerValue != null) {
                                coreControl.createEntityAttributeInteger(entityAttribute, upperRangeIntegerValue, upperLimitIntegerValue,
                                        lowerLimitIntegerValue, lowerRangeIntegerValue, createdBy);
                            }
                            if(unitOfMeasureType != null) {
                                coreControl.createEntityAttributeNumeric(entityAttribute, unitOfMeasureType, createdBy);
                            }
                        }
                        case LONG -> {
                            if(upperRangeLongValue != null || upperLimitLongValue != null || lowerLimitLongValue != null || lowerRangeLongValue != null) {
                                coreControl.createEntityAttributeLong(entityAttribute, upperRangeLongValue, upperLimitLongValue,
                                        lowerLimitLongValue, lowerRangeLongValue, createdBy);
                            }
                            if(unitOfMeasureType != null) {
                                coreControl.createEntityAttributeNumeric(entityAttribute, unitOfMeasureType, createdBy);
                            }
                        }
                        case LISTITEM, MULTIPLELISTITEM -> {
                            if(entityListItemSequence != null) {
                                coreControl.createEntityAttributeListItem(entityAttribute, entityListItemSequence, createdBy);
                            }
                        }
                        case WORKFLOW -> {
                            if(workflow != null) {
                                coreControl.createEntityAttributeWorkflow(entityAttribute, workflow, createdBy);
                            }
                        }
                        default -> {
                            // Nothing required for other EntityAttributeTypes
                        }
                    }
                }
            } else {
                handleExecutionError(DuplicateEntityAttributeNameException.class, eea, ExecutionErrors.DuplicateEntityAttributeName.name(),
                        entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(), entityAttributeName);
            }
        } else {
            handleExecutionError(EntityTypeIsNotExtensibleException.class, eea, ExecutionErrors.EntityTypeIsNotExtensible.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName());
        }
        
        return entityAttribute;
    }
    
    public EntityAttribute getEntityAttributeByName(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String entityAttributeName, EntityPermission entityPermission) {
        var entityAttribute = coreControl.getEntityAttributeByName(entityType, entityAttributeName, entityPermission);

        if(entityAttribute == null) {
            var entityTypeDetail = entityType.getLastDetail();
            
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
        var entityType = EntityTypeLogic.getInstance().getEntityTypeByName(eea, componentVendor, entityTypeName);
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
        var entityType = EntityTypeLogic.getInstance().getEntityTypeByName(eea, componentVendorName, entityTypeName);
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
    
    public EntityAttribute getEntityAttributeByUuid(final ExecutionErrorAccumulator eea, final String uuid,
            final EntityPermission entityPermission) {
        EntityAttribute entityAttribute = null;
        
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, uuid,
                ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityAttribute.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            entityAttribute = coreControl.getEntityAttributeByEntityInstance(entityInstance, entityPermission);
        }

        return entityAttribute;
    }
    
    public EntityAttribute getEntityAttributeByUuid(final ExecutionErrorAccumulator eea, final String uuid) {
        return getEntityAttributeByUuid(eea, uuid, EntityPermission.READ_ONLY);
    }
    
    public EntityAttribute getEntityAttributeByUuidForUpdate(final ExecutionErrorAccumulator eea, final String uuid) {
        return getEntityAttributeByUuid(eea, uuid, EntityPermission.READ_WRITE);
    }

    // For when we need to determine the EntityType from what the user passes in:
    public EntityAttribute getEntityAttributeByUniversalSpec(final ExecutionErrorAccumulator eea, final EntityAttributeUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        EntityAttribute entityAttribute = null;
        var componentVendorName = universalSpec.getComponentVendorName();
        var entityTypeName = universalSpec.getEntityTypeName();
        var entityAttributeName = universalSpec.getEntityAttributeName();
        var universalSpecCount = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        var parameterCount = (componentVendorName == null && entityTypeName == null && entityAttributeName == null ? 0 : 1)
                + universalSpecCount;

        switch(parameterCount) {
            case 1 -> {
                if(universalSpecCount == 1) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityAttribute.name());

                    if(!eea.hasExecutionErrors()) {
                        entityAttribute = coreControl.getEntityAttributeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    entityAttribute = getEntityAttributeByName(eea, componentVendorName, entityTypeName, entityAttributeName, entityPermission);
                }
            }
            default -> {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }
        }

        return entityAttribute;
    }

    public EntityAttribute getEntityAttributeByUniversalSpec(final ExecutionErrorAccumulator eea, final EntityAttributeUniversalSpec universalSpec) {
        return getEntityAttributeByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public EntityAttribute getEntityAttributeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final EntityAttributeUniversalSpec universalSpec) {
        return getEntityAttributeByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    // For when we can get the EntityType from the EntityInstance:
    public EntityAttribute getEntityAttribute(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance,
            final EntityAttributeSpec spec, final EntityAttributeUuid uuid, final EntityPermission entityPermission,
            final EntityAttributeTypes... entityAttributeTypes) {
        EntityAttribute entityAttribute = null;
        var entityAttributeName = spec.getEntityAttributeName();
        var entityAttributeUuid = uuid.getEntityAttributeUuid();
        var parameterCount = (entityAttributeName == null ? 0 : 1) + (entityAttributeUuid == null ? 0 : 1);

        if (parameterCount == 1) {
            entityAttribute = entityAttributeName == null
                    ? getEntityAttributeByUuid(eea, entityAttributeUuid, entityPermission)
                    : getEntityAttributeByName(eea, entityInstance.getEntityType(), entityAttributeName, entityPermission);
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        // If there are no other errors, and the EntityAttribute was specified by ULID, then verify the EntityType...
        if((eea == null || !eea.hasExecutionErrors()) && entityAttributeUuid != null) {
            if(!entityInstance.getEntityType().equals(entityAttribute.getLastDetail().getEntityType())) {
                var expectedEntityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();
                var suppliedEntityTypeDetail = entityInstance.getEntityType().getLastDetail();

                handleExecutionError(MismatchedEntityTypeException.class, eea, ExecutionErrors.MismatchedEntityType.name(),
                        expectedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        expectedEntityTypeDetail.getEntityTypeName(),
                        suppliedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        suppliedEntityTypeDetail.getEntityTypeName());
            }
        }

        // If there are no other errors, and there are one of more entityAttributeTypes specified, then verify the EntityAttributeType...
        if((eea == null || !eea.hasExecutionErrors()) && entityAttributeTypes.length > 0) {
            var entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
            var found = false;

            for(var entityAttributeType : entityAttributeTypes) {
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
            final EntityAttributeSpec spec, final EntityAttributeUuid uuid, final EntityAttributeTypes... entityAttributeTypes) {
        return getEntityAttribute(eea, entityInstance, spec, uuid, EntityPermission.READ_ONLY, entityAttributeTypes);
    }

    public EntityAttribute getEntityAttributeForUpdate(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance,
            final EntityAttributeSpec spec, final EntityAttributeUuid uuid, final EntityAttributeTypes... entityAttributeTypes) {
        return getEntityAttribute(eea, entityInstance, spec, uuid, EntityPermission.READ_WRITE, entityAttributeTypes);
    }

    public EntityInstance getEntityInstanceAttribute(final ExecutionErrorAccumulator eea,
            final EntityInstanceAttributeSpec entityInstanceAttributeSpec) {
        var entityRef = entityInstanceAttributeSpec.getEntityRefAttribute();
        var uuid = entityInstanceAttributeSpec.getUuidAttribute();

        return EntityInstanceLogic.getInstance().getEntityInstance(eea, entityRef, uuid, null);
    }

    private List<EntityInstancePKResult> getEntityInstanceResultsByEntityAttributeTypeName(EntityAttribute entityAttribute) {
        List<EntityInstancePKResult> entityInstanceResults = null;
        var entityAttributeTypeName = entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName();
        
        if(entityAttributeTypeName.equals(EntityAttributeTypes.BOOLEAN.name())) {
            entityInstanceResults = new EntityInstancePKsByBooleanEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.NAME.name())) {
            entityInstanceResults = new EntityInstancePKsByNameEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.INTEGER.name())) {
            entityInstanceResults = new EntityInstancePKsByIntegerEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LONG.name())) {
            entityInstanceResults = new EntityInstancePKsByLongEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.STRING.name())) {
            entityInstanceResults = new EntityInstancePKsByStringEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.GEOPOINT.name())) {
            entityInstanceResults = new EntityInstancePKsByGeoPointEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.BLOB.name())) {
            entityInstanceResults = new EntityInstancePKsByBlobEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.CLOB.name())) {
            entityInstanceResults = new EntityInstancePKsByClobEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.DATE.name())) {
            entityInstanceResults = new EntityInstancePKsByDateEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.TIME.name())) {
            entityInstanceResults = new EntityInstancePKsByTimeEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
            entityInstanceResults = new EntityInstancePKsByListItemEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
            entityInstanceResults = new EntityInstancePKsByMultipleListItemEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.ENTITY.name())) {
            entityInstanceResults = new EntityInstancePKsByEntityEntityAttributeQuery().execute(entityAttribute);
        } else if(entityAttributeTypeName.equals(EntityAttributeTypes.COLLECTION.name())) {
            entityInstanceResults = new EntityInstancePKsByCollectionEntityAttributeQuery().execute(entityAttribute);
        }

        return entityInstanceResults;
    }
    
    public void updateEntityAttributeFromValue(final Session session, final EntityAttributeDetailValue entityAttributeDetailValue,
            final BasePK updatedBy) {
        if(entityAttributeDetailValue.getEntityAttributeNameHasBeenModified()) {
            final var indexControl = Session.getModelController(IndexControl.class);
            final var entityAttribute = coreControl.getEntityAttributeByPK(entityAttributeDetailValue.getEntityAttributePK());
            
            if(indexControl.countIndexTypesByEntityType(entityAttribute.getLastDetail().getEntityType()) > 0) {
                final var queueControl = Session.getModelController(QueueControl.class);
                final var queueTypePK = QueueTypeLogic.getInstance().getQueueTypeByName(null, QueueTypes.INDEXING.name()).getPrimaryKey();
                final var entityInstanceResults = getEntityInstanceResultsByEntityAttributeTypeName(entityAttribute);
                final var queuedEntities = new ArrayList<QueuedEntityValue>(entityInstanceResults.size());

                for(final var entityInstanceResult : entityInstanceResults) {
                    queuedEntities.add(new QueuedEntityValue(queueTypePK, entityInstanceResult.getEntityInstancePK(), session.START_TIME_LONG));
                }

                queueControl.createQueuedEntities(queuedEntities);
            }
        }
        
        coreControl.updateEntityAttributeFromValue(entityAttributeDetailValue, updatedBy);
    }
    
    public void deleteEntityAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final PartyPK deletedByPK) {
        coreControl.deleteEntityAttribute(entityAttribute, deletedByPK);
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
                    entityListItemSequence = SequenceGeneratorLogic.getInstance().getDefaultSequence(eea, SequenceTypes.ENTITY_LIST_ITEM.name());
                }

                if(eea != null && !hasExecutionErrors(eea)) {
                    entityListItemName = SequenceGeneratorLogic.getInstance().getNextSequenceValue(eea, entityListItemSequence);
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
        var entityAttribute = EntityAttributeLogic.getInstance().getEntityAttributeByName(eea, componentVendorName,
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
        
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, uuid,
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
        var universalSpecCount = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        var parameterCount = (componentVendorName == null && entityTypeName == null && entityAttributeName == null && entityListItemName == null ? 0 : 1)
                + universalSpecCount;

        switch(parameterCount) {
            case 1 -> {
                if(universalSpecCount == 1) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityListItem.name());

                    if(!eea.hasExecutionErrors()) {
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
            var indexControl = Session.getModelController(IndexControl.class);
            var entityListItem = coreControl.getEntityListItemByPK(entityListItemDetailValue.getEntityListItemPK());
            var entityAttributeDetail = entityListItem.getLastDetail().getEntityAttribute().getLastDetail();
            
            if(indexControl.countIndexTypesByEntityType(entityAttributeDetail.getEntityType()) > 0) {
                var queueControl = Session.getModelController(QueueControl.class);
                var queueTypePK = QueueTypeLogic.getInstance().getQueueTypeByName(null, QueueTypes.INDEXING.name()).getPrimaryKey();
                var entityAttributeTypeName = entityAttributeDetail.getEntityAttributeType().getEntityAttributeTypeName();

                if(entityAttributeTypeName.equals(EntityAttributeTypes.LISTITEM.name())) {
                    var entityListItemAttributes = coreControl.getEntityListItemAttributesByEntityListItem(entityListItem);
                    List<QueuedEntityValue> queuedEntities = new ArrayList<>(entityListItemAttributes.size());

                    entityListItemAttributes.forEach((entityListItemAttribute) -> {
                        queuedEntities.add(new QueuedEntityValue(queueTypePK, entityListItemAttribute.getEntityInstancePK(), session.START_TIME_LONG));
                    });

                    queueControl.createQueuedEntities(queuedEntities);
                } else if(entityAttributeTypeName.equals(EntityAttributeTypes.MULTIPLELISTITEM.name())) {
                    var entityMultipleListItemAttributes = coreControl.getEntityMultipleListItemAttributesByEntityListItem(entityListItem);
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
        coreControl.deleteEntityListItem(entityListItem, deletedBy);
    }

    private void checkEntityType(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance) {
        if(!entityInstance.getEntityType().equals(entityAttribute.getLastDetail().getEntityType())) {
            var expectedEntityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();
            var suppliedEntityTypeDetail = entityInstance.getEntityType().getLastDetail();

            handleExecutionError(MismatchedEntityTypeException.class, eea, ExecutionErrors.MismatchedEntityType.name(),
                    expectedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    expectedEntityTypeDetail.getEntityTypeName(),
                    suppliedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    suppliedEntityTypeDetail.getEntityTypeName());
        }
    }

    private void checkEntityType(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityAttributeTypes entityAttributeType) {
        var entityAttributeTypeName = entityAttributeType.name();

        if(!entityAttribute.getLastDetail().getEntityAttributeType().getEntityAttributeTypeName().equals(entityAttributeTypeName)) {
            var expectedEntityTypeDetail = entityAttribute.getLastDetail().getEntityType().getLastDetail();

            handleExecutionError(MismatchedEntityAttributeTypeException.class, eea, ExecutionErrors.MismatchedEntityAttributeType.name(),
                    expectedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    expectedEntityTypeDetail.getEntityTypeName(),
                    entityAttributeTypeName);
        }
    }

    public EntityBooleanDefault createEntityBooleanDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Boolean booleanAttribute, final boolean addMissingAttributes, final BasePK createdBy) {
        EntityBooleanDefault entityBooleanDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.BOOLEAN);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityBooleanDefault = coreControl.getEntityBooleanDefault(entityAttribute);

            if(entityBooleanDefault == null) {
                coreControl.createEntityBooleanDefault(entityAttribute, booleanAttribute, createdBy);

                if(addMissingAttributes) {
                    new EntityInstancesMissingBooleanEntityAttributeQuery().execute(entityAttribute).forEach(entityInstanceResult ->
                            coreControl.createEntityBooleanAttribute(entityAttribute.getPrimaryKey(),
                                    entityInstanceResult.getEntityInstance(), booleanAttribute, createdBy));
                }
            } else {
                handleExecutionError(DuplicateEntityBooleanDefaultException.class, eea, ExecutionErrors.DuplicateEntityBooleanDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityBooleanDefault;
    }

    public void deleteEntityBooleanDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.BOOLEAN);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityBooleanDefault = coreControl.getEntityBooleanDefaultForUpdate(entityAttribute);

            if(entityBooleanDefault == null) {
                handleExecutionError(UnknownEntityBooleanDefaultException.class, eea, ExecutionErrors.UnknownEntityBooleanDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            } else {
                coreControl.deleteEntityBooleanDefault(entityBooleanDefault, deletedBy);
            }
        }
    }

    public EntityBooleanAttribute createEntityBooleanAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Boolean booleanAttribute, final BasePK createdBy) {
        EntityBooleanAttribute entityBooleanAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
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

    public EntityIntegerDefault createEntityIntegerDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Integer integerAttribute, final boolean addMissingAttributes, final BasePK createdBy) {
        EntityIntegerDefault entityIntegerDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.INTEGER);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityAttributeInteger = coreControl.getEntityAttributeInteger(entityAttribute);

            if(entityAttributeInteger != null) {
                var upperRangeIntegerValue = entityAttributeInteger.getUpperRangeIntegerValue();
                var lowerRangeIntegerValue = entityAttributeInteger.getLowerRangeIntegerValue();

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
                entityIntegerDefault = coreControl.getEntityIntegerDefault(entityAttribute);

                if(entityIntegerDefault == null) {
                    coreControl.createEntityIntegerDefault(entityAttribute, integerAttribute, createdBy);

                    if(addMissingAttributes) {
                        new EntityInstancesMissingIntegerEntityAttributeQuery().execute(entityAttribute).forEach(entityInstanceResult ->
                                coreControl.createEntityIntegerAttribute(entityAttribute.getPrimaryKey(),
                                        entityInstanceResult.getEntityInstance(), integerAttribute, createdBy));
                    }
                } else {
                    handleExecutionError(DuplicateEntityIntegerDefaultException.class, eea, ExecutionErrors.DuplicateEntityIntegerDefault.name(),
                            entityAttribute.getLastDetail().getEntityAttributeName());
                }
            }
        }

        return entityIntegerDefault;
    }

    public void deleteEntityIntegerDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.INTEGER);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityIntegerDefault = coreControl.getEntityIntegerDefaultForUpdate(entityAttribute);

            if(entityIntegerDefault == null) {
                handleExecutionError(UnknownEntityIntegerDefaultException.class, eea, ExecutionErrors.UnknownEntityIntegerDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            } else {
                coreControl.deleteEntityIntegerDefault(entityIntegerDefault, deletedBy);
            }
        }
    }

    public EntityIntegerAttribute createEntityIntegerAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Integer integerAttribute, final BasePK createdBy) {
        EntityIntegerAttribute entityIntegerAttribute = null;
        
        checkEntityType(eea, entityAttribute, entityInstance);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            var entityAttributeInteger = coreControl.getEntityAttributeInteger(entityAttribute);
            
            if(entityAttributeInteger != null) {
                var upperRangeIntegerValue = entityAttributeInteger.getUpperRangeIntegerValue();
                var lowerRangeIntegerValue = entityAttributeInteger.getLowerRangeIntegerValue();
                
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

    public EntityLongDefault createEntityLongDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Long longAttribute, final boolean addMissingAttributes, BasePK createdBy) {
        EntityLongDefault entityLongDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.LONG);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityAttributeLong = coreControl.getEntityAttributeLong(entityAttribute);

            if(entityAttributeLong != null) {
                var upperRangeLongValue = entityAttributeLong.getUpperRangeLongValue();
                var lowerRangeLongValue = entityAttributeLong.getLowerRangeLongValue();

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
                entityLongDefault = coreControl.getEntityLongDefault(entityAttribute);

                if(entityLongDefault == null) {
                    coreControl.createEntityLongDefault(entityAttribute, longAttribute, createdBy);

                    if(addMissingAttributes) {
                        new EntityInstancesMissingLongEntityAttributeQuery().execute(entityAttribute).forEach(entityInstanceResult ->
                                coreControl.createEntityLongAttribute(entityAttribute.getPrimaryKey(),
                                        entityInstanceResult.getEntityInstance(), longAttribute, createdBy));
                    }
                } else {
                    handleExecutionError(DuplicateEntityLongDefaultException.class, eea, ExecutionErrors.DuplicateEntityLongDefault.name(),
                            entityAttribute.getLastDetail().getEntityAttributeName());
                }
            }
        }

        return entityLongDefault;
    }

    public void deleteEntityLongDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.LONG);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityLongDefault = coreControl.getEntityLongDefaultForUpdate(entityAttribute);

            if(entityLongDefault == null) {
                handleExecutionError(UnknownEntityLongDefaultException.class, eea, ExecutionErrors.UnknownEntityLongDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            } else {
                coreControl.deleteEntityLongDefault(entityLongDefault, deletedBy);
            }
        }
    }

    public EntityLongAttribute createEntityLongAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Long longAttribute, final BasePK createdBy) {
        EntityLongAttribute entityLongAttribute = null;
        
        checkEntityType(eea, entityAttribute, entityInstance);
        
        if(eea == null || !eea.hasExecutionErrors()) {
            var entityAttributeLong = coreControl.getEntityAttributeLong(entityAttribute);
            
            if(entityAttributeLong != null) {
                var upperRangeLongValue = entityAttributeLong.getUpperRangeLongValue();
                var lowerRangeLongValue = entityAttributeLong.getLowerRangeLongValue();
                
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

    public EntityStringDefault createEntityStringDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Language language, final String stringAttribute, final boolean addMissingAttributes, final BasePK createdBy) {
        EntityStringDefault entityStringDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.STRING);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityStringDefault = coreControl.getEntityStringDefault(entityAttribute, language);

            if(entityStringDefault == null) {
                coreControl.createEntityStringDefault(entityAttribute, language, stringAttribute, createdBy);

                if(addMissingAttributes) {
                    new EntityInstancesMissingStringEntityAttributeQuery().execute(entityAttribute, language).forEach(entityInstanceResult ->
                            coreControl.createEntityStringAttribute(entityAttribute.getPrimaryKey(),
                                    entityInstanceResult.getEntityInstance(), language, stringAttribute, createdBy));
                }
            } else {
                handleExecutionError(DuplicateEntityStringDefaultException.class, eea, ExecutionErrors.DuplicateEntityStringDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityStringDefault;
    }

    public void deleteEntityStringDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Language language, final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.STRING);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityStringDefault = coreControl.getEntityStringDefaultForUpdate(entityAttribute, language);

            if(entityStringDefault == null) {
                handleExecutionError(UnknownEntityStringDefaultException.class, eea, ExecutionErrors.UnknownEntityStringDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName(),
                        language.getLanguageIsoName());
            } else {
                coreControl.deleteEntityStringDefault(entityStringDefault, deletedBy);
            }
        }
    }

    public EntityStringAttribute createEntityStringAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Language language, final String stringAttribute, final BasePK createdBy) {
        EntityStringAttribute entityStringAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityAttributeString = coreControl.getEntityAttributeString(entityAttribute);
            var validationPattern = entityAttributeString == null ? null : entityAttributeString.getValidationPattern();

            if(validationPattern != null) {
                var pattern = Pattern.compile(validationPattern);
                var m = pattern.matcher(stringAttribute);

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

    public EntityClobAttribute createEntityClobAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Language language, final String clobAttribute, final MimeType mimeType,
            final BasePK createdBy) {
        EntityClobAttribute entityClobAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityClobAttribute = coreControl.getEntityClobAttribute(entityAttribute, entityInstance, language);

            if(entityClobAttribute == null) {
                coreControl.createEntityClobAttribute(entityAttribute, entityInstance, language, clobAttribute, mimeType, createdBy);
            } else {
                handleExecutionError(DuplicateEntityClobAttributeException.class, eea, ExecutionErrors.DuplicateEntityClobAttribute.name(),
                        EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                        entityAttribute.getLastDetail().getEntityAttributeName(),
                        language.getLanguageIsoName());
            }
        }

        return entityClobAttribute;
    }

    public EntityNameAttribute createEntityNameAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final String nameAttribute, final EntityInstance entityInstance, final BasePK createdBy) {
        EntityNameAttribute entityNameAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityNameAttribute = coreControl.getEntityNameAttribute(entityAttribute, entityInstance);

            if(entityNameAttribute == null) {
                coreControl.createEntityNameAttribute(entityAttribute, nameAttribute, entityInstance, createdBy);
            } else {
                handleExecutionError(DuplicateEntityNameAttributeException.class, eea, ExecutionErrors.DuplicateEntityNameAttribute.name(),
                        EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityNameAttribute;
    }

    public EntityDateDefault createEntityDateDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Integer dateAttribute, final boolean addMissingAttributes, BasePK createdBy) {
        EntityDateDefault entityDateDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.DATE);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityDateDefault = coreControl.getEntityDateDefault(entityAttribute);

            if(entityDateDefault == null) {
                coreControl.createEntityDateDefault(entityAttribute, dateAttribute, createdBy);

                if(addMissingAttributes) {
                    new EntityInstancesMissingDateEntityAttributeQuery().execute(entityAttribute).forEach(entityInstanceResult ->
                            coreControl.createEntityDateAttribute(entityAttribute.getPrimaryKey(),
                                    entityInstanceResult.getEntityInstance(), dateAttribute, createdBy));
                }
            } else {
                handleExecutionError(DuplicateEntityDateDefaultException.class, eea, ExecutionErrors.DuplicateEntityDateDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityDateDefault;
    }

    public void deleteEntityDateDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.DATE);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityDateDefault = coreControl.getEntityDateDefaultForUpdate(entityAttribute);

            if(entityDateDefault == null) {
                handleExecutionError(UnknownEntityDateDefaultException.class, eea, ExecutionErrors.UnknownEntityDateDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            } else {
                coreControl.deleteEntityDateDefault(entityDateDefault, deletedBy);
            }
        }
    }

    public EntityDateAttribute createEntityDateAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Integer dateAttribute, final BasePK createdBy) {
        EntityDateAttribute entityDateAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityDateAttribute = coreControl.getEntityDateAttribute(entityAttribute, entityInstance);

            if(entityDateAttribute == null) {
                coreControl.createEntityDateAttribute(entityAttribute, entityInstance, dateAttribute, createdBy);
            } else {
                handleExecutionError(DuplicateEntityDateAttributeException.class, eea, ExecutionErrors.DuplicateEntityDateAttribute.name(),
                        EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityDateAttribute;
    }

    public EntityTimeDefault createEntityTimeDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Long timeAttribute, final boolean addMissingAttributes, BasePK createdBy) {
        EntityTimeDefault entityTimeDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.TIME);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityTimeDefault = coreControl.getEntityTimeDefault(entityAttribute);

            if(entityTimeDefault == null) {
                coreControl.createEntityTimeDefault(entityAttribute, timeAttribute, createdBy);

                if(addMissingAttributes) {
                    new EntityInstancesMissingTimeEntityAttributeQuery().execute(entityAttribute).forEach(entityInstanceResult ->
                            coreControl.createEntityTimeAttribute(entityAttribute.getPrimaryKey(),
                                    entityInstanceResult.getEntityInstance(), timeAttribute, createdBy));
                }
            } else {
                handleExecutionError(DuplicateEntityTimeDefaultException.class, eea, ExecutionErrors.DuplicateEntityTimeDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityTimeDefault;
    }

    public void deleteEntityTimeDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.TIME);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityTimeDefault = coreControl.getEntityTimeDefaultForUpdate(entityAttribute);

            if(entityTimeDefault == null) {
                handleExecutionError(UnknownEntityTimeDefaultException.class, eea, ExecutionErrors.UnknownEntityTimeDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            } else {
                coreControl.deleteEntityTimeDefault(entityTimeDefault, deletedBy);
            }
        }
    }

    public EntityTimeAttribute createEntityTimeAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Long timeAttribute, final BasePK createdBy) {
        EntityTimeAttribute entityTimeAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityTimeAttribute = coreControl.getEntityTimeAttribute(entityAttribute, entityInstance);

            if(entityTimeAttribute == null) {
                coreControl.createEntityTimeAttribute(entityAttribute, entityInstance, timeAttribute, createdBy);
            } else {
                handleExecutionError(DuplicateEntityTimeAttributeException.class, eea, ExecutionErrors.DuplicateEntityTimeAttribute.name(),
                        EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityTimeAttribute;
    }

    public EntityGeoPointDefault createEntityGeoPointDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final Integer latitude, final Integer longitude, final Long elevation, final Long altitude, final boolean addMissingAttributes, BasePK createdBy) {
        EntityGeoPointDefault entityGeoPointDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.GEOPOINT);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityGeoPointDefault = coreControl.getEntityGeoPointDefault(entityAttribute);

            if(entityGeoPointDefault == null) {
                coreControl.createEntityGeoPointDefault(entityAttribute, latitude, longitude, elevation, altitude, createdBy);

                if(addMissingAttributes) {
                    new EntityInstancesMissingGeoPointEntityAttributeQuery().execute(entityAttribute).forEach(entityInstanceResult ->
                            coreControl.createEntityGeoPointAttribute(entityAttribute.getPrimaryKey(),
                                    entityInstanceResult.getEntityInstance(), latitude, longitude, elevation, altitude, createdBy));
                }
            } else {
                handleExecutionError(DuplicateEntityGeoPointDefaultException.class, eea, ExecutionErrors.DuplicateEntityGeoPointDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityGeoPointDefault;
    }

    public void deleteEntityGeoPointDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.GEOPOINT);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityGeoPointDefault = coreControl.getEntityGeoPointDefaultForUpdate(entityAttribute);

            if(entityGeoPointDefault == null) {
                handleExecutionError(UnknownEntityGeoPointDefaultException.class, eea, ExecutionErrors.UnknownEntityGeoPointDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            } else {
                coreControl.deleteEntityGeoPointDefault(entityGeoPointDefault, deletedBy);
            }
        }
    }

    public EntityGeoPointAttribute createEntityGeoPointAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityInstance entityInstance, final Integer latitude, final Integer longitude, final Long elevation, final Long altitude, final BasePK createdBy) {
        EntityGeoPointAttribute entityGeoPointAttribute = null;

        checkEntityType(eea, entityAttribute, entityInstance);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityGeoPointAttribute = coreControl.getEntityGeoPointAttribute(entityAttribute, entityInstance);

            if(entityGeoPointAttribute == null) {
                coreControl.createEntityGeoPointAttribute(entityAttribute, entityInstance, latitude, longitude, elevation, altitude, createdBy);
            } else {
                handleExecutionError(DuplicateEntityGeoPointAttributeException.class, eea, ExecutionErrors.DuplicateEntityGeoPointAttribute.name(),
                        EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityGeoPointAttribute;
    }

    public EntityListItemDefault createEntityListItemDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityListItem entityListItem, final boolean addMissingAttributes, final BasePK createdBy) {
        EntityListItemDefault entityListItemDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.LISTITEM);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityListItemDefault = coreControl.getEntityListItemDefault(entityAttribute);

            if(entityListItemDefault == null) {
                coreControl.createEntityListItemDefault(entityAttribute, entityListItem, createdBy);

                if(addMissingAttributes) {
                    new EntityInstancesMissingListItemEntityAttributeQuery().execute(entityAttribute).forEach(entityInstanceResult ->
                            coreControl.createEntityListItemAttribute(entityAttribute.getPrimaryKey(),
                                    entityInstanceResult.getEntityInstance(), entityListItem, createdBy));
                }
            } else {
                handleExecutionError(DuplicateEntityListItemDefaultException.class, eea, ExecutionErrors.DuplicateEntityListItemDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            }
        }

        return entityListItemDefault;
    }

    public void deleteEntityListItemDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.LISTITEM);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityListItemDefault = coreControl.getEntityListItemDefaultForUpdate(entityAttribute);

            if(entityListItemDefault == null) {
                handleExecutionError(UnknownEntityListItemDefaultException.class, eea, ExecutionErrors.UnknownEntityListItemDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName());
            } else {
                coreControl.deleteEntityListItemDefault(entityListItemDefault, deletedBy);
            }
        }
    }

    public EntityListItemAttribute createEntityListItemAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
        final EntityInstance entityInstance, final EntityListItem entityListItem, final BasePK createdBy) {
        EntityListItemAttribute entityListItemAttribute = null;
        
        checkEntityType(eea, entityAttribute, entityInstance);
        
        if(eea == null || !eea.hasExecutionErrors()) {
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

    public EntityMultipleListItemDefault createEntityMultipleListItemDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityListItem entityListItem, final boolean addMissingAttributes, final BasePK createdBy) {
        EntityMultipleListItemDefault entityListItemDefault = null;

        checkEntityType(eea, entityAttribute, EntityAttributeTypes.MULTIPLELISTITEM);

        if(eea == null || !eea.hasExecutionErrors()) {
            entityListItemDefault = coreControl.getEntityMultipleListItemDefault(entityAttribute, entityListItem);

            if(entityListItemDefault == null) {
                coreControl.createEntityMultipleListItemDefault(entityAttribute, entityListItem, createdBy);

                if(addMissingAttributes) {
                    new EntityInstancesMissingMultipleListItemEntityAttributeQuery().execute(entityAttribute, entityListItem).forEach(entityInstanceResult ->
                            coreControl.createEntityMultipleListItemAttribute(entityAttribute.getPrimaryKey(),
                                    entityInstanceResult.getEntityInstance(), entityListItem, createdBy));
                }
            } else {
                handleExecutionError(DuplicateEntityMultipleListItemDefaultException.class, eea, ExecutionErrors.DuplicateEntityMultipleListItemDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName(), entityListItem.getLastDetail().getEntityListItemName());
            }
        }

        return entityListItemDefault;
    }

    public void deleteEntityMultipleListItemDefault(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
            final EntityListItem entityListItem, final BasePK deletedBy) {
        checkEntityType(eea, entityAttribute, EntityAttributeTypes.MULTIPLELISTITEM);

        if(eea == null || !eea.hasExecutionErrors()) {
            var entityListItemDefault = coreControl.getEntityMultipleListItemDefaultForUpdate(entityAttribute, entityListItem);

            if(entityListItemDefault == null) {
                handleExecutionError(UnknownEntityMultipleListItemDefaultException.class, eea, ExecutionErrors.UnknownEntityMultipleListItemDefault.name(),
                        entityAttribute.getLastDetail().getEntityAttributeName(), entityListItem.getLastDetail().getEntityListItemName());
            } else {
                coreControl.deleteEntityMultipleListItemDefault(entityListItemDefault, deletedBy);
            }
        }
    }

    public EntityMultipleListItemAttribute createEntityMultipleListItemAttribute(final ExecutionErrorAccumulator eea, final EntityAttribute entityAttribute,
        final EntityInstance entityInstance, final EntityListItem entityListItem, final BasePK createdBy) {
        EntityMultipleListItemAttribute entityMultipleListItemAttribute = null;
        
        checkEntityType(eea, entityAttribute, entityInstance);
        
        if(eea == null || !eea.hasExecutionErrors()) {
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
