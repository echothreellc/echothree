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
import com.echothree.model.control.forum.common.transfer.ForumMessagePartTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.control.icon.server.control.IconControl;
import com.echothree.model.data.forum.server.entity.ForumMessage;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.transfer.ListWrapper;
import com.echothree.util.common.transfer.MapWrapper;
import com.echothree.util.server.persistence.Session;

public class ForumMessageTransferCache
        extends BaseForumTransferCache<ForumMessage, ForumMessageTransfer> {

    IconControl iconControl = Session.getModelController(IconControl.class);
    boolean includeForumMessageRoles;
    boolean includeForumMessageParts;
    boolean includeForumMessageAttachments;

    /** Creates a new instance of ForumMessageTransferCache */
    public ForumMessageTransferCache(ForumControl forumControl) {
        super(forumControl);

        var options = session.getOptions();
        if(options != null) {
            setIncludeUuid(options.contains(ForumOptions.ForumMessageIncludeUuid));
            includeForumMessageRoles = options.contains(ForumOptions.ForumMessageIncludeForumMessageRoles);
            includeForumMessageParts = options.contains(ForumOptions.ForumMessageIncludeForumMessageParts);
            includeForumMessageAttachments = options.contains(ForumOptions.ForumMessageIncludeForumMessageAttachments);
            setIncludeEntityAttributeGroups(options.contains(ForumOptions.ForumMessageIncludeEntityAttributeGroups));
            setIncludeTagScopes(options.contains(ForumOptions.ForumMessageIncludeTagScopes));
        }
        
        setIncludeEntityInstance(true);
    }

    public ForumMessageTransfer getForumMessageTransfer(ForumMessage forumMessage) {
        var forumMessageTransfer = get(forumMessage);

        if(forumMessageTransfer == null) {
            var forumMessageDetail = forumMessage.getLastDetail();
            var forumMessageName = forumMessageDetail.getForumMessageName();
            var forumThreadTransfer = forumControl.getForumThreadTransfer(userVisit, forumMessageDetail.getForumThread());
            var forumMessageTypeTransfer = forumControl.getForumMessageTypeTransfer(userVisit, forumMessageDetail.getForumMessageType());
            var parentForumMessage = forumMessageDetail.getParentForumMessage();
            var parentForumMessageTransfer = parentForumMessage == null ? null : forumControl.getForumMessageTransfer(userVisit, parentForumMessage);
            var icon = forumMessageDetail.getIcon();
            var iconTransfer = icon == null ? null : iconControl.getIconTransfer(userVisit, icon);
            var unformattedPostedTime = forumMessageDetail.getPostedTime();
            var postedTime = formatTypicalDateTime(userVisit, unformattedPostedTime);

            forumMessageTransfer = new ForumMessageTransfer(forumMessageName, forumThreadTransfer, forumMessageTypeTransfer, parentForumMessageTransfer, iconTransfer, unformattedPostedTime, postedTime);
            put(userVisit, forumMessage, forumMessageTransfer);

            if(includeForumMessageRoles) {
                forumMessageTransfer.setForumMessageRoles(new ListWrapper<>(forumControl.getForumMessageRoleTransfersByForumMessage(userVisit, forumMessage)));
            }

            if(includeForumMessageParts) {
                var forumMessagePartTransfers = forumControl.getForumMessagePartTransfersByForumMessageAndLanguage(userVisit, forumMessage, getLanguage(userVisit));
                var forumMessageParts = new MapWrapper<ForumMessagePartTransfer>(forumMessagePartTransfers.size());

                forumMessagePartTransfers.forEach((forumMessagePartTransfer) -> {
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