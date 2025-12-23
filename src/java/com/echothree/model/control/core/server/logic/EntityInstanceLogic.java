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

import com.echothree.control.user.core.common.spec.EntityRefSpec;
import com.echothree.control.user.core.common.spec.UniversalEntitySpec;
import com.echothree.control.user.core.common.spec.UuidSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EventTypes;
import com.echothree.model.control.core.common.exception.DeletedEntityRefException;
import com.echothree.model.control.core.common.exception.DeletedUuidException;
import com.echothree.model.control.core.common.exception.InvalidComponentVendorException;
import com.echothree.model.control.core.common.exception.InvalidEntityTypeException;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.UnknownEntityRefException;
import com.echothree.model.control.core.common.exception.UnknownUuidException;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.EventControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityIdGenerator;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class EntityInstanceLogic
        extends BaseLogic {

    @Inject
    protected EntityInstanceControl entityInstanceControl;

    protected EntityInstanceLogic() {
        super();
    }

    public static EntityInstanceLogic getInstance() {
        return CDI.current().select(EntityInstanceLogic.class).get();
    }

    public EntityInstance createEntityInstance(final ExecutionErrorAccumulator eea, final EntityType entityType,
            final BasePK createdBy) {
        var entityTypeDetail = entityType.getLastDetail();
        var componentVendorName = entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName();
        EntityInstance entityInstance = null;

        if(!ComponentVendors.ECHO_THREE.name().equals(componentVendorName)) {
            var eventControl = Session.getModelController(EventControl.class);
            var entityTypeName = entityTypeDetail.getEntityTypeName();
            var entityIdGenerator = new EntityIdGenerator(componentVendorName, entityTypeName, 1); // TODO
            var entityId = entityIdGenerator.getNextEntityId();
            var basePK = new BasePK(componentVendorName, entityTypeName, entityId);
            var event = eventControl.sendEvent(basePK, EventTypes.CREATE, null, null, createdBy);

            entityInstance = event.getEntityInstance();
        } else {
            handleExecutionError(InvalidComponentVendorException.class, eea, ExecutionErrors.InvalidComponentVendor.name(), componentVendorName);
        }

        return entityInstance;
    }

    private boolean checkEntityTimeForDeletion(EventControl eventControl, EntityInstance entityInstance) {
        boolean wasDeleted = false;
        var entityTime = eventControl.getEntityTime(entityInstance);

        // If the EntityTime is null, then we're not going to find a DeletedTime, which means it must exist...
        // do not mark it as having been deleted.
        if(entityTime != null) {
            // Check the DeletedTime...
            if(entityTime.getDeletedTime() != null) {
                // It's been deleted.
                wasDeleted = true;
            }
        }

        return wasDeleted;
    }
    
    public EntityInstance getEntityInstanceByEntityRef(final ExecutionErrorAccumulator eea, final String entityRef) {
        var entityInstance = entityInstanceControl.getEntityInstanceByEntityRef(entityRef);

        if(entityInstance == null) {
            handleExecutionError(UnknownEntityRefException.class, eea, ExecutionErrors.UnknownEntityRef.name(), entityRef);
        } else {
            var eventControl = Session.getModelController(EventControl.class);
            var wasDeleted = checkEntityTimeForDeletion(eventControl, entityInstance);

            if(wasDeleted) {
                entityInstance = null;
                handleExecutionError(DeletedEntityRefException.class, eea, ExecutionErrors.DeletedEntityRef.name(), entityRef);
            }
        }


        return entityInstance;
    }

    public EntityInstance getEntityInstanceByEntityRef(final ExecutionErrorAccumulator eea, final EntityRefSpec spec) {
        return getEntityInstanceByEntityRef(eea, spec.getEntityRef());
    }
    
    public EntityInstance getEntityInstanceByUuid(final ExecutionErrorAccumulator eea, final String uuid) {
        var entityInstance = entityInstanceControl.getEntityInstanceByUuid(uuid);

        if(entityInstance == null) {
            handleExecutionError(UnknownUuidException.class, eea, ExecutionErrors.UnknownUuid.name(), uuid);
        } else {
            var eventControl = Session.getModelController(EventControl.class);
            var wasDeleted = checkEntityTimeForDeletion(eventControl, entityInstanceControl.getEntityInstanceByUuid(uuid));

            if(wasDeleted) {
                entityInstance = null;
                handleExecutionError(DeletedUuidException.class, eea, ExecutionErrors.DeletedUuid.name(), uuid);
            }
        }

        return entityInstance;
    }

    public EntityInstance getEntityInstanceByUuid(final ExecutionErrorAccumulator eea, final UuidSpec spec) {
        return getEntityInstanceByUuid(eea, spec.getUuid());
    }
    
    public EntityInstance getEntityInstance(final ExecutionErrorAccumulator eea, final String entityRef,
            final String uuid, final String componentVendorName, final String... entityTypeNames) {
        var parameterCount = countPossibleEntitySpecs(entityRef, uuid);
        EntityInstance entityInstance = null;
        
        if(parameterCount == 1) {
            if(entityRef != null) {
                entityInstance = getEntityInstanceByEntityRef(eea, entityRef);
            } else if(uuid != null) {
                entityInstance = getEntityInstanceByUuid(eea, uuid);
            }
        } else {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }
        
        if((eea == null || !eea.hasExecutionErrors()) && componentVendorName != null && entityTypeNames.length > 0) {
            var entityTypeDetail = entityInstance.getEntityType().getLastDetail();
            var foundComponentVendorName = entityTypeDetail.getComponentVendor().getLastDetail().getComponentVendorName();
            var foundEntityTypeName = entityTypeDetail.getEntityTypeName();
            var found = false;
            
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
    
    public EntityInstance getEntityInstance(final ExecutionErrorAccumulator eea, final EntityRefSpec entityRefSpec,
            final UuidSpec uuidSpec, final String componentVendorName, final String... entityTypeNames) {
        return getEntityInstance(eea, entityRefSpec.getEntityRef(), uuidSpec.getUuid(),
                componentVendorName, entityTypeNames);
    }
    
    public EntityInstance getEntityInstance(final ExecutionErrorAccumulator eea, final UniversalEntitySpec universalEntitySpec) {
        return getEntityInstance(eea, universalEntitySpec.getEntityRef(),
                universalEntitySpec.getUuid(), null);
    }
    
    public EntityInstance getEntityInstance(final ExecutionErrorAccumulator eea, final UniversalEntitySpec universalEntitySpec,
            final String componentVendorName, final String... entityTypeNames) {
        return getEntityInstance(eea, universalEntitySpec.getEntityRef(),
                universalEntitySpec.getUuid(), componentVendorName, entityTypeNames);
    }
    
    public int countPossibleEntitySpecs(final String entityRef, final String uuid) {
        return (entityRef == null ? 0 : 1) + (uuid == null ? 0 : 1);
    }
    
    public int countPossibleEntitySpecs(final EntityRefSpec entityRefSpec, final UuidSpec uuidSpec) {
        return countPossibleEntitySpecs(entityRefSpec == null ? null : entityRefSpec.getEntityRef(),
                uuidSpec == null ? null : uuidSpec.getUuid());
    }
    
    public int countPossibleEntitySpecs(final UniversalEntitySpec universalEntitySpec) {
        return universalEntitySpec == null ? 0 : countPossibleEntitySpecs(universalEntitySpec.getEntityRef(),
                universalEntitySpec.getUuid());
    }
    
    public String getEntityRefFromEntityInstance(EntityInstance entityInstance) {
        var entityTypeDetail = entityInstance.getEntityType().getLastDetail();
        var componentVendorDetail = entityTypeDetail.getComponentVendor().getLastDetail();
        
        return componentVendorDetail.getComponentVendorName() + '.' +
                entityTypeDetail.getEntityTypeName() + '.' +
                entityInstance.getEntityUniqueId();
    }

    public void deleteEntityInstance(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance, final BasePK deletedBy) {
        var componentVendorName = entityInstance.getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName();

        if(!ComponentVendors.ECHO_THREE.name().equals(componentVendorName)) {
            entityInstanceControl.deleteEntityInstance(entityInstance, deletedBy);
        } else {
            handleExecutionError(InvalidComponentVendorException.class, eea, ExecutionErrors.InvalidComponentVendor.name(), componentVendorName);
        }
    }

    public void removeEntityInstance(final ExecutionErrorAccumulator eea, final EntityInstance entityInstance) {
        var componentVendorName = entityInstance.getEntityType().getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName();

        if(!ComponentVendors.ECHO_THREE.name().equals(componentVendorName)) {
            entityInstanceControl.removeEntityInstance(entityInstance);
        } else {
            handleExecutionError(InvalidComponentVendorException.class, eea, ExecutionErrors.InvalidComponentVendor.name(), componentVendorName);
        }
    }

}
