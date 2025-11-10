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

import com.echothree.model.control.forum.common.transfer.ForumRoleTypeTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumRoleType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ForumRoleTypeTransferCache
        extends BaseForumTransferCache<ForumRoleType, ForumRoleTypeTransfer> {
    
    /** Creates a new instance of ForumRoleTypeTransferCache */
    public ForumRoleTypeTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);
    }
    
    public ForumRoleTypeTransfer getForumRoleTypeTransfer(ForumRoleType forumRoleType) {
        var forumRoleTypeTransfer = get(forumRoleType);
        
        if(forumRoleTypeTransfer == null) {
            var forumRoleTypeName = forumRoleType.getForumRoleTypeName();
            var isDefault = forumRoleType.getIsDefault();
            var sortOrder = forumRoleType.getSortOrder();
            var description = forumControl.getBestForumRoleTypeDescription(forumRoleType, getLanguage(userVisit));
            
            forumRoleTypeTransfer = new ForumRoleTypeTransfer(forumRoleTypeName, isDefault, sortOrder, description);
            put(userVisit, forumRoleType, forumRoleTypeTransfer);
        }
        
        return forumRoleTypeTransfer;
    }
    
}
