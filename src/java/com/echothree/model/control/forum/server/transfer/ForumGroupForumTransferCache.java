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

package com.echothree.model.control.forum.server.transfer;

import com.echothree.model.control.forum.common.transfer.ForumGroupForumTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumGroupForum;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ForumGroupForumTransferCache
        extends BaseForumTransferCache<ForumGroupForum, ForumGroupForumTransfer> {

    ForumControl forumControl = Session.getModelController(ForumControl.class);

    /** Creates a new instance of ForumGroupForumTransferCache */
    protected ForumGroupForumTransferCache() {
        super();
    }
    
    public ForumGroupForumTransfer getForumGroupForumTransfer(UserVisit userVisit, ForumGroupForum forumGroupForum) {
        var forumGroupForumTransfer = get(forumGroupForum);
        
        if(forumGroupForumTransfer == null) {
            var forumGroup = forumControl.getForumGroupTransfer(userVisit, forumGroupForum.getForumGroup());
            var forum = forumControl.getForumTransfer(userVisit, forumGroupForum.getForum());
            var isDefault = forumGroupForum.getIsDefault();
            var sortOrder = forumGroupForum.getSortOrder();
            
            forumGroupForumTransfer = new ForumGroupForumTransfer(forumGroup, forum, isDefault, sortOrder);
            put(userVisit, forumGroupForum, forumGroupForumTransfer);
        }
        
        return forumGroupForumTransfer;
    }
    
}
