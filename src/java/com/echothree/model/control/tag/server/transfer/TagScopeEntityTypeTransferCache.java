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

package com.echothree.model.control.tag.server.transfer;

import com.echothree.model.control.core.server.control.EntityTypeControl;
import com.echothree.model.control.tag.common.transfer.TagScopeEntityTypeTransfer;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.tag.server.entity.TagScopeEntityType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TagScopeEntityTypeTransferCache
        extends BaseTagTransferCache<TagScopeEntityType, TagScopeEntityTypeTransfer> {

    EntityTypeControl entityTypeControl = Session.getModelController(EntityTypeControl.class);
    TagControl tagControl = Session.getModelController(TagControl.class);

    /** Creates a new instance of TagScopeEntityTypeTransferCache */
    public TagScopeEntityTypeTransferCache(UserVisit userVisit) {
        super(userVisit);
    }
    
    public TagScopeEntityTypeTransfer getTagScopeEntityTypeTransfer(TagScopeEntityType tagScopeEntityType) {
        var tagScopeEntityTypeTransfer = get(tagScopeEntityType);
        
        if(tagScopeEntityTypeTransfer == null) {
            var tagScope = tagControl.getTagScopeTransfer(userVisit, tagScopeEntityType.getTagScope());
            var entityType = entityTypeControl.getEntityTypeTransfer(userVisit, tagScopeEntityType.getEntityType());
            
            tagScopeEntityTypeTransfer = new TagScopeEntityTypeTransfer(tagScope, entityType);
            put(userVisit, tagScopeEntityType, tagScopeEntityTypeTransfer);
        }
        
        return tagScopeEntityTypeTransfer;
    }
    
}
