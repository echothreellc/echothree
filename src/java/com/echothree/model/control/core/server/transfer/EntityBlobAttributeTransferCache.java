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

import com.echothree.model.control.core.common.CoreOptions;
import com.echothree.model.control.core.common.transfer.EntityAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityBlobAttributeTransfer;
import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.EntityTimeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.EntityBlobAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class EntityBlobAttributeTransferCache
        extends BaseCoreTransferCache<EntityBlobAttribute, EntityBlobAttributeTransfer> {
    
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    boolean includeBlob;
    boolean includeETag;
    
    /** Creates a new instance of EntityBlobAttributeTransferCache */
    public EntityBlobAttributeTransferCache(UserVisit userVisit, CoreControl coreControl) {
        super(userVisit, coreControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeBlob = options.contains(CoreOptions.EntityBlobAttributeIncludeBlob);
            includeETag = options.contains(CoreOptions.EntityBlobAttributeIncludeETag);
        }
    }
    
    public EntityBlobAttributeTransfer getEntityBlobAttributeTransfer(EntityBlobAttribute entityBlobAttribute, EntityInstance entityInstance) {
        EntityBlobAttributeTransfer entityBlobAttributeTransfer = get(entityBlobAttribute);
        
        if(entityBlobAttributeTransfer == null) {
            EntityAttributeTransfer entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityBlobAttribute.getEntityAttribute(), entityInstance) : null;
            EntityInstanceTransfer entityInstanceTransfer = coreControl.getEntityInstanceTransfer(userVisit, entityBlobAttribute.getEntityInstance(), false, false, false, false, false);
            LanguageTransfer language = partyControl.getLanguageTransfer(userVisit, entityBlobAttribute.getLanguage());
            ByteArray blobAttribute = includeBlob ? entityBlobAttribute.getBlobAttribute() : null;
            MimeTypeTransfer mimeType = coreControl.getMimeTypeTransfer(userVisit, entityBlobAttribute.getMimeType());
            String eTag = null;
            
            if(includeETag) {
                // Item Descriptions do not have their own EntityTime, fall back on the Item's EntityTime.
                EntityTimeTransfer entityTimeTransfer = entityInstanceTransfer.getEntityTime();
                Long modifiedTime = entityTimeTransfer.getUnformattedModifiedTime();
                long maxTime = modifiedTime == null ? entityTimeTransfer.getUnformattedCreatedTime() : modifiedTime;
                long eTagEntityId = entityBlobAttribute.getPrimaryKey().getEntityId();
                int eTagSize = entityBlobAttribute.getBlobAttribute().length();

                // EntityId-Size-ModifiedTime
                eTag = new StringBuilder(Long.toHexString(eTagEntityId)).append('-').append(Integer.toHexString(eTagSize)).append('-').append(Long.toHexString(maxTime)).toString();
            }
            
            entityBlobAttributeTransfer = new EntityBlobAttributeTransfer(entityAttribute, entityInstanceTransfer, language, blobAttribute, mimeType, eTag);
            put(entityBlobAttribute, entityBlobAttributeTransfer);
        }
        
        return entityBlobAttributeTransfer;
    }
    
}
