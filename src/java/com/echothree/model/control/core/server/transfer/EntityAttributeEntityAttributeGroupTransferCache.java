// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.core.remote.transfer.EntityAttributeEntityAttributeGroupTransfer;
import com.echothree.model.control.core.remote.transfer.EntityAttributeGroupTransfer;
import com.echothree.model.control.core.remote.transfer.EntityAttributeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;

public class EntityAttributeEntityAttributeGroupTransferCache
        extends BaseCoreTransferCache<EntityAttributeEntityAttributeGroup, EntityAttributeEntityAttributeGroupTransfer> {
    
    /** Creates a new instance of EntityAttributeEntityAttributeGroupTransferCache */
    public EntityAttributeEntityAttributeGroupTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public EntityAttributeEntityAttributeGroupTransfer getEntityAttributeEntityAttributeGroupTransfer(EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup, EntityInstance entityInstance) {
        EntityAttributeEntityAttributeGroupTransfer entityAttributeEntityAttributeGroupTransfer = get(entityAttributeEntityAttributeGroup);
        
        if(entityAttributeEntityAttributeGroupTransfer == null) {
            EntityAttributeTransfer entityAttributeTransfer = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityAttributeEntityAttributeGroup.getEntityAttribute(), entityInstance) : null;
            EntityAttributeGroupTransfer entityAttributeGroupTransfer = entityInstance == null ? coreControl.getEntityAttributeGroupTransfer(userVisit, entityAttributeEntityAttributeGroup.getEntityAttributeGroup(), entityInstance) : null;
            Integer sortOrder = entityAttributeEntityAttributeGroup.getSortOrder();
            
            entityAttributeEntityAttributeGroupTransfer = new EntityAttributeEntityAttributeGroupTransfer(entityAttributeTransfer, entityAttributeGroupTransfer, sortOrder);
            put(entityAttributeEntityAttributeGroup, entityAttributeEntityAttributeGroupTransfer);
        }
        return entityAttributeEntityAttributeGroupTransfer;
    }
    
}
