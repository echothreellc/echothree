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

import com.echothree.util.common.transfer.ListWrapper;
import java.util.List;

public class CommentListWrapper
        extends ListWrapper<CommentTransfer> {
    
    private CommentTypeTransfer commentType;
    
    /** Creates a new instance of CommentListWrapper */
    public CommentListWrapper(CommentTypeTransfer commentType, List<CommentTransfer> comments) {
        super(comments);
        
        this.commentType = commentType;
    }
    
    public CommentTypeTransfer getCommentType() {
        return commentType;
    }
    
    public void setCommentType(CommentTypeTransfer commentType) {
        this.commentType = commentType;
    }
    
}
