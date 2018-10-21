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

import com.echothree.model.control.core.remote.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.CoreControl;
import com.echothree.model.control.forum.remote.transfer.ForumMimeTypeTransfer;
import com.echothree.model.control.forum.remote.transfer.ForumTransfer;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumMimeType;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class ForumMimeTypeTransferCache
        extends BaseForumTransferCache<ForumMimeType, ForumMimeTypeTransfer> {
    
    CoreControl coreControl;
    
    /** Creates a new instance of ForumMimeTypeTransferCache */
    public ForumMimeTypeTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);
        
        coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    }
    
    public ForumMimeTypeTransfer getForumMimeTypeTransfer(ForumMimeType forumMimeType) {
        ForumMimeTypeTransfer forumMimeTypeTransfer = get(forumMimeType);
        
        if(forumMimeTypeTransfer == null) {
            ForumTransfer forum = forumControl.getForumTransfer(userVisit, forumMimeType.getForum());
            MimeTypeTransfer mimeType = coreControl.getMimeTypeTransfer(userVisit, forumMimeType.getMimeType());
            Boolean isDefault = forumMimeType.getIsDefault();
            Integer sortOrder = forumMimeType.getSortOrder();
            
            forumMimeTypeTransfer = new ForumMimeTypeTransfer(forum, mimeType, isDefault, sortOrder);
            put(forumMimeType, forumMimeTypeTransfer);
        }
        
        return forumMimeTypeTransfer;
    }
    
}
