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

import com.echothree.model.control.tag.common.transfer.TagScopeDescriptionTransfer;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.tag.server.entity.TagScopeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class TagScopeDescriptionTransferCache
        extends BaseTagDescriptionTransferCache<TagScopeDescription, TagScopeDescriptionTransfer> {

    TagControl tagControl = Session.getModelController(TagControl.class);

    /** Creates a new instance of TagScopeDescriptionTransferCache */
    public TagScopeDescriptionTransferCache() {
        super();
    }
    
    public TagScopeDescriptionTransfer getTagScopeDescriptionTransfer(TagScopeDescription tagScopeDescription) {
        var tagScopeDescriptionTransfer = get(tagScopeDescription);
        
        if(tagScopeDescriptionTransfer == null) {
            var tagScopeTransfer = tagControl.getTagScopeTransfer(userVisit, tagScopeDescription.getTagScope());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, tagScopeDescription.getLanguage());
            
            tagScopeDescriptionTransfer = new TagScopeDescriptionTransfer(languageTransfer, tagScopeTransfer, tagScopeDescription.getDescription());
            put(userVisit, tagScopeDescription, tagScopeDescriptionTransfer);
        }
        
        return tagScopeDescriptionTransfer;
    }
    
}
