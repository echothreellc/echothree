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

import com.echothree.util.server.transfer.BaseTransferCaches;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class ForumTransferCaches
        extends BaseTransferCaches {
    
    @Inject
    ForumTypeTransferCache forumTypeTransferCache;
    
    @Inject
    ForumMessageTypeTransferCache forumMessageTypeTransferCache;
    
    @Inject
    ForumRoleTypeTransferCache forumRoleTypeTransferCache;
    
    @Inject
    ForumGroupDescriptionTransferCache forumGroupDescriptionTransferCache;
    
    @Inject
    ForumDescriptionTransferCache forumDescriptionTransferCache;
    
    @Inject
    ForumGroupTransferCache forumGroupTransferCache;
    
    @Inject
    ForumGroupForumTransferCache forumGroupForumTransferCache;
    
    @Inject
    ForumTransferCache forumTransferCache;
    
    @Inject
    ForumMessagePartTypeTransferCache forumMessagePartTypeTransferCache;
    
    @Inject
    ForumMessageTypePartTypeTransferCache forumMessageTypePartTypeTransferCache;
    
    @Inject
    ForumMimeTypeTransferCache forumMimeTypeTransferCache;
    
    @Inject
    ForumPartyRoleTransferCache forumPartyRoleTransferCache;
    
    @Inject
    ForumPartyTypeRoleTransferCache forumPartyTypeRoleTransferCache;
    
    @Inject
    ForumForumThreadTransferCache forumForumThreadTransferCache;
    
    @Inject
    ForumMessagePartTransferCache forumMessagePartTransferCache;
    
    @Inject
    ForumMessageRoleTransferCache forumMessageRoleTransferCache;
    
    @Inject
    ForumMessageTransferCache forumMessageTransferCache;
    
    @Inject
    ForumMessageAttachmentTransferCache forumMessageAttachmentTransferCache;
    
    @Inject
    ForumMessageAttachmentDescriptionTransferCache forumMessageAttachmentDescriptionTransferCache;
    
    @Inject
    ForumThreadTransferCache forumThreadTransferCache;

    /** Creates a new instance of ForumTransferCaches */
    protected ForumTransferCaches() {
        super();
    }
    
    public ForumTypeTransferCache getForumTypeTransferCache() {
        return forumTypeTransferCache;
    }
    
    public ForumMessageTypeTransferCache getForumMessageTypeTransferCache() {
        return forumMessageTypeTransferCache;
    }
    
    public ForumRoleTypeTransferCache getForumRoleTypeTransferCache() {
        return forumRoleTypeTransferCache;
    }
    
    public ForumGroupDescriptionTransferCache getForumGroupDescriptionTransferCache() {
        return forumGroupDescriptionTransferCache;
    }
    
    public ForumDescriptionTransferCache getForumDescriptionTransferCache() {
        return forumDescriptionTransferCache;
    }
    
    public ForumGroupTransferCache getForumGroupTransferCache() {
        return forumGroupTransferCache;
    }
    
    public ForumGroupForumTransferCache getForumGroupForumTransferCache() {
        return forumGroupForumTransferCache;
    }
    
    public ForumTransferCache getForumTransferCache() {
        return forumTransferCache;
    }
    
    public ForumMessagePartTypeTransferCache getForumMessagePartTypeTransferCache() {
        return forumMessagePartTypeTransferCache;
    }
    
    public ForumMessageTypePartTypeTransferCache getForumMessageTypePartTypeTransferCache() {
        return forumMessageTypePartTypeTransferCache;
    }
    
    public ForumMimeTypeTransferCache getForumMimeTypeTransferCache() {
        return forumMimeTypeTransferCache;
    }
    
    public ForumPartyRoleTransferCache getForumPartyRoleTransferCache() {
        return forumPartyRoleTransferCache;
    }
    
    public ForumPartyTypeRoleTransferCache getForumPartyTypeRoleTransferCache() {
        return forumPartyTypeRoleTransferCache;
    }
    
    public ForumForumThreadTransferCache getForumForumThreadTransferCache() {
        return forumForumThreadTransferCache;
    }
    
    public ForumMessagePartTransferCache getForumMessagePartTransferCache() {
        return forumMessagePartTransferCache;
    }
    
    public ForumMessageRoleTransferCache getForumMessageRoleTransferCache() {
        return forumMessageRoleTransferCache;
    }
    
    public ForumMessageTransferCache getForumMessageTransferCache() {
        return forumMessageTransferCache;
    }
    
    public ForumMessageAttachmentTransferCache getForumMessageAttachmentTransferCache() {
        return forumMessageAttachmentTransferCache;
    }

    public ForumMessageAttachmentDescriptionTransferCache getForumMessageAttachmentDescriptionTransferCache() {
        return forumMessageAttachmentDescriptionTransferCache;
    }

    public ForumThreadTransferCache getForumThreadTransferCache() {
        return forumThreadTransferCache;
    }
    
}
