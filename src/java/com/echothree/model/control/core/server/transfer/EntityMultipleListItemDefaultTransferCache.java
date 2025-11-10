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

import com.echothree.model.control.core.common.transfer.EntityMultipleListItemDefaultTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityMultipleListItemDefault;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EntityMultipleListItemDefaultTransferCache
        extends BaseCoreTransferCache<EntityMultipleListItemDefault, EntityMultipleListItemDefaultTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of EntityMultipleListItemDefaultTransferCache */
    public EntityMultipleListItemDefaultTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public EntityMultipleListItemDefaultTransfer getEntityMultipleListItemDefaultTransfer(EntityMultipleListItemDefault entityMultipleListItemDefault) {
        var entityMultipleListItemDefaultTransfer = get(entityMultipleListItemDefault);
        
        if(entityMultipleListItemDefaultTransfer == null) {
            var entityAttribute = coreControl.getEntityAttributeTransfer(userVisit, entityMultipleListItemDefault.getEntityAttribute(), null);
            var entityListItem = coreControl.getEntityListItemTransfer(userVisit, entityMultipleListItemDefault.getEntityListItem(), null);
            
            entityMultipleListItemDefaultTransfer = new EntityMultipleListItemDefaultTransfer(entityAttribute, entityListItem);
            put(userVisit, entityMultipleListItemDefault, entityMultipleListItemDefaultTransfer);
        }
        
        return entityMultipleListItemDefaultTransfer;
    }
    
}
