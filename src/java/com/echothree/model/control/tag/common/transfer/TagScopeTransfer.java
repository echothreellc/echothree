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

package com.echothree.model.control.tag.common.transfer;

import com.echothree.util.common.transfer.CopyableTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class TagScopeTransfer
        extends BaseTransfer
        implements CopyableTransfer<TagScopeTransfer> {
    
    private String tagScopeName;
    private Boolean isDefault;
    private Integer sortOrder;
    private String description;
    
    private ListWrapper<TagTransfer> tags;

    /** Creates a new instance of TagScopeTransfer */
    public TagScopeTransfer(String tagScopeName, Boolean isDefault, Integer sortOrder,  String description) {
        this.tagScopeName = tagScopeName;
        this.isDefault = isDefault;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getTagScopeName() {
        return tagScopeName;
    }
    
    public void setTagScopeName(String tagScopeName) {
        this.tagScopeName = tagScopeName;
    }
    
    public Boolean getIsDefault() {
        return isDefault;
    }
    
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public ListWrapper<TagTransfer> getTags() {
        return tags;
    }
    
    public void setTags(ListWrapper<TagTransfer> tags) {
        this.tags = tags;
    }

    @Override
    public TagScopeTransfer copy() {
        var tagScope = new TagScopeTransfer(tagScopeName, isDefault, sortOrder,  description);

        tagScope.setTags(tags);

        return tagScope;
    }

}
