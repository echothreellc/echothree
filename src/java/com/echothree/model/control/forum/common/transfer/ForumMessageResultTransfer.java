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

import com.echothree.model.control.forum.common.transfer.ForumMessageTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ForumMessageResultTransfer
        extends BaseTransfer {

    private String forumMessageName;
    private ForumMessageTransfer forumMessage;
    
    /** Creates a new instance of ForumMessageResultTransfer */
    public ForumMessageResultTransfer(String forumMessageName, ForumMessageTransfer forumMessage) {
        this.forumMessageName = forumMessageName;
        this.forumMessage = forumMessage;
    }

    /**
     * Returns the forumMessageName.
     * @return the forumMessageName
     */
    public String getForumMessageName() {
        return forumMessageName;
    }

    /**
     * Sets the forumMessageName.
     * @param forumMessageName the forumMessageName to set
     */
    public void setForumMessageName(String forumMessageName) {
        this.forumMessageName = forumMessageName;
    }

    /**
     * Returns the forumMessage.
     * @return the forumMessage
     */
    public ForumMessageTransfer getForumMessage() {
        return forumMessage;
    }

    /**
     * Sets the forumMessage.
     * @param forumMessage the forumMessage to set
     */
    public void setForumMessage(ForumMessageTransfer forumMessage) {
        this.forumMessage = forumMessage;
    }

}
