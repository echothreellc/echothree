// --------------------------------------------------------------------------------
// Copyright 2002-2018 Echo Three, LLC
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

import com.echothree.model.control.comment.remote.transfer.CommentTypeDescriptionTransfer;
import com.echothree.model.control.comment.remote.transfer.CommentTypeTransfer;
import com.echothree.model.control.comment.server.CommentControl;
import com.echothree.model.control.party.remote.transfer.LanguageTransfer;
import com.echothree.model.data.comment.server.entity.CommentTypeDescription;
import com.echothree.model.data.user.server.entity.UserVisit;

public class CommentTypeDescriptionTransferCache
        extends BaseCommentDescriptionTransferCache<CommentTypeDescription, CommentTypeDescriptionTransfer> {
    
    /** Creates a new instance of CommentTypeDescriptionTransferCache */
    public CommentTypeDescriptionTransferCache(UserVisit userVisit, CommentControl commentControl) {
        super(userVisit, commentControl);
    }
    
    public CommentTypeDescriptionTransfer getCommentTypeDescriptionTransfer(CommentTypeDescription commentTypeDescription) {
        CommentTypeDescriptionTransfer commentTypeDescriptionTransfer = get(commentTypeDescription);
        
        if(commentTypeDescriptionTransfer == null) {
            CommentTypeTransferCache commentTypeTransferCache = commentControl.getCommentTransferCaches(userVisit).getCommentTypeTransferCache();
            CommentTypeTransfer commentTypeTransfer = commentTypeTransferCache.getCommentTypeTransfer(commentTypeDescription.getCommentType());
            LanguageTransfer languageTransfer = partyControl.getLanguageTransfer(userVisit, commentTypeDescription.getLanguage());
            
            commentTypeDescriptionTransfer = new CommentTypeDescriptionTransfer(languageTransfer, commentTypeTransfer, commentTypeDescription.getDescription());
            put(commentTypeDescription, commentTypeDescriptionTransfer);
        }
        
        return commentTypeDescriptionTransfer;
    }
    
}
