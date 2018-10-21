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

import com.echothree.model.control.core.remote.transfer.EntityAttributeTransfer;
import com.echothree.model.control.core.remote.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.remote.transfer.EntityListItemAttributeTransfer;
import com.echothree.model.control.core.remote.transfer.EntityListItemTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import com.echothree.model.data.user.server.entity.UserVisit;

public class EntityListItemAttributeTransferCache
        extends BaseCoreTransferCache<EntityListItemAttribute, EntityListItemAttributeTransfer> {
    
    /** Creates a new instance of EntityListItemAttributeTransferCache */
    public EntityListItemAttributeTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public EntityListItemAttributeTransfer getEntityListItemAttributeTransfer(EntityListItemAttribute entityListItemAttribute, EntityInstance entityInstance) {
        EntityListItemAttributeTransfer entityListItemAttributeTransfer = get(entityListItemAttribute);
        
        if(entityListItemAttributeTransfer == null) {
            EntityAttributeTransfer entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityListItemAttribute.getEntityAttribute(), entityInstance) : null;
            EntityInstanceTransfer entityInstanceTransfer = coreControl.getEntityInstanceTransfer(userVisit, entityListItemAttribute.getEntityInstance(), false, false, false, false, false);
            EntityListItemTransfer entityListItem = coreControl.getEntityListItemTransfer(userVisit, entityListItemAttribute.getEntityListItem(), entityInstance);
            
            entityListItemAttributeTransfer = new EntityListItemAttributeTransfer(entityAttribute, entityInstanceTransfer, entityListItem);
            put(entityListItemAttribute, entityListItemAttributeTransfer);
        }
        
        return entityListItemAttributeTransfer;
    }
    
}
