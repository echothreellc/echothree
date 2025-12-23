// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
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

import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.model.control.forum.common.transfer.ForumThreadTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.data.forum.server.entity.ForumThread;
import com.echothree.model.data.forum.server.factory.ForumMessageFactory;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ForumThreadTransferCache
        extends BaseForumTransferCache<ForumThread, ForumThreadTransfer> {

    ForumControl forumControl = Session.getModelController(ForumControl.class);
    IconControl iconControl = Session.getModelController(IconControl.class);

    boolean includeForumMessages;
    boolean includeForumForumThreads;
    boolean hasForumMessageLimits;
    
    /** Creates a new instance of ForumThreadTransferCache */
    protected ForumThreadTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(ForumOptions.ForumThreadIncludeUuid));
            includeForumMessages = options.contains(ForumOptions.ForumThreadIncludeForumMessages);
            includeForumForumThreads = options.contains(ForumOptions.ForumThreadIncludeForumForumThreads);
            setIncludeEntityAttributeGroups(options.contains(ForumOptions.ForumThreadIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ForumOptions.ForumThreadIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
        
        hasForumMessageLimits = session.hasLimit(ForumMessageFactory.class);
    }
    
    public ForumThreadTransfer getForumThreadTransfer(UserVisit userVisit, ForumThread forumThread) {
        var forumThreadTransfer = get(forumThread);
        
        if(forumThreadTransfer == null) {
            var forumThreadDetail = forumThread.getLastDetail();
            var forumThreadName = forumThreadDetail.getForumThreadName();
            var icon = forumThreadDetail.getIcon();
            var iconTransfer = icon == null? null: iconControl.getIconTransfer(userVisit, icon);
            var unformattedPostedTime = forumThreadDetail.getPostedTime();
            var postedTime = formatTypicalDateTime(userVisit, unformattedPostedTime);
            var sortOrder = forumThreadDetail.getSortOrder();
                    
            forumThreadTransfer = new ForumThreadTransfer(forumThreadName, iconTransfer, unformattedPostedTime, postedTime, sortOrder);
            put(userVisit, forumThread, forumThreadTransfer);
            
            if(includeForumMessages) {
                if(hasForumMessageLimits) {
                    forumThreadTransfer.setForumMessageCount(forumControl.countForumMessagesByForumThread(forumThread));
                }
                
                forumThreadTransfer.setForumMessages(new ListWrapper<>(forumControl.getForumMessageTransfersByForumThread(userVisit, forumThread)));
            }
            
            if(includeForumForumThreads) {
                forumThreadTransfer.setForumForumThreads(new ListWrapper<>(forumControl.getForumForumThreadTransfersByForumThread(userVisit, forumThread)));
            }
        }
        
        return forumThreadTransfer;
    }
    
}
