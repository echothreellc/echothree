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

import com.echothree.control.user.core.common.spec.EntityRefSpec;
import com.echothree.control.user.core.common.spec.GuidSpec;
import com.echothree.control.user.core.common.spec.KeySpec;
import com.echothree.control.user.core.common.spec.UlidSpec;
import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.exception.InvalidComponentVendorException;
import com.echothree.model.control.core.common.exception.InvalidEntityTypeException;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.UnknownEntityRefException;
import com.echothree.model.control.core.common.exception.UnknownGuidException;
import com.echothree.model.control.core.common.exception.UnknownKeyException;
import com.echothree.model.control.core.common.exception.UnknownUlidException;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTime;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.core.server.entity.EntityTypeDetail;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityIdGenerator;
import com.echothree.util.server.persistence.Session;

public class EntityInstanceLogic
        extends BaseLogic {

    private EntityInstanceLogic() {
        super();
    }

    private static class EntityInstanceLogicHolder {
        static EntityInstanceLogic instance = new EntityInstanceLogic();
    }

    public static EntityInstanceLogic getInstance() {
        return EntityInstanceLogicHolder.instance;
    }

    public EntityInstance createEntityInstance(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final BasePK createdBy) {
        var entityTypeDetail = entityType.getLastDetail();
        var componentVendorName = entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName();
        EntityInstance entityInstance = null;

        if(!ComponentVendors.ECHO_THREE.name().equals(componentVendorName)) {
            var coreControl = Session.getModelController(CoreControl.class);
            var entityTypeName = entityTypeDetail.getEntityTypeName();
            var entityIdGenerator = new EntityIdGenerator(componentVendorName, entityTypeName, 1); // TODO
            var entityId = entityIdGenerator.getNextEntityId();
            var basePK = new BasePK(componentVendorName, entityTypeName, entityId);
            var event = coreControl.sendEvent(basePK, EventTypes.CREATE, null, null, createdBy);

            entityInstance = event.getEntityInstance();
        } else {
            handleExecutionError(InvalidComponentVendorException.class, eea, ExecutionErrors.InvalidComponentVendor.name(), componentVendorName);
        }

        return entityInstance;
    }

    private EntityInstance checkEntityTimeForDeletion(CoreControl coreControl, EntityInstance entityInstance) {
        // If the EntityInstance is null, then it is already going to indicate it has been deleted, otherwise...
        if(entityInstance != null) {
            EntityTime entityTime = coreControl.getEntityTime(entityInstance);

            // If the EntityTime is null, then we're not going to find a DeletedTime, which means it must exist...
            if(entityTime != null) {
                // Check the DeletedTime...
                if(entityTime.getDeletedTime() != null) {
                    // It's been deleted.
                    entityInstance = null;
                }
            }
        }
        
        return entityInstance;
    }
    
    public EntityInstance getEntityInstanceByEntityRef(final ExecutionErrorAccumulator eea, final String entityRef) {
        var coreControl = Session.getModelController(CoreControl.class);
        EntityInstance entityInstance = checkEntityTimeForDeletion(coreControl, coreControl.getEntityInstanceByEntityRef(entityRef));
        
        if(entityInstance == null) {
            handleExecutionError(UnknownEntityRefException.class, eea, ExecutionErrors.UnknownEntityRef.name(), entityRef);
        }

        return entityInstance;
    }

    public EntityInstance getEntityInstanceByEntityRef(final ExecutionErrorAccumulator eea, final EntityRefSpec spec) {
        return getEntityInstanceByEntityRef(eea, spec.getEntityRef());
    }
    
    public EntityInstance getEntityInstanceByKey(final ExecutionErrorAccumulator eea, final String key) {
        var coreControl = Session.getModelController(CoreControl.class);
        EntityInstance entityInstance = checkEntityTimeForDeletion(coreControl, coreControl.getEntityInstanceByKey(key));

        if(entityInstance == null) {
            handleExecutionError(UnknownKeyException.class, eea, ExecutionErrors.UnknownKey.name(), key);
        }

        return entityInstance;
    }

    public EntityInstance getEntityInstanceByKey(final ExecutionErrorAccumulator eea, final KeySpec spec) {
        return getEntityInstanceByKey(eea, spec.getKey());
    }
    
    public EntityInstance getEntityInstanceByGuid(final ExecutionErrorAccumulator eea, final String guid) {
        var coreControl = Session.getModelController(CoreControl.class);
        EntityInstance entityInstance = checkEntityTimeForDeletion(coreControl, coreControl.getEntityInstanceByGuid(guid));

        if(entityInstance == null) {
            handleExecutionError(UnknownGuidException.class, eea, ExecutionErrors.UnknownGuid.name(), guid);
        }

        return entityInstance;
    }

    public EntityInstance getEntityInstanceByUlid(final ExecutionErrorAccumulator eea, final String ulid) {
        var coreControl = Session.getModelController(CoreControl.class);
        EntityInstance entityInstance = checkEntityTimeForDeletion(coreControl, coreControl.getEntityInstanceByUlid(ulid));

        if(entityInstance == null) {
            handleExecutionError(UnknownUlidException.class, eea, ExecutionErrors.UnknownUlid.name(), ulid);
        }

        return entityInstance;
    }

    public EntityInstance getEntityInstanceByGuid(final ExecutionErrorAccumulator eea, final GuidSpec spec) {
        return getEntityInstanceByGuid(eea, spec.getGuid());
    }
    
    public EntityInstance getEntityInstanceByUlid(final ExecutionErrorAccumulator eea, final UlidSpec spec) {
        return getEntityInstanceByUlid(eea, spec.getUlid());
    }
    
    public EntityInstance getEntityInstance(final ExecutionErrorAccumulator eea, final String entityRef, final String key,
            final String guid, final String ulid, final String componentVendorName, final String... entityTypeNames) {
        var parameterCount = countPossibleEntitySpecs(entityRef, key, guid, ulid);
        EntityInstance entityInstance = null;
        
        if(parameterCount == 1) {
            if(entityRef != null) {
                entityInstance = getEntityInstanceByEntityRef(eea, entityRef);
            } else if(key != null) {
                entityInstance = getEntityInstanceByKey(eea, key);
            } else if(guid != null) {
                entityInstance = getEntityInstanceByGuid(eea, guid);
            } else if(ulid != null) {
                entityInstance = getEntityInstanceByUlid(eea, ulid);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }
        
        if((eea == null || !eea.hasExecutionErrors()) && componentVendorName != null && entityTypeNames.length > 0) {
            EntityTypeDetail entityTypeDetail = entityInstance.getEntityType().getLastDetail();
            String foundComponentVendorName = entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName();
            String foundEntityTypeName = entityTypeDetail.getEntityTypeName();
            boolean found = false;
            
            if(foundComponentVendorName.equals(componentVendorName)) {
                for(var entityTypeName : entityTypeNames) {
                    if(entityTypeName.equals(foundEntityTypeName)) {
                        found = true;
                        break;
                    }
                }
            }
            
            if(!found) {
                handleExecutionError(InvalidEntityTypeException.class, eea, ExecutionErrors.InvalidEntityType.name(), foundComponentVendorName, foundEntityTypeName);
                entityInstance = null;
            }
        }
        
        return entityInstance;
    }
    
    public EntityInstance getEntityInstance(final ExecutionErrorAccumulator eea, final EntityRefSpec entityRefSpec, final KeySpec keySpec,
            final GuidSpec guidSpec, final UlidSpec ulidSpec, final String componentVendorName, final String... entityTypeNames) {
        return getEntityInstance(eea, entityRefSpec.getEntityRef(), keySpec.getKey(), guidSpec.getGuid(), ulidSpec.getUlid(),
                componentVendorName, entityTypeNames);
    }
    
    public EntityInstance getEntityInstance(final ExecutionErrorAccumulator eea, final UniversalEntitySpec universalEntitySpec) {
        return getEntityInstance(eea, universalEntitySpec.getEntityRef(), universalEntitySpec.getKey(),
                universalEntitySpec.getGuid(), universalEntitySpec.getUlid(), null);
    }
    
    public EntityInstance getEntityInstance(final ExecutionErrorAccumulator eea, final UniversalEntitySpec universalEntitySpec,
            final String componentVendorName, final String... entityTypeNames) {
        return getEntityInstance(eea, universalEntitySpec.getEntityRef(), universalEntitySpec.getKey(),
                universalEntitySpec.getGuid(), universalEntitySpec.getUlid(), componentVendorName, entityTypeNames);
    }
    
    public int countPossibleEntitySpecs(final String entityRef, final String key, final String guid, final String ulid) {
        return (entityRef == null ? 0 : 1) + (key == null ? 0 : 1) + (guid == null ? 0 : 1) + (ulid == null ? 0 : 1);
    }
    
    public int countPossibleEntitySpecs(final EntityRefSpec entityRefSpec, final KeySpec keySpec, final GuidSpec guidSpec,
            final UlidSpec ulidSpec) {
        return countPossibleEntitySpecs(entityRefSpec == null ? null : entityRefSpec.getEntityRef(),
                keySpec == null ? null : keySpec.getKey(), guidSpec == null ? null : guidSpec.getGuid(),
                ulidSpec == null ? null : ulidSpec.getUlid());
    }
    
    public int countPossibleEntitySpecs(final UniversalEntitySpec universalEntitySpec) {
        return universalEntitySpec == null ? 0 : countPossibleEntitySpecs(universalEntitySpec.getEntityRef(),
                universalEntitySpec.getKey(), universalEntitySpec.getGuid(), universalEntitySpec.getUlid());
    }
    
    public String getEntityRefFromEntityInstance(EntityInstance entityInstance) {
        var entityTypeDetail = entityInstance.getEntityType().getLastDetail();
        var componentVendorDetail = entityTypeDetail.getComponentVendor().getLastDetail();
        
        return new StringBuilder(componentVendorDetail.getComponentVendorName()).append('.')
                .append(entityTypeDetail.getEntityTypeName()).append('.')
                .append(entityInstance.getEntityUniqueId()).toString();
    }

}
