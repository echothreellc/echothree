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
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class ForumThreadTransfer
        extends BaseTransfer {
    
    private String forumThreadName;
    private IconTransfer icon;
    private Long unformattedPostedTime;
    private String postedTime;
    private Integer sortOrder;
    
    private Long forumMessageCount;
    private ListWrapper<ForumMessageTransfer> forumMessages;
    private ListWrapper<ForumForumThreadTransfer> forumForumThreads;
    
    /** Creates a new instance of ForumThreadTransfer */
    public ForumThreadTransfer(String forumThreadName, IconTransfer icon, Long unformattedPostedTime, String postedTime,
            Integer sortOrder) {
        this.forumThreadName = forumThreadName;
        this.icon = icon;
        this.unformattedPostedTime = unformattedPostedTime;
        this.postedTime = postedTime;
        this.sortOrder = sortOrder;
    }
    
    public String getForumThreadName() {
        return forumThreadName;
    }
    
    public void setForumThreadName(String forumThreadName) {
        this.forumThreadName = forumThreadName;
    }
    
    public IconTransfer getIcon() {
        return icon;
    }
    
    public void setIcon(IconTransfer icon) {
        this.icon = icon;
    }
    
    public Long getUnformattedPostedTime() {
        return unformattedPostedTime;
    }
    
    public void setUnformattedPostedTime(Long unformattedPostedTime) {
        this.unformattedPostedTime = unformattedPostedTime;
    }
    
    public String getPostedTime() {
        return postedTime;
    }
    
    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }
    
    public Integer getSortOrder() {
        return sortOrder;
    }
    
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
    
    public Long getForumMessageCount() {
        return forumMessageCount;
    }
    
    public void setForumMessageCount(Long forumMessageCount) {
        this.forumMessageCount = forumMessageCount;
    }
    
    public ListWrapper<ForumMessageTransfer> getForumMessages() {
        return forumMessages;
    }
    
    public void setForumMessages(ListWrapper<ForumMessageTransfer> forumMessages) {
        this.forumMessages = forumMessages;
    }
    
    public ListWrapper<ForumForumThreadTransfer> getForumForumThreads() {
        return forumForumThreads;
    }
    
    public void setForumForumThreads(ListWrapper<ForumForumThreadTransfer> forumForumThreads) {
        this.forumForumThreads = forumForumThreads;
    }
    
}
