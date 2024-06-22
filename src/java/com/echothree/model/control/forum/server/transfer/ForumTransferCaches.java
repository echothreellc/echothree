// --------------------------------------------------------------------------------
// Copyright 2002-2024 Echo Three, LLC
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

import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class ForumTransferCaches
        extends BaseTransferCaches {
    
    protected ForumControl forumControl;
    
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
    public ForumTransferCaches(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit);
        
        this.forumControl = forumControl;
    }
    
    public ForumTypeTransferCache getForumTypeTransferCache() {
        if(forumTypeTransferCache == null)
            forumTypeTransferCache = new ForumTypeTransferCache(userVisit, forumControl);
        
        return forumTypeTransferCache;
    }
    
    public ForumMessageTypeTransferCache getForumMessageTypeTransferCache() {
        if(forumMessageTypeTransferCache == null)
            forumMessageTypeTransferCache = new ForumMessageTypeTransferCache(userVisit, forumControl);
        
        return forumMessageTypeTransferCache;
    }
    
    public ForumRoleTypeTransferCache getForumRoleTypeTransferCache() {
        if(forumRoleTypeTransferCache == null)
            forumRoleTypeTransferCache = new ForumRoleTypeTransferCache(userVisit, forumControl);
        
        return forumRoleTypeTransferCache;
    }
    
    public ForumGroupDescriptionTransferCache getForumGroupDescriptionTransferCache() {
        if(forumGroupDescriptionTransferCache == null)
            forumGroupDescriptionTransferCache = new ForumGroupDescriptionTransferCache(userVisit, forumControl);
        
        return forumGroupDescriptionTransferCache;
    }
    
    public ForumDescriptionTransferCache getForumDescriptionTransferCache() {
        if(forumDescriptionTransferCache == null)
            forumDescriptionTransferCache = new ForumDescriptionTransferCache(userVisit, forumControl);
        
        return forumDescriptionTransferCache;
    }
    
    public ForumGroupTransferCache getForumGroupTransferCache() {
        if(forumGroupTransferCache == null)
            forumGroupTransferCache = new ForumGroupTransferCache(userVisit, forumControl);
        
        return forumGroupTransferCache;
    }
    
    public ForumGroupForumTransferCache getForumGroupForumTransferCache() {
        if(forumGroupForumTransferCache == null)
            forumGroupForumTransferCache = new ForumGroupForumTransferCache(userVisit, forumControl);
        
        return forumGroupForumTransferCache;
    }
    
    public ForumTransferCache getForumTransferCache() {
        if(forumTransferCache == null)
            forumTransferCache = new ForumTransferCache(userVisit, forumControl);
        
        return forumTransferCache;
    }
    
    public ForumMessagePartTypeTransferCache getForumMessagePartTypeTransferCache() {
        if(forumMessagePartTypeTransferCache == null)
            forumMessagePartTypeTransferCache = new ForumMessagePartTypeTransferCache(userVisit, forumControl);
        
        return forumMessagePartTypeTransferCache;
    }
    
    public ForumMessageTypePartTypeTransferCache getForumMessageTypePartTypeTransferCache() {
        if(forumMessageTypePartTypeTransferCache == null)
            forumMessageTypePartTypeTransferCache = new ForumMessageTypePartTypeTransferCache(userVisit, forumControl);
        
        return forumMessageTypePartTypeTransferCache;
    }
    
    public ForumMimeTypeTransferCache getForumMimeTypeTransferCache() {
        if(forumMimeTypeTransferCache == null)
            forumMimeTypeTransferCache = new ForumMimeTypeTransferCache(userVisit, forumControl);
        
        return forumMimeTypeTransferCache;
    }
    
    public ForumPartyRoleTransferCache getForumPartyRoleTransferCache() {
        if(forumPartyRoleTransferCache == null)
            forumPartyRoleTransferCache = new ForumPartyRoleTransferCache(userVisit, forumControl);
        
        return forumPartyRoleTransferCache;
    }
    
    public ForumPartyTypeRoleTransferCache getForumPartyTypeRoleTransferCache() {
        if(forumPartyTypeRoleTransferCache == null)
            forumPartyTypeRoleTransferCache = new ForumPartyTypeRoleTransferCache(userVisit, forumControl);
        
        return forumPartyTypeRoleTransferCache;
    }
    
    public ForumForumThreadTransferCache getForumForumThreadTransferCache() {
        if(forumForumThreadTransferCache == null)
            forumForumThreadTransferCache = new ForumForumThreadTransferCache(userVisit, forumControl);
        
        return forumForumThreadTransferCache;
    }
    
    public ForumMessagePartTransferCache getForumMessagePartTransferCache() {
        if(forumMessagePartTransferCache == null)
            forumMessagePartTransferCache = new ForumMessagePartTransferCache(userVisit, forumControl);
        
        return forumMessagePartTransferCache;
    }
    
    public ForumMessageRoleTransferCache getForumMessageRoleTransferCache() {
        if(forumMessageRoleTransferCache == null)
            forumMessageRoleTransferCache = new ForumMessageRoleTransferCache(userVisit, forumControl);
        
        return forumMessageRoleTransferCache;
    }
    
    public ForumMessageTransferCache getForumMessageTransferCache() {
        if(forumMessageTransferCache == null)
            forumMessageTransferCache = new ForumMessageTransferCache(userVisit, forumControl);
        
        return forumMessageTransferCache;
    }
    
    public ForumMessageAttachmentTransferCache getForumMessageAttachmentTransferCache() {
        if(forumMessageAttachmentTransferCache == null)
            forumMessageAttachmentTransferCache = new ForumMessageAttachmentTransferCache(userVisit, forumControl);

        return forumMessageAttachmentTransferCache;
    }

    public ForumMessageAttachmentDescriptionTransferCache getForumMessageAttachmentDescriptionTransferCache() {
        if(forumMessageAttachmentDescriptionTransferCache == null)
            forumMessageAttachmentDescriptionTransferCache = new ForumMessageAttachmentDescriptionTransferCache(userVisit, forumControl);

        return forumMessageAttachmentDescriptionTransferCache;
    }

    public ForumThreadTransferCache getForumThreadTransferCache() {
        if(forumThreadTransferCache == null)
            forumThreadTransferCache = new ForumThreadTransferCache(userVisit, forumControl);
        
        return forumThreadTransferCache;
    }
    
}
