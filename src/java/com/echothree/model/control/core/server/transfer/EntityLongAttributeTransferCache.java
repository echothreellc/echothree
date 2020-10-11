// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.EntityAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityLongAttributeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityLongAttribute;
import com.echothree.model.data.user.server.entity.UserVisit;

public class EntityLongAttributeTransferCache
        extends BaseCoreTransferCache<EntityLongAttribute, EntityLongAttributeTransfer> {
    
    /** Creates a new instance of EntityLongAttributeTransferCache */
    public EntityLongAttributeTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public EntityLongAttributeTransfer getEntityLongAttributeTransfer(EntityLongAttribute entityLongAttribute, EntityInstance entityInstance) {
        EntityLongAttributeTransfer entityLongAttributeTransfer = get(entityLongAttribute);
        
        if(entityLongAttributeTransfer == null) {
            EntityAttributeTransfer entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityLongAttribute.getEntityAttribute(), entityInstance) : null;
            EntityInstanceTransfer entityInstanceTransfer = coreControl.getEntityInstanceTransfer(userVisit, entityLongAttribute.getEntityInstance(), false, false, false, false, false);
            Long longAttribute = entityLongAttribute.getLongAttribute();
            
            entityLongAttributeTransfer = new EntityLongAttributeTransfer(entityAttribute, entityInstanceTransfer, longAttribute);
            put(entityLongAttribute, entityLongAttributeTransfer);
        }
        
        return entityLongAttributeTransfer;
    }
    
}
