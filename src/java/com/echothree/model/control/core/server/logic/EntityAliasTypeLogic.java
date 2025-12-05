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

import com.echothree.control.user.core.common.spec.EntityAliasTypeSpec;
import com.echothree.control.user.core.common.spec.EntityAliasTypeUniversalSpec;
import com.echothree.control.user.core.common.spec.EntityAliasTypeUuid;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.DuplicateEntityAliasException;
import com.echothree.model.control.core.common.exception.DuplicateEntityAliasTypeAliasException;
import com.echothree.model.control.core.common.exception.DuplicateEntityAliasTypeNameException;
import com.echothree.model.control.core.common.exception.EntityTypeIsNotExtensibleException;
import com.echothree.model.control.core.common.exception.InvalidAliasException;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.MismatchedEntityTypeException;
import com.echothree.model.control.core.common.exception.UnknownEntityAliasException;
import com.echothree.model.control.core.common.exception.UnknownEntityAliasTypeNameException;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.index.server.control.IndexControl;
import com.echothree.model.control.queue.common.QueueTypes;
import com.echothree.model.control.queue.server.control.QueueControl;
import com.echothree.model.control.queue.server.logic.QueueTypeLogic;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAlias;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.value.EntityAliasTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.queue.server.value.QueuedEntityValue;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class EntityAliasTypeLogic
        extends BaseLogic {

    @Inject
    protected EntityAliasControl entityAliasControl;

    @Inject
    protected IndexControl indexControl;

    @Inject
    protected QueueControl queueControl;

    @Inject
    protected EntityInstanceLogic entityInstanceLogic;

    @Inject
    protected EntityTypeLogic entityTypeLogic;

    @Inject
    protected QueueTypeLogic queueTypeLogic;

    protected EntityAliasTypeLogic() {
        super();
    }

    public static EntityAliasTypeLogic getInstance() {
        return CDI.current().select(EntityAliasTypeLogic.class).get();
    }

    public EntityAliasType createEntityAliasType(final ExecutionErrorAccumulator eea, final EntityType entityType,
            String entityAliasTypeName, final String validationPattern, final Boolean isDefault,
            final Integer sortOrder, final BasePK createdBy, final Language language, final String description) {
        EntityAliasType entityAliasType = null;
        var entityTypeDetail = entityType.getLastDetail();

        if(entityTypeDetail.getIsExtensible()) {
            entityAliasType = entityAliasControl.getEntityAliasTypeByName(entityType, entityAliasTypeName);

            if(entityAliasType == null) {
                entityAliasType = entityAliasControl.createEntityAliasType(entityType, entityAliasTypeName, validationPattern,
                        isDefault, sortOrder, createdBy);

                if(description != null) {
                    entityAliasControl.createEntityAliasTypeDescription(entityAliasType, language, description, createdBy);
                }
            } else {
                handleExecutionError(DuplicateEntityAliasTypeNameException.class, eea, ExecutionErrors.DuplicateEntityAliasTypeName.name(),
                        entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(), entityAliasTypeName);
            }
        } else {
            handleExecutionError(EntityTypeIsNotExtensibleException.class, eea, ExecutionErrors.EntityTypeIsNotExtensible.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName());
        }

        return entityAliasType;
    }
    
    public EntityAliasType getEntityAliasTypeByName(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String entityAliasTypeName, EntityPermission entityPermission) {
        var entityAliasType = entityAliasControl.getEntityAliasTypeByName(entityType, entityAliasTypeName, entityPermission);

        if(entityAliasType == null) {
            var entityTypeDetail = entityType.getLastDetail();
            
            handleExecutionError(UnknownEntityAliasTypeNameException.class, eea, ExecutionErrors.UnknownEntityAliasTypeName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(), entityAliasTypeName);
        }

        return entityAliasType;
    }

    public EntityAliasType getEntityAliasTypeByName(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String entityAliasTypeName) {
        return getEntityAliasTypeByName(eea, entityType, entityAliasTypeName, EntityPermission.READ_ONLY);
    }
    
    public EntityAliasType getEntityAliasTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String entityAliasTypeName) {
        return getEntityAliasTypeByName(eea, entityType, entityAliasTypeName, EntityPermission.READ_WRITE);
    }
    
    public EntityAliasType getEntityAliasTypeByName(final ExecutionErrorAccumulator eea, final ComponentVendor componentVendor,
            final String entityTypeName, final String entityAliasTypeName, EntityPermission entityPermission) {
        var entityType = entityTypeLogic.getEntityTypeByName(eea, componentVendor, entityTypeName);
        EntityAliasType entityAliasType = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            entityAliasType = getEntityAliasTypeByName(eea, entityType, entityAliasTypeName, entityPermission);
        }

        return entityAliasType;
    }

    public EntityAliasType getEntityAliasTypeByName(final ExecutionErrorAccumulator eea, final ComponentVendor componentVendor,
            final String entityTypeName, final String entityAliasTypeName) {
        return getEntityAliasTypeByName(eea, componentVendor, entityTypeName, entityAliasTypeName, EntityPermission.READ_ONLY);
    }
    
    public EntityAliasType getEntityAliasTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final ComponentVendor componentVendor,
            final String entityTypeName, final String entityAliasTypeName) {
        return getEntityAliasTypeByName(eea, componentVendor, entityTypeName, entityAliasTypeName, EntityPermission.READ_WRITE);
    }
    
    public EntityAliasType getEntityAliasTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String entityAliasTypeName, EntityPermission entityPermission) {
        var entityType = entityTypeLogic.getEntityTypeByName(eea, componentVendorName, entityTypeName);
        EntityAliasType entityAliasType = null;
        
        if(eea == null || !eea.hasExecutionErrors()) {
            entityAliasType = getEntityAliasTypeByName(eea, entityType, entityAliasTypeName, entityPermission);
        }

        return entityAliasType;
    }
    
    public EntityAliasType getEntityAliasTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String entityAliasTypeName) {
        return getEntityAliasTypeByName(eea, componentVendorName, entityTypeName, entityAliasTypeName, EntityPermission.READ_ONLY);
    }
    
    public EntityAliasType getEntityAliasTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final String entityAliasTypeName) {
        return getEntityAliasTypeByName(eea, componentVendorName, entityTypeName, entityAliasTypeName, EntityPermission.READ_WRITE);
    }
    
    public EntityAliasType getEntityAliasTypeByUuid(final ExecutionErrorAccumulator eea, final String uuid,
            final EntityPermission entityPermission) {
        EntityAliasType entityAliasType = null;
        
        var entityInstance = entityInstanceLogic.getEntityInstance(eea, (String)null, uuid,
                ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityAliasType.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            entityAliasType = entityAliasControl.getEntityAliasTypeByEntityInstance(entityInstance, entityPermission);
        }

        return entityAliasType;
    }
    
    public EntityAliasType getEntityAliasTypeByUuid(final ExecutionErrorAccumulator eea, final String uuid) {
        return getEntityAliasTypeByUuid(eea, uuid, EntityPermission.READ_ONLY);
    }
    
    public EntityAliasType getEntityAliasTypeByUuidForUpdate(final ExecutionErrorAccumulator eea, final String uuid) {
        return getEntityAliasTypeByUuid(eea, uuid, EntityPermission.READ_WRITE);
    }

    // For when we need to determine the EntityType from what the user passes in:
    public EntityAliasType getEntityAliasTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final EntityAliasTypeUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        EntityAliasType entityAliasType = null;
        var componentVendorName = universalSpec.getComponentVendorName();
        var entityTypeName = universalSpec.getEntityTypeName();
        var entityAliasTypeName = universalSpec.getEntityAliasTypeName();
        var universalSpecCount = entityInstanceLogic.countPossibleEntitySpecs(universalSpec);
        var parameterCount = (componentVendorName == null && entityTypeName == null && entityAliasTypeName == null ? 0 : 1)
                + universalSpecCount;

        switch(parameterCount) {
            case 1 -> {
                if(universalSpecCount == 1) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityAliasType.name());

                    if(!eea.hasExecutionErrors()) {
                        entityAliasType = entityAliasControl.getEntityAliasTypeByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    entityAliasType = getEntityAliasTypeByName(eea, componentVendorName, entityTypeName, entityAliasTypeName, entityPermission);
                }
            }
            default -> {
                handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            }
        }

        return entityAliasType;
    }

    public EntityAliasType getEntityAliasTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final EntityAliasTypeUniversalSpec universalSpec) {
        return getEntityAliasTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public EntityAliasType getEntityAliasTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea, final EntityAliasTypeUniversalSpec universalSpec) {
        return getEntityAliasTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

    // For when we can get the EntityType from the EntityInstance:
    public EntityAliasType getEntityAliasType(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance,
            final EntityAliasTypeSpec spec, final EntityAliasTypeUuid uuid, final EntityPermission entityPermission) {
        EntityAliasType entityAliasType = null;
        var entityAliasTypeName = spec.getEntityAliasTypeName();
        var entityAliasTypeUuid = uuid.getEntityAliasTypeUuid();
        var parameterCount = (entityAliasTypeName == null ? 0 : 1) + (entityAliasTypeUuid == null ? 0 : 1);

        if (parameterCount == 1) {
            entityAliasType = entityAliasTypeName == null
                    ? getEntityAliasTypeByUuid(eea, entityAliasTypeUuid, entityPermission)
                    : getEntityAliasTypeByName(eea, entityInstance.getEntityType(), entityAliasTypeName, entityPermission);
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }
        
        // If there are no other errors, and the EntityAliasType was specified by ULID, then verify the EntityType...
        if((eea == null || !eea.hasExecutionErrors()) && entityAliasTypeUuid != null) {
            if(!entityInstance.getEntityType().equals(entityAliasType.getLastDetail().getEntityType())) {
                var expectedEntityTypeDetail = entityAliasType.getLastDetail().getEntityType().getLastDetail();
                var suppliedEntityTypeDetail = entityInstance.getEntityType().getLastDetail();

                handleExecutionError(MismatchedEntityTypeException.class, eea, ExecutionErrors.MismatchedEntityType.name(),
                        expectedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        expectedEntityTypeDetail.getEntityTypeName(),
                        suppliedEntityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                        suppliedEntityTypeDetail.getEntityTypeName());
            }
        }

        return entityAliasType;
    }
    
    public EntityAliasType getEntityAliasType(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance,
            final EntityAliasTypeSpec spec, final EntityAliasTypeUuid uuid) {
        return getEntityAliasType(eea, entityInstance, spec, uuid, EntityPermission.READ_ONLY);
    }
    
    public EntityAliasType getEntityAliasTypeForUpdate(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance,
            final EntityAliasTypeSpec spec, final EntityAliasTypeUuid uuid) {
        return getEntityAliasType(eea, entityInstance, spec, uuid, EntityPermission.READ_WRITE);
    }
    
    public void updateEntityAliasTypeFromValue(final Session session, final EntityAliasTypeDetailValue entityAliasTypeDetailValue,
            final BasePK updatedBy) {
        if(entityAliasTypeDetailValue.getEntityAliasTypeNameHasBeenModified()) {
            final var entityAliasType = entityAliasControl.getEntityAliasTypeByPK(entityAliasTypeDetailValue.getEntityAliasTypePK());

            if(indexControl.countIndexTypesByEntityType(entityAliasType.getLastDetail().getEntityType()) > 0) {
                final var queueTypePK = queueTypeLogic.getQueueTypeByName(null, QueueTypes.INDEXING.name()).getPrimaryKey();
                final var entityAliases = entityAliasControl.getEntityAliasesByEntityAliasType(entityAliasType);
                final var queuedEntities = new ArrayList<QueuedEntityValue>(entityAliases.size());

                for(final var entityAlias : entityAliases) {
                    queuedEntities.add(new QueuedEntityValue(queueTypePK, entityAlias.getEntityInstancePK(), session.getStartTime()));
                }

                queueControl.createQueuedEntities(queuedEntities);
            }
        }
        
        entityAliasControl.updateEntityAliasTypeFromValue(entityAliasTypeDetailValue, updatedBy);
    }
    
    public void deleteEntityAliasType(final ExecutionErrorAccumulator eea, final EntityAliasType entityAliasType,
            final PartyPK deletedByPK) {
        entityAliasControl.deleteEntityAliasType(entityAliasType, deletedByPK);
    }

    public EntityAlias createEntityAlias(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance,
            final EntityAliasType entityAliasType, final String alias, final BasePK createdBy) {
        EntityAlias entityAlias = null;
        var validationPattern = entityAliasType.getLastDetail().getValidationPattern();

        if(validationPattern != null) {
            var pattern = Pattern.compile(validationPattern);
            var matcher = pattern.matcher(alias);

            if(!matcher.matches()) {
                handleExecutionError(InvalidAliasException.class, eea, ExecutionErrors.InvalidAlias.name(),
                        entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                        entityAliasType.getLastDetail().getEntityAliasTypeName(), alias);
            }
        }

        if(eea == null || !eea.hasExecutionErrors()) {
            entityAlias = entityAliasControl.getEntityAlias(entityInstance, entityAliasType);

            if(entityAlias == null) {
                entityAlias = entityAliasControl.getEntityAliasByEntityAliasTypeAndAlias(entityAliasType, alias);

                if(entityAlias == null) {
                    entityAlias = entityAliasControl.createEntityAlias(entityInstance, entityAliasType, alias, createdBy);
                } else {
                    handleExecutionError(DuplicateEntityAliasTypeAliasException.class, eea, ExecutionErrors.DuplicateEntityAliasTypeAlias.name(),
                            entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                            entityAliasType.getLastDetail().getEntityAliasTypeName(), alias);
                }
            } else {
                handleExecutionError(DuplicateEntityAliasException.class, eea, ExecutionErrors.DuplicateEntityAlias.name(),
                        entityInstanceLogic.getEntityRefFromEntityInstance(entityInstance),
                        entityAliasType.getLastDetail().getEntityAliasTypeName());
            }
        }

        return entityAlias;
    }

    public EntityAlias getEntityAliasByAlias(final ExecutionErrorAccumulator eea, final EntityAliasType entityAliasType,
            final String alias) {
        var entityAlias = entityAliasControl.getEntityAliasByEntityAliasTypeAndAlias(entityAliasType, alias);

        if(entityAlias == null) {
            var entityAliasTypeDetail = entityAliasType.getLastDetail();
            var entityTypeDetail = entityAliasTypeDetail.getEntityType().getLastDetail();

            handleExecutionError(UnknownEntityAliasException.class, eea, ExecutionErrors.UnknownEntityAlias.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityTypeDetail.getEntityTypeName(), entityAliasTypeDetail.getEntityAliasTypeName(),
                    alias);
        }

        return entityAlias;
    }

}
