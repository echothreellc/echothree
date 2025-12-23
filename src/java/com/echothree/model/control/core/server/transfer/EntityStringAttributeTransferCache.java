// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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
import com.echothree.model.control.core.common.transfer.EntityStringAttributeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.core.server.entity.EntityStringAttribute;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntityStringAttributeTransferCache
        extends BaseCoreTransferCache<EntityStringAttribute, EntityStringAttributeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    boolean includeString;
    
    /** Creates a new instance of EntityStringAttributeTransferCache */
    protected EntityStringAttributeTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeString = options.contains(CoreOptions.EntityStringAttributeIncludeString);
        }
    }
    
    public EntityStringAttributeTransfer getEntityStringAttributeTransfer(final UserVisit userVisit, final EntityStringAttribute entityStringAttribute, final EntityInstance entityInstance) {
        var entityStringAttributeTransfer = get(entityStringAttribute);
        
        if(entityStringAttributeTransfer == null) {
            var entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityStringAttribute.getEntityAttribute(), entityInstance) : null;
            var entityInstanceTransfer = entityInstanceControl.getEntityInstanceTransfer(userVisit, entityStringAttribute.getEntityInstance(), false, false, false, false);
            var language = partyControl.getLanguageTransfer(userVisit, entityStringAttribute.getLanguage());
            var stringAttribute = includeString ? entityStringAttribute.getStringAttribute() : null;
            
            entityStringAttributeTransfer = new EntityStringAttributeTransfer(entityAttribute, entityInstanceTransfer, language, stringAttribute);
            put(userVisit, entityStringAttribute, entityStringAttributeTransfer);
        }
        
        return entityStringAttributeTransfer;
    }
    
}
