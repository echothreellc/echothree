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

import com.echothree.model.control.core.common.transfer.EntityAppearanceTransfer;
import com.echothree.model.control.core.server.control.AppearanceControl;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityAppearance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EntityAppearanceTransferCache
        extends BaseCoreTransferCache<EntityAppearance, EntityAppearanceTransfer> {

    AppearanceControl appearanceControl = Session.getModelController(AppearanceControl.class);
    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of EntityAppearanceTransferCache */
    public EntityAppearanceTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public EntityAppearanceTransfer getEntityAppearanceTransfer(EntityAppearance entityAppearance) {
        var entityAppearanceTransfer = get(entityAppearance);
        
        if(entityAppearanceTransfer == null) {
            var entityInstance = coreControl.getEntityInstanceTransfer(userVisit, entityAppearance.getEntityInstance(), false, false, false, false);
            var appearance = appearanceControl.getAppearanceTransfer(userVisit, entityAppearance.getAppearance());
            
            entityAppearanceTransfer = new EntityAppearanceTransfer(entityInstance, appearance);
            put(entityAppearance, entityAppearanceTransfer);
        }
        
        return entityAppearanceTransfer;
    }
    
}
