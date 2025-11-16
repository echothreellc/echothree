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

import com.echothree.model.control.core.common.transfer.EntityAliasTypeDescriptionTransfer;
import com.echothree.model.control.core.server.control.EntityAliasControl;
import com.echothree.model.data.core.server.entity.EntityAliasTypeDescription;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntityAliasTypeDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<EntityAliasTypeDescription, EntityAliasTypeDescriptionTransfer> {

    EntityAliasControl entityAliasControl = Session.getModelController(EntityAliasControl.class);

    /** Creates a new instance of EntityAliasTypeDescriptionTransferCache */
    protected EntityAliasTypeDescriptionTransferCache() {
        super();
    }
    
    public EntityAliasTypeDescriptionTransfer getEntityAliasTypeDescriptionTransfer(final UserVisit userVisit, final EntityAliasTypeDescription entityAliasTypeDescription, final EntityInstance entityInstance) {
        var entityAliasTypeDescriptionTransfer = get(entityAliasTypeDescription);
        
        if(entityAliasTypeDescriptionTransfer == null) {
            var entityAliasTypeTransfer = entityInstance == null ? entityAliasControl.getEntityAliasTypeTransfer(userVisit, entityAliasTypeDescription.getEntityAliasType(), entityInstance) : null;
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, entityAliasTypeDescription.getLanguage());
            
            entityAliasTypeDescriptionTransfer = new EntityAliasTypeDescriptionTransfer(languageTransfer, entityAliasTypeTransfer, entityAliasTypeDescription.getDescription());
            put(userVisit, entityAliasTypeDescription, entityAliasTypeDescriptionTransfer);
        }
        return entityAliasTypeDescriptionTransfer;
    }
    
}
