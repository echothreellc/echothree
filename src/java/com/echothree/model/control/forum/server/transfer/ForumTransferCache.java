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

import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.model.control.forum.common.transfer.ForumTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.control.sequence.server.control.SequenceControl;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ForumTransferCache
        extends BaseForumTransferCache<Forum, ForumTransfer> {

    ForumControl forumControl = Session.getModelController(ForumControl.class);
    IconControl iconControl = Session.getModelController(IconControl.class);
    SequenceControl sequenceControl = Session.getModelController(SequenceControl.class);

    boolean includeForumGroups;
    boolean includeForumThreads;
    boolean includeFutureForumThreads;
    
    /** Creates a new instance of ForumTransferCache */
    protected ForumTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(ForumOptions.ForumIncludeUuid));
            includeForumGroups = options.contains(ForumOptions.ForumIncludeForumGroups);
            includeForumThreads = options.contains(ForumOptions.ForumIncludeForumThreads);
            includeFutureForumThreads = options.contains(ForumOptions.ForumIncludeFutureForumThreads);
            setIncludeEntityAttributeGroups(options.contains(ForumOptions.ForumIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ForumOptions.ForumIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public ForumTransfer getForumTransfer(UserVisit userVisit, Forum forum) {
        var forumTransfer = get(forum);
        
        if(forumTransfer == null) {
            var forumDetail = forum.getLastDetail();
            var forumName = forumDetail.getForumName();
            var forumTypeTransfer = forumControl.getForumTypeTransfer(userVisit, forumDetail.getForumType());
            var icon = forumDetail.getIcon();
            var iconTransfer = icon == null? null: iconControl.getIconTransfer(userVisit, icon);
            var forumThreadSequence = forumDetail.getForumThreadSequence();
            var forumThreadSequenceTransfer = forumThreadSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, forumThreadSequence);
            var forumMessageSequence = forumDetail.getForumMessageSequence();
            var forumMessageSequenceTransfer = forumMessageSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, forumMessageSequence);
            var sortOrder = forumDetail.getSortOrder();
            var description = forumControl.getBestForumDescription(forum, getLanguage(userVisit));
            
            forumTransfer = new ForumTransfer(forumName, forumTypeTransfer, iconTransfer, forumThreadSequenceTransfer, forumMessageSequenceTransfer, sortOrder, description);
            put(userVisit, forum, forumTransfer);
            
            if(includeForumGroups) {
                var forumGroupForums = forumControl.getForumGroupForumsByForum(forum);
                List<ForumGroup> forumGroups = new ArrayList<>(forumGroupForums.size());
                
                forumGroupForums.forEach((forumGroupForum) -> {
                    forumGroups.add(forumGroupForum.getForumGroup());
                });
                
                forumTransfer.setForumGroups(new ListWrapper<>(forumControl.getForumGroupTransfers(userVisit, forumGroups)));
            }
            
            if(includeForumThreads) {
                forumTransfer.setForumThreads(new ListWrapper<>(forumControl.getForumThreadTransfersByForum(userVisit, forum, includeFutureForumThreads)));
            }
        }
        
        return forumTransfer;
    }
    
}
