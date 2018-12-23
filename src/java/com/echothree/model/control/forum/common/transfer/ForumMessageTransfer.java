// --------------------------------------------------------------------------------
// Copyright 2002-2019 Echo Three, LLC
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
import com.echothree.util.common.transfer.MapWrapper;

public class ForumMessageTransfer
        extends BaseTransfer {
    
    private String forumMessageName;
    private ForumThreadTransfer forumThread;
    private ForumMessageTypeTransfer forumMessageType;
    private ForumMessageTransfer parentForumMessage;
    private IconTransfer icon;
    private Long unformattedPostedTime;
    private String postedTime;
    
    private ListWrapper<ForumMessageRoleTransfer> forumMessageRoles;
    private MapWrapper<ForumMessagePartTransfer> forumMessageParts;
    private ListWrapper<ForumMessageAttachmentTransfer> forumMessageAttachments;
    
    /** Creates a new instance of ForumMessageTransfer */
    public ForumMessageTransfer(String forumMessageName, ForumThreadTransfer forumThread, ForumMessageTypeTransfer forumMessageType,
            ForumMessageTransfer parentForumMessage, IconTransfer icon, Long unformattedPostedTime, String postedTime) {
        this.forumMessageName = forumMessageName;
        this.forumThread = forumThread;
        this.forumMessageType = forumMessageType;
        this.parentForumMessage = parentForumMessage;
        this.icon = icon;
        this.unformattedPostedTime = unformattedPostedTime;
        this.postedTime = postedTime;
    }

    /**
     * @return the forumMessageName
     */
    public String getForumMessageName() {
        return forumMessageName;
    }

    /**
     * @param forumMessageName the forumMessageName to set
     */
    public void setForumMessageName(String forumMessageName) {
        this.forumMessageName = forumMessageName;
    }

    /**
     * @return the forumThread
     */
    public ForumThreadTransfer getForumThread() {
        return forumThread;
    }

    /**
     * @param forumThread the forumThread to set
     */
    public void setForumThread(ForumThreadTransfer forumThread) {
        this.forumThread = forumThread;
    }

    /**
     * @return the forumMessageType
     */
    public ForumMessageTypeTransfer getForumMessageType() {
        return forumMessageType;
    }

    /**
     * @param forumMessageType the forumMessageType to set
     */
    public void setForumMessageType(ForumMessageTypeTransfer forumMessageType) {
        this.forumMessageType = forumMessageType;
    }

    /**
     * @return the parentForumMessage
     */
    public ForumMessageTransfer getParentForumMessage() {
        return parentForumMessage;
    }

    /**
     * @param parentForumMessage the parentForumMessage to set
     */
    public void setParentForumMessage(ForumMessageTransfer parentForumMessage) {
        this.parentForumMessage = parentForumMessage;
    }

    /**
     * @return the icon
     */
    public IconTransfer getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(IconTransfer icon) {
        this.icon = icon;
    }

    /**
     * @return the unformattedPostedTime
     */
    public Long getUnformattedPostedTime() {
        return unformattedPostedTime;
    }

    /**
     * @param unformattedPostedTime the unformattedPostedTime to set
     */
    public void setUnformattedPostedTime(Long unformattedPostedTime) {
        this.unformattedPostedTime = unformattedPostedTime;
    }

    /**
     * @return the postedTime
     */
    public String getPostedTime() {
        return postedTime;
    }

    /**
     * @param postedTime the postedTime to set
     */
    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    /**
     * @return the forumMessageRoles
     */
    public ListWrapper<ForumMessageRoleTransfer> getForumMessageRoles() {
        return forumMessageRoles;
    }

    /**
     * @param forumMessageRoles the forumMessageRoles to set
     */
    public void setForumMessageRoles(ListWrapper<ForumMessageRoleTransfer> forumMessageRoles) {
        this.forumMessageRoles = forumMessageRoles;
    }

    /**
     * @return the forumMessageParts
     */
    public MapWrapper<ForumMessagePartTransfer> getForumMessageParts() {
        return forumMessageParts;
    }

    /**
     * @param forumMessageParts the forumMessageParts to set
     */
    public void setForumMessageParts(MapWrapper<ForumMessagePartTransfer> forumMessageParts) {
        this.forumMessageParts = forumMessageParts;
    }

    /**
     * @return the forumMessageAttachments
     */
    public ListWrapper<ForumMessageAttachmentTransfer> getForumMessageAttachments() {
        return forumMessageAttachments;
    }

    /**
     * @param forumMessageAttachments the forumMessageAttachments to set
     */
    public void setForumMessageAttachments(ListWrapper<ForumMessageAttachmentTransfer> forumMessageAttachments) {
        this.forumMessageAttachments = forumMessageAttachments;
    }
    
}
