// --------------------------------------------------------------------------------
// Copyright 2002-2021 Echo Three, LLC
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

package com.echothree.model.control.tag.server.transfer;

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.tag.common.transfer.EntityTagTransfer;
import com.echothree.model.control.tag.common.transfer.TagTransfer;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.tag.server.entity.EntityTag;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class EntityTagTransferCache
        extends BaseTagTransferCache<EntityTag, EntityTagTransfer> {
    
    CoreControl coreControl = Session.getModelController(CoreControl.class);
    
    /** Creates a new instance of EntityTagTransferCache */
    public EntityTagTransferCache(UserVisit userVisit, TagControl tagControl) {
        super(userVisit, tagControl);
    }
    
    public EntityTagTransfer getEntityTagTransfer(EntityTag entityTag) {
        EntityTagTransfer entityTagTransfer = get(entityTag);
        
        if(entityTagTransfer == null) {
            EntityInstanceTransfer taggedEntityInstance = coreControl.getEntityInstanceTransfer(userVisit, entityTag.getTaggedEntityInstance(), false, false, false, false, false);
            TagTransfer tag = tagControl.getTagTransfer(userVisit, entityTag.getTag());
            
            entityTagTransfer = new EntityTagTransfer(taggedEntityInstance, tag);
            put(entityTag, entityTagTransfer);
        }
        
        return entityTagTransfer;
    }
    
}
