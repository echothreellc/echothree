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

import com.echothree.model.control.tag.common.TagOptions;
import com.echothree.model.control.tag.common.transfer.TagTransfer;
import com.echothree.model.control.tag.server.control.TagControl;
import com.echothree.model.data.tag.server.entity.Tag;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TagTransferCache
        extends BaseTagTransferCache<Tag, TagTransfer> {

    TagControl tagControl = Session.getModelController(TagControl.class);

    boolean includeUsageCount;

    /** Creates a new instance of TagTransferCache */
    protected TagTransferCache() {
        super();

        var options = session.getOptions();
        if(options != null) {
            includeUsageCount = options.contains(TagOptions.TagIncludeUsageCount);
        }

        setIncludeEntityInstance(true);
    }
    
    public TagTransfer getTagTransfer(UserVisit userVisit, Tag tag) {
        var tagTransfer = get(tag);
        
        if(tagTransfer == null) {
            var tagDetail = tag.getLastDetail();
            var tagScope = tagControl.getTagScopeTransfer(userVisit, tagDetail.getTagScope());
            var tagName = tagDetail.getTagName();
            var usageCount = includeUsageCount ? tagControl.countEntityTagsByTag(tag) : null;
            
            tagTransfer = new TagTransfer(tagScope, tagName, usageCount);
            put(userVisit, tag, tagTransfer);
        }
        
        return tagTransfer;
    }
    
}
