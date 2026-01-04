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

import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.util.common.transfer.BaseTransfer;

public class ForumMessageRoleTransfer
        extends BaseTransfer {
    
    private ForumMessageTransfer forumMessage;
    private ForumRoleTypeTransfer forumRoleType;
    private PartyTransfer party;
    
    /** Creates a new instance of ForumMessageRoleTransfer */
    public ForumMessageRoleTransfer(ForumMessageTransfer forumMessage, ForumRoleTypeTransfer forumRoleType, PartyTransfer party) {
        this.forumMessage = forumMessage;
        this.forumRoleType = forumRoleType;
        this.party = party;
    }
    
    public ForumMessageTransfer getForumMessage() {
        return forumMessage;
    }
    
    public void setForumMessage(ForumMessageTransfer forumMessage) {
        this.forumMessage = forumMessage;
    }
    
    public ForumRoleTypeTransfer getForumRoleType() {
        return forumRoleType;
    }
    
    public void setForumRoleType(ForumRoleTypeTransfer forumRoleType) {
        this.forumRoleType = forumRoleType;
    }
    
    public PartyTransfer getParty() {
        return party;
    }
    
    public void setParty(PartyTransfer party) {
        this.party = party;
    }
    
}
