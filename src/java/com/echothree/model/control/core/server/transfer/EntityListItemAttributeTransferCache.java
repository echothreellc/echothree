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

import com.echothree.model.control.core.common.transfer.EntityListItemAttributeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityListItemAttribute;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntityListItemAttributeTransferCache
        extends BaseCoreTransferCache<EntityListItemAttribute, EntityListItemAttributeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

    /** Creates a new instance of EntityListItemAttributeTransferCache */
    public EntityListItemAttributeTransferCache() {
        super();
    }
    
    public EntityListItemAttributeTransfer getEntityListItemAttributeTransfer(final UserVisit userVisit, final EntityListItemAttribute entityListItemAttribute, final EntityInstance entityInstance) {
        var entityListItemAttributeTransfer = get(entityListItemAttribute);
        
        if(entityListItemAttributeTransfer == null) {
            var entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityListItemAttribute.getEntityAttribute(), entityInstance) : null;
            var entityInstanceTransfer = entityInstanceControl.getEntityInstanceTransfer(userVisit, entityListItemAttribute.getEntityInstance(), false, false, false, false);
            var entityListItem = coreControl.getEntityListItemTransfer(userVisit, entityListItemAttribute.getEntityListItem(), entityInstance);
            
            entityListItemAttributeTransfer = new EntityListItemAttributeTransfer(entityAttribute, entityInstanceTransfer, entityListItem);
            put(userVisit, entityListItemAttribute, entityListItemAttributeTransfer);
        }
        
        return entityListItemAttributeTransfer;
    }
    
}
