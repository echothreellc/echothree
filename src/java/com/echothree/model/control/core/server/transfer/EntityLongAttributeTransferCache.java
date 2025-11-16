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

import com.echothree.model.control.core.common.transfer.EntityLongAttributeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityLongAttribute;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntityLongAttributeTransferCache
        extends BaseCoreTransferCache<EntityLongAttribute, EntityLongAttributeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

    /** Creates a new instance of EntityLongAttributeTransferCache */
    protected EntityLongAttributeTransferCache() {
        super();
    }
    
    public EntityLongAttributeTransfer getEntityLongAttributeTransfer(final UserVisit userVisit, final EntityLongAttribute entityLongAttribute, final EntityInstance entityInstance) {
        var entityLongAttributeTransfer = get(entityLongAttribute);
        
        if(entityLongAttributeTransfer == null) {
            var entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityLongAttribute.getEntityAttribute(), entityInstance) : null;
            var entityInstanceTransfer = entityInstanceControl.getEntityInstanceTransfer(userVisit, entityLongAttribute.getEntityInstance(), false, false, false, false);
            var longAttribute = entityLongAttribute.getLongAttribute();
            
            entityLongAttributeTransfer = new EntityLongAttributeTransfer(entityAttribute, entityInstanceTransfer, longAttribute);
            put(userVisit, entityLongAttribute, entityLongAttributeTransfer);
        }
        
        return entityLongAttributeTransfer;
    }
    
}
