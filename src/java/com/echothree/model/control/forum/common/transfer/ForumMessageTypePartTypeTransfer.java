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

package com.echothree.model.control.forum.common.transfer;

import com.echothree.util.common.transfer.BaseTransfer;

public class ForumMessageTypePartTypeTransfer
        extends BaseTransfer {
    
    private ForumMessageTypeTransfer forumMessageType;
    private Boolean indexDefault;
    private Integer sortOrder;
    private ForumMessagePartTypeTransfer forumMessagePartType;
    
    /** Creates a new instance of ForumMessageTypePartTypeTransfer */
    public ForumMessageTypePartTypeTransfer(ForumMessageTypeTransfer forumMessageType, Boolean indexDefault, Integer sortOrder,
            ForumMessagePartTypeTransfer forumMessagePartType) {
        this.forumMessageType = forumMessageType;
        this.indexDefault = indexDefault;
        this.sortOrder = sortOrder;
        this.forumMessagePartType = forumMessagePartType;
    }
    
    public ForumMessageTypeTransfer getForumMessageType() {
        return forumMessageType;
    }
    
    public void setForumMessageType(ForumMessageTypeTransfer forumMessageType) {
        this.forumMessageType = forumMessageType;
    }
    
    public Boolean getIndexDefault() {
        return indexDefault;
    }

    public void setIndexDefault(Boolean indexDefault) {
        this.indexDefault = indexDefault;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public ForumMessagePartTypeTransfer getForumMessagePartType() {
        return forumMessagePartType;
    }
    
    public void setForumMessagePartType(ForumMessagePartTypeTransfer forumMessagePartType) {
        this.forumMessagePartType = forumMessagePartType;
    }

}
