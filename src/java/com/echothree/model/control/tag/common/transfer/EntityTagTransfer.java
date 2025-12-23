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

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class EntityTagTransfer
        extends BaseTransfer {

    private EntityInstanceTransfer taggedEntityInstance;
    private TagTransfer tag;
    
    /** Creates a new instance of EntityTagTransfer */
    public EntityTagTransfer(EntityInstanceTransfer taggedEntityInstance, TagTransfer tag) {
        this.taggedEntityInstance = taggedEntityInstance;
        this.tag = tag;
    }

    /**
     * Returns the taggedEntityInstance.
     * @return the taggedEntityInstance
     */
    public EntityInstanceTransfer getTaggedEntityInstance() {
        return taggedEntityInstance;
    }

    /**
     * Sets the taggedEntityInstance.
     * @param taggedEntityInstance the taggedEntityInstance to set
     */
    public void setTaggedEntityInstance(EntityInstanceTransfer taggedEntityInstance) {
        this.taggedEntityInstance = taggedEntityInstance;
    }

    /**
     * Returns the tag.
     * @return the tag
     */
    public TagTransfer getTag() {
        return tag;
    }

    /**
     * Sets the tag.
     * @param tag the tag to set
     */
    public void setTag(TagTransfer tag) {
        this.tag = tag;
    }
    
}
