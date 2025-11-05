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

import com.echothree.control.user.core.common.spec.EntityAttributeGroupUniversalSpec;
import com.echothree.model.control.core.common.ComponentVendors;
import com.echothree.model.control.core.common.EntityTypes;
import com.echothree.model.control.core.common.exception.DuplicateEntityAttributeGroupNameException;
import com.echothree.model.control.core.common.exception.InvalidParameterCountException;
import com.echothree.model.control.core.common.exception.UnknownEntityAttributeGroupNameException;
import com.echothree.model.control.sequence.common.SequenceTypes;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.control.sequence.server.logic.SequenceGeneratorLogic;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.EntityPermission;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

@ApplicationScoped
public class EntityAttributeGroupLogic
        extends BaseLogic {

    @Inject
    protected SequenceControl sequenceControl;

    @Inject
    protected EntityInstanceLogic entityInstanceLogic;

    @Inject
    protected SequenceGeneratorLogic sequenceGeneratorLogic;

    protected EntityAttributeGroupLogic() {
        super();
    }

    public static EntityAttributeGroupLogic getInstance() {
        return CDI.current().select(EntityAttributeGroupLogic.class).get();
    }
    
    public EntityAttributeGroup createEntityAttributeGroup(final ExecutionErrorAccumulator eea, String entityAttributeGroupName,
            final Boolean isDefault, final Integer sortOrder, final String description, final Language language, final BasePK createdBy) {
        if(entityAttributeGroupName == null) {
            var sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceTypes.ENTITY_ATTRIBUTE_GROUP.name());
            
            entityAttributeGroupName = sequenceGeneratorLogic.getNextSequenceValue(sequence);
        }

        var entityAttributeGroup = coreControl.getEntityAttributeGroupByName(entityAttributeGroupName);
        
        if(entityAttributeGroup == null) {
            entityAttributeGroup = coreControl.createEntityAttributeGroup(entityAttributeGroupName, isDefault, sortOrder, createdBy);

            if(description != null) {
                coreControl.createEntityAttributeGroupDescription(entityAttributeGroup, language, description, createdBy);
            }
        } else {
            handleExecutionError(DuplicateEntityAttributeGroupNameException.class, eea, ExecutionErrors.DuplicateEntityAttributeGroupName.name(),
                    entityAttributeGroupName);
        }

        return entityAttributeGroup;
    }

    public EntityAttributeGroup getEntityAttributeGroupByName(final ExecutionErrorAccumulator eea, final String entityAttributeGroupName,
            final EntityPermission entityPermission) {
        var entityAttributeGroup = coreControl.getEntityAttributeGroupByName(entityAttributeGroupName, entityPermission);

        if(entityAttributeGroup == null) {
            handleExecutionError(UnknownEntityAttributeGroupNameException.class, eea, ExecutionErrors.UnknownEntityAttributeGroupName.name(), entityAttributeGroupName);
        }

        return entityAttributeGroup;
    }

    public EntityAttributeGroup getEntityAttributeGroupByName(final ExecutionErrorAccumulator eea, final String entityAttributeGroupName) {
        return getEntityAttributeGroupByName(eea, entityAttributeGroupName, EntityPermission.READ_ONLY);
    }

    public EntityAttributeGroup getEntityAttributeGroupByNameForUpdate(final ExecutionErrorAccumulator eea, final String entityAttributeGroupName) {
        return getEntityAttributeGroupByName(eea, entityAttributeGroupName, EntityPermission.READ_WRITE);
    }

    public EntityAttributeGroup getEntityAttributeGroupByUniversalSpec(final ExecutionErrorAccumulator eea,
            final EntityAttributeGroupUniversalSpec universalSpec, final EntityPermission entityPermission) {
        EntityAttributeGroup entityAttributeGroup = null;
        var entityAttributeGroupName = universalSpec.getEntityAttributeGroupName();
        var parameterCount = (entityAttributeGroupName == null ? 0 : 1) + entityInstanceLogic.countPossibleEntitySpecs(universalSpec);

        switch(parameterCount) {
            case 1 -> {
                if(entityAttributeGroupName == null) {
                    var entityInstance = entityInstanceLogic.getEntityInstance(eea, universalSpec,
                            ComponentVendors.ECHO_THREE.name(), EntityTypes.EntityAttributeGroup.name());

                    if(!eea.hasExecutionErrors()) {
                        entityAttributeGroup = coreControl.getEntityAttributeGroupByEntityInstance(entityInstance, entityPermission);
                    }
                } else {
                    entityAttributeGroup = getEntityAttributeGroupByName(eea, entityAttributeGroupName, entityPermission);
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
