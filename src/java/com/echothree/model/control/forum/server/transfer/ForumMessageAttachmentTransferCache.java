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

import com.echothree.model.control.core.common.transfer.EntityTimeTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.core.server.control.CoreControl;
import com.echothree.model.control.forum.common.ForumOptions;
import com.echothree.model.control.forum.common.transfer.ForumMessageAttachmentTransfer;
import com.echothree.model.control.forum.common.transfer.ForumMessageTransfer;
import com.echothree.model.control.forum.server.ForumControl;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachment;
import com.echothree.model.data.forum.server.entity.ForumMessageAttachmentDetail;
import com.echothree.model.data.forum.server.entity.ForumMessageBlobAttachment;
import com.echothree.model.data.forum.server.entity.ForumMessageClobAttachment;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.server.persistence.Session;
import java.util.Set;

public class ForumMessageAttachmentTransferCache
        extends BaseForumTransferCache<ForumMessageAttachment, ForumMessageAttachmentTransfer> {
    
    CoreControl coreControl = (CoreControl)Session.getModelController(CoreControl.class);
    boolean includeBlob;
    boolean includeClob;
    boolean includeETag;
    
    /** Creates a new instance of ForumMessageAttachmentTransferCache */
    public ForumMessageAttachmentTransferCache(UserVisit userVisit, ForumControl forumControl) {
        super(userVisit, forumControl);
        
        Set<String> options = session.getOptions();
        if(options != null) {
            includeBlob = options.contains(ForumOptions.ForumMessageAttachmentIncludeBlob);
            includeClob = options.contains(ForumOptions.ForumMessageAttachmentIncludeClob);
            includeETag = options.contains(ForumOptions.ForumMessageAttachmentIncludeETag);
        }
        
        setIncludeEntityInstance(true);
    }
    
    public ForumMessageAttachmentTransfer getForumMessageAttachmentTransfer(ForumMessageAttachment forumMessageAttachment) {
        ForumMessageAttachmentTransfer forumMessageAttachmentTransfer = get(forumMessageAttachment);
        
        if(forumMessageAttachmentTransfer == null) {
            ForumMessageAttachmentDetail forumMessageAttachmentDetail = forumMessageAttachment.getLastDetail();
            ForumMessageTransfer forumMessage = forumControl.getForumMessageTransfer(userVisit, forumMessageAttachmentDetail.getForumMessage());
            Integer forumMessageAttachmentSequence = forumMessageAttachmentDetail.getForumMessageAttachmentSequence();
            MimeTypeTransfer mimeType = coreControl.getMimeTypeTransfer(userVisit, forumMessageAttachmentDetail.getMimeType());
            String description = forumControl.getBestForumMessageAttachmentDescription(forumMessageAttachment, getLanguage());
            ByteArray blob = null;
            String clob = null;
            String eTag = null;
            long eTagEntityId = 0;
            int eTagSize = 0;
            
            if(includeBlob) {
                ForumMessageBlobAttachment forumMessageAttachmentBlob = forumControl.getForumMessageBlobAttachment(forumMessageAttachment);
                
                if(forumMessageAttachmentBlob != null) {
                    blob = forumMessageAttachmentBlob.getBlob();
                }
            }
            
            if(includeClob) {
                ForumMessageClobAttachment forumMessageAttachmentClob = forumControl.getForumMessageClobAttachment(forumMessageAttachment);
                
                if(forumMessageAttachmentClob != null) {
                    clob = forumMessageAttachmentClob.getClob();
                }
            }
            
            if(includeETag && eTagEntityId != 0) {
                // Forum Message Attachments do not have their own EntityTime, fall back on the Item's EntityTime.
                EntityTimeTransfer entityTimeTransfer = forumMessage.getEntityInstance().getEntityTime();
                Long modifiedTime = entityTimeTransfer.getUnformattedModifiedTime();
                long maxTime = modifiedTime == null ? entityTimeTransfer.getUnformattedCreatedTime() : modifiedTime;

                // EntityId-Size-ModifiedTime
                eTag = new StringBuilder(Long.toHexString(eTagEntityId)).append('-').append(Integer.toHexString(eTagSize)).append('-').append(Long.toHexString(maxTime)).toString();
            }

            forumMessageAttachmentTransfer = new ForumMessageAttachmentTransfer(forumMessage, forumMessageAttachmentSequence, mimeType, description, blob, clob,
                    eTag);
            put(forumMessageAttachment, forumMessageAttachmentTransfer);
        }
        
        return forumMessageAttachmentTransfer;
    }
    
}
