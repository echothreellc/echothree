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

package com.echothree.model.control.comment.server.transfer;

import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.transfer.BaseTransferCaches;

public class CommentTransferCaches
        extends BaseTransferCaches {
    
    protected CommentControl commentControl;
    
    protected CommentTypeTransferCache commentTypeTransferCache;
    protected CommentTypeDescriptionTransferCache commentTypeDescriptionTransferCache;
    protected CommentUsageTypeTransferCache commentUsageTypeTransferCache;
    protected CommentUsageTypeDescriptionTransferCache commentUsageTypeDescriptionTransferCache;
    protected CommentTransferCache commentTransferCache;
    protected CommentUsageTransferCache commentUsageTransferCache;
    
    /** Creates a new instance of CommentTransferCaches */
    public CommentTransferCaches(CommentControl commentControl) {
        super();
        
        this.commentControl = commentControl;
    }
    
    public CommentTypeTransferCache getCommentTypeTransferCache() {
        if(commentTypeTransferCache == null)
            commentTypeTransferCache = new CommentTypeTransferCache(commentControl);
        
        return commentTypeTransferCache;
    }
    
    public CommentTypeDescriptionTransferCache getCommentTypeDescriptionTransferCache() {
        if(commentTypeDescriptionTransferCache == null)
            commentTypeDescriptionTransferCache = new CommentTypeDescriptionTransferCache(commentControl);
        
        return commentTypeDescriptionTransferCache;
    }
    
    public CommentUsageTypeTransferCache getCommentUsageTypeTransferCache() {
        if(commentUsageTypeTransferCache == null)
            commentUsageTypeTransferCache = new CommentUsageTypeTransferCache(commentControl);
        
        return commentUsageTypeTransferCache;
    }
    
    public CommentUsageTypeDescriptionTransferCache getCommentUsageTypeDescriptionTransferCache() {
        if(commentUsageTypeDescriptionTransferCache == null)
            commentUsageTypeDescriptionTransferCache = new CommentUsageTypeDescriptionTransferCache(commentControl);
        
        return commentUsageTypeDescriptionTransferCache;
    }
    
    public CommentTransferCache getCommentTransferCache() {
        if(commentTransferCache == null)
            commentTransferCache = new CommentTransferCache(commentControl);
        
        return commentTransferCache;
    }
    
    public CommentUsageTransferCache getCommentUsageTransferCache() {
        if(commentUsageTransferCache == null)
            commentUsageTransferCache = new CommentUsageTransferCache(commentControl);
        
        return commentUsageTransferCache;
    }
    
}
