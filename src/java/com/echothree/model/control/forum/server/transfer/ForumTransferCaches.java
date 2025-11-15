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

public class ForumTransferCaches
        extends BaseTransferCaches {
    
    protected ForumTypeTransferCache forumTypeTransferCache;
    protected ForumMessageTypeTransferCache forumMessageTypeTransferCache;
    protected ForumRoleTypeTransferCache forumRoleTypeTransferCache;
    protected ForumGroupDescriptionTransferCache forumGroupDescriptionTransferCache;
    protected ForumDescriptionTransferCache forumDescriptionTransferCache;
    protected ForumGroupTransferCache forumGroupTransferCache;
    protected ForumGroupForumTransferCache forumGroupForumTransferCache;
    protected ForumTransferCache forumTransferCache;
    protected ForumMessagePartTypeTransferCache forumMessagePartTypeTransferCache;
    protected ForumMessageTypePartTypeTransferCache forumMessageTypePartTypeTransferCache;
    protected ForumMimeTypeTransferCache forumMimeTypeTransferCache;
    protected ForumPartyRoleTransferCache forumPartyRoleTransferCache;
    protected ForumPartyTypeRoleTransferCache forumPartyTypeRoleTransferCache;
    protected ForumForumThreadTransferCache forumForumThreadTransferCache;
    protected ForumMessagePartTransferCache forumMessagePartTransferCache;
    protected ForumMessageRoleTransferCache forumMessageRoleTransferCache;
    protected ForumMessageTransferCache forumMessageTransferCache;
    protected ForumMessageAttachmentTransferCache forumMessageAttachmentTransferCache;
    protected ForumMessageAttachmentDescriptionTransferCache forumMessageAttachmentDescriptionTransferCache;
    protected ForumThreadTransferCache forumThreadTransferCache;
    
    /** Creates a new instance of ForumTransferCaches */
    public ForumTransferCaches() {
        super();
    }
    
    public ForumTypeTransferCache getForumTypeTransferCache() {
        if(forumTypeTransferCache == null)
            forumTypeTransferCache = new ForumTypeTransferCache();
        
        return forumTypeTransferCache;
    }
    
    public ForumMessageTypeTransferCache getForumMessageTypeTransferCache() {
        if(forumMessageTypeTransferCache == null)
            forumMessageTypeTransferCache = new ForumMessageTypeTransferCache();
        
        return forumMessageTypeTransferCache;
    }
    
    public ForumRoleTypeTransferCache getForumRoleTypeTransferCache() {
        if(forumRoleTypeTransferCache == null)
            forumRoleTypeTransferCache = new ForumRoleTypeTransferCache();
        
        return forumRoleTypeTransferCache;
    }
    
    public ForumGroupDescriptionTransferCache getForumGroupDescriptionTransferCache() {
        if(forumGroupDescriptionTransferCache == null)
            forumGroupDescriptionTransferCache = new ForumGroupDescriptionTransferCache();
        
        return forumGroupDescriptionTransferCache;
    }
    
    public ForumDescriptionTransferCache getForumDescriptionTransferCache() {
        if(forumDescriptionTransferCache == null)
            forumDescriptionTransferCache = new ForumDescriptionTransferCache();
        
        return forumDescriptionTransferCache;
    }
    
    public ForumGroupTransferCache getForumGroupTransferCache() {
        if(forumGroupTransferCache == null)
            forumGroupTransferCache = new ForumGroupTransferCache();
        
        return forumGroupTransferCache;
    }
    
    public ForumGroupForumTransferCache getForumGroupForumTransferCache() {
        if(forumGroupForumTransferCache == null)
            forumGroupForumTransferCache = new ForumGroupForumTransferCache();
        
        return forumGroupForumTransferCache;
    }
    
    public ForumTransferCache getForumTransferCache() {
        if(forumTransferCache == null)
            forumTransferCache = new ForumTransferCache();
        
        return forumTransferCache;
    }
    
    public ForumMessagePartTypeTransferCache getForumMessagePartTypeTransferCache() {
        if(forumMessagePartTypeTransferCache == null)
            forumMessagePartTypeTransferCache = new ForumMessagePartTypeTransferCache();
        
        return forumMessagePartTypeTransferCache;
    }
    
    public ForumMessageTypePartTypeTransferCache getForumMessageTypePartTypeTransferCache() {
        if(forumMessageTypePartTypeTransferCache == null)
            forumMessageTypePartTypeTransferCache = new ForumMessageTypePartTypeTransferCache();
        
        return forumMessageTypePartTypeTransferCache;
    }
    
    public ForumMimeTypeTransferCache getForumMimeTypeTransferCache() {
        if(forumMimeTypeTransferCache == null)
            forumMimeTypeTransferCache = new ForumMimeTypeTransferCache();
        
        return forumMimeTypeTransferCache;
    }
    
    public ForumPartyRoleTransferCache getForumPartyRoleTransferCache() {
        if(forumPartyRoleTransferCache == null)
            forumPartyRoleTransferCache = new ForumPartyRoleTransferCache();
        
        return forumPartyRoleTransferCache;
    }
    
    public ForumPartyTypeRoleTransferCache getForumPartyTypeRoleTransferCache() {
        if(forumPartyTypeRoleTransferCache == null)
            forumPartyTypeRoleTransferCache = new ForumPartyTypeRoleTransferCache();
        
        return forumPartyTypeRoleTransferCache;
    }
    
    public ForumForumThreadTransferCache getForumForumThreadTransferCache() {
        if(forumForumThreadTransferCache == null)
            forumForumThreadTransferCache = new ForumForumThreadTransferCache();
        
        return forumForumThreadTransferCache;
    }
    
    public ForumMessagePartTransferCache getForumMessagePartTransferCache() {
        if(forumMessagePartTransferCache == null)
            forumMessagePartTransferCache = new ForumMessagePartTransferCache();
        
        return forumMessagePartTransferCache;
    }
    
    public ForumMessageRoleTransferCache getForumMessageRoleTransferCache() {
        if(forumMessageRoleTransferCache == null)
            forumMessageRoleTransferCache = new ForumMessageRoleTransferCache();
        
        return forumMessageRoleTransferCache;
    }
    
    public ForumMessageTransferCache getForumMessageTransferCache() {
        if(forumMessageTransferCache == null)
            forumMessageTransferCache = new ForumMessageTransferCache();
        
        return forumMessageTransferCache;
    }
    
    public ForumMessageAttachmentTransferCache getForumMessageAttachmentTransferCache() {
        if(forumMessageAttachmentTransferCache == null)
            forumMessageAttachmentTransferCache = new ForumMessageAttachmentTransferCache();

        return forumMessageAttachmentTransferCache;
    }

    public ForumMessageAttachmentDescriptionTransferCache getForumMessageAttachmentDescriptionTransferCache() {
        if(forumMessageAttachmentDescriptionTransferCache == null)
            forumMessageAttachmentDescriptionTransferCache = new ForumMessageAttachmentDescriptionTransferCache();

        return forumMessageAttachmentDescriptionTransferCache;
    }

    public ForumThreadTransferCache getForumThreadTransferCache() {
        if(forumThreadTransferCache == null)
            forumThreadTransferCache = new ForumThreadTransferCache();
        
        return forumThreadTransferCache;
    }
    
}
