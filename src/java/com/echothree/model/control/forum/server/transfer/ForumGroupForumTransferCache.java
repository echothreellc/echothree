// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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
import com.echothree.model.control.forum.common.transfer.ForumGroupTransfer;
import com.echothree.model.control.forum.common.transfer.ForumTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumGroupForum;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ForumGroupForumTransferCache
        extends BaseForumTransferCache<ForumGroupForum, ForumGroupForumTransfer> {
    
    /** Creates a new instance of ForumGroupForumTransferCache */
    public ForumGroupForumTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);
    }
    
    public ForumGroupForumTransfer getForumGroupForumTransfer(ForumGroupForum forumGroupForum) {
        ForumGroupForumTransfer forumGroupForumTransfer = get(forumGroupForum);
        
        if(forumGroupForumTransfer == null) {
            ForumGroupTransfer forumGroup = forumControl.getForumGroupTransfer(userVisit, forumGroupForum.getForumGroup());
            ForumTransfer forum = forumControl.getForumTransfer(userVisit, forumGroupForum.getForum());
            Boolean isDefault = forumGroupForum.getIsDefault();
            Integer sortOrder = forumGroupForum.getSortOrder();
            
            forumGroupForumTransfer = new ForumGroupForumTransfer(forumGroup, forum, isDefault, sortOrder);
            put(forumGroupForum, forumGroupForumTransfer);
        }
        
        return forumGroupForumTransfer;
    }
    
}
