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
import com.echothree.model.control.core.common.transfer.EntityNameAttributeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityNameAttribute;
import com.echothree.model.data.user.server.entity.UserVisit;

public class EntityNameAttributeTransferCache
        extends BaseCoreTransferCache<EntityNameAttribute, EntityNameAttributeTransfer> {
    
    /** Creates a new instance of EntityNameAttributeTransferCache */
    public EntityNameAttributeTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public EntityNameAttributeTransfer getEntityNameAttributeTransfer(EntityNameAttribute entityNameAttribute, EntityInstance entityInstance) {
        EntityNameAttributeTransfer entityNameAttributeTransfer = get(entityNameAttribute);
        
        if(entityNameAttributeTransfer == null) {
            EntityAttributeTransfer entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityNameAttribute.getEntityAttribute(), entityInstance) : null;
            String nameAttribute = entityNameAttribute.getNameAttribute();
            EntityInstanceTransfer entityInstanceTransfer = coreControl.getEntityInstanceTransfer(userVisit, entityNameAttribute.getEntityInstance(), false, false, false, false, false);
            
            entityNameAttributeTransfer = new EntityNameAttributeTransfer(entityAttribute, nameAttribute, entityInstanceTransfer);
            put(entityNameAttribute, entityNameAttributeTransfer);
        }
        
        return entityNameAttributeTransfer;
    }
    
}
