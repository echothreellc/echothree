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

import com.echothree.model.control.forum.common.transfer.ForumMessageTypePartTypeTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumMessageTypePartType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ForumMessageTypePartTypeTransferCache
        extends BaseForumTransferCache<ForumMessageTypePartType, ForumMessageTypePartTypeTransfer> {
    
    /** Creates a new instance of ForumMessageTypePartTypeTransferCache */
    public ForumMessageTypePartTypeTransferCache(ForumControl forumControl) {
        super(forumControl);
    }
    
    public ForumMessageTypePartTypeTransfer getForumMessageTypePartTypeTransfer(ForumMessageTypePartType forumMessageTypePartType) {
        var forumMessageTypePartTypeTransfer = get(forumMessageTypePartType);
        
        if(forumMessageTypePartTypeTransfer == null) {
            var forumMessageType = forumControl.getForumMessageTypeTransfer(userVisit, forumMessageTypePartType.getForumMessageType());
            var indexDefault = forumMessageTypePartType.getIndexDefault();
            var sortOrder = forumMessageTypePartType.getSortOrder();
            var forumMessagePartType = forumControl.getForumMessagePartTypeTransfer(userVisit, forumMessageTypePartType.getForumMessagePartType());
            
            forumMessageTypePartTypeTransfer = new ForumMessageTypePartTypeTransfer(forumMessageType, indexDefault, sortOrder, forumMessagePartType);
            put(userVisit, forumMessageTypePartType, forumMessageTypePartTypeTransfer);
        }
        
        return forumMessageTypePartTypeTransfer;
    }
    
}
