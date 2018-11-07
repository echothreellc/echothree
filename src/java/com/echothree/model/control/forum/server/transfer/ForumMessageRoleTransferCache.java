// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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
import com.echothree.model.control.forum.common.transfer.ForumMessageTransfer;
import com.echothree.model.control.forum.common.transfer.ForumRoleTypeTransfer;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.control.party.common.transfer.PartyTransfer;
import com.echothree.model.control.party.server.PartyControl;
import com.echothree.model.data.forum.server.entity.ForumMessageRole;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ForumMessageRoleTransferCache
        extends BaseForumTransferCache<ForumMessageRole, ForumMessageRoleTransfer> {
    
    PartyControl partyControl;
    
    /** Creates a new instance of ForumMessageRoleTransferCache */
    public ForumMessageRoleTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);
        
        partyControl = (PartyControl)Session.getModelController(PartyControl.class);
    }
    
    public ForumMessageRoleTransfer getForumMessageRoleTransfer(ForumMessageRole forumMessageRole) {
        ForumMessageRoleTransfer forumMessageRoleTransfer = get(forumMessageRole);
        
        if(forumMessageRoleTransfer == null) {
            ForumMessageTransfer forumMessage = forumControl.getForumMessageTransfer(userVisit, forumMessageRole.getForumMessage());
            ForumRoleTypeTransfer forumRoleType = forumControl.getForumRoleTypeTransfer(userVisit, forumMessageRole.getForumRoleType());
            PartyTransfer party = partyControl.getPartyTransfer(userVisit, forumMessageRole.getParty());
            
            forumMessageRoleTransfer = new ForumMessageRoleTransfer(forumMessage, forumRoleType, party);
            put(forumMessageRole, forumMessageRoleTransfer);
        }
        
        return forumMessageRoleTransfer;
    }
    
}
