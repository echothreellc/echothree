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

import com.echothree.model.control.forum.common.transfer.ForumMessageAttachmentDescriptionTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachmentDescription;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ForumMessageAttachmentDescriptionTransferCache
        extends BaseForumDescriptionTransferCache<ForumMessageAttachmentDescription, ForumMessageAttachmentDescriptionTransfer> {

    ForumControl forumControl = Session.getModelController(ForumControl.class);

    /** Creates a new instance of ForumMessageAttachmentDescriptionTransferCache */
    public ForumMessageAttachmentDescriptionTransferCache() {
        super();
    }
    
    public ForumMessageAttachmentDescriptionTransfer getForumMessageAttachmentDescriptionTransfer(UserVisit userVisit, ForumMessageAttachmentDescription forumMessageAttachmentDescription) {
        var forumMessageAttachmentDescriptionTransfer = get(forumMessageAttachmentDescription);
        
        if(forumMessageAttachmentDescriptionTransfer == null) {
            var forumMessageAttachmentTransferCache = forumControl.getForumTransferCaches().getForumMessageAttachmentTransferCache();
            var forumMessageAttachmentTransfer = forumMessageAttachmentTransferCache.getForumMessageAttachmentTransfer(userVisit, forumMessageAttachmentDescription.getForumMessageAttachment());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, forumMessageAttachmentDescription.getLanguage());
            
            forumMessageAttachmentDescriptionTransfer = new ForumMessageAttachmentDescriptionTransfer(languageTransfer, forumMessageAttachmentTransfer, forumMessageAttachmentDescription.getDescription());
            put(userVisit, forumMessageAttachmentDescription, forumMessageAttachmentDescriptionTransfer);
        }
        
        return forumMessageAttachmentDescriptionTransfer;
    }
    
}
