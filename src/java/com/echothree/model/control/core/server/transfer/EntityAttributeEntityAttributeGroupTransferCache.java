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

import com.echothree.model.control.core.common.transfer.EntityAttributeEntityAttributeGroupTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityAttributeEntityAttributeGroup;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntityAttributeEntityAttributeGroupTransferCache
        extends BaseCoreTransferCache<EntityAttributeEntityAttributeGroup, EntityAttributeEntityAttributeGroupTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of EntityAttributeEntityAttributeGroupTransferCache */
    public EntityAttributeEntityAttributeGroupTransferCache() {
        super();
    }
    
    public EntityAttributeEntityAttributeGroupTransfer getEntityAttributeEntityAttributeGroupTransfer(final UserVisit userVisit, final EntityAttributeEntityAttributeGroup entityAttributeEntityAttributeGroup, final EntityInstance entityInstance) {
        var entityAttributeEntityAttributeGroupTransfer = get(entityAttributeEntityAttributeGroup);
        
        if(entityAttributeEntityAttributeGroupTransfer == null) {
            var entityAttributeTransfer = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityAttributeEntityAttributeGroup.getEntityAttribute(), entityInstance) : null;
            var entityAttributeGroupTransfer = entityInstance == null ? coreControl.getEntityAttributeGroupTransfer(userVisit, entityAttributeEntityAttributeGroup.getEntityAttributeGroup(), entityInstance) : null;
            var sortOrder = entityAttributeEntityAttributeGroup.getSortOrder();
            
            entityAttributeEntityAttributeGroupTransfer = new EntityAttributeEntityAttributeGroupTransfer(entityAttributeTransfer, entityAttributeGroupTransfer, sortOrder);
            put(userVisit, entityAttributeEntityAttributeGroup, entityAttributeEntityAttributeGroupTransfer);
        }
        return entityAttributeEntityAttributeGroupTransfer;
    }
    
}
