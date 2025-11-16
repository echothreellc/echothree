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
import javax.enterprise.inject.spi.CDI;

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
            forumTypeTransferCache = CDI.current().select(ForumTypeTransferCache.class).get();
        
        return forumTypeTransferCache;
    }
    
    public ForumMessageTypeTransferCache getForumMessageTypeTransferCache() {
        if(forumMessageTypeTransferCache == null)
            forumMessageTypeTransferCache = CDI.current().select(ForumMessageTypeTransferCache.class).get();
        
        return forumMessageTypeTransferCache;
    }
    
    public ForumRoleTypeTransferCache getForumRoleTypeTransferCache() {
        if(forumRoleTypeTransferCache == null)
            forumRoleTypeTransferCache = CDI.current().select(ForumRoleTypeTransferCache.class).get();
        
        return forumRoleTypeTransferCache;
    }
    
    public ForumGroupDescriptionTransferCache getForumGroupDescriptionTransferCache() {
        if(forumGroupDescriptionTransferCache == null)
            forumGroupDescriptionTransferCache = CDI.current().select(ForumGroupDescriptionTransferCache.class).get();
        
        return forumGroupDescriptionTransferCache;
    }
    
    public ForumDescriptionTransferCache getForumDescriptionTransferCache() {
        if(forumDescriptionTransferCache == null)
            forumDescriptionTransferCache = CDI.current().select(ForumDescriptionTransferCache.class).get();
        
        return forumDescriptionTransferCache;
    }
    
    public ForumGroupTransferCache getForumGroupTransferCache() {
        if(forumGroupTransferCache == null)
            forumGroupTransferCache = CDI.current().select(ForumGroupTransferCache.class).get();
        
        return forumGroupTransferCache;
    }
    
    public ForumGroupForumTransferCache getForumGroupForumTransferCache() {
        if(forumGroupForumTransferCache == null)
            forumGroupForumTransferCache = CDI.current().select(ForumGroupForumTransferCache.class).get();
        
        return forumGroupForumTransferCache;
    }
    
    public ForumTransferCache getForumTransferCache() {
        if(forumTransferCache == null)
            forumTransferCache = CDI.current().select(ForumTransferCache.class).get();
        
        return forumTransferCache;
    }
    
    public ForumMessagePartTypeTransferCache getForumMessagePartTypeTransferCache() {
        if(forumMessagePartTypeTransferCache == null)
            forumMessagePartTypeTransferCache = CDI.current().select(ForumMessagePartTypeTransferCache.class).get();
        
        return forumMessagePartTypeTransferCache;
    }
    
    public ForumMessageTypePartTypeTransferCache getForumMessageTypePartTypeTransferCache() {
        if(forumMessageTypePartTypeTransferCache == null)
            forumMessageTypePartTypeTransferCache = CDI.current().select(ForumMessageTypePartTypeTransferCache.class).get();
        
        return forumMessageTypePartTypeTransferCache;
    }
    
    public ForumMimeTypeTransferCache getForumMimeTypeTransferCache() {
        if(forumMimeTypeTransferCache == null)
            forumMimeTypeTransferCache = CDI.current().select(ForumMimeTypeTransferCache.class).get();
        
        return forumMimeTypeTransferCache;
    }
    
    public ForumPartyRoleTransferCache getForumPartyRoleTransferCache() {
        if(forumPartyRoleTransferCache == null)
            forumPartyRoleTransferCache = CDI.current().select(ForumPartyRoleTransferCache.class).get();
        
        return forumPartyRoleTransferCache;
    }
    
    public ForumPartyTypeRoleTransferCache getForumPartyTypeRoleTransferCache() {
        if(forumPartyTypeRoleTransferCache == null)
            forumPartyTypeRoleTransferCache = CDI.current().select(ForumPartyTypeRoleTransferCache.class).get();
        
        return forumPartyTypeRoleTransferCache;
    }
    
    public ForumForumThreadTransferCache getForumForumThreadTransferCache() {
        if(forumForumThreadTransferCache == null)
            forumForumThreadTransferCache = CDI.current().select(ForumForumThreadTransferCache.class).get();
        
        return forumForumThreadTransferCache;
    }
    
    public ForumMessagePartTransferCache getForumMessagePartTransferCache() {
        if(forumMessagePartTransferCache == null)
            forumMessagePartTransferCache = CDI.current().select(ForumMessagePartTransferCache.class).get();
        
        return forumMessagePartTransferCache;
    }
    
    public ForumMessageRoleTransferCache getForumMessageRoleTransferCache() {
        if(forumMessageRoleTransferCache == null)
            forumMessageRoleTransferCache = CDI.current().select(ForumMessageRoleTransferCache.class).get();
        
        return forumMessageRoleTransferCache;
    }
    
    public ForumMessageTransferCache getForumMessageTransferCache() {
        if(forumMessageTransferCache == null)
            forumMessageTransferCache = CDI.current().select(ForumMessageTransferCache.class).get();
        
        return forumMessageTransferCache;
    }
    
    public ForumMessageAttachmentTransferCache getForumMessageAttachmentTransferCache() {
        if(forumMessageAttachmentTransferCache == null)
            forumMessageAttachmentTransferCache = CDI.current().select(ForumMessageAttachmentTransferCache.class).get();

        return forumMessageAttachmentTransferCache;
    }

    public ForumMessageAttachmentDescriptionTransferCache getForumMessageAttachmentDescriptionTransferCache() {
        if(forumMessageAttachmentDescriptionTransferCache == null)
            forumMessageAttachmentDescriptionTransferCache = CDI.current().select(ForumMessageAttachmentDescriptionTransferCache.class).get();

        return forumMessageAttachmentDescriptionTransferCache;
    }

    public ForumThreadTransferCache getForumThreadTransferCache() {
        if(forumThreadTransferCache == null)
            forumThreadTransferCache = CDI.current().select(ForumThreadTransferCache.class).get();
        
        return forumThreadTransferCache;
    }
    
}
