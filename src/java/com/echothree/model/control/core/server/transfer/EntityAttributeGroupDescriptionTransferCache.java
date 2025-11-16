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

import com.echothree.model.control.core.common.transfer.EntityAttributeGroupDescriptionTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.data.core.server.entity.EntityAttributeGroupDescription;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntityAttributeGroupDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<EntityAttributeGroupDescription, EntityAttributeGroupDescriptionTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);

    /** Creates a new instance of EntityAttributeGroupDescriptionTransferCache */
    public EntityAttributeGroupDescriptionTransferCache() {
        super();
    }
    
    public EntityAttributeGroupDescriptionTransfer getEntityAttributeGroupDescriptionTransfer(final UserVisit userVisit, final EntityAttributeGroupDescription entityAttributeGroupDescription, final EntityInstance entityInstance) {
        var entityAttributeGroupDescriptionTransfer = get(entityAttributeGroupDescription);
        
        if(entityAttributeGroupDescriptionTransfer == null) {
            var entityAttributeGroupTransfer = entityInstance == null ? coreControl.getEntityAttributeGroupTransfer(userVisit, entityAttributeGroupDescription.getEntityAttributeGroup(), entityInstance) : null;
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, entityAttributeGroupDescription.getLanguage());
            
            entityAttributeGroupDescriptionTransfer = new EntityAttributeGroupDescriptionTransfer(languageTransfer, entityAttributeGroupTransfer, entityAttributeGroupDescription.getDescription());
            put(userVisit, entityAttributeGroupDescription, entityAttributeGroupDescriptionTransfer);
        }
        
        return entityAttributeGroupDescriptionTransfer;
    }
    
}
