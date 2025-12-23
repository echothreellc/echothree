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

import com.echothree.util.common.transfer.BaseTransfer;

public class CommentUsageTypeTransfer
        extends BaseTransfer {
    
    private CommentTypeTransfer commentType;
    private String commentUsageTypeName;
    private Boolean selectedByDefault;
    private String description;
    
    /** Creates a new instance of CommentUsageTypeTransfer */
    public CommentUsageTypeTransfer(CommentTypeTransfer commentType, String commentUsageTypeName, Boolean selectedByDefault, String description) {
        this.commentType = commentType;
        this.commentUsageTypeName = commentUsageTypeName;
        this.selectedByDefault = selectedByDefault;
        this.description = description;
    }
    
    public CommentTypeTransfer getCommentType() {
        return commentType;
    }
    
    public void setCommentType(CommentTypeTransfer commentType) {
        this.commentType = commentType;
    }
    
    public String getCommentUsageTypeName() {
        return commentUsageTypeName;
    }
    
    public void setCommentUsageTypeName(String commentUsageTypeName) {
        this.commentUsageTypeName = commentUsageTypeName;
    }
    
    public Boolean getSelectedByDefault() {
        return selectedByDefault;
    }
    
    public void setSelectedByDefault(Boolean selectedByDefault) {
        this.selectedByDefault = selectedByDefault;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
