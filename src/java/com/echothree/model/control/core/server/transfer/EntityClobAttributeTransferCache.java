// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.model.control.core.common.transfer.EntityClobAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.EntityClobAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class EntityClobAttributeTransferCache
        extends BaseCoreTransferCache<EntityClobAttribute, EntityClobAttributeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);

    boolean includeClob;
    boolean includeETag;
    
    /** Creates a new instance of EntityClobAttributeTransferCache */
    public EntityClobAttributeTransferCache(UserVisit userVisit) {
        super(userVisit);
        
        var options = session.getOptions();
        if(options != null) {
            includeClob = options.contains(CoreOptions.EntityClobAttributeIncludeClob);
            includeETag = options.contains(CoreOptions.EntityClobAttributeIncludeETag);
        }
    }
    
    public EntityClobAttributeTransfer getEntityClobAttributeTransfer(EntityClobAttribute entityClobAttribute, EntityInstance entityInstance) {
        EntityClobAttributeTransfer entityClobAttributeTransfer = get(entityClobAttribute);
        
        if(entityClobAttributeTransfer == null) {
            EntityAttributeTransfer entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityClobAttribute.getEntityAttribute(), entityInstance) : null;
            EntityInstanceTransfer entityInstanceTransfer = coreControl.getEntityInstanceTransfer(userVisit, entityClobAttribute.getEntityInstance(), false, false, false, false, false, false);
            LanguageTransfer language = partyControl.getLanguageTransfer(userVisit, entityClobAttribute.getLanguage());
            String clobAttribute = includeClob ? entityClobAttribute.getClobAttribute() : null;
            MimeTypeTransfer mimeType = coreControl.getMimeTypeTransfer(userVisit, entityClobAttribute.getMimeType());
            String eTag = null;
            
            if(includeETag) {
                // Item Descriptions do not have their own EntityTime, fall back on the Item's EntityTime.
                EntityTimeTransfer entityTimeTransfer = entityInstanceTransfer.getEntityTime();
                Long modifiedTime = entityTimeTransfer.getUnformattedModifiedTime();
                long maxTime = modifiedTime == null ? entityTimeTransfer.getUnformattedCreatedTime() : modifiedTime;
                long eTagEntityId = entityClobAttribute.getPrimaryKey().getEntityId();
                int eTagSize = entityClobAttribute.getClobAttribute().length();

                // EntityId-Size-ModifiedTime
                eTag = new StringBuilder(Long.toHexString(eTagEntityId)).append('-').append(Integer.toHexString(eTagSize)).append('-').append(Long.toHexString(maxTime)).toString();
            }
            
            entityClobAttributeTransfer = new EntityClobAttributeTransfer(entityAttribute, entityInstanceTransfer, language, clobAttribute, mimeType, eTag);
            put(entityClobAttribute, entityClobAttributeTransfer);
        }
        
        return entityClobAttributeTransfer;
    }
    
}
