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

import com.echothree.util.common.transfer.BaseTransfer;

public class TagTransfer
        extends BaseTransfer {
    
    private TagScopeTransfer tagScope;
    private String tagName;
    private Long usageCount;
    
    /** Creates a new instance of TagTransfer */
    public TagTransfer(TagScopeTransfer tagScope, String tagName, Long usageCount) {
        this.tagScope = tagScope;
        this.tagName = tagName;
        this.usageCount = usageCount;
    }

    /**
     * Returns the tagScope.
     * @return the tagScope
     */
    public TagScopeTransfer getTagScope() {
        return tagScope;
    }

    /**
     * Sets the tagScope.
     * @param tagScope the tagScope to set
     */
    public void setTagScope(TagScopeTransfer tagScope) {
        this.tagScope = tagScope;
    }

    /**
     * Returns the tagName.
     * @return the tagName
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Sets the tagName.
     * @param tagName the tagName to set
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Returns the usageCount.
     * @return the usageCount
     */
    public Long getUsageCount() {
        return usageCount;
    }

    /**
     * Sets the usageCount.
     * @param usageCount the usageCount to set
     */
    public void setUsageCount(Long usageCount) {
        this.usageCount = usageCount;
    }
    
}
