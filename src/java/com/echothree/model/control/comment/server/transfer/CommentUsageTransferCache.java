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

import com.echothree.model.control.comment.common.transfer.CommentUsageTransfer;
import com.echothree.model.control.comment.server.control.CommentControl;
import com.echothree.model.data.comment.server.entity.CommentUsage;
import com.echothree.model.data.user.server.entity.UserVisit;
import com.echothree.util.server.persistence.Session;

public class CommentUsageTransferCache
        extends BaseCommentTransferCache<CommentUsage, CommentUsageTransfer> {

    CommentControl commentControl = Session.getModelController(CommentControl.class);

    /** Creates a new instance of CommentUsageTransferCache */
    public CommentUsageTransferCache() {
        super();
    }
    
    public CommentUsageTransfer getCommentUsageTransfer(UserVisit userVisit, CommentUsage commentUsage) {
        var commentUsageTransfer = get(commentUsage);
        
        if(commentUsageTransfer == null) {
            commentUsageTransfer = new CommentUsageTransfer();
            put(userVisit, commentUsage, commentUsageTransfer);
            
            commentUsageTransfer.setComment(commentControl.getCommentTransfer(userVisit, commentUsage.getComment()));
            commentUsageTransfer.setCommentUsageType(commentControl.getCommentUsageTypeTransfer(userVisit,
                    commentUsage.getCommentUsageType()));
        }
        
        return commentUsageTransfer;
    }
    
}
