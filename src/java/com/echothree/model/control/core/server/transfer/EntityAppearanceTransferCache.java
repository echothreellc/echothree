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

import com.echothree.model.control.core.common.transfer.AppearanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityAppearanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityAppearance;
import com.echothree.model.data.user.server.entity.UserVisit;

public class EntityAppearanceTransferCache
        extends BaseCoreTransferCache<EntityAppearance, EntityAppearanceTransfer> {
    
    /** Creates a new instance of EntityAppearanceTransferCache */
    public EntityAppearanceTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
    }
    
    public EntityAppearanceTransfer getEntityAppearanceTransfer(EntityAppearance entityAppearance) {
        EntityAppearanceTransfer entityAppearanceTransfer = get(entityAppearance);
        
        if(entityAppearanceTransfer == null) {
            EntityInstanceTransfer entityInstance = coreControl.getEntityInstanceTransfer(userVisit, entityAppearance.getEntityInstance(), false, false, false, false, false);
            AppearanceTransfer appearance = coreControl.getAppearanceTransfer(userVisit, entityAppearance.getAppearance());
            
            entityAppearanceTransfer = new EntityAppearanceTransfer(entityInstance, appearance);
            put(entityAppearance, entityAppearanceTransfer);
        }
        
        return entityAppearanceTransfer;
    }
    
}
