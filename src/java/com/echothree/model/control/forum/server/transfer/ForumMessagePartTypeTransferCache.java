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

import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.forum.common.transfer.ForumMessagePartTypeTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumMessagePartType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ForumMessagePartTypeTransferCache
        extends BaseForumTransferCache<ForumMessagePartType, ForumMessagePartTypeTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);

    /** Creates a new instance of ForumMessagePartTypeTransferCache */
    public ForumMessagePartTypeTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);
    }
    
    public ForumMessagePartTypeTransfer getForumMessagePartTypeTransfer(ForumMessagePartType forumMessagePartType) {
        var forumMessagePartTypeTransfer = get(forumMessagePartType);
        
        if(forumMessagePartTypeTransfer == null) {
            var forumMessagePartTypeName = forumMessagePartType.getForumMessagePartTypeName();
            var mimeTypeUsageType = forumMessagePartType.getMimeTypeUsageType();
            var mimeTypeUsageTypeTransfer = mimeTypeUsageType == null? null: mimeTypeControl.getMimeTypeUsageTypeTransfer(userVisit, mimeTypeUsageType);
            var sortOrder = forumMessagePartType.getSortOrder();
            
            forumMessagePartTypeTransfer = new ForumMessagePartTypeTransfer(forumMessagePartTypeName, mimeTypeUsageTypeTransfer,
                    sortOrder);
            put(forumMessagePartType, forumMessagePartTypeTransfer);
        }
        
        return forumMessagePartTypeTransfer;
    }
    
}
