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

import com.echothree.model.control.icon.common.transfer.IconTransfer;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class ForumTransfer
        extends BaseTransfer {
    
    private String forumName;
    private ForumTypeTransfer forumType;
    private IconTransfer icon;
    private SequenceTransfer forumThreadSequence;
    private SequenceTransfer forumMessageSequence;
    private Integer sortOrder;
    private String description;
    
    private ListWrapper<ForumGroupTransfer> forumGroups;
    private ListWrapper<ForumThreadTransfer> forumThreads;
    
    /** Creates a new instance of ForumTransfer */
    public ForumTransfer(String forumName, ForumTypeTransfer forumType, IconTransfer icon, SequenceTransfer forumThreadSequence,
            SequenceTransfer forumMessageSequence, Integer sortOrder, String description) {
        this.forumName = forumName;
        this.forumType = forumType;
        this.icon = icon;
        this.forumThreadSequence = forumThreadSequence;
        this.forumMessageSequence = forumMessageSequence;
        this.sortOrder = sortOrder;
        this.description = description;
    }
    
    public String getForumName() {
        return forumName;
    }
    
    public void setForumName(String forumName) {
        this.forumName = forumName;
    }
    
    public ForumTypeTransfer getForumType() {
        return forumType;
    }
    
    public void setForumType(ForumTypeTransfer forumType) {
        this.forumType = forumType;
    }
    
    public IconTransfer getIcon() {
        return icon;
    }
    
    public void setIcon(IconTransfer icon) {
        this.icon = icon;
    }
    
    public SequenceTransfer getForumThreadSequence() {
        return forumThreadSequence;
    }
    
    public void setForumThreadSequence(SequenceTransfer forumThreadSequence) {
        this.forumThreadSequence = forumThreadSequence;
    }
    
    public SequenceTransfer getForumMessageSequence() {
        return forumMessageSequence;
    }
    
    public void setForumMessageSequence(SequenceTransfer forumMessageSequence) {
        this.forumMessageSequence = forumMessageSequence;
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
    
    public ListWrapper<ForumGroupTransfer> getForumGroups() {
        return forumGroups;
    }
    
    public void setForumGroups(ListWrapper<ForumGroupTransfer> forumGroups) {
        this.forumGroups = forumGroups;
    }
    
    public ListWrapper<ForumThreadTransfer> getForumThreads() {
        return forumThreads;
    }
    
    public void setForumThreads(ListWrapper<ForumThreadTransfer> forumThreads) {
        this.forumThreads = forumThreads;
    }
    
}
