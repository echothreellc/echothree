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

package com.echothree.model.control.comment.common.transfer;

import com.echothree.model.control.core.common.transfer.EntityInstanceTransfer;
import com.echothree.model.control.core.common.transfer.MimeTypeTransfer;
import com.echothree.model.control.party.common.transfer.LanguageTransfer;
import com.echothree.model.control.workflow.common.transfer.WorkflowEntityStatusTransfer;
import com.echothree.util.common.persistence.type.ByteArray;
import com.echothree.util.common.transfer.BaseTransfer;
import com.echothree.util.common.transfer.ListWrapper;

public class CommentTransfer
        extends BaseTransfer {
    
    private String commentName;
    private CommentTypeTransfer commentType;
    private EntityInstanceTransfer commentedEntityInstance;
    private EntityInstanceTransfer commentedByEntityInstance;
    private LanguageTransfer language;
    private String description;
    private MimeTypeTransfer mimeType;
    private String string;
    private ByteArray blob;
    private String clob;
    
    private ListWrapper<CommentUsageTransfer> commentUsages;
    private WorkflowEntityStatusTransfer commentStatus;
    
    /** Creates a new instance of CommentTransfer */
    public CommentTransfer() {
        // Nothing.
    }
    
    /** Creates a new instance of CommentTransfer */
    public CommentTransfer(String commentName, CommentTypeTransfer commentType, EntityInstanceTransfer commentedEntityInstance,
            EntityInstanceTransfer commentedByEntityInstance, LanguageTransfer language, String description,
            MimeTypeTransfer mimeType, String string, ByteArray blob, String clob) {
        this.commentName = commentName;
        this.commentType = commentType;
        this.commentedEntityInstance = commentedEntityInstance;
        this.commentedByEntityInstance = commentedByEntityInstance;
        this.language = language;
        this.description = description;
        this.mimeType = mimeType;
        this.string = string;
        this.blob = blob;
        this.clob = clob;
    }
    
    public String getCommentName() {
        return commentName;
    }
    
    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }
    
    public CommentTypeTransfer getCommentType() {
        return commentType;
    }
    
    public void setCommentType(CommentTypeTransfer commentType) {
        this.commentType = commentType;
    }
    
    public EntityInstanceTransfer getCommentedEntityInstance() {
        return commentedEntityInstance;
    }
    
    public void setCommentedEntityInstance(EntityInstanceTransfer commentedEntityInstance) {
        this.commentedEntityInstance = commentedEntityInstance;
    }
    
    public EntityInstanceTransfer getCommentedByEntityInstance() {
        return commentedByEntityInstance;
    }
    
    public void setCommentedByEntityInstance(EntityInstanceTransfer commentedByEntityInstance) {
        this.commentedByEntityInstance = commentedByEntityInstance;
    }
    
    public LanguageTransfer getLanguage() {
        return language;
    }
    
    public void setLanguage(LanguageTransfer language) {
        this.language = language;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public MimeTypeTransfer getMimeType() {
        return mimeType;
    }
    
    public void setMimeType(MimeTypeTransfer mimeType) {
        this.mimeType = mimeType;
    }
    
    public String getString() {
        return string;
    }
    
    public void setString(String string) {
        this.string = string;
    }
    
    public ByteArray getBlob() {
        return blob;
    }
    
    public void setBlob(ByteArray blob) {
        this.blob = blob;
    }
    
    public String getClob() {
        return clob;
    }
    
    public void setClob(String clob) {
        this.clob = clob;
    }
    
    public ListWrapper<CommentUsageTransfer> getCommentUsages() {
        return commentUsages;
    }
    
    public void setCommentUsages(ListWrapper<CommentUsageTransfer> commentUsages) {
        this.commentUsages = commentUsages;
    }
    
    public WorkflowEntityStatusTransfer getCommentStatus() {
        return commentStatus;
    }
    
    public void setCommentStatus(WorkflowEntityStatusTransfer commentStatus) {
        this.commentStatus = commentStatus;
    }
    
}
