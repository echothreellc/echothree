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
import com.echothree.model.control.core.common.transfer.EntityDateAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.data.core.server.entity.EntityDateAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.string.DateUtils;

public class EntityDateAttributeTransferCache
        extends BaseCoreTransferCache<EntityDateAttribute, EntityDateAttributeTransfer> {
    
    /** Creates a new instance of EntityDateAttributeTransferCache */
    public EntityDateAttributeTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public EntityDateAttributeTransfer getEntityDateAttributeTransfer(EntityDateAttribute entityDateAttribute, EntityInstance entityInstance) {
        EntityDateAttributeTransfer entityDateAttributeTransfer = get(entityDateAttribute);
        
        if(entityDateAttributeTransfer == null) {
            EntityAttributeTransfer entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityDateAttribute.getEntityAttribute(), entityInstance) : null;
            EntityInstanceTransfer entityInstanceTransfer = coreControl.getEntityInstanceTransfer(userVisit, entityDateAttribute.getEntityInstance(), false, false, false, false, false);
            Integer unformattedDateAttribute = entityDateAttribute.getDateAttribute();
            String dateAttribute = DateUtils.getInstance().formatDate(userVisit, unformattedDateAttribute);
            
            entityDateAttributeTransfer = new EntityDateAttributeTransfer(entityAttribute, entityInstanceTransfer, dateAttribute, unformattedDateAttribute);
            put(entityDateAttribute, entityDateAttributeTransfer);
        }
        
        return entityDateAttributeTransfer;
    }
    
}
