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

import com.echothree.model.control.forum.common.transfer.ForumPartyRoleTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.forum.server.entity.ForumPartyRole;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ForumPartyRoleTransferCache
        extends BaseForumTransferCache<ForumPartyRole, ForumPartyRoleTransfer> {

    ForumControl forumControl = Session.getModelController(ForumControl.class);
    PartyControl partyControl = Session.getModelController(PartyControl.class);
    
    /** Creates a new instance of ForumPartyRoleTransferCache */
    public ForumPartyRoleTransferCache() {
        super();
    }
    
    public ForumPartyRoleTransfer getForumPartyRoleTransfer(UserVisit userVisit, ForumPartyRole forumPartyRole) {
        var forumPartyRoleTransfer = get(forumPartyRole);
        
        if(forumPartyRoleTransfer == null) {
            var forum = forumControl.getForumTransfer(userVisit, forumPartyRole.getForum());
            var party = partyControl.getPartyTransfer(userVisit, forumPartyRole.getParty());
            var forumRoleType = forumControl.getForumRoleTypeTransfer(userVisit, forumPartyRole.getForumRoleType());
            
            forumPartyRoleTransfer = new ForumPartyRoleTransfer(forum, party, forumRoleType);
            put(userVisit, forumPartyRole, forumPartyRoleTransfer);
        }
        
        return forumPartyRoleTransfer;
    }
    
}
