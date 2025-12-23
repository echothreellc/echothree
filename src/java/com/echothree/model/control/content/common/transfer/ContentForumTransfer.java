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

package com.echothree.model.control.content.common.transfer;

import com.echothree.model.control.forum.common.transfer.ForumTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ContentForumTransfer
        extends BaseTransfer {
    
    private ContentCollectionTransfer contentCollection;
    private ForumTransfer forum;
    private Boolean isDefault;
    
    /** Creates a new instance of ContentForumTransfer */
    public ContentForumTransfer(ContentCollectionTransfer contentCollection, ForumTransfer forum, Boolean isDefault) {
        this.contentCollection = contentCollection;
        this.forum = forum;
        this.isDefault = isDefault;
    }

    /**
     * Returns the contentCollection.
     * @return the contentCollection
     */
    public ContentCollectionTransfer getContentCollection() {
        return contentCollection;
    }

    /**
     * Sets the contentCollection.
     * @param contentCollection the contentCollection to set
     */
    public void setContentCollection(ContentCollectionTransfer contentCollection) {
        this.contentCollection = contentCollection;
    }

    /**
     * Returns the forum.
     * @return the forum
     */
    public ForumTransfer getForum() {
        return forum;
    }

    /**
     * Sets the forum.
     * @param forum the forum to set
     */
    public void setForum(ForumTransfer forum) {
        this.forum = forum;
    }

    /**
     * Returns the isDefault.
     * @return the isDefault
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the isDefault.
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
}
