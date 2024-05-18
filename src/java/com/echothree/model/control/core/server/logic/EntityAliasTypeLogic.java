// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.control.user.core.common.spec.EntityAliasTypeUlid;
import com.echothree.control.user.core.common.spec.EntityAliasTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.DuplicateEntityAliasException;
import com.echothree.model.control.core.common.exception.DuplicateEntityAliasTypeAliasException;
import com.echothree.model.control.core.common.exception.DuplicateEntityAliasTypeNameException;
import com.echothree.model.control.core.common.exception.InvalidAliasException;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.MismatchedEntityTypeException;
import com.echothree.model.control.core.common.exception.UnknownEntityAliasTypeNameException;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityAlias;
import com.echothree.model.data.core.server.entity.EntityAliasType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.model.data.core.server.value.EntityAliasTypeDetailValue;
import com.echothree.model.data.party.common.pk.PartyPK;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import com.echothree.util.server.persistence.Session;
import java.util.regex.Pattern;

public class EntityAliasTypeLogic
        extends BaseLogic {
    
    private EntityAliasTypeLogic() {
        super();
    }
    
    private static class EntityAliasTypeLogicHolder {
        static EntityAliasTypeLogic instance = new EntityAliasTypeLogic();
    }
    
    public static EntityAliasTypeLogic getInstance() {
        return EntityAliasTypeLogicHolder.instance;
    }

    public EntityAliasType createEntityAliasType(final ExecutionErrorAccumulator eea, final EntityType entityType,
            String entityAliasTypeName, final String validationPattern, final Boolean isDefault,
            final Integer sortOrder, final BasePK createdBy, final Language language, final String description) {
        var coreControl = Session.getModelController(CoreControl.class);
        var entityAliasType = coreControl.getEntityAliasTypeByName(entityType, entityAliasTypeName);

        if(entityAliasType == null) {
            entityAliasType = coreControl.createEntityAliasType(entityType, entityAliasTypeName, validationPattern,
                    isDefault, sortOrder, createdBy);

            if(description != null) {
                coreControl.createEntityAliasTypeDescription(entityAliasType, language, description, createdBy);
            }
        } else {
            var entityTypeDetail = entityType.getLastDetail();

            handleExecutionError(DuplicateEntityAliasTypeNameException.class, eea, ExecutionErrors.DuplicateEntityAliasTypeName.name(),
                    entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName(), entityTypeDetail.getEntityTypeName(), entityAliasTypeName);
        }

        return entityAliasType;
    }
    
    public EntityAliasType getEntityAliasTypeByName(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final String entityAliasTypeName, EntityPermission entityPermission) {
        var coreControl = Session.getModelController(CoreControl.class);
        EntityAliasType entityAliasType = coreControl.getEntityAliasTypeByName(entityType, entityAliasTypeName, entityPermission);

        if(entityAliasType == null) {
            EntityTypeDetail entityTypeDetail = entityType.getLastDetail();
            
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
        EntityType entityType = EntityTypeLogic.getInstance().getEntityTypeByName(eea, componentVendor, entityTypeName);
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
        EntityType entityType = EntityTypeLogic.getInstance().getEntityTypeByName(eea, componentVendorName, entityTypeName);
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
    
    public EntityAliasType getEntityAliasTypeByUlid(final ExecutionErrorAccumulator eea, final String ulid,
            final EntityPermission entityPermission) {
        EntityAliasType entityAliasType = null;
        
        var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, (String)null, null, null, ulid,
                ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityAliasType.name());

        if(eea == null || !eea.hasExecutionErrors()) {
            var coreControl = Session.getModelController(CoreControl.class);
            
            entityAliasType = coreControl.getEntityAliasTypeByEntityInstance(entityInstance, entityPermission);
        }

        return entityAliasType;
    }
    
    public EntityAliasType getEntityAliasTypeByUlid(final ExecutionErrorAccumulator eea, final String ulid) {
        return getEntityAliasTypeByUlid(eea, ulid, EntityPermission.READ_ONLY);
    }
    
    public EntityAliasType getEntityAliasTypeByUlidForUpdate(final ExecutionErrorAccumulator eea, final String ulid) {
        return getEntityAliasTypeByUlid(eea, ulid, EntityPermission.READ_WRITE);
    }

    // For when we need to determine the EntityType from what the user passes in:
    public EntityAliasType getEntityAliasTypeByUniversalSpec(final ExecutionErrorAccumulator eea, final EntityAliasTypeUniversalSpec universalSpec,
            final EntityPermission entityPermission) {
        EntityAliasType entityAliasType = null;
        var componentVendorName = universalSpec.getComponentVendorName();
        var entityTypeName = universalSpec.getEntityTypeName();
        var entityAliasTypeName = universalSpec.getEntityAliasTypeName();
        var universalSpecCount = EntityInstanceLogic.getInstance().countPossibleEntitySpecs(universalSpec);
        var parameterCount = (componentVendorName == null && entityTypeName == null && entityAliasTypeName == null ? 0 : 1)
                + universalSpecCount;

        switch(parameterCount) {
            case 1 -> {
                if(universalSpecCount == 1) {
                    var entityInstance = EntityInstanceLogic.getInstance().getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityAliasType.name());

                    if(!eea.hasExecutionErrors()) {
                        var coreControl = Session.getModelController(CoreControl.class);

                        entityAliasType = coreControl.getEntityAliasTypeByEntityInstance(entityInstance, entityPermission);
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
            final EntityAliasTypeSpec spec, final EntityAliasTypeUlid ulid, final EntityPermission entityPermission) {
        EntityAliasType entityAliasType = null;
        String entityAliasTypeName = spec.getEntityAliasTypeName();
        String entityAliasTypeUlid = ulid.getEntityAliasTypeUlid();
        var parameterCount = (entityAliasTypeName == null ? 0 : 1) + (entityAliasTypeUlid == null ? 0 : 1);

        if (parameterCount == 1) {
            entityAliasType = entityAliasTypeName == null
                    ? getEntityAliasTypeByUlid(eea, entityAliasTypeUlid, entityPermission)
                    : getEntityAliasTypeByName(eea, entityInstance.getEntityType(), entityAliasTypeName, entityPermission);
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }
        
        // If there are no other errors, and the EntityAliasType was specified by ULID, then verify the EntityType...
        if((eea == null || !eea.hasExecutionErrors()) && entityAliasTypeUlid != null) {
            if(!entityInstance.getEntityType().equals(entityAliasType.getLastDetail().getEntityType())) {
                EntityTypeDetail expectedEntityTypeDetail = entityAliasType.getLastDetail().getEntityType().getLastDetail();
                EntityTypeDetail suppliedEntityTypeDetail = entityInstance.getEntityType().getLastDetail();

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
            final EntityAliasTypeSpec spec, final EntityAliasTypeUlid ulid) {
        return getEntityAliasType(eea, entityInstance, spec, ulid, EntityPermission.READ_ONLY);
    }
    
    public EntityAliasType getEntityAliasTypeForUpdate(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance,
            final EntityAliasTypeSpec spec, final EntityAliasTypeUlid ulid) {
        return getEntityAliasType(eea, entityInstance, spec, ulid, EntityPermission.READ_WRITE);
    }
    
    public void updateEntityAliasTypeFromValue(final Session session, final EntityAliasTypeDetailValue entityAliasTypeDetailValue,
            final BasePK updatedBy) {
        final var coreControl = Session.getModelController(CoreControl.class);

        coreControl.updateEntityAliasTypeFromValue(entityAliasTypeDetailValue, updatedBy);
    }
    
    public void deleteEntityAliasType(final ExecutionErrorAccumulator eea, final EntityAliasType entityAliasType,
            final PartyPK deletedByPK) {
        var coreControl = Session.getModelController(CoreControl.class);

        coreControl.deleteEntityAliasType(entityAliasType, deletedByPK);
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
                        EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                        entityAliasType.getLastDetail().getEntityAliasTypeName(), alias);
            }
        }

        if(eea == null || !eea.hasExecutionErrors()) {
            var coreControl = Session.getModelController(CoreControl.class);

            entityAlias = coreControl.getEntityAlias(entityInstance, entityAliasType);

            if(entityAlias == null) {
                entityAlias = coreControl.getEntityAliasByEntityAliasTypeAndAlias(entityAliasType, alias);

                if(entityAlias == null) {
                    entityAlias = coreControl.createEntityAlias(entityInstance, entityAliasType, alias, createdBy);
                } else {
                    handleExecutionError(DuplicateEntityAliasTypeAliasException.class, eea, ExecutionErrors.DuplicateEntityAliasTypeAlias.name(),
                            EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                            entityAliasType.getLastDetail().getEntityAliasTypeName(), alias);
                }
            } else {
                handleExecutionError(DuplicateEntityAliasException.class, eea, ExecutionErrors.DuplicateEntityAlias.name(),
                        EntityInstanceLogic.getInstance().getEntityRefFromEntityInstance(entityInstance),
                        entityAliasType.getLastDetail().getEntityAliasTypeName());
            }
        }

        return entityAlias;
    }


}
