// --------------------------------------------------------------------------------
// Copyright 2002-2023 Echo Three, LLC
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
import com.echothree.model.control.forum.common.transfer.ForumGroupTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.icon.common.transfer.IconTransfer;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.model.data.forum.server.entity.ForumGroupDetail;
import com.echothree.model.data.forum.server.entity.ForumGroupForum;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ForumGroupTransferCache
        extends BaseForumTransferCache<ForumGroup, ForumGroupTransfer> {
    
    IconControl iconControl = Session.getModelController(IconControl.class);
    boolean includeForums;
    
    /** Creates a new instance of ForumGroupTransferCache */
    public ForumGroupTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeForums = options.contains(ForumOptions.ForumGroupIncludeForums);
            setIncludeEntityAttributeGroups(options.contains(ForumOptions.ForumGroupIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ForumOptions.ForumGroupIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public ForumGroupTransfer getForumGroupTransfer(ForumGroup forumGroup) {
        ForumGroupTransfer forumGroupTransfer = get(forumGroup);
        
        if(forumGroupTransfer == null) {
            ForumGroupDetail forumGroupDetail = forumGroup.getLastDetail();
            String forumGroupName = forumGroupDetail.getForumGroupName();
            Icon icon = forumGroupDetail.getIcon();
            IconTransfer iconTransfer = icon == null? null: iconControl.getIconTransfer(userVisit, icon);
            Integer sortOrder = forumGroupDetail.getSortOrder();
            String description = forumControl.getBestForumGroupDescription(forumGroup, getLanguage());
            
            forumGroupTransfer = new ForumGroupTransfer(forumGroupName, iconTransfer, sortOrder, description);
            put(forumGroup, forumGroupTransfer);
            
            if(includeForums) {
                List<ForumGroupForum> forumGroupForums = forumControl.getForumGroupForumsByForumGroup(forumGroup);
                List<Forum> forums = new ArrayList<>(forumGroupForums.size());
                
                forumGroupForums.forEach((forumGroupForum) -> {
                    forums.add(forumGroupForum.getForum());
                });
                
                forumGroupTransfer.setForums(new ListWrapper<>(forumControl.getForumTransfers(userVisit, forums)));
            }
        }
        
        return forumGroupTransfer;
    }
    
}
