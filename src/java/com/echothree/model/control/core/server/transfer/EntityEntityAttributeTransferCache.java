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

import com.echothree.model.control.core.common.transfer.EntityEntityAttributeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.core.server.entity.EntityEntityAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EntityEntityAttributeTransferCache
        extends BaseCoreTransferCache<EntityEntityAttribute, EntityEntityAttributeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

    /** Creates a new instance of EntityEntityAttributeTransferCache */
    public EntityEntityAttributeTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public EntityEntityAttributeTransfer getEntityEntityAttributeTransfer(EntityEntityAttribute entityEntityAttribute, EntityInstance entityInstance) {
        var entityEntityAttributeTransfer = get(entityEntityAttribute);
        
        if(entityEntityAttributeTransfer == null) {
            var entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityEntityAttribute.getEntityAttribute(), entityInstance) : null;
            var entityInstanceTransfer = entityInstanceControl.getEntityInstanceTransfer(userVisit, entityEntityAttribute.getEntityInstance(), false, false, false, false);
            var entityInstanceAttribute = entityInstanceControl.getEntityInstanceTransfer(userVisit, entityEntityAttribute.getEntityInstanceAttribute(), false, false, false, false);
            
            entityEntityAttributeTransfer = new EntityEntityAttributeTransfer(entityAttribute, entityInstanceTransfer, entityInstanceAttribute);
            put(entityEntityAttribute, entityEntityAttributeTransfer);
        }
        
        return entityEntityAttributeTransfer;
    }
    
}
