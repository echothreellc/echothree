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

package com.echothree.model.control.forum.common.transfer;

import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.common.transfer.BaseTransfer;

public class ForumMessageAttachmentTransfer
        extends BaseTransfer {
    
    private ForumMessageTransfer forumMessage;
    private Integer forumMessageAttachmentSequence;
    private MimeTypeTransfer mimeType;
    private String description;
    private ByteArray blob;
    private String clob;
    private String eTag;
    
    /** Creates a new instance of ForumMessageAttachmentTransfer */
    public ForumMessageAttachmentTransfer(ForumMessageTransfer forumMessage, Integer forumMessageAttachmentSequence, MimeTypeTransfer mimeType,
            String description, ByteArray blob, String clob, String eTag) {
        this.forumMessage = forumMessage;
        this.forumMessageAttachmentSequence = forumMessageAttachmentSequence;
        this.mimeType = mimeType;
        this.description = description;
        this.blob = blob;
        this.clob = clob;
        this.eTag = eTag;
    }

    /**
     * Returns the forumMessage.
     * @return the forumMessage
     */
    public ForumMessageTransfer getForumMessage() {
        return forumMessage;
    }

    /**
     * Sets the forumMessage.
     * @param forumMessage the forumMessage to set
     */
    public void setForumMessage(ForumMessageTransfer forumMessage) {
        this.forumMessage = forumMessage;
    }

    /**
     * Returns the forumMessageAttachmentSequence.
     * @return the forumMessageAttachmentSequence
     */
    public Integer getForumMessageAttachmentSequence() {
        return forumMessageAttachmentSequence;
    }

    /**
     * Sets the forumMessageAttachmentSequence.
     * @param forumMessageAttachmentSequence the forumMessageAttachmentSequence to set
     */
    public void setForumMessageAttachmentSequence(Integer forumMessageAttachmentSequence) {
        this.forumMessageAttachmentSequence = forumMessageAttachmentSequence;
    }

    /**
     * Returns the mimeType.
     * @return the mimeType
     */
    public MimeTypeTransfer getMimeType() {
        return mimeType;
    }

    /**
     * Sets the mimeType.
     * @param mimeType the mimeType to set
     */
    public void setMimeType(MimeTypeTransfer mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Returns the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the blob.
     * @return the blob
     */
    public ByteArray getBlob() {
        return blob;
    }

    /**
     * Sets the blob.
     * @param blob the blob to set
     */
    public void setBlob(ByteArray blob) {
        this.blob = blob;
    }

    /**
     * Returns the clob.
     * @return the clob
     */
    public String getClob() {
        return clob;
    }

    /**
     * Sets the clob.
     * @param clob the clob to set
     */
    public void setClob(String clob) {
        this.clob = clob;
    }

    /**
     * Returns the eTag.
     * @return the eTag
     */
    public String geteTag() {
        return eTag;
    }

    /**
     * Sets the eTag.
     * @param eTag the eTag to set
     */
    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

}
