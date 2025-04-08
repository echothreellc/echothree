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

import com.echothree.model.control.core.common.transfer.EntityTypeDescriptionTransfer;
import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.data.core.server.entity.EntityTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EntityTypeDescriptionTransferCache
        extends BaseCoreDescriptionTransferCache<EntityTypeDescription, EntityTypeDescriptionTransfer> {

    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);

    /** Creates a new instance of EntityTypeDescriptionTransferCache */
    public EntityTypeDescriptionTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public EntityTypeDescriptionTransfer getEntityTypeDescriptionTransfer(EntityTypeDescription entityTypeDescription) {
        var entityTypeDescriptionTransfer = get(entityTypeDescription);
        
        if(entityTypeDescriptionTransfer == null) {
            var entityTypeTransfer = entityTypeControl.getEntityTypeTransfer(userVisit, entityTypeDescription.getEntityType());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, entityTypeDescription.getLanguage());
            
            entityTypeDescriptionTransfer = new EntityTypeDescriptionTransfer(languageTransfer, entityTypeTransfer,
                    entityTypeDescription.getDescription());
            put(entityTypeDescription, entityTypeDescriptionTransfer);
        }
        return entityTypeDescriptionTransfer;
    }
    
}
