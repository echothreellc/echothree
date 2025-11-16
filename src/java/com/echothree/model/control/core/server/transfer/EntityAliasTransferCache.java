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

import com.echothree.model.control.core.common.transfer.EntityAliasTransfer;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.data.core.server.entity.EntityAlias;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntityAliasTransferCache
        extends BaseCoreTransferCache<EntityAlias, EntityAliasTransfer> {

    EntityAliasControl entityAliasControl = Session.getModelController(EntityAliasControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);

    /** Creates a new instance of EntityAliasTransferCache */
    protected EntityAliasTransferCache() {
        super();
    }
    
    public EntityAliasTransfer getEntityAliasTransfer(UserVisit userVisit, EntityAlias entityAlias) {
        var entityAliasTransfer = get(entityAlias);
        
        if(entityAliasTransfer == null) {
            var entityInstance = entityAlias.getEntityInstance();
            var entityAliasType = entityAliasControl.getEntityAliasTypeTransfer(userVisit, entityAlias.getEntityAliasType(), entityInstance);
            var alias = entityAlias.getAlias();
            var entityInstanceTransfer = entityInstanceControl.getEntityInstanceTransfer(userVisit, entityAlias.getEntityInstance(), false, false, false, false);
            
            entityAliasTransfer = new EntityAliasTransfer(entityAliasType, alias, entityInstanceTransfer);
            put(userVisit, entityAlias, entityAliasTransfer);
        }
        
        return entityAliasTransfer;
    }
    
}
