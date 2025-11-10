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

import com.echothree.model.control.comment.common.transfer.CommentUsageTypeTransfer;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.data.comment.server.entity.CommentUsageType;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CommentUsageTypeTransferCache
        extends BaseCommentTransferCache<CommentUsageType, CommentUsageTypeTransfer> {
    
    /** Creates a new instance of CommentUsageTypeTransferCache */
    public CommentUsageTypeTransferCache(UserVisit userVisit, CommentControl commentControl) {
        super(userVisit, commentControl);
        
        setIncludeEntityInstance(true);
    }
    
    public CommentUsageTypeTransfer getCommentUsageTypeTransfer(CommentUsageType commentUsageType) {
        var commentUsageTypeTransfer = get(commentUsageType);
        
        if(commentUsageTypeTransfer == null) {
            var commentUsageTypeDetail = commentUsageType.getLastDetail();
            var commentType = commentControl.getCommentTypeTransfer(userVisit, commentUsageTypeDetail.getCommentType());
            var commentUsageTypeName = commentUsageTypeDetail.getCommentUsageTypeName();
            var selectedByDefault = commentUsageTypeDetail.getSelectedByDefault();
            var description = commentControl.getBestCommentUsageTypeDescription(commentUsageType, getLanguage(userVisit));
            
            commentUsageTypeTransfer = new CommentUsageTypeTransfer(commentType, commentUsageTypeName, selectedByDefault, description);
            put(userVisit, commentUsageType, commentUsageTypeTransfer);
        }
        
        return commentUsageTypeTransfer;
    }
    
}
