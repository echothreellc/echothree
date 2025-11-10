// --------------------------------------------------------------------------------
// Copyright 2002-2025 Echo Three, LLC
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

package com.echothree.model.control.forum.server.transfer;

import com.echothree.model.control.forum.common.transfer.ForumMessageRoleTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.forum.server.entity.ForumMessageRole;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ForumMessageRoleTransferCache
        extends BaseForumTransferCache<ForumMessageRole, ForumMessageRoleTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of ForumMessageRoleTransferCache */
    public ForumMessageRoleTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);
        
        partyControl = Session.getModelController(PartyControl.class);
    }
    
    public ForumMessageRoleTransfer getForumMessageRoleTransfer(ForumMessageRole forumMessageRole) {
        var forumMessageRoleTransfer = get(forumMessageRole);
        
        if(forumMessageRoleTransfer == null) {
            var forumMessage = forumControl.getForumMessageTransfer(userVisit, forumMessageRole.getForumMessage());
            var forumRoleType = forumControl.getForumRoleTypeTransfer(userVisit, forumMessageRole.getForumRoleType());
            var party = partyControl.getPartyTransfer(userVisit, forumMessageRole.getParty());
            
            forumMessageRoleTransfer = new ForumMessageRoleTransfer(forumMessage, forumRoleType, party);
            put(userVisit, forumMessageRole, forumMessageRoleTransfer);
        }
        
        return forumMessageRoleTransfer;
    }
    
}
