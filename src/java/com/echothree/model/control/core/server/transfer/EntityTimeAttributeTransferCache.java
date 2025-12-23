// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.model.control.core.common.transfer.EntityTimeAttributeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityTimeAttribute;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntityTimeAttributeTransferCache
        extends BaseCoreTransferCache<EntityTimeAttribute, EntityTimeAttributeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

    /** Creates a new instance of EntityTimeAttributeTransferCache */
    protected EntityTimeAttributeTransferCache() {
        super();
    }
    
    public EntityTimeAttributeTransfer getEntityTimeAttributeTransfer(final UserVisit userVisit, final EntityTimeAttribute entityTimeAttribute, final EntityInstance entityInstance) {
        var entityTimeAttributeTransfer = get(entityTimeAttribute);
        
        if(entityTimeAttributeTransfer == null) {
            var entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityTimeAttribute.getEntityAttribute(), entityInstance) : null;
            var entityInstanceTransfer = entityInstanceControl.getEntityInstanceTransfer(userVisit, entityTimeAttribute.getEntityInstance(), false, false, false, false);
            var unformattedTimeAttribute = entityTimeAttribute.getTimeAttribute();
            var timeAttribute = formatTypicalDateTime(userVisit, unformattedTimeAttribute);
            
            entityTimeAttributeTransfer = new EntityTimeAttributeTransfer(entityAttribute, entityInstanceTransfer, timeAttribute, unformattedTimeAttribute);
            put(userVisit, entityTimeAttribute, entityTimeAttributeTransfer);
        }
        
        return entityTimeAttributeTransfer;
    }
    
}
