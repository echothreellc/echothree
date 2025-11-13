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

package com.echothree.model.control.core.server.transfer;

import com.echothree.model.control.core.common.transfer.EntityAttributeEntityTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityType;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EntityAttributeEntityTypeTransferCache
        extends BaseCoreTransferCache<EntityAttributeEntityType, EntityAttributeEntityTypeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);

    /** Creates a new instance of EntityAttributeEntityTypeTransferCache */
    public EntityAttributeEntityTypeTransferCache() {
        super();
    }
    
    public EntityAttributeEntityTypeTransfer getEntityAttributeEntityTypeTransfer(EntityAttributeEntityType entityAttributeEntityType, EntityInstance entityInstance) {
        var entityAttributeEntityTypeTransfer = get(entityAttributeEntityType);
        
        if(entityAttributeEntityTypeTransfer == null) {
            var entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityAttributeEntityType.getEntityAttribute(), entityInstance) : null;
            var allowedEntityType = entityTypeControl.getEntityTypeTransfer(userVisit, entityAttributeEntityType.getAllowedEntityType());
            
            entityAttributeEntityTypeTransfer = new EntityAttributeEntityTypeTransfer(entityAttribute, allowedEntityType);
            put(userVisit, entityAttributeEntityType, entityAttributeEntityTypeTransfer);
        }
        
        return entityAttributeEntityTypeTransfer;
    }
    
}
