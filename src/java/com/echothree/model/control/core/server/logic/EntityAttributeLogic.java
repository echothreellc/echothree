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

import com.echothree.control.user.core.common.spec.EntityAttributeSpec;
import com.echothree.control.user.core.common.spec.EntityAttributeUniversalSpec;
import com.echothree.control.user.core.common.spec.EntityAttributeUuid;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityAttributeTypes;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.DuplicateEntityAttributeNameException;
import com.echothree.model.control.core.common.exception.DuplicateWorkflowUsageInEntityAttributeException;
import com.echothree.model.control.core.common.exception.EntityTypeIsNotExtensibleException;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.MismatchedEntityAttributeTypeException;
import com.echothree.model.control.core.common.exception.MismatchedEntityTypeException;
import com.echothree.model.control.core.common.exception.UnknownEntityAttributeNameException;
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
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.queue.common.QueueTypes;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.control.queue.server.logic.QueueTypeLogic;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.control.workflow.common.exception.UnknownWorkflowEntityTypeException;
import com.echothree.model.control.workflow.server.control.WorkflowControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAttribute;
import com.echothree.model.data.core.server.entity.EntityAttributeType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.value.EntityAttributeDetailValue;
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
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EntityAttributeLogic
        extends BaseLogic {

    @Inject
    protected IndexControl indexControl;

    @Inject
    protected QueueControl queueControl;

    @Inject
    protected SequenceControl sequenceControl;

    @Inject
    protected WorkflowControl workflowControl;

    @Inject
    protected EntityInstanceLogic entityInstanceLogic;

    @Inject
    protected EntityTypeLogic entityTypeLogic;

    @Inject
    protected QueueTypeLogic queueTypeLogic;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;

    protected EntityAttributeLogic() {
        super();
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
                var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.ENTITY_ATTRIBUTE.name());

                entityAttributeName = sequenceGeneratorLogic.getNextSequenceValue(sequence);
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
        var entityType = entityTypeLogic.getEntityTypeByName(eea, componentVendor, entityTypeName);
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
        var entityType = entityTypeLogic.getEntityTypeByName(eea, componentVendorName, entityTypeName);
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
        
        var entityInstance = entityInstanceLogic.getEntityInstance(eea, (String)null, uuid,
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

    public EntityAttribute getEntityAttributeByUniversalSpec(final ExecutionErrorAccumulator eea, final EntityAttributeUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        EntityAttribute entityAttribute = null;
        var componentVendorName = universalSpec.getComponentVendorName();
        var entityTypeName = universalSpec.getEntityTypeName();
        var entityAttributeName = universalSpec.getEntityAttributeName();
        var universalSpecCount = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        var parameterCount = (componentVendorName == null && entityTypeName == null && entityAttributeName == null ? 0 : 1)
                + universalSpecCount;

        switch(parameterCount) {
            case 1 -> {
                if(universalSpecCount == 1) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityAttribute.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
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
            final var entityAttribute = coreControl.getEntityAttributeByPK(entityAttributeDetailValue.getEntityAttributePK());
            
            if(indexControl.countIndexTypesByEntityType(entityAttribute.getLastDetail().getEntityType()) > 0) {
                final var queueTypePK = queueTypeLogic.getQueueTypeByName(null, QueueTypes.INDEXING.name()).getPrimaryKey();
                final var entityInstanceResults = getEntityInstanceResultsByEntityAttributeTypeName(entityAttribute);
                final var queuedEntities = new ArrayList<QueuedEntityValue>(entityInstanceResults.size());

                for(final var entityInstanceResult : entityInstanceResults) {
                    queuedEntities.add(new QueuedEntityValue(queueTypePK, entityInstanceResult.getEntityInstancePK(), session.getStartTime()));
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

}
