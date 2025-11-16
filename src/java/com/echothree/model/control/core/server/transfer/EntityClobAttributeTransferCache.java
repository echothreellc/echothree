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

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.EntityClobAttributeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntityClobAttributeTransferCache
        extends BaseCoreTransferCache<EntityClobAttribute, EntityClobAttributeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);

    boolean includeClob;
    boolean includeETag;
    
    /** Creates a new instance of EntityClobAttributeTransferCache */
    protected EntityClobAttributeTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeClob = options.contains(CoreOptions.EntityClobAttributeIncludeClob);
            includeETag = options.contains(CoreOptions.EntityClobAttributeIncludeETag);
        }
    }
    
    public EntityClobAttributeTransfer getEntityClobAttributeTransfer(final UserVisit userVisit, final EntityClobAttribute entityClobAttribute, final EntityInstance entityInstance) {
        var entityClobAttributeTransfer = get(entityClobAttribute);
        
        if(entityClobAttributeTransfer == null) {
            var entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityClobAttribute.getEntityAttribute(), entityInstance) : null;
            var entityInstanceTransfer = entityInstanceControl.getEntityInstanceTransfer(userVisit, entityClobAttribute.getEntityInstance(), false, false, false, false);
            var language = partyControl.getLanguageTransfer(userVisit, entityClobAttribute.getLanguage());
            var clobAttribute = includeClob ? entityClobAttribute.getClobAttribute() : null;
            var mimeType = mimeTypeControl.getMimeTypeTransfer(userVisit, entityClobAttribute.getMimeType());
            String eTag = null;
            
            if(includeETag) {
                // Item Descriptions do not have their own EntityTime, fall back on the Item's EntityTime.
                var entityTimeTransfer = entityInstanceTransfer.getEntityTime();
                var modifiedTime = entityTimeTransfer.getUnformattedModifiedTime();
                long maxTime = modifiedTime == null ? entityTimeTransfer.getUnformattedCreatedTime() : modifiedTime;
                long eTagEntityId = entityClobAttribute.getPrimaryKey().getEntityId();
                var eTagSize = entityClobAttribute.getClobAttribute().length();

                // EntityId-Size-ModifiedTime
                eTag = Long.toHexString(eTagEntityId) + '-' + Integer.toHexString(eTagSize) + '-' + Long.toHexString(maxTime);
            }
            
            entityClobAttributeTransfer = new EntityClobAttributeTransfer(entityAttribute, entityInstanceTransfer, language, clobAttribute, mimeType, eTag);
            put(userVisit, entityClobAttribute, entityClobAttributeTransfer);
        }
        
        return entityClobAttributeTransfer;
    }
    
}
