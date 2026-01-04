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
import com.echothree.model.control.forum.common.transfer.ForumGroupTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.data.forum.server.entity.Forum;
import com.echothree.model.data.forum.server.entity.ForumGroup;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;

@RequestScoped
public class ForumGroupTransferCache
        extends BaseForumTransferCache<ForumGroup, ForumGroupTransfer> {

    ForumControl forumControl = Session.getModelController(ForumControl.class);
    IconControl iconControl = Session.getModelController(IconControl.class);

    boolean includeForums;
    
    /** Creates a new instance of ForumGroupTransferCache */
    protected ForumGroupTransferCache() {
        super();
        
        var options = session.getOptions();
        if(options != null) {
            includeForums = options.contains(ForumOptions.ForumGroupIncludeForums);
            setIncludeEntityAttributeGroups(options.contains(ForumOptions.ForumGroupIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ForumOptions.ForumGroupIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }
    
    public ForumGroupTransfer getForumGroupTransfer(UserVisit userVisit, ForumGroup forumGroup) {
        var forumGroupTransfer = get(forumGroup);
        
        if(forumGroupTransfer == null) {
            var forumGroupDetail = forumGroup.getLastDetail();
            var forumGroupName = forumGroupDetail.getForumGroupName();
            var icon = forumGroupDetail.getIcon();
            var iconTransfer = icon == null? null: iconControl.getIconTransfer(userVisit, icon);
            var sortOrder = forumGroupDetail.getSortOrder();
            var description = forumControl.getBestForumGroupDescription(forumGroup, getLanguage(userVisit));
            
            forumGroupTransfer = new ForumGroupTransfer(forumGroupName, iconTransfer, sortOrder, description);
            put(userVisit, forumGroup, forumGroupTransfer);
            
            if(includeForums) {
                var forumGroupForums = forumControl.getForumGroupForumsByForumGroup(forumGroup);
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
