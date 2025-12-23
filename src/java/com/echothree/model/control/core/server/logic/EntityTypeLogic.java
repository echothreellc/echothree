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

import com.echothree.control.user.core.common.spec.EntityTypeUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.UnknownEntityTypeNameException;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.data.core.server.entity.ComponentVendor;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class EntityTypeLogic
        extends BaseLogic {
    
    @Inject
    EntityTypeControl entityTypeControl;
    
    @Inject
    ComponentVendorLogic componentVendorLogic;
    
    @Inject
    EntityInstanceLogic entityInstanceLogic;

    protected EntityTypeLogic() {
        super();
    }

    public static EntityTypeLogic getInstance() {
        return CDI.current().select(EntityTypeLogic.class).get();
    }
    
    public EntityType getEntityTypeByName(final ExecutionErrorAccumulator eea, final ComponentVendor componentVendor,
            final String entityTypeName, final EntityPermission entityPermission) {
        var entityType = entityTypeControl.getEntityTypeByName(componentVendor, entityTypeName, entityPermission);

        if(entityType == null) {
            handleExecutionError(UnknownEntityTypeNameException.class, eea, ExecutionErrors.UnknownEntityTypeName.name(),
                    componentVendor.getLastDetail().getComponentVendorName(), entityTypeName);
        }

        return entityType;
    }

    public EntityType getEntityTypeByName(final ExecutionErrorAccumulator eea, final ComponentVendor componentVendor,
            final String entityTypeName) {
        return getEntityTypeByName(eea, componentVendor, entityTypeName, EntityPermission.READ_ONLY);
    }

    public EntityType getEntityTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final ComponentVendor componentVendor,
            final String entityTypeName) {
        return getEntityTypeByName(eea, componentVendor, entityTypeName, EntityPermission.READ_WRITE);
    }

    public EntityType getEntityTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName, final EntityPermission entityPermission) {
        var componentVendor = componentVendorLogic.getComponentVendorByName(eea, componentVendorName);
        EntityType entityType = null;
        
        if(!hasExecutionErrors(eea)) {
            entityType = getEntityTypeByName(eea, componentVendor, entityTypeName, entityPermission);
        }
        
        return entityType;
    }

    public EntityType getEntityTypeByName(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName) {
        return getEntityTypeByName(eea, componentVendorName, entityTypeName, EntityPermission.READ_ONLY);
    }

    public EntityType getEntityTypeByNameForUpdate(final ExecutionErrorAccumulator eea, final String componentVendorName,
            final String entityTypeName) {
        return getEntityTypeByName(eea, componentVendorName, entityTypeName, EntityPermission.READ_WRITE);
    }

    public EntityType getEntityTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final EntityTypeUniversalSpec universalSpec, final EntityPermission entityPermission) {
        EntityType entityType = null;
        var componentVendorName = universalSpec.getComponentVendorName();
        var entityTypeName = universalSpec.getEntityTypeName();
        var parameterCount = (componentVendorName == null ? 0 : 1) + (entityTypeName == null ? 0 : 1)
                + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 2 -> {
                if(componentVendorName != null && entityTypeName != null) {
                    entityType = getEntityTypeByName(eea, componentVendorName, entityTypeName, entityPermission);
                } else {
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
                }
            }
            case 1 -> {
                var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                        ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityType.name());

                if(!eea.hasExecutionErrors()) {
                    entityType = entityTypeControl.getEntityTypeByEntityInstance(entityInstance, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return entityType;
    }

    public EntityType getEntityTypeByUniversalSpec(final ExecutionErrorAccumulator eea,
            final EntityTypeUniversalSpec universalSpec) {
        return getEntityTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public EntityType getEntityTypeByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final EntityTypeUniversalSpec universalSpec) {
        return getEntityTypeByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }
    
}
