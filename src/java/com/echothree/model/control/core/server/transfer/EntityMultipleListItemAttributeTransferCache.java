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

import com.echothree.model.control.core.common.transfer.EntityMultipleListItemAttributeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemAttribute;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EntityMultipleListItemAttributeTransferCache
        extends BaseCoreTransferCache<EntityMultipleListItemAttribute, EntityMultipleListItemAttributeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

    /** Creates a new instance of EntityMultipleListItemAttributeTransferCache */
    public EntityMultipleListItemAttributeTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public EntityMultipleListItemAttributeTransfer getEntityMultipleListItemAttributeTransfer(EntityMultipleListItemAttribute entityMultipleListItemAttribute, EntityInstance entityInstance) {
        var entityMultipleListItemAttributeTransfer = get(entityMultipleListItemAttribute);
        
        if(entityMultipleListItemAttributeTransfer == null) {
            var entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityMultipleListItemAttribute.getEntityAttribute(), entityInstance) : null;
            var entityInstanceTransfer = entityInstanceControl.getEntityInstanceTransfer(userVisit, entityMultipleListItemAttribute.getEntityInstance(), false, false, false, false);
            var entityListItem = coreControl.getEntityListItemTransfer(userVisit, entityMultipleListItemAttribute.getEntityListItem(), entityInstance);
            
            entityMultipleListItemAttributeTransfer = new EntityMultipleListItemAttributeTransfer(entityAttribute, entityInstanceTransfer, entityListItem);
            put(entityMultipleListItemAttribute, entityMultipleListItemAttributeTransfer);
        }
        
        return entityMultipleListItemAttributeTransfer;
    }
    
}
