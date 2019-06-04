// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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

import com.echothree.model.control.core.common.exception.DuplicateEntityAttributeGroupNameException;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.sequence.common.SequenceConstants;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.data.core.server.entity.EntityAttributeGroup;
import com.echothree.model.data.party.server.entity.Language;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.util.common.message.ExecutionErrors;
import com.echothree.util.common.persistence.BasePK;
import com.echothree.util.server.control.BaseLogic;
import com.echothree.util.server.message.ExecutionErrorAccumulator;
import com.echothree.util.server.persistence.Session;

public class EntityAttributeGroupLogic
        extends BaseLogic {
    
    private EntityAttributeGroupLogic() {
        super();
    }
    
    private static class EntityAttributeGroupLogicHolder {
        static EntityAttributeGroupLogic instance = new EntityAttributeGroupLogic();
    }
    
    public static EntityAttributeGroupLogic getInstance() {
        return EntityAttributeGroupLogicHolder.instance;
    }
    
    public EntityAttributeGroup createEntityAttributeGroup(final ExecutionErrorAccumulator eea, String entityAttributeGroupName,
            final Boolean isDefault, final Integer sortOrder, final String description, final Language language, final BasePK createdBy) {
        var coreControl = (CoreControl)Session.getModelController(CoreControl.class);
        
        if(entityAttributeGroupName == null) {
            var sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
            Sequence sequence = sequenceControl.getDefaultSequenceUsingNames(SequenceConstants.SequenceType_ENTITY_ATTRIBUTE_GROUP);
            
            entityAttributeGroupName = sequenceControl.getNextSequenceValue(sequence);
        }
        
        EntityAttributeGroup entityAttributeGroup = coreControl.getEntityAttributeGroupByName(entityAttributeGroupName);
        
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
    
}
