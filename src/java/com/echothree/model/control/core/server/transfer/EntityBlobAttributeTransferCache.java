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
import com.echothree.model.control.core.common.transfer.EntityBlobAttributeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.core.server.control.EntityInstanceControl;
import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.core.server.entity.EntityBlobAttribute;
import com.echothree.model.data.core.server.entity.EntityInstance;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EntityBlobAttributeTransferCache
        extends BaseCoreTransferCache<EntityBlobAttribute, EntityBlobAttributeTransfer> {

    CoreControl coreControl = Session.getModelController(CoreControl.class);
    EntityInstanceControl entityInstanceControl = Session.getModelController(EntityInstanceControl.class);
    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);

    boolean includeBlob;
    boolean includeETag;
    
    /** Creates a new instance of EntityBlobAttributeTransferCache */
    public EntityBlobAttributeTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeBlob = options.contains(CoreOptions.EntityBlobAttributeIncludeBlob);
            includeETag = options.contains(CoreOptions.EntityBlobAttributeIncludeETag);
        }
    }
    
    public EntityBlobAttributeTransfer getEntityBlobAttributeTransfer(final UserVisit userVisit, final EntityBlobAttribute entityBlobAttribute, final EntityInstance entityInstance) {
        var entityBlobAttributeTransfer = get(entityBlobAttribute);
        
        if(entityBlobAttributeTransfer == null) {
            var entityAttribute = entityInstance == null ? coreControl.getEntityAttributeTransfer(userVisit, entityBlobAttribute.getEntityAttribute(), entityInstance) : null;
            var entityInstanceTransfer = entityInstanceControl.getEntityInstanceTransfer(userVisit, entityBlobAttribute.getEntityInstance(), false, false, false, false);
            var language = partyControl.getLanguageTransfer(userVisit, entityBlobAttribute.getLanguage());
            var blobAttribute = includeBlob ? entityBlobAttribute.getBlobAttribute() : null;
            var mimeType = mimeTypeControl.getMimeTypeTransfer(userVisit, entityBlobAttribute.getMimeType());
            String eTag = null;
            
            if(includeETag) {
                // Item Descriptions do not have their own EntityTime, fall back on the Item's EntityTime.
                var entityTimeTransfer = entityInstanceTransfer.getEntityTime();
                var modifiedTime = entityTimeTransfer.getUnformattedModifiedTime();
                long maxTime = modifiedTime == null ? entityTimeTransfer.getUnformattedCreatedTime() : modifiedTime;
                long eTagEntityId = entityBlobAttribute.getPrimaryKey().getEntityId();
                var eTagSize = entityBlobAttribute.getBlobAttribute().length();

                // EntityId-Size-ModifiedTime
                eTag = Long.toHexString(eTagEntityId) + '-' + Integer.toHexString(eTagSize) + '-' + Long.toHexString(maxTime);
            }
            
            entityBlobAttributeTransfer = new EntityBlobAttributeTransfer(entityAttribute, entityInstanceTransfer, language, blobAttribute, mimeType, eTag);
            put(userVisit, entityBlobAttribute, entityBlobAttributeTransfer);
        }
        
        return entityBlobAttributeTransfer;
    }
    
}
