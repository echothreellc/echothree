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

package com.echothree.model.control.forum.server.transfer;

import com.echothree.model.control.forum.common.transfer.ForumForumThreadTransfer;
import com.echothree.model.control.forum.common.transfer.ForumThreadTransfer;
import com.echothree.model.control.forum.common.transfer.ForumTransfer;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumForumThread;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ForumForumThreadTransferCache
        extends BaseForumTransferCache<ForumForumThread, ForumForumThreadTransfer> {
    
    /** Creates a new instance of ForumForumThreadTransferCache */
    public ForumForumThreadTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);
    }
    
    public ForumForumThreadTransfer getForumForumThreadTransfer(ForumForumThread forumForumThread) {
        ForumForumThreadTransfer forumForumThreadTransfer = get(forumForumThread);
        
        if(forumForumThreadTransfer == null) {
            ForumTransfer forum = forumControl.getForumTransfer(userVisit, forumForumThread.getForum());
            ForumThreadTransfer forumThread = forumControl.getForumThreadTransfer(userVisit, forumForumThread.getForumThread());
            Boolean isDefault = forumForumThread.getIsDefault();
            Integer sortOrder = forumForumThread.getSortOrder();
            
            forumForumThreadTransfer = new ForumForumThreadTransfer(forum, forumThread, isDefault, sortOrder);
            put(forumForumThread, forumForumThreadTransfer);
        }
        
        return forumForumThreadTransfer;
    }
    
}
