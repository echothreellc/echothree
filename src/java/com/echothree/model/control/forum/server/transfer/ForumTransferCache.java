// --------------------------------------------------------------------------------
// Copyright 2002-2020 Echo Three, LLC
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
import com.echothree.model.control.forum.common.transfer.ForumTypeTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.icon.common.transfer.IconTransfer;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.control.sequence.common.transfer.SequenceTransfer;
import com.echothree.model.control.sequence.server.SequenceControl;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumDetail;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.model.data.forum.server.entity.ForumGroupForum;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.sequence.server.entity.Sequence;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ForumTransferCache
        extends BaseForumTransferCache<Forum, ForumTransfer> {
    
    IconControl iconControl = (IconControl)Session.getModelController(IconControl.class);
    SequenceControl sequenceControl = (SequenceControl)Session.getModelController(SequenceControl.class);
    boolean includeForumGroups;
    boolean includeForumThreads;
    boolean includeFutureForumThreads;
    
    /** Creates a new instance of ForumTransferCache */
    public ForumTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeGuid(options.contains(ForumOptions.ForumIncludeGuid));
            includeForumGroups = options.contains(ForumOptions.ForumIncludeForumGroups);
            includeForumThreads = options.contains(ForumOptions.ForumIncludeForumThreads);
            includeFutureForumThreads = options.contains(ForumOptions.ForumIncludeFutureForumThreads);
            setIncludeEntityAttributeGroups(options.contains(ForumOptions.ForumIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ForumOptions.ForumIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public ForumTransfer getForumTransfer(Forum forum) {
        ForumTransfer forumTransfer = get(forum);
        
        if(forumTransfer == null) {
            ForumDetail forumDetail = forum.getLastDetail();
            String forumName = forumDetail.getForumName();
            ForumTypeTransfer forumTypeTransfer = forumControl.getForumTypeTransfer(userVisit, forumDetail.getForumType());
            Icon icon = forumDetail.getIcon();
            IconTransfer iconTransfer = icon == null? null: iconControl.getIconTransfer(userVisit, icon);
            Sequence forumThreadSequence = forumDetail.getForumThreadSequence();
            SequenceTransfer forumThreadSequenceTransfer = forumThreadSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, forumThreadSequence);
            Sequence forumMessageSequence = forumDetail.getForumMessageSequence();
            SequenceTransfer forumMessageSequenceTransfer = forumMessageSequence == null? null: sequenceControl.getSequenceTransfer(userVisit, forumMessageSequence);
            Integer sortOrder = forumDetail.getSortOrder();
            String description = forumControl.getBestForumDescription(forum, getLanguage());
            
            forumTransfer = new ForumTransfer(forumName, forumTypeTransfer, iconTransfer, forumThreadSequenceTransfer, forumMessageSequenceTransfer, sortOrder, description);
            put(forum, forumTransfer);
            
            if(includeForumGroups) {
                List<ForumGroupForum> forumGroupForums = forumControl.getForumGroupForumsByForum(forum);
                List<ForumGroup> forumGroups = new ArrayList<>(forumGroupForums.size());
                
                forumGroupForums.stream().forEach((forumGroupForum) -> {
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
