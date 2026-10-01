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

import com.echothree.control.user.core.common.spec.EntityAttributeGroupUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.DuplicateEntityAttributeGroupNameException;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.UnknownEntityAttributeGroupNameException;
import com.echothree.model.control.core.server.control.EntityAttributeGroupControl;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityType;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EntityAttributeGroupLogic
        extends BaseLogic {

    @Inject
    protected EntityAttributeGroupControl entityAttributeGroupControl;

    @Inject
    protected SequenceControl sequenceControl;

    @Inject
    protected EntityInstanceLogic entityInstanceLogic;

    @Inject
    protected EntityTypeLogic entityTypeLogic;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;

    protected EntityAttributeGroupLogic() {
        super();
    }
    
    public EntityAttributeGroup createEntityAttributeGroup(final ExecutionErrorAccumulator eea, final EntityType entityType, String entityAttributeGroupName,
            final Boolean isDefault, final Integer sortOrder, final String description, final Language language, final BasePK createdBy) {
        if(entityAttributeGroupName == null) {
            var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.ENTITY_ATTRIBUTE_GROUP.name());
            
            entityAttributeGroupName = sequenceGeneratorLogic.getNextSequenceValue(sequence);
        }

        var entityAttributeGroup = entityAttributeGroupControl.getEntityAttributeGroupByName(entityType, entityAttributeGroupName);
        
        if(entityAttributeGroup == null) {
            entityAttributeGroup = entityAttributeGroupControl.createEntityAttributeGroup(entityType, entityAttributeGroupName, isDefault, sortOrder, createdBy);

            if(description != null) {
                entityAttributeGroupControl.createEntityAttributeGroupDescription(entityAttributeGroup, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateEntityAttributeGroupNameException.class, eea, ExecutionErrors.DuplicateEntityAttributeGroupName.name(),
                    entityType.getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityType.getLastDetail().getEntityTypeName(), entityAttributeGroupName);
        }

        return entityAttributeGroup;
    }

    public EntityAttributeGroup getEntityAttributeGroupByName(final ExecutionErrorAccumulator eea, final EntityType entityType, final String entityAttributeGroupName,
            final EntityPermission entityPermission) {
        var entityAttributeGroup = entityAttributeGroupControl.getEntityAttributeGroupByName(entityType, entityAttributeGroupName, entityPermission);

        if(entityAttributeGroup == null) {
            handleExecutionError(UnknownEntityAttributeGroupNameException.class, eea, ExecutionErrors.UnknownEntityAttributeGroupName.name(),
                    entityType.getLastDetail().getComponentVendor().getLastDetail().getComponentVendorName(),
                    entityType.getLastDetail().getEntityTypeName(), entityAttributeGroupName);
        }

        return entityAttributeGroup;
    }

    public EntityAttributeGroup getEntityAttributeGroupByName(final ExecutionErrorAccumulator eea, final EntityType entityType, final String entityAttributeGroupName) {
        return getEntityAttributeGroupByName(eea, entityType, entityAttributeGroupName, EntityPermission.READ_ONLY);
    }

    public EntityAttributeGroup getEntityAttributeGroupByNameForUpdate(final ExecutionErrorAccumulator eea, final EntityType entityType, final String entityAttributeGroupName) {
        return getEntityAttributeGroupByName(eea, entityType, entityAttributeGroupName, EntityPermission.READ_WRITE);
    }

    public EntityAttributeGroup getEntityAttributeGroupByName(final ExecutionErrorAccumulator eea,
            final String componentVendorName, final String entityTypeName, final String entityAttributeGroupName,
            final EntityPermission entityPermission) {
        var entityType = entityTypeLogic.getEntityTypeByName(eea, componentVendorName, entityTypeName);

        return hasExecutionErrors(eea) ? null : getEntityAttributeGroupByName(eea, entityType, entityAttributeGroupName, entityPermission);
    }

    public EntityAttributeGroup getEntityAttributeGroupByName(final ExecutionErrorAccumulator eea,
            final String componentVendorName, final String entityTypeName, final String entityAttributeGroupName) {
        return getEntityAttributeGroupByName(eea, componentVendorName, entityTypeName, entityAttributeGroupName, EntityPermission.READ_ONLY);
    }

    public EntityAttributeGroup getEntityAttributeGroupByUniversalSpec(final ExecutionErrorAccumulator eea,
            final EntityAttributeGroupUniversalSpec universalSpec, final EntityPermission entityPermission) {
        EntityAttributeGroup entityAttributeGroup = null;
        var entityAttributeGroupName = universalSpec.getEntityAttributeGroupName();
        var componentVendorName = universalSpec.getComponentVendorName();
        var entityTypeName = universalSpec.getEntityTypeName();
        var nameCount = (componentVendorName == null ? 0 : 1) + (entityTypeName == null ? 0 : 1)
                + (entityAttributeGroupName == null ? 0 : 1);
        if(nameCount != 0 && nameCount != 3) {
            handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
            return null;
        }
        var parameterCount = (nameCount == 3 ? 1 : 0) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(entityAttributeGroupName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityAttributeGroup.name());

                    if(eea == null || !eea.hasExecutionErrors()) {
                        entityAttributeGroup = entityAttributeGroupControl.getEntityAttributeGroupByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    entityAttributeGroup = getEntityAttributeGroupByName(eea, componentVendorName, entityTypeName, entityAttributeGroupName, entityPermission);
                }
            }
            default ->
                    handleExecutionError(InvalidParameterCountException.class, eea, ExecutionErrors.InvalidParameterCount.name());
        }

        return entityAttributeGroup;
    }

    public EntityAttributeGroup getEntityAttributeGroupByUniversalSpec(final ExecutionErrorAccumulator eea,
            final EntityAttributeGroupUniversalSpec universalSpec) {
        return getEntityAttributeGroupByUniversalSpec(eea, universalSpec, EntityPermission.READ_ONLY);
    }

    public EntityAttributeGroup getEntityAttributeGroupByUniversalSpecForUpdate(final ExecutionErrorAccumulator eea,
            final EntityAttributeGroupUniversalSpec universalSpec) {
        return getEntityAttributeGroupByUniversalSpec(eea, universalSpec, EntityPermission.READ_WRITE);
    }

}
