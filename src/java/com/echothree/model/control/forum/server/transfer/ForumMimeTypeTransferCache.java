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
import com.echothree.model.control.forum.common.transfer.ForumMimeTypeTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumMimeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ForumMimeTypeTransferCache
        extends BaseForumTransferCache<ForumMimeType, ForumMimeTypeTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    
    /** Creates a new instance of ForumMimeTypeTransferCache */
    public ForumMimeTypeTransferCache(ForumControl forumControl) {
        super(forumControl);
    }
    
    public ForumMimeTypeTransfer getForumMimeTypeTransfer(UserVisit userVisit, ForumMimeType forumMimeType) {
        var forumMimeTypeTransfer = get(forumMimeType);
        
        if(forumMimeTypeTransfer == null) {
            var forum = forumControl.getForumTransfer(userVisit, forumMimeType.getForum());
            var mimeType = mimeTypeControl.getMimeTypeTransfer(userVisit, forumMimeType.getMimeType());
            var isDefault = forumMimeType.getIsDefault();
            var sortOrder = forumMimeType.getSortOrder();
            
            forumMimeTypeTransfer = new ForumMimeTypeTransfer(forum, mimeType, isDefault, sortOrder);
            put(userVisit, forumMimeType, forumMimeTypeTransfer);
        }
        
        return forumMimeTypeTransfer;
    }
    
}
