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
import com.echothree.model.control.forum.common.transfer.ForumMessagePartTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageTypeTransfer;
import com.echothree.model.control.forum.common.transfer.ForumThreadTransfer;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.control.icon.common.transfer.IconTransfer;
import com.echothree.model.control.icon.server.IconControl;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.forum.server.entity.ForumMessageDetail;
import com.echothree.model.data.icon.server.entity.Icon;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;
import java.util.List;
import java.util.Set;

public class ForumMessageTransferCache
        extends BaseForumTransferCache<ForumMessage, ForumMessageTransfer> {

    IconControl iconControl = (IconControl)Session.getModelController(IconControl.class);
    boolean includeForumMessageRoles;
    boolean includeForumMessageParts;
    boolean includeForumMessageAttachments;

    /** Creates a new instance of ForumMessageTransferCache */
    public ForumMessageTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);

        Set<String> options = session.getOptions();
        if(options != null) {
            setIncludeGuid(options.contains(ForumOptions.ForumMessageIncludeGuid));
            includeForumMessageRoles = options.contains(ForumOptions.ForumMessageIncludeForumMessageRoles);
            includeForumMessageParts = options.contains(ForumOptions.ForumMessageIncludeForumMessageParts);
            includeForumMessageAttachments = options.contains(ForumOptions.ForumMessageIncludeForumMessageAttachments);
            setIncludeEntityAttributeGroups(options.contains(ForumOptions.ForumMessageIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ForumOptions.ForumMessageIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }

    public ForumMessageTransfer getForumMessageTransfer(ForumMessage forumMessage) {
        ForumMessageTransfer forumMessageTransfer = get(forumMessage);

        if(forumMessageTransfer == null) {
            ForumMessageDetail forumMessageDetail = forumMessage.getLastDetail();
            String forumMessageName = forumMessageDetail.getForumMessageName();
            ForumThreadTransfer forumThreadTransfer = forumControl.getForumThreadTransfer(userVisit, forumMessageDetail.getForumThread());
            ForumMessageTypeTransfer forumMessageTypeTransfer = forumControl.getForumMessageTypeTransfer(userVisit, forumMessageDetail.getForumMessageType());
            ForumMessage parentForumMessage = forumMessageDetail.getParentForumMessage();
            ForumMessageTransfer parentForumMessageTransfer = parentForumMessage == null ? null : forumControl.getForumMessageTransfer(userVisit, parentForumMessage);
            Icon icon = forumMessageDetail.getIcon();
            IconTransfer iconTransfer = icon == null ? null : iconControl.getIconTransfer(userVisit, icon);
            Long unformattedPostedTime = forumMessageDetail.getPostedTime();
            String postedTime = formatTypicalDateTime(unformattedPostedTime);

            forumMessageTransfer = new ForumMessageTransfer(forumMessageName, forumThreadTransfer, forumMessageTypeTransfer, parentForumMessageTransfer, iconTransfer, unformattedPostedTime, postedTime);
            put(forumMessage, forumMessageTransfer);

            if(includeForumMessageRoles) {
                forumMessageTransfer.setForumMessageRoles(new ListWrapper<>(forumControl.getForumMessageRoleTransfersByForumMessage(userVisit, forumMessage)));
            }

            if(includeForumMessageParts) {
                List<ForumMessagePartTransfer> forumMessagePartTransfers = forumControl.getForumMessagePartTransfersByForumMessageAndLanguage(userVisit, forumMessage, getLanguage());
                MapWrapper<ForumMessagePartTransfer> forumMessageParts = new MapWrapper<>(forumMessagePartTransfers.size());

                forumMessagePartTransfers.stream().forEach((forumMessagePartTransfer) -> {
                    forumMessageParts.put(forumMessagePartTransfer.getForumMessagePartType().getForumMessagePartTypeName(), forumMessagePartTransfer);
                });

                forumMessageTransfer.setForumMessageParts(forumMessageParts);
            }

            if(includeForumMessageAttachments) {
                forumMessageTransfer.setForumMessageAttachments(new ListWrapper<>(forumControl.getForumMessageAttachmentTransfersByForumMessage(userVisit, forumMessage)));
            }
        }

        return forumMessageTransfer;
    }
}