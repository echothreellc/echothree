// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.EntityAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityStringAttributeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class EntityStringAttributeTransferCache
        extends BaseCoreTransferCache<EntityStringAttribute, EntityStringAttributeTransfer> {
    
    PartyControl partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    boolean includeString;
    
    /** Creates a new instance of EntityStringAttributeTransferCache */
    public EntityStringAttributeTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeString = options.contains(CoreOptions.EntityStringAttributeIncludeString);
        }
    }
    
    public EntityStringAttributeTransfer getEntityStringAttributeTransfer(EntityStringAttribute entityStringAttribute, EntityInstance entityInstance) {
        EntityStringAttributeTransfer entityStringAttributeTransfer = get(entityStringAttribute);
        
        if(entityStringAttributeTransfer == null) {
            EntityAttributeTransfer entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityStringAttribute.getEntityAttribute(), entityInstance) : null;
            EntityInstanceTransfer entityInstanceTransfer = coreControl.getEntityInstanceTransfer(userVisit, entityStringAttribute.getEntityInstance(), false, false, false, false, false);
            LanguageTransfer language = partyControl.getLanguageTransfer(userVisit, entityStringAttribute.getLanguage());
            String stringAttribute = includeString ? entityStringAttribute.getStringAttribute() : null;
            
            entityStringAttributeTransfer = new EntityStringAttributeTransfer(entityAttribute, entityInstanceTransfer, language, stringAttribute);
            put(entityStringAttribute, entityStringAttributeTransfer);
        }
        
        return entityStringAttributeTransfer;
    }
    
}
