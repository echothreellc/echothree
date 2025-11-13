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

import com.echothree.model.control.forum.common.transfer.ForumDescriptionTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ForumDescriptionTransferCache
        extends BaseForumDescriptionTransferCache<ForumDescription, ForumDescriptionTransfer> {
    
    /** Creates a new instance of ForumDescriptionTransferCache */
    public ForumDescriptionTransferCache(ForumControl forumControl) {
        super(forumControl);
    }
    
    public ForumDescriptionTransfer getForumDescriptionTransfer(UserVisit userVisit, ForumDescription forumDescription) {
        var forumDescriptionTransfer = get(forumDescription);
        
        if(forumDescriptionTransfer == null) {
            var forumTransfer = forumControl.getForumTransfer(userVisit, forumDescription.getForum());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, forumDescription.getLanguage());
            
            forumDescriptionTransfer = new ForumDescriptionTransfer(languageTransfer, forumTransfer, forumDescription.getDescription());
            put(userVisit, forumDescription, forumDescriptionTransfer);
        }
        
        return forumDescriptionTransfer;
    }
    
}
