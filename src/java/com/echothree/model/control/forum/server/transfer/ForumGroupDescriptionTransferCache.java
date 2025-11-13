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

import com.echothree.model.control.forum.common.transfer.ForumGroupDescriptionTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumGroupDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class ForumGroupDescriptionTransferCache
        extends BaseForumDescriptionTransferCache<ForumGroupDescription, ForumGroupDescriptionTransfer> {
    
    /** Creates a new instance of ForumGroupDescriptionTransferCache */
    public ForumGroupDescriptionTransferCache(ForumControl forumControl) {
        super(forumControl);
    }
    
    public ForumGroupDescriptionTransfer getForumGroupDescriptionTransfer(UserVisit userVisit, ForumGroupDescription forumGroupDescription) {
        var forumGroupDescriptionTransfer = get(forumGroupDescription);
        
        if(forumGroupDescriptionTransfer == null) {
            var forumGroupTransfer = forumControl.getForumGroupTransfer(userVisit, forumGroupDescription.getForumGroup());
            var languageTransfer = partyControl.getLanguageTransfer(userVisit, forumGroupDescription.getLanguage());
            
            forumGroupDescriptionTransfer = new ForumGroupDescriptionTransfer(languageTransfer, forumGroupTransfer, forumGroupDescription.getDescription());
            put(userVisit, forumGroupDescription, forumGroupDescriptionTransfer);
        }
        
        return forumGroupDescriptionTransfer;
    }
    
}
