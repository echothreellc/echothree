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

import com.echothree.model.control.core.server.control.MimeTypeControl;
import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.model.control.forum.common.transfer.ForumMessageAttachmentTransfer;
import com.echothree.model.control.forum.server.control.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachment;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.persistence.Session;

public class ForumMessageAttachmentTransferCache
        extends BaseForumTransferCache<ForumMessageAttachment, ForumMessageAttachmentTransfer> {

    MimeTypeControl mimeTypeControl = Session.getModelController(MimeTypeControl.class);
    boolean includeBlob;
    boolean includeClob;
    boolean includeETag;
    
    /** Creates a new instance of ForumMessageAttachmentTransferCache */
    public ForumMessageAttachmentTransferCache(ForumControl forumControl) {
        super(forumControl);
        
        var options = session.getOptions();
        if(options != null) {
            includeBlob = options.contains(ForumOptions.ForumMessageAttachmentIncludeBlob);
            includeClob = options.contains(ForumOptions.ForumMessageAttachmentIncludeClob);
            includeETag = options.contains(ForumOptions.ForumMessageAttachmentIncludeETag);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public ForumMessageAttachmentTransfer getForumMessageAttachmentTransfer(UserVisit userVisit, ForumMessageAttachment forumMessageAttachment) {
        var forumMessageAttachmentTransfer = get(forumMessageAttachment);
        
        if(forumMessageAttachmentTransfer == null) {
            var forumMessageAttachmentDetail = forumMessageAttachment.getLastDetail();
            var forumMessage = forumControl.getForumMessageTransfer(userVisit, forumMessageAttachmentDetail.getForumMessage());
            var forumMessageAttachmentSequence = forumMessageAttachmentDetail.getForumMessageAttachmentSequence();
            var mimeType = mimeTypeControl.getMimeTypeTransfer(userVisit, forumMessageAttachmentDetail.getMimeType());
            var description = forumControl.getBestForumMessageAttachmentDescription(forumMessageAttachment, getLanguage(userVisit));
            ByteArray blob = null;
            String clob = null;
            String eTag = null;
            long eTagEntityId = 0;
            var eTagSize = 0;
            
            if(includeBlob) {
                var forumMessageAttachmentBlob = forumControl.getForumMessageBlobAttachment(forumMessageAttachment);
                
                if(forumMessageAttachmentBlob != null) {
                    blob = forumMessageAttachmentBlob.getBlob();
                }
            }
            
            if(includeClob) {
                var forumMessageAttachmentClob = forumControl.getForumMessageClobAttachment(forumMessageAttachment);
                
                if(forumMessageAttachmentClob != null) {
                    clob = forumMessageAttachmentClob.getClob();
                }
            }
            
            if(includeETag && eTagEntityId != 0) {
                // Forum Message Attachments do not have their own EntityTime, fall back on the Item's EntityTime.
                var entityTimeTransfer = forumMessage.getEntityInstance().getEntityTime();
                var modifiedTime = entityTimeTransfer.getUnformattedModifiedTime();
                long maxTime = modifiedTime == null ? entityTimeTransfer.getUnformattedCreatedTime() : modifiedTime;

                // EntityId-Size-ModifiedTime
                eTag = Long.toHexString(eTagEntityId) + '-' + Integer.toHexString(eTagSize) + '-' + Long.toHexString(maxTime);
            }

            forumMessageAttachmentTransfer = new ForumMessageAttachmentTransfer(forumMessage, forumMessageAttachmentSequence, mimeType, description, blob, clob,
                    eTag);
            put(userVisit, forumMessageAttachment, forumMessageAttachmentTransfer);
        }
        
        return forumMessageAttachmentTransfer;
    }
    
}
